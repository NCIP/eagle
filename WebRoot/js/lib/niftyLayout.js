/*nifty corners layout*/

NiftyLoad=function(){
try	{
	Nifty("div#menu a","small transparent top");
	Nifty("ul#intro li","same-height");
	
	Nifty("div.date");

	Nifty("div#content,div#side","same-height");
	Nifty("div#content", "same-height");
	Nifty("div#side", "same-height");
	
	Nifty("div.comments div");
	Nifty("div#footer");
	Nifty("div#container","bottom");
	}
catch(er)	{
//	debug(er.description);
}
}