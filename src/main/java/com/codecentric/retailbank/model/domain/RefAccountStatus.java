package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Entity
@Table(name = "ref_account_status")
public class RefAccountStatus {

    @Id
    @Column(name = "account_status_id")
    @GeneratedValue
    private Long id;

    @Length(max = 15)
    @Column(name = "account_status_code", nullable = false, unique = true)
    private String code;

    @Length(max = 255)
    @Column(name = "account_status_description")
    private String description;

    @Length(max = 1)
    @Column(name = "active")
    private String isActive;

    @Length(max = 1)
    @Column(name = "closed")
    private String isClosed;


    public RefAccountStatus() {
        super();
    }

    public RefAccountStatus(Long id) {
        super();
        this.id = id;
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

    public void setFields(@Length(max = 15) String code,
                          @Length(max = 255) String description,
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
