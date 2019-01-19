package com.codecentric.retailbank.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class BranchDto {

    private Long id;

    private AddressDto address;

    @NotNull
    private BankDto bank;

    @NotNull
    private RefBranchTypeDto type;

    @Length(min = 7, max = 255, message = "Details field must contain between 7 and 255 characters.")
    private String details;


    public BranchDto() {
    }

    public BranchDto(Long id) {
        this.id = id;
    }

    public BranchDto(Long id,
                     AddressDto address,
                     BankDto bank,
                     RefBranchTypeDto type,
                     String details) {
        this.id = id;
        this.address = address;
        this.bank = bank;
        this.type = type;
        this.details = details;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public BankDto getBank() {
        return bank;
    }

    public void setBank(BankDto bank) {
        this.bank = bank;
    }

    public RefBranchTypeDto getType() {
        return type;
    }

    public void setType(RefBranchTypeDto type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
