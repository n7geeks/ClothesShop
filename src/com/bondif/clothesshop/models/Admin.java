package com.bondif.clothesshop.models;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class Admin {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private InputStream picture;

    public Admin(Long id, String firstName, String lastName, String email, String password, InputStream picture) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.picture = picture;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public InputStream getPicture() {
        return picture;
    }

    public void setPicture(InputStream picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + email + "\n";
    }
}
