<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工序管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {

	// 地区选择菜单
	$(".areaSelect").lSelect({
		url: "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
	});

});
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
			<li class="active"><#if isAdd??>添加工序记录<#else>编辑工序记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>process!save.action<#else>process!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						 工序编码:
					</th>
					<td>
							<input type="text" name="process.processCode" value="${(process.processCode)!}" class="formText {required: true, minlength: 2, maxlength: 100}" />
							<label class="requireField">*</label>				
					</td>
				</tr>
				<tr>
					<th>
						工序名称:
					</th>
					<td>
							<input type="text" name="process.processName" value="${(process.processName)!}" class="formText {required: true, minlength: 2, maxlength: 100}" />
							<label class="requireField">*</label>
						
					</td>
				</tr>
				<tr>
					<th>
						状态:
					</th>
					<td>
							<input type="text" name="process.state" value="${(process.state)!}" class="formText {required: true, minlength: 1, maxlength: 100}" />
							<label class="requireField">*</label>
						
					</td>
				</tr>
				<tr>
					<th>
						是否删除:
					</th>
					<td>
							<input type="text" name="process.isDel" value="${(process.isDel)!}" class="formText {required: true, minlength: 1, maxlength: 100}" />
							<label class="requireField">*</label>
						
					</td>
				</tr>		
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						&nbsp;
					</td>
				</tr>
				<#if isEdit>
					<tr>
						<th>
							注册日间
						</th>
						<td>
							${(process.createDate?string("yyyy-MM-dd HH:mm:ss"))!}
						</td>
					</tr>
					<tr>
						<th>
							注册IP
						</th>
						<td>
							${(process.registerIp)!}
						</td>
					</tr>
				</#if>
				<#list enabledprocessAttributeList as list>
					<tr>
						<th>
							${list.name}:
						</th>
						<td>
							<#if list.attributeType == "text">
								<input type="text" name="el_${list.id}" class="formText<#if list.isRequired> {required: true}</#if>" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "number">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>number: true}" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "alphaint">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>lettersonly: true}" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "email">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>email: true}" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "select">
								<select name="el_${list.id}"<#if list.isRequired> class="{required: true}"</#if>>
									<option value="">请选择...</option>
									<#list list.attributeOptionList as attributeOptionList>
										<option value="${attributeOptionList}"<#if (process.processAttributeMap.get(list)[0] == attributeOptionList)!> selected</#if>>${attributeOptionList}</option>
									</#list>
								</select>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "checkbox">
								<#list list.attributeOptionList as attributeOptionList>
									<label><input type="checkbox" name="el_${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="${attributeOptionList}"<#if (process.processAttributeMap.get(list).contains(attributeOptionList))!> checked</#if>  />${attributeOptionList}</label>
								</#list>
								<span id="${list.id}MessagePosition"></span>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "name">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "gender">
								<label><input type="radio" name="el_${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="male" <#if (process.processAttributeMap.get(list)[0] == "male")!> checked</#if> />${action.getText("Gender.male")}</label>
								<label><input type="radio" name="el_${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="female" <#if (process.processAttributeMap.get(list)[0] == "female")!> checked</#if> />${action.getText("Gender.female")}</label>
								<span id="${list.id}MessagePosition"></span>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "date">
								<input type="text" name="el_${list.id}" class="formText datePicker {<#if list.isRequired>required: true, </#if>dateISO: true}" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "area">
								<input type="text" name="el_${list.id}" class="formText areaSelect<#if list.isRequired> {required: true}</#if>" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "address">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "zipCode">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>zipCode: true}" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "mobile">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>mobile: true}" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "phone">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>phone: true}" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "qq">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "msn">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "wangwang">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "skype">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(process.processAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							</#if>
						</td>
					</tr>
				</#list>
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