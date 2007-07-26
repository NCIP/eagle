<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.util.LabelValueBean"%>

<html:form action="snpQuery.do?method=submit">
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
			<html:select property="groupNames">
				<html:options collection="groupNamesList" property="value" labelProperty="label"/>
			</html:select>
		</div>
		
		<div>
			<b>SNP</b>
			<html:text property="snp" styleId="snp" />
		</div>
		
		<div style="text-align:center">
			<input type="button" value="Submit Query" />
		</div>
	</div>
	</p>
</html:form>
