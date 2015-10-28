<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑设备维修单 - Powered By ${systemConfig.systemName}</title>
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
			<li class="active"><#if isAdd??>添加设备维修单<#else>编辑设备维修单</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	
								
		<div class="blank1"></div>
		<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
							<!--./add by welson 0910 -->
							
							
							<form id="inputForm" class="validate" action="<#if isAdd??>mass!save.action<#else>mass!update.action</#if>" method="post" enctype="multipart/form-data" >
								<input type="hidden" name="id" value="${id}" />
								
								
							<div id="inputtabs">
											<ul>
												<li>
													<a href="#tabs-1">单据信息</a>
												</li>

												<li>
													<a href="#tabs-2">单据日志</a>
												</li>												
												<li>
													<a href="#tabs-3">相关单据</a>
												</li>
											</ul>
		
			<table id="tabs-1" class="inputTable tabContent">
				<tr>
					<th>
						类型:
					</th>
					<td>
						<select name="device.maintenanceType" class="{required: true}">
							<option value="">请选择...</option>							
						</select>
					</td>
					<th>
						设备编号:
					</th>
					<td>
						<select name="device.maintenanceType" class="{required: true}">
							<option value="">请选择...</option>							
						</select>
					</td>
				</tr>

				<tr>
					<th>
						停用车间:
					</th>
					<td>
						<input type="text" class="formText" name="device.workShop" value="${(device.workShop)!}" />
					</td>
					<th>
						车间联系人:
					</th>
					<td>
						<input type="text" class="formText" name="device.workshopLinkman" value="${(device.workshopLinkman)!}" />
					</td>
				</tr>
				<tr>
					<th>
						是否停机:
					</th>
					<td>
						<input type="text" class="formText" name="device.isDown" value="${(device.isDown)!}" />
					</td>
					<th>
						停产维修:
					</th>
					<td>
						<input type="text" class="formText" name="device.isMaintenance" value="${(device.isMaintenance)!}" />
					</td>
				</tr>
				<tr>
					<th>
						故障描述:
					</th>
					<td>
					    <input type="text" class="formText" name="device.diagnosis" value="${(device.diagnosis)!}" />
					</td>
				</tr>
				<tr>
					<th>
						处理开始时间:
					</th>
					<td>
						<input type="text" name="device.beginTime" class="formText {required: true, min: 0}" value="${(device.beginTime)!}" />
					</td>
					<th>
						处理结束时间:
					</th>
					<td>
						<input type="text" name="device.dndTime" class="formText {required: true, min: 0}" value="${(device.dndTime)!}" />
					</td>
				</tr>				
				<tr>
					<th>
						处理人员:
					</th>
					<td>
						<input type="text" name="device.disposalWorkers" class="formText {required: true, min: 0}" value="${(device.disposalWorkers)!}" />
					</td>
				</tr>				
				<tr>
					<th>
						总维修时间:
					</th>
					<td>
						<input type="text" name="device.totalMaintenanceTime" class="formText {required: true, min: 0}" value="${(device.totalMaintenanceTime)!}" />						
					</td>
					<th>
						总停机时间:
					</th>
					<td>
						<input type="text" name="device.totalDownTime" class="formText {required: true}" value="${(device.totalDownTime)!}"/>						
					</td>
				</tr>
				<tr>
					<th>
						故障性质:
					</th>
					<td>
						<select name="device.faultCharacter" class="{required: true}">
							<option value="">请选择...</option>							
						</select>
					</td>
				</tr>															
				<tr>
					<th>
						处理过程:
					</th>
					<td>
						<input type="text" name="device.process" class="formText {required: true}" value="${(device.process)!}"/>
					</td>
				</tr>
				<tr>
					<th>
						原因分析:
					</th>
					<td>
						<input type="text" name="device.causeAnalysis" class="formText {required: true}" value="${(device.causeAnalysis)!}"/>
					</td>
				</tr>
				<tr>
					<th>
						预防对策:
					</th>
					<td>
						<input type="text" name="device.preventionCountermeasures" class="formText {required: true}" value="${(device.preventionCountermeasures)!}"/>
					</td>
				</tr>
				<tr>
					<th>
						接到电话号码:
					</th>
					<td>
						<input type="text" name="device.phone" class="formText {required: true}" value="${(device.phone)!}"/>
					</td>
					<th>
						接到电话时间:
					</th>
					<td>
						<input type="text" name="device.callTime" class="formText {required: true}" value="${(device.callTime)!}"/>
					</td>
				</tr>
				<tr>
					<th>
						到达现场时间:
					</th>
					<td>
						<input type="text" name="device.arrivedTime" class="formText {required: true}" value="${(device.arrivedTime)!}"/>
					</td>
					<th>
						服务态度:
					</th>
					<td>
						<select name="device.serviceAttitude" class="{required: true}">
							<option value="">请选择...</option>							
						</select>
					</td>
				</tr>
			</table>
			
			
			
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
			
			</div>
	
			
			<div class="buttonArea">
				<input type="submit" class="formButton" value="保存" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
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