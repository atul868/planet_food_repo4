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
import planetfood.pojo.Employees;

/**
 *
 * @author Jonty
 */
public class EmployeesDao {
    public static boolean addEmployee(Employees e)throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into employees values(?,?,?,?)");
        ps.setString(1, e.getEmpid());
        ps.setString(2, e.getEmpname());
        ps.setString(3,e.getJob());
        ps.setInt(4,e.getSal());
        int x=ps.executeUpdate();
        if(x>0)
            return true;
        else
            return false;

}
    
    public static boolean updateEmployee(Employees e) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps= conn.prepareStatement("Update employees set ename=?,job=?, sal=? where emid=?");
        ps.setString(1, e.getEmpname());
        ps.setString(2,e.getJob());
        ps.setDouble(3,e.getSal());
        ps.setString(4,e.getEmpid());
        int x= ps.executeUpdate();
        if(x!=0)
            return true;
        else
            return false;
    }
    
    public static boolean deleteEmployee(String s) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("delete from employees where emid=?");
        ps.setString(1,s);
        int x=ps.executeUpdate();
        if(x!=0)
            return true;
        else
            return false;
    }
    
    public static String getNewId() throws SQLException{
        Connection conn=DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select count(*) from employees");
        int id= 101;
        if(rs.next()){
            id=id+rs.getInt(1);
        }
        String sr= "E"+id;
        return sr;
    }
    
    public static ArrayList<Employees> getAllEmployees() throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry = "Select * from employees";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(qry);
        ArrayList<Employees> arr=new ArrayList<Employees>();
        while (rs.next())
        {
            Employees e =new Employees();
            e.setEmpid(rs.getString("emid"));
            e.setEmpname(rs.getString("ename"));
            e.setJob(rs.getString("job"));
            e.setSal(rs.getInt("sal"));
            arr.add(e);
            
        }
        return arr;
    }

    public static ArrayList<String> getAllEmployeesId() throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry = "Select * from employees";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(qry);
        ArrayList<String> arr=new ArrayList<String>();
        while (rs.next())
        {
            arr.add(rs.getString("emid")); 
        }
        return arr;
    }
    
    public static HashMap<String,String> getCashier() throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry="Select emid,ename from employees where job='cashier'";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(qry);
        HashMap<String,String> cashier=new HashMap();
        while(rs.next()){
            String name=rs.getString(1);
            String id=rs.getString(2);
            cashier.put(id, name);
        }
        return cashier;
    }

}
