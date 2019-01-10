package com.codecentric.retailbank.model.dto;

import org.hibernate.validator.constraints.Length;

public class RefTransactionTypeDto {

    private Long id;

    @Length(max = 15)
    private String code;

    @Length(max = 255)
    private String description;

    @Length(max = 1)
    private Boolean isDepositType;

    @Length(max = 1)
    private Boolean isWithdrawalType;


    public RefTransactionTypeDto() {
        super();
    }

    public RefTransactionTypeDto(Long id) {
        super();
        this.id = id;
    }

    public RefTransactionTypeDto(Long id,
                                 @Length(max = 15) String code,
                                 @Length(max = 255) String description,
                                 @Length(max = 1) String isDepositType,
                                 @Length(max = 1) String isWithdrawalType) {
        super();
        this.id = id;
        this.code = code;
        this.description = description;
        this.isDepositType = isDepositType.equalsIgnoreCase("Y");
        this.isWithdrawalType = isWithdrawalType.equalsIgnoreCase("Y");
    }

    public RefTransactionTypeDto(Long id,
                                 @Length(max = 15) String code,
                                 @Length(max = 255) String description,
                                 @Length(max = 1) Boolean isDepositType,
                                 @Length(max = 1) Boolean isWithdrawalType) {
        super();
        this.id = id;
        this.code = code;
        this.description = description;
        this.isDepositType = isDepositType;
        this.isWithdrawalType = isWithdrawalType;
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
}
