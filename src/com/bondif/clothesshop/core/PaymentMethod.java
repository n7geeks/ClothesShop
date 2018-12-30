package com.bondif.clothesshop.core;

public enum PaymentMethod {
    CASH("comptant en espèce"),
    CHECK("comptant par chèque"),
    ONLINE("comptant en ligne"),
    DRAFTS("par traites");

    private String label;

    PaymentMethod(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
