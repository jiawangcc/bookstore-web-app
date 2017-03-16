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
public class transController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public transController() {
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
			url = insertTrans(request);
		}
		
		response.sendRedirect(url);
	}
	
	private String insertTrans(HttpServletRequest request){
		
		String url = "";
		
		if (request.getSession().getAttribute("name")==null){
			url = "/bookstore/signIn.jsp";
			return url;
		}
		
		boolean flag = false;		
		Map<String, String[]> shoppingItemMap = request.getParameterMap();
		
		if (shoppingItemMap == null){
			url = "/bookstore/checkoutError.jsp";
			return url;
		}
		
		int tId = -1;
		
		int[] amountInfo;
		
		Set<Map.Entry<String, String[]>> entrySet = shoppingItemMap.entrySet();
		  
		for(Map.Entry<String, String[]> entry : entrySet) {  
		  
		    int key = Integer.parseInt(entry.getKey());  
		  
		    int value = Integer.parseInt(entry.getValue()[0]);  
		  
		    amountInfo = transactionDB.checkAmount(key, value);
		    
		    if (amountInfo[0]==1){
		    	url =  "/bookstore/amountError.jsp?productId="+amountInfo[1]+"&amount="+amountInfo[2];
		    	return url;
		    }
		  
		}
		
		if (!entrySet.isEmpty()) {
			tId = transactionDB.getTransactionId();
		}
		
		
		for (Map.Entry<String, String[]> entry : entrySet) {  
			  
		    int key = Integer.parseInt(entry.getKey());  
		  
		    int value = Integer.parseInt(entry.getValue()[0]);  
		  
		    flag = transactionDB.insertTransaction((String)request.getSession().getAttribute("email"), tId, key, value);
		  
		}
		
		if(!flag){
			url = "/bookstore/checkoutError.jsp";
		}else{
			url = "/bookstore/checkOut.jsp";
		}
		
		return url;
	}

}
