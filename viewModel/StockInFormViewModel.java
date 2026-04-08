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
        String Error = validate(supplierID,productID,quantity,status);
        if(Error!=null){
            return Error;
        }
        StockIn s = new StockIn(-1,productID,supplierID,ProductName,SupplierName,
                quantity,status,transactionDate,notes);
        stockInDAO.save(s);
        return "Stock In order Saved !" ;
    }

    public String update(int transactionID, int productID, int supplierID,String ProductName,String SupplierName ,
                         int quantity, String status, LocalDateTime transactionDate, String notes,String prevStatus){
        String Error = validate(supplierID,productID,quantity,status);
        if(Error!=null){
            return Error;
        }
        StockIn s = new StockIn(transactionID,productID,supplierID,ProductName,SupplierName,
                quantity,status,transactionDate,notes);
        if(Objects.equals(prevStatus, "Completed") && Objects.equals(status, "Completed") ){
            return "A Completed stock out cannot be changed";
        }

        if(Objects.equals(prevStatus, "Cancelled") && Objects.equals(status, "Cancelled")){
            return "A Cancelled stock out cannot be changed";
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


    private String validate(int SupplierID, int productId, int quantity, String status) {

        if (productId <= 0) {
            return "Please select a Product";
        }

        if (quantity <= 0){
            return "Quantity must be greater than 0";
        }

        if (status == null || (!status.equals("Waiting") && !status.equals("Completed") &&
                                !status.equals("Cancelled"))){
            return "Status must be Waiting, Completed or Cancelled";
        }

        return null;
    }

}
