<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>一键网站更新 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
$().ready( function() {

	// 根据更新选项显示/隐藏开始日期和结束日期
	$(".buildTypeInput").click( function() {
		if ($(this).val() == "date") {
			$(".dateTr").show();
		} else {
			$(".dateTr").hide();
		}
	})
	
	$("#inputForm").submit(function() {
		$("#buildType").val($(".buildTypeInput:checked").val());
		$("#maxResults").val($("#maxResultsInput").val());
		$("#beginDate").val($("#beginDateInput").val());
		$("#endDate").val($("#endDateInput").val());
	});

	var isInitialize = false;
	var buildTotal = 0;
	$("#inputForm").ajaxForm({
		dataType: "json",
		beforeSubmit: function(data) {
			if (!isInitialize) {
				isInitialize = true;
				$(".buildTypeInput").attr("disabled", true);
				$("#maxResultsInput").attr("disabled", true);
				$("#beginDateInput").attr("disabled", true);
				$("#endDateInput").attr("disabled", true);
				$(":submit").attr("disabled", true);
				$("#statusTr").show();
				$("#status").text("正在更新BASE_JAVASCRIPT，请稍后...");
			}
		},
		success: function(data) {
		    //alert();
			if (data.buildTotal) {
				buildTotal += Number(data.buildTotal);
			}
			if (data.status == "baseJavascriptFinish") {
				$("#status").text("正在更新自定义错误页，请稍后...");
				$("#buildContent").val("errorPage");
				$("#inputForm").submit();
			} 
			
			else if (data.status == "errorPageFinish") {
				$("#status").text("正在更新首页，请稍后...");
				$("#buildContent").val("index");
				$("#inputForm").submit();
				
				$(":submit").attr("disabled", false);
				$.message("success", "网站更新成功！[更新总数: " + buildTotal + "]");
				isInitialize = false;
				buildTotal = 0;
			}
			/* else if (data.status == "indexFinish") {
				$("#status").text("正在更新登录页，请稍后...");
				$("#buildContent").val("login");
				$("#inputForm").submit();
			} else if (data.status == "loginFinish") {
				$("#status").text("正在更新文章，请稍后...");
				$("#buildContent").val("article");
				$("#inputForm").submit();
			} else if (data.status == "articleBuilding") {
				var maxResults = Number($("#maxResults").val());
				var firstResult = Number(data.firstResult);
				$("#status").text("正在更新文章[" + (firstResult + 1) + " - " + (firstResult + maxResults) + "]，请稍后...");
				$("#buildContent").val("article");
				$("#firstResult").val(firstResult);
				$("#inputForm").submit();
			} else if (data.status == "articleFinish") {
				$("#status").text("正在更新产品，请稍后...");
				$("#buildContent").val("product");
				$("#firstResult").val("0");
				$("#inputForm").submit();
			} else if (data.status == "productBuilding") {
				var maxResults = Number($("#maxResults").val());
				var firstResult = Number(data.firstResult);
				$("#status").text("正在更新产品[" + (firstResult + 1) + " - " + (firstResult + maxResults) + "]，请稍后...");
				$("#buildContent").val("product");
				$("#firstResult").val(firstResult);
				$("#inputForm").submit();
			} else if (data.status == "productFinish") {
				$("#buildContent").val("");
				$("#firstResult").val("0");
				$("#statusTr").hide();
				$(".buildTypeInput").attr("disabled", false);
				$("#maxResultsInput").attr("disabled", false);
				$("#beginDateInput").attr("disabled", false);
				$("#endDateInput").attr("disabled", false);
				$(":submit").attr("disabled", false);
				$.message("success", "网站更新成功！[更新总数: " + buildTotal + "]");
				isInitialize = false;
				buildTotal = 0;
			}
			*/
		}
	});

});
</script>
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
			<li class="active">一键网站更新</li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->

		<form id="inputForm" action="build_html!all.action" method="post">
			<input type="hidden" id="buildType" name="buildType" value="" />
			<input type="hidden" id="maxResults" name="maxResults" value="" />
			<input type="hidden" id="firstResult" name="firstResult" value="0" />
			<input type="hidden" id="buildContent" name="buildContent" value="" />
			<input type="hidden" id="beginDate" name="beginDate" value="" />
			<input type="hidden" id="endDate" name="endDate" value="" />
			<table class="inputTable">
				<tr>
					<th>
						更新选项:
					</th>
					<td>
					
					<label class="pull-left inline">
					    <small class="muted smaller-90">指定日期:</small>
						<input type="radio" name="buildTypeInput" class="ace" value="date" checked />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">更新所有:</small>
						<input type="radio" name="buildTypeInput" class="ace" value="all" />
						<span class="lbl middle"></span>
					</label>	
					

					</td>
				</tr>
				<tr class="dateTr">
					<th>
						起始日期:
					</th>
					<td>
						<input type="text" id="beginDateInput" name="" class="formText datePicker" value="${(defaultBeginDate?string("yyyy-MM-dd"))!}" title="留空则从最早的内容开始更新" />
					</td>
				</tr>
				<tr class="dateTr">
					<th>
						结束日期:
					</th>
					<td>
						<input type="text" id="endDateInput" name="" class="formText datePicker" value="${(defaultEndDate?string("yyyy-MM-dd"))!}" title="留空则更新至最后的内容" />
					</td>
				</tr>
				<tr>
					<th>
						每次更新数
					</th>
					<td>
						<input type="text" id="maxResultsInput" name="" class="formText" value="50" />
					</td>
				</tr>
				<tr id="statusTr" class="hidden">
					<th>
						&nbsp;
					</th>
					<td>
						<span class="loadingBar">&nbsp;</span>
						<p id="status"></p>
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