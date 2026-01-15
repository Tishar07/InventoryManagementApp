package DAO;

import database.DBConnection;
import model.User;

import java.sql.*;

public class UserDAO {
    private Connection connection;
    public UserDAO(Connection connection){
        this.connection= connection;
    }
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

    //Query to fetch data from table (user,person) to validate login details
    public User validateLogin(String Username,String Password){
        String sql = "SELECT x.*,z.* " +
                     "FROM users x " +
                     "INNER JOIN person z ON x.user_id = z.person_id " +
                     "WHERE x.username = ? AND x.password = ?";

        try(Connection conn = DBConnection.getConnection();PreparedStatement ps = conn.prepareStatement(sql)){
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
