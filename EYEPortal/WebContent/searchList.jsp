<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<%
String name = (String) session.getAttribute("name");
String role = (String) session.getAttribute("role");
if (role == null) {
	response.sendRedirect("index.jsp");
	return;
}
%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Educating Young Eye - Search Results</title>
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
    <%
    String title = "";
    if(role.equals("nurse")){
    	title = "School Nurse Portal";
    }
    else if(role.equals("doctor")){
    	title = "Doctor Portal";
    }
    else{
    	title = "Administrator Portal";
    }
    %>
    <h1><%=title %></h1>
  </div>


  <div id="navigation">
      <%
    if(role.equals("admin")){%>
    	  <a href="registerRequest.jsp">Verify user request</a>
          <a href="listStudent.jsp">Edit student</a> 
          <a href="uploadProjectPaper.jsp">Team resource</a> 
           <a href="uploadTestResult.jsp">Upload test result</a> 
    <%}
    else if(role.equals("nurse")){%>
    	  <a href="nurse.jsp">Home</a>
    <%}
    else{%>
    	  <a href="doctor.jsp">Home</a>
    <%}
    %>
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a></p>
    
    <h2><a name=""></a>Search a student by ID, name or school</h2>
    <div>
     <form action="searchList.jsp" method="post">
      <input type="text" name="keyword" size=80 style="height:20px;"/>&nbsp;<input type="submit" class="button" value="Search"/>
     </form>
    </div>
    <p>&nbsp;</p>
			<%
				String keyword = request.getParameter("keyword");
				List<Student> students = Collections.emptyList();
				if (role.equalsIgnoreCase("admin")) {
					students = studentDB.showAllStudent(Optional.of(keyword));
				} else if (role.equalsIgnoreCase("nurse")) {
					students = studentDB.showAllStudentForNurse(Integer.parseInt((String)session.getAttribute("id")), Optional.of(keyword));
				} if (role.equalsIgnoreCase("doctor")) {
					students = studentDB.showStudentsByReferralStatus(Integer.parseInt((String)session.getAttribute("id")), Optional.<Boolean>empty(), Optional.of(keyword));
				}
				if (students.isEmpty()) {
			%>
			<p>
				No results were found for "<%=keyword%>".
			</p>
			<%
				} else {
			%>
				<table>
				<tr>
					<th>Student Id</th>
					<th>Student name</th>
					<th>Date of birth</th>
					<th>School</th>
					<th>Grade</th>
					<%if(role.equals("admin")){
						out.print("<th>Edit</th>");
					}
					else{
						out.print("<th>View score</th>");
					}
					%>					
				</tr>
			<%
					for (Student s : students) {
			%>
				<tr>
					<td><%=s.getStuId()%></td>
					<td><%=s.getFirstname()%> <%=s.getLastname()%></td>
					<td><%=s.getDOB()%></td>
					<td><%=s.getSchool()%></td>
					<td><%=s.getGrade()%></td>
					<%if(role.equals("admin")){%>
						<td><input type="button" class="button" value="Edit" onclick="editInfo('<%=s.getStuId()%>')"/></td>
					<%}
					else{%>
					<td><input type="button" class="button" value="View Score" onclick="viewScore('<%=s.getStuId()%>')"/></td>
					<%}%>				
				</tr>
				<%
					}
					}
				%>
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
    
    function editInfo(studentId){
        document.getElementById("studentIdToEdit").value = studentId;
        document.editStudent.submit();
    }
</script>
<form name="showScore" method="post" action="viewScore.jsp">
<input type="hidden" name="studentId" id="studentId">
</form>
<form name="editStudent" method="post" action="editStudent.jsp">
<input type="hidden" name="studentId" id="studentIdToEdit">
</form>
</body>
</html>
