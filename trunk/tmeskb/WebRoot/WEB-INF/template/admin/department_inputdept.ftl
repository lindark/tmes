<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>部门管理 - Powered By ${systemConfig.systemName}</title>
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
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/department_inputdept.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
<style type="text/css">
	.mymust{color: red;font-size: 10px;}
	.class_label_xfuname{width:200px;line-height: 30px;border:1px solid;border-color: #d5d5d5;}
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
			<li class="active"><#if isAdd??>添加部门<#else>修改部门</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	<!-- add by welson 0728 -->
	<div class="page-content">
		<div class="page-content-area">					
			<div class="row">
				<div class="col-xs-12">
					<!-- ./ add by welson 0728 -->
					<form id="inputForm" class="validate" action="<#if isadd??>department!savedept.action<#else>department!updatedept.action</#if>" method="post">
						<input type="hidden" name="loginid" value="<@sec.authentication property='principal.id' />" />
						<input id="deptid" type="hidden" name="department.id" value="${(department.id)!}" />
						<div class="profile-user-info profile-user-info-striped">
							<div class="profile-info-row">
								<div class="profile-info-name">部门编码</div>
								<div class="profile-info-value">
									<input id="input_deptcode" type="text" name="department.deptCode" class=" input input-sm formText {required: true}" value="${(department.deptCode)!}" />
									<span id="span_code" style="color:red;font-family: 微软雅黑;font-size:10px;"></span>
									<label class="requireField">*</label>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">部门名称</div>
								<div class="profile-info-value">
									<input type="text" name="department.deptName" class=" input input-sm formText {required: true}" value="${(department.deptName)!}" />
									<label class="requireField">*</label>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">上级部门</div>
								<div class="profile-info-value">
									<img id="img_adddept" title="上级部门" alt="上级部门" style="cursor:pointer" src="/template/shop/images/add_bug.gif">
									<span id="span_dept">${(department.parentDept.deptName)! }</span>
									<input type="hidden" id="input_dept" name="department.parentDept.id" value="${(department.parentDept.id)! }" class="col-xs-10 col-sm-5" />
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">成本中心</div>
								<div class="profile-info-value">
									<input type="text" name="department.costcenter" class=" input input-sm" value="${(department.costcenter)!}" />
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">发料移动类型</div>
								<div class="profile-info-value">
									<input type="text" name="department.movetype" class=" input input-sm" value="${(department.movetype)!}" />
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">退料移动类型</div>
								<div class="profile-info-value">
									<input type="text" name="department.movetype1" class=" input input-sm" value="${(department.movetype1)!}" />
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">部门负责人</div>
								<div class="profile-info-value">
									<img id="img_addleader" title="添加负责人" alt="添加负责人" style="cursor:pointer" src="/template/shop/images/add_bug.gif">
									<span id="span_leader">${(department.deptLeader.name)! }</span>
									<input type="hidden" id="input_leader" name="department.deptLeader.id" value="${(department.deptLeader.id)! }" class="col-xs-10 col-sm-5" />
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">是否启用</div>
								<div class="profile-info-value">
									<label class="pull-left inline"> <small
										class="muted smaller-90">已启用:</small> <input type="radio"
										name="department.isWork" class="ace" value="Y" <#if
										(department.isWork == "Y")!> checked</#if> /> <span
										class="lbl middle"></span> </label> <label class="pull-left inline">

										<small class="muted smaller-90">未启用:</small> <input
										type="radio" name="department.isWork" class="ace"
										value="N" <#if (isAdd || department.isWork
										== "N")!> checked</#if> /> <span class="lbl middle"></span>
									</label>
								</div>
							</div>
						</div>
						<div class="buttonArea">
							<input type="button" class="formButton" id="btn_submit" value="确  定" hidefocus="true" />
							<input type="button" class="formButton" id="btn_return" value="返  回" />
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
