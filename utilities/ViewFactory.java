package utilities;


import DAO.ProductDAO;
import database.DBConnection;
import view.LoginView;
import view.ProductFormView;
import view.ProductView;
import view.RegisterView;
import viewModel.LoginViewModel;
import viewModel.ProductFormViewModel;
import viewModel.ProductViewModel;
import viewModel.RegistrationViewModel;

import java.sql.Connection;
import java.sql.SQLException;
import DAO.UserDAO;

public class ViewFactory {

    private static Connection connection;
    private static UserDAO userDAO;
    private static ProductDAO productDAO;

    static {
        try {
            connection = DBConnection.getConnection();
            userDAO = new UserDAO(connection);
            productDAO = new ProductDAO(connection);
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




}
