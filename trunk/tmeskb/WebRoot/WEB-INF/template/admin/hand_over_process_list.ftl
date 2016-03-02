<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>交接管理</title>
<meta name="description"
	content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

<#include "/WEB-INF/template/common/includelist.ftl">
<!--modify weitao-->
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<script src="${base}/template/admin/assets/js/ace-extra.min.js"></script>

<script src="${base }/template/admin/js/manage/handover.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/manage/handover_list.js"></script>
<script src="${base}/template/admin/assets/js/chosen.jquery.min.js"></script>
 <script type="text/javascript" src="${base}/template/common/js/base.js"></script>

<style type="text/css">
.ztree li span.button.add {
	margin-left: 2px;
	margin-right: -1px;
	background-position: -144px 0;
	vertical-align: top;
	*vertical-align: middle
}
.buttonArea{
	padding:2px;
	text-align:center;
}
input.oddhandOverMount,input.unhandOverMount,input.afterWork{
	padding:2px;
}
.div_top{
	margin:10px;
}
#ui-datepicker-div{
	z-index:9999 !important;
}
.layui-layer-btn1{
	display:none !important;
}
</style>

 
  
<link rel="stylesheet" href="${base }/template/admin/js/sliding/css/style.css" type="text/css"></link>


<script type="text/javascript" src="${base }/template/admin/js/sliding/js/modernizr.js">

</script><script type="text/javascript" src="${base }/template/admin/js/sliding/js/main.js"></script>

<script>
/*
	$(function(){
		var LODOP=getLodop();
		LODOP.PRINT_INIT("打印任务名");
		LODOP.SET_PRINT_PAGESIZE(1,"10mm","10mm","10mm");
		LODOP.ADD_PRINT_HTM("0mm","6mm","31.15mm","6mm","<p>建新赵氏集团</p>");
		LODOP.ADD_PRINT_TEXT("6mm","2mm","49mm","6mm","桑塔纳NF/前门dfadddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
		//LODOP.ADD_PRINT_BARCODE(0,0,200,100,"128Auto","123231231");
		LODOP.SET_PRINT_STYLEA(0,"ShowBarText",0);
		LODOP.PREVIEW();
	})
	*/
</script>
</head>

<body class="no-skin list">
<input type="hidden" id="loginid" value="<@sec.authentication property='principal.id' />" />
	<!-- add by welson 0728 -->
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

			<!-- ./ add by welson 0728 -->

			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a></li>
					<li class="active">交接管理&nbsp;<span class="pageInfo"></span>
					</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
						<div class="div_top">
										 下班生产日期:
                                    <input type="text" id="productDate" name="" value="" class="datePicker formText"/>
                                   &nbsp;&nbsp;
                                     班次:
                                     <select name="shift"id="sl_sh">
                                     	<option value="" <#if (admin.shift == "")!> selected</#if>></option>
                                     	<!-- <option value="1" <#if (admin.shift == 1)!> selected</#if>>早</option> -->
										<option value="2" <#if (admin.shift == 2)!> selected</#if>>白</option>
										<option value="3" <#if (admin.shift == 3)!> selected</#if>>晚</option>
                                   </select>
									</div>
						
							<div id="inputtabs">
								<ul>
									<li><a href="#tabs-1">工序交接</a>
									</li>
									<li><a href="#tabs-4">零头数交接</a>
									</li>
									<!-- <li><a href="#tabs-5">抽包异常交接</a>
									</li> -->
									<li><a href="#tabs-2">线边仓交接</a>
									</li>
									<li class="over"><a href="#tabs-3">总体交接确认</a>
									</li>
								</ul>
								
								<div id="tabs-1">
									<!-- <a href="#0" class="cd-btn">右侧滑动弹出框</a>  -->

									<div class="cd-panel from-right" style="z-index:9999">
										<header class="cd-panel-header">
											<span>工序交接</span>
											<a href="#" class="cd-panel-close">关闭</a>
										</header>
								
										<div class="cd-panel-container">
											<div class="cd-panel-content">
												<div id="load" style="text-align:center;z-index:2">
													<img src="${base }/template/admin/js/layer/skin/default/loading-0.gif"></img>
												</div>
											</div> <!-- cd-panel-content -->
										</div> <!-- cd-panel-container -->
									</div> <!-- cd-panel -->
									<div class="widget-box">
										<div
											class="widget-header widget-header-blue widget-header-flat">

											<div class="widget-toolbar"></div>
										</div>
										<div class="widget-body">
											<div class="widget-main">
												<!-- #section:plugins/fuelux.wizard -->
												<div id="fuelux-wizard" data-target="#step-container">
													<!-- #section:plugins/fuelux.wizard.steps -->
													<ul class="wizard-steps">
														<#assign  num=0/>
														<#list processList as list>
															<#assign  num=num+1/>
															<li data-target="#step_${(list.id)! }" class="step-jump">
																<span class="step">${num }</span>
																<span class="title">${(list.processName)! }</span>
																<input type="hidden" class="process" value="${list.id}"/>
															</li>
														</#list>
														
													</ul>

													<!-- /section:plugins/fuelux.wizard.steps -->
												</div>

												<hr />

												<div class="step-content pos-rel" id="step-container">
													<#list processList as list>
														<div class="step-pane" id="step_${(list.id)! }">
															<#list materialList as wList>
																<div class="col-md-4">
																	<input type="hidden" value="${wList.id }"/>
																	<a href="javascript:void(0);" class="maclick">
																		<span class="materialCode">${(wList.materialCode)! } </span>
																		<span class="materialName">${(wList.materialName)! } </span>
																	</a>
																</div>
															</#list>
														</div>
													</#list>
												</div>

												<!-- /section:plugins/fuelux.wizard.container -->


												<!-- /section:plugins/fuelux.wizard -->
											</div>
											<!-- /.widget-main -->
										</div>
										<!-- /.widget-body -->
									</div>
								</div>
								<div id="tabs-4">
								<form id="oddlist" action="odd_hand_over!creditsubmit.action" method="post">
									<!-- 这里是零头数交接
									<a href="显示"></a> -->
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<th class="center">产品名称</th>
												<th class="center">计划数量</th>
												<th class="center">产品编号</th>
												<th class="center">随工单编号</th>
												<th class="center">下一班随工单编号</th>
												<th class="center">零头数交接数量</th>
												<th class="center">异常交接数量</th>
											</tr>
										</thead>
	
										<tbody>
											
											<#list workingbillList as list>
												<tr>
													<input type="hidden" class="workkingId"name="workingBillIds" value="${list.id}" />
													<td class="center">${list.maktx }</td>
													<td class="center">${list.planCount }</td>
													<td class="center">${list.matnr }</td>
													<td class="center workingCode" name="workingCode">${list.workingBillCode}</td>
														<#if (list.oddHandOverSet!=null && list.oddHandOverSet?size>0)! >
															<#list list.oddHandOverSet as loh>
																<td class="center"><input type="text" class="afterWork" value="${loh.afterWorkingCode}"/>	</td>
																<td class="center"><input type="text" class="oddhandOverMount" name="actualMounts" value="${loh.actualBomMount }"/></td>
																<td class="center"><input type="text" class="unhandOverMount" name="unMounts" value="${loh.unBomMount }"/></td>
															<#break>
															</#list>
														<#else>
														<td class="center"><input type="text" class="afterWork" value=""/>	</td>
														<td class="center"><input type="text" class="oddhandOverMount" name="actualMounts" value=""/></td>
														<td class="center"><input type="text" class="unhandOverMount" name="unMounts" value=""/></td>
														</#if>
												</tr>
											</#list>
											</form>
										</tbody>
									</table>
									<div class="buttonArea" >
										<button class="btn btn-white btn-default btn-sm btn-round btnsubmit" id="oddcreditsubmit" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
										</button>&nbsp;&nbsp;
										<button class="btn btn-white btn-default btn-sm btn-round btnapproval" id="oddcreditapproval" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
										</button>
									</div>
								</div>
								<!-- <div id="tabs-5">
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<th class="center">产品名称</th>
												<th class="center">计划数量</th>
												<th class="center">产品编号</th>
												<th class="center">随工单编号</th>
												<th class="center">异常交接数量</th>
											</tr>
										</thead>
	
										<tbody>
											<form id="pplist" action="odd_hand_over!creditsubmit.action" method="post">
											<#list workingbillList as list>
												<tr>
													<input type="hidden" class="workkingId"name="workingBillIds" value="${list.id}" />
													<td class="center">${list.maktx }</td>
													<td class="center">${list.planCount }</td>
													<td class="center">${list.matnr }</td>
													<td class="center">${list.workingBillCode }	</td>
													<#if list.oddHandOverSet!=null>
														<#list list.oddHandOverSet as loh>
															<td class="center"><input type="text" class="handOverMount" name="actualMounts" value="${loh.actualHOMount }"/></td>
														<#break>
														</#list>
														<#else>
														<td class="center"><input type="text" class="handOverMount" name="actualMounts" value=""/></td>
														</#if>
												</tr>
											</#list>
											</form>
										</tbody>
									</table>
									<div class="buttonArea" >
										<button class="btn btn-white btn-default btn-sm btn-round btnsubmit" id="oddcreditsubmit" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
										</button>&nbsp;&nbsp;
										<button class="btn btn-white btn-default btn-sm btn-round btnapproval" id="oddcreditapproval" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
										</button>
									</div>
								</div> -->
								<div id="tabs-2">
									显示批次库存
									<table class="table table-striped table-bordered">
										<thead>
											<tr>
												<th class="center">库存地点</th>
												<th class="center">物料编码</th>
												<th class="center">物料描述</th>
												<th class="center">批次</th>
												<th class="center">数量</th>
											</tr>
										</thead>
	
										<tbody>
											<#list locationonsideList as list>
												<tr>
													<td class="center">${list.locationCode }</td>
													<td class="center">${list.materialCode }</td>
													<td class="center">${list.materialName }</td>
													<td class="center">${list.charg }</td>
													<td class="center">${list.amount }	</td>
												</tr>
											</#list>
	
										</tbody>
									</table>
								</div>
								<div id="tabs-3">
									<div class="ceshi">
									<table id="grid-table"></table>
		
										<div id="grid-pager"></div>
									</div>
										
									<div class="buttonArea">
										<button class="btn btn-white btn-default btn-sm btn-round btnsubmit" id="creditsubmit" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										上班刷卡提交
										</button>&nbsp;&nbsp;
										<button class="btn btn-white btn-default btn-sm btn-round btnapproval" id="creditapproval" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										下班刷卡确认
										</button>
									</div>
								</div>
							</div>



							<script type="text/javascript">
								var $path_base = "${base}/template/admin";//in Ace demo this will be used for editurl parameter
							</script>

							<!-- PAGE CONTENT ENDS -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

				
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>

<script
	src="${base}/template/admin/assets/js/fuelux/fuelux.wizard.min.js"></script>
<script src="${base}/template/admin/assets/js/jquery.validate.min.js"></script>
<script src="${base}/template/admin/assets/js/additional-methods.min.js"></script>
<script src="${base}/template/admin/assets/js/jquery.maskedinput.min.js"></script>
<script src="${base}/template/admin/assets/js/select2.min.js"></script>

<script type="text/javascript"
	src="${base}/template/admin/js/manage/handovercontrol.js"></script>

</html>
<script type="text/javascript">
	$(function() {
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
		$("#productDate").val(nowtime);
		
		$("#inputtabs").tabs();
		$(".over").click(function(){
			$(window).triggerHandler('resize.jqGrid');
			$("#grid-table").jqGrid('setGridParam',{
				url:"hand_over_process!ajlist.action",
				datatype:"json",
				page:1
			}).trigger("reloadGrid");
		})
		
		/*刷卡确认*/
		$("#creditapproval").click(function(){
			var shift = $("#sl_sh").val();
			if(shift==""){
				layer.alert("请选择班次");
				return false;
			}
			/* var $afterwork = $(".afterWork");
			for(var i=0;i<$afterwork.length;i++){
				if($afterwork.eq(i).val()==""){
					layer.alert("数据错误,无法查询到下一班随工单");
					return false;
				}
			} */
			var productDate = $("#productDate").val();
			if(productDate==""){
				layer.alert("生产日期不允许为空");
				return false;
			}else{
					// 统一日期格式
					var strDate = /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/;
					var beforelength = productDate.length;
					if(beforelength==9){
						var befores = productDate.charAt(beforelength-1);
						if(befores=="0"){
							layer.alert("请输入正确的日期格式(例如:1970-01-01或1907-1-1)")
							return false;
						}
					}
					  //判断日期是否是预期的格式
					  if (!strDate.test(productDate)) {
						  layer.alert("请输入正确的日期格式(例如:1970-01-01或1907-1-1)")
					    return false;
					  }
			}
			var shift = $("#sl_sh").val();
			var loginid = $("#loginid").val();//当前登录人的id
			var url="hand_over!creditapproval.action?loginid="+loginid+"&shift="+shift+"&nowDate="+productDate;
			credit.creditCard(url,function(data){
				if(data.status=="success"){
					$("#creditapproval").prop("disabled",true);
					$("#creditsubmit").prop("disabled",true);
					$("#sl_sh").prop("disabled",true);
					$("#productDate").prop("disabled",true);	
				}
			})
			
		});
		
		/*刷卡提交*/
		$("#creditsubmit").click(function(){
			var shift = $("#sl_sh").val();
			if(shift==""){
				layer.alert("请选择班次");
				return false;
			}
			/*var $afterwork = $(".afterWork");
			 for(var i=0;i<$afterwork.length;i++){
				if($afterwork.eq(i).val()==""){
					layer.alert("数据错误,无法查询到下一班随工单");
					return false;
				}
			} */
			var url="hand_over!creditsubmit.action?loginid="+$("#loginid").val();
			credit.creditCard(url,function(data){
				//alert("OK");
			})
			
		});
		
		$("#btn_back").click(function(){
			window.history.back();
		});
		
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
			
		});
		
		
		//零头交接提交
		$("#oddcreditsubmit").click(function(){
			var shift = $("#sl_sh").val();
			if(shift==""){
				layer.alert("请选择班次");
				return false;
			}
			/*var $afterwork = $(".afterWork");
			 for(var i=0;i<$afterwork.length;i++){
				if($afterwork.eq(i).val()==""){
					layer.alert("数据错误,无法查询到下一班随工单");
					return false;
				}
			} */
			
			var productDate = $("#productDate").val();
			var url = "odd_hand_over!creditsubmit.action?nowDate="+productDate+"&shift="+shift;
			var dt = $("#oddlist").serialize();
			credit.creditCard(url,function(data){
			},dt);

		});
		
		//零头交接确认
		$("#oddcreditapproval").click(function(){
			
			var shift = $("#sl_sh").val();
			if(shift==""){
				layer.alert("请选择班次");
				return false;
			}
			/* var $afterwork = $(".afterWork");
			for(var i=0;i<$afterwork.length;i++){
				if($afterwork.eq(i).val()==""){
					layer.alert("数据错误,无法查询到下一班随工单");
					return false;
				}
			} */
			var url = "odd_hand_over!creditapproval.action";
			var dt = $("#oddlist").serialize();
			credit.creditCard(url,function(data){
				if(data.status=="success"){
					$("#oddcreditapproval").prop("disabled",true);
					$("#oddcreditsubmit").prop("disabled",true);
				}
			},dt);
		});
		
	/* 	//抽包异常交接提交
		$("#ppcreditsubmit").click(function(){
			var url = "pum_pack_hand_over!creditSubmit.action";
			var dt = $("#pplist").serialize();
			credit.creditCard(url,function(data){
			},dt);

		});
		
		//抽包异常交接提交
		$("#ppcreditapproval").click(function(){
			var url = "pum_pack_hand_over!crediTapproval.action";
			var dt = $("#pplist").serialize();
			credit.creditCard(url,function(data){
			},dt);
		}); */
		
		var productDates = $("#productDate").val();
		/* var array = new Array();
		$(".workingCode").each(function(){
			array.push($(this).text());
		}); 
		var shift = $("#sl_sh").val();
		var $afterwork = $(".afterWork");
		if(productDates!=""){
			$.ajax({
				url:"odd_hand_over!findAfterWorkingCode.action",
				data:{"nowDate":productDates,"shift":shift,"workingCode":array},
				traditional: true,  
				dataType: "json",
				type:"POST",
				success:function(data){
					if(data.status=="error"){
						for(var i=0;i<$afterwork.length;i++){
							$afterwork.eq(i).val("");
						}
					}else{
						for(var i=0;i<$afterwork.length;i++){
							$afterwork.eq(i).val(data[i].afterCode);
						}
					}
					
				},
				error:function(){
					alert("操作失败");
				}
			});
			
		} */
		$("#sl_sh").change(function(){
			var shift = $("#sl_sh").val();
			var $afterwork = $(".afterWork");
			if(shift==""){
				
				for(var i=0;i<$afterwork.length;i++){
					$afterwork.eq(i).val("");
				}
				return false;
			}
			var productDate = $("#productDate").val();
			if(productDate==""){
				return false;
			}
			
			 var array = [];
				$(".workingCode").each(function(){
					array.push($(this).text());
				});
			  $.ajax({
					url:"odd_hand_over!findAfterWorkingCode.action",
					data:{"nowDate":productDate,"shift":shift,"workingCode":array},
					traditional: true,  
					dataType: "json",
					type:"POST",
					success:function(data){
						if(data.status=="error"){
							for(var i=0;i<$afterwork.length;i++){
								$afterwork.eq(i).val("");
							}
						}else{
							for(var i=0;i<$afterwork.length;i++){
								$afterwork.eq(i).val(data[i].afterCode);
							}
						}
					},
					error:function(){
						layer.alert("操作失败");
					}
				});  
			
		});
		$("#productDate").change(function(){
			if($(this).val()==""){
				alert("生产日期不允许为空");
				$(this).val(productDates);
				return false;
			}else{
					// 统一日期格式
					var strDate = /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/;
					var beforelength = $(this).val().length;
					if(beforelength==9){
						var befores = $(this).val().charAt(beforelength-1);
						if(befores=="0"){
							layer.alert("请输入正确的日期格式(例如:1970-01-01或1907-1-1)");
							$(this).val(productDates);
							return false;
						}
					}
					  //判断日期是否是预期的格式
					  if (!strDate.test($(this).val())) {
						  layer.alert("请输入正确的日期格式(例如:1970-01-01或1907-1-1)");
					    $(this).val(productDates);
					    return false;
					  }
					  var shift = $("#sl_sh").val();
						if(shift==""){
							return false;
						}
					  var productDate = $(this).val();
					  var array = [];
						$(".workingCode").each(function(){
							array.push($(this).text());
						});
						var $afterwork = $(".afterWork");
					  $.ajax({
							url:"odd_hand_over!findAfterWorkingCode.action",
							data:{"nowDate":productDate,"shift":shift,"workingCode":array},
							traditional: true,  
							dataType: "json",
							type:"POST",
							success:function(data){
								if(data.status=="error"){
									for(var i=0;i<$afterwork.length;i++){
										$afterwork.eq(i).val("");
									}
								}else{
									for(var i=0;i<$afterwork.length;i++){
										$afterwork.eq(i).val(data[i].afterCode);
									}
								}
							},
							error:function(){
								layer.alert("操作失败");
							}
						});  
					  
			}
			
		});
		
		
		
	});
	
	

    
</script>