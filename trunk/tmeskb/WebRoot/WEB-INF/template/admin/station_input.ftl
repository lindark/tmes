<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>工位管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/station_input.js"></script>
<style type="text/css">
	.mymust{color: red;font-family: 微软雅黑;font-size: 10px;}
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
			<li class="active"><#if isAdd??>添加工位<#else>修改工位</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
			<form id="inputForm" class="validate" action="<#if isAdd??>station!save.action<#else>station!update.action</#if>" method="post">
				<input id="stationid" type="hidden" name="station.id" value="${(station.id)!}" />
				<div id="inputtabs">
					<ul>
						<li>
							<a href="#tabs-1">工位信息</a>
						</li>
					</ul>
					<div id="tabs-1">
					<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
							<div class="profile-info-row">
								<div class="profile-info-name">工位编码</div>					
								<div class="profile-info-value">
									<input id="input_code" type="text" name="station.code" value="${(station.code)!}" class=" input input-sm  formText {required: true}" />
									<span id="span_code" class="mymust"></span>
									<label class="requireField">*</label>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">工位名称</div>					
								<div class="profile-info-value">
									<input id="input_name" type="text" name="station.name" value="${(station.name)!}" class=" input input-sm  formText {required: true}" />
									<label class="requireField">*</label>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">岗位编码/名称</div>					
								<div class="profile-info-value">
									<select class="chosen-select" name="station.posts.id" class="{required: true}" style="width:200px;">
										<#list list_post as list>
										<option value="${(list.id)!}" <#if (list.id == station.posts.id)!> selected</#if>>${(list.postCode)!}--${(list.postName)!}</option>
										</#list>
									</select>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name">是否启用</div>					
								<div class="profile-info-value">
									<label class="pull-left inline">
					                	<small class="muted smaller-90">已启用</small>
						            	<input type="radio" class="ace" name="station.isWork" value="Y"<#if (isAdd||station.isWork == 'Y')!> checked</#if> />
						            	<span class="lbl middle"></span>
					                </label>						
					                <label class="pull-left inline">
					                	<small class="muted smaller-90">未启用</small>
						            	<input type="radio" class="ace" name="station.isWork" value="N"<#if (station.isWork == 'N')!> checked</#if>  />
						            	<span class="lbl middle"></span>
					                </label>		
								</div>	
							</div>
						</div>
						<div class="buttonArea">
							<input id="btn_sub" type="button" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
						</div>
					</div>
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