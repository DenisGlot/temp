function field_focus(field, email)
{
  if(field.value == email)
  {
    field.value = '';
  }
}

function field_blur(field, email)
{
  if(field.value == '')
  {
    field.value = email;
  }
}

//Fade in dashboard box
$(document).ready(function(){
  $('.box').hide().fadeIn(1000);
  });
//Stop click event
$('#btn2').click(function(event){
  event.preventDefault(); 
  window.location.href = 'item?email='+$('#email').val()+'password='+$('#password').val();
});

function redirect(){
	 window.location.href = 'item?email='+$('#email').val()+'password='+$('#password').val();
}