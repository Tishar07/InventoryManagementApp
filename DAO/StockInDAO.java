package DAO;


import model.Product;
import model.StockIn;
import model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class StockInDAO {
    private Connection conn;
    public StockInDAO(Connection conn){
        this.conn=conn;
    }

    public ArrayList<StockIn>getStockIn(){
        ArrayList<StockIn> stocks = new ArrayList<>();

        String sql = """
                SELECT x.*, p.Name,v.ProductName
                FROM stocktransaction x
                INNER JOIN supplier y ON x.SupplierID = y.SupplierID
                INNER JOIN person p ON p.PersonID = y.PersonID
                INNER JOIN product v ON v.ProductID = x.ProductID
                WHERE x.TransactionType = 'SUPPLIER_IN';
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                StockIn s = new StockIn(
                        rs.getInt("TransactionID"),
                        rs.getInt("ProductID"),
                        rs.getInt("SupplierID"),
                        rs.getString("ProductName"),
                        rs.getString("Name"),
                        rs.getInt("Quantity"),
                        rs.getString("Status"),
                        rs.getTimestamp("TransactionDate").toLocalDateTime(),
                        rs.getString("Notes")
                        );
                stocks.add(s);

            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return stocks;

    }

    public ArrayList<StockIn> searchStockIn(String value) {
        ArrayList<StockIn> stockInList = new ArrayList<>();

        String sql = """
                    SELECT x.*, p.Name, v.ProductName
                    FROM stocktransaction x
                    INNER JOIN supplier y ON x.SupplierID = y.SupplierID
                    INNER JOIN person p ON p.PersonID = y.PersonID
                    INNER JOIN product v ON v.ProductID = x.ProductID
                    WHERE x.TransactionType = 'SUPPLIER_IN'
                    AND (
                           x.TransactionID = ?
                        OR x.Quantity = ?
                        OR p.Name LIKE CONCAT('%', ?, '%')
                        OR v.ProductName LIKE CONCAT('%', ?, '%')
                        OR x.Status LIKE CONCAT('%', ?, '%')
                        OR x.Notes LIKE CONCAT('%', ?, '%')
                        OR DATE_FORMAT(x.TransactionDate, '%Y-%m-%d') LIKE CONCAT('%', ?, '%')
                    );
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            if (isInteger(value)) {
                ps.setInt(1, Integer.parseInt(value));
                ps.setInt(2, Integer.parseInt(value));
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            ps.setString(3, value);
            ps.setString(4, value);
            ps.setString(5, value);
            ps.setString(6, value);
            ps.setString(7, value);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StockIn s = new StockIn(
                        rs.getInt("TransactionID"),
                        rs.getInt("ProductID"),
                        rs.getInt("SupplierID"),
                        rs.getString("ProductName"),
                        rs.getString("Name"),
                        rs.getInt("Quantity"),
                        rs.getString("Status"),
                        rs.getTimestamp("TransactionDate").toLocalDateTime(),
                        rs.getString("Notes")
                );
                stockInList.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stockInList;
    }

    public boolean CancelOrder(int TransactionID){
        boolean response = false;

        String sql= """
                    SELECT TransactionID
                    FROM stocktransaction
                    WHERE TransactionID = ?
                    AND Status NOT IN ('Cancelled', 'Completed');
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,TransactionID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                response = true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        if(response){
            sql= """
                UPDATE stocktransaction
                SET Status = 'Cancelled'
                WHERE TransactionID = ?
                """;
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1,TransactionID);
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        return response;
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

    public ArrayList<Supplier>getSuppliers(){
        ArrayList<Supplier> supplierArrayList = new ArrayList<>();
        String sql= """
                SELECT x.SupplierID,p.*
                FROM supplier x INNER JOIN person p ON x.PersonID = p.PersonID
                WHERE p.Status ='Active';
                """;
        try (PreparedStatement ps =conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Supplier s = new Supplier(
                        rs.getInt("SupplierID"),
                        rs.getString("Name"),
                        rs.getString("Address"),
                        rs.getString("Email"),
                        rs.getString("Contact"),
                        rs.getString("Status"));
                supplierArrayList.add(s);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return supplierArrayList;
    }

    public ArrayList<Product>getProducts(int SupplierID){
        ArrayList<Product> productArrayList= new ArrayList<>();
        String sql = """
                SELECT p.*
                FROM supplier_product x INNER JOIN product p ON x.ProductID = p.ProductID
                WHERE x.SupplierID = ?
                """;

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,SupplierID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Product p = new Product(
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("UnitPrice"),
                        rs.getString("ProductStatus"),
                        rs.getString("Gender"),
                        rs.getString("ImagePath")
                        );
                productArrayList.add(p);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return productArrayList;

    }

    public void save(StockIn stockIn){
        int transactionID=-1;
        String sql= """
                INSERT INTO stocktransaction(ProductID,SupplierID,Quantity,TransactionType,Status,TransactionDate,Notes)
                VALUES(?,?,?,?,?,?,?)
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            ps.setInt(1,stockIn.getProductID());
            ps.setInt(2,stockIn.getSupplierID());
            ps.setInt(3,stockIn.getQuantity());
            ps.setString(4,stockIn.getTransactionType());
            ps.setString(5,stockIn.getStatus());
            ps.setTimestamp(6, Timestamp.valueOf(stockIn.getTransactionDate()));
            ps.setString(7,stockIn.getNotes());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                transactionID=rs.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        sql = """
                INSERT INTO stockhistory(TransactionID,ProductID,ProductName,SupplierID,SupplierName,Quantity,TransactionType,Status,ActionDate)
                VALUES(?,?,?,?,?,?,?,?,?)
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,transactionID);
            ps.setInt(2,stockIn.getProductID());
            ps.setString(3,stockIn.getProductName());
            ps.setInt(4,stockIn.getSupplierID());
            ps.setString(5,stockIn.getSupplierName());
            ps.setInt(6,stockIn.getQuantity());
            ps.setString(7,stockIn.getTransactionType());
            ps.setString(8,stockIn.getStatus());
            ps.setTimestamp(9, Timestamp.valueOf(stockIn.getTransactionDate()));
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }

        if(Objects.equals(stockIn.getStatus(), "Completed")){
            sql= """
                    SELECT ProductID
                    FROM stockstatus
                    WHERE ProductID = ?
                    """;
            boolean Exists= false;
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,stockIn.getProductID());
                ResultSet rs= ps.executeQuery();
                if(rs.next()){
                    Exists=true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            if(Exists==false){

                sql = """
                        INSERT INTO stockstatus (ProductID,CurrentStock)
                        VALUES(?,?)
                        """;
                try (PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setInt(1,stockIn.getProductID());
                    ps.setInt(2,stockIn.getQuantity());
                    ps.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                sql= """
                        UPDATE stockstatus
                        SET CurrentStock = CurrentStock + ?
                        WHERE ProductID = ?;
                        """;
                try (PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setInt(1,stockIn.getQuantity());
                    ps.setInt(2,stockIn.getProductID());
                    ps.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void update(StockIn stockIn){
        String sql= """
                UPDATE stocktransaction
                SET ProductID = ?,
                    SupplierID = ?,
                    Quantity = ?,
                    TransactionType = ?,
                    Status = ?,
                    TransactionDate = ?,
                    Notes = ?
                WHERE TransactionID = ?;
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,stockIn.getProductID());
            ps.setInt(2,stockIn.getSupplierID());
            ps.setInt(3,stockIn.getQuantity());
            ps.setString(4,stockIn.getTransactionType());
            ps.setString(5,stockIn.getStatus());
            ps.setTimestamp(6, Timestamp.valueOf(stockIn.getTransactionDate()));
            ps.setString(7,stockIn.getNotes());
            ps.setInt(8, stockIn.getTransactionID());
            ps.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
        sql = """
            INSERT INTO stockhistory
            (TransactionID,ProductID, ProductName, SupplierID, SupplierName, Quantity, TransactionType, Status, ActionDate)
            VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, stockIn.getTransactionID());
            ps.setInt(2, stockIn.getProductID());
            ps.setString(3, stockIn.getProductName());
            ps.setInt(4, stockIn.getSupplierID());
            ps.setString(5, stockIn.getSupplierName());
            ps.setInt(6, stockIn.getQuantity());
            ps.setString(7, stockIn.getTransactionType());
            ps.setString(8, stockIn.getStatus());
            ps.setTimestamp(9, Timestamp.valueOf(stockIn.getTransactionDate()));

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(Objects.equals(stockIn.getStatus(), "Completed")){
            sql= """
                    SELECT ProductID
                    FROM stockstatus
                    WHERE ProductID = ?
                    """;
            boolean Exists= false;
            try(PreparedStatement ps = conn.prepareStatement(sql)){
                ps.setInt(1,stockIn.getProductID());
                ResultSet rs= ps.executeQuery();
                if(rs.next()){
                    Exists=true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
            if(!Exists){
                sql = """
                        INSERT INTO stockstatus (ProductID,CurrentStock)
                        VALUES(?,?)
                        """;
                try (PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setInt(1,stockIn.getProductID());
                    ps.setInt(2,stockIn.getQuantity());
                    ps.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }else{
                sql= """
                        UPDATE stockstatus
                        SET CurrentStock = CurrentStock + ?
                        WHERE ProductID = ?;
                        """;
                try (PreparedStatement ps = conn.prepareStatement(sql)){
                    ps.setInt(1,stockIn.getQuantity());
                    ps.setInt(2,stockIn.getProductID());
                    ps.executeUpdate();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public StockIn getExistingStockIn(int transactionID){
        StockIn s=null;
        String  sql ="""
                SELECT x.*, p.Name,v.ProductName
                FROM stocktransaction x
                INNER JOIN supplier y ON x.SupplierID = y.SupplierID
                INNER JOIN person p ON p.PersonID = y.PersonID
                INNER JOIN product v ON v.ProductID = x.ProductID
                WHERE x.TransactionType = 'SUPPLIER_IN' AND x.TransactionID =?;
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1,transactionID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                s = new StockIn(
                        rs.getInt("TransactionID"),
                        rs.getInt("ProductID"),
                        rs.getInt("SupplierID"),
                        rs.getString("ProductName"),
                        rs.getString("Name"),
                        rs.getInt("Quantity"),
                        rs.getString("Status"),
                        rs.getTimestamp("TransactionDate").toLocalDateTime(),
                        rs.getString("Notes")
                );
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return s;
    }




}
