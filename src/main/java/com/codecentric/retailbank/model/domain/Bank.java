package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @Column(name = "bank_id")
    @GeneratedValue
    private Long id;

    @Length(min = 7, max = 255, message = "Details field must contain between 7 and 255 characters.")
    @Column(name = "bank_details")
    private String details;

    public Bank() {
        super();
    }

    public Bank(String details) {
        this.details = details;
    }

    public Bank(Long id) {
        this.id = id;
    }


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
