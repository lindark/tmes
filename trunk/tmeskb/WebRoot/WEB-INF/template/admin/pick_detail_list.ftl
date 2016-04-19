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
<input type="hidden" id="loginid" value="<@sec.authentication property='principal.id' />" />
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
						<form id="inputForm" name="inputForm" class="validate" action="<#if isAdd??>pick_detail!creditsubmit.action<#else>pick_detail!creditupdate.action</#if>"
							 method="post">
							 <input type="hidden" id="my_id" name="my_id" value="${(my_id)! }" />
                            <input type="hidden"  id="workingBillId" name="workingBillId" value="${(workingbill.id)!} "/>
                            <input type="hidden" id="pickId" name="pickId" value="${(pick.id)! }" />
                            <input type="hidden" id="type" name="type" value="${type }"/>
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
												
												<div class="profile-info-row">
													<div class="profile-info-name">操作类型</div>
													<div class="profile-info-value">
														<select id="pickType" name="info" style="width:300px;" >
													    
													    <#if type="back">
													    <option value="262">退料</option>
													    <#else>
													    <option value="">-请选择-</option> 
													    <#list allType as alist>
													    <option value="${alist.dictkey}"<#if ((isAdd &&alist.isDefault) || (isEdit && pick.move_type ==alist.dictkey))!> selected</#if>>${alist.dictvalue}</option>
													    </#list>
													    </#if>
													    <!--  
													    <option value="262">退料</option> -->
													</select>
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
											<th style="text-align:center;">裁切倍数</th>
											<th style="text-align:center;">裁切后库存数量</th>
											<th style="text-align:center;">批次</th>	
											<th style="text-align:center;">裁切后领退料</th>
											<th style="text-align:center;">实际领退料</th>
										</tr>
									</thead>

									<tbody>
									    <#assign  num=0/>
										<#list bomList as list>
											<tr id="tr_1">
												<td class="center" name="">${(list.materialCode)! }</td>
												<td class="center" name="">${(list.materialName)! }</td>
												<td class="center" name="">${(list.stockAmount)!  }</td>
												<td class="center" name="">${(list.cqmultiple)!  }</td>
												<td class="center" name="">${(list.cqhStockAmount)!  }</td>
												<#if list.xcharg == null || list.xcharg ="" >
												<td align="center" id="charg"><input type="text" class="charg"></td>
												<#else>
												<td class="center" id="charg" name="">${(list.xcharg) }</td>
												</#if>
												<td class="center" name=""><input type="text" name="pickDetailList[${(num)}].cqPickAmount" value="${(list.cqPickAmount)!}" class="notnull input input-sm formText {digits:true} cqPickAmount" /></td>
												<td class="center">
													<input type="text" name="pickDetailList[${(num)}].pickAmount" value="${(list.pickAmount)!}" class="notnull input input-sm formText {digits:true} pickAmount"  readonly/>
													<input type="hidden" name="pickDetailList[${(num)}].materialCode" value="${(list.materialCode)! }"/>
													<input type="hidden" name="pickDetailList[${(num)}].materialName" value="${(list.materialName)! }"/>
													<input type="hidden" name="pickDetailList[${(num)}].pickType" value="${(list.pickType)! }"/>
													<input type="hidden" id="stockAmount${(num)}" name="pickDetailList[${(num)}].stockAmount" value="${(list.stockAmount)! }" class="stockAmount"/>
													<input type="hidden" name="pickDetailList[${(num)}].charg" value="${(list.xcharg)! }"/>
													<input type="hidden" name="pickDetailList[${(num)}].id" value="${(list.pickDetailid)! }"/>
													<input type="hidden" name="pickDetailList[${(num)}].cqmultiple" value="${(list.cqmultiple)! }"/>
													<input type="hidden" name="pickDetailList[${(num)}].cqhStockAmount" value="${(list.cqhStockAmount)! }"/>
												</td>											
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
				
                                  <div class="row buttons col-md-8 col-sm-4 sub-style">
                                     <button class="btn btn-white btn-default btn-sm btn-round" id="btn_save" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡提交
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_confirm" type=button>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡确认
									</button>
									<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type=button>
										<i class="ace-icon fa fa-home"></i>
										返回
									</button>
									</div>
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
		$("#btn_save").click(function(){
			var i = 0;
			var pickType = $("#pickType").val();
			if(pickType ==''){
				layer.alert("请选择正确的操作类型",{icon: 7});
				return false;
			}
			var flag = true;
			$(".cqPickAmount").each(function(){
				if($(this).val() != null && $(this).val() != ""){
					if(($(this).parent().prev().text()) == null || ($(this).parent().prev().text()) == ""){
						if(($(this).parent().prev().children().val()) == null || ($(this).parent().prev().children().val()) == ""){
							flag = false;
							layer.alert("请填写批次号",{icon: 7});
							return false;
						}
					}
				}
			});
			if(flag){
				if(pickType =='261') {
					$("#mytable tr").each(function(){
						var stockAmount = ($(this).children("td:eq(4)").text());
						var pickAmount = ($(this).children("td:eq(5)").children("input:first").val());
						if(pickAmount != null){
							i=floatSub(pickAmount,stockAmount);//减法
							if(i>0)
							{
								layer.alert("领料数量不能大于库存数量",{icon: 7});
								return false;
							}	
						}
					});
				}
				 var dt=$("#inputForm").serialize();
						var workingBillId = $("#workingBillId").val();
						var loginId = $("#loginid").val();
						<#if isAdd??>
						var url="pick_detail!creditsubmit.action?loginId="+loginId;
						<#else>
						var url="pick_detail!creditupdate.action?loginId="+loginId;
						</#if>
						credit.creditCard(url,function(data){
							if(data.status=="success"){
								layer.alert(data.message, {icon: 6},function(){
									window.location.href="pick!list.action?workingBillId="+workingBillId;
								}); 
							}else if(data.status=="error"){
								layer.alert(data.message, {
							        closeBtn: 0,
							        icon:5,
							        skin:'error'
							    });
							}					
						},dt)
			}
			
		});
		
		
		$("#btn_confirm").click(function(){
			var i = 0;
			var pickType = $("#pickType").val();
			if(pickType ==''){
				layer.alert("请选择正确的操作类型",{icon: 7});
				return false;
			}
			var flag = true;
			$(".cqPickAmount").each(function(){
				if($(this).val() != null && $(this).val() != ""){
					if(($(this).parent().prev().text()) == null || ($(this).parent().prev().text()) == ""){
						if(($(this).parent().prev().children().val()) == null || ($(this).parent().prev().children().val()) == ""){
							flag = false;
							layer.alert("请填写批次号",{icon: 7});
							return false;
						}
					}
				}
			});
			if(flag){
			if(pickType =='261') {
			$("#mytable tr").each(function(){
				var stockAmount = ($(this).children("td:eq(4)").text());
				var pickAmount = ($(this).children("td:eq(5)").children("input:first").val());
				if(pickAmount != null){
					i=floatSub(pickAmount,stockAmount);//减法
					if(i>0)
					{
						layer.alert("领料数量不能大于库存数量",{icon: 7});
						return false;
					}
						
				}
			});
		}
			
			var flag = false;
			$(".notnull").each(function(){
				var sVal = $(this).val();
				if(sVal != "" && sVal !="0")
					flag = true;
			})
			if(!flag){
				layer.alert("数量为0或者空时不允许刷卡确认",{icon: 7});
				return false;
			}
		
			
			 var dt=$("#inputForm").serialize();
				var workingBillId = $("#workingBillId").val();
				var loginId = $("#loginid").val();
				var url="pick_detail!creditapproval.action?loginId="+loginId;
				credit.creditCard(url,function(data){
					if(data.status=="success"){
						layer.alert(data.message, {icon: 6},function(){
							window.location.href="pick!list.action?workingBillId="+workingBillId;
						}); 
					}else if(data.status=="error"){
						layer.alert(data.message, {
					        closeBtn: 0,
					        icon:5,
					        skin:'error'
					    });
					}					
				},dt)
			
			}
		});
		
		
	

		
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
		
		$(".cqPickAmount").change(function(){
			var mount = $(this).val();
			if(mount!=""){
				mount = mount*1;
				var cqmultiple = $(this).parent().prev().prev().prev().text()*1;
				var total  = mount / cqmultiple;
				$(this).parent().next().children().eq(0).val(total);
				//var id = $(this).parent().children().eq(1).text();
			}else{
				$(this).parent().next().children().eq(0).val("");
			}
		
		});
		
		$(".charg").change(function(){
			var num = $(this).val();
			if(num!=""){
				$(this).parent().next().next().children().eq(5).val(num);
			}
		});
		
	})
	
	


	
</script>
