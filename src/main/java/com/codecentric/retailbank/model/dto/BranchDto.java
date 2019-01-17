package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.domain.Address;
import com.codecentric.retailbank.model.domain.Bank;
import com.codecentric.retailbank.model.domain.RefBranchType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class BranchDto {

    private Long id;

    private Address address;

    @NotNull
    private Bank bank;

    @NotNull
    private RefBranchType type;

    @Length(min = 7, max = 255, message = "Details field must contain between 7 and 255 characters.")
    private String details;


    public BranchDto() {
    }

    public BranchDto(Long id,
                     Address address,
                     @NotNull Bank bank,
                     @NotNull RefBranchType type,
                     @Length(min = 7, max = 255) String details) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public RefBranchType getType() {
        return type;
    }

    public void setType(RefBranchType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
