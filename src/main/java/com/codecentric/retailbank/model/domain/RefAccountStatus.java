package com.codecentric.retailbank.model.domain;

import javax.validation.constraints.Size;

public class RefAccountStatus {

    private Long id;

    @Size(max = 15)
    private String code;

    @Size(max = 255)
    private String description;

    @Size(max = 1)
    private String isActive;

    @Size(max = 1)
    private String isClosed;


    public RefAccountStatus() {
    }

    public RefAccountStatus(Long id) {
        this.id = id;
    }

    public RefAccountStatus(Long id, @Size(max = 15) String code) {
        this.id = id;
        this.code = code;
    }

    public RefAccountStatus(Long id,
                            @Size(max = 15) String code,
                            @Size(max = 255) String description,
                            @Size(max = 1) String isActive,
                            @Size(max = 1) String isClosed) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isActive = isActive;
        this.isClosed = isClosed;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(String isClosed) {
        this.isClosed = isClosed;
    }

    public void setFields(@Size(max = 15) String code,
                          @Size(max = 255) String description,
                          Boolean isActive,
                          Boolean isClosed) {
        this.code = code;
        this.description = description;

        if(isActive != null)
            this.isActive = isActive.booleanValue() == true ? "Y" : "N";

        if(isClosed != null)
            this.isClosed = isClosed.booleanValue() == true ? "Y" : "N";;
    }
}
