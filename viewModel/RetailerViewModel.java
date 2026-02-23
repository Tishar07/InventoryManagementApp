package viewModel;

import DAO.RetailerDAO;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import model.Retailer;

import java.util.ArrayList;

public class RetailerViewModel {
    private RetailerDAO retailerDAO;
    public RetailerViewModel(RetailerDAO retailerDAO){
        this.retailerDAO= retailerDAO;
    }

    public Object[][] FetchRetailers() {
        ArrayList<Retailer> retailerList = retailerDAO.getRetailer();
        Object[][] arrayRetailer = new Object[retailerList.size()][6];
        for (int i = 0; i < retailerList.size(); i++) {
            Retailer r = retailerList.get(i);
            arrayRetailer[i][0] = r.getRetailerid();
            arrayRetailer[i][1] = r.getName();
            arrayRetailer[i][2] = r.getEmail();
            arrayRetailer[i][3] = r.getContact();
            arrayRetailer[i][4] = r.getStatus();
        }
        return arrayRetailer;
    }

    public String Delete(int RetailerID){
        retailerDAO.delete(RetailerID);
        return "Successfully Deleted";
    }

    public Object[][] SearchRetailers(String value){
        ArrayList<Retailer> retailerList = retailerDAO.searchRetailer(value);
        Object[][] arrayRetailer = new Object[retailerList.size()][6];
        for (int i = 0; i < retailerList.size(); i++) {
            Retailer r = retailerList.get(i);
            arrayRetailer[i][0] = r.getRetailerid();
            arrayRetailer[i][1] = r.getName();
            arrayRetailer[i][2] = r.getEmail();
            arrayRetailer[i][3] = r.getContact();
            arrayRetailer[i][4] = r.getStatus();
        }
        return arrayRetailer;
    };
}
