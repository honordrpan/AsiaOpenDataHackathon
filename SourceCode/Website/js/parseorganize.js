var organizername = [];
var uniqueorganizername = [];
var count_organize = 0;
var uniqueorganize_company = [];
var marker_by_organize = [];
var company_index_ratio = [];
var temp_name = [];
$(document).ready(function() {
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var mydata1 = JSON.parse(this.responseText);
			for(i in mydata1.OrganizerToCompany){
				uniqueorganizername.push(i);
				addorganizername(i, count_organize);
				uniqueorganize_company.push(mydata1.OrganizerToCompany[i]);
				count_organize++;
			}
		}
	};
	
	xmlhttp.open("GET", "BuildCase.json", true);
	xmlhttp.send();

})
function addorganizername(name, i){
	
	  var ul = document.getElementById("organization");
	  var li = document.createElement("li");
	  var a = document.createElement("href");
	  a.appendChild(document.createTextNode(name));
	  var text = "<a href='#' onclick='showorganizedetail("+i+");'>"+name+"</a>"
	  li.innerHTML=text;
	  li.setAttribute("id", name); // added line
	  ul.appendChild(li);
}

function clearorganizefontsize(){
	for(i=0;i<uniqueorganizername.length;i++){
		document.getElementById(uniqueorganizername[i]).style.fontSize  = "large";
	}
}



function showorganizedetail(o_name){
	
	cy_index = o_name;
	claearmarker();
	clearmapdata();
	//alert(uniqueorganizername[i]);
	clearorganizefontsize();
	document.getElementById(uniqueorganizername[o_name]).style.fontSize  = "x-large";
	//alert(uniqueorganize_company[o_name]);
	document.getElementById("firstcardtitle").innerHTML = "<h3 style='color:#008CBA;'>"+uniqueorganizername[o_name]+"</h3>"+"承接案件公司";
	
	var ul = document.getElementById("company_detail");
	ul.innerHTML="";
	temp_name = [];
	for(i=0; i<uniqueorganize_company[o_name].length; i++){
		temp_name.push(uniqueorganize_company[o_name][i]);
	}
	
	
	for(i=0;i<temp_name.length; i++){
		var li = document.createElement("li");
		var a = document.createElement("href");
		a.appendChild(document.createTextNode(temp_name[i]));
		var text = "<a href='#' onclick='markerjump("+i+")'>"+temp_name[i]+"</a>"
		li.innerHTML=text;
		li.setAttribute("id", i); // added line
		ul.appendChild(li);
	}
	var company_index = [];
	
	for(i=0;i<temp_name.length; i++){
		//alert(companyname.indexOf(temp_name[i]));
		company_index.push(companyname.indexOf(temp_name[i]));
	}
	
	var company_index_value = new Array(company_index.length);
	var company_index_money = new Array(company_index.length);
	for(i=0;i<company_index_value.length;i++){
		company_index_value[i] = 0;
		company_index_money[i] = 0;
	}
		
	
	for(i=0;i<company_index.length;i++){
		
		for(j=0;j<companyall[company_index[i]].length;j++){
			
			
			if(companyall[company_index[i]][j].Organizer == uniqueorganizername[o_name]){
				company_index_value[i]++;
				company_index_money[i] += parseInt(companyall[company_index[i]][j].Price);
				//alert("11");
			}
				
		}
		//alert(company_index_value[i]);//company_index_value[i]是 i公司做過多少次 uniqueorganizername[o_name]的工程
	}
	
	company_index_ratio = [];
	var money_sum = 0;
	for(i=0;i<company_index_money.length;i++){
		money_sum+=company_index_money[i];
	}
	//var r = 0;
	for(i=0;i<company_index_money.length;i++){
		var ratio = company_index_money[i]/money_sum*100;
		var num = new Number(ratio);
		company_index_ratio.push(num.toFixed(2));
		//alert(num.toFixed(2));
		//r+=ratio;
	}
	//alert(company_index_ratio);
	var ul = document.getElementById("company_case");
	document.getElementById("secondtitle").innerHTML = "<h3 style='color:#008CBA;'>"+uniqueorganizername[o_name]+"</h3>"+"公司承包建案資料";
	ul.innerHTML="";
	for(i=0;i<temp_name.length; i++){
		var li = document.createElement("li");
		var a = document.createElement("href");
		a.appendChild(document.createTextNode(name));
		var text = "<a href='#'>公司名稱:"+temp_name[i]+"<br>賺取金額(NT):"+company_index_money[i]+"(千元)</a><hr>";
		li.innerHTML=text;
		li.setAttribute("id", i); // added line
		ul.appendChild(li);
	}
	
	for(i=0;i<company_index.length;i++){
		companydetail_data.push({
			key:   temp_name[i],
			value: company_index_value[i]
		});
	}
	X_values = temp_name;
	series_data = company_index_money;
	drawcompanycasechart();
	drawcompanydetailchart();
	urlgps(temp_name);
	relation_organizetocompany(o_name);
}
var o_index;
function relation_organizetocompany(o_name){
	o_index = o_name;
	//alert(cy[o_name]);
	connection();	
}

var all_info = [];
var marker;
function urlgps(c_name){
	marker_by_organize = [];
	for(let i = 0 ;i<c_name.length;i++){
		var address = c_name[i];
		var fuck = company_index_ratio[i];
		console.log(fuck);
		geocoder.geocode( { 'address': address}, function(results, status) {
		  if (status == 'OK') {
			//map.setCenter(results[0].geometry.location);
			marker = new google.maps.Marker({
				map: map,
				position: results[0].geometry.location,
				icon: 'img/placeholder.png',
				animation: google.maps.Animation.DROP
			});
			marker_by_organize.push(marker);

			//alert(results[0].geometry.location);
		  } else {
			//alert('Geocode was not successful for the following reason: ' + status);
		  }
		});
	}
	
}



function claearmarker(){
	if(marker_by_organize.length >0){
		for(i=0;i<marker_by_organize.length;i++){
			marker_by_organize[i].setMap(null);
		}
	}
}

function markerjump(name){
	for(i=0;i<marker_by_organize.length;i++){
			marker_by_organize[i].setAnimation(null);
	}
	for(i=0;i<all_info.length;i++){
		all_info[i].close();
	}
	marker_by_organize[name].setAnimation(google.maps.Animation.BOUNCE);
	var contentString = '<div><h4 style="sans-serif;font-size: 22px;font-weight: 400;padding: 10px;margin: 1px;border-radius: 2px 2px 0 0;">'+temp_name[name]+'</h4><hr><h6 style="text-align: center;">公司賺取單位金額比例</h6><div id="container_chart" style="margin: 20px; width: 200px; height: 100px;"></div></div>';
	var infowindow = new google.maps.InfoWindow({
		content: contentString
    });
	infowindow.open(map, marker_by_organize[name]);
	var interval = setTimeout(
		function(){
			//alert($('#myCarousel').hasClass('in'));
			drawbar(company_index_ratio[name]);
	}, 1000);
	
	all_info.push(infowindow);
}


function drawbar(input){
var bar = new ProgressBar.SemiCircle(container_chart, {
	strokeWidth: 6,
	color: '#FFEA82',
	trailColor: '#eee',
	trailWidth: 1,
	easing: 'easeInOut',
	duration: 1400,
	svgStyle: null,
	text: {
	value: '',
	alignToBottom: false
	},
	from: {color: '#FFEA82'},
	to: {color: '#ED6A5A'},
	// Set default step function for all animate calls
	step: (state, bar) => {
	bar.path.setAttribute('stroke', state.color);
	var value = Math.round(bar.value() * 100);
	if (value === 0) {
	  bar.setText('');
	} else {
	  bar.setText(value+"%");
	}

	bar.text.style.color = state.color;
	}
	});
	bar.text.style.fontFamily = '"Raleway", Helvetica, sans-serif';
	bar.text.style.fontSize = '3rem';

	bar.animate(input*0.01);  // Number from 0.0 to 1.0
}





