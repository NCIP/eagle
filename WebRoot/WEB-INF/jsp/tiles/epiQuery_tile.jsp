<%@ page language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<p>
<div class="comments">
<h2>Epidemiology Query</h2>
	<p style="font-size:10px; text-align:center;">
			<a href="#smoking">Tobacco Consumption</a> |
			<a href="#tobdep">Tobacco Dependency</a> |
			<a href="#demographics">Subject Characteristics</a> |
			<a href="#behavioral">Behavioral</a> |
			<a href="#diet">Diet</a> |
			<a href="#familyHistory">Family History</a> |
			<a href="#occupation">Environmental Tobacco Smoke</a>
	</p>
	<div>
		<b>Query Name</b>
			<input type="text" />
			(should be unique)
	</div>

	<div>
		<b>Patients</b>
			<select>
				<option>Group1</option>
				<option>Group2</option>
				<option>Group3</option>
				<option>Group4</option>
			</select>
	</div>

	<a name="smoking"></a>
	<h4 class="underlineRight">
		Tobacco Consumption
	</h4>

	<div>
		<b>Smoking Status</b>
		<select>
			<option>Current Smoker</option>
			<option>Former Smoker</option>
			<option>Occasional Smoker</option>
			<option>Non-smoker</option>
			<option>...	</option>
		</select>
	</div>

	<div>
		<b>Cigarette Smoking</b><br/>
		Intensity:<input type="text" />
		(cigarettes per day)
		<br />
		Duration:<input type="text" />
		(years smoked)
		<br />
		Age at Initiation:	<input type="text" />
		(years)
		<br />
		Years Since Quitting:<input type="text" />
		(years)
		<br />
	</div>

	<a name="tobdep"></a>
	<h4 class="underlineRight">
		Tobacco Dependency
	</h4>
	<div>
		<b>Fagerstrom</b>
		<select>
			<option>1</option>
			<option>2</option>
			<option>3</option>
			<option>...</option>
		</select>
	</div>

	<a name="demographics"></a>
	<h4 class="underlineRight">
		Patient Characteristics
	</h4>
	<div>
		<b>Age (years)</b>
		lower:
		<select style="width:60px">
			<option>10</option>
			<option>10</option>
		</select>
		upper:
		<select style="width:60px">
			<option>90</option>
			<option>10</option>
		</select>
		&nbsp;years
	</div>

	<div>
		<b>Self Reported Gender</b>
		<select>
			<option>Male</option>
			<option>Female</option>
			<option>other/unknown</option>
		</select>
	</div>

	<div>
		<b>Weight</b>
		<input type="text" />
		<select>
			<option>LBS</option>
			<option>KILOS</option>
		</select>
	</div>

	<div>
		<b>Height</b>
			<input type="text" />
			<select>
				<option>Inches</option>
				<option>CM</option>
			</select>
	</div>

	<div>
		<b>Waist Circumference</b>
		<select>
			<option>20</option>
			<option>21</option>
			<option>33</option>
			<option>34</option>
			<option>...</option>
		</select>

		<select>
			<option>inches</option>
			<option>cm</option>
			<option>...</option>
		</select>
	</div>

	<div>
		<b>Education Level (highest)</b>
		<select>
			<option>High School</option>
			<option>Bachelors</option>
			<option>Masters	</option>
			<option>PhD</option>
		</select>
	</div>

	<div>
		<b>Socio-ecomomic Status</b>
			<select>
				<option>
					measure 1
				</option>
				<option>
					measure 2
				</option>
			</select>
	</div>

	<div>
		<b>Residential Area</b>
			<select>
				<option>
					area 1
				</option>
				<option>
					area 2
				</option>
			</select>
	</div>

	<div>
		<b>Maritial Status</b>
			<select>
				<option>Single</option>
				<option>Married</option>
				<option>Divorced</option>
				<option>Widowed</option>
			</select>
	</div>

	<div>
		<b>Religion</b>
			<select>
				<option>
					religion 1
				</option>
				<option>
					religion 2
				</option>
			</select>
	</div>


	<a name="behavioral"></a>
	<h4 class="underlineRight">
		Behavioral
	</h4>
	<div>
		<b>Depression Score</b>
			<select>
				<option>
					1
				</option>
				<option>
					2
				</option>
			</select>
	</div>

	<div>
		<b>Anxiety Score</b>
			<select>
				<option>
					1
				</option>
				<option>
					2
				</option>
			</select>
	</div>

	<a name="diet"></a>
	<h4 class="underlineRight">
		Diet Notes
	</h4>
	<div>
		<b>Diet Items</b>
		<div id="foodItems">
			<select>
				<option>
					select food..
				</option>
				<option>
					Tomatoes
				</option>
				<option>
					Raw Peppers
				</option>
				<option>
					Salad
				</option>
				<option>
					Potatoes
				</option>
				<option>
					Onions
				</option>
				<option>
					etc
				</option>
			</select>
			<select>
				<option>
					select frequency..
				</option>
				<option>
					never
				</option>
				<option>
					1-6 times a season
				</option>
				<option>
					7-11 times a season
				</option>
				<option>
					once a month
				</option>
				<option>
					2-3 times a month
				</option>
				<option>
					once a week
				</option>
				<option>
					etc
				</option>
			</select>
			<br />
		</div>
		<div>
			<a href="#"
				onclick="new Insertion.Before('foodItems', '<div>'+$('foodItems').innerHTML+'</div>');return false;">[more]</a>
			*All Items will be combined with an "AND"
		</div>
	</div>

	<a name="familyHistory"></a>
	<h4 class="underlineRight">
		Family History
	</h4>
	<div>
		<b>Medical Conditions</b>
		<div id="famHist">
			<select>
				<option>
					select relative..
				</option>
				<option>
					mother
				</option>
				<option>
					father
				</option>
				<option>
					sister
				</option>
				<option>
					brother
				</option>
				<option>
					grand-mother
				</option>
				<option>
					etc
				</option>
			</select>
			<select>
				<option>
					select condition...
				</option>
				<option>
					chronic bronchitis
				</option>
				<option>
					pneumonia
				</option>
				<option>
					emphysema
				</option>
				<option>
					tuberculosis
				</option>
				<option>
					etc
				</option>
			</select>
			<input type="checkbox">
			currently alive
			<br />
		</div>
		<div>
			<a href="#"
				onclick="new Insertion.Before('famHist', '<div>'+$('famHist').innerHTML+'</div>');return false;">[more]</a>
			*All Items will be combined with an "AND"
		</div>
	</div>
	<div>
		<b>Relative's Smoking Status</b>
		<br/>
		Select which relatives were smokers (smoked more than 100 cigarettes
		in their lifetime)
		<br />
		<input type="checkbox" />
		Mother
		<input type="checkbox" />
		Father
		<br />
		<input type="checkbox" />
		Grandmother (Maternal)
		<input type="checkbox" />
		Grandfather (Maternal)
		<br />
		<input type="checkbox" />
		Grandfather (Paternal)
		<input type="checkbox" />
		Grandfather (Paternal)
		<br />
		<input type="checkbox" />
		Sister
		<input type="checkbox" />
		Brother
		<br />
	</div>


	<a name="occupation"></a>
	<h4 class="underlineRight">
		Environmental Tobacco Smoke
	</h4>
	<div>
		<b>Occupational Exposure to smoking</b>
		<div id="jobs">
			<input type="text" value="job name">
			<input type="text" size="6" value="start date">
			<input type="text" size="6" value="end date">
			<select>
				<option>
					select smokiness..
				</option>
				<option>
					light
				</option>
				<option>
					moderate
				</option>
				<option>
					heavy
				</option>
				<option>
					etc
				</option>
			</select>
			<br />
		</div>
		<div>
			<a href="#"
				onclick="new Insertion.Before('jobs', '<div>'+$('jobs').innerHTML+'</div>');return false;">[more]</a>
			*All Items will be combined with an "AND"
		</div>
	</div>
	
	<div>
		<b>Living Companions who smoked</b>
		<div id="living">
			<select>
				<option>
					select person..
				</option>
				<option>
					father
				</option>
				<option>
					mother
				</option>
				<option>
					sister
				</option>
				<option>
					etc
				</option>
			</select>
			<input type="text" size="6" value="years">
			<input type="text" size="6" value="hrs/day">
			<select>
				<option>
					select product..
				</option>
				<option>
					cigar
				</option>
				<option>
					cigarette
				</option>
				<option>
					pipe
				</option>
				<option>
					etc
				</option>
			</select>
			<br />
		</div>
		<div>
			<a href="#"
				onclick="new Insertion.Before('living', '<div>'+$('living').innerHTML+'</div>');return false;">[more]</a>
			*All Items will be combined with an "AND"
		</div>
	</div>
	
	<div>
		<b>Exposure to smoke</b>
		<br/>
		Select the areas in which you were exposed to smoke
		<br />
		<input type="checkbox" />
		Childhood
		<br />
		<input type="checkbox" />
		Workplace
		<br />
		<input type="checkbox" />
		Adulthood (in home)
		<br />
	</div>

	<div style="text-align:center">
		<button>Submit Query </button>
	</div>
</div>
</p>
