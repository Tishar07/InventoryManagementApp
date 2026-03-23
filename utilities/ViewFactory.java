package utilities;


import DAO.*;
import DAO.DashboardDAO;
import DAO.HistoryDAO;
import DAO.ProductDAO;
import DAO.RetailerDAO;
import DAO.SupplierDAO;
import DAO.StockOutDAO;
import database.DBConnection;
import view.*;
import viewModel.*;

import java.sql.Connection;
import java.sql.SQLException;

public class ViewFactory {

    private static Connection connection;
    private static UserDAO userDAO;
    private static ProductDAO productDAO;
    private static DashboardDAO dashboardDAO;
    private static RetailerDAO retailerDAO;
    private static SupplierDAO supplierDAO;
    private static HistoryDAO historyDAO;
    private static StockOutDAO stockOutDAO;
    private static StockInDAO stockInDAO;
    private static StockStatusDAO stockStatusDAO;



    static {
        try {
            connection = DBConnection.getConnection();
            userDAO = new UserDAO(connection);
            productDAO = new ProductDAO(connection);
            dashboardDAO = new DashboardDAO(connection);
            retailerDAO = new RetailerDAO(connection);
            supplierDAO = new SupplierDAO(connection);
            stockInDAO = new StockInDAO(connection);
            historyDAO = new HistoryDAO(connection);
            stockOutDAO = new StockOutDAO(connection);
            stockStatusDAO = new StockStatusDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

    public static LoginView createLoginView() {
        LoginViewModel viewModel = new LoginViewModel(userDAO);
        return new LoginView(viewModel);
    }

    public static RegisterView createRegisterView() {
        RegistrationViewModel viewModel = new RegistrationViewModel(userDAO);
        return new RegisterView(viewModel);
    }

    public static ProductFormView createProductFormView() {
        ProductFormViewModel viewModel = new ProductFormViewModel(productDAO);
        return new ProductFormView(viewModel);
    }

    public static ProductFormView createProductFormViewEdit(int ProductID) {
        ProductFormViewModel viewModel = new ProductFormViewModel(productDAO);
        return new ProductFormView(viewModel, ProductID);
    }

    public static ProductView createProductView() {
        ProductViewModel viewModel = new ProductViewModel(productDAO);
        return new ProductView(viewModel);
    }

    public static DashboardView createDashboardView() {
        DashboardViewModel viewModel = new DashboardViewModel(dashboardDAO);
        return new DashboardView(viewModel);
    }

    public static RetailerView createRetailerView() {
        RetailerViewModel viewModel = new RetailerViewModel(retailerDAO);
        return new RetailerView(viewModel);
    }


    public static RetailerFormView createRetailerViewForm() {
        RetailerFormViewModel viewModel = new RetailerFormViewModel(retailerDAO);
        return new RetailerFormView(viewModel);
    }

    public static RetailerFormView createRetailerViewFormEditView(int RetailerID) {
        RetailerFormViewModel viewModel = new RetailerFormViewModel(retailerDAO);
        return new RetailerFormView(viewModel, RetailerID);
    }

    public static SupplierView createSupplierView() {
        SupplierViewModel viewModel = new SupplierViewModel(supplierDAO);
        return new SupplierView(viewModel);
    }

    public static SupplierFormView createSupplierViewForm() {
        SupplierFormViewModel viewModel = new SupplierFormViewModel(supplierDAO);
        return new SupplierFormView(viewModel);
    }

    public static SupplierFormView createSupplierViewFormEditView(int SupplierID) {
        SupplierFormViewModel viewModel = new SupplierFormViewModel(supplierDAO);
        return new SupplierFormView(viewModel, SupplierID);
    }

    public static StockInView createStockInView() {
        StockInViewModel viewModel = new StockInViewModel(stockInDAO);
        return new StockInView(viewModel);
    }

    public static StockInFormView createStockInFormView() {
        StockInFormViewModel viewModel = new StockInFormViewModel(stockInDAO);
        return new StockInFormView(viewModel);
    }

    public static StockInFormView createStockInFormViewEdit(int transactionID) {
        StockInFormViewModel viewModel = new StockInFormViewModel(stockInDAO);
        return new StockInFormView(viewModel, transactionID);
    }


    public static HistoryView createHistoryView() {
        HistoryViewModel viewModel = new HistoryViewModel(historyDAO);
        return new HistoryView(viewModel);
    }

    public static StockOutView createStockOutView() {
        StockOutViewModel viewModel = new StockOutViewModel(stockOutDAO);
        return new StockOutView(viewModel);
    }


    public static StockOutFormView createStockOutFormView() {
        StockOutFormViewModel viewModel = new StockOutFormViewModel(stockOutDAO);
        return new StockOutFormView(viewModel);
    }

    public static StockOutFormView createStockOutFormViewEdit(int TransactionID) {
        StockOutFormViewModel viewModel = new StockOutFormViewModel(stockOutDAO);
        return new StockOutFormView(viewModel, TransactionID);
    }

    public static UserFormView createUserFormEditView(String username) {
        UserFormViewModel viewModel = new UserFormViewModel(userDAO);
        return new UserFormView(viewModel,username);
    }

    public static StockStatusView createStockStatusView() {
        StockStatusViewModel viewModel = new StockStatusViewModel(stockStatusDAO);
        return new StockStatusView(viewModel);
    }

}



