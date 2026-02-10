package DAO;

import model.Category;
import model.Subcategory;
import model.Product;
import model.Supplier;
import java.sql.*;
import java.util.ArrayList;

public class ProductDAO {
    private Connection conn;
    public ProductDAO(Connection conn){
        this.conn= conn;
    }
    public ArrayList<Product> RunSqlProduct(String sql){
        ArrayList<Product> Product = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productId=rs.getInt("ProductID");
                String productName=rs.getString("ProductName");
                Double unitPrice=rs.getDouble("UnitPrice");
                String productStatus=rs.getString("ProductStatus");
                String gender=rs.getString("Gender");
                String imagePath=rs.getString("ImagePath");
                Product.add(new Product(productId,productName,unitPrice,productStatus,gender,imagePath));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Product;
    }
    public ArrayList<Category> getCategory() {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = "SELECT CategoryID, CategoryName FROM category";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                categories.add(
                        new Category(rs.getInt("CategoryID"),
                        rs.getString("CategoryName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }
    public ArrayList<Product> getProduct()  {
        ArrayList<Product> Product = new ArrayList<>();
        String sql = "SELECT *" +
                      "FROM product";
        Product = RunSqlProduct(sql);
        return Product;
    }
    public ArrayList<Product> SortProduct(int sort)  {
        ArrayList<Product> Product = new ArrayList<>();
        String SortAttribute="";
        String sql = "SELECT * " +
                "FROM product "+
                "ORDER BY "+SortAttribute+" ASC";
        switch (sort){
            case 0:
                SortAttribute="ProductID";
            case 1:
                SortAttribute ="ProductName";
            case 2:
                SortAttribute="UnitPrice";
            case 3:
                sql="SELECT x.* " +
                        "FROM Product x INNER join Stock z " +
                        "ON x.ProductID = z.ProductID " +
                        "ORDER BY z.QuantityAvailable ASC";
            default:
                sql = "SELECT *" +
                        "FROM product";

        }
        Product = RunSqlProduct(sql);
        return Product;
    }
    public ArrayList<Product> SearchProduct(String Keyword){
        String sql = "SELECT * FROM Product WHERE ProductName LIKE '%" + Keyword + "%'";
        ArrayList<Product> Product = new ArrayList<>();
        Product= RunSqlProduct(sql);
        return Product;
    }
    public ArrayList<Subcategory> getSubCategory(String category) {
        ArrayList<Subcategory> subCategories = new ArrayList<>();
        String CatSql = "SELECT CategoryID FROM Category WHERE CategoryName = ?";
        String CatID="";

        String SubCatsql = "SELECT SubCategoryID, CategoryID, SubCategoryName FROM subcategory WHERE CategoryID = ?";

        try(PreparedStatement ps = conn.prepareStatement(CatSql)){
            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                CatID = rs.getString(1);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        try (PreparedStatement ps = conn.prepareStatement(SubCatsql)) {
            ps.setString(1, CatID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("SubCategoryID");
                int catId = rs.getInt("CategoryID");
                String name = rs.getString("SubCategoryName");

                subCategories.add(new Subcategory(id, catId, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subCategories;
    }
    public ArrayList<Supplier> getSupplier(){
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String sql= "SELECT x.SupplierID,z.Name,z.Email,z.Contact,z.Address,z.Status " +
                    "FROM supplier x INNER JOIN person z " +
                    "ON x.PersonID = z.PersonID";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("SupplierID");
                String Name = rs.getString("Name");
                String Email = rs.getString("Email");
                String Contact = rs.getString("Contact");
                String Address = rs.getString("Address");
                String Status = rs.getString("Status");
                suppliers.add(new Supplier(id,Name,Email,Contact,Address,Status));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }
    public void save(String ProductName, Double UnitPrice,String ProductStatus,String Gender,String ImagePath,int Category,int[] Subcategory,int[] supplier ){
        String sql = "INSERT INTO Product (ProductName, UnitPrice, ProductStatus, Gender, ImagePath) VALUES (?, ?, ?, ?, ?)";
        int productId=0 ;
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1, ProductName);
            ps.setDouble(2, UnitPrice);
            ps.setString(3, ProductStatus);
            ps.setString(4, Gender);
            ps.setString(5, ImagePath);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    productId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
         sql = "INSERT INTO product_subcategory_category (ProductID, SubcategoryID, CategoryID) " +
                 "VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            if(productId!=0){
                for (int i =0 ; i< Subcategory.length;i++) {
                    ps.setInt(1, productId);
                    ps.setInt(2, Subcategory[i]);
                    ps.setInt(3, Category);
                    ps.addBatch();
                }
                ps.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


        sql = "INSERT INTO supplier_product (SupplierID, ProductID) " +
                "VALUES(?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)){
            if(productId!=0){
                for (int i =0 ; i< supplier.length;i++) {
                    ps.setInt(1, supplier[i]);
                    ps.setInt(2, productId);
                    ps.addBatch();
                }
                ps.executeUpdate();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }


    //Fetch Existing Product
    public Product getExistingProduct(int ProductID){
        Product P= new Product(0,"",0.0,"","","");
        String sql = " SELECT * FROM Product WHERE ProductID = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ProductID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int productId=rs.getInt("ProductID");
                String productName=rs.getString("ProductName");
                Double unitPrice=rs.getDouble("UnitPrice");
                String productStatus=rs.getString("ProductStatus");
                String gender=rs.getString("Gender");
                String imagePath=rs.getString("ImagePath");
                P = new Product(productId,productName,unitPrice,productStatus,gender,imagePath);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return P;
    }
    public Category getCategoryExistingProduct(int ProductID){
        Category categorie=new Category(0,"");
        int catId = 0;
        String sql = "SELECT CategoryID FROM product_subcategory_category WHERE ProductID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ProductID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                catId = rs.getInt("CategoryID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String CatSql = "SELECT CategoryID, CategoryName FROM category WHERE CategoryID= ?";
        try (PreparedStatement ps = conn.prepareStatement(CatSql)) {
            ps.setInt(1, catId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                categorie = new Category(rs.getInt("CategoryID"), rs.getString("CategoryName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorie;
    }
    public ArrayList<Subcategory> getSubCategoryExistingProduct(int productID) {
        ArrayList<Subcategory> subCategories = new ArrayList<>();
        String sql = """
                SELECT psc.SubCategoryID,psc.CategoryID,ps.SubCategoryName
                FROM product_subcategory_category psc
                JOIN subcategory ps ON psc.SubCategoryID = ps.SubCategoryID
                WHERE psc.ProductID = ? ;
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int subId = rs.getInt("psc.SubCategoryID");
                    int catId = rs.getInt("psc.CategoryID");
                    String name = rs.getString("ps.SubName");
                    subCategories.add(new Subcategory(subId, catId, name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subCategories;
    }
    public ArrayList<Supplier> getSupplierExistingProduct(int productID){
        ArrayList<Supplier> suppliers = new ArrayList<>();
        String sql= """
                    SELECT p.*, x.SupplierID
                    FROM person p
                    JOIN supplier x
                        ON p.PersonID = x.PersonID
                    JOIN supplier_product z
                        ON x.SupplierID = z.SupplierID
                    WHERE z.ProductID = ?;
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("x.SupplierID");
                String Name = rs.getString("p.Name");
                String Email = rs.getString("p.Email");
                String Contact = rs.getString("p.Contact");
                String Address = rs.getString("p.Address");
                String Status = rs.getString("p.Status");
                suppliers.add(new Supplier(id,Name,Email,Contact,Address,Status));
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }


}
