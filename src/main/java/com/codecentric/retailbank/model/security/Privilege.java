package com.codecentric.retailbank.model.security;

import java.util.Collection;

public class Privilege {

    //region FIELDS
    private Long id;

    private String name;

    private Collection<Role> roles;
    //endregion

    //region CONSTRUCTOR
    public Privilege() {
    }

    public Privilege(String name) {
        this.name = name;
    }

    public Privilege(Long id, String name, Collection<Role> roles) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
    //endregion

    //region OVERRIDES
    @Override public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Privilege other = (Privilege) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }

    @Override public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Privilege [name=").append(name).append("]").append("[id=").append(id).append("]");
        return builder.toString();
    }
    //endregion
}
