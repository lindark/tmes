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
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/scrap_input.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/jquery.easydrag.handler.beta2.js"></script>
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
.img_addbug{cursor:pointer;margin-left:1px;}
#span_bug{margin-left: 5px;}
#test1{width:800px;height:400px;}
.div-value{padding-right:30px;min-width:200px; }
.div-value2{text-align:right;padding-right:0px;min-width:200px;}
.input-value{width:80px;height:30px;line-height:30px;}
#tb_scraplater a{cursor: pointer;}
.divbox{margin-top: 20px;}
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
								<input type="hidden" id="my_id" name="my_id" value="${(my_id)! }" />
								<input type="hidden" name="scrap.workingBill.id" value="${(workingbill.id)! }" />
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
														<#if show??>
															<#if list_scrapmsg??>
																<#assign num=0 />
																<#list list_scrapmsg as list>
																	<tr>
																		<td>${(list.smmatterNum)! }</td>
																		<td>${(list.smmatterDes)! }</td>
																		<td>${(list.xsmduty)! }</td>
																		<td>${(list.smreson)! }</td>
																	</tr>
																	<#assign num=num+1 />
																</#list>
															</#if>
														<#else>
															<#if list_material??>
																<#assign num=0 />
																<#list list_material as list>
																	<tr>
																		<td>${(list.materialCode)! }</td>
																		<td>${(list.materialName)! }</td>
																		<td>
																			<select name="list_scrapmsg[${num}].smduty" class="input input-sm form-control chosen-select">
																				<option value="">&nbsp;</option>
																				<#list list_dict as dlist>
																					<option value="${(dlist.dictkey)! }" <#if (dlist.dictkey==list.xsmduty)!>selected</#if>>${(dlist.dictvalue)! }</option>
																				</#list>
																			</select>
																		</td>
																		<td>
																			<img id="img_addbug${num}" class="img_addbug" title="添加报废信息" alt="添加报废信息" src="${base}/template/shop/images/add_bug.gif" />
																			<span id="span_bug${num}">${(list.xsmreson)!}</span>
																			<input type="hidden"  id="input_msgbug${num}" name="list_scrapmsg[${num}].smreson" value="${(list.xsmreson)!}" />
																			<input type="hidden"  id="input_msgmenge${num}" name="list_scrapmsg[${num}].menge" value="${(list.xmenge)! }" />
																			<input type="hidden" name="list_scrapmsg[${num}].smmatterNum" value="${(list.materialCode)! }" />
																			<input type="hidden" name="list_scrapmsg[${num}].smmatterDes" value="${(list.materialName)! }" />
																			<input type="hidden" name="list_scrapmsg[${num}].materialId" value="${(list.id)! }" />
																			<input type="hidden" name="list_scrapmsg[${num}].id" value="${(list.xsmid)! }" />
																			
																			<input id="input_bugnum${num}" name="list_scrapbug[${num}].xbugnums" type="hidden" value="${(list.xsbnums)! }" />
																			<input id="input_bugid${num}" name="list_scrapbug[${num}].xbugids" type="hidden" value="${(list.xsbids)! }" />
																		</td>
																	</tr>
																	<#assign num=num+1 />
																</#list>
															</#if>
														</#if>
													</table>
												</div>
											</div>
											<div class="profile-user-info profile-user-info-striped">
												<#if show??>
												<#else>
												<div class="profile-info-row">
													<div class="row buttons col-md-8 col-sm-4">
														<a id="btn_add" class="btn btn-white btn-default btn-sm btn-round">
															<i class="ace-icon glyphicon glyphicon-plus"></i>
															报废产出后添加
														</a>
													</div>
												</div>
												</#if>
												<div class="profile-info-row">
													<table id="tb_scraplater" class="table table-striped table-bordered table-hover">
														<#if show??>
															<tr>
																<th style="width:25%;">物料编码</th>
																<th style="width:50%;">物料描述</th>
																<th style="width:25%;">物料数量</th>
															</tr>
															<#if list_scraplater??>
																<#assign slnum=0 />
																<#list list_scraplater as sllist>
																	<tr>
																		<td>${(sllist.slmatterNum)! }</td>
																		<td>${(sllist.slmatterDes)! }</td>
																		<td>${(sllist.slmatterCount)! }</td>
																	</tr>
																<#assign slnum=slnum+1 />
																</#list>
															</#if>
														<#else>
															<tr>
																<th style="width:20%;">物料编码</th>
																<th style="width:40%;">物料描述</th>
																<th style="width:20%;">物料数量</th>
																<th style="width:20%;">操作</th>
															</tr>
															<#if list_scraplater??>
																<#assign slnum=0 />
																<#list list_scraplater as sllist>
																	<tr>
																		<td>${(sllist.slmatterNum)! }</td>
																		<td>${(sllist.slmatterDes)! }</td>
																		<td>
																			<span id="span_count${(sllist.slmatterNum)! }">${(sllist.slmatterCount)! }</span>
																			<input id="input_slnum${slnum}" type="hidden" name="list_scraplater[${slnum}].slmatterNum" value="${(sllist.slmatterNum)! }" />
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
										<!-- end msg -->
									</div>
									<br/>
									<div class="row buttons col-md-8 col-sm-4 sub-style">
										<#if show??><#else>
										<a id="btn_save" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-cloud-upload"></i>
											刷卡保存
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
<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
							<div id="divbox" style="display: none;">
								<div class="profile-user-info profile-user-info-striped divbox">
									<div class="profile-info-row ceshi">
										<div class="profile-info-value div-value">
											<#if show??>
											<#else>
												<#assign num=0 />
												<#if list_cause??>
													<#list list_cause as clist>
														<div class="col-md-2 col-xs-6 col-sm-3 div-value2">
															<label>${(clist.causeName)! }</label>
															<input id="mynum${num}" type="text" value="${(clist.causeNum)! }" class=" input-value" />
														</div>
														<#assign num=num+1 />
													</#list>
												</#if>
											</#if>
										</div>
									</div>
								</div>
							</div>
<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
							<div id="divbox2" style="display: none;">
								<div class="profile-user-info profile-user-info-striped divbox">
									<div class="profile-info-row ceshi">
										<div class="profile-info-row">
											<div class="profile-info-name">条子名称</div>
											<div class="profile-info-value div-value">
											<#if list_material??>
												<select id="sl_material" style="width:150px;">
													<option value="baga">---请选择---</option>
													<#assign num2=0 />
													<#list list_material as list2>
														<option id="opt_${(list2.materialCode)! }" value="${(list2.materialCode)! }">${(list2.materialName)! }</option>
													<#assign num2=num2+1 />
													</#list>
												</select>
												<label class="requireField">*</label>
											</#if>
											</div>
										</div>
										<div class="profile-info-row">
											<div class="profile-info-name">条子数量</div>
											<div class="profile-info-value div-value">
												<input id="sl_num" type="text" style="width:150px;" />
												<label class="requireField">*</label>
											</div>
										</div>
									</div>
								</div>
							</div>
<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
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
	$("#tb_scraplater tr").each(function(){
		var wlcode=($(this).children("td:first").text());
		<#list list_material as list3>
			if("${(list3.materialCode)!}"==wlcode)
			{
				$("#opt_${(list3.materialCode)!}").hide();
			}
		</#list>
		
	});
}

//报废信息表是否为空
function sm_event()
{
	number="0";
	var i=0;
	<#list list_material as smlist>
		var xval=$("#input_msgbug"+i).val();
		if(xval!=null&&xval!="")
		{
			number="1";
		}
		i+=1;
	</#list>
	if(number=="0")
	{
		return false;
	}
	else
	{
		return true;
	}
}

</script>
