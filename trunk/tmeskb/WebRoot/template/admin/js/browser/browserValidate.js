$(function(){
	// 表单验证 ajax 
	$("form.validateajax").validate({
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
					var index = parent.layer.getFrameIndex(window.name);
					if(data.status=="error"){
						parent.layer.msg(data.message);
						return false;
					}
					
					parent.layer.msg('保存成功');
					parent.layer.close(index);
//					var zTree = $.fn.zTree.getZTreeObj("ingageTree");
//					zTree.cancelEditName(data.deptName);
				},error:function(data){
					layer.alert("保存失败",{
						shade: 0
					});
				}
			});
		}
	});
	
})