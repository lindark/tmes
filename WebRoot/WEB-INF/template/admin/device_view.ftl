<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>查看设备维修单 - Powered By ${systemConfig.systemName}</title>
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
.profile-user-info-striped{border:0px;}
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
			<li class="active">查看设备维修单</li>
		</ul><!-- /.breadcrumb -->
	</div> 
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
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
													${(device.equipments.equipmentName)!}																
										</div> 
										
									</div>
											
									<div class="profile-info-row">
									    <div class="profile-info-name">使用车间</div>
									    <div class="profile-info-value">									    
													${(device.workShop.workShopName)!}									       											
										</div>
										 <div class="profile-info-name">车间联系人</div>
									    <div class="profile-info-value">									         
												${(device.workshopLinkman.name)!}												       											
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
						                      </select>									
										</div>
									</div>
									
																															
									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 处理开始时间 </div>
									    <div class="profile-info-value">
											${(device.beginTime)!}
										</div>
										<div class="profile-info-name"> 处理结束时间 </div>
									    <div class="profile-info-value">
											${(device.dndTime)!}
										</div>
									</div> 
									
									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 处理人员</div>
									    <div class="profile-info-value">								        
													${(device.disposalWorkers.name)!}												
										</div>	
										
										<div class="profile-info-name">故障性质</div>
									    <div class="profile-info-value">
									        <select name="device.faultCharacter">	
									                <#list allProperty as list>
								                     <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && device.faultCharacter == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                        </#list>  						
						                      </select>												
										</div>								
									</div>
									
									
									<div class="profile-info-row">
									    <div class="profile-info-name">总维修时间 </div>
									    <div class="profile-info-value">
											${(device.totalMaintenanceTime)!}
										</div>	
										<div class="profile-info-name"> 总停机时间</div>
									    <div class="profile-info-value">
											${(device.totalDownTime)!}
										</div>
									</div>	
									
									<div class="profile-info-row">
									    
										<div class="profile-info-name">故障原因</div>
									    <div class="profile-info-value">
											<#list device.receiptSet as list>
													${list.reasonName}, </#list>
										</div>												
									</div>																												
									
									<div class="profile-info-row">
									    <div class="profile-info-name">接到电话号码</div>
									    <div class="profile-info-value">
											${(device.phone)!}
										</div>	
										<div class="profile-info-name"> 接到电话时间</div>
									    <div class="profile-info-value">
											${(device.callTime)!}
										</div>
									</div>		
									
									<div class="profile-info-row">
									    <div class="profile-info-name">到达现场时间</div>
									    <div class="profile-info-value">
											${(device.arrivedTime)!}
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
									        ${(device.diagnosis)!}
									    </div>
									</div>	
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name">处理过程</div>
									    <div class="profile-info-value">
									        ${(device.process)!}
									    </div>
									</div>	
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name"> 原因分析 </div>
									    <div class="profile-info-value">
									        ${(device.causeAnalysis)!}
									    </div>
									</div>	
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name"> 预防对策 </div>
									    <div class="profile-info-value">
									        ${(device.preventionCountermeasures)!}
									    </div>
									</div>	
						</div>
						<div class="profile-user-info profile-user-info-striped">
						        <div class="profile-info-row">	
									    <div class="profile-info-name">更换零部件数量及型号</div>
									    <div class="profile-info-value">
									       ${(device.changeAccessoryAmountType)!}
									    </div>
									</div>	
						</div>
				<!--weitao end modify-->	
				<div class="buttonArea">                                                                          
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
				    <tbody>	
										        <#list (qualityList)! as list>
										        <tr>						
													<td>
														<a href="quality!view.action?id=${(list.id)}">质量问题单${(list_index+1)!}</a>										
													</td>
												</tr>
												</#list> 
												<#list (modelList)! as list>
										        <tr>						
													<td>
														<a href="model!view.action?id=${(list.id)}">工模维修单${(list_index+1)!}</a>										
													</td>
												</tr>
												</#list> 
												<#list (craftList)! as list>
										        <tr>						
													<td>
														<a href="craft!view.action?id=${(list.id)}">工艺维修单${(list_index+1)!}</a>										
													</td>
												</tr>
												</#list> 
												<#list (deviceList)! as list>
										        <tr>						
													<td>
														<a href="device!view.action?id=${(list.id)}">设备维修单${(list_index+1)!}</a>										
													</td>
												</tr>
												</#list> 
										</tbody>
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