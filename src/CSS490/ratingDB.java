package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class ratingDB {
	private static String dbURL = "jdbc:mysql://localhost:3306/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	
	//insert a user
	public static boolean insertRating(String email, int pId, int r){
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select customerId from customers where email = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int cId = rs.getInt(1);
			
			query = "insert into ratings values(?,?,?, NOW())";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, pId);	
			stmt.setInt(2, cId);	
			stmt.setInt(3, r);	
			
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
	
	// sum all ratings for one book
			
	public static double sumRatings(int productId){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int total = 0;
		int n = 0;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select rating from ratings where productId = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, productId);
			rs = stmt.executeQuery();
			
			while(rs.next()){				
				total += rs.getInt(1);
				n++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return (double)total/(double)n;
	}
	
	// show all ratings for one particular customer
	
	public static ArrayList<Rating> showRatingsForCustomer(String email){
	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Rating> ratings = new ArrayList();
		int cId=0;
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select customerId from customers where email = ?";

			stmt = conn.prepareStatement(query);
	
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if(rs.next()){
				cId = rs.getInt(1);
			}else{
				return null;
			}
			
			query = "select productId, customerId, rating, date from ratings where customerId = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, cId);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Rating r = new Rating();
				r.setProductId(rs.getInt(1));
				r.setCustomerId(cId);
				r.setRating(rs.getInt(3));
				r.setDate(rs.getDate(4));
				ratings.add(r);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return ratings;
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
