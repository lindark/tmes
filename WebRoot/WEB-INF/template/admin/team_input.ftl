<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑班组管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/team_input.js"></script>

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
			<li class="active"><#if isAdd??>添加班组记录<#else>编辑班组记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
			<form id="inputForm" class="validate" action="<#if isAdd??>team!save.action<#else>team!update.action</#if>" method="post">
				<input type="hidden" name="id" value="${id}" />
				<input id="xfuid" type="hidden" name="team.factoryUnit.id" value="${(team.xfactoryUnitId)! }" />
				<input id="xcode" type="hidden" value="${(team.teamCode)!}" />
				<div id="inputtabs">
					<ul>
						<li>
							<a href="#tabs-1">班组信息</a>
						</li>
					</ul>
			
					<div id="tabs-1">
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
							<div class="profile-info-row">
								<div class="profile-info-name"> 班组编码 </div>					
								<div class="profile-info-value">
									<input id="team_code" type="text" name="team.teamCode" value="${(team.teamCode)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
									<label class="requireField">*</label>&nbsp;&nbsp;<span id="span_tip_tcode" class="mymust"></span>
								</div>
							</div>
							
							<div class="profile-info-row">
								<div class="profile-info-name"> 班组名称 </div>					
								<div class="profile-info-value">
									<input id="team_name" type="text" name="team.teamName" value="${(team.teamName)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
									<label class="requireField">*</label>&nbsp;&nbsp;<span id="span_tip_tname" class="mymust"></span>
								</div>
							</div>
							
							<div class="profile-info-row">
								<div class="profile-info-name"> 单元名称 </div>					
								<div class="profile-info-value">
									<label id="label_xfuname" class="class_label_xfuname">${(team.xfactoryUnitName)!}&nbsp;</label>
									<label class="requireField">*</label>&nbsp;&nbsp;<span id="span_tip_xfuname" class="mymust"></span>
								</div>
							</div>
									
							<div class="profile-info-row">
								<div class="profile-info-name"> 状态</div>					
								<div class="profile-info-value">
									<label class="pull-left inline">
					                	<small class="muted smaller-90">已启用:</small>
						            	<input type="radio" class="ace" name="team.state" value="1"<#if (team.state == '1')!> checked</#if> />
						            	<span class="lbl middle"></span>
						                 &nbsp;&nbsp;
					                </label>						
					                <label class="pull-left inline">
					                	<small class="muted smaller-90">未启用:</small>
						            	<input type="radio" class="ace" name="team.state" value="2"<#if (isAdd || team.state == '2')!> checked</#if>  />
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