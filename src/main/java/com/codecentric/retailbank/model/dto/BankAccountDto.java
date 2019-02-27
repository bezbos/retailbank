package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.domain.BankAccount;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class BankAccountDto {
    //region FIELDS
    private Long id;

    @NotNull
    private RefAccountStatusDto status;

    @NotNull
    private RefAccountTypeDto type;

    @NotNull
    private CustomerDto customer;

    @NotNull
    private BigDecimal balance;

    @Size(max = 255)
    private String details;
    //endregion

    //region CONSTRUCTORS
    public BankAccountDto() {
    }

    public BankAccountDto(Long id) {
        this.id = id;
    }

    public BankAccountDto(Long id, @NotNull BigDecimal balance, @Size(max = 255) String details) {
        this.id = id;
        this.balance = balance;
        this.details = details;
    }

    public BankAccountDto(Long id,
                          @NotNull RefAccountStatusDto status,
                          @NotNull RefAccountTypeDto type,
                          @NotNull CustomerDto customer,
                          @NotNull BigDecimal balance,
                          @Size(max = 255) String details) {
        this.id = id;
        this.status = status;
        this.type = type;
        this.customer = customer;
        this.balance = balance;
        this.details = details;
    }
    //endregion

    //region SETTERS / GETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefAccountStatusDto getStatus() {
        return status;
    }

    public void setStatus(RefAccountStatusDto status) {
        this.status = status;
    }

    public RefAccountTypeDto getType() {
        return type;
    }

    public void setType(RefAccountTypeDto type) {
        this.type = type;
    }

    public CustomerDto getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDto customer) {
        this.customer = customer;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
    public BankAccount getDBModel() {
        return new BankAccount(
                this.id,
                this.status != null ? this.status.getDBModel() : null,
                this.type != null ? this.type.getDBModel() : null,
                this.customer != null ? this.customer.getDBModel() : null,
                this.balance,
                this.details
        );
    }
    //endregion
}
