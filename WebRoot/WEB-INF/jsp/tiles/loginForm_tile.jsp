<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
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
		<td style="text-align:center"><button onclick="$('loginForm').submit();">Login</button></td>
	</tr>
</table>
</html:form>
<!-- end login box -->

</p>