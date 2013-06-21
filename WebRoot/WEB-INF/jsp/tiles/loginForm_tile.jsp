<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ page import="gov.nih.nci.eagle.util.EAGLEConstants,gov.nih.nci.eagle.bean.UserInfoBean" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<%
if(session!=null && session.getAttribute(EAGLEConstants.userInfoBean)==null ) {
%>
<p>
<!--  start login box -->
<html:form action="login.do" styleId="loginForm">
<html:errors property="username" />
<html:errors property="password" />
<html:messages id="invalidLogin" message="true" header="errors.header" footer="errors.footer">
	<bean:write name="invalidLogin" />
</html:messages>

<table id="loginTable" border="0">
	<tr>
		<td>username:<br/><html:text styleId="username" property="username" /></td>
	</tr>
	<tr>
		<td>password:<br/><html:password styleId="password" property="password" /></td>
	</tr>
	<tr>
		<td style="text-align:center"><input type="submit" value="Login" /></td>
	</tr>
</table>
</html:form>
<!-- end login box -->
</p>
<% } else {%>

<tiles:insert page="/WEB-INF/jsp/tiles/sideBar_tile.jsp"/>
<% } %>