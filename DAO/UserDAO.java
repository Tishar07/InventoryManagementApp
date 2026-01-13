package DAO;

import database.DBConnection;
import model.User;

import java.sql.*;

public class UserDAO {

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean insertUser(User r) {
        String sql = "INSERT INTO users " +
                "(username, role, password, name, email, status, address, contact) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, r.getUsername());
            ps.setString(2, r.getRole());
            ps.setString(3, r.getPassword());
            ps.setString(4, r.getName());
            ps.setString(5, r.getEmail());
            ps.setString(6, r.getStatus());
            ps.setString(7, r.getAddress());
            ps.setString(8, r.getContact());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
