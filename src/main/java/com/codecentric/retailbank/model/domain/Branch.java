package com.codecentric.retailbank.model.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Branch {

    private Long id;

    private Address addressId;

    @NotNull
    private Bank bankId;

    @NotNull
    private RefBranchType refBranchTypeId;

    @Size(max = 255)
    private String details;


    public Branch() {
        super();
    }

    public Branch(Long id) {
        super();
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddressId() {
        return addressId;
    }

    public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

    public Bank getBankId() {
        return bankId;
    }

    public void setBankId(Bank bankId) {
        this.bankId = bankId;
    }

    public RefBranchType getRefBranchTypeId() {
        return refBranchTypeId;
    }

    public void setRefBranchTypeId(RefBranchType branchTypeCode) {
        this.refBranchTypeId = branchTypeCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public void setFields(Address address,
                          @NotNull Bank bank,
                          @NotNull RefBranchType type,
                          @Size(max = 255) String details) {
        this.addressId = address;
        this.bankId = bank;
        this.refBranchTypeId = type;
        this.details = details;
    }

}
