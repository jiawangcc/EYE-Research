<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<%
if(session.getAttribute("role") == null || !((String) session.getAttribute("role")).equalsIgnoreCase("researcher")) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
String name = (String) session.getAttribute("name");
int researchId = Integer.valueOf((String)session.getAttribute("id"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Educating Young Eye - Researcher Portal</title>
<link href="portal.css" rel="stylesheet" type="text/css">
<style>
#header {
	background-image: url(image/researcher.png);
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
    <h1>Researcher Portal</h1>
  </div>


  <div id="navigation">
  <a href="researcher.jsp">Current research</a> 
  <a href="downloadDataset.jsp">Download datasets</a> 
  <a class="current" href="uploadWork.jsp">Share your work</a> 
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
    
   
    <h2>Share your work</h2>
    <form action="Upload/paper" method="post" enctype="multipart/form-data">
        Title: <input type="text" name="title" size=50 required><br><br>
        Keyword: <input type="text" name="keyword" size=47 required><br><br>
        Abstract: <p><input type="text" name="abstract" size=100 style="height:100px;" required/></p>
		Article in pdf: <input type="file" name="paper" id = "paper" required ><br><br>
		<button type="submit" class="button" onclick="return checkFile()"> Upload </button>
    </form>
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
    var extension = document.getElementById("paper").value.substr((document.getElementById("paper").value.lastIndexOf('.') +1));
    if (!/(pdf)$/ig.test(extension)) {
      alert("Invalid file type: "+extension+".  Please use pdf.");
      return false;
    }
}
</script>

</body>
</html>