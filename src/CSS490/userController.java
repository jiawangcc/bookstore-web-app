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
public class userController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public userController() {
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
		if(requestURI.endsWith("register")){
			url = registerUser(request);
		}else if(requestURI.endsWith("login")){
			url = login(request);
		}
		
		response.sendRedirect(url);
	}
	
	private String registerUser(HttpServletRequest request){
		String url = "";
        
		String passwd = request.getParameter("passwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		
		int flag = 0;
		flag = userDB.checkUserAvail(email);
		if(flag > 0){
			url = "/bookstore/registerError.jsp";
		}else{
			User user = new User();
			
			user.setPasswd(passwd);
			user.setName(name);
			user.setEmail(email);
			if(userDB.insertUser(user)){
				request.getSession().setAttribute("name", name);
				request.getSession().setAttribute("email", email);
				url = "/bookstore/index.jsp";
			}
		}
		return url;
	}
		
	private String login (HttpServletRequest request){
		
		String url = "";
        
		String passwd = request.getParameter("password");
		String email = request.getParameter("email");
		
		String name = userDB.checkLogin(email,passwd);
		if(name.equals("")){
			url = "/bookstore/loginError.jsp";
		}else{
				request.getSession().setAttribute("email", email);
				request.getSession().setAttribute("name", name);
				url = "/bookstore/index.jsp";
		}
		
		return url;
	}
}
