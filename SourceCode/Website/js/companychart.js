var companydetail_data = [];
function drawcompanydetailchart(){
	
	var chart = AmCharts.makeChart("company_chart", {
	  "type": "pie",
	  "startDuration": 0,
	   "theme": "light",
	  "addClassNames": true,
	  "legend":{
		"position":"right",
		"marginRight":0,
		"autoMargins":false
	  },
	  "innerRadius": "30%",
	  "defs": {
		"filter": [{
		  "id": "shadow",
		  "width": "200%",
		  "height": "200%",
		  "feOffset": {
			"result": "offOut",
			"in": "SourceAlpha",
			"dx": 0,
			"dy": 0
		  },
		  "feGaussianBlur": {
			"result": "blurOut",
			"in": "offOut",
			"stdDeviation": 5
		  },
		  "feBlend": {
			"in": "SourceGraphic",
			"in2": "blurOut",
			"mode": "normal"
		  }
		}]
	  },
	  "dataProvider": companydetail_data,
	  "valueField": "value",
	  "titleField": "key"
});
chart.labelsEnabled = false;
chart.autoMargins = false;
chart.marginTop = 0;
chart.marginBottom = 0;
chart.marginLeft = 0;
chart.marginRight = 0;
chart.pullOutRadius = 0;
chart.addListener("init", handleInit);

chart.addListener("rollOverSlice", function(e) {
  handleRollOver(e);
});

function handleInit(){
  chart.legend.addListener("rollOverItem", handleRollOver);
}

function handleRollOver(e){
  var wedge = e.dataItem.wedge.node;
  wedge.parentNode.appendChild(wedge);
}
}
