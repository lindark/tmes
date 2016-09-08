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
							<form class="form-horizontal" id="searchform" action="">
							  <div class="profile-user-info profile-user-info-striped">
							    <div class="profile-info-row">
 								<#-- <div class="profile-info-value div-value">
										<label class="col-sm-3" style="text-align:right">单元编码:</label>
										<div class="col-sm-6">
											<input type="text" id="workcode"name="workcode" class="input input-sm form-control" value="">
										</div>
										    <label class="requireField">*</label>	
									</div>
							    </div>-->
							    <div class="profile-info-value div-value">
										<label class="col-sm-3" style="text-align:right">生产订单号:</label>
										<div class="col-sm-6">
											<input type="text" id="aufnr"name="aufnr" class="input input-sm form-control" value="">
										</div>
									</div>
							    </div>
							
							   <div class="profile-info-row">
							    <div class="profile-info-value div-value">
										<label class="col-sm-3" style="text-align:right">生产日期:</label>
										<div class="col-sm-7">
											<div class="input-daterange input-group">
												<input type="text" id="start" class="input-sm form-control datePicker" name="start">
												<span class="input-group-addon">
													<i class="fa fa-exchange"></i>
												</span>
												<input type="text" id="end" class="input-sm form-control datePicker" name="end">
										</div>	
									</div>								
										<label class="requireField">*</label>	
								</div>
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
<input type="hidden" id="sameteamid" value="${(sameTeamId)! }" />
</body>
</html>
<script type="text/javascript">

	function getGridId() {
		var choose ="ERROR";
		//var workcode = $("#workcode").val();
		var aufnr = $("#aufnr").val();
		var start = $("#start").val();
		var end = $("#end").val();
		/*if(aufnr == null || aufnr == "" || aufnr.length> 10){
			layer.alert("订单号不能为空且长度不能大于10位!");
		}
		else*/ 
		/*if(workcode == null || workcode==""){
			layer.alert("单元不能为空");
			return false;
		}*/ 
		if(start == null || start == "" ||end ==null || end ==""){
			layer.alert("生产日期不能为空!");
		}
		else{			
		 choose = aufnr + "," + start + "," + end;
		}
		//alert(choose);
		return choose;
	}
</script>