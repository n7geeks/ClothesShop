package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Customer;
import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.models.OrderLine;
import com.bondif.clothesshop.models.Payment;

import java.sql.*;
import java.time.LocalDateTime;
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
        String sql = "select * from orders o, customers c where o.customer_id = c.id";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getInt("c.id");
                String code = rs.getString("c.code");
                String firstName = rs.getString("c.firstName");
                String lastName = rs.getString("c.lastName");
                String phone = rs.getString("c.phone");
                String address = rs.getString("c.address");
                String email = rs.getString("c.email");

                Customer customer = new Customer(id, code, firstName, lastName, phone, address, email);

                Order order = new Order();
                order.setId(rs.getLong("o.id"));
                order.setCustomer(customer);
                order.setTotal(rs.getDouble("o.total"));
                order.setCreatedAt(Tools.toLocalDateTime(rs.getTimestamp("o.created_at")));

                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public Order findOne(long id) {
        Order order = null;
        String sql = "select * from orders o, customers c where o.customer_id = c.id and o.id = ?";

        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            rs.next();

            long idCustomer = rs.getInt("c.id");
            String code = rs.getString("c.code");
            String firstName = rs.getString("c.firstName");
            String lastName = rs.getString("c.lastName");
            String phone = rs.getString("c.phone");
            String address = rs.getString("c.address");
            String email = rs.getString("c.email");
            Customer customer = new Customer(idCustomer, code, firstName, lastName, phone, address, email);

            long idOrder = rs.getLong("o.id");
            double total = rs.getDouble("o.total");
            LocalDateTime dateTime = Tools.toLocalDateTime(rs.getTimestamp("created_at"));
            System.out.println(dateTime);
            order = new Order(idOrder, customer, total, dateTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    @Override
    public void create(Order order) {
        OrderLineDaoImpl orderLineDao = new OrderLineDaoImpl();
        PaymentDaoImpl paymentDao = new PaymentDaoImpl();
        ChequeDaoImpl chequeDao = new ChequeDaoImpl();
        PreparedStatement pstsmt;

        String query = "INSERT INTO orders VALUES (NULL, ?, ?, now())";
        try {
            pstsmt = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstsmt.setLong(1, order.getCustomer().getId());
            pstsmt.setDouble(2, order.getTotal());
            pstsmt.execute();

            ResultSet generatedKeys = pstsmt.getGeneratedKeys();
            if(generatedKeys.next()) order.setId(generatedKeys.getLong(1));
            else throw new SQLException("Creating order failed");

            for (OrderLine orderLine: order.getOrderLines()) {
                orderLine.setOrder(order);
                orderLineDao.create(orderLine);
            }

            for (Payment payment : order.getPayments()) {
                payment.setOrder(order);
                if(payment.getCheque() != null) {
                    payment.getCheque().setId(chequeDao.create(payment.getCheque()));
                }
                paymentDao.create(payment);
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
