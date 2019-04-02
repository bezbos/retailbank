package com.codecentric.retailbank.model.domain;

import com.codecentric.retailbank.model.dto.TransactionDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

public class Transaction {

    //region FIELDS
    private Long id;

    @NotNull
    private BankAccount senderAccount;

    @NotNull
    private BankAccount receiverAccount;

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
                       BankAccount senderAccount,
                       BankAccount receiverAccount,
                       Merchant merchant,
                       RefTransactionType type,
                       Date date,
                       BigDecimal amount,
                       String details) {
        this.id = id;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
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

    public BankAccount getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(BankAccount senderAccount) {
        this.senderAccount = senderAccount;
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

    public BankAccount getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(BankAccount receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
    //endregion

    //region HELPERS
    @JsonIgnore
    public TransactionDto getDto() {
        return new TransactionDto(
                this.id,
                this.senderAccount.getDto(),
                this.receiverAccount.getDto(),
                this.merchant.getDto(),
                this.type.getDto(),
                this.date,
                this.amount,
                this.details
        );
    }
    //endregion
}
