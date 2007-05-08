function spawnx(url, winw, winh, name) {
    var w = window.open(url, name, "screenX=0,screenY=0,status=yes,toolbar=no,menubar=no,location=no,width=" + winw + ",height=" + winh + ",scrollbars=yes,resizable=yes");
	
	//check for pop-up blocker
    if (w == null || typeof (w) == "undefined") {
        alert("You have pop-ups blocked.  Please click the highlighted link at the top of this page to view the report.  You may disable your pop-up blocker for this site to avoid doing this in the future.");
        /*
		if(document.all) {
			  document.all.popup.visible = "true"; 
			  document.all.popup.className = "pop";
		      document.all.popup.innerText = "You have pop-ups blocked.  Click <a href="javascript:spawnx('"+url+"',"+winw+","+winh+",'"+name+"');">here</a> to view the report."; 
			  
		    } else { 
			  document.getElementById('popup').visible = "true";
			  document.getElementById('popup').className= "pop";
		      document.getElementById('popup').innerHTML = "You have pop-ups blocked.  Click <a href="javascript:spawnx('"+url+"',"+winw+","+winh+",'"+name+"');">here</a> to view the report."; 	  
		} 
		*/
        document.write("<Br><Br><span class=\"pop\">You have pop-ups blocked.  Click <a href=\"javascript:spawnx('" + url + "'," + winw + "," + winh + ",'" + name + "');\">here</a> to view the report.</span>");
		//scroll(0, 8000);
    } else {
        w.focus();
    }
}

function initSortArrows(el, asc)	{
 	var htm = "up";
 	if("true" != asc)	{
 		htm = "down";
 	}
	new Insertion.After(el," <img align='right' style='float:right;' src=\"images/eagle/12-em-"+htm+".png\" />");
/*
	new Insertion.After(el," <div id='sarr' style='float:right;width:20px; height:20px;'> </div>");
	$('sarr').style.backgroundImage = "url(images/eagle/12-em-"+htm+".png)";
	$('sarr').style.backgroundPosition = "0% 0%";
	$('sarr').style.backgroundRepeat = "no-repeat";
*/
}

function initGroupReports(el)	{
	el.style.display = "none";
	//alert(el.onclick);
	//alert(el.innerHTML);
	var htm = "<a href='#' id='"+el.innerHTML+"_button' >"+el.innerHTML+"</a> ";
	new Insertion.Before($("groupReportsContainer"), htm);
	//$(el.innerHTML+"_button").onclick = function()	{ el.onclick(); };
	$(el.innerHTML+"_button").onmouseover = function()	{ overlib($('comparisonPatients_'+el.innerHTML).innerHTML, RIGHT, CAPTION, el.innerHTML+" Report", WIDTH, 300); };
	$(el.innerHTML+"_button").onmouseout = function()	{ return nd(); };
	$(el.innerHTML+"_button").onclick = function()	{ return false; };
}

function spawnAnnotation(type, item)	{
	switch(type)	{
		case 'gene':
			alert("I will link this gene ("+item+") to CGAP");
		break;
		case 'reporter':
			alert("I will link this reporter ("+item+") to LPG");		
		break;
	
	}
}