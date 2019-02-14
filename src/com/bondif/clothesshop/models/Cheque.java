package com.bondif.clothesshop.models;

import java.time.LocalDateTime;

public class Cheque {
    private int id;
    private String owner;
    private String effectiveDate;

    public Cheque(int id, String owner, String effectiveDate) {
        this.id = id;
        this.owner = owner;
        this.effectiveDate = effectiveDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
}
