<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>中转仓 - Powered By ${systemConfig.systemName}</title>
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
<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/dump_entry.js"></script>
<style>
body {
	background: #fff;
	font-family: 微软雅黑;
}
.layui-layer-btn1{
	display:none !important;
}
.sub-style{float: left;margin-left:8px;}
.ckbox{margin-left:1px;}
.div_size{font-size: 14px;font-family: 微软雅黑;}
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
					<li class="active"><#if isAdd??>添加配送单<#else>编辑配送单</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<!-- add by welson 0728 -->
			
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<form id="inputForm" name="inputForm" class="validate" action="" method="post">
								<input type="hidden" id="dumpid" value="${dumpid!}" />
								<div id="inputtabs">
								 	<ul>
								    	<li><a href="#tabs-1"><#if isAdd??>添加配送单<#else>编辑配送单</#if></a></li>
									</ul>
									<div id="tabs-1" class="tab1">
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">发出库存地点</div>
												<div class="profile-info-value">
													<span id="pddname">${(factoryunit.psaddress)!}</span>
													<input id="input_psaddress" type="hidden" value="${(factoryunit.psaddress)!}">
												</div>
												<div class="profile-info-name">配送仓位</div>
												<div class="profile-info-value">
													<span id="ppaname">${(factoryunit.psPositionAddress)!}</span>
													<input id="input_psPositionAddress" type="hidden" value="${(factoryunit.psPositionAddress)!}">
												</div>
												<div class="profile-info-name">接收库存地点</div>
												<div class="profile-info-value">
													<!-- <span>${(factoryunit.warehouse)!}</span>
													<input id="input_jsaddress" type="hidden" value="${(factoryunit.warehouse)!}"> -->
													<select id="input_jsaddress">
														<option value=""></option>
														<#list factoryunitList as list>
															<option value="${(list.warehouse)!}">${(list.warehouse)!}</option>
														</#list>
													<select>
												</div>
											</div>
										</div>
										<div class="profile-user-info profile-user-info-striped">
								  			<!-- 
								  			<div class="profile-info-row">
								    			<table id="tb_material" class="table table-striped table-bordered table-hover">
													<tr>
														<th class="tabth">物料编码</th>
														<th class="tabth">物料描述</th>
													</tr>
													<#if list_material??>
													<#list list_material as list>
														<tr>
															<td>
																<input type="checkbox" class="ckbox" value="${(list.factoryunit.id)!}" />&nbsp;
																<span>${(list.materialCode)! }</span>
															</td>
															<td>${(list.materialName)! }</td>
														</tr>
													</#list>
													</#if>
												</table>
							  				</div>
							  				-->
							  				<br/>
							  				<div class="step-content pos-rel div_size" id="div_mydata">
												<#list list_material as list>
													<div class="col-md-4 div_click">
														<!-- <input type="checkbox" class="ckbox" value="${(list.factoryunit.id)!}" /> -->
														<a href="javascript:void(0)" class="a_click">
															<input type="hidden" value="${(list.factoryunit.id)!}" />
															<span>${(list.materialCode)! } </span>
															<span>${(list.materialName)! } </span>
														</a>
													</div>
												</#list>
											</div>
						   				</div>
									</div>
									<br/>
									<div class="row buttons col-md-8 col-sm-4 sub-style">
                         					<!-- 
                         					<button class="btn btn-white btn-default btn-sm btn-round" id="btn_add" type="button">
											<i class="ace-icon fa fa-folder-open-o"></i>
											添加物料
										</button>
										-->
										<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type="button">
											<i class="ace-icon fa fa-home"></i>
											返回
										</button>
									</div>
                     			</div>
							</form>
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
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */
	$(function(){
		var ishead=0;
		$("#ace-settings-btn").click(function(){
			if(ishead==0){
				ishead=1;
				$("#ace-settings-box").addClass("open");
			}else{
				ishead=0;
				$("#ace-settings-box").removeClass("open");
			}
		});
		$(".btn-colorpicker").click(function(){
				$(".dropdown-colorpicker").addClass("open");
		});
		
		var ishead2=0;
		$(".light-blue").click(function(){
			if(ishead2==0){
				ishead2=1;
				$(this).addClass("open");
			}else{
				ishead2=0;
				$(this).removeClass("open");
			}
			
		});
		$("#input_jsaddress").change(function(){
			var wearhouse = $(this).val();
			<#list factoryunitList as list>
				if(list.warehouse==wearhouse){
					$("#input_psPositionAddress").val(${(list.psPositionAddress)!});
					$("#ppaname").text(${(list.psPositionAddress)!});
					$("#input_psaddress").val(${(list.psaddress)!});
					$("#pddname").text(${(list.psaddress)!});
					<#break>
				}
			</#list>
		});
	});
</script>