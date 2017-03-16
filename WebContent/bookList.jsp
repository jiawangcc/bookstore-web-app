<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat, CSS490.*" %>

<% String category = request.getParameter("category");
String keyword = request.getParameter("keyword");
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
<title>Book List</title>
</head>
<body>
<p><a href="index.jsp">Home</a> | 
<%if(name != null) {
	out.print("Hi, " + name +" | <a href=\"signOut.jsp\">Sign Out</a>");
	} else {
	out.print("<a href=\"signIn.jsp\">Sign In</a>");
} %> | <a href="account.jsp">Your Account</a> | <a href="shoppingCart.jsp">Shopping Cart</a></p>
<table>
	<tr>
		<td>Cover</td>
		<td>Title</td>
		<td>Author</td>
		<td>Price</td>
		<td></td>
		<td></td>
		<td>Overall rating</td>
		<td>Your rating</td>
		<td></td>
	</tr>
<%
	List<Book> books = Collections.EMPTY_LIST;
    List<Book> booktop = Collections.EMPTY_LIST;
    if (category != null) {
    	books = bookDB.showBooks(category);
    	int rank = 5;
    	booktop = bookDB.topAll(rank, category);
    }else if (keyword != null) {
    	books = bookDB.searchBooks(keyword);
    	if (books.isEmpty()){
    		response.sendRedirect("searchError.jsp?keyword="+keyword);
    	}
    }
    for(Book b: books){
	
%>

<tr>
<form action="shoppingCart.jsp" method="post">
	<td width="10%"><img src="images/<%=b.getProductId()%>.jpg" alt="no images" style="width:150px;height:200px;"/></td>
	<td width="10%"><%=b.getTitle()%></td>
	<td width="10%"><%=b.getAuthor()%></td>
	<td width="10%"><%=b.getPrice()%></td>
	<td width="10%">Quantity:
	    <select name="quantity" id="quantityItem<%=b.getProductId()%>">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
		</select>
	<td width="10%"><input type="button" value="Add to cart" onclick="addToCart('<%=b.getProductId()%>')"/></td>
</form>
      
    <form action="rating/insert" method="post">
    <input type="hidden" name="pId" value="<%=b.getProductId()%>" />
    <input type="hidden" name="category" value="<%=category%>" />
      <td width="10%"><%=dF.format(ratingDB.sumRatings(b.getProductId()))%></td>
      <td width="10%">
            <select name="rating">
			      <option value="1">1</option>
			      <option value="2">2</option>
			      <option value="3">3</option>
			      <option value="4">4</option>
			      <option value="5">5</option>
			</select>
      </td>
	  <td width="10%"> <input type="submit" value="Rate this book"/></td>
	</form>
</tr>
<%
 }
%>
</table>
<%
if (category != null) {
	out.print("</table> <center><br><br> <h2>Top 5 bestsellers</h2> </center> <table> <tr> <td>Cover</td> <td>Title</td>"
        + "<td>Author</td> <td>Price</td> <td></td> <td></td> <td>Current rating</td> <td>Your rating</td>"
        + "<td></td> </tr>");
}
    for(Book b: booktop){
    
%>

<tr>
<form action="shoppingCart.jsp" method="post">
    <td width="10%"><img src="images/<%=b.getProductId()%>.jpg" alt="no images" style="width:150px;height:200px;"/></td>
    <td width="10%"><%=b.getTitle()%></td>
    <td width="10%"><%=b.getAuthor()%></td>
    <td width="10%"><%=b.getPrice()%></td>
    <td width="10%">Quantity:
        <select name="quantity" id="quantityItem<%=b.getProductId()%>">
            <option value="1">1</option>
            <option value="2">2</option>
            <option value="3">3</option>
            <option value="4">4</option>
            <option value="5">5</option>
            <option value="6">6</option>
            <option value="7">7</option>
            <option value="8">8</option>
            <option value="9">9</option>
            <option value="10">10</option>
        </select>
    <td width="10%"><input type="button" value="Add to cart" onclick="addToCart('<%=b.getProductId()%>')"/></td>
</form>
      
    <form action="rating/insert" method="post">
    <input type="hidden" name="pId" value="<%=b.getProductId()%>" />
    <input type="hidden" name="category" value="<%=category%>" />
      <td width="10%"><%=dF.format(ratingDB.sumRatings(b.getProductId())) %></td>
      <td width="10%">
            <select name="rating">
                  <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
            </select>
      </td>
      <td width="10%"> <input type="submit" value="Rate this book"/></td>
    </form>
</tr>
<%
 }
%>


</table>


<script>
	function addToCart(productId){
		document.getElementById("productId").value = productId;
		document.getElementById("quantityCopy").value = document.getElementById("quantityItem"+productId).value;
		document.addItemsToShoppingCart.submit();
	}
</script>
<form name="addItemsToShoppingCart" method="post" action="shoppingCart.jsp">
<input type="hidden" name="productId" id="productId">
<input type="hidden" name="quantity" id="quantityCopy">
</form>
</body>
</html>