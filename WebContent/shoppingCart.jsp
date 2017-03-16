<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*, java.text.DecimalFormat, CSS490.*" %>

<%!
DecimalFormat dF = new DecimalFormat("#.00");
 %>

<%
String email = (String) session.getAttribute("email");
String name = (String) session.getAttribute("name");
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/mystyles.css" rel="stylesheet">
<title>Shopping Cart</title>
</head>
<body>

<p><a href="index.jsp">Home</a> | 
<%if(name != null) {
	out.print("Hi, " + name +" | <a href=\"signOut.jsp\">Sign Out</a>");
	} else {
	out.print("<a href=\"signIn.jsp\">Sign In</a>");
} %> | <a href="account.jsp">Your Account</a> | <a href="shoppingCart.jsp">Shopping Cart</a></p>

<%
Map<String, String> shoppingItemMap = (HashMap<String, String>) session.getAttribute("shoppingCart");

if (shoppingItemMap == null) {
	shoppingItemMap = new HashMap<String, String>();
}
String pIdKey = request.getParameter("productId");


if(pIdKey!=null){

	if (shoppingItemMap.containsKey(pIdKey)) {
		
		String quantityToAdd = request.getParameter("quantity"); // from listBook add event
		String newQuantity = request.getParameter("newQuantity"); // from shoppingCart update event
		
		if (quantityToAdd != null) {
			int quantity = Integer.parseInt(quantityToAdd)+Integer.parseInt(shoppingItemMap.get(pIdKey));
			shoppingItemMap.put(pIdKey, String.valueOf(quantity)); 
		}
		
		if (newQuantity != null) {
			int quantity = Integer.parseInt(newQuantity);
			if (quantity == 0) {
				shoppingItemMap.remove(pIdKey);
			} else if (quantity > 0) {
				shoppingItemMap.put(pIdKey, String.valueOf(quantity));
			} 
		}
		
		
	} else {
		
		shoppingItemMap.put(pIdKey, request.getParameter("quantity")); 
		
	}
	
}
session.setAttribute("shoppingCart", shoppingItemMap);
%>


<form action="transaction/insert" method="post">
<table>
	<tr>
		<td>Title</td>
		<td>Author</td>
		<td>Price</td>
		<td>Quantity</td>
		<td>Subtotal</td>
	</tr>
<%
    double total = 0;
	if (shoppingItemMap != null) {
		for (Map.Entry<String, String> entry : shoppingItemMap.entrySet()) {
   			 Book b = bookDB.showBookInfo(Integer.parseInt(entry.getKey()));
%>
	<tr>
		<td width="10%"><%=b.getTitle()%></td>
		<td width="10%"><%=b.getAuthor()%></td>
		<td width="10%">$<%=b.getPrice()%></td>
		<td width="10%"><input type="number" min="0" id = "quantity<%=entry.getKey()%>" name = "<%=entry.getKey()%>" value = "<%=entry.getValue()%>"/></td>
		<td width="10%" id = "subtotal">$<%=dF.format(b.getPrice()*Integer.parseInt(entry.getValue()))%></td>
		<td width="10%"><input id = "update" type="button" value="Modify" onclick="Modify(<%=entry.getKey()%>)"></td>
	</tr>
	
<%	
        total += b.getPrice()*Integer.parseInt(entry.getValue());
	     
		}
	}
     
%>
</table>

<p>Total:$<%=dF.format(total)%></p>
<input type="submit" value="Check Out">
</form>

<form id="updateform" method="post">
	<input type="hidden" name="productId" id="productId" />
	<input type="hidden" name="newQuantity" id="newQuantity" />
</form>

<script>
	function Modify(pID){
		document.getElementById("productId").value = pID;
		var newQuantity = document.getElementById("quantity"+pID).value;
		if (!/^\+?(0|[1-9]\d*)$/.test(newQuantity)) {
			alert(newQuantity + " is not a valid integer.");
			return;
		}
		document.getElementById("newQuantity").value = newQuantity;
		document.getElementById("updateform").submit();
	}

</script>

</body>
</html>