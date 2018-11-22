package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class CategoryDaoImpl extends AbstractDao implements Dao<Category> {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    @Override
    public Collection<Category> findAll() {
        Collection<Category> categories = new LinkedList<>();
        String sql = "select * from categories";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long idcat = rs.getLong("id");
                String titlecat= rs.getString("title");

                Category c = new Category(idcat, titlecat);
                categories.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }

    @Override
    public Category findOne(long id) {
        return null;
    }

    @Override
    public void create(Category entity) {
        PreparedStatement pstsmt;

        String query = "INSERT INTO categories VALUES (NULL, ?)";
        try {
            pstsmt = getConnection().prepareStatement(query);
            pstsmt.setString(1, entity.getTitle());

            pstsmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Category entity) {
        PreparedStatement pstsmt;

        String query = "update categories set title = ? where id = ?";
        try {
            pstsmt = getConnection().prepareStatement(query);
            pstsmt.setString(1, entity.getTitle());
            pstsmt.setLong(2, entity.getIdCategory());


            pstsmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Category entity) {
        PreparedStatement pstsmt;

        String query = "delete from categories where id = ?";
        try {
            pstsmt = getConnection().prepareStatement(query);
            pstsmt.setLong(1, entity.getIdCategory());

            pstsmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
