var companyname = [];
var companyall = [];
var city = [];
$(document).ready(function() {
	var xmlhttp = new XMLHttpRequest();
	var mydata;
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			mydata = JSON.parse(this.responseText);
			for(var i=0; i<mydata.CompanyToCase.length; i++){
				companyname.push(mydata.CompanyToCase[i].CompanyName);
				addcompanyname(mydata.CompanyToCase[i].CompanyName,i);
				companyall.push(mydata.CompanyToCase[i].Cases);
			}
			for (x in mydata.MapToCompany) {
				city.push({
					name: x,
					company: mydata.MapToCompany[x]
				});
			}
		}
	};
	xmlhttp.open("GET", "BuildCase.json", true);
	xmlhttp.send();
})

function addcompanyname(name,i) {
  var ul = document.getElementById("company");
  var li = document.createElement("li");
  var a = document.createElement("href");
  a.appendChild(document.createTextNode(name));
  var text = "<a href='#' onclick='showcompanydetail("+i+");change_companypiedata("+i+");change_mapdata("+i+");showcompanycase("+i+");'>"+name+"</a>"
  li.innerHTML=text;
  li.setAttribute("id", i); // added line
  ul.appendChild(li);
}

function clearfontsize(){
	var com = document.getElementById("company");
	for(var i=0;i<com.childNodes.length;++i){
		var ah = com.childNodes[i].childNodes;
		if(ah.length == 1){
			ah[0].style.fontSize = "small";
		}
	}
}

function change_companypiedata(company_id){
	var temp_name = [];
	var count = 1;
	var companydetail_data_temp = [];
	companydetail_data = [];
	for(i=0; i<companyall[company_id].length; i++){
		temp_name.push(companyall[company_id][i].Organizer);
	}
	
	var unique_temp = [];
	for(i=0;i<temp_name.length;i++){
		if(unique_temp.indexOf(temp_name[i])<0)
			unique_temp.push(temp_name[i]);
	}
	var new_key_value = new Array(unique_temp.length);
	var sum = 0;
	for(i=0;i<unique_temp.length;i++){
		sum = 0;
		for(j=0;j<temp_name.length;j++){
			if(unique_temp[i]==temp_name[j])
				sum++;
		}
		new_key_value[i] = sum;
	}
	
	for(i =0; i<unique_temp.length;i++){
		companydetail_data.push({
			key:   unique_temp[i],
			value: new_key_value[i]
		});
	}
	drawcompanydetailchart();
}

function change_mapdata(company_id){
	var temp_name = [];
	mapdata = [];
	for(i=0; i<companyall[company_id].length; i++){
		temp_name.push(companyall[company_id][i].Region);
	}
	
	var unique_temp = [];
	for(i=0;i<temp_name.length;i++){
		if(unique_temp.indexOf(temp_name[i])<0)
			unique_temp.push(temp_name[i]);
	}
	var new_key_value = new Array(unique_temp.length);
	var sum = 0;
	for(i=0;i<unique_temp.length;i++){
		sum = 0;
		for(j=0;j<temp_name.length;j++){
			if(unique_temp[i]==temp_name[j])
				sum++;
		}
		new_key_value[i] = sum;
	}
	
	for(i =0; i<unique_temp.length;i++){
		mapdata.push({
			key:   unique_temp[i],
			value: new_key_value[i]
		});
	}
	drawmap();
}

function showcompanycase(company_id){
	X_values = [];
    series_data = [];
	var ul = document.getElementById("company_case");
	document.getElementById("secondtitle").innerHTML = "<h3 style='color:#008CBA;'>"+companyname[company_id]+"</h3>"+"公司承包建案資料";
	var Case = [];
	ul.innerHTML="";
	for(i=0; i<companyall[company_id].length; i++){
		Case.push({
			organizer:   companyall[company_id][i].Organizer,
			cases: companyall[company_id][i].CaseName,
			money: companyall[company_id][i].Price
		});
	}
	for(i=0;i<Case.length; i++){
		var li = document.createElement("li");
		var a = document.createElement("href");
		a.appendChild(document.createTextNode(name));
		var text = "<a href='#'>建案名稱:<br>"+Case[i].cases+"<br>金額(NT):"+Case[i].money+"(千元)</a><hr>";
		li.innerHTML=text;
		li.setAttribute("id", i); // added line
		ul.appendChild(li);
		X_values.push(Case[i].cases);
		series_data.push(Case[i].money);
	}
	drawcompanycasechart();
}


function showcompanydetail(company_id){
	claearmarker();
	var temp_name = [];
	clearfontsize();
	document.getElementById(company_id).style.fontSize  = "x-large";
	document.getElementById("firstcardtitle").innerHTML = "<h3 style='color:#008CBA;'>"+companyname[company_id]+"</h3>"+"承接案件發包單位";
	var ul = document.getElementById("company_detail");
	ul.innerHTML="";
	for(i=0; i<companyall[company_id].length; i++){
		temp_name.push(companyall[company_id][i].Organizer);
	}
	
	var unique_temp = [];
	for(i=0;i<temp_name.length;i++){
		if(unique_temp.indexOf(temp_name[i])<0)
			unique_temp.push(temp_name[i]);
	}
	for(i=0;i<unique_temp.length; i++){
		var li = document.createElement("li");
		var a = document.createElement("href");
		a.appendChild(document.createTextNode(name));
		var text = "<a href='#'>"+unique_temp[i]+"</a>"
		li.innerHTML=text;
		li.setAttribute("id", i); // added line
		ul.appendChild(li);
	}
}


