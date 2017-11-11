var isSubmitting = false;
$(document).ready(function(){
        $(function(){
        $('#mydiv').submit(function calc(e){
        	if(isSubmitting){
        		return;
        	}
        	isSubmitting=true;
                e.preventDefault();
                var form = $(this);
                var post_url = form.attr('action');
                if($('#first').val()==null){
                	return;
                }
                if($('#second').val()==null){
                	return;
                }
                var post_data = {
                      first : $('#first').val(),
                      second : $('#second').val(),
                      act : $('#act option:selected').val()
                };
                $('#myform', form).html('Please wait...');
                $.ajax({
                    type: 'POST',
                    url: post_url, 
                    contentType: "application/json",
                    data: JSON.stringify(post_data),
                    async: true,
                    success: function(msg) {
                        $(form).fadeOut(100, function(){
                            form.html(msg).fadeIn();
                        });
                        isSubmitting=false;
                    },
                    error: function(message) {
                    	 $(form).fadeOut(100, function(){
                             form.html(message).fadeIn();

                         });
                    	 isSubmitting=false;
                    }
                });
            });
        });
         });