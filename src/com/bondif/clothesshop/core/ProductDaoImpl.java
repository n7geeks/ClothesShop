package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class ProductDaoImpl extends AbstractDao implements Dao<Product> {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    @Override
    public Collection<Product> findAll() {
        Collection<Product> products = new LinkedList<>();
        String sql = "select * from products";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long code = rs.getLong("code");
                String label = rs.getString("label");
                double buyingPrice = rs.getDouble("buyingPrice");
                double sellingPrice = rs.getDouble("sellingPrice");
                String image = rs.getString("image");

                Product p = new Product(code, label, buyingPrice, sellingPrice, image);
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    @Override
    public Product findOne(long id) {
        return null;
    }

    @Override
    public void create(Product entity) {

    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(Product entity) {

    }
}
