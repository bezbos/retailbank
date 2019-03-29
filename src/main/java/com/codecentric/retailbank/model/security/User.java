package com.codecentric.retailbank.model.security;

import org.jboss.aerogear.security.otp.api.Base32;

import java.util.Collection;

public class User {

    //region FIELDS
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private boolean enabled;

    private boolean isUsing2FA;

    private String secret;

    private AuthProvider authProvider;

    private String authProviderId;

    private Collection<Role> roles;
    //endregion

    //region CONSTRUCTORS
    public User() {
        this.secret = Base32.random();
        this.enabled = false;
    }

    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(Long id,
                String firstName,
                String lastName,
                String email,
                String password,
                boolean enabled,
                boolean isUsing2FA,
                String secret,
                Collection<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.isUsing2FA = isUsing2FA;
        this.secret = secret;
        this.roles = roles;
    }

    public User(Long id,
                String firstName,
                String lastName,
                String email,
                String password,
                boolean enabled,
                boolean isUsing2FA,
                String secret,
                String authProvider,
                String authProviderId,
                Collection<Role> roles){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.isUsing2FA = isUsing2FA;
        this.secret = secret;
        this.authProvider = authProvider == null ? AuthProvider.local : AuthProvider.valueOf(authProvider);
        this.authProviderId = authProviderId;
        this.roles = roles;
    }
    //endregion

    //region GETTERS / SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isUsing2FA() {
        return isUsing2FA;
    }

    public void setUsing2FA(boolean isUsing2FA) {
        this.isUsing2FA = isUsing2FA;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public AuthProvider getProvider() {
        return authProvider;
    }

    public void setProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }

    public String getProviderId() {
        return authProviderId;
    }

    public void setProviderId(String authProviderId) {
        this.authProviderId = authProviderId;
    }

    //endregion

    //region OVERRIDES
    @Override public int hashCode() {
        int prime = 31;
        int result = 1;
        result = (prime * result) + ((email == null) ? 0 : email.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User user = (User) obj;
        if (!email.equals(user.email)) {
            return false;
        }
        return true;
    }

    @Override public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [id=").append(id).append(", firstName=").append(firstName).append(", lastName=").append(lastName).append(", email=").append(email).append(", password=").append(password).append(", enabled=").append(enabled).append(", isUsing2FA=")
                .append(isUsing2FA).append(", secret=").append(secret).append(", roles=").append(roles).append("]");
        return builder.toString();
    }
    //endregion

}