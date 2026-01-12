package model;
public class registration {
    private int userid;
    private String username;
    private String role;
    private String password;
    private String name;
    private String email;
    private String status;
    private String address;
    private String contact;

    public registration(int userid,String username,String role,String password,String name, String email, String status,String address,String contact){
        this.userid = userid;
        this.username = username;
        this.role = role;
        this.password = password;
        this.name =name;
        this.email = email;
        this.status = status;
        this.address = address;
        this.contact = contact;
    }

    public int getUserid(){
        return userid;
    }
    public void setUserid(int userid){
        this.userid = userid;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public String getContact(){
        return contact;
    }
    public void setContact(String contact){
        this.contact = contact;
    }

}
