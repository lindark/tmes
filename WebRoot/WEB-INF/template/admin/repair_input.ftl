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
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
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
								action="<#if isAdd??>repair!save.action<#else>reppair!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${(id)!}" />
								<input type="hidden" class="input input-sm" name="repair.workingbill.id" value="${workingbill.id} ">
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
												<div class="profile-info-name">责任人</div>

												<div class="profile-info-value">
													<input type="hidden" id="adminId" name="repair.duty.id" value="${(repair.duty.id)!}"  class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" readonly="readonly"/>					
												    
												    <#if isAdd??><button type="button" class="btn btn-xs btn-info" id="userAddBtn" data-toggle="button">选择</button>				                                    
				                                     <span id ="adminName"></span>
										         	 <label class="requireField">*</label>	
										         	 <#else>
										         	 ${(repair.duty.name)!}    
										         	 </#if>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">责任工序</div>

												<div class="profile-info-value">
													<select name="repair.processResponse" id="form-field-icon-1" class="chosen-select"> 
												        <#list allResponse as list>
											            <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && "repair.processResponse" == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
										                </#list>   
												    </select> 
												</div>
											</div>
										</div>
										<!--weitao end modify-->


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
<script type="text/javascript">
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	 var $adminId=$("#adminId");
	 var $adminName=$("#adminName");
	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		var title = "人员";
		var width="800px";
		var height="600px";
		var content="repair!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        var work = iframeWin.getGridId();
        //alert(work);
        var id=work.split(",");
        $adminId.val(id[1]);
        $adminName.text(id[0]);
        layer.close(index);            	          	     	
        
       
	  });
	 	
	});
	
})
</script>
</html>