<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>编辑个人资料 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
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
			<li class="active">编辑个人资料</li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->

	

		<form id="inputForm" class="validate" action="admin_profile!update.action" method="post">
			<table class="inputTable">
				<tr>
					<th>
						登录名:
					</th>
					<td>
						${(admin.username)!}
					</td>
				</tr>
				<tr>
					<th>
						姓&nbsp;&nbsp;&nbsp;名:
					</th>
					<td>
						${(admin.name)!}
					</td>
				</tr>
				<tr>
					<th>
						部&nbsp;&nbsp;&nbsp;门:
					</th>
					<td>
						${(admin.department)!}
					</td>
				</tr>
				<tr>
					<th>
						当前密码:
					</th>
					<td>
						<input type="password" id="currentPassword" name="currentPassword" class="formText {remote: 'admin_profile!checkCurrentPassword.action', messages:{remote: '当前密码错误,请重新输入!'}}" />
					</td>
				</tr>
				<tr>
					<th>
						新密码:
					</th>
					<td>
						<input type="password" id="password" name="admin.password" class="formText {requiredTo: '#currentPassword', minlength: 4, maxlength: 20, messages:{requiredTo: '请输入新密码!'}}" title="密码长度只允许在4-20之间" />
					</td>
				</tr>
				<tr>
					<th>
						确认新密码:
					</th>
					<td>
						<input type="password" name="rePassword" class="formText {equalTo: '#password', messages:{equalTo: '两次密码输入不一致!'}}" />
					</td>
				</tr>
				<tr>
					<th>
						E-mail:
					</th>
					<td>
						<input type="text" name="admin.email" class="formText {email: true, required: true, messages:{required: '请输入E-mail!'}}" value="${(admin.email)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						&nbsp;
					</th>
					<td>
						<span class="warnInfo"><span class="icon">&nbsp;</span>系统提示：如果要修改密码，请先填写当前密码，如留空，则密码保持不变</span>
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