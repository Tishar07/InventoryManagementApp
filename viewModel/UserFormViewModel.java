package viewModel;

import DAO.UserDAO;
import model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserFormViewModel {
    private UserDAO userDAO;

    public UserFormViewModel(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public String update(String username, String name, String newUsername, String email,
                         String address, String contact,
                         String newPassword, String confirmPassword) {

        // Field Validation
        if (name == null || name.trim().length() < 2 || !name.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$")) {
            return "Invalid Name (Only letters, spaces, hyphen and apostrophe allowed)";
        }

        if (newUsername == null || newUsername.trim().length() < 3 || !newUsername.matches("^[A-Za-z0-9_]{3,20}$")) {
            return "Invalid Username (3-20 characters, letters, numbers and underscores only)";
        }

        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})+$")) {
            return "Invalid Email";
        }

        if (address.isEmpty() || !address.matches("^[A-Za-z0-9.,'\\-/\\s]{5,100}$")) {
            return "Invalid Address (At least 5 characters)";
        }

        if (contact == null || contact.trim().isEmpty()) {
            return "Contact cannot be empty";
        }

        // Password Change
        String passwordToSave;
        boolean anyPasswordFieldFilled = !newPassword.isEmpty() || !confirmPassword.isEmpty();

        User existing = userDAO.findByUsername(username);

        if (anyPasswordFieldFilled) {
            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                return "Please fill in both password fields to change your password";
            }

            if (newPassword.length() < 3 || newPassword.length() > 20) {
                return "Password must be between 3 and 20 characters";
            }

            if (!newPassword.matches(".*[A-Z].*")) {
                return "Password must contain at least one uppercase letter";
            }

            if (!newPassword.matches(".*[a-z].*")) {
                return "Password must contain at least one lowercase letter";
            }

            if (!newPassword.matches(".*[0-9].*")) {
                return "Password must contain at least one number";
            }

            if (!newPassword.matches(".*[^A-Za-z0-9].*")) {
                return "Password must contain at least one symbol";
            }

            if (!Objects.equals(newPassword, confirmPassword)) {
                return "New password and confirmation do not match";
            }

            if (Objects.equals(newPassword, existing.getPassword())) {
                return "New password must be different from the current password";
            }

            passwordToSave = newPassword;
        } else {
            passwordToSave = existing.getPassword();
        }

        userDAO.updateByUserID(existing.getUserid(), name, newUsername, email, passwordToSave, address, existing.getRole(), contact);
        return "Updated Successfully";
    }

    public Map<String, String> FetchUserExisting(String username) {
        User u = userDAO.findByUsername(username);
        Map<String, String> data = new HashMap<>();
        data.put("name",     u.getName());
        data.put("username", u.getUsername());
        data.put("email",    u.getEmail());
        data.put("address",  u.getAddress());
        data.put("contact",  u.getContact());
        return data;
    }
}