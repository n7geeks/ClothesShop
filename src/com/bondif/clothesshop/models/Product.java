package com.bondif.clothesshop.models;

public class Product {
    private long code;
    private String label;
    private double buyPrice;
    private double sellPrice;

    public Product(long code, String label, double buyPrice, double sellPrice) {
        this.code = code;
        this.label = label;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public long getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }
}
