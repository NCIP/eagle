<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insert definition="cssBaseDef">
	<tiles:put name="mainForm" type="string" value="<div class='error'>You need to login.</div>"/>
	<tiles:put name="sideBar" value="/WEB-INF/jsp/tiles/loginForm_tile.jsp" />
</tiles:insert>