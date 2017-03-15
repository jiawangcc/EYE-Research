<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
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

			
			<h2>New user account request</h2>
               <form action="register/new" method="post">
               			<div>Please select your role <select name="role">
						  <option value="nurse">School nurse</option>
						  <option value="doctor">Doctor</option>
						  <option value="researcher">Researcher</option>
						</select></div><br>
			    
				* First Name <input type="text" name="firstname" id="firstname" required="required"/><br><br>
				    
				* Last Name <input type="text" name="lastname" id="lastname" required="required"/><br><br>
				
				* Affiliation <input type="text" name="affil" id="affil" required="required"/><br><br>
				    				    
				* Email <input type="text" name="email" id="email" required="required"/><br><br>
			    <p>You need to provide additional contact information</p>
			    <p>for us to verify your identification.</p>
				* Contact Person Name <input type="text" name="contact" id="contact"  required="required"/><br><br>
				
				* Contact Phone <input type="text" name="phone" id="phone"  required="required"/><br><br>
							
				<div id="warning" class="feedback"></div>
				<input type="submit" class="button" value="Sign Up" id="submit"/><br><br>
			
			    <div>* Mandatory Information</div><br/>
		       </form>

<p>Already have an account? <a href = "index.jsp">Sign in</a> </p>

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