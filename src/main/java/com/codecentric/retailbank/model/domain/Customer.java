package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long id;

    @NotNull
    @OneToOne(targetEntity = Address.class)
    @PrimaryKeyJoinColumn
    private Address address;

    @NotNull
    @OneToOne(targetEntity = Branch.class)
    @PrimaryKeyJoinColumn
    private Branch branch;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
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
