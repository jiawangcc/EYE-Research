<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<%if(session.getAttribute("role") == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Educating Young Eye Web Portal - Login</title>
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
			<h1>EYE Web Portal</h1>
		</div>


		<div id="navigation">
		</div>

		<div id="mainContent">
		<p>&nbsp;</p>
			<h2>Create your new password</h2>

			<form action="Login/change" method="post">
				<div>Email/Id <input type="text" id="id" name="id" required/></div><br>
				<div>Password <input type="password" id="oldPassword" name="oldPassword" required/></div><br>
				<div>New password <input type="password" id="newPassword" name="newPassword" required/></div><br>
				<div>Confirm new password <input type="password" id="newPasswordConfirm" name="newPasswordConfirm" required/></div>
				<div id="feedback1" class="feedback"></div> <br>
				<div><input type="submit" class="button" id="submit" value="Update"/></div>
			</form>
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

 <script>
   		var elMsg1 = document.getElementById('feedback1'); 

			 
		function loginCheck(){
	        if(document.getElementById('newPassword').value!= document.getElementById('newPasswordConfirm').value){
	        	    elMsg1.textContent= "Confirm password doesn't match new password";
	                document.getElementById('feedback1').focus();
	                return false;
	        }
		}
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick = loginCheck;
</script>

</body>
</html>