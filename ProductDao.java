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
import planetfood.pojo.Product;

/**
 *
 * @author jonty
 */
public class ProductDao {
    public static String getNewId() throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select count(*) from products");
        int id= 101;
        if(rs.next()){
            id=id+rs.getInt(1);
        }
        String sr= "P"+id;
        return sr;
        
    }
    
    public static boolean addProduct(Product p) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into products values (?,?,?,?,?)");
        ps.setString(1,p.getProdId());
        ps.setString(2,p.getCatId());
        ps.setString(3,p.getProdName());
        ps.setDouble(4,p.getProdPrice());
        ps.setString(5, p.getIsActive());
        int x=ps.executeUpdate();
        if(x!=0)
            return true;
        else
            return false;
    }
    
    public static HashMap<String,Product> getProductsByCategory(String catId) throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry="Select * from products where cat_id=? and active='Y'";
        PreparedStatement ps=conn.prepareStatement(qry);
        HashMap<String,Product> productList=new HashMap<String,Product>();
        ps.setString(1,catId);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            Product p=new Product();
            p.setCatId(catId);
            p.setProdId(rs.getString("prod_id"));
            p.setProdName(rs.getString("prod_name"));
            p.setProdPrice(rs.getDouble("prod_price"));
            p.setIsActive(rs.getString("active"));
            productList.put(p.getProdId(),p);
        }
        return productList;
    }
    
    public static ArrayList<Product> getAllData() throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry = "Select * from products";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(qry);
        ArrayList<Product> arr=new ArrayList<Product>();
        while (rs.next())
        {
            Product p =new Product();
            p.setProdId(rs.getString("prod_id"));
            p.setCatId(rs.getString("cat_id"));
            p.setProdName(rs.getString("prod_name"));
            p.setProdPrice(rs.getDouble("prod_price"));
            p.setIsActive(rs.getString("active"));
            arr.add(p);
            
        }
        return arr;
    
}
    
    public static HashMap<String,String> getActiveProductsByCategory(String catId) throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry="Select prod_name,prod_Id from products where cat_id=? and active='Y'";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1, catId);
        ResultSet rs=ps.executeQuery();
        HashMap<String,String> productList=new HashMap<String,String>();
        while(rs.next()){
            String prodName=rs.getString("prod_name");
            String prodId=rs.getString("prod_id");
            productList.put(prodName, prodId);
        }
        return productList;
        
    }
    
    public static boolean removeProduct(String prodId) throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry="update products set active='N' where prod_Id=?";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1, prodId);
        int x=ps.executeUpdate();
        if(x==0)
            return false;
        else
            return true;
    }
    
    public static boolean UpdateProduct(Product p) throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry="Update Products set prod_name=?,prod_price=?,active=? where prod_id=?";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1, p.getProdName());
        ps.setDouble(2, p.getProdPrice());
        ps.setString(3, p.getIsActive());
        ps.setString(4, p.getProdId());
        int x=ps.executeUpdate();
        if (x==0)
            return false;
        else
            return true;
    }
}
