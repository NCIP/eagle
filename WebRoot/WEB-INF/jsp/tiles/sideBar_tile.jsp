<%@ page import="gov.nih.nci.caintegrator.application.lists.ListType,gov.nih.nci.eagle.util.EAGLEListFilter"%>

<div id="sidebar">
<%
	ListType[] lts = EAGLEListFilter.values();
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<p style="text-align:left; margin-top:20px;">
		<b><%=label%> Lists:</b>
		<p id="sidebar<%=label%>UL">
			<img src="images/indicator.gif"/>
		</p>	
	</p>
<%
	}
%>
	<br/><br/>
	<b style="color:#A90101; font-size:10px;">Items in Red are "custom" lists</b>
	<br/><br/>
</div>

<script language="javascript">
	SidebarHelper.loadSidebar();
</script>