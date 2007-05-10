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
      	$('covariates').hide(); //this is for IE, it ignores the display:none
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
			$('pvalue').disable();
			$('foldchange').disable();
		}
		else	{
			$('covariates').hide();
			$('pvalue').enable();
			$('foldchange').enable();
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
			//$('nonselectedGroups').innerHTML = $('tissueGroupsOptions').innerHTML;
			// ^^ IE wont just inject the HTML, so need to create each option manually
			var ops = $('tissueGroupsOptions').getElementsByTagName("option");
			try	{
				for(var ii=0; ii<ops.length; ii++)	{
					$('nonselectedGroups').options[ii] = new Option(ops[ii].text, ops[ii].value);
				}
			}catch(e)	{ debug(e.message); }

		}
		else	{
			//blood
			//$('nonselectedGroups').innerHTML = $('bloodGroupsOptions').innerHTML;	

			var ops = $('bloodGroupsOptions').getElementsByTagName("option");
			try	{
				for(var ii=0; ii<ops.length; ii++)	{
					$('nonselectedGroups').options[ii] = new Option(ops[ii].text, ops[ii].value);
				}
			}catch(e)	{ debug(e.message); }
			
		}
		
	},
	'validate': function(fromwhere)	{
		if($('analysisName').value == "")	{
			alert("Please enter an Analysis Name");
			$('analysisName').style.border="1px solid red";
			return false;
		}
		if($('statisticalMethod').selectedIndex == 0 && $('selectedBaseline').length<1)	{
			//need a baseline
			alert("Please choose a baseline");
			$('selectedBaseline').style.border="1px solid red";
			$('selectedBaseline').style.backgroundColor = "yellow";
			
			$('nonselectedGroups').style.border="1px solid red";
			$('nonselectedGroups').style.backgroundColor = "yellow";
			return false;
		}
		if($('statisticalMethod').selectedIndex == 0 && $('selectedGroups').length!=1  )	{
			//T-TEST is 1 and 1
			alert("Please select only 1 baseline and 1 comparison group for this type of analysis");
			$('selectedGroups').style.border="1px solid red";
			$('selectedGroups').style.backgroundColor = "yellow";
			return false;
		}
		if($('statisticalMethod').selectedIndex == 1 && $('selectedGroups').length<2  )	{
			//F-TEST, needs at least 2 grps
			alert("Please select 2 or more comparison groups for this type of analysis");
			$('selectedGroups').style.border="1px solid red";
			$('selectedGroups').style.backgroundColor = "yellow";
			return false;
		}
		if($('statisticalMethod').selectedIndex == 2 && ($('selectedBaseline').length<1 || $('selectedGroups').length<1) )	{
			//GLM is 1 and M
			alert("Please select only 1 baseline and 1 or more comparison groups for this type of analysis");
			$('selectedBaseline').style.border="1px solid red";
			$('selectedBaseline').style.backgroundColor = "yellow";
			
			$('nonselectedGroups').style.border="1px solid red";
			$('nonselectedGroups').style.backgroundColor = "yellow";
			return false;
		}
		
		//save the selectedGroups
		MenuSwapper.saveMe( $('selectedGroups'),$('nonselectedGroups') );
		
		if(fromwhere == 'enter')
			return true;
		else
			document.forms[0].submit();
	
	}

}
</script>
<html:form action="classComparison.do?method=submit" onsubmit="return CCForm.validate('enter'); " styleId="ccForm">
<html:errors property="queryErrors" />

<p>
<div class="comments">
<h2 style="text-align:center">
<script type="text/javascript">Help.insertHelp("Class_comparison", "align='right'");</script>
Class Comparison Analysis
</h2>
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


<div style="display:none;" id="covariates">
	<b>Co-variates:</b>
	<br/>	
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
						<input onclick="if($('selectedBaseline').length && $('selectedBaseline').length>0){ alert('You have already selected a baseline.  Please unselect it first.'); return; }; MenuSwapper.move($('nonselectedGroups'),$('selectedBaseline'));" value="Base&gt;&gt;" type="button">
						<br/><br/>
						</b>
						
						<input onclick="MenuSwapper.move($('selectedGroups'),$('nonselectedGroups'));" value="&lt;&lt;" type="button" />
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
	<html:text size="14" value="2.0" property="foldChange" styleId="foldchange" />
			
	&nbsp;p-Value &nbsp; &le;
	<html:text size="10" value="0.05" property="pvalue" styleId="pvalue" />

</div>


 
<div style="text-align:center">
	<input type="button" onclick="return CCForm.validate(); " value="Submit Analysis" />
</div>

</div>

</p>
</html:form>