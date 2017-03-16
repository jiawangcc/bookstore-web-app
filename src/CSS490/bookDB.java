package CSS490;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;
import java.util.*;

public class bookDB {
	private static String dbURL = "jdbc:mysql://localhost:3306/CSS490";
	private static String dbUser = "css490";
	private static String dbPass = "css490pass";
	
	//insert a new book
	public static int insertBook(Book b){
		int bookId = -1;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			
			String query = "insert into books(title, amount, cost, price, author, category) values(?,?,?,?,?,?)";
			
			stmt = conn.prepareStatement(query);
			stmt.setString(1, b.getTitle());
			stmt.setInt(2, b.getAmount());
			stmt.setDouble(3, b.getCost());
			stmt.setDouble(4, b.getPrice());
			stmt.setString(5, b.getAuthor());
			stmt.setString(6, b.getCategory());
			
			int i = stmt.executeUpdate();
			if(i > 0){
				query = "select productId from books where title='"+b.getTitle()+"'";
				stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery();
				rs.next();
				bookId = rs.getInt(1);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn);
		}
		
		return bookId;
	}
	
	// show all books of one particular category
			
	public static ArrayList<Book> showBooks(String category){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Book> books = new ArrayList();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select productId, title, amount, price, author from books where category = ?";
			stmt = conn.prepareStatement(query);
			stmt.setString(1, category);
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Book b = new Book();
				b.setProductId(rs.getInt(1));
				b.setTitle(rs.getString(2));
				b.setAmount(rs.getInt(3));
				b.setPrice(rs.getDouble(4));
				b.setAuthor(rs.getString(5));
				books.add(b);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return books;
	}
	
	//show all books
	public static ArrayList<Book> showBooks(){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Book> books = new ArrayList();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select productId, title, amount, cost, price, author, category from books order by productId";
			stmt = conn.prepareStatement(query);;
			rs = stmt.executeQuery();
			
			while(rs.next()){
				Book b = new Book();
				b.setProductId(rs.getInt(1));
				b.setTitle(rs.getString(2));
				b.setAmount(rs.getInt(3));
				b.setCost(rs.getDouble(4));
				b.setPrice(rs.getDouble(5));
				b.setAuthor(rs.getString(6));
				b.setCategory(rs.getString(7));
				books.add(b);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return books;
	}
	// show a book information
	
	public static Book showBookInfo(int productId){
			
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Book b = new Book();
		b.setProductId(productId);
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select title, amount, cost, price, author, category from books where productId = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, productId);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				b.setTitle(rs.getString(1));
				b.setAmount(rs.getInt(2));
				b.setCost(rs.getDouble(3));
				b.setPrice(rs.getDouble(4));
				b.setAuthor(rs.getString(5));
				b.setCategory(rs.getString(6));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return b;
	}
	
	
	public static ArrayList<Book> searchBooks(String keyword){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Book> books = new ArrayList<Book>();
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "select title, price, author, category, productId from books where title like '%" + keyword + "%' or author like '%" + keyword + "%'";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
						
			while(rs.next()){
				Book b = new Book();
				b.setTitle(rs.getString(1));
				b.setPrice(rs.getDouble(2));
				b.setAuthor(rs.getString(3));
				b.setCategory(rs.getString(4));
				b.setProductId(rs.getInt(5));
				books.add(b);
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt,conn,rs);
		}
		return books;
	}
	// modify a book's information
	
	public static int updateBook(Book b){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			String query = "update books set amount=?, cost=?, price=? where productId = ?";
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, b.getAmount());
			stmt.setDouble(2, b.getCost());
			stmt.setDouble(3, b.getPrice());
			stmt.setInt(4, b.getProductId());

			flag = stmt.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}
		return flag;
	}
	
	//delete a book
	
	public static int deleteBook(int productId){
		int flag = 0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			stmt = conn.prepareStatement("delete from books where productId = ?");
			stmt.setInt(1, productId);
			flag = stmt.executeUpdate();
			
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
	
	// show top sales
public static ArrayList<Book> topAll(int rank, String type){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ResultSet rss = null;
		String query = null;
		ArrayList<Book> books = new ArrayList();
		ArrayList<Integer> bookId = new ArrayList();
		ArrayList<Integer> bookSale = new ArrayList();
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
			if(type == null){
				query = " select p.productId, sum(p.quantity) as a from(select productId, quantity from transactions where date between '2016-02-15' and '2016-03-10') as p group by p.productId order by a desc limit ?";
				stmt = conn.prepareStatement(query);
				stmt.setInt(1, rank);
				rs = stmt.executeQuery();
				while(rs.next()){
					bookId.add(rs.getInt(1));
					bookSale.add(rs.getInt(2));
				}
				
				for(int i=0; i<rank; i++){
					query ="select productId, title, amount, cost, price, author, category from books where productId = ?";			
					stmt = conn.prepareStatement(query);
					stmt.setInt(1, bookId.get(i).intValue());
					rss = stmt.executeQuery();
					rss.next();
					Book b = new Book();
					b.setProductId(rss.getInt(1));
					b.setTitle(rss.getString(2));
					b.setAmount(rss.getInt(3));
					b.setCost(rss.getDouble(4));
					b.setPrice(rss.getDouble(5));
					b.setAuthor(rss.getString(6));
					b.setCategory(rss.getString(7));
					books.add(b);
				}
				
			}
			else{
				query = "select productId, count(productId) as best from transactions group by productId order by best desc";
				stmt = conn.prepareStatement(query);
				rs = stmt.executeQuery();
				int n=0;
				while(rs.next()){
					bookId.add(rs.getInt(1));
					bookSale.add(rs.getInt(2));
					n++;
				}
				for(int i=0; i<n; i++){
					query ="select productId, title, amount, cost, price, author, category from books where productId = ?";			
					stmt = conn.prepareStatement(query);
					stmt.setInt(1, bookId.get(i).intValue());
					rss = stmt.executeQuery();
					rss.next();
					Book b = new Book();
					b.setProductId(rss.getInt(1));
					b.setTitle(rss.getString(2));
					b.setAmount(rss.getInt(3));
					b.setCost(rss.getDouble(4));
					b.setPrice(rss.getDouble(5));
					b.setAuthor(rss.getString(6));
					b.setCategory(rss.getString(7));
					if(b.getCategory().equals(type)){
						books.add(b);
					}
					if(books.size()>=rank){break;}
				}
			}
					
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeAll(stmt, conn, rs);
		}	
		return books;
	}
// show top ratings
public static ArrayList<Book> topRating(){
	
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;
	ResultSet rss = null;
	ArrayList<Book> books = new ArrayList();
	ArrayList<Integer> bookId = new ArrayList();
	
	try{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		//String query = " select productId from ratings order by rating desc limit 5";
		String query = "select productId, avg(rating) as avg_rating from ratings group by productId order by avg_rating desc limit 5";
		stmt = conn.prepareStatement(query);;
		rs = stmt.executeQuery();
		
		while(rs.next()){
			bookId.add(rs.getInt(1));
		}
		
		for(int i=0; i<5; i++){
			query ="select productId, title, amount, cost, price, author, category from books where productId = ?";			
			stmt = conn.prepareStatement(query);
			stmt.setInt(1, bookId.get(i).intValue());
			rss = stmt.executeQuery();
			rss.next();
			Book b = new Book();
			b.setProductId(rss.getInt(1));
			b.setTitle(rss.getString(2));
			b.setAmount(rss.getInt(3));
			b.setCost(rss.getDouble(4));
			b.setPrice(rss.getDouble(5));
			b.setAuthor(rss.getString(6));
			b.setCategory(rss.getString(7));
			books.add(b);
		}
		
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		closeAll(stmt, conn, rs);
	}	
	return books;
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
