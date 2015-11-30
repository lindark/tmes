		<!-- basic scripts -->
		<!--[if !IE]> -->
<script type="text/javascript">
	window.jQuery || document.write("<script src='${base}/template/admin/assets/js/jquery.min.js'>"+"<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${base}/template/admin/assets/js/jquery1x.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
<script type="text/javascript">
	if('ontouchstart' in document.documentElement) document.write("<script src='${base}/template/admin/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
</script>
		<script src="${base}/template/admin/assets/js/bootstrap.min.js"></script>
		
		
		<!-- page specific plugin scripts -->
		<!--[if lte IE 8]>
		  <script src="${base}/template/admin/assets/js/excanvas.min.js"></script>
		<![endif]-->
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.custom.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.easypiechart.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.sparkline.min.js"></script>
		<script src="${base}/template/admin/assets/js/flot/jquery.flot.min.js"></script>
		<script src="${base}/template/admin/assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="${base}/template/admin/assets/js/flot/jquery.flot.resize.min.js"></script>
		
		
		<!-- ace scripts -->
		<script src="${base}/template/admin/assets/js/ace-elements.min.js"></script>
		<script src="${base}/template/admin/assets/js/ace.min.js"></script>
		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		<link rel="stylesheet" href="${base}/template/admin/assets/css/ace.onpage-help.css" />
		<link rel="stylesheet" href="${base}/template/admin/docs/assets/js/themes/sunburst.css" />
		<script type="text/javascript"> ace.vars['base'] = '${base}/template/admin/'; </script>
		<script src="${base}/template/admin/assets/js/ace/elements.onpage-help.js"></script>
		<script src="${base}/template/admin/assets/js/ace/ace.onpage-help.js"></script>
		<script src="${base}/template/admin/docs/assets/js/rainbow.js"></script>
		<script src="${base}/template/admin/docs/assets/js/language/generic.js"></script>
		<script src="${base}/template/admin/docs/assets/js/language/html.js"></script>
		<script src="${base}/template/admin/docs/assets/js/language/css.js"></script>
		<script src="${base}/template/admin/docs/assets/js/language/javascript.js"></script>
		<script type="text/javascript" src="${base}/template/common/js/jquery.cookie.js"></script>
		<#include "/WEB-INF/template/common/include_access.ftl">
	<script type="text/javascript">
	    //open Menu
		jQuery(function($) {
			var path = document.location.pathname;
			var actionName = path.replace(/\/admin\//, '');
			//alert(actionName);
			var str = actionName.substring(actionName.indexOf("!") + 1,actionName.indexOf("."));
			var flag = urleach(actionName);
			if(flag==false)
				actionName = actionName.replace(str,"list");
			var aObj = jQuery(".nav-list li a[href='"+actionName+"']");
			if(aObj.parent().is("li")){
				aObj.parent().addClass("active");
			}
			liOpen(aObj);
			
		});
		/**
		*用地址栏在左边导航栏中寻找，找到 return true, 未找到  return false;
		*/
		function urleach(actionName){
			var flag = false;
			jQuery(".nav-list li").each(function(){
				var sVal = jQuery(this).find("a").attr("href");
				sVal = sVal.replace(/\/admin\//, '');
				if(actionName == sVal)
					flag = true;
			})
			return flag;
		}
		
		function liOpen(xObj){
			if(xObj.length>0){
			    if(xObj.is("li")&&xObj.hasClass("hsub")&&!xObj.hasClass("open")){
			       xObj.addClass("open hsub");
			       xObj.find("ul").eq(0).removeClass("nav-hide");
			       xObj.find("ul").eq(0).addClass("submenu nav-show");
			       xObj.find("ul").eq(0).css("display","block");
			       xObj.find("ul").eq(0).show();
			       //递归展开上级菜单
			       ulOpen(xObj.parent());
			    }else{
			       liOpen(xObj.parent());//递归触发上级对象点击事件
			    }
		    }
		    
		}
		
		function ulOpen(uxObj){
		  if(uxObj.length>0){
		    if(uxObj.is("ul")){
		      	uxObj.removeClass("nav-hide");
			    uxObj.addClass("submenu nav-show");
			    uxObj.css("display","block");
			    uxObj.show();
		    }else{
		      ulOpen(uxObj.parent());
		    }
		  }
		}
  </script>
