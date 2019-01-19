package com.codecentric.retailbank.model.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Branch {

    private Long id;

    private Address address;

    @NotNull
    private Bank bank;

    @NotNull
    private RefBranchType refBranchType;

    @Size(max = 255)
    private String details;


    public Branch() {
    }

    public Branch(Long id) {
        this.id = id;
    }

    public Branch(Long id,
                  Address address,
                  @NotNull Bank bank,
                  @NotNull RefBranchType refBranchType,
                  @Size(max = 255) String details) {
        this.id = id;
        this.address = address;
        this.bank = bank;
        this.refBranchType = refBranchType;
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

    public RefBranchType getRefBranchType() {
        return refBranchType;
    }

    public void setRefBranchType(RefBranchType branchTypeCode) {
        this.refBranchType = branchTypeCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setFields(Address address,
                          @NotNull Bank bank,
                          @NotNull RefBranchType refBranchType,
                          @Size(max = 255) String details) {
        this.address = address;
        this.bank = bank;
        this.refBranchType = refBranchType;
        this.details = details;
    }
}