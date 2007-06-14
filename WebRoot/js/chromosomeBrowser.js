
var container = null;
var container_position = null;
var surface = null;
var surface_size = null;
var current_shape = null;
var current_shape_window = null;
var last_position = null;	
var box_start = 20;
var box_height = 40;	
var chromosome = '1';
var topY = 20;
var group;
var draggable;
var bands = {};
var all_arms = {};

function renderGraph (returnVal) {
	$("dataGraph").innerHTML = "<b>" + returnVal + "</b>";
}

dojo.widget.defineWidget("user.ChromosomeBrowser", 
	dojo.widget.HtmlWidget, {

		render:function () {
			dwr.util.useLoadingMessage();
			container = dojo.byId("chromosomeBrowser");
			container_position = dojo.html.abs(container);
			surface = dojo.gfx.createSurface(container, 100, 675);
			group = surface.createGroup();
			surface_size = surface.getDimensions();
			surface_size.width = parseInt(surface_size.width);
			surface_size.height = parseInt(surface_size.height);
			ChromosomeBrowserHelper.getChromosomeArms(chromosome, this.drawArms);
			dojo.event.connect(container, "onmousedown", this.handleMouseDown);
			dojo.event.connect(container, "onmousemove", this.handleMouseMove);
			dojo.event.connect(container, "onmouseup", this.handleMouseUp);
		}, 
		
		drawBands:function (cytobands) {
		console.log("in draw bands");
			var startY = topY;
			var shade = false;
			var rec;
			for (var index = 0; index < cytobands.length; ++index) {
				var heightY = (cytobands[index].end - cytobands[index].start);
				rec = {x:30, y:startY, width:30, height:heightY};
				dojo.widget.byId('chromosomeBrowser').drawBand(rec, cytobands[index].name, shade);
				shade = !shade;
				startY = startY + heightY;
			}
		}, 
		
		drawArms:function (arms) {
			var rec;
			var armHeight;
			var armStart = topY;

			for (var index = 0; index < arms.length; ++index) {
				armHeight = (arms[index].end - arms[index].start);
				rec = {x:30, y:armStart, width:30, height:armHeight, r:15};
				console.log(group);
				all_arms[index] = group.createRect(rec).setStroke({color:[0, 0, 0, 1], width:2, cap:"butt", join:2}).setFill([0, 0, 0, 0.01]).applyTransform(dojo.gfx.matrix.identity);
				all_arms[index].getEventSource().setAttribute("bandid", arms[index].name);
				armStart = armStart + armHeight;
			}
			ChromosomeBrowserHelper.getChromosomeCytobands(chromosome, dojo.widget.byId('chromosomeBrowser').drawBands);
			var g1 = surface.createGroup();
			var startPoint = {x:28, y:topY + 15};
			var endPoint = {x:62, y:topY + 15};
			var re1 = g1.createPath().moveTo(startPoint).arcTo(17, 15, 0, false, true, endPoint).lineTo(62, 5).lineTo(28, 5).lineTo(28, topY + 15).setStroke({color:[255, 255, 255, 1]}).setFill([255, 255, 255, 1]);
			var startHeight = (arms[0].end - arms[0].start);
			startPoint = {x:28, y:startHeight + 35};
			endPoint = {x:62, y:startHeight + 35};
			var re2 = g1.createPath().moveTo(startPoint).arcTo(17, 15, 0, false, true, endPoint).lineTo(62, startHeight + 6).arcTo(17, 15, 0, false, true, {x:28, y:startHeight + 6}).setStroke({color:[255, 255, 255, 1]}).setFill([255, 255, 255, 1]);
			startPoint = {x:28, y:armStart - 13};
			endPoint = {x:62, y:armStart - 13};
			var re3 = g1.createPath().moveTo(startPoint).arcTo(17, 15, 0, false, false, endPoint).lineTo(62, armStart + 6).arcTo(17, 15, 0, false, true, {x:28, y:armStart + 6}).setStroke({color:[255, 255, 255, 1]}).setFill([255, 255, 255, 1]);
			dojo.widget.byId('chromosomeBrowser').drawDraggableWindow();
		}, 
		
		drawDraggableWindow:function () {
			var box = {x:25, y:box_start, width:40, height:box_height};
			draggable = surface.createRect(box).setStroke({color:[255, 0, 0, 1], width:2, cap:"butt", join:2}).setFill([255, 0, 0, 0.5]).applyTransform(dojo.gfx.matrix.identity);
			draggable.getEventSource().setAttribute("box", "box");
			draggable.getEventSource().setAttribute("bandid", "box");
			dojo.html.setClass(draggable.getEventSource(), "movable");
			bands["box"] = draggable;
		}, 
		
		drawBand:function (rec, name, black) {
			var band;
			var fill;
			if (black) {
				fill = 0.9;
				band = group.createRect(rec).setStroke({color:[0, 0, 0, fill]}).setFill([0, 0, 0, fill]).applyTransform(dojo.gfx.matrix.identity);
			} else {
				fill = 0.1;
				band = group.createRect(rec).setStroke({color:[0, 0, 0, fill]}).setFill([0, 0, 0, fill]).applyTransform(dojo.gfx.matrix.identity);
			}
			band.getEventSource().setAttribute("bandid", name);
			bands[name] = band;
		}, 
		
		handleMouseDown:function (event) {
			var shape = dojo.widget.byId('chromosomeBrowser').getShape(event);
			if (shape && (event.target.getAttribute("bandid") == "box")) {
				current_shape = shape;
				last_position = {y:event.clientY - container_position.y};
				var params = shape.getShape();
				var topY = shape.matrix.dy + 15;
				var dy = last_position.y - topY;
				current_shape_window = {y1:dy, y2:625 + dy};
			}
			dojo.event.browser.stopEvent(event);
		}, 
		
		handleMouseUp:function (event) {
				// Log out the height of the box since the box started at
				// 20
			if (current_shape != null) {
				ChromosomeBrowserHelper.getDataForRange((current_shape.matrix.dy + box_start - 10), (current_shape.matrix.dy + box_start + box_height - 10), renderGraph);
			}
			current_shape = null;
			dojo.event.browser.stopEvent(event);
		}, 
		
		getShape:function (event) {
			var id = event.target.getAttribute("bandid");
			var s = id ? bands[id] : null;
			return s;
		}, 
		
		handleMouseMove:function (event) {
			if (!current_shape) {
				return;
			}
			var y = Math.min(Math.max(event.clientY - container_position.y, current_shape_window.y1), current_shape_window.y2);
						//var y = event.clientY - container_position.y;
			current_shape.applyTransform({dy:y - last_position.y});
			last_position = {y:y};
			dojo.event.browser.stopEvent(event);
		}
		

});

