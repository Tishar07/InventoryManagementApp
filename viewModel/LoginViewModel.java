package viewModel;

import DAO.UserDAO;
import model.User;
import utilities.Session;

import java.util.Objects;

public class LoginViewModel {
    private UserDAO userdao;

    public LoginViewModel(UserDAO userdao) {
        this.userdao = userdao;
    }

    //Logic to validate Login
    public boolean login(String Username, String Password) {
        if (Objects.equals(Username, "") || Username == null) {
            return false;
        }
        if (Objects.equals(Password, "") || Password == null) {
            return false;
        }
        User user = userdao.validateLogin(Username, Password);
        if (user != null) {
            Session.setUsername(user.getUsername());
            return true;
        }
        return false;
    }
}
