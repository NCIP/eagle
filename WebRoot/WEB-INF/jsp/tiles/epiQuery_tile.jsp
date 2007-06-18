<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="org.apache.struts.util.LabelValueBean" %>


<script type="text/javascript">
//foodItems(item1)
//get the number = 1
//increment global counter
//get the innerHTML, replace "_1" with updated global counter "_2"
//do the insertion
//params - which global counter, div.innerHTML
 gDietItems = 1;
 gMedicalConditions = 1;
 gOccupationalExposure = 1;
 gLivingCompanions = 1;

var EpiQuery = {
	'insertTile' : function(whichElement)	{
		var domhtml = $(whichElement).innerHTML;
		//find 'gXXX)', ex ..item1)
		
		var newhtml;
		if(domhtml != '')	{
			switch(whichElement)	{
				case 'foodItems':	
				newhtml = domhtml.gsub("_1", "_"+(gDietItems+1));
				gDietItems+=1;
				break;
				case 'famHist':	
				newhtml = domhtml.gsub("_1", "_"+(gMedicalConditions+1));
				gMedicalConditions++;
				break;
				case 'jobs':	
				newhtml = domhtml.gsub("_1", "_"+(gOccupationalExposure+1));
				gOccupationalExposure++;
				break;
				case 'living':	
				newhtml = domhtml.gsub("_1", "_"+(gLivingCompanions+1));
				gLivingCompanions++;
				break;
			}
			if(newhtml != '')	{
				new Insertion.Before(whichElement, '<b>'+newhtml+'</b>');
			}
		}
	},
	'validate': function()	{
	
	}

};
</script>

<html:form action="epiQuery.do?method=submit" >
<html:errors property="queryErrors" />

<p>
<div class="comments">
<h2>Epidemiology Query</h2>
	<p style="font-size:10px; text-align:center;">
			<a href="#smoking">Tobacco Consumption</a> |
			<a href="#tobdep">Tobacco Dependency</a> |
			<a href="#demographics">Subject Characteristics</a> |
			<a href="#behavioral">Behavioral</a> |
			<!--   a href="#diet">Diet</a> | -->
			<a href="#familyHistory">Family History</a> |
			<a href="#occupation">Environmental Tobacco Smoke</a>
	</p>
	<div>
		<b>Query Name</b>
			<html:text property="queryName" />
			(should be unique)
	</div>

	<div>
		<b>Patients</b>
		<html:select property="patientGroup">
			<option value="">All</option>
			<html:optionsCollection property="existingGroups"/>
		</html:select>
	</div>

	<a name="smoking"></a>
	<h4 class="underlineRight">
		Tobacco Consumption
	</h4>

	<div>
		<b>Smoking Status (Cigarette)</b>
		<html:select property="smokingStatus">
			<option value="">Any</option>
			<html:optionsCollection property="existingSmokingStatus"/>
		</html:select>
	</div>

	<div>
		<b>Cigarette Smoking</b><br/>
		Intensity Range: 
		<html:text size="5" property="intensityLower" /> to
		<html:text size="5" property="intensityUpper" />
		(cigarettes per day)
		<br />
		Duration Range: <html:text size="5" property="durationLower" /> to
		<html:text size="5" property="durationUpper" />
		(years smoked)
		<br />
		Age at Initiation:	<html:text size="5" property="ageAtInitiationLower" /> to
		<html:text size="5" property="ageAtInitiationUpper" />
		(years)
		<br />
		Years Since Quitting: <html:text size="5" property="yearsSinceQuittingLower" /> to
		<html:text size="5" property="yearsSinceQuittingUpper" />
		(years)
		<br />
	</div>

	<a name="tobdep"></a>
	<h4 class="underlineRight">
		Tobacco Dependency
	</h4>
	<div>
		<b>Fagerstrom</b>
		<html:select property="fagerstromScore">
			<option value="">Any</option>
			<html:option value="1">1</html:option>
			<html:option value="2">2</html:option>
			<html:option value="3">3</html:option>
			<html:option value="4">	4</html:option>
			<html:option value="5">	5</html:option>
			<html:option value="6">	6</html:option>
			<html:option value="7">	7</html:option>
			<html:option value="8">	8</html:option>
			<html:option value="9">	9</html:option>
			<html:option value="10">10</html:option>
		</html:select>
	</div>

	<a name="demographics"></a>
	<h4 class="underlineRight">
		Patient Characteristics
	</h4>
	<div>
		<b>Age Range</b>
		
		<html:select property="ageLower" style="width:60px">
			<option value="">N/A</option>
			<html:option value="10">10</html:option>
			<html:option value="20">20</html:option>
			<html:option value="30">40</html:option>
		</html:select>
		 to 
		<html:select property="ageUpper" style="width:60px">
			<option value="">N/A</option>
			<html:option value="80">80</html:option>
			<html:option value="90">90</html:option>
		</html:select>
		&nbsp;years
	</div>

	<div>
		<b>Self Reported Gender</b>
		<html:select property="gender">
			<option value="">Any</option>
			<html:optionsCollection property="existingGender"/>
		</html:select>
	</div>

	<div>
		<b>Weight Range</b>
		<html:text property="weightLower" size="5" /> to
		<html:text property="weightUpper" size="5" /> kg
		
	<!--
		<html:select property="weightMeasure">
			<html:option value="lbs">LBS</html:option>
			<html:option value="kilos">KILOS</html:option>
		</html:select>
	-->
	</div>

	<div>
		<b>Height Range</b>
			<html:text property="heightLower" size="5" /> to
			<html:text property="heightUpper" size="5" /> cm
<!-- 		<html:select property="heightMeasure">
				<html:option value="inches">Inches</html:option>
				<html:option value="cm">CM</html:option>
			</html:select>
-->
	</div>

	<!-- 
	<div>
 
		<b>Waist Circumference Range</b>
			<html:text property="waistLower" size="5" /> to
			<html:text property="waistUpper" size="5" /> cm
	--> 
<!--  
		<html:select property="waistMeasure">
			<html:option value="inches">inches</html:option>
			<html:option value="cm">cm</html:option>
		</html:select>

	</div>
-->

	<div>
		<b>Education Level (highest)</b><br/>
		<html:select property="educationLevel">
			<option value="">Any</option>
			<html:optionsCollection property="existingEducationLevel"/>
		</html:select>
	</div>
<!-- 
	<div>
		<b>Socio-ecomomic Status</b>
			<html:select property="socioEconomicStatus">
				<html:option value="m1">
					measure 1
				</html:option>
				<html:option value="m2">
					measure 2
				</html:option>
			</html:select>
	</div>
-->
	<div>
		<b>Residential Area</b>
			<html:select property="residentialArea">
				<option value="">Any</option>
				<html:optionsCollection property="existingResidentialArea"/>
			</html:select>
	</div>

	<div>
		<b>Maritial Status</b>
			<html:select property="maritalStatus">
				<option value="">Any</option>
				<html:optionsCollection property="existingMaritalStatus"/>
			</html:select>
	</div>

	<div>
		<b>Religion</b>
			<html:select property="religion">
				<option value="">Any</option>
				<html:optionsCollection property="existingReligion"/>
			</html:select>
	</div>


	<a name="behavioral"></a>
	<h4 class="underlineRight">
		Behavioral
	</h4>
	<div>
		<b>Depression Score</b>
			
			<html:select property="depressionScore" disabled="true">
				<html:option value="1">	1</html:option>
				<html:option value="2">	2</html:option>
			</html:select>
	</div>

	<div>
		<b>Anxiety Score</b>
			<html:select property="anxietyScore" disabled="true">
				<html:option value="1">
					1
				</html:option>
				<html:option value="2">
					2
				</html:option>
			</html:select>
	</div>

	<a name="diet"></a>
	<!--
	<h4 class="underlineRight">
		Diet Notes
	</h4>
	<div>
		<b>Diet Items</b> <br />
		<b id="foodItems">
			<html:select property="foodItems(item_1)">
				<html:option value="">
					select food..
				</html:option>
				<html:option value="tomatoes">
					Tomatoes
				</html:option>
				<html:option value="raw peppers">
					Raw Peppers
				</html:option>
				<html:option value="salad">
					Salad
				</html:option>
				<html:option value="potatoes">
					Potatoes
				</html:option>
				<html:option value="onions">
					Onions
				</html:option>
			</html:select>

			<html:select property="foodItems(freq_1)">
				<html:option value="">
					select frequency..
				</html:option>
				<html:option value="never">
					never
				</html:option>
				<html:option value="1-6 per season">
					1-6 times a season
				</html:option>
				<html:option value="7-11 per season">
					7-11 times a season
				</html:option>
				<html:option value="1 per month">
					once a month
				</html:option>
				<html:option value="2-3 per month">
					2-3 times a month
				</html:option>
				<html:option value="1 per week">
					once a week
				</html:option>
			</html:select>
			<br />
		</b>
		<div>
			<a href="#" onclick="EpiQuery.insertTile('foodItems'); return false;">[more]</a>
			*All Items will be combined with an "AND"
		</div>
	</div>
-->
	<a name="familyHistory"></a>
	<h4 class="underlineRight">
		Family History
	</h4>
	
	<!--   div>
		<b>Medical Conditions</b><br/>
		<b id="famHist">
			<html:select property="relatives(relative_1)">
				<html:option value="">
					select relative..
				</html:option>
				<html:option value="mother">
					mother
				</html:option>
				<html:option value="father">
					father
				</html:option>
				<html:option value="sister">
					sister
				</html:option>
				<html:option value="brother">
					brother
				</html:option>
				<html:option value="grand-mother">
					grand-mother
				</html:option>
			</html:select>
			<html:select property="relatives(condition_1)">
				<html:option value="">
					select condition...
				</html:option>
				<html:option value="bronchitis">
					chronic bronchitis
				</html:option>
				<html:option value="pneumonia">
					pneumonia
				</html:option>
				<html:option value="emphysema">
					emphysema
				</html:option>
				<html:option value="tuberculosis">
					tuberculosis
				</html:option>
			</html:select>
			<html:checkbox property="relatives(currentlyAlive_1)" value="1" />
			currently alive
			<br />
		</b>
		<div>
			<a href="#" onclick="EpiQuery.insertTile('famHist'); return false;">[more]</a>
			*All Items will be combined with an "AND"
		</div>
	</div -->
	
	
		<div>
		<b>Relatives With Lung Cancer</b>
		<br/>
		Did one of your first-degree relatives (mother, father, siblings, or children) have lung cancer?
		<br/>
		<input type="radio" name="familyLungCancer" value="-1" checked="true" />any
		<html:radio property="familyLungCancer" value="1" />yes
		<html:radio property="familyLungCancer" value="0" />no
		<html:radio property="familyLungCancer" value="9" />unknown

		<br/>
		<!--  
		select which relatives had lung cancer
		<br />
		<logic:iterate id="epiForm" name="epiForm" property="existingRelatives" scope="request">
			<html:multibox property="relativesWithCancer"> 
				<bean:write name="epiForm" property="value"/> 
			</html:multibox> 
			<bean:write name="epiForm" property="label"/> 
		</logic:iterate>
		<br />
		-->
	</div>
	
	<div>
		<b>Relative's Smoking Status</b>
		<br/>
		select which relatives were smokers (smoked more than 100 cigarettes
		in their lifetime)
		<br />
		<logic:iterate id="epiForm" name="epiForm" property="existingRelatives" scope="request">
			<html:multibox property="relativesWhoSmoked"> 
				<bean:write name="epiForm" property="value"/> 
			</html:multibox> 
			<bean:write name="epiForm" property="label"/> 
		</logic:iterate>
		<br/>
	</div>


	<a name="occupation"></a>
	<h4 class="underlineRight">
		Environmental Tobacco Smoke
	</h4>
	<!-- 
	<div>
		<b>Occupational Exposure to smoking</b><br/>
	-->
		<!--  
		<b id="jobs">
			<html:text property="jobs(name_1)" value="job name" />
			<html:text property="jobs(startDate_1)" size="6" value="start date"/>
			<html:text property="jobs(endDate_1)" size="6" value="end date"/>
			<html:select property="jobs(smokiness_1)">
				<html:optionsCollection property="existingSmokiness"/>
			</html:select>
			<br />
		</b>
		<div>
			<a href="#" onclick="EpiQuery.insertTile('jobs'); return false;">[more]</a>		
			*All Items will be combined with an "AND"
		</div>
		-->
	<!-- 
		<br/>
		<logic:iterate id="epiForm" name="epiForm" property="existingSmokiness" scope="request">
			years worked in <bean:write name="epiForm" property="label"/> smoke:
			<html:text property="<%="jobs(" +((LabelValueBean) epiForm).getValue()+ ")" %>" size="5"/><br/>
		</logic:iterate>
	</div>
	-->
	<!-- 
	<div>
		<b>Living Companions who smoked</b><br/>
	-->
		<!--  
		<b id="living">
			<html:select property="livingCompanions(livingCompanion_1)">
				<html:optionsCollection property="existingRelatives"/>
			</html:select>
			<html:text property="livingCompanions(companionYears_1)" size="6" value="years" />
			<html:text property="livingCompanions(companionHoursPerDay_1)" size="6" value="hrs/day" />
			<html:select property="livingCompanions(companionProduct_1)">
				<html:option value="">
					select product..
				</html:option>
				<html:optionsCollection property="existingTobaccoType"/>
			</html:select>
			<br />
		</b>
		<div>
			<a href="#" onclick="EpiQuery.insertTile('living'); return false;">[more]</a>
			*All Items will be combined with an "AND"
		</div>
		-->
	<!-- 
		<br/>
		<logic:iterate id="epiForm" name="epiForm" property="existingTobaccoType" scope="request">
			Companions who smoked <bean:write name="epiForm" property="label"/>s:
			<html:text property="<%="livingCompanions(" +((LabelValueBean) epiForm).getValue()+ "_hrs)" %>" size="5"/>hrs/day
			<html:text property="<%="livingCompanions(" +((LabelValueBean) epiForm).getValue()+ "_yrs)" %>" size="5"/>yrs<br/>
		</logic:iterate>
	</div>
	-->
	
	<div>
		<b>Exposure to smoke</b>
		<br/>
		select the areas in which you were exposed to smoke
		<br />
		
		<logic:iterate id="epiForm" name="epiForm" property="existingSmokingAreas" scope="request">
			<html:multibox property="smokingAreas">
				<bean:write name="epiForm" property="value"/>
			</html:multibox>
			<bean:write name="epiForm" property="label"/>
		</logic:iterate>
		<br />
	</div>

	<div style="text-align:center">
		<button onclick="document.forms[0].submit();">Submit Query </button>
	</div>
</div>
</p>
</html:form>