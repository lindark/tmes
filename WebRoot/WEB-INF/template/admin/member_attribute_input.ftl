<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑会员扩展字段  - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	var $memberAttributeType = $("#memberAttributeType");
	var $memberAttributeTypeTr = $("#memberAttributeTypeTr");
	var $addAndRemoveTr = $("#addAndRemoveTr");

	// 显示选项内容
	$memberAttributeType.change(function() {
		memberAttributeChange();
	})
	
	// 增加选项内容输入框
	$("#addImage").click( function() {
		addAttributeOptionTr();
	})
	
	// 减少选项内容输入框
	$("#removeImage").click( function() {
		removeAttributeOptionTr();
	})
	
	// 删除选项内容输入框
	$(".deleteImage").livequery("click", function() {
		if($(".attributeOptionTr").length > 1) {
			$(this).parent().parent().remove();
		} else {
			alert("请至少保留一个选项!");
		}
	});

	function memberAttributeChange() {
		$addAndRemoveTr.hide();
		$(".attributeOptionTr").remove();
		if($memberAttributeType.val() == "select" || $memberAttributeType.val() == "checkbox") {
			addAttributeOptionTr();
			$addAndRemoveTr.show();
		}
	}
	
	function addAttributeOptionTr() {
		var attributeOptionTrHtml = '<tr class="attributeOptionTr"><th>选项内容:</th><td><input type="text" name="attributeOptionList" class="formText attributeOption {required: true}" />&nbsp;<img src="${base}/template/admin/images/input_delete_icon.gif" class="deleteImage" alt="删除" /></td></tr>';
		if($(".attributeOptionTr").length > 0) {
			$(".attributeOptionTr:last").after(attributeOptionTrHtml);
		} else {
			$memberAttributeTypeTr.after(attributeOptionTrHtml);
		}
	}

	function removeAttributeOptionTr() {
		if($(".attributeOptionTr").length > 1) {
			$(".attributeOptionTr:last").remove();
		} else {
			alert("请至少保留一个选项!");
		}
	}

})
</script>
<style type="text/css">
<!--

.deleteImage, #addImage, #removeImage {
	cursor: pointer;
}

-->
</style>
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
			<li class="active"><#if isAdd??>添加会员字段<#else>编辑会员字段</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->


		<form id="inputForm" class="validate" action="<#if isAdd??>member_attribute!save.action<#else>member_attribute!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						扩展字段名称:
					</th>
					<td>
						<input type="text" name="memberAttribute.name" class="formText {required: true, remote: 'member_attribute!checkName.action?oldValue=${(memberAttribute.name?url)!}', messages: {remote: '扩展字段名称已存在!'}}" value="${(memberAttribute.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr id="memberAttributeTypeTr">
					<th>
						扩展字段类型:
					</th>
					<td>
						<select id="memberAttributeType" name="memberAttribute.attributeType" class="{required: true}">
							<option value="">请选择...</option>
							<#list allAttributeType as list>
								<option value="${list}"<#if (list == memberAttribute.attributeType)!> selected</#if>>
									${action.getText("AttributeType." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<#if (memberAttribute.attributeType == "select" || memberAttribute.attributeType == "checkbox")!>
					<#list memberAttribute.attributeOptionList as list>
						<tr class="attributeOptionTr">
							<th>选项内容:</th>
							<td>
								<input type="text" name="attributeOptionList" class="formText attributeOption {required: true}" value="${list}" />
								&nbsp;<img src="${base}/template/admin/images/input_delete_icon.gif" class="deleteImage" alt="删除" />
							</td>
						</tr>
					</#list>
					<tr id="addAndRemoveTr">
				<#else>
					<tr id="addAndRemoveTr" style="display: none;">
				</#if>
					<td class="label">
						&nbsp;
					</td>
					<td>
						<img src="${base}/template/admin/images/input_add_icon.gif" id="addImage" alt="增加选项" />&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/template/admin/images/input_remove_icon.gif" id="removeImage" alt="减少选项" />
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="memberAttribute.orderList" class="formText {required: true, digits: true}" value="${(memberAttribute.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						是否必填:
					</th>
					<td>
					<label class="pull-left inline">
					    <small class="muted smaller-90">是:</small>
						<input type="radio" name="memberAttribute.isRequired" class="ace" value="true"<#if (memberAttribute.isRequired == true)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">否:</small>
						<input type="radio" name="memberAttribute.isRequired" class="ace" value="false"<#if (isAdd || memberAttribute.isRequired == false)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>	
				   </td>
				</tr>
				<tr>
					<th>
						是否启用:
					</th>
					<td>
					
					<label class="pull-left inline">
					    <small class="muted smaller-90">是:</small>
						<input type="radio" name="memberAttribute.isEnabled" class="ace" value="true"<#if (memberAttribute.isEnabled == true)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">否:</small>
						<input type="radio" name="memberAttribute.isEnabled" class="ace" value="false"<#if (isAdd || memberAttribute.isEnabled == false)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>	
					
					
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