<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<h2 style="text-align:center">
<script type="text/javascript">Help.insertHelp("AdvancedSearch_overview", "align='right'");</script>
Advanced Search
</h2>
<div class="listboxtop">
	Select an Advanced Search Type
</div>
<div class="hoaMenuBox">
	<div style="padding:10px; width:90%;">
		<a href="epiQueryInit.do?method=setup">EPI Query</a><br/>
		<div style="font-size:9px;">
	    	Perform a query using EPI data as search criteria.  Results will yield a tabular report containing EPI data for each patient.
	    </div>
	</div>
	
	<div style="padding:10px; width:90%;">
		<a href="snpQueryInit.do?method=setup">SNP Query</a><br/>
		<div style="font-size:9px;">
	    	Perform a query among patient groups based on a single SNP.  Results will yield an RxC table displaying SNP counts.
	    </div>
	</div>
<br/><br/><br/>
</div>

<script type="text/javascript">
	Nifty("div.listboxtop","top");
</script>