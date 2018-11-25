package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Category;
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
        CategoryDaoImpl categoryDao = new CategoryDaoImpl();
        Collection<Product> products = new LinkedList<>();
        Collection<Category> categories = categoryDao.findAll();
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
                long category_id = rs.getLong("category_id");
                Category category = getCategoryById(categories, category_id);

                Product p = new Product(code, label, buyingPrice, sellingPrice, image, category);
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
        String sql = "select code, label, buyingPrice, sellingPrice, image, p.category_id cat_id, title cat_title from products p, categories c where p.category_id = c.id and p.code = ?";

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
            Category category = new Category(rs.getLong("cat_id"), rs.getString("cat_title"));

            product = new Product(codeDb, label, buyingPrice, sellingPrice, image, category);

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

        String query = "INSERT INTO Products VALUES (NULL, ?, ?, ?, ?, ?)";
        try {
            pstsmt = getConnection().prepareStatement(query);
            pstsmt.setLong(1, product.getCategory().getId());
            pstsmt.setString(2, product.getLabel());
            pstsmt.setDouble(3, product.getBuyingPrice());
            pstsmt.setDouble(4, product.getSellingPrice());
            pstsmt.setString(5, product.getImage());
            pstsmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        PreparedStatement pstmt;

        String sql = "update products set category_id = ?, label = ?, buyingPrice = ?, sellingPrice = ?, image = ? where code = ?";

        try {
            pstmt = getConnection().prepareStatement(sql);

            pstmt.setLong(1, product.getCategory().getId());
            pstmt.setString(2, product.getLabel());
            pstmt.setDouble(3, product.getBuyingPrice());
            pstmt.setDouble(4, product.getSellingPrice());
            pstmt.setString(5, product.getImage());
            pstmt.setLong(6, product.getCode());

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

    private Category getCategoryById(Collection<Category> categories, long category_id) {
        for (Category category: categories) {
            if(category.getId() == category_id) {
                return category;
            }
        }
        return null;
    }
}