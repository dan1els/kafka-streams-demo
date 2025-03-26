package com.example;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
public class Main {

    @Configuration
    @EnableKafka
    @EnableKafkaStreams
    public static class KafkaConfig {

        @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
        KafkaStreamsConfiguration kStreamsConfig() {
            Map<String, Object> config = new HashMap<>();
            config.put(StreamsConfig.APPLICATION_ID_CONFIG, "enriched-orders-app");
            config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            config.put(StreamsConfig.STATESTORE_CACHE_MAX_BYTES_CONFIG, "10485760"); // 10 MB cache
            config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
            config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
            config.put(StreamsConfig.STATE_DIR_CONFIG, "/Users/ruaryec/Developer/work/streams-test/untitled");

            return new KafkaStreamsConfiguration(config);
        }

        @Bean
        <T> KafkaProducer<Long, T> producer() {
            String bootstrapServers = "localhost:9092";
            Properties producerConfig = new Properties();
            producerConfig.put("bootstrap.servers", bootstrapServers);
            producerConfig.put("key.serializer", Serdes.Long().serializer().getClass().getName());
            producerConfig.put("value.serializer", JsonSerializer.class.getName());
            return new KafkaProducer<>(producerConfig);
        }


        // other config
    }

    @Component
    public class Joiner {


        @Autowired
        void buildPipeline(StreamsBuilder streamsBuilder) {


            KTable<Long, Order> ordersTable = streamsBuilder.table(
                    "orders", Consumed.with(Serdes.Long(), new Order.OrderSerde())
            );
            KTable<Long, Customer> customersTable = streamsBuilder.table(
                    "customers",
                    Consumed.with(Serdes.Long(), new Customer.CustomerSerde())
            );
            KTable<Long, Address> addressesTable = streamsBuilder.table(
                    "addresses",
                    Consumed.with(Serdes.Long(), new Address.AddressSerde())
            );

            // Join Orders with Customers on customer_id
            ordersTable
                    .join(
                            customersTable,
                            (order) -> order.getCustomerId(), // Extract foreign key (customer_id) from com.example.Order
                            EnrichedOrder::new,
                            Materialized.with(Serdes.Long(), new EnrichedOrder.EnrichedOderSerde()) // need to materialize join to add second join
                    )
                   .join(
                            addressesTable,
                            (order) -> order.getAddressId(), // Extract foreign key (address_id) from com.example.Order
                            (order, address) -> {
                                order.setAddress(address);
                                return order; // Enrich order with address details
                            }
                            //Materialized.with(Serdes.String(), new EnrichedOrder.EnrichedOderSerde())
                    )
                    .toStream()
                    .peek((key, value) -> System.out.println(value))
                    .to("enriched-orders-out", Produced.with(Serdes.Long(), new EnrichedOrder.EnrichedOderSerde()));
        }
    }

    public static void main(String[] args) {

        createTopic("orders");
        createTopic("customers");
        createTopic("addresses");
        createTopic("enriched-orders-out");


        SpringApplication.run(Main.class, args);
    }

    private static void createTopic(String name) {
        try {
            Properties props = new Properties();
            props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
            AdminClient adminClient = AdminClient.create(props);

            int numPartitions = 3;
            short replicationFactor = 1;
            NewTopic newTopic = new NewTopic(name, numPartitions, replicationFactor);

            var result = adminClient.createTopics(Collections.singleton(newTopic)).all().get();
        } catch (Exception e) {

        }
    }
}
