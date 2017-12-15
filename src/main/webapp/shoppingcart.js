var isSubmitting = false;
$(document).ready(function(){
        $(function(){
        $('html').submit(function calc(e){
        	if(isSubmitting){
        		return;
        	}
        	isSubmitting=true;
                e.preventDefault();
                var form = $(this);
                var post_url = form.attr('action');
                if($('#quantity').val()==null){
                	return;
                }
                var post_data = {
                      quantity : $('#quantity').val()
                };
                $('#total-price', form).fadeOut(1000);
                $.ajax({
                    type: 'POST',
                    url: post_url, 
                    contentType: "application/json",
                    data: JSON.stringify(post_data),
                    async: true,
                    success: function(msg) {
                        $('#total-price').html(msg).fadeIn(1000);
                        isSubmitting=false;
                    },
                    error: function(message) {
                    	$('#total-price').html(msg);
                        isSubmitting=false;
                    }
                });
            });
        });
         });