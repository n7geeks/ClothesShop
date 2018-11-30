package com.bondif.clothesshop.models;

public class Product {
    private Long code;
    private String label;
    private int qty;
    private double buyingPrice;
    private double sellingPrice;
    private String image;
    private Category category;

    public Product(Long code, String label, int qty, double buyingPrice, double sellingPrice, String image, Category category) {
        this.code = code;
        this.label = label;
        this.qty = qty;
        this.buyingPrice = buyingPrice;
        this.sellingPrice = sellingPrice;
        this.image = image;
        this.category = category;
    }

    public Product() {
        this.code = 0L;
        this.label = "";
        this.qty = 0;
        this.buyingPrice = 0.0;
        this.sellingPrice = 0.0;
        this.image = "";
        this.category = null;
    }

    public Long getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getBuyingPrice() {
        return buyingPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public String getImage() {
        return image;
    }

    public Category getCategory() {
        return category;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setBuyingPrice(double buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return label;
    }
}
