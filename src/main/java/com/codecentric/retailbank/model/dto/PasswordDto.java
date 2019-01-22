package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.validation.annotation.ValidPassword;

import javax.validation.constraints.NotNull;

public class PasswordDto {

    //region FIELDS
    @NotNull
    private String oldPassword;

    @NotNull
    @ValidPassword
    private String newPassword;
    //endregion

    //region GETTERS / SETTERS
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    //endregion
}
