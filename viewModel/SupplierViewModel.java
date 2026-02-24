package viewModel;

import DAO.SupplierDAO;
import model.Supplier;

import java.util.ArrayList;

public class SupplierViewModel {
    private SupplierDAO supplierDAO;

    public SupplierViewModel(SupplierDAO supplierDAO){
        this.supplierDAO = supplierDAO;
    }

    public Object[][] FetchSuppliers() {
        ArrayList<Supplier> supplierList = supplierDAO.getSupplier();
        Object[][] arraySupplier = new Object[supplierList.size()][6];
        for (int i = 0; i < supplierList.size(); i++) {
            Supplier s = supplierList.get(i);
            arraySupplier[i][0] = s.getSupplierId();
            arraySupplier[i][1] = s.getName();
            arraySupplier[i][2] = s.getEmail();
            arraySupplier[i][3] = s.getContact();
            arraySupplier[i][4] = s.getStatus();
        }
        return arraySupplier;
    }

    public String Delete(int SupplierID){
        supplierDAO.delete(SupplierID);
        return "Successfully Deleted";
    }

    public Object[][] SearchSuppliers(String value){
        ArrayList<Supplier> supplierList = supplierDAO.searchSupplier(value);
        Object[][] arraySupplier = new Object[supplierList.size()][6];
        for (int i = 0; i < supplierList.size(); i++) {
            Supplier s = supplierList.get(i);
            arraySupplier[i][0] = s.getSupplierId();
            arraySupplier[i][1] = s.getName();
            arraySupplier[i][2] = s.getEmail();
            arraySupplier[i][3] = s.getContact();
            arraySupplier[i][4] = s.getStatus();
        }
        return arraySupplier;
    }
}