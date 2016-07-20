 <#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>管理中心 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<link rel="stylesheet"
	href="${base}/template/admin/assets/css/jquery.gritter.css" />
<script src='${base}/template/admin/assets/js/jquery.min.js'></script>
<script src="${base}/template/admin/js/Main/teamindex.js"></script>
<script src="${base}/template/admin/assets/js/jquery.gritter.min.js"></script>
<script src="${base}/template/admin/js/layer/layer.js"></script>
<script src="${base}/template/admin/js/Main/admin_teamindex.js"></script>

<script src='http://localhost:8000/CLodopfuncs.js'></script>
<!-- 
<script type="text/javascript" src="${base }/template/admin/js/LodopFuncs.js"></script>

<script type="text/javascript">
	/*
	$(function(){
		LODOP.PRINT_INIT("打印任务名");
		LODOP.SET_PRINT_PAGESIZE(2,"80mm","50mm","箱标签");//宽度 49mm 高度 74mm 
		LODOP.ADD_PRINT_HTM("0mm","5.42mm","31.15mm","6mm","<p>&nbsp;建新赵氏集团</p>");
		LODOP.ADD_PRINT_TEXT("6mm","0mm","49mm","6mm","桑塔纳NF/前门");
		LODOP.ADD_PRINT_TEXT("12mm","0mm","49mm","6mm","34D839431A5AP");
		LODOP.ADD_PRINT_BARCODE("18mm","2mm","27mm","6mm","128Auto","123231231");
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		LODOP.ADD_PRINT_TEXT("28mm","0mm","49mm","6mm","物料号:40100971");
		LODOP.ADD_PRINT_BARCODE("34mm","2mm","22mm","6mm","128Auto","160224");
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		LODOP.ADD_PRINT_TEXT("42mm","0mm","49mm","5mm","包装批次:160224");
		LODOP.ADD_PRINT_TEXT("47mm","0mm","49mm","5mm","数量:20");
		LODOP.ADD_PRINT_TEXT("52mm","0mm","49mm","5mm","喷码批次:");
		LODOP.ADD_PRINT_BARCODE("57mm","2mm","37mm","6mm","128Auto","987654321");
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		LODOP.ADD_PRINT_TEXT("65mm","0mm","49mm","5mm","随工单:10015167532");
		LODOP.SET_PRINT_COPIES(1);//控制打印份数
		//LODOP.SET_PRINT_STYLEA(0,"Angle",90);
		LODOP.PREVIEW();
	})
	*/
</script>

  -->
</head>
<body class="no-skin">

	<#include "/WEB-INF/template/admin/admin_navbar.ftl">
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>

		<#include "/WEB-INF/template/admin/admin_sidebar.ftl">

		<div class="main-content">
			<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">

			<!-- #section:basics/content.breadcrumbs -->
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">管理中心</a>
					</li>
					<li class="active">首页</li>
				</ul>
			</div>

			<div class="page-content">


				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">

							<div class="row">

								<div class="col-xs-12 col-sm-6 widget-container-col">
									<!-- #section:custom/widget-box -->
									<div class="widget-box transparent">
										<div class="widget-header">
											<h4 class="widget-title lighter">班组信息</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>

											</div>
										</div>

										<div class="widget-body">
											<div
												class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="profile-user-info profile-user-info-striped">
												
												
												   <div class="profile-info-row">
														<div class="profile-info-name">工厂/车间：</div>
														<div class="profile-info-value">
															${(admin.team.factoryUnit.workShop.factory.factoryName)!
															} &nbsp;&nbsp;&nbsp;    ${(admin.team.factoryUnit.workShop.workShopName)!
															}
														</div>
													</div>

													

													<div class="profile-info-row">
														<div class="profile-info-name">单元：</div>
														<div class="profile-info-value">
															${(admin.team.factoryUnit.factoryUnitName)! }
															<input type="hidden" id="funid" value="${(admin.team.factoryUnit.id)!}">
														</div>										
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name">班组：</div>
														<div class="profile-info-value">															
															${(admin.team.teamName)! }
															<button class="btn btn-white btn-default btn-sm btn-round access"data-access-list="changeTeamButton"  id="changeTeamButton">更换班组</button>													   												   														
														</div>		
													</div>
													
													<div class="profile-info-row">
													
													    <div class="profile-info-name">生产日期/班次:</div>
														<div class="profile-info-value">
														      <form id="inputForm" class="validate" action="admin!productupdate.action" method="post">
								                        <input type="hidden" name="id" value="${(admin.id)!}" />
								                        <input type="hidden" id="productDate" value="${(admin.productDate)!}"/>
								                        <input type="hidden" id="shift" value="${(admin.shift)!}"/>
								                        
								                        
														<input type="text" id="productDate_input"name="admin.productDate" value="${(admin.productDate)!}" class="datePicker formText {required: true}"/>
														 
														<select name="admin.shift" class="formText {required: true}">
														<option></option>
														<option value="1" <#if (admin.shift == 1)!> selected</#if>>早</option>
														<option value="2" <#if (admin.shift == 2)!> selected</#if>>白</option> 
														<option value="3" <#if (admin.shift == 3)!> selected</#if>>晚</option>
													    </select>&nbsp;&nbsp;&nbsp; <button class="btn btn-white btn-default btn-sm btn-round" id="submitButton" type="button">保存</button>														   
													    </form>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>

									<!-- /section:custom/widget-box -->
								</div>
								<!--按钮组start-->
								<div class="col-xs-12 col-sm-6 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header">
											<h4 class="widget-title lighter">业务处理</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>

											</div>
										</div>

										<div class="widget-body">
											<div
												class="widget-main padding-6 no-padding-left no-padding-right">
												<!-- 
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="dumpconfirm">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block"
														id="dump">
														<i class="ace-icon fa fa-credit-card bigger-110"></i> <span
															class="bigger-110 no-text-shadow">转储确认</span>
													</button>
												</div>
												 -->
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="cartonreceiving">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="carton">
														<i class="ace-icon fa fa-file-archive-o bigger-110"></i> <span
															class="bigger-110 no-text-shadow">纸箱收货</span>
													</button>
												</div>
												
												<div class="col-md-3 col-sm-4" style="padding:2px;">
													<button                                               
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="csll" type="button">
														<i class="ace-icon fa fa-reply-all bigger-110"></i> <span
															class="bigger-110 no-text-shadow">超市领料</span>
													</button>
												</div>
												
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="getmaterial">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block disabled"
														id="pick">
														<i class="ace-icon fa fa-credit-card bigger-110"></i> <span
															class="bigger-110 no-text-shadow">裁切配送</span>
													</button>
												</div>
												<!-- <div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="handover">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block"
														id="handoverprocess">
														<i class="ace-icon fa fa-fire bigger-110"></i> <span
															class="bigger-110 no-text-shadow">交接</span>
													</button>
												</div> -->
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="sttm">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block disabled"
														id="dailywork">
														<i class="ace-icon fa fa-share-alt bigger-110"></i> <span
															class="bigger-110 no-text-shadow">报工</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="putstorage">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block disabled"
														id="storage">
														<i class="ace-icon fa fa-tasks bigger-110"></i> <span
															class="bigger-110 no-text-shadow">成品检验</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="endproduct">
													<button                                               
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="endProduct" type="button">
														<i class="ace-icon fa fa-archive bigger-110"></i> <span
															class="bigger-110 no-text-shadow">成品入库</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 " style="padding:2px;"
													data-access-list="quickresponse">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block"
														id="qResponse">
														<i class="ace-icon fa fa-volume-up bigger-110"></i> <span
															class="bigger-110 no-text-shadow">快速响应</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 " style="padding:2px;"
													data-access-list="inspection">
													
													<button data-toggle="dropdown" id="scenecheck"
														class="btn btn-green btn-success btn-bold btn-round dropdown-toggle btn-block">
														<i class="ace-icon fa fa-leaf bigger-110"></i> <span
															class="bigger-110 no-text-shadow">现场检验</span>
															<i class="ace-icon fa fa-angle-down icon-on-right"></i>
													</button>
													<ul class="dropdown-menu dropdown-success dropdown-menu-right">
														<li>
															<a href="javascript:void(0);" id="pollingtest">成品巡检</a>
														</li>
	
														<li>
															<a href="javascript:void(0);" id="sample">抽检</a>
														</li>
	
														<li>
															<a href="javascript:void(0);" id="halfinspection">半成品巡检</a>
														</li>
														
														<li>
															<a href="javascript:void(0);" id="rework">返工</a>
														</li>
														<li>
															<a href="javascript:void(0);" id="scrap">报废</a>
														</li>
														
													</ul>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="repair">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block disabled"
														id="repair">
														<i class="ace-icon fa fa-cog bigger-110"></i> <span
															class="bigger-110 no-text-shadow"> 返修发货</span>
													</button>
												</div>
												
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="repairgoods">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block disabled"
														id="repairin">
														<i class="ace-icon fa fa-glass bigger-110"></i> <span
															class="bigger-110 no-text-shadow">返修收货</span>
													</button>
												</div>
												
												<!-- 
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="halfinspection">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block disabled"
														id="halfinspection">
														<i class="ace-icon fa fa-star-half-o bigger-110"></i> <span
															class="bigger-110 no-text-shadow">半成品巡检</span>
													</button>
												</div>
												 -->
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="attendance">
													<button
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="kaoqin" type="button">
														<i class="ace-icon fa fa-users bigger-110"></i> <span
															class="bigger-110 no-text-shadow">考勤</span>
													</button>
												</div>
												
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="returnProduct">
													<button                                               
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="returnProduct" type="button">
														<i class="ace-icon fa fa-reply-all bigger-110"></i> <span
															class="bigger-110 no-text-shadow">退回中转仓</span>
													</button>
												</div>
												<div class="col-md-3 col-sm-4 access" style="padding:2px;"
													data-access-list="returnProduct">
													<button                                               
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="deptpk" type="button">
														<i class="ace-icon fa fa-reply-all bigger-110"></i> <span
															class="bigger-110 no-text-shadow">部门领用</span>
													</button>
												</div>
												
												<div class="col-md-3 col-sm-4" style="padding:2px;">
													<button                                               
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="updown" type="button">
														<i class="ace-icon fa fa-reply-all bigger-110"></i> <span
															class="bigger-110 no-text-shadow">上架/下架</span>
													</button>
												</div>
												
												<div class="col-md-3 col-sm-4" style="padding:2px;">
													<button                                               
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="locatHO" type="button">
														<i class="ace-icon fa fa-reply-all bigger-110"></i> <span
															class="bigger-110 no-text-shadow">仓位库存交接</span>
													</button>
												</div>
												   <div class="col-md-3 col-sm-4" style="padding:2px;">
													<button                                               
														class="btn btn-green btn-success btn-bold btn-round btn-block" id="processHO" type="button">
														<i class="ace-icon fa fa-reply-all bigger-110"></i> <span
															class="bigger-110 no-text-shadow">工序交接</span>
													</button>
												</div> 
												<input type="hidden" id="loginid" value="<@sec.authentication property='principal.id' />" />
												

											</div>
										</div>
									</div>

								</div>
								<!--按钮组end-->
							</div>
							<!-- /section:custom/extra.hr -->
							<div class="row" id="wbload">
								<div class="col-sm-12">
									<div class="widget-box transparent">
										<div class="widget-header widget-header-flat">
											<h4 class="widget-title lighter">
												<i class="ace-icon fa fa-star orange"></i> 今日随工单
											</h4>

											<div class="widget-toolbar">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>
											</div>
										</div>

										<div class="widget-body">
											<div class="widget-main no-padding">
												<table class="table table-bordered table-striped" id="table00">
													<thead class="thin-border-bottom">
														<tr>
															<th style="width:26%"><i class="ace-icon fa fa-caret-right blue"></i>产品名称
															</th>
															<th style="width:12%"><i
																class="ace-icon fa fa-caret-right blue"></i>班组</th>
															<th style="width:7%"><i
																class="ace-icon fa fa-caret-right blue"></i>副主任</th>
															<th style="width:7%"><i
																class="ace-icon fa fa-caret-right blue"></i>主任</th>
															<th style="width:5%"><i
																class="ace-icon fa fa-caret-right blue"></i>数量</th>
															<th style="width:7%"><i
																class="ace-icon fa fa-caret-right blue"></i>产品编号</th>
															<th style="width:8%"><i
																class="ace-icon fa fa-caret-right blue"></i>随工单编号</th>
															<th style="width:6%"><i
																class="ace-icon fa fa-caret-right blue"></i>模具</th>
															<th style="width:5%"><i
																class="ace-icon fa fa-caret-right blue"></i>交接</th>
															<th style="width:9%"><i
																class="ace-icon fa fa-caret-right blue"></i>条码打印</th>
														</tr>
													</thead>

													<tbody>
														<input type="hidden" value="${info}" id="info">
														<#list workingbillList as list>
														<tr>
															<#if !list.moudle ??>
															<td><input type="checkbox" class="ckbox"
																name="WorkingBill.workingBillCode" value="${list.id}" />&nbsp;
																<a href="javascript:void(0);" class="a matkx" <#if list.diffamount??>style="color:red"</#if>>${list.maktx}</a>&nbsp;&nbsp;
																<a href="javascript:void(0);" class="a moudle" <#if list.moudle=="">style="color:red"</#if>>[添加模具组号]</a>&nbsp;&nbsp;${(list.moudle)!}
															</td>
															<#else>
															<td><input type="checkbox" class="ckbox"
																name="WorkingBill.workingBillCode" value="${list.id}" />&nbsp;
																<a href="javascript:void(0);" class="a matkx" <#if list.diffamount??>style="color:red"</#if>>${list.maktx}</a>&nbsp;&nbsp;
																<a href="javascript:void(0);" class="a moudle" <#if list.moudle=="">style="color:red"</#if>>${(list.moudle)!}</a>&nbsp;&nbsp;
															</td>
															</#if>
															<#if !list.teamName ??>
															<td >
															<a href="javascript:void(0);" class="a teamName" >[添加班组]</a>&nbsp;
															</td>
															<#else>
															<td >
															<a href="javascript:void(0);" class="a teamName" >${(list.teamName)!}</a>&nbsp;
															</td>
															</#if>
															<td >
															<a href="javascript:void(0);" class="a fuzhuren" >${(list.fuzhuren)!}</a>&nbsp;
															</td>
															<td>
															<a href="javascript:void(0);" class="a zhuren" >${(list.zhuren)!}</a>&nbsp;
															</td>
															<td >${list.planCount}</td>
															<td >${list.matnr}</td>
															<td >${list.workingBillCode}</td>
															<td >${(list.module)!}</td>
															<td>
																<#if list.isHand=="Y">
																	是
																<#else>
																	否
																</#if>
															</td>
														<td > <input type="text" style="width:30px" class="input-sm col-sm-2 part"/>&nbsp; 
																<a href="javascript:void(0);" class="barcode">打印</a>
															</td>
														</tr>
														</#list>
													</tbody>
												</table>
											</div>
											<!-- /.widget-main -->
										</div>
										<!-- /.widget-body -->
									</div>
									<!-- /.widget-box -->
								</div>
								<!-- /.col -->
	

							</div>
							<!-- /.row -->

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->


			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
		<!-- /.add by welson 0728  -->
	</div>
	<!-- /.main-container -->

	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">

	<script type="text/javascript">
	$(function(){
		<#if workingbillList==null || workingbillList?size <=0>
		$.gritter
				.add({
					// (string | mandatory) the heading of the notification
					title : '通知!',
					// (string | mandatory) the text inside the notification
					text : '您当前尚未绑定"生产日期"和"班次"信息，无法加载数据,请点击<a href="admin!product.action" class="orange">绑定生产日期和班次</a> 进行绑定，绑定后，随工单将自动加载',
					class_name : 'gritter-success'
				});
		</#if>
		
		    //$('#wbload').load('admin!findWorkingBill.action');
		$(".teamName").click(function(){
			var t = $(this);
			var wbid = t.parent().parent().find(".ckbox").val();
			var funid = $("#funid").val();
			teamName_event(t,funid,wbid);
		});
		
		/**
		 * 获取班组
		 */
		function teamName_event(t,funid,wbid)
		{
			layer.open({
		        type: 2,
		        skin: 'layui-layer-lan',
		        shift:2,
		        title: "选择班组",
		        fix: true,
		        shade: 0.5,
		        shadeClose: true,
		        maxmin: true,
		        scrollbar: false,
		        btn:['确认','取消'],
		        area: ["40%", "50%"],//弹出框的高度，宽度
		        content:"working_bill!findTeam.action?funid="+funid+"&wbid="+wbid,
		        yes:function(index,layero){//确定
		        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
		        	var value = iframeWin.getName();
		        	var arr = value.split(";");
		        	var teamName=arr[0];
		        	var teamid=arr[1];
		        	$.ajax({
		        		url:"working_bill!updateWKInTeam.action",
		        		data:{"teamid":teamid,"teamName":teamName,"wbid":wbid},
		        		dataType:"json",
		        		success:function(data){
		        			if(data.status=="success"){
		        				var zhuren = data.zhuren;
		        				var fuzhuren = data.fuzhuren;
		        				var isNotSelect = data.isNotSelect;
		        				if(isNotSelect == 1){
		        					t.text("[添加班组]");
		        				}else{
		        					t.text(teamName);
		        				}
		        				t.parent().next().children().text(fuzhuren);
		        				t.parent().next().next().children().text(zhuren);
		        			}else{
		        				alert(data.message);
		        			}
		        		},
		        		error:function(){
		        			alert("添加失败");
		        		}
		        	});
		            layer.close(index);
		        	return false;
		        },
		        no:function(index)
		        {
		        	layer.close(index);
		        	return false;
		        }
		    });
			return false;
		}
	});
	$(".fuzhuren").click(function(){
		var t = $(this);
		var wbid = t.parent().parent().find(".ckbox").val();
		var mark = 1;
		PersonSelect(t,wbid,mark);
	});
	$(".zhuren").click(function(){
		var t = $(this);
		var wbid = t.parent().parent().find(".ckbox").val();
		var mark = 0;
		PersonSelect(t,wbid,mark);
	});
	function PersonSelect(t,wbid,mark){
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        title: "选择人员",
	        fix: true,
	        shade: 0.5,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        btn:['确认','取消'],
	        area: ["40%", "50%"],//弹出框的高度，宽度
	        content:"process_handover!browser.action",
	        yes:function(index,layero){//确定
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var work=iframeWin.getGridId();
	    		var id=work.split(",");
	    		var empid=id[0];
	        	var emp_name=id[1];
	    		$.ajax({
	        		url:"working_bill!updatePerson.action",
	        		data:{"wbid":wbid,"mark":mark,"empid":empid},
	        		dataType:"json",
	        		success:function(data){
	        			if(data.status=="success"){
	        				var zhuren = data.zhuren;
	        				var fuzhuren = data.fuzhuren;
	        				t.text(emp_name);
	        			}else{
	        				alert(data.message);
	        			}
	        		},
	        		error:function(){
	        			alert("添加失败");
	        		}
	        	});
	    		
	            layer.close(index);
	        	return false;
	        },
	        no:function(index){
	        	layer.close(index);
	        	return false;
	        }
	    });
		return false;
	}
	</script>
</body>
</html>