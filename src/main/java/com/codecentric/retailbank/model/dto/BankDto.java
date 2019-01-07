package com.codecentric.retailbank.model.dto;

import org.hibernate.validator.constraints.Length;

public class BankDto {

    private Long id;

    @Length(min = 7, max = 255, message = "Details field must contain between 7 and 255 characters.")
    private String details;

    public BankDto() {
        super();
    }

    public BankDto(Long id, String details) {
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
