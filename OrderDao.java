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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import planetfood.dbutil.DBConnection;
import planetfood.pojo.OrderDetail;
import planetfood.pojo.Orders;

/**
 *
 * @author jonty
 */
public class OrderDao {
    public static ArrayList<Orders> getOrdersByDate(Date startDate,Date endDate)throws SQLException{  //Select java.util for date
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Select * from orders where ord_date between ? and ?");
        long ms1=startDate.getTime();
        long ms2=endDate.getTime();
        java.sql.Date d1=new java.sql.Date(ms1);
        java.sql.Date d2=new java.sql.Date(ms2);
        ps.setDate(1, d1);
        ps.setDate(2, d2);
        ArrayList<Orders> orderList=new ArrayList();
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            Orders o=new Orders();
            o.setOrdId(rs.getString("ord_id"));
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
            java.sql.Date d=rs.getDate("ord_date");
            String dateStr=sdf.format(d);
            o.setOrdDate(dateStr);
            o.setOrdAmount(rs.getDouble("ord_amount"));
            o.setGst(rs.getDouble("gst"));
            o.setGstAmount(rs.getDouble("gst_amount"));
            o.setGrandTotal(rs.getDouble("grand_total"));
            o.setDiscount(rs.getDouble("discount"));
            o.setUserId(rs.getString("userid"));
            orderList.add(o);
        }
        return orderList;
    }
    
     public static ArrayList<Orders> getAllData()throws SQLException{
        Connection conn=DBConnection.getConnection();
        String qry="select * from orders";
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(qry);
        ArrayList<Orders>OrderList=new ArrayList<Orders>();
        while(rs.next())
        {
            Orders p=new Orders();
            p.setOrdId(rs.getString("ORD_ID"));
            p.setOrdDate(rs.getString("ORD_DATE"));
            p.setGst(rs.getDouble("GST"));
            p.setGstAmount(rs.getDouble("GST_AMOUNT"));
            p.setDiscount(rs.getDouble("DISCOUNT"));
            p.setGrandTotal(rs.getDouble("GRAND_TOTAL"));
            p.setUserId(rs.getString("USERID"));
            p.setOrdAmount(rs.getDouble("ORD_AMOUNT"));
            
            OrderList.add(p);
            
        }
        return OrderList;
    }
    
    
    public static ArrayList<Orders> getOrdersByName(Date startDate, Date endDate, String userId) throws SQLException{
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Select * from orders where userid = ? and ord_date between ? and ? ");
        long ms1=startDate.getTime();
        long ms2=endDate.getTime();
        java.sql.Date d1=new java.sql.Date(ms1);
        java.sql.Date d2=new java.sql.Date(ms2);
        ps.setString(1, userId);
        ps.setDate(2, d1);
        ps.setDate(3,d2);
        
        ArrayList<Orders> orderList=new ArrayList();
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            Orders o=new Orders();
            o.setOrdId(rs.getString("ord_id"));
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
            java.sql.Date d=rs.getDate("ord_date");
            String dateStr=sdf.format(d);
            o.setOrdDate(dateStr);
            o.setOrdAmount(rs.getDouble("ord_amount"));
            o.setGst(rs.getDouble("gst"));
            o.setGstAmount(rs.getDouble("gst_amount"));
            o.setGrandTotal(rs.getDouble("grand_total"));
            o.setDiscount(rs.getDouble("discount"));
            o.setUserId(rs.getString("userid"));
            orderList.add(o);
        }
        return orderList;
    }
    
    public static String getNewId() throws SQLException{
        Connection conn= DBConnection.getConnection();
        Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery("Select count(*) from orders");
        int id= 101;
        if(rs.next()){
            id=id+rs.getInt(1);
        }
        String sr= "OD"+id;
        return sr;
        
    }
    
    public static Boolean addOrder(Orders order, ArrayList<OrderDetail> orderList) throws Exception {
        Connection conn=DBConnection.getConnection();
        PreparedStatement ps=conn.prepareStatement("Insert into orders values(?,?,?,?,?,?,?,?)");
        ps.setString(1, order.getOrdId());
        
        String dateStr=order.getOrdDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        java.util.Date d1=sdf.parse(dateStr);
        java.sql.Date d2=new java.sql.Date(d1.getTime());
        ps.setDate(2, d2);
        
        ps.setDouble(3, order.getGst());
        ps.setDouble(4, order.getGstAmount());
        ps.setDouble(5, order.getDiscount());
        ps.setDouble(6, order.getGrandTotal());
        ps.setString(7, order.getUserId());
        ps.setDouble(8,order.getOrdAmount());
        
        int x=ps.executeUpdate();
        
        PreparedStatement ps2=conn.prepareStatement("insert into order_details values(?,?,?,?)");
        int count=0,y;
        for(OrderDetail detail:orderList){
            ps2.setString(1, detail.getOrdid());
            ps2.setString(2, detail.getProdid());
            ps2.setDouble(3, detail.getQuantity());
            ps2.setDouble(4, detail.getCost());
            y=ps2.executeUpdate();
            count=count+y;
        }
        
        if(x>0 && count==orderList.size())
            return true;
        else
            return false;
        
        
    }
}
