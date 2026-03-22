package viewModel;

import DAO.StockOutDAO;
import model.Product;
import model.Retailer;
import model.StockOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StockOutFormViewModel {
    private StockOutDAO stockOutDAO;

    public StockOutFormViewModel(StockOutDAO stockOutDAO) {
        this.stockOutDAO = stockOutDAO;
    }

    public Object[][] FetchRetailers() {
        ArrayList<Retailer> list = stockOutDAO.getRetailers();
        Object[][] data = new Object[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getRetailerid();
            data[i][1] = list.get(i).getName();
        }
        return data;
    }

    public Object[][] FetchProducts() {
        ArrayList<Product> list = stockOutDAO.getProducts();
        Object[][] data = new Object[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getProductId();
            data[i][1] = list.get(i).getProductName();
        }
        return data;
    }

    // ── Save — retailerId = 0 for DISPOSED ───────────────────────
    public String save(int retailerId, int productId, String quantityStr,
                       String transactionType, String status, String notes) {

        String err = validate(retailerId, productId, quantityStr,
                transactionType, status);
        if (err != null) return err;

        stockOutDAO.save(retailerId, productId,
                Integer.parseInt(quantityStr.trim()),
                transactionType, status,
                notes == null ? "" : notes.trim());
        return "Saved Successfully";
    }

    // ── Update ───────────────────────────────────────────────────
    public String update(int transactionId, int retailerId, int productId,
                         String quantityStr, String transactionType,
                         String status, String notes) {

        StockOut existing = stockOutDAO.getExistingStockOut(transactionId);
        if (existing == null) return "Record not found";

        String transitionError = validateStatusTransition(
                existing.getStatus(), status);
        if (transitionError != null) return transitionError;

        String err = validate(retailerId, productId, quantityStr,
                transactionType, status);
        if (err != null) return err;

        stockOutDAO.update(transactionId, retailerId, productId,
                Integer.parseInt(quantityStr.trim()),
                transactionType, status,
                notes == null ? "" : notes.trim());
        return "Updated Successfully";
    }

    public Map<String, String> FetchExistingStockOut(int transactionId) {
        StockOut s = stockOutDAO.getExistingStockOut(transactionId);
        Map<String, String> data = new HashMap<>();
        if (s == null) return data;
        data.put("transactionId",   String.valueOf(s.getTransactionId()));
        data.put("retailerId",      String.valueOf(s.getRetailerId()));
        data.put("retailerName",    s.getRetailerName());
        data.put("productId",       String.valueOf(s.getProductId()));
        data.put("productName",     s.getProductName());
        data.put("quantity",        String.valueOf(s.getQuantity()));
        data.put("transactionType", s.getTransactionType());
        data.put("status",          s.getStatus());
        data.put("notes",           s.getNotes() != null ? s.getNotes() : "");
        return data;
    }

    // ── Status transition rules ───────────────────────────────────
    private String validateStatusTransition(String current, String next) {
        switch (current) {
            case "Completed": return "A Completed stock out cannot be changed";
            case "Cancelled": return "A Cancelled stock out cannot be changed";
            case "Waiting":   return null; // any transition allowed
            default:          return "Unknown status: " + current;
        }
    }

    // ── Validation ───────────────────────────────────────────────
    private String validate(int retailerId, int productId,
                            String quantityStr, String transactionType,
                            String status) {

        if (transactionType == null ||
                (!transactionType.equals("RETAILER_OUT") &&
                        !transactionType.equals("DISPOSED")))
            return "Please select a valid Type";

        // Retailer only required for RETAILER_OUT
        if (transactionType.equals("RETAILER_OUT") && retailerId <= 0)
            return "Please select a Retailer";

        if (productId <= 0)
            return "Please select a Product";

        if (quantityStr == null || quantityStr.trim().isEmpty())
            return "Quantity is required";

        int qty;
        try {
            qty = Integer.parseInt(quantityStr.trim());
        } catch (NumberFormatException e) {
            return "Quantity must be a whole number";
        }
        if (qty <= 0)
            return "Quantity must be greater than 0";

        if (status == null || (!status.equals("Waiting") &&
                !status.equals("Completed") && !status.equals("Cancelled")))
            return "Status must be Waiting, Completed or Cancelled";

        return null;
    }
}