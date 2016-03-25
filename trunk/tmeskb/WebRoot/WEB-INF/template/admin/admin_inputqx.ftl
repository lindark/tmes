<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>人员管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browserValidate.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/admin_inputqx.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
<style type="text/css">
	.mymust{color: red;font-size: 10px;}
	.class_label_xfuname{width:200px;line-height: 30px;border:1px solid;border-color: #d5d5d5;}
	.xspan{font-family: 微软雅黑;font-size: 10px;color:red;}
</style>
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
			<li class="active">员工权限维护</li>
		</ul><!-- /.breadcrumb -->
	</div>
	<!-- add by welson 0728 -->
	<div class="page-content">
		<div class="page-content-area">					
		<div class="row">
		<div class="col-xs-12">
		<!-- ./ add by welson 0728 -->
		<form id="xform" method="post" action="admin!saveqx.action" class="validate">
			<input type="hidden" id="loginid" name="loginid" value="<@sec.authentication property='principal.id' />" />
			<#if isEdit??>
				<input id="input_id" type="hidden" name="admin.id" value="${(admin.id)! }"/>
			</#if>
			<div class="profile-user-info profile-user-info-striped">
				<!-- 
				<div class="profile-info-row">
					<div class="profile-info-name">部门名称</div>
					<div class="profile-info-value">
						<span id="span_deptname">${(admin.department.deptName)! }</span>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">班组</div>
					<div class="profile-info-value">
						<span id="span_teamname">${(admin.team.teamName)! }</span>
					</div>
				</div>
				-->
				<div class="profile-info-row">
					<div class="profile-info-name">登录名</div>
					<div class="profile-info-value">
						<!-- 
						<input id="input_username" type="text" name="admin.username" id="form-field-1" placeholder="登录名" class="col-xs-10 col-sm-5 formText {required: true, username: true, remote: 'admin!checkUsername.action', minlength: 2, maxlength: 20, messages: {remote: '登录名已存在,请重新输入!'}}" title="登录名只允许包含中文、英文、数字和下划线">
						 -->
						<input id="input_username" type="text" name="admin.username" id="form-field-1" placeholder="登录名" value="${(admin.username)! }" class="col-xs-10 col-sm-5 formText {required: true,username: true, minlength: 2, maxlength: 20}" title="登录名只允许包含中文、英文、数字和下划线">
						<span id="span_username" style="color:red;font-family: 微软雅黑;font-size:10px;"></span>
						<label class="requireField">*</label>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">密码</div>
					<div class="profile-info-value">
						<input type="password" id="password" placeholder="密码" name="admin.password"
							<#if isAdd??>
								class="col-xs-10 col-sm-5 formText {required: true,minlength: 4, maxlength: 20}"
							<#else>
								class="col-xs-10 col-sm-5 formText {minlength: 4, maxlength: 20}"
							</#if>
							title="密码长度只允许在4-20之间" />
						<label class="requireField">*</label>
						<#if isEdit??>
						<span class="warnInfo"><span class="icon">&nbsp;</span>如果要修改密码,请填写密码,若留空,密码将保持不变!</span> </#if>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">重复密码</div>
					<div class="profile-info-value">
						<input type="password" name="rePassword" placeholder="请重复输入密码"
							class="col-xs-10 col-sm-5 formText {equalTo: '#password', messages: {equalTo: '两次密码输入不一致!'}}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">管理角色</div>
					<div class="profile-info-value">
						<#list allRoleSystem as list>
							<label>
								<input type="checkbox" name="roleList.id" 
									class="{required: true, messages: {required: '请至少选择一个角色!'}, messagePosition: '#roleMessagePosition'}"
									value="${list.id}"
									<#if (admin.roleSet.contains(list)== true)!> checked="checked"</#if> 
								/>
								${(list.name)!}
							</label>
						</#list>
						<span id="roleMessagePosition"></span>
						<a href="javascript:void(0);" class="a" onClick="$('#morerole').removeClass('hide')"><span id="more">更多...</span></a>
						<div style="border:1px solid #ccc" class="hide" id="morerole">
							<#list allRole as list>
								<label>
									<input type="checkbox" name="roleList.id" value="${list.id}"
										<#if (admin.roleSet.contains(list)== true)!> checked="checked"</#if>
									/>
									${(list.name)!}
								</label>
							</#list>
						</div>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">是否启用</div>
					<div class="profile-info-value">
						<label class="pull-left inline">
							<small class="muted smaller-90">已启用:</small>
							<input type="radio" name="admin.isAccountEnabled" class="ace" value="true" <#if (isAdd || admin.isAccountEnabled == true)!> checked</#if> />
							<span class="lbl middle"></span> </label> <label class="pull-left inline">

							<small class="muted smaller-90">未启用:</small>
							<input type="radio" name="admin.isAccountEnabled" class="ace" value="false"<#if (admin.isAccountEnabled== false)!> checked</#if> />
							<span class="lbl middle"></span>
						</label>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">员工姓名</div>
					<div class="profile-info-value">
						<#if isAdd??>
							<img id="img_emp" title="添加员工" alt="添加员工" style="cursor:pointer" src="/template/shop/images/add_bug.gif">
							<span id="span_emp"></span>
							<input type="hidden" id="input_emp" name="admin.id" value="" class="col-xs-10 col-sm-5 formText {required: true}" />
							<label class="requireField">*</label>
						<#else>
							<span id="span_emp">${(admin.name)! }</span>
						</#if>
					</div>
				</div>
			</div>
			<div class="buttonArea">
				<input type="button" class="formButton" id="btn_submit" value="确  定" hidefocus="true" />
				<input type="button" class="formButton" id="btn_return" value="返  回" hidefocus="true" />
			</div>
		</form>
	<!-- add by welson 0728 -->	
	</div><!-- /.col -->
	</div><!-- /.row -->
	<!-- PAGE CONTENT ENDS -->
	</div><!-- /.page-content-area -->
	<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div><!-- /.page-content -->
	</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->
</body>
</html>