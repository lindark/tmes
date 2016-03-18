<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>成品入库 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
<style>
body {
	background: #fff;
}
.table>tbody>tr>td{
	 text-align: center;
	 vertical-align: initial;
}
inupt.stockMout{
	padding:2px;
}
.site{
	margin-left:10px;
	margin-bottom:10px;
}

.tabth{text-align:center;}
.sub_style{text-align: center;width:100%;}
</style>
</head>
<body class="no-skin input">
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
				<script type="text/javascript">try {ace.settings.check('breadcrumbs', 'fixed')} catch (e) {}</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> 
					<a href="admin!index.action">管理中心</a></li>
					<li class="active">添加成品入库</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<!-- add by welson 0728 -->
			
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<form id="inputForm" name="inputForm" class="validate" action="" method="post">
							<input type="hidden" id="productDate" value="${productDate}"/>
							<input type="hidden" id="shift" value="${shift}"/>
								<!-- <div class="site">
									<span class="reSite">发出库存地点:
										<select name="endProducts.repertorySite">
											<#list allSite as als>
											<option value="${als.dictkey}" <#if (admin.team.factoryUnit.warehouse)! == als.dictkey>selected</#if>>${als.dictvalue}</option>
											</#list>
										</select>
									</span>
									<span class="reSite">接收库存地点:
										<select name="endProducts.receiveRepertorySite">
											<#list allSite as als>
											<option value="${als.dictkey}" <#if als.dictkey=="2401">selected</#if>>${als.dictvalue}</option>
											</#list>
										</select>
									</span>
									<div style="clear:both"></div>
								</div> -->
								<div id="inputtabs">
								 	<ul>
								    	<li><a href="#tabs-1">添加成品入库</a></li>
									</ul>
									<div id="tabs-1" class="tab1">				
									<div class="site">接收库存地点:
										<select name="info" style="margin-right:30px;">
											<#list allSite as als>
											<option value="${als.dictkey}" <#if als.dictkey=="2401">selected</#if>>${als.dictvalue}</option>
											</#list>
										</select>
										<#if isAdd??>
											<label class="" style="text-align:right">物料编码:</label>
											<input type="text" name="materialCode" id="in_seartch_1"class="input input-sm" value="${info}">&nbsp;&nbsp;&nbsp;
											<label class="" style="text-align:right">物料描述:</label>
											<input type="text" name="materialDesp" id="in_seartch_2"class="input input-sm" value="${desp}" >&nbsp;&nbsp;&nbsp;
											
											<a id="search_btn" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												搜索
											</a>
											</#if>
									</div>
										<div class="profile-user-info profile-user-info-striped">
								  			<div class="profile-info-row">
								    			<table id="tb_cartonson" class="table table-striped table-bordered table-hover">
								    			
													<tr>
														<th class="tabth">库存地点</th>
														<th class="tabth">物料编码</th>
														<th class="tabth">物料描述</th>
														<th class="tabth">批次</th>
														<th class="tabth">库存数量</th>
														<th class="tabth">库存箱数</th>
														<th class="tabth">入库箱数</th>
													</tr>
													
														<#if endProduct!=null>
														<tr>
															<td>${(endProduct.repertorySite)! }</td>
																	<input type="hidden"  name="id" value="${(endProduct.id)! }">
																	<input type="hidden"  name="endProduct.repertorySite" value="${(endProduct.repertorySite)! }">
																	<input type="hidden"  name="endProduct.repertorySiteDesp" value="${(endProduct.repertorySiteDesp)!}">
																	<td>${(endProduct.materialCode)! }</td>
																	<td>${(endProduct.materialDesp)! }</td>
																	<input type="hidden"  name="endProduct.materialCode" value="${(endProduct.materialCode)!}">
																	<input type="hidden"  name="endProduct.materialDesp" value="${(endProduct.materialDesp)!}">
																	<td>${(endProduct.materialBatch)! }</td>
																	<input type="hidden"  name="endProduct.materialBatch" value="${(endProduct.materialBatch)!}">
																	<td>${(endProduct.actualMaterialMount)! }</td>
																	<td>${(endProduct.actualMaterialBoxMount)! }</td>
																	<input type="hidden"  name="endProduct.actualMaterialBoxMount" value="${(endProduct.actualMaterialBoxMount)!}">
																	<input type="hidden"  name="endProduct.actualMaterialMount" value="${(endProduct.actualMaterialMount)!}">
																	
																	<td><input type="text"  name="endProduct.stockBoxMout" value="${(endProduct.stockBoxMout)!}" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"></td>
															</tr>
														<#else>
															<#list locationonsideList as lns>
																<tr>
																	<td>${(lns.locationCode)! }</td>
																	<input type="hidden"  name="endProducts[${lns_index}].repertorySite" value="${(lns.locationCode)!}">
																	<input type="hidden"  name="endProducts[${lns_index}].repertorySiteDesp" value="${(lns.locationName)!}">
																	<td>${(lns.materialCode)! }</td>
																	<td>${(lns.materialName)! }</td>
																	<input type="hidden"  name="endProducts[${lns_index}].materialCode" value="${(lns.materialCode)!}">
																	<input type="hidden"  name="endProducts[${lns_index}].materialDesp" value="${(lns.materialName)!}">
																	<td>${(lns.charg)! }</td>
																	<input type="hidden"  name="endProducts[${lns_index}].materialBatch" value="${(lns.charg)!}">
																	<td>${(lns.amount)! }</td>
																	<td>${(lns.boxMount)! }</td>
																	<input type="hidden"  name="endProducts[${lns_index}].actualMaterialBoxMount" value="${(lns.boxMount)!}">
																	<input type="hidden"  name="endProducts[${lns_index}].actualMaterialMount" value="${(lns.amount)!}">
																	<td  style="width:150px"><input style="width:95%"type="text" name="endProducts[${lns_index}].stockBoxMout" value=""onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"></td>
																</tr>
															</#list>
															</#if>
												</table>
							  				</div>
						   				</div>
									</div>
                     			</div>
							</form>
							<br/>
							<div class="row buttons  col-sm-4 sub_style">
                            		<button class="btn btn-white btn-default btn-sm btn-round" id="btn_subm" type="button" />
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>&nbsp;&nbsp;&nbsp;&nbsp;
									<!-- <button class="btn btn-white btn-default btn-sm btn-round" id="btn_sure" type="button" />
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
									</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
								<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type="button" />
									<i class="ace-icon fa fa-home"></i>
									返回
								</button>
							</div>
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
<script type="text/javascript">
$(function(){
	var $subm = $("#btn_subm");
	var $sure = $("#btn_subm");
	var $ep = "${(endProduct)!''}";
	//刷卡提交
	$subm.click(function(){
		var productDate = $("#productDate").val();
		var shift = $("#shift").val();
		//alert(shift);
		//return false;
		var dt = $("#inputForm").serialize();
		if($ep==""){
			var url = "end_product!creditsubmit.action?productDate="+productDate+"&shift="+shift;
		}else{
			var url = "end_product!update.action?productDate="+productDate+"&shift="+shift;
		}
		credit.creditCard(url,function(data){
			if(data.status=="success"){
				window.location.href="end_product!list.action?productDate="+productDate+"&shift="+shift;
			}
		},dt);
	});
	//刷卡确认
	/* $sure.click(function(){
		var dt = $("#inputForm").serialize();
		var url = "end_product!creditApproval.action";
		credit.creditCard(url,function(data){
			/* if(data.status=="success"){
				$subm.prop("display":true);
				$sure.prop("display":true);
			} 
		},dt);
	}); */
	$("#btn_back").click(function(){
		window.history.back();
	});
	$("#search_btn").click(function(){
		var maco = $("#in_seartch_1").val();
		var macodesp = $("#in_seartch_2").val();
		window.location.href="end_product!add.action?info="+maco+"&desp="+macodesp;
	});
});
</script>