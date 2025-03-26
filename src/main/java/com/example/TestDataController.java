package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.stream.IntStream;

@RestController("/test")
public class TestDataController {

    private final KafkaProducer<Long, Order> orderProducer;
    private final KafkaProducer<Long, Customer> customerProducer;
    private final KafkaProducer<Long, Address> addressProducer;

    private final LocalDate reportingDate = LocalDate.now();
    @Autowired
    public TestDataController(KafkaProducer<Long, Order> orderProducer, KafkaProducer<Long, Customer> customerProducer, KafkaProducer<Long, Address> addressProducer) {
        this.orderProducer = orderProducer;
        this.customerProducer = customerProducer;
        this.addressProducer = addressProducer;
    }

    @PostMapping("/order")
    public void order(@RequestBody OrderDto dto) {
        Integer key = dto.id;
        Order order = new Order(key.longValue(), dto.reportingDate, dto.customerId.longValue(), dto.addressId.longValue());
        orderProducer.send(new ProducerRecord<>("orders", key.longValue(), order));
    }

    @PostMapping("/order/{size}")
    public void order(@PathVariable Integer size) {

        IntStream.range(0, size).forEach(i -> {
            Integer key = i;
            Order order = new Order(key.longValue(), reportingDate, (long) i, (long) i);
            orderProducer.send(new ProducerRecord<>("orders", key.longValue(), order));
        });

    }

    @PostMapping("/customer")
    public void customer(@RequestBody AddressDto dto) {
        var key = new CustomerId(dto.id.longValue(), dto.reportingDate);
        Customer customer = new Customer(key, "FirstName" + dto.id, "LastName" + dto.id);
        customerProducer.send(new ProducerRecord<>("customers", key.getId().longValue(), customer));
    }

    @PostMapping("/customer/{size}")
    public void customer(@PathVariable Integer size) {

        IntStream.range(0, size).forEach(i -> {
            var key = new CustomerId((long) i, reportingDate);
            Customer customer = new Customer(key, "FirstName" + i, "LastName" + i);
            customerProducer.send(new ProducerRecord<>("customers", key.getId(), customer));
        });

    }

    @PostMapping("/address")
    public void address(@RequestBody CustomerDto dto) {
        var key = new AddressId(dto.id.longValue(), dto.reportingDate);
        Address address = new Address(key,  "Street" + dto.id, "City" + dto.id);
        addressProducer.send(new ProducerRecord<>("addresses", key.getId(), address));
    }

    @PostMapping("/address/{size}")
    public void address(@PathVariable Integer size) {

        IntStream.range(0, size).forEach(i -> {
            var key = new AddressId((long) i, reportingDate);
            Address address = new Address(key,  "Street" + i, "City" + i);
            addressProducer.send(new ProducerRecord<>("addresses", key.getId(), address));
        });

    }

    public static class OrderDto {
        LocalDate reportingDate;
        Integer id;
        Integer addressId;
        Integer customerId;

        public LocalDate getReportingDate() {
            return reportingDate;
        }

        public void setReportingDate(LocalDate reportingDate) {
            this.reportingDate = reportingDate;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAddressId() {
            return addressId;
        }

        public void setAddressId(Integer addressId) {
            this.addressId = addressId;
        }

        public Integer getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Integer customerId) {
            this.customerId = customerId;
        }
    }

    public static class AddressDto {
        LocalDate reportingDate;
        Integer id;

        public LocalDate getReportingDate() {
            return reportingDate;
        }

        public void setReportingDate(LocalDate reportingDate) {
            this.reportingDate = reportingDate;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public static class CustomerDto {
        LocalDate reportingDate;
        Integer id;

        public LocalDate getReportingDate() {
            return reportingDate;
        }

        public void setReportingDate(LocalDate reportingDate) {
            this.reportingDate = reportingDate;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

}
