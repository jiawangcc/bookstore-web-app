<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
    
<%	session.invalidate(); %>

<script>
	alert("You logged out successfully");
	window.location.href="index.jsp";
</script>