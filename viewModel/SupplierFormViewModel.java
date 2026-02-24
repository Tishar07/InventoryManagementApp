package viewModel;

import DAO.SupplierDAO;
import model.Supplier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SupplierFormViewModel {

    private SupplierDAO supplierDAO;

    public SupplierFormViewModel(SupplierDAO supplierDAO) {
        this.supplierDAO = supplierDAO;
    }

    public String save(String name, String email, String address, String contact, String status) {

        if (name == null || name.trim().length() < 2 ||
                !name.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$")) {
            return "Invalid Name (Only letters, spaces, hyphen and apostrophe allowed)";
        }

        if (email == null ||
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})+$")) {
            return "Invalid Email";
        }

        if (address == null ||
                address.isEmpty() ||
                !address.matches("^[A-Za-z0-9.,'\\-/\\s]{5,100}$")) {
            return "Invalid address (At least 5 characters)";
        }

        if (contact.startsWith("+230")) {
            contact = contact.substring(4);
        }

        if (!contact.matches("^[24578][0-9]{7}$")) {
            return "Invalid Mauritian number";
        }

        if (!Objects.equals(status, "Active") && !Objects.equals(status, "Inactive")) {
            return "Invalid Status";
        }

        supplierDAO.save(name, email, address, contact, status);
        return "Saved Successfully";
    }

    // =========================
    // UPDATE SUPPLIER
    // =========================
    public String update(int SupplierID, String name, String email,
                         String address, String contact, String status) {

        if (name == null || name.isEmpty() ||
                !name.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$")) {
            return "Invalid Name (At least 2 characters and no numbers)";
        }

        if (email == null ||
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})+$")) {
            return "Invalid Email";
        }

        if (address == null ||
                address.isEmpty() ||
                !address.matches("^[A-Za-z0-9.,'\\-/\\s]{5,100}$")) {
            return "Invalid address (At least 5 characters)";
        }

        if (contact.startsWith("+230")) {
            contact = contact.substring(4);
        }

        if (!contact.matches("^[24578][0-9]{7}$")) {
            return "Invalid Mauritian number";
        }

        if (!Objects.equals(status, "Active") && !Objects.equals(status, "Inactive")) {
            return "Invalid Status";
        }

        supplierDAO.update(SupplierID, name, email, address, contact, status);
        return "Updated Successfully";
    }

    public Map<String, String> FetchSupplierExisting(int SupplierID) {

        Supplier s = supplierDAO.getExistingSupplier(SupplierID);

        Map<String, String> data = new HashMap<>();
        data.put("name", s.getName());
        data.put("email", s.getEmail());
        data.put("address", s.getAddress());
        data.put("contact", s.getContact());
        data.put("status", s.getStatus());
        return data;
    }
}