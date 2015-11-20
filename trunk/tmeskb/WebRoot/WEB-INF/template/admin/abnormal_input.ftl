<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑异常 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body{background:#fff;}
</style>
</head>
<body class="no-skin input">
	
<!-- add by welson 0728 -->	
<#include "/WEB-INF/template/admin/admin_navbar.ftl">
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
	<div class="main-content">
	<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">
	
	<!-- ./ add by welson 0728 -->
	
<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">管理中心</a>
			</li>
			<li class="active">添加异常</li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="abnormal!save.action" method="post">
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">异常信息</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						
						<div class="col-xs-12">	
						<button type="button" class="btn btn-xs btn-info" id="userAddBtn" data-toggle="button">选择人员</button>		
						</div>
						
						<div class="col-xs-12" style="height:6px;">	
						</div>
						
						<div class="col-xs-12">					                                    						
						<table id="sample-table-1" class="table table-striped table-bordered ">
											<thead>
												<tr>											
													<th>姓名</th>
													<th>短信</th>
													<th>操作</th>
												</tr>
											</thead>

											<tbody>
											<!-- 
												<tr class="person">					
													<td>
													    <input type="text" name="name" id="name"　value="" class="{required: true}" readonly="readonly"/>
														<input type="hidden" name="nameId" id="nameId"　value=""/>
													</td>
													<td>
													    <select name="callReasonId" class="{required: true}"
														style="width:200px;">
														<option value="">请选择...</option> 
														<#list callReasonList as list>
														<option value="${list.id}">${list.callReason}</option>
														</#list>
													</select>
													</td>
													
												</tr>
											 -->	
											</tbody>
						</table>
						</div>
								
	       
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
			
			
		</form>
	
<!-- add by welson 0728 -->	
				</div><!-- /.col -->
				</div><!-- /.row -->

				<!-- PAGE CONTENT ENDS -->
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.page-content-area -->
	<#include "/WEB-INF/template/admin/admin_footer.ftl">
</div><!-- /.page-content -->
				</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->


</body>
<script type="text/javascript">
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		var title = "人员";
		var width="800px";
		var height="600px";
		var content="abnormal!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        var arrayObj = iframeWin.getGridId();
        
        var size = $("#sample-table-1 tbody tr").length;
        var ii=0;
        for(var i=0;i<arrayObj.length;i++){
        	
        	$("#sample-table-1 tbody tr td input").each(function(){
                
                if($(this).val()==arrayObj[i].id){
                	alert("存在相同的人员");
                	ii=1;
                	return false; 
                }
            });
        	
        	if(ii==1){
        		break;
        	}
        	var html ="<tr>";
	        	html+="<td>";
	        	html+="<input type='hidden' name='adminSet["+size+"].id' value='"+arrayObj[i].id+"'/>";
	        	html+="<input type='text' name='adminSet["+size+"].name' value='"+arrayObj[i].name+"'/>";
	        	html+="</td>";
	        	html+="<td>";
	        	html+="<select name='callReasonSet["+size+"].id' class='{required: true}' style='width:200px;'><option value=''>请选择...</option>";
	        	html+="<#list callReasonList as list><option value='${list.id}'>${list.callReason}</option></#list>";
				html+=+"</select>";
	        	html+="</td>";
	        	html+="<td>";
	        	html+="<span class='deleteImage' style='cursor:pointer'>删除</span>";
	        	html+="</td>";
        	html +="</tr>";
        	$("#sample-table-1 tbody").append(html);
        	size=size+1;
        }
        layer.close(index);       
	
		});
	});
	
	
	$(".deleteImage").livequery("click", function() {
		$(this).parent().parent().remove();
	})
	
})



</script>
</html>