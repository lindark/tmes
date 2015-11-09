$(function(){
	// 表单验证 ajax 
	$("form.validate").validate({
		errorClass: "validateError",
		ignore: ".ignoreValidate",
		onkeyup:false,
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
			$.ajax({	
				url: $(form).attr("action"),
				data: $(form).serialize(),
				dataType: "json",
				async: false,
				beforeSend: function(data) {
					$(this).attr("disabled", true);
				},
				success: function(data) {
					$.tip(data.status, data.message);
					var $dom = $("#dialog-message");
					$dom.dialog( "close" ); 
//					var zTree = $.fn.zTree.getZTreeObj("ingageTree");
//					zTree.cancelEditName(data.deptName);
				}
			});
		}
	});
	
})