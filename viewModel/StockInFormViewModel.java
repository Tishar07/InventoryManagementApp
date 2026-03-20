package viewModel;

import DAO.StockInDAO;
import model.Product;
import model.StockIn;
import model.Supplier;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class StockInFormViewModel {
    private StockInDAO stockInDAO;
    public StockInFormViewModel(StockInDAO stockInDAO){
        this.stockInDAO=stockInDAO;
    }
    public ArrayList<Supplier>fetchSupplier(){
        return stockInDAO.getSuppliers();
    }
    public ArrayList<Product>fetchProduct(int SupplierID){
        return stockInDAO.getProducts(SupplierID);
    }
    public String save(int productID, int supplierID, String ProductName, String SupplierName ,
                       int quantity, String status, LocalDateTime transactionDate, String notes){
        StockIn s = new StockIn(-1,productID,supplierID,ProductName,SupplierName,
                quantity,status,transactionDate,notes);
        stockInDAO.save(s);
        return "Stock In order Saved !" ;
    }

    public String update(int transactionID, int productID, int supplierID,String ProductName,String SupplierName ,
                         int quantity, String status, LocalDateTime transactionDate, String notes,String prevStatus){
        StockIn s = new StockIn(transactionID,productID,supplierID,ProductName,SupplierName,
                quantity,status,transactionDate,notes);
        if(Objects.equals(prevStatus, "Completed") && Objects.equals(status, "Completed") ){
            return "Already in Completed";
        }

        if((Objects.equals(prevStatus, "Waiting") && Objects.equals(status, "Waiting"))){
            return "Already in Waiting";
        }

        if(Objects.equals(prevStatus, "Cancelled") && Objects.equals(status, "Cancelled")){
            return "Already in Cancelled";
        }

        if((Objects.equals(prevStatus, "Cancelled") && Objects.equals(status, "Completed"))||
                (Objects.equals(prevStatus, "Cancelled") && Objects.equals(status, "Waiting"))){
            return "Cannot move from Cancelled";
        }

        if((Objects.equals(prevStatus, "Completed") && Objects.equals(status, "Waiting")) ||
                Objects.equals(prevStatus, "Cancelled") && Objects.equals(status, "Waiting")){
            return "Cannot Move order to Waiting";
        }

        if(Objects.equals(prevStatus, "Completed") && Objects.equals(status, "Cancelled")){
            return "Cannot cancel a Completed Order";
        }

        stockInDAO.update(s);
        return "Stock In order Updated !";
    }

    public StockIn FetchExistingStockIn(int transactionID){
        return stockInDAO.getExistingStockIn(transactionID);
    }

}
