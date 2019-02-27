package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.domain.Customer;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CustomerDto {
    //region FIELDS
    private Long id;

    private AddressDto address;

    @NotNull
    private BranchDto branch;

    @NotNull
    @Size(max = 255)
    private String personalDetails;

    @NotNull
    @Size(max = 255)
    private String contactDetails;
    //endregion

    //region CONSTRUCTORS
    public CustomerDto() {
    }

    public CustomerDto(Long id) {
        this.id = id;
    }

    public CustomerDto(AddressDto address,
                       @NotNull BranchDto branch,
                       @NotNull @Size(max = 255) String personalDetails,
                       @NotNull @Size(max = 255) String contactDetails) {
        this.address = address;
        this.branch = branch;
        this.personalDetails = personalDetails;
        this.contactDetails = contactDetails;
    }

    public CustomerDto(Long id, @NotNull @Size(max = 255) String personalDetails) {
        this.id = id;
        this.personalDetails = personalDetails;
    }

    public CustomerDto(Long id,
                       AddressDto address,
                       @NotNull BranchDto branch,
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

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public BranchDto getBranch() {
        return branch;
    }

    public void setBranch(BranchDto branch) {
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
    @JsonIgnore
    public Customer getDBModel(){
        return new Customer(
                this.id,
                this.address != null ? this.address.getDBModel() : null,
                this.branch != null ? this.branch.getDBModel() : null,
                this.personalDetails,
                this.contactDetails
        );
    }
    //endregion
}
