package com.codecentric.retailbank.model.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ref_account_types")
public class RefAccountTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_type_id")
    private Long id;

    @NotNull
    @Column(name = "account_type_code", nullable = false, unique = true)
    private String code;

    @Column(name = "account_type_description")
    private String description;

    @Column(name = "checking")
    private String isCheckingType;

    @Column(name = "savings")
    private String isSavingsType;

    @Column(name = "certificate_of_deposit")
    private String isCertificateOfDepositType;

    @Column(name = "money_market")
    private String isMoneyMarketType;

    @Column(name = "individual_retirement")
    private String isIndividualRetirementType;


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
}
