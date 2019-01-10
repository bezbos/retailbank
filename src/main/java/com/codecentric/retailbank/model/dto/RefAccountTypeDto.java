package com.codecentric.retailbank.model.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class RefAccountTypeDto {

    private Long id;

    @NotNull
    @Length(max = 15)
    private String code;

    @Length(max = 255)
    private String description;

    private Boolean isCheckingType;

    private Boolean isSavingsType;

    private Boolean isCertificateOfDepositType;

    private Boolean isMoneyMarketType;

    private Boolean isIndividualRetirementType;


    public RefAccountTypeDto() {
        super();
    }

    public RefAccountTypeDto(Long id) {
        super();
        this.id = id;
    }

    public RefAccountTypeDto(Long id,
                             @NotNull @Length(max = 15) String code,
                             @Length(max = 255) String description,
                             Boolean isCheckingType,
                             Boolean isSavingsType,
                             Boolean isCertificateOfDepositType,
                             Boolean isMoneyMarketType,
                             Boolean isIndividualRetirementType) {
        super();
        this.id = id;
        this.code = code;
        this.description = description;
        this.isCheckingType = isCheckingType;
        this.isSavingsType = isSavingsType;
        this.isCertificateOfDepositType = isCertificateOfDepositType;
        this.isMoneyMarketType = isMoneyMarketType;
        this.isIndividualRetirementType = isIndividualRetirementType;
    }

    public RefAccountTypeDto(Long id,
                             @NotNull @Length(max = 15) String code,
                             @Length(max = 255) String description,
                             String isCheckingType,
                             String isSavingsType,
                             String isCertificateOfDepositType,
                             String isMoneyMarketType,
                             String isIndividualRetirementType) {
        super();
        this.id = id;
        this.code = code;
        this.description = description;
        this.isCheckingType = isCheckingType.equalsIgnoreCase("Y");
        this.isSavingsType = isSavingsType.equalsIgnoreCase("Y");
        this.isCertificateOfDepositType = isCertificateOfDepositType.equalsIgnoreCase("Y");
        this.isMoneyMarketType = isMoneyMarketType.equalsIgnoreCase("Y");
        this.isIndividualRetirementType = isIndividualRetirementType.equalsIgnoreCase("Y");
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

    public Boolean getIsCheckingType() {
        return isCheckingType;
    }

    public void setIsCheckingType(Boolean checkingType) {
        isCheckingType = checkingType;
    }

    public Boolean getIsSavingsType() {
        return isSavingsType;
    }

    public void setIsSavingsType(Boolean savingsType) {
        isSavingsType = savingsType;
    }

    public Boolean getIsCertificateOfDepositType() {
        return isCertificateOfDepositType;
    }

    public void setIsCertificateOfDepositType(Boolean certificateOfDepositType) {
        isCertificateOfDepositType = certificateOfDepositType;
    }

    public Boolean getIsMoneyMarketType() {
        return isMoneyMarketType;
    }

    public void setIsMoneyMarketType(Boolean moneyMarketType) {
        isMoneyMarketType = moneyMarketType;
    }

    public Boolean getIsIndividualRetirementType() {
        return isIndividualRetirementType;
    }

    public void setIsIndividualRetirementType(Boolean individualRetirementType) {
        isIndividualRetirementType = individualRetirementType;
    }
}
