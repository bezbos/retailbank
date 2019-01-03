package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    private RefBranchType branchType;

    @Length(max = 255)
    private String branchDetails;


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

    public RefBranchType getBranchType() {
        return branchType;
    }

    public void setBranchType(RefBranchType branchTypeCode) {
        this.branchType = branchTypeCode;
    }

    public String getBranchDetails() {
        return branchDetails;
    }

    public void setBranchDetails(String branchDetails) {
        this.branchDetails = branchDetails;
    }
}
