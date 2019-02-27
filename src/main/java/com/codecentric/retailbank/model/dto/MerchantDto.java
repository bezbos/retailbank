package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.domain.Merchant;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Size;

public class MerchantDto {
    //region FIELDS
    private Long id;

    @Size(max = 255)
    private String details;
    //endregion

    //region CONSTRUCTORS
    public MerchantDto(Long id) {
        this.id = id;
    }

    public MerchantDto(Long id, @Size(max = 255) String details) {
        this.id = id;
        this.details = details;
    }
    //endregion

    //region GETTERS / SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
    //endregion

    //region HELPERS
    @JsonIgnore
    public Merchant getDBModel() {
        return new Merchant(
                this.id,
                this.details
        );
    }
    //endregion
}
