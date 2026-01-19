package viewModel;

import DAO.UserDAO;
import model.User;

import java.util.regex.Pattern;

public class RegistrationViewModel {

    private UserDAO userdao;
    public RegistrationViewModel(UserDAO userdao){
        this.userdao = userdao;
    }

    private boolean hasBlankFields(User r) {
        return r.getUsername().isEmpty()
                || r.getPassword().isEmpty()
                || r.getName().isEmpty()
                || r.getEmail().isEmpty()
                || r.getRole().isEmpty()
                || r.getStatus().isEmpty()
                || r.getAddress().isEmpty()
                || r.getContact().isEmpty();
    }

    private boolean isPasswordValid(String password) {
        String regex =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
        return Pattern.matches(regex, password);
    }


    public String registerUser(User r) {


        if (hasBlankFields(r)) {
            return "Please fill all fields";
        }


        User existingUser = userdao.findByUsername(r.getUsername());
        if (existingUser != null) {
            return "Username already exists";
        }


        if (!isPasswordValid(r.getPassword())) {
            return "Password must contain uppercase, lowercase, number and symbol";
        }


        try {
            userdao.insert(r);
            return "Registration successful";
        } catch (RuntimeException e) {
            return "Registration failed";
        }
    }
}
