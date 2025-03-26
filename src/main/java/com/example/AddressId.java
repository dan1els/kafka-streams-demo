package com.example;


import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDate;

public class AddressId {

    private Long id;
    private LocalDate reportingDate;

    public AddressId() {

    }

    public AddressId(Long id, LocalDate reportingDate) {
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


    public static class AddressIdSerde extends JsonSerde<AddressId> {}
}
