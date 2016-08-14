<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<title>考勤管理</title>
<head>
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" href="${base}/template/admin/css/kaoqin_list.css"
	type="text/css"></link>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<meta name="description"
	content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<!-- 
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/style.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/jiuyi/admin/css/setstyle.css"/>
		<link rel="stylesheet" rev="stylesheet" type="text/css" media="all" href="${base}/template/admin/ztree/css/zTreeStyle/zTreeStyle.css"/>
		<link rel="stylesheet" href="${base}/template/admin/assets/css/jquery-ui.min.css" />
		 -->

<#include "/WEB-INF/template/common/includelist.ftl">
<!--modify weitao-->
<script type="text/javascript"
	src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/browser/browser.js"></script>
<!-- 
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		 -->
<script type="text/javascript"
	src="${base}/template/admin/js/BasicInfo/kaoqin_list.js"></script>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style type="text/css">
.div_outexcel {
	text-align: center;
}
</style>
</head>
<body class="no-skin list xtxt">
	<input type="hidden" id="loginid"
		value="<@sec.authentication property='principal.id' />" />
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
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a>
					</li>
					<li class="active">考勤管理&nbsp;<span class="pageInfo"></span></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12 col-sm-6 widget-container-col ui-sortable">

									<div class="widget-box ui-sortable-handle">
										<div class="widget-header">
											<h4 class="widget-title">当前班组信息</h4>
										</div>

										<div class="widget-body">
											<div class="widget-main">
												<div class="profile-user-info profile-user-info-striped">
													<div class="profile-info-row">
														<div class="profile-info-name">工厂</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="username">${(admin.team.factoryUnit.workShop.factory.factoryName)!}</span>
														</div>
														
														<div class="profile-info-name">车间</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="username">${(admin.team.factoryUnit.workShop.workShopName)!}</span>
														</div>
													</div>

													<div class="profile-info-row">
														<div class="profile-info-name">单元</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${(admin.team.factoryUnit.factoryUnitName)!}</span>
														</div>
														
														<div class="profile-info-name">班次</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${(admin.xshift)!}</span>
															&nbsp;&nbsp; <span class="editable editable-click"
																id="age">${(admin.team.classSys)!}</span> &nbsp;&nbsp; <span
																class="editable editable-click" id="age">${(admin.team.basic)!}</span>
															<input type="hidden" id="classtime" value="${admin.shift}" />
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name">生产日期</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${(admin.productDate)!}</span>
															<input type="hidden" id="productDate" value="${admin.productDate}" />
														</div>
													</div>
													
													<div class="profile-info-row">
														<div class="profile-info-name">班组</div>
														<div class="profile-info-value">
															<span class="editable editable-click" id="age">${(admin.team.teamName)!}</span>
															<input type="hidden" id="teamid" value="${admin.team.id}" />
														</div>
													</div>
													
													<div class="profile-info-row">
													<div class="profile-info-name">工作小时</div>
													<div class="profile-info-value">
 														<input type="hidden" id="workHourss" value="${workHours}">
														<select id="workHours" class="formText {required: true}">
														<option></option>
														<#list list_workHours as whlist>
														<option value="${(whlist.dictkey)! }"<#if (whlist.dictkey== workHours)!>selected</#if>>${(whlist.dictvalue)!}
														</option> 
														</#list>
														</select> 
														&nbsp;&nbsp;&nbsp; 
														<button class="btn btn-white btn-default btn-sm btn-round" id="submitButton" type="button">保存</button>	
													</div>
												</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="col-xs-12 col-sm-6 widget-container-col ui-sortable">
									<div class="widget-box ui-sortable-handle div-height">
										<div class="widget-header">
											<h4 class="widget-title">考勤管理</h4>
										</div>

										<div class="widget-body">
											<div class="widget-main">
												<div class="div-btn">
													<button id="btn_add" class="btn_add">
														<img style=""
															src="${base}/template/admin/images/btn_addworker.gif"></img>
														添加代班员工
													</button>
												</div>
												<div class="div-btn2">
													<button id="btn_open" class="btn_open">
														<img id="img_startkaoqin" style="" src=""></img> <span
															id="span_startkaoqin"></span>
													</button>
												</div>
											</div>
											<div class="widget-main">
												<div class="div-btn3">
													<button id="btn_clickandcredit" class="btn_clickandcredit">
														<img style=""
															src="${base}/template/admin/images/btn_card3.gif"></img>
														点击刷卡上班
													</button>
												</div>
												<!--<div class="div-btn4" >
													<button id="btn_gooffwork" class="btn_gooffwork">
														<img style=""
															src="${base}/template/admin/images/gooffwork3.gif"></img>
														班组下班
													</button>
												</div>-->
											</div>
										</div>
									</div>
								</div>
							</div>
							<input type="hidden" id="sameteamid" value="${sameTeamId}" />
							
							<div class="row">
								<div class="col-xs-12">
									
									<table id="grid-table"></table>

									<div id="grid-pager"></div>
									
								</div>
							</div>
							</br>
							<div class="div_outexcel">
								<button id="btn_outexcel" type="button"
									class="btn btn-white btn-default btn-sm btn-round">
									<i class="ace-icon fa fa-cloud-upload"></i> 导出Excel
								</button>
							</div>
							
							<script type="text/javascript">
								var $path_base = "${base}/template/admin";
							</script>

						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

				<#include "/WEB-INF/template/admin/admin_footer.ftl">
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->
	<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
	<div id="divbox" style="display: none;">
		<br />
		<form id="xform" method="post" action="temp_kaoqin!updatetempkaoqin.action" class="validate" >
			<input type="hidden" id="kq-id" name="kaoqin.id" /> 
		<div class="profile-user-info profile-user-info-striped divbox">
			<div class="profile-info-row ceshi">
				<div class="profile-info-row">
					<div class="profile-info-name">姓名：</div>
					<div class="profile-info-value div-value" id="kq-name">												
					</div>
					<div class="profile-info-name">手机号码：</div>
					<div class="profile-info-value div-value">
						<input typr="text" id="phoneNum" name="kaoqin.phoneNum"> 			
					</div>
				</div>
								
				<div class="profile-info-row">
					<div class="profile-info-name">员工状态：</div>
					<div class="profile-info-value div-value">
						<#if list_dict??> <select id="select_state" class="select_state" name="kaoqin.workState">
							<#list list_dict as dlist>
							<option value="${(dlist.dictkey)! }"<#if
								(dlist.dictkey==list.workstate)!>selected</#if>>${(dlist.dictvalue)!
								}</option> </#list>
						</select> </#if>
					</div>
					<div class="profile-info-name">异常小时数：</div>
					<div class="profile-info-value div-value">
						<input type="text" id="input_hours" class=" input input-sm" name="kaoqin.tardyHours"/> 
						<span id="span_hours" style="font-family: 微软雅黑;font-size: 10px;color: red;"></span>
					</div>
				</div>				
				<div class="profile-info-row">
					<div class="profile-info-name">岗位</div>
					<div class="profile-info-value">						
							<img id="img_post" title="岗位" alt="岗位" style="cursor:pointer" src="/template/shop/images/add_bug.gif" />
							<span id="span_postname"></span>
							<input type="hidden" id="input_post" name="kaoqin.postCode" value="" class="col-xs-10 col-sm-5" />
						
					</div>
					<div class="profile-info-name">工位名称</div>
					<div class="profile-info-value">
						<select id="sel_station" name="kaoqin.stationCode" class="chosen-select work" multiple="true" style="width:290px;" data-placeholder="请选择...">
					    	<#if list_station??>
					    		<#list list_station as list>
					    			<option value="${(list.code)!}">${(list.code)!}--${(list.name)!}</option>
					    		</#list>
					    	</#if>
					    </select>
					</div>					
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">模具组号</div>
					<div class="profile-info-value">
						<select class="chosen-select work" id="model" multiple="true"
							style="width:290px;" name="kaoqin.modelNum"
							data-placeholder="请选择..."> <#list unitModelList as list>
							<option value="${(list.id)!}">${(list.station)!}</option>
							</#list>
						</select>
					</div>
				
					<div class="profile-info-name">工作范围</div>
					<div class="profile-info-value">
						<select class="chosen-select work" multiple="true" id="product" style="width:290px;" name="kaoqin.workNum" data-placeholder="请选择...">
							<#list unitProductList as list>
							      <option value="${(list.materialCode)!}">${(list.materialName)!}</option>
							</#list>
					    </select>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- ////////////////////////////////////////////////////////////////////////////////////////////////////// -->
	<!-- <input type="hidden" id="isstartteam" value="${isstartteam!}"> -->
	<input type="hidden" id="iscancreditcard" value="${iscancreditcard}">
	<input type="hidden" id="iswork" value="${iswork}">
</body>
</html>
<script type="text/javascript">

	$(function() {
		zhazha();
		//员工状态编辑事件
		//emp_state();
	});

	//员工状态编辑事件
	/*
	 function emp_state()
	 {
	 var i=0;
	 <#list list_emp as listemp>
	 //初始化样式
	 var val=$("#input_state${(listemp.cardNumber)!}").val();
	 if(val=="2"||val=="5"||val=="6")
	 {
	 sapn_stype1("span_state${(listemp.cardNumber)!}");
	 }
	 else
	 {
	 sapn_stype2("span_state${(listemp.cardNumber)!}");
	 }
	 //编辑点击事件
	 /*
	 $("#a_edit${(listkq.cardNum)!}").click(function(){
	 var index=$(this).attr("id");
	 index=index.substring(6,index.length);
	 edit_event(index);
	 });
	 *//*
				i+=1;
			</#list>
		}
	 */
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */
	function zhazha() {
		var ishead = 0;
		$("#ace-settings-btn").click(function() {
			if (ishead == 0) {
				ishead = 1;
				$("#ace-settings-box").addClass("open");
			} else {
				ishead = 0;
				$("#ace-settings-box").removeClass("open");
			}
		});
		$(".btn-colorpicker").click(function() {
			$(".dropdown-colorpicker").addClass("open");
		});

		var ishead2 = 0;
		$(".light-blue").click(function() {
			if (ishead2 == 0) {
				ishead2 = 1;
				$(this).addClass("open");
			} else {
				ishead2 = 0;
				$(this).removeClass("open");
			}
		});

		/*
		var ishead3=0;
		$(".hsub").click(function(){
			if(ishead3==0){
				alert("OK");
				ishead3=1;
				$(".hsub").addClass("open");
				//$(this).find(".submenu").removeClass("nav-hide");
			}else{
				ishead3=0;
				//$(this).removeClass("open");
				//$(this).find(".submenu").removeClass("nav-show").addClass("nav-hide").css("display","none");
			}
			
		})
		 */
	}
</script>
