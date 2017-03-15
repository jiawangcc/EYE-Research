<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<%if(session.getAttribute("role") == null || !((String) session.getAttribute("role")).equalsIgnoreCase("doctor")) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
String name = (String) session.getAttribute("name");
int doctorId = Integer.valueOf((String)session.getAttribute("id"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Educating Young Eye - Doctor Portal</title>
<link href="portal.css" rel="stylesheet" type="text/css">
<style>
#header {
	background-image: url(image/doctor.png);
	background-size: 28%;
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
    <h1>Doctor Portal</h1>
  </div>


  <div id="navigation">
  <a href="doctor.jsp">Home</a>
  <a href="doctor.jsp">New referral</a>
  <a href="#diagnosis"> Diagnosed students</a>
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
    
    <h2><a name=""></a>Search a student by ID, name or school</h2>
    <div>
     <form action="searchList.jsp" method="post">
      <input type="text" name="keyword" size=80 style="height:20px;"/>&nbsp;<input type="submit" class="button" value="Search"/>
     </form>
    </div>
<p>&nbsp;</p>

    <h2><a name="refer"></a>New referred students</h2>

    <table>
  <tr>
    <th>Student Id</th>
    <th>Student name</th>
    <th>DOB</th>
    <th>School</th>
    <th>Grade</th>
    <th>View score</th>
  </tr>
  
   <%
  	    List<Student> students = studentDB.showStudentsByReferralStatus(doctorId, Optional.of(false), Optional.<String>empty());
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
    <p>&nbsp;</p>
    <h2><a name="diagnosis"></a>Students have been diagnosed</h2>

     
      <table>
  <tr>
    <th>Student Id</th>
    <th>Student name</th>
    <th>DOB</th>
    <th>School</th>
    <th>Grade</th>
    <th>View score</th>
  </tr>
  
  <%
  	    students = studentDB.showStudentsByReferralStatus(doctorId, Optional.of(true), Optional.<String>empty());
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