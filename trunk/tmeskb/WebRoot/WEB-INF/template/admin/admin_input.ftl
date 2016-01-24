<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>人员管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<#include "/WEB-INF/template/common/includelist.ftl">
<script type="text/javascript"
	src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript"
	src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript"
	src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript"
	src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browserValidate.js"></script>
<link href="${base}/template/admin/css/input.css" rel="stylesheet"	type="text/css" />
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
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-content">




			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validateajax form-horizontal"
								action="<#if isAdd??>admin!save.action<#else>admin!update.action</#if>"
								method="post">								
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-1"> 登录名 </label>
										<div class="col-sm-9">
											<input type="hidden" name="id" value="${(admin.id)! }"/>
											<#if isAdd??>
											<input type="text" name="admin.username" id="form-field-1" placeholder="登录名" class="col-xs-10 col-sm-5 formText {required: true, username: true, remote: 'admin!checkUsername.action', minlength: 2, maxlength: 20, messages: {remote: '登录名已存在,请重新输入!'}}" title="登录名只允许包含中文、英文、数字和下划线">
											<label class="requireField">*</label>
											<#else> ${(admin.username)!} <input type="hidden"
												name="admin.username" value="${(admin.username)!}" />
											</#if>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 密码 </label>
										<div class="col-sm-9">
											<input type="password" id="password" placeholder="密码" name="admin.password"<#if
											isAdd??>class="col-xs-10 col-sm-5 formText {required: true,
											minlength: 4, maxlength: 20}"<#else>class="col-xs-10 col-sm-5
											formText {minlength: 4, maxlength: 20}"</#if>
											title="密码长度只允许在4-20之间" /> <label class="requireField">*</label>
											<#if isEdit??> <span class="warnInfo"><span
												class="icon">&nbsp;</span>如果要修改密码,请填写密码,若留空,密码将保持不变!</span> </#if>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 重复密码 </label>
										<div class="col-sm-9">
											<input type="password" name="rePassword" placeholder="请重复输入密码"
												class="col-xs-10 col-sm-5 formText {equalTo: '#password', messages: {equalTo: '两次密码输入不一致!'}}" />
											<#if isAdd??><label class="requireField">*</label></#if>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">工号</label>
										<div class="col-sm-9">
											<input type="text" name="admin.workNumber"
												class="col-xs-10 col-sm-5 formText {required: true}"
												value="${(admin.workNumber)!}" /> <label class="requireField">*</label>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">直接上级</label>
										<div class="col-sm-9">
										    <select style="width:290px;" name="admin.parentAdmin.id">
														<option value="">请选择...</option> 
														<#list adminList as list>
														<option value="${list.id}" <#if (list.id == admin.parentAdmin.id)!> selected</#if>>${list.name}</option>
														</#list>
											</select>
										   <!--   <#if isAdd??>
										    <select style="width:290px;" name="parentId">
														<option value="">请选择...</option> 
														<#list adminList as list>
														<option value="${list.id}" <#if (list.id == admin.parentAdmin.id)!> selected</#if>>${list.name}</option>
														</#list>
											</select>
											<#else>
										       ${(admin.parentAdmin.name)!}
										    </#if>--> 
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">身份证号</label>
										<div class="col-sm-9">
											<input type="text" name="admin.identityCard"
												class="col-xs-10 col-sm-5 formText {required: true}"
												value="${(admin.identityCard)!}" /> <label class="requireField">*</label>
										</div>
								</div>
								
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">岗位</label>
										<div class="col-sm-9">
											<select name="admin.post.id" id="form-field-icon-1" class="col-xs-10 col-sm-5 formText {required: true}"> 
											    <option value="">-请选择-</option>											
										        <#list postList as list>							
									            <option value="${list.id}"<#if ((isAdd && list.isDefault) || (isEdit && admin.post.id == list.id))!> selected</#if>>${list.postName}</option>
								                </#list>   
										      </select> 
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">工位</label>
										<div class="col-sm-9">
										       <select style="width:290px;" name="admin.unitdistributeProduct.id">
														<option value="">请选择...</option> 
														<#list unitProductList as list>
														<option value="${list.id}" <#if (list.id == admin.unitdistributeProduct.id)!> selected</#if>>${list.materialName}</option>
														</#list>
											</select>
											  
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2">工作范围</label>
										<div class="col-sm-9">
											  <select style="width:290px;" name="admin.unitdistributeModel.id">
														<option value="">请选择...</option> 
														<#list unitProductList as list>
														<option value="${list.id}" <#if (list.id == admin.unitdistributeModel.id)!> selected</#if>>${list.materialName}</option>
														</#list>
											</select>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 联系电话</label>
										<div class="col-sm-9">
											<input type="text" name="admin.phoneNo"
												class="col-xs-10 col-sm-5"
												value="${(admin.phoneNo)!}" /> <label class="requireField">*</label>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> E-mail</label>
										<div class="col-sm-9">
											<input type="text" name="admin.email"
												class="col-xs-10 col-sm-5"
												value="${(admin.email)!}" />
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 管理角色</label>
										<div class="col-sm-9">
											<#list allRoleSystem as list> <label> <input
												type="checkbox" name="roleList.id"
												class="{required: true, messages: {required: '请至少选择一个角色!'}, messagePosition: '#roleMessagePosition'}"
												value="${list.id}"<#if (admin.roleSet.contains(list)
												== true)!> checked="checked"</#if> /> ${(list.name)!} </label>
											</#list> <span id="roleMessagePosition"></span>
											<a href="javascript:void(0);" class="a" onClick="$('#morerole').removeClass('hide')"><span id="more">更多...</span></a>
											<div style="border:1px solid #ccc" class="hide" id="morerole">
												<#list allRole as list>
													<label> <input
													type="checkbox" name="roleList.id"
													value="${list.id}"<#if (admin.roleSet.contains(list)
													== true)!> checked="checked"</#if> /> ${(list.name)!} </label>
												</#list>
											</div>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 是否启用</label>
										<div class="col-sm-9">
											<label class="pull-left inline"> <small
												class="muted smaller-90">是:</small> <input type="radio"
												name="admin.isAccountEnabled" class="ace" value="true"<#if
												(admin.isAccountEnabled == true)!> checked</#if> /> <span
												class="lbl middle"></span> </label> <label class="pull-left inline">

												<small class="muted smaller-90">否:</small> <input
												type="radio" name="admin.isAccountEnabled" class="ace"
												value="false"<#if (isAdd || admin.isAccountEnabled
												== false)!> checked</#if> /> <span class="lbl middle"></span>
											</label>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 部门</label>
										<div class="col-sm-9">
											<#if isAdd??> <input type="hidden" name="admin.department.id"
											value="${(departid)!}" />${(departName)!}
										<#else> <input type="hidden" name="departid"
											value="${(admin.department.id)!}" />
											${(admin.department.deptName)!}
										</#if>
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 姓名</label>
										<div class="col-sm-9">
										<input type="text" name="admin.name"
												class=" input input-sm  formText {required: true}"
												value="${(admin.name)!}" />
										</div>
								</div>
								<div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 卡号</label>
										<div class="col-sm-9">
											<input type="text" name="admin.cardNumber"
												class=" input input-sm  formText {required: true}"
												value="${(admin.cardNumber)!}" />
										</div>
								</div>
								<!--  <div class="form-group">
										<label class="col-sm-3 control-label no-padding-right" for="form-field-2"> 状态</label>
										<div class="col-sm-9">
											<input type="text" name=""
												class=" input input-sm  formText {required: true}"
												value="${(admin.name)!}" />
										</div>
								</div>-->
								
								<div class="buttonArea" style="display:none">
									<input type="submit" class="formButton" id="submit_btn"
										value="确  定" hidefocus="true" />
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
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>
<script type="text/javascript">
	$(function(){
		$("ceshi").focus(function(){
			layer.open({
				type : 2, 
				area : [ '250px', '300px' ],
				title : false,
				shade : 0,
				shadeClose : true,
				offset : [ top, left ],
				btn : [ '确定', '取消' ],
				closeBtn : 0,
				content : 'team!browser.action',
				yes : function(index, layero) {
					//var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
					//var ii = iframeWin.getName();
					//$("#departName").val(ii);
					//layer.close(index);
				}
			});
			
		})
		
	})

	
</script>
</html>