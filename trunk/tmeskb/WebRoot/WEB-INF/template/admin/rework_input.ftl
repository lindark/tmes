<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑返工管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript">
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
			
			<li class="active"><#if isAdd??>添加返工记录<#else>编辑返工记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm"
		name="inputForm" class="validate" action="<#if isAdd??>rework!save.action<#else>rework!update.action</#if>"	method="post">
			<input type="hidden" name="id" value="${(id)!}" />
			<input type="hidden" class="input input-sm" name="rework.workingbill.id" value="${(workingbill.id)!} ">
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">返工</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
						                   <div class="profile-info-row">
												<div class="profile-info-name">产品编号</div>
												<div class="profile-info-value">
													<span>${workingbill.matnr}</span>
												</div>
												
												
												<div class="profile-info-name">产品名称</div>
												<div class="profile-info-value">
													<span>${workingbill.maktx}</span>
												</div>
																								
											</div>
				

											<div class="profile-info-row">
												<div class="profile-info-name">翻包次数</div>
												<div class="profile-info-value">
													<input type="text" name="rework.reworkCount" "
														value="${(rework.reworkCount)!}"
														class=" input input-sm formText {minlength:0,maxlength: 100}" />													
												</div>

											<div class="profile-info-name">翻包数量</div>
												<div class="profile-info-value">
													<input type="text" name="rework.reworkAmount"
														value="${(rework.reworkAmount)!}"
														class=" input input-sm formText {minlength:0,maxlength: 100}" />
												</div>
											</div>


											<div class="profile-info-row">
												<div class="profile-info-name">缺陷数量</div>
												<div class="profile-info-value">
													<input type="text" name="rework.defectAmount"
														value="${(rework.defectAmount)!}"
														class=" input input-sm formText {minlength:0,maxlength: 100}" />													
												</div>


												<div class="profile-info-name">是否合格</div>
												<div class="profile-info-value">
													<select name=rework.isQualified id="form-field-icon-1"
														class="input input-sm form-control">
														<option value="">--</option> <#list allCheck as list>
														<option value="${list.dictkey}"<#if ((isAdd &&list.isDefault) || (isEdit && rework.isQualified ==list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>
													</select>
												</div>												
											</div>
										</div>
											
											<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">问题描述:</div>
												<div class="profile-info-value">
													<textarea name="rework.problem"
														style="width:600px;">${(rework.problem)!}</textarea>
												</div>
											</div>
										</div>
                             
                                      
                                      
                                        <div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">整改方案:</div>
												<div class="profile-info-value">
													<textarea name="rework.rectify"
														style="width:600px;">${(rework.rectify)!}</textarea>
												</div>
											</div>
										</div>
										
										
										<div class="profile-user-info profile-user-info-striped">
										  <div class="profile-info-row">
												<div class="profile-info-name">责任人</div>
												<div class="profile-info-value">
												 <input type="hidden" id="adminId" name="rework.duty.id" value="${(rework.duty.id)!}"  class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" readonly="readonly"/>					
												    
												    <#if isAdd??><button type="button" class="btn btn-xs btn-info" id="userAddBtn" data-toggle="button">选择</button>				                                    
				                                     <span id ="adminName"></span>
										         	 <label class="requireField">*</label>	
										         	 <#else>
										         	 ${(rework.duty.name)!}    
										         	 </#if>									
										    </div>
										 </div>
										</div>
										
										
										<div class="profile-user-info profile-user-info-striped">
										  <div class="profile-info-row">
										   <div class="profile-info-name">完工日期</div>
										     <div class="profile-info-value">
												<div class="input-daterange input-group">
												<input type="text" class="input-sm form-control datePicker " name="rework.completeDate" value="${(rework.completeDate)!}" onchange=out(this); >
											</div>
											</div>						
										 </div>
										</div>
										
										
										<div class="profile-user-info profile-user-info-striped">
										 <div class="profile-info-name">是否完工</div>
												<div class="profile-info-value">
													<label class="pull-left inline"> <small
														class="muted smaller-90">已完工:</small> <input type="radio"
														class="ace" name="rework.isCompelete" value="Y"<#if
														(rework.isCompelete == 'Y')!> checked</#if> /> <span
														class="lbl middle"></span> &nbsp;&nbsp; </label> <label
														class="pull-left inline"> <small
														class="muted smaller-90">未完工:</small> <input type="radio"
														class="ace" name="rework.isCompelete" value="N"<#if
														(isAdd || rework.isCompelete == 'N')!> checked</#if> /> <span》
														class="lbl middle"></span> </label>
												</div>															
										</div>
								</div>
										
							
				
			<div class="buttonArea" >			              
									<button class="btn btn-white btn-success btn-bold btn-round" id="completeRework" type=button>
										<span class="bigger-110 no-text-shadow">刷卡提交</span>
									</button>									
									<button class="btn btn-white btn-success btn-bold btn-round" id="checkRework" type=button>
										<span class="bigger-110 no-text-shadow">刷卡审核</span>
									</button>
									<button class="btn btn-white btn-success btn-bold btn-round" id="confirmRework" type=button>
										<span class="bigger-110 no-text-shadow">刷卡确认</span>
									</button>
									<button class="btn btn-white btn-success btn-bold btn-round" id="returnRework" type=button>
										<span class="bigger-110 no-text-shadow">返回</span>
									</button>
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
<script type="text/javascript">
$(function(){
	var $userAddBtn = $("#userAddBtn");//添加用户
	 var $adminId=$("#adminId");
	 var $adminName=$("#adminName");
	/**
	 * 添加按钮点击
	 */
	$userAddBtn.click(function(){
		var title = "人员";
		var width="800px";
		var height="600px";
		var content="rework!browser.action";
		jiuyi.admin.browser.dialog(title,width,height,content,function(index,layero){
			
        var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
        var work = iframeWin.getGridId();
        //alert(work);
        var id=work.split(",");
        $adminId.val(id[1]);
        $adminName.text(id[0]);
        layer.close(index);            	          	     	
        
       
	  });
	 	
	});
	
})


function out(input){
 var s =input.value;
 var date1 = new Date(Date.parse(s.replace(/-/g, "/")));
 var date2 = new Date();
if(date1.getMilliseconds()>date2.getMilliseconds()){
   alert("不能选择今天之前的日期!");
return;
}
}


$(function(){
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
	
	$("#addRework").click(function(){
		var workingBillId = $("#workingBillId").val();
		$("#inputForm").submit();  	
		
	});
	
	
	$("#completeRework").click(function(){		
    	$("#inputForm").submit();  		    		
	});
	
	
	$("#checkRework").click(function(){
	    document.inputForm.action="rework!check.action";
	    $("#inputForm").submit(); 			
	});
	
	$("#confirmRework").click(function(){
		document.inputForm.action="rework!confirm.action";
		$("#inputForm").submit(); 			
	});
	
	
	$("#undoRework").click(function(){
		var workingBillId = $("#workingBillId").val();
		var id = "";
		id=$("#grid-table").jqGrid('getGridParam','selarrrow');
		if(id==""){
			alert("请选择至少一条返工记录！");
		}else{
			window.location.href="carton!undo.action?id="+id+"&workingBillId="+workingBillId;			
		}
		
	});
	$("#returnRework").click(function(){
		window.history.back();
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


</script>
</html>