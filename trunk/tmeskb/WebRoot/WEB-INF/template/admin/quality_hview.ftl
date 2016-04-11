<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>查看质量问题单 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/unusual/js/quality.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}

.deleteImage,#addImage,#removeImage {
	cursor: pointer;
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
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>
										<li><a href="#tabs-2">单据日志</a></li>
									    <li><a href="#tabs-3">整改情况跟踪</a></li>									 						
									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">												    
													${(product)!}													
												</div>
												
												<div class="profile-info-name">生产工序</div>

												<div class="profile-info-value" >												    
													${(quality.processName )!}
												</div>

											</div>
											
											<div class="profile-info-row">
											<div class="profile-info-name">产品bom</div>
											<div class="profile-info-value">${(bomproduct)!}</div>
											</div>

											<div class="profile-info-row">
												
												<div class="profile-info-name">班组</div>

												<div class="profile-info-value">												  
												       ${(quality.team.teamName)!}												       												
												</div>
												<div class="profile-info-name">挤出批次</div>
												<div class="profile-info-value">
												${(quality.extrusionBatches)!}													
												</div>
											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">接收人</div>
												<div class="profile-info-value">${(quality.receiver.name)!}</div>
											</div>
											
											<div class="profile-info-row">
												<div class="profile-info-name">抽检数量</div>
												<div class="profile-info-value">
												    ${(quality.samplingAmont)!}										
												</div>
												<div class="profile-info-name">缺陷数量</div>
												<div class="profile-info-value">
												    ${(quality.failAmont)!}											
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">报告时间</div>
												<div class="profile-info-value">
												   ${(quality.createDate)!}												
												</div>
												<div class="profile-info-name">计划完成时间</div>
												<div class="profile-info-value">
												   ${(quality.overTime)!}												
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">生产日期</div>
												<div class="profile-info-value">
												   ${(quality.productDate)!}												
												</div>
												<div class="profile-info-name">班次</div>
												<div class="profile-info-value">
												   <#if (quality.shift == 1)!> 早</#if>
														<#if (quality.shift == 2)!> 白</#if>
														<#if (quality.shift == 3)!> 晚</#if>												
												</div>
											</div>
										</div>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">质量问题描述</div>
												<div class="profile-info-value">
												    ${(quality.problemDescriptionName)!}										
												</div>
											</div>
										</div>

										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">车间整改方案</div>
												<div class="profile-info-value">
													${(quality.rectificationScheme)!}
												</div>
											</div>
										</div>

										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">工程师意见</div>
												<div class="profile-info-value">
													${(quality.engineerOpinion)!}
												</div>
											</div>
										</div>
										</form>
										<!--weitao end modify-->
                                        <div class="buttonArea">
                                        
                                         
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnQuality" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
                                        								
									     </div>
   
									</div>
                                    
                                    <form id="inputForm1" action="flowing_rectify!save.action" method="post"></form>
                                    
									<table id="tabs-2" class="inputTable tabContent">
										<tbody>
											<tr class="title">
												<th>时间</th>
												<th>内容</th>
												<th>修改人</th>
											</tr>
											<#list (quality.unusualLogSet)! as list>
											<tr>
												<td>${(list.createDate)!}</td>
												<td>${(list.info)!}</td>
												<td>${(list.operator.name)!}</td>
											</tr>
											</#list>
										</tbody>
									</table>									
									
									<div id="tabs-3">
										<table id="sample-table-1"
											class="table table-striped table-bordered table-hover">											
											<tbody>											  											   											  			
												
												<#list (quality.flowingRectify)! as list>
												<tr class="zg">
													<td>
													<div>
													${(list.content)!}</div>		
													</div>
													</td>
												</tr>
												</#list> 
												
			                             
											</tbody>
										</table>
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
	<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->
<script>
$(function() {
	$("#sample-table-1 tbody .zg .text").attr("disabled",true);
	//$("#sample-table-1 tbody .zg .text").hide();
	
	$(".save").live("click", function() {
		$text =	$(this).prev(); 
		$input = $(this).prev().prev();
		var i = $text.serialize();
		var ii = $("#qualityId").val();
		var iii = $(this).prev().prev().val();
		if(iii==null || iii==undefined || iii==""){
			iii="";
		}
		var ids = "";
		ids += ii+","+i+","+iii;
	    url="flowing_rectify!save.action?ids="+ids;
		$.ajax({
			url: url,
			//data: ids,
			dataType: "json",		
			success: function(data) {
				
				$.tip(data.status, data.message);
				$input.val(data.id);
				$text.attr("disabled",true);
			}
		});	
		  		  		  
	});
	
	$(".edit").livequery("click", function(){
		$(this).prev().prev().attr("disabled",false);

	})
	
	
	$(".deleteButton").livequery("click", function() {
		if($(".zg").length > 1) {
			$(this).parent().parent().remove();
		} else {
			alert("请至少保留一个选项!");
		}
	})		
	
})
</script>
</body>
</html>