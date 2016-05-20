
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>管理登录 - Powered By ${systemConfig.systemName}</title>
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/ingageapp/css/jquery-ui.css"/>
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/ingageapp/css/base.css"/>
<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/ingageapp/css/register.css"/>

<script type="text/javascript">
    pageContextPath = '';
    resJsPath = '/js';
    resCssPath = '/css';
    resImgPath = '/img';
    NO_PERMISSION_ERROR = "401";
    DATA_VALIDATE_ERROR = "402";
    SYSTEM_ERROR = "501";
</script>

</head>
<body>
<div class="wrapper" >
	<!--left img-->
	<div class="left_img"></div>
	<!--right content -->
	<div class="right_content" id="div_main">
		<!--main-->
		<div class="main">
			<div class="register">
				<div class="logo"></div>
				<div id="loginDiv">
					<h1>登录</h1>
					<label class="error_info" style="display: none;margin: 0px;"></label>
					<form action="${base}/admin/loginVerify" method="post" name="login_form" id="login_form">
						<ul class="register_form">
							<li class="clear">
								<input class="input_code" tabindex="1" id="username"  name="j_username" placeholder="请输入登录名"/>
							</li>
							<li class="clear">
								<input id="password" type="password" class="input_code" tabindex="2"
								name="j_password" placeholder="请输入登录密码"/>
							</li>
							
						</ul>
						<!-- 
						<ul class="verification_form register_form" style=" margin-top:0; padding-top:0; border-top:0;">
							<li  class="clear">
								<div class="sucess_info">
									<input class="input_code" type="text" maxlength="4" checkType="verify" id="captcha" name="j_captcha" placeholder="请输入四位识别码"/>
									<input type="hidden" name="sessionID" class="input_sessionId"/>
								</div>
								<span class="verification_img">
									<div style="text-align:left;"> <img id="captchaImage" style="width:95px;height:45px;" src="${base}/captcha.jpg" alt="换一张"/></div>
								</span>
								<span class="dimness">看不清</span>
								<a href="javascript:;" class="change" id="change">换一张</a>
							</li>
						</ul>
						 -->
					</form>
					<div class="register_submit clear">
						<a id="btn_login" href="javascript:;" title="登录" class="button_blue ">登录</a>&nbsp;
						<a id="btn_creditlogin" href="javascript:;" title="刷卡登陆" class="button_blue " style="margin:auto 5px;">刷卡登陆</a>
						<a href="javascript:;" title="登录" class="button_blue button_disable button_waiting" style="display: none">登录中<span></span></a>
						<!--
						<span class="register_login">
							<a href="/global/register.action" title="免费注册">免费注册</a>
							<span style="margin:0 15px;">|</span>
							<a href="/global/password-forget.action" title="忘记密码？">忘记密码？</a>
						</span>
						-->
					</div>
					<!--
					<div class="mobile_download">
						<span>手机版客户端：</span>
						<span><a class="ios_download" onclick="iOSDownload();" href="javascript:;">苹果版客户端下载</a></span>
						<span><a class="android_download" onclick="androidDownload();" href="javascript:;">安卓版客户端下载</a></span>
					</div>
					-->
				</div>
			</div>
		</div>
	</div>
	<!--right content end -->
</div>
<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='${base}/template/admin/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
<script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js/core/platform.js">
</script><script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js/core/jquery.js"></script>
<script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js/core/jquery-ui.js"></script>
<script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js/core/jquery-migrate.js"></script>
<script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js/core/jquery.placeholder.js"></script>
<script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js/common/jquery.jmpopups-0.5.1.rk.js">
</script><script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js/common/jquery.form-3.45.min.js">
</script><script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js/page/login/login.js">
</script><script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js//common/jquery.watermark.js">
</script><script type="text/javascript" src="${base}/template/admin/jiuyi/ingageapp/js//common/jquery.json-2.2.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.timers-1.1.2.js"></script>
<script src="${base}/template/admin/js/layer/layer.js"></script>
</script><script type="text/javascript">

// 登录页面若在框架内，则跳出框架
if (self != top) {
	top.location = self.location;
};

$(function(){
	var $change = $("#change"); //获取图片的jquery对象
	var $username = $("#username");
	var $password = $("#password");
	var $captcha = $("#captcha");
	var $isSaveUsername = $("#isSaveUsername");
	var $btn_creditlogin = $("#btn_creditlogin");//刷卡登录
	
	
	
	document.onkeydown=function(event){
            var e = event || window.event || arguments.callee.caller.arguments[0];
                      
             if(e && e.keyCode==13){ // enter 键
                 //要做的事情
                 $("#btn_login").trigger("click");
            }
        }; 
	
	
	/*
	// 判断"记住客户编号"功能是否默认选中,并自动填充登录客户编号
	if(jQuery.cookie("adminUsername") != null) {
		$isSaveUsername.attr("checked", true);
		$username.val(jQuery.cookie("adminUsername"));
		$password.focus();
	} else {
		$isSaveUsername.attr("checked", false);
		$username.focus();
	}
	*/
	
	
	$("#btn_login").click(function(){
		if ($username.val() == "") {
			alert("请输入您的客户编号!");
			return false;
		}
		if ($password.val() == "") {
			alert("请输入您的密码!");
			return false;
		}
		/*
		if ($captcha.val() == "") {
			alert("请输入您的验证码!");
			return false;
		}
		*/
		/*
		if($isSaveUsername.attr("checked") == true) {
			jQuery.cookie("adminUsername", $username.val(), {expires: 30});
		} else {
			jQuery.cookie("adminUsername", null);
		}
		*/
		
		$("#login_form").submit();
	});
	
	
	
	
	
	// 刷新验证码
	$change.click( function() {
		var $captchaImage = $("#captchaImage");
		var timestamp = (new Date()).valueOf();
		var imageSrc = $captchaImage.attr("src");
		if(imageSrc.indexOf("?") >= 0) {
			imageSrc = imageSrc.substring(0, imageSrc.indexOf("?"));
		}
		imageSrc = imageSrc + "?timestamp=" + timestamp;
		$captchaImage.attr("src", imageSrc);
	});
	
	//刷卡登录
	$btn_creditlogin.click(function(){
		var url="admin!creditlogin.action";
		credit.creditCard(url,function(data){
			var username = data.username;
			var cardnumber = credit.cardnumber;
			window.location.href="${base}/admin/loginVerify?j_card=true&j_username="+username+"&j_password=shuaka-"+cardnumber;
		})
	});
	
});

<#if (actionErrors?size > 0)>
		alert("<#list errorMessages as list>${list}\n</#list>");
	</#if>

	
	var credit = {
			"creditCard":function(url,callback,data){
				if(typeof(data) == "undefined")
					data = "";
				var bool = url.indexOf("?");
				var cardnubmer="";
				credit.index= layer.msg('请刷卡', {icon: 16,time:20000,shade:0.3,shadeClose:true},function(){
					
					if(typeof(credit.cardnumber) == "undefined"){
						$('body').stopTime ();
						return false;
					}
					var index1;
					if(bool >0)
						cardnumber = "&cardnumber="+credit.cardnumber;
					else
						cardnumber = "?cardnumber="+credit.cardnumber;
					$.ajax({	
						url: url+cardnumber,
						data:data,
						dataType: "json",
						async: false,
						beforeSend: function(data) {
							index1 = layer.load();
						},
						success: function(data) {
							layer.close(index1);
							$.message(data.status,data.message);
							callback(data);
						},error:function(data){
							layer.close(index1);
							$.message("error","您当前无权限!");
						}
					});
				});
				//var systemDate = "${.now?string('yyyy-MM-dd HH:mm:ss')}";//服务器时间
				
				$.post("base_admin!getSystemDate.action",function(data){
					var systemDate = data.systemDate;
					//credit.everyTime(systemDate);
					$.post("credit_card!getCredit.action", { createDate: systemDate},function(data){
						if(data.status == "no"){//未找到
							//layer.close(index);
							layer.alert(data.cardnumber);
							layer.close(credit.index);
						}else if(data.status == "yes"){//已找到
							//$('body').stopTime ();
							credit.cardnumber = data.cardnumber;//获取卡号
							layer.close(credit.index);
						}
						
					},"json" );
				},"json" );
				
			},
			/*  "everyTime":function(sysdate){
				$('body').everyTime('10s','B',function(){//计划任务
					$.post("credit_card!getCredit.action", { createDate: sysdate},function(data){
						if(data.status == "no"){//未找到
							//layer.close(index);
						}else if(data.status == "yes"){//已找到
							$('body').stopTime ();
							credit.cardnumber = data.cardnumber;//获取卡号
							layer.close(credit.index);
						}
						
					},"json" );
				},0,true);
			}  */
	}
	
	$.message = function(status,message){
		if(status=="success"){
			layer.msg(message, {icon: 6}); 
		}else if(status=="error"){
			layer.alert(message, {
		        closeBtn: 0,
		        icon:5,
		        skin:'error'
		    });
		}
		
	}
</script>
</body>
</html>
