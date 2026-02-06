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
            SELECT u.UserID,u.Username,u.Password,u.Role,p.Name,p.Email,p.Status,p.Address,p.Contact
            FROM user u
            JOIN person p ON u.UserID = p.PersonID
            WHERE u.Username = ?
        """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("u.UserID"),
                        rs.getString("u.Username"),
                        rs.getString("u.Role"),
                        rs.getString("u.Password"),
                        rs.getString("p.Name"),
                        rs.getString("p.Email"),
                        rs.getString("p.Status"),
                        rs.getString("p.Contact"),
                        rs.getString("p.Address")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Database error", e);
        }

        return null;
    }

    public void insert(User r) {

        String insertPerson = """
            INSERT INTO person (Name, Email, Contact, Address, Status)
            VALUES (?, ?, ?, ?, ?)
        """;

        String insertUser = """
            INSERT INTO user (UserID, Username, Password, Role)
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
            FROM person p
            JOIN user u ON p.PersonID = u.UserID
            WHERE u.Username = ?
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
                     "FROM user x " +
                     "INNER JOIN person z ON x.UserID = z.PersonID " +
                     "WHERE x.Username = ? AND x.Password = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, Username);
            ps.setString(2, Password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return new User(
                        rs.getInt("x.UserID"),
                        rs.getString("x.Username"),
                        rs.getString("x.Role"),
                        rs.getString("x.Password"),
                        rs.getString("z.Name"),
                        rs.getString("z.Email"),
                        rs.getString("z.Status"),
                        rs.getString("z.Address"),
                        rs.getString("z.Contact")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  null;
    }

}
