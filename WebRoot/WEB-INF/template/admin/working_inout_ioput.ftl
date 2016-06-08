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
		//var num = 0;
		//var $jq_table_h = $(".ui-jqgrid-htable").find("tr").eq(0);
		//alert($jq_table_h.length);
		//alert($jq_table_h.get(0).tagName);
		 //var $jq_table_tr = $("#grid-table tr").length;
		 //alert($jq_table_tr);
		//var $jq_table_h_th =$jq_table_h.find("th");
		// var $jq_table_td = $jq_table_tr.find("td");
		//alert($jq_table_h_th.length);
		//$jq_table_h_th.eq(1).css("width","150px");
		//var tr_length = $jq_table_tr.length;
		//var td_length = $jq_table_td.length;
		///var flag = true;
		/* tdf:for(var i=0;i<td_length;i++){
			var result = 0;
			for(var j=0;j<tr_length;j++){
				result = result + ($jq_table_tr.eq(j).find("td").eq(i).text())*1;
				if(result！==0){
					continue tdf;
				}
			}
			if(result==0){
				$("#grid-table tr td:nth-child("+i+")").css("display","none");
			}
		}  */
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
		$("#jqg10").children().each(function(){
			var flag = false;
			var tdValue = $(this).text();
			var tdValue1 = $.trim(tdValue);
			tdNum = $(this).parents("tr").find("td").index($(this));
			var tdNum1 = tdNum;
			if(tdValue1 == 0 && tdNum>0){
				$(".ui-row-ltr").each(function(){
					var tdValue1 = $(this).find("td").eq(tdNum).text();
					
					if(tdValue1 != 0){
						flag = true;
					}
				})
				if(flag == false){
					$(".ui-jqgrid-labels").find("th").eq(tdNum).hide();
					$(".ui-row-ltr").each(function(){
						$(this).find("td").eq(tdNum1).hide();
					});
					$(".jqgfirstrow").find("td").eq(tdNum1).hide();
				}
			}
		});
	
	var width = $("#grid-table_cb").css("width");
	$(".jqgfirstrow td:first").css("width","25px");
}
</script>
</body>
</html>
