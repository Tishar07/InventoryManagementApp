package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bachandb";
        String username = "root";
        String password = "Pushkaar21$";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected sucessfully!");
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
        }
    }
}
