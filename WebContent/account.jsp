<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*, java.text.DecimalFormat,CSS490.*" %>  

<%
String email = (String) session.getAttribute("email");
String name = (String) session.getAttribute("name");
%>

<%!
DecimalFormat dF = new DecimalFormat("#.00");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/mystyles.css" rel="stylesheet">
<title>My Account</title>
</head>
<body>

<p><a href="index.jsp">Home</a> | 
<%if(name != null) {
	out.print("Hi, " + name +" | <a href=\"signOut.jsp\">Sign Out</a>");
	} else {
		response.sendRedirect("signIn.jsp");
} %> | <a href="account.jsp">Your Account</a> | <a href="shoppingCart.jsp">Shopping Cart</a></p>

<h1>Order History</h1></br>


   <table>
   		<thead><td>Title:</td><td>Author:</td><td>Quantity:</td><td>Total:</td><td>Date:</td>
<%
	ArrayList<Trans> trans = transactionDB.showTransForCustomer(email);

    if(trans==null){
    	out.print("You have no order history.");
    }else{
    	for(Trans t: trans){
    		Book b = bookDB.showBookInfo(t.getProductId());
    		
%>
		<tr>
			<td width="20%"><%=b.getTitle()%></td>
			<td width="20%"><%=b.getAuthor()%></td>
			<td width="20%"><%=t.getQuantity()%></td>
			<td width="20%"><%=dF.format(b.getPrice()*t.getQuantity())%></td>
			<td width="20%"><%=t.getDate()%></td>
		</tr>

<%     
       }
	}
%>
		
	</table>

<h1>My ratings</h1></br>

   <table>
   		<thead><td>Title:</td><td>Author:</td><td>My Rating:</td><td>Date:</td>
<%
	ArrayList<Rating> ratings = ratingDB.showRatingsForCustomer(email);

    if(ratings==null){
    	out.print("You have no rating history.");
    }else{
    	for(Rating r: ratings){
    		Book b = bookDB.showBookInfo(r.getProductId());
    		
%>
		<tr>
			<td width="20%"><%=b.getTitle()%></td>
			<td width="20%"><%=b.getAuthor()%></td>
			<td width="20%"><%=dF.format(r.getRating())%></td>
			<td width="20%"><%=r.getDate()%></td>
		</tr>
<%     
       }
	}
%>

</body>
</html>