<%--L
   Copyright SAIC

   Distributed under the OSI-approved BSD 3-Clause License.
   See http://ncip.github.com/eagle/LICENSE.txt for details.
L--%>

<html>
<head>
<!-- 
	This is the app specific page, edit as necessary (graphics, header, footer, etc)
	-The JS includes and 'rdeHere' containers must exist
	-Paths to the JS libs may vary (needs prototype1.5.1+ and scriptaculous1.7+)
	-DWR ChromosBrowser must be properly configured
	-Instantiate the RDE onload, via prototype Event.observe()
 -->
	<title>RDE</title>
	<script type="text/javascript" src="../js/lib/prototype.js"></script>
	<script type="text/javascript" src="../js/lib/scriptaculous/effects.js"></script>
	<script type="text/javascript" src="../js/lib/scriptaculous/dragdrop.js"></script>
	<!--  
	   <script type="text/javascript" src="../js/lib/scriptaculous/slider.js"></script>
	   <script type="text/javascript" src="../js/wz_jsgraphics.js"></script>
	-->
	<script type="text/javascript" src="../dwrspring/interface/ChromosomeBrowser.js"></script>
	<script type="text/javascript" src="../dwrspring/util.js"></script>   
	<script type="text/javascript" src="../dwrspring/engine.js"></script>  
	<script type="text/javascript" src="js/rde.js"></script>  
   
	<script type="text/javascript">
		Event.observe(window, 'load', function() {  rde = new RDE();  }, false);
	
		//left for debugging...remove the below funcs
		function pageInit()	{ console.log("old pageInit"); }
		function updateGraph(dr){ console.log("old updateGraph"); }	
		function makeBrowser(bands) { console.log("old makeBrowser"); }	
		function makeGraph(values) { console.log("old makeGraph");	}	
		function swapIt()	{ console.log("old swapIt"); }
		function zoom(z)	{ console.log("old zoom");	}

   </script>  
	<style>
	#loadingHdr, .loadingHdr	{
		opacity: .5;
		filter: alpha(opacity=50);
	}
		.blood, .tissue, .cancer	{
			/*
				opacity: .5;
				filter: alpha(opacity=50);
			*/
			border:0px;
		}
		.blood, .Blood {
color:red;
			border-right: 2px solid red;
			border-left: 2px solid red;
			border-top: 2px solid red;
			position:relative;
		}
		.tissue, .TissueNormal {
			border-right: 2px solid green;
			border-left: 2px solid green;
			border-top: 2px solid green;
			position:relative;
			color:green;
		}
		.cancer, .TissueCancer {
			border-right: 2px solid blue;
			border-left: 2px solid blue;
			border-top: 2px solid blue;
			position:relative;
			color:blue;
		}
	</style>
</head>
<body>
	<h1>RDE PROTOTYPE</h1>
	<div id="rdeHere"></div>
</body>
</html>
