/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Categories;
import planetfood.pojo.Product;

/**
 *
 * @author jonty
 */
public class CategoryDao {
    public static HashMap<String,String> getAllCategoryId() throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select * from categories");
        HashMap<String,String> categories= new HashMap();
        while(rs.next()){
            String catId=rs.getString(1);
            String catName= rs.getString(2);
            categories.put(catName,catId);
        }
        return categories;
    }
    
    public static String getNewId() throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select count(*) from categories");
        int id= 101;
        if(rs.next()){
            id=id+rs.getInt(1);
        }
        String sr= "C"+id;
        return sr;
        
    }
    
    public static boolean addCategory(Categories c) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into categories values (?,?)");
        ps.setString(1,c.getCatId());
        ps.setString(2,c.getCatName());
        int x=ps.executeUpdate();
        if(x!=0)
            return true;
        else
            return false;
    }
    
    public static HashMap<String,String> getAllCategory() throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select * from categories");
        HashMap<String,String> categories= new HashMap();
        while(rs.next()){
            String catId=rs.getString(1);
            String catName= rs.getString(2);
            categories.put(catId,catName);
        }
        return categories;
    }
    
    public static boolean editCategory(Categories c) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("update categories set cat_name=? where cat_id=?");
        ps.setString(1,c.getCatName());
        ps.setString(2,c.getCatId());
        int x=ps.executeUpdate();
        if(x!=0)
            return true;
        else
            return false;
    }
    
    public static ArrayList<Categories> getAllData() throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry = "Select * from categories";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(qry);
        ArrayList<Categories> arr=new ArrayList<Categories>();
        while (rs.next())
        {
            Categories c =new Categories();
            c.setCatId(rs.getString("cat_id"));
            c.setCatName(rs.getString("cat_name"));
            arr.add(c);
            
        }
        return arr;
    
}
    
}
