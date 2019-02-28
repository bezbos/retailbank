package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.domain.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {

    //region FIELDS
    private Long id;

    @NotNull
    private BankAccountDto account;

    @NotNull
    private MerchantDto merchant;

    @NotNull
    private RefTransactionTypeDto type;

    @NotNull
    private Date date;

    @NotNull
    private BigDecimal amount;

    @Size(max = 255)
    private String details;
    //endregion

    //region CONSTRUCTOR
    public TransactionDto() {
    }

    public TransactionDto(Long id,
                          @NotNull BankAccountDto account,
                          @NotNull MerchantDto merchant,
                          @NotNull RefTransactionTypeDto type,
                          @NotNull Date date,
                          @NotNull BigDecimal amount,
                          @Size(max = 255) String details) {
        this.id = id;
        this.account = account;
        this.merchant = merchant;
        this.type = type;
        this.date = date;
        this.amount = amount;
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

    public BankAccountDto getAccount() {
        return account;
    }

    public void setAccount(BankAccountDto account) {
        this.account = account;
    }

    public MerchantDto getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantDto merchant) {
        this.merchant = merchant;
    }

    public RefTransactionTypeDto getType() {
        return type;
    }

    public void setType(RefTransactionTypeDto type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
    public Transaction getDBModel() {
        return new Transaction(
                this.id,
                this.account != null ? this.account.getDBModel() : null,
                this.merchant != null ? merchant.getDBModel() : null,
                this.type != null ? this.type.getDBModel() : null,
                this.date,
                this.amount,
                this.details
        );
    }
    //endregion
}
