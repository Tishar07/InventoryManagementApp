package viewModel;

import DAO.StockStatusDAO;
import model.StockStatus;

import java.util.ArrayList;

public class StockStatusViewModel {
    private StockStatusDAO stockStatusDAO;

    public StockStatusViewModel(StockStatusDAO stockStatusDAO) {
        this.stockStatusDAO = stockStatusDAO;
    }

    public Object[][] FetchStockStatus() {
        return toArray(stockStatusDAO.getStockStatus());
    }

    public Object[][] SearchStockStatus(String value) {
        return toArray(stockStatusDAO.searchStockStatus(value));
    }

    private Object[][] toArray(ArrayList<StockStatus> list) {
        Object[][] data = new Object[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            StockStatus s = list.get(i);
            data[i][0] = s.getProductID();
            data[i][1] = s.getProductName();
            data[i][2] = s.getCurrentStock();
        }
        return data;
    }
}
