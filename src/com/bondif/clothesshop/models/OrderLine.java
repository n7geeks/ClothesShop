package com.bondif.clothesshop.models;

public class OrderLine {
    private long id;
    private Product product;
    private double price;
    private int qty;
    private Order order;

    public OrderLine(long id, Product product, double price, int qty) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.qty = qty;
    }

    public OrderLine() {
        this.id = 0;
        this.product = null;
        this.price = 0.0;
        this.qty = 0;
    }

    public OrderLine(OrderLine orderLine) {
        this.id = orderLine.id;
        this.product = orderLine.product;
        this.price = orderLine.price;
        this.qty = orderLine.qty;
        this.order = orderLine.order;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getTotal() {
        return qty * price;
    }
}
