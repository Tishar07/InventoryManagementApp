package viewModel;

import DAO.ProductDAO;
import model.Product;

import java.util.*;

public class ProductViewModel {
    private ProductDAO productDAO;
    public ProductViewModel(ProductDAO productdao){
        this.productDAO = productdao;
    }

    public List<Product> sortProducts(int sort, List<Product> products) {
        if (products == null || products.isEmpty()) {
            return Collections.emptyList();
        }

        List<Product> sortedProducts = new ArrayList<>(products);

        switch (sort) {
            case 0:
                sortedProducts.sort(Comparator.comparingInt(Product::getProductId));
                break;

            case 1:
                sortedProducts.sort(Comparator.comparing(Product::getProductName, String.CASE_INSENSITIVE_ORDER));
                break;

            case 2:
                sortedProducts.sort(Comparator.comparingDouble(Product::getUnitPrice));
                break;
            default:
                break;
        }

        return sortedProducts;
    }

    public List <Product> searchProducts(String keyword){
        List<Product> SeachedProducts = new ArrayList<>();
        if(!Objects.equals(keyword, "")){
            SeachedProducts = productDAO.SearchProduct(keyword);
        }
        return SeachedProducts;
    }

    public List <Product> getProducts(){
        List<Product> Products = new ArrayList<>();
        Products=productDAO.getProduct();
        return Products;
    }

    public String deleteProduct(int ProductID){
        productDAO.delete(ProductID);
        return "Deleted Successfully";
    }


}
