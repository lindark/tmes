<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>权限对象管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<#include "/WEB-INF/template/common/includelist.ftl">
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browserValidate.js"></script>
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
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

							<form id="inputForm" class="validateajax"
								action="<#if isAdd??>admin!save.action<#else>admin!update.action</#if>"
								method="post">
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<input type="hidden" name="id" value="${(admin.id)!}" />
										<!--id-->
										<div class="profile-info-name">登录名：</div>

										<div class="profile-info-value">
											<input type="text" id="ceshi" name="" value="" /> <#if
											isAdd??> <input type="text" name="admin.username"
												class="input input-sm formText {required: true, username: true, remote: 'admin!checkUsername.action', minlength: 2, maxlength: 20, messages: {remote: '登录名已存在,请重新输入!'}}"
												title="登录名只允许包含中文、英文、数字和下划线" /> <label class="requireField">*</label>
											<#else> ${(admin.username)!} <input type="hidden"
												name="admin.username" value="${(admin.username)!}" />
											</#if>
										</div>


									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<input type="hidden" name="department.id" value="" />
										<!--id-->
										<div class="profile-info-name">密码：</div>

										<div class="profile-info-value">
											<input type="password" id="password" name="admin.password"<#if
											isAdd??>class=" input input-sm formText {required: true,
											minlength: 4, maxlength: 20}"<#else>class="input input-sm
											formText {minlength: 4, maxlength: 20}"</#if>
											title="密码长度只允许在4-20之间" /> <label class="requireField">*</label>
											<#if isEdit??> <span class="warnInfo"><span
												class="icon">&nbsp;</span>如果要修改密码,请填写密码,若留空,密码将保持不变!</span> </#if>
										</div>

									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<input type="hidden" name="department.id" value="" />
										<!--id-->
										<div class="profile-info-name">重复密码：</div>

										<div class="profile-info-value">
											<input type="password" name="rePassword"
												class="input input-sm formText {equalTo: '#password', messages: {equalTo: '两次密码输入不一致!'}}" />
											<#if isAdd??><label class="requireField">*</label></#if>
										</div>

									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<input type="hidden" name="department.id" value="" />
										<!--id-->
										<div class="profile-info-name">E-mail：</div>

										<div class="profile-info-value">
											<input type="text" name="admin.email"
												class="input input-sm formText {required: true, email: true}"
												value="${(admin.email)!}" /> <label class="requireField">*</label>
										</div>

									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<input type="hidden" name="department.id" value="" />
										<!--id-->
										<div class="profile-info-name">管理角色：</div>

										<div class="profile-info-value">
											<#list allRole as list> <label> <input
												type="checkbox" name="roleList.id"
												class="{required: true, messages: {required: '请至少选择一个角色!'}, messagePosition: '#roleMessagePosition'}"
												value="${list.id}"<#if
												(admin.roleSet.contains(list) == true)!>
												checked="checked"</#if> /> ${(list.name)!} </label> </#list> <span
												id="roleMessagePosition"></span>
										</div>

									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<input type="hidden" name="department.id" value="" />
										<!--id-->
										<div class="profile-info-name">是否启用：</div>

										<div class="profile-info-value">
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
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<#if isAdd??> <input type="hidden" name="admin.department.id"
											value="${(departid)!}" />
										<div class="profile-info-name">部门：</div>

										<div class="profile-info-value">${(departName)!}</div>
										<#else> <input type="hidden" name="admin.department.id"
											value="${(admin.department.id)!}" />
										<div class="profile-info-name">部门：</div>

										<div class="profile-info-value">
											${(admin.department.deptName)!}</div>
										</#if>
									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name">姓名：</div>

										<div class="profile-info-value">
											<input type="text" name="admin.name"
												class=" input input-sm  formText {required: true}"
												value="${(admin.name)!}" />
										</div>

									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name">卡号：</div>

										<div class="profile-info-value">
											<input type="text" name=""
												class=" input input-sm  formText {required: true}"
												value="${(admin.name)!}" />
										</div>

									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name">班组：</div>

										<div class="profile-info-value">
											<input type="text" name="admin.team.id"
												class=" input input-sm  formText {required: true}"
												value="${(admin.team.teamName)!}" />
										</div>

									</div>
								</div>
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name">状态：</div>

										<div class="profile-info-value">
											<input type="text" name=""
												class=" input input-sm  formText {required: true}"
												value="${(admin.name)!}" />
										</div>

									</div>
								</div>
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
</html>
<script>

	$("#ceshi").focus(function(){
		var offset=$(this).offset();
		var controlHeight = $(this).height();
		var left = offset.left+"px";
		var top = offset.top+controlHeight+"px";
		layer.open({
		    type: 2, //page层
		    area: ['250px', '300px'],
		    title: false,
		    shade: 0,
		    shadeClose:true,
		    offset:[top,left],
		    btn:['确定','取消'],
		    closeBtn:0,
		    content: 'department!browser.action',
		    yes:function(index,layero){
		    	alert("OK");
		    }
		}); 
	});
	
</script>