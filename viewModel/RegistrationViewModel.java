package viewModel;

import DAO.UserDAO;
import model.User;
import java.util.regex.Pattern;

public class RegistrationViewModel {

    private UserDAO regDAO = new UserDAO();

    
    private boolean validateBlankFields(User r) {
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
        
        if (validateBlankFields(r)) {
            return "Please fill all of the fields";
        }
        
        //Mone call sa function la depi registrationDAO
        if (regDAO.usernameExists(r.getUsername())) {
            return "Username already exists in the database";
        }

        if (!isPasswordValid(r.getPassword())) {
            return "Password must have uppercase, lowercase, number , symbol and number of character must be more than 8";
        }

        boolean saved = regDAO.insertUser(r);

        if (saved) {
            return "Registration successful";
        }
        return "Registration failed:";
    }
}
