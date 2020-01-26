/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planetfood.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author Jonty
 */
public class DBConnection {
    private static Connection conn;
    static{
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            conn=DriverManager.getConnection("jdbc:oracle:thin:@//AFTAB:1521/XE","planetfood","students");
            JOptionPane.showMessageDialog(null, "Connected Successfully To The Database","Success!",JOptionPane.INFORMATION_MESSAGE);
           }
        catch(Exception ex){
        JOptionPane.showMessageDialog(null, "Error Connecting To The Database:"+ex,"Error!",JOptionPane.ERROR_MESSAGE);
        System.out.println("Exception is:"+ex);
        }
    }
    public static Connection getConnection(){
        return conn;
    }

    public static void closeConnection()
    {
        
    }
}
