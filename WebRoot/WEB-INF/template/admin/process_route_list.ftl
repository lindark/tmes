<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工序管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/BasicInfo/process_input.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/BasicInfo/matbrower.js"></script>
<style type="text/css">
.mymust {
	color: red;
	font-size: 10px;
}

.p_name {
	width: 420px;
	line-height: 30px;
	border: 1px solid;
	border-color: #d5d5d5;
}
#addImage {
	cursor: pointer;
}
</style>
<script type="text/javascript">
	$().ready(function() {
		// 地区选择菜单
		$(".areaSelect").lSelect({
			url : "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
		});
	});
</script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}
</style>
</head>
<body class="no-skin input">

	<!-- add by welson 0728 -->
	<#include "/WEB-INF/template/admin/admin_navbar.ftl">
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
		<div class="main-content">
			<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">

			<!-- ./ add by welson 0728 -->

			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a></li>
					<li class="active"><#if isAdd??>添加工序记录<#else>编辑工序记录</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate"
								action="process_route!save.action" method="post">
								<input type="hidden" name="id" value="${id}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">产品工序信息</a></li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<!--  <div class="profile-info-name">展开层</div>
												<div class="profile-info-value">
													<input type="text" name="material.spread"
														value="${(material.spread)!}"
														class=" input input-sm formText {required: true,minlength:0,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>-->

												<div class="profile-info-name">产品名称</div>
												<div class="profile-info-value">
													<input type="hidden" id="productId"
														name="productid"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}"
														readonly="readonly" />
													<button type="button" class="btn btn-xs btn-info"
														id="productSeach" data-toggle="button">选择</button>
													<span id="productCode"></span><span id="productName"></span> <label class="requireField">*</label>
												</div>
											</div>
										</div>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="row buttons col-md-8 col-sm-4">
													<a id="addImage"
														class="btn btn-white btn-default btn-sm btn-round"> <i
														class="ace-icon glyphicon glyphicon-plus"></i> 增加一行工序
													</a>
												</div>
											</div>
											<div class="profile-info-row">
												<table id="tb_material"
													class="table table-striped table-bordered table-hover">
													<tr>
														<th class="center">产品编号</th>
														<th class="center">产品名称</th>
														<th class="center">产品数量</th>
														<th class="center">工序</th>
														<th class="center">排序码</th>
														<th class="center">工作中心</th>
														<th class="center">计量单位</th>
														<th class="center">版本号</th>
														<th class="center">操作</th>
													</tr>
												</table>
											</div>
										</div>

										<div class="buttonArea">
											<input type="submit" class="formButton" value="确  定"
												hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; <input
												type="button" class="formButton"
												onclick="window.history.back(); return false;" value="返  回"
												hidefocus="true" />
										</div>
							</form>

							<!-- add by welson 0728 -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content-area -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->


</body>
</html>
<script type="text/javascript">
	$(function() {
		
		//产品点击
		$("#productSeach").click(function(){
			showMaterial();
			$("#tb_material").html("");
		});
		
		
		$("#addImage").click(function() {
			var productid = $("#productId").val();
			var productname = $("#productName").text();
			var productCode = $("#productCode").text();
			if(productid == ""){
				$.message("error","请先选择产品");
				return false;
			}
			addAttributeOptionTr(productid,productname,productCode,"","","","","","");
		});
	})
	var num = 0; 
	function addAttributeOptionTr(productid,productname,productCode,productsNum,version,sortcode,unit,workCenter,processId) {
		var attributeOptionTrHtml = "<tr class='zg'>" +
		"<td>"+productCode+"</td>" +
		"<td>"+productname+"</td>" +
		"<td><input type='hidden' name='processrouteList["+num+"].products.id' value='"+productid+"' class='form-control'/>" +
			"<input type='text' name='processrouteList["+num+"].productAmount' value='"+productsNum+"' class='form-control'/>" +
 		"</td>" +
		"<td>" +
			"<select name='processrouteList["+num+"].process.id' class='sele'>" +
			<#list processAll as list>
				"<option value='${list.id}'>${list.processCode} ${list.processName}</option>"+
			</#list>
			"</select>" +
		"</td>" +
		"<td><input type='text' name='processrouteList["+num+"].sortcode' value='"+sortcode+"' class='form-control'/></td>" +
		"<td><input type='text' name='processrouteList["+num+"].workCenter'  value='"+workCenter+"' class='form-control'/></td>" +
		"<td><input type='text' name='processrouteList["+num+"].unit' value='"+unit+"' class='form-control'/></td>" +
		"<td><input type='text' name='' value='"+version+"' class='form-control' readonly='readonly'/></td>" +
		"<td>" +
		//"	/" +
		"	<a class='delete'>删除</a>" +
		"</td>" +
		"</tr>";
		$("#tb_material").append(attributeOptionTrHtml);
		num = num+1;
		
		
}
	
	//读取产品信息
	function showMaterial(){
		var title = "选择产品";
		var width="800px";
		var height="600px";
		var content="products!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	//alert(iframeWin);
        	 var work = iframeWin.getGridId();
             var id=work.split(",");
             $("#productId").val(id[1]);
             $("#productName").text(id[0]);
             $("#productCode").text(id[2]);
             layer.close(index);
             
             loadData(id[1]);//加载表单数据
             
	 });
		
	}
	$(".delete").livequery("click", function() {
		if($(".zg").length > 1) {
			$(this).parent().parent().remove();
		} else {
			$.message("error","至少保留一项！");
		}
	})
	
	function loadData(productid){
		var url = "process_route!getRoute.action?productid="+productid;
		$.ajax({	
			url: url,
			//data: "{\"productid\":\""+productid+"\"}",
			dataType: "json",
			async: false,
			beforeSend: function(data) {
				
			},
			success: function(data) {
				var list = data.list;
				$.each(list,function(i,obj){
					addAttributeOptionTr(obj.productsid,obj.productsName,obj.productsCode,obj.productsNum,obj.version,obj.sortcode,obj.unit,obj.workCenter,obj.processId);
				})
				
			},error:function(data){
				$.message("error","系统出现问题，请联系系统管理员!");
			}
		});
		
		
	}
</script>