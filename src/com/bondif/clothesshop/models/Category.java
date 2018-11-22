package com.bondif.clothesshop.models;

public class Category {
    private Long idCategory;
    private String title;

    public Category(String title) {
        this.title = title;
    }

    public Category(Long idCategory, String title) {
        this.idCategory = idCategory;
        this.title = title;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
