<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<%	session.invalidate(); %>
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
	height: 150px;
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
			
			<table>
			<tr>
			<h2>Please sign in</h2>

			<form action="Login" method="post">
			<p>Parent can use child's information to sign in</p>
				<input type="radio" name="role" value="parent" required> Parent 
				<input type="radio" name="role" value="nurse"> School nurse 
				<input type="radio" name="role" value="doctor"> Doctor 
				<input type="radio" name="role" value="researcher"> Researcher <br><br>
				
				User ID / Email <input type="text" id="id" name="id" required/>
				        <div id="feedback1" class="feedback"></div> <br>
				Password <input type="password" id="password" name="password" required/>
				         <div id="feedback2" class="feedback"></div><br>
				<input type="submit" class="button" id="submit" value="Sign in"/>
			</form>
			
			<p>New to EYE portal? <a href = "register.jsp">Create your account.</a></p>
			</tr>
			</table>
            

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
   		var elMsg2 = document.getElementById('feedback2');
		 
		function loginCheck(){
	        if(document.getElementById('id').value==""){
	        	    elMsg1.textContent= "Pleaes enter your user ID";
	                document.getElementById('id').focus();
	                return false;
	        }
	        if(document.getElementById('password').value==""){
	                elMsg2.textContent = "Please enter your password";
	                document.getElementById('password').focus();
	                return false;
	        }
		}
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick = loginCheck;
</script>

</body>
</html>