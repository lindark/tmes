<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑产品Bom管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript">
	$().ready(function() {

		// 地区选择菜单
		$(".areaSelect").lSelect({
			url : "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
		});

	});
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
						href="admin!index.action">管理中心</a>
					</li>
					<li class="active"><#if
						isAdd??>添加产品Bom记录<#else>编辑产品Bom记录</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm"
								action="<#if isAdd??>material!save.action<#else>material!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">产品Bom信息</a>
										</li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">产品编码</div>
												<div class="profile-info-value">
													<input type="text" name="material.productCode"
														value="${(material.productCode)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">组件类型</div>
												<div class="profile-info-value">
													<input type="text" name="material.materialType"
														value="${(material.materialType)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>


												<div class="profile-info-name">组件编码</div>
												<div class="profile-info-value">
													<#if isAdd??> <input type="text" name="material.materialCode"
														class="formText {required: true,minlength:2,maxlength: 100,materialCode:true,remote:'material!checkMaterialCode.action',messages:{remote:'组件编码已存在'}}" />
													<label class="requireField">*</label> <#else>
													${material.materialCode} <input type="hidden"
														name="material.materialCode"
														value="${(material.materialCode)!}" /> </#if>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">组件名称</div>
												<div class="profile-info-value">
													<input type="text" name="material.materialName"
														value="${(material.materialName)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>


												<div class="profile-info-name">来源库存地点</div>
												<div class="profile-info-value">
													<input type="text" name="material.sourceLocation"
														value="${(material.sourceLocation)!}"
														class=" input input-sm  formText {required: true,minlength:1,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>


											<div class="profile-info-row">
												<div class="profile-info-name">去向库存地点</div>
												<div class="profile-info-value">
													<input type="text" name="material.storeLocation"
														value="${(material.storeLocation)!}"
														class=" input input-sm  formText {required: true,minlength:1,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>


												<div class="profile-info-name">组件单位</div>
												<div class="profile-info-value">
													<input type="text" name="material.materialUnit"
														value="${(material.materialUnit)!}"
														class=" input input-sm  formText {required: true,minlength:1,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>


											<div class="profile-info-row">
												<div class="profile-info-name">组件数量</div>
												<div class="profile-info-value">
													<input type="text" name="material.materialAmount"
														value="${(material.materialAmount)!}"
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>


												<div class="profile-info-name">批次</div>
												<div class="profile-info-value">
													<input type="text" name="material.batch"
														value="${(material.batch)!}"
														class=" input input-sm  formText {required: true,minlength:1,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">状态</div>
												<div class="profile-info-value">
													<label class="pull-left inline"> <small
														class="muted smaller-90">已启用:</small> <input type="radio"
														class="ace" name="material.state" value="1"<#if
														(material.state == '1')!> checked</#if> /> <span
														class="lbl middle"></span> &nbsp;&nbsp; </label> <label
														class="pull-left inline"> <small
														class="muted smaller-90">未启用:</small> <input type="radio"
														class="ace" name="material.state" value="2"<#if
														(isAdd || material.state == '2')!> checked</#if> /> <span
														class="lbl middle"></span> </label>
												</div>
											</div>
										</div>

										<#list enabledmaterialAttributeList as list>
										<tr>
											<th>${list.name}:</th>
											<td><#if list.attributeType == "text"> <input
												type="text" name="el_${list.id}"
												class="formText<#if list.isRequired> {required: true}</#if>"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "number"> <input type="text"
												name="el_${list.id}"
												class="formText {<#if list.isRequired>required: true, </#if>number: true}"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "alphaint"> <input
												type="text" name="el_${list.id}"
												class="formText {<#if list.isRequired>required: true, </#if>lettersonly: true}"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "email"> <input type="text"
												name="el_${list.id}"
												class="formText {<#if list.isRequired>required: true, </#if>email: true}"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "select"> <select
												name="el_${list.id}"<#if list.isRequired>
													class="{required: true}"</#if>>
													<option value="">请选择...</option> <#list
													list.attributeOptionList as attributeOptionList>
													<option value="${attributeOptionList}"<#if
														(material.materialAttributeMap.get(list)[0] ==
														attributeOptionList)!>
														selected</#if>>${attributeOptionList}</option> </#list>
											</select> <#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "checkbox"> <#list
												list.attributeOptionList as attributeOptionList> <label><input
													type="checkbox" name="el_${list.id}"<#if
													list.isRequired> class="{required: true, messagePosition:
													'#${list.id}MessagePosition'}"</#if>
													value="${attributeOptionList}"<#if
													(material.materialAttributeMap.get(list).contains(attributeOptionList))!>
													checked</#if> />${attributeOptionList}</label> </#list> <span
												id="${list.id}MessagePosition"></span> <#if list.isRequired><label
												class="requireField">*</label></#if> <#elseif
												list.attributeType == "name"> <input type="text"
												name="el_${list.id}"
												class="formText <#if list.isRequired>{required: true}</#if>"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "gender"> <label><input
													type="radio" name="el_${list.id}"<#if
													list.isRequired> class="{required: true, messagePosition:
													'#${list.id}MessagePosition'}"</#if> value="male" <#if
													(material.materialAttributeMap.get(list)[0] == "male")!>
													checked</#if> />${action.getText("Gender.male")}</label> <label><input
													type="radio" name="el_${list.id}"<#if
													list.isRequired> class="{required: true, messagePosition:
													'#${list.id}MessagePosition'}"</#if> value="female" <#if
													(material.materialAttributeMap.get(list)[0] == "female")!>
													checked</#if> />${action.getText("Gender.female")}</label> <span
												id="${list.id}MessagePosition"></span> <#if list.isRequired><label
												class="requireField">*</label></#if> <#elseif
												list.attributeType == "date"> <input type="text"
												name="el_${list.id}"
												class="formText datePicker {<#if list.isRequired>required: true, </#if>dateISO: true}"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "area"> <input type="text"
												name="el_${list.id}"
												class="formText areaSelect<#if list.isRequired> {required: true}</#if>"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "address"> <input type="text"
												name="el_${list.id}"
												class="formText <#if list.isRequired>{required: true}</#if>"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "zipCode"> <input type="text"
												name="el_${list.id}"
												class="formText {<#if list.isRequired>required: true, </#if>zipCode: true}"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "mobile"> <input type="text"
												name="el_${list.id}"
												class="formText {<#if list.isRequired>required: true, </#if>mobile: true}"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "phone"> <input type="text"
												name="el_${list.id}"
												class="formText {<#if list.isRequired>required: true, </#if>phone: true}"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "qq"> <input type="text"
												name="el_${list.id}"
												class="formText <#if list.isRequired>{required: true}</#if>"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "msn"> <input type="text"
												name="el_${list.id}"
												class="formText <#if list.isRequired>{required: true}</#if>"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "wangwang"> <input
												type="text" name="el_${list.id}"
												class="formText <#if list.isRequired>{required: true}</#if>"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												<#elseif list.attributeType == "skype"> <input type="text"
												name="el_${list.id}"
												class="formText <#if list.isRequired>{required: true}</#if>"
												value="${(material.materialAttributeMap.get(list)[0])!}" />
												<#if list.isRequired><label class="requireField">*</label></#if>
												</#if></td>
										</tr>
										</#list>
										</table>
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


</body>
</html>