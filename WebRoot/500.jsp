<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insert definition="cssBaseDef">
	<tiles:put name="mainForm" type="string" value="<div class='error'>A System error has occurred</div>"/>
	<tiles:put name="sideBar" value="/WEB-INF/jsp/tiles/blank_tile.jsp" />
</tiles:insert>