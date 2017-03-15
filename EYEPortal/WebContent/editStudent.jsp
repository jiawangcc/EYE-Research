<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import = "java.util.*,java.text.DecimalFormat,edu.uw.jiawang.*" %>
<% if(session.getAttribute("role") == null || !((String) session.getAttribute("role")).equalsIgnoreCase("admin")) {
	response.sendRedirect(request.getContextPath() + "/admin.jsp");
	return;
}%>

<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Educating Young Eye - Edit Student Info</title>
<script src="jquery-3.1.1.min.js" type="text/javascript"></script>
<link href="portal.css" rel="stylesheet" type="text/css">
<style>
#header {
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
    <h1>Admin Portal</h1>
  </div>


  <div id="navigation">
    <a href="registerRequest.jsp">Verify user request</a>
  <a href="listStudent.jsp">Edit student</a> 
 <a href="uploadProjectPaper.jsp">Team resource</a>
 <a href="uploadTestResult.jsp">Upload test result</a> 
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome admin | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>

    <h2>Student Information</h2>
    <%
	    String sId;
	    if (session.getAttribute("studentId") != null) {
        	if (request.getParameter("studentId") != null) {
        		// set session value to current student
        		session.putValue("studentId", request.getParameter("studentId"));
        	}
	    	// after updating student info
	    	sId = (String) session.getAttribute("studentId");
	    } else {
	    	// from score listing page
	    	sId = request.getParameter("studentId");
	    }
	    int id = Integer.parseInt(sId);
	    Student s = studentDB.showStudent(id);		
	%>
    <div id="sidebar1"><img src="image/<%=id%>.jpg" width="150" height="200"> 
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    <p>&nbsp;</p>
    </div>
    <form id="studentInfo">
    First name:<input type="text" id="firstName" value="<%=s.getFirstname()%>" required><br><br>
Last name:<input type="text" id="lastName" value="<%=s.getLastname()%>" required><br><br>
Date of Birth:<input type="date" id="dob" value="<%=s.getDOB()%>" required><br><br>
<input type="hidden" id="studentId" value="<%=s.getStuId()%>" required>
Grade:<select id="grade" required>
<% request.setAttribute("grade", s.getGrade()); %>
	<c:forEach begin="1" end="5" varStatus="loop">
	    <option value="${loop.index}" ${loop.index == grade ? 'selected' : ''}>${loop.index}</option>
	</c:forEach>
		</select><br><br>
School:<input type="text" id="school" value="<%=s.getSchool()%>" size = 30 required><br><br>

<input type="button" class="button" value="Update" onclick="update()"/>
</form>
<br /><br /><br />
<div id="update-status"></div>
<script type="text/javascript">
	function update() {
		if ($('form')[0].checkValidity()) {
			$.ajax({
				type: "POST",
				url: "Student/Update",
				data: {
					firstName: $('#firstName').val(),
					lastName: $('#lastName').val(),
					dob: $('#dob').val(),
					studentId: $('#studentId').val(),
					grade: $('#grade').val(),
					school: $('#school').val(),
				  },
				  success: function( result ) {
				    $( '#update-status' ).html( "<strong>" + result + "</strong><br />" );
				  }
				});
			setTimeout(function(){
			    location.reload();
			}, 1500);
		} else {
			$( '#update-status' ).html("<strong style=\"color:red;\">Invalid input</strong>");
		}
	}
</script>    <p>&nbsp;</p>
    

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
