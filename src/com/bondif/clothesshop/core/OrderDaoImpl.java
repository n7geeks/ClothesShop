package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Customer;
import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.models.OrderLine;

import java.sql.*;
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
                order.setCustomer(new Customer(rs.getLong("customer_id")));
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
    public void create(Order order) {
        OrderLineDaoImpl orderLineDao = new OrderLineDaoImpl();
        PreparedStatement pstsmt;

        String query = "INSERT INTO orders VALUES (NULL, ?, ?, ?)";
        try {
            pstsmt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstsmt.setLong(1, order.getCustomer().getId());
            pstsmt.setDouble(2, order.getTotal());
            pstsmt.setDate(3, Tools.toDate(order.getCreatedAt()));
            pstsmt.execute();

            ResultSet generatedKeys = pstsmt.getGeneratedKeys();
            if(generatedKeys.next()) order.setId(generatedKeys.getLong(1));
            else throw new SQLException("Creating order failed");

            for (OrderLine orderLine: order.getOrderLines()) {
                orderLine.setOrder(order);
                orderLineDao.create(orderLine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void delete(Order entity) {

    }
}
