<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<%
if(session.getAttribute("role") == null) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
String name = (String) session.getAttribute("name");
String role = (String) session.getAttribute("role");
int userId = Integer.valueOf((String)session.getAttribute("id"));
%>
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Educating Young Eye - View Score</title>
<link href="portal.css" rel="stylesheet" type="text/css">
<style>
#header {
	background-image: url(image/<%=role%>.jpg);
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
    <%
    String title = "";
    if(role.equals("nurse")){
    	title = "School Nurse Portal";
    }
    else if(role.equals("doctor")){
    	title = "Doctor Portal";
    }
    %>
    <h1><%=title %></h1>
  </div>


  <div id="navigation">
  <a href="<%=role%>.jsp">Home</a> 
  <div class="dropdown">
  <a href="" class="dropbtn">Test results</a>
    <div class="dropdown-content">
       <a href="#toolbox">Near vision toolbox</a>
       <a href="#redflag">RedFlag</a>
    </div>
  </div>
  <a href="#nurse">Nurse note</a> 
  <a href="#doctor">Doctor diagnosis</a>
  <a href="#add">Add comment</a>
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>

            <%
            String sId;
            if (session.getAttribute("studentId") != null) {
            	if (request.getParameter("studentId") != null) {
            		// set session value to current student
            		session.putValue("studentId", request.getParameter("studentId"));
            	}
            	// after inserting new nurse note
            	sId = (String) session.getAttribute("studentId");
            } else {
            	// from score listing page
            	sId = request.getParameter("studentId");
            }
            int id = Integer.parseInt(sId);
            Student s = studentDB.showStudent(id);
            %>
			
            <div id="sidebar1"> <img src="image/<%=id%>.jpg" width="150" height="200"> </div>
            <p>&nbsp;</p>
			<p>Student: <%=s.getFirstname() %> <%=s.getLastname() %></p>		
			<p>Date of birth: <%=s.getDOB() %></p>
			<p>Student ID: <%=s.getStuId() %></p>
			<p>School: <%=s.getSchool() %></p>
			<p>Grade: <%=s.getGrade() %></p>

    
    <p>&nbsp;</p>
    <h2><a name="toolbox"></a>Near vision toolbox test</h2>

			<table>
				<tr>
					<th>Test date</th>
					<th>Test name</th>
					<th>Test score</th>
				</tr>
				<%
					List<Score> scores = scoreDB.showStuScore(id, 1);
					for (Score sc : scores) {
				%>
				<tr>
					<td><%=sc.getDate().substring(0, 19)%></td>
					<td><%=sc.getTestName()%></td>
					<%
					String acuity_score = "";
					if(sc.getTestId()==2){
						acuity_score = "20/";
					}
					%>
					<td><%=acuity_score+sc.getScore()%></td>
				</tr>
				<%
					}
				%>

			</table>
			<h4><a href="#top">back to top</a>    </h4>
			
    <h2><a name="redflag"></a>RedFlag test</h2>

			<table>
				<tr>
					<th>Test date</th>
					<th>Test name</th>
					<th>Test result</th>
				</tr>
				<%
				scores = scoreDB.showStuScore(id, 2);
				for(Score sc : scores){
				%>
				<tr>
					<td><%=sc.getDate().substring(0, 19)%></td>
					<td><%=sc.getTestName()%></td>
					<%
					String seconds = "";
					if(sc.getTestId()>=8 && sc.getTestId()<=11){
						seconds = " seconds";
					}
					%>
					<td><%=sc.getScore()+seconds%></td>
				</tr>
                <%}%>
			</table>
<h4><a href="#top">back to top</a>    </h4>
<p>&nbsp;</p>

<h2><a name="nurse"></a>School nurse notes</h2>
             <table>
                <col width="180">
                <col width="150">
				<tr>
					<th>Date</th>
					<th>Nurse</th>
					<th>Notes</th>
				</tr>
				<% 
				List<nurseRes> results = nurseResDB.showNote(id);
				for( nurseRes nr : results){
				%>
				<tr>
					<td><%=nr.getDate().substring(0, 19)%></td>
					<td><%=nr.getNurseFN()%> <%=nr.getNurseLN()%></td>
					<td><%=nr.getNote()%></td>
				</tr>
				<%}%>
			</table>
			<p>&nbsp;</p>
    
<h2><a name="doctor"></a>Doctor diagnosis</h2>
             <table>
                <col width="180">
                <col width="150"> 
				<tr>
					<th>Date</th>
					<th>Doctor</th>
					<th>Notes</th>
				</tr>
				<% 
				List<doctorRes> docresults = doctorResDB.showNote(id);
				for( doctorRes dr : docresults){
				%>
				<tr>
					<td><%=dr.getDate().substring(0, 19)%></td>
					<td><%=dr.getDoctorFN()%> <%=dr.getDoctorLN()%></td>
					<td><%=dr.getNote()%></td>
				</tr>
				<%}%>
			</table>
<p>&nbsp;</p>			
			
		<% 
			String sectionHeader;
			if (role.equalsIgnoreCase("Nurse")) {
				sectionHeader = "nurse note";
			} else if (role.equalsIgnoreCase("Doctor")) {
				sectionHeader = "doctor diagnosis";
			} else {
				sectionHeader = "";
			}
		%>
<h2><a name="add"></a>Add a new <%= sectionHeader %></h2>
			
	<form action="<%= role %>Res/New" method="post" id="note">
	<input type="hidden" name="studentId" value="<%=id %>">
	<input type="hidden" name="<%= role.toLowerCase() %>Id" value="<%=userId %>">
	<textarea rows="10" cols="100" name="comment" form="note"></textarea><br>
	<%if (role.equals("nurse")){%>
	Refer to a doctor for further diagnostic:<select name="doctorId">
		<option value="0">No Referral</option>
	<% for (Doctor d : doctorDB.listAllDoctors()) { %>
		<option value="<%= d.getId() %>"><%= d.getName() %> (<%= d.getAffiliation() %>)</option>
	<% } 
	}%>
	</select>
<p><input type="submit" class="button" id="submit" value="Submit new note"/></p>
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
