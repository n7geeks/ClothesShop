package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Cheque;

import java.sql.*;
import java.util.Collection;

public class ChequeDaoImpl extends AbstractDao {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    public int create(Cheque cheque) {
        PreparedStatement pstsmt;

        String sql = "INSERT INTO cheques VALUES (NULL, ?, ?)";
        try {
            pstsmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstsmt.setString(1, cheque.getOwner());
            pstsmt.setString(2, cheque.getEffectiveDate());
            pstsmt.execute();

            ResultSet generatedKeys = pstsmt.getGeneratedKeys();
            if(generatedKeys.next()) return generatedKeys.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }
}
