import DAO.UserDAO;
import database.DBConnection;
import model.User;
import view.LoginView;
import viewModel.LoginViewModel;

import java.sql.Connection;
import java.sql.SQLException;

public class main {
    public static void main(String[] args) throws SQLException {
        Connection conn = DBConnection.getConnection();
        UserDAO userDAO = new UserDAO(conn);
        LoginViewModel viewModel = new LoginViewModel(userDAO);
        LoginView loginView = new LoginView(viewModel);
        loginView.setVisible(true);
    }
}
