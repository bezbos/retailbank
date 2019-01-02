package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "ref_branch_types")
public class RefBranchType {

    @Id
    @Column(name = "branch_type_code")
    private String code;

    @Length(max = 255)
    private String description;

    @Length(max = 1)
    private String largeUrban;  // Y or N

    @Length(max = 1)
    private String smallRural;  // Y or N

    @Length(max = 1)
    private String mediumSuburban;  // Y or N


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLargeUrban() {
        return largeUrban;
    }

    public void setLargeUrban(String largeUrban) {
        this.largeUrban = largeUrban;
    }

    public String getSmallRural() {
        return smallRural;
    }

    public void setSmallRural(String smallRural) {
        this.smallRural = smallRural;
    }

    public String getMediumSuburban() {
        return mediumSuburban;
    }

    public void setMediumSuburban(String mediumSuburban) {
        this.mediumSuburban = mediumSuburban;
    }
}
