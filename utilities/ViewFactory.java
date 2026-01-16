package utilities;


import database.DBConnection;
import view.LoginView;
import view.RegisterView;
import viewModel.LoginViewModel;
import viewModel.RegistrationViewModel;

import java.sql.Connection;
import java.sql.SQLException;
import DAO.UserDAO;

public class ViewFactory {

    private static Connection connection;
    private static UserDAO userDAO;

    static {
        try {
            connection = DBConnection.getConnection();
            userDAO = new UserDAO(connection);
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

}
