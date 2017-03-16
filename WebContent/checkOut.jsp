<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%	session.removeAttribute("shoppingCart");
String name = (String) session.getAttribute("name");
%>
<html>
<head>
<meta charset="UTF-8">
<link href="css/signin.css" rel="stylesheet">
<title>Thank you</title>
</head>
<body>
<p> <a href="index.jsp">Home</a> | 
<%if(name != null) {
	out.print("Hi, " + name +" | <a href=\"signOut.jsp\">Sign Out</a>");
	} else {
	out.print("<a href=\"signIn.jsp\">Sign In</a>");
} %> | <a href="account.jsp">Your Account</a> | <a href="shoppingCart.jsp">Shopping Cart</a>

<h1> Thank you for shopping with us!</h1>


</body>
</html>