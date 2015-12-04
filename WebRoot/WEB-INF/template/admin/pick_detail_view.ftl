<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>领/退料管理 - Powered By ${systemConfig.systemName}</title>
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
					<li class="active">领/退料详情</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<!-- add by welson 0728 -->
			
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

									<form id="inputForm" name="inputForm" class="validate" action="pick_detail!save.action?id+"=id
							 method="post">
                            <input type="hidden" class="input input-sm" name="workingBillId" value="${(workingbill.id)!} ">
                            <input type="hidden" id="my_id" name="my_id" value="${(my_id)! }" />
							
							<div id="inputtabs">
								 <ul>
								    <li><a href="#tabs-1">领/退料详情</a></li>
								</ul>
								<div id="tabs-1" class="tab1">
								
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
											</div>
								<div class="profile-user-info profile-user-info-striped">
								  <div class="profile-info-row">
								    <table class="table table-striped table-bordered table-hover" id="mytable">
									<thead>
										<tr>
											<th style="text-align:center;">组件编号</th>
											<th style="text-align:center;">组件名称</th>
											<th style="text-align:center;">库存数量</th>
											<th style="text-align:center;">领/退料数量</th>
											<th style="text-align:center;">操作类型</th>
										</tr>
									</thead>

									<tbody>
									    <#assign  num=0/>
										<#list pkList as list>
											<tr id="tr_1">
												<td class="center" name="">${(list.materialCode)! }</td>
												<td class="center" name="">${(list.materialName)! }</td>
												<td class="center" ></td>
										 		<td class="center">${(list.pickAmount)! }</td>
												<td class="center">${(list.pickType)! }</td>									
											</tr>
											<#assign num=num+1/>
										</#list>

									</tbody>
								</table>
							  </div>
						   </div>
						</div>
                     </div>
				</form>
                                 <button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type=button>
										<span class="ace-icon fa fa-home">返回</span>
								</button>
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
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */
	
	$(function(){		
/*		$("#btn_save").click(function(){
			document.inputForm.action="pick_detail!save.action";
			$("#inputForm").submit();
		});
		
	
		
		$("#btn_confirm").click(function(){		
			document.inputForm.action="pick_detail!confirms.action?id="+id;	
			$("#inputForm").submit();
		});
*/		

	$("#btn_back").click(function(){
	window.history.back();
});
		var ishead=0;
		$("#ace-settings-btn").click(function(){
			if(ishead==0){
				ishead=1;
				$("#ace-settings-box").addClass("open");
			}else{
				ishead=0;
				$("#ace-settings-box").removeClass("open");
			}
		});
		$(".btn-colorpicker").click(function(){
				$(".dropdown-colorpicker").addClass("open");
		})
		
		var ishead2=0;
		$(".light-blue").click(function(){
			if(ishead2==0){
				ishead2=1;
				$(this).addClass("open");
			}else{
				ishead2=0;
				$(this).removeClass("open");
			}
			
		})
	})
	
	$(function(){
		$("#mytable td").each(function(){
			 var value = $(this).text();
			 //alert(value);
			});
     });
	


	
</script>
