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
  <a class="current" href="researcher.jsp">Current research</a> 
  <a href="downloadDataset.jsp">Download datasets</a> 
  <a href="uploadWork.jsp">Share your work</a> 
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
    
    <h2>Search article by title or keyword</h2>
    <div>
     <form action="searchPaper.jsp" method="post">
      <input type="text" name="keywordPaper" size=80 style="height:20px;"/>&nbsp;<input type="submit" class="button" value="Search"/>
     </form>
    </div>
     <p>&nbsp;</p>

    <h2></a>Current research work</h2>
		<table>  
		  	<%
		  	    List<researchWork> works = researchWorkDB.showAllWork();
				for(  researchWork w : works){	
			%>
		  <tr>
		    <p><font size="+1"><b><%=w.getTitle() %></b></font> <a href="Download?paperId=<%=w.getPaperId() %>"><button class="button">Download</button></a></p>
		    <div id="author"><%=w.getFirstname() %> <%=w.getLastname() %>. <%=w.getAffil() %>. <%=w.getEmail() %>. Date: <%=w.getDate().substring(0,19) %></div>   
		    <p><font color="blue">Keyword:</font> <%=w.getKeyword() %></p>
		    <p><font color="blue">Abstract:</font> <%=w.getAbstract() %></p>
		    <p>&nbsp;</p>
		  </tr>
		  <%} %>
		  
		</table> 
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