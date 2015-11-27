<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>返修管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
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

							<form id="inputForm" class="validate"
								action="<#if isAdd??>repair!save.action<#else>reppair!update.action</#if>"
								method="post">
								合格数量：<input type="text" id="input_qulified"  name="sample.qulified" value="" />
								合格率：<input type="text" id="input_qrate" name="sample.qulifiedRate" value="" />
								描述ID：<input type="text" id="input_rd" name="sampleRecord.recordDescription" />
								缺陷数量：<input type="text" id="input_rnum" name="sampleRecord.recordNum" value="" />
								<!-- tabs start -->
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">返修单</a></li>
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
														<input id="sample_num" type="text" name="sample.sampleNum" value="" class=" input input-sm  formText" />
														<label class="requireField">*</label>
													</div>
													<div class="profile-info-name">抽检类型</div>
													<div class="profile-info-value">
														<select name="sample.sampleType" id="sample_type" class="input input-sm form-control chosen-select">
															<#list list_dict as dlist>
																<option value="${(dlist.dictkey)! }">${(dlist.dictvalue)! }</option>
															</#list>
														</select>
														<label class="requireField">*</label>
													</div>
												</div>
												<div class="profile-info-row">
													<div class="profile-info-name">合格数量</div>
													<div class="profile-info-value">
														<span id="span_sq">0</span>
													</div>
													<div class="profile-info-name">合格率</div>  
													<div class="profile-info-value">
														<span id="span_qrate">0%</span>
													</div>
												</div>
											</div>
											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row ceshi">
													<div class="profile-info-value">
														&nbsp;
													</div>
												</div>
												
												<div class="profile-info-row ceshi">
													<div class="profile-info-name div-name">缺陷内容</div>
												</div>
												<div class="profile-info-row ceshi">
													<div class="profile-info-value div-value">
														<#assign num=0 />
														<#list list_cause as list>
															<div class="col-md-2 col-xs-6 col-sm-3 div-value2">
																<input id="sr_id" type="hidden" value="${(list.id)! }"/>
																<label>${(list.causeName)! }</label>
																<input id="sr_num${num}" type="text" value="" class=" input-value" />
																<input id="sr_num2${num}" type="hidden" value="" />
															</div>
															<#assign num=num+1 />
														</#list>
													</div>
												</div>
											</div>
										<!-- end msg -->
									</div>
									<div class="buttonArea">
										<div class="col-md-2 col-sm-4">
											<button class="btn btn-white btn-success btn-bold btn-round btn-block" id="btn_confirm" type="button">
												<span class="bigger-110 no-text-shadow">刷卡保存</span>
											</button>
										</div>
										<div class="col-md-2 col-sm-4">
											<button class="btn btn-white btn-success btn-bold btn-round btn-block" id="btn_revoke" type="button">
												<span class="bigger-110 no-text-shadow">刷卡确认</span>
											</button>
										</div>
										<div class="col-md-2 col-sm-4">
											<button class="btn btn-white btn-success btn-bold btn-round btn-block" id="btn_back" type="button">
												<span class="bigger-110 no-text-shadow">返回</span>
											</button>
										</div>
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
$(function(){
	//抽检数量事件
	sample_event();
	//缺陷事件
	cause_event();
	
	$("#btn_confirm").click(function(){
		var b=$(this).attr("id");
		alert(b);
	});
});

//抽检数量事件
function sample_event()
{
	$("#sample_num").change(function(){
		var samplenum=$(this).val().replace(" ","");
		if(samplenum!=null&&samplenum!="")
		{
			var reg=/^[0-9]+(\.[0-9]+)?$/;//整数或小数
			if(reg.test(samplenum))
			{
				samplenum=setScale(samplenum,0,"");
				$(this).val(samplenum);
				$("#span_sq").text(samplenum);//合格数量
				if(samplenum>0)
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
					tocalc(samplenum,num_qx,"")
				}
				else
				{
					$("#span_sq").text("0");//合格数量
					$("#span_qrate").text("0%");//合格率
				}
			}
			else
			{
				layer.alert("输入不合法!",false);
				$(this).val("");
				$("#span_sq").text("0");//合格数量
				$("#span_qrate").text("0%");//合格率
			}
		}
	
	});
}
//缺陷事件
function cause_event()
{
	var i=0;
	<#list list_cause as list>
		$("#sr_num"+i).change(function(){
			var idval=$(this).attr("id");
			i=idval.substring(idval.length-1,idval.length);
			var samplenum=$("#sample_num").val();//抽检数量
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
					if(num_qx>0&&(samplenum>0&&samplenum!=null&&samplenum!=""))
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
				}
				else
				{
					layer.alert("输入不合法!",false);
					$(this).val("");//缺陷数量
					$("#sr_num2"+i).val("");//缺陷数量--备胎
					if(num_bt!=""&&num_bt!=null&&num_bt>0)
					{
						tocalc(samplenum,"",num_bt);
					}
				}
			}
		});
		i+=1;
	</#list>
}
//计算并赋值：抽取数量，缺陷数量,缺陷数量备胎
function tocalc(samplenum,qxnum,qxnum_bt)
{
	var hgnum=$("#span_sq").text();//合格数量
	if(qxnum!="")
	{
		hgnum=floatSub(hgnum,qxnum);//减法--合格数量
	}
	if(qxnum_bt!="")
	{
		hgnum=floatAdd(hgnum,qxnum_bt);//加法--合格数量
	}
	var qrnum=floatDiv(hgnum,samplenum);//除法--合格率
	qrnum=floatMul(qrnum,100);//乘法--合格率
	qrnum=setScale(qrnum,2,"");//精度--合格率
	$("#span_sq").text(hgnum);//合格数量
	$("#span_qrate").text(qrnum+"%");//合格率
}
</script>	
