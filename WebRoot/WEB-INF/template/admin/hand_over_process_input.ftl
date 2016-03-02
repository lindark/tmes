<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>工序交接管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<#include "/WEB-INF/template/common/includelist.ftl">
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}

.operateBar{
	padding:3px 0px;
}

.operateName{
	text-align: right;
    padding: 6px 10px 6px 4px;
    font-weight: 400;
    color: #667E99;
    background-color: transparent;
    /* border-top: 1px dotted #D5E4F1; */
    display: table-cell;
    width: 110px;
    vertical-align: middle;
}
.operateValue{
    display: table-cell;
    padding: 6px 4px 6px 6px;
    /* border-top: 1px dotted #D5E4F1; */
}
</style>
</head>
<body class="no-skin input">
	<!-- add by welson 0728 -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<div class="main-content">




			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate"
								action="#"
								method="post">
								<div class="operateBar" >
									<div style="float:left">
										<div class="operateName">子件名称:</div>
									    <div class="operateValue">
												 ${(materialCode)! } &nbsp;
												 ${(materialName)! }
									    </div>
									</div>
									<div style="float:right">
									   <div class="operateName">提交人:</div>
									   <div class="operateValue">
												 	${(submitadmin)! }
									   </div>
									   <div class="operateName">确认人:</div>
									   <div class="operateValue">
									 	  ${(approvaladmin)! }
									   </div>
									</div>
										<!-- 
											<div class="profile-info-row">
												 <div class="profile-info-name">提交人:</div>
												 <div class="profile-info-value">
												 	${(submitadmin)! }
												 </div>
												 <div class="profile-info-name">确认人:</div>
												 <div class="profile-info-value">
												 	${(approvaladmin)! }
												 </div>
											</div>
									 -->
								</div>
								
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th class="center">随工单号</th>
											<th class="center">产品编号</th>
											<th class="center">产品名称</th>
											<th class="center">下班随工单</th>
											<th class="center">裁切倍数</th>
											<th class="center">裁切后正常交接数量</th>
											<th class="center">裁切后返修交接数量</th>
											<th class="center">实际正常交接数量</th>
											<th class="center">实际返修交接数量</th>
										</tr>
									</thead>

									<tbody>
										<#assign  num=0/>
										<#list workingbillList as list>
											<tr>
												<td class="left">${list.workingBillCode }</td>
												<td class="left">${list.matnr }</td>
												<td class="left">${list.maktx }</td>
												<td class="left">
													<input type="text" name="handoverprocessList[${num }].afterworkingbill.workingBillCode" class="form-control afterworkingBillCode" value="${list.afterworkingBillCode }"/>
												</td>
												<td class="left">
													${list.cqsl }
													<input type="hidden" class="form-control cqsl" name="handoverprocessList[${num }].cqsl" value="${list.cqsl }"/>
												</td>
												<td class="left">
													<input type="text" class="form-control cqamount formText{digits:true,messagePosition: '#MessagePosition'}" name="handoverprocessList[${num }].cqamount" value="${(list.cqamount)! }"/>
												</td>
												<td class="left">
													<input type="text" class="form-control cqrepairamount formText{digits:true,messagePosition: '#MessagePosition'}" name="handoverprocessList[${num }].cqrepairAmount" value="${(list.cqrepairamount)! }"/>
												</td>
												<td class="left">
													<input type="hidden" class="form-control" name="handoverprocessList[${num }].materialCode" value="${materialCode }"/> <!-- 物料组件 -->
													<input type="hidden" class="form-control" name="handoverprocessList[${num }].materialName" value="${materialName }"/> <!-- 物料描述 -->
													<input type="hidden" class="form-control" name="handoverprocessList[${num }].beforworkingbill.id" value="${list.id }"/><!-- 上班随工单 -->
													<input type="hidden" class="form-control" name="handoverprocessList[${num }].processid" value="${processid }"/><!-- 工序-->
													<input type="hidden" class="form-control formText{digits:true,messagePosition: '#MessagePosition'} amountinp" name="handoverprocessList[${num }].amount" value="${(list.amount)! }"/><!-- 实际正常交接数量 -->							
													<span class="amount">${(list.amount)! }</span>
												</td>
							 					<td class="left">
							 						<input type="hidden" class="form-control formText{digits:true,messagePosition: '#MessagePosition'} repairamountinp" name="handoverprocessList[${num }].repairAmount" value="${(list.repairamount)! }"/><!-- 实际返修交接数量 -->
							 						<span class="repairamount">${(list.repairamount)! }</span>
							 					</td>		       
											</tr>
											<#assign  num=num+1/>
										</#list>

									</tbody>
								</table>
								<span id="MessagePosition"></span>
								<div class="buttonArea" style="display:none">
									<input type="submit" class="formButton" id="submit_btn"
										value="确  定" hidefocus="true" />
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
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>
<script type="text/javascript">
	$(function(){
		var $cqamount = $(".cqamount");//裁切后正常交接数量
		var $cqrepairamount = $(".cqrepairamount");//裁切后返修交接数量
		
		$cqamount.change(function(){//裁切后正常交接数量改变事件
			var sVal = $(this).val();//裁切后正常数量
			var sCqsl = $(this).parent().parent().find(".cqsl").val();//裁切倍数
			var sv = floatDiv(sVal,sCqsl);
			if(isNaN(sv)) sv = 0;
			var sv = setScale(sv,2,"");//保留两位小数
			$(this).parent().parent().find(".amountinp").val(sv);
			$(this).parent().parent().find(".amount").text(sv);
		});
		
		$cqrepairamount.change(function(){//裁切后返修交接数量改变事件
			var sVal = $(this).val();//裁切后返修交接数量
			var sCqsl = $(this).parent().parent().find(".cqsl").val();//裁切倍数
			var sv = floatDiv(sVal,sCqsl);
			if(isNaN(sv)) sv = 0;
			var sv = setScale(sv,2,"");//保留两位小数
			$(this).parent().parent().find(".repairamountinp").val(sv);
			$(this).parent().parent().find(".repairamount").text(sv);
		});
	})

	
	function reloadwin(){
		location.reload();
		
	}
</script>
</html>