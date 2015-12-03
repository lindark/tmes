<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>故障原因信息- Powered By
	${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/includelist.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/unusual/js/model.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript"
	src="${base }/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
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
                             <fieldset id="self-data">
													    <div class="form">
												        <select name="type" class="type select"  data-first-title="---选择类型---" data-value="" data-url="fault_reason!getType.action" data-json-space="type"></select>
												        <select name="faultId" class="faultReason select"  data-first-title="---选择原因---" data-value="" data-url="fault_reason!getAll.action" data-json-space="faultReason"></select>		
												        <select name="faultReasonSet.id" class="child select"  data-first-title="---选择原因---" data-value="" data-url="fault_reason!getChild.action" data-json-space="child"></select>													
												        </div>
							</fieldset>
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

</body>
</html>
<script type="text/javascript">

	$(function() {
		
		$("#self-data").cxSelect({
			selects: ['type', 'faultReason', 'child'],
			jsonName: 'name',
			jsonValue: 'value'
		},function(select){//回调
			//$(select).trigger("chosen:updated"); 
			//$(select).chosen({allow_single_deselect:true,no_results_text:"没有找到",search_contains: true});
		});	
	})
	
	function getGridId(){
		var i=$(".child option:selected").text();
		var ii=$(".child option:selected").val();
		var work=""+i+","+ii;
		return work;
	}
	

</script>