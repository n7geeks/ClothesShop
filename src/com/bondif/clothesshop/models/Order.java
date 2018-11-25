package com.bondif.clothesshop.models;

import java.time.LocalDateTime;
import java.util.Collection;

public class Order {
    private long id;
    private String customer;
    private double total;
    private LocalDateTime createdAt;
    private Collection<OrderLine> orderLines;

    public Order(long id, String customer, double total, LocalDateTime createdAt) {
        this.id = id;
        this.customer = customer;
        this.total = total;
        this.createdAt = createdAt;
    }

    public Order() {
        this.id = 0;
        this.customer = "";
        this.total = 0;
        this.createdAt = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
