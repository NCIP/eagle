<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
/*
 *		this is the main tiles template for the form based pages
*/
%>
<%-- checks user authentication for all pages using UserInfoBean--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title><tiles:getAsString name="title"/></title>
	<%-- include html head with all css and js available for pages--%>
	<tiles:insert attribute="htmlHead"/> 
</head>

<body>
	 <%-- include header --%>
    <tiles:insert attribute="header"/>
     <%-- include div for overlib --%>
    <tiles:insert attribute="overlib"/>
	<div class="content">
		<%-- include crumb menu --%>
		<tiles:insert attribute="crumbMenu"/> 
		<table cellspacing="0" cellpadding="0" border="0" width="100%">
			<tr>
				<td width="575"> 
					<table cellpadding="4" cellspacing="2" border="0" width="100%"> 
					<tr class="report"><td><b><tiles:getAsString name="title"/></b></td></tr>
						<tr><td>
						<tiles:insert attribute="tabs"/>
						<div id="main">
							<%-- include the main form --%>
							<tiles:insert attribute="mainForm"/> 
						</div>
						</td></tr>						
					</table>
				</td>
				<td valign="top" class="sideBar">
					<%-- include sidebar --%>
				    <tiles:insert attribute="sideBar"/> 
				</td>
			</tr>
		</table>
	<%-- include footer --%>	
	<tiles:insert attribute="footer"/> 
	</div>
</body>
</html>