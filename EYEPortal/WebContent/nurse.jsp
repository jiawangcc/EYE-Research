<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<%
if(session.getAttribute("role") == null || !((String) session.getAttribute("role")).equalsIgnoreCase("nurse")) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
String name = (String) session.getAttribute("name");
int nurseId = Integer.valueOf((String)session.getAttribute("id"));
%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Educating Young Eye - School nurse Portal</title>
<link href="portal.css" rel="stylesheet" type="text/css">
<style>
#header {
	background-image: url(image/nurse.jpg);
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
    <h1>School Nurse Portal</h1>
  </div>


  <div id="navigation">
  <a href="nurse.jsp" class="current">Student List</a> 
  <div class="dropdown">
  <a href="overview.jsp" class="dropbtn">Test result overview</a>
    <div class="dropdown-content">
       <a href="overview.jsp#toolbox">Near vision toolbox</a>
       <a href="overview.jsp#redflag">RedFlag</a>
    </div>
  </div>

  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
    
    <h2>Search a student by ID, name or school</h2>
    <div>
     <form action="searchList.jsp" method="post">
      <input type="text" name="keyword" size=80 style="height:20px;"/>&nbsp;<input type="submit" class="button" value="Search"/>
     </form>
    </div>


<p>&nbsp;</p>

    <h2><a name="info"></a>Student Information</h2>

  <table>
  <tr>
    <th>Student Id</th>
    <th>Student name</th>
    <th>Date of birth</th>
    <th>School</th>
    <th>Grade</th>
    <th>View score</th>
  </tr>
  
  	<%
  	    List<Student> students = studentDB.showAllStudentForNurse(nurseId, Optional.<String>empty());
		for( Student s : students){	
	%>
  <tr>
    <td><%=s.getStuId() %></td>
    <td><%=s.getFirstname() %> <%=s.getLastname() %></td>
    <td><%=s.getDOB() %></td>
    <td><%=s.getSchool() %></td>
    <td><%=s.getGrade() %></td>
    <td><input type="button" class="button" value="View Score" onclick="viewScore('<%=s.getStuId()%>')"/></td>
  </tr>
  <%} %>
</table>
<h4><a href="#top">back to top</a>    </h4>
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

<script type="text/javascript">
    function viewScore(studentId){
        document.getElementById("studentId").value = studentId;
        document.showScore.submit();
    }
</script>
<form name="showScore" method="post" action="viewScore.jsp">
<input type="hidden" name="studentId" id="studentId">
</form>
</body>
</html>
