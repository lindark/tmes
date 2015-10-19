<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑支付方式 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {

	var $paymentConfigTypeSelect = $("select[name='paymentConfig.paymentConfigType']");
	var $tenpayConfigTr = $(".tenpayConfigTr");
	var $tenpayInput = $(".tenpayConfigTr :input");
	
	// 根据支付类型显示/隐藏输入项
	$paymentConfigTypeSelect.change( function() {
		var $this = $(this);
		if ($this.val() == "tenpay") {
			$tenpayInput.removeClass("ignoreValidate");
			$tenpayConfigTr.show();
		} else {
			$tenpayInput.addClass("ignoreValidate");
			$tenpayConfigTr.hide();
		}
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
			<li class="active"><#if isAdd??>添加支付方式<#else>编辑支付方式</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>


	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>payment_config!save.action<#else>payment_config!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						支付类型:
					</th>
					<td>
						<select name="paymentConfig.paymentConfigType" class="{required: true}">
							<option value="">请选择...</option>
							<#list allPaymentConfigType as list>
								<option value="${list}"<#if (list == paymentConfig.paymentConfigType)!> selected</#if>>
									${action.getText("PaymentConfigType." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						支付方式名称:
					</th>
					<td>
						<input type="text" name="paymentConfig.name" class="formText {required: true, remote: 'payment_config!checkName.action?oldValue=${(paymentConfig.name?url)!}', messages: {remote: '支付方式名称已存在!'}}" value="${(paymentConfig.name)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						支付手续费设置:
					</th>
					<td>
						<#list allPaymentFeeType as list>
							<label class="requireField">
								<input type="radio" name="paymentConfig.paymentFeeType" value="${list}"<#if ((isAdd && list == "scale") || list == paymentConfig.paymentFeeType)!> checked </#if>>
								${action.getText("PaymentFeeType." + list)}
							</label>
						</#list>
						<label class="requireField">*</label>
					</td>
				</tr>
					<th>
						支付费率/固定费用:
					</th>
					<td>
						<input type="text" name="paymentConfig.paymentFee" class="formText {required: true, min: 0}'" value="${(paymentConfig.paymentFee)!"0"}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr class="tenpayConfigTr<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> hidden</#if>">
					<th>
						财付通交易类型:
					</th>
					<td>
						<select name="tenpayConfig.tenpayType" class="<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!>ignoreValidate </#if>{required: true}">
							<option value="">请选择...</option>
							<#list allTenpayType as list>
								<option value="${list}"<#if (list == tenpayConfig.tenpayType)!> selected</#if>>
									${action.getText("TenpayType." + list)}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr class="tenpayConfigTr<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> hidden</#if>">
					<th>
						财付通商户号:
					</th>
					<td>
						<input type="text" name="tenpayConfig.bargainorId" class="formText<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> ignoreValidate</#if> {required: true}'" value="${(tenpayConfig.bargainorId)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr class="tenpayConfigTr<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> hidden</#if>">
					<th>
						财付通密钥:
					</th>
					<td>
						<input type="text" name="tenpayConfig.key" class="formText<#if (isAdd || paymentConfig.paymentConfigType != "tenpay")!> ignoreValidate</#if> {required: true}'" value="${(tenpayConfig.key)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						排序:
					</th>
					<td>
						<input type="text" name="paymentConfig.orderList" class="formText {required: true, digits: true}" value="${(paymentConfig.orderList)!50}" title="只允许输入零或正整数" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						介绍:
					</th>
					<td>
						<textarea name="paymentConfig.description" class="wysiwyg" rows="20" cols="100">${(paymentConfig.description)!}</textarea>
					</td>
				</tr>
			</table>
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