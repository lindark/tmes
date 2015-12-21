<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑产品管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/material_input.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/matbrower.js"></script>
<script type="text/javascript">
$().ready( function() {

	// 地区选择菜单
	$(".areaSelect").lSelect({
		url: "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
	});

});
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body{background:#fff;}
</style>
</head>
<body class="no-skin input">
	
<!-- add by welson 0728 -->	
<#include "/WEB-INF/template/admin/admin_navbar.ftl">
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
	<div class="main-content">
	<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">
	
	<!-- ./ add by welson 0728 -->
	
<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">管理中心</a>
			</li>
			
			<li class="active">更新Boom</li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="material!save.action" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">产品Bom信息</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
					                <div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<!--  <div class="profile-info-name">展开层</div>
												<div class="profile-info-value">
													<input type="text" name="material.spread"
														value="${(material.spread)!}"
														class=" input input-sm formText {required: true,minlength:0,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>-->
 
                                               <div class="profile-info-name">产品名称</div>
												<div class="profile-info-value">
												 <input type="hidden" id="productId" name="material.products.id" value="${(material.products.id)!}"  class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" readonly="readonly"/>					
												    
												    <#if isAdd??><button type="button" class="btn btn-xs btn-info" id="userAddBtn" data-toggle="button">选择</button>				                                    
				                                     <span id ="productName"></span>
										         	 <label class="requireField">*</label>	
										         	 <#else>
										         	 ${(material.products.productsName)!}    
										         	 </#if>	
												</div>
												
												
												<div class="profile-info-name">产品数量</div>
												<div class="profile-info-value">
													<input type="text" name="material.batch"
														value="${(material.products.productsAmount)!}"
														class=" input input-sm formText {required: true,minlength:1,maxlength: 100}" />
													<label class="requireField">*</label>
												</div>
											</div>												
									</div>
									<div class="profile-user-info profile-user-info-striped">
												<#if show??>
												<#else>
												<div class="profile-info-row">
													<div class="row buttons col-md-8 col-sm-4">
														<a id="btn_add" class="btn btn-white btn-default btn-sm btn-round">
															<i class="ace-icon glyphicon glyphicon-plus"></i>
															增加组件
														</a>
													</div>
												</div>
												</#if>
												<div class="profile-info-row">
													<table id="tb_material" class="table table-striped table-bordered table-hover">
														<#if show??>
															<tr>
																<th>组件编码</th>
																<th>组件名称</th>
																<th>组件单位</th>
																<th>组件数量</th>															
																<th>批次</th>
																<th>是否为纸箱</th>
																<th>版本号</th>
															</tr>
															<#if materialList??>
																<#assign slnum=0 />
																<#list materialList as sllist>
																	<tr>
																		<td>${(sllist.materialCode)! }</td>
																		<td>${(sllist.materialName)! }</td>
																		<td>${(sllist.materialUnit)! }</td>
																		<td>${(sllist.materialAmount)!}</td>
																		<td>${(sllist.batch)!}</td>
																		<td>${(sllist.isCarton)!}</td>
																		<td>${(sllist.version)!}</td>
																	</tr>
																<#assign slnum=slnum+1 />
																</#list>
															</#if>
														<#else>
															<tr>
																<th>组件编码</th>
																<th>组件名称</th>
																<th>组件单位</th>
																<th>组件数量</th>
																<th>批次</th>
																<th>是否为纸箱</th>
																<th>版本号</th>
																<th>操作</th>
															</tr>
															<#if materialList??>
																<#assign slnum=0 />
																<#list materialList as sllist>
																	<tr>
																		<td>${(sllist.materialCode)! }</td>
																		<td>${(sllist.materialName)! }</td>
																		<td>${(sllist.materialUnit)! }</td>
																		<td>${(sllist.materialAmount)!}</td>
																		<td>${(sllist.batch)!}</td>
																		<td>${(sllist.isCarton)!}</td>
																		<td>${(sllist.version)!}</td>
																		<td>
																			<span id="span_count${(sllist.slmatterNum)! }">${(sllist.slmatterCount)! }</span>
																			<input id="input_slnum${slnum}" type="hidden" name="materialList[${slnum}].materialCode" value="${(sllist.slmatterNum)! }" />
																			<input id="input_sldes${slnum}" type="hidden" name="list_scraplater[${slnum}].slmatterDes" value="${(sllist.slmatterDes)! }" />
																			<input id="input_count${(sllist.slmatterNum)!}" type="text" name="list_scraplater[${slnum}].slmatterCount" value="${(sllist.slmatterCount)! }" onblur="inputcount_blur(${(sllist.slmatterNum)! })" style="display: none;" />
																		</td>
																		<td>
																			<a onclick="edit_click(${(sllist.slmatterNum)! })">编辑</a>
																			&nbsp;&nbsp;
																			<a onclick="del_click(${(sllist.slmatterNum)! })">删除</a>
																		</td>
																	</tr>
																	<#assign slnum=slnum+1 />
																</#list>
															</#if>
														</#if>
													</table>
												</div>
											</div>
				
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
		<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
							<div id="divbox2" style="display: none;">
								<div class="profile-user-info profile-user-info-striped divbox">
									    <div class="profile-info-row">
											<div class="profile-info-name">组件编码</div>
											<div class="profile-info-value div-value">
												<input id="sl_num1" type="text" style="width:150px;" />
												<label class="requireField">*</label>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">组件名称</div>
											<div class="profile-info-value div-value">
												<input id="sl_num2" type="text" style="width:150px;" />
												<label class="requireField">*</label>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">组件单位</div>
											<div class="profile-info-value div-value">
												<input id="sl_num3" type="text" style="width:150px;" />
												<label class="requireField">*</label>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">组件数量</div>
											<div class="profile-info-value div-value">
												<input id="sl_num" type="text" style="width:150px;" />
												<label class="requireField">*</label>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">批次</div>
											<div class="profile-info-value div-value">
												<input id="sl_num5" type="text" style="width:150px;" />
												<label class="requireField">*</label>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">是否为纸箱</div>
											<div class="profile-info-value div-value">
												<input id="sl_num6" type="text" style="width:150px;" />
												<label class="requireField">*</label>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">版本 </div>
											<div class="profile-info-value div-value">
												<input id="sl_num7" type="text" style="width:150px;" />
												<label class="requireField">*</label>
											</div>
										</div>
									</div>
								</div>
							</div>
<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
	
<!-- add by welson 0728 -->	
				</div><!-- /.col -->
				</div><!-- /.row -->

				<!-- PAGE CONTENT ENDS -->
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.page-content-area -->
	<#include "/WEB-INF/template/admin/admin_footer.ftl">
</div><!-- /.page-content -->
				</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->


</body>
</html>
<script type="text/javascript">
var number="0";
//给按钮加事件--添加缺陷信息事件
function addbug_event()
{
	var i=0;
	<#list list_material as list>
		$("#img_addbug"+i).live("click",function(){
			var idval=$(this).attr("id");
			i=idval.substring(idval.length-1,idval.length);
			btn_addbug_event(i);
		});
		i+=1;
	</#list>
}
//缺陷内容事件
function cause_event()
{
	var i=0;
	<#list list_cause as list>
		$("#mynum"+i).change(function(){
			var idval=$(this).attr("id");
			i=idval.substring(idval.length-1,idval.length);
			write_bugnum_event(i);
		});
		i+=1;
	</#list>
}

//如果当前选择行报废数量已有值，就赋给box框中对应的框中
function rowtobox_event(index)
{
	var rowids=$("#input_bugid"+index).val();//ids
	var rownums=$("#input_bugnum"+index).val();//数量
	var rowids_array=rowids.split(",");
	var rownums_array=rownums.split(",");
	var i=0;
	<#list list_cause as list>
		$("#mynum"+i).val("");//先清空
		for(var j=0;j<rowids_array.length;j++)
		{
			if(rowids_array[j]!=null&&rowids_array[j]!="")
			{
				var id="${(list.id)!}";
				if(rowids_array[j]==id)
				{
					$("#mynum"+i).val(rownums_array[j]);
				}
			}
		}
		i+=1;
	</#list>
}

//报废原因数量填写好并确定之后把填写的保存起来
function boxtorow_event(index)
{
	var rowids="";
	var rownums="";
	var count=0;
	var spanbug="";
	//获取已填写的报废原因
	var i=0;
	<#list list_cause as list>
		var boxnum=$("#mynum"+i).val();
		if(boxnum!=null&&boxnum!=""&&boxnum>0)
		{
			var id="${(list.id)!}";
			var des="${(list.causeName)!}";
			rowids=rowids+id+",";
			rownums=rownums+boxnum+",";
			count=floatAdd(count,boxnum);
			spanbug=spanbug+des+""+boxnum+",";
		}
		i+=1;
	</#list>
	//给选择的行赋值
	$("#input_bugid"+index).val(rowids);
	$("#input_bugnum"+index).val(rownums);
	if(count==0)
	{
		$("#input_msgmenge"+index).val("");
	}
	else
	{
		$("#input_msgmenge"+index).val(count);
	}
	if(spanbug!="")
	{
		spanbug=spanbug.substring(0, spanbug.length-1);
	}
	$("#span_bug"+index).text(spanbug);
	$("#input_msgbug"+index).val(spanbug);
}

//初始化报废后产出表
function scraplater_event()
{
	$("#tb_material tr").each(function(){
		var wlcode=($(this).children("td:first").text());
		<#list list_material as list3>
			if("${(list3.materialCode)!}"==wlcode)
			{
				$("#opt_${(list3.materialCode)!}").hide();
			}
		</#list>
		
	});
}

</script>