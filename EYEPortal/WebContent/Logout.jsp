<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<% boolean checkAdmin = ((String) session.getAttribute("role")).equals("admin");
String des = checkAdmin? "admin":"index";
%>  
<%	session.invalidate(); %>

<script>
    window.location.href="<%=des%>.jsp";
	alert("You logged out successfully");	
</script>