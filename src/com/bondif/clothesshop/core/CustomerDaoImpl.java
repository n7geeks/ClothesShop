package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Customer;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

public class CustomerDaoImpl extends AbstractDao implements Dao<Customer> {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    private Collection<Customer> getCustomersList(String sqlQuery){
        Collection<Customer> customers = new LinkedList<>();

        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sqlQuery);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getInt("id");
                String code = rs.getString("code");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                String email = rs.getString("email");

                Customer customer = new Customer(id, code, firstName, lastName, phone, address, email);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public Collection<Customer> findAll() {
        Collection<Customer> customers;
        String sql = "SELECT * FROM CUSTOMERS";

        customers = getCustomersList(sql);
        return customers;
    }

    @Override
    public Customer findOne(long id) {
        String query = "SELECT * FROM WHERE id = ?";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(query);
            rs = pstmt.executeQuery();

            pstmt.setLong(1, id);

            String code = rs.getString("code");
            String firstName = rs.getString("firstName");
            String lastName = rs.getString("lastName");
            String phone = rs.getString("phone");
            String address = rs.getString("address");
            String email = rs.getString("email");

            return (new Customer(id, code, firstName, lastName, phone, address, email));
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(Customer customer) {
        PreparedStatement pstmt;
        String query = "INSERT INTO CUSTOMERS VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        int validate;

        try {
            pstmt = getConnection().prepareStatement(query);

            pstmt.setString(1, customer.getCode());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getEmail());

            validate = pstmt.executeUpdate();
            if (validate != 0){
                Alert msgBox = new Alert(Alert.AlertType.INFORMATION);
                msgBox.setTitle("Adding a customer");
                msgBox.setHeaderText(null);
                msgBox.setContentText("The customer " + customer.getFirstName() + " " + customer.getLastName() + " added successfully");
                msgBox.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Customer customer) {
        String query = "UPDATE CUSTOMERS SET code = ?, firstName = ?, lastName = ?, phone = ?, address = ?, email = ? WHERE id = ?";
        PreparedStatement pstmt;
        int validate;

        try {

            pstmt = getConnection().prepareStatement(query);

            pstmt.setString(1, customer.getCode());
            pstmt.setString(2, customer.getFirstName());
            pstmt.setString(3, customer.getLastName());
            pstmt.setString(4, customer.getPhone());
            pstmt.setString(5, customer.getAddress());
            pstmt.setString(6, customer.getEmail());
            pstmt.setLong(7, customer.getId());

            validate = pstmt.executeUpdate();

            if (validate != 0){
                Alert msgBox = new Alert(Alert.AlertType.INFORMATION);
                msgBox.setTitle("Editing a customer");
                msgBox.setHeaderText(null);
                msgBox.setContentText("The customer " + customer.getFirstName() + " " + customer.getLastName() + " edited successfully");
                msgBox.showAndWait();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Customer customer) {
        String query = "DELETE FROM CUSTOMERS WHERE id = ?";
        PreparedStatement pstmt;

        try{
            pstmt = getConnection().prepareStatement(query);

            pstmt.setLong(1, customer.getId());

            pstmt.executeUpdate();

            //alert
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Collection<Customer> findAll(String searchString) {
        Collection<Customer> customers;
        String sql = "SELECT * FROM CUSTOMERS WHERE id LIKE '%" + searchString + "%' OR code LIKE '%" + searchString + "%' OR firstName LIKE '%" + searchString + "%' OR lastName LIKE '%" + searchString +
                "%' OR phone LIKE '%" + searchString + "%' OR address LIKE '%" + searchString + "%' OR email LIKE '%" + searchString + "%'";
        customers = getCustomersList(sql);

        return customers;
    }
}