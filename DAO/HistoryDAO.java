package DAO;
import model.StockHistory;
import java.sql.*;
import java.util.ArrayList;

public class HistoryDAO {
    private Connection conn;
    public HistoryDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<StockHistory> getHistory() {

        ArrayList<StockHistory> historyList = new ArrayList<>();

        String sql = """
                SELECT HistoryID, TransactionID, ProductID, ProductName,
                       SupplierID, SupplierName, RetailerID, RetailerName,
                       Quantity, TransactionType, Status, ActionDate
                FROM stockhistory
                ORDER BY ActionDate DESC
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                historyList.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    public ArrayList<StockHistory> searchHistory(String value) {
        ArrayList<StockHistory> historyList = new ArrayList<>();

        String sql = """
                SELECT HistoryID, TransactionID, ProductID, ProductName,
                       SupplierID, SupplierName, RetailerID, RetailerName,
                       Quantity, TransactionType, Status, ActionDate
                FROM stockhistory
                WHERE ProductName LIKE ?
                   OR SupplierName LIKE ?
                   OR RetailerName LIKE ?
                   OR TransactionType LIKE ?
                   OR Status LIKE ?
                ORDER BY ActionDate DESC
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String like = "%" + value + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            ps.setString(4, like);
            ps.setString(5, like);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                historyList.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }

    public ArrayList<StockHistory> filterByType(String type) {
        ArrayList<StockHistory> historyList = new ArrayList<>();

        String sql = """
                SELECT HistoryID, TransactionID, ProductID, ProductName,
                       SupplierID, SupplierName, RetailerID, RetailerName,
                       Quantity, TransactionType, Status, ActionDate
                FROM stockhistory
                WHERE TransactionType = ?
                ORDER BY ActionDate DESC
                """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                historyList.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return historyList;
    }

    private StockHistory mapRow(ResultSet rs) throws SQLException {
        return new StockHistory(
                rs.getInt("HistoryID"),
                rs.getInt("TransactionID"),
                rs.getInt("ProductID"),
                rs.getString("ProductName"),
                rs.getInt("SupplierID"),
                rs.getString("SupplierName"),
                rs.getInt("RetailerID"),
                rs.getString("RetailerName"),
                rs.getInt("Quantity"),
                rs.getString("TransactionType"),
                rs.getString("Status"),
                rs.getString("ActionDate")
        );
    }
}