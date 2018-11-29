package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Category;
import com.bondif.clothesshop.models.Order;
import com.bondif.clothesshop.models.OrderLine;
import com.bondif.clothesshop.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class OrderLineDaoImpl extends AbstractDao implements Dao<OrderLine> {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    public Collection<OrderLine> findAll(Order order) {
        Collection<OrderLine> orderLines = new LinkedList<>();
        String sql = "select * from order_lines ol, products p, categories c " +
                "where ol.product_id = p.code " +
                "and p.category_id = c.id " +
                "and order_id = ?";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setLong(1, order.getId());
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Category c = new Category();
                c.setId(rs.getLong("c.id"));
                c.setTitle(rs.getString("c.title"));

                Product p = new Product();
                p.setCode(rs.getLong("p.code"));
                p.setLabel(rs.getString("p.label"));
                p.setBuyingPrice(rs.getDouble("p.buyingPrice"));
                p.setSellingPrice(rs.getDouble("p.sellingPrice"));
                p.setImage(rs.getString("p.image"));
                p.setCategory(c);

                OrderLine orderLine = new OrderLine();
                orderLine.setId(rs.getLong("id"));
                orderLine.setProduct(p);
                orderLine.setPrice(rs.getDouble("price"));
                orderLine.setQty(rs.getInt("qty"));
                orderLine.setOrder(order);

                orderLines.add(orderLine);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderLines;
    }


    @Override
    public Collection<OrderLine> findAll() {
        return null;
    }

    @Override
    public OrderLine findOne(long id) {
        return null;
    }

    @Override
    public void create(OrderLine orderLine) {
        PreparedStatement pstsmt;

        String query = "INSERT INTO order_lines VALUES (NULL, ?, ?, ?, ?)";
        try {
            pstsmt = getConnection().prepareStatement(query);
            pstsmt.setLong(1, orderLine.getProduct().getCode());
            pstsmt.setLong(2, orderLine.getOrder().getId());
            pstsmt.setInt(3, orderLine.getQty());
            pstsmt.setDouble(4, orderLine.getPrice());
            pstsmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(OrderLine entity) {

    }

    @Override
    public void delete(OrderLine entity) {

    }
}
