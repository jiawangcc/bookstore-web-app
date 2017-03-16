<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*, CSS490.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/mystyles.css" rel="stylesheet">
<title>Manage Book</title>
</head>
<body>

<p><a href="index.jsp">Home</a> | Hi admin! | <a href="manageBook.jsp">Manage book</a> | <a href="viewData.jsp">View business data</a>
 | <a href="signOut.jsp">Sign Out</a></p>

<form name = "add" enctype="multipart/form-data" method = "post" action="book/add">
Title:<input type="text" name="title" required><br><br>
Amount:<input type="number" min="0" step="1" name="amount" required><br><br>
Author:<input type="text" name="author" required><br><br>
Cost:<input type="number" min="0" step="any" name="cost" required/><br><br>
Price:<input type="number" min="0" step="any" name="price" required><br><br>
Category:<select name="category">
			<option value="Arts">Arts</option>
			<option value="Fiction">Fiction</option>
			<option value="Science">Science</option>
			<option value="Business">Business</option>
			<option value="History">History</option>
		</select><br><br>
Book cover image: <input type="file" name="coverPhoto" required><br><br>
<input type="submit" value ="Add">
</form>

<p></p>

<form name="modify" method="post">
<table id="list">
	<tr>
		<th>Cover</th>
		<th>Title</th>
		<th>Author</th>
		<th>Amount</th>
		<th>Cost</th>
		<th>Price</th>
		<th>Category</th>
		<th></th>
	</tr>
<%
	ArrayList<Book> books = bookDB.showBooks();
	for(Book b: books){
%>
<tr>
	<td width="10%"><img src="images/<%=b.getProductId()%>.jpg" alt="no images" style="width:150px;height:200px;"/></td>
	<td width="10%"><%=b.getTitle()%></td>
	<td width="10%"><%=b.getAuthor()%></td>
	<td width="10%"><input type="number" min="0" name="amt<%=b.getProductId()%>" id="amt<%=b.getProductId()%>" value="<%=b.getAmount()%>" readonly></td>
	<td width="10%"><input type="number" min="0" name="cost<%=b.getProductId()%>" id="cost<%=b.getProductId()%>" value="<%=b.getCost()%>" readonly></td>
	<td width="10%"><input type="number" min="0" name="price<%=b.getProductId()%>" id="price<%=b.getProductId()%>" value="<%=b.getPrice()%>" readonly></td>
	<td width="10%"><%=b.getCategory()%></td>
	<td width="10%">
		<a id="modifyLink<%=b.getProductId()%>" style="display:block" href="javascript:bookModify('<%=b.getProductId()%>');">[modify]</a>
		<a id="updateLink<%=b.getProductId()%>" style="display:none" href="javascript:bookUpdate('<%=b.getProductId()%>');">[update]</a>
		<a href="javascript:bookDelete('<%=b.getProductId()%>');">[delete]</a>
	</td>
</tr>
<%
	}
%>
</table>
</form>
<form name="bookUpdate" method="post" action="book/update">
<input type="hidden" name="productId" id="productIdToUpdate">
<input type="hidden" name="amt" id="amt">
<input type="hidden" name="cost" id="cost">
<input type="hidden" name="price" id="price">
</form>
<form name="bookDelete" method="post" action="book/delete">
<input type="hidden" name="productId" id="productIdToDel">
</form>

<script>

	function toggle_readonly_state(textBoxId){
	   var textBox = document.getElementById(textBoxId);
	    if(textBox.hasAttribute('readonly')){   
	        textBox.removeAttribute('readonly');
	    }else{       
	        textBox.setAttribute('readonly', 'readonly');   
	    }
	}
	
    function toggle_visibility(id) {
        var e = document.getElementById(id);
        if(e.style.display == 'block')
           e.style.display = 'none';
        else
           e.style.display = 'block';
     }
	
	function bookModify(productId){
		toggle_readonly_state("amt" + productId);
		toggle_readonly_state("cost" + productId);
		toggle_readonly_state("price" + productId);
		toggle_visibility("modifyLink" + productId);
		toggle_visibility("updateLink" + productId);
	}
	
	function bookUpdate(productId){
		
		document.getElementById("productIdToUpdate").value= productId;
		
		var newQuantity = document.getElementById("amt" + productId).value;
		if (!/^\+?(0|[1-9]\d*)$/.test(newQuantity)) {
			alert(newQuantity + " is not a valid integer.");
			return;
		}
		toggle_readonly_state("amt" + productId);
		toggle_readonly_state("cost" + productId);
		toggle_readonly_state("price" + productId);
		
		document.getElementById("amt").value= newQuantity;
		
		document.getElementById("cost").value= document.getElementById("cost" + productId).value;
		document.getElementById("price").value= document.getElementById("price" + productId).value;
		
		document.bookUpdate.submit();
	}

	function bookDelete(productId){
		document.getElementById("productIdToDel").value= productId;
		document.bookDelete.submit();
	}
</script>
</body>
</html>