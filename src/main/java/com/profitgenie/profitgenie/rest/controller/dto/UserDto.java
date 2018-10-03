package com.profitgenie.profitgenie.rest.controller.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

    private long id;
    private String password;
    private String email;
    private Boolean support;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSupport() {
        return support;
    }

    public void setSupport(Boolean support) {
        this.support = support;
    }
}
