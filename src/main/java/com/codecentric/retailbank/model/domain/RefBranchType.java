package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.naming.Name;
import javax.persistence.*;

@Entity
@Table(name = "ref_branch_types")
@Cacheable(false)
public class RefBranchType {

    @Id
    @Column(name = "branch_type_id")
    private Long id;

    @Column(name = "branch_type_code", nullable = false, unique = true)
    private String code;

    @Length(max = 255)
    @Column(name = "branch_type_description")
    private String description;

    @Length(max = 1)
    @Column(name = "large_urban")
    private String isLargeUrban;  // Y or N

    @Length(max = 1)
    @Column(name = "small_rural")
    private String isSmallRural;  // Y or N

    @Length(max = 1)
    @Column(name = "medium_suburban")
    private String isMediumSuburban;  // Y or N


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
        return isLargeUrban;
    }

    public void setLargeUrban(String largeUrban) {
        this.isLargeUrban = largeUrban;
    }

    public String getSmallRural() {
        return isSmallRural;
    }

    public void setSmallRural(String smallRural) {
        this.isSmallRural = smallRural;
    }

    public String getMediumSuburban() {
        return isMediumSuburban;
    }

    public void setMediumSuburban(String mediumSuburban) {
        this.isMediumSuburban = mediumSuburban;
    }

}
