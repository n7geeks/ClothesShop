package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Category;
import com.bondif.clothesshop.models.Product;
import com.bondif.clothesshop.models.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class SaleDaoImpl extends AbstractDao implements Dao<Sale> {

    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    @Override
    public Collection<Sale> findAll() {
        Collection<Sale> sales = new LinkedList<>();
        String sql = "select * from sales";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sql);
            rs = pstmt.executeQuery();

            Sale sale = new Sale();
            while (rs.next()) {
                sale.setId(rs.getLong("id"));
                sale.setCustomer(rs.getLong("customer_id") + "");
                sale.setTotal(rs.getDouble("total"));
                sale.setCreatedAt(Tools.toLocalDateTime(rs.getTimestamp("created_at")));

                sales.add(sale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sales;
    }

    @Override
    public Sale findOne(long id) {
        return null;
    }

    @Override
    public void create(Sale entity) {

    }

    @Override
    public void update(Sale entity) {

    }

    @Override
    public void delete(Sale entity) {

    }
}
