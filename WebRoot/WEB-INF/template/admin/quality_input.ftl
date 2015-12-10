<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑质量问题单 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/unusual/js/quality.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}

.deleteImage,#addImage,#removeImage {
	cursor: pointer;
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
					<li class="active"><#if isAdd??>添加质量问题单<#else>编辑质量问题单</#if></li>
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
								action="<#if isAdd??>quality!save.action<#else>quality!update.action</#if>"
								method="post">
								<input type="hidden" name="id" id="qualityId" value="${id}" /> <input
									type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>
										<li><a href="#tabs-2">单据日志</a></li>
									    <#if isAdd??> <#else><li><a href="#tabs-3">整改情况跟踪</a></li> </#if>									 
										<li><a href="#tabs-4">相关单据</a></li>
									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
												    <#if isAdd??>
												    <button type="button" class="btn btn-xs btn-info" id="productId" data-toggle="button">选择</button>											 
												    <span id="productName1"></span>
													<input type="hidden" name="quality.products.id" id="productNa" value="" class="formText {required: true}"/>
													<#else>
													${(quality.products.productsName)!}
													</#if>	
													
												</div>
												
												<div class="profile-info-name">生产工序</div>

												<div class="profile-info-value" >
												     <#if isAdd??>												    
													<select name="quality.process.id" id="processName" class="formText {required: true}">
														
													</select>
													<#else>
													${(quality.process.processName)!}
													</#if>
												</div>

											</div>

											<div class="profile-info-row">
												
												<div class="profile-info-name">班组</div>

												<div class="profile-info-value">
												    <#if isAdd??>
												      <!--  <input type="text" name="quality.team.teamName"
														value="${(admin.department.team.teamName)!}"
														class=" input input-sm  formText {required: true}" readonly="readonly"/> -->
														<span>${(admin.department.team.teamName)!}</span>
													   <input type="hidden" name="quality.team.id" value="${(admin.department.team.id)!}" class="formText {required: true}"/>
												    <#else>
												       ${(quality.team.teamName)!}												       
												    </#if>													
												</div>
												<div class="profile-info-name">挤出批次</div>
												<div class="profile-info-value">
												    <#if isAdd??>	
													<input type="text" name="quality.extrusionBatches"
														value="${(quality.extrusionBatches)!}"
														class=" input input-sm  formText {required: true, digits: true}" />
													<#else>
													   ${(quality.extrusionBatches)!}
													</#if>	
												</div>
											</div>
											
											
											<div class="profile-info-row">
												<div class="profile-info-name">接收人</div>
												<div class="profile-info-value">
												     <#if isAdd??>
													<button type="button" class="btn btn-xs btn-info"
														id="receive" data-toggle="button">选择</button>
													<span id="receiveName1"></span>
												    <input type="hidden" name="quality.receiver.id" id="receiveNa" value="" class="formText {required: true}"/>
													<#else> ${(quality.receiver.name)!} </#if>
												</div>
											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">抽检数量</div>
												<div class="profile-info-value">
												    <#if isAdd??>
													<input type="text" name="quality.samplingAmont"
														value="${(quality.samplingAmont)!}"
														class=" input input-sm  formText {required: true, digits: true}" />
													<#else> ${(quality.samplingAmont)!} </#if>
												</div>
												<div class="profile-info-name">缺陷数量</div>
												<div class="profile-info-value">
												   <#if isAdd??>
													<input type="text" name="quality.failAmont"
														value="${(quality.failAmont)!}"
														class=" input input-sm  formText {required: true, digits: true}" />
													<#else> ${(quality.failAmont)!} </#if>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">报告时间</div>
												<div class="profile-info-value">
												   <#if isAdd??>
													<input type="text" name="quality.createDate"
														value="${(quality.createDate)!}"
														class="formText {required: true,date:'date',dateFormat: 'yy-mm-dd'} datePicker" />
													<#else> ${(quality.createDate)!} </#if>
												</div>
												<div class="profile-info-name">计划完成时间</div>
												<div class="profile-info-value">
												     <#if isAdd??>
													<input type="text" name="quality.overTime"
														value="${(quality.overTime)!}"
														class="formText {required: true,date:'date',dateFormat: 'yy-mm-dd'} datePicker" />
													<#else> ${(quality.overTime)!} </#if>
												</div>
											</div>

										</div>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">质量问题描述</div>
												<div class="profile-info-value">
												    <#if isAdd??>
													<textarea name="quality.problemDescription"
														style="width:600px;" class="formText {required: true}">${(quality.problemDescription)!}</textarea>
												    <#else> ${(quality.problemDescription)!} </#if>
												</div>
											</div>
										</div>
                                       
                                       <#if isAdd??>
                                       <#else>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">车间整改方案</div>
												<div class="profile-info-value">
													<textarea name="quality.rectificationScheme"
														style="width:600px;">${(quality.rectificationScheme)!}</textarea>
												</div>
											</div>
										</div>

										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">工程师意见</div>
												<div class="profile-info-value">
													<textarea name="quality.engineerOpinion"
														style="width:600px;">${(quality.engineerOpinion)!} </textarea>
												</div>
											</div>
										</div>
										</#if>
										</form>
										<!--weitao end modify-->
                                        <div class="buttonArea">
                                        
                                            <#if isAdd??>
									<button class="btn btn-white btn-default btn-sm btn-round" id="completeQuality" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>&nbsp;&nbsp;	
									<#else>
									</#if>	
									<#if isAdd??><#else>								
									<button class="btn btn-white btn-default btn-sm btn-round" id="checkQuality" type=button>
										<i class="ace-icon glyphicon glyphicon-ok"></i>
										刷卡回复
									</button>&nbsp;&nbsp;
									
									<button class="btn btn-white btn-default btn-sm btn-round" id="closeQuality" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡关闭
									</button>&nbsp;&nbsp;
									</#if>
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnQuality" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
                                        
									<!--  	<input type="submit" class="formButton" value="确  定"
											hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; <input
											type="button" class="formButton"
											onclick="window.history.back(); return false;" value="返  回"
											hidefocus="true" />-->
									     </div>
   
									</div>
                                    
                                    <form id="inputForm1" action="flowing_rectify!save.action" method="post"></form>
                                    
									<table id="tabs-2" class="inputTable tabContent">
										<tbody>
											<tr class="title">
												<th>时间</th>
												<th>内容</th>
												<th>修改人</th>
											</tr>
											<#list (quality.unusualLogSet)! as list>
											<tr>
												<td>${(list.createDate)!}</td>
												<td>${(list.info)!}</td>
												<td>${(list.operator.name)!}</td>
											</tr>
											</#list>
										</tbody>
									</table>
									<#if isAdd??>
									<#else>
									
									<div id="tabs-3">
										<table id="sample-table-1"
											class="table table-striped table-bordered table-hover">
											<thead>
												<tr>									
													<th>内容</th>
												</tr>
											</thead>

											<tbody>											  											   											  			
												<#if (quality==null||quality.flowingRectify==null||(quality.flowingRectify)!?size==0)>
												<tr class="zg">						
													<td><div><input type="hidden" name="flowingId" value=""/><textarea name="flowingRectify.content"
														style="width:600px;" class="text">${(list.content)!}</textarea>
														&nbsp;&nbsp;&nbsp;<a class="save" style="cursor:pointer">保存</a>&nbsp;&nbsp;<a  style="cursor:pointer" class="edit">编辑</a>&nbsp;&nbsp;<a class="deleteButton" style="cursor:pointer">删除</a>			
														</div>													
													</td>
												</tr>
												<#else> 
												<#list (quality.flowingRectify)! as list>
												<tr class="zg">
													<td>
													<div>
													<input type="hidden" name="flowingId" value="${(list.id)!}"/>
													<textarea name="flowingRectify.content"
														style="width:600px;" class="text">${(list.content)!}</textarea>&nbsp;&nbsp;&nbsp;<a class="save" style="cursor:pointer" >保存</a>&nbsp;&nbsp;<a style="cursor:pointer" class="edit">编辑</a>&nbsp;&nbsp;<a class="deleteButton" style="cursor:pointer">删除</a></div>		
													</div>
													</td>
												</tr>
												</#list> 
												</#if>
												<tr>
					                                 <td colspan="3">
					                                 <button type="button" class="btn btn-xs btn-info" id="addImage">新增</button>	
					                                 <button type="button" class="btn btn-xs btn-info" id="removeImage">删除</button>		
					                                 </td>
			                                     </tr>
			                             
											</tbody>
										</table>
									</div>
							
									 </#if>
									<table id="tabs-4" class="inputTable tabContent">                                      
										<tbody>	
										        <#if (qualityList?size>1) >
										            <tr>						
													<td>
														<a href="quality!list.action?abnorId=${(abnormal.id)}">质量问题单</a>										
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
														<a href="model!list.action?abnorId=${(abnormal.id)}">工模维修单</a>										
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
														<a href="craft!list.action?abnorId=${(abnormal.id)}">工艺维修单</a>										
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
														<a href="device!list.action?abnorId=${(abnormal.id)}">设备维修单</a>										
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
										        <!--  
										        <#list (qualityList)! as list>
										        <tr>						
													<td>
														<a href="quality!view.action?id=${(list.id)}">质量问题单${(list_index+1)!}</a>										
													</td>
												</tr>
												</#list> -->
																																
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
<script>
$(function() {
	$("#sample-table-1 tbody .zg .text").attr("disabled",true);
	//$("#sample-table-1 tbody .zg .text").hide();
	
	$(".save").live("click", function() {
		$text =	$(this).prev(); 
		$input = $(this).prev().prev();
		var i = $text.serialize();
		var ii = $("#qualityId").val();
		var iii = $(this).prev().prev().val();
		if(iii==null || iii==undefined || iii==""){
			iii="";
		}
		var ids = "";
		ids += ii+","+i+","+iii;
	    url="flowing_rectify!save.action?ids="+ids;
		$.ajax({
			url: url,
			//data: ids,
			dataType: "json",		
			success: function(data) {
				
				$.tip(data.status, data.message);
				$input.val(data.id);
				$text.attr("disabled",true);
			}
		});	
		  		  		  
	});
	
	$(".edit").livequery("click", function(){
		$(this).prev().prev().attr("disabled",false);

	})
	
	
	$(".deleteButton").livequery("click", function() {
		if($(".zg").length > 1) {
			$(this).parent().parent().remove();
		} else {
			alert("请至少保留一个选项!");
		}
	})		
	
})
</script>
</body>
</html>