<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>投入产出表</title>
		<meta name="description" content="Dynamic tables and grids using jqGrid plugin" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
		<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		 <script type="text/javascript" src="${base}/template/admin/js/report/workinginout_list.js"></script> 
        <script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>		
		<#include "/WEB-INF/template/common/include_adm_top.ftl">
	
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
	 	<script type="text/javascript" src="${base}/template/admin/js/list.js"></script> 
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		<style>
			.operateBar{
				padding:3px 0px;
			}
			.changeDate{
				cursor:pointer;
			}
		</style>
		<script type="text/javascript">
			var jsondata = ${(jsondata)!};
		</script>
	</head>
<body class="no-skin list">
	
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
			<li class="active">投入产出表</li>
		</ul><!-- /.breadcrumb -->
	</div>

	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by weitao  -->
									<form class="form-horizontal" id="searchform" action="working_inout!ajlist.action" role="form">
								<!--    <input type="hidden" id="aufnr" name="aufnr" value="${(workingBill.aufnr)!}"/>	
								   <input type="hidden" id="start" name="start" value=""/>	
								   <input type="hidden" id="end" name="end" value=""/>	--> 				
								  
								   <div class="operateBar">
								   	<div class="form-group">
										<label class="col-sm-2" style="text-align:right">生产订单号:</label>
										<div class="col-sm-4">
											<input type="text" name="workingBillCode" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
										<label class="col-sm-2" style="text-align:right">生产日期:</label>
										<div class="col-sm-4">
											<div class="input-daterange input-group">
												<input type="text" class="input-sm form-control datePicker" name="start" id="startDate">
												<span class="input-group-addon changeDate">
													<i class="fa fa-exchange"></i>
												</span>
												<input type="text" class="input-sm form-control datePicker" name="end" id="endDate">
											</div>
										</div>
									</div>
									
									<div class="form-group">
										<label class="col-sm-1 col-md-offset-1"style="text-align:right">单元:</label>
										<div class="col-sm-4">
											<input type="text" name="info" class="input input-sm form-control" value="" id="form-field-icon-1">
										</div>
									</div>
									
									
										<div class="form-group" style="text-align:center">
											<a  id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												搜索
											</a>
											<a  id="excelReport" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												Excel导出
											</a>
										</div>
										
									</div>
								</form>
									<input type="hidden" id="jsondata" value='${(jsondata)!}'/>
									<table id="grid-table"></table>
									<div id="grid-pager"></div>
								<!-- add by weitao -->	
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

	<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div><!-- /.page-content -->
	</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->


</body>

</html>
<script  type="text/javascript">

	$(function() {
		var $searchbtn = $("#searchButton_1");
		var $startdateinput = $("input[name='start']");
		var $enddateinput = $("input[name='end']");
		var $excelReport = $("#excelReport");
		var $searchform = $("#searchform");//表单
			if($startdateinput.val()==""){
				var nowtime = now_date();
				$startdateinput.val(nowtime);
			}
			if($enddateinput.val()==""){
				var nowtime = now_date();
				$enddateinput.val(nowtime);
			}
		$("#startDate").blur(function(){
			var date = check_date($(this).val());
			if(date!=""){
				$(this).val(date);
			}
		});
		$("#endDate").blur(function(){
			var date = check_date($(this).val());
			if(date!=""){
				$(this).val(date);
			}
		});
		
		
		 $searchbtn.click(function(){
			 $("#grid-table").jqGrid('setGridParam',{
					//url:"admin!ajlist.action?departid="+nodes[0].id,
					datatype:"json",
					page:1
				}).trigger("reloadGrid");
		}); 
		 
		 $excelReport.click(function(){
			 $searchform.attr("action","working_inout!excelexport.action");
			 $searchform.submit();
			 
		 });
		
		 $(".changeDate").click(function(){
			 var sd = $("#startDate").val();
			 var ed = $("#endDate").val();
			 $("#startDate").val(ed);
			 $("#endDate").val(sd);
		 });
		 
	}); 
	function now_date(){
		var nowDate = new Date();
		var nowYear = nowDate.getFullYear();
		var nowMonth = nowDate.getMonth()+1;
		var nowDay = nowDate.getDate();
		if(nowMonth.toString().length==1){
			nowMonth = "0"+nowMonth;
		}
		if(nowDay.toString().length==1){
			nowDay = "0"+nowDay;
		}
	    var nowtime = nowYear+"-"+nowMonth+"-"+nowDay;
	    return nowtime;
	}
	function check_date(datevalue){
		if(datevalue==""){
				alert("日期范围不允许为空");
				var nowt = now_date()
				return nowt;
		}else{
			// 统一日期格式
			var strDate = /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/;
			var beforelength = datevalue.length;
			if(beforelength==9){
				var befores = datevalue.charAt(beforelength-1);
				if(befores=="0"){
					//alert("请输入正确的日期格式(例如:1970-01-01或1907-1-1)")
					var nowt = now_date()
					return nowt;
				}
			}
			  //判断日期是否是预期的格式
			  if (!strDate.test(datevalue)) {
			    //alert("请输入正确的日期格式(例如:1970-01-01或1907-1-1)")
			    var nowt = now_date()
				return nowt;
			  }
		}
		return "";
	}
</script>

<script type="text/javascript">
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */
	
	$(function(){
		var ishead=0;
		$("#ace-settings-btn").click(function(){
			if(ishead==0){
				ishead=1;
				$("#ace-settings-box").addClass("open");
			}else{
				ishead=0;
				$("#ace-settings-box").removeClass("open");
			}
		});
		$(".btn-colorpicker").click(function(){
				$(".dropdown-colorpicker").addClass("open");
		})
		
		var ishead2=0;
		$(".light-blue").click(function(){
			if(ishead2==0){
				ishead2=1;
				$(this).addClass("open");
			}else{
				ishead2=0;
				$(this).removeClass("open");
			}
			
		})
		
		/*
		var ishead3=0;
		$(".hsub").click(function(){
			if(ishead3==0){
				alert("OK");
				ishead3=1;
				$(".hsub").addClass("open");
				//$(this).find(".submenu").removeClass("nav-hide");
			}else{
				ishead3=0;
				//$(this).removeClass("open");
				//$(this).find(".submenu").removeClass("nav-show").addClass("nav-hide").css("display","none");
			}
			
		})
		*/
	})
	
	
</script>