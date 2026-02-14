package utilities;


import DAO.DashboardDAO;
import DAO.ProductDAO;
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

    static {
        try {
            connection = DBConnection.getConnection();
            userDAO = new UserDAO(connection);
            productDAO = new ProductDAO(connection);
            dashboardDAO = new DashboardDAO(connection);
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

    public static ProductView createProductView () {
        ProductViewModel viewModel = new ProductViewModel(productDAO);
        return new ProductView(viewModel);
    }

    public static DashboardView createDashboardView(){
        DashboardViewModel viewModel = new DashboardViewModel(dashboardDAO);
        return  new DashboardView(viewModel);
    }

}
