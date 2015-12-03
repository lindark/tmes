<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>报废单管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {background: #fff;font-family: 微软雅黑;}
.div1
{
	background-color: #f9f9f9; 
	padding: 6px 10px 6px 4px;
    border-top: 1px dotted #D5E4F1;
    display: table-cell;
    vertical-align: middle;
}
.sub-style{float: right;}
#img_addbug{cursor:pointer;margin-left:1px;}
#span_bug{margin-left: 5px;}
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
				<script type="text/javascript">try {ace.settings.check('breadcrumbs', 'fixed')} catch (e) {}</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> 
					<a href="admin!index.action">管理中心</a></li>
					<li class="active">报废单</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<!-- add by welson 0728 -->
			
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate" action="<#if add??>scrap!save.action</#if><#if edit??>scrap!update.action</#if><#if show??></#if>" method="post">
								<input type="hidden" id="input_rd" name="info"  value="" />
								<input type="hidden" id="input_rnum" name="info2" value="" />
								<input type="hidden" id="my_id" name="my_id" value="${(my_id)! }" />
								<input type="hidden" name="sample.workingBill.id" value="${(workingbill.id)! }" />
								<input type="hidden" id="id" name="scrap.id" value="${(scrap.id)! }" />
								
								<!-- tabs start -->
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">报废信息编辑</a></li>
									</ul>
									<div id="tabs-1" class="tab1">
										<!-- msg start -->
										
											<!-- workingbill  start -->
											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<div class="profile-info-name">随工单号</div>
													<div class="profile-info-value">
														<span>${(workingbill.workingBillCode)!}</span>
													</div>
												</div>
									
												<div class="profile-info-row">
													<div class="profile-info-name">产品编号</div>
													<div class="profile-info-value">
														<span>${(workingbill.matnr)!}</span>
													</div>
													<div class="profile-info-name">产品名称</div>  
													<div class="profile-info-value">
														<span>${(workingbill.maktx)!}</span>
													</div>
												</div>
											</div>
											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<table class="table table-striped table-bordered table-hover">
														<tr>
															<th style="width: 15%;">物料编码</th>
															<th style="width: 33%;">物料描述</th>
															<th style="width: 22%;">责任划分</th>
															<th style="width: 30%;min-width:105px;">报废原因/数量</th>
														</tr>
														<#if list_material??>
															<#list list_material as list>
																<tr>
																	<td>${(list.materialCode)! }</td>
																	<td>${(list.materialName)! }</td>
																	<td>
																		<#if show??>
																			<span>${scraptype! }</span>
																		<#else>
																			<select name="scrapMessage.smduty" class="input input-sm form-control chosen-select">
																				<#list list_dict as dlist>
																					<option value="${(dlist.dictkey)! }" <#if (dlist.dictkey==sample.sampleType)!>selected</#if>>${(dlist.dictvalue)! }</option>
																				</#list>
																			</select>
																		</#if>
																	</td>
																	<td>
																		<img id="img_addbug" title="添加报废信息" alt="添加报废信息" src="${base}/template/shop/images/add_bug.gif" />
																		<span id="span_bug">www</span>
																	</td>
																</tr>
															</#list>
														</#if>
													</table>
												</div>
											</div>
											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<div class="row buttons col-md-8 col-sm-4">
														<a id="btn_add" class="btn btn-white btn-default btn-sm btn-round">
															<i class="ace-icon glyphicon glyphicon-plus"></i>
															报废产出后添加
														</a>
													</div>
												</div>
												<div class="profile-info-row">
													<table class="table table-striped table-bordered table-hover">
														<tr>
															<th style="width:25%;">物料编码</th>
															<th style="width:55%;">物料描述</th>
															<th style="width:20%;">物料数量</th>
														</tr>
													</table>
												</div>
											</div>
										<!-- end msg -->
									</div>
									<br/>
									<div class="row buttons col-md-8 col-sm-4 sub-style">
										<#if show??><#else>
										<a id="btn_save" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-cloud-upload"></i>
											刷卡提交
										</a>
										<a id="btn_confirm" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-cloud-upload"></i>
											刷卡确认
										</a>
										</#if>
										<a id="btn_back" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-home"></i>
											返回
										</a>
									</div>
								</div>
								<!-- end tabs -->
							</form>
							<!-- add by welson 0728 -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				
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
</html>
	
