package com.bondif.clothesshop.models;

public class Category {
    private Long id;
    private String title;

    public Category(String title) {
        this.title = title;
    }

    public Category(Long idCategory, String title) {
        this.id = idCategory;
        this.title = title;
    }

    public Category() {
        this.id = 0L;
        this.title = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long idCategory) {
        this.id = idCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
