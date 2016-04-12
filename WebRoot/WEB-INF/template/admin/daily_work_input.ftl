<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>报工管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
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
					<li class="active">报工</li>
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
								action="<#if isAdd??>daily_work!save.action<#else>daily_work!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${(id)!}" />
								<input type="hidden" class="input input-sm" name="dailyWork.workingbill.id" value="${workingbill.id} " id="wkid">
								<#if isEdit>
								<input type="hidden" class="input input-sm" name="dailyWork.createUser.id" value="${dailyWork.createUser.id} ">
								</#if>
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">报工管理</a></li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
													<div class="profile-info-name">随工单</div>
	
													<div class="profile-info-value">
														<span>${workingbill.workingBillCode}</span>
													</div>
	
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">产品编号</div>

												<div class="profile-info-value">
													<span>${workingbill.matnr}</span>
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
													<span>${workingbill.maktx}</span>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">累计报工数量</div>

												<div class="profile-info-value">
													<span class="editable editable-click" id="age">${workingbill.dailyWorkTotalAmount}</span>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">报工数</div>

												<div class="profile-info-value">
													<input type="text" name="dailyWork.enterAmount"
														value="${(dailyWork.enterAmount)!}"
														class=" input input-sm formText {required: true,min: 1}" />
													<label class="requireField">*</label>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">模具</div>

												<div class="profile-info-value">
												    <select name="dailyWork.moudle">
												    <!--  <#list allMoudle as list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && dailyWork.moudle ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>-->
														<#list pager.list as list>
															<option value="${list.station}"<#if (list.station==dailyWork.moudle)!>selected</#if>>${list.station}</option>
														</#list>
													</select> 
													
												</div>
											</div>
										</div>
										<!--weitao end modify-->


									</div>
									<div class="buttonArea">
										<a id="btn_save" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-cloud-upload"></i>
											刷卡保存
										</a>
										<a id="btn_back" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-home"></i>
											返回
										</a>
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
$(function(){
	//刷卡保存
	$("#btn_save").click(function(){
		//提交
		//$("#inputForm").submit();
		var dt = $("#inputForm").serialize();
		<#if isAdd??>
			var url = "daily_work!creditsave.action";		
		<#else>
			var url = "daily_work!creditupdate.action";
		</#if>
		credit.creditCard(url,function(data){
			var workingbillid = $("#wkid").val();
			//$.message(data.status,data.message);
			if(data.status=="success"){
	    		layer.alert(data.message, {icon: 6},function(){
				window.location.href = "daily_work!list.action?workingBillId="+ workingbillid;
	    	});
	    	}else if(data.status=="error"){
	    		layer.alert(data.message,{
	    			closeBtn: 0,
	    			icon: 5,
	    			skin:'error'
	    		});
	    	}
		},dt)
	});
	
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
});
</script>