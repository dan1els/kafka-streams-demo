package com.example;

import org.springframework.kafka.support.serializer.JsonSerde;

import java.io.Serializable;
import java.time.LocalDate;

public class Order implements Serializable {
    private Long id;
    private LocalDate reportingDate;
    private Long customerId;
    protected Long addressId;

    public Order() {
    }

    public Order(Long id, LocalDate reportingDate, Long customerId, Long addressId) {
        this.id = id;
        this.reportingDate = reportingDate;
        this.customerId = customerId;
        this.addressId = addressId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(LocalDate reportingDate) {
        this.reportingDate = reportingDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", reportingDate=" + reportingDate +
                ", customerId='" + customerId + '\'' +
                ", addressId='" + addressId + '\'' +
                '}';
    }

    // Getters and setters

    public static class OrderSerde extends JsonSerde<Order> {}
}