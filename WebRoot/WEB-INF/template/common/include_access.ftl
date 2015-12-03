<#assign ww=JspTaglibs["/WEB-INF/access.tld"]/>
<script type="text/javascript">

jQuery(function($){
	var pathaccess =<@ww.access/>;//从自定义标签获得当前页面拥有的权限对象
	
	$(".access").each(function(i){
		var $this = $(this);
		var data = $(this).data("accessList");//获取data-access-list 的值
		var obj = pathaccess.list;
		$(obj).each(function(i){
			if(obj[i].accObjkey == data){//找到字段
				if(obj[i].type=="button"){//如果是按钮
					$this.removeClass("access");
				}else if(obj[i].type=="input"){//文本框
					alert(obj[i].state);
					if(obj[i].state=="3"){//隐藏
						
					}else if(obj[i].state=="2"){//必填
						$this.removeClass("access");
						$this.find(":input").addClass("formText {required: true}");
					}else if(obj[i].state=="0"){//只读
						$this.removeClass("access");
						$this.find(":input").each(function(i){
							var sVal = $(this).val();
							$(this).hide().after("<span>"+sVal+"</span>");
						});
					}else if(obj[i].state=="1"){//编辑
						$this.removeClass("access");
					}else{//按钮
						$this.removeClass("access");
					}
				}
			}
		})
	});
})

	
</script>
 