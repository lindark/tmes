<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>巡检管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"src="${base}/template/admin/js/BasicInfo/pollingtest_input.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}

.div-name {
	text-align: center;
}

.div-value {
	padding-right: 30px;
	min-width: 200px;
}

.div-value2 {
	text-align: right;
	padding-right: 0px;
	min-width: 200px;
}

.input-value {
	width: 80px;
	height: 30px;
	line-height: 30px;
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
						href="admin!index.action">管理中心</a></li>
					<li class="active">巡检单</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate"
								action="<#if add??>pollingtest!save.action</#if><#if edit??>pollingtest!update.action</#if><#if show??></#if>"
								method="post">
								<input type="hidden" id="input_qulified"  name="pollingtest.qualifiedAmount" value="${(pollingtest.qualifiedAmount)! }" />
								<input type="hidden" id="input_qrate" name="pollingtest.passedPercent" value="${(pollingtest.passedPercent)! }" />
								<input type="hidden" id="input_rd" name="info" />
								<input type="hidden" id="input_rnum" name="info2" value="" />
								<input type="hidden" id="my_id" name="my_id" value="${(my_id)! }"/>
								<input type="hidden" name="pollingtest.id" value="${(pollingtest.id)!}" /> 
								<input type="hidden" class="input input-sm"
									name="pollingtest.workingbill.id" value="${workingbill.id} ">
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">巡检单</a></li>

									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">随工单</div>

												<div class="profile-info-value">
													<span>${workingbill.workingBillCode}</span>
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">产品编号</div>

												<div class="profile-info-value">
													<span>${workingbill.matnr}</span>
												</div>
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
													<span>${workingbill.maktx}</span>
												</div>

											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">巡检数量</div>
												<div class="profile-info-value">
													<#if show??>
															<span>${(pollingtest.pollingtestAmount)! }</span>
													<#else>
													<input id="sample_num" type="text" name="pollingtest.pollingtestAmount"
														value="${(pollingtest.pollingtestAmount)!}"
														class=" input input-sm formText {required: true,min: 0}" />
													</#if>
												</div>

												<div class="profile-info-name">硫化时间</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.curingTime1" <#if show??>readonly="readonly"</#if>
														style="width:10%" value="${(pollingtest.curingTime1)!}"
														class=" input input-sm formText {min: 0}" />
													<input type="text" name="pollingtest.curingTime2" <#if show??>readonly="readonly"</#if>
														style="width:10%" value="${(pollingtest.curingTime2)!}"
														class=" input input-sm formText {min: 0}" />
													<input type="text" name="pollingtest.curingTime3" <#if show??>readonly="readonly"</#if>
														style="width:10%" value="${(pollingtest.curingTime3)!}"
														class=" input input-sm formText {min: 0}" />
													<input type="text" name="pollingtest.curingTime4" <#if show??>readonly="readonly"</#if>
														style="width:10%" value="${(pollingtest.curingTime4)!}"
														class=" input input-sm formText {min: 0}" />
													<span>&nbsp;&nbsp;&nbsp;/秒</span>
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">工艺确认</div>
												<div class="profile-info-value">
												<#if show??>
													<span>${pollingtestType! }</span>
												<#else>
													<select name="pollingtest.craftWork" id="form-field-icon-1" class="chosen-select">
														<#list allCraftWork as
														list>
														<option value="${list.dictkey}"<#if ((isAdd &&
															list.isDefault) || (isEdit && pollingtest.craftWork ==
															list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>
													</select>
												</#if>
												</div>

												<div class="profile-info-name">固化时间</div>
												<div class="profile-info-value">
													<input type="text" name="pollingtest.settingTime1" <#if show??>readonly="readonly"</#if>
														style="width:10%" value="${(pollingtest.settingTime1)!}"
														class=" input input-sm formText {min: 0}" />
													<input type="text" name="pollingtest.settingTime2" <#if show??>readonly="readonly"</#if>
														style="width:10%" value="${(pollingtest.settingTime2)!}"
														class=" input input-sm formText {min: 0}" />
													<input type="text" name="pollingtest.settingTime3" <#if show??>readonly="readonly"</#if>
														style="width:10%" value="${(pollingtest.settingTime3)!}"
														class=" input input-sm formText {min: 0}" />
													<input type="text" name="pollingtest.settingTime4" <#if show??>readonly="readonly"</#if>
														style="width:10%" value="${(pollingtest.settingTime4)!}"
														class=" input input-sm formText {min: 0}" />
													<span>&nbsp;&nbsp;&nbsp;/秒</span>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">尺寸1</div>
												<div class="profile-info-value">
													<#if show??>
															<span>${(pollingtest.size1)! }</span>
													<#else>
													<input type="text" name="pollingtest.size1"
														value="${(pollingtest.size1)!}"
														class=" input input-sm" />
													</#if>
												</div>

												<div class="profile-info-name">尺寸2</div>
												<div class="profile-info-value">
												<#if show??>
														<span>${(pollingtest.size2)! }</span>
												<#else>
													<input type="text" name="pollingtest.size2"
														value="${(pollingtest.size2)!}"
														class=" input input-sm" />
												</#if>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">尺寸3</div>
												<div class="profile-info-value">
												<#if show??>
														<span>${(pollingtest.size3)! }</span>
												<#else>
													<input type="text" name="pollingtest.size3"
														value="${(pollingtest.size3)!}"
														class=" input input-sm" />
												</#if>
												</div>

												<div class="profile-info-name">合格数量</div>
												<div class="profile-info-value">
													<span id="span_sq">${(pollingtest.qualifiedAmount)! }</span>
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">合格率</div>
												<div class="profile-info-value">
													<#if add??>
														<span id="span_qrate">0.00%</span>
													<#else>
														<span id="span_qrate">${(pollingtest.passedPercent)! }</span>
													</#if>
												</div>
											</div>
										</div>
										<!--weitao end modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row ceshi">
												<div class="profile-info-value">
													<sapn id="span_tip" style="color:red;"></sapn>
												</div>
											</div>
											<div class="profile-info-name div-name">不合格原因</div>
											<div class="profile-info-row ceshi">
												<div class="profile-info-value div-value">
												<#if show??>
													<#list list_pollingtestRecord as list>
													<div class="col-md-2 col-xs-6 col-sm-3 div-value2">
														<label>${(list.recordDescription)! }</label> 
														<input id="sr_num${num}" type="text" value="${(list.recordNum)! }" class=" input-value" readonly="readonly" />
													</div>
													</#list>
												<#else>
													<#assign num=0 /> 
													<#if list_cause??>
														<#list list_cause as list>
														<div class="col-md-2 col-xs-6 col-sm-3 div-value2">
															<input id="sr_id" type="hidden" value="${(list.id)! }" />
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
									</div>
									<div class="buttonArea">
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
			i=idval.substring(idval.length-1,idval.length);
			var samplenum=$("#sample_num").val();//巡检数量
			var num_bt=$("#sr_num2"+i).val();//备胎
			var num_qx=$(this).val().replace(" ","");//缺陷
			if(num_qx!=null&&num_qx!="")
			{
				var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
				if(reg.test(num_qx))
				{	
					num_qx=setScale(num_qx,0,"");//精度--去小数
					$(this).val(num_qx);
					$("#sr_num2"+i).val(num_qx);//备胎，防止第一次输入正确第二次不正确时无法获取原数据--合格数量无法重新计算
					if(num_qx>=0&&(samplenum>=0&&samplenum!=null&&samplenum!=""))
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
					//layer.alert("输入不合法!",false);
					layer.msg("输入不合法!", {icon: 5});
					$(this).val("");//缺陷数量
					$("#sr_num2"+i).val("");//缺陷数量--备胎
					if(num_bt!=""&&num_bt!=null&&num_bt>0&&(samplenum!=null&&samplenum!=""))
					{
						tocalc(samplenum,"",num_bt);
					}
				}
			}
			else{
				$("#sr_num2"+i).val("");//缺陷数量--备胎
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
</script>	
