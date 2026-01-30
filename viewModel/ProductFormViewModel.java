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
        boolean Invalid = false;
        ArrayList<Product> products = productdao.getProduct();
        if (ProductName == null || ProductName.isEmpty()) {
            for (int i = 0; i < products.size(); i++) {
                String input = ProductName.trim().toLowerCase();
                String dbValue = products.get(i).getProductName().trim().toLowerCase();
                if (dbValue.equals(input)){
                    return "Product Name Already Exists !";
                }
            }
            return "Product Name is Empty !";
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
        if(Objects.equals(ImagePath, "") || ImagePath == null){
            return "Image Missing";
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


}
