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
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Educating Young Eye - Search Results</title>
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
    <h1>Researcher Portal</h1>
  </div>


  <div id="navigation">
  <a href="researcher.jsp">Home</a> 
  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a></p>
    
    <h2><a name=""></a>Search article</h2>
    <div>
     <form action="searchPaper.jsp" method="post">
      <input type="text" name="keywordPaper" size=80 style="height:20px;"/>&nbsp;<input type="submit" class="button" value="Search"/>
     </form>
    </div>

        <table>  
		  	<%
		  	    List<researchWork> works = Collections.EMPTY_LIST;
		  	    String keyword = request.getParameter("keywordPaper");
		  	    works = researchWorkDB.searchWork(keyword);
				for(researchWork w : works){	
			%>
		  <tr>
		    <p><font size="+1"><b><%=w.getTitle() %></b></font> <a href="ResearchWork/Download?paperId=<%=w.getPaperId() %>"><button class="button">Download</button></a></p>
		    <div id="author"><%=w.getFirstname() %> <%=w.getLastname() %>. <%=w.getAffil() %>. <%=w.getEmail() %>. Date: <%=w.getDate() %></div>   
		    <p><font color="blue">Keyword:</font> <%=w.getKeyword() %></p>
		    <p><font color="blue">Abstract:</font> <%=w.getAbstract() %></p>
		    <p>&nbsp;</p>
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
