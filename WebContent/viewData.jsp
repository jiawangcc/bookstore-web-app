<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat, CSS490.*" %>

<% String category = request.getParameter("category");
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
<link href="css/admin.css" rel="stylesheet">
<title>Book List</title>
</head>
<body>

<p><a href="index.jsp">Home</a> | Hi admin! | <a href="manageBook.jsp">Manage book</a> | <a href="viewData.jsp">View business data</a></p>

<center>
<h1>Weekly report</h1>
<% ArrayList<Double> rt = transactionDB.showProfit("weekly"); 
%>
<table>
    <tr>
            <td> Cost</td>
            <td> Sale</td>
            <td> Profit</td>
    <tr>
<tr>

    <td width="20%"><%=dF.format(rt.get(0).doubleValue())%></td>
    <td width="20%"><%=dF.format(rt.get(1).doubleValue()) %></td>
    <td width="20%"><%=dF.format(rt.get(2).doubleValue())%></td>

</tr>
</table>
</center>
<center><br>
<h1>Monthly report</h1>
<% ArrayList<Double> mrt = transactionDB.showProfit("monthly"); 
%>
<table>
    <tr>
            <td> Cost</td>
            <td> Sale</td>
            <td> Profit</td>
    <tr>
<tr>

    <td width="20%"><%=dF.format(mrt.get(0).doubleValue()) %></td>
    <td width="20%"><%=dF.format(mrt.get(1).doubleValue())%></td>
    <td width="20%"><%=dF.format(mrt.get(2).doubleValue())%></td>

</tr>
</table>

<center><br>
<h1>Value customer list</h1>
</center>
<table>
    <tr>
        <td>Customer Id</td>
        <td>Quantity of trans</td>
        <td>Category</td>
    </tr>
<% ArrayList<ArrayList<String>> customers = transactionDB.showVauleCustomers();
for (int i=0; i<customers.size(); i++){
	ArrayList<String> single = customers.get(i);	
%>
<tr>
    <td width="20%"><%=single.get(0) %></td>
    <td width="20%"><%=single.get(1) %></td>
    <td width="20%"><%=single.get(2) %></td>
</tr>
<%	}
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