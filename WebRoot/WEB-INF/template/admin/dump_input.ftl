<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>

<title>添加/编辑转储记录 - Powered By ${systemConfig.systemName}</title>
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
				<a href="admin!index.action">生产管理</a>
			</li>
			<li class="active"><#if isAdd??>添加转储记录<#else>编辑转储记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>dump!save.action<#else>dump!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						物料凭证号:
					</th>
					<td>
							<input type="text" name="dump.voucherId" value="${(dump.voucherId)!}" class="formText {required: true, minlength: 2, maxlength: 100}" />
							<label class="requireField">*</label>				
					</td>
				</tr>
				<tr>
					<th>
						发货日期:
					</th>
					<td>
							<input type="text" name="dump.deliveryDate" value="${(dump.deliveryDate)!}" class="formText {required: true, minlength: 2, maxlength: 100} datePicker" />
							<label class="requireField">*</label>
						
					</td>
				</tr>
				<tr>
					<th>
						确认人:
					</th>
					<td>
							<input type="text" name="dump.confirmUser" value="${(dump.confirmUser)!}" class="formText {required: true, minlength: 2, maxlength: 100}" />
							<label class="requireField">*</label>
						
					</td>
				</tr>
				<tr>
					<th>
						状态:
					</th>
					<td>
					<label class="pull-left inline">
					    <small class="muted smaller-90">已确认:</small>
						<input type="radio" class="ace" name="dump.state" value="已确认"<#if (dump.state == '已确认')!> checked</#if> />
						<span class="lbl middle"></span>
						&nbsp;&nbsp;
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">未确认:</small>
						<input type="radio" class="ace" name="dump.state" value="未确认"<#if (isAdd || dump.state == '未确认')!> checked</#if>  />
						<span class="lbl middle"></span>
					</label>	
					
					</td>
				</tr>	
				<#list enableddumpAttributeList as list>
					<tr>
						<th>
							${list.name}:
						</th>
						<td>
							<#if list.attributeType == "text">
								<input type="text" name="el_${list.id}" class="formText<#if list.isRequired> {required: true}</#if>" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "number">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>number: true}" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "alphaint">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>lettersonly: true}" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "email">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>email: true}" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "select">
								<select name="el_${list.id}"<#if list.isRequired> class="{required: true}"</#if>>
									<option value="">请选择...</option>
									<#list list.attributeOptionList as attributeOptionList>
										<option value="${attributeOptionList}"<#if (dump.dumpAttributeMap.get(list)[0] == attributeOptionList)!> selected</#if>>${attributeOptionList}</option>
									</#list>
								</select>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "checkbox">
								<#list list.attributeOptionList as attributeOptionList>
									<label><input type="checkbox" name="el_${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="${attributeOptionList}"<#if (dump.dumpAttributeMap.get(list).contains(attributeOptionList))!> checked</#if>  />${attributeOptionList}</label>
								</#list>
								<span id="${list.id}MessagePosition"></span>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "name">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "gender">
								<label><input type="radio" name="el_${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="male" <#if (dump.dumpAttributeMap.get(list)[0] == "male")!> checked</#if> />${action.getText("Gender.male")}</label>
								<label><input type="radio" name="el_${list.id}"<#if list.isRequired> class="{required: true, messagePosition: '#${list.id}MessagePosition'}"</#if> value="female" <#if (dump.dumpAttributeMap.get(list)[0] == "female")!> checked</#if> />${action.getText("Gender.female")}</label>
								<span id="${list.id}MessagePosition"></span>
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "date">
								<input type="text" name="el_${list.id}" class="formText datePicker {<#if list.isRequired>required: true, </#if>dateISO: true}" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "area">
								<input type="text" name="el_${list.id}" class="formText areaSelect<#if list.isRequired> {required: true}</#if>" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "address">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "zipCode">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>zipCode: true}" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "mobile">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>mobile: true}" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "phone">
								<input type="text" name="el_${list.id}" class="formText {<#if list.isRequired>required: true, </#if>phone: true}" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "qq">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "msn">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "wangwang">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
								<#if list.isRequired><label class="requireField">*</label></#if>
							<#elseif list.attributeType == "skype">
								<input type="text" name="el_${list.id}" class="formText <#if list.isRequired>{required: true}</#if>" value="${(dump.dumpAttributeMap.get(list)[0])!}" />
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