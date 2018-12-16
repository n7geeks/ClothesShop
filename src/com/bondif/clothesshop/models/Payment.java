package com.bondif.clothesshop.models;

import java.time.LocalDateTime;

public class Payment {
    private long id;
    private double amount;
    private LocalDateTime createdAt;
    private Order order;

    public Payment() {
        this.id = 0;
        this.amount = 0.0;
        this.createdAt = null;
        this.order = null;
    }

    public Payment(long id, double amount, LocalDateTime createdAt, Order order) {
        this.id = id;
        this.amount = amount;
        this.createdAt = createdAt;
        this.order = order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
