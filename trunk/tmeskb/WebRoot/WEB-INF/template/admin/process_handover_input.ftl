


<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>管理中心</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
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
<style type="text/css">
	.mymust{color: red;font-size: 10px;}
	.class_label_xfuname{width:200px;line-height: 30px;border:1px solid;border-color: #d5d5d5;}
	.xspan{font-family: 微软雅黑;font-size: 10px;color:red;}
	.add_icon{
		width:20px;
		text-align:center;
	}
	.add_icon i{
		dispaly:block;
		cursor:pointer;
	}
	.width_input{
	width:100%;
	}
</style>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
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
					<li class="active">工序交接</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal validate" id="inputForm" method="post"
								action="" role="form">
								<#if isAdd??>
								<div class="widget-box transparent">
									<!-- <input type="text" name="processHandoverTop.processName" id="processName">
									<select name="processHandoverTop.processCode" id="process">
										<#list processList as pl>
											<option value="${(pl.processCode)! }">${(pl.processName)! }</option>
										</#list> 
									</select> -->
									<div class="div_top">
										 下班生产日期:
                                    <input type="text" id="productDate" name="" value="" class="datePicker formText"/>
                                   &nbsp;&nbsp;&nbsp;&nbsp;
                                	        班次:
                                     <select name="shift"id="sl_sh">
                                     	<option value="" <#if (admin.shift == "")!> selected</#if>></option>
                                     	<option value="1" <#if (admin.shift == 1)!> selected</#if>>早</option>
										<option value="2" <#if (admin.shift == 2)!> selected</#if>>白</option>
										<option value="3" <#if (admin.shift == 3)!> selected</#if>>晚</option>
                              	     </select>
                                   &nbsp;&nbsp;&nbsp;&nbsp;
                                   <!--  
                                   	   工序选择:
                                     <select name="shift"id="sl_sh">
                                     	<#list processList as list>
                                     	<option value="${list.processName }">${list.processName }</option>
                                     	</#list>
                                     </select>
                                     &nbsp;&nbsp;&nbsp;&nbsp;
                                     <input type="checkbox" id="qualified" name="processHandoverTop.processType" value="合格" border: 1px solid black">合格
                                     &nbsp;&nbsp;&nbsp;&nbsp;
                                     <input type="checkbox" id="repair" name="processHandoverTop.processType" value="返修" border: 1px solid black">返修 -->
										<div class="widget-header">
											<h4 class="widget-title lighter">工序类型选择</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="collapse"></a>

											</div>
										</div>
										
										<div class="widget-body">
											<div class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="profile-user-info profile-user-info-striped">
													<div class="profile-info-row">
														<div class="profile-info-name">工序选择：</div>
														<div class="profile-info-value">
															<select name="processHandoverTop.processName">
						                                     	<#list processList as list>
						                                     	<option value="${list.processName }">${list.processName }</option>
						                                     	</#list>
						                                     </select>
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name">合格：</div>
														<div class="profile-info-value" style="width:300px;">
															<input type="checkbox" id="qualified" style="width:18px;height:18px;" name="processHandoverTop.processType" value="合格" border: 1px solid black">
														</div>
														<div class="profile-info-name" >返修：</div>
														<div class="profile-info-value">
															<input type="checkbox" id="repair" style="width:18px;height:18px;"name="processHandoverTop.processType" value="返修" border: 1px solid black">
														</div>
													</div>
												</div>
											</div>
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
														<input type="hidden" name="processHandoverTop.werk" value="${(admin.team.factoryUnit.workShop.factory.factoryName)!}">
														<input type="hidden" name="processHandoverTop.wshop" value="${(admin.team.factoryUnit.workShop.workShopName)!}">
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
									<#else>
									<div class="widget-header">
											<h4 class="widget-title lighter">工序类型选择</h4>
											<div class="widget-toolbar no-border">
												<a href="#" data-action="collapse"></a>

											</div>
										</div>
										
										<div class="widget-body">
											<div class="widget-main padding-6 no-padding-left no-padding-right">
												<div class="profile-user-info profile-user-info-striped">
													<div class="profile-info-row">
														<div class="profile-info-name">工序选择：</div>
														<div class="profile-info-value">
															<select name="processHandoverTop.processName"<#if show??>disabled="true"</#if>>
						                                     	<option value="${processHandoverTop.processName }">${processHandoverTop.processName }</option>
						                                    </select>
														</div>
													</div>
													<div class="profile-info-row">
														<div class="profile-info-name">合格：</div>
														<div class="profile-info-value" style="width:300px;">
															<input type="checkbox" <#if (processHandoverTop.processType?index_of("合格")!=-1)!>checked </#if> id="qualified" style="width:18px;height:18px;border: 1px solid black;" name="processHandoverTop.processType" value="合格" <#if show??>disabled="true"</#if>>
														</div>
														<div class="profile-info-name" >返修：</div>
														<div class="profile-info-value">
															<input type="checkbox" <#if (processHandoverTop.processType?index_of("返修")!=-1)!>checked </#if> id="repair" style="width:18px;height:18px;border: 1px solid black;"name="processHandoverTop.processType" value="返修"  <#if show??>disabled="true"</#if>>
														</div>
													</div>
												</div>
											</div>
										</div>
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
														<input type="hidden" name="processHandoverTop.id" value="${(processHandoverTop.id)!}">
														<input type="hidden" name="processHandoverTop.werk" value="${(processHandoverTop.werk)!}">
														<input type="hidden" name="processHandoverTop.wshop" value="${(processHandoverTop.wshop)!}">
															${(processHandoverTop.werk)!} &nbsp;&nbsp;&nbsp;    
															${(processHandoverTop.wshop)!}</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">单元：</div>
														<div class="profile-info-value" >
														<input type="hidden" name="processHandoverTop.factoryUnitCode" value="${(processHandoverTop.factoryUnitCode)! }">
														<input type="hidden" name="processHandoverTop.factoryUnitName" value="${(processHandoverTop.factoryUnitName)! }">
														${(processHandoverTop.factoryUnitName)! }
														</div>										
													</div>
													
													<div class="profile-info-row">
													
													    <div class="profile-info-name">生产日期/班次:</div>
														<div class="profile-info-value">
														<input type="hidden" name="processHandoverTop.productDate" value="${(processHandoverTop.productDate)!}">
														<input type="hidden" name="processHandoverTop.shift" value="${(processHandoverTop.shift)! }">
														
								       								 ${processHandoverTop.productDate} &nbsp;&nbsp;&nbsp; 
								       								 <#if (admin.shift == 2)!> 
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
									</#if>
								<div class="operateBar">
									<!-- <div class="form-group">
                                    <label class="col-sm-1 col-md-offset-1"style="text-align:right">物料编码:</label>
										<div class="col-sm-3">
											<input type="text" name="info"
												class="input input-sm form-control" value="${(info)! }"
												id="info">
										</div>
										<label class="col-sm-1 col-md-offset-1"style="text-align:right">物料描述:</label>
										<div class="col-sm-3">
											<input type="text" name="desp"
												class="input input-sm form-control" value="${(desp)! }"
												id="desp">
										</div>
									</div>
									<div class="form-group">
									<label class="col-sm-1 col-md-offset-1"style="text-align:right">单元:</label>
										<div class="col-sm-3">
										<img id="img_faunit" title="单元" alt="单元" style="cursor:pointer" src="/template/shop/images/add_bug.gif" />
										<span id="infoNames" >${(infoName)! }</span>
										<input type="hidden" name="infoName" id="infoName" value="${(infoName)! }">
										<input type="hidden" id="infoId" name="infoId" value="${(infoId)! }" class="col-xs-10 col-sm-5 " />
										
										</div>
										<label class="col-sm-1 col-md-offset-1"style="text-align:right">库位:</label>
										<div class="col-sm-3">
											<input type="text" name="position"
												class="input input-sm form-control " value="${(position)! }"
												id="position"/>
												
										</div>
									</div>
									<div class="form-group" style="text-align:center">
										<a id="searchButton1"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-filter blue"></i> 搜索
										</a>
									</div> -->
									<div class="profile-user-info profile-user-info-striped">
								  			<div class="profile-info-row">
									<table id="" class="table table-striped table-bordered table-hover">
								    			
													<tr>
														<th></th>
														<th class="tabth">产品名称</th>
														<th class="tabth">计划数量</th>
														<th class="tabth">产品编号</th>
														<th class="tabth">上班随工单</th>
														<th class="tabth">下班随工单</th>
														<th class="tabth">成品数</th>
														<th class="tabth">责任人</th>
														<th class="tabth mblnr"style="display:none">物料凭证号</th>
													</tr>
														<#list processHandoverLists?sort_by("matnr") as list>
															<#if show??>
																<tr>
																	<td class="add_icon">
																	<i class="i_plus ace-icon fa fa-plus blue fa-2x" id="i_plus"></i>
																	<i class="i_minus ace-icon fa fa-minus blue fa-2x"id="i_minus" style="display:none"></i>
																	</td>
																	<td>${(list.maktx)! }
																	<input type="hidden" name="processHandoverList[${list_index}].id" value="${(list.id)! }">
																	<input type="hidden" name="processHandoverList[${list_index}].maktx" value="${(list.maktx)! }">
																	</td>
																	
																	<td>${(list.planCount)! }
																	<input type="hidden" name="processHandoverList[${list_index}].planCount" value="${(list.planCount)! }">
																	</td>
																	
																	<td>${(list.matnr)! }
																	<input type="hidden" name="processHandoverList[${list_index}].matnr" value="${(list.matnr)! }">
																	</td>
																	
																	<td>${(list.workingBillCode)! }
																	<input type="hidden" name="processHandoverList[${list_index}].workingBillCode" value="${(list.workingBillCode)! }">
																	</td>
																	<#if (list.afterWorkingBillCode)!??>
																	<td>${(list.afterWorkingBillCode)! }</td>
																	<input type="hidden" name="processHandoverList[${list_index}].afterWorkingBillCode" value="${(list.afterWorkingBillCode)! }">
																	<#else>
																	<input type="text" name="processHandoverList[${list_index}].afterWorkingBillCode" value="">
																	</#if>
																	<td ><input type="text" name="processHandoverList[${list_index}].productAmount" class="show_input productAmount {number:true,messages:{number:'*请输入正确金额'}}" style="padding:2px 2px ;"value="${(list.productAmount)!''}"></td>
																	<td >${(list.responsibleName)! }
																	<input type="hidden" name="processHandoverList[${list_index}].responsibleName" value="${(list.responsibleName)! }">
																	</td>
																	<td class="mblnr" style="display:none">${(list.mblnr)! }
																	</td>
																</tr>
																<tr class="tableson"  style="display:none;">
																	<td></td>
																	<td colspan=8>
																		<table id="" class="table table-striped table-bordered table-hover" style="width:100%;margin-bottom: 0px; ">
																		<tr>
																				<th class="">子件名称</th>
																				<th class="">条子数量</th>
																				<th class="">产品数量</th>
																				<th class="">合格数量</th>
																				<th class="">返修数量</th>
																				<th class="">裁切倍数</th>
																				<th class="">裁切后正常交接数量</th>
																				<th class="">裁切后返修交接数量</th>
																				<th class="">实际正常交接数量</th>
																				<th class="">实际返修交接数量</th>
																				
																		</tr>
																		<#list list.processHandoverSonSet as bl>
																			<tr>
																				<td style="width:25%;">
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].id" value="${bl.id }"> 
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].beforeWorkingCode" value="${bl.beforeWorkingCode }"> 
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].bomCode" value="${bl.bomCode }">
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].bomDesp" value="${bl.bomDesp }">
																				${bl.bomCode } &nbsp;&nbsp;&nbsp;${bl.bomDesp }
																				</td>
																				<td style="width:5%;">${(bl.materialAmount)! }
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].materialAmount"value="${(bl.materialAmount)! }">
																				</td>
																				<td style="width:5%;">${(bl.productAmount)! }
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].productAmount"value="${(bl.productAmount)! }">
																				</td>
																				<td style="width:10%;"><input type="text"  class=" show_input  width_input {number:true,messages:{number:'*请输入正确金额'}}" name="processHandoverSonList[${list_index}${bl_index }].bomAmount"  style="padding:2px 2px;"value="${(bl.bomAmount)! }">
																				</td>
																				<td  style="width:10%;"><input type="text"  class="show_input  width_input {number:true,messages:{number:'*请输入正确数量'}}" name="processHandoverSonList[${list_index}${bl_index }].repairNumber"  style="padding:2px 2px ;"value="${(bl.repairNumber)! }">
																				</td>
																				<td style="width:5%;">${bl.cqsl }
																				<input type="hidden" class="show_input cqsl " name="processHandoverSonList[${list_index}${bl_index }].cqsl" value="${(bl.cqsl)! }"/>
																				</td>
																				<td style="width:10%;">
																				<input type="text" id="cqamount" readonly= "true" class="cqamount width_input" name="processHandoverSonList[${list_index}${bl_index }].cqamount"value="${(bl.cqamount)! }"/>
																				</td>
																				<td style="width:10%;">
																				<input type="text" id="cqrepairamount" readonly= "true" class="cqrepairamount width_input" name="processHandoverSonList[${list_index}${bl_index }].cqrepairamount"value="${(bl.cqrepairamount)! }"/>
																				</td>
																				<td style="width:10%;">
																				<input type="hidden" class="amountinp" name="processHandoverSonList[${list_index}${bl_index }].amount"value="${(bl.amount)! }"/>
																				<span class="amount">${(bl.amount)! }</span>
																				</td>
																				<td style="width:10%;">
																				<input type="hidden" class="repairamountinp" name="processHandoverSonList[${list_index}${bl_index }].repairamount"value="${(bl.repairamount)! }"/>
																				<span class="repairamount">${(bl.repairamount)! }</span>
																				</td>
																			</tr>
																		</#list> 
																		
																		</table>
																	</td>
																</tr>
															<#else>
															<tr>
																	<td class="add_icon">
																	<i class="i_plus ace-icon fa fa-plus blue fa-2x" id="i_plus"></i>
																	<i class="i_minus ace-icon fa fa-minus blue fa-2x"id="i_minus" style="display:none"></i>
																	</td>
																	<td>${(list.maktx)! }
																	<input type="hidden" name="processHandoverList[${list_index}].id" value="${(list.id)! }">
																	<input type="hidden" name="processHandoverList[${list_index}].maktx" value="${(list.maktx)! }">
																	</td>
																	
																	<td>${(list.planCount)! }
																	<input type="hidden" name="processHandoverList[${list_index}].planCount" value="${(list.planCount)! }">
																	</td>
																	
																	<td>${(list.matnr)! }
																	<input type="hidden" name="processHandoverList[${list_index}].matnr" value="${(list.matnr)! }">
																	</td>
																	
																	<td class="workingCode" name="workingCode" class="workingCode">${(list.workingBillCode)! }
																	<input type="hidden" name="processHandoverList[${list_index}].workingBillCode" class="workingCode1" value="${(list.workingBillCode)! }">
																	</td>
																	<!--  
																	<#if (list.afterWorkingBillCode)!??> -->
																	<td class="center" ><input type="text" class="afterWork state_input"
																	 name="processHandoverList[${list_index}].afterWorkingBillCode" value="${(list.afterWorkingBillCode)! }"/></td>
																	<!-- 
																	<#else>
																	<input type="text" name="processHandoverList[${list_index}].afterWorkingBillCode" value="">
																	</#if>  -->
																	<td style="width:10%"><input type="text" name="processHandoverList[${list_index}].productAmount" class="bomAmount show_input productAmount {number:true,messages:{number:'*请输入正确金额'}}" style="width:100%"value="${(list.productAmount)!''}" readonly></td>
																	<td>
																	<img id="pId" class="img_addbug" title="添加责任人信息" alt="添加责任人信息 style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" />
																	<span id="responsibleName">${(list.responsibleName) }</span>
																	<input type="hidden" name="processHandoverList[${list_index}].responsibleName" id="responsibleNa" value="${(list.responsibleName) }" class="formText {required: true}" />
																	<input type="hidden" name="processHandoverList[${list_index}].responsibleId" id="responsibleId" value="${(list.responsibleId) }" class="formText {required: true}" /> 
																	</td>
																	<td class="mblnr" style="display:none">${(list.mblnr)! }
																	</td>
																</tr>
																<tr class="tableson"  style="display:none;">
																	<td></td>
																	<td colspan=8>
																		<table id="" class="table table-striped table-bordered table-hover" style="width:95%;margin-bottom: 0px; ">
																		<tr>
																				<th class="">子件名称</th>
																				<th class="">条子数量</th>
																				<th class="">产品数量</th>
																				<th class="">合格数量</th>
																				<th class="">返修数量</th>
																				<th class="">裁切倍数</th>
																				<th class="">裁切后正常交接数量</th>
																				<th class="">裁切后返修交接数量</th>
																				<th class="">实际正常交接数量</th>
																				<th class="">实际返修交接数量</th>
																				
																		</tr>
																		<#list list.processHandoverSonSet as bl>
																			<tr>
																				<td style="width:25%"> 
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].id" value="${bl.id }"> 
																				 <input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].beforeWorkingCode" value="${bl.beforeWorkingCode }"> 
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].bomCode" value="${bl.bomCode }">
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].bomDesp" value="${bl.bomDesp }">
																				${bl.bomCode } &nbsp;&nbsp;&nbsp;${bl.bomDesp }
																				</td>
																				<td style="width:5%">${(bl.materialAmount)! }
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].materialAmount"value="${(bl.materialAmount)! }">
																				</td>
																				<td style="width:5%">${(bl.productAmount)! }
																				<input type="hidden" name="processHandoverSonList[${list_index}${bl_index }].productAmount"value="${(bl.productAmount)! }">
																				</td>
																				<td style="width:10%"><input type="text"  readonly= "true" class=" bomAmount show_input width_input bomAmount {number:true,messages:{number:'*请输入正确数量'}}" name="processHandoverSonList[${list_index}${bl_index }].bomAmount"  style="padding:2px 2px ;"value="${(bl.bomAmount)! }">
																				</td>
																				<td style="width:10%"> <input type="text"  readonly= "true" class=" repairNumber show_input  width_input {number:true,messages:{number:'*请输入正确数量'}}" name="processHandoverSonList[${list_index}${bl_index }].repairNumber"  style="padding:2px 2px ;"value="${(bl.repairNumber)! }">
																				</td>
																				<td style="width:5%">${bl.cqsl }
																				<input type="hidden" class="show_input cqsl" name="processHandoverSonList[${list_index}${bl_index }].cqsl" value="${(bl.cqsl)! }"/>
																				</td>
																				<td style="width:10%">
																				<input type="text" id="cqamount" class="width_input cqamount" name="processHandoverSonList[${list_index}${bl_index }].cqamount"value="${(bl.cqamount)! }" readonly/>
																				</td >
																				<td style="width:10%">
																				<input type="text" id="cqrepairamount"  class="repairNumber width_input cqrepairamount" name="processHandoverSonList[${list_index}${bl_index }].cqrepairamount"value="${(bl.cqrepairamount)! }"readonly/>
																				</td>
																				<td style="width:10%">
																				<input type="hidden" class="amountinp" name="processHandoverSonList[${list_index}${bl_index }].amount"value="${(bl.amount)! }"/>
																				<span class="amount">${(bl.amount)! }</span>
																				</td>
																				<td style="width:10%">
																				<input type="hidden" class="repairamountinp" name="processHandoverSonList[${list_index}${bl_index }].repairamount"value="${(bl.repairamount)! }"/>
																				<span class="repairamount">${(bl.repairamount)! }</span>
																				</td>
																			</tr>
																		</#list> 
																		
																		</table>
																	</td>
																</tr>
															</#if>
														</#list>
										</table>
										</div>
										</div>
								</div>
							</form>
							<div class="row buttons col-md-8 col-sm-4 sub-style" style="margin-top:5px;text-align:center">
                                     <button class="btn btn-white btn-default btn-sm btn-round" id="btn_save" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>
									<!-- <button class="btn btn-white btn-default btn-sm btn-round" id="btn_confirm" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
									</button> -->
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
									</div>
							<!-- <div class="row">
								<div class="col-xs-12">
									<table id="grid-table"></table>

									<div id="grid-pager"></div>
								</div>
							</div> -->
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
<script type="text/javascript">
function showUnit(num1){
	var title = "选择单元";
	var width="800px";
	var height="632px";
	var content="process_handover!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var work=iframeWin.getGridId();
		var id=work.split(",");
//		$("#responsibleName").text(id[1]);
//		$("#responsibleNa").val(id[1]);//单元名称
//		$("#responsibleId").val(id[0]);
		num1.next().text(id[1]);
		num1.next().next().val(id[1]);
		num1.next().next().next().val(id[0]);
		layer.close(index); 
	});
}
	$(function(){
		if($("#qualified").is(':checked')){
			$(".bomAmount").removeAttr("readonly");
			$(".cqamount").removeAttr("readonly");
		}
		if($("#repair").is(':checked')){
			$(".repairNumber").removeAttr("readonly");
		}
		$(".img_addbug").click( function() {
			showUnit($(this));
		});
		$("#qualified").click(function(){
			if($(this).is(':checked')){
			$(".bomAmount").removeAttr("readonly");
			$(".cqamount").removeAttr("readonly");
			}else{
				$(".bomAmount").attr("readonly","true");
				$(".cqamount").attr("readonly","true");
			}
		});
		$("#repair").click(function(){
			if($(this).is(':checked')){
				$(".repairNumber").removeAttr("readonly");
				}else{
					$(".repairNumber").attr("readonly","true");
				}
		});
		var $cqamount = $(".cqamount");//裁切后正常交接数量
		var $cqrepairamount = $(".cqrepairamount");//裁切后返修交接数量
		
		$cqamount.change(function(){//裁切后正常交接数量改变事件
			var sVal = $(this).val();//裁切后正常数量
			var sCqsl = $(this).parent().parent().find(".cqsl").val();//裁切倍数
			var sv = floatDiv(sVal,sCqsl);
			if(isNaN(sv)) sv = 0;
			var sv = setScale(sv,2,"");//保留两位小数
			$(this).parent().parent().find(".amountinp").val(sv);
			$(this).parent().parent().find(".amount").text(sv);
		});
		
		$cqrepairamount.change(function(){//裁切后返修交接数量改变事件
			var sVal = $(this).val();//裁切后返修交接数量
			var sCqsl = $(this).parent().parent().find(".cqsl").val();//裁切倍数
			var sv = floatDiv(sVal,sCqsl);
			if(isNaN(sv)) sv = 0;
			var sv = setScale(sv,2,"");//保留两位小数
			$(this).parent().parent().find(".repairamountinp").val(sv);
			$(this).parent().parent().find(".repairamount").text(sv);
		});
		
		<#if show??>
		$(".show_input").attr("readonly","readonly");
		 $("#btn_save").css("display","none");
		 $(".mblnr").css("display","table-cell");
		</#if>
		
		$(".i_plus").click(function(){
			$(this).css("display","none");
			$(this).next().css("display","block");
			$(this).parent().parent().next().css("display","table-row");
			
		});
		$(".i_minus").click(function(){
			$(this).css("display","none");
			$(this).prev().css("display","block");
			$(this).parent().parent().next().css("display","none");
		});
		$(".productAmount").change(function(){
			var $productA = $(this);
			var productAmount = $productA.val();
			if(productAmount==""){
				productAmount = 0;
			}
			$productA.parent().parent().next().find(".bomAmount").each(function(){
				var materialAmount = $(this).parent().prev().prev().text();
				if(materialAmount==""){
					materialAmount = 0;
				}
				var productAmountson = $(this).parent().prev().text();
				if(productAmountson=="" || productAmountson==0){
					$(this).val("");
				}else{
					var accDivA = floatDiv(materialAmount,productAmountson)
					var accMulA = floatMul(accDivA,productAmount);
					$(this).val(accMulA.toFixed(2));
				}
			});
		});
		 $("#btn_save").click(function(){
		 		var dt=$("#inputForm").serialize();
				var loginId = $("#loginid").val();
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
				$(".workingCode1").each(function(){
					if($(this).val() == $(this).parent().next().children().val()){
						flag = false;
						layer.alert("上班随工单不允许与下班随工单一致",{icon: 7});
						return false;
					}
				});
				<#if !(isAdd??)>
				$(".productAmount").each(function(){
					var productAmount = $(this).val();
					var afterworkbill = $(this).parent().prev().prev().prev().children().val();
					if(productAmount != null && productAmount != ""){
						if(afterworkbill == null || afterworkbill == ""){
							flag = false;
							layer.alert("请填写下班随工单",{icon: 7});
							return false;
						}
					}
				});
				</#if>
				if(flag){
				<#if isAdd??>
				var url="process_handover!creditsubmit.action?loginid="+loginId;
				<#else>
				var url="process_handover!creditupdate.action?loginid="+loginId;
				</#if>
				credit.creditCard(url,function(data){
					if(data.status=="success"){
						layer.alert(data.message, {icon: 6},function(){
							window.location.href="process_handover!list.action";
						}); 
					}else if(data.status=="error"){
						layer.alert(data.message, {
					        closeBtn: 0,
					        icon:5,
					        skin:'error'
						    });
						}					
					},dt)
				}
		}); 
		 /*返回*/
			$("#btn_back").click(function(){
				window.history.back();
			});
		 var process = $("#process option:checked").text();
		 $("#processName").val(process);
		 
		 $("#process").change(function(){
			 var process = $("#process option:checked").text();
			 $("#processName").val(process);
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
	});
</script>