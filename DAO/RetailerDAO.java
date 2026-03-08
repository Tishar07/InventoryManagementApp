package DAO;

import model.Retailer;

import java.sql.*;
import java.util.ArrayList;

public class RetailerDAO {
    private Connection conn;
    public RetailerDAO(Connection conn){
        this.conn= conn;
    }

    public ArrayList<Retailer> getRetailer(){
        ArrayList<Retailer> RetailerList = new ArrayList<>();
        String sql = """
                SELECT r.RetailerID , p.*
                FROM person p INNER JOIN retailer r
                ON p.personID = r.PersonID
                WHERE p.Status='Active'
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Retailer r = new Retailer(
                        rs.getInt("r.RetailerID"),
                        rs.getString("p.Name"),
                        rs.getString("p.Email"),
                        rs.getString("p.Address"),
                        rs.getString("p.Contact"),
                        rs.getString("p.Status"));
                RetailerList.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  RetailerList;
    }

    public Retailer getExistingRetailer(int RetailerID){
        Retailer Retailer = null;
        String sql = """
                SELECT r.RetailerID , p.*
                FROM person p INNER JOIN retailer r
                ON p.personID = r.PersonID
                WHERE r.RetailerID = ? AND p.Status='Active'
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,RetailerID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                Retailer = new Retailer(
                        rs.getInt("r.RetailerID"),
                        rs.getString("p.Name"),
                        rs.getString("p.Email"),
                        rs.getString("p.Address"),
                        rs.getString("p.Contact"),
                        rs.getString("p.Status"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  Retailer;
    }

    public void save(String name, String email, String address, String contact, String status) {

        String sql = """
        INSERT INTO person (name, email, address, contact, status)
        VALUES (?,?,?,?,?)
        """;

        int personId = -1;

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, address);
            ps.setString(4, contact);
            ps.setString(5, status);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                personId = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert into retailer
        sql = "INSERT INTO retailer (PersonID) VALUES (?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, personId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Update(int RetailerID,String name, String email, String address, String contact, String status){
        String sql = """
                UPDATE person p
                INNER JOIN retailer r ON p.PersonID = r.PersonID
                SET
                    p.Name = ?,
                    p.Email = ?,
                    p.Contact = ?,
                    p.Address = ?,
                    p.Status = ?
                WHERE r.RetailerID = ?;
                """;

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,contact);
            ps.setString(4,address);
            ps.setString(5,status);
            ps.setInt(6,RetailerID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int RetailerID){
     String sql= """
                UPDATE person p
                INNER JOIN retailer r ON r.PersonID = p.PersonID
                SET p.Status = 'Inactive'
                WHERE r.RetailerID = ?;
            """;
     try(PreparedStatement ps = conn.prepareStatement(sql)){
         ps.setInt(1,RetailerID);
         ps.executeUpdate();
     }catch (SQLException e){
         e.printStackTrace();
     }

    }

    public ArrayList<Retailer> searchRetailer(String Value){
        ArrayList<Retailer> RetailerList = new ArrayList<>();
        String sql = """
                SELECT r.RetailerID, p.*
                FROM person p
                INNER JOIN retailer r ON p.PersonID = r.PersonID
                WHERE p.Status = 'Active'
                AND (
                      r.RetailerID = ?
                   OR p.Name LIKE CONCAT('%', ?, '%')
                   OR p.Email LIKE CONCAT('%', ?, '%')
                   OR p.Contact LIKE CONCAT('%', ?, '%')
                   OR p.Address LIKE CONCAT('%', ?, '%')
                );
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            if (isInteger(Value)&&Value.length()<=2) {
                ps.setInt(1, Integer.parseInt(Value));
            } else {
                ps.setNull(1, java.sql.Types.INTEGER);
            }
            String likeValue = "%" + Value + "%";
            ps.setString(2, likeValue);
            ps.setString(3, likeValue);
            ps.setString(4, likeValue);
            ps.setString(5, likeValue);

            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Retailer r = new Retailer(
                        rs.getInt("r.RetailerID"),
                        rs.getString("p.Name"),
                        rs.getString("p.Email"),
                        rs.getString("p.Address"),
                        rs.getString("p.Contact"),
                        "Active");
                RetailerList.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  RetailerList;
    }
    private boolean isInteger(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
