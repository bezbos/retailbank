package com.codecentric.retailbank.model.domain;

import com.codecentric.retailbank.model.dto.AddressDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Address {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String line1;

    @Size(max = 255)
    private String line2;

    @Size(max = 255)
    private String townCity;

    @Size(max = 50)
    private String zipPostcode;

    @Size(max = 50)
    private String stateProvinceCountry;

    @Size(max = 50)
    private String country;

    @Size(max = 255)
    private String otherDetails;


    public Address() {

    }

    public Address(Long id) {
        this.id = id;
    }

    public Address(Long id, @NotNull @Size(max = 255) String line1) {
        this.id = id;
        this.line1 = line1;
    }

    public Address(Long id,
                   @NotNull String line1,
                   String line2,
                   String townCity,
                   String zipPostcode,
                   String stateProvinceCountry,
                   String country,
                   String otherDetails) {
        this.id = id;
        this.line1 = line1;
        this.line2 = line2;
        this.townCity = townCity;
        this.zipPostcode = zipPostcode;
        this.stateProvinceCountry = stateProvinceCountry;
        this.country = country;
        this.otherDetails = otherDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getTownCity() {
        return townCity;
    }

    public void setTownCity(String townCity) {
        this.townCity = townCity;
    }

    public String getZipPostcode() {
        return zipPostcode;
    }

    public void setZipPostcode(String zipPostcode) {
        this.zipPostcode = zipPostcode;
    }

    public String getStateProvinceCountry() {
        return stateProvinceCountry;
    }

    public void setStateProvinceCountry(String stateProvinceCountry) {
        this.stateProvinceCountry = stateProvinceCountry;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }


    public void setFields(@NotNull String line1,
                          String line2,
                          String townCity,
                          String zipPostcode,
                          String stateProvinceCountry,
                          String country,
                          String otherDetails) {
        this.line1 = line1;
        this.line2 = line2;
        this.townCity = townCity;
        this.zipPostcode = zipPostcode;
        this.stateProvinceCountry = stateProvinceCountry;
        this.country = country;
        this.otherDetails = otherDetails;
    }

    public AddressDto getDto(){
        return new AddressDto(
                this.id,
                this.getLine1(),
                this.getLine2(),
                this.getTownCity(),
                this.getZipPostcode(),
                this.getStateProvinceCountry(),
                this.getCountry(),
                this.getOtherDetails()
        );
    }
}
