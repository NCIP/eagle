<% 
String s = request.getParameter("s")!= null ? (String) request.getParameter("s") : "1";
String a = "activelink";
%>


<div id="menu">
	<ul id="nav">
	<li id="home" class="<%= (s.equals("1")) ? a : "" %>"><a href="welcome.do">Home</a></li>
	<li id="who" class="<%= (s.equals("2")) ? a : "" %>"><a href="advancedHome.do" onclick="return false;">Advanced Search</a></li>
	<li id="prod" class="<%= (s.equals("3")) ? a : "" %>"><a href="analysisHome.do">Analysis</a></li>
	<li id="serv" class="<%= (s.equals("5")) ? a : "" %>"><a href="manageLists.do">Manage Lists</a></li>
	<li id="cont"class="<%= (s.equals("4")) ? a : "" %>"><a href="viewResults.do">View Results</a></li>
	</ul>
</div>