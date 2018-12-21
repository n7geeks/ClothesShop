package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.models.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

public class PaymentDaoImpl extends AbstractDao implements Dao<Payment> {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    @Override
    public Collection<Payment> findAll() {
        return new LinkedList<>();
    }

    public Collection<Payment> findAll(Order order) {
        Collection<Payment> payments = new LinkedList<>();
        String sql = "select id, amount, created_at from payments where order_id = ?";

        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setLong(1, order.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Payment payment = new Payment();
                payment.setId(rs.getLong("id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setCreatedAt(Tools.toLocalDateTime(rs.getTimestamp("created_at")));
                payments.add(payment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
