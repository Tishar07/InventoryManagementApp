package viewModel;

import DAO.StockInDAO;
import model.Retailer;
import model.StockIn;

import java.util.ArrayList;

public class StockInViewModel {
    private StockInDAO stockInDAO;

    public StockInViewModel(StockInDAO stockInDAO){
        this.stockInDAO=stockInDAO;
    }

    public Object[][] FetchStockIn() {
        ArrayList<StockIn> stockInList = stockInDAO.getStockIn();
        Object[][] arrayStockIn = new Object[stockInList.size()][7];
        for(int i = 0; i < stockInList.size(); i++){
            StockIn s = stockInList.get(i);
            arrayStockIn[i][0] = s.getTransactionID();
            arrayStockIn[i][1] = s.getProductName();
            arrayStockIn[i][2] = s.getSupplierName();
            arrayStockIn[i][3] = s.getQuantity();
            arrayStockIn[i][4] = s.getStatus();
            arrayStockIn[i][5] = s.getTransactionDate();
        }
        return  arrayStockIn;
    }

    public Object[][] SearchStockIn(String value){
        ArrayList<StockIn> stockInList = stockInDAO.searchStockIn(value);
        Object[][] arrayStockIn = new Object[stockInList.size()][7];
        for (int i = 0; i < stockInList.size(); i++) {
            StockIn s = stockInList.get(i);
            arrayStockIn[i][0] = s.getTransactionID();
            arrayStockIn[i][1] = s.getProductName();
            arrayStockIn[i][2] = s.getSupplierName();
            arrayStockIn[i][3] = s.getQuantity();
            arrayStockIn[i][4] = s.getStatus();
            arrayStockIn[i][5] = s.getTransactionDate();
        }
        return arrayStockIn;
    };

    public boolean CancelOrder(int TransactionID){
        return stockInDAO.CancelOrder(TransactionID);
    }


}
