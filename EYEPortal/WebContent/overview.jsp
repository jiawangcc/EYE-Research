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

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<script type="text/javascript">
	google.charts.load('current', {
		'packages' : [ 'corechart' ]
	});

	<% for (Score t : scoreDB.showAllTests()) { 
		int testId = t.getTestId();
		String testName = t.getTestName();
	%>
	google.charts.setOnLoadCallback(drawChart<%= testId %>);
	
	function drawChart<%= testId %>() {
		var data = google.visualization.arrayToDataTable([
			[ '<%= testName %>', 'score' ],
			
			<%
			final boolean renderHisto = testId < 3 || testName.startsWith("Balance");
			if (renderHisto) {
              for(Score s: scoreDB.showAllScoreEntriesByTest(testId)) { %>
					[ 'anonymized test taker', <%= s.getScore() %>],
				<% } %>]);
			<%} else {%>
			<% for (Map.Entry<String, Long> entry : scoreController.prepareDataset(testId)
					.entrySet()) { %>
			['<%= entry.getKey() %>', <%= entry.getValue() %>],
			<% } %>]);
			<% } %>

		var options = {
			title: '<%= testName %>',
			<%= renderHisto? "legend: {position: 'none'}," : "" %>
	        hAxis: {
	            title: <%= testName.startsWith("Balance") ? "'Time (seconds)'" : "'Score'" %>
	          },
	          vAxis: {
	            title: 'Count',
	          }
		};

		var chart = new google.visualization.<%= renderHisto ? "Histogram" : "PieChart" %>(document
				.getElementById('chart_<%= testId %>'));
		chart.draw(data, options);
	}
	
	<% } %>
	


</script>
</head>

<body>
<div id="container">

  <div id="header">

    <a name="top"></a>
    <div align="right"><img src="image/logo.jpg"> </div>
    <h1>School Nurse Portal</h1>
  </div>


  <div id="navigation">
  <a href="nurse.jsp">Student List</a> 
  <div class="dropdown">
  <a href="overview.jsp" class = "current" class="dropbtn" >Test result overview</a>
    <div class="dropdown-content">
       <a href="#toolbox">Near vision toolbox</a>
       <a href="#redflag">RedFlag</a>
    </div>
  </div>

  </div>

  

  <div id="mainContent">

    <p>&nbsp;</p>

    <p align="right">Welcome <%=name %> | <a href="Logout.jsp">Log out</a> | <a href="changePassword.jsp">Change password</a></p>
    <p>&nbsp;</p>
    
    <h2><a name="toolbox"></a>Test result overview: Near vision toolbox</h2>

     
    <table>
  <% Iterator<Score> iter = scoreDB.showAllTestsByGame(1).iterator();
  while (iter.hasNext()) {
  %>
    <tr>
    <td><div id="chart_<%= iter.next().getTestId() %>" style="width: 450px; height: 250px;"></div></td>
    <% if (iter.hasNext()) { %>
    <td><div id="chart_<%= iter.next().getTestId() %>" style="width: 450px; height: 250px;"></div></td>
    <% } %>
  </tr>
  <% } %>
</table>
<h4><a href="#top">back to top</a>    </h4>
    <p>&nbsp;</p>
    <h2><a name="redflag"></a>Test result overview: RedFlag</h2>

    <table>
  <% Iterator<Score> iter2 = scoreDB.showAllTestsByGame(2).iterator();
  while (iter2.hasNext()) {
  %>
    <tr>
    <td><div id="chart_<%= iter2.next().getTestId() %>" style="width: 450px; height: 250px;"></div></td>
    <% if (iter2.hasNext()) { %>
    <td><div id="chart_<%= iter2.next().getTestId() %>" style="width: 450px; height: 250px;"></div></td>
    <% } %>
  </tr>
  <% } %>
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
