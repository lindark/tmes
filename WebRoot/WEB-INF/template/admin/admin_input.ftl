<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<title>添加/编辑操作员 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">

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
			<li class="active"><#if isAdd??>添加操作员<#else>编辑操作员</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
	
	
		<form id="inputForm" class="validate" action="<#if isAdd??>admin!save.action<#else>admin!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div class="blank1"></div>
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">基本信息</a>
				</li>
				<li>
					<a href="#tabs-2">个人资料</a>
				</li>
				
			</ul>
			
			<table id="tabs-1" class="inputTable tabContent" >
				<tr>
					<th>
						登录名:
					</th>
					<td>
						<#if isAdd??>
							<input type="text" name="admin.username" class="formText {required: true, username: true, remote: 'admin!checkUsername.action', minlength: 2, maxlength: 20, messages: {remote: '会员编号已存在,请重新输入!'}}" title="会员编号只允许包含中文、英文、数字和下划线" />
							<label class="requireField">*</label>
						<#else>
							${(admin.username)!}
							<input type="hidden" name="admin.username" value="${(admin.username)!}" />
						</#if>
					</td>
				</tr>
				<tr>
					<th>
						密 码:
					</th>
					<td>
						<input type="password" name="admin.password" id="password" <#if isAdd??>class="formText {required: true, minlength: 4, maxlength: 20}"<#else>class="formText {minlength: 4, maxlength: 20}"</#if> title="密码长度只允许在4-20之间" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						重复密码:
					</th>
					<td>
						<input type="password" name="rePassword" class="formText {equalTo: '#password', messages: {equalTo: '两次密码输入不一致!'}}" />
						<#if isAdd??><label class="requireField">*</label></#if>
					</td>
				</tr>
				<tr>
					<th>
						E-mail:
					</th>
					<td>
						<input type="text" name="admin.email" class="formText {required: true, email: true}" value="${(admin.email)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						管理角色:
					</th>
					<td>
						<#list allRole as list>
							<label>
								<input type="checkbox" name="roleList.id" class="{required: true, messages: {required: '请至少选择一个角色!'}, messagePosition: '#roleMessagePosition'}" value="${list.id}" <#if (admin.roleSet.contains(list) == true)!> checked="checked"</#if> />
								${(list.name)!}
							</label>
						</#list>
						<span id="roleMessagePosition"></span>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						是否启用:
					</th>
					<td>
					<label class="pull-left inline">
					    <small class="muted smaller-90">是:</small>
						<input type="radio" name="admin.isAccountEnabled" class="ace" value="true"<#if (admin.isAccountEnabled == true)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">否:</small>
						<input type="radio" name="admin.isAccountEnabled" class="ace" value="false"<#if (isAdd || admin.isAccountEnabled == false)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
					
				</td>
				</tr>
				<#if isEdit??>
					<tr>
						<th>&nbsp;</th>
						<td>
							<span class="warnInfo"><span class="icon">&nbsp;</span>如果要修改密码,请填写密码,若留空,密码将保持不变!</span>
						</td>
					</tr>
				</#if>
			</table>
			<table id="tabs-2" class="inputTable tabContent">
				<tr>
					<th>
						部门:
					</th>
					<td>
						<input type="text" name="admin.department" class="formText" value="${(admin.department)!}" />
					</td>
				</tr>
				<tr>
					<th>
						姓名:
					</th>
					<td>
						<input type="text" name="admin.name" class="formText" value="${(admin.name)!}" />
					</td>
				</tr>
			</table>
			</div>
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
				
		
				</div>
		
	</div>

	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	
	<!-- ./ add by welson 0728 -->

	
</body>
</html>