package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class transactionDB {
	private static String dbURL = "jdbc:mysql://localhost:3306/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	
	//use for setting productId when insert a new transaction into db.
	public static int getTransactionId(){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		int tId = -1;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "select max(transactionId) from transactions";
			stmt = conn.prepareStatement(query);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			tId = rs.getInt(1);

			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn);
		}
		
		return tId;
	}
	
	//check amount
		public static int[] checkAmount(int pId, int q){
			
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			int[] result = {0,0,0};
			try{

				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
				
				String query = "select amount from books where productId = ?";
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, pId);
				rs = stmt.executeQuery();
				rs.next();
				int quantity = rs.getInt(1);
				
				if(q > quantity){
					result[0]=1;
					result[1]=pId;
					result[2]=q;
					return result;
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				closeAll(stmt, conn);
			}
			
			return result;
		}
		
	//insert a transaction
	public static boolean insertTransaction(String email, int tId, int pId, int q){
		
		boolean result = true;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{

			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);

			String query = "select amount from books where productId = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, pId);
			rs = stmt.executeQuery();
			rs.next();
			int quantity = rs.getInt(1);

			query = "select customerId from customers where email = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			rs.next();
			int cId = rs.getInt(1);

			query = "insert into transactions values(?,?,?,?, NOW())";
			stmt = conn.prepareStatement(query);

			stmt.setInt(1, tId + 1);
			stmt.setInt(2, cId);
			stmt.setInt(3, pId);
			stmt.setInt(4, q);

			int i = stmt.executeUpdate();
			if (i == 0) {
				result = false;
			}

			query = "update books set amount = ? where productId = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, quantity - q);
			stmt.setInt(2, pId);
			stmt.executeUpdate();
			
			
		}catch(Exception e){
			result = false;
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn);
		}
		
		return result;
	}
	
	// show all transations
			
	public static ArrayList<Trans> showTrans(){
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Trans> trans = new ArrayList();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select productId, customerId, productId, date from user order by date";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Trans t = new Trans();
				t.setTransactionId(rs.getInt(1));
				t.setCustomerId(rs.getInt(2));
				t.setProductId(rs.getInt(3));
				t.setDate(rs.getDate(4));
				trans.add(t);
			
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return trans;
	}
	
	// show transactions for one particular customer
	
	public static ArrayList<Trans> showTransForCustomer(String email){
	
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Trans> trans = new ArrayList();
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
			
			query = "select transactionId, productId, quantity, date from transactions where customerId = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, cId);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Trans t = new Trans();
				t.setTransactionId(rs.getInt(1));
				t.setCustomerId(cId);
				t.setProductId(rs.getInt(2));
				t.setQuantity(rs.getInt(3));
				t.setDate(rs.getDate(4));
				trans.add(t);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return trans;
	}
	
	// show report weekly or monthly	
	public static ArrayList<Double> showProfit(String type){
			
			int flag = 0;
			double cost = 0;
			double price = 0;
			double profit = 0;
			Connection conn = null;
			PreparedStatement stmt = null;
			String query = null;
			ResultSet rs = null;
			ArrayList<Trans> trans = new ArrayList();
			ArrayList<Integer> pid = new ArrayList();
			ArrayList<Integer> quan = new ArrayList();
			ArrayList<Double> rt = new ArrayList();
			try{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
				if (type.equals("weekly")){
					query = "select productId, quantity from transactions where date between '2016-02-01' and '2016-02-15'";
					stmt = conn.prepareStatement(query);
					rs = stmt.executeQuery();
					while(rs.next()){
						pid.add(rs.getInt(1));
						quan.add(rs.getInt(2));		
					}
					for(int i=0; i<pid.size(); i++){
						query = "select cost, price from books where productId = ?";
						stmt = conn.prepareStatement(query);
						stmt.setInt(1, pid.get(i).intValue());
						rs = stmt.executeQuery();
						rs.next();
						cost += rs.getDouble(1) * quan.get(i).intValue();
						price += rs.getDouble(2) * quan.get(i).intValue();			
					}
					profit = price - cost;
					rt.add(cost);
					rt.add(price);
					rt.add(profit);	
				}
				if (type.equals("monthly")){
					query = "select productId, quantity from transactions where date between '2016-02-10' and '2016-03-10'";
					stmt = conn.prepareStatement(query);
					rs = stmt.executeQuery();
					while(rs.next()){
						pid.add(rs.getInt(1));
						quan.add(rs.getInt(2));		
					}
					for(int i=0; i<pid.size(); i++){
						query = "select cost, price from books where productId = ?";
						stmt = conn.prepareStatement(query);
						stmt.setInt(1, pid.get(i).intValue());
						rs = stmt.executeQuery();
						rs.next();
						cost += rs.getDouble(1) * quan.get(i).intValue();
						price += rs.getDouble(2) * quan.get(i).intValue();				
					}
					profit = price - cost;	
					rt.add(cost);
					rt.add(price);
					rt.add(profit);
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				closeAll(stmt, conn, rs);
			}	
			return rt;
		}
		
	// show valued customers
	public static ArrayList<ArrayList<String>> showVauleCustomers(){
		
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<ArrayList<String>> customers = new ArrayList<ArrayList<String>>();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select customerId, tid, category from (select customerId, count(distinct transactionId) as tid, category from transactions join books using(productId) where date between '2016-02-01' and '2016-02-29' group by category, customerId ) as t where tid>1";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			int i =0;
			while(rs.next()){
				ArrayList<String> single = new ArrayList<String>();
				single.add(Integer.toString(rs.getInt(1)));
				single.add(Integer.toString(rs.getInt(2)));
				single.add(rs.getString(3));
				customers.add(single);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return customers;
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
