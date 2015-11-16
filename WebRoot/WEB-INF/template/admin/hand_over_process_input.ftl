<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>交接列表 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"src="${base}/template/admin/js/unusual/js/quality.js"></script>

		<!-- page specific plugin scripts -->
		<script src="${base}/template/admin/assets/js/ace-extra.min.js"></script>
		

<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body{background:#fff;}
.deleteImage, #addImage, #removeImage {
	cursor: pointer;
}
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
			<li class="active">交接列表</li>
		</ul><!-- /.breadcrumb -->
	</div> 
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="javascript:void(0);" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">工序交接</a>
				</li>
				<li>
					<a href="#tabs-2">线边仓交接</a>
				</li>
				<li>
					<a href="#tabs-3">总体交接确认</a>
				</li>
			</ul>
			
			<div id="tabs-1">
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
													<li data-target="#step1" class="active">
														<span class="step">1</span>
														<span class="title">裁切</span>
													</li>

													<li data-target="#step2">
														<span class="step">2</span>
														<span class="title">接角</span>
													</li>

													<li data-target="#step3">
														<span class="step">3</span>
														<span class="title">植絨</span>
													</li>

													<li data-target="#step4">
														<span class="step">4</span>
														<span class="title">清洗</span>
													</li>
												</ul>

												<!-- /section:plugins/fuelux.wizard.steps -->
											</div>

											<hr />

											<div class="step-content pos-rel" id="step-container">
												<div class="step-pane active" id="step1">
													<h3 class="lighter block green">Enter the following information</h3>
													
													<form class="form-horizontal" id="sample-form">
														<!-- #section:elements.form.input-state -->
														<div class="form-group has-warning">
															<label for="inputWarning" class="col-xs-12 col-sm-3 control-label no-padding-right">Input with warning</label>

															<div class="col-xs-12 col-sm-5">
																<span class="block input-icon input-icon-right">
																	<input type="text" id="inputWarning" class="width-100" />
																	<i class="ace-icon fa fa-leaf"></i>
																</span>
															</div>
															<div class="help-block col-xs-12 col-sm-reset inline"> Warning tip help! </div>
														</div>

														<!-- /section:elements.form.input-state -->
														<div class="form-group has-error">
															<label for="inputError" class="col-xs-12 col-sm-3 col-md-3 control-label no-padding-right">Input with error</label>

															<div class="col-xs-12 col-sm-5">
																<span class="block input-icon input-icon-right">
																	<input type="text" id="inputError" class="width-100" />
																	<i class="ace-icon fa fa-times-circle"></i>
																</span>
															</div>
															<div class="help-block col-xs-12 col-sm-reset inline"> Error tip help! </div>
														</div>

														<div class="form-group has-success">
															<label for="inputSuccess" class="col-xs-12 col-sm-3 control-label no-padding-right">Input with success</label>

															<div class="col-xs-12 col-sm-5">
																<span class="block input-icon input-icon-right">
																	<input type="text" id="inputSuccess" class="width-100" />
																	<i class="ace-icon fa fa-check-circle"></i>
																</span>
															</div>
															<div class="help-block col-xs-12 col-sm-reset inline"> Success tip help! </div>
														</div>

														<div class="form-group has-info">
															<label for="inputInfo" class="col-xs-12 col-sm-3 control-label no-padding-right">Input with info</label>

															<div class="col-xs-12 col-sm-5">
																<span class="block input-icon input-icon-right">
																	<input type="text" id="inputInfo" class="width-100" />
																	<i class="ace-icon fa fa-info-circle"></i>
																</span>
															</div>
															<div class="help-block col-xs-12 col-sm-reset inline"> Info tip help! </div>
														</div>

														<div class="form-group">
															<label for="inputError2" class="col-xs-12 col-sm-3 control-label no-padding-right">Input with error</label>

															<div class="col-xs-12 col-sm-5">
																<span class="input-icon block">
																	<input type="text" id="inputError2" class="width-100" />
																	<i class="ace-icon fa fa-times-circle red"></i>
																</span>
															</div>
															<div class="help-block col-xs-12 col-sm-reset inline"> Error tip help! </div>
														</div>
													</form>

													<form class="form-horizontal hide" id="validation-form" method="get">
														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="email">Email Address:</label>

															<div class="col-xs-12 col-sm-9">
																<div class="clearfix">
																	<input type="email" name="email" id="email" class="col-xs-12 col-sm-6" />
																</div>
															</div>
														</div>

														<div class="space-2"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="password">Password:</label>

															<div class="col-xs-12 col-sm-9">
																<div class="clearfix">
																	<input type="password" name="password" id="password" class="col-xs-12 col-sm-4" />
																</div>
															</div>
														</div>

														<div class="space-2"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="password2">Confirm Password:</label>

															<div class="col-xs-12 col-sm-9">
																<div class="clearfix">
																	<input type="password" name="password2" id="password2" class="col-xs-12 col-sm-4" />
																</div>
															</div>
														</div>

														<div class="hr hr-dotted"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="name">Company Name:</label>

															<div class="col-xs-12 col-sm-9">
																<div class="clearfix">
																	<input type="text" id="name" name="name" class="col-xs-12 col-sm-5" />
																</div>
															</div>
														</div>

														<div class="space-2"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="phone">Phone Number:</label>

															<div class="col-xs-12 col-sm-9">
																<div class="input-group">
																	<span class="input-group-addon">
																		<i class="ace-icon fa fa-phone"></i>
																	</span>

																	<input type="tel" id="phone" name="phone" />
																</div>
															</div>
														</div>

														<div class="space-2"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="url">Company URL:</label>

															<div class="col-xs-12 col-sm-9">
																<div class="clearfix">
																	<input type="url" id="url" name="url" class="col-xs-12 col-sm-8" />
																</div>
															</div>
														</div>

														<div class="hr hr-dotted"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right">Subscribe to</label>

															<div class="col-xs-12 col-sm-9">
																<div>
																	<label>
																		<input name="subscription" value="1" type="checkbox" class="ace" />
																		<span class="lbl"> Latest news and announcements</span>
																	</label>
																</div>

																<div>
																	<label>
																		<input name="subscription" value="2" type="checkbox" class="ace" />
																		<span class="lbl"> Product offers and discounts</span>
																	</label>
																</div>
															</div>
														</div>

														<div class="space-2"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right">Gender</label>

															<div class="col-xs-12 col-sm-9">
																<div>
																	<label class="line-height-1 blue">
																		<input name="gender" value="1" type="radio" class="ace" />
																		<span class="lbl"> Male</span>
																	</label>
																</div>

																<div>
																	<label class="line-height-1 blue">
																		<input name="gender" value="2" type="radio" class="ace" />
																		<span class="lbl"> Female</span>
																	</label>
																</div>
															</div>
														</div>

														<div class="hr hr-dotted"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="state">State</label>

															<div class="col-xs-12 col-sm-9">
																<select id="state" name="state" class="select2" data-placeholder="Click to Choose...">
																	<option value="">&nbsp;</option>
																	<option value="AL">Alabama</option>
																	<option value="AK">Alaska</option>
																	<option value="AZ">Arizona</option>
																	<option value="AR">Arkansas</option>
																	<option value="CA">California</option>
																	<option value="CO">Colorado</option>
																	<option value="CT">Connecticut</option>
																	<option value="DE">Delaware</option>
																	<option value="FL">Florida</option>
																	<option value="GA">Georgia</option>
																	<option value="HI">Hawaii</option>
																	<option value="ID">Idaho</option>
																	<option value="IL">Illinois</option>
																	<option value="IN">Indiana</option>
																	<option value="IA">Iowa</option>
																	<option value="KS">Kansas</option>
																	<option value="KY">Kentucky</option>
																	<option value="LA">Louisiana</option>
																	<option value="ME">Maine</option>
																	<option value="MD">Maryland</option>
																	<option value="MA">Massachusetts</option>
																	<option value="MI">Michigan</option>
																	<option value="MN">Minnesota</option>
																	<option value="MS">Mississippi</option>
																	<option value="MO">Missouri</option>
																	<option value="MT">Montana</option>
																	<option value="NE">Nebraska</option>
																	<option value="NV">Nevada</option>
																	<option value="NH">New Hampshire</option>
																	<option value="NJ">New Jersey</option>
																	<option value="NM">New Mexico</option>
																	<option value="NY">New York</option>
																	<option value="NC">North Carolina</option>
																	<option value="ND">North Dakota</option>
																	<option value="OH">Ohio</option>
																	<option value="OK">Oklahoma</option>
																	<option value="OR">Oregon</option>
																	<option value="PA">Pennsylvania</option>
																	<option value="RI">Rhode Island</option>
																	<option value="SC">South Carolina</option>
																	<option value="SD">South Dakota</option>
																	<option value="TN">Tennessee</option>
																	<option value="TX">Texas</option>
																	<option value="UT">Utah</option>
																	<option value="VT">Vermont</option>
																	<option value="VA">Virginia</option>
																	<option value="WA">Washington</option>
																	<option value="WV">West Virginia</option>
																	<option value="WI">Wisconsin</option>
																	<option value="WY">Wyoming</option>
																</select>
															</div>
														</div>

														<div class="space-2"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="platform">Platform</label>

															<div class="col-xs-12 col-sm-9">
																<div class="clearfix">
																	<select class="input-medium" id="platform" name="platform">
																		<option value="">------------------</option>
																		<option value="linux">Linux</option>
																		<option value="windows">Windows</option>
																		<option value="mac">Mac OS</option>
																		<option value="ios">iOS</option>
																		<option value="android">Android</option>
																	</select>
																</div>
															</div>
														</div>

														<div class="space-2"></div>

														<div class="form-group">
															<label class="control-label col-xs-12 col-sm-3 no-padding-right" for="comment">Comment</label>

															<div class="col-xs-12 col-sm-9">
																<div class="clearfix">
																	<textarea class="input-xlarge" name="comment" id="comment"></textarea>
																</div>
															</div>
														</div>

														<div class="space-8"></div>

														<div class="form-group">
															<div class="col-xs-12 col-sm-4 col-sm-offset-3">
																<label>
																	<input name="agree" id="agree" type="checkbox" class="ace" />
																	<span class="lbl"> I accept the policy</span>
																</label>
															</div>
														</div>
													</form>
												</div>

												<div class="step-pane" id="step2">
													
												</div>

												<div class="step-pane" id="step3">
													
												</div>

												<div class="step-pane" id="step4">
													
												</div>
											</div>

											<!-- /section:plugins/fuelux.wizard.container -->
											<hr />
											<div class="wizard-actions">
												<!-- #section:plugins/fuelux.wizard.buttons -->
												<button class="btn btn-prev">
													<i class="ace-icon fa fa-arrow-left"></i>
													Prev
												</button>

												<button class="btn btn-success btn-next" data-last="Finish">
													Next
													<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
												</button>

												<!-- /section:plugins/fuelux.wizard.buttons -->
											</div>

											<!-- /section:plugins/fuelux.wizard -->
										</div><!-- /.widget-main -->
									</div><!-- /.widget-body -->
								</div>
			
			<table id="tabs-2" class="inputTable tabContent">
				
			 <div id="tabs-3">
			     	
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
    <!-- 
   <script src="${base }/template/admin/assets/js/fuelux/fuelux.wizard.min.js"></script>
    <script type="text/javascript" src="${base}/template/admin/js/manage/handovercontrol.js"></script>
	 -->
	  
	  
	    <script src="${base}/template/admin/assets/js/fuelux/fuelux.wizard.min.js"></script>
		<script src="${base}/template/admin/assets/js/additional-methods.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.maskedinput.min.js"></script>
		<script src="${base}/template/admin/assets/js/select2.min.js"></script>

        <script type="text/javascript" src="${base}/template/admin/js/manage/handovercontrol.js"></script>
         

		
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
		
		/*
		var ishead3=0;
		$(".hsub").click(function(){
			if(ishead3==0){
				alert("OK");
				ishead3=1;
				$(".hsub").addClass("open");
				//$(this).find(".submenu").removeClass("nav-hide");
			}else{
				ishead3=0;
				//$(this).removeClass("open");
				//$(this).find(".submenu").removeClass("nav-show").addClass("nav-hide").css("display","none");
			}
			
		})
		*/
	})
	
	
</script>

</body>
</html>