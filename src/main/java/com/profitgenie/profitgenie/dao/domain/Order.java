package com.profitgenie.profitgenie.dao.domain;

import javax.persistence.*;

@Table(name = "PG_order")
@Entity
public class Order extends AbstractEntity {

    @Column(name = "account_id")
    private long accountId;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "payment_email")
    private String paymentEmail;


    @Column(name = "order_name")
    private String orderName;

    @Column
    private long price;

    @Column(name = "order_id")
    private long orderId;

    @ManyToOne
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPaymentEmail() {
        return paymentEmail;
    }

    public void setPaymentEmail(String paymentEmail) {
        this.paymentEmail = paymentEmail;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
