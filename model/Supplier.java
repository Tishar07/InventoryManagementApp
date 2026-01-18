package model;

public class Supplier {
    private int Supplierid;
    private String name;
    private String email;
    private String address;
    private String contact;
    private String status;


    public Supplier (int Supplierid, String name, String email, String address, String contact, String status){
       this.Supplierid = Supplierid;
       this.name = name;
       this.email = email;
       this.address = address;
       this.contact = contact;
       this.status = status;

    }

    public int getSupplierid() { return Supplierid; }
    public void setSupplierid(int Supplierid) { this.Supplierid = Supplierid; }

    public String getName(){ return name; }
    public void setName(String name) {this.name = name; }

    public String getEmail(){return email; }
    public void setEmail(String email) {this.email = email; }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

