package com.codecentric.retailbank.model.domain;


import javax.validation.constraints.Size;

public class RefTransactionType {

    //region FIELDS
    private Long id;

    @Size(max = 15)
    private String code;

    @Size(max = 255)
    private String description;

    @Size(max = 1)
    private String isDeposit;

    @Size(max = 1)
    private String isWithdrawal;
    //endregion

    //region CONSTRUCTOR
    public RefTransactionType() {
    }

    public RefTransactionType(Long id) {
        this.id = id;
    }

    public RefTransactionType(Long id, @Size(max = 15) String code) {
        this.id = id;
        this.code = code;
    }

    public RefTransactionType(Long id,
                              @Size(max = 15) String code,
                              @Size(max = 255) String description,
                              @Size(max = 1) String isDeposit,
                              @Size(max = 1) String isWithdrawal) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.isDeposit = isDeposit;
        this.isWithdrawal = isWithdrawal;
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

    public String getIsDeposit() {
        return isDeposit;
    }

    public void setIsDeposit(String isDeposit) {
        this.isDeposit = isDeposit;
    }

    public String getIsWithdrawal() {
        return isWithdrawal;
    }

    public void setIsWithdrawal(String isWithdrawal) {
        this.isWithdrawal = isWithdrawal;
    }
    //endregion

    //region HELPERS
    public void setFields(@Size(max = 15) String code,
                          @Size(max = 255) String description,
                          Boolean isDeposit,
                          Boolean isWithdrawal) {
        this.code = code;
        this.description = description;

        if (isDeposit != null)
            this.isDeposit = isDeposit.booleanValue() == true ? "Y" : "N";

        if (isWithdrawal != null)
            this.isWithdrawal = isWithdrawal.booleanValue() == true ? "Y" : "N";
    }
    //endregion
}
