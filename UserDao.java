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
import java.util.ArrayList;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.Product;
import planetfood.pojo.User;

/**
 *
 * @author Jonty
 */
public class UserDao {
    public static String validateUser(User user) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String qry = "Select username from Users where userid=? and password=? and usertype=?";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1,user.getUserid());
        ps.setString(2,user.getPassword());
        ps.setString(3,user.getUserType());
        ResultSet rs=ps.executeQuery();
        String username =null;
        if(rs.next()){
            username=rs.getString(1);
        }
        return username;
    }
    
    public static boolean addCashier(User user) throws SQLException{
        Connection conn = DBConnection.getConnection();
        String qry = "insert into users values(?,?,?,?,?)";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1,user.getUserid());
        ps.setString(2,user.getUserName());
        ps.setString(3,user.getEmpId());
        ps.setString(4,user.getPassword());
        ps.setString(5,user.getUserType().toUpperCase());
        int x=ps.executeUpdate();
        if(x>0)
            return true;
        else
            return false;
    }
    
    public static User getUser(String id) throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry = "Select * from users where empid=? and usertype='cashier'";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1, id);
        ResultSet rs=ps.executeQuery();
        User u =new User();
        while(rs.next()){
            u.setUserid(rs.getString("userid"));
            u.setUserName(rs.getString("username"));
            u.setEmpId(rs.getString("empid"));
            u.setPassword(rs.getString("password"));
            u.setUserType(rs.getString("usertype"));
        }
            
            
        
        return u;
    } 
    
    public static boolean deleteUser(String id) throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry = "Delete from users where empid=?";
        PreparedStatement ps=conn.prepareStatement(qry);
        ps.setString(1, id);
        int x=ps.executeUpdate();
        if(x>0)
            return true;
        else
            return false;
}
}
