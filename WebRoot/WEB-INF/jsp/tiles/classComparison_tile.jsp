<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<script type="text/javascript" src="js/common/MenuSwapper.js"></script>
<script type="text/javascript">
var CCForm = {
	'changeStat': function(el)	{
		if(el.selectedIndex != '0')	{
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
		
		if(this.selectedIndex == '2')	{ 
			$('covariates').show();
		}
		else	{
			$('covariates').hide();
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
<html:form action="classComparison.do?method=submit" >
<html:errors property="queryErrors" />

<p>
<div class="comments">
<h2>Class Comparison Analysis</h2>
<div>
	<b>Analysis Name:</b>
	<html:text property="analysisName" styleId="analysisName"/>
</div>

<div class="elementTile">
	<b>Statistical Method</b>
	<html:select styleId="statisticalMethod" property="statisticalMethod" 
	onchange="CCForm.changeStat(this); ">
		<html:option value="t-test"> T-Test: Two Sample Test</html:option>
		<html:option value="f-test"> F-Test: One Way ANOVA</html:option>
		<html:option value="lm"> Linear Model with/without covariate adjustment</html:option>
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
	
	<html:select property="covariate" style="width: 200px;">
		<html:option value="age">Age</html:option>
		<html:option value="gender">Gender</html:option>
		<html:option value="smokingStatus">Smoking Status</html:option>
		<html:option value="residentialArea">Residential Area</html:option>
	</html:select>				
</div>

<div>
	<b>Compare Patient Groups</b><br/>


		<table align="center" border="0">
			<tbody>
				<tr style="vertical-align: top;">
					<td>
						Existing Groups
						<br/>
						<html:select property="existingGroups" multiple="multiple" size="8"
							ondblclick="MenuSwapper.move($('nonselectedGroups'),$('selectedGroups'));"
							styleId="nonselectedGroups" style="width: 200px;">
							<html:optionsCollection property="existingGroups"/>
						</html:select>
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


<div>
	<b>Platform:</b>
	<html:select property="platform" styleId="platform">
		<html:option value="blood">Blood</html:option>
		<html:option value="tissue">Tissue</html:option>
	</html:select>
</div>
 
<div style="text-align:center">
	<button onclick="return CCForm.validate();  ">Submit Analysis</button>
</div>

</div>

</p>
</html:form>