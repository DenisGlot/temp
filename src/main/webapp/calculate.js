$(document).ready(function(){
        $(function(){
        $('#mydiv').submit(function(e){
                e.preventDefault();
                var form = $(this);
                var post_url = form.attr('action');
                var post_data = {
                      first : $('#first').val(),
                      second : $('#second').val(),
                      act : $('#act option:selected').val()
                }
                $('#myform', form).html('Please wait...');
                $.ajax({
                    type: 'POST',
                    url: post_url, 
                    contentType: "application/json",
                    data: JSON.stringify(post_data),
                    success: function(msg) {
                        $(form).fadeOut(100, function(){
                            form.html(msg).fadeIn();

                        });
                    }
                });
            });
        });
         });