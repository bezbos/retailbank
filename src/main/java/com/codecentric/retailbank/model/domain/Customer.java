package com.codecentric.retailbank.model.domain;

import com.codecentric.retailbank.model.dto.CustomerDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Customer {

    //region FIELDS
    private Long id;

    private Address address;

    @NotNull
    private Branch branch;

    @NotNull
    @Size(max = 255)
    private String personalDetails;

    @NotNull
    @Size(max = 255)
    private String contactDetails;
    //endregion

    //region CONSTRUCTORS
    public Customer() {
    }

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(Address address,
                    @NotNull Branch branch,
                    @NotNull @Size(max = 255) String personalDetails,
                    @NotNull @Size(max = 255) String contactDetails) {
        this.address = address;
        this.branch = branch;
        this.personalDetails = personalDetails;
        this.contactDetails = contactDetails;
    }

    public Customer(Long id, @NotNull @Size(max = 255) String personalDetails) {
        this.id = id;
        this.personalDetails = personalDetails;
    }

    public Customer(Long id,
                    Address address,
                    @NotNull Branch branch,
                    @NotNull @Size(max = 255) String personalDetails,
                    @NotNull @Size(max = 255) String contactDetails) {
        this.id = id;
        this.address = address;
        this.branch = branch;
        this.personalDetails = personalDetails;
        this.contactDetails = contactDetails;
    }
    //endregion

    //region GETTERS / SETTERS
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
    //endregion

    //region HELPERS
    public CustomerDto getDto() {
        return new CustomerDto(
                this.id,
                this.address != null ? this.address.getDto() : null,
                this.branch != null ? this.branch.getDto() : null,
                this.personalDetails,
                this.contactDetails
        );
    }
    //endregion
}
