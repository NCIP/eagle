<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<script type="text/javascript" src="js/common/MenuSwapper.js"></script>
<script type="text/javascript">

Event.observe(window, 'load',
      function() { 
      	$('ccForm').reset(); //force reset on soft refresh
      }
    );

var CCForm = {
	'changeStat': function(el)	{
		if(el.selectedIndex == '1')	{
			//$('baselineButtons').hide();
			$$('#baselineButtons input', '#baselineField select').each( function(s)	{
				s.disabled = "true";
			} );
			
			//clear the baseline
			MenuSwapper.move($('selectedBaseline'),$('nonselectedGroups'));
			//$('baselineField').hide(); 
		} 
		else	{ 
			//$('baselineButtons').show();
			$$('#baselineButtons input', '#baselineField select').each( function(s)	{
				s.disabled = "";
			} );
			//$('baselineField').show();
		} 
		
		if(el.selectedIndex == '2')	{ 
			$('covariates').show();
		}
		else	{
			$('covariates').hide();
		}
	},
	'changeSpecimen' : function(el)	{
		//clear all selections, hide the existing options and show the correct ones
		MenuSwapper.saveMe( $('selectedGroups'),$('nonselectedGroups') ); //select them all
		MenuSwapper.move($('selectedGroups'),$('nonselectedGroups')); //move them back
		
		//clear the baseline
		MenuSwapper.move($('selectedBaseline'),$('nonselectedGroups'));
		
		if(el.selectedIndex != 0)	{
			//tissue
			$('nonselectedGroups').innerHTML = $('tissueGroupsOptions').innerHTML;
			//$('bloodGroups').hide();
			//$('tissueGroups').show();
		}
		else	{
			//$('tissueGroups').hide();
			//$('bloodGroups').show();	
			$('nonselectedGroups').innerHTML = $('bloodGroupsOptions').innerHTML;	
		}
		
	},
	'validate': function()	{
		if($('analysisName').value == "")	{
			alert("Please enter an Analysis Name");
			$('analysisName').style.border="1px solid red";
			return false;
		}
		if($('statisticalMethod').selectedIndex == 0 && $('selectedBaseline').length<1)	{
			//need a baseline
			alert("Please choose a baseline");
			$('selectedBaseline').style.border="1px solid red";
			$('nonselectedGroups').style.border="1px solid red";
			return false;
		}
		else	{
		
			
		}
		
		//save the selectedGroups
		MenuSwapper.saveMe( $('selectedGroups'),$('nonselectedGroups') );
		return true;
	
	}

}
</script>
<html:form action="classComparison.do?method=submit" onsubmit="return CCForm.validate(); " styleId="ccForm">
<html:errors property="queryErrors" />

<p>
<div class="comments">
<h2>Class Comparison Analysis</h2>
<div>
	<b>Analysis Name:</b>
	<html:text property="analysisName" styleId="analysisName"/>
</div>


<div>
	<b>Specimen Type:</b>
	<html:select property="specimenType" styleId="platform" onchange="CCForm.changeSpecimen(this);">
		<html:optionsCollection property="existingSpecimenTypes"/>
	</html:select>
</div>

<div class="elementTile">
	<b>Statistical Method</b><br/>
	<html:select style="margin-left:50px" styleId="statisticalMethod" property="statisticalMethod" 
	onchange="CCForm.changeStat(this); ">
		<html:option value="TTEst"> T-Test: Two Sample Test</html:option>
		<html:option value="FTest"> F-Test: One Way ANOVA</html:option>
		<html:option value="GLM">Generalized Linear Model with/without covariate adjustment</html:option>
	</html:select>
</div>


<div id="covariates" style="display:none">
	<b>Co-variates:</b>
	<br/>
	<!-- 
	<input type="checkbox"/>Gender
	<input type="checkbox"/>Smoking Status
	<input type="checkbox"/>Age
	<input type="checkbox"/>Residential Area
	-->
	
		<html:checkbox property="selectedCovariates" value="age">Age</html:checkbox>
		<html:checkbox property="selectedCovariates" value="gender">Gender</html:checkbox>
		<html:checkbox property="selectedCovariates" value="smokingStatus">Smoking Status</html:checkbox>
		<html:checkbox disabled="true" property="selectedCovariates" value="residentialArea">Residential Area</html:checkbox>
			
</div>

<div>
	<b>Compare Patient Groups</b><br/>


		<table align="center" border="0">
			<tbody>
				<tr style="vertical-align: top;">
					<td>
						Existing Groups
						<br/>
						<span style="display:none; width:1px; height:1px; overflow:none;" id="optionsContainer">
							<select id="bloodGroupsOptions">
							<logic:iterate id="classComparisonForm" name="classComparisonForm" property="existingGroups" scope="request">
								<option value="<bean:write name="classComparisonForm" property="value"/>"><bean:write name="classComparisonForm" property="value"/></option>
							</logic:iterate>
							</select>
							
							<select id="tissueGroupsOptions">
							<logic:iterate id="classComparisonForm" name="classComparisonForm" property="existingTissueGroups" scope="request">
								<option value="<bean:write name="classComparisonForm" property="value"/>"><bean:write name="classComparisonForm" property="value"/></option>
							</logic:iterate>
							</select>
		
						</span>
						
						<span id="bloodGroups">
						<html:select property="existingGroups" multiple="multiple" size="8"
							ondblclick="MenuSwapper.move($('nonselectedGroups'),$('selectedGroups'));"
							styleId="nonselectedGroups" style="width: 200px;">
							<html:optionsCollection property="existingGroups"/>
						</html:select>
						</span>
						<!-- 
						<span id="tissueGroups" style="display:none">
						<html:select property="existingGroups" multiple="multiple" size="8"
							ondblclick="MenuSwapper.move($('nonselectedGroups'),$('selectedGroups'));"
							styleId="nonselectedGroups" style="width: 200px;">
							<html:optionsCollection property="existingTissueGroups"/>
						</html:select>
						</span>
						-->
					</td>
					<td style="vertical-align: middle;" id="menuSwapperButtons">
						<b id="baselineButtons">
						<input onclick="MenuSwapper.move($('selectedBaseline'),$('nonselectedGroups'));" value="&lt;&lt;Base" type="button">
						<br/>	
						<input onclick="if($('selectedBaseline').length && $('selectedBaseline').length>0){ alert('You have already selected a baseline.  Please unselect it first.'); return; }; MenuSwapper.move($('nonselectedGroups'),$('selectedBaseline'));" value="Base&gt;&gt;" type="button">
						<br/><br/>
						</b>
						
						<input onclick="MenuSwapper.move($('selectedGroups'),$('nonselectedGroups'));" value="&lt;&lt;" type="button" />
						<br/>
						<input onclick="MenuSwapper.move($('nonselectedGroups'),$('selectedGroups'));" value="&gt;&gt;" type="button" />
					</td>
					<td>
						<span id="baselineField">
						Baseline Group<br/>
						<select name="baseline" id="selectedBaseline" size="1" style="width:200px;">
						</select><br/>
						</span>
						Selected Groups
						<br/>
						<html:select property="selectedGroups" multiple="multiple" size="5"
							ondblclick="MenuSwapper.move($('selectedGroups'),$('nonselectedGroups'));"
							styleId="selectedGroups" style="width: 200px;"></html:select>
					</td>
				</tr>
			</tbody>
		</table>
</div>

<div>
<b>Select Constraint</b><br/>
	
	&nbsp;&nbsp;Fold Change&nbsp; &ge;	
	<html:text size="14" value="2.0" property="foldChange" />
			
	&nbsp;p-Value &nbsp; &le;
	<html:text size="10" value="0.05" property="pvalue" />

</div>


 
<div style="text-align:center">
	<button onclick="return CCForm.validate(); ">Submit Analysis</button>
</div>

</div>

</p>
</html:form>