package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @Column(name = "address_id")
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(max = 255)
    @Column(name = "line_1")
    private String line1;

    @Length(max = 255)
    @Column(name = "line_2")
    private String line2;

    @Length(max = 255)
    @Column(name = "town_city")
    private String townCity;

    @Length(max = 50)
    @Column(name = "zip_postcode")
    private String zipPostcode;

    @Length(max = 50)
    @Column(name = "state_province_country")
    private String stateProvinceCountry;

    @Column(name = "country")
    @Length(max = 50)
    private String country;

    @Length(max = 255)
    @Column(name = "other_details")
    private String otherDetails;


    public Address() {
        super();
    }

    public Address(Long id) {
        this.id = id;
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


    public void setFields(@NotNull @Length(max = 255) String line1,
                          @Length(max = 255) String line2,
                          @Length(max = 255) String townCity,
                          @Length(max = 50) String zipPostcode,
                          @Length(max = 50) String stateProvinceCountry,
                          @Length(max = 50) String country,
                          @Length(max = 255) String otherDetails) {
        this.line1 = line1;
        this.line2 = line2;
        this.townCity = townCity;
        this.zipPostcode = zipPostcode;
        this.stateProvinceCountry = stateProvinceCountry;
        this.country = country;
        this.otherDetails = otherDetails;
    }
}
