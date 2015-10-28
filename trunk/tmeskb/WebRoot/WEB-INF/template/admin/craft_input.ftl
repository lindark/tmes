<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工艺维修单- Powered By ${systemConfig.systemName}</title>
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
			<li class="active"><#if isAdd??>添加工艺维修单<#else>编辑工艺维修单</#if></li>
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
						产品名称:
					</th>
					<td>
						<input type="text" name="craft.productName" class="formText {required: true}" value="${(craft.productName)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						机台号:
					</th>
					<td>
						<input type="text" class="formText" name="craft.cabinetCode" value="${(craft.cabinetCode)!}" />
					</td>
				</tr>
				<tr>
					<th>
						时间:
					</th>
					<td>
						<input type="text" class="formText" name="craft.createDate" value="${(craft.createDate)!}" />
					</td>
				</tr>
				<tr>
					<th>
						班组:
					</th>
					<td>
						<input type="text" class="formText" name="craft.classes" value="${(craft.classes)!}" />
					</td>
				</tr>
				<tr>
					<th>
						制造异常描述:
					</th>
					<td>
					    <input type="text" class="formText" name="craft.unusualDescription_make" value="${(craft.unusualDescription_make)!}" />
					</td>
				</tr>
				<tr>
					<th>
						制造处理措施:
					</th>
					<td>
						<input type="text" name="craft.treatmentMeasure_make" class="formText {required: true, min: 0}" value="${(craft.treatmentMeasure_make)!}" />
					</td>
				</tr>
				<tr>
					<th>
						制造处理结果:
					</th>
					<td>
						<input type="text" name="craft.resultCode_make" class="formText {required: true, min: 0}" value="${(craft.resultCode_make)!}" />
					</td>
				</tr>
				<tr>
					<th>
						工艺异常分析:
					</th>
					<td>
						<input type="text" name="craft.unusualDescription_process" class="formText {required: true, min: 0}" value="${(craft.unusualDescription_process)!}" />
					</td>
				</tr>				
				<tr>
					<th>
						工艺处理措施:
					</th>
					<td>
						<input type="text" name="craft.treatmentMeasure_process" class="formText {required: true, min: 0}" value="${(craft.treatmentMeasure_process)!}" />						
					</td>
				</tr>
				<tr>
					<th>
						工艺处理结果:
					</th>
					<td>
						<input type="text" name="craft.resultCode_process" class="formText {required: true}" value="${(craft.resultCode_process)!}"/>						
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