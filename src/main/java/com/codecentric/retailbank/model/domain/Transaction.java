package com.codecentric.retailbank.model.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    //region FIELDS
    private Long id;

    @NotNull
    private BankAccount account;

    @NotNull
    private Merchant merchant;

    @NotNull
    private RefTransactionType type;

    @NotNull
    private Date date;

    @NotNull
    private BigDecimal amount;

    @Size(max = 255)
    private String details;
    //endregion

    //region CONSTRUCTOR
    public Transaction() {
    }

    public Transaction(Long id,
                       @NotNull BankAccount account,
                       @NotNull Merchant merchant,
                       @NotNull RefTransactionType type,
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

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }

    public RefTransactionType getType() {
        return type;
    }

    public void setType(RefTransactionType type) {
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
}
