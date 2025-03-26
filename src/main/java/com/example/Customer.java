package com.example;

import org.springframework.kafka.support.serializer.JsonSerde;

import java.io.Serializable;
import java.time.LocalDate;

public class Customer {
    private Long id;
    private LocalDate reportingDate;
    private String firstName;
    private String lastName;

    public Customer() {

    }

    public Customer(CustomerId id, String firstName, String lastName) {
        this(id.getId(), id.getReportingDate(), firstName, lastName);
    }

    public Customer(Long id, LocalDate reportingDate, String firstName, String lastName) {
        this.id = id;
        this.reportingDate = reportingDate;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Customer(Long customerId, LocalDate reportingDate) {
        this.id = customerId;
        this.reportingDate = reportingDate;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    // Getters and setters

    public static class CustomerSerde extends JsonSerde<Customer> {}
}
