package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class userDB {
	private static String dbURL = "jdbc:mysql://localhost:3306/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	
	//insert a user
	public static boolean insertUser(User user){
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
//			Context initCtx = new InitialContext();
//			Context envCtx = (Context)initCtx.lookup("java:comp/env");
//			DataSource ds = (DataSource)envCtx.lookup("jdbc/css490");
//			conn = ds.getConnection();
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into customers(name, email, password)"+ 
					"values(?,?,?)";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPasswd());
			
			int i = stmt.executeUpdate();
			if(i > 0){
				result = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn);
		}
		
		return result;
	}
		
	// check whether a user has already signed up or not. 
	
	public static int checkUserAvail(String email){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select * from customers where email = ?");
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if(rs.next()){
				flag = 1;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}
	
	//check whether email and password match and return customer name
	public static String checkLogin(String email, String password){
		
        String name = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("select name from customers where email = ? and password = ?");
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()){
				name=rs.getString(1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return name;
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
	
	private static void closeAll(Statement stmt, Connection conn){
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
	}
}
