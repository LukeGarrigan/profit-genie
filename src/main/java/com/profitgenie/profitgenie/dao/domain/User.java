package com.profitgenie.profitgenie.dao.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Table(name = "PG_user")
@Entity
public class User extends AbstractEntity {

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Boolean support;

    @Column
    private Boolean member;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    public Boolean getMember() {
        return member;
    }

    public void setMember(Boolean member) {
        this.member = member;
    }

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

    public Boolean getSupport() {
        return support;
    }

    public void setSupport(Boolean support) {
        this.support = support;
    }


}
