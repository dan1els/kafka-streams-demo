package com.example;

import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDate;

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

    public static class CustomerIdSerde extends JsonSerde<CustomerId> {}
}
