<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑质量问题单 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/unusual/js/quality.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}

.deleteImage,#addImage,#removeImage {
	cursor: pointer;
}

.profile-user-info-striped {
	border: 0px;
}
</style>
</head>
<body class="no-skin input">

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
					<li class="active"><#if isAdd??>添加质量问题单<#else>编辑质量问题单</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validatecredit"
								action="<#if isAdd??>quality!creditsave.action<#else>quality!creditupdate.action</#if>"
								method="post">
								<input type="hidden" name="id" id="qualityId" value="${id}" />
								<input type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>
										<li><a href="#tabs-2">单据日志</a></li> <#if isAdd??> <#else>
										<li><a href="#tabs-3">整改情况跟踪</a></li> </#if>
										<li><a href="#tabs-4">相关单据</a></li>
									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">

												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
													<#if isAdd??> <select class="chosen-select"
														name="quality.products" id="productChoice"
														style="width:200px;">
														<option value="">请选择...</option>
														<#list workList as list>	
																								
														<option value="${list.matnr}" label="${list.maktx},${list.id}"　<#if
															(list.matnr == quality.products)!>
															selected</#if>>${list.maktx}</option> 														
															</#list>
													</select> <#else> ${(product)!} </#if>
                                                    
													<!-- 
													<#if isAdd??> <img id="productId" class="img_addbug"
														title="添加产品信息" alt="添加产品信息" style="cursor:pointer"
														src="${base}/template/shop/images/add_bug.gif" />
													<span id="productName1"></span> <input type="hidden"
														name="quality.products.id" id="productNa" value=""
														class="formText {required: true}" /> <#else>
													${(quality.products.productsName)!} </#if> -->

												</div>

												<div class="profile-info-name">生产工序</div>

												<div class="profile-info-value">
													<#if isAdd??> <select name="quality.process" style="width:163px;"
														id="processName">
															<option value="">请选择...</option>
														<#list processList as list>	
																								
														<option value="${list.dictkey}" label="${list.dictvalue}">${list.dictvalue}</option> 														
															</#list>
													</select> <#else> ${(quality.processName)!} </#if>
												</div>

											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">产品bom</div>
												<div class="profile-info-value">
												       <#if isAdd??> <img id="productId" class="img_addbug"
															title="添加产品信息" alt="添加产品信息" style="cursor:pointer"
															src="${base}/template/shop/images/add_bug.gif" />
														<span id="productName1"></span> <input type="hidden"
															name="quality.bom" id="productNa" value=""
															/> <#else>
														${(bomproduct)!} </#if>
												</div>
												<div class="profile-info-name">台机号</div>
												<div class="profile-info-value">
													<input type="text" name="quality.machineNumber"
														value="${(quality.machineNumber)!}"
														class=" input input-sm  formText {required: false}" />
												</div>
											</div>

											<div class="profile-info-row">

												<div class="profile-info-name">班组</div>

												<div class="profile-info-value">
													<#if isAdd??>
													<!--  <input type="text" name="quality.team.teamName"
														value="${(admin.team.teamName)!}"
														class=" input input-sm  formText {required: true}" readonly="readonly"/> -->
													<span>${(admin.team.teamName)!}</span> <input
														type="hidden" name="quality.team.id"
														value="${(admin.team.id)!}"
														class="formText {required: true}" /> <#else>
													${(quality.team.teamName)!} </#if>
												</div>
												<div class="profile-info-name">挤出批次</div>
												<div class="profile-info-value">
													<input type="text" name="quality.extrusionBatches"
														value="${(quality.extrusionBatches)!}"
														class=" input input-sm  formText {digits: true}" />
												</div>
											</div>


											<div class="profile-info-row">
												<div class="profile-info-name">接收人</div>
												<div class="profile-info-value">
													<#if isAdd??> <img id="receive" class="img_addbug"
														title="添加人员信息" alt="添加人员信息" style="cursor:pointer"
														src="${base}/template/shop/images/add_bug.gif" />
													<!-- <button type="button" class="btn btn-xs btn-info"
														id="receive" data-toggle="button">选择</button> -->
													<span id="receiveName1"></span> <input type="hidden"
														name="quality.receiver.id" id="receiveNa" value=""
														class="formText {required: true}" /> <#else>
													${(quality.receiver.name)!} </#if><span>*</span>
												</div>
												<div class="profile-info-name">质量工程师</div>
												<div class="profile-info-value">
													<img id="receive1" class="img_addbug" title="添加人员信息1" alt="添加人员信息1" style="cursor:pointer" src="${base}/template/shop/images/add_bug.gif" /> 
													<span id="receiveName2">${(quality.engineer.name)!}</span>
													<div style="display:inline-block;margin-left:10px;" id="delete-opt-container">
														<#if !isAdd??> 
															<#if quality.engineer?if_exists ><a href="javascript:void(0);" onclick="deleteEngineer();">删除</a></#if>
														</#if>
													</div>
													<input type="hidden" name="quality.engineer.id" id="receiveNa1" value="${(quality.engineer.id)!}" class="formText {}" /> 													
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">抽检数量</div>
												<div class="profile-info-value">
													<input type="text" name="quality.samplingAmont"
														value="${(quality.samplingAmont)!}"
														class=" input input-sm  formText {required: true, digits: true}" /><span>*</span>
												</div>
												<div class="profile-info-name">缺陷数量</div>
												<div class="profile-info-value">
													<input type="text" name="quality.failAmont"
														value="${(quality.failAmont)!}"
														class=" input input-sm  formText {required: true, digits: true}" /><span>*</span>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">报告时间</div>
												<div class="profile-info-value">
													<input type="text" name="quality.createDate"
														value="${(quality.createDate)!}"
														class="formText {required: true,date:'date',dateFormat: 'yy-mm-dd'} datePicker" /><span>*</span>
												</div>
												<div class="profile-info-name">计划完成时间</div>
												<div class="profile-info-value">
													<input type="text" name="quality.overTime"
														value="${(quality.overTime)!}"
														class="formText {required: true,date:'date',dateFormat: 'yy-mm-dd'} datePicker" /><span>*</span>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">生产日期</div>
												<div class="profile-info-value">
													
													<#if isAdd?? >
														${(admin.productDate)!}
													<#else>
														${(quality.productDate)!}													
													</#if>
														
												</div>
												<div class="profile-info-name">班次</div>
												<div class="profile-info-value">
													<#if isAdd?? >
														<#if (admin.shift == 1)!> 早</#if>
														<#if (admin.shift == 2)!> 白</#if>
														<#if (admin.shift == 3)!> 晚</#if>																												
													<#else>														
														<#if (quality.shift == 1)!> 早</#if>
														<#if (quality.shift == 2)!> 白</#if>
														<#if (quality.shift == 3)!> 晚</#if>																											
													</#if>												
													
												</div>
											</div>
										</div>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">质量问题描述</div>
												<!--<div class="profile-info-value">
													<textarea name="quality.problemDescription"
														style="width:600px;" class="formText {required: true}">${(quality.problemDescription)!}</textarea>
												</div>-->
												
												<div class="profile-info-value">
													<#list problemDescriptionList as list>											
														<label><input value="${list.id}"  type="checkbox" name="quality.qualityProblemDescription" class="input mycheckbox formText {}"/>${ (list.problemDescription)! }</label>														
													</#list>
												</div>		
											</div>
											
											
										</div>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">质量问题描述</div>
												
												<div class="profile-info-value">
													<input type="text" name="quality.writeQualityPromblem"
														value="${(quality.writeQualityPromblem)!}"
														class=" input input-sm  formText" />
												</div>
											</div>
										</div>
										<#if isAdd??> <#else>
										<div
											class="profile-user-info profile-user-info-striped access"
											data-access-list="rectificationScheme">
											<div class="profile-info-row">
												<div class="profile-info-name">车间整改方案</div>
												<div class="profile-info-value">
													<textarea name="quality.rectificationScheme"
														style="width:600px;">${(quality.rectificationScheme)!}</textarea>
												</div>
											</div>
										</div>

										<div
											class="profile-user-info profile-user-info-striped access"
											data-access-list="engineerOpinion">
											<div class="profile-info-row">
												<div class="profile-info-name">工程师意见</div>
												<div class="profile-info-value">
													<textarea name="quality.engineerOpinion"
														style="width:600px;">${(quality.engineerOpinion)!} </textarea>
												</div>
											</div>
										</div>
										</#if>
							</form>
							<!--weitao end modify-->
							<div class="buttonArea">

								<#if isAdd??>
								<button class="btn btn-white btn-default btn-sm btn-round"
									id="completeQuality" type=button>
									<i class="ace-icon glyphicon glyphicon-check"></i> 刷卡提交
								</button>
								&nbsp;&nbsp;
								<button class="btn btn-white btn-default btn-sm btn-round"
									id="returnQuality" type=button>
									<i class="ace-icon fa fa-home"></i> 返回
								</button>
								<#else>
								<button class="btn btn-white btn-default btn-sm btn-round"
									id="completeQuality" type=button>
									<i class="ace-icon glyphicon glyphicon-check"></i> 刷卡提交
								</button>
								&nbsp;&nbsp;
								<button class="btn btn-white btn-default btn-sm btn-round"
									id="checkQuality" type=button>
									<i class="ace-icon glyphicon glyphicon-ok"></i> 刷卡回复
								</button>
								&nbsp;&nbsp;

								<button class="btn btn-white btn-default btn-sm btn-round"
									id="closeQuality" type=button>
									<i class="ace-icon fa fa-cloud-upload"></i> 刷卡关闭
								</button>
								&nbsp;&nbsp;

								<button class="btn btn-white btn-default btn-sm btn-round"
									id="returnQuality" type=button>
									<i class="ace-icon fa fa-home"></i> 返回
								</button>
								</#if>
							</div>

						</div>

						<form id="inputForm1" action="flowing_rectify!creditsave.action"
							method="post">

							<table id="tabs-2" class="inputTable tabContent">
								<tbody>
									<tr class="title">
										<th>时间</th>
										<th>内容</th>
										<th>修改人</th>
									</tr>
									<#list (quality.unusualLogSet)! as list>
									<tr>
										<td>${(list.createDate)!}</td>
										<td>${(list.info)!}</td>
										<td>${(list.operator.name)!}</td>
									</tr>
									</#list>
								</tbody>
							</table>
							<#if isAdd??> <#else>

							<div id="tabs-3">
								<table id="sample-table-1"
									class="table table-striped table-bordered table-hover access"
									data-access-list="flowingRec">
									<thead>
										<tr>
											<th>内容</th>
										</tr>
									</thead>

									<tbody>
										<#if
										(quality==null||quality.flowingRectify==null||(quality.flowingRectify)!?size==0)>
										<tr class="zg">
											<td><div>
													<input type="hidden" name="flowingId" value="" />
													<textarea name="flowingRectify.content"
														style="width:600px;" class="text">${(list.content)!}</textarea>
													&nbsp;&nbsp;&nbsp;<a class="save" style="cursor:pointer">刷卡保存</a>&nbsp;&nbsp;<a
														style="cursor:pointer" class="edit">编辑</a>&nbsp;&nbsp;<a
														class="deleteButton" style="cursor:pointer">删除</a>
												</div>
											</td>
										</tr>
										<#else> <#list (quality.flowingRectify)! as list>
										<tr class="zg">
											<td>
												<div>
													<input type="hidden" name="flowingId" value="${(list.id)!}" />
													<textarea name="flowingRectify.content"
														style="width:600px;" class="text">${(list.content)!}</textarea>
													&nbsp;&nbsp;&nbsp;<a class="save" style="cursor:pointer">刷卡保存</a>&nbsp;&nbsp;<a
														style="cursor:pointer" class="edit">编辑</a>&nbsp;&nbsp;<a
														class="deleteButton" style="cursor:pointer">删除</a>
												</div>
												</div>
											</td>
										</tr>
										</#list> </#if>
										<tr>
											<td colspan="3">
												<button type="button" class="btn btn-xs btn-info"
													id="addImage">新增</button>
												<button type="button" class="btn btn-xs btn-info"
													id="removeImage">删除</button>
											</td>
										</tr>

									</tbody>
								</table>
							</div>
							</#if>

							<table id="tabs-4" class="inputTable tabContent">
								<tbody>
									<#if (qualityList?size>1) >
									<tr>
										<td>
											<a href="quality!sealist.action?abnorId=${(abnormal.id)}">质量问题单</a>
										</td>
									</tr>
									<#else> <#list (qualityList)! as list>
									<tr>
										<td><a href="quality!view.action?id=${(list.id)}">质量问题单</a>
										</td>
									</tr>
									</#list> </#if> <#if (modelList?size>1) >
									<tr>
										<td><a
											href="model!sealist.action?abnorId=${(abnormal.id)}">工模维修单</a>
										</td>
									</tr>
									<#else> <#list (modelList)! as list>
									<tr>
										<td><a href="model!view.action?id=${(list.id)}">工模维修单</a>
										</td>
									</tr>
									</#list> </#if> <#if (craftList?size>1) >
									<tr>
										<td><a
											href="craft!sealist.action?abnorId=${(abnormal.id)}">工艺维修单</a>
										</td>
									</tr>
									<#else> <#list (craftList)! as list>
									<tr>
										<td><a href="craft!view.action?id=${(list.id)}">工艺维修单</a>
										</td>
									</tr>
									</#list> </#if> <#if (deviceList?size>1) >
									<tr>
										<td><a
											href="device!sealist.action?abnorId=${(abnormal.id)}">设备维修单</a>
										</td>
									</tr>
									<#else> <#list (deviceList)! as list>
									<tr>
										<td><a href="device!view.action?id=${(list.id)}">设备维修单</a>
										</td>
									</tr>
									</#list> </#if>
								</tbody>
							</table>


						</form>

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
	<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->
	<#if !isAdd??> 
	<script>
	
		var qpdstr="${quality.qualityProblemDescription }";
		var qpdList=qpdstr.split(", ");
		$(document).ready(function()
		{
			$(".mycheckbox").each(function()
			{
				for(var i=0;i<qpdList.length;i++)
				{
					if($(this).val()==qpdList[i])
					{
						$(this).attr("checked","checked");
					}
				}
			});
			
			
		});
		
		</script>
	</#if>
	<script>
	
		
	
	
	
		$(function() {

			$("form.validatecredit")
					.validate(
							{

								errorClass : "validateError",
								ignore : ".ignoreValidate",
								onkeyup : false,
								errorPlacement : function(error, element) {
									var messagePosition = element.metadata().messagePosition;
									if ("undefined" != typeof messagePosition
											&& messagePosition != "") {
										var $messagePosition = $(messagePosition);
										if ($messagePosition.size() > 0) {
											error.insertAfter($messagePosition)
													.fadeOut(300).fadeIn(300);
										} else {
											error.insertAfter(element).fadeOut(
													300).fadeIn(300);
										}
									} else {
										error.insertAfter(element).fadeOut(300)
												.fadeIn(300);
									}
								},
								submitHandler : function(form) {
									var url = $(form).attr("action");
									var dt = $(form).serialize();
									/*credit.creditCard(url,function(data){
										$.message(data.status,data.message);
										window.location.href = "abnormal!list.action";
									},dt)*/

									credit
											.creditCard(
													url,
													function(data) {
														if (data.status == "success") {
															layer
																	.alert(
																			data.message,
																			{
																				icon : 6
																			},
																			function() {
																				window.location.href = "abnormal!list.action";
																			});
														} else if (data.status == "error") {
															layer
																	.alert(
																			data.message,
																			{
																				closeBtn : 0,
																				icon : 5,
																				skin : 'error'
																			});
														}
													}, dt)

								}
							});

			$("#sample-table-1 tbody .zg .text").attr("disabled", true);
			//$("#sample-table-1 tbody .zg .text").hide();

			$(".save").live("click", function() {
				$text = $(this).prev();
				$input = $(this).prev().prev();
				var i = $text.serialize();
				var ii = $("#qualityId").val();
				var iii = $(this).prev().prev().val();
				if (iii == null || iii == undefined || iii == "") {
					iii = "";
				}
				var ids = "";
				ids += ii + "," + i + "," + iii;
				url = "flowing_rectify!creditsave.action?ids=" + ids;

				credit.creditCard(url, function(data) {
					if (data.status == "success") {
						var index = layer.alert(data.message, {
							icon : 6
						}, function() {
							//	$.tip(data.status, data.message);
							$input.val(data.id);
							$text.attr("disabled", true);
							layer.close(index);
							//window.location.href="abnormal!list.action";
						});
					} else if (data.status == "error") {
						layer.alert(data.message, {
							closeBtn : 0,
							icon : 5,
							skin : 'error'
						});
					}
				})

				/*$.ajax({
					url: url,
					//data: ids,
					dataType: "json",		
					success: function(data) {
						
						$.tip(data.status, data.message);
						$input.val(data.id);
						$text.attr("disabled",true);
					}
				});	*/

			});

			$(".edit").live("click", function() {
				$(this).prev().prev().attr("disabled", false);

			})

			$(".deleteButton").live("click", function() {
				if ($(".zg").length > 1) {
					$(this).parent().parent().remove();
				} else {
					alert("请至少保留一个选项!");
				}
			})

		})
		
	
	</script>
</body>
</html>