<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工模维修单 - Powered By ${systemConfig.systemName}</title>
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
	src="${base}/template/admin/js/unusual/js/model.js"></script>

<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;	
}
.profile-user-info-striped{border:0px;}
/*
.div_all_background{background-color:#000; opacity:0.05;z-index:10;width:1400px;height:1800px;}
#main-container{z-index:9;}
#dialog{z-index:10;background: #fff;}*/
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
					<li class="active"><#if isAdd??>添加工模维修单<#else>编辑工模维修单</#if></li>
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
								action="<#if isAdd??>model!creditsave.action<#else>model!creditupdate.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" /> <input
									type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
									
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
												<!-- <div class="profile-info-name">产品名称</div>
												<div class="profile-info-value">
													<#if isAdd??>
													 <img id="productId" class="img_addbug" title="添加产品信息" alt="添加产品信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />							
														<span id="productName1"></span> <input type="hidden"
														name="model.products.id" id="productNa" value="" class="formText {required: true}"/>
													<#else> ${(model.products.productsName)!} </#if>
												</div> -->
												
												 <div class="profile-info-name">设备名称</div>
												<div class="profile-info-value">
													<#if isAdd??>
													 <img id="deviceId" class="img_addbug" title="添加产品信息" alt="添加产品信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />							
														<span id="productName1"></span> <input type="hidden"
														name="model.equipments.id" id="productNa" value="" class="formText {required: true}"/>
														<input type="hidden"
														name="model.equipments.equipmentNo" id="equipNo" value=""
														class="formText {required: true}" />
													<#else> ${(model.equipments.equipmentName)!} </#if>
												</div>

												<div class="profile-info-name">班组</div>

												<div class="profile-info-value">
												   <#if isAdd??> 
														<span>${(admin.team.teamName)!}</span>
													   <input type="hidden" name="model.teamId.id" value="${(admin.team.id)!}"/>
												    <#else>
												       ${(model.teamId.teamName)!}												       
												    </#if>									
												</div>


											</div>

											<div class="profile-info-row">

												<div class="profile-info-name">种类</div>

												<div class="profile-info-value">
													<select name="model.type">
													     <#list allState as list>
								                             <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && model.type == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                             </#list> 
													</select>
																							
												</div>
                                                <!-- 
												<div class="profile-info-name">提报人</div>
												<div class="profile-info-value">
													<#if isAdd??> 	
														<span>${(abnormal.iniitiator.name)!}</span> <input
														type="hidden" name="model.initiator.id"
														value="${(abnormal.iniitiator.id)!}"
														class=" input input-sm  formText {required: true}" />
													<#else> ${(model.initiator.name)!} </#if>

												</div> -->
											</div>																													

											<div class="profile-info-row">
												<div class="profile-info-name">通知时间</div>
												<div class="profile-info-value">
													<input type="text" name="model.noticeTime"
														value="${(abnormal.callDate)!?string("yyyy-MM-dd HH:mm:ss")}"
														readonly="readonly"/>
												</div>
												<div class="profile-info-name">到场时间</div>
												<div class="profile-info-value">
													<input type="text" name="model.arriveTime"
														value="${(replydate)!}"
														readonly="readonly"/>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">维修人员</div>
												<div class="profile-info-value">
												    <#if isAdd??>
												    <img id="repairId" class="img_addbug" title="添加人员信息" alt="添加人员信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													<!-- <button type="button" class="btn btn-xs btn-info"
														id="repairId" data-toggle="button">选择</button> -->
														<span id="repairName1"></span> <input type="hidden"
														name="model.fixer.id" id="repairNa" value="" class="formText {required: true}"/>
													<#else> ${(model.fixer.name)!} </#if>

												</div>
											</div>
												 <#if isAdd??>												
												 <#else>
												<div class="profile-info-row">
												<div class="profile-info-name">维修时间</div>
												<div class="profile-info-value">
												   
												    <input type="text" name="model.fixTime"
														value="${(model.fixTime)!}"
														class=" input input-sm  formText {digits: true}" />
												    
												</div>
												</div>
												</#if>
											
											<div class="profile-info-row">
												<div class="profile-info-name">检验员</div>
												<div class="profile-info-value">
												    <#if isAdd??>
												    <img id="insepectorId" class="img_addbug" title="添加人员信息" alt="添加人员信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													<!--  <button type="button" class="btn btn-xs btn-info"
														id="insepectorId" data-toggle="button">选择</button>-->
														<span id="insepectorName1"></span> <input type="hidden"
														name="model.insepector.id" id="insepectorNa" value="" class="formText {required: true}"/>
													<#else> ${(model.insepector.name)!} </#if>

												</div>
											</div>
												<#if isAdd??>												
												<#else>
												<div class="profile-info-row access" data-access-list="confirmTime">
												<div class="profile-info-name">确认时间</div>
												<div class="profile-info-value">
												    
													<input type="text" name="model.confirmTime"
														value="${(model.confirmTime)!?string("yyyy-MM-dd HH:mm:ss")}"
														class="datePicker" />											
												</div>
												</div>
												</#if>
											
                                           
                                           <#if isAdd??>
											<#else>
											<div class="profile-info-row access" data-access-list="modelreason">
												<div class="profile-info-name">故障原因</div>
												<div class="profile-info-value" id="reason">
						                               <#if ((model.faultReasonSet)!?size>0)>
						                                    <#list model.faultReasonSet as list> 
												         <span> ${(list.reasonName)!}</span>&nbsp;&nbsp;&nbsp; 
												       </#list> 
												       
													   <#else>
													    <img id="faultReason" class="img_addbug"  title="添加故障信息" alt="添加故障信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													  <!--   <button type="button" class="btn btn-xs btn-info  access"
														id="faultReason" data-access-list="modelreason" data-toggle="button">选择</button>--> 
												       </#if>																			
												</div>
												
											</div>
											</#if>
											
											
											<#if isAdd??>
											<#else>
											<div class="profile-info-row access" data-access-list="modelhandle">
											    <div class="profile-info-name">处理方法与结果</div>
												<div class="profile-info-value" id="means">
												       <#if ((model.handleSet)!?size>0)>	
												          <#list model.handleSet as list> 
												         <span> ${(list.handleName)!}</span>&nbsp;&nbsp;&nbsp; 
												        </#list> 				                               
												       
													   <#else>
													   <img id="handleResult" class="img_addbug"  title="添加处理信息" alt="添加处理信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													 <!--   <button type="button" class="btn btn-xs btn-info  access"
														id="handleResult" data-access-list="modelhandle" data-toggle="button">选择</button> -->
												       </#if>													    
												</div>
												
											</div>
											</#if>
											
											<#if isAdd??>
											<#else>
											<div class="profile-info-row access" data-access-list="modelprevent">
											    <div class="profile-info-name">长期预防措施</div>
												<div class="profile-info-value" id="prevent">
												
												    <#if ((model.longSet)!?size>0)>					                               
												        <#list model.longSet as list> 
												         <span> ${(list.discribe)!}</span>&nbsp;&nbsp;&nbsp; 
												        </#list> 
													<#else>
													     <img id="longPrevent" class="img_addbug"   title="添加预防措施信息" alt="添加预防措施信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
													   <!--  <button type="button" class="btn btn-xs btn-info access"
														id="longPrevent" data-access-list="modelprevent" data-toggle="button">选择</button> --> 
													    
												    </#if>
												    
												<!-- 	<input type="text" name="model.measure"
														value="${(model.measure)!}"
														class=" input input-sm  formText {required: true}" /> -->
												</div>
											</div>
											</#if>

										</div>																	 

										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">不良现象描述</div>
												<div class="profile-info-value">
													<textarea name="model.failDescript" style="width:600px;" class="formText {required: true}">${(model.failDescript)!} </textarea>
												</div>
											</div>
										</div>
										</form>
										
                                    <div class="buttonArea">
                                    
                                    <#if isAdd??>
									<button class="btn btn-white btn-default btn-sm btn-round" id="completeModel" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>&nbsp;&nbsp;										
									<#else>		
									<button class="btn btn-white btn-default btn-sm btn-round" id="completeModel" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>&nbsp;&nbsp;						
									<button class="btn btn-white btn-default btn-sm btn-round " id="checkModel" type=button>
										<i class="ace-icon glyphicon glyphicon-ok"></i>
										刷卡回复
									</button>&nbsp;&nbsp;
									<button class="btn btn-white btn-default btn-sm btn-round " id="confirmModel" type=button>
										<i class="ace-icon glyphicon glyphicon-play-circle"></i>
										刷卡确认
									</button>&nbsp;&nbsp;
									<button class="btn btn-white btn-default btn-sm btn-round" id="closeModel" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡关闭
									</button>&nbsp;&nbsp;
									</#if>
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnModel" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
										
									</div>
							
										<!--weitao end modify-->


									</div>

									<table id="tabs-2" class="inputTable tabContent">
										<tbody>
											<tr class="title">
												<th>时间</th>
												<th>内容</th>
												<th>修改人</th>
											</tr>
											<#list (model.modelLogSet)! as list>
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