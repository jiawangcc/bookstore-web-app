package CSS490;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class userController
 */
@MultipartConfig(location="/Applications/apache-tomcat-7.0.67/wtpwebapps/bookstore/images", fileSizeThreshold=1024*1024, 
maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)

public class bookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bookController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String requestURI = request.getRequestURI(); //return value of action
		System.out.println(requestURI); 
		String url = "";
		if(requestURI.endsWith("add")){
			url = addBook(request);
		}else if(requestURI.endsWith("update")){
			url = updateBook(request);
		}else if(requestURI.endsWith("delete")){
			url = deleteBook(request);
		}
		
		response.sendRedirect(url);
	}
	
	private String addBook(HttpServletRequest request){
		Collection<Part> parts = Collections.emptySet();
		try {
			parts = request.getParts();
		} catch (IllegalStateException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ServletException e1) {
			e1.printStackTrace();
		}
		
		if (parts.size() != 7) {
			throw new IllegalArgumentException();
		}
		
		String url = "";

		String title = request.getParameter("title");
		int amount = Integer.parseInt(request.getParameter("amount"));
		double cost = Double.parseDouble(request.getParameter("cost"));
		double price = Double.parseDouble(request.getParameter("price"));
		String author = request.getParameter("author");
		String category = request.getParameter("category");

		Book b = new Book();

		b.setTitle(title);
		b.setAmount(amount);
		b.setCost(cost);
		b.setPrice(price);
		b.setAuthor(author);
		b.setCategory(category);
		
		int bookId = bookDB.insertBook(b);
		if (bookId != -1) {
			url = "/bookstore/manageBook.jsp";
		
			try{
				Part filePart = request.getPart("coverPhoto");
			       InputStream imageInputStream = filePart.getInputStream();
			       //read imageInputStream
			       filePart.write(bookId + ".jpg");
			}catch(IOException ioe){
				System.out.println(ioe);
			}catch(Exception e){
				System.out.println(e);
			}
		}

		return url;
	}
	
	private String updateBook(HttpServletRequest request){
		String url = "";
		
		int productId = Integer.parseInt(request.getParameter("productId"));
		int amount = Integer.parseInt(request.getParameter("amt"));
		double cost = Double.parseDouble(request.getParameter("cost"));
		double price = Double.parseDouble(request.getParameter("price"));
		
		Book b = new Book();
		b.setProductId(productId);
		b.setAmount(amount);
		b.setCost(cost);
		b.setPrice(price);
		
		int flag = 0;
		flag = bookDB.updateBook(b);
		if(flag > 0){
			url = "/bookstore/manageBook.jsp";
		}
		return url;
	}
	
	private String deleteBook(HttpServletRequest request){
		String url = "";
		int productId = Integer.parseInt(request.getParameter("productId"));
		int flag = 0;
		flag = bookDB.deleteBook(productId);
		if(flag > 0){
			url = "/bookstore/manageBook.jsp";
		}
		return url;
	}
	
	 
/*	private static void copyFileUsingFileStreams(File source, File dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}*/
}
