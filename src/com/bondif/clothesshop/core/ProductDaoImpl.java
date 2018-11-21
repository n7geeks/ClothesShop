package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Product;

import java.io.File;
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
    public Product findOne(long code) {
        Product product = null;
        String sql = "select * from products where code = ?";

        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setLong(1, code);
            rs = pstmt.executeQuery();
            rs.next();
            System.out.println(rs.getLong("code"));

            long codeDb = rs.getLong("code");
            String label = rs.getString("label");
            double buyingPrice = rs.getDouble("buyingPrice");
            double sellingPrice = rs.getDouble("sellingPrice");
            String image = rs.getString("image");

            product = new Product(codeDb, label, buyingPrice, sellingPrice, image);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void create(Product product) {
        PreparedStatement pstsmt;

        System.out.println(product.getImage());
        product.setImage(saveImage(product.getImage()));

        String query = "INSERT INTO Products VALUES (NULL, ?, ?, ?, ?)";
        try {
            pstsmt = getConnection().prepareStatement(query);
            pstsmt.setString(1, product.getLabel());
            pstsmt.setDouble(2, product.getBuyingPrice());
            pstsmt.setDouble(3, product.getSellingPrice());
            pstsmt.setString(4, product.getImage());
            pstsmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        PreparedStatement pstmt;

        String sql = "update products set label = ?, buyingPrice = ?, sellingPrice = ?, image = ?";

        try {
            pstmt = getConnection().prepareStatement(sql);

            pstmt.setString(1, product.getLabel());
            pstmt.setDouble(2, product.getBuyingPrice());
            pstmt.setDouble(3, product.getSellingPrice());
            pstmt.setString(4, product.getImage());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Product product) {
        PreparedStatement pstmt;

        String sql = "delete from products where code = ?";

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setLong(1, product.getCode());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String saveImage(String image) {
        Path savedImage = Paths.get(image);

        String newPath = "resources/images/products/" + new Date().getTime() + "_" + savedImage.getFileName();
        Path destImage = Paths.get(newPath);

        try {
            Files.copy(savedImage, destImage, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return newPath;
    }
}