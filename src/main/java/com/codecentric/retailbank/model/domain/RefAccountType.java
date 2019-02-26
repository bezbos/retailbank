package com.codecentric.retailbank.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RefAccountType {

    //region FIELDS
    private Long id;

    @NotNull
    @Size(max = 15)
    private String code;

    @Size(max = 255)
    private String description;

    @Size(max = 1)
    private String isCheckingType;

    @Size(max = 1)
    private String isSavingsType;

    @Size(max = 1)
    private String isCertificateOfDepositType;

    @Size(max = 1)
    private String isMoneyMarketType;

    @Size(max = 1)
    private String isIndividualRetirementType;
    //endregion

    //region CONSTRUCTORS
    public RefAccountType() {
    }

    public RefAccountType(Long id) {
        this.id = id;
    }

    public RefAccountType(Long id, @NotNull @Size(max = 15) String code) {
        this.id = id;
        this.code = code;
    }

    public RefAccountType(Long id,
                          @NotNull @Size(max = 15) String code,
                          @Size(max = 255) String description,
                          @Size(max = 1) String isCheckingType,
                          @Size(max = 1) String isSavingsType,
                          @Size(max = 1) String isCertificateOfDepositType,
                          @Size(max = 1) String isMoneyMarketType,
                          @Size(max = 1) String isIndividualRetirementType) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isCheckingType = isCheckingType;
        this.isSavingsType = isSavingsType;
        this.isCertificateOfDepositType = isCertificateOfDepositType;
        this.isMoneyMarketType = isMoneyMarketType;
        this.isIndividualRetirementType = isIndividualRetirementType;
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

    public String getIsCheckingType() {
        return isCheckingType;
    }

    public void setIsCheckingType(String isCheckingType) {
        this.isCheckingType = isCheckingType;
    }

    public String getIsSavingsType() {
        return isSavingsType;
    }

    public void setIsSavingsType(String isSavingsType) {
        this.isSavingsType = isSavingsType;
    }

    public String getIsCertificateOfDepositType() {
        return isCertificateOfDepositType;
    }

    public void setIsCertificateOfDepositType(String isCertificateOfDepositType) {
        this.isCertificateOfDepositType = isCertificateOfDepositType;
    }

    public String getIsMoneyMarketType() {
        return isMoneyMarketType;
    }

    public void setIsMoneyMarketType(String isMoneyMarketType) {
        this.isMoneyMarketType = isMoneyMarketType;
    }

    public String getIsIndividualRetirementType() {
        return isIndividualRetirementType;
    }

    public void setIsIndividualRetirementType(String isIndividualRetirementType) {
        this.isIndividualRetirementType = isIndividualRetirementType;
    }
    //endregion

    //region HELPERS
    @JsonIgnore
    public void setFields(@NotNull @Size(max = 15) String code,
                          @Size(max = 255) String description,
                          Boolean isCheckingType,
                          Boolean isSavingsType,
                          Boolean isCertificateOfDepositType,
                          Boolean isMoneyMarketType,
                          Boolean isIndividualRetirementType) {
        this.code = code;
        this.description = description;

        if (isCheckingType != null)
            this.isCheckingType = isCheckingType.booleanValue() == true ? "Y" : "N";

        if (isSavingsType != null)
            this.isSavingsType = isSavingsType.booleanValue() == true ? "Y" : "N";

        if (isCertificateOfDepositType != null)
            this.isCertificateOfDepositType = isCertificateOfDepositType.booleanValue() == true ? "Y" : "N";

        if (isMoneyMarketType != null)
            this.isMoneyMarketType = isMoneyMarketType.booleanValue() == true ? "Y" : "N";

        if (isIndividualRetirementType != null)
            this.isIndividualRetirementType = isIndividualRetirementType.booleanValue() == true ? "Y" : "N";
    }
    //endregion
}
