package dietchallenge;

import java.sql.Connection;
import java.sql.DriverManager;

public class DietDBManager {
	Connection conn = null;
	   
	   String driver = "oracle.jdbc.driver.OracleDriver";
	   String url = "jdbc:oracle:thin:@localhost:1521:xe";
	   String user = "dietchallenge";
	   String pw = "1234";
	   
	   public Connection getConnection() {
	      
	      try {
	         Class.forName(driver);
	         conn = DriverManager.getConnection(url,user,pw);
	      }catch(Exception e) {
	         e.printStackTrace();
	      }
	      return conn;
	   }

}
