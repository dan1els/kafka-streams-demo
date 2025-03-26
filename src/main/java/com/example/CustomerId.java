package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Objects;

public class CustomerId {

    private Long id;
    private LocalDate reportingDate;

    public CustomerId() {

    }

    public CustomerId(Long id, LocalDate reportingDate) {
        this.id = id;
        this.reportingDate = reportingDate;
    }

    public LocalDate getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(LocalDate reportingDate) {
        this.reportingDate = reportingDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CustomerId that = (CustomerId) o;
        return Objects.equals(id, that.id) && Objects.equals(reportingDate, that.reportingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reportingDate);
    }

    public static class CustomerIdSerde extends Serdes.WrapperSerde<CustomerId> {
        public CustomerIdSerde() {
            super(new CustomerIdSerdeSerializer(), new CustomerIdSerdeDeserializer());
        }

        public static class CustomerIdSerdeSerializer implements Serializer<CustomerId> {

            private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            @Override
            public byte[] serialize(String s, CustomerId customerId) {
                try {
                    return objectMapper.writeValueAsBytes(customerId);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

        }

        public static class CustomerIdSerdeDeserializer implements Deserializer<CustomerId> {

            private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

            @Override
            public CustomerId deserialize(String s, byte[] bytes) {
                try {
                    return objectMapper.readValue(bytes, CustomerId.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
