$(function(){
	// 表单验证
	$("form.validate").validate({
		errorClass: "validateError",
		ignore: ".ignoreValidate",
		errorPlacement: function(error, element) {
			var messagePosition = element.metadata().messagePosition;
			if("undefined" != typeof messagePosition && messagePosition != "") {
				var $messagePosition = $(messagePosition);
				if ($messagePosition.size() > 0) {
					error.insertAfter($messagePosition).fadeOut(300).fadeIn(300);
				} else {
					error.insertAfter(element).fadeOut(300).fadeIn(300);
				}
			} else {
				error.insertAfter(element).fadeOut(300).fadeIn(300);
			}
		},
		submitHandler: function(form) {
			alert("进不来");
			$(form).find(":submit").attr("disabled", true);
			form.submit();
		}
	});
	
})