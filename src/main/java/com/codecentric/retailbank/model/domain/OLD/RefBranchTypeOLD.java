package com.codecentric.retailbank.model.domain.OLD;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ref_branch_types")
public class RefBranchTypeOLD {

    @Id
    @Column(name = "branch_type_id")
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(max = 15)
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


    public RefBranchTypeOLD() {
    }

    public RefBranchTypeOLD(Long id) {
        this.id = id;
    }

    public RefBranchTypeOLD(Long id,
                            @NotNull @Length(max = 15) String code,
                            @Length(max = 255) String description,
                            @Length(max = 1) String isLargeUrban,
                            @Length(max = 1) String isSmallRural,
                            @Length(max = 1) String isMediumSuburban) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isLargeUrban = isLargeUrban;
        this.isSmallRural = isSmallRural;
        this.isMediumSuburban = isMediumSuburban;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getIsLargeUrban() {
        return isLargeUrban;
    }

    public void setIsLargeUrban(String largeUrban) {
        this.isLargeUrban = largeUrban;
    }

    public String getIsSmallRural() {
        return isSmallRural;
    }

    public void setIsSmallRural(String smallRural) {
        this.isSmallRural = smallRural;
    }

    public String getIsMediumSuburban() {
        return isMediumSuburban;
    }

    public void setIsMediumSuburban(String mediumSuburban) {
        this.isMediumSuburban = mediumSuburban;
    }


    public void setFields(String code,
                          @Length(max = 255) String description,
                          Boolean isLargeUrban,
                          Boolean isSmallRural,
                          Boolean isMediumSuburban) {
        this.code = code;
        this.description = description;

        if (isLargeUrban != null)
            this.isLargeUrban = isLargeUrban.booleanValue() == true ? "Y" : "N";

        if (isSmallRural != null)
            this.isSmallRural = isSmallRural.booleanValue() == true ? "Y" : "N";

        if (isMediumSuburban != null)
            this.isMediumSuburban = isMediumSuburban.booleanValue() == true ? "Y" : "N";
    }

}
