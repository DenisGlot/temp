function calculate() {
	$.ajax({
		url : "calc",
		type:"POST",
		success : function(html) {
			$('#result').innerHTML = 'Your result is : ' + html
		}
	});
}