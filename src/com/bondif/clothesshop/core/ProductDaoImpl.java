package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Category;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.views.GUITools;
import javafx.scene.control.Alert;

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
        String sql = "select * from products p, categories c where p.category_id = c.id";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long code = rs.getLong("p.code");
                String label = rs.getString("p.label");
                int qty = rs.getInt("p.qty");
                double buyingPrice = rs.getDouble("p.buyingPrice");
                double sellingPrice = rs.getDouble("p.sellingPrice");
                String image = rs.getString("p.image");

                Category category = new Category();
                category.setId(rs.getLong("c.id"));
                category.setTitle(rs.getString("c.title"));

                Product p = new Product(code, label, qty, buyingPrice, sellingPrice, image, category);
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Collection<Product> findAll(String filter) {
        Collection<Product> products = new LinkedList<>();
        filter = "%" + filter + "%";
        if (filter.isEmpty()) return findAll();
        String sql = "select * from products p, categories c where p.category_id = c.id " +
                "and (p.code like ? " +
                "or p.label like ?)";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, filter);
            pstmt.setString(2, filter);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long code = rs.getLong("p.code");
                String label = rs.getString("p.label");
                int qty = rs.getInt("p.qty");
                double buyingPrice = rs.getDouble("p.buyingPrice");
                double sellingPrice = rs.getDouble("p.sellingPrice");
                String image = rs.getString("p.image");

                Category category = new Category();
                category.setId(rs.getLong("c.id"));
                category.setTitle(rs.getString("c.title"));

                Product p = new Product(code, label, qty, buyingPrice, sellingPrice, image, category);
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
        String sql = "select code, label, qty, buyingPrice, sellingPrice, image, p.category_id cat_id, title cat_title from products p, categories c where p.category_id = c.id and p.code = ?";

        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setLong(1, code);
            rs = pstmt.executeQuery();
            rs.next();

            long codeDb = rs.getLong("code");
            String label = rs.getString("label");
            int qty = rs.getInt("qty");
            double buyingPrice = rs.getDouble("buyingPrice");
            double sellingPrice = rs.getDouble("sellingPrice");
            String image = rs.getString("image");
            Category category = new Category(rs.getLong("cat_id"), rs.getString("cat_title"));

            product = new Product(codeDb, label, qty, buyingPrice, sellingPrice, image, category);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    @Override
    public void create(Product product) {
        PreparedStatement pstsmt;

        product.setImage(saveImage(product.getImage()));

        String query = "INSERT INTO Products VALUES (NULL, ?, ?, ?, ?, ?, ?)";
        try {
            pstsmt = getConnection().prepareStatement(query);
            pstsmt.setLong(1, product.getCategory().getId());
            pstsmt.setString(2, product.getLabel());
            pstsmt.setInt(3, product.getQty());
            pstsmt.setDouble(4, product.getBuyingPrice());
            pstsmt.setDouble(5, product.getSellingPrice());
            pstsmt.setString(6, product.getImage());
            pstsmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) {
        PreparedStatement pstmt;

        String sql = "update products set category_id = ?, label = ?, qty = ?, buyingPrice = ?, sellingPrice = ?, image = ? where code = ?";

        try {
            pstmt = getConnection().prepareStatement(sql);

            pstmt.setLong(1, product.getCategory().getId());
            pstmt.setString(2, product.getLabel());
            pstmt.setInt(3, product.getQty());
            pstmt.setDouble(4, product.getBuyingPrice());
            pstmt.setDouble(5, product.getSellingPrice());
            pstmt.setString(6, product.getImage());
            pstmt.setLong(7, product.getCode());

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
            GUITools.openDialogOk("Echec de suppression", null, "Impossible de supprimer un produit qui existe dans une ligne de commande!", Alert.AlertType.ERROR);
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

    public void updateQty(Product product){
        PreparedStatement pstmt;

        String sql = "update products set qty = ? where code = ?";

        try {
            pstmt = getConnection().prepareStatement(sql);

            pstmt.setInt(1, product.getQty());
            pstmt.setLong(2, product.getCode());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}