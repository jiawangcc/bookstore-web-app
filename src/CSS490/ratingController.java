package CSS490;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

/**
 * Servlet implementation class transController
 */
public class ratingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ratingController() {
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
		if(requestURI.endsWith("insert")){
			url = insert(request);
		}
		response.sendRedirect(url);
	}
	
	private String insert(HttpServletRequest request){
		String url = "";
        
		int rating = Integer.parseInt(request.getParameter("rating"));
		String email = (String) request.getSession().getAttribute("email");
		int pId = Integer.parseInt(request.getParameter("pId"));
		
		if (email==null){
			url = "/bookstore/signIn.jsp";
			return url;
		}
		
		boolean flag = false;

		flag = ratingDB.insertRating(email,pId, rating);
		if(flag == false){
			url = "/bookstore/ratingError.jsp";
		}else{
			if ( request.getHeader("referer").endsWith("index.jsp")){
				url = "/bookstore/index.jsp";
			}else{
				url = "/bookstore/bookList.jsp?category="+request.getParameter("category");
			}			
		}
		return url;
	}
	
}
