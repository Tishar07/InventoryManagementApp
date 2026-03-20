package model;

import java.time.LocalDateTime;

public class StockIn {

    private int TransactionID;
    private int ProductID;
    private int SupplierID;
    private String ProductName;
    private String SupplierName;
    private int Quantity;
    private String TransactionType = "SUPPLIER_IN";
    private String Status;
    private LocalDateTime TransactionDate;
    private String Notes;

    public StockIn(int transactionID, int productID, int supplierID,String ProductName,String SupplierName ,int quantity, String status, LocalDateTime transactionDate, String notes) {
        this.TransactionID = transactionID;
        this.ProductID = productID;
        this.SupplierID = supplierID;
        this.ProductName= ProductName;
        this.SupplierName =SupplierName;
        this.Quantity = quantity;
        this.Status = status;
        this.TransactionDate = LocalDateTime.now();
        this.Notes = notes;
        this.TransactionType = "SUPPLIER_IN";
    }

    // Getters

    public int getTransactionID() {
        return TransactionID;
    }

    public int getProductID() {
        return ProductID;
    }

    public int getSupplierID() {
        return SupplierID;
    }

    public String getProductName() {
        return ProductName;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public int getQuantity() {
        return Quantity;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public String getStatus() {
        return Status;
    }

    public LocalDateTime getTransactionDate() {
        return TransactionDate;
    }

    public String getNotes() {
        return Notes;
    }

    // Setters

    public void setTransactionID(int transactionID) {
        this.TransactionID = transactionID;
    }

    public void setProductID(int productID) {
        this.ProductID = productID;
    }

    public void setSupplierID(int supplierID) {
        this.SupplierID = supplierID;
    }

    public void setProductName(String productName) {
        this.ProductName = productName;
    }

    public void setSupplierName(String supplierName) {
        this.SupplierName = supplierName;
    }

    public void setQuantity(int quantity) {
        this.Quantity = quantity;
    }

    public void setTransactionType(String transactionType) {
        this.TransactionType = transactionType;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.TransactionDate = transactionDate;
    }

    public void setNotes(String notes) {
        this.Notes = notes;
    }
}
