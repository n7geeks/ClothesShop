package com.bondif.clothesshop.core;

public enum PaymentMethod {
    CASH("comptant en espèce", "0"),
    CHECK("comptant par chèque", "1"),
    ONLINE("comptant en ligne", "2"),
    DRAFTS("par traites", "3");

    private String label;
    private String dbName;

    PaymentMethod(String label, String dbName) {
        this.label = label;
        this.dbName = dbName;
    }

    public String dbName() {
        return dbName;
    }

    @Override
    public String toString() {
        return label;
    }
}
