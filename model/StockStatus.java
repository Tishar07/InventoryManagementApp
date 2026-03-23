package model;

public class StockStatus {
    private int productID;
    private String productName;
    private int currentStock;

    public StockStatus(int productID, String productName, int currentStock) {
        this.productID = productID;
        this.productName = productName;
        this.currentStock = currentStock;
    }

    public int getProductID()       { return productID; }
    public String getProductName()  { return productName; }
    public int getCurrentStock()    { return currentStock; }
}

