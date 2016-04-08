<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>半成品巡检管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/template/admin/js/BasicInfo/it_input.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/layer/jquery.easydrag.handler.beta2.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
	font-family: 微软雅黑;
}

.div1 {
	background-color: #f9f9f9;
	padding: 6px 10px 6px 4px;
	border-top: 1px dotted #D5E4F1;
	display: table-cell;
	vertical-align: middle;
}

.sub-style {
	float: right;
}

.img_addbug {
	cursor: pointer;
	margin-left: 1px;
}

#span_bug {
	margin-left: 5px;
}

#test1 {
	width: 800px;
	height: 400px;
}

.div-value {
	padding-right: 30px;
	min-width: 200px;
}

.div-value2 {
	text-align: right;
	padding-right: 0px;
}

.input-value {
	width: 80px;
	height: 30px;
	line-height: 30px;
}

#tb_scraplater a {
	cursor: pointer;
}

.divbox {
	margin-top: 20px;
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
				<script type="text/javascript">try {ace.settings.check('breadcrumbs', 'fixed')} catch (e) {}</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a></li>
					<li class="active">半成品巡检列表</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" name="inputForm" class="validate"
								action="<#if isAdd??>itermediate_test!creditsave.action<#else>itermediate_test!creditupdate.action</#if>"
								method="post">
								<input type="hidden" id="my_id" name="my_id"
									value="${(my_id)! }" /> <input type="hidden"
									id="workingBillId" name="itermediateTest.workingbill.id"
									value="${(workingbill.id)! }" /> <input type="hidden" id="id"
									name="itermediateTest.id" value="${(itermediateTest.id)! }" />

								<!-- tabs start -->
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">半成品巡检编辑</a></li>
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
												<#if isAdd>
												<div class="profile-info-name">组件编码</div>
												<div class="col-sm-4">
													<input type="text" name="lgpla"
														class="input input-sm form-control formText {required: true}"
														value="${materialCode!"303" }" id="materialCode" />
												</div>
												<div class="col-sm-2">
													<a id="searchButton1"
														class="btn btn-white btn-default btn-sm btn-round"> <i
														class="ace-icon fa fa-filter blue"></i> 搜索 </a>
												</div>
												</#if>
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
												<table id="tb_itd"
													class="table table-striped table-bordered table-hover">
													<tr>
														<th class="center" width="250">组件编码</th>
														<th class="center" width="400">组件名称</th>
														<th class="center" width="100">检验数量</th>
														<!--<th class="center" "display:none">尺寸1</th>
														<th class="center" "display:none">尺寸2</th>
														<th class="center" "display:none">尺寸3</th>
														<th class="center" "display:none">尺寸4</th>
														<th class="center" "display:none">尺寸5</th>
														--><th class="center">不合格原因/数量</th>
														<th class="center" width="100">合格数量</th>
													</tr>
													<#if show??> <#if list_itmesg??> <#assign num=0 /> <#list
													list_itmesg as list>
													<tr>
														<td>${(list.materialCode)! }</td>
														<td>${(list.materialName)! }</td>
														<td>${(list.testAmount)! }</td>
														<!--<td>${(list.goodsSzie1)! }</td>
														<td>${(list.goodsSzie2)! }</td>
														<td>${(list.goodsSzie3)! }</td>
														<td>${(list.goodsSzie4)! }</td>
														<td>${(list.goodsSzie5)! }</td>
														--><td>${(list.failReason)! }</td>
														<td>${(list.passAmount)! }</td>
														
													</tr>
													<#assign num=num+1 /> </#list> </#if> <#else> <#if
													list_material??> <#assign num=0 /> <#list list_material as
													list>
													<tr>
														<td>${(list.materialCode)! }</td>
														<td>${(list.materialName)! }</td>
														<td><input type="text"
															name="list_itmesg[${num}].testAmount"
															value="${(list.xtestAmount)!}"
															class="form-control formText {digits:true} test_num" />
														</td>
														<!--<td><input type="text"
															name="list_itmesg[${num}].goodsSzie1"
															value="${(list.xgoodsSzie1)!}" class="form-control" />
														</td>
														<td><input type="text"
															name="list_itmesg[${num}].goodsSzie2"
															value="${(list.xgoodsSzie2)!}" class="form-control" />
														</td>
														<td><input type="text"
															name="list_itmesg[${num}].goodsSzie3"
															value="${(list.xgoodsSzie3)!}" class="form-control" />
														</td>
														<td><input type="text"
															name="list_itmesg[${num}].goodsSzie4"
															value="${(list.xgoodsSzie4)!}" class="form-control" />
														</td>
														<td><input type="text"
															name="list_itmesg[${num}].goodsSzie5"
															value="${(list.xgoodsSzie5)!}" class="form-control" />
														</td>
														--><td><img id="img_addbug${num}" class="img_addbug"
															title="添加不合格原因" alt="添加不合格原因"
															src="${base}/template/shop/images/add_bug.gif" /> <span
															id="span_bug${num}">${(list.xfailReason)!}</span> <input
															type="hidden" id="input_msgbug${num}"
															name="list_itmesg[${num}].failReason"
															value="${(list.xfailReason)!}" /> <input type="hidden"
															name="list_itmesg[${num}].materialCode"
															value="${(list.materialCode)! }" /> <input type="hidden"
															name="list_itmesg[${num}].materialName"
															value="${(list.materialName)! }" /> <input type="hidden"
															name="list_itmesg[${num}].materialId"
															value="${(list.id)! }" /> <input type="hidden"
															name="list_itmesg[${num}].id" value="${(list.xitid)! }" />
															<input type="hidden" id="input_msgmenge${num}"
															class="noNum" name="list_itmesg[${num}].failAmount"
															value="${(list.xfailAmount)! }" /> <input
															id="input_bugnum${num}"
															name="list_itbug[${num}].xbugnums" type="hidden"
															value="${(list.xrecordNum)! }" /> <input
															id="input_bugid${num}" name="list_itbug[${num}].xbugids"
															type="hidden" value="${(list.xrecordid)! }" />
														</td>
														<td><input type="text"
															class="form-control formText {digits:true} pass_num" readonly="readonly"
															value="${(list.xpassamount)! }"
															/>
														</td>
													</tr>
													<#assign num=num+1 /> </#list> </#if> </#if>
												</table>
											</div>
										</div>
									</div>
									<br />
									<div class="row buttons col-md-8 col-sm-4 sub-style">
										<#if show??><#else> <a id="btn_save"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-cloud-upload"></i> 刷卡保存 </a> <a
											id="btn_confirm"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-cloud-upload"></i> 刷卡确认 </a> </#if> <a
											id="btn_back"
											class="btn btn-white btn-default btn-sm btn-round"> <i
											class="ace-icon fa fa-home"></i> 返回 </a>
									</div>
								</div>
								<!-- end tabs -->

							</form>
							<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
							<div id="divbox" style="display: none;">
								<div class="profile-user-info profile-user-info-striped divbox">
									<div class="profile-info-row ceshi">
										<div class="profile-info-value div-value">
											<#if show??> <#else> <#assign num=0 /> <#if list_cause??>
											<#list list_cause as clist>
											<div class="col-xs-4" style="margin:2px auto;">
												<label>${(clist.causeName)! }</label> <input
													id="mynum${num}" type="text" value="${(clist.causeNum)! }"
													class="input-value" />
											</div>
											<#assign num=num+1 /> </#list> </#if> </#if>
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
//合计计算
$(function () {
	$(".test_num").change(function(){		
		var num = $(this).val();
		var noNum = $(this).parent().parent().find(".noNum").val();
		var passNum = num-noNum;
		
		
		$(this).parent().parent().find(".pass_num").val(passNum);
		if(passNum<0){
			alert("请输入正确数量!");
		}
	});
	})

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
			spanbug=spanbug+des+"/"+boxnum+",";
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
	var testnum=$("#input_msgmenge"+index).parent().parent().find(".test_num").val();
	if (testnum-count<0&&testnum>0){
		$("#input_msgmenge"+index).parent().parent().find(".pass_num").val(0);
		alert("请输入正确数量")
	}else{
		$("#input_msgmenge"+index).parent().parent().find(".pass_num").val(testnum-count);
	}
	
}


$(function(){
	//搜索
	$("#searchButton1").click(function() {
		 var materialCode = $("#materialCode").val(); 
		 window.location.href = "itermediate_test!add.action?matnr=${(workingbill.matnr)!}"+
				 				"&workingBillId=${workingbill.id}&materialCode ="+ materialCode;
	});
	// 刷卡保存
	$("#btn_save").click(function() {
		sub_event("1");
	});

	// 刷卡提交
	$("#btn_confirm").click(function() {
		sub_event("2")
	});
	
	//返回
	$("#btn_back").click(function(){
		window.history.back();
	});
	
	//给按钮加事件--添加缺陷信息事件
	addbug_event();
	//缺陷内容事件
	cause_event();
	
	
});


//添加报废原因按钮事件
function btn_addbug_event(index)
{
	//选择当前行的事件
	rowtobox_event(index);
	layer.open({
	    type: 1,
	    shade:0.52,//遮罩透明度
	    title: "添加不合格原因",
	    area:["600px","300px"],//弹出层宽高
	    closeBtn: 1,//0没有关闭按钮，1-3不同样式关闭按钮---右上角的位置
	    shadeClose: false,//点击遮罩层(阴影部分)：true时点击遮罩就关闭，false时不会
	    btn:["确定","取消"],
	    yes:function(){boxtorow_event(index);layer.closeAll();},
	    content: $("#divbox")//可以 引入一个页面如："a.jsp"  
	});
}

//报废数量输入框change事件
function write_bugnum_event(index)
{
	var bugnum=$("#mynum"+index).val().replace(" ","");
	if(bugnum!=null&&bugnum!="")
	{
		var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
		if(!reg.test(bugnum))
		{
			layer.alert("输入不合法!",false);
			$("#mynum"+index).val("");
		}
		else
		{
			bugnum=setScale(bugnum,0,"");//精度
			$("#mynum"+index).val(bugnum);
		}
	}
}

/**
 * 按钮事件 
 */
//1.刷卡保存2.刷卡确认
function sub_event(my_id) 
{
	//保存时检查合格数量
	var nums = $('.pass_num');
	for(i=0;i<nums.length;i++)
	{
		var num=nums.eq(i).val();
		if(num<0){
			layer.msg("请确认输入的数量", {icon: 5});
			return false;
		}
	}
	$("#my_id").val(my_id);//赋值
	if(my_id=="1")
	{
		var dt=$("#inputForm").serialize();
		console.log(dt);
		<#if isAdd??>
		var url = "itermediate_test!creditsave.action";		
	    <#else>
		var url = "itermediate_test!creditupdate.action";
	   </#if>
	   var workingBillId = $("#workingBillId").val();  
	    credit.creditCard(url,function(data){	
	    	if(data.status=="success"){
	    		layer.alert(data.message, {icon: 6},function(){
			    window.location.href="itermediate_test!list.action?workingBillId="+ workingBillId;
	    	});
	    	}else if(data.status=="error"){
	    		layer.alert(data.message,{
	    			closeBtn: 0,
	    			icon: 5,
	    			skin:'error'
	    		});
	    	}
		},dt)
	}
	if(my_id=="2")
	{
		if(!sl_event())
		{
			layer.alert("半成品巡检表为空,不能确认!",false);
		}
		else
		{
		    var dt=$("#inputForm").serialize();
		    var url="itermediate_test!creditsave.action";
	    	var workingBillId = $("#workingBillId").val();
		    credit.creditCard(url,function(data){
		    	if(data.status=="success"){
		    		layer.alert(data.message, {icon: 6},function(){
				    window.location.href="itermediate_test!list.action?workingBillId="+ workingBillId;
		    	});
		    	}else if(data.status=="error"){
		    		layer.alert(data.message,{
		    			closeBtn: 0,
		    			icon: 5,
		    			skin:'error'
		    		});
		    	}
			},dt)
		}
	}
}


function sl_event(){
	var i="0";
	$("#tb_itd tr").each(function(){
		var wlcode=($(this).children("td:eq(2)").children("input:first").val());		
		if(wlcode!=null&&wlcode!=""){
			i="1";
		}
	});
	if(i=="1"){
		return true;
	}else{
		return false;
	}		
}



</script>
