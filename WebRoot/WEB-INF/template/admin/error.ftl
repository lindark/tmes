<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>提示信息 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/message.css" rel="stylesheet" type="text/css" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body{background:#fff;}
</style>
</head>
<body class="no-skin message">
	
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
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
						
						
									<div class="error-container widget-box widget-color-red ui-sortable-handle " style="margin-left:30%;margin-right:30%;margin-top:15%;">
											<div class="widget-header">
												<h5 class="widget-title">错误信息</h5>

												<div class="widget-toolbar">
													<a href="#" data-action="collapse">
														<i class="1 ace-icon fa bigger-125 fa-chevron-up"></i>
													</a>
												</div>

												<div class="widget-toolbar no-border">
													

													
												</div>
											</div>

											<div class="widget-body" style="display: block;">
												<div class="widget-main">
													
													<p class="alert alert-danger">
														<#if (errorMessages?size > 0)!>
															${errorMessages} 1
															<#list errorMessages as list>${list}<br></#list>
														<#elseif (actionMessages?size > 0)!>
															${actionMessages} 2
															<#list actionMessages as list>${list}<br></#list>
														<#elseif (fieldErrors?size > 0)!>
															<#list (fieldErrors?values)! as value>
																${value}
															</#list>
														<#else>
															亲，您的操作出现错误了~
														</#if>
													</p>
												</div>

												<div class="widget-toolbox padding-8 clearfix" style="width:100%;text-align:center;">
													
											<button class="btn btn-lg btn-danger" <#if redirectionUrl??>onclick="window.location.href='${redirectionUrl}'"<#else>onclick="window.history.back(); return false;"</#if> >
												<i class="ace-icon fa fa-check"></i>
												我知道了
											</button>
											
												</div>
											</div>
										</div>
												
						

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