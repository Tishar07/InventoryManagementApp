package DAO;

import model.Supplier;
import database.DBConnection;
import java.sql.*;
import java.util.*;

public class SupplierDAO {

    public void addSupplier(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO suppliers (supplier_id, name, email, address, contact, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, supplier.getSupplierid());
            stmt.setString(2, supplier.getName());
            stmt.setString(3, supplier.getEmail());
            stmt.setString(4, supplier.getAddress());
            stmt.setString(5, supplier.getContact());
            stmt.setString(6, supplier.getStatus());
            stmt.executeUpdate();
        }
    }

    public Supplier getSupplierById(int id) throws SQLException {
        String sql = "SELECT * FROM suppliers WHERE supplier_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("contact"),
                        rs.getString("status")
                );
            }
        }
        return null;
    }

    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT * FROM suppliers";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                suppliers.add(new Supplier(
                        rs.getInt("supplier_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("address"),
                        rs.getString("contact"),
                        rs.getString("status")
                ));
            }
        }
        return suppliers;
    }

    public void updateSupplier(Supplier supplier) throws SQLException {
        String sql = "UPDATE suppliers SET name=?, email=?, address=?, contact=?, status=? WHERE supplier_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, supplier.getName());
            stmt.setString(2, supplier.getEmail());
            stmt.setString(3, supplier.getAddress());
            stmt.setString(4, supplier.getContact());
            stmt.setString(5, supplier.getStatus());
            stmt.setInt(6, supplier.getSupplierid());
            stmt.executeUpdate();
        }
    }

    public void deleteSupplier(int id) throws SQLException {
        String sql = "DELETE FROM suppliers WHERE supplier_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
