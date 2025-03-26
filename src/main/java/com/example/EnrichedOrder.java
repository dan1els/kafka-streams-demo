package com.example;

import org.springframework.kafka.support.serializer.JsonSerde;

import java.io.Serializable;
import java.time.LocalDate;

public class EnrichedOrder extends Order implements Serializable {

    private Long id;
    private LocalDate reportingDate;
    private Customer customer;
    private Address address;

    public EnrichedOrder() {

    }

    public EnrichedOrder(Order order, Customer customer) {
        this.id = order.getId();
        this.reportingDate = order.getReportingDate();
        this.customer = customer;
        this.addressId = order.getAddressId();
    }

    public EnrichedOrder(Order order, Address address, Customer customer) {
        this.id = order.getId();
        this.reportingDate = order.getReportingDate();
        this.customer = customer;
        this.address = address;
        this.addressId = order.getAddressId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public LocalDate getReportingDate() {
        return reportingDate;
    }

    public void setReportingDate(LocalDate reportingDate) {
        this.reportingDate = reportingDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "EnrichedOrder{" +
                "id='" + id + '\'' +
                ", reportingDate=" + reportingDate +
                ", customer=" + customer +
                ", address=" + address +
                '}';
    }

    public static class EnrichedOderSerde extends JsonSerde<EnrichedOrder> {}
}
