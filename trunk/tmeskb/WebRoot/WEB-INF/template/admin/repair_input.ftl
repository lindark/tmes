<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>返修管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />

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

<script type="text/javascript"
	src="${base}/template/admin/js/BasicInfo/repair_input.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}

.img_costcenter {
	cursor: pointer;
	margin-left: 1px;
}
.jianxin_input{
	width:350px;
    min-height: 30px;
    border: 1px solid #c7c0c0;
    float: left;
    text-align: -webkit-left;
    display: block;
    word-break: break-all;
    word-wrap: break-word;
}
a.jianxin_a{
	content: "\f00d";
    color: #888;
    font-family: '微软雅黑',FontAwesome;
    position: absolute;
    top: 4px;
    right: 3px;
    display: block;
    width: 12px;
    height: 12px;
    font-size: 1px;
    font-weight: bold;
    text-decoration: none;
}
li.jianxin_li{
	position: relative;
    margin: 3px 0 3px 5px;
    padding: 3px 5px 3px 5px;
    border: 1px solid #aaa;
    border-radius: 3px;
    background-color: #e4e4e4;
    background-image: -webkit-gradient(linear,50% 0,50% 100%,color-stop(20%,#f4f4f4),color-stop(50%,#f0f0f0),color-stop(52%,#e8e8e8),color-stop(100%,#eee));
    background-image: -webkit-linear-gradient(#f4f4f4 20%,#f0f0f0 50%,#e8e8e8 52%,#eee 100%);
    background-image: -moz-linear-gradient(#f4f4f4 20%,#f0f0f0 50%,#e8e8e8 52%,#eee 100%);
    background-image: -o-linear-gradient(#f4f4f4 20%,#f0f0f0 50%,#e8e8e8 52%,#eee 100%);
    background-image: linear-gradient(#f4f4f4 20%,#f0f0f0 50%,#e8e8e8 52%,#eee 100%);
    background-clip: padding-box;
    box-shadow: 0 0 2px #fff inset,0 1px 0 rgba(0,0,0,.05);
    color: #333;
    line-height: 13px;
    cursor: default;
    float: left;
    list-style: none;
}
ul.jianxin_ul{
	position: relative;
    overflow: hidden;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    width: 100%;
    height: auto!important;
    height: 1%;
    border: 1px solid #aaa;
    background-color: #fff;
    background-image: -webkit-gradient(linear,50% 0,50% 100%,color-stop(1%,#eee),color-stop(15%,#fff));
    background-image: -webkit-linear-gradient(#eee 1%,#fff 15%);
    background-image: -moz-linear-gradient(#eee 1%,#fff 15%);
    background-image: -o-linear-gradient(#eee 1%,#fff 15%);
    background-image: linear-gradient(#eee 1%,#fff 15%);
    cursor: text;
    list-style-type: none;
    box-shadow: none!important;
}
a{
	color: #4a4a4a;;
	font-weight: bold;
    text-decoration: none;
    cursor: pointer;
    margin-left:5px;
}
a:hover {
    color: #888;
    text-decoration: none;
    
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
						href="admin!index.action">管理中心</a>
					</li>
					<li class="active">成品返修</li>
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
							<input type="hidden" name="loginid" id="loginid" value="<@sec.authentication property='principal.id' />" />
								<input type="hidden" name="repair.id" value="${(repair.id)!}" />
								<input type="hidden" name="repair.workingbill.id"
									value="${workingbill.id} " id="wkid"> <input
									type="hidden" name="workingBillId" value="${workingbill.id}" />
								<input type="hidden" name="repair.CXORJC" id="input_cxorjc"
									value="${(repair.CXORJC)! }" />
								<div id="inputtabs">
									<ul >
										<li><a href="#tabs-1">成品返修</a>
										</li>

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

												<div class="profile-info-name">累计返修数量</div>
												<div class="profile-info-value">
													<span class="editable editable-click" id="age">${workingbill.totalRepairAmount}</span>
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">本次返修数量</div>
												<div class="profile-info-value">
													<#if show??> ${(repair.repairAmount)!} <#else> <input
														type="text" id="input_num" name="repair.repairAmount"
														value="${(repair.repairAmount)!}"
														class=" input input-sm formText {required: true}" /> <label
														class="requireField">*</label> </#if>
												</div>

												<div class="profile-info-name">返修部位</div>
												<#-- <div class="profile-info-value">
													<#if show??> ${(repair.repairPart)!} <#else> <input
														type="text" id="input_part" name="repair.repairPart"
														value="${(repair.repairPart)!}"
														class=" input input-sm formText {required: true}" /> <label
														class="requireField">*</label> </#if>
												</div> -->
												<div class="profile-info-row">
												<div class="profile-info-value">
													<#if show??> 
														${(repair.repairPart)! } 
													<#else> 
														<select name="repair.repairPart" id="rpart_select" multiple class="chosen-select">
															<#if repairDetailList??>
															<option value=""<#if (repair.repairPart=="")!>selected</#if>></option>
																<#if (repair.repairPart??)!>
																	<#assign arry = repair.repairPart?split(",")>
																	<#assign flag = false>
																	<#list repairDetailList as list>
																		<#list arry as ar>
																			<#if ar?trim==list.dictvalue>
																				<#assign flag = true>
																				<#break>
																			</#if>
																		</#list>
																		<option value="${(list.dictvalue)! }"<#if flag==true>selected</#if>>
																			${(list.dictvalue)! }
																		</option>	
																	</#list>
																<#else>
																	<#list repairDetailList as list>
																		<option value="${(list.dictvalue)! }">
																			${(list.dictvalue)! }
																		</option>
																	</#list>
																</#if>
															</#if>
														</select>
													</#if>
												</div>
											</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">责任人</div>
												<div class="profile-info-value" style="width: 45%; word-break: break-all; word-wrap: break-word;">	
												<#if show??> 
														<#assign  rname=(repair.responsibleNum?split(","))! />
														<#assign  rdu=(repair.duty?split(","))! />
														<#list rdu as rdulist>
															<#assign  rn=rdulist_index />
															${rdulist?trim}(${rname[rn]?trim}),
														</#list>
														
												<#else>	
													<td>
													<img id="pId" class="img_addbug" title="添加责任人信息" alt="添加责任人信息" style="cursor:pointer;margin-left:20px" src="${base}/template/shop/images/add_bug.gif" />&nbsp;&nbsp;&nbsp;*
														<!-- <input type="hidden" name="repair.duty" id="responsibleNa" value="${(repair.duty)!}" class="formText {required: true}" /> -->
														<div class="jianxin_input">
															<ul class="jianxin_ul" id="jianxin_ul">
															<#assign  rid=(repair.responsibleId?split(","))! />
															<#assign  rname=(repair.responsibleNum?split(","))! />
															<#assign  rdu=(repair.duty?split(","))! />
															<#list rid as ridlist>
																<li class="jianxin_li" >
																	<#assign  rn=ridlist_index />
																	<span>${rdu[rn]?trim}${rname[rn]?trim}</span>
																	<a href="javascript:void(0);" id="${rname[rn]?trim}" onclick="del_li('#${rname[rn]?trim}')">X</a>
																	<input type="hidden" name="repair.responsibleId" value="${ridlist?trim}"/>	
																	<input type="hidden" name="repair.duty" value="${rdu[rn]?trim}"/>
																	<input type="hidden" name="repair.responsibleNum" value="${rname[rn]?trim}"/>													
																</li>
															
															</#list>
															<!--	<li class="jianxin_li">
																	<span>111111</span>
																	<a href="javascript:void(0);" onclick="del_li">X</a>
																	<input type="hidden" name="repair.responsibleId"/>	
																	<input type="hidden" name="repair.responsibleNum"/>													
																</li> -->
															</ul>	
														</div>
													</td>
												</#if>
												</div>
												<div class="profile-info-name">批次</div>
												<div class="profile-info-value">
													<#if show??> ${(repair.charg)!} <#else> <input type="text"
														id="input_charg" name="repair.charg"
														value="${(repair.charg)!}" class=" input input-sm" />
													<!-- 
														<label class="requireField">*</label>
														 -->
													</#if>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">责任工序</div>
												<div class="profile-info-value">
													<#if show??> 
														${(repair.responseName)! } 
													<#else> 
														<select name="repair.processCode" id="r_select" multiple class="chosen-select">
															<#if processList??>
																<option value="" <#if (repair.processCode=="")!>selected</#if>></option>
																<#if (repair.processCode??)!>
																	<#assign arry = repair.processCode?split(",")>
																	<#assign flag = false>
																	<#list processList as list>
																		<#list arry as ar>
																			<#if ar?trim==list.dictkey>
																				<#assign flag = true>
																				<#break>
																			</#if>
																		</#list>
																		<option value="${(list.dictkey)! }"<#if flag==true>selected</#if>>
																			${(list.dictvalue)! }
																		</option>
																		<#assign flag = false>
																	</#list>
																<#else>
																	<#list processList as list>
																		<option value="${(list.dictkey)! }"<#if (repair.processCode==list.dictkey)!>selected</#if>>
																			${(list.dictvalue)! }
																		</option>
																	</#list>
																</#if>
															</#if>
														</select>
													</#if>
												</div>
												
												<div class="profile-info-name">模具</div>
												<div class="profile-info-value">
													<#if show??> ${(repair.xmould)! } <#else> <select
														name="repair.mould"> <#list list_dict as list>
														<option value="${list.dictkey}"<#if
															(list.dictkey==repair.mould)!>
															selected</#if>>${list.dictvalue}</option> </#list>
													</select> </#if>
												</div>
											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">成品/组件</div>
												<div class="profile-info-value">
													<#if show??> ${(repair.xrepairtype)! } <#else> <label
														class="pull-left inline"> <small
														class="muted smaller-90">成品:</small> <input type="radio"
														id="repairtype_cp" class="ace" name="repair.repairtype"
														value="CP"<#if (isAdd || repair.repairtype ==
														'CP')!> checked</#if> /> <span class="lbl middle"></span>
													</label>
													<!--
					                        			&nbsp;&nbsp;				
					                        			<label class="pull-left inline">
					                            			<small class="muted smaller-90">组件:</small>
						                        			<input type="radio" id="repairtype_zj" class="ace" name="repair.repairtype" value="ZJ"<#if (repair.repairtype == 'ZJ')!> checked</#if>  />
						                         			<span class="lbl middle"></span>
					                        			</label>
					                        			  -->
													</#if>
												</div>

												<div class="profile-info-name">部门名称/成本中心</div>
												<div class="profile-info-value">
													<#if show??> 
													${(repair.departmentName)! }/${(repair.costcenter)!} 
													<#else> 
													<a href="javascript:void(0)" id="deptclick"> 
														<img
														class="img_addbug" title="部门选择" alt="部门选择"
														style="cursor:pointer"
														src="/template/shop/images/add_bug.gif"> </a> 
														<#if add??> 
													<span id="departmentcontext" ></span>
													<input type="hidden" id="departmentName"
														name="repair.departmentName" value="${(departmentName)! }" />
													<input type="hidden" id="costcenter"
														name="repair.costcenter" value="${(costcenter)! }" />
													<#else> <span id="departmentcontext" >${(repair.departmentName)!
														}/${(repair.costcenter)!}</span> <input type="hidden"
														id="departmentName" name="repair.departmentName"
														value="${(repair.departmentName)! }" /> <input
														type="hidden" id="costcenter" name="repair.costcenter"
														value="${(repair.costcenter)! }" />
														</#if>
														</#if>
													<!--
													<#if show??>
														${(repair.costcenter)!}
													<#else>
														<img id="img_costcenter" class="img_costcenter" title="添加成本中心" alt="添加成本中心" src="${base}/template/shop/images/add_bug.gif" />
														<#if add??>
															<span id="span_costcenter">10008431</span>
															<input type="hidden" id="input_costcenter" name="repair.costcenter" value="10008431" />
														<#else>
															<span id="span_costcenter">${(repair.costcenter)!}</span>
															<input type="hidden" id="input_costcenter" name="repair.costcenter" value="${(repair.costcenter)!}" />
														</#if>
														<label class="requireField">*</label>
													</#if>
												-->
												</div>
											</div>
											
										</div>
										<!--weitao end modify , gyf modify-->
										<br />
										<!-- gyf start piece-->
										<div id="div_addpiece"
											class="profile-user-info profile-user-info-striped">
											<#if show??> <#else>
											<div class="profile-info-row">
												<div class="row buttons col-md-8 col-sm-4">
													<a id="btn_addpiece"
														class="btn btn-white btn-default btn-sm btn-round"> <i
														class="ace-icon glyphicon glyphicon-plus"></i> 添加产品组件 </a>
												</div>
											</div>
											</#if>

											<div class="profile-info-row">
												<table id="tb_repairpiece"
													class="table table-striped table-bordered table-hover">
													<#if show??>
													<tr>
														<th style="width:20%;">组件编码</th>
														<th style="width:30%;">组件描述</th>
														<th style="width:20%;">计划数量</th>
														<th style="width:15%;">组件数量</th>
														<th style="width:15%;">返修总数量</th>
													</tr>
													<#if list_rp??> <#list list_rp as rplist>
													<tr>
														<td>${(rplist.rpcode)! }</td>
														<td>${(rplist.rpname)! }</td>
														<td>${(rplist.productnum)! }</td>
														<td>${(rplist.piecenum)! }</td>
														<td>${(rplist.rpcount)! }</td>
													</tr>
													</#list> </#if> <#else>
													<tr>
														<th style="width:20%;">组件编码</th>
														<th style="width:35%;">组件描述</th>
														<th style="width:15%;">计划数量</th>
														<th style="width:15%;">组件数量</th>
														<th style="width:15%;">操作</th>
													</tr>
													<#if list_rp??> <#assign rpnum=0 /> <#list list_rp as
													rplist>
													<tr>
														<td>${(rplist.rpcode)! }<input type="hidden"
															name="list_rp[${rpnum}].rpcode"
															value="${(rplist.rpcode)! }" />
														</td>
														<td>${(rplist.rpname)! }<input type="hidden"
															name="list_rp[${rpnum}].rpname"
															value="${(rplist.rpname)! }" />
														</td>
														<td>${(rplist.productnum)! }<input type="hidden"
															name="list_rp[${rpnum}].productnum"
															value="${(rplist.productnum)! }" />
														</td>
														<td>${(rplist.piecenum)! }<input type="hidden"
															name="list_rp[${rpnum}].piecenum"
															value="${(rplist.piecenum)! }" />
														</td>
														<td><a id="a_${(rplist.rpcode)! }"
															onclick="del_event(${(rplist.rpcode)! })"
															style="cursor:pointer;">删除</a></td>
													</tr>
													<#assign rpnum=rpnum+1 /> </#list> </#if> </#if>
												</table>
											</div>
										</div>
										<!-- gyf end piece-->
									</div>
									<div class="buttonArea">
										<#if show??> <#else> <a id="btn_save"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-cloud-upload"></i> 刷卡保存 </a> </#if> <a
											id="btn_back"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-home"></i> 返回 </a>
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
	        	//$("#departid").val(depart.departid);
	        	//$("#departmentNametext").text(depart.departName);
	        	//$("#departmentName").val(depart.departName);
	        	//$("#costcenter").val(depart.costcenter);
	        	//$("#movetype").val(depart.movetype);
	        	//$("#movetypetext").text(depart.movetype);
	        	//$("#movetype1").val(depart.movetype1);
	        	//$("#movetypetext1").text(depart.movetype1);
	        	//$("#costcentertext").text(depart.costcenter);
	        	$("#departmentcontext").text(depart.departName+'/'+depart.costcenter);
	        	$("#costcenter").val(depart.costcenter);
	        	$("#departmentName").val(depart.departName);
	        	layer.close(index);
	        	return false;
	        },
	        end:function(index,layero){
	        	$this.removeAttr("disabled");
	        }
	        
	    });
		return false;
	})
})
function showUnit(num1){
	var title = "选择责任人";
	var width="800px";
	var height="632px";
	var content="process_handover!browser.action";
	jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){		
		var iframeWin=window[layero.find('iframe')[0]['name']];//获得iframe的对象
		var array=iframeWin.getGridId();
		var behtml = "";
		//alert(array);
		for(var i=0;i<array.length;i++){
			var work = array[i];
			var id=work.split(",");
			//alert(id);
			behtml = behtml+"<li class=\"jianxin_li\"><span>"+id[1]+id[2]+"</span><a href=\"javascript:void(0);\" id=\"a_"+id[2]+"\" onclick=\"del_li('#a_"+id[2]+"')\">X</a><input type=\"hidden\" name=\"repair.responsibleId\" value=\""+id[0]+"\"><input type=\"hidden\" name=\"repair.duty\" value=\""+id[1]+"\"><input type=\"hidden\" name=\"repair.responsibleNum\"  value=\""+id[2]+"\"></li>";
		}
		num1.parent().find("#jianxin_ul").append(behtml);
		layer.close(index); 
	});
}
function del_li(a_li){
	$(a_li).parents("li").remove();
}
/*
$(function(){
	$(".img_addbug").click( function() {
		showUnit($(this));
	});
});*/
$(function(){
	$("#pId").click( function() {
		showUnit($(this));
	});
});
$(function(){
	//刷卡保存
	$("#btn_save").click(function(){
		//提交
		if(iscansave()){
			var url=""
			<#if add??>
				url = "repair!creditsave.action";
			<#else>
				url = "repair!creditupdate.action";
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
	
	
	/* $("#r_select").on("change",function(){
		var rpt = '<option value="1">weq</option>';
		
		$("#rpart_select option").remove();
		$("#rpart_select").append(rpt);		
		$("#rpart_select").val("1");
		$("#rpart_select").chosen();
		$("#rpart_select").trigger("chosen:updated");
	}); */
});
</script>