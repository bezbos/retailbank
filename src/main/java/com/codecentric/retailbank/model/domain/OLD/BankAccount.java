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

@Entity
@Table(name = "accounts")
public class BankAccount {

    @Id
    @Column(name = "account_number")
    @GeneratedValue
    private Long id;

    @NotNull
    @OneToOne(targetEntity = RefAccountStatus.class)
    @JoinColumn(name = "account_status_id")
    private RefAccountStatus status;

    @NotNull
    @OneToOne(targetEntity = RefAccountType.class)
    @JoinColumn(name = "account_type_id")
    private RefAccountType type;

    @NotNull
    @OneToOne(targetEntity = Customer.class)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    @Column(name = "current_balance")
    private BigDecimal balance;

    @Length(max = 255)
    @Column(name = "other_details")
    private String details;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RefAccountStatus getStatus() {
        return status;
    }

    public void setStatus(RefAccountStatus status) {
        this.status = status;
    }

    public RefAccountType getType() {
        return type;
    }

    public void setType(RefAccountType type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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
}
