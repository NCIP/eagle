<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<%@ page import="gov.nih.nci.caintegrator.application.lists.ListType,gov.nih.nci.eagle.util.EAGLEListFilter"%>

<div id="sidebar">

<div class="release">
	<p>
		<b>EAGLE release v<%=gov.nih.nci.eagle.util.EAGLEConstants.appVersion %></b>
	</p>
</div>
<%
	ListType[] lts = EAGLEListFilter.values();
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<div style="text-align:left; margin-top:10px;padding-left:5px;">
		<b><%=label%> Lists:</b>
		<div id="sidebar<%=label%>UL">
			<img src="images/indicator.gif" />
		</div>	
	</div>
<%
	}
%>
	<br/><br/>
<!--  	
	<b style="color:#A90101; font-size:10px;">Items in Red are "custom" lists</b>
	<br/><br/>
-->
</div>

<script language="javascript">
	SidebarHelper.loadSidebar();
</script>