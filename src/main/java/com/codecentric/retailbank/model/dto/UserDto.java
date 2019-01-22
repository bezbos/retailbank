package com.codecentric.retailbank.model.dto;

import com.codecentric.retailbank.model.validation.annotation.PasswordMatches;
import com.codecentric.retailbank.model.validation.annotation.ValidEmail;
import com.codecentric.retailbank.model.validation.annotation.ValidPassword;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
public class UserDto {

    //region FIELDS
    @NotNull
    @NotEmpty
    private String firstName;

    @NotNull
    @NotEmpty
    private String lastName;

    @NotNull
    @NotEmpty
    @ValidPassword
    private String password;

    private String matchingPassword;

    @ValidEmail
    @NotNull
    @NotEmpty
    private String email;

    private boolean isUsing2FA;
    //endregion

    //region GETTERS / SETTERS
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isUsing2FA() {
        return isUsing2FA;
    }

    public void setUsing2FA(boolean using2FA) {
        isUsing2FA = using2FA;
    }
    //endregion
}
