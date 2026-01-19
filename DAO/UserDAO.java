package DAO;

import database.DBConnection;
import model.User;

import java.sql.*;

public class UserDAO {
    private Connection conn;
    public UserDAO(Connection conn){
        this.conn= conn;
    }

    public User findByUsername(String username) {

        String sql = """
            SELECT u.user_id,u.username,u.password,u.role,p.name,p.email,p.status,p.address,p.contact
            FROM Users u
            JOIN Person p ON u.user_id = p.person_id
            WHERE u.username = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("role"),
                        rs.getString("password"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("status"),
                        rs.getString("contact"),
                        rs.getString("address")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }

        return null;
    }

    public void insert(User r) {

        String insertPerson = """
            INSERT INTO Person (name, email, contact, address, status)
            VALUES (?, ?, ?, ?, ?)
        """;

        String insertUser = """
            INSERT INTO Users (user_id, username, password, role)
            VALUES (?, ?, ?, ?)
        """;

        try {
            conn.setAutoCommit(false);
            int personId;

            try (PreparedStatement ps = conn.prepareStatement(
                    insertPerson, Statement.RETURN_GENERATED_KEYS)) {

                ps.setString(1, r.getName());
                ps.setString(2, r.getEmail());
                ps.setString(3, r.getContact());
                ps.setString(4, r.getAddress());
                ps.setString(5, r.getStatus());
                ps.executeUpdate();

                ResultSet keys = ps.getGeneratedKeys();
                if (!keys.next()) {
                    throw new SQLException("Failed to generate person_id");
                }
                personId = keys.getInt(1);
            }

            try (PreparedStatement ps = conn.prepareStatement(insertUser)) {
                ps.setInt(1, personId);
                ps.setString(2, r.getUsername());
                ps.setString(3, r.getPassword());
                ps.setString(4, r.getRole());
                ps.executeUpdate();
            }

            conn.commit();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to insert user", e);
        }
    }

    public void deleteByUsername(String username) {

        String sql = """
            DELETE p
            FROM Person p
            JOIN Users u ON p.person_id = u.user_id
            WHERE u.username = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Delete failed", e);
        }
    }

    //Query to fetch data from table (user,person) to validate login details
    public User validateLogin(String Username,String Password){
        String sql = "SELECT x.*,z.* " +
                     "FROM users x " +
                     "INNER JOIN person z ON x.user_id = z.person_id " +
                     "WHERE x.username = ? AND x.password = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, Username);
            ps.setString(2, Password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new User(
                        rs.getInt("x.user_id"),
                        rs.getString("x.username"),
                        rs.getString("x.role"),
                        rs.getString("x.password"),
                        rs.getString("z.name"),
                        rs.getString("z.email"),
                        rs.getString("z.status"),
                        rs.getString("z.address"),
                        rs.getString("z.contact")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
