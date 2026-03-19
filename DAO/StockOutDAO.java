// DAO/StockOutDAO.java
package DAO;

import model.Product;
import model.Retailer;
import model.StockOut;

import java.sql.*;
import java.util.ArrayList;

public class StockOutDAO {
    private Connection conn;

    public StockOutDAO(Connection conn) {
        this.conn = conn;
    }

    // ─────────────────────────────────────────────────────────────
    // FETCH ALL  (only RETAILER_OUT transactions)
    // ─────────────────────────────────────────────────────────────
    public ArrayList<StockOut> getStockOut() {
        ArrayList<StockOut> list = new ArrayList<>();
        String sql = """
                SELECT
                    st.TransactionID, st.ProductID, pr.ProductName,
                    st.RetailerID,    p.Name AS RetailerName,
                    st.Quantity,      st.Status,
                    st.Notes,         st.TransactionDate
                FROM stocktransaction st
                JOIN product  pr ON st.ProductID  = pr.ProductID
                JOIN retailer r  ON st.RetailerID = r.RetailerID
                JOIN person   p  ON r.PersonID    = p.PersonID
                WHERE st.TransactionType = 'RETAILER_OUT'
                ORDER BY st.TransactionID DESC
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ─────────────────────────────────────────────────────────────
    // FETCH SINGLE RECORD FOR EDIT FORM
    // ─────────────────────────────────────────────────────────────
    public StockOut getExistingStockOut(int transactionId) {
        String sql = """
                SELECT
                    st.TransactionID, st.ProductID, pr.ProductName,
                    st.RetailerID,    p.Name AS RetailerName,
                    st.Quantity,      st.Status,
                    st.Notes,         st.TransactionDate
                FROM stocktransaction st
                JOIN product  pr ON st.ProductID  = pr.ProductID
                JOIN retailer r  ON st.RetailerID = r.RetailerID
                JOIN person   p  ON r.PersonID    = p.PersonID
                WHERE st.TransactionID = ?
                  AND st.TransactionType = 'RETAILER_OUT'
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ─────────────────────────────────────────────────────────────
    // SAVE  — insert transaction + deduct stockstatus + log history
    // ─────────────────────────────────────────────────────────────
    public void save(int retailerId, int productId,
                     int quantity, String status, String notes) {

        // 1. Insert into stocktransaction
        int transactionId = 0;
        String insertTx = """
                INSERT INTO stocktransaction
                    (ProductID, RetailerID, Quantity, TransactionType, Status, Notes)
                VALUES (?, ?, ?, 'RETAILER_OUT', ?, ?)
                """;
        try (PreparedStatement ps =
                     conn.prepareStatement(insertTx, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, productId);
            ps.setInt(2, retailerId);
            ps.setInt(3, quantity);
            ps.setString(4, status);
            ps.setString(5, notes);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) transactionId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // 2. Deduct from stockstatus only when Completed
        if (status.equals("Completed")) {
            deductStock(productId, quantity);
        }

        // 3. Log to stockhistory
        logHistory(transactionId, productId, retailerId, quantity, status);
    }

    // ─────────────────────────────────────────────────────────────
    // UPDATE  — adjust stockstatus based on old vs new status/qty
    // ─────────────────────────────────────────────────────────────
    public void update(int transactionId, int retailerId, int productId,
                       int quantity, String status, String notes) {

        // 1. Fetch old values before update
        int oldQty = 0;
        int oldProductId = 0;
        String oldStatus = "";
        String fetchOld = """
                SELECT Quantity, ProductID, Status
                FROM stocktransaction
                WHERE TransactionID = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(fetchOld)) {
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                oldQty       = rs.getInt("Quantity");
                oldProductId = rs.getInt("ProductID");
                oldStatus    = rs.getString("Status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // 2. Update stocktransaction record
        String updateTx = """
                UPDATE stocktransaction
                SET RetailerID = ?, ProductID = ?,
                    Quantity = ?, Status = ?, Notes = ?
                WHERE TransactionID = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(updateTx)) {
            ps.setInt(1, retailerId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setString(4, status);
            ps.setString(5, notes);
            ps.setInt(6, transactionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // 3. Reverse old stock effect, apply new stock effect
        //    Only "Completed" transactions affect stockstatus
        if (oldStatus.equals("Completed")) {
            restoreStock(oldProductId, oldQty);   // undo old deduction
        }
        if (status.equals("Completed")) {
            deductStock(productId, quantity);      // apply new deduction
        }

        // 4. Log update to stockhistory
        logHistory(transactionId, productId, retailerId, quantity, status);
    }

    // ─────────────────────────────────────────────────────────────
    // CANCEL  — soft delete by setting Status = 'Cancelled'
    // ─────────────────────────────────────────────────────────────
    public void cancel(int transactionId) {
        // Fetch before cancelling
        int qty = 0; int productId = 0; String currentStatus = "";
        String fetchSql = "SELECT Quantity, ProductID, Status FROM stocktransaction WHERE TransactionID = ?";
        try (PreparedStatement ps = conn.prepareStatement(fetchSql)) {
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                qty           = rs.getInt("Quantity");
                productId     = rs.getInt("ProductID");
                currentStatus = rs.getString("Status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Restore stock only if it was previously Completed
        if (currentStatus.equals("Completed")) {
            restoreStock(productId, qty);
        }

        // Soft cancel
        String cancelSql = "UPDATE stocktransaction SET Status = 'Cancelled' WHERE TransactionID = ?";
        try (PreparedStatement ps = conn.prepareStatement(cancelSql)) {
            ps.setInt(1, transactionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ─────────────────────────────────────────────────────────────
    // SEARCH
    // ─────────────────────────────────────────────────────────────
    public ArrayList<StockOut> searchStockOut(String value) {
        ArrayList<StockOut> list = new ArrayList<>();
        String sql = """
                SELECT
                    st.TransactionID, st.ProductID, pr.ProductName,
                    st.RetailerID,    p.Name AS RetailerName,
                    st.Quantity,      st.Status,
                    st.Notes,         st.TransactionDate
                FROM stocktransaction st
                JOIN product  pr ON st.ProductID  = pr.ProductID
                JOIN retailer r  ON st.RetailerID = r.RetailerID
                JOIN person   p  ON r.PersonID    = p.PersonID
                WHERE st.TransactionType = 'RETAILER_OUT'
                AND (
                      st.TransactionID = ?
                   OR pr.ProductName  LIKE CONCAT('%', ?, '%')
                   OR p.Name          LIKE CONCAT('%', ?, '%')
                   OR st.Status       LIKE CONCAT('%', ?, '%')
                )
                ORDER BY st.TransactionID DESC
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (isInteger(value)) ps.setInt(1, Integer.parseInt(value));
            else                  ps.setNull(1, Types.INTEGER);
            ps.setString(2, value);
            ps.setString(3, value);
            ps.setString(4, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ─────────────────────────────────────────────────────────────
    // DROPDOWNS
    // ─────────────────────────────────────────────────────────────
    public ArrayList<Retailer> getRetailers() {
        ArrayList<Retailer> list = new ArrayList<>();
        String sql = """
                SELECT r.RetailerID, p.Name, p.Email, p.Address, p.Contact, p.Status
                FROM retailer r
                JOIN person p ON r.PersonID = p.PersonID
                WHERE p.Status = 'Active'
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Retailer(
                        rs.getInt("RetailerID"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getString("Contact"),
                        rs.getString("Status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<Product> getProducts() {
        ArrayList<Product> list = new ArrayList<>();
        String sql = """
                SELECT ProductID, ProductName, UnitPrice,
                       ProductStatus, Gender, ImagePath
                FROM product
                WHERE ProductStatus = 'Available'
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("UnitPrice"),
                        rs.getString("ProductStatus"),
                        rs.getString("Gender"),
                        rs.getString("ImagePath")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ─────────────────────────────────────────────────────────────
    // PRIVATE HELPERS
    // ─────────────────────────────────────────────────────────────

    /** Reduce CurrentStock in stockstatus */
    private void deductStock(int productId, int quantity) {
        String sql = """
                UPDATE stockstatus
                SET CurrentStock = CurrentStock - ?,
                    LastUpdated  = NOW()
                WHERE ProductID = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Restore CurrentStock in stockstatus (used on cancel / update reversal) */
    private void restoreStock(int productId, int quantity) {
        String sql = """
                UPDATE stockstatus
                SET CurrentStock = CurrentStock + ?,
                    LastUpdated  = NOW()
                WHERE ProductID = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, quantity);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Write a snapshot row into stockhistory */
    private void logHistory(int transactionId, int productId,
                            int retailerId, int quantity, String status) {
        // Resolve names for the snapshot columns
        String productName  = "";
        String retailerName = "";

        String nameSql = """
                SELECT pr.ProductName, p.Name AS RetailerName
                FROM product  pr,
                     retailer r
                JOIN person p ON r.PersonID = p.PersonID
                WHERE pr.ProductID  = ?
                  AND r.RetailerID  = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(nameSql)) {
            ps.setInt(1, productId);
            ps.setInt(2, retailerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                productName  = rs.getString("ProductName");
                retailerName = rs.getString("RetailerName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String insertHistory = """
                INSERT INTO stockhistory
                    (TransactionID, ProductID, ProductName,
                     RetailerID, RetailerName,
                     Quantity, TransactionType, Status)
                VALUES (?, ?, ?, ?, ?, ?, 'RETAILER_OUT', ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(insertHistory)) {
            ps.setInt(1, transactionId);
            ps.setInt(2, productId);
            ps.setString(3, productName);
            ps.setInt(4, retailerId);
            ps.setString(5, retailerName);
            ps.setInt(6, quantity);
            ps.setString(7, status);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private StockOut mapRow(ResultSet rs) throws SQLException {
        return new StockOut(
                rs.getInt("TransactionID"),
                rs.getInt("ProductID"),
                rs.getString("ProductName"),
                rs.getInt("RetailerID"),
                rs.getString("RetailerName"),
                rs.getInt("Quantity"),
                rs.getString("Status"),
                rs.getString("Notes"),
                rs.getTimestamp("TransactionDate") != null
                        ? rs.getTimestamp("TransactionDate").toLocalDateTime()
                        : null);
    }

    private boolean isInteger(String str) {
        if (str == null || str.isEmpty()) return false;
        try { Integer.parseInt(str); return true; }
        catch (NumberFormatException e) { return false; }
    }
}