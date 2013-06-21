<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<html:form action="clinicalQuery.do?method=submit" >
<html:errors property="queryErrors" />

<p>
<div class="comments">

<h2>Clinical Query</h2>
<div>
	<b>Query Name</b>
	<html:text property="queryName" />
	(should be unique)
</div>

<div>
	<b>Patients</b>
	<html:select property="patientGroup">
		<html:option value="Group1">Group1</html:option>
		<html:option value="Group2">Group2</html:option>
		<html:option value="Group3">Group3</html:option>
	</html:select>
</div>

<div>
	<b>TNM Values</b> 
	T: <html:text property="tvalue" size="5"/>
	N: <html:text property="nvalue" size="5"/>
	M: <html:text property="mvalue" size="5"/>
</div>

<div>
	<b>Stage</b>
	<html:select property="stage" multiple="true" size="4" style="width:100px;">
		<html:option value="I">I</html:option>
		<html:option value="II">II</html:option>
		<html:option value="III">III</html:option>
		<html:option value="IV">IV</html:option>
	</html:select>

	<b>Grade</b>
	<html:select property="grade" multiple="true" size="4" style="width:100px">
		<html:option value="1">1</html:option>
		<html:option value="2">2</html:option>
		<html:option value="3">3</html:option>
		<html:option value="4">4</html:option>
	</html:select>

	<b>Histology</b>
	<html:select property="histology" multiple="true" size="4" style="width:100px">
		<html:option value="1">1</html:option>
		<html:option value="2">2</html:option>
		<html:option value="3">3</html:option>
		<html:option value="4">4</html:option>
	</html:select>
</div>

<div>	
	<b>Cotanine Level Range</b> <html:text property="cotanineMin" /> to <html:text property="cotanineMax" />
</div>


<div style="text-align:center">
	<button>Submit Query</button>
</div>

</div>

</p>
</html:form>