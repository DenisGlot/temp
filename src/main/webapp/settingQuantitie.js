
var isSubmitting = false;

$('.minus-btn').on('click', function(e) {
	e.preventDefault();
	var $this = $(this);
	var $input = $this.closest('div').find('input');
	var value = parseInt($input.val());

	if (value > 1) {
		value = value - 1;
	} else {
		value = 0;
	}

	$input.val(value);
	update();

});

$('.plus-btn').on('click', function(e) {
	e.preventDefault();
	var $this = $(this);
	var $input = $this.closest('div').find('input');
	var value = parseInt($input.val());

	if (value < 100) {
		value = value + 1;
	} else {
		value = 100;
	}

	$input.val(value);
	update();
});


function update(){
        $(function(){
        	if(isSubmitting){
        		return;
        	}
        	isSubmitting=true;
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
                        $('body').html(msg).fadeIn(1000);
                        isSubmitting=false;
                    },
                    error: function(message) {
                    	$('body').html(msg);
                        isSubmitting=false;
                    }
                });
        });
     }