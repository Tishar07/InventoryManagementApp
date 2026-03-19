// viewModel/StockOutViewModel.java
package viewModel;

import DAO.StockOutDAO;
import model.StockOut;
import java.util.ArrayList;

public class StockOutViewModel {
    private StockOutDAO stockOutDAO;

    public StockOutViewModel(StockOutDAO stockOutDAO) {
        this.stockOutDAO = stockOutDAO;
    }

    public Object[][] FetchStockOuts() {
        return toArray(stockOutDAO.getStockOut());
    }

    public Object[][] SearchStockOuts(String value) {
        return toArray(stockOutDAO.searchStockOut(value));
    }

    public String Cancel(int transactionId) {
        stockOutDAO.cancel(transactionId);
        return "Successfully Cancelled";
    }

    private Object[][] toArray(ArrayList<StockOut> list) {
        Object[][] data = new Object[list.size()][7];
        for (int i = 0; i < list.size(); i++) {
            StockOut s = list.get(i);
            data[i][0] = s.getTransactionId();
            data[i][1] = s.getRetailerName();
            data[i][2] = s.getProductName();
            data[i][3] = s.getQuantity();
            data[i][4] = s.getStatus();
            data[i][5] = s.getNotes();
            data[i][6] = s.getTransactionDate();
        }
        return data;
    }
}