package com.codecentric.retailbank.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class BranchDto {

    //region FIELDS
    private Long id;

    private AddressDto address;

    @NotNull
    private BankDto bank;

    @NotNull
    private RefBranchTypeDto type;

    @Length(min = 7, max = 255, message = "Details field must contain between 7 and 255 characters.")
    private String details;
    //endregion

    //region CONSTRUCTORS
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
    //endregion

    //region GETTERS / SETTERS
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
    //endregion
}
