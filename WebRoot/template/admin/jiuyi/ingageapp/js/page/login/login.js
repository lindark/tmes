function reset() {
	var login_password_form = $("#login_password_reset_form");
	var login_input_password_reset = $("#login_password_reset");
	var login_input_password_reset_again = $("#login_password_reset_again");
	var login_button_reset_save = $("#login_reset_button_save");
	var login_reset_button_saving = $("#login_reset_button_saving");
	var password_error_msg = $("#password_error_msg");
	var password_again_error_msg = $("#password_again_error_msg");
	var password_rule = /^[a-zA-Z0-9\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b\uFF01\u201c\u201d\u2018\u2019\u300e\u300f\u300c\u300d\uFF09\uFF08\.\_\-\?\~\!\@\#\$\%\^\&\*\\\+\`\=\[\]\(\)\{\}\|\;\'\:\"\,\/\<\>]+$/;
	login_input_password_reset.watermark("请输入6位以上登录密码");
	login_input_password_reset_again.watermark("请再次输入您的密码");
	$("body").bind('keyup', function(event) {
		if (event.keyCode == 13) {
			login_button_reset_save.trigger("click");
		}
	});
	login_button_reset_save.click(function() {
		do_Reset();
	});

	function do_Reset() {
		var options = {
			resetForm: false,
			beforeSubmit: login_reset_validate,
			success: login_reset_dealResponse
		};
		login_password_form.ajaxSubmit(options);
	}


	login_input_password_reset.focus(function() {
		$(this).addClass("input_onfocus");
	}).blur(function() {
		$(this).removeClass("input_onfocus");
	});
	login_input_password_reset_again.focus(function() {
		$(this).addClass("input_onfocus");
	}).blur(function() {
		$(this).removeClass("input_onfocus");
	});

	function login_reset_dealResponse(responseText) {
		var responseTextJson = $.evalJSON(responseText);
		var status = responseTextJson.status;
		var statusText = responseTextJson.statusText;
		var view = responseTextJson.data.view;
		switch (status) {
			case 0:
				$(".right_content").html(view);
				break;
			case 202:
				$("#passwordResetDiv").html(view);
				break;
			default:
				login_button_reset_save.show();
				login_reset_button_saving.hide();
				errorTip(statusText);
		}
	}

	function login_input_password_reset_blur_validate() {
		if (login_input_password_reset_validate()) {
			login_input_password_reset.addClass("input_error");
			window.setTimeout(function() {
				password_error_msg.html("6-16位数字、字母或常用符号，字母区分大小写");
				password_error_msg.show();
			}, 100);
			return false;
		}
		if (!login_input_password_reset_rule_validate()) {
			login_input_password_reset.addClass("input_error");
			window.setTimeout(function() {
				password_error_msg.html("6-16位数字、字母或常用符号，字母区分大小写");
				password_error_msg.show();
			}, 100);
			return false;
		} else {
			password_error_msg.hide();
			login_input_password_reset.removeClass("input_error");
			return true;
		}
	}

	function login_input_password_reset_again_blur_validate() {
		if (login_input_password_reset_again_validate()) {
			window.setTimeout(function() {
				password_again_error_msg.html("6-16位数字、字母或常用符号，字母区分大小写");
				password_again_error_msg.show();
			}, 100);
			login_input_password_reset_again.addClass("input_error");
			return false;
		}
		if (!login_input_password_reset_again_rule_validate()) {
			window.setTimeout(function() {
				password_again_error_msg.html("6-16位数字、字母或常用符号，字母区分大小写");
				password_again_error_msg.show();
			}, 100);
			login_input_password_reset_again.addClass("input_error");
			return false;
		}
		if (!login_input_password_reset_same_validate()) {
			window.setTimeout(function() {
				password_again_error_msg.html("两次输入的密码不一致");
				password_again_error_msg.show();
			}, 100);
			login_input_password_reset_again.addClass("input_error");
			return false;
		} else {
			password_again_error_msg.hide();
			login_input_password_reset_again.removeClass("input_error");
			return true;
		}
	}


	login_input_password_reset.blur(function() {
		return login_input_password_reset_blur_validate();
	});
	login_input_password_reset_again.blur(function() {
		return login_input_password_reset_again_blur_validate();
	});

	function login_input_password_reset_rule_validate() {
		return (password_rule.test(login_input_password_reset.val()));
	}

	function login_input_password_reset_again_rule_validate() {
		return (password_rule.test(login_input_password_reset_again.val()));
	}

	function login_input_password_reset_validate() {
		return (login_input_password_reset.val().length < 6);
	}

	function login_input_password_reset_again_validate() {
		return (login_input_password_reset_again.val().length < 6);
	}

	function login_input_password_reset_same_validate() {
		return (login_input_password_reset.val() == login_input_password_reset_again.val());
	}

	function login_reset_validate() {
		if (login_input_password_reset_blur_validate() && login_input_password_reset_again_blur_validate()) {
			login_button_reset_save.hide();
			login_reset_button_saving.show();
			return true;
		}
		return false;
	}

}

function login(cookieName, loginName) {
	var login_form = $("#login_form");
	var login_input_passportID = $("#login_passportID");
	var login_input_password = $("#login_password");
	var login_button_save = $("#login_button_save");
	var login_button_saving = $("#login_button_saving");
	var login_error_msg = $("#login_error_msg");
	var login_password_error_msg = $("#login_password_error_msg");
	var $ulVerify = $('#ul_verifycode');
	var $iptCode = $ulVerify.find('input.input_code');
	login_input_password.watermark("请输入登录密码");
	$iptCode.watermark("请输入4位识别码");
	if (loginName == "") {
		if (cookieName) {
			login_input_passportID.val(cookieName);
		}
	} else {
		login_input_passportID.val(loginName);
	}
	login_input_passportID.watermark("请输入手机号/邮箱");
	$("body").bind('keyup', function(event) {
		if (event.keyCode == 13) {
			login_button_save.trigger("click");
		}
	});
	var sessionId;
	login_input_passportID.blur(function() {
		loginName_validate();
		login_input_passportID.unbind("keyup").bind("keyup", function() {
			if (loginName_validate()) {
				login_input_passportID.removeClass("input_error").addClass("input_onfocus");
				login_error_msg.hide();
			}
		});
		var $ipt = $(this),
			loginName = $.trim($ipt.val());
		if (loginName_validate()) {
			$.post('/global/request-nick-check.action', {
				loginName: loginName
			}, function(json) {
				if (json.status == 0) {
					var data = json.data,
						needcode = data.needCode;
					sessionId = data.sessionID;
					var $ul = $('#ul_verifycode');
					if (needcode) {
						$ul.show().find('img').attr('src', '/global/check-code.action?sessionID=' + sessionId + '&' + Math.random());
						$ul.find('input[type="hidden"]').val(sessionId);
						$ul.find('span.sucess_ico').css('visibility', 'hidden');
						$ul.find('div.sucess_info').removeClass('input_error');
						$ul.find('.error_info').hide();
					} else {
						$('#ul_verifycode').hide().find(':input').val('');
					}
				} else {}
			}, 'json');
		}
	});

	function resetVerify() {
		$('#ul_verifycode').find('span.sucess_ico').css('visibility', 'hidden');
		$('#ul_verifycode').find('div.sucess_info').removeClass('input_error');
		$('#ul_verifycode').find('.error_info').hide();
	}


	$('#ul_verifycode a.change').click(function(evt) {
		evt.preventDefault();
		$('#ul_verifycode').find('img').attr('src', '/global/check-code.action?sessionID=' + sessionId + '&' + Math.random());
		$('#ul_verifycode').find('span.sucess_ico').css('visibility', 'hidden');
		$('#ul_verifycode').find('div.sucess_info').removeClass('input_error');
		$('#ul_verifycode input.input_code').val('').focus();
	});

	function checkCode(evt, from) {
		var $ipt = $(this),
			code = $ipt.val(),
			len = code.length;
		if (len == 4) {
			$.post('/global/compare-code.action', {
				graphCode: code,
				sessionID: sessionId
			}, function(json) {
				resetVerify();
				if (json.status == 0) {
					$('#ul_verifycode').find('span.sucess_ico').css('visibility', 'visible');
					from == 'keyPress' && login_button_save.trigger("click");
				} else {
					$('#ul_verifycode').find('div.sucess_info').addClass('input_error');
					$('#ul_verifycode').find('.error_info').show().text('请输入正确的识别码');
				}
			}, 'json');
		} else if (len == 0) {
			resetVerify();
			$('#ul_verifycode').find('div.sucess_info').addClass('input_error');
			$('#ul_verifycode').find('.error_info').show().text('请输入4位识别码');
		} else {
			resetVerify();
			$('#ul_verifycode').find('div.sucess_info').addClass('input_error');
			$('#ul_verifycode').find('.error_info').show().text('请输入4位识别码');
		}
	}

	$('#ul_verifycode input.input_code').bind('blur', checkCode);
	$('#ul_verifycode input.input_code').bind('keyup', function(evt) {
		if (evt.keyCode == 13) {
			evt.stopPropagation();
			checkCode.call(this, null, 'keyPress');
		}
	});
	login_input_password.blur(function() {
		password_validate();
		login_input_password.unbind("keyup").bind("keyup", function() {
			if (password_validate()) {
				login_input_password.removeClass("input_error").addClass("input_onfocus");
				login_password_error_msg.hide();
			}
		});
	});
	login_button_save.click(function() {
		do_login();
	});
	login_input_passportID.focus(function() {
		$(this).addClass("input_onfocus");
	}).blur(function() {
		$(this).removeClass("input_onfocus");
	});
	login_input_password.focus(function() {
		$(this).addClass("input_onfocus");
	}).blur(function() {
		$(this).removeClass("input_onfocus");
	});

	function do_login() {
		var options = {
			resetForm: false,
			beforeSubmit: login_validate,
			success: login_dealResponse
		};
		login_form.ajaxSubmit(options);
	}

	function login_dealResponse(responseText) {
		var responseTextJson = $.evalJSON(responseText);
		var status = responseTextJson.status;
		var statusText = responseTextJson.statusText;
		var view = responseTextJson.data.view;
		switch (status) {
			case 400:
				setTimeout(function() {
					login_password_error_msg.hide();
					login_error_msg.html(statusText);
					login_error_msg.show();
					login_input_password.removeClass("input_error").removeClass("input_onfocus");
					login_input_passportID.addClass("input_error").focus();
					login_button_save.show();
					login_button_saving.hide();
					var $ul = $('#ul_verifycode');
					if (responseTextJson.data.needCode) {
						sessionId = responseTextJson.data.sessionID;
						$ul.show().find('img').attr('src', '/global/check-code.action?sessionID=' + sessionId + '&' + Math.random());
						$ul.find('input[type="hidden"]').val(sessionId);
						$ul.find('span.sucess_ico').css('visibility', 'hidden');
						$ul.find('div.sucess_info').removeClass('input_error');
						$ul.find('.error_info').hide();
						$('#ul_verifycode input.input_code').val('');
					} else {
						if (!$ul.is(':hidden')) {
							$('#ul_verifycode a.change').trigger("click");
							$('#ul_verifycode').find('span.sucess_ico').css('visibility', 'hidden');
						}
					}
				}, 500);
				break;
			case 401:
				setTimeout(function() {
					login_error_msg.hide();
					login_password_error_msg.html(statusText);
					login_password_error_msg.show();
					login_input_passportID.removeClass("input_error").removeClass("input_onfocus");
					login_input_password.addClass("input_error").focus();
					login_button_save.show();
					login_button_saving.hide();
					var $ul = $('#ul_verifycode');
					if (responseTextJson.data.needCode) {
						sessionId = responseTextJson.data.sessionID;
						$ul.show().find('img').attr('src', '/global/check-code.action?sessionID=' + sessionId + '&' + Math.random());
						$ul.find('input[type="hidden"]').val(sessionId);
						$ul.find('span.sucess_ico').css('visibility', 'hidden');
						$ul.find('div.sucess_info').removeClass('input_error');
						$ul.find('.error_info').hide();
						$('#ul_verifycode input.input_code').val('');
					} else {
						if (!$ul.is(':hidden')) {
							$('#ul_verifycode a.change').trigger("click");
						}
					}
					login_input_password.focus().select();
				}, 500);
				break;
			case 410:
				login_input_passportID.trigger('blur');
				login_button_save.show();
				login_button_saving.hide();
				var $ul = $('#ul_verifycode');
				if (!$ul.is(':hidden')) {
					$('#ul_verifycode a.change').trigger("click");
					$('#ul_verifycode').find('.error_info').show().text('请输入正确的识别码');
				}
				break;
			case 201:
			case 202:
				$("#loginDiv").html(view);
				break;
            case 0:
                window.location.href = pageContextPath + "/route/ing.action";
                break;
            default:
                errorTip(statusText);
                login_button_save.show();
                login_button_saving.hide();
		}
	}

	function loginName_validate() {
		var regexMobile = /^1[3|4|5|7|8][\d]{9}$/;
		var regexEmail = /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/;
		if ($.trim(login_input_passportID.val()) == "") {
			login_error_msg.html("手机号或邮箱地址不能为空");
			login_error_msg.show();
			login_input_passportID.addClass("input_error");
			return false;
		}
		var loginName = $.trim(login_input_passportID.val());
		if (!(regexMobile.test(loginName) || regexEmail.test(loginName))) {
			window.setTimeout(function() {
				login_error_msg.html("请输入正确的手机号或邮箱地址");
				login_error_msg.show();
			}, 100);
			login_input_passportID.addClass("input_error");
			return false;
		}
		return true;
	}

	function password_validate() {
		if ($.trim(login_input_password.val()) == "") {
			window.setTimeout(function() {
				login_password_error_msg.html("密码不能为空");
				login_password_error_msg.show();
			}, 100);
			login_input_password.addClass("input_error");
			return false;
		}
		return true;
	}

	function login_validate() {
		if (loginName_validate() && password_validate()) {
			if ($('#ul_verifycode').is(':visible')) {
				var $iptVerify = $('#ul_verifycode input.input_code'),
					code = $iptVerify.val(),
					$label = $('#ul_verifycode').find('label.error_info');
				if (code.length == 0) {
					$label.text('请输入4位识别码').show();
				} else if (code.length < 4) {
					$label.text('请输入4位识别码').show();
				} else if ($('#ul_verifycode').find('span.sucess_ico').is(':hidden')) {
					$label.text('请输入正确的识别码').show();
				} else {
					login_button_save.hide();
					login_button_saving.show();
					return true;
				}
				$iptVerify.focus().select();
			} else {
				login_button_save.hide();
				login_button_saving.show();
				return true;
			}
		}
		return false;
	}

}

function forget() {
	var login_error_msg = $("#input_passport_loginNameMessageLabel");
	var login_captcha_error_msg = $("#input_passport_captchaMessageLabel");
	var passport_forget_form = $("#passport_forget_form");
	var input_passport_loginName = $("#input_passport_loginName");
	var input_passport_captcha = $("#input_passport_captcha");
	var captcha_button = $("#captcha_button");
	var captcha_check_button = $("#captcha_check_button");
	var captcha_checking_button = $("#captcha_checking_button");
	input_passport_loginName.watermark("请输入登陆账号(手机号/邮箱)").initCheck();
	input_passport_captcha.watermark("请输入收到的验证码").initCheck();
	$('#ul_verifycode input.input_code').watermark("请输入识别码");
	var second = 0;

	function countDown() {
		if (second > 0) {
			captcha_button.html(second + "秒后重新获取").addClass("button_disable");
			setTimeout(countDown, 1000);
		} else {
			captcha_button.html("重新获取验证码").removeClass("button_disable");
		}
		second--;
	}


	captcha_button.click(function() {
		if (second > 0) {
			return false;
		}
		fetch_captcha();
	});
	captcha_check_button.click(function() {
		check_captcha();
	});

	function check_captcha() {
		var options = {
			resetForm: false,
			beforeSubmit: form_validate,
			success: captcha_dealResponse
		};
		if (!captcha_check_button.hasClass("button_disable")) {
			passport_forget_form.attr("action", pageContextPath + "/global/captcha-check.action");
			passport_forget_form.ajaxSubmit(options);
		}
	}

	function fetch_captcha() {
		var options = {
			resetForm: false,
			beforeSubmit: captcha_validate,
			success: captcha_fetch_dealResponse
		};
		passport_forget_form.attr("action", pageContextPath + "/global/captcha-fetch.action");
		passport_forget_form.ajaxSubmit(options);
	}

	var sessionId;
	input_passport_loginName.focus(function() {
		$(this).addClass("input_onfocus");
	}).blur(function() {
		$(this).removeClass("input_onfocus");
		var $ipt = $(this),
			loginName = $.trim($ipt.val());
		if ($ipt.check()) {
			$.post('/global/request-nick-check.action', {
				loginName: loginName
			}, function(json) {
				if (json.status == 0) {
					var data = json.data,
						needcode = data.needCode;
					sessionId = data.sessionID;
					var $ul = $('#ul_verifycode');
					if (needcode) {
						$ul.show().find('img').attr('src', '/global/check-code.action?sessionID=' + sessionId + '&' + Math.random());
						$ul.find('input[type="hidden"]').val(sessionId);
						$ul.find('span.sucess_ico').css('visibility', 'hidden');
						$ul.find('div.sucess_info').removeClass('input_error');
						$ul.find('.error_info').hide();
					} else {
						$('#ul_verifycode').hide().find(':input').val('');
					}
				} else {}
			}, 'json');
		}
	});
	$('#ul_verifycode a.change').click(function(evt) {
		evt.preventDefault();
		$('#ul_verifycode').find('img').attr('src', '/global/check-code.action?sessionID=' + sessionId + '&' + Math.random());
		$('#ul_verifycode input.input_code').val('').focus();
		resetVerify();
	});

	function resetVerify() {
		$('#ul_verifycode').find('span.sucess_ico').css('visibility', 'hidden');
		$('#ul_verifycode').find('div.sucess_info').removeClass('input_error');
		$('#ul_verifycode').find('.error_info').hide();
	}


	$('#ul_verifycode input.input_code').bind('blur', function(evt, from) {
		var $ipt = $(this),
			code = $ipt.val(),
			len = code.length;
		resetVerify();
		if (len == 4) {
			$.post('/global/compare-code.action', {
				graphCode: code,
				sessionID: sessionId
			}, function(json) {
				if (json.status == 0) {
					$('#ul_verifycode').find('span.sucess_ico').css('visibility', 'visible');
					from == 'keyPress' && check_captcha();
				} else {
					$('#ul_verifycode').find('div.sucess_info').addClass('input_error');
					$('#ul_verifycode').find('.error_info').show().text('请输入正确的识别码');
				}
			}, 'json');
		}
	});
	$('#ul_verifycode input.input_code').bind('keypress', function(evt) {
		if (evt.keyCode == 13) {
			$(this).trigger('blur', ['keyPress']);
		}
	});
	input_passport_captcha.focus(function() {
		$(this).addClass("input_onfocus");
	}).blur(function() {
		$(this).removeClass("input_onfocus");
	});

	function form_validate() {
		if (input_passport_loginName.check() && input_passport_captcha.check()) {
			if ($('#ul_verifycode').is(':visible')) {
				var $iptVerify = $('#ul_verifycode input.input_code'),
					code = $iptVerify.val(),
					$label = $('#ul_verifycode').find('label.error_info');
				if (code.length == 0) {
					$label.text('请输入4位识别码').show();
				} else if (code.length < 4) {
					$label.text('请输入4位识别码').show();
				} else if ($('#ul_verifycode').find('span.sucess_ico').is(':hidden')) {
					$label.text('请输入正确的识别码').show();
				} else {
					captcha_check_button.hide();
					captcha_checking_button.show();
					return true;
				}
				$iptVerify.focus().select();
			} else {
				return true;
			}
		}
		return false;
	}

	function captcha_validate() {
		return (input_passport_loginName.check());
	}

	function captcha_dealResponse(responseText) {
		var responseTextJson = $.evalJSON(responseText);
		var status = responseTextJson.status;
		var statusText = responseTextJson.statusText;
		var view = responseTextJson.data.view;
		switch (status) {
			case 400:
				setTimeout(function() {
					login_error_msg.html(statusText);
					login_error_msg.show();
					input_passport_loginName.removeClass("input_error").addClass("input_error");
					captcha_check_button.show();
					captcha_checking_button.hide();
				}, 500);
				break;
			case 408:
			case 409:
				setTimeout(function() {
					login_captcha_error_msg.html(statusText);
					login_captcha_error_msg.show();
					input_passport_captcha.removeClass("input_error").addClass("input_error");
					captcha_check_button.show();
					captcha_checking_button.hide();
					var $ul = $('#ul_verifycode');
					if (responseTextJson.data.needCode) {
						$ul.show().find('img').attr('src', '/global/check-code.action?sessionID=' + sessionId + '&' + Math.random());
						$ul.find('input[type="hidden"]').val(sessionId);
						$ul.find('span.sucess_ico').css('visibility', 'hidden');
						$ul.find('div.sucess_info').removeClass('input_error');
						$ul.find('.error_info').hide();
						$('#ul_verifycode input.input_code').val('');
						input_passport_captcha.focus().select();
					} else {
						if (!$ul.is(':hidden')) {
							$('#ul_verifycode a.change').trigger("click");
							$('#ul_verifycode').find('span.sucess_ico').css('visibility', 'visible');
						}
					}
				}, 500);
				break;
			case 410:
				captcha_check_button.show();
				captcha_checking_button.hide();
				if ($('#ul_verifycode').is(':visible')) {
					var $iptVerify = $('#ul_verifycode input.input_code'),
						code = $iptVerify.val(),
						$label = $('#ul_verifycode').find('label.error_info');
					$('#ul_verifycode a.change').trigger("click");
					$label.text('识别码输入错误，请重新输入').show();
				} else {
					input_passport_loginName.trigger('blur');
				}
				captcha_check_button.show();
				captcha_checking_button.hide();
				break;
			case 201:
				$("#passwordForgetDiv").html(view);
				break;
			case 0:
				successTip("验证码已发送");
				break;
			default:
				errorTip(statusText);
				captcha_check_button.show();
				captcha_checking_button.hide();
		}
	}

	function captcha_fetch_dealResponse(responseText) {
		var responseTextJson = $.evalJSON(responseText);
		var status = responseTextJson.status;
		var statusText = responseTextJson.statusText;
		switch (status) {
			case 400:
				login_error_msg.html(statusText);
				login_error_msg.show();
				input_passport_loginName.removeClass("input_error").addClass("input_error");
				break;
			case 0:
				successTip("验证码已发送");
				second = 60;
				countDown();
				break;
			default:
				errorTip(statusText);
				second = 60;
				countDown();
		}
	}

}

function forget_reset() {
	var password_rule = /^[a-zA-Z0-9\u3002\uff1b\uff0c\uff1a\u201c\u201d\uff08\uff09\u3001\uff1f\u300a\u300b\uFF01\u201c\u201d\u2018\u2019\u300e\u300f\u300c\u300d\uFF09\uFF08\.\_\-\?\~\!\@\#\$\%\^\&\*\\\+\`\=\[\]\(\)\{\}\|\;\'\:\"\,\/\<\>]+$/;
	var input_password_forget_reset = $("#input_password_forget_reset");
	var input_password_forget_reset_again = $("#input_password_forget_reset_again");
	var password_forget_button = $("#password_forget_button");
	var password_forget_button_ing = $("#password_forget_button_ing");
	var password_forget_reset_form = $("#password_forget_reset_form");
	var password_forget_error_msg = $("#password_forget_error_msg");
	var password_forget_again_error_msg = $("#password_forget_again_error_msg");
	input_password_forget_reset.watermark("请输入6位以上密码");
	input_password_forget_reset_again.watermark("请再次输入您的密码");
	input_password_forget_reset.focus(function() {
		$(this).addClass("input_onfocus");
	}).blur(function() {
		$(this).removeClass("input_onfocus");
	});
	input_password_forget_reset_again.focus(function() {
		$(this).addClass("input_onfocus");
	}).blur(function() {
		$(this).removeClass("input_onfocus");
	});

	function input_password_reset_blur_validate() {
		if (password_forget_reset_validate()) {
			window.setTimeout(function() {
				password_forget_error_msg.html("6-16位数字、字母或常用符号，字母区分大小写");
				password_forget_error_msg.show();
			}, 100);
			input_password_forget_reset.addClass("input_error");
			return false;
		}
		if (!password_forget_reset_rule()) {
			window.setTimeout(function() {
				password_forget_error_msg.html("6-16位数字、字母或常用符号，字母区分大小写");
				password_forget_error_msg.show();
			}, 100);
			input_password_forget_reset.addClass("input_error");
			return false;
		} else {
			password_forget_error_msg.hide();
			input_password_forget_reset.removeClass("input_error");
			return true;
		}
	}

	function input_password_reset_again_blur_validate() {
		if (password_forget_reset_again_validate()) {
			window.setTimeout(function() {
				password_forget_again_error_msg.html("6-16位数字、字母或常用符号，字母区分大小写");
				password_forget_again_error_msg.show();
			}, 100);
			input_password_forget_reset_again.addClass("input_error");
			return false;
		}
		if (!password_forget_reset_again_rule()) {
			window.setTimeout(function() {
				password_forget_again_error_msg.html("6-16位数字、字母或常用符号，字母区分大小写");
				password_forget_again_error_msg.show();
			}, 100);
			input_password_forget_reset_again.addClass("input_error");
			return false;
		}
		if (!password_forget_reset_same_validate()) {
			window.setTimeout(function() {
				password_forget_again_error_msg.html("两次输入的密码不一致");
				password_forget_again_error_msg.show();
				input_password_forget_reset_again.addClass("input_error");
			}, 100);
			return false;
		} else {
			password_forget_error_msg.hide();
			password_forget_again_error_msg.hide();
			input_password_forget_reset_again.removeClass("input_error");
			return true;
		}
	}


	input_password_forget_reset.blur(function() {
		return input_password_reset_blur_validate();
	});
	input_password_forget_reset_again.blur(function() {
		return input_password_reset_again_blur_validate();
	});
	password_forget_button.click(function() {
		var options = {
			resetForm: false,
			beforeSubmit: password_forget_submit_validate,
			success: password_forget_reset__dealResponse
		};
		password_forget_reset_form.ajaxSubmit(options);
	});

	function password_forget_reset__dealResponse(responseText) {
		var responseTextJson = $.evalJSON(responseText);
		var status = responseTextJson.status;
		var statusText = responseTextJson.statusText;
		var view = responseTextJson.data.view;
		switch (status) {
			case 0:
				$(".right_content").html(view);
				break;
			default:
				errorTip(statusText);
				password_forget_button.show();
				password_forget_button_ing.hide();
		}
	}

	function password_forget_submit_validate() {
		if (input_password_reset_blur_validate() && input_password_reset_again_blur_validate()) {
			password_forget_button.hide();
			password_forget_button_ing.show();
			return true;
		}
		return false;
	}

	function password_forget_reset_validate() {
		return (input_password_forget_reset.val().length < 6);
	}

	function password_forget_reset_rule() {
		return (password_rule.test(input_password_forget_reset.val()));
	}

	function password_forget_reset_again_rule() {
		return (password_rule.test(input_password_forget_reset_again.val()));
	}

	function password_forget_reset_again_validate() {
		return (input_password_forget_reset_again.val().length < 6);
	}

	function password_forget_reset_same_validate() {
		return (input_password_forget_reset.val() == input_password_forget_reset_again.val());
	}

}

function tenantChoice() {
	$("li:first-child").addClass("hover");
	$("#tenantChoiceContent_tenantId").val($("li.hover").find("span").attr("id"));
	$(document).keydown(function(event) {
		if (event.keyCode == 38) { //up
			if ($("li").hasClass("hover")) {
				if ($("li:first-child").hasClass("hover")) {
					$("li:first-child").removeClass("hover");
					$("li:last-child").addClass("hover");
				} else {
					$("li.hover").removeClass("hover").prev().addClass("hover");
				}
			} else {
				$("li:last-child").addClass("hover");
			}
		} else if (event.keyCode == 40) { //down
			if ($("li").hasClass("hover")) {
				if ($("li:last-child").hasClass("hover")) {
					$("li:last-child").removeClass("hover");
					$("li:first-child").addClass("hover");
				} else {
					$("li.hover").removeClass("hover").next().addClass("hover");
				}
			} else {
				$("li:first-child").addClass("hover");
			}
		}
		$("#tenantChoiceContent_tenantId").val($("li.hover").find("span").attr("id"));
	});
	$("li").click(function() {
		$(this).addClass("hover").siblings().removeClass("hover");
		$("#tenantChoiceContent_tenantId").val($(this).find("span").attr("id"));
	});
	$("#login_entry_button").click(function() {
		do_Entry();
	});

	function do_Entry() {
		var options = {
			beforeSubmit: login_tenant_choice_validate,
			success: login_tenant_choice_dealResponse
		};
		$("#login_tenant_choice").ajaxSubmit(options);
	}

	function login_tenant_choice_dealResponse(responseText) {
		var responseTextJson = $.parseJSON(responseText);
		var status = responseTextJson.status;
		if (status == 0) {
			window.location.href = pageContextPath + "/route/ing.action";
		} else {
			errorTip("服务器错误");
			$("#login_entry_button_ing").hide();
			$("#login_entry_button").show();
		}
	}

	function login_tenant_choice_validate() {
		if ($.trim($("#tenantChoiceContent_tenantId").val()) == "") {
			errorTip("请选择一个租户");
			return false;
		}
		$("#login_entry_button_ing").show();
		$("#login_entry_button").hide();
		return true;
	}


	$("body").bind('keyup', function(event) {
		if (event.keyCode == 13) {
			$("#login_entry_button").trigger("click");
		}
	});
}

function successTip(statusText) {
	$("#success_result_layer p").html(statusText);
	$("#success_result_layer").show().animate({
		top: 20 + $(document).scrollTop() + "px"
	}, {
		duration: 200,
		queue: false
	});
	setTimeout("$('#success_result_layer').hide();", 3000);
	divScroll("success_result_layer");
}

function errorTip(statusText) {
	$("#error_result_layer p").html(statusText);
	$("#error_result_layer").show().animate({
		top: 20 + $(document).scrollTop() + "px"
	}, {
		duration: 200,
		queue: false
	});
	setTimeout("$('#error_result_layer').hide();", 3000);
	divScroll("error_result_layer");
}

function divScroll(id) {
	$(window).scroll(function() {
		$("#" + id).animate({
			top: 20 + $(document).scrollTop() + "px"
		}, {
			duration: 200,
			queue: false
		});
	});
}

function errorResult(statusText) {
	var login_error_msg = $("#login_error_msg");
	login_error_msg.html(statusText);
	login_error_msg.show();
}
/************************************************************************************/


$.widget && 
$.widget('mxx.login', {
	options: {

	},
	_create: function() {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		if ($this.checkBroswer()) {
			$this.initLoginFrom();
		}
	},
	checkBroswer: function() {
		//if ($.browser.msie && parseInt($.browser.version) < 10)
		if (platform.name == 'IE' && parseInt(platform.version) < 10) {
			this.element.prepend('<div>\
			    <div class="shade_div_dark"></div>\
			    <div class="browser_judge">\
			        <span class="browser_title">您的浏览器版本太低</span>\
			        <p>您目前的IE浏览器版本较低，为了更好的安全性和浏览体验，请更新浏览器 <font>点击图标立即下载</font></p>\
			        <ul class="browser_list clear">\
			            <li class="browser_ico"><a href="http://w.x.baidu.com/alading/anquan_soft_down_b/11843"><span class="browser_firefox"></span></a></li>\
			            <li class="browser_ico"><a href="http://w.x.baidu.com/alading/anquan_soft_down_b/12966"><span class="browser_safari"></span></a></li>\
			            <li class="browser_ico"><a href="http://w.x.baidu.com/alading/anquan_soft_down_normal/23360"><span class="browser_ie"></span></a></li>\
			            <li class="browser_ico"><a href="http://w.x.baidu.com/alading/anquan_soft_down_b/14744"><span class="browser_chorme"></span></a></li>\
			        </ul>\
			        <ul class="browser_namelist clear">\
			            <li><div class="browser_name"><a>Firefox</a></div></li>\
			            <li><div class="browser_name"><a>Safari</a></div></li>\
			            <li><div class="browser_name"><a>IE11</a></div></li>\
			            <li><div class="browser_name"><a>Chrome</a></div></li>\
			        </ul>\
			    </div>\
			</div>');
			return false;
		}
		return true;
	},
	initLoginFrom: function() {
		var $this = this,
			$elem = this.element,
			opt = this.options;

		$this.$passport = $elem.find('input[name="loginName"]');
		$this.$password = $elem.find('input[name="password"]');
		$this.$error = $elem.find('label.error_info');
		$this.$verify = $elem.find('ul.verification_form');
		$this.$iptCode = $this.$verify.find('input.input_code');
		$this.$sessionId = $this.$verify.find('input.input_sessionId');
		$this.$change = $this.$verify.find('a.change');
		$this.$img = $this.$verify.find('img.img_code');
		$this.$submit = $elem.find('a.button_blue').first();
		$this.$submiting = $elem.find('a.button_waiting');
		$this.$form = $elem.find('form');


		$(document).on('keyup', function(evt) {
			evt.keyCode == 13 && $this.submit();
		});

		$this.$passport.on('blur', function(evt) {
			$this.checkPassport() && $this.validatePassport();
		});

		$this.$change.on('click', function(evt) {
			evt.preventDefault();
			$this.changeCode(evt);
		});

		$this.$submit.on('click', function(evt) {
			evt.preventDefault();
			$this.submit();
		})

		var loginName = opt.loginName || opt.cookieName || '';
		if (loginName) {
			$this.$passport.val(loginName).triggerHandler('blur');
		}
	},
	submit: function() {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		if (!$this.checkPassport() || !$this.checkPassword() || $this.$verify.is(':visible') && !$this.checkVerifyCode()) {
			return;
		}
		$this.loginStart();
        $this.ppCache = {};
		$.post($this.$form.attr('action'), $this.$form.serialize(), null, 'json').done(function(json) {
			$this.loginEnd();
			switch (json.status) {
				case 400:
				case 401:
					$this.errorMsg('用户名与密码不匹配，请重新输入');
					if (json.data.needCode) {
						$this.openVerify(json.data.sessionID);
					} else {
						$this.closeVerify();
					}
					break;
				case 410:
					$this.errorMsg('识别码错误，请重新输入');

					break;
				case 201:
				case 202:
					$("#loginDiv").html(json.data.view);
					break;
				case 0:
                    location.href = pageContextPath + "/route/ing.action";
					break;
				default:
                    $this.errorMsg(json.statusText);
                    $this.loginEnd();
			}
		}).fail(function() {
			$this.loginEnd(1000);
			$this.errorMsg('系统繁忙，请稍后再试');
		});

	},
	loginStart: function() {
		this.$submit.hide();
		this.$submiting.show();
	},
	loginEnd: function(delay) {
		this._delay(function() {
			this.$submit.show();
			this.$submiting.hide();
		}, delay || 0);
	},
	checkPassport: function() {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		var pp = $.trim($this.$passport.val());
		if (!pp) {
			return $this.errorMsg('手机号或邮箱地址不能为空');
		}
		if (!$this.rMobile.test(pp) && !$this.rEmail.test(pp)) {
			return $this.errorMsg('请输入正确的手机号或邮箱地址');
		}
		return $this.errorMsg() || true;
	},
	checkPassword: function() {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		var pw = $this.$password.val();
		if (!pw) {
			return $this.errorMsg('登录密码不能为空');
		}
		return $this.errorMsg() || true;
	},
	checkVerifyCode: function() {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		var vc = $this.$iptCode.val();
		if (vc.length != 4) {
			return $this.errorMsg('请输入4位识别码');
		}
		return $this.errorMsg() || true;
	},
	validatePassport: function() {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		var pp = $.trim($this.$passport.val());
		if($this.lastPassPort != pp){
			$this.ppCache = {};
			$this.lastPassPort = pp;
		}
		if ($this.ppCache[pp]) {
			$this._validatePassport($this.ppCache[pp]);
		} else {
			$.post('/global/request-nick-check.action', {
				loginName: pp
			}, function(json) {
				if (json.status == 0) {
					$this._validatePassport($this.ppCache[pp] = json.data);
				} else {

				}
			}, 'json');
		}
	},
	_validatePassport: function(data) {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		var needcode = data.needCode;
		if (needcode) {
			$this.$verify.is(':hidden') && $this.openVerify(data.sessionID);
		} else {
			$this.closeVerify();
		}
	},
	openVerify: function(sessionId) {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		if ($this.$sessionId.val() != sessionId) {
			$this.$sessionId.val($this.sessionId = sessionId);
		}
		$this.$verify.show();
		$this.changeCode();
	},
	closeVerify: function() {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		$this.$sessionId.val('');
		$this.$iptCode.val('');
		$this.$verify.hide();
	},
	changeCode: function(evt) {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		$this.$img.attr('src', '/global/check-code.action?sessionID=' + $this.sessionId + '&' + Math.random());
		$this.$iptCode.val('');
		evt && !evt.isTrigger && $this.$iptCode.focus();
	},
	errorMsg: function(msg) {
		var $this = this,
			$elem = this.element,
			opt = this.options;
		if (msg) {
			$this.$error.text(msg);
			if ($this.$error.is(':visible')) {
				$this.$error.fadeTo('fast', 0.3).fadeTo('fast', 1);
			} else {
				$this.$error.show();
			}
		} else {
			$this.$error.is(':visible') && $this.$error.hide();
		}
	},
	ppCache: {

	},
	rMobile: /^1[3|4|5|7|8][\d]{9}$/,
	rEmail: /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/
});

function androidDownload() {
    var url = pageContextPath + "/global/android-download.action?decorator=popups&confirm=true";
    $.openPopupLayer({
        width:550,
        url:url
    });
}

function iOSDownload() {
    var url = pageContextPath + "/global/ios-download.action?decorator=popups&confirm=true";
    $.openPopupLayer({
        width:500,
        url:url
    });
}