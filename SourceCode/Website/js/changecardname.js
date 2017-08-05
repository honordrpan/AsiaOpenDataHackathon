function changeName(name){
	clearmapdata();
	var ul = document.getElementById("company_detail");
	ul.innerHTML="";
	ul = document.getElementById("company_case");
	ul.innerHTML="";
	if(name=="company"){
		document.getElementById("firstcardtitle").innerHTML="公司基本資料";
		document.getElementById("firstcarddetail").innerHTML="公司詳細基本資料";
		document.getElementById("secondtitle").innerHTML="公司承包建案資料";
		document.getElementById("seconddetail").innerHTML="公司承包建案詳細資料";
		document.getElementById("thirdtitle").innerHTML="公司與公司關聯性";
		document.getElementById("thirddetail").innerHTML="公司與公司關聯性詳細資料";
	}
	else{
		document.getElementById("firstcardtitle").innerHTML="單位基本資料";
		document.getElementById("firstcarddetail").innerHTML="單位詳細基本資料";
		document.getElementById("secondtitle").innerHTML="單位承包建案資料";
		document.getElementById("seconddetail").innerHTML="單位承包建案詳細資料";
		document.getElementById("thirdtitle").innerHTML="單位與公司關聯性";
		document.getElementById("thirddetail").innerHTML="單位與公司關聯性詳細資料";
	}
}