package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.domain.RefTransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

public class RefTransactionTypeDto {

    //region FIELDS
    private Long id;

    @Length(max = 15)
    private String code;

    @Length(max = 255)
    private String description;

    private Boolean isDepositType;

    private Boolean isWithdrawalType;
    //endregion

    //region CONSTRUCTOR
    public RefTransactionTypeDto() {
    }

    public RefTransactionTypeDto(Long id,
                                 @Length(max = 15) String code,
                                 @Length(max = 255) String description,
                                 @Length(max = 1) String isDepositType,
                                 @Length(max = 1) String isWithdrawalType) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isDepositType = isDepositType != null && isDepositType.equalsIgnoreCase("Y");
        this.isWithdrawalType = isWithdrawalType != null && isWithdrawalType.equalsIgnoreCase("Y");
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

    public Boolean getIsDepositType() {
        return isDepositType;
    }

    public void setIsDepositType(Boolean deposit) {
        isDepositType = deposit;
    }

    public Boolean getIsWithdrawalType() {
        return isWithdrawalType;
    }

    public void setIsWithdrawalType(Boolean withdrawalType) {
        isWithdrawalType = withdrawalType;
    }
    //endregion

    //region HELPERS
    @JsonIgnore
    public RefTransactionType getDBModel() {
        return new RefTransactionType(
                this.id,
                this.code,
                this.description,
                this.isDepositType != null
                        ? (this.isDepositType.booleanValue() ? "Y" : "N")
                        : "N",
                this.isWithdrawalType != null
                        ? (this.isWithdrawalType.booleanValue() ? "Y" : "N")
                        : "N"
        );
    }
    //endregion
}
