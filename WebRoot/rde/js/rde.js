//	global RDE configurable variables
	var stw = 55; 			//default width, may affect the rounding of the chr, use caution
	var hmod = 2; 			//height modifier, zoom *hmod
	//var maxZoomLevel = 5; //not used?
	var currentZoom = 0; 	//hold the current zoom level
	var chrNumber = "1";
	
	//TODO: makes reference to existing HTML elements, should we auto generate this in RDE constructor?
	var chrName = "chr"; 					//table id that holds the chr browser, must be a table
	var loadingContainerId = "loading"; 	//id of the loading div with indicator icon
	var tContainerId = "tImage"; 			//id of the t-chart div
//	end global variables	
	
//	Main global objects
	var rde; 		//RDE 
	var browser; 	//ChromosomeView
	var view;		//ExpressionView
//

RDE = Class.create();
RDE.prototype = {
	initialize: function()	{
		console.log("in RDE const");
		
			var loadingDiv = document.createElement("div");
			loadingDiv.style.width="100%";
			loadingDiv.style.height="50px";
			loadingDiv.style.backgroundColor="yellow";
			loadingDiv.style.position="absolute";
			loadingDiv.style.top="0";
			loadingDiv.style.right="0";
			loadingDiv.style.display="none";
			loadingDiv.style.backgroundImage="url(img/indicator.gif)";
			loadingDiv.style.backgroundRepeat="no-repeat";
			loadingDiv.style.backgroundPosition="top right";
			loadingDiv.innerHTML = "Loading... ";
			loadingDiv.style.zIndex="999";
			loadingDiv.className="loadingHdr";
			loadingDiv.id="loadingHdr";
			//should use prototype1.6 builder here
			document.getElementsByTagName("body")[0].appendChild(loadingDiv);
			console.log("added the loading");
			$('loadingHdr').show();
			
		new Ajax.Updater('rdeHere', 'rdeTemplate.html', {
			onComplete: function()	{
				console.log("complete");
				ChromosomeBrowser.getChromosomeCytobands(chrNumber, rde.makeBrowser);
				ChromosomeBrowser.getDataForRegionJSON(chrNumber, null, null, rde.makeGraph);
				new Draggable(chrName,{constraint:'vertical',ghosting:false, change: function(dr){$(tContainerId).hide(); $(loadingContainerId).show(); }, revert: rde.updateGraph});
				console.log("all compelte");
			}
		}
		);
		
		//call these in onComplete ?
//		ChromosomeBrowser.getChromosomeCytobands(chrNumber, this.makeBrowser);
//		ChromosomeBrowser.getDataForRegion(chrNumber, null, null, this.makeGraph);
//		new Draggable(chrName,{constraint:'vertical',ghosting:false, change: function(dr){$(tContainerId).hide(); $(loadingContainerId).show(); }, revert: this.updateGraph});
		
	},
	updateGraph : function(dr)	{
		console.log(dr.style.top.sub('px', '').sub('-', '')); 
		var temp;
		if(dr.style.top.include('-'))
			temp = Number(dr.style.top.sub('px', '').sub('-', ''));
		else 
			temp = 0;
		console.log(temp * browser.scale);
		ChromosomeBrowser.getDataForRegion(chrNumber, (temp * browser.scale), ((temp + 500) * browser.scale), rde.makeGraph);
		setTimeout(
			function(){
				$(loadingContainerId).hide(); 
				$(tContainerId).show(); 
			}, 500); 
	},
	makeBrowser: function(bands)	{
		console.log("in makeBrowser");
		//console.log(bands);
		browser = new ChromosomeView(chrName, chrNumber, null, bands);
	},
	makeGraph: function(values)	{
		console.log("in makeGraph");
		view = new ExpressionView("group1", "group2", values);
		//TODO: get real grp names?
	},
	swapIt: function()	{
		$(tContainerId).hide();
		$(loadingContainerId).show();
		setTimeout(function()	{
			$(loadingContainerId).hide();
			$(tContainerId).show();
		}, 500);
	},
	
	zoom : function(z)	{
		this.swapIt();
   
		if(z=="out" && (currentZoom-1)<0)	{
			//	console.log("cant zoom out again");
			return;
		}
		
		var mod; //hold the modification value	
		if(z == "in")	{
			mod = "*"+hmod;
			currentZoom++;
		}
		else if(z=="out")	{
			mod = "/"+hmod;
			currentZoom--;
		}
		else	{			
			//TODO: figure out resets, re-draw the chr, reset the Tchart
			location.href=location.href;
			mod = "/"+Math.pow(hmod, currentZoom);
			currentZoom = 0;	
		}
	  
		//width stays the same, just change the height of each TD and the height of hte ARCs
		var tdss = $$('table#' + chrName+ ' td'); //should ref chrName var insead of string?
		tdss.each(function(el)	{
			if(el.getStyle("height")!=null)	{
				var h = Math.round( eval( parseInt(el.style.height.replace("px", "")) + mod) );
				el.style.height = h;
			}
		} );
	
	}
};

	// This is the ChromosomeView on the left hadn side of the page. 
	// It dynamically draws from the database.
	ChromosomeView = Class.create();
	Object.extend(ChromosomeView.prototype, {
		div: null, 
		chromosome: null, 
		arms: null,
		bands: null,
		scale: 200000,
		initialize:function (div, chromosome, arms, bands) {
			Object.extend(this, chromosome, arms, bands);
			if ($(div)) {
				this.div = div;
				this.chromosome = chromosome;
				this.arms = arms;
				this.bands = bands;
				this.createBands(bands);
			}
		},
		createBands: function(cytobands) {
		
			$(this.div).style.width = stw + "px";
			
			var shade = false;

			for (var index = 0; index < cytobands.length; ++index) {
				var heightY = (cytobands[index].end - cytobands[index].start) / this.scale;
				var newTr = document.createElement("tr");
				var newTd = document.createElement("td");
				newTr.appendChild(newTd);
				newTd.style.height = heightY + "px";
				if(shade) {
					newTd.style.backgroundColor = "black";
				}
				shade = !shade;
				$(this.div).appendChild(newTr);
			}
			
		}
		
	});
	
	ExpressionView = Class.create();
	Object.extend(ExpressionView.prototype, {
		group1div: null, 
		group2div: null,
		values: null,
		initialize:function (div1, div2, values) {
			
//		var tmp = values.evalJSON();
//		console.log("JSON Data: ");
//		console.log(tmp);
		
			Object.extend(this, div1, div2, values);
				this.drawBars(values);
		},		
		drawBars: function(values) {
			var c = $(tContainerId);
			var tab = document.createElement("table");
			tab.cellPadding="0";
			tab.cellSpacing="0";
			tab.border="0";
			tab.style.width="700px";
		
/* start JSON impl */
			var reporters = values.evalJSON();
			console.log("unique loc/rep: " + reporters.length);
			
			for(var i=0; i<reporters.length; i++)	{
				var rep = reporters[i];
				var repId = rep.reporterId;
				var start = rep.start;
				var end = rep.end;
				var chr = rep.chromosome;
				
				//create the data row with 1-N ticks
				var newTr = document.createElement("tr");
				//Left, Center, Right TDs
				var newTdL = document.createElement("td");
				var newTdC = document.createElement("td");
				var newTdR = document.createElement("td");
				
				var newTdAnnot = document.createElement("td");
				
				newTr.appendChild(newTdL);
				newTr.appendChild(newTdC);
				newTr.appendChild(newTdR);
				newTr.appendChild(newTdAnnot);
				newTdAnnot.style.fontSize="10px";
				newTdAnnot.style.fontFamily="tahoma";
				newTdAnnot.style.width="400px";
				newTdAnnot.innerHTML = "Start: " + start + " End: " + end + " Reporter: " + repId;
				
				//inner T bar cell
				newTdC.innerHTML = "&nbsp;";
				newTdC.style.backgroundColor="#000";

				//for each dataTypes[] 
				for(var ii=0; ii<rep.dataTypes.length; ii++)	{
					var dtype = rep.dataTypes[ii];
					var fc = dtype.foldChange;
					var dt = dtype.dataType;

					var newDiv = document.createElement("div");
					newDiv.className = dt;
					
					if(fc<0)	{
						newDiv.style.margin = "0 0 0 auto;"
						newTdL.appendChild(newDiv);
						newDiv.style.borderRight = "0px";
					}
					else	{
						newDiv.style.margin = "0 auto 0 0";
						newTdR.appendChild(newDiv);
						newDiv.style.borderLeft = "0px";
					}
					
					
					newDiv.style.width = ((Math.abs(fc) / 10) * 100 * 10) + "px";
					
					newDiv.onmouseover = function()	{ this.style.backgroundColor = this.getStyle('color'); };
					newDiv.onmouseout  = function()	{ this.style.backgroundColor=''; };
					newDiv.onclick = function()	{ alert(this.style.width.sub('px','')); };
					
					newDiv.innerHTML = "&nbsp;";
					newDiv.style.fontSize="8px"; //this makes the bars height
					
					if(newDiv.siblings().length>0)	{
						var padd = newDiv.siblings().length * 5;
						newDiv.style.top = padd*-1;
					}
				}
				tab.appendChild(newTr);
				
				//create the spacer row
				var blankTr = document.createElement("tr");
				var blankTdL = document.createElement("td");
				var blankTdC = document.createElement("td");
				var blankTdR = document.createElement("td");
				var blankTdAnnot = document.createElement("td");
				blankTdAnnot.style.backgroundColor="#fff";
				
				blankTdC.style.backgroundColor="#000";
				blankTdL.style.fontSize = "10px";
				blankTdL.innerHTML="&nbsp;";
				blankTdC.innerHTML="&nbsp;&nbsp;&nbsp;";
				blankTdR.innerHTML="&nbsp;";
				blankTr.style.backgroundColor="#f0f0f0";
				blankTr.appendChild(blankTdL);
				blankTr.appendChild(blankTdC);
				blankTr.appendChild(blankTdR);
				blankTr.appendChild(blankTdAnnot);
				tab.appendChild(blankTr);
			
			}
/* end JSON impl */


/*	
//TO BE REMOVED: 
//this block is based on the non JSON return values		

			for (var index = 0; index < values.length; ++index) {
				console.log(values[index]);
				
				//spacing between reps (insert a blank row with a certain height
				//space height is fixed for testing now
				if(index>0 && values[index].reporterId != values[index-1].reporterId)	{
					//is the current one the same as the prev
						//blank row
						//console.log("spacing between reps");
						var blankTr = document.createElement("tr");
						var blankTdL = document.createElement("td");
						var blankTdC = document.createElement("td");
						var blankTdR = document.createElement("td");
						blankTdC.style.backgroundColor="#000";
						blankTdL.style.fontSize = "10px";
						blankTdL.innerHTML="&nbsp;";
						blankTdC.innerHTML="&nbsp;&nbsp;&nbsp;";
						blankTdR.innerHTML="&nbsp;";
						blankTr.style.backgroundColor="#f0f0f0";
						blankTr.appendChild(blankTdL);
						blankTr.appendChild(blankTdC);
						blankTr.appendChild(blankTdR);
						tab.appendChild(blankTr);
				}

				var newTr = document.createElement("tr");
				//Left, Center, Right TDs
				var newTdL = document.createElement("td");
				var newTdC = document.createElement("td");
				var newTdR = document.createElement("td");
				
				var newDiv = document.createElement("div");
				newTr.appendChild(newTdL);
				newTr.appendChild(newTdC);
				newTr.appendChild(newTdR);
				newTdC.innerHTML = "&nbsp;";
				newTdC.style.backgroundColor="#000";

				newDiv.className = values[index].sampleType;
				
				if(values[index].foldChange<0)	{
					newDiv.style.cssFloat="right";
					newTdL.appendChild(newDiv);
				}
				else	{
					newTdR.appendChild(newDiv);
				}
				
				newDiv.style.width = ((Math.abs(values[index].foldChange) / 10) * 100 * 10) + "px";
				
				newDiv.innerHTML = "&nbsp;";
				
				newDiv.style.fontSize="12px"; //this makes the bars height
				newDiv.style.color = "black";
				
				newTdL.style.fontSize="12px";
				newTdL.innerHTML += values[index].reporterId + " " + values[index].start + " - " + values[index].end;
				//newDiv.innerHTML += Math.round(values[index].foldChange*1000)/1000;
				tab.appendChild(newTr);
			}
*/


			c.appendChild(tab);
			setTimeout( function() { $('loadingHdr').hide(); }, 1000);
		}



	});