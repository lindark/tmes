<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工模维修单 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/unusual/js/device.js"></script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
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
			<li class="active"><#if isAdd??>添加设备维修单<#else>编辑设备维修单</#if></li>
		</ul><!-- /.breadcrumb -->
	</div> 
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>device!save.action<#else>device!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">基本信息</a>
				</li>
				<li>
					<a href="#tabs-2">单据日志</a>
				</li>
				<li>
					<a href="#tabs-3">相关单据</a>
				</li>
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name"> 类型 </div>
					
										<div class="profile-info-value">
										    <select name="device.maintenanceType" class="formText {required: true}">
							                <#list allType as list>
								            <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && device.maintenanceType == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                </#list>              
						                    </select>										
											
										</div>
										 <div class="profile-info-name"> 设备名称</div>
					
										<div class="profile-info-value">
										     <#if isAdd??>
												    <button type="button" class="btn btn-xs btn-info" id="deviceId" data-toggle="button">选择</button>											 
												    <span id="deviceName1"></span>
													<input type="hidden" name="device.equipments.id" id="deviceNa" value="" class="formText {required: true}"/>
													<#else>
													${(device.equipments.equipmentName)!}
											</#if>	
										
										</div> 
										
									</div>
									 
								<!--  	<div class="profile-info-row">
										<div class="profile-info-name"> 设备名称</div>
					
										<div class="profile-info-value">							
								<input type="text" name="device.deviceName" value="${(device.deviceName)!}" class=" input input-sm  formText {required: true}" />									
										</div>
										<div class="profile-info-name">设备型号</div>
					
										<div class="profile-info-value">
											 <select name="device.deviceModel">							
								<option value="1">
									z-00A
								</option>
								<option value="2">
									z-00B
								</option>
						</select>
										</div>
									</div>	-->								
									<div class="profile-info-row">
									    <div class="profile-info-name">使用车间</div>
									    <div class="profile-info-value">
									    
									          <#if isAdd??>
												    <button type="button" class="btn btn-xs btn-info" id="workShopId" data-toggle="button">选择</button>											 
												    <span id="workShopName1"></span>
													<input type="hidden" name="device.workShop.id" id="workShopNa" value="" class="formText {required: true}"/>
													<#else>
													${(device.workShop.workShopName)!}
											</#if>	
									       											
										</div>
										 <div class="profile-info-name">车间联系人</div>
									    <div class="profile-info-value">
									         <#if isAdd??>
									            <span>${(admin.name)!}</span>
												<input type="hidden" name="device.workshopLinkman.id" value="${(admin.id)!}"/>
									         <#else>
												${(device.workshopLinkman.name)!}												       
											 </#if>											
										</div>									
									</div>		
									
									<div class="profile-info-row">
									    <div class="profile-info-name">是否停机</div>
									    <div class="profile-info-value">
									          <select name="device.isDown">										          
							                <#list allDown as list>
								            <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && device.isDown == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                </#list>              						
								               
						                      </select>									
										</div>
										<div class="profile-info-name">停产维修</div>
									    <div class="profile-info-value">
									          <select name="device.isMaintenance">	
									         <#list allMaintenance as list>
								            <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && device.isMaintenance == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                </#list>						
								                <!--   <option value="1">是</option>
								                  <option value="2">否</option> -->
						                      </select>									
										</div>
									</div>
									
																															
									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 处理开始时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="device.beginTime" value="${(device.beginTime)!}" class="formText {required: true,date:'date',dateFormat: 'yy-mm-dd'} datePicker"/>
										</div>
										<div class="profile-info-name"> 处理结束时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="device.dndTime" value="${(device.dndTime)!}" class="formText {required: true,date:'date',dateFormat: 'yy-mm-dd'} datePicker" />
										</div>
									</div> 
									
									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 处理人员</div>
									    <div class="profile-info-value">
									        <#if isAdd??>
												    <button type="button" class="btn btn-xs btn-info" id="adminId" data-toggle="button">选择</button>											 
												    <span id="adminName1"></span>
													<input type="hidden" name="device.disposalWorkers.id" id="adminNa" value="" class="formText {required: true}"/>
													<#else>
													${(device.disposalWorkers.name)!}
											</#if>	
											
										</div>									
									</div>
									
									
									<div class="profile-info-row">
									    <div class="profile-info-name">总维修时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="device.totalMaintenanceTime" value="${(device.totalMaintenanceTime)!}" class=" input input-sm  formText {required: true, digits: true}" />
										</div>	
										<div class="profile-info-name"> 总停机时间</div>
									    <div class="profile-info-value">
											<input type="text" name="device.totalDownTime" value="${(device.totalDownTime)!}" class="input input-sm  formText {required: true, digits: true}" />
										</div>
									</div>	
									
									<div class="profile-info-row">
									    <div class="profile-info-name">故障性质</div>
									    <div class="profile-info-value">
									        <select name="device.faultCharacter">	
									                <#list allProperty as list>
								                     <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && device.faultCharacter == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                        </#list>  						
						                      </select>												
										</div>
										<div class="profile-info-name">故障原因</div>
									    <div class="profile-info-value">
											<input type="text" name="device.faultReason" value="${(device.faultReason)!}" class=" input input-sm  formText {required: true}" />
										</div>												
									</div>																												
									
									<div class="profile-info-row">
									    <div class="profile-info-name">接到电话号码</div>
									    <div class="profile-info-value">
											<input type="text" name="device.phone" value="${(device.phone)!}" class=" input input-sm  formText {required: true}" />
										</div>	
										<div class="profile-info-name"> 接到电话时间</div>
									    <div class="profile-info-value">
											<input type="text" name="device.callTime" value="${(device.callTime)!}" class="formText {required: true,date:'date',dateFormat: 'yy-mm-dd'} datePicker" />
										</div>
									</div>		
									
									<div class="profile-info-row">
									    <div class="profile-info-name">到达现场时间</div>
									    <div class="profile-info-value">
											<input type="text" name="device.arrivedTime" value="${(device.arrivedTime)!}" class="formText {required: true,date:'date',dateFormat: 'yy-mm-dd'} datePicker" />
										</div>	
										<div class="profile-info-name">服务态度</div>
									    <div class="profile-info-value">
									        <select name="device.serviceAttitude">							
								                   <#list allAttitude as list>
								                     <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && device.serviceAttitude == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                       </#list>
						                     </select>													
										</div>
									</div>	
																			
							
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name"> 故障描述 </div>
									    <div class="profile-info-value">
									        <textarea name="device.diagnosis" style="width:600px;">${(device.diagnosis)!} </textarea>
									    </div>
									</div>	
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name">处理过程</div>
									    <div class="profile-info-value">
									        <textarea name="device.process" style="width:600px;">${(device.process)!} </textarea>
									    </div>
									</div>	
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name"> 原因分析 </div>
									    <div class="profile-info-value">
									        <textarea name="device.causeAnalysis" style="width:600px;">${(device.causeAnalysis)!} </textarea>
									    </div>
									</div>	
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name"> 预防对策 </div>
									    <div class="profile-info-value">
									        <textarea name="device.preventionCountermeasures" style="width:600px;">${(device.preventionCountermeasures)!} </textarea>
									    </div>
									</div>	
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name">更换零部件数量及型号</div>
									    <div class="profile-info-value">
									        <textarea name="device.changeAccessoryAmountType" style="width:600px;">${(device.changeAccessoryAmountType)!} </textarea>
									    </div>
									</div>	
						</div>
				</form>	
				<!--weitao end modify-->	
				<div class="buttonArea">
                                        
                                    <#if isAdd??>
									<button class="btn btn-white btn-default btn-sm btn-round" id="completeDevice" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>&nbsp;&nbsp;	
									<#else>
									</#if>	
									<#if isAdd??><#else>								
									<button class="btn btn-white btn-default btn-sm btn-round" id="checkDevice" type=button>
										<i class="ace-icon glyphicon glyphicon-ok"></i>
										刷卡回复
									</button>&nbsp;&nbsp;
									
									<button class="btn btn-white btn-default btn-sm btn-round" id="closeDevice" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡关闭
									</button>&nbsp;&nbsp;
									</#if>
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnDevice" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
			
			</div>
			</div>
			<table id="tabs-2" class="inputTable tabContent">
				<tbody><tr class="title">
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
				
			</table>
					
	
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
</html>