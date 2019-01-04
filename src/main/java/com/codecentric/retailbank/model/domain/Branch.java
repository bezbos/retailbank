package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @Column(name = "branch_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @OneToOne(targetEntity = Address.class)
    @PrimaryKeyJoinColumn
    private Address address;

    @NotNull
    @OneToOne(targetEntity = Bank.class)
    @PrimaryKeyJoinColumn
    private Bank bank;

    @NotNull
    @OneToOne(targetEntity = RefBranchType.class)
    @PrimaryKeyJoinColumn
    private RefBranchType type;

    @Length(max = 255)
    @Column(name = "branch_details")
    private String details;


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

    public RefBranchType getType() {
        return type;
    }

    public void setType(RefBranchType branchTypeCode) {
        this.type = branchTypeCode;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
