<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title><tiles:getAsString name="title" /></title>
		<tiles:insert attribute="htmlHead" />
		<link rel="shortcut icon" href="eagle.ico" />
	</head>
	<body>
		<%-- include div for overlib --%>
		<tiles:insert attribute="overlib" />
		<tiles:insert attribute="nciHeader" />
		<div id="header">
			<h1>
				<a href="index.jsp" style="text-decoration:none; color:#fff">EAGLE App [Logo will go Here]</a>
			</h1>
			<tiles:insert attribute="tabs" />
		</div>
		<div id="container">
		<tiles:insert page="/WEB-INF/jsp/tiles/crumbMenu_tile.jsp" flush="true"/>
			<div id="content">
				<p>
					<tiles:insert attribute="mainForm" />
				</p>
			</div> <!--  /content -->

			<div id="side">
				<tiles:insert attribute="sideBar" />
			</div>

			<div id="footer">
				<p>
					EAGLE Footers will go here
				</p>
			</div>
			<tiles:insert attribute="nciFooter" />
		</div> <!--  /container -->
	</body>
</html>
