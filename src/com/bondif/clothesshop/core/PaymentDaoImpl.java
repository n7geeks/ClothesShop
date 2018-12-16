package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class PaymentDaoImpl extends AbstractDao implements Dao<Payment> {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    @Override
    public Collection<Payment> findAll() {
        Collection<Payment> payments = new LinkedList<>();
        return payments;
    }

    @Override
    public Payment findOne(long id) {
        return null;
    }

    @Override
    public void create(Payment payment) {
        PreparedStatement pstsmt;

        String sql = "INSERT INTO payments VALUES (NULL, ?, ?, now())";
        try {
            pstsmt = getConnection().prepareStatement(sql);
            pstsmt.setLong(1, payment.getOrder().getId());
            pstsmt.setDouble(2, payment.getAmount());
            pstsmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Payment entity) {

    }

    @Override
    public void delete(Payment entity) {

    }
}
