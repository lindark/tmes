<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>上架/下架管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
<script
	src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
<style>
body {
	background: #fff;
}

inupt.stockMout {
	padding: 2px;
}

.site {
	margin-left: 10px;
	margin-bottom: 10px;
}

.tabth {
	text-align: center;
}

.sub_style {
	text-align: center;
	width: 100%;
}
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
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a></li>
					<li class="active"><#if type='up' || type='down'>上架/下架管理
						<#else> 超市领料</#if></li>
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
								<input type="hidden" id="productDate" value="${productDate}" />
								<input type="hidden" id="shift" value="${shift}" />
								<input type="hidden" name="type" value="${(type)!}"/> 
								<input type="hidden" id="work" name="workingBill" value="${(workingBill)!'' }"/>
								<input type="hidden" id="workId" name="workingBillId" value="${(workingBillId)!'' }"/>
								<input type="hidden" id="cardnumber1" name="cardnumber1" value="${(cardnumber1)!}">
								<input type="hidden" id="funid" name="funid" value="${(funid)!}">
								<input type="hidden" id="inputmenge" name="inputmenge" value="${(inputmenge)!}">
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
										<li><a href="#tabs-1"><#if type='up' ||
												type='down'>上架/下架管理 <#else> 超市领料</#if></a></li>
									</ul>
									<div id="tabs-1" class="tab1">
										<div>
											<#if isAdd??> 
											<label class="" style="text-align:right">发出仓位:</label>
											<#if type="updown">
												<select name="lgpla" id="select1" style=" width:160px;">
												<option value="" ></option>
												<#list PositionManagementList as list>
												<option value="${list }"<#if list==lgpla>selected </#if>>${list }</option>
												</#list>	
												</select>
											<#else>
											<#if type="down">
												<select name="lgpla" style=" width:160px;" id="lgpla">
												<option value="" ></option>
												<#list trimWareHouseSet as list>
												<#if list != "R-00">
												<option value="${list}"<#if list==lgpla>selected </#if>>${list}</option>
												<#else>
													<#if workingBillId ?? || workingBillId =="">
													<option value="${list}"<#if list==lgpla>selected </#if>>${list}</option>
													</#if>
												</#if>
												</#list>
											</select>
											<#else>
											<input type="text" name="lgpla"<#if type='up'> readonly </#if> value="${(lgpla)! }">&nbsp;&nbsp;&nbsp;
											</#if>
											</#if>
											<label class="" style="text-align:right">接收仓位:</label>
											<#if type="updown">
												<select name="lgplaun" id="select2" style=" width:160px;"value="${(lgplaun)! }">
												<option value="${(lgplaun)! }">${(lgplaun)! }</option>
												
												</select>
											<#else>
											<#if type='up'>
												<select name="lgplaun" style=" width:160px;">
												<option value="" ></option>
												<#list supermarketWarehouseSet as list>
												<option value="${list}"<#if list==lgplaun>selected </#if>>${list}</option>
												</#list>
											</select> 	
											<#else>
											<input type="text" name="lgplaun" <#if type="down"> readonly </#if> value=" ${(lgplaun)! }">&nbsp;&nbsp;&nbsp;
											</#if>
											 <#if type='up' || type='down'>
												<label class="" style="text-align:right">物料编码:</label>
												<input type="text" name="materialCode" value="${(materialCode)! }" class="input input-sm">
												&nbsp;&nbsp;&nbsp;
												<label class="" style="text-align:right">物料描述:</label>
												<input type="text" name="materialDesp" value="${(materialDesp)! }" class="input input-sm">&nbsp;&nbsp;&nbsp;
											 </#if>
											 </#if>
											<a id="search_btn" class="btn btn-white btn-default btn-sm btn-round">
												<i class="ace-icon fa fa-filter blue"></i>
												搜索
											</a>
										</#if>
										</div>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<table id="tb_cartonson"
													class="table table-striped table-bordered table-hover">

													<tr>
														<th class="tabth">库存地点</th>
														<#if type="down">
														<th class="tabth">仓位</th>
														</#if>
														<th class="tabth">物料编码</th>
														<th class="tabth">物料描述</th>
														<th class="tabth">批次</th>
														<th class="tabth">库存数量</th>
														<th class="tabth">数量</th>
														<th class="tabth">来料说明</th>
													</tr>
													<#list locationonsideList as lns>
													<tr>
														<td>${(lns.locationCode)! }</td>
														<input type="hidden" name="updownList[${lns_index}].lgort"
															value="${(lns.locationCode)!}">
														<input type="hidden"
															name="updownList[${lns_index}].lgortname"
															value="${(lns.locationName)!}">
														<#if type="down">
														<td>${(lns.locationName)! }</td>
														<input type="hidden" name="updownList[${lns_index}].lgpla"
															value="${(lns.locationName)!}">
														<input type="hidden"
															name="updownList[${lns_index}].lgplaname"
															value="${(lns.locationName)!}">
														</#if>
														<td>${(lns.materialCode)! }</td>
														<td>${(lns.materialName)! }</td>
														<input type="hidden" name="updownList[${lns_index}].matnr"
															value="${(lns.materialCode)!}">
														<input type="hidden" name="updownList[${lns_index}].maktx"
															value="${(lns.materialName)!}">
														<td>${(lns.charg)! }</td>
														<input type="hidden" name="updownList[${lns_index}].charg"
															value="${(lns.charg)!}">
														<td>${(lns.amount)! }</td>
														<input type="hidden"
															name="updownList[${lns_index}].amount"
															value="${(lns.amount)!}">
														<td style="width:150px"><input style="width:95%"
															type="text" name="updownList[${lns_index}].dwnum"
															value="${(lns.xamount)!}"
															onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"
															onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value">
														</td>
														<td><select name="updownList[${lns_index}].detail"
															value=""><option value="">请选择
															</option><#list allDetails as dic>
															<option value="${dic.dictkey}">${(dic.dictvalue)!}</option></#list>
														</select></td>
														<!--<input type="hidden"
															name="updownList[${lns_index}].detail"
															value="${(lns.detail)!}">-->
													</tr>
													</#list>
												</table>
											</div>
										</div>
									</div>
								</div>
							</form>
							<br />
							<div class="row buttons  col-sm-4 sub_style">
								<button class="btn btn-white btn-default btn-sm btn-round"
									id="btn_subm" type="button" />
								<i class="ace-icon glyphicon glyphicon-check"></i> 刷卡保存
								</button>
								&nbsp;&nbsp;&nbsp;&nbsp;
								<!-- <button class="btn btn-white btn-default btn-sm btn-round" id="btn_sure" type="button" />
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
									</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
								<button class="btn btn-white btn-default btn-sm btn-round"
									id="btn_back" type="button" />
								<i class="ace-icon fa fa-home"></i> 返回
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
$(function() {
		var $select1 = $("#select1");
		$select1.change(function(){
			var warehouse = $(this).val();
			$("#select2").children().remove();
			$.ajax({	
				url: "up_down!trimget.action",
				data:{"warehouse":warehouse},
				dataType: "json",
				async: false,
				success: function(data) {
					for (var i = 0; i < data.length; i++) {
						$("#select2").append("<option>"+data[i]+"</option>");
					}
				},error:function(data){
					alert("发生异常")
				}
			});
		});
		
		var $subm = $("#btn_subm");
		var $sure = $("#btn_subm");
		//刷卡提交
		$subm.click(function() {
			var dt = $("#inputForm").serialize();
			var loginid = $("#loginid").val();
			var url = "up_down!creditsave.action?loginid="+loginid;
			credit.creditCard(url, function(data) {
				if (data.status == "success") {
					//window.location.href="end_product!list.action?productDate="+productDate+"&shift="+shift;
					var workId = $("#workId").val();
					if( workId != ""){
					window.location.href = "pick!list.action?workingBillId="+'${(workingbill.id)!}';
					}	
				}
			}, dt);
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
		$("#btn_back").click(function() {
			//window.history.back();
			var type = '${(type)! }';
			if (type == "up" || type == "down")
				window.location.href = "up_down!list.action";
			else if (type == "updown")
				window.location.href = "up_down!cslist.action";
		});
		$("#search_btn").click(function() {
			var workId = $("#workId").val();
			var cardnumber = $("#cardnumber1").val();
			if(cardnumber != ""){
				$("#inputForm").attr("action","up_down!addForWuliu.action");
			}else{
				if( workId != ""){
					$("#inputForm").attr("action","up_down!trim.action");
				}else{
				$("#inputForm").attr("action", "up_down!add.action");
				}
			}
			
			$("#inputForm").submit();
		});
	});
</script>