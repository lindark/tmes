<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑产品 - Powered By ${systemConfig.systemName}</title>
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
			<li class="active"><#if isAdd??>添加质量问题单<#else>编辑质量问题单</#if></li>
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
													<a href="#tabs-3">整改情况跟踪</a>
												</li>
												<li>
													<a href="#tabs-4">相关单据</a>
												</li>
											</ul>
		
			<table id="tabs-1" class="inputTable tabContent">
				<tr>
					<th>
						产品名称:
					</th>
					<td>
						<input type="text" name="quality.productName" class="formText {required: true}" value="${(quality.productName)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						产品编号:
					</th>
					<td>
						<input type="text" class="formText" name="quality.productNo" value="${(quality.productNo)!}" />
					</td>
				</tr>
				<tr>
					<th>
						生产工序:
					</th>
					<td>
						<select name="quality.process">
							<option value="">请选择...</option>							
						</select>
					</td>
				</tr>
				<tr>
					<th>
						班组:
					</th>
					<td>
						<input type="text" class="formText" name="quality.group" value="${(quality.group)!}" />
					</td>
				</tr>
				<tr>
					<th>
						质量问题描述:
					</th>
					<td>
					    <input type="text" class="formText" name="quality.problemDescription" value="${(quality.problemDescription)!}" />
					</td>
				</tr>
				<tr>
					<th>
						抽检数量:
					</th>
					<td>
						<input type="text" name="quality.samplingAmont" class="formText {required: true, min: 0}" value="${(quality.samplingAmont)!}" />
					</td>
				</tr>
				<tr>
					<th>
						缺陷数量:
					</th>
					<td>
						<input type="text" name="quality.failAmont" class="formText {required: true, min: 0}" value="${(quality.failAmont)!}" />
					</td>
				</tr>
				<tr>
					<th>
						挤出批次:
					</th>
					<td>
						<input type="text" name="quality.extrusionBatches" class="formText {required: true, min: 0}" value="${(quality.extrusionBatches)!}" />
					</td>
				</tr>				
				<tr>
					<th>
						报告时间:
					</th>
					<td>
						<input type="text" name="quality.createDate" class="formText {required: true, min: 0}" value="${(quality.createDate)!}" />						
					</td>
				</tr>
				<tr>
					<th>
						计划完成时间:
					</th>
					<td>
						<input type="text" name="quality.overTime" class="formText {required: true}" value="${(quality.overTime)!}"/>						
					</td>
				</tr>
				<tr>
					<th>
						车间整改方案:
					</th>
					<td>
						<input type="text" name="quality.rectificationScheme" class="formText {required: true}" value="${(quality.rectificationScheme)!}"/>
					</td>
				</tr>															
				<tr>
					<th>
						工程师意见:
					</th>
					<td>
						<textarea name="quality.engineerOpinion" class="formTextarea" cols="100">${(quality.engineerOpinion)!}</textarea>
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
				<tbody><tr class="title">
				<th>时间</th>
				<th>内容</th>
				<th>操作</th>
			</tr>
				<tr>
					<td>
						2015-09-16 09:20
					</td>
					<td>						
						工序XX问题已解决			
					</td>
					<td>
						编辑
					</td>					
				</tr>
		</tbody>
			</table>
			
			<table id="tabs-4" class="inputTable tabContent">
				
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