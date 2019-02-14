package com.bondif.clothesshop.models;

import com.bondif.clothesshop.core.PaymentMethod;

import java.time.LocalDateTime;

public class Payment {
    private long id;
    private double amount;
    private PaymentMethod method;
    private Cheque cheque;
    private LocalDateTime createdAt;
    private Order order;

    public Payment() {
        this.id = 0;
        this.amount = 0.0;
        this.createdAt = null;
        this.order = null;
        this.method = null;
        this.cheque = null;
    }

    public Payment(long id, double amount, PaymentMethod method, LocalDateTime createdAt, Order order) {
        this.id = id;
        this.amount = amount;
        this.method = method;
        this.createdAt = createdAt;
        this.order = order;
        this.cheque = null;
    }

    public Payment(long id, double amount, PaymentMethod method, Cheque cheque, LocalDateTime createdAt, Order order) {
        this.id = id;
        this.amount = amount;
        this.method = method;
        this.createdAt = createdAt;
        this.order = order;
        this.cheque = cheque;
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

    public PaymentMethod getMethod() {
        return method;
    }

    public void setMethod(PaymentMethod method) {
        this.method = method;
    }


    public Cheque getCheque() {
        return cheque;
    }

    public void setCheque(Cheque cheque) {
        this.cheque = cheque;
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
