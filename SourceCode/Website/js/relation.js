
function connection(){
	//alert(cy[o_index]);
	//relationGraphCY = document.getElementById("cy");
	//cy[o_index].container = relationGraphCY
	var cy1 = cytoscape(cy[o_index]);
	cy1.resize();
	var interval = setTimeout(
		function(){
			//alert($('#myCarousel').hasClass('in'));
			cy1.resize();
		}, 1000);
	
		

}
