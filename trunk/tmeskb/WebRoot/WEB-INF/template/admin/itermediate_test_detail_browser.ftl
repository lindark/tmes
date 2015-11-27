<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>不合格原因管理 - Powered By ${systemConfig.systemName}</title>
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
</style>
</head>
<body class="no-skin input">

	<!-- add by welson 0728 -->
	<div class="main-container" id="main-container">
		<script type="text/javascript">
		
		</script>
		<div class="main-content">
			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<form id="inputForm" class="validate" action="<#if isAdd??>itermediate_test!save.action<#else>itermediate_test!update.action</#if>" 
							method="post">					
	                            <table class="table table-striped table-bordered" id="table">
												<div class="profile-info-row ceshi">
													<div class="profile-info-name div-name">不合格原因</div>
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
									</table>
							  </form>
						  </div>
					 </div>


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
<script type="text/javascript">
</script>