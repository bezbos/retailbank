package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.domain.RefBranchType;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RefBranchTypeDto {

    private Long id;

    @NotNull
    @Length(max = 15)
    private String code;

    @Length(max = 255)
    private String description;

    private Boolean isLargeUrbanType;

    private Boolean isSmallRuralType;

    private Boolean isMediumSuburbanType;


    public RefBranchTypeDto() {
    }

    public RefBranchTypeDto(Long id) {
        this.id = id;
    }

    public RefBranchTypeDto(Long id,
                            @NotNull @Length(max = 15) String code,
                            @Length(max = 255) String description,
                            @Length(max = 1) String isLargeUrban,
                            @Length(max = 1) String isSmallRural,
                            @Length(max = 1) String isMediumSuburban) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isLargeUrbanType = isLargeUrban != null ? isLargeUrban.equalsIgnoreCase("Y") : false;
        this.isSmallRuralType = isSmallRural != null ? isSmallRural.equalsIgnoreCase("Y") : false;
        this.isMediumSuburbanType = isMediumSuburban != null ? isMediumSuburban.equalsIgnoreCase("Y") : false;
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

    public Boolean getIsLargeUrbanType() {
        if(isLargeUrbanType == null)
            isLargeUrbanType = false;

        return isLargeUrbanType;
    }

    public void setIsLargeUrbanType(Boolean largeUrban) {
        isLargeUrbanType = largeUrban;
    }

    public Boolean getIsSmallRuralType() {
        if(isSmallRuralType == null)
            isSmallRuralType = false;

        return isSmallRuralType;
    }

    public void setIsSmallRuralType(Boolean smallRural) {
        isSmallRuralType = smallRural;
    }

    public Boolean getIsMediumSuburbanType() {
        if(isMediumSuburbanType == null)
            isMediumSuburbanType = false;

        return isMediumSuburbanType;
    }

    public void setIsMediumSuburbanType(Boolean mediumSuburban) {
        isMediumSuburbanType = mediumSuburban;
    }

    public RefBranchType getDBModel(){
        return new RefBranchType(
                this.id,
                this.code,
                this.description,
                this.getIsLargeUrbanType() ? "Y" : "N",
                this.getIsSmallRuralType() ? "Y" : "N",
                this.getIsMediumSuburbanType() ? "Y" : "N"
        );
    }
}
