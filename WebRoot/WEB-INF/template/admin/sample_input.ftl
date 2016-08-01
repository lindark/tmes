<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>抽检单管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript"src="${base}/template/admin/js/BasicInfo/sample_input.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}
.div-name{text-align: center;}
.div-value{padding-right:30px;min-width:200px; }
.div-value2{text-align:right;padding-right:0px;min-width:200px;}
.input-value{width:80px;height:30px;line-height:30px;}
.sub-style{float: right;}
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
					<li class="active">抽检单</li>
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
								<input type="hidden" id="input_qulified"  name="sample.qulified" value="${(sample.qulified)! }" />
								<input type="hidden" id="input_qrate" name="sample.qulifiedRate" value="${(sample.qulifiedRate)! }" />
								<input type="hidden" id="input_rd" name="info"  value="" />
								<input type="hidden" id="input_rnum" name="info2" value="" />
								<input type="hidden" id="my_id" name="my_id" value="${(my_id)! }" />
								<input type="hidden" id="xwbid" name="sample.workingBill.id" value="${(workingbill.id)! }" />
								<input type="hidden" id="id" name="sample.id" value="${(sample.id)! }" />
								
								<!-- tabs start -->
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">抽检单</a></li>
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
												<div class="profile-info-row">
													<div class="profile-info-name">抽检数量</div>
													<div class="profile-info-value">
														<#if show??>
															<span>${(sample.sampleNum)! }</span>
														<#else>
															<input id="sample_num" type="text" name="sample.sampleNum" value="${(sample.sampleNum)! }" class=" input input-sm  formText" />
														</#if>
														<label class="requireField">*</label>
													</div>
													<div class="profile-info-name">抽检类型</div>
													<div class="profile-info-value">
														<#if show??>
															<span>${(sampletype)! }</span>
														<#else>
															<select name="sample.sampleType" id="sample_type" class="input input-sm">
																<#list list_dict as dlist>
																<option value="${(dlist.dictkey)! }" <#if (dlist.dictkey==sample.sampleType)!>selected</#if>>${(dlist.dictvalue)! }</option>
																</#list>
															</select>
														</#if>
														<label class="requireField">*</label>
													</div>
												</div>
												<div class="profile-info-row">
													<div class="profile-info-name">合格数量</div>
													<div class="profile-info-value">
														<span id="span_sq">${(sample.qulified)! }</span>
													</div>
													<div class="profile-info-name">合格率</div>  
													<div class="profile-info-value">
														
															<#if add??>
																<span id="span_qrate">0.00%</span>
															<#else>
																<span id="span_qrate">${(sample.qulifiedRate)! }</span>
															</#if>
														
													</div>
												</div>
												<div class="profile-info-row">
												<div class="profile-info-name">模具</div>
												<#if show??>
												<div class="profile-info-value">
														<span >${(sample.moudle)! }</span>
													</div>
												<#else>
												<div class="profile-info-value">
												    <select name="sample.moudle"> 
												    <!-- <#list
														allMoudle as list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && dailyWork.moudle ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list> -->
														<#list pager.list as list>
														<option value="${list.station}" <#if (list.station==sample.moudle)!>selected</#if>>${list.station}</option>
														</#list>
													</select>
													
												</div>
												</#if>
												
											</div>
												</div>
											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row ceshi">
													<div class="profile-info-value">
														<sapn id="span_tip" style="color:red;"></sapn>
													</div>
												</div>
												
												<div class="profile-info-row ceshi">
													<div class="profile-info-name div-name">缺陷内容</div>
												</div>
												<div class="profile-info-row ceshi">
													<div class="profile-info-value div-value">
														<#if show??>
															<#list list_samrecord as list>
																<div class="col-md-2 col-xs-6 col-sm-3 div-value2">
																	<label>${(list.recordDescription)! }</label>
																	<input type="text" value="${(list.recordNum)! }" readonly="readonly" class=" input-value" />
																</div>
															</#list>
														<#else>
															<#assign num=0 />
															<#if list_cause??>
																<#list list_cause as list>
																	<div class="col-md-2 col-xs-6 col-sm-3 div-value2">
																		<label>${(list.causeName)! }</label>
																		<input id="sr_num${num}" type="text" value="${(list.causeNum)! }" class=" input-value" />
																		<input id="sr_num2${num}" type="hidden" value="${(list.causeNum)! }" />
																	</div>
																	<#assign num=num+1 />
																</#list>
															</#if>
														</#if>
													</div>
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
var qxnums="";
var qxids="";
$(function(){
	//缺陷事件
	cause_event();
	//必填提示隐藏/显示
	tip_event();
});
//缺陷事件
function cause_event()
{
	var i=0;
	<#list list_cause as list>
		$("#sr_num"+i).change(function(){
			var idval=$(this).attr("id");
			var count = i;
			i=idval.substring(idval.length-1,idval.length);
			var samplenum=$("#sample_num").val();//抽检数量
			var num_bt=$("#sr_num2"+i).val();//备胎
			var num_qx=$(this).val().replace(/\s+/g,"");//缺陷
			if(num_qx!=null&&num_qx!="")
			{
				var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
				if(reg.test(num_qx))
				{	
					num_qx=setScale(num_qx,0,"");//精度--去小数
					$(this).val(num_qx);
					if(count>10){
						var the_sr_num2 = count-1;
						$("#sr_num2"+the_sr_num2).val(num_qx);
					}else{
					$("#sr_num2"+i).val(num_qx);//备胎，防止第一次输入正确第二次不正确时无法获取原数据--合格数量无法重新计算
					}
					if(num_qx>=0&&(samplenum!=null&&samplenum!=""))
					{
						if(num_bt>0&&num_bt!=null&&num_bt!="")
						{
							tocalc(samplenum,num_qx,num_bt);
						}
						else
						{
							tocalc(samplenum,num_qx,"");
						}
					}
					else
					{
						$("#span_tip").text("");
					}
				}
				else
				{
					layer.alert("输入不合法!",false);
					$(this).val("");//缺陷数量
					$("#sr_num2"+i).val("");//缺陷数量--备胎
					if(num_bt!=""&&num_bt!=null&&num_bt>0&&(samplenum!=null&&samplenum!=""))
					{
						tocalc(samplenum,"",num_bt);
					}
				}
			}
			else
			{
				$("#sr_num2"+i).val("");//缺陷数量--备胎
				//alert(samplenum);
				if(num_bt!=""&&num_bt!=null&&num_bt>0&&(samplenum!=null&&samplenum!=""))
				{
					tocalc(samplenum,"",num_bt);
				}
			}
		});
		i+=1;
	</#list>
}

//获取缺陷数量
function getqxnum()
{
	var num_qx=0;
	var i=0;
	<#list list_cause as list>
		var num=$("#sr_num"+i).val();
		if(num!="0"&&num!=""&&num!=null)
		{
			num_qx=floatAdd(num_qx,num);//加法
		}
		i+=1;
	</#list>
	return num_qx;
}

//获取缺陷描述的id--字符串形式

function getqxids()
{
	qxnums="";
	qxids="";
	var i=0;
	<#list list_cause as list>
	var num=$("#sr_num"+i).val();
	if(num!="0"&&num!=""&&num!=null)
	{
		qxnums+=num+",";
		var id="${(list.id)! }";
		qxids+=id+",";
	}
	i+=1;
	</#list>
	return qxids;
}

//获取缺陷数量--字符串形式
function getqxnums()
{
	return qxnums;
}

//必填提示隐藏/显示
function tip_event()
{
	<#if show??>
		$(".requireField").hide();
	</#if>
}

//提交事件:1.刷卡保存，2.刷卡确认
function sub_event(my_id)
{
	var samplenum=$("#sample_num").val();//抽检数量
	if(samplenum==""||samplenum==null)
	{
		layer.alert("抽检数量不能为空!",false);
	}
	else
	{
		var hgnum=$("#input_qulified").val();//合格数量--如果走到这一步，合格数量是一个整数
		if(hgnum<0)
		{
			layer.alert("缺陷数量不能大于抽检数量!",false);
		}
		else
		{
			//赋值
			fuzhi(my_id);
			var url="";
			//1.刷卡保存
			if(my_id=="1")
			{
				//新增保存
				<#if add??>url="sample!creditsave.action";</#if>
				//修改保存
				<#if edit??>url="sample!creditupdate.action";</#if>
				tosubmit(url);//提交
			}
			//2.刷卡确认
			if(my_id=="2")
			{
				//新增确认
				<#if add??>url="sample!creditsubmit.action";</#if>
				//修改确认
				<#if edit??>url="sample!creditreply.action";</#if>
				tosubmit(url);//提交
			}
		}
	}
}
</script>	
