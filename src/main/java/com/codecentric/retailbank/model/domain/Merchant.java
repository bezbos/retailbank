package com.codecentric.retailbank.model.domain;

import org.attoparser.trace.MarkupTraceEvent;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "merchants")
public class Merchant {

    @Id
    @Column(name = "merchant_id")
    @GeneratedValue
    private Long id;

    @Length(max = 255)
    @Column(name = "merchant_details")
    private String details;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
