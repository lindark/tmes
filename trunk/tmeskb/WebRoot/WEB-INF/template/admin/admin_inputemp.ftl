<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>部门管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>

<script type="text/javascript" src="${base }/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
<style type="text/css">
	.mymust{color: red;font-size: 10px;}
	.class_label_xfuname{width:200px;line-height: 30px;border:1px solid;border-color: #d5d5d5;}
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
			<li class="active"><#if isAdd??>添加部门<#else>修改部门</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								<form type="post"
								action="<#if isAdd??>department!savedept.action<#else>department!updatedept.action</#if>"
								id="departform" class="validateajax">
								<div class="profile-user-info profile-user-info-striped">
									<div class="profile-info-row">
										<input type="hidden" name="department.id"
											value="${(department.id)!}" />
										<!--id-->
										<div class="profile-info-name">部门名称</div>

										<div class="profile-info-value">
											<input id="deptname" type="text" name="department.deptName" class=" input input-sm formText {required: true}" value="${(department.deptName)!}" />
										<label class="requireField">*</label>
										</div>
									</div>
									<div class="profile-info-row">
										<div class="profile-info-name">上级部门</div>

										<div class="profile-info-value">
											<select name="department.parentDept.id" id="r_select" class="chosen-select">
												<#if list??>
												    <#list list as list>
											            <option value="${(list.id)!}"<#if (department.parentDept.id==list.id)!> selected</#if>>${(list.deptName)!}</option>
										               </#list>  
										         </#if>
											</select>
											<label class="requireField">*</label>
											<!-- 
											<#if isAdd??> <input type="hidden"
												name="department.parentDept.id" value="${(pid)!}" /> <span>${(pname)!}</span>
											<#else> <input type="hidden" name="department.parentDept.id"
												value="${(department.parentDept.id)!}" /> <span>${(department.parentDept.deptName)!}</span>
											</#if>
											 -->
										</div>

									</div>
									<div class="profile-info-row">
										<div class="profile-info-name">所属班组</div>

										<div class="profile-info-value">
											<div class="example">
												<fieldset id="self-data">
													<div class="form">
														<select name="factoryid" class="factory select"  data-first-title="请选择..." data-value="${(department.team.factoryUnit.workShop.factory.id)! }" data-url="department!getFactory.action" data-json-space="factory"></select>
														<select name="workshopid"	class="workshop select"  data-first-title="请选择..." data-value="${(department.team.factoryUnit.workShop.id)! }" data-url="department!getWorkshop.action" data-json-space="workshop"></select>
														<select name="factoryunitid"	class="factoryunit select"  data-first-title="请选择..." data-value="${(department.team.factoryUnit.id)! }" data-url="department!getFactoryunit.action" data-json-space="factoryunit"></select>
														<select name="department.team.id" class="team select" data-first-title="请选择..." data-value="${(department.team.id)! }" data-url="department!getTeam.action" data-json-space="team" id="teamselect"></select>
											<input id="input_teamsel" type="hidden" class="formText {required: true}"/>
											<label class="requireField">*</label>
													</div>
												</fieldset>
											</div>
										</div>

									</div>

								</div>
								<div class="buttonArea">
									<input type="button" class="formButton" id="submit_btn" value="确  定" hidefocus="true" />
									<input type="button" class="formButton" onclick="window.history.back(); return false;" value="返  回" hidefocus="true" />
								</div>
							</form>
			
<!-- add by welson 0728 -->	
				</div><!-- /.col -->
				</div><!-- /.row -->

				<!-- PAGE CONTENT ENDS -->
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
	$("#self-data").cxSelect({
			selects: ['factory', 'workshop', 'factoryunit','team'],
			jsonName: 'name',
			jsonValue: 'value'
		},function(select){//回调
			$(select).trigger("chosen:updated"); 
			$(select).chosen({allow_single_deselect:true,no_results_text:"没有找到",search_contains: true});
	});	
		
	/**
	* 提交
	*/
	$("#submit_btn").click(function(){
		//部门去空
		var deptname=$("#deptname").val().replace(/\s+/g,"");
		$("#deptname").val(deptname);
		//所属班组不能为空
		var teamval=$("#teamselect").val();
		if(teamval!=null&&teamval!="")
		{
			$("#input_teamsel").val(teamval);
		}
		else
		{
			$("#input_teamsel").val("");
		}
		$("#departform").submit();
	});
});

</script>