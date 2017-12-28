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
                $.ajax({
                    type: 'POST',
                    url: post_url, 
                    contentType: "application/json",
                    data: JSON.stringify(post_data),
                    async: true,
                    success: function(msg) {
                    	$('.myError').html(jQuery(msg).find('.myError').html());
                        $('#myResult').html(jQuery(msg).find('#myResult').html());
                        isSubmitting=false;
                    },
                    error: function(message) {
                    	$('body').html(msg);
                        isSubmitting=false;
                    }
                });
            });
        });
         });