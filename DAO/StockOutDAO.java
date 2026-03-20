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

    // ── Fetch all RETAILER_OUT and DISPOSED transactions ─────────
    public ArrayList<StockOut> getStockOut() {
        ArrayList<StockOut> list = new ArrayList<>();
        String sql = """
                SELECT
                    st.TransactionID,
                    st.ProductID,   pr.ProductName,
                    st.RetailerID,
                    COALESCE(p.Name, '-') AS RetailerName,
                    st.Quantity,
                    st.TransactionType,
                    st.Status,
                    st.Notes,
                    st.TransactionDate
                FROM stocktransaction st
                JOIN product pr ON st.ProductID = pr.ProductID
                LEFT JOIN retailer r ON st.RetailerID = r.RetailerID
                LEFT JOIN person   p ON r.PersonID    = p.PersonID
                WHERE st.TransactionType IN ('RETAILER_OUT', 'DISPOSED')
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

    // ── Fetch single record ───────────────────────────────────────
    public StockOut getExistingStockOut(int transactionId) {
        String sql = """
                SELECT
                    st.TransactionID,
                    st.ProductID,   pr.ProductName,
                    st.RetailerID,
                    COALESCE(p.Name, '-') AS RetailerName,
                    st.Quantity,
                    st.TransactionType,
                    st.Status,
                    st.Notes,
                    st.TransactionDate
                FROM stocktransaction st
                JOIN product pr ON st.ProductID = pr.ProductID
                LEFT JOIN retailer r ON st.RetailerID = r.RetailerID
                LEFT JOIN person   p ON r.PersonID    = p.PersonID
                WHERE st.TransactionID = ?
                  AND st.TransactionType IN ('RETAILER_OUT', 'DISPOSED')
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

    // ── Save ─────────────────────────────────────────────────────
    // retailerId = 0 when type is DISPOSED
    public void save(int retailerId, int productId, int quantity,
                     String transactionType, String status, String notes) {

        // 1. Insert into stocktransaction
        int transactionId = 0;
        String insertTx = """
                INSERT INTO stocktransaction
                    (ProductID, RetailerID, Quantity, TransactionType, Status, Notes)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps =
                     conn.prepareStatement(insertTx, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, productId);
            if (retailerId > 0) ps.setInt(2, retailerId);
            else                ps.setNull(2, Types.INTEGER);
            ps.setInt(3, quantity);
            ps.setString(4, transactionType);
            ps.setString(5, status);
            ps.setString(6, notes);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) transactionId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // 2. Deduct from Stock table only when Completed
        if (status.equals("Completed")) {
            deductStock(productId, quantity);
        }

        // 3. Log — null previousStatus = new record
        logHistory(transactionId, productId, retailerId,
                quantity, transactionType, status, null);
    }

    // ── Update ───────────────────────────────────────────────────
    public void update(int transactionId, int retailerId, int productId,
                       int quantity, String transactionType,
                       String status, String notes) {

        // 1. Fetch old values before update
        int    oldQty       = 0;
        int    oldProductId = 0;
        String oldStatus    = "";

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
                SET RetailerID = ?, ProductID = ?, Quantity = ?,
                    TransactionType = ?, Status = ?, Notes = ?
                WHERE TransactionID = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(updateTx)) {
            if (retailerId > 0) ps.setInt(1, retailerId);
            else                ps.setNull(1, Types.INTEGER);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setString(4, transactionType);
            ps.setString(5, status);
            ps.setString(6, notes);
            ps.setInt(7, transactionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // 3. Adjust Stock table based on old vs new status
        if (oldStatus.equals("Completed")) restoreStock(oldProductId, oldQty);
        if (status.equals("Completed"))    deductStock(productId, quantity);

        // 4. Log with oldStatus so history shows exactly what changed
        logHistory(transactionId, productId, retailerId,
                quantity, transactionType, status, oldStatus);
    }

    // ── Cancel (soft delete) ─────────────────────────────────────
    public void cancel(int transactionId) {
        int    qty             = 0;
        int    productId       = 0;
        int    retailerId      = 0;
        String currentStatus   = "";
        String transactionType = "";

        String fetchSql = """
                SELECT Quantity, ProductID, RetailerID, Status, TransactionType
                FROM stocktransaction
                WHERE TransactionID = ?
                """;
        try (PreparedStatement ps = conn.prepareStatement(fetchSql)) {
            ps.setInt(1, transactionId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                qty             = rs.getInt("Quantity");
                productId       = rs.getInt("ProductID");
                currentStatus   = rs.getString("Status");
                transactionType = rs.getString("TransactionType");

                // Safely handle NULL RetailerID for DISPOSED
                retailerId = rs.getInt("RetailerID");
                if (rs.wasNull()) retailerId = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Restore stock only if it was previously Completed
        if (currentStatus.equals("Completed")) restoreStock(productId, qty);

        // Soft cancel — update status only, row stays in DB
        String sql = "UPDATE stocktransaction SET Status = 'Cancelled' WHERE TransactionID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, transactionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        // Log the cancellation with previous status
        logHistory(transactionId, productId, retailerId,
                qty, transactionType, "Cancelled", currentStatus);
    }

    // ── Search ───────────────────────────────────────────────────
    public ArrayList<StockOut> searchStockOut(String value) {
        ArrayList<StockOut> list = new ArrayList<>();
        String sql = """
                SELECT
                    st.TransactionID,
                    st.ProductID,   pr.ProductName,
                    st.RetailerID,
                    COALESCE(p.Name, '-') AS RetailerName,
                    st.Quantity,
                    st.TransactionType,
                    st.Status,
                    st.Notes,
                    st.TransactionDate
                FROM stocktransaction st
                JOIN product pr ON st.ProductID = pr.ProductID
                LEFT JOIN retailer r ON st.RetailerID = r.RetailerID
                LEFT JOIN person   p ON r.PersonID    = p.PersonID
                WHERE st.TransactionType IN ('RETAILER_OUT', 'DISPOSED')
                AND (
                      st.TransactionID = ?
                   OR pr.ProductName        LIKE CONCAT('%', ?, '%')
                   OR COALESCE(p.Name, '')  LIKE CONCAT('%', ?, '%')
                   OR st.Status             LIKE CONCAT('%', ?, '%')
                   OR st.TransactionType    LIKE CONCAT('%', ?, '%')
                )
                ORDER BY st.TransactionID DESC
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (isInteger(value)) ps.setInt(1, Integer.parseInt(value));
            else                  ps.setNull(1, Types.INTEGER);
            ps.setString(2, value);
            ps.setString(3, value);
            ps.setString(4, value);
            ps.setString(5, value);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ── Dropdowns ────────────────────────────────────────────────
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

    // ── Private helpers ───────────────────────────────────────────

    // Deduct from Stock table (correct table for your DB)
    private void deductStock(int productId, int quantity) {
        String sql = """
                UPDATE stock
                SET QuantityAvailable = QuantityAvailable - ?,
                    LastUpdatedDate   = CURDATE()
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

    // Restore to Stock table (correct table for your DB)
    private void restoreStock(int productId, int quantity) {
        String sql = """
                UPDATE stock
                SET QuantityAvailable = QuantityAvailable + ?,
                    LastUpdatedDate   = CURDATE()
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

    // ── logHistory ────────────────────────────────────────────────
    // previousStatus = null  → new record being created
    // previousStatus = value → existing record being updated
    private void logHistory(int transactionId, int productId,
                            int retailerId,   int quantity,
                            String transactionType, String newStatus,
                            String previousStatus) {

        // Resolve product name
        String productName = "";
        try (PreparedStatement ps = conn.prepareStatement(
                "SELECT ProductName FROM product WHERE ProductID = ?")) {
            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) productName = rs.getString("ProductName");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Resolve retailer name — only for RETAILER_OUT
        String retailerName = null;
        if (retailerId > 0) {
            try (PreparedStatement ps = conn.prepareStatement("""
                    SELECT p.Name FROM person p
                    JOIN retailer r ON r.PersonID = p.PersonID
                    WHERE r.RetailerID = ?
                    """)) {
                ps.setInt(1, retailerId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) retailerName = rs.getString("Name");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // Human-readable reason showing what changed
        String reason;
        if (previousStatus == null) {
            reason = "Created with status: " + newStatus;
        } else if (!previousStatus.equals(newStatus)) {
            reason = "Status changed from " + previousStatus + " to " + newStatus;
        } else {
            reason = "Record updated — status unchanged: " + newStatus;
        }

        String insertHistory = """
                INSERT INTO stockhistory
                    (TransactionID, ProductID, ProductName,
                     SupplierID,    SupplierName,
                     RetailerID,    RetailerName,
                     Quantity,      TransactionType, Status)
                VALUES (?, ?, ?, NULL, NULL, ?, ?, ?, ?, ?)
                """;
        try (PreparedStatement ps = conn.prepareStatement(insertHistory)) {
            ps.setInt(1, transactionId);
            ps.setInt(2, productId);
            ps.setString(3, productName);
            if (retailerId > 0) {
                ps.setInt(4, retailerId);
                ps.setString(5, retailerName);
            } else {
                // DISPOSED — no retailer
                ps.setNull(4, Types.INTEGER);
                ps.setNull(5, Types.VARCHAR);
            }
            ps.setInt(6, quantity);
            ps.setString(7, transactionType);
            ps.setString(8, newStatus);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private StockOut mapRow(ResultSet rs) throws SQLException {
        int retailerId = rs.getInt("RetailerID");
        if (rs.wasNull()) retailerId = 0;
        return new StockOut(
                rs.getInt("TransactionID"),
                rs.getInt("ProductID"),
                rs.getString("ProductName"),
                retailerId,
                rs.getString("RetailerName"),
                rs.getInt("Quantity"),
                rs.getString("TransactionType"),
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