<script type="text/javascript" src="js/common/MenuSwapper.js"></script>
<p>
<div class="comments">
<div>
	<b>Analysis Name:</b>
	<input type="text"/>
</div>

<div class="elementTile">
	<b>Statistical Method</b>
	<select onchange="$('baseline').disabled = (this.selectedIndex == '0') ? '' : 'true'; if(this.selectedIndex == '2') { $('covariates').show();}else {$('covariates').hide();} ">
		<option> T-Test: Two Sample Test</option>
		<option> F-Test: One Way ANOVA</option>
		<option> Linear Model with/without covariate adjustment</option>
	</select>
</div>

<div id="baselineDiv">
	<b>Baseline:</b>
	<select id="baseline">
		<option>Group1</option>
		<option>Group1</option>
		<option>Group1</option>
		<option>Group1</option>
	</select>
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
	<table align="center" border="0">
			<tbody>
				<tr style="vertical-align: top;">
					<td>
						<select name="cexistingGroups" multiple="multiple" size="5"
							ondblclick="MenuSwapper.move($('cnonselectedGroups'),$('cselectedGroups'));"
							id="cnonselectedGroups" style="width: 200px;">
							<option>Age</option>
							<option>Gender</option>
							<option>Smoking Status</option>
							<option>Residential Area</option>
						</select>
					</td>
					<td style="vertical-align: middle;">
						<input
							onclick="MenuSwapper.move($('cselectedGroups'),$('cnonselectedGroups'));"
							value="&lt;&lt;" type="button"/>
						<br>
						<input
							onclick="MenuSwapper.move($('cnonselectedGroups'),$('cselectedGroups'));"
							value="&gt;&gt;" type="button"/>
					</td>
					<td>
						<select name="cselectedGroups" multiple="multiple" size="5"
							ondblclick="MenuSwapper.move($('cselectedGroups'),$('cnonselectedGroups'));"
							id="cselectedGroups" style="width: 200px;"></select>
					</td>
				</tr>
			</tbody>
		</table>
</div>

<div>
	<b>Compare Patient Groups</b><br/>


		<table align="center" border="0">
			<tbody>
				<tr style="vertical-align: top;">
					<td>
						Existing Groups
						<br>
						<select name="existingGroups" multiple="multiple" size="5"
							ondblclick="MenuSwapper.move($('nonselectedGroups'),$('selectedGroups'));"
							id="nonselectedGroups" style="width: 200px;">
							<option>
								Smokers
							</option>
							<option>
								Non-Smokers
							</option>
							<option>
								Males
							</option>
							<option>
								Females
							</option>
							<option>
								ALL
							</option>
						</select>
					</td>
					<td style="vertical-align: middle;">
						<input
							onclick="MenuSwapper.move($('selectedGroups'),$('nonselectedGroups'));"
							value="&lt;&lt;" type="button">
						<br>
						<input
							onclick="MenuSwapper.move($('nonselectedGroups'),$('selectedGroups'));"
							value="&gt;&gt;" type="button">
					</td>
					<td>
						Selected Groups
						<br>
						<select name="selectedGroups" multiple="multiple" size="5"
							ondblclick="MenuSwapper.move($('selectedGroups'),$('nonselectedGroups'));"
							id="selectedGroups" style="width: 200px;"></select>
					</td>
				</tr>

			</tbody>
		</table>


	</div>


<div>
<b>Select Constraint</b><br/>
	
	&nbsp;&nbsp;Fold Change&nbsp; &ge;	
	<input size="14" value="2.0" type="text"/>
			
	&nbsp;p-Value &nbsp; &le;
	<input size="10" value="0.05" type="text"/>

</div>


<div>
	<b>Platform:</b>
	<select>
		<option selected="true">Blood</option>
		<option>Tissue</option>
	</select>
</div>

<div style="text-align:center">
	<button>Submit Analysis</button>
</div>

</div>
</p>