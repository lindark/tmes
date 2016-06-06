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
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/working_inout_ioput.js"></script>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script> 
<script type="text/javascript">
	var jsondata = ${(jsondata)!};
	$(function(){
//		$("#grid-table_materialName").css("width","150px");
//		$("#grid-table").css("width","100%");
	});

</script>
<style>
body {
	background: #fff;
}
</style>

</head>
<body class="no-skin input">
<input type="hidden" id="jsondata" value='${(jsondata)!}'/>
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
<input type="hidden" id="wbid" value="${wbid }" />
<script type="text/javascript">
function endLoad(){
	var tdNum;//第一行td的值
	var trNum = 0;//tr的行数
	var flag = false;
//		$(".ui-row-ltr").each(function(){
//			trNum = trNum + 1;
//		});
		$("#jqg10").children().each(function(){
			var tdValue = $(this).text();
			tdNum = $(this).parents("tr").find("td").index($(this));
			var tdNum1 = tdNum;
			if(tdValue == 0 && tdNum>0){
				$(".ui-row-ltr").each(function(){
					var tdValue1 = $(this).find("td").eq(tdNum).text();
					
					if(tdValue1 != 0){
						flag = true;
					}
				})
				if(flag == false){
					$(".ui-jqgrid-labels").find("th").eq(tdNum).hide();
					$(".ui-row-ltr").each(function(){
						$(this).find("td").eq(tdNum1).css("display","none");
					});
				}
			}
		});
	//	$(".ui-row-ltr").each(function(){
	//		$(this).find("td").css("width",1/tdNum);
	//	});
	$(".jqgfirstrow").remove();
	$("#grid-table").css("width","100%");
	var width = $("#grid-table_cb").css("width");
	$(".ui-row-ltr").each(function(){
		$(this).find("td").eq(0).css("width",width);
	});
		
}
</script>
</body>
</html>
