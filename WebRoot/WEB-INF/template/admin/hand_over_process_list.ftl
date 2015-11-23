<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>工序交接</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
		<script src="${base}/template/admin/assets/js/ace-extra.min.js"></script>		
		<script type="text/javascript"src="${base}/template/admin/js/unusual/js/quality.js"></script>

<style type="text/css">
	.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
}
</style>
	</head>
<body class="no-skin list">
	
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
			<li class="active">工序交接单&nbsp;<span class="pageInfo"></span></li>
		</ul><!-- /.breadcrumb -->
	</div>

	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								
								<div class="widget-box">
									<div class="widget-header widget-header-blue widget-header-flat">
										
										<div class="widget-toolbar">
										
										</div>
									</div>

									<div class="widget-body">
										<div class="widget-main">
											<!-- #section:plugins/fuelux.wizard -->
											<div id="fuelux-wizard" data-target="#step-container">
												<!-- #section:plugins/fuelux.wizard.steps -->
												<ul class="wizard-steps">
													<li data-target="#step1" class="step-jump">
														<span class="step">1</span>
														<span class="title">裁切</span>
													</li>

													<li data-target="#step2" class="step-jump">
														<span class="step">2</span>
														<span class="title">接角</span>
													</li>

													<li data-target="#step3" class="step-jump">
														<span class="step">3</span>
														<span class="title">植絨</span>
													</li>

													<li data-target="#step4" class="step-jump">
														<span class="step">4</span>
														<span class="title">清洗</span>
													</li>
												</ul>

												<!-- /section:plugins/fuelux.wizard.steps -->
											</div>

											<hr />

											<div class="step-content pos-rel" id="step-container">
												<div class="step-pane active" id="step1">
													11 测试1
												</div>

												<div class="step-pane" id="step2">
													12测试2
												</div>

												<div class="step-pane" id="step3">
													13测试3
												</div>

												<div class="step-pane" id="step4">
													14测试4
												</div>
											</div>

											<!-- /section:plugins/fuelux.wizard.container -->
											

											<!-- /section:plugins/fuelux.wizard -->
										</div><!-- /.widget-main -->
									</div><!-- /.widget-body -->
								</div>
								
								
									
								<script type="text/javascript">
									var $path_base = "${base}/template/admin";//in Ace demo this will be used for editurl parameter
								</script>

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

	<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div><!-- /.page-content -->
	</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->

</body>

	<script src="${base}/template/admin/assets/js/fuelux/fuelux.wizard.min.js"></script>
	<script src="${base}/template/admin/assets/js/jquery.validate.min.js"></script>
		<script src="${base}/template/admin/assets/js/additional-methods.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.maskedinput.min.js"></script>
		<script src="${base}/template/admin/assets/js/select2.min.js"></script>

        <script type="text/javascript" src="${base}/template/admin/js/manage/handovercontrol.js"></script>

</html>
<script type="text/javascript">
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */
	
	$(function(){
		var ishead=0;
		$("#ace-settings-btn").click(function(){
			if(ishead==0){
				ishead=1;
				$("#ace-settings-box").addClass("open");
			}else{
				ishead=0;
				$("#ace-settings-box").removeClass("open");
			}
		});
		$(".btn-colorpicker").click(function(){
				$(".dropdown-colorpicker").addClass("open");
		})
		
		var ishead2=0;
		$(".light-blue").click(function(){
			if(ishead2==0){
				ishead2=1;
				$(this).addClass("open");
			}else{
				ishead2=0;
				$(this).removeClass("open");
			}
			
		})


	})
	
	
</script>