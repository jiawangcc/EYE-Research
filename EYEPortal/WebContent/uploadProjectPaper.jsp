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
  <a href="uploadProjectPaper.jsp" class = "current">Team resource</a> 
  <a href="uploadTestResult.jsp">Upload test result</a> 
  </div>

  

  <div id="mainContent">
  <p>&nbsp;</p>
  <p align="right">Welcome admin | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
      
    <h2><a name="download"></a>Files in the database</h2>
    
    <table>
  <tr>
    <th>File</th>
    <th>Uploaded by</th>
    <th>Uploaded at</th>
    <th>Download</th>
    <th>Delete</th>
  </tr>
  <%
  	for (ProjectDocument doc : DocumentDB.showAllDocuments()) {
  %>
  <tr>
    <td><%=doc.getFileName() %></td>
    <td><%=doc.getEmail() %></td>
    <td><%=doc.getDate().get().substring(0,19) %></td>
    <td><a href="Download?documentId=<%=doc.getDocumentId().get() %>"><button class="button">Download</button></a></td>
    <td><a href="Delete?documentId=<%=doc.getDocumentId().get() %>"><button class="button">Delete</button></a></td>
  </tr>
  <% } %>

</table>
    <p>&nbsp;</p>
    
    <h2><a name="upload"></a>Upload a file</h2>
    <form action="Upload/document" method="post" enctype="multipart/form-data">
        file: <input type="file" name="document" required><br><br>
    <button type="submit" class="button" id="InputFile"> Upload </button>
    </form>
    <p>&nbsp;</p>
    <h4><a href="#top">back to top</a>    </h4>

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