function calculate() {
$(function() {
    $.ajax({
  success: function(html){ 
$("#mydiv").html(html);
}
      
    })
})
}