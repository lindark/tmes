<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>部门领料 - Powered By ${systemConfig.systemName}</title>
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
					<li class="active"><#if isAdd??>添加<#else>修改</#if>部门领料记录</li>
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
								    	<li><a href="#tabs-1"><#if isAdd??>添加<#else>修改</#if>部门领料记录</a></li>
									</ul>
									<div id="tabs-1" class="tab1">
									
									<div class="row">
										<!-- 
											<div class="col-md-2">
												接收部门:
												<a href="javascript:void(0)" id="deptclick">
													 <img class="img_addbug" title="添加单元信息" alt="添加单元信息" style="cursor:pointer" src="/template/shop/images/add_bug.gif">
												 </a>	
												<input type="hidden" id="departid" name="departid"/>
												<span id="departname"></span>
											</div>
										 -->
											<div class="col-md-3">
												接收成本中心:
												<a href="javascript:void(0)" id="deptclick">
													 <img class="img_addbug" title="部门选择" alt="部门选择" style="cursor:pointer" src="/template/shop/images/add_bug.gif">
												 </a>
												<input type="hidden" id="costcenter" name="costcenter" value="${(deptpick.costcenter)! }"/>
												<span id="costcentertext" style="color:red">${(deptpick.costcenter)! }</span>
											</div>
											<div class="col-md-3">
												接收部门:
												<input type="hidden" id="departid" name="departid" value="${(deptpick.departid)! }"/>
												<input type="hidden" id="departmentName" name="departmentName" value="${(deptpick.departmentName)! }"/>
												<span id="departmentNametext" style="color:red">${(deptpick.departmentName)! }</span>
												
											</div>
											<div class="col-md-3">
												发料移动类型:
												<input type="hidden" id="movetype" name="movetype" value="${(deptpick.movetype)! }"/>
												<span id="movetypetext" style="color:red">${(deptpick.movetype)! }</span>
											</div>
										</div>
										<div class="row">
											<#if isAdd??>
												<div class="col-md-3">
													物料编码:
													<input type="text" name="materialCode" id="in_seartch_1"class="input input-sm" value="${info}">&nbsp;&nbsp;&nbsp;
												</div>
												<div class="col-md-3">
													物料描述:
													<input type="text" name="materialDesp" id="in_seartch_2"class="input input-sm" value="${desp}" >&nbsp;&nbsp;&nbsp;
												</div>
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
														<th class="tabth">入库数</th>
													</tr>
													
														<#if deptpick!=null>
														<tr>
															<td>${(deptpick.repertorySite)! }</td>
															<td>${(deptpick.materialCode)! }</td>
															<td>${(deptpick.materialName)! }</td>
															<td>${(deptpick.materialBatch)! }</td>
															<td>${(deptpick.actualMaterialMount)! }</td>
															<td><input type="text"  name="deptpickList[0].stockMount" value="${(deptpick.stockMount)!}" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value">
															<input type="hidden" name="deptpickList[0].id" value="${(deptpick.id)! }"/>
															</td>
															</tr>
														<#else>
															<#list locationonsideList as lns>
																<tr>
																	<td>${(lns.locationCode)! }</td>
																	<input type="hidden"  name="deptpickList[${lns_index}].repertorySite" value="${(lns.locationCode)!}">
																	<td>${(lns.materialCode)! }</td>
																	<td>${(lns.materialName)! }</td>
																	<input type="hidden"  name="deptpickList[${lns_index}].materialCode" value="${(lns.materialCode)!}">
																	<input type="hidden"  name="deptpickList[${lns_index}].materialName" value="${(lns.materialName)!}">
																	<td>${(lns.charg)! }</td>
																	<input type="hidden"  name="deptpickList[${lns_index}].materialBatch" value="${(lns.charg)!}">
																	<td>${(lns.amount)! }</td>
																	<input type="hidden"  name="deptpickList[${lns_index}].actualMaterialMount" value="${(lns.amount)!}">
																	<td  style="width:150px"><input style="width:95%"type="text" name="deptpickList[${lns_index}].stockMount" value="" onkeypress="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value" onkeyup="if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value"></td>
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
	var $dept= $("#deptclick");//部门选择功能
	$dept.click(function(){
		var $this = $(this);
		$this.attr("disabled",true);
		var content = "department!browser.action";
		var title="部门选择";
		var width="250px";
		var height = "400px";
		
		var offsetleft = $(this).offset().left;
		var offsettop = $(this).offset().top;
		var kjheight = $(this).height();
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        offset:[offsettop+kjheight+"px",offsetleft+"px"],
	        title: title,
	        fix: false,
	        shade: 0,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        btn:['确认'],
	        area: [width, height],//弹出框的高度，宽度
	        content:content,
	        yes:function(index,layero){//确定
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var depart = iframeWin.getName();
	        	//alert(depart.departid+","+depart.departName);
	        	$("#departid").val(depart.departid);
	        	$("#departmentNametext").text(depart.departName);
	        	$("#departmentName").val(depart.departName);
	        	$("#costcenter").val(depart.costcenter);
	        	$("#movetype").val(depart.movetype);
	        	$("#movetypetext").text(depart.movetype);
	        	$("#costcentertext").text(depart.costcenter);
	        	layer.close(index);
	        	return false;
	        },
	        end:function(index,layero){
	        	$this.removeAttr("disabled");
	        }
	        
	    });
		return false;
	})
	
	
	var $subm = $("#btn_subm");
	var $sure = $("#btn_subm");
	//刷卡提交
	$subm.click(function(){
		var costcenter = $("#costcenter").val();
		if(costcenter == ""){
			layer.alert("请填写成本中心");
			return false;
		}
		
		var dt = $("#inputForm").serialize();
		var url = "deptpick!creditsubmit.action";
		credit.creditCard(url,function(data){
			if(data.status=="success"){
				window.location.href="deptpick!list.action";
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
		window.location.href="deptpick!add.action?info="+maco+"&desp="+macodesp;
	});
});
</script>