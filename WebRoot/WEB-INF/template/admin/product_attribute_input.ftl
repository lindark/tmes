<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>添加/编辑产品属性 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready(function() {

	var $productAttributeType = $("#productAttributeType");
	var $productAttributeTypeTr = $("#productAttributeTypeTr");
	var $addAndRemoveTr = $("#addAndRemoveTr");

	// 显示选项内容
	$productAttributeType.change(function() {
		productAttributeChange();
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

	function productAttributeChange() {
		$addAndRemoveTr.hide();
		$(".attributeOptionTr").remove();
		if($productAttributeType.val() == "select" || $productAttributeType.val() == "checkbox") {
			addAttributeOptionTr();
			$addAndRemoveTr.show();
		}
	}
	
	function addAttributeOptionTr() {
		var attributeOptionTrHtml = '<tr class="attributeOptionTr"><th>选项内容:</th><td><input type="text" name="attributeOptionList" class="formText attributeOption {required: true}" />&nbsp;<img src="${base}/template/admin/images/input_delete_icon.gif" class="deleteImage" alt="删除" /></td></tr>';
		if($(".attributeOptionTr").length > 0) {
			$(".attributeOptionTr:last").after(attributeOptionTrHtml);
		} else {
			$productAttributeTypeTr.after(attributeOptionTrHtml);
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
			<li class="active"><#if isAdd??>添加产品属性<#else>编辑产品属性</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>product_attribute!save.action<#else>product_attribute!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" name="productTypeId" value="${productTypeId}" />
			<table class="inputTable">
				<tr>
					<th>
						所属产品类型:
					</th>
					<td>
						<select name="productAttribute.productType.id" id="productTypeId" class="{required: true}">
							<option value="">请选择...</option>
							<#list allProductType as list>
								<option value="${list.id}"<#if (list.id == productTypeId || list.id == productAttribute.productType.id)!> selected </#if>>${list.name}</option>
							</#list>
						</select>
						<lable class="requireField">*</lable>
					</td>
				</tr>
				<tr>
					<th>
						产品属性名称:
					</th>
					<td>
						<input type="text" name="productAttribute.name" id="productAttributeName" class="formText {required: true}" value="${(productAttribute.name)!}" />
						<lable class="requireField">*</lable>
					</td>
				</tr>
				<tr id="productAttributeTypeTr">
					<th>
						产品属性类型:
					</th>
					<td>
						<select id="productAttributeType" name="productAttribute.attributeType" class="{required: true}">
							<option value="">请选择...</option>
							<#list allAttributeType as list>
								<option value="${list}"<#if (list == productAttribute.attributeType)!> selected </#if>>
								${action.getText("AttributeType." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<#if (productAttribute.attributeType == "select" || productAttribute.attributeType == "checkbox")!>
					<#list productAttribute.attributeOptionList as list>
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
						<input type="text" name="productAttribute.orderList" class="formText {digits: true, required:true}" value="${(productAttribute.orderList)!50}" title="只允许输入零或正整数" />
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
						<input type="radio" name="productAttribute.isRequired" class="ace" value="true"<#if (productAttribute.isRequired == true)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">否:</small>
						<input type="radio" name="productAttribute.isRequired" class="ace" value="false"<#if (isAdd || productAttribute.isRequired == false)!> checked</#if> />
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
						<input type="radio" name="productAttribute.isEnabled" class="ace" value="true"<#if (productAttribute.isEnabled == true)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">否:</small>
						<input type="radio" name="productAttribute.isEnabled" class="ace" value="false"<#if (isAdd || productAttribute.isEnabled == false)!> checked</#if> />
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