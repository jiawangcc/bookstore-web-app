package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class adminDB {
	private static String dbURL = "jdbc:mysql://localhost:3306/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	
	//check whether email and password match
	public static boolean checkLogin(String email, String password){
		
        boolean flag = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select * from admins where email = ? and password = ?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()){
				flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}

	
	private static void closeAll(Statement stmt, Connection conn, ResultSet rs){
		if(stmt != null){
			try{
				stmt.close();
			}catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(SQLException sqle){
			}
		}
		if(rs != null){
			try{
				rs.close();
			}catch(SQLException sqle){
			}
		}
	}
}
