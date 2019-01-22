package com.codecentric.retailbank.model.domain;

import javax.validation.constraints.Size;

public class Merchant {

    //region FIELDS
    private Long id;

    @Size(max = 255)
    private String details;
    //endregion

    //region CONSTRUCTORS
    public Merchant(Long id) {
        this.id = id;
    }

    public Merchant(Long id, @Size(max = 255) String details) {
        this.id = id;
        this.details = details;
    }
    //endregion

    //region GETTERS / SETTERS
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
    //endregion
}
