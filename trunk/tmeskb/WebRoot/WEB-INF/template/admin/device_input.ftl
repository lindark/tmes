<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑设备维修单 - Powered By ${systemConfig.systemName}</title>
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
	src="${base}/template/admin/js/unusual/js/device.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}

.profile-user-info-striped {
	border: 0px;
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
					<li class="active"><#if isAdd??>添加设备维修单<#else>编辑设备维修单</#if></li>
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
								action="<#if isAdd??>device!creditsave.action<#else>device!creditupdate.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" /> 
								<input type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
								<input type="hidden" name="device.team.id" value="${(admin.team.id)!}"/>
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
											    <div class="profile-info-name">类型</div>
												<div class="profile-info-value">
													<select name="device.maintenanceType"
														class="formText {required: true}"> <#list allType
														as list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && device.maintenanceType ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>
													</select>
												</div>
												
												<div class="profile-info-name">故障原因</div>
												<div class="profile-info-value">
													
													<select name="device.fault"> <#list
														reasonList as list>
														<option value="${list.id}"<#if ((isAdd &&
															list.isDefault) || (isEdit && device.fault ==
															list.id))!> selected</#if>>${list.reasonName}</option>
														</#list>
													</select>
													
												</div>
											   
												
											</div></div>
											
											<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												
												<div class="profile-info-name">设备名称</div>
												<div class="profile-info-value">
													<#if isAdd??>
													<img id="deviceId" class="img_addbug" title="添加设备信息" alt="添加设备信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													<span id="deviceName1"></span> <input type="hidden"
														name="device.equipments.id" id="deviceNa" value=""
														class="formText {required: true}" />
														<input type="hidden"
														name="device.equipments.equipmentNo" id="equipNo" value=""
														class="formText {required: true}" /> <#else>
													${(device.equipments.equipmentName)!} </#if>

												</div>	
																					
											</div>
                                           </div>
                                           
                                           <div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
                                           <div class="profile-info-name">使用车间</div>
											<div class="profile-info-value">
											    <#if isAdd??>
											    <input type="hidden"
														name="device.workShop.id"  value="${(workshop.id)!}"
														class="formText {required: true}" />${(workshop.workShopName)!}
												<#else>
												    ${(device.workShop.workShopName)!}
                                                </#if>
												<!-- 	<#if isAdd??>
													<img id="workShopId" class="img_addbug" title="添加车间信息" alt="添加车间信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													<span id="workShopName1"></span> <input type="hidden"
														name="device.workShop.id" id="workShopNa" value=""
														class="formText {required: true}" /> <#else>
													${(device.workShop.workShopName)!} </#if> -->

												</div>
												</div>
												</div>	                                          											
											
											<div class="profile-user-info profile-user-info-striped">

											<div class="profile-info-row">
												<div class="profile-info-name">是否停机</div>
												<div class="profile-info-value">
													<select name="device.isDown"> <#list allDown as
														list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && device.isDown ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>

													</select>
												</div>
												<div class="profile-info-name">停产维修</div>
												<div class="profile-info-value">
													<select name="device.isMaintenance"> <#list
														allMaintenance as list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && device.isMaintenance ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>
						
													</select>
												</div>
											</div></div>
                                            

                                           <div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">处理人员</div>
												<div class="profile-info-value">
													<#if isAdd??>
													<img id="adminId" class="img_addbug" title="添加人员信息" alt="添加人员信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													<span id="adminName1"></span> <input type="hidden"
														name="device.disposalWorkers.id" id="adminNa" value=""
														class="formText {required: true}" /> <#else>
													${(device.disposalWorkers.name)!} </#if>

												</div>
												
												

											</div></div>
                                             
                                             <div class="profile-user-info profile-user-info-striped">
                                             <div class="profile-info-row">

												
												<div class="profile-info-name">故障性质</div>
												<div class="profile-info-value">
													<select name="device.faultCharacter"> <#list
														allProperty as list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && device.faultCharacter ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>
													</select>
												</div>												
											    
											</div>                                      
                                                                                       
										</div>
										
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
										 <div class="profile-info-name">报废数</div>
												<div class="profile-info-value">
												   <input type="text" name="device.scrapNo"	value="${(device.scrapNo)!}" class="formText {required: true}" />
												</div>
										 </div>
										 </div>
										
										 <div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">故障描述</div>
												<div class="profile-info-value">
												    <textarea name="device.diagnosis" style="width:600px;" class="formText {required: true}">${(device.diagnosis)!} </textarea>													
												</div>
											</div>
										</div> 
										
										
										
										    <#if isAdd??> 
                                            <#else>
                                            <div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row access" data-access-list="beginEndTime">
												<div class="profile-info-name">处理开始时间</div>
												<div class="profile-info-value">
													<input type="text" name="device.beginTime"
														value="${(device.beginTime)!}"
														class="datePicker"/>
												</div>
												<div class="profile-info-name">处理结束时间</div>
												<div class="profile-info-value">
													<input type="text" name="device.dndTime"
														value="${(device.dndTime)!}"
														class="datePicker"/>
												</div>
											</div>
											
											<div class="profile-info-row access" data-access-list="totalTime">
												<div class="profile-info-name">总维修时间</div>
												<div class="profile-info-value">
													<input type="text" name="device.totalMaintenanceTime"
														value="${(device.totalMaintenanceTime)!}"
														class="input input-sm  formText {digits: true}"/>
												</div>
												<div class="profile-info-name">总停机时间</div>
												<div class="profile-info-value">
													<input type="text" name="device.totalDownTime"
														value="${(device.totalDownTime)!}"
														class="input input-sm  formText {digits: true}"/>
												</div>
											</div>      
											</div>
                                            </#if> 
																																					
										
										<#if isAdd??>												
										<#else>
										<div class="profile-user-info profile-user-info-striped access" data-access-list="process">
											<div class="profile-info-row">
												<div class="profile-info-name">处理过程</div>
												<div class="profile-info-value" id="process">	
												     <#if ((device.deviceStepSet)!?size>0)>
						                                    <#list device.deviceStepSet as list> 
												            <span> ${(list.vornr)!}</span>&nbsp;&nbsp;&nbsp; 
												            </#list> 
												       
													   <#else>											    
												    <img id="faultProcess" class="img_addbug" title="添加信息" alt="添加信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />												  														
												 	   </#if>
												</div>
											</div>
										</div>
										<div class="profile-user-info profile-user-info-striped access" data-access-list="causeAnalysis">
											<div class="profile-info-row">
												<div class="profile-info-name">原因分析</div>
												<div class="profile-info-value">
													<textarea name="device.causeAnalysis" style="width:600px;" >${(device.causeAnalysis)!} </textarea>
												</div>
											</div>
										</div>
										<div class="profile-user-info profile-user-info-striped access" data-access-list="preventionCountermeasures">
											<div class="profile-info-row">
												<div class="profile-info-name">预防对策</div>
												<div class="profile-info-value">
													<textarea name="device.preventionCountermeasures"
														style="width:600px;" >${(device.preventionCountermeasures)!} </textarea>
												</div>
											</div>
										</div>
										<div class="profile-user-info profile-user-info-striped access" data-access-list="changeAccessoryAmountType">
											<div class="profile-info-row">
												<div class="profile-info-name">更换零部件数量及型号</div>
												<div class="profile-info-value">
													<textarea name="device.changeAccessoryAmountType"
														style="width:600px;" >${(device.changeAccessoryAmountType)!} </textarea>
												</div>
											</div>
										</div>
										</#if>
										
										
										
										 <#if isAdd??> 
                                            <#else>
											                                    											
                                            <div class="profile-user-info profile-user-info-striped access" data-access-list="phoneTimeAttitude">
											<div class="profile-info-row">
												<div class="profile-info-name">接到电话号码</div>
												<div class="profile-info-value">
													<input type="text" name="device.phone"
														value="${(device.phone)!}"
														class="input input-sm"/>
												</div>
												<div class="profile-info-name">接到电话时间</div>
												<div class="profile-info-value">
													<input type="text" name="device.callTime"
														value="${(device.callTime)!}"
														class="datePicker"/>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">到达现场时间</div>
												<div class="profile-info-value">
													<input type="text" name="device.arrivedTime"
														value="${(device.arrivedTime)!}"
														class="datePicker"/>
												</div>
												<div class="profile-info-name">服务态度</div>
												<div class="profile-info-value">
													<select name="device.serviceAttitude"> <#list
														allAttitude as list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && device.serviceAttitude ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>
													</select>
												</div>
											</div>
											</div>
                                            </#if> 
										
										
							</form>
							<!--weitao end modify-->
							<div class="buttonArea">

								<#if isAdd??>
								<button class="btn btn-white btn-default btn-sm btn-round" 
									id="completeDevice" type=button>
									<i class="ace-icon glyphicon glyphicon-check"></i> 刷卡提交
								</button>
								&nbsp;&nbsp; <#else>
								<button class="btn btn-white btn-default btn-sm btn-round" 
									id="completeDevice" type=button>
									<i class="ace-icon glyphicon glyphicon-check"></i> 刷卡提交
								</button>
								<button class="btn btn-white btn-default btn-sm btn-round"
									id="checkDevice" type=button>
									<i class="ace-icon glyphicon glyphicon-ok"></i> 刷卡回复
								</button>
								&nbsp;&nbsp;

								<button class="btn btn-white btn-default btn-sm btn-round"
									id="closeDevice" type=button>
									<i class="ace-icon fa fa-cloud-upload"></i> 刷卡关闭
								</button>
								&nbsp;&nbsp; </#if>
								<button class="btn btn-white btn-default btn-sm btn-round"
									id="returnDevice" type=button>
									<i class="ace-icon fa fa-home"></i> 返回
								</button>

							</div>
						</div>
						<table id="tabs-2" class="inputTable tabContent">
							<tbody>
								<tr class="title">
									<th>时间</th>
									<th>内容</th>
									<th>修改人</th>
								</tr>
								<#list (device.deviceLogSet)! as list>
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