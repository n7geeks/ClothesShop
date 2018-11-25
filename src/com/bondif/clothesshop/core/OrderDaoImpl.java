package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class OrderDaoImpl extends AbstractDao implements Dao<Order> {

    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    @Override
    public Collection<Order> findAll() {
        Collection<Order> orders = new LinkedList<>();
        String sql = "select * from orders";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong("id"));
                order.setCustomer(rs.getLong("customer_id") + "");
                order.setTotal(rs.getDouble("total"));
                order.setCreatedAt(Tools.toLocalDateTime(rs.getTimestamp("created_at")));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public Order findOne(long id) {
        return null;
    }

    @Override
    public void create(Order entity) {

    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void delete(Order entity) {

    }
}
