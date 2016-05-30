<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>管理中心</title>
<meta name="description"
	content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browserValidate.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true /></#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
</head>
<body class="no-skin list">
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
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a></li>
					<li class="active">零头数交接</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal validate" id="inputForm" method="post" action="" role="form">
								<div class="widget-box transparent">
						<div class="div_top">
								<!-- <div>
								下班生产日期:
                                <input type="text" id="productDate" name="processHandoverTop.afterProductDate" value="${(admin.afterProductDate)!}" class="datePicker formText"/>
                                   &nbsp;&nbsp;
                                 	         班次:
                                     <select name="processHandoverTop.aftershift"id="sl_sh">
                                     	<option value="" <#if (admin.aftershift == "")!> selected</#if>></option>
                                     	<option value="1" <#if (admin.aftershift== 1)!> selected</#if>>早</option>
										<option value="2" <#if (admin.aftershift == 2)!> selected</#if>>白</option>
										<option value="3" <#if (admin.aftershift == 3)!> selected</#if>>晚</option>
                                   </select>
								</div> -->
						<div class="widget-header">
							<h4 class="widget-title lighter">班组信息</h4>
							<div class="widget-toolbar no-border">
								<a href="#" data-action="collapse"> <i
									class="ace-icon fa fa-chevron-up"></i> </a>

							</div>
							
							
						</div>
						<div class="widget-body">
											<div
												class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="profile-user-info profile-user-info-striped">
												   <div class="profile-info-row">
														<div class="profile-info-name">工厂/车间：</div>
														<div class="profile-info-value">
															${(admin.team.factoryUnit.workShop.factory.factoryName)!} &nbsp;&nbsp;&nbsp;    
															${(admin.team.factoryUnit.workShop.workShopName)!}</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">单元：</div>
														<div class="profile-info-value" >
														${(admin.team.factoryUnit.factoryUnitName)! }
														</div>										
													</div>
													
													<div class="profile-info-row">
													
													    <div class="profile-info-name">生产日期/班次:</div>
														<div class="profile-info-value">
								       								 ${admin.productDate} &nbsp;&nbsp;&nbsp; 
								       								 <#if (admin.shift == 1)!>
								       								 早 
								       								 <#elseif (admin.shift == 2)!> 
								       								 	白
								       								 <#elseif (admin.shift == 3)!>
								       								 	晚
								       								 <#else>
								       								 </#if>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
						</br>
									<div class="row buttons col-md-8 col-sm-4 sub-style" style="margin-top:5px;text-align:center">
                                     <button class="btn btn-white btn-default btn-sm btn-round " id="btn_save" type="button" >
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_confirm" type="button">
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type="button" >
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
									</div>
								</div>
							</form>
							
							<!-- add by weitao -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

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
<script type ="text/javascript">

   $(function(){
	   $("#btn_save").click(function(){
				//var loginid = $("#loginid").val();
				var $save = $(this);
				var url = "process_handover!allSubmit.action";
				credit.creditCard(url,function(data){
					if(data.status=="success"){
						//window.location.href="process_handover!list.action";
						 $save.attr("disabled",true);
					}
				});
	 });
	   $("#btn_confirm").click(function(){
			var loginid = $("#loginid").val();
			var url = "process_handover!allapproval.action?loginid="+loginid;
			credit.creditCard(url,function(data){
				if(data.status=="success"){
					window.location.href="process_handover!list.action";
				}
			});
});


		 /*返回*/
		$("#btn_back").click(function(){
			window.history.back();return false;
		});
   });
</script>