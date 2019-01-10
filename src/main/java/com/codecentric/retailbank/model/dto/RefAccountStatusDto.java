package com.codecentric.retailbank.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RefAccountStatusDto {

    private Long id;

    @NotNull
    @Length(max = 15)
    private String code;

    @Length(max = 255)
    private String description;

    @Length(max = 1)
    private Boolean isActiveStatus;

    @Length(max = 1)
    private Boolean isClosedStatus;


    public RefAccountStatusDto(Long id) {
        super();
        this.id = id;
    }

    public RefAccountStatusDto(Long id,
                               @NotNull @Length(max = 15) String code,
                               @Length(max = 255) String description,
                               @Length(max = 1) Boolean isActiveStatus,
                               @Length(max = 1) Boolean isClosedStatus) {
        super();
        this.id = id;
        this.code = code;
        this.description = description;
        this.isActiveStatus = isActiveStatus;
        this.isClosedStatus = isClosedStatus;
    }

    public RefAccountStatusDto(Long id,
                               @NotNull @Length(max = 15) String code,
                               @Length(max = 255) String description,
                               @Length(max = 1) String isActiveStatus,
                               @Length(max = 1) String isClosedStatus) {
        super();
        this.id = id;
        this.code = code;
        this.description = description;
        this.isActiveStatus = isActiveStatus.equalsIgnoreCase("Y");
        this.isClosedStatus = isClosedStatus.equalsIgnoreCase("Y");
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
}
