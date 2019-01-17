package com.codecentric.retailbank.model.domain.OLD;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    @GeneratedValue
    private Long id;

    @NotNull
    @OneToOne(targetEntity = BankAccount.class)
    @JoinColumn(name = "account_number")
    private BankAccount account;

    @NotNull
    @OneToOne(targetEntity = Merchant.class)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @NotNull
    @OneToOne(targetEntity = RefTransactionType.class)
    @JoinColumn(name = "transaction_type_id")
    private RefTransactionType type;

    @NotNull
    @Column(name = "transaction_date_time")
    private Date date;

    @NotNull
    @Column(name = "transaction_amount")
    private BigDecimal amount;

    @Length(max = 255)
    @Column(name = "other_details")
    private String details;


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
}
