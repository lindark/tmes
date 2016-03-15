<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>返修收货管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
	<#include "/WEB-INF/template/common/includelist.ftl"> <!--modify weitao-->
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/repairin_input.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
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
					<li class="active">返修收货</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate" action="" method="post">
								<input type="hidden" name="repairin.id" value="${(repairin.id)!}" />
								<input type="hidden" class="input input-sm" name="repairin.workingbill.id" value="${workingbill.id} " id="wkid">
								<input type="hidden" name="workingBillId" value="${workingbill.id} ">
								<input type="hidden" name="repairin.CXORJC" id="input_cxorjc" value="${(repairin.CXORJC)! }" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">返修收货管理</a></li>

									</ul>
									<div id="tabs-1">
										<!--weitao begin modify , gyf modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">随工单</div>
												<div class="profile-info-value">
													<span>${workingbill.workingBillCode}</span>
												</div>
												<div class="profile-info-name">产品名称</div>
												<div class="profile-info-value">
													<span>${workingbill.maktx}</span>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">产品编号</div>
												<div class="profile-info-value">
													<span>${workingbill.matnr}</span>
												</div>
												<div class="profile-info-name">总返修收货数量</div>
												<div class="profile-info-value">
													<#if show??>
														${(workingbill.totalRepairinAmount)!}
													<#else>
														<span class="editable editable-click" id="age">${(workingbill.totalRepairinAmount)!}</span>
													</#if>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">返修收货数量</div>
												<div class="profile-info-value">
													<#if show??>
														${(repairin.receiveAmount)!}
													<#else>
														<input type="text" id="input_num" name="repairin.receiveAmount"
															value="${(repairin.receiveAmount)!}"
															class=" input input-sm formText {required: true}" />
														<label class="requireField">*</label>
													</#if>
												</div>
												
												<div class="profile-info-name">成本中心</div>
												<div class="profile-info-value">
													<#if show??>
														${(repairin.costcenter)!}
													<#else>
														<img id="img_costcenter" class="img_costcenter" title="添加成本中心" alt="添加成本中心" src="${base}/template/shop/images/add_bug.gif" />
														<#if add??>
															<span id="span_costcenter">10008431</span>
															<input type="hidden" id="input_costcenter" name="repairin.costcenter" value="10008431" />
														<#else>
															<span id="span_costcenter">${(repairin.costcenter)!}</span>
															<input type="hidden" id="input_costcenter" name="repairin.costcenter" value="${(repairin.costcenter)!}" />
														</#if>
														<label class="requireField">*</label>
													</#if>
												</div>
											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">成品/组件</div>					
												<div class="profile-info-value">
													<#if show??>
														${(repairin.xrepairintype)! }
													<#else>
														<label class="pull-left inline">
					                          				<small class="muted smaller-90">成品:</small>
						                       				<input type="radio" id="repairintype_cp" class="ace" name="repairin.repairintype" value="CP"<#if (isAdd || repairin.repairintype == 'CP')!> checked</#if> />
						                       				<span class="lbl middle"></span>
					                        			</label>
					                        			<!--
					                        			&nbsp;&nbsp;			
					                        			<label class="pull-left inline">
					                            			<small class="muted smaller-90">组件:</small>
						                        			<input type="radio" id="repairintype_zj" class="ace" name="repairin.repairintype" value="ZJ"<#if (repairin.repairintype == 'ZJ')!> checked</#if>  />
						                         			<span class="lbl middle"></span>
					                        			</label>
					                        			  -->
													</#if>
												</div>	
											</div>
										</div>
										<!--weitao end modify , gyf modify-->
										<br/>
										<!-- gyf start piece-->
										<div id="div_addpiece" class="profile-user-info profile-user-info-striped">
											<#if show??>
											<#else>
												<div class="profile-info-row">
													<div class="row buttons col-md-8 col-sm-4">
														<a id="btn_addpiece" class="btn btn-white btn-default btn-sm btn-round">
															<i class="ace-icon glyphicon glyphicon-plus"></i>
															添加产品组件
														</a>
													</div>
												</div>
											</#if>
											 
											<div class="profile-info-row">
												<table id="tb_repairinpiece" class="table table-striped table-bordered table-hover">
													<#if show??>
														<tr>
															<th style="width:25%;">组件编码</th>
															<th style="width:35%;">组件描述</th>
															<th style="width:20%;">产品数量</th>
															<th style="width:20%;">组件数量</th>
														</tr>
														<#if list_rp??>
															<#list list_rp as rplist>
																<tr>
																	<td>${(rplist.rpcode)! }</td>
																	<td>${(rplist.rpname)! }</td>
																	<td>${(rplist.productnum)! }</td>
																	<td>${(rplist.piecenum)! }</td>
																</tr>
															</#list>
														</#if>
													<#else>
														<tr>
															<th style="width:20%;">组件编码</th>
															<th style="width:35%;">组件描述</th>
															<th style="width:15%;">产品数量</th>
															<th style="width:15%;">组件数量</th>
															<th style="width:15%;">操作</th>
														</tr>
														<#if list_rp??>
															<#assign rpnum=0 />
															<#list list_rp as rplist>
																<tr>
																	<td>${(rplist.rpcode)! }<input type="hidden" name="list_rp[${rpnum}].rpcode" value="${(rplist.rpcode)! }" /></td>
																	<td>${(rplist.rpname)! }<input type="hidden" name="list_rp[${rpnum}].rpname" value="${(rplist.rpname)! }" /></td>
																	<td>${(rplist.productnum)! }<input type="hidden" name="list_rp[${rpnum}].productnum" value="${(rplist.productnum)! }" /></td>
																	<td>${(rplist.piecenum)! }<input type="hidden" name="list_rp[${rpnum}].piecenum" value="${(rplist.piecenum)! }" /></td>
																	<td>
																		<a id="a_${(rplist.rpcode)! }" onclick="del_event(${(rplist.rpcode)! })"  style="cursor:pointer;">删除</a>
																	</td>
																</tr>
																<#assign rpnum=rpnum+1 />
															</#list>
														</#if>
													</#if>
												</table>
											</div>
										</div>
										<!-- gyf end piece-->
									</div>
									<div class="buttonArea">
										<#if show??>
										<#else>
											<a id="btn_save" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-cloud-upload"></i>
												刷卡保存
											</a>
										</#if>
										<a id="btn_back" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-home"></i>
											返回
										</a>
									</div>
								</div>
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

</body>
</html>
<script type="text/javascript">
$(function(){
	//刷卡保存
	$("#btn_save").click(function(){
		//提交
		if(iscansave())
		{
			var url="";
			<#if add??>
				url = "repairin!creditsave.action";		
			<#else>
				url = "repairin!creditupdate.action";
			</#if>
			save_event(url);
		}
		else
		{
			layer.alert("必填项不能为空,请检查!",false);
		}
		
	});
	
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
});
</script>