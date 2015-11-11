<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑产品 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
	$()
			.ready(
					function() {

						// 查询产品属性
						$("#productTypeId")
								.change(
										function() {
											$(".productAttributeContentTr")
													.remove();
											var productTypeId = $(
													"#productTypeId").val();
											$
													.ajax({
														url : "product_attribute!ajaxProductAttribute.action",
														dataType : "json",
														data : {
															productTypeId : productTypeId
														},
														async : false,
														success : function(json) {
															var productAttributeTrHtml = "";
															$
																	.each(
																			json,
																			function(
																					i) {
																				if (json[i]["attributeType"] == "text") {
																					productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>'
																							+ json[i].name
																							+ ':</th><td><input type="text" name="'
																							+ json[i].id
																							+ '"'
																							+ ((json[i].isRequired == true) ? ' class="formText {required: true}"'
																									: ' class="formText"')
																							+ ' />'
																							+ ((json[i].isRequired == true) ? '<label class="requireField">*</label>'
																									: '')
																							+ '</td></tr>';
																				} else if (json[i]["attributeType"] == "number") {
																					productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>'
																							+ json[i].name
																							+ ':</th><td><input type="text" name="'
																							+ json[i].id
																							+ '"'
																							+ ((json[i].isRequired == true) ? ' class="formText {required: true, number: true}"'
																									: ' class="formText {number: true}"')
																							+ ' />'
																							+ ((json[i].isRequired == true) ? '<label class="requireField">*</label>'
																									: '')
																							+ '</td></tr>';
																				} else if (json[i]["attributeType"] == "alphaint") {
																					productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>'
																							+ json[i].name
																							+ ':</th><td><input type="text" name="'
																							+ json[i].id
																							+ '"'
																							+ ((json[i].isRequired == true) ? ' class="formText {required: true, lettersonly: true}"'
																									: ' class="formText {lettersonly: true}"')
																							+ ' />'
																							+ ((json[i].isRequired == true) ? '<label class="requireField">*</label>'
																									: '')
																							+ '</td></tr>';
																				} else if (json[i]["attributeType"] == "select") {
																					var productAttributeOption = '<option value="">请选择...</option>';
																					for ( var key in json[i]["attributeOptionList"]) {
																						productAttributeOption += ('<option value="' + json[i]["attributeOptionList"][key] + '">'
																								+ json[i]["attributeOptionList"][key] + '</option>');
																					}
																					productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>'
																							+ json[i].name
																							+ ':</th><td><select name="'
																							+ json[i].id
																							+ '"'
																							+ ((json[i].isRequired == true) ? ' class="{required: true}"'
																									: '')
																							+ '>'
																							+ productAttributeOption
																							+ '</select>'
																							+ ((json[i].isRequired == true) ? '<label class="requireField">*</label>'
																									: '')
																							+ '</td></tr>';
																				} else if (json[i]["attributeType"] == "checkbox") {
																					var productAttributeOption = "";
																					for ( var key in json[i]["attributeOptionList"]) {
																						productAttributeOption += ('<label><input type="checkbox" name="'
																								+ json[i].id
																								+ '" value="'
																								+ json[i]["attributeOptionList"][key]
																								+ '"'
																								+ ((json[i].isRequired == true) ? ' class="{required: true, messagePosition: \'#'
																										+ json[i].id
																										+ 'MessagePosition\'}"'
																										: '')
																								+ ' />'
																								+ json[i]["attributeOptionList"][key] + '</label>&nbsp;&nbsp;');
																					}
																					productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>'
																							+ json[i].name
																							+ ':</th><td>'
																							+ productAttributeOption
																							+ ((json[i].isRequired == true) ? '<span id="' + json[i].id + 'MessagePosition"></span><label class="requireField">*</label>'
																									: '')
																							+ '</td></tr>';
																				} else if (json[i]["attributeType"] == "date") {
																					productAttributeTrHtml += '<tr class="productAttributeContentTr"><th>'
																							+ json[i].name
																							+ ':</th><td><input type="text" name="'
																							+ json[i].id
																							+ '"'
																							+ ((json[i].isRequired == true) ? ' class="formText datePicker {required: true, dateISO: true}"'
																									: ' class="formText datePicker {dateISO: true}"')
																							+ ' />'
																							+ ((json[i].isRequired == true) ? '<label class="requireField">*</label>'
																									: '')
																							+ '</td></tr>';
																				}
																			})
															$("#productTypeTr")
																	.after(
																			productAttributeTrHtml);
															$.bindDatePicker();
														}
													});
										});

						// 产品图片预览滚动栏
						$(".productImageArea .scrollable").scrollable({
							speed : 600
						});

						// 显示产品图片预览操作层
						$(".productImageArea li")
								.livequery(
										"mouseover",
										function() {
											$(this)
													.find(
															".productImageOperate")
													.show();
										});

						// 隐藏产品图片预览操作层
						$(".productImageArea li")
								.livequery(
										"mouseout",
										function() {
											$(this)
													.find(
															".productImageOperate")
													.hide();
										});

						// 产品图片左移
						$(".left").livequery(
								"click",
								function() {
									var $productImageLi = $(this).parent()
											.parent().parent();
									var $productImagePrevLi = $productImageLi
											.prev("li");
									if ($productImagePrevLi.length > 0) {
										$productImagePrevLi
												.insertAfter($productImageLi);
									}
								});

						// 产品图片右移
						$(".right").livequery(
								"click",
								function() {
									var $productImageLi = $(this).parent()
											.parent().parent();
									var $productImageNextLi = $productImageLi
											.next("li");
									if ($productImageNextLi.length > 0) {
										$productImageNextLi
												.insertBefore($productImageLi);
									}
								});

						// 产品图片删除
						$(".delete")
								.livequery(
										"click",
										function() {
											var $productImageLi = $(this)
													.parent().parent().parent();
											var $productImagePreview = $productImageLi
													.find(".productImagePreview");
											var $productImageIds = $productImageLi
													.find("input[name='productImageIds']");
											var $productImageFiles = $productImageLi
													.find("input[name='productImages']");
											var $productImageParameterTypes = $productImageLi
													.find("input[name='productImageParameterTypes']");
											$productImageIds.remove();
											$productImageFiles
													.after('<input type="file" name="productImages" hidefocus="true" />');
											$productImageFiles.remove();
											$productImageParameterTypes
													.remove();

											$productImagePreview.html("暂无图片");
											$productImagePreview
													.removeAttr("title");
											if ($.browser.msie) {
												if (window.XMLHttpRequest) {
													$productImagePreview[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = 'scale', src='')";
												}
											}
										});

						// 产品图片选择预览
						var $productImageScrollable = $(
								".productImageArea .scrollable").scrollable();
						var productImageLiHtml = '<li><div class="productImageBox"><div class="productImagePreview">暂无图片</div><div class="productImageOperate"><a class="left" href="javascript: void(0);" alt="左移" hidefocus="true"></a><a class="right" href="javascript: void(0);" title="右移" hidefocus="true"></a><a class="delete" href="javascript: void(0);" title="删除" hidefocus="true"></a></div><a class="productImageUploadButton" href="javascript: void(0);"><input type="file" name="productImages" hidefocus="true" /><div>上传新图片</div></a></div></li>';
						$(".productImageUploadButton input")
								.livequery(
										"change",
										function() {
											var $this = $(this);
											var $productImageLi = $this
													.parent().parent().parent();
											var $productImagePreview = $productImageLi
													.find(".productImagePreview");
											var fileName = $this.val().substr(
													$this.val().lastIndexOf(
															"\\") + 1);
											if (/(<#list systemConfig.allowedUploadImageExtension?split(stack.findValue("@cc.jiuyi.bean.SystemConfig@EXTENSION_SEPARATOR")) as list><#if list_has_next>.${list}|<#else>.${list}</#if></#list>)$/i
													.test($this.val()) == false) {
												$.message("您选择的文件格式错误！");
												return false;
											}
											$productImagePreview.empty();
											$productImagePreview.attr("title",
													fileName);
											if ($.browser.msie) {
												if (!window.XMLHttpRequest) {
													$productImagePreview
															.html('<img src="'
																	+ $this
																			.val()
																	+ '" />');
												} else {
													$this[0].select();
													var imgSrc = document.selection
															.createRange().text;
													$productImagePreview[0].style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod = 'scale', src='"
															+ imgSrc + "')";
												}
											} else if ($.browser.mozilla) {
												$productImagePreview
														.html('<img src="'
																+ $this[0].files[0]
																		.getAsDataURL()
																+ '" />');
											} else {
												$productImagePreview
														.html(fileName);
											}
											if ($productImageLi.next().length == 0) {
												$productImageLi
														.after(productImageLiHtml);
												if ($productImageScrollable
														.getSize() > 5) {
													$productImageScrollable
															.next();
												}
											}
											var $productImageIds = $productImageLi
													.find("input[name='productImageIds']");
											var $productImageParameterTypes = $productImageLi
													.find("input[name='productImageParameterTypes']");
											var $productImageUploadButton = $productImageLi
													.find(".productImageUploadButton");
											$productImageIds.remove();
											if ($productImageParameterTypes.length > 0) {
												$productImageParameterTypes
														.val("productImageFile");
											} else {
												$productImageUploadButton
														.append('<input type="hidden" name="productImageParameterTypes" value="productImageFile" />');
											}
										});

					})
</script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}
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
					<li class="active"><#if isAdd??>添加产品<#else>编辑产品</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>




			<div class="blank1"></div>
			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!--./add by welson 0910 -->


							<form id="inputForm" class="validate"
								action="<#if isAdd??>product!save.action<#else>product!update.action</#if>"
								method="post" enctype="multipart/form-data">
								<input type="hidden" name="id" value="${id}" />


								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>

										<li><a href="#tabs-2">产品描述</a></li>

										<li><a href="#tabs-3">产品属性</a></li>
									</ul>

									<table id="tabs-1" class="inputTable tabContent">
										<tr>
											<th>产品名称:</th>
											<td><input type="text" name="product.name"
												class="formText {required: true}" value="${(product.name)!}" />
												<label class="requireField">*</label></td>
										</tr>
										<tr>
											<th>产品编码:</th>
											<td><input type="text" class="formText"
												name="product.productSn" value="${(product.productSn)!}"
												placeholder="SAP/ERP编码" /></td>
										</tr>
										<tr>
											<th>产品分类:</th>
											<td><select name="product.productCategory.id"
												class="{required: true}">
													<option value="">请选择...</option> <#list
													productCategoryTreeList as list>
													<option value="${list.id}"<#if (list.id ==
														product.productCategory.id)!> selected</#if>> <#if
														list.level != 0> <#list 1..list.level as i>------</#list>
														</#if> ${list.name}</option> </#list>
											</select></td>
										</tr>
										<tr>
											<th>产品品牌:</th>
											<td><select name="product.brand.id">
													<option value="">请选择...</option> <#list allBrand as list>
													<option value="${list.id}"<#if (list.id ==
														product.brand.id)!> selected </#if>> ${list.name}</option> </#list>
											</select></td>
										</tr>
										<tr>
											<th>单价:</th>
											<td><input type="text" name="product.price"
												class="formText {required: true, min: 0}"
												value="${(product.price)!" 0"}" /> <label
												class="requireField">*</label></td>
										</tr>
										<tr>
											<th>市场售价:</th>
											<td><input type="text" name="product.marketPrice"
												class="formText {required: true, min: 0}"
												value="${(product.marketPrice)!" 0"}" /> <label
												class="requireField">*</label></td>
										</tr>
										<#if systemConfig.pointType == "productSet">
										<tr>
											<th>积分:</th>
											<td><input type="text" name="product.point"
												class="formText {required: true, digits: true}"
												value="${(product.point)!" 0"}" /></td>
										</tr>
										</#if>
										<tr>
											<th>产品重量:</th>
											<td><input type="text" name="product.weight"
												class="formText {required: true, min: 0, messagePosition: '#weightMessagePosition'}"
												value="${(product.weight)!" 0"}" title="0表示不计重量" /> <select
												name="product.weightUnit"> <#list allWeightUnit as
													list>
													<option value="${list}"<#if (list ==
														product.weightUnit)!> selected </#if>>
														${action.getText("WeightUnit." + list)}</option> </#list>
											</select> <span id="weightMessagePosition"></span> <label
												class="requireField">*</label></td>
										</tr>
										<tr>
											<th>库存量:</th>
											<td><input type="text" name="product.store"
												class="formText {digits: true}" value="${(product.store)!}"
												title="只允许输入零或正整数，为空表示不计库存" /> <input type="button"
												name="syncLgort" class="btn btn-sm btn-default" value="同步库存"
												hidefocus="true" /></td>
										</tr>
										<tr>
											<th>是否精品推荐:</th>
											<td><label class="pull-left inline"> <small
													class="muted smaller-90">是:</small> <input type="radio"
													name="product.isBest" class="ace" value="true"<#if
													(product.isBest == true)!> checked</#if> /> <span
													class="lbl middle"></span> </label> <label class="pull-left inline">

													<small class="muted smaller-90">否:</small> <input
													type="radio" name="product.isBest" class="ace"
													value="false"<#if (isAdd || product.isBest ==
													false)!> checked</#if> /> <span class="lbl middle"></span>
											</label></td>
										</tr>
										<tr>
											<th>是否新品推荐:</th>
											<td><label class="pull-left inline"> <small
													class="muted smaller-90">是:</small> <input type="radio"
													name="product.isNew" class="ace" value="true"<#if
													(product.isNew == true)!> checked</#if> /> <span
													class="lbl middle"></span> </label> <label class="pull-left inline">

													<small class="muted smaller-90">否:</small> <input
													type="radio" name="product.isNew" class="ace" value="false"<#if
													(isAdd || product.isNew == false)!> checked</#if> /> <span
													class="lbl middle"></span> </label></td>
										</tr>
										<tr>
											<th>是否热销推荐:</th>
											<td><label class="pull-left inline"> <small
													class="muted smaller-90">是:</small> <input type="radio"
													name="product.isHot" class="ace" value="true"<#if
													(product.isHot == true)!> checked</#if> /> <span
													class="lbl middle"></span> </label> <label class="pull-left inline">

													<small class="muted smaller-90">否:</small> <input
													type="radio" name="product.isHot" class="ace" value="false"<#if
													(isAdd || product.isHot == false)!> checked</#if> /> <span
													class="lbl middle"></span> </label></td>
										</tr>
										<tr>
											<th>是否上架:</th>
											<td><label class="pull-left inline"> <small
													class="muted smaller-90">是:</small> <input type="radio"
													name="product.isMarketable" class="ace" value="true"<#if
													(product.isMarketable == true)!> checked</#if> /> <span
													class="lbl middle"></span> </label> <label class="pull-left inline">

													<small class="muted smaller-90">否:</small> <input
													type="radio" name="product.isMarketable" class="ace"
													value="false"<#if (isAdd || product.isMarketable ==
													false)!> checked</#if> /> <span class="lbl middle"></span>
											</label></td>
										</tr>
										<tr>
											<th>上传产品图片</th>
											<td>
												<div class="productImageArea">
													<div class="example"></div>
													<a class="prev browse" href="javascript:void(0);"
														hidefocus="true"></a>
													<div class="scrollable">
														<ul class="items">
															<#list (product.productImageList)! as list>
															<li>
																<div class="productImageBox">
																	<div class="productImagePreview png">
																		<img src="${base}${list.thumbnailProductImagePath}">
																	</div>
																	<div class="productImageOperate">
																		<a class="left" href="javascript: void(0);" alt="左移"
																			hidefocus="true"></a> <a class="right"
																			href="javascript: void(0);" title="右移"
																			hidefocus="true"></a> <a class="delete"
																			href="javascript: void(0);" title="删除"
																			hidefocus="true"></a>
																	</div>
																	<a class="productImageUploadButton"
																		href="javascript: void(0);"> <input type="hidden"
																		name="productImageIds" value="${list.id}" /> <input
																		type="hidden" name="productImageParameterTypes"
																		value="productImageId" /> <#if
																		systemConfig.allowedUploadImageExtension != ""> <input
																		type="file" name="productImages" hidefocus="true" />
																		<div>上传新图片</div> <#else>
																		<div>不允许上传</div> </#if> </a>
																</div></li> </#list>
															<li>
																<div class="productImageBox">
																	<div class="productImagePreview png">暂无图片</div>
																	<div class="productImageOperate">
																		<a class="left" href="javascript: void(0);" alt="左移"
																			hidefocus="true"></a> <a class="right"
																			href="javascript: void(0);" title="右移"
																			hidefocus="true"></a> <a class="delete"
																			href="javascript: void(0);" title="删除"
																			hidefocus="true"></a>
																	</div>
																	<a class="productImageUploadButton"
																		href="javascript: void(0);"> <#if
																		systemConfig.allowedUploadImageExtension != ""> <input
																		type="file" name="productImages" hidefocus="true" />
																		<div>上传新图片</div> <#else>
																		<div>不允许上传</div> </#if> </a>
																</div></li>
														</ul>
													</div>
													<a class="next browse" href="javascript:void(0);"
														hidefocus="true"></a>
													<div class="blank1"></div>
													<#if systemConfig.allowedUploadImageExtension != ""> <span
														class="warnInfo"><span class="icon">&nbsp;</span><#if
														(systemConfig.uploadLimit) != 0 &&
														(systemConfig.uploadLimit <
														1024)>小于${systemConfig.uploadLimit}KB<#elseif
														(systemConfig.uploadLimit >=
														1024)>小于${systemConfig.uploadLimit / 1024}MB</#if> (<#list
														systemConfig.allowedUploadImageExtension?split(stack.findValue("@cc.jiuyi.bean.SystemConfig@EXTENSION_SEPARATOR"))
														as list><#if
														list_has_next>*.${list};<#else>*.${list}</#if></#list>)</span>
													<#else> <span class="warnInfo"><span class="icon">&nbsp;</span>系统设置不允许上传图片文件!</span>
													</#if>
												</div></td>
										</tr>
										<tr>
											<th>页面关键词:</th>
											<td><input type="text" name="product.metaKeywords"
												class="formText" value="${(product.metaKeywords)!}" /></td>
										</tr>
										<tr>
											<th>页面描述:</th>
											<td><textarea name="product.metaDescription"
													class="formTextarea" cols="100">${(product.metaDescription)!}</textarea>
											</td>
										</tr>
									</table>



									<table id="tabs-2" class="inputTable tabContent">
										<tr>
											<td colspan="2"><textarea name="product.description"
													class="wysiwyg" style="width: 100%; height: 450px;">${(product.description)!}</textarea>
											</td>
										</tr>
									</table>




									<table id="tabs-3" class="inputTable tabContent">
										<tr id="productTypeTr">
											<th>产品类型:</th>
											<td><select name="product.productType.id"
												id="productTypeId">
													<option value="">请选择...</option> <#list allProductType as
													list>
													<option value="${list.id}"<#if (list.id ==
														product.productType.id)!> selected </#if>>${list.name}</option>
													</#list>
											</select></td>
										</tr>
										<#list (product.productType.enabledProductAttributeList)! as
										list>
										<tr class="productAttributeContentTr">
											<th>${list.name}:</th>
											<td><#if list.attributeType == "text"> <input
												type="text" name="${list.id}"
												class="formText<#if list.isRequired> {required: true}</#if>"
												value="${(product.productAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "number"> <input type="text"
												name="${list.id}"
												class="formText {<#if list.isRequired>required: true, </#if>number: true}"
												value="${(product.productAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "alphaint"> <input
												type="text" name="${list.id}"
												class="formText {<#if list.isRequired>required: true, </#if>lettersonly: true}"
												value="${(product.productAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "select"> <select
												name="${list.id}"<#if list.isRequired>
													class="{required: true}"</#if>>
													<option value="">请选择...</option> <#list
													list.attributeOptionList as attributeOptionList>
													<option value="${attributeOptionList}"<#if
														(product.productAttributeMap.get(list)[0] ==
														attributeOptionList)!>
														selected</#if>>${attributeOptionList}</option> </#list>
											</select> <#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "checkbox"> <#list
												list.attributeOptionList as attributeOptionList> <label><input
													type="checkbox" name="${list.id}"<#if
													list.isRequired> class="{required: true, messagePosition:
													'#${list.id}MessagePosition'}"</#if>
													value="${attributeOptionList}"<#if
													(product.productAttributeMap.get(list).contains(attributeOptionList))!>
													checked</#if> />${attributeOptionList}</label> </#list> <span
												id="${list.id}MessagePosition"></span> <#if list.isRequired><label
												class="requireField">*</label></#if> <#elseif
												list.attributeType == "date"> <input type="text"
												name="${list.id}"
												class="formText datePicker {<#if list.isRequired>required: true, </#if>dateISO: true}"
												value="${(product.productAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												</#if></td>
										</tr>
										</#list>
									</table>

								</div>


								<div class="buttonArea">
									<input type="submit" class="formButton" value="确  定"
										hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; <input
										type="button" class="formButton"
										onclick="window.history.back(); return false;" value="返  回"
										hidefocus="true" />
								</div>
							</form>

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
</html>