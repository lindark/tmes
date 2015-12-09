<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>查看工模维修单 - Powered By ${systemConfig.systemName}</title>
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
<script type="text/javascript"
	src="${base }/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
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
					<li class="active">查看工模维修单</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							
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
													${(model.products.productsName)!}
												</div>

												<div class="profile-info-name">班组</div>

												<div class="profile-info-value">											   
												       ${(model.teamId.teamName)!}												       								
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

												<div class="profile-info-name">提报人</div>
												<div class="profile-info-value">
													${(model.initiator.name)!}
												</div>
											</div>

							
										
										
							

											<div class="profile-info-row">
												<div class="profile-info-name">通知时间</div>
												<div class="profile-info-value">
													${(model.noticeTime)!}														
												</div>
												<div class="profile-info-name">到场时间</div>
												<div class="profile-info-value">
													${(model.arriveTime)!}
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">维修人员</div>
												<div class="profile-info-value">
												   ${(model.fixer.name)!}

												</div>
												<div class="profile-info-name">维修时间</div>
												<div class="profile-info-value">
													${(model.fixTime)!}
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">检验员</div>
												<div class="profile-info-value">
												    ${(model.insepector.name)!}
												</div>
												<div class="profile-info-name">确认时间</div>
												<div class="profile-info-value">
													${(model.confirmTime)!}
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">故障原因</div>
												<div class="profile-info-value" id="reason">						                             
													    <#list model.faultReasonSet as list> 
												         <span> ${(list.reasonName)!}</span>&nbsp;&nbsp;&nbsp; 
												        </#list> 																		
												</div>
												
											</div>
											<div class="profile-info-row">
											    <div class="profile-info-name">处理方法与结果</div>
												<div class="profile-info-value" id="means">
													    <#list model.handleSet as list> 
												         <span> ${(list.handleName)!}</span>&nbsp;&nbsp;&nbsp; 
												        </#list> 													    
												</div>
												
											</div>
											
											<div class="profile-info-row">
											    <div class="profile-info-name">长期预防措施</div>
												<div class="profile-info-value" id="prevent">
													    <#list model.longSet as list> 
												         <span> ${(list.discribe)!}</span>&nbsp;&nbsp;&nbsp; 
												        </#list> 
										
												</div>
											</div>
											

										</div>																	 

										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">不良现象描述</div>
												<div class="profile-info-value">
													${(model.failDescript)!}
												</div>
											</div>
										</div>
										</form>
										
                                    <div class="buttonArea">                                                                       
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
/*
	$(function() {
		
		$("#self-data").cxSelect({
			selects: ['type', 'faultReason', 'child'],
			jsonName: 'name',
			jsonValue: 'value'
		},function(select){//回调
			$(select).trigger("chosen:updated"); 
			$(select).chosen({allow_single_deselect:true,no_results_text:"没有找到",search_contains: true});
		});	
		
		
		 /*	$("#faultReason").click(function(){
			$("#all_background")
			.css("display","block");
			$("#dialog").css("display","block");
	       $("#main-container").css(
			"display","none");
		})
		
		
	})*/

</script>

</html>