package com.codecentric.retailbank.model.domain;

import com.codecentric.retailbank.model.dto.BranchDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Branch {

    //region FIELDS
    private Long id;

    private Address address;

    @NotNull
    private Bank bank;

    @NotNull
    private RefBranchType refBranchType;

    @Size(max = 255)
    private String details;
    //endregion

    //region CONSTRUCTORS
    public Branch() {
    }

    public Branch(Long id) {
        this.id = id;
    }

    public Branch(Long id, @Size(max = 255) String details) {
        this.id = id;
        this.details = details;
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
    //endregion

    //region GETTERS / SETTERS
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
    //endregion

    //region HELPERS
    @JsonIgnore
    public void setFields(Address address,
                          @NotNull Bank bank,
                          @NotNull RefBranchType refBranchType,
                          @Size(max = 255) String details) {
        this.address = address;
        this.bank = bank;
        this.refBranchType = refBranchType;
        this.details = details;
    }

    @JsonIgnore
    public BranchDto getDto() {
        return new BranchDto(
                this.id,
                this.address != null ? this.address.getDto() : null,
                this.bank != null ? this.bank.getDto() : null,
                this.refBranchType != null ? this.refBranchType.getDto() : null,
                this.details
        );
    }
    //endregion
}
