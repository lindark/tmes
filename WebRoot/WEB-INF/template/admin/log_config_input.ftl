<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑日志监控设置 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$(document).ready( function() {

	// 查询Action类对应的方法名称
	$("#actionClassName").change( function() {
		var actionClassName = $("#actionClassName");
		var actionMethodName = $("#actionMethodName");
		$.post("log_config!getAllActionMethod.action", {
			"logConfig.actionClassName" :actionClassName.val()
		}, function(data, textStatus) {
			if (data != "") {
				actionMethodName.html(data);
			} else {
				actionMethodName.html("");
			}
		});
	});

})
</script>
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
			<li class="active"><#if isAdd??>添加需进行日志监控的方法<#else>编辑需进行日志监控的方法</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>


	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
		<form id="inputForm" class="validate" action="<#if isAdd??>log_config!save.action<#else>log_config!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						操作名称:
					</th>
					<td>
						<input type="text" name="logConfig.operationName" class="formText {required: true, remote: 'log_config!checkOperationName.action?oldValue=${(logConfig.operationName)!}', messages: {remote: '此操作名称已存在!'}}" value="${(logConfig.operationName)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						Action类:
					</th>
					<td>
						<select id="actionClassName" name="logConfig.actionClassName" class="{required: true, messages: {required: '此内容为必选项,请选择!'}}">
							<option value="">请选择...</option>
							<#list allActionClassName as list>
								<option value="${list}" <#if (list == logConfig.actionClassName)!>selected="selected"</#if>>
									${list}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						Action方法:
					</th>
					<td>
						<select id="actionMethodName" name="logConfig.actionMethodName" class="{required: true}">
							<option value="">请选择...</option>
							<option value="${(logConfig.actionMethodName)!}" selected="selected">
								${(logConfig.actionMethodName)!}
							</option>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						描述:
					</th>
					<td>
						<input type="text" name="logConfig.description" class="formText" value="${(logConfig.description)!}" />
					</td>
				</tr>
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