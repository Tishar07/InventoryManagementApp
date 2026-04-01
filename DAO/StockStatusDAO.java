package DAO;

import model.StockStatus;

import java.sql.*;
import java.util.ArrayList;

public class StockStatusDAO {
    private Connection conn;

    public StockStatusDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<StockStatus> getStockStatus() {
        ArrayList<StockStatus> list = new ArrayList<>();
        String sql = """
                SELECT ss.ProductID, p.ProductName, ss.CurrentStock
                FROM stockstatus ss
                JOIN product p ON ss.ProductID = p.ProductID
                WHERE p.ProductStatus ='Available'
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new StockStatus(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getInt("CurrentStock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<StockStatus> searchStockStatus(String value) {
        ArrayList<StockStatus> list = new ArrayList<>();
        String sql = """
                SELECT ss.ProductID, p.ProductName, ss.CurrentStock
                FROM stockstatus ss
                JOIN product p ON ss.ProductID = p.ProductID
                WHERE p.ProductName LIKE CONCAT('%', ?, '%')
                OR ss.CurrentStock = ?
                ORDER BY p.ProductName ASC
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, value);
            if (isInteger(value)) ps.setInt(2, Integer.parseInt(value));
            else ps.setNull(2, Types.INTEGER);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new StockStatus(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getInt("CurrentStock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private boolean isInteger(String str) {
        if (str == null || str.isEmpty()) return false;
        try { Integer.parseInt(str); return true; }
        catch (NumberFormatException e) { return false; }
    }
}
