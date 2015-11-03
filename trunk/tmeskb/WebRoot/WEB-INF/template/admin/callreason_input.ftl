<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>呼叫原因 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
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
			<li class="active"><#if isAdd??>添加呼叫原因<#else>编辑呼叫原因</#if></li>
		</ul><!-- /.breadcrumb -->
	</div> 
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>callreason!save.action<#else>callreason!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">呼叫原因</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name"> 呼叫类型</div>
					
										<div class="profile-info-value">
											<input type="text" name="callreason.callType" value="${(callreason.callType)!}" class=" input input-sm  formText {required: true}" />
										</div>
										<div class="profile-info-name"> 呼叫原因 </div>
					
										<div class="profile-info-value">
											<input type="text" name="callreason.callReason" value="${(callreason.callReason)!}" class=" input input-sm  formText {required: true}" />
										</div>
										<div class="profile-info-name"> 状态 </div>
					
										<div class="profile-info-value">
											<label class="pull-left inline">
											    <small class="muted smaller-90">已确认:</small>
												<input type="radio" class="ace" name="callreason.state" value="已确认"<#if (callreason.state == '已确认')!> checked</#if> />
												<span class="lbl middle"></span>
												&nbsp;&nbsp;
											</label>
											
											<label class="pull-left inline">

											    <small class="muted smaller-90">未确认:</small>
												<input type="radio" class="ace" name="callreason.state" value="未确认"<#if (isAdd || callreason.state == '未确认')!> checked</#if>  />
												<span class="lbl middle"></span>
											</label>
										</div>
										
									</div>
						</div>
				<!--weitao end modify-->	
				
			
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
				</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->

</body>
</html>