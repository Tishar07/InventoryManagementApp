package viewModel;

import DAO.ProductDAO;
import model.Category;
import model.Product;
import model.Subcategory;
import model.Supplier;
import java.util.ArrayList;
import java.util.Objects;

public class ProductFormViewModel {
    private ProductDAO productdao;
    public ProductFormViewModel(ProductDAO productdao){
        this.productdao = productdao;
    }
    public Category[] FetchCategory(){
        return productdao.getCategory().toArray(new Category[0]);
    }
    public Subcategory[] FetchSubcategory(String category){
        return productdao.getSubCategory(category).toArray(new Subcategory[0]);
    }
    public Supplier[] FetchSupplier(){
        return productdao.getSupplier().toArray(new Supplier[0]);
    }
    public String save(String ProductName, Double UnitPrice,String ProductStatus,String Gender,String ImagePath,int Category,int[] Subcategory,int[] suppliers){
        ArrayList<Product> products = productdao.getProduct();

        if (ProductName == null || ProductName.trim().isEmpty()) {
            return "Product Name is Empty !";
        }
        if (!ProductName.matches("^[a-zA-Z0-9 ]+$")) {
            return "Product Name contains invalid special characters!";
        }
        String input = ProductName.trim().toLowerCase();
        for (Product p : products) {
            String dbValue = p.getProductName().trim().toLowerCase();
            if (dbValue.equals(input)) {
                return "Product Name Already Exists !";
            }
        }
        if (UnitPrice==0.0||UnitPrice<0){
            return "Invalid Price";
        }
        if(Objects.equals(ProductStatus, "") || ProductStatus == null){
            return "Product Status Empty";
        }
        if(Objects.equals(Gender, "") || Gender == null){
            return "Gender Status Empty";
        }
        if (ImagePath == null || ImagePath.trim().isEmpty()) {
            return "Image Missing";
        }
        if (!ImagePath.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            return "Invalid image format!";
        }
        if (Category==-1) {
            return "Category Missing";
        }
        if (Subcategory.length == 0) {
            return "Subcategory Missing";
        }
        if (suppliers.length == 0) {
            return "Supplier Missing";
        }


        productdao.save(ProductName,UnitPrice,ProductStatus,Gender,ImagePath,Category,Subcategory,suppliers);
        return "Successfully Saved !";

    }

    public Product FetchExistingProduct(int ProductID){
        return productdao.getExistingProduct(ProductID);
    }
    public Category FetchCategoryExistingProduct(int productID){
        return productdao.getCategoryExistingProduct(productID);
    }

    public Subcategory[] FetchSubcategory(int ProductID){
        return productdao.getSubCategoryExistingProduct(ProductID).toArray(new Subcategory[0]);
    }

    public Supplier[] FetchSupplierExistingProduct(int ProductID){
        return productdao.getSupplierExistingProduct(ProductID).toArray(new Supplier[0]);
    }

    public String update(String ProductName, Double UnitPrice,String ProductStatus,String Gender,String ImagePath,
                         int Category,int[] Subcategory,int[] suppliers,int productID,String PrevProductName){
        ArrayList<Product> products = productdao.getProduct();

        if (ProductName == null || ProductName.trim().isEmpty()) {
            return "Product Name is Empty !";
        }
        if (!ProductName.matches("^[a-zA-Z0-9 ]+$")) {
            return "Product Name contains invalid special characters!";
        }

        String input = ProductName.trim().toLowerCase();
        if(!ProductName.equals(PrevProductName)){
            for (Product p : products) {
                String dbValue = p.getProductName().trim().toLowerCase();
                if (dbValue.equals(input) ) {
                    return "Product Name Already Exists !";
                }
            }
        }

        if (UnitPrice==0.0||UnitPrice<0){
            return "Invalid Price";
        }
        if(Objects.equals(ProductStatus, "") || ProductStatus == null){
            return "Product Status Empty";
        }
        if(Objects.equals(Gender, "") || Gender == null){
            return "Gender Status Empty";
        }
        if (ImagePath == null || ImagePath.trim().isEmpty()) {
            return "Image Missing";
        }
        if (!ImagePath.matches(".*\\.(jpg|jpeg|png|gif)$")) {
            return "Invalid image format!";
        }
        if (Category==-1) {
            return "Category Missing";
        }
        if (Subcategory.length == 0) {
            return "Subcategory Missing";
        }
        if (suppliers.length == 0) {
            return "Supplier Missing";
        }


        productdao.Update(ProductName,UnitPrice,ProductStatus,Gender,ImagePath,Category,Subcategory,suppliers,productID);
        return "Successfully Updated !";

    }


}
