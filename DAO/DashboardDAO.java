package DAO;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DashboardDAO {
    private Connection conn;
    public DashboardDAO(Connection conn){
        this.conn= conn;
    }

    public Map<String,Integer> getSubCategoryTotal(){
        Map<String,Integer> sub = new HashMap<>();
        String sql= """
                SELECT z.SubCategoryName,COUNT(ProductID) AS TotalProducts
                FROM product_subcategory_category x INNER JOIN subcategory z
                ON x.SubCategoryID = z.SubCategoryID
                GROUP BY z.SubCategoryName;
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                sub.put(rs.getString("z.SubCategoryName"),rs.getInt("TotalProducts"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return sub;
    }

    public Map<String,Integer> getGenderTotal(){
        Map<String,Integer> gender = new HashMap<>();
        String sql= """
                SELECT z.Gender,COUNT(DISTINCT  x.ProductID) AS TotalGender
                FROM product_subcategory_category x INNER JOIN product z
                ON x.ProductID = z.ProductID
                GROUP BY z.Gender;
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                gender.put(rs.getString("z.Gender"),rs.getInt("TotalGender"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return gender;
    }


    public Map<String,Integer> getCategoryTotal(){
        Map<String,Integer> CategoryStock = new HashMap<>();
        String sql= """
                SELECT y.CategoryName,
                       SUM(z.QuantityAvailable) AS TotalStock
                FROM (
                        SELECT DISTINCT ProductID, CategoryID
                        FROM product_subcategory_category
                     ) x
                INNER JOIN stock z
                    ON x.ProductID = z.ProductID
                INNER JOIN category y
                    ON x.CategoryID = y.CategoryID
                GROUP BY y.CategoryName;
                """;
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                CategoryStock.put(rs.getString("y.CategoryName"),rs.getInt("TotalStock"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        return CategoryStock;
    }

    public Map<String, List<Integer>> getStockInOutPerMonth(){
        Map<String, List<Integer>> StockPerMonth = new HashMap<>();

        String sql= """
                SELECT
                    MONTHNAME(ChangeDate) AS Month,
                    SUM(CASE WHEN ChangeType = 'IN' THEN QuantityType ELSE 0 END) AS `Total In`,
                    SUM(CASE WHEN ChangeType = 'OUT' THEN QuantityType ELSE 0 END) AS `Total Out`
                FROM StockHistory
                GROUP BY MONTH(ChangeDate), MONTHNAME(ChangeDate)
                ORDER BY MONTH(ChangeDate);
                """;

        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                List<Integer> StockData = new ArrayList<>();
                StockData.add(rs.getInt("Total In"));
                StockData.add(rs.getInt("Total Out"));
                StockPerMonth.put(rs.getString("Month"),StockData);


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return StockPerMonth;
    }


}
