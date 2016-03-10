<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑设备管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
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
			<li class="active"><#if isAdd??>添加设备记录<#else>编辑设备记录</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>equipments!save.action<#else>equipments!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<div id="inputtabs">
			<ul>
				<li>
					<a href="#tabs-1">设备信息</a>
				</li>
				
			</ul>
			
			<div id="tabs-1">
			
				<!--weitao begin modify-->
						<div class="profile-user-info profile-user-info-striped">
									 	<div class="profile-info-row">
										<div class="profile-info-name"> 设备编码 </div>					
										<div class="profile-info-value">
										        <input type="text" name="equipments.equipmentNo" value="${(equipments.equipmentNo)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />											   											
										</div>
										<div class="profile-info-name"> 设备名称 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.equipmentName" value="${(equipments.equipmentName)!}" class=" input input-sm  formText {required: true,minlength:2,maxlength: 100}" />
											<label class="requireField">*</label>	
										</div>
									</div>	
								
									
									<div class="profile-info-row">	
										<div class="profile-info-name"> 公司 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.company" value="${(equipments.company)!}" class=" input input-sm  formText {required: true}" />
											<label class="requireField">*</label>	
										</div>
										<div class="profile-info-name"> 工厂 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.factory" value="${(equipments.factory)!}" class=" input input-sm  formText {required: true}" />
											<label class="requireField">*</label>	
										</div>
									</div>
									
									 <div class="profile-info-row">	
										<div class="profile-info-name"> 成本中心 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.costCenter" value="${(equipments.costCenter)!}" class=" input input-sm  formText {required: true}" />
											<label class="requireField">*</label>	
										</div>
										<div class="profile-info-name"> 工作中心 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.workCenter" value="${(equipments.workCenter)!}" class=" input input-sm  formText {required: true}" />
											<label class="requireField">*</label>	
										</div>
									</div>	
									
									 <div class="profile-info-row">	
										<div class="profile-info-name"> 功能位置 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.functionPosition" value="${(equipments.functionPosition)!}" class=" input input-sm" />	
										</div>
										<div class="profile-info-name"> 位置 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.position" value="${(equipments.position)!}" class=" input input-sm" />								
										</div>
									</div>	
									
									<div class="profile-info-row">	
										<div class="profile-info-name"> ABC标识 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.identify" value="${(equipments.identify)!}" class=" input input-sm" />
										</div>
										<div class="profile-info-name"> 设备种类 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.type" value="${(equipments.type)!}" class=" input input-sm" />
										</div>
									</div>
									
									<div class="profile-info-row">	
										<div class="profile-info-name"> 状态 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.condition" value="${(equipments.condition)!}" class=" input input-sm" />
										</div>
										<div class="profile-info-name"> 有效起始日 </div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.startday" value="${(equipments.startday)!}" class=" input input-sm" />
										</div>
									</div>
									
									
									<div class="profile-info-row">	
										<div class="profile-info-name"> 有效截至日</div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.endday" value="${(equipments.endday)!}" class=" input input-sm" />
										</div>
										<div class="profile-info-name"> 设备类型</div>					
										<div class="profile-info-value">
										    <select name="equipments.version" style="width:163px;" class="formText {required: true}">
							                <option value="">全选</option> 
							                <#list allVersion as list>
								            <option value="${list.dictkey}"<#if ((isAdd && list.isDefault) || (isEdit && equipments.version == list.dictkey))!> selected</#if>>${list.dictvalue}</option>
							                </#list>              
						                    </select>
											<label class="requireField">*</label>	
											
										</div>
									</div>
									
									<div class="profile-info-row">	
										
									</div>
									
									<div class="profile-info-row">
									    <div class="profile-info-name"> 功能位置描述</div>					
										<div class="profile-info-value">
											<input type="text" name="equipments.functionPositiondecribe" value="${(equipments.functionPositiondecribe)!}" class=" input input-sm" />
										</div>
										
								    </div>
								    <div class="profile-info-row">
										<div class="profile-info-name"> 是否启用</div>					
										<div class="profile-info-value">
											<label class="pull-left inline">
					                           <small class="muted smaller-90">已启用:</small>
						                       <input type="radio" class="ace" name="equipments.state" value="1"<#if (equipments.state == '1')!> checked</#if> />
						                       <span class="lbl middle"></span>
						                         &nbsp;&nbsp;
					                        </label>						
					                        <label class="pull-left inline">
					                            <small class="muted smaller-90">未启用:</small>
						                        <input type="radio" class="ace" name="equipments.state" value="2"<#if (isAdd || equipments.state == '2')!> checked</#if>  />
						                         <span class="lbl middle"></span>
					                        </label>		
										</div>	
									</div>					
						</div>								
			</table>
			<div class="buttonArea">
				<input type="submit" class="formButton" value="确  定" hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp;
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