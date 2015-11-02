<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<title>管理登录 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">

</head>
<body class="login-layout">

<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class="ace-icon fa fa-leaf green"></i>
									<span class="red">${systemConfig.systemDescription}</span>
									<span class="white" id="id-text2">${systemConfig.systemVersion}</span>
								</h1>
								<h4 class="blue" id="id-company-text">&copy; ${systemConfig.systemName}</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												请输入您的登录信息
											</h4>

											<div class="space-6"></div>

											<form id="loginForm" class="form" action="${base}/admin/loginVerify" method="post">
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" id="username" name="j_username" class="form-control" placeholder="会员编号" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" id="password" name="j_password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>
													
													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="text" id="captcha" name="j_captcha" class="form-control" placeholder="验证码" />
														</label>
														<div class="width-35 pull-right">
														<img id="captchaImage" src="${base}/captcha.jpg" alt="换一张" />
														</div>
													</div>
													
													

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" id="isSaveUsername" class="ace" />
															<span class="lbl"> 记住登陆信息</span>
														</label>

														<button type="submit" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">Login</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>

											
										</div><!-- /.widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="${base}/" class="forgot-password-link">
													<i class="ace-icon fa fa-arrow-left"></i>
													返回首页
												</a>
											</div>

											
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								
							</div><!-- /.position-relative -->

							<div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">Dark</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">Blur</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">Light</a>
								&nbsp; &nbsp; &nbsp;
							</div>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->
		
		
	
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${base}/template/admin/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
			 $(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			 });
			});
			
			
			
			//you don't need this, just used for changing background
			jQuery(function($) {
			 $('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');
				
				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');
				
				e.preventDefault();
			 });
			  $('#btn-login-light').trigger('click');
			});
		</script>
		
		
<script type="text/javascript">

// 登录页面若在框架内，则跳出框架
if (self != top) {
	top.location = self.location;
};

jQuery(function() {
	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $captchaImage = $("#captchaImage");
	var $isSaveUsername = $("#isSaveUsername");
   
	// 判断"记住会员编号"功能是否默认选中,并自动填充登录会员编号
	if(jQuery.cookie("adminUsername") != null) {
		$isSaveUsername.attr("checked", true);
		$username.val(jQuery.cookie("adminUsername"));
		$password.focus();
	} else {
		$isSaveUsername.attr("checked", false);
		$username.focus();
	}

	// 提交表单验证,记住登录会员编号
	$("#loginForm").submit( function() {
		if ($username.val() == "") {
			alert("请输入您的会员编号!");
			return false;
		}
		if ($password.val() == "") {
			alert("请输入您的密码!");
			return false;
		}
		if ($captcha.val() == "") {
			alert("请输入您的验证码!");
			return false;
		}
		
		if($isSaveUsername.attr("checked") == true) {
			jQuery.cookie("adminUsername", $username.val(), {expires: 30});
		} else {
			jQuery.cookie("adminUsername", null);
		}
		
	});

	// 刷新验证码
	$captchaImage.click( function() {
		var timestamp = (new Date()).valueOf();
		var imageSrc = $captchaImage.attr("src");
		if(imageSrc.indexOf("?") >= 0) {
			imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
		}
		imageSrc = imageSrc + "?timestamp=" + timestamp;
		$captchaImage.attr("src", imageSrc);
	});

	<#if (actionErrors?size > 0)>
		alert("<#list errorMessages as list>${list}\n</#list>");
	</#if>

});
</script>
</body>
</html>