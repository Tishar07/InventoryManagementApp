package model;

import java.time.LocalDateTime;

public class StockOut {
    private int transactionId;
    private int productId;
    private String productName;
    private int retailerId;
    private String retailerName;
    private int quantity;
    private String transactionType; // RETAILER_OUT or DISPOSED
    private String status;          // Waiting | Completed | Cancelled
    private String notes;
    private LocalDateTime transactionDate;

    public StockOut(int transactionId, int productId, String productName,
                    int retailerId, String retailerName,
                    int quantity, String transactionType,
                    String status, String notes,
                    LocalDateTime transactionDate) {
        this.transactionId   = transactionId;
        this.productId       = productId;
        this.productName     = productName;
        this.retailerId      = retailerId;
        this.retailerName    = retailerName;
        this.quantity        = quantity;
        this.transactionType = transactionType;
        this.status          = status;
        this.notes           = notes;
        this.transactionDate = transactionDate;
    }

    public int           getTransactionId()    { return transactionId; }
    public int           getProductId()        { return productId; }
    public String        getProductName()      { return productName; }
    public int           getRetailerId()       { return retailerId; }
    public String        getRetailerName()     { return retailerName; }
    public int           getQuantity()         { return quantity; }
    public String        getTransactionType()  { return transactionType; }
    public String        getStatus()           { return status; }
    public String        getNotes()            { return notes; }
    public LocalDateTime getTransactionDate()  { return transactionDate; }
}