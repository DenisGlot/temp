	$(document).ready(function() {
		$(".registrationForm").validate({
			rules : {
				fname : {
					required : true,
					minlength: 3,
				},
				lname : {
					required : true,
					minlength: 3,
				},
				password : {
					required : true,
					minlength: 5
				},
				confirmPassword : {
					required : true,
					minlength: 5,
					equalTo: "#password"
				}
			},
			highlight: function(e){
				$(e).closest(".form-group").removeClass('has-success').addClass('has-error');
			},
			unhighlight: function(e){
				$(e).closest(".form-group").removeClass('has-error').addClass('has-success');
			}
			
		});
	});
