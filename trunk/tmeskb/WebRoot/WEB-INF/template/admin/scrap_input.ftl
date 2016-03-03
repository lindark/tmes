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
.input-value{width:80px;height:30px;line-height:30px;display:inline;}
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

							<form id="inputForm" class="validate" action="" method="post">
								<input type="hidden" id="my_id" name="my_id" value="${(my_id)! }" />
								<input type="hidden" id="xwbid" name="scrap.workingBill.id" value="${(workingbill.id)! }" />
								<input type="hidden" id="id" name="scrap.id" value="${(scrap.id)! }" />
								
								<!-- tabs start -->
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">报废信息</a></li>
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
											<br/>
											<div class="profile-user-info profile-user-info-striped">
												<#if show??>
												<#else>
												<div class="profile-info-row">
													<div class="row buttons col-md-8 col-sm-4">
														<a id="btn_addmsg" class="btn btn-white btn-default btn-sm btn-round">
															<i class="ace-icon glyphicon glyphicon-plus"></i>
															添加报废信息
														</a>
													</div>
												</div>					
												</#if>
												<div class="profile-info-row">
													<table id="tab_scrapmsg" class="table table-striped table-bordered table-hover">
														<#if show??>
														<tr>
															<th style="width: 15%;">物料编码</th>
															<th style="width: 30%;">物料描述</th>
															<th style="width: 20%;">责任划分</th>
															<th style="width: 35%;min-width:105px;">报废原因/数量</th>
														</tr>
														<#else>
														<tr>
															<th style="width: 15%;">物料编码</th>
															<th style="width: 20%;">物料描述</th>
															<th style="width: 20%;">责任划分</th>
															<th style="width: 35%;min-width:105px;">报废原因(数量,批次/责任人)</th>
															<th style="width: 10%;">操作</th>
														</tr>
														</#if>
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
															<#if list_scrapmsg??>
																<#assign num=0 />
																<#list list_scrapmsg as list>
																	<tr>
																		<td>${(list.smmatterNum)! }</td>
																		<td>${(list.smmatterDes)! }</td>
																		<td>
																			<select id="select_duty${num}" name="list_scrapmsg[${num}].smduty" style="width:150px;">
																				<option value="baga">&nbsp;</option>
																				<#list list_dict as dlist>
																					<option value="${(dlist.dictkey)! }" <#if (dlist.dictkey==list.smduty)!>selected</#if>>${(dlist.dictvalue)! }</option>
																				</#list>
																			</select>
																		</td>
																		<td>
																			<img id="img_addbug${num}" onclick="btn_addbug_event('${num}')" class="img_addbug" title="添加报废数量" alt="添加报废数量" src="${base}/template/shop/images/add_bug.gif" />
																			<span id="span_bug${num}">${(list.smreson)!}</span>
																			<input type="hidden"  id="input_msgbug${num}" name="list_scrapmsg[${num}].smreson" value="${(list.smreson)!}" />
																			<input type="hidden"  id="input_msgmenge${num}" name="list_scrapmsg[${num}].menge" value="${(list.menge)! }" />
																			<input type="hidden" name="list_scrapmsg[${num}].smmatterNum" value="${(list.smmatterNum)! }" />
																			<input type="hidden" name="list_scrapmsg[${num}].smmatterDes" value="${(list.smmatterDes)! }" />
																			
																			<input id="input_bugnum${num}" name="list_scrapbug[${num}].xbugnums" type="hidden" value="${(list.xsbnums)! }" />
																			<input id="input_bugid${num}" name="list_scrapbug[${num}].xbugids" type="hidden" value="${(list.xsbids)! }" />
																		</td>
																		<td><a id="a_delsm${num}" onclick="delsm_click('${num}')" href="javascript:void(0);">删除</a></td>
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
												    <div style="padding:6px;margin:6px;">报废产出</div>
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
																			<a onclick="edit_click('${(sllist.slmatterNum)! }')">编辑</a>
																			&nbsp;&nbsp;
																			<a onclick="del_click('${(sllist.slmatterNum)! }')">删除</a>
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
										<!-- 
										<a id="btn_confirm" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-cloud-upload"></i>
											刷卡确认
										</a>
										 -->
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
										<#if show??>
										<#else>
											<div class="profile-info-value div-value">
												<span style="color: red;font-size: 14px;">提示：第一个空格填写报废数量&nbsp;&nbsp;第二个空格填写批次/责任人</span>
												<#assign num=0 />
												<#if list_cause??>
													<div id="div_allcause">
													<#list list_cause as clist>
														<div id="div_${num}" class="col-md-6 col-xs-6 col-sm-6 div-value2">
															<label>${(clist.causeName)! }/责任人</label>
															<input id="mynum${num}" type="text" class=" input-value" />
															<input id="mypeople${num}" type="text" class=" input-value" />
														</div>
														<#assign num=num+1 />
													</#list>
													</div>
												</#if>
											</div>
										</#if>
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
												<select id="sl_material" style="width:150px;">
													<option value="baga">---请选择---</option>
													<#if list_so??>
														<#assign num2=0 />
														<#list list_so as list2>
															<option id="opt_${(list2.productsCode)! }" value="${(list2.productsCode)! }">${(list2.productsName)! }</option>
															<#assign num2=num2+1 />
														</#list>
													</#if>
												</select>
												<label class="requireField">*</label>
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
							<div id="divbox3" style="display: none;">
								<div class="profile-user-info profile-user-info-striped divbox">
									<div class="profile-info-row ceshi">
										<div class="profile-info-row">
											<div class="profile-info-name">物料描述</div>
											<div class="profile-info-value div-value">
												<select id="sl_bom" style="width:150px;">
													<option value="baga">---请选择---</option>
													<#if list_material??>
														<#assign num2=0 />
														<#list list_material as list2>
															<option id="optsm_${(list2.materialCode)! }" value="${(list2.materialCode)! }">${(list2.materialName)! }</option>
															<#assign num2=num2+1 />
														</#list>
													</#if>
												</select>
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
		/*
		$("#img_addbug"+i).live("click",function(){
			var idval=$(this).attr("id");
			idval=idval.substring(10,idval.length);
			btn_addbug_event(idval);
		});
		*/
		//责任类型变化事件
		$("#select_duty"+i).change(function(){
			var idval2=$(this).attr("id");
			idval2=idval2.substring(11,idval2.length);
			selectduty_event(idval2);
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
			i=idval.substring(5,idval.length);
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
		$("#mypeople"+i).val("");
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
			var mypeople=$("#mypeople"+i).val().replace(/\s+/g,"");
			rowids=rowids+id+",";
			rownums=rownums+boxnum+",";
			count=floatAdd(count,boxnum);			
			spanbug=spanbug+des+""+"/"+boxnum+"/"+mypeople+";";
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

/**
 * 按钮事件 
 */
//1.刷卡保存2.刷卡确认
function sub_event(my_id)
{
	$("#my_id").val(my_id);//赋值
	var url="";
	//1.刷卡保存
	if(my_id=="1")
	{
		//新增保存
		<#if add??>url="scrap!creditsave.action";</#if>
		//修改保存
		<#if edit??>url="scrap!creditupdate.action";</#if>
		tosubmit(url);//提交
	}
	//2.刷卡确认
	if(my_id=="2")
	{
		//新增确认
		<#if add??>url="scrap!creditsubmit.action";</#if>
		//修改确认
		<#if edit??>url="scrap!creditreply.action";</#if>
		tosubmit(url);//提交
		/*
		if(!sl_event())
		{
			layer.alert("报废后产出表为空,不能确认!",false);
		}
		else
		{
			
		}
		*/
	}
}

//缺陷隐藏或显示
function showorhide_event(obj)
{
	var i=0;
	<#list list_cause as list>
		var dutyval="${(list.causeduty)!}";
		if(obj==dutyval)
		{
			$("#div_"+i).show();
		}
		else
		{
			$("#div_"+i).hide();
		}
		i+=1;
	</#list>
}

//添加报废信息
function fuzhi_box3torow(val,txt)
{
	//获取表的行数，确定新增的行值
	var row=$("#tab_scrapmsg tr").length;
	row=floatSub(row,1);//减法
	var xhtml="<tr>" +
			"<td>"+val+"</td>" +
			"<td>"+txt+"</td>" +
			"<td><select id='select_duty"+row+"' name='list_scrapmsg["+row+"].smduty' style='width:150px;'>" +
			"<option value='baga'>&nbsp;</option>"+
			<#list list_dict as dlist2>
			"<option value='${(dlist2.dictkey)! }'>${(dlist2.dictvalue)! }</option>"+
			</#list>
			"</select></td>" +
			"<td>"+
			"<img id='img_addbug"+row+"' onclick=btn_addbug_event('"+row+"') class='img_addbug' title='添加报废数量' alt='添加报废数量' src='${base}/template/shop/images/add_bug.gif' />"+
			"<span id='span_bug"+row+"'></span>"+
			"<input type='hidden'  id='input_msgbug"+row+"' name='list_scrapmsg["+row+"].smreson' />"+
			"<input type='hidden'  id='input_msgmenge"+row+"' name='list_scrapmsg["+row+"].menge' />"+
			"<input type='hidden' name='list_scrapmsg["+row+"].smmatterNum' value='"+val+"' />"+
			"<input type='hidden' name='list_scrapmsg["+row+"].smmatterDes' value='"+txt+"' />"+
			"<input id='input_bugnum"+row+"' name='list_scrapbug["+row+"].xbugnums' type='hidden' />"+
			"<input id='input_bugid"+row+"' name='list_scrapbug["+row+"].xbugids' type='hidden' />"+
			"</td>"+
			"<td>" +
			"<a id='a_delsm"+row+"' onclick='delsm_click("+row+")' href='javascript:void(0);'>删除</a>" +
			"</td>" +
			"</tr>";
	$("#tab_scrapmsg").append(xhtml);
}
</script>
