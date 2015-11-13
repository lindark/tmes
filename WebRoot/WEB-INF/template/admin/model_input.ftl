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
								
		<form id="inputForm" class="validate" action="<#if isAdd??>model!save.action<#else>model!update.action</#if>" method="post">
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
										<div class="profile-info-name"> 产品编号 </div>
					
										<div class="profile-info-value">
										    <select name="model.productCode">							
								<option value="1">
									40010021
								</option>
								<option value="2">
									40010021
								</option>
						</select>
											<!-- <input type="text" name="quality.productName" value="${(quality.productName)!}" class=" input input-sm  formText {required: true}" /> -->
										</div>
										 <div class="profile-info-name"> 产品名称</div>
					
										<div class="profile-info-value">
											<input type="text" name="model.productName" value="${(model.productName)!}" class=" input input-sm  formText {required: true}" />
										</div> 
										
									</div>
									 
									<div class="profile-info-row">
										<div class="profile-info-name"> 班组</div>
					
										<div class="profile-info-value">
										<#if isAdd??>
												       <input type="text" name="model.teamId"
														value="${(abnormal.teamId)!}"
														class=" input input-sm  formText {required: true}" />
												    <#else>
												       <input type="text" name="model.teamId"
														value="${(model.teamId)!}"
														class=" input input-sm  formText {required: true}" />
										</#if>															
										</div>
										<div class="profile-info-name">种类 </div>
					
										<div class="profile-info-value">
											 <select name="model.type">							
								<option value="1">
									工装
								</option>
								<option value="2">
									接角
								</option>
						</select>
										</div>
									</div>									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 提报人 </div>
									    <div class="profile-info-value">
											<input type="text" name="model.initiator" value="${(model.initiator)!}" class=" input input-sm  formText {required: true}" />
										</div>
										 <div class="profile-info-name">时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="model.createDate" value="${(model.createDate)!}" class="formText {required: true} datePicker"/>
										</div>									
									</div>
							</div>
							
						<div class="profile-user-info profile-user-info-striped">
						  <div class="profile-info-row">
									    <div class="profile-info-name"> 不良现象描述 </div>
									    <div class="profile-info-value">
											<textarea name="model.failDescript" style="width:600px;">${(model.failDescript)!} </textarea>
										</div>
							</div>
						</div>
							<div class="profile-user-info profile-user-info-striped">																										
									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 通知时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="model.noticeTime" value="${(model.noticeTime)!}" class="formText {required: true} datePicker"/>
										</div>
										<div class="profile-info-name"> 到场时间 </div>
									    <div class="profile-info-value">
											<input type="text" name="model.arriveTime" value="${(model.arriveTime)!}" class="formText {required: true} datePicker" />
										</div>
									</div> 
									<div class="profile-info-row">
									    <div class="profile-info-name"> 维修人员</div>
									    <div class="profile-info-value">
											<input type="text" name="model.fixer" value="${(model.fixer)!}" class=" input input-sm  formText {required: true}" />
										</div>
										 <div class="profile-info-name"> 维修时间</div>
									    <div class="profile-info-value">
											<input type="text" name="model.fixTime" value="${(model.fixTime)!}" class=" input input-sm  formText {required: true}" />
										</div>
									</div>
									<div class="profile-info-row">
									    <div class="profile-info-name"> 检验员 </div>
									    <div class="profile-info-value">
											<input type="text" name="model.insepector" value="${(model.insepector)!}" class=" input input-sm  formText {required: true}" />
										</div>	
										<div class="profile-info-name"> 确认时间</div>
									    <div class="profile-info-value">
											<input type="text" name="model.confirmTime" value="${(model.confirmTime)!}" class="formText {required: true} datePicker" />
										</div>
									</div>	
									
									<div class="profile-info-row">
									    <div class="profile-info-name">故障原因</div>
									    <div class="profile-info-value">
											<input type="text" name="model.faultCause" value="${(model.faultCause)!}" class=" input input-sm  formText {required: true}" />
										</div>	
										<div class="profile-info-name">处理方法与结果</div>
									    <div class="profile-info-value">
											<input type="text" name="model.resolve" value="${(model.resolve)!}" class="input input-sm  formText {required: true}" />
										</div>
									</div>	
									<div class="profile-info-row">
									    <div class="profile-info-name">长期预防措施</div>
									    <div class="profile-info-value">
											<input type="text" name="model.measure" value="${(model.measure)!}" class=" input input-sm  formText {required: true}" />
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