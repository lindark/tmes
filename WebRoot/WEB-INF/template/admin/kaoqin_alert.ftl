<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title></title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/includelist.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/kaoqin_alert.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<#include "/WEB-INF/template/common/include_adm_top.ftl">

<style>
body {
	background: #fff;
}

</style>

</head>
<body class="no-skin input">
	<!-- add by welson 0728 -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-content">
			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<form class="form-horizontal" id="searchform" action="kaoqin!getemp.action" role="form">
								<input type="hidden" id="loginid" name="loginid" value="<@sec.authentication property='principal.id' />" />
								<div class="operateBar">
									<div class="form-group">
										<label class="col-sm-2" style="text-align:right;">单元:</label>
										<div class="col-sm-4">
											<input type="text" name="xFactoryUnit"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div>
										


										<label class="col-sm-2" style="text-align:right;">班次:</label>
										<div class="col-sm-4">
											<select name="shift" id="form-field-icon-1" class="input input-sm form-control">
								                <option value="">非当前班次</option>
								                <!--<option value="1">早</option>
								                <option value="2">白</option>
								                <option value="3">晚</option>-->
						               		</select>
										</div>
									</div>
									
									
									<div class="form-group">
										
										
										<label class="col-sm-2" style="text-align:right;">岗位:</label>
										<div class="col-sm-4">
											<input type="text" name="skill"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div>
										
										<label class="col-sm-2"
											style="text-align:right;">班组:</label>
										<div class="col-sm-4">
											<input type="text" name="team"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div>
										
									</div>
									
									
									<div class="form-group">
										<label class="col-sm-2" style="text-align:right;">员工姓名:</label>
										<div class="col-sm-4">
											<input type="text" name="name"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div>
										<label class="col-sm-2" style="text-align:right;">员工工号:</label>
										<div class="col-sm-4">
											<input type="text" name="workNumber"
												class="input input-sm form-control" value=""
												id="form-field-icon-1">
										</div>
									</div>
									

									<div class="form-group" style="text-align:center">
										<a id="searchButton"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-filter blue"></i> 搜索 </a>
									</div>
								</div>
							</form>


							<table id="grid-table"></table>

							<div id="grid-pager"></div>
							<!-- add by welson 0728 -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content-area -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->
<input type="hidden" id="sameteamid" value="${sameTeamId }" />
</body>
</html>
<script type="text/javascript">
function getGridId()
{
	var ids=$("#grid-table").jqGrid('getGridParam','selarrrow');
	if(ids.length <1)
	{
		layer.alert("请选择至少一个员工",false);
		return "baga";
	}
	else
	{
		return ids;
	}
}

$(document).keydown(function(event){
	if(event.keyCode==13)
	{
		$("#searchButton").click();
	}
});
</script>