<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<% if(session.getAttribute("role") == null || !((String) session.getAttribute("role")).equalsIgnoreCase("admin")) {
	response.sendRedirect(request.getContextPath() + "/admin.jsp");
	return;
}%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Educating Young Eye - Researcher Portal</title>
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
    <div align="right"><img src="image/logo.jpg"> </div>
    <h1>Administrator Portal</h1>
  </div>


  <div id="navigation">
  <a href="registerRequest.jsp">Verify user request</a>
  <a href="listStudent.jsp">Edit student</a> 
  <a href="uploadProjectPaper.jsp">Team resource</a> 
  <a href="uploadTestResult.jsp" class = "current">Upload test result</a> 
  </div>

  

  <div id="mainContent">
  <p>&nbsp;</p>
  <p align="right">Welcome admin | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
      
    <h2><a name="upload"></a>Upload test result via csv</h2>
    <form action="Upload/dataset" method="post" enctype="multipart/form-data">
        file: <input type="file" name="document" id="csv" required><br><br>
    <button type="submit" class="button" id="InputFile" onclick="return checkFile()"> Upload </button>
    </form>
    <p>&nbsp;</p>
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

    <p><a href="http://educatingyoungeyes.com/">EYE conference site</a> | <a href="http://www.uwb.edu/">UW Bothell</a> | <a href="">Contact us</a></p>

    <p><a href="">About the Site</a> | <a href="">Site
					Map</a> | <a href="">Sources</a></p>

  </div>

</div>
<script> 
function checkFile() {
    var extension = document.getElementById("csv").value.substr((document.getElementById("csv").value.lastIndexOf('.') +1));
    if (!/(csv)$/ig.test(extension)) {
      alert("Invalid file type: "+extension+".  Please use csv.");
      return false;
    }
}
</script>
</body>
</html>