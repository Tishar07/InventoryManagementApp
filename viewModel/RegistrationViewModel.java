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
        return r.getUsername() == null || r.getUsername().trim().isEmpty()
                || r.getPassword() == null || r.getPassword().trim().isEmpty()
                || r.getName() == null || r.getName().trim().isEmpty()
                || r.getEmail() == null || r.getEmail().trim().isEmpty()
                || r.getRole() == null || r.getRole().trim().isEmpty()
                || r.getStatus() == null || r.getStatus().trim().isEmpty()
                || r.getAddress() == null || r.getAddress().trim().isEmpty()
                || r.getContact() == null || r.getContact().trim().isEmpty();
    }

    // Username validation
    private boolean isUsernameValid(String username){
        return username.matches("^[A-Za-z0-9_]{3,20}$");
    }

    // Email validation
    private boolean isEmailValid(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})+$");
    }

    // Password validation
    private boolean isPasswordValid(String password) {
        String regex =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).{8,}$";
        return Pattern.matches(regex, password);
    }

    // Mauritian phone validation
    private boolean isContactValid(String contact){

        if(contact.startsWith("+230")){
            contact = contact.substring(4);
        }

        return contact.matches("^[24578][0-9]{7}$");
    }

    // Address validation
    private boolean isAddressValid(String address){
        return address.matches("^[A-Za-z0-9.,'\\-/\\s]{5,100}$");
    }

    public String registerUser(User r) {

        if (hasBlankFields(r)) {
            return "Please fill all fields";
        }

        if(!isUsernameValid(r.getUsername())){
            return "Invalid Username (3-20 characters, letters/numbers/underscore)";
        }

        if(!isEmailValid(r.getEmail())){
            return "Invalid Email format";
        }

        if(!isPasswordValid(r.getPassword())){
            return "Password must contain uppercase, lowercase, number and symbol";
        }

        if(!isContactValid(r.getContact())){
            return "Invalid Mauritian phone number";
        }

        if(!isAddressValid(r.getAddress())){
            return "Invalid address (5-100 characters)";
        }

        User existingUser = userdao.findByUsername(r.getUsername());

        if (existingUser != null) {
            return "Username already exists";
        }

        try {
            userdao.insert(r);
            return "Registration successful";
        } catch (RuntimeException e) {
            return "Registration failed";
        }
    }
}