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
.div-name{text-align: center;}
.div-value{padding-right:30px;min-width:200px; }
.div-value2{text-align:right;padding-right:0px;min-width:200px;}
.input-value{width:80px;height:30px;line-height:30px;}
.sub-style{float: right;}
.requireField{color:red;}
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
								
		<form id="inputForm" name="inputForm" class="validate" enctype="multipart/form-data" action=""	method="post">
			<input id="rk" type="hidden" name="id" value="${(id)!}" />
			<input type="hidden" id="workingBillId" class="input input-sm" name="rework.workingbill.id" value="${(workingbill.id)!} ">
			<input type="hidden" id="reworkId" value="${(rework.id)!''}"/>
			<input type="hidden" id="reworkCount" name="rework.reworkCount" value="${(rework.reworkCount)!}"/>
			<div id="inputtabs">
			<ul>
				<li>
				  <#if show??>
				     <a href="#tabs-1">查看返工单</a>
				  <#else>
				     <a href="#tabs-1">编辑返工单</a>
				  </#if>			
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
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
				

											<div class="profile-info-row">							  
                                               <div class="profile-info-name" >翻包数量</div>
												<div class="profile-info-value access" data-access-list="reworkAmount">
												  <#if show??>
															<span>${(reworkRecord.reworkAmount)! }</span>
												  <#else>
													<input type="text" name="reworkRecord.reworkAmount" value="${(reworkRecord.reworkAmount)!}" class="theAmount isZero input input-sm"/>
												 </#if>
												<label class="requireField">*</label>
												</div>
												
												<div class="profile-info-name">缺陷数量</div>
												<div class="profile-info-value access" data-access-list="defectAmount">
												<#if show??>
													 <span>${(reworkRecord.defectAmount)!}</span>
												  <#else>
													<input type="text" name="reworkRecord.defectAmount" value="${(reworkRecord.defectAmount)!}" class="theAmount isZero input input-sm" />	
												</#if>
												<label class="requireField">*</label>												
											 </div>
										 </div>


										<div class="profile-info-row">								
												

												<div class="profile-info-name">是否合格</div>
												<div class="profile-info-value access" data-access-list="isQualified">
												     <#if show??>
														<span>${isQualified! }</span>
													<#else>
													<select name=reworkRecord.isQualified id="form-field-icon-1" class="">
														<#list allCheck as list>
														<option value="${list.dictkey}"<#if ((isAdd &&list.dictkey =='N') || (isEdit && reworkRecord.isQualified ==list.dictkey))!> selected</#if>>${list.dictvalue}</option>
														</#list>
													</select>
													</#if>
														<label class="requireField">*</label>
												</div>												
										</div>
											
									
										
										<div class="profile-info-row">
												<div class="profile-info-name">问题描述:</div>
												<div class="profile-info-value access" data-access-list="problem">
												 <#if show??>
													 <span>${(reworkRecord.problem)! }</span>
												  <#else>
													<textarea name="reworkRecord.problem" style="width:90%;" class="isZero input input-sm">${(reworkRecord.problem)!}</textarea>
													</#if>
												   <label class="requireField">*</label>
												</div>
										</div>
										
                                     
                               
                                       
										<div class="profile-info-row">
												<div class="profile-info-name">整改方案:</div>
												<div class="profile-info-value access"  data-access-list="rectify">
												<#if show??>
													 <span>${(reworkRecord.rectify)! }</span>
												  <#else>
													<textarea name="reworkRecord.rectify" style="width:90%;" class="isZero input input-sm">${(reworkRecord.rectify)!}</textarea>
												</#if>
												   <label class="requireField">*</label>
												</div>
										</div>
										
								
									
									
										  <div class="profile-info-row">
												<div class="profile-info-name">责任人</div>
												<div class="profile-info-value access" data-access-list="dutyName">
												 <#if show??>
													 <span>${(reworkRecord.duty.name)! }</span>
												 <#else>
												 <input type="hidden" id="adminId" name="reworkRecord.duty.id" value="${(reworkRecord.duty.id)!}"  class="isZero input input-sm" readonly="readonly"/>					
												    
												    <#if isAdd??><button type="button" class="btn btn-xs btn-info" id="userAddBtn" data-toggle="button">选择</button>				                                    
				                                     <span id ="adminName"></span>
										         	 <label class="requireField">*</label>	
										         	 <#else>
										         	 ${(reworkRecord.duty.name)!}    
										         	 </#if>	
										         	 </#if>								
										    </div>
									     </div>	
									
									
										  <div class="profile-info-row">
										   <div class="profile-info-name">完工日期</div>
										     <div class="profile-info-value access" data-access-list="completeDate">
										      <#if show??>
													 <span>${(reworkRecord.completeDate)! }</span>
											  <#else>
												<div class="input-group">
												<input type="text" class="isZero input-sm form-control datePicker productDate" style="width:90%"name="reworkRecord.completeDate" value="${(reworkRecord.completeDate)!}"  >
												<span class="requireField">*</span>
												</div>
											</#if>
											</div>						
									    </div>	
								</div>
							</div>
							<br/>
						          <div class="row buttons col-md-8 col-sm-4 sub-style">	
				                  <#if show??><#else>
				                    <#if xadd??>
								    <button class="btn btn-white btn-default btn-sm btn-round" id="completeRework" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>
								   <#else>
										<button class="btn btn-white btn-default btn-sm btn-round" id="checkRework" type=button>
										<i class="ace-icon glyphicon glyphicon-ok"></i>
										刷卡回复
									   </button>
									   <button class="btn btn-white btn-default btn-sm btn-round" id="confirmRework" type=button>
										<i class="ace-icon fa fa-cloud-upload"></i>
										刷卡确认
									   </button>
								   </#if>
									</#if>
								   <button class="btn btn-white btn-default btn-sm btn-round" id="returnRework" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
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
</html>
<script type="text/javascript">
$(function(){
	//必填提示隐藏/显示
	tip_event();
	
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
	 $(".productDate").change(function(){
		 if($(this).val()!=""){
			 out($(this));
		   }
	}); 
});


 function out(input){
   var s =input.val();
   var date1 = new Date(s.replace("-", "/"));
    var d = new Date();
    var vYear = d.getFullYear();
    var vMon = d.getMonth() + 1;
    var vDay = d.getDate();
    var date2 = new Date(vYear+"/"+vMon+"/"+vDay);
	if(date2>date1){
    	alert("不能选择今天之前的日期!");
    	input.val("");
  		return false;
  	}
	if($(this).val().length!=10){
		alert("日期格式错误");
		$(this).val("");
		return false;
	}
} 

$(function(){
	var reworkId = $("#reworkId").val();
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

	$("#completeRework").click(function(){	
		var flag=true;
		var regx = /^(\+|-)?\d+($|\d+$)/;
		$(".theAmount").each(function(){
			if($(this).val!=null&&$(this).val()!=""){
				flag = regx.test($(this).val());
				if(flag == false){
					layer.msg("数量输入整数或输入不合法!", {icon: 5});
					return false;
				}
			}
		})
		$(".isZero").each(function(){
			if($(this).val()==""){		
				alert("请填写必填项");
				flag = false;
				return false;
			}
		});
		if(flag){
		var dt=$("#inputForm").serialize();
			var workingBillId = $("#workingBillId").val();
			var reworkCount = $("#reworkCount").val();
			var url="rework_record!creditsubmit.action?reworkId="+reworkId+"&workingBillId="+workingBillId+"&reworkCount="+reworkCount;
			credit.creditCard(url,function(data){
				if(data.status=="success"){
					layer.alert(data.message, {icon: 6},function(){
						window.location.href="rework_record!list.action?reworkId="+data.reworkId+"&workingBillId="+workingBillId;
					}); 
				}else if(data.status=="error"){
					layer.alert(data.message, {
				        closeBtn: 0,
				        icon:5,
				        skin:'error'
				   });
				}		
			},dt);
		}else{
			$("#inputFrom").submit();
		}
	});
	
	
	$("#checkRework").click(function(){
		var dt=$("#inputForm").serialize();
		var workingBillId = $("#workingBillId").val();
		var url="rework_record!creditreply.action?reworkId="+reworkId;
		credit.creditCard(url,function(data){
			if(data.status=="success"){
				layer.alert(data.message, {icon: 6},function(){
					window.location.href="rework_record!list.action?reworkId="+data.reworkId+"&workingBillId="+workingBillId;
				}); 
			}else if(data.status=="error"){
				layer.alert(data.message, {
			        closeBtn: 0,
			        icon:5,
			        skin:'error'
			   });
			}		
		},dt);				
	});
	
	$("#confirmRework").click(function(){
		
		
	//	var id=$("#rk").val();
		var dt=$("#inputForm").serialize();
		var workingBillId = $("#workingBillId").val();
		var url="rework_record!creditapproval.action?reworkId="+reworkId;
		credit.creditCard(url,function(data){
			if(data.status=="success"){
				layer.alert(data.message, {icon: 6},function(){
					window.location.href="rework_record!list.action?reworkId="+data.reworkId+"&workingBillId="+workingBillId;
				}); 
			}else if(data.status=="error"){
				layer.alert(data.message, {
			        closeBtn: 0,
			        icon:5,
			        skin:'error'
			   });
			}		
		},dt);	
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

function tip_event()
{
	<#if show??>
		$(".requireField").hide();
	</#if>
}




</script>
