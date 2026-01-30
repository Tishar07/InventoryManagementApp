package model;


import java.math.BigDecimal;


public class Product {

    private int productId;
    private String productName;
    private Double unitPrice;
    private String productStatus;
    private String gender;
    private String imagePath;

    public Product(int productId, String productName, Double unitPrice, String productStatus, String gender, String imagePath) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.productStatus = productStatus;
        this.gender = gender;
        this.imagePath = imagePath;
    }



    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }

    public String getProductStatus() { return productStatus; }
    public void setProductStatus(String productStatus) { this.productStatus = productStatus; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}