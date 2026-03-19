// viewModel/StockOutFormViewModel.java
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

    // ── Dropdowns ────────────────────────────────────────────────
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

    // ── Save ─────────────────────────────────────────────────────
    public String save(int retailerId, int productId,
                       String quantityStr, String status, String notes) {

        String err = validate(retailerId, productId, quantityStr, status);
        if (err != null) return err;

        stockOutDAO.save(retailerId, productId,
                Integer.parseInt(quantityStr.trim()), status,
                notes == null ? "" : notes.trim());
        return "Saved Successfully";
    }

    // ── Update ───────────────────────────────────────────────────
    public String update(int transactionId, int retailerId, int productId,
                         String quantityStr, String status, String notes) {

        String err = validate(retailerId, productId, quantityStr, status);
        if (err != null) return err;

        stockOutDAO.update(transactionId, retailerId, productId,
                Integer.parseInt(quantityStr.trim()), status,
                notes == null ? "" : notes.trim());
        return "Updated Successfully";
    }

    // ── Fetch existing for edit form ─────────────────────────────
    public Map<String, String> FetchExistingStockOut(int transactionId) {
        StockOut s = stockOutDAO.getExistingStockOut(transactionId);
        Map<String, String> data = new HashMap<>();
        if (s == null) return data;
        data.put("transactionId", String.valueOf(s.getTransactionId()));
        data.put("retailerId",    String.valueOf(s.getRetailerId()));
        data.put("retailerName",  s.getRetailerName());
        data.put("productId",     String.valueOf(s.getProductId()));
        data.put("productName",   s.getProductName());
        data.put("quantity",      String.valueOf(s.getQuantity()));
        data.put("status",        s.getStatus());
        data.put("notes",         s.getNotes() != null ? s.getNotes() : "");
        return data;
    }

    // ── Validation ───────────────────────────────────────────────
    private String validate(int retailerId, int productId,
                            String quantityStr, String status) {
        if (retailerId <= 0)
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