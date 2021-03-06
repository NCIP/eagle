<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<% 
	//default settings for tabs
	String simple = "";
	String adv = "";
	String viewResults = "";
	String analysis = "";
	String secondary = "";
	String list = "";
	
	String advSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"welcome.do\">Search Home</a></li>\n" +
							"<li><a href=\"clinicalInit.do?method=setup\">Clinical</a></li>\n" +
							"<li><a href=\"copyNumberInit.do?method=setup\">Copy Number</a></li>\n" +							
							"</ul>\n";
	String resultsSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"viewResults.do\">View Findings</a></li>\n" +
							"<li><a href=\"#\">Managed Saved Lists</a></li>\n" +
							"</ul>\n";				
	String simpleSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"#\">ID Lookup Home</a></li>\n" +
							"</ul>\n";
	String analysisSecondary = "<ul id=\"secondary\">\n" +
							"<li><a href=\"analysisHome.do\">Analysis Home</a></li>\n" +
							"<li><a href=\"classComparisonInit.do?method=setup\">Class Comparison</a></li>\n" +
							"<li><a href=\"#\">PCA</a></li>\n" +
							"<li><a href=\"#\">HC</a></li>\n" +										
							"</ul>\n";
							
	String s = (String) request.getParameter("s");
	if(s != null)	{
		int sect = Integer.parseInt(s);	
		switch(sect)	{
			case 1:
				//1 is simple search
				simple = "<span>ID Lookup</span>\n" + simpleSecondary;
				adv = "<a href=\"welcome.do\">Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			case 2:
				//2 is adv
				simple = "<a href=\"#\">ID Lookup</a>";
				adv = "<span>Search</span>\n" + advSecondary;
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			case 3:
			    //3 is high order analysis
				simple = "<a href=\"#\">ID Lookup</a>";
				adv = "<a href=\"welcome.do\">Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<span>High Order Analysis</span>\n" + analysisSecondary;
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			case 4:
			    //4 is view results
				simple = "<a href=\"#\">ID Lookup</a>";
				adv = "<a href=\"welcome.do\"> Search</a>";
				viewResults = "<span>View Results&nbsp;&nbsp;</span>\n";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
			case 5:
			    //5 manage lists
				simple = "<a href=\"#\">ID Lookup</a>";
				adv = "<a href=\"welcome.do\">Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<span>Manage Lists</span>\n";
				break;
			default:				
				simple = "<a href=\"#\">ID Lookup</a>";
				adv = "<a href=\"welcome.do\">Search</a>";
				viewResults = "<a href=\"viewResults.do\">View Results&nbsp;&nbsp;</a>";
				analysis = "<a href=\"analysisHome.do\">High Order Analysis</a>";
				list = "<a href=\"manageLists.do\">Manage Lists</a>";
				break;
		
		}
	}
%>
<div id="header">
	<ul id="primary">
		<li><%= simple %></li>
		<li><%= adv %></li>
		<li><%= analysis %></li>
		<li><%= viewResults %></li>
		<li><%= list %></li>			
	</ul>
</div>
