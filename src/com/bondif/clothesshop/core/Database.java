package com.bondif.clothesshop.core;

import java.sql.*;

public class Database {
    private static Database ourInstance = new Database();
    private static Connection connection;

    public static Database getInstance() {
        return ourInstance;
    }

    public Connection getConnection() {
        return connection;
    }

    private Database() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/clothes_shop", "root", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
