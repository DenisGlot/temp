var isSubmitting = false;
//Make the connection with SoppingSrvlet
$('.minus-btn').on('click', function(e) {
	e.preventDefault();
	if(isSubmitting){
		return;
	}
	isSubmitting =true;
	var $this = $(this);
	var post_url = $this.attr('action');
	var $input = $this.closest('div').find('input');
	var $totalprice = $this.closest('div').next();
	var price = parseInt($this.closest('div').prev().find('.priceOfProduct').text());
	var value = parseInt($input.val());

	if (value > 1) {
		value = value - 1;
	} else {
		value = 0;
	}

	$input.val(value);
	$totalprice.html(value*price + '$');
	var post_data = {
			productId : $this.closest('div').parent().attr('id'),
	        quantity : $input.val()
      };
	$.ajax({
		    url: post_url,
		    contentType: "application/json",
            data: JSON.stringify(post_data),
		    type: 'POST',
		    success: function(msg) {
                isSubmitting=false;
            },
            error: function(message) {
                isSubmitting=false;
            }
    });

});

$('.plus-btn').on('click', function(e) {
	e.preventDefault();
	if(isSubmitting){
		return;
	}
	isSubmitting =true;
	var $this = $(this);
	var post_url = $this.attr('action');
	var $input = $this.closest('div').find('input');
	var $totalprice = $this.closest('div').next();
	var price = parseInt($this.closest('div').prev().find('.priceOfProduct').text());
	var value = parseInt($input.val());

	if (value < 100) {
		value = value + 1;
	} else {
		value = 100;
	}

	$input.val(value);
	$totalprice.html(value*price + '$');
	
	var post_data = {
			productId : $this.closest('div').parent().attr('id'),
	        quantity : $input.val()
      };
	$.ajax({
		    url: post_url,
		    contentType: "application/json",
            data: JSON.stringify(post_data),
		    type: 'POST',
		    success: function(msg) {
                isSubmitting=false;
            },
            error: function(message) {
                isSubmitting=false;
            }
    });
});