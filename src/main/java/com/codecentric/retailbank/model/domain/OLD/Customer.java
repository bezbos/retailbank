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

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "customer_id")
    @GeneratedValue
    private Long id;

    @OneToOne(targetEntity = AddressOLD.class)
    @JoinColumn(name = "address_id")
    private AddressOLD address;

    @NotNull
    @OneToOne(targetEntity = BranchOLD.class)
    @JoinColumn(name = "branch_id")
    private BranchOLD branch;

    @NotNull
    @Length(max = 255)
    @Column(name = "personal_details")
    private String personalDetails;

    @NotNull
    @Length(max = 255)
    @Column(name = "contact_details")
    private String contactDetails;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressOLD getAddress() {
        return address;
    }

    public void setAddress(AddressOLD address) {
        this.address = address;
    }

    public BranchOLD getBranch() {
        return branch;
    }

    public void setBranch(BranchOLD branch) {
        this.branch = branch;
    }

    public String getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(String personalDetails) {
        this.personalDetails = personalDetails;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }
}
