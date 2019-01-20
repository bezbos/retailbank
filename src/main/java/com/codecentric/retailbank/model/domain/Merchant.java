package com.codecentric.retailbank.model.domain;

import javax.validation.constraints.Size;

public class Merchant {

    private Long id;

    @Size(max = 255)
    private String details;


    public Merchant(Long id) {
        this.id = id;
    }

    public Merchant(Long id, @Size(max = 255) String details) {
        this.id = id;
        this.details = details;
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
