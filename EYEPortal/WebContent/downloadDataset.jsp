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
  <a class="current.jsp" href="downloadDataset">Download datasets</a> 
  <a href="uploadWork.jsp">Share your work</a> 
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
        
    <h2><a name="download"></a>Download datasets</h2>
    
    <script language="JavaScript">
    function toggleSchool(source) {
    	  checkboxes = document.getElementsByName('school');
    	  for(var i=0, n=checkboxes.length;i<n;i++) {
    	    checkboxes[i].checked = source.checked;
    	  }
    	}
    
    function toggleGame(source) {
  	  checkboxes = document.getElementsByName('game');
  	  for(var i=0, n=checkboxes.length;i<n;i++) {
  	    checkboxes[i].checked = source.checked;
  	  }
  	}
    
    function toggleGrade(source) {
  	  checkboxes = document.getElementsByName('grade');
  	  for(var i=0, n=checkboxes.length;i<n;i++) {
  	    checkboxes[i].checked = source.checked;
  	  }
  	}
	</script>

<form action="Download/Dataset" method="post">
<table>
	<tr>
					<th>Select school(s)</th>
					<th>Select game(s)</th>
					<th>Select grade(s)</th>
	</tr>
<tr>
<td>
<input type="checkbox" onClick="toggleSchool(this)" checked > All schools<br>
<% for (String school : studentDB.getAllSchools()) { %>
  <input type="checkbox" name="school" value="<%= school %>" checked> <%= school %> <br>
  
  <% } %>
</td>
<td>
<input type="checkbox" onClick="toggleGame(this)" checked> All games<br>
<% for (Score s : scoreDB.showAllGames()) { %>
  <input type="checkbox" name="game" value="<%= s.getGameId() %>" checked> <%= s.getGameName() %> <br>
  
  <% } %>
</td>
<td>
<input type="checkbox" onClick="toggleGrade(this)" checked> All grades<br>
<% for (Integer grade : studentDB.getAllGrades()) { %>
  <input type="checkbox" name="grade" value="<%= grade %>" checked> <%= grade %> <br>
  
  <% } %>
</td>
</tr>
</table>

  <button type="submit" class="button">Download dataset</button>
</form>

    
<p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p>
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

</body>
</html>