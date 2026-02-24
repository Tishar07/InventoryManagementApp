package utilities;


import DAO.DashboardDAO;
import DAO.ProductDAO;
import DAO.RetailerDAO;
import DAO.SupplierDAO;
import database.DBConnection;
import view.*;
import viewModel.*;

import java.sql.Connection;
import java.sql.SQLException;
import DAO.UserDAO;

public class ViewFactory {

    private static Connection connection;
    private static UserDAO userDAO;
    private static ProductDAO productDAO;
    private static DashboardDAO dashboardDAO;
    private static RetailerDAO retailerDAO;
    private static SupplierDAO supplierDAO;


    static {
        try {
            connection = DBConnection.getConnection();
            userDAO = new UserDAO(connection);
            productDAO = new ProductDAO(connection);
            dashboardDAO = new DashboardDAO(connection);
            retailerDAO = new RetailerDAO(connection);
            supplierDAO = new SupplierDAO(connection);
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

    public static ProductFormView createProductFormView () {
        ProductFormViewModel viewModel = new ProductFormViewModel(productDAO);
        return new ProductFormView(viewModel);
    }

//    public static ProductView createProductView () {
//        ProductViewModel viewModel = new ProductViewModel(productDAO);
//        return new ProductView(viewModel);
//    }

    public static DashboardView createDashboardView(){
        DashboardViewModel viewModel = new DashboardViewModel(dashboardDAO);
        return  new DashboardView(viewModel);
    }

    public static RetailerView createRetailerView(){
        RetailerViewModel viewModel = new RetailerViewModel(retailerDAO);
        return  new RetailerView(viewModel);
    }


    public static  RetailerFormView createRetailerViewForm(){
        RetailerFormViewModel viewModel = new RetailerFormViewModel(retailerDAO);
        return new RetailerFormView(viewModel);
    }

    public static  RetailerFormView createRetailerViewFormEditView(int RetailerID){
        RetailerFormViewModel viewModel = new RetailerFormViewModel(retailerDAO);
        return new RetailerFormView(viewModel,RetailerID);
    }
    public static SupplierView createSupplierView(){
        SupplierViewModel viewModel = new SupplierViewModel(supplierDAO);
        return new SupplierView(viewModel);
    }

    public static SupplierFormView createSupplierViewForm(){
        SupplierFormViewModel viewModel = new SupplierFormViewModel(supplierDAO);
        return new SupplierFormView(viewModel);
    }
}
