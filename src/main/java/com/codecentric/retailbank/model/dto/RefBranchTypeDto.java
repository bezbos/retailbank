package com.codecentric.retailbank.model.dto;

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
        super();
    }

    public RefBranchTypeDto(Long id,
                            @NotNull @Length(max = 15) String code,
                            @Length(max = 255) String description,
                            @Length(max = 1) String isLargeUrban,
                            @Length(max = 1) String isSmallRural,
                            @Length(max = 1) String isMediumSuburban) {
        super();
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
        return isLargeUrbanType;
    }

    public void setIsLargeUrbanType(Boolean largeUrban) {
        isLargeUrbanType = largeUrban;
    }

    public Boolean getIsSmallRuralType() {
        return isSmallRuralType;
    }

    public void setIsSmallRuralType(Boolean smallRural) {
        isSmallRuralType = smallRural;
    }

    public Boolean getIsMediumSuburbanType() {
        return isMediumSuburbanType;
    }

    public void setIsMediumSuburbanType(Boolean mediumSuburban) {
        isMediumSuburbanType = mediumSuburban;
    }
}
