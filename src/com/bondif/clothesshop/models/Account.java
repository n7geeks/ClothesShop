package com.bondif.clothesshop.models;

public class Account {
    private final int RIB;
    private double amount;
    private Customer customer;

    public Account(int RIB, double amount, Customer customer) {
        this.RIB = RIB;
        this.amount = amount;
        this.customer = customer;
    }

    public int getRIB() {
        return RIB;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
