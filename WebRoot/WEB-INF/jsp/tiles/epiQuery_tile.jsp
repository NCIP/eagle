<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

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
			<html:option value="Group1">Group1</html:option>
			<html:option value="Group2">Group2</html:option>
			<html:option value="Group3">Group3</html:option>
		</html:select>
	</div>

	<a name="smoking"></a>
	<h4 class="underlineRight">
		Tobacco Consumption
	</h4>

	<div>
		<b>Smoking Status (Cigarette)</b>
		<html:select property="smokingStatus">
			<html:option value="current">Current Smoker</html:option>
			<html:option value="former">Former Smoker</html:option>
			<html:option value="occasional">Occasional Smoker</html:option>
			<html:option value="non-smoker">Non-smoker</html:option>
		</html:select>
	</div>

	<div>
		<b>Cigarette Smoking</b><br/>
		Intensity: <html:text property="intensity" />
		(cigarettes per day)
		<br />
		Duration: <html:text property="duration" />
		(years smoked)
		<br />
		Age at Initiation:	<html:text property="ageAtInitiation" />
		(years)
		<br />
		Years Since Quitting: <html:text property="yearsSinceQuitting" />
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
			<html:option value="1">1</html:option>
			<html:option value="2">2</html:option>
		</html:select>
	</div>

	<a name="demographics"></a>
	<h4 class="underlineRight">
		Patient Characteristics
	</h4>
	<div>
		<b>Age (years)</b>
		lower:
		<html:select property="ageLower" style="width:60px">
			<html:option value="10">10</html:option>
			<html:option value="20">20</html:option>
		</html:select>
		upper:
		<html:select property="ageUpper" style="width:60px">
			<html:option value="80">80</html:option>
			<html:option value="90">90</html:option>
		</html:select>
		&nbsp;years
	</div>

	<div>
		<b>Self Reported Gender</b>
		<html:select property="gender">
			<html:option value="male">Male</html:option>
			<html:option value="female">Female</html:option>
			<html:option value="other">other/unknown</html:option>
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

	<div>
		<b>Waist Circumference Range</b>
			<html:text property="waistLower" size="5" /> to
			<html:text property="waistUpper" size="5" /> cm
 
<!--  
		<html:select property="waistMeasure">
			<html:option value="inches">inches</html:option>
			<html:option value="cm">cm</html:option>
		</html:select>
-->
	</div>

	<div>
		<b>Education Level (highest)</b>
		<html:select property="educationLevel">
			<html:option value="hs">High School</html:option>
			<html:option value="bs">Bachelors</html:option>
			<html:option value="ms">Masters	</html:option>
			<html:option value="phd">PhD</html:option>
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
				<html:option value="a1">
					area 1
				</html:option>
				<html:option value="a2">
					area 2
				</html:option>
			</html:select>
	</div>

	<div>
		<b>Maritial Status</b>
			<html:select property="maritalStatus">
				<html:option value="single">Single</html:option>
				<html:option value="married">Married</html:option>
				<html:option value="divorced">Divorced</html:option>
				<html:option value="widowed">Widowed</html:option>
			</html:select>
	</div>

	<div>
		<b>Religion</b>
			<html:select property="religion">
				<html:option value="r1">
					religion 1
				</html:option>
				<html:option value="r2">
					religion 2
				</html:option>
			</html:select>
	</div>


	<a name="behavioral"></a>
	<h4 class="underlineRight">
		Behavioral
	</h4>
	<div>
		<b>Depression Score</b>
			<html:select property="depressionScore" disabled="true">
				<html:option value="1">
					1
				</html:option>
				<html:option value="2">
					2
				</html:option>
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
	<!--  h4 class="underlineRight">
		Diet Notes
	</h4>
	<div>
		<b>Diet Items</b><br/>
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
	</div -->

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
		select which relatives had lung cancer
		<br />
		<html:checkbox property="relativesWithCancer" value="mother"/>
		Mother
		<html:checkbox property="relativesWithCancer" value="father"/>
		Father
		<br />
		<html:checkbox property="relativesWithCancer" value="grandmotherm"/>
		Grandmother (Maternal)
		<html:checkbox property="relativesWithCancer" value="grandfatherm"/>
		Grandfather (Maternal)
		<br />
		<html:checkbox property="relativesWithCancer" value="grandfatherp"/>
		Grandfather (Paternal)
		<html:checkbox property="relativesWithCancer" value="grandmotherp"/>
		Grandfather (Paternal)
		<br />
		<html:checkbox property="relativesWithCancer" value="sister"/>
		Sister
		<html:checkbox property="relativesWithCancer" value="brother"/>
		Brother
		<br />
	</div>
	
	<div>
		<b>Relative's Smoking Status</b>
		<br/>
		select which relatives were smokers (smoked more than 100 cigarettes
		in their lifetime)
		<br />
		<html:checkbox property="relativesWhoSmoked" value="mother"/>
		Mother
		<html:checkbox property="relativesWhoSmoked" value="father"/>
		Father
		<br />
		<html:checkbox property="relativesWhoSmoked" value="grandmotherm"/>
		Grandmother (Maternal)
		<html:checkbox property="relativesWhoSmoked" value="grandfatherm"/>
		Grandfather (Maternal)
		<br />
		<html:checkbox property="relativesWhoSmoked" value="grandfatherp"/>
		Grandfather (Paternal)
		<html:checkbox property="relativesWhoSmoked" value="grandmotherp"/>
		Grandfather (Paternal)
		<br />
		<html:checkbox property="relativesWhoSmoked" value="sister"/>
		Sister
		<html:checkbox property="relativesWhoSmoked" value="brother"/>
		Brother
		<br />
	</div>


	<a name="occupation"></a>
	<h4 class="underlineRight">
		Environmental Tobacco Smoke
	</h4>
	<div>
		<b>Occupational Exposure to smoking</b><br/>
		<b id="jobs">
			<html:text property="jobs(name_1)" value="job name" />
			<html:text property="jobs(startDate_1)" size="6" value="start date"/>
			<html:text property="jobs(endDate_1)" size="6" value="end date"/>
			<html:select property="jobs(smokiness_1)">
				<html:option value="">
					select smokiness..
				</html:option>
				<html:option value="light">
					light
				</html:option>
				<html:option value="moderate">
					moderate
				</html:option>
				<html:option value="heavy">
					heavy
				</html:option>
			</html:select>
			<br />
		</b>
		<div>
			<a href="#" onclick="EpiQuery.insertTile('jobs'); return false;">[more]</a>		
			*All Items will be combined with an "AND"
		</div>
	</div>
	
	<div>
		<b>Living Companions who smoked</b><br/>
		<b id="living">
			<html:select property="livingCompanions(livingCompanion_1)">
				<html:option value="">
					select person..
				</html:option>
				<html:option value="father">
					father
				</html:option>
				<html:option value="mother">
					mother
				</html:option>
				<html:option value="sister">
					sister
				</html:option>
			</html:select>
			<html:text property="livingCompanions(companionYears_1)" size="6" value="years" />
			<html:text property="livingCompanions(companionHoursPerDay_1)" size="6" value="hrs/day" />
			<html:select property="livingCompanions(companionProduct_1)">
				<html:option value="">
					select product..
				</html:option>
				<html:option value="cigar">
					cigar
				</html:option>
				<html:option value="cigarette">
					cigarette
				</html:option>
				<html:option value="pipe">
					pipe
				</html:option>
				<html:option value="cigarello">
					cigarello
				</html:option>
			</html:select>
			<br />
		</b>
		<div>
			<a href="#" onclick="EpiQuery.insertTile('living'); return false;">[more]</a>
			*All Items will be combined with an "AND"
		</div>
	</div>
	
	<div>
		<b>Exposure to smoke</b>
		<br/>
		select the areas in which you were exposed to smoke
		<br />
		<html:checkbox property="smokingAreas" value="childhood" />
		Childhood
		<br />
		<html:checkbox property="smokingAreas" value="workplace" />
		Workplace
		<br />
		<html:checkbox property="smokingAreas" value="adulthoodInHome" />
		Adulthood (in home)
		<br />
	</div>

	<div style="text-align:center">
		<button>Submit Query </button>
	</div>
</div>
</p>
</html:form>