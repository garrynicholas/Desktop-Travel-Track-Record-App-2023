/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;
import com.mysql.cj.jdbc.Driver;
import java.sql.*;

/**
 *
 * @author USER
 */

public class koneksi {
   private static Connection koneksi;
   
   public static Connection GetConnection()throws SQLException {
   if(koneksi == null) {
       new Driver();
       
       koneksi = DriverManager.getConnection("jdbc:mysql://localhost:3306/rekamjejak_gunawan","root","");
   }
   
   return koneksi;
   }
   
}
