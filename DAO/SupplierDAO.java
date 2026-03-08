package DAO;

import model.Supplier;

import java.sql.*;
import java.util.ArrayList;

public class SupplierDAO {

    private Connection conn;

    public SupplierDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<Supplier> getSupplier() {

        ArrayList<Supplier> supplierList = new ArrayList<>();

        String sql = """
                SELECT s.SupplierID, p.Name, p.Email, p.Address, p.Contact, p.Status
                FROM person p
                INNER JOIN supplier s ON p.PersonID = s.PersonID
                WHERE p.Status='Active'
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getInt("SupplierID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Contact"),
                        rs.getString("Status")
                );
                supplierList.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierList;
    }

    public Supplier getExistingSupplier(int SupplierID) {

        Supplier supplier = null;

        String sql = """
                SELECT s.SupplierID, p.Name, p.Email, p.Address, p.Contact, p.Status
                FROM person p
                INNER JOIN supplier s ON p.PersonID = s.PersonID
                WHERE s.SupplierID = ? AND p.Status ='Active'
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, SupplierID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                supplier = new Supplier(
                        rs.getInt("SupplierID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Contact"),
                        rs.getString("Status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplier;
    }

    public ArrayList<Supplier> searchSupplier(String value) {

        ArrayList<Supplier> supplierList = new ArrayList<>();

        String sql = """
                SELECT s.SupplierID, p.Name, p.Email, p.Address, p.Contact, p.Status
                FROM person p
                INNER JOIN supplier s ON p.PersonID = s.PersonID
                WHERE p.Status = 'Active'
                AND (
                      s.SupplierID = ?
                   OR p.Name LIKE CONCAT('%', ?, '%')
                   OR p.Email LIKE CONCAT('%', ?, '%')
                   OR p.Contact LIKE CONCAT('%', ?, '%')
                   OR p.Address LIKE CONCAT('%', ?, '%')
                );
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            if (isInteger(value)) {
                ps.setInt(1, Integer.parseInt(value));
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }

            String likeValue = "%" + value + "%";
            ps.setString(2, likeValue);
            ps.setString(3, likeValue);
            ps.setString(4, likeValue);
            ps.setString(5, likeValue);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Supplier sup = new Supplier(
                        rs.getInt("SupplierID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Contact"),
                        "Active"
                );
                supplierList.add(sup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplierList;
    }

    public void save(String name, String email, String address, String contact, String status) {

        String sql = """
                INSERT INTO person (Name, Email, Address, Contact, Status)
                VALUES (?,?,?,?,?)
                """;

        int personId = -1;
        try (PreparedStatement ps =
                     conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, contact);
            ps.setString(5, status);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                personId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "INSERT INTO supplier (PersonID) VALUES (?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(int SupplierID, String name, String email, String address, String contact, String status) {

        String sql = """
                UPDATE person p
                INNER JOIN supplier s ON p.PersonID = s.PersonID
                SET
                    p.Name = ?,
                    p.Email = ?,
                    p.Contact = ?,
                    p.Address = ?,
                    p.Status = ?
                WHERE s.SupplierID = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, contact);
            ps.setString(4, address);
            ps.setString(5, status);
            ps.setInt(6, SupplierID);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int SupplierID) {

        String sql = """
                UPDATE person p
                INNER JOIN supplier s ON s.PersonID = p.PersonID
                SET p.Status = 'Active'
                WHERE s.SupplierID = ?;
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, SupplierID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean isInteger(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}