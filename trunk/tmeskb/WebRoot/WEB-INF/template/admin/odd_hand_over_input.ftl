<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>管理中心</title>
<meta name="description"
	content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browserValidate.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true /></#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
</head>
<body class="no-skin list" onload="refresh()">
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
					<li class="active">零头数交接</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal validate" id="inputForm" method="post" action="" role="form">
								<div class="widget-box transparent">
						<div class="div_top">
								<div>
								下班生产日期:
                                <input type="text" id="productDate" name="processHandoverTop.afterProductDate" value="${(processHandoverTop.afterProductDate)!}" class="datePicker formText"/>
                                   &nbsp;&nbsp;
                                 	         班次:
                                     <select name="processHandoverTop.aftershift"id="sl_sh">
                                     	<option value="" <#if (processHandoverTop.aftershift == "")!> selected</#if>></option>
                                     	<option value="1" <#if (processHandoverTop.aftershift== 1)!> selected</#if>>早</option>
										<option value="2" <#if (processHandoverTop.aftershift == 2)!> selected</#if>>白</option>
										<option value="3" <#if (processHandoverTop.aftershift == 3)!> selected</#if>>晚</option>
                                   </select>
								</div>
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
														<input type="hidden" name="processHandoverTop.id" value="${(processHandoverTop.id)!}">
														<input type="hidden" name="processHandoverTop.werk" value="${(admin.team.factoryUnit.workShop.factory.factoryCode)!}">
														<input type="hidden" name="processHandoverTop.wshop" value="${(admin.team.factoryUnit.workShop.workShopCode)!}">
															${(admin.team.factoryUnit.workShop.factory.factoryName)!} &nbsp;&nbsp;&nbsp;    
															${(admin.team.factoryUnit.workShop.workShopName)!}</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">单元：</div>
														<div class="profile-info-value" >
														<input type="hidden" name="processHandoverTop.factoryUnitCode" value="${(admin.team.factoryUnit.factoryUnitCode)! }">
														<input type="hidden" name="processHandoverTop.factoryUnitName" value="${(admin.team.factoryUnit.factoryUnitName)! }">
														${(admin.team.factoryUnit.factoryUnitName)! }
														</div>										
													</div>
													
													<div class="profile-info-row">
													
													    <div class="profile-info-name">生产日期/班次:</div>
														<div class="profile-info-value">
														<input type="hidden" name="processHandoverTop.productDate" value="${admin.productDate}">
														<input type="hidden" name="processHandoverTop.shift" value="${(admin.shift)! }">
														
								       								 ${admin.productDate} &nbsp;&nbsp;&nbsp; 
								       								 <#if (admin.shift == 1)!>
								       								 早 
								       								 <#elseif (admin.shift == 2)!> 
								       								 	白
								       								 <#elseif (admin.shift == 3)!>
								       								 	晚
								       								 <#else>
								       								 </#if>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
						</br>
						<table id="table" class="table table-striped table-bordered">
										<thead>
											<tr>
												<th class="center" style="width:20%">产品名称</th>
												<th class="center" style="width:5%">计划数量</th>
												<th class="center" style="width:10%">产品编号</th>
												<th class="center"style="width:10%">本班随工单编号</th>
												<th class="center"style="width:15%">下班随工单编号</th>
												<th class="center"style="width:10%">零头交接数量</th>
												<th class="center"style="width:10%">异常交接数量</th>
												<th class="center"style="width:5%">模具</th>
												<th class="center"style="width:10%">责任人</th>
												<#if !(show??)>
												<th class="center"style="width:5%">操作</th>
												</#if>
											</tr>
										</thead>
	
										<tbody>
											<#list processHandoverLists?sort_by("matnr") as list>
												<tr>
													<td class="center" style="width:20%">${(list.maktx)! }
													<input type="hidden" name="processHandoverList[${list_index}].id" value="${(list.id)! }">
													<input type="hidden" name="processHandoverList[${list_index}].maktx" value="${(list.maktx)! }">
													</td>
													<td class="center" style="width:5%">${(list.planCount)! }
													<input type="hidden" name="processHandoverList[${list_index}].planCount" value="${(list.planCount)! }">
													</td>
													<td class="center" style="width:10%">${(list.matnr)! }
													<input type="hidden" name="processHandoverList[${list_index}].matnr" value="${(list.matnr)! }">
													</td>
													<td class="center workingCode" style="width:10%"name="workingCode">${(list.workingBillCode)! }
													<input type="hidden" name="processHandoverList[${list_index}].workingBillCode" value="${(list.workingBillCode)! }">
													</td>
													<td class="center" style="width:15%">
													<input type="text" style="width:95%" class="afterWork state_input" name="processHandoverList[${list_index}].afterWorkingBillCode" value="${(list.afterWorkingBillCode)! }"/>
													</td>
														<!--  
														<#if (list.oddHandOverSet!=null && list.oddHandOverSet?size>0)! >
															<#list list.oddHandOverSet as loh>
																<td class="center"><input type="text" class="oddhandOverMount state_input" name="actualMounts" value="${loh.actualBomMount }"/></td>
																<td class="center"><input type="text" class="unhandOverMount state_input" name="unMounts" value="${loh.unBomMount }"/></td>
																<td class="center OddHstate"></td>
															<#break>
															</#list>
														<#else> 	</#if> -->
													<td class="center" style="width:10%"><input type="text" style="width:95%" class="oddhandOverMount state_input " name="processHandoverList[${list_index}].actualHOMount" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"value="${(list.actualHOMount) }"/></td>
													<td class="center" style="width:10%"><input type="text" style="width:95%" class="unhandOverMount state_input "onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" name="processHandoverList[${list_index}].unHOMount" value="${(list.unHOMount) }"/></td>
														<td style="width:5%">
																	<#if !show??>
																	<#list pagerMapList as bl>
																	<#if bl.workingBillCode.searchString == list.workingBillCode>
																			<select id="station"  name="processHandoverList[${list_index}].station">
																			<#if (bl.pager.list?size>0)!>
																			<option value=""></option>
																			<#list bl.pager.list as pagerlist>
																				<option value="${(pagerlist.station)! }" <#if (pagerlist.station == (list.station))!> selected</#if>>${(pagerlist.station)! } </option>
																			</#list>
																			<#else>
																				<#list bl.pager.list as pagerlist>
																					<option value="${(pagerlist.station)! }" <#if (pagerlist.station == (list.station))!> selected</#if>>${(pagerlist.station)! } </option>
																				</#list>
																			</#if>	
																			</select>
																	<#break>
																	</#if>
																	</#list>
																	<#else>
																	${(list.station)! }
																	</#if>
																	</td>
														<td style="width:5%">
														<#if !(show??)>
														<img id="pId" class="img_addbug" title="添加单元信息" alt="添加单元信息" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
														<span id="responsibleName">${(list.responsibleName) }</span>
														<input type="hidden" name="processHandoverList[${list_index}].responsibleName" id="responsibleNa" value="${(list.responsibleName) }" class="formText {required: true}" />
														<input type="hidden" name="processHandoverList[${list_index}].responsibleId" id="responsibleId" value="${(list.responsibleId) }" class="formText {required: true}" /> 			
														</td>
														<td style="width:5%"><a href="javascript:void(0);" class="removeLine">删除</a></td>
														<#else>
														<span id="responsibleName">${(list.responsibleName) }</span>		
														</#if>
														<#list list.oddHandOverSet as bl>
														<input type="hidden" name="oddHandOverList[${list_index}${bl_index }].id" value="${bl.id }">  
														<input type="hidden" name="oddHandOverList[${list_index}${bl_index }].materialAmount" value="${bl.materialAmount }">   
														<input type="hidden" name="oddHandOverList[${list_index}${bl_index }].beforeWokingCode" value="${bl.beforeWokingCode }">
														<input type="hidden" name="oddHandOverList[${list_index}${bl_index }].afterWorkingCode" value="${bl.afterWorkingCode }">
														<input type="hidden" name="oddHandOverList[${list_index}${bl_index }].productAmount" value="${bl.productAmount }">
														<input type="hidden" name="oddHandOverList[${list_index}${bl_index }].bomCode" value="${bl.bomCode }">
														<input type="hidden" name="oddHandOverList[${list_index}${bl_index }].bomDesp" value="${bl.bomDesp }">
														</#list>
												</tr>
											</#list>
										</tbody>
									</table>
									<#if !show??>
									<div class="row buttons col-md-8 col-sm-4 sub-style" style="margin-top:5px;text-align:center">
                                     <button class="btn btn-white btn-default btn-sm btn-round btnsubmit" id="btn_save" type="button" <#if state=="2">disabled</#if>>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>
									</#if>
									<!-- <button class="btn btn-white btn-default btn-sm btn-round" id="btn_confirm" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
									</button> -->
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type="button" style="text-align:center">
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
									</div>
								</div>
							</form>
							
							<!-- add by weitao -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

				<#include "/WEB-INF/template/admin/admin_footer.ftl">
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->
</body>
</html>
<script language="javascript">
function refresh(){
	<#if show??>
	$(".afterWork").attr("readonly","true");
	$(".oddhandOverMount").attr("readonly","true");
	$(".unhandOverMount").attr("readonly","true");
	$(".station").attr("readonly","true");
	</#if>
}
window.refresh();
</script>
<script type ="text/javascript">
function showUnit(num1){
	var title = "选择单元";
	var width="800px";
	var height="632px";
	var content="process_handover!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
		num1.next().text(id[1]);
		num1.next().next().val(id[1]);
		num1.next().next().next().val(id[0]);
		layer.close(index); 
	});
}
   $(function(){
	   $(".img_addbug").click( function() {
			showUnit($(this));
		});
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
				var loginid = $("#loginid").val();
			  $.ajax({
					url:"odd_hand_over!findAfterWorkingCode.action?loginid="+loginid,
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
						var loginid = $("#loginid").val();
					  $.ajax({
							url:"odd_hand_over!findAfterWorkingCode.action?loginid="+loginid,
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
	 $("#btn_save").click(function(){
	 		var dt=$("#inputForm").serialize();
		//	var loginId = $("#loginid").val();
			var productDate = $("#productDate").val();
			var select = $("#sl_sh").val();
			
			if(productDate == ""){
				layer.alert("请选择下班生产日期",{icon: 7});
				return false;
			}
			
			if(select == ""){
				layer.alert("请选择班次",{icon: 7});
				return false;
			}
			var flag = true;
			$(".afterWork").each(function(){
				if($(this).val() == $(this).parent().prev().children().val()){
					flag = false;
					layer.alert("本班随工单不允许与下班随工单一致",{icon: 7});
					return false;
				}
			});
			if( $("table tr:visible").length == 1){
				flag = false;
				layer.alert("请至少保留一条数据",{icon: 7});
				return false;
			}
			$(".oddhandOverMount").each(function(){
				var productAmount = $(this).val();
				var afterworkbill = $(this).parent().prev().children().val();
				if(productAmount != null && productAmount != ""){
					if(afterworkbill == null || afterworkbill == ""){
						flag = false;
						layer.alert("请填写下班随工单",{icon: 7});
						return false;
					}
					
					if($("#station").val()==""){
						flag = false;
						layer.alert("请选择模具",{icon: 7});
						return false;
					};
				}
			});
			if(flag){
				var loginid = $("#loginid").val();
				<#if isAdd??>
				var url = "odd_hand_over!newCreditsubmit.action?loginid="+loginid;
				<#else>
				var url = "odd_hand_over!creditupdate.action?loginid="+loginid;
				</#if>
				credit.creditCard(url,function(data){
					if(data.status=="success"){
						window.location.href="process_handover!list.action";
					}
				},dt);
			}
	 });
	 /*返回*/
		$("#btn_back").click(function(){
			window.history.back();return false;
		});
	 /*删除操作*/
		$(".removeLine").click(
				function()
				{
					//var tr=$(this).parent().parent();
					$(this).parent().parent().remove()
				}
			);
   });
</script>