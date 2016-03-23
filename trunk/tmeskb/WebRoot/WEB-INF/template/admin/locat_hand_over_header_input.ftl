<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>线边仓交接 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
<style>
body {
	background: #fff;
}
.table>tbody>tr>td{
	 text-align: center;
	 vertical-align: initial;
}
inupt.stockMout{
	padding:2px;
}
.site{
	margin-left:10px;
	margin-bottom:10px;
}

.tabth{text-align:center;}
.sub_style{text-align: center;width:100%;}
</style>
</head>
<body class="no-skin input">
<input type="hidden" id="loginid" value="<@sec.authentication property='principal.id' />" />
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
				<script type="text/javascript">try {ace.settings.check('breadcrumbs', 'fixed')} catch (e) {}</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> 
					<a href="admin!index.action">管理中心</a></li>
					<li class="active"><#if isAdd??>添加线边仓交接<#else>添加线边仓交接</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<!-- add by welson 0728 -->
			
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<form class="form-horizontal validate" id="searchform1" method="post"
								action="locat_hand_over_header!add.action" role="form">
								<input type="hidden" name="loginid" value="<@sec.authentication property='principal.id' />" />
								<div class="operateBar">
									<div class="form-group">
								<!-- 	<label class="col-sm-1 col-md-offset-1"style="text-align:right"><label class="requireField" style="color:red;">*</label>单元:</label>
										<div class="col-sm-3">
										<img id="img_faunit" title="单元" alt="单元" style="cursor:pointer" src="/template/shop/images/add_bug.gif" />
										<span id="infoNames" >${(infoName)! }</span>
										<input type="hidden" name="infoName" id="infoName" value="${(infoName)! }">
										<input type="hidden" id="infoId" name="infoId" value="${(infoId)! }" class="col-xs-10 col-sm-5 formText {required: true}" />
										 -->
										</div>
										<label class="col-sm-1 col-md-offset-1"style="text-align:right"><label class="requireField" style="color:red;">*</label>库位:</label>
										<div class="col-sm-4">
											<input type="text" name="lgpla"
												class="input input-sm form-control formText {required: true}" value="${(lgpla)! }"
												id="position"/>
										</div>
										<div class="col-sm-2">
										<a id="searchButton1"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-filter blue"></i> 搜索
										</a>
										</div>
									</div>
								<br/><br/><br/>
									<div class="profile-user-info profile-user-info-striped">
								  			<div class="profile-info-row">
									<table id="tb_lho" class="table table-striped table-bordered table-hover">
								    			
													<tr>
														<th class="tabth">库存地点</th>
														<th class="tabth">物料编码</th>
														<th class="tabth">物料描述</th>
														<th class="tabth">批次</th>
														<th class="tabth">库存数量</th>
													</tr>
													<#list (locasideListMap)! as lns>
																<tr>
																	<td>${(lns.lgort)! }<input type="hidden" name="locatHandOverList[${lns_index}].locationCode" value="${(lns.lgort)! }"></td>
																	<td>${(lns.matnr)! }<input type="hidden" name="locatHandOverList[${lns_index}].materialCode" value="${(lns.matnr)! }"></td>
																	<td>${(lns.maktx)! }<input type="hidden" name="locatHandOverList[${lns_index}].materialName" value="${(lns.maktx)! }"></td>
																	<td>${(lns.charg)! }<input type="hidden" name="locatHandOverList[${lns_index}].charg" value="${(lns.charg)! }"></td>
																	<td>${(lns.verme)! }
																	<input type="hidden" name="locatHandOverList[${lns_index}].amount" value="${(lns.verme)! }">
																	<input type="hidden" name="locatHandOverList[${lns_index}].lgpla" value="${(lns.lgpla)! }">
																	</td>
																	
																</tr>
															</#list>
										</table>
										</div>
										</div>
								</div>
							</form>
							<br/>
							<div class="row buttons  col-sm-4 sub_style">
							<button class="btn btn-white btn-default btn-sm btn-round" id="btn_subm" type="button" />
									<i class="ace-icon fa fa-filter blue"></i>
									 刷卡提交
								</button>&nbsp;&nbsp;&nbsp;&nbsp;
								<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type="button" />
									<i class="ace-icon fa fa-home"></i>
									返回
								</button>
							</div>
							<!-- add by welson 0728 -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.main-container -->
<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
<!-- ./ add by welson 0728 -->
</body>
</html>
<script type="text/javascript">
$(function(){
	var $subm = $("#btn_subm");
	//刷卡提交
	$subm.click(function(){
		var tr_lho = $("#tb_lho").find("tr").length;
		//alert($("#searchform1").serialize());
		if(tr_lho>1){
			var dt = $("#searchform1").serialize();
			var loginId = $("#loginid").val();//当前登录人的id
			var url = "locat_hand_over_header!creditsubmit.action?loginId="+loginId;
			credit.creditCard(url,function(data){
				if(data.status=="success"){
					window.location.href="locat_hand_over_header!list.action";
				}
			},dt);
		}else{
			alert("不存在数据，无需提交");
		}
	});

	$("#btn_back").click(function(){
		window.location.href="locat_hand_over_header!list.action";
	});
	$("#searchButton1").click(function(){
		$("#searchform1").submit();
	});
});
</script>