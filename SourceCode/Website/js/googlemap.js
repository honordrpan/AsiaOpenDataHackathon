var taiwan = {lat: 23.249108, lng: 120.817430 };
var geoJsonObject;
var cityname = [];
var thejson;
var map;
var mapdata = [];
var infordata = [	
		{
			counties: "連江縣",
			GPS: "26.151848,119.937325"
		},
		{
			counties: "金門縣",
			GPS: "24.444965,118.375524"
		},
		{
			counties: "宜蘭縣",
			GPS: "24.689869,121.745654"
		},
		{
			counties: "彰化縣",
			GPS: "24.053414,120.559423"
		},
		{
			counties: "南投縣",
			GPS: "23.897105,120.964629"
		},
		{
			counties: "雲林縣",
			GPS: "23.708760,120.377591"
		},
		{
			counties: "屏東縣",
			GPS: "22.623697,120.552908"
		},
		{
			counties: "台東縣",
			GPS: "22.911241,121.018930"
		},
		{
			counties: "花蓮縣",
			GPS: "23.790869,121.392522"
		},
		{
			counties: "澎湖縣",
			GPS: "23.573355,119.619034"
		},
		{
			counties: "基隆市",
			GPS: "25.115944,121.735249"
		},
		{
			counties: "新竹市",
			GPS: "24.817913,120.967026"
		},
		{
			counties: "台北市",
			GPS: "25.046706,121.552274"
		},
		{
			counties: "新北市",
			GPS: "25.013465,121.455615"
		},
		{
			counties: "台中市",
			GPS: "24.233621,120.635861"
		},
		{
			counties: "台南市",
			GPS: "23.019707,120.227084"
		},
		{
			counties: "桃園市",
			GPS: "24.972870,121.206060"
		},
		{
			counties: "苗栗縣",
			GPS: "24.563369,120.816557"
		},
		{
			counties: "新竹縣",
			GPS: "24.713709,121.164644"
		},
		{
			counties: "嘉義市",
			GPS: "23.479583,120.459252"
		},
		{
			counties: "嘉義縣",
			GPS: "23.453020,120.624053"
		},
		{
			counties: "高雄市",
			GPS: "22.656957,120.355556"
		}
	];
var geocoder;
function initMap() {
	geocoder = new google.maps.Geocoder();
	map = new google.maps.Map(document.getElementById('map'), {
	  zoom: 8,
	  center: taiwan,
	  styles:mapstyle
	});
	drawmap();
}

function drawmap(){
	clearmapdata();
	$.getJSON("counties.json", function(data){
		geoJsonObject = topojson.feature(data, data.objects.map);
		map.data.addGeoJson(geoJsonObject); 
	}); 
	
	map.data.setStyle(function(feature) {
		var SD_NAME = feature.getProperty('name');
		var color = "#fff2e6";
		for(i=0;i<mapdata.length;i++){
			if(SD_NAME == mapdata[i].key){
				color = colorselect(mapdata[i].value);
				//setinforwindow(mapdata[i].key);
			}
		}
		return {
		  fillOpacity: 0.7,
		  fillColor: color,
		  strokeWeight: 3
		}
	});

	// Set the fill color to red when the feature is clicked.
	// Stroke weight remains 3.
	map.data.addListener('click', function(event) {	
	   document.getElementById("select_map").innerHTML = "選取範圍:"+event.feature.getProperty('name');
	   var ul = document.getElementById("company");
	   ul.innerHTML="";
	   map.data.revertStyle();
	   map.data.overrideStyle(event.feature, {strokeWeight: 7,fillColor: "#fff2e6"});
	   map.data.setStyle(function(feature) {
			var color = "#fff2e6";
			return {
			  fillOpacity: 0.7,
			  fillColor: color,
			  strokeWeight: 3
			}
		});
	   for(i=0;i<companyall.length;i++){
		   for(j=0;j<companyall[i].length;j++){
				if(companyall[i][j].Region==event.feature.getProperty('name')){
					var li = document.createElement("li");
					var a = document.createElement("href");
					a.appendChild(document.createTextNode(companyname[i]));
					var text = "<a href='#' onclick='showcompanydetail("+i+");change_companypiedata("+i+");change_mapdata("+i+");showcompanycase("+i+");'>"+companyname[i]+"</a>"
					li.innerHTML=text;
					li.setAttribute("id", i); // added line
					ul.appendChild(li);
					break;
				}
		   }
	   }
	});
}

function clearmapdata(){
	map.data.forEach(function (feature) {
		map.data.remove(feature);
	});
	$.getJSON("counties.json", function(data){
		geoJsonObject = topojson.feature(data, data.objects.map);
		map.data.addGeoJson(geoJsonObject); 
	}); 
	map.data.setStyle(function(feature) {
		var SD_NAME = feature.getProperty('name');
		var color = "#fff2e6";
		return {
		  fillOpacity: 0.7,
		  fillColor: color,
		  strokeWeight: 3
		}
	});
}




function colorselect(input){
	if(input==1)
		return '#F3B6D9'
	else if(input==2)
		return '#F79BCF'
	else if(input==3)
		return '#EA96C6'
	else if(input==4)
		return '#C17AA2'
	else if(input==5)
		return '#A23974'
	else if(input==6)
		return '#FF906B'
	else if(input==7)
		return '#FC6E3F'
	else if(input==8)
		return '#FE5D26'
	else if(input==9)
		return '#FA4616'
	else if(input>9)
		return '#E53D00'
}


