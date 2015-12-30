<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工艺维修单 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/unusual/js/craft.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}
.profile-user-info-striped{border:0px;}
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
					<li class="active"><#if isAdd??>添加工艺维修单<#else>编辑工艺维修单</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validatecredit"
								action="<#if isAdd??>craft!creditsave.action<#else>craft!creditupdate.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" />
                                <input type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>
										<li><a href="#tabs-2">单据日志</a></li>
										<li><a href="#tabs-3">相关单据</a></li>
									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
											
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
												    <#if isAdd??>
												     <img id="productId" class="img_addbug" title="添加产品信息" alt="添加产品信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													<!-- <button type="button" class="btn btn-xs btn-info"
														id="productId" data-toggle="button">选择</button> -->
													<span id="productName1"></span>
												    <input type="hidden" name="craft.products.id" id="productNa" value="" class="formText {required: true}"/>
													<#else> ${(craft.products.productsName)!} </#if>
													
												</div>
											 	<div class="profile-info-name">产品编码</div>

												<div class="profile-info-value">
												      <#if isAdd??>
												      <span id="productNo"></span>						 
													  <#else> ${(craft.products.productsCode)!} </#if>
												</div> 
												

											</div>

                                        
											<div class="profile-info-row">
												 <div class="profile-info-name">班组</div>

												<div class="profile-info-value">
												   <#if isAdd??>
												      <span>${(admin.department.team.teamName)!}</span>
												     <!--  <input type="text" name="craft.team.teamName"
														value="${(admin.department.team.teamName)!}"
														class=" input input-sm  formText {required: true}" readonly="readonly"/> --> 
													   <input type="hidden" name="craft.team.id" value="${(admin.department.team.id)!}"/>
												    <#else>
												       ${(craft.team.teamName)!}												       
												    </#if>									
												</div>
												<div class="profile-info-name">机台号</div>

												<div class="profile-info-value">
													<select name="craft.cabinetCode">
                                                          <#list allCode as list>
								                             <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && craft.cabinetCode == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                             </#list> 
													</select>
												</div>
											</div>

                                          </div>
                                          
                                          <div class="profile-user-info profile-user-info-striped">
                                              <div class="profile-info-row">
													<div class="profile-info-name">维修员</div>
													<div class="profile-info-value">
													<#if isAdd??>
													 <img id="repair" class="img_addbug" title="添加人员信息" alt="添加人员信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													<!--  <button type="button" class="btn btn-xs btn-info"
														id="repair" data-toggle="button">选择</button>-->
													<span id="repairName1"></span>
												    <input type="hidden" name="craft.repairName.id" id="repairNa" value="" class="formText {required: true}"/>
													<#else> ${(craft.repairName.name)!} </#if>
													</div>
											  </div>
                                          </div>
                                          
											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<div class="profile-info-name">制造异常描述</div>
													<div class="profile-info-value">
													   <#if isAdd??>
													      <#list reasonList as list>
												<div style="width: 30%; float: left;">
													<label><input type="checkbox" name="reasonIds"
														value="${list.id}"<#if
														(craft.receiptReasonSet.contains(list) == true)!>
														checked="checked"</#if> />${(list.reasonName)!}</label>
												</div> </#list>
												        <#else>
												         	<#list craft.receiptReasonSet as list>
												         	    ${list.reasonName},
												         	</#list>
												        </#if>
													    <!--  <textarea name="craft.unusualDescription_make" style="width:600px;" class="formText {required: true}">${(craft.unusualDescription_make)!} </textarea>	--> 		
													</div>
												</div>
											


											
												<div class="profile-info-row">
													<div class="profile-info-name">制造处理措施</div>
													<div class="profile-info-value">	
													         <textarea name="craft.treatmentMeasure_make" style="width:600px;" class="formText {required: true}">${(craft.treatmentMeasure_make)!} </textarea>																															   
													</div>
												</div>
											


										
												<div class="profile-info-row">
													<div class="profile-info-name">制造处理结果</div>
													<div class="profile-info-value">
													    <textarea name="craft.resultCode_make" style="width:600px;" class="formText {required: true}">${(craft.resultCode_make)!} </textarea>				
													</div>
												</div>
											</div>

                                            <#if isAdd??>												
											<#else>
											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row access" data-access-list="unusualDescription_process">
													<div class="profile-info-name">工艺异常分析</div>
													<div class="profile-info-value">
													     <textarea name="craft.unusualDescription_process" style="width:600px;">${(craft.unusualDescription_process)!} </textarea>												
													</div>
												</div>

												<div class="profile-info-row access" data-access-list="treatmentMeasure_process">
													<div class="profile-info-name">工艺处理措施</div>
													<div class="profile-info-value">
													  <textarea name="craft.treatmentMeasure_process" style="width:600px;">${(craft.treatmentMeasure_process)!} </textarea>													
													</div>
												</div>
												<div class="profile-info-row access" data-access-list="resultCode_process">
													<div class="profile-info-name">工艺处理结果</div>
													<div class="profile-info-value">
													    <textarea name="craft.resultCode_process" style="width:600px;">${(craft.resultCode_process)!} </textarea>												
													</div>
												</div>
											</div>
										    </#if>
                                  </form>
										<!--weitao end modify-->
							   <div class="buttonArea">	
				  				                  
									<#if isAdd??>
									<button class="btn btn-white btn-default btn-sm btn-round" id="completeCraft" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>&nbsp;&nbsp;										
									<#else>
									<button class="btn btn-white btn-default btn-sm btn-round" id="completeCraft" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>&nbsp;&nbsp;								
									<button class="btn btn-white btn-default btn-sm btn-round" id="checkCraft" type=button>
										<i class="ace-icon glyphicon glyphicon-ok"></i>
										刷卡回复
									</button>&nbsp;&nbsp;
									<button class="btn btn-white btn-default btn-sm btn-round" id="closeCraft" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡关闭
									</button>&nbsp;&nbsp;
									</#if>
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnCraft" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
                 	            </div>
									<!-- 	<div class="buttonArea">
										<input type="submit" class="formButton" value="确  定"
											hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; <input
											type="button" class="formButton"
											onclick="window.history.back(); return false;" value="返  回"
											hidefocus="true" />
									    </div> -->
							
                                 
                                 
                                 
                                 

									</div>
									

									<table id="tabs-2" class="inputTable tabContent">
										<tbody>
											<tr class="title">
												<th>时间</th>
												<th>内容</th>
												<th>修改人</th>
											</tr>
											<#list (craft.craftLogSet)! as list>
											<tr>
												<td>${(list.createDate)!}</td>
												<td>${(list.info)!}</td>
												<td>${(list.operator.name)!}</td>
											</tr>
											</#list>
										</tbody>
									</table>

									<table id="tabs-3" class="inputTable tabContent">
                                            <tbody>	
										         <#if (qualityList?size>1) >
										            <tr>						
													<td>
														<a href="quality!sealist.action?abnorId=${(abnormal.id)}">质量问题单</a>										
													</td>
												    </tr>
										        <#else>
										            <#list (qualityList)! as list>
										            <tr>						
													<td>
														<a href="quality!view.action?id=${(list.id)}">质量问题单</a>										
													</td>
												    </tr>
												     </#list>
										        </#if>
										        <#if (modelList?size>1) >
										            <tr>						
													<td>
														<a href="model!sealist.action?abnorId=${(abnormal.id)}">工模维修单</a>										
													</td>
												    </tr>
										        <#else>
										            <#list (modelList)! as list>
										            <tr>						
													<td>
														<a href="model!view.action?id=${(list.id)}">工模维修单</a>										
													</td>
												    </tr>
												     </#list>
										        </#if>
										         <#if (craftList?size>1) >
										            <tr>						
													<td>
														<a href="craft!sealist.action?abnorId=${(abnormal.id)}">工艺维修单</a>										
													</td>
												    </tr>
										        <#else>
										            <#list (craftList)! as list>
										            <tr>						
													<td>
														<a href="craft!view.action?id=${(list.id)}">工艺维修单</a>										
													</td>
												    </tr>
												     </#list>
										        </#if>
										         <#if (deviceList?size>1) >
										            <tr>						
													<td>
														<a href="device!sealist.action?abnorId=${(abnormal.id)}">设备维修单</a>										
													</td>
												    </tr>
										        <#else>
										            <#list (deviceList)! as list>
										            <tr>						
													<td>
														<a href="device!view.action?id=${(list.id)}">设备维修单</a>										
													</td>
												    </tr>
												    </#list>
										        </#if>
										</tbody>
									</table>

								

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
$(function() {	

$("form.validatecredit").validate({
		
		errorClass: "validateError",
		ignore: ".ignoreValidate",
		onkeyup:false,
		errorPlacement: function(error, element) {
			var messagePosition = element.metadata().messagePosition;
			if("undefined" != typeof messagePosition && messagePosition != "") {
				var $messagePosition = $(messagePosition);
				if ($messagePosition.size() > 0) {
					error.insertAfter($messagePosition).fadeOut(300).fadeIn(300);
				} else {
					error.insertAfter(element).fadeOut(300).fadeIn(300);
				}
			} else {
				error.insertAfter(element).fadeOut(300).fadeIn(300);
			}
		},
		submitHandler: function(form) {
			var url = $(form).attr("action");
			var dt = $(form).serialize();
			credit.creditCard(url,function(data){
					if(data.status=="success"){
						layer.alert(data.message, {icon: 6},function(){
							window.location.href="abnormal!list.action";
						}); 
					}else if(data.status=="error"){
						layer.alert(data.message, {
					        closeBtn: 0,
					        icon:5,
					        skin:'error'
					   });
					}		
				},dt)
			
		}
	});
})
</script>
</html>