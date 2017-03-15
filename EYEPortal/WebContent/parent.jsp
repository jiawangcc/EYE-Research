<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<%
if(session.getAttribute("role") == null || !((String) session.getAttribute("role")).equalsIgnoreCase("parent")) {
	response.sendRedirect(request.getContextPath() + "/index.jsp");
	return;
}
String name = (String) session.getAttribute("name");
int studentId = Integer.valueOf((String)session.getAttribute("id"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Educating Young Eye - Parent Portal</title>
<link href="portal.css" rel="stylesheet" type="text/css">
<style>
#header {
	background-image: url(image/parent.jpg);
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
			<div align="right">
				<img src="image/logo.jpg">
			</div>
			<h1>Parent Portal</h1>
		</div>


		<div id="navigation">
			<div class="dropdown">
				<a href="" class="dropbtn">Test results</a>
				<div class="dropdown-content">
					<a href="#toolbox">Near vision toolbox</a> <a href="#redflag">RedFlag</a>
				</div>
			</div>
			<a href="#nurse">Nurse note</a> <a href="#doctor">Doctor
				diagnosis</a>
		</div>



		<div id="mainContent">

			<p>&nbsp;</p>

			<%
			Student s = studentDB.showStudent(studentId);		
			%>
			<p align="right">Hi <%=s.getFirstname()%>'s parent | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
			
			<div id="sidebar1">
				<img src="image/<%=s.getStuId() %>.jpg" width="150" height="200">
			</div>

			<p>Student: <%=s.getFirstname() %> <%=s.getLastname() %></p>		
			<p>Date of birth: <%=s.getDOB() %></p>
			<p>Student ID: <%=s.getStuId() %></p>
			<p>School: <%=s.getSchool() %></p>
			<p>Grade: <%=s.getGrade() %></p>

			<p>&nbsp;</p>
			<p>&nbsp;</p>
			<h2>
				<a name="toolbox"></a>Near vision toolbox test
			</h2>
			<div id="description">
				<p>This toolbox includes four tests. Convergence Insufficiency
					Symptom Survey (CISS): asks 15 question which help diagnose
					children who suffer from convergence insufficiency. Near visual
					acuity: tests the ability to see clearly when reading a boo up
					close or browsing a computer. Stereopsis: tests the ability to form
					two images from both eyes to successfully combine into one image in
					the brain. Near point of convergence: tests the ability to converge
					two eyes while you're trying to focus on a nearby object.</p>
				<p>&nbsp;</p>
			</div>

			<table>
				<tr>
					<th>Test date</th>
					<th>Test name</th>
					<th>Test score</th>
				</tr>
				<%
				List<Score> scores = scoreDB.showStuScore(studentId, 1);
				for(Score sc : scores){
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
				<%}%>
			</table>
			<h4>
				<a href="#top">back to top</a>
			</h4>
			<p>&nbsp;</p>
			
			<h2>
				<a name="redflag"></a>RedFlag test
			</h2>
			<div id="description">
				<p>
					RedFlagis a vision screening tool, based on Katie Johnson’s <i>RED
						FLAGS for Primary Teachers</i> book. The vision screening test within
					the application consists of six simple activities for
					vision-screening kids in elementary schools: Tracking the eyeball,
					Balance, Teaming with pencil eraser, Skipping, Visual
					Discrimination, and Tummy crawl.
				</p>
			</div>

			<table>
				<tr>
					<th>Test date</th>
					<th>Test name</th>
					<th>Test result</th>
				</tr>
				<%
				scores = scoreDB.showStuScore(studentId, 2);
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
			

			<h4>
				<a href="#top">back to top</a>
			</h4>
			<p>&nbsp;</p>

			<h2>
				<a name="nurse"></a>School nurse comments
			</h2>
			<table>
			    <col width="100">
                <col width="150"> 
				<tr>
					<th>Date</th>
					<th>Nurse</th>
					<th>Comments</th>
				</tr>
				<% 
				List<nurseRes> results = nurseResDB.showNote(studentId);
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
			<h2>
				<a name="doctor"></a>Doctor diagnosis
			</h2>

           <table>
                <col width="100">
                <col width="150">               
				<tr>
					<th>Date</th>
					<th>Doctor</th>
					<th>Comments</th>
				</tr>
				<% 
				List<doctorRes> docresults = doctorResDB.showNote(studentId);
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

			<h4>
				<a href="#top">back to top</a>
			</h4>

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
					Map</a> | <a href="">Sources</a></a>
			</p>

		</div>

	</div>

</body>
</html>