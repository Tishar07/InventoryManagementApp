package model;

public class StockHistory {

    private int historyID;
    private int transactionID;
    private int productID;
    private String productName;
    private int supplierID;
    private String supplierName;
    private int retailerID;
    private String retailerName;
    private int quantity;
    private String transactionType;
    private String status;
    private String actionDate;

    public StockHistory(int historyID, int transactionID, int productID, String productName,
                        int supplierID, String supplierName, int retailerID, String retailerName,
                        int quantity, String transactionType, String status, String actionDate) {
        this.historyID = historyID;
        this.transactionID = transactionID;
        this.productID = productID;
        this.productName = productName;
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.retailerID = retailerID;
        this.retailerName = retailerName;
        this.quantity = quantity;
        this.transactionType = transactionType;
        this.status = status;
        this.actionDate = actionDate;
    }

    public int getHistoryID() { return historyID; }
    public int getTransactionID() { return transactionID; }
    public int getProductID() { return productID; }
    public String getProductName() { return productName; }
    public int getSupplierID() { return supplierID; }
    public String getSupplierName() { return supplierName; }
    public int getRetailerID() { return retailerID; }
    public String getRetailerName() { return retailerName; }
    public int getQuantity() { return quantity; }
    public String getTransactionType() { return transactionType; }
    public String getStatus() { return status; }
    public String getActionDate() { return actionDate; }
}