package CSS490;

import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class userController
 */
public class adminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public adminController() {
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
		if(requestURI.endsWith("login")){
			url = login(request);
		}
		
		response.sendRedirect(url);
	}
	
	private String login (HttpServletRequest request){
		
		String url = "";
		boolean flag = false;
        
		String passwd = request.getParameter("password");
		String email = request.getParameter("email");
		
		request.getSession().setAttribute("adminname", email);
		flag = adminDB.checkLogin(email,passwd);
		if(flag){
			url = "/bookstore/manageBook.jsp";
		}else{
		
		    url = "/bookstore/loginError.jsp";
		}
		
		return url;
	}
}
