<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑工序管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/process_input.js"></script>
<style type="text/css">
	.mymust{color: red;font-size: 10px;}
	.p_name{width:420px;line-height: 30px;border:1px solid;border-color: #d5d5d5;}
</style>
<script type="text/javascript">
$().ready( function() {
	// 地区选择菜单
	$(".areaSelect").lSelect({
		url: "${base}/admin/area!ajaxChildrenArea.action"// Json数据获取url
	});
});
</script>
<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body{background:#fff;}
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
	
	<!-- ./ add by welson 0728 -->
	
<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">管理中心</a>
			</li>
			<li class="active"><#if isAdd??>添加工序记录<#else>编辑工序记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>process!save.action<#else>process!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<input type="hidden" id="xcode" value="${(process.processCode)!}" />
			<input type="hidden" id="productsId" name="products.id" value=""/>
			<input type="hidden" id="productsName" name="products.productsName" value=""/>
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">工序信息</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<div class="profile-info-name"> 工序编码 </div>					
										<div class="profile-info-value">
										<#if isAdd??>
											<input id="processcode" type="text" name="process.processCode" value="${(process.processCode)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" /><label class="requireField">*</label>&nbsp;&nbsp;<span id="span_code" class="mymust"></span>
											<!-- <input id="processcode" type="text" name="process.processCode" class="formText {required: true,minlength:2,maxlength: 100,processCode:true,remote:'process!checkProcesssCode.action',messages:{remote:'工序编码已存在'}}" /> -->
										<#else>
										    ${process.processCode}
										    <input id="processcode" type="hidden" name="process.processCode" value="${(process.processCode)!}"/><!-- <label class="requireField">*</label>&nbsp;&nbsp;<span id="span_code" style="display: none"></span> -->
										</#if>	
										</div>
									</div>	
									
									<div class="profile-info-row">
										<div class="profile-info-name">工序名称 </div>					
										<div class="profile-info-value">
											<input id="processname" type="text" name="process.processName" value="${(process.processName)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" /><label class="requireField">*</label>&nbsp;&nbsp;<span id="span_name" class="mymust"></span>
										</div>
									</div>
									
									<div class="profile-info-name">车间</div>
												<div class="profile-info-value">
													<input type="hidden" id="workShopId" name="process.workShopId" value="${(process.workShopId)!} "
														class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}"/>
													<button type="button" class="btn btn-xs btn-info"
														id="workShopSeach" data-toggle="button">选择</button>
													<#if isAdd??>														
													<span id="workShopName"></span><span id="factoryName"></span>
													<label class="requireField">*</label>	
													<#else>	
													<!--  
													  <span id="workShopName"></span> -->
													  <span id="newWorkShopName">${(process.xworkShop)!}</span>
													  <input type="hidden" id="workShopName" name="process.xworkShop" value="${(process.xworkShop)!} " />
													  <input type="hidden" id="factoryName" name="process.xfactory" value="${(process.xfactory)!}" />
													<label class="requireField">*</label>	
													</#if>											
												</div>
									
									
									<div class="profile-info-row">
										<div class="profile-info-name">状态</div>					
										<div class="profile-info-value">
											<label class="pull-left inline">
					                           <small class="muted smaller-90">已启用:</small>
						                       <input type="radio" class="ace" name="process.state" value="1"<#if (process.state == '1')!> checked</#if> />
						                       <span class="lbl middle"></span>
						                         &nbsp;&nbsp;
					                        </label>						
					                        <label class="pull-left inline">
					                            <small class="muted smaller-90">未启用:</small>
						                        <input type="radio" class="ace" name="process.state" value="2"<#if (isAdd || process.state == '2')!> checked</#if>  />
						                         <span class="lbl middle"></span>
					                        </label>		
										</div>	
									</div>							
						</div>
				
			<div class="buttonArea">
				<input id="btn_sub" type="button" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
			</div>
		</form>
	
<!-- add by welson 0728 -->	
				</div><!-- /.col -->
				</div><!-- /.row -->

				<!-- PAGE CONTENT ENDS -->
			</div><!-- /.col -->
		</div><!-- /.row -->
	</div><!-- /.page-content-area -->
	<#include "/WEB-INF/template/admin/admin_footer.ftl">
</div><!-- /.page-content -->
				</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->
</body>
</html>
<script type="text/javascript">
$(function() {

	//产品点击
	$("#workShopSeach").click(function(){
		showShop();	
	});

	//读取产品信息
	function showShop(){
		var title = "选择车间";
		var width="800px";
		var height="600px";
		var content="process!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        	//alert(iframeWin);
        	 var work = iframeWin.getGridId();
             var id=work.split(",");
             $("#workShopId").val(id[1]);
             $("#workShopName").text(id[0]);
             $("#newWorkShopName").text(id[0]);
             $("#factoryName").text(id[2]);
             layer.close(index); 
             loadData(id[1]);//加载表单数据            
	 });	
	}
	
})
</script>