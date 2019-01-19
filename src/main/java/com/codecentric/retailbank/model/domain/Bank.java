package com.codecentric.retailbank.model.domain;

import com.codecentric.retailbank.model.dto.BankDto;

import javax.validation.constraints.Size;

public class Bank {

    private Long id;

    @Size(min = 7, max = 255, message = "Details field must contain between 7 and 255 characters.")
    private String details;


    public Bank() {
    }

    public Bank(String details) {
        this.details = details;
    }

    public Bank(Long id) {
        this.id = id;
    }

    public Bank(Long id, String details) {
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

    public BankDto getDto(){
        return new BankDto(
          this.id,
          this.details
        );
    }
}
