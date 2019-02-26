package com.codecentric.retailbank.model.domain;

import com.codecentric.retailbank.model.dto.RefBranchTypeDto;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RefBranchType {

    //region FIELDS
    private Long id;

    @NotNull
    @Size(max = 15)
    private String code;

    @Size(max = 255)
    private String description;

    @Size(max = 1)
    private String isLargeUrban;  // Y or N

    @Size(max = 1)
    private String isSmallRural;  // Y or N

    @Size(max = 1)
    private String isMediumSuburban;  // Y or N
    //endregion

    //region CONSTRUCTORS
    public RefBranchType() {
    }

    public RefBranchType(Long id) {
        this.id = id;
    }

    public RefBranchType(Long id,
                         @NotNull @Size(max = 15) String code,
                         @Size(max = 255) String description,
                         @Size(max = 1) String isLargeUrban,
                         @Size(max = 1) String isSmallRural,
                         @Size(max = 1) String isMediumSuburban) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isLargeUrban = isLargeUrban;
        this.isSmallRural = isSmallRural;
        this.isMediumSuburban = isMediumSuburban;
    }
    //endregion

    //region GETTERS / SETTERS
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
    //endregion

    //region HELPERS
    @JsonIgnore
    public void setFields(String code,
                          @Size(max = 255) String description,
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

    @JsonIgnore
    public RefBranchTypeDto getDto(){
        return new RefBranchTypeDto(
                this.getId(),
                this.getCode(),
                this.getDescription(),
                this.getIsLargeUrban(),
                this.getIsSmallRural(),
                this.getIsMediumSuburban()
        );
    }
    //endregion

}
