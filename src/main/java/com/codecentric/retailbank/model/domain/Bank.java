package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;

import javax.persistence.*;

@Entity
@Table(name = "banks")
public class Bank {
    @Id
    @Column(name = "bank_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(max = 255)
    @Column(name = "bank_details")
    private String bankDetails;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }
}
