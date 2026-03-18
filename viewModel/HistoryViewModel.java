package viewModel;
import DAO.HistoryDAO;
import model.StockHistory;
import java.util.ArrayList;

public class HistoryViewModel {
    private HistoryDAO historyDAO;

    public HistoryViewModel(HistoryDAO historyDAO) {
        this.historyDAO = historyDAO;
    }

    public Object[][] FetchHistory() {
        ArrayList<StockHistory> list = historyDAO.getHistory();
        return toTableData(list);
    }

    public Object[][] SearchHistory(String value) {
        ArrayList<StockHistory> list = historyDAO.searchHistory(value);
        return toTableData(list);
    }

    public Object[][] FilterByType(String type) {
        ArrayList<StockHistory> list = historyDAO.filterByType(type);
        return toTableData(list);
    }

    private Object[][] toTableData(ArrayList<StockHistory> list) {
        Object[][] data = new Object[list.size()][9];
        for (int i = 0; i < list.size(); i++) {
            StockHistory h = list.get(i);
            data[i][0] = h.getHistoryID();
            data[i][1] = h.getTransactionID();
            data[i][2] = h.getProductName();
            data[i][3] = h.getSupplierName() != null ? h.getSupplierName() : "-";
            data[i][4] = h.getRetailerName() != null ? h.getRetailerName() : "-";
            data[i][5] = h.getQuantity();
            data[i][6] = h.getTransactionType();
            data[i][7] = h.getStatus();
            data[i][8] = h.getActionDate();
        }
        return data;
    }
}