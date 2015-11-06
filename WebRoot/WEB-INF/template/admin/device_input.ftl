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
			<li class="active"><#if isAdd??>添加工模维修单<#else>编辑工模维修单</#if></li>
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
										    <select name="device.maintenanceType">							
								<option value="1">
									停线维修
								</option>
								<option value="2">
									插针保养
								</option>
						</select>
											<!-- <input type="text" name="quality.productName" value="${(quality.productName)!}" class=" input input-sm  formText {required: true}" /> -->
										</div>
										 <div class="profile-info-name"> 设备编号</div>
					
										<div class="profile-info-value">
											 <select name="device.deviceNo">							
								<option value="1">
									130032
								</option>
								<option value="2">
									130031
								</option>
						</select>
										</div> 
										
									</div>
									 
									<div class="profile-info-row">
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
									</div>									
									<div class="profile-info-row">
									    <div class="profile-info-name">使用车间</div>
									    <div class="profile-info-value">
									          <select name="device.workShop">							
								<option value="1">
									A
								</option>
								<option value="2">
									B
								</option>
						</select>
											<!-- <input type="text" name="device.workShop" value="${(device.workShop)!}" class=" input input-sm  formText {required: true}" /> -->
										</div>
										 <div class="profile-info-name">车间联系人</div>
									    <div class="profile-info-value">
											<input type="text" name="device.workshopLinkman" value="${(device.workshopLinkman)!}" class="input input-sm formText {required: true}"/>
										</div>									
									</div>		
									
									<div class="profile-info-row">
									    <div class="profile-info-name">是否停机</div>
									    <div class="profile-info-value">
									          <select name="device.isDown">							
								                  <option value="1">是</option>
								                  <option value="2">否</option>
						                      </select>									
										</div>
										<div class="profile-info-name">停产维修</div>
									    <div class="profile-info-value">
									          <select name="device.isMaintenance">							
								                  <option value="1">是</option>
								                  <option value="2">否</option>
						                      </select>									
										</div>
									</div>
																																	
									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 处理开始时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="device.beginTime" value="${(device.beginTime)!}" class="formText {required: true} datePicker"/>
										</div>
										<div class="profile-info-name"> 处理结束时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="device.dndTime" value="${(device.dndTime)!}" class="formText {required: true} datePicker" />
										</div>
									</div> 
									
									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 处理人员</div>
									    <div class="profile-info-value">
											<input type="text" name="device.disposalWorkers" value="${(device.disposalWorkers)!}" class=" input input-sm  formText {required: true}" />
										</div>									
									</div>
									
									
									<div class="profile-info-row">
									    <div class="profile-info-name">总维修时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="device.totalMaintenanceTime" value="${(device.totalMaintenanceTime)!}" class=" input input-sm  formText {required: true}" />
										</div>	
										<div class="profile-info-name"> 总停机时间</div>
									    <div class="profile-info-value">
											<input type="text" name="device.totalDownTime" value="${(device.totalDownTime)!}" class="input input-sm  formText {required: true}" />
										</div>
									</div>	
									
									<div class="profile-info-row">
									    <div class="profile-info-name">故障性质</div>
									    <div class="profile-info-value">
									        <select name="device.faultCharacter">							
								                  <option value="1">操作不当</option>
								                  <option value="2">点检维护不当</option>
						                      </select>												
										</div>											
									</div>	
									
									<div class="profile-info-row">
									    <div class="profile-info-name">故障原因</div>
									    <div class="profile-info-value">
											<input type="text" name="device.faultReason" value="${(device.faultReason)!}" class=" input input-sm  formText {required: true}" />
										</div>											
									</div>	
									<div class="profile-info-row">
									    <div class="profile-info-name">处理过程</div>
									    <div class="profile-info-value">
											<input type="text" name="device.process" value="${(device.process)!}" class=" input input-sm  formText {required: true}" />
										</div>											
									</div>	
									<div class="profile-info-row">
									    <div class="profile-info-name">原因分析</div>
									    <div class="profile-info-value">
											<input type="text" name="device.faultReason" value="${(device.faultReason)!}" class=" input input-sm  formText {required: true}" />
										</div>											
									</div>	
									<div class="profile-info-row">
									    <div class="profile-info-name">预防对策</div>
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
											<input type="text" name="device.callTime" value="${(device.callTime)!}" class="formText {required: true} datePicker" />
										</div>
									</div>		
									
									<div class="profile-info-row">
									    <div class="profile-info-name">到达现场时间</div>
									    <div class="profile-info-value">
											<input type="text" name="device.arrivedTime" value="${(device.arrivedTime)!}" class="formText {required: true} datePicker" />
										</div>	
										<div class="profile-info-name">服务态度</div>
									    <div class="profile-info-value">
									        <select name="device.serviceAttitude">							
								                  <option value="1">非常满意</option>
								                  <option value="2">满意</option>
						                     </select>													
										</div>
									</div>												
							
						</div>
						
				<!--weitao end modify-->	
				
			
			</div>
			
			<table id="tabs-2" class="inputTable tabContent">
				<tbody><tr class="title">
				<th>时间</th>
				<th>内容</th>
				<th>修改人</th>
			</tr>
				<tr>
					<td>
						2015-09-16 09:20
					</td>
					<td>						
						张三已刷卡				
					</td>
					<td>
						张三
					</td>					
				</tr>
		</tbody>
			</table>
														
			<table id="tabs-3" class="inputTable tabContent">
				
			</table>
			
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	
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