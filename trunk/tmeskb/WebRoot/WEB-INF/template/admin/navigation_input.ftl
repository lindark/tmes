<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑导航 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

$(document).ready( function() {

	// 将选择的系统内容地址填充至链接地址中,若名称为空,则自动将名称填充至名称输入框.
	$name = $("#name");
	$systemUrl = $("#systemUrl");
	$url = $("#url");
	$systemUrl.change( function() {
		var url = $systemUrl.val();
		$url.val(url);
		if($name.val() == "") {
			var systemUrlSelectedText = $("$systemUrl:selected").get(0).text;
			systemUrlSelectedText = systemUrlSelectedText.replace(/^-*\s*/g,"");
			$name.val(systemUrlSelectedText);
		}
	});
	
	// 链接地址内容修改时，系统内容选择框修改为不选择任何项目
	$url.keypress( function() {
		$systemUrl.val("");
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
			<li class="active"><#if isAdd??>添加导航<#else>编辑导航</#if></li>
		</ul><!-- /.breadcrumb -->
	</div> 
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
	
		<form id="inputForm" class="validate" action="<#if isAdd??>navigation!save.action<#else>navigation!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						名称:
					</th>
					<td>
						<input type="text" id="name" name="navigation.name" class="formText {required: true}" value="${(navigation.name)!}" />	 
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						系统内容:
					</th>
					<td>
						<select id="systemUrl">
							<option value="">-----</option>
							<#list articleCategoryTreeList as list>
								<option value="${"$"}{base}/shop/article!list.action?id=${list.id}"<#if (("${'$'}{base}/shop/article!list.action?id=" + list.id) == navigation.url)!> selected </#if>>
									<#if list.level != 0>
										<#list 1..list.level as i>&nbsp;&nbsp;&nbsp;&nbsp;</#list>
									</#if>
								${list.name}
								</option>
							</#list>
							<option value="">-----</option>
							<#list productCategoryTreeList as list>
								<option value="${"$"}{base}/shop/product!list.action?id=${list.id}"<#if (("${'$'}{base}/shop/product!list.action?id=" + list.id) == navigation.url)!> selected </#if>>
									<#if list.level != 0>
										<#list 1..list.level as i>&nbsp;&nbsp;&nbsp;&nbsp;</#list>
									</#if>
									${list.name}
								</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						链接地址:
					</th>
					<td>
						<input type="text" id="url" name="navigation.url" class="formText {required: true}" value="${(navigation.url)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						位置:
					</th>
					<td>
						<select name="navigation.position">
							<option value="top" <#if (navigation.position == "top")!>selected="selected" </#if>>
								${action.getText("Position.top")}
							</option>
							<option value="middle" <#if (isAdd || navigation.position == "middle")!>selected="selected" </#if>>
								${action.getText("Position.middle")}
							</option>
							<option value="bottom" <#if (navigation.position == "bottom")!>selected="selected" </#if>>
								${action.getText("Position.bottom")}
							</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>
						是否显示:
					</th>
					<td>
						<label><input type="radio" name="navigation.isVisible" value="true"<#if (isAdd || navigation.isVisible == true)!> checked</#if> />是</label>
						<label><input type="radio" name="navigation.isVisible" value="false"<#if (navigation.isVisible == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						是否在新窗口中打开:
					</th>
					<td>
						<label><input type="radio" name="navigation.isBlankTarget" value="true"<#if (navigation.isBlankTarget == true)!> checked</#if> />是</label>
						<label><input type="radio" name="navigation.isBlankTarget" value="false"<#if (isAdd || navigation.isBlankTarget == false)!> checked</#if> />否</label>
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="navigation.orderList" class="formText {digits: true, required: true}" value="${(navigation.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
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