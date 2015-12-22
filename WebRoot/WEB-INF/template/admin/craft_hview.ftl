<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>查看工艺维修单 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/unusual/js/craft.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}
.profile-user-info-striped{border:0px;}
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

			<!-- ./ add by welson 0728 -->


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate"
								action="<#if isAdd??>craft!save.action<#else>craft!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" />
                                <input type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>
										<li><a href="#tabs-2">单据日志</a></li>
									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
											
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
												    ${(craft.products.productsName)!}
													
												</div>
											 	<div class="profile-info-name">产品编码</div>

												<div class="profile-info-value">
												      ${(craft.products.productsCode)!}
												</div> 
												

											</div>

											<div class="profile-info-row">
												 <div class="profile-info-name">班组</div>

												<div class="profile-info-value">												   
												       ${(craft.team.teamName)!}												       								
												</div>
												<div class="profile-info-name">机台号</div>

												<div class="profile-info-value">													
                                                         ${(machineName)!}													
												</div>
											</div>

                                          </div>

											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<div class="profile-info-name">制造异常描述</div>
													<div class="profile-info-value">
													     <#list craft.receiptReasonSet as list>
												         	    ${list.reasonName},
												         </#list>													
													</div>
												</div>
											


											
												<div class="profile-info-row">
													<div class="profile-info-name">制造处理措施</div>
													<div class="profile-info-value">
													   ${(craft.treatmentMeasure_make)!}								
													</div>
												</div>
											


										
												<div class="profile-info-row">
													<div class="profile-info-name">制造处理结果</div>
													<div class="profile-info-value">
													    ${(craft.resultCode_make)!}			
													</div>
												</div>
											</div>


											<div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<div class="profile-info-name">工艺异常分析</div>
													<div class="profile-info-value">
													     ${(craft.unusualDescription_process)!}											
													</div>
												</div>

												<div class="profile-info-row">
													<div class="profile-info-name">工艺处理措施</div>
													<div class="profile-info-value">
													  ${(craft.treatmentMeasure_process)!}											
													</div>
												</div>
												<div class="profile-info-row">
													<div class="profile-info-name">工艺处理结果</div>
													<div class="profile-info-value">
													    ${(craft.resultCode_process)!}											
													</div>
												</div>
											</div>
										
                                  </form>
										<!--weitao end modify-->
							   <div class="buttonArea">					  		                  									
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnCraft" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
                 	            </div>
									
									</div>
									

									<table id="tabs-2" class="inputTable tabContent">
										<tbody>
											<tr class="title">
												<th>时间</th>
												<th>内容</th>
												<th>修改人</th>
											</tr>
											<#list (craft.craftLogSet)! as list>
											<tr>
												<td>${(list.createDate)!}</td>
												<td>${(list.info)!}</td>
												<td>${(list.operator.name)!}</td>
											</tr>
											</#list>
										</tbody>
									</table>								

								

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
	<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>
</html>