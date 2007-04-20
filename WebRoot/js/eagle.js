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