<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, java.util.*, CSS490.* "%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/signin.css" rel="stylesheet">
<title>Error</title>
</head>
<body>

<%
 
Book b = bookDB.showBookInfo(Integer.parseInt(request.getParameter("productId")));
%>
<h1>Your purchase of book: <%=b.getTitle()%> quantity: <%=request.getParameter("amount")%> can't be processed.</h1>
<h1>The available amount is <%=b.getAmount()%>.</h1>

</body>
</html>