<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>返修管理 - Powered By ${systemConfig.systemName}</title>
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
					<li class="active">返修单</li>
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
								action="<#if isAdd??>repair!save.action<#else>repair!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${(id)!}" />
								<input type="hidden" class="input input-sm" name="repair.workingbill.id" value="${workingbill.id} " id="wkid">
								<#if isEdit>
								<input type="hidden" class="input input-sm" name="repair.createUser.id" value="${repair.createUser.id} ">
								</#if>
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">返修单</a></li>

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
												<div class="profile-info-name">累计返修数量</div>

												<div class="profile-info-value">
													<span class="editable editable-click" id="age">${workingbill.totalRepairAmount}</span>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">本次返修数量</div>

												<div class="profile-info-value">
													<input type="text" name="repair.repairAmount"
														value="${(repair.repairAmount)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													<label class="requireField">*</label>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">返修部位</div>

												<div class="profile-info-value">
													<input type="text" name="repair.repairPart"
														value="${(repair.repairPart)!}"
														class=" input input-sm formText {required: true}" />
													<label class="requireField">*</label>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">责任人/批次</div>

												<div class="profile-info-value">
													<input type="text" name="repair.duty"
														value="${(repair.duty)!}"
														class=" input input-sm formText {required: true}" />
													<label class="requireField">*</label>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">责任工序</div>

												<div class="profile-info-value">
													<select name="repair.processCode" id="form-field-icon-1" class="chosen-select"> 
												        <#list allProcess as list>
											            <option value="${list.processCode}"<#if (isEdit&&repair.processResponse.id==list.id)!> selected</#if>>${list.processName}</option>
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
			var url = "repair!creditsave.action";		
		<#else>
			var url = "repair!creditupdate.action";
		</#if>
		credit.creditCard(url,function(data){
			var workingbillid = $("#wkid").val();
			/* $.message(data.status,data.message);
			window.location.href = "repair!list.action?workingBillId="+ workingbillid; */
			if(data.status=="success"){
	    		layer.alert(data.message, {icon: 6},function(){
	    			window.location.href = "repair!list.action?workingBillId="+ workingbillid;
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