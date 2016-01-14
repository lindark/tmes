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
								
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th class="center">随工单号</th>
											<th class="center">产品编号</th>
											<th class="center">产品名称</th>
											<th class="center">下班随工单</th>
											<th class="center">正常交接数量</th>
											<th class="center">返修交接数量</th>
										</tr>
									</thead>

									<tbody>
										<#assign  num=0/>
										<#list workingbillList as list>
											<tr>
												<td class="center">${list.workingBillCode }</td>
												<td class="center">${list.matnr }</td>
												<td class="center">${list.maktx }</td>
												<td class="center">
													<input type="text" name="handoverprocessList[${num }].afterworkingbill.workingBillCode" class="form-control" value="${list.afterworkingBillCode }"/>
												</td>
												<td class="center">
													<input type="hidden" class="form-control" name="handoverprocessList[${num }].materialCode" value="${materialCode }"/> <!-- 物料组件 -->
													<input type="hidden" class="form-control" name="handoverprocessList[${num }].materialName" value="${materialName }"/> <!-- 物料描述 -->
													<input type="hidden" class="form-control" name="handoverprocessList[${num }].beforworkingbill.id" value="${list.id }"/><!-- 上班随工单 -->
													<input type="hidden" class="form-control" name="handoverprocessList[${num }].processid" value="${processid }"/><!-- 工序-->
													<input type="text" class="form-control formText{digits:true,messagePosition: '#MessagePosition'}" name="handoverprocessList[${num }].amount" value="${(list.amount)! }"/><!-- 正常交接数量 -->							
													
												</td>
							 					<td class="center">
							 						<input type="text" class="form-control formText{digits:true,messagePosition: '#MessagePosition'}" name="handoverprocessList[${num }].repairAmount" value="${(list.repairamount)! }"/><!-- 返修交接数量 -->
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

</html>