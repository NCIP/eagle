<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<%@ page import="gov.nih.nci.caintegrator.application.lists.ListType,
				gov.nih.nci.caintegrator.application.lists.ListSubType,
				gov.nih.nci.caintegrator.application.lists.UserList,
				gov.nih.nci.caintegrator.application.lists.UserListBean,
 				gov.nih.nci.caintegrator.application.lists.ListManager, 
				gov.nih.nci.caintegrator.application.lists.UserListBeanHelper,
				gov.nih.nci.eagle.util.EAGLEListFilter,
				org.apache.struts.upload.FormFile,
				java.io.File,
				java.util.Map,
				java.util.HashMap,
				java.util.List,
				org.dom4j.Document"%>
<!--   
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/DynamicListHelper.js'></script>
-->
<script type='text/javascript' src='js/common/ManageListHelper.js'></script>

<script type='text/javascript' src='dwr/interface/UserListHelper.js'></script>
<script type='text/javascript' src='js/common/TextFormList.js'></script>
<script type='text/javascript' src='js/common/FormChanger.js'></script>
<script type='text/javascript' src='js/lib/scriptaculous/effects.js'></script>

<!--
<script type='text/javascript' src='dwr/util.js'></script>

<script type='text/javascript' src='js/lib/common/StatusMessage.js'></script>
-->
<script type="text/javascript">
	function handleResponse(msg)	{
		ManageListHelper.handleResponse(msg);
	}
	
	//http://simon.incutio.com/archive/2004/05/26/addLoadEvent
	function addLoadEvent(func) {
	var oldonload = window.onload;
  	if (typeof window.onload != 'function') {
    	window.onload = func;
  	} 
  	else {
	    window.onload = function() {
	    	if (oldonload) {
	        	oldonload();
	      	}
			func();
	    }
 	}
}
</script>
<style>	
	.status {
		color:#A90101;
		font-weight:bold;
	}
	.groupList li, .listbox li	{
		margin-left:20px;
		list-type:none;
		list-style-type: none;
	}

	li.detailsList	{
		padding:3px;
	}
	
	acronym	{
		text-decoration:underline;
		cursor:pointer;
	}
</style>

<iframe id="RSIFrame" name="RSIFrame" style="width:0px; height:0px; border: 0px" src="blank.jsp"></iframe>
<span id="info" style="display:none">&nbsp;</span>

<h2 style="text-align:center">
<script type="text/javascript">Help.insertHelp("Manage_lists_overview", "align='right'");</script>
Manage Lists
</h2>

<!--  
	<div style="text-align:center; font-size:11px;">
	<%
		ListType[] lts = EAGLEListFilter.values();
		for(int i=0; i<lts.length; i++)	{
			String label = lts[i].toString();
	%>
		<a href="#<%=label%>Lists"><%=label%> Lists</a> 
	<% } %>
		   <a href="#addList">Add List</a> 
	</div>
-->
<%
	for(int i=0; i<lts.length; i++)	{
		String label = lts[i].toString();
%>
	<a name="<%=label%>Lists"></a>
	<div class="listboxtop" onclick="new Effect.toggle('<%=label%>Container')">
			<%=label%> Lists
	</div>
	<div class="listbox" id="<%=label%>ListsFS">
		
		<div id="<%=label%>Container">
			<br/>
			<div id="<%=label%>ListDiv"></div>	
			
			<div id="<%=label%>UniteDiv" class="listfunctions" />
				New List Name:<input type="text" id="<%=label%>GroupName"/>
				<b><input type="button" onclick="ManageListHelper.groupSelectedLists('<%=label%>','<%=label%>ListsFS', $('<%=label%>GroupName').value,'join')" value="Join"/></b>	
				<b><input type="button" onclick="ManageListHelper.groupSelectedLists('<%=label%>','<%=label%>ListsFS', $('<%=label%>GroupName').value,'intersect')" value="Intersect"/></b>	
				<b><input type="button" onclick="ManageListHelper.groupSelectedLists('<%=label%>','<%=label%>ListsFS', $('<%=label%>GroupName').value,'difference')" value="Difference"/></b>					
				<span class="status" id="<%=label%>GroupStatus"></span>
			</div>
		</div>
	</div>
	<div style="text-align:right; margin:10px;">
		<a href="#" onclick="javascript:scroll(0,0);return false;">[top]</a>
	</div>
<%
	}
%>
<script type="text/javascript">

		if(!saf)	{
			addLoadEvent(ManageListHelper.generic_cb); 
		}
		else	{
			ManageListHelper.generic_cb("init");
		}
		
		addLoadEvent(function() { Nifty("div.listboxtop","top"); });
		
		//StatusMessage.showStatus("asdf");
</script>

<!--  
<a name="addList"></a>
<fieldset class="listForm" id="listForm">
	<legend class="listLegend">
		<a onclick="FormChanger.type2upload();return false;" href="#">Upload List</a> -or- <a href="#" onclick="FormChanger.upload2type();return false;">Manually type List</a>
	</legend>
	<br/>
	<div id="uploadListDiv">
	<form id="uploadForm" method="post" action="upload.jsp" enctype="multipart/form-data" target="RSIFrame" onSubmit="return false;">
		<table border="0" cellspacing="2" cellpadding="2">
			<tr>
				<td>
					Choose the list type:
				</td>
				<td colspan="2">
					<select id="typeSelector" name="type">
<%
					for(int i=0; i<lts.length; i++)	{
						
						String label = lts[i].toString();			
						
						//process the subtypes associated with this ListType
						List subtypes = EAGLEListFilter.getSubTypesForType(lts[i]);	
						if(subtypes!=null && subtypes.size()>0)	{
							for(int s=0; s<subtypes.size(); s++)	{
								String lsts =((ListSubType)subtypes.get(s)).toString();
%>
								<option value="<%=label%>|<%=lsts%>"><%=label%>-<%=lsts%></option>
<%
							}
						}
						else  {					
%>
							<option value="<%=label%>"><%=label%></option>
<%
						}
					}
%>					
					</select>
					
				</td>
			</tr>
			<tr id="uploadRow">
				<td>
					Upload file:
				</td>
				<td colspan="2">
					<input type="file" id="upload" name="upload" size="25">
				</td>
			</tr>
			<tr id="textRow" style="display:none">
				<td>
					Type Ids:<br/> (one per line)
				</td>
				<td colspan="2">
					<textarea id="typeListIds" style="width:150px;height:100px"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					Name list:
				</td>
				<td colspan="2">
					<input type="text" id="listName" name="listName" size="30">
					<input type="button" value="add list" onclick="ManageListHelper.validateForm()" id="uploadButton">
					<span id="uploadStatus" style="display:none"><img src="images/indicator.gif"/>&nbsp; saving...</span>
				</td>
			</tr>

		</table>
	</form>
	</div>
	
</fieldset>

<div style="text-align:right; margin:10px;">
<a href="#" onclick="javascript:scroll(0,0);return false;">[top]</a>
</div>
-->