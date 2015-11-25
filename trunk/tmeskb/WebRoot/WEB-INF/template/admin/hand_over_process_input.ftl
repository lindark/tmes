<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>权限对象管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<#include "/WEB-INF/template/common/includelist.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
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

							<form id="inputForm" class="validateajax"
								action="hand_over_process!save.action"
								method="post">
								<!-- hide 块 start -->
									
								<!-- hide 块 end-->
								<table class="table table-striped table-bordered">
									<thead>
										<tr>
											<th class="center">随工单号</th>
											<th>产品编号</th>
											<th>产品名称</th>
											<th>交接数量</th>
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
													<input type="hidden" name="handoverprocessList[${num }].material.id" value="${material.id }"/> <!-- 物料组件 -->
													<input type="hidden" name="handoverprocessList[${num }].beforworkingbill.id" value="${list.id }"/><!-- 上班随工单 -->
													<input type="hidden" name="handoverprocessList[${num }].process.id" value="${processid }"/><!-- 工序-->
													<input type="text" name="handoverprocessList[${num }].amount" value=""/><!-- 数量 -->
												</td>
											</tr>
											<#assign  num=num+1/>
										</#list>

									</tbody>
								</table>
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
		loadHandover();//加载数量信息
		
	})
	
	function loadHandover(){
		$.ajax({	
			url: "hand_over_process!getAmount.action",
			data: $("#inputForm").serialize(),
			dataType: "json",
			async: false,
			success: function(data) {
				alert(data);
			},error:function(data){
				/*
				layer.alert("操作失败",{
					shade: 0
				});
				*/
			}
		});
		
	}
</script>
</html>