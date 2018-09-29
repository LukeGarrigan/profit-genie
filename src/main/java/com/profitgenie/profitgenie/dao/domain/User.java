package com.profitgenie.profitgenie.dao.domain;

import javax.persistence.*;


@Table(name = "PG_user")
@Entity
public class User extends AbstractEntity {

    @Column
    private String email;

    @Column
    private String password;

    public User(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
