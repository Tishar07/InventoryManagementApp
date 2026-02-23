package viewModel;

import DAO.RetailerDAO;
import model.Retailer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RetailerFormViewModel {
    private RetailerDAO retailerDAO;
    public RetailerFormViewModel(RetailerDAO retailerDAO){
        this.retailerDAO= retailerDAO;
    }

    public String save(String name, String email, String address, String contact, String status){
        if (name == null || name.trim().length() < 2 || !name.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$")) {
            return "Invalid Name (Only letters, spaces, hyphen and apostrophe allowed)";
        }
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+(\\.[A-Za-z]{2,})+$")) {
            return "Invalid Email";
        }

        if (address.isEmpty() || !address.matches("^[A-Za-z0-9.,'\\-/\\s]{5,100}$")) {
            return "Invalid address(At least 5 characters)";
        }
        if (contact.startsWith("+230")) {
            contact = contact.substring(4);
        }
        if (!contact.matches("^[24578][0-9]{7}$")) {
            return "Invalid Mauritian number";
        }
        if (Objects.equals(status, "active") || Objects.equals(status, "inactive")){
            return "Invalid Status";
        }
        retailerDAO.save(name,email,address,contact,status);
        return "Saved Successfully";
    }

    public String update(int RetailerID,String name, String email, String address, String contact, String status){
        if (!name.matches("^[A-Za-z]+([ '-][A-Za-z]+)*$")||name.isEmpty()) {
            return "Invalid Name(At least 2 characters and No Numbers/Special Characters Allowed)";
        }
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            return "Invalid Email";
        }

        if (address.isEmpty() || !address.matches("^[A-Za-z0-9.,'\\-/\\s]{5,100}$")) {
            return "Invalid address(At least 5 characters)";
        }

        if (contact.startsWith("+230")) {
            contact = contact.substring(4);
        }

        if (!contact.matches("^[24578][0-9]{7}$")) {
            return "Invalid Mauritian number";
        }

        if (!Objects.equals(status, "Active") && !Objects.equals(status, "Inactive")){
            return "Invalid Status";
        }

        retailerDAO.Update(RetailerID,name,email,address,contact,status);
        return "Updated Successfully";

    }

    public Map<String,String> FetchRetailerExisting(int RetailerID){
        Retailer r = retailerDAO.getExistingRetailer(RetailerID);
        Map<String,String> data = new HashMap<>();
        data.put("name",r.getName());
        data.put("email",r.getEmail());
        data.put("address",r.getAddress());
        data.put("contact",r.getContact());
        data.put("status",r.getStatus());
        return data;
    }

}
