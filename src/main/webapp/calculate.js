function calculate() {
	$.ajax({
		url : "calc",
		type : "POST",
		success : function(html) {
			$('#mydiv').load('calc');
		}
	});
}