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
                WHERE s.SupplierID = ?
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
                WHERE p.Name LIKE ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + value + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Supplier sup = new Supplier(
                        rs.getInt("SupplierID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Contact"),
                        rs.getString("Status")
                );
                supplierList.add(sup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierList;
    }

    public void save(String name, String email, String address,
                     String contact, String status) {

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

    public void update(int SupplierID, String name, String email,
                       String address, String contact, String status) {

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

    // =========================
    // DELETE SUPPLIER
    // =========================
    public void delete(int SupplierID) {

        String sql = """
                DELETE p
                FROM person p
                INNER JOIN supplier s ON s.PersonID = p.PersonID
                WHERE s.SupplierID = ?
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, SupplierID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        sql = "DELETE FROM supplier WHERE SupplierID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, SupplierID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}