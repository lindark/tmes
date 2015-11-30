<#assign ww=JspTaglibs["/WEB-INF/access.tld"]/>
<script type="text/javascript">
	jQuery(function($){
		var pathaccess = <@ww.access/>;//从自定义标签获得当前页面拥有的权限对象
		
		$(".access").each(function(i){
			var $this = $(this);
			var data = $(this).data("accessList");//获取data-access-list 的值
			var obj = pathaccess.list;
			$(obj).each(function(i){
				if(obj[i].accObjkey == data){//找到字段
					$this.removeClass("access");
				}
			})
		});
	})
	
	
</script>