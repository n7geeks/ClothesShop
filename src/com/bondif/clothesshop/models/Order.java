package com.bondif.clothesshop.models;

import java.time.LocalDateTime;
import java.util.Collection;

public class Order {
    private long id;
    private Customer customer;
    private double total;
    private LocalDateTime createdAt;
    private Collection<OrderLine> orderLines;
    private Collection<Payment> payments;

    public Order(long id, Customer customer, double total, LocalDateTime createdAt) {
        this.id = id;
        this.customer = customer;
        this.total = total;
        this.createdAt = createdAt;
        this.orderLines = null;
        this.payments = null;
    }

    public Order(long id, Customer customer, double total, LocalDateTime createdAt, Collection<OrderLine> orderLines, Collection<Payment> payments) {
        this.id = id;
        this.customer = customer;
        this.total = total;
        this.createdAt = createdAt;
        this.orderLines = orderLines;
        this.payments = payments;
    }

    public Order() {
        this.id = 0;
        this.customer = null;
        this.total = 0;
        this.createdAt = null;
        this.orderLines = null;
        this.payments = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
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

    public Collection<OrderLine> getOrderLines() {
        return orderLines;
    }

    public Collection<Payment> getPayments() {
        return payments;
    }
}
