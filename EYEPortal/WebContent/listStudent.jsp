<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<% if(session.getAttribute("role") == null || !((String) session.getAttribute("role")).equalsIgnoreCase("admin")) {
	response.sendRedirect(request.getContextPath() + "/admin.jsp");
	return;
}%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Educating Young Eye - Admin Portal</title>
<link href="portal.css" rel="stylesheet" type="text/css">
<style>
#header {
	background-size: 25%;
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
  <a class = "current" href="listStudent.jsp">Edit student</a> 
  <a href="uploadProjectPaper.jsp">Team resource</a> 
  <a href="uploadTestResult.jsp">Upload test result</a> 
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome admin | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
    
    <h2><a name=""></a>Search a student</h2>
    <div>
     <form action="searchList.jsp" method="post">
      <input type="text" name="keyword" size=80 style="height:20px;"/>&nbsp;<input type="submit" class="button" value="Search"/>
     </form>
    </div>


<p>&nbsp;</p>

    <h2><a name=""></a>Student Information</h2>

  <table>
  <tr>
    <th>Student Id</th>
    <th>Student name</th>
    <th>Date of birth</th>
    <th>School</th>
    <th>Grade</th>
    <th>Edit</th>
  </tr>
  
  	<%
  	    List<Student> students = studentDB.showAllStudent(Optional.<String>empty());
		for( Student s : students){	
	%>
  <tr>
    <td><%=s.getStuId() %></td>
    <td><%=s.getFirstname() %> <%=s.getLastname() %></td>
    <td><%=s.getDOB() %></td>
    <td><%=s.getSchool() %></td>
    <td><%=s.getGrade() %></td>
    <td><input type="button" class="button" value="Edit" onclick="edit('<%=s.getStuId()%>')"/></td>
  </tr>
  <%} %>
</table>
    <p>&nbsp;</p>
    
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

<script type="text/javascript">
    function edit(studentId){
        document.getElementById("studentId").value = studentId;
        document.Edit.submit();
    }
</script>
<form name="Edit" method="post" action="editStudent.jsp">
<input type="hidden" name="studentId" id="studentId">
</form>
</body>
</html>
