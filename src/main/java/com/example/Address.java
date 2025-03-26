package com.example;

import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDate;

public class Address {
    private Long id;
    private LocalDate reportingDate;
    private String street;
    private String city;

    public Address() {

    }

    public Address(Long id, LocalDate reportingDate) {
        this(id, reportingDate, null, null);
    }

    public Address(AddressId id, String firstName, String lastName) {
        this(id.getId(), id.getReportingDate(), firstName, lastName);
    }

    public Address(Long id, LocalDate reportingDate, String street, String city) {
        this.id = id;
        this.reportingDate = reportingDate;
        this.street = street;
        this.city = city;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public static class AddressSerde extends JsonSerde<Address> {}

}
