<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,edu.uw.jiawang.*" %>
<%
if(session.getAttribute("role") == null || !((String) session.getAttribute("role")).equalsIgnoreCase("admin")) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Educating Young Eye Web Portal - Login</title>
<script src="jquery-3.1.1.min.js" type="text/javascript"></script>
<link href="portal.css" rel="stylesheet" type="text/css">
<style>
#header {
	background-size: 30%;
	background-repeat: no-repeat;
	height: 170px;
	padding-top: 0;
	padding-right: 10px;
	padding-bottom: 0;
	padding-left: 20px;
	text-align: center;
	color: #00b3b3;
	vertical-align: bottom;
}
</style>
</head>

<body>
	<div id="container">

		<div id="header">
			<a name="top"></a>
			<div align="right">
				<img src="image/logo.jpg">
			</div>
			<h1>Administrator Portal</h1>
		</div>


		<div id="navigation">
  <a class = "current" href="registerRequest.jsp">Verify user request</a>
  <a href="listStudent.jsp">Edit student</a> 
  <a href="uploadProjectPaper.jsp">Team resource</a> 
  <a href="uploadTestResult.jsp">Upload test result</a> 
		</div>

		<div id="mainContent">
		<p>&nbsp;</p>
		<p align="right">Welcome admin | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>

			<h2>New user account request</h2>
<table>
  <tr>
    <th>Account role</th>
    <th>Name</th>
    <th>Affiliation</th>
    <th>Email</th>
    <th>Contact person</th>
    <th>Contact phone</th>
    <th>Accept</th>
    <th>Decline</th>
  </tr>
  
  	<%
  	    List<Register> entries = RegisterDB.showAllRegisterEntries();
		for( Register r : entries){	
	%>
  <tr>
    <td><%=r.getRole() %></td>
    <td><%=r.getFirstname() %> <%=r.getLastname() %></td>
    <td><%=r.getAffiliation() %></td>
    <td><%=r.getEmail() %></td>
    <td><%=r.getContact() %></td>
    <td><%=r.getPhone() %></td>
    <td><input type="button" class="button" value="Approve" onclick="approve('<%=r.getRole() %>','<%=r.getEmail() %>')"/></td>
    <td><input type="button" class="button" value="Reject" onclick="reject('<%=r.getRole() %>','<%=r.getEmail() %>')"/></td>
  </tr>
  <%} %>
</table>
<div id="register-status" align="center"></div>
<script type="text/javascript">
	function approve(role, email) {
		goAhead = confirm("Approve " + email + " for " + role + "?");
		if (goAhead) {
			$.ajax({
				  url: "register/approve",
				  data: {
				    role: role, email: email
				  },
				  success: function( result ) {
				    $( "#register-status" ).html( "<strong>" + result + "</strong><br /> Refreshing page ..." );
				  }
				});
			setTimeout(function(){
			    location.reload();
			}, 3000);
		}
	}
	function reject(role, email) {
		goAhead = confirm("Reject " + email + " for " + role + "?");
		if (goAhead) {
			$.ajax({
			  url: "register/reject",
			  data: {
				  role: role, email: email
			  },
			  success: function( result ) {
			    $( "#register-status" ).html( "<strong>" + result + "</strong><br /> Refreshing page ..." );
			  }
			});
			setTimeout(function(){
			    location.reload();
			}, 3000);
		}
	}
</script>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<p>&nbsp;</p>

			<h4 class="floatright top">&nbsp;</h4>

		</div>

		<!-- This clearing element should immediately follow the #mainContent div in order to force the #container div to contain all child floats -->

		<!-- end #container -->

		<div id="footer" align="center">

			<p>
				<a href="http://educatingyoungeyes.com/">EYE conference site</a> | <a
					href="http://www.uwb.edu/">UW Bothell</a> | <a href="">Contact
					us</a>
			</p>

			<p>
				<a href="">About the Site</a> | <a href="">Site
					Map</a> | <a href="">Sources</a>
			</p>

		</div>

	</div>
</body>
</html>