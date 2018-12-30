package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Admin;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;

public class AdminDaoImpl extends AbstractDao {
    @Override
    public Connection getConnection() {
        return Database.getInstance().getConnection();
    }

    private Collection<Admin> getAdminsList(String sqlQuery){
        Collection<Admin> admins = new LinkedList<>();

        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(sqlQuery);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getInt("id");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String password = rs.getString("password");
                InputStream picture = rs.getBinaryStream("picture");

                Admin admin = new Admin(id, firstName, lastName, email, password, picture);
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admins;
    }

    public Collection<Admin> findAll() {
        Collection<Admin> admins;
        String sql = "SELECT * FROM ADMINS";

        admins = getAdminsList(sql);
        return admins;
    }

    public void createAdmin(Admin admin){
        if (findAdmin(admin.getEmail())) {
            System.out.println("Admin already exists");
        }else{
            PreparedStatement pstmt;
            String query = "INSERT INTO Admins VALUES (NULL, ?, ?, ?, ?, ?)";

            try {
                pstmt = getConnection().prepareStatement(query);
                pstmt.setString(1, admin.getFirstName());
                pstmt.setString(2, admin.getLastName());
                pstmt.setString(3, admin.getEmail());
                pstmt.setString(4, admin.getPassword());
                pstmt.setBinaryStream(5, admin.getPicture());
                pstmt.executeUpdate();
            } catch (SQLException exp){
                exp.printStackTrace();
            }
        }
    }

    public boolean findAdmin (String email) {
        String query = "SELECT * FROM Admins WHERE email LIKE ?";
        PreparedStatement pstmt;
        boolean found = false;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()){
                found = pstmt.execute();
            }
        }catch (SQLException exp){
            exp.printStackTrace();
        }

        return found;
    }

    public long findAdminId (String email) {
        String query = "SELECT id FROM Admins WHERE email LIKE ?";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            if (rs.next()){
                return rs.getLong(1);
            }
        }catch (SQLException exp){
            exp.printStackTrace();
        }

        return -1;
    }

    public Admin findAdmin (long id) {
        String query = "SELECT * FROM Admins WHERE id = ?";
        PreparedStatement pstmt;
        ResultSet rs;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()){
                Admin admin = new Admin(id, rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getBinaryStream(6));

                return admin;
            }
        }catch (SQLException exp){
            exp.printStackTrace();
        }
        return null;
    }

    public Image getAdminImage (String email) {
        String query = "SELECT picture FROM Admins WHERE email LIKE ?";
        PreparedStatement pstmt;
        boolean found = false;
        ResultSet rs = null;
        java.awt.Image image = null;
        javafx.scene.image.Image img = null;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                try {
                    InputStream is = new BufferedInputStream(rs.getBinaryStream(1));
                    BufferedImage imBuff = ImageIO.read(is);
                    //image = ImageIO.read(is);
                    img = SwingFXUtils.toFXImage(imBuff, null);
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
            }
        }catch (SQLException exp){
            exp.printStackTrace();
        }

        return img;
    }

    public boolean checkPassword (String email, String password) {
        boolean valid = false;
        PreparedStatement pstmt;
        String query = "SELECT password FROM Admins WHERE email LIKE ?";
        ResultSet resultSet;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()){
                if (password.equals(resultSet.getString(1))){
                    valid = true;
                }
            }
        } catch (SQLException exp){
            exp.printStackTrace();
        }

        return valid;
    }

    public String getPassword(String email) {
        PreparedStatement pstmt;
        String query = "SELECT password FROM Admins WHERE email LIKE ?";
        ResultSet resultSet;

        try {
            pstmt = getConnection().prepareStatement(query);
            pstmt.setString(1, email);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (SQLException exp){
            exp.printStackTrace();
        }

        return null;
    }
}
