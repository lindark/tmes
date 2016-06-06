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
															<th><i class="ace-icon fa fa-caret-right blue"></i>产品名称
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>计划数量
															</th>

															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>产品编号</th>
															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>随工单编号</th>
																<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>模具</th>
																<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>交接状态</th>
															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>条码打印</th>
														</tr>
													</thead>

													<tbody id="wbload">
	<input type="hidden" value="${info}" id="info">
														<#list workingbillList as list>
														<tr>
															<td><input type="checkbox" class="ckbox"
																name="WorkingBill.workingBillCode" value="${list.id}" />&nbsp;
																<a href="javascript:void(0);" class="a matkx" <#if list.diffamount??>style="color:red"</#if>>${list.maktx}</a>&nbsp;&nbsp;
																<a href="javascript:void(0);" class="a moudle" <#if list.moudle=="">style="color:red"</#if>>[添加模具组号]</a>&nbsp;&nbsp;${(list.moudle)!}
															</td>

															<td><b class="green">${list.planCount}</b>
															</td>

															<td class="hidden-480">${list.matnr}</td>
															<td class="hidden-480">${list.workingBillCode}</td>
															<td class="hidden-480">${(list.module)!}</td>
															<td class="hidden-480">
																<#if list.isHand=="Y">
																	已交接
																<#else>
																	未交接
																</#if>
															</td>
															<td class="hidden-480"><input type="text" class="input-sm col-sm-2 part"/>&nbsp; 
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