package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.domain.RefAccountStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RefAccountStatusDto {

    //region FIELDS
    private Long id;

    @NotNull
    @Length(max = 15)
    private String code;

    @Length(max = 255)
    private String description;

    private Boolean isActiveStatus;

    private Boolean isClosedStatus;
    //endregion

    //region CONSTRUCTOR
    public RefAccountStatusDto() {
    }

    public RefAccountStatusDto(Long id,
                               @NotNull @Length(max = 15) String code,
                               @Length(max = 255) String description,
                               @Length(max = 1) String isActiveStatus,
                               @Length(max = 1) String isClosedStatus) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isActiveStatus = isActiveStatus != null ? isActiveStatus.equalsIgnoreCase("Y") : false;
        this.isClosedStatus = isClosedStatus != null ? isClosedStatus.equalsIgnoreCase("Y") : false;
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

    public Boolean getIsActiveStatus() {
        return isActiveStatus;
    }

    public void setIsActiveStatus(Boolean activeStatus) {
        isActiveStatus = activeStatus;
    }

    public Boolean getIsClosedStatus() {
        return isClosedStatus;
    }

    public void setIsClosedStatus(Boolean closedStatus) {
        isClosedStatus = closedStatus;
    }
    //endregion

    //region HELPERS
    @JsonIgnore
    public RefAccountStatus getDBModel(){
        return new RefAccountStatus(
                this.id,
                this.code,
                this.description,
                this.isActiveStatus != null
                        ? (this.isActiveStatus.booleanValue() ? "Y" : "N")
                        : "N",
                this.isClosedStatus != null
                        ? (this.isClosedStatus.booleanValue() ? "Y" : "N")
                        : "N"
        );
    }
    //endregion
}
