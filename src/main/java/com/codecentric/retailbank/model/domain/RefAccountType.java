package com.codecentric.retailbank.model.domain;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ref_account_types")
public class RefAccountType {

    @Id
    @Column(name = "account_type_id")
    @GeneratedValue
    private Long id;

    @NotNull
    @Length(max = 15)
    @Column(name = "account_type_code", nullable = false, unique = true)
    private String code;

    @Length(max = 255)
    @Column(name = "account_type_description")
    private String description;

    @Length(max = 1)
    @Column(name = "checking")
    private String isCheckingType;

    @Length(max = 1)
    @Column(name = "savings")
    private String isSavingsType;

    @Length(max = 1)
    @Column(name = "certificate_of_deposit")
    private String isCertificateOfDepositType;

    @Length(max = 1)
    @Column(name = "money_market")
    private String isMoneyMarketType;

    @Length(max = 1)
    @Column(name = "individual_retirement")
    private String isIndividualRetirementType;


    public RefAccountType() {
        super();
    }

    public RefAccountType(Long id) {
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
