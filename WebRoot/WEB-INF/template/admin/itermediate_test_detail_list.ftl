<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>半成品巡检管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<#include "/WEB-INF/template/common/includelist.ftl">
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>	
		<script type="text/javascript" src="${base}/template/admin/js/manage/it.js"></script>	
		<script type="text/javascript"src="${base}/template/admin/js/BasicInfo/sample_input.js"></script>
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
<#include "/WEB-INF/template/admin/admin_navbar.ftl">
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
	<div class="main-content">
	<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">

<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">管理中心</a>
			</li>
			<li class="active">半成品巡检列表</li>
		</ul>
	</div>
			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" name="inputForm" class="validate"
								action="<#if isAdd??>pick_detail!save.action<#else>pick_detail!update.action</#if>"
								method="post">
                            <input type="hidden" class="input input-sm" name="workingBillId" value="${(workingbill.id)!} ">
                            <div>
								<table class="table table-striped table-bordered" id="mytable">
									<thead>
										<tr>
										
											<th class="center";">组件编号</th>
											<th class="center";">组件名称</th>
											<th class="center";">检验数量</th>
											<th class="center";">尺寸1</th>
											<th class="center";">尺寸2</th>
											<th class="center";">尺寸3</th>
											<th class="center";">尺寸4</th>
											<th class="center";">尺寸5</th>
											<th class="center";">不合格数量/原因</th>											
										</tr>
									</thead>

									<tbody>
									    <#assign  num=0/>
										<#list materialList as list>
											<tr id="tr_1">
												<td class="center" name="">${(list.materialCode)! }</td>
												<td class="center" name="">${(list.materialName)! }</td>
												<td class="center">
												   <input type="text"  class="input input-sm form-control"/>
												</td>
												<td class="center">
												   <input type="text"  class="input input-sm form-control"/>
												</td>
												<td class="center">
												   <input type="text"  class="input input-sm form-control"/>
												</td>	
												<td class="center"> 
												   <input type="text"  class="input input-sm form-control"/>
											    </td>
												<td class="center">
												  <input type="text"  class="input input-sm form-control"/>
												</td>
												<td class="center"> 
												  <input type="text"  class="input input-sm form-control"/>
												</td>
												<td class="center">
												 <input type="text"  class="input input-sm form-control"/>
												  <#if isAdd??><button type="button" class="btn btn-xs btn-info itAddBtn"  data-toggle="button">选择</button>				                                    
				                                     
										         	 <#else>  
										          </#if>		
												</td>										
											</tr>
											<#assign num=num+1/>
										</#list>

                                           
									</tbody>
								</table>
                             </div>
						</form>

                                     <button class="btn btn-white btn-default btn-sm btn-round" id="addIt" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="returnIt" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
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
	 <#include "/WEB-INF/template/admin/admin_footer.ftl">
      <#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->
<script type="text/javascript">
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */

	 $(function(){		
		$("#addIt").click(function(){
			document.inputForm.action="pick_detail!save.action";
			$("#inputForm").submit();
		});
		
		$("#returnIt").click(function(){
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
</body>
</html>