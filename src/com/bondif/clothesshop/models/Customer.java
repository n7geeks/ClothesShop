package com.bondif.clothesshop.models;

import java.io.Serializable;

public class Customer implements Serializable {
    private Long id;
    private String code;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String email;

    public Customer (Long id, String code, String firstName, String lastName, String phone, String address, String email){
        this.id = id;
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    public Customer() {
    }

    public Long getId () { return id; }

    public String getCode() { return code; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public String getPhone() { return phone; }

    public String getAddress() { return address; }

    public String getEmail() { return email; }

    public void setId(Long id) { this.id = id; }

    public void setCode(String code) { this.code = code; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public void setPhone(String phone) { this.phone = phone; }

    public void setAddress(String address) { this.address = address; }

    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
