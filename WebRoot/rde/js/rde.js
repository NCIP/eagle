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
		new Ajax.Updater('rdeHere', 'rdeTemplate.html', {
			onComplete: function()	{
				console.log("complete");
				ChromosomeBrowser.getChromosomeCytobands(chrNumber, rde.makeBrowser);
				ChromosomeBrowser.getDataForRegion(chrNumber, null, null, rde.makeGraph);
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
		ChromosomeBrowser.getDataForRegion(chrNumber, (temp * browser.scale), ((temp + 500) * browser.scale), makeGraph);
		setTimeout(
			function(){
				$(loadingContainerId).hide(); 
				$(tContainerId).show(); 
			}, 500); 
	},
	makeBrowser: function(bands)	{
		console.log("in makeBrowser");
		console.log(bands);
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
			Object.extend(this, div1, div2, values);
			if ($(div1) && $(div2)) {
				this.group1div = div1;
				this.group2div = div2;
				this.values = values;
				this.drawBars(values);
			}
		},		
		drawBars: function(values) {
			for (var index = 0; index < values.length; ++index) {
				console.log(values[index]);
				var locToAdd;
				if(values[index].foldChange > 0) {
					locToAdd = $(this.group2div);
				} else {
					locToAdd = $(this.group1div);
				}
				var newTr = document.createElement("tr");
				var newTd = document.createElement("td");
				var newDiv = document.createElement("div");
				newTd.appendChild(newDiv);
				newTr.appendChild(newTd);
				newDiv.className = values[index].sampleType;
				
				if(values[index].foldChange<0)	{
					newDiv.style.cssFloat="right";
				}
				
				newDiv.style.width = ((Math.abs(values[index].foldChange) / 10) * 100) + "px";
				
				newDiv.innerHTML = "&nbsp;";
				locToAdd.appendChild(newTr);
			}
		}
	});