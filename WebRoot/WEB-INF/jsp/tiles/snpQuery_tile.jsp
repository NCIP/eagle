<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.util.LabelValueBean"%>
  <link rel="stylesheet" type="text/css" href="css/autocomplete.css"/>
  <script type='text/javascript' src='dwrspring/interface/AnnotationService.js'></script>
  <script type='text/javascript' src='dwrspring/engine.js'></script>
  <script type='text/javascript' src='dwrspring/util.js'></script>
<script type="text/javascript" src="js/lib/scriptaculous/effects.js"></script>
<script type="text/javascript" src="js/lib/scriptaculous/controls.js"></script>
<script type="text/javascript" src="js/autocomplete.js"></script>

<script type="text/javascript">
 var SNPForm = {
 	validate: function(fromwhere)	{
 		var msg = "All fields are required.  Please fill in each field.";
 		
 		if($('queryName').value == "" || $('snp').value == "")	{
 			alert(msg);
 			return false;
 		}
 		
 		if(fromwhere == 'enter')	{
			return true;
		}
		else	{
			document.forms[0].submit();
		}
 	}
 
 
 }
</script>
<html:form action="snpQuery.do?method=submit" onsubmit="return SNPForm.validate('enter'); ">

	<html:errors property="queryErrors" />

	<p>

	<div class="comments">
		<h2 style="text-align:center">
			<script type="text/javascript">Help.insertHelp("SNP_query", "align='right'");</script>
			SNP Query
		</h2>
		
		<span id="errors" style="color:red"></span>
		<div>
			<b>Query Name</b>
			<html:text property="queryName" styleId="queryName" />
			(should be unique)
		</div>
	
		<div>
			<b>Patients</b>
			<html:select property="groupName">
				<html:options collection="groupNamesList" property="value" labelProperty="label"/>
			</html:select>
		</div>
		
		<div>
			<b>SNP</b>
			<html:text property="snp" styleId="snp" />
					<div id="snpSuggestList" class="auto_complete"></div>
		</div>
		
		<div style="text-align:center">
			<input type="button" value="Submit Query"  onclick="return SNPForm.validate(); " />
		</div>
	</div>
	</p>
	<script type="text/javascript">
	new Autocompleter.DWR('snp', 'snpSuggestList', updateSnpList, { partialChars: 0});
	</script>	

</html:form>
