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
    private BankAccountDto senderAccount;

    @NotNull
    private BankAccountDto receiverAccount;

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
    public TransactionDto(Long id,
                          BankAccountDto senderAccount,
                          BankAccountDto receiverAccount,
                          MerchantDto merchant,
                          RefTransactionTypeDto type,
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

    public BankAccountDto getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(BankAccountDto senderAccount) {
        this.senderAccount = senderAccount;
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

    public BankAccountDto getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(BankAccountDto receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
    //endregion

    //region HELPERS
    @JsonIgnore
    public Transaction getDBModel() {
        return new Transaction(
                this.id,
                this.senderAccount != null ? this.senderAccount.getDBModel() : null,
                this.receiverAccount != null ? this.receiverAccount.getDBModel() : null,
                this.merchant != null ? merchant.getDBModel() : null,
                this.type != null ? this.type.getDBModel() : null,
                this.date,
                this.amount,
                this.details
        );
    }
    //endregion
}
