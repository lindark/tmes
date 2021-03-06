<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>人员管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browserValidate.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/admin_inputry.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
<style type="text/css">
	.mymust{color: red;font-size: 10px;}
	.class_label_xfuname{width:200px;line-height: 30px;border:1px solid;border-color: #d5d5d5;}
	.xspan{font-family: 微软雅黑;font-size: 10px;color:red;}
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
			<li class="active"><#if isAdd??>添加员工<#else>修改员工信息</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	<!-- add by welson 0728 -->
	<div class="page-content">
		<div class="page-content-area">					
		<div class="row">
		<div class="col-xs-12">
		<!-- ./ add by welson 0728 -->
		<form id="xform" method="post" action="<#if isAdd??>admin!saveempry.action<#else>admin!updateempry.action</#if>" class="validate">
			<input type="hidden" id="loginid" name="loginid" value="<@sec.authentication property='principal.id' />" />
			<input type="hidden" id="adminid" name="admin.id" value="${(admin.id)! }"/>
			<input type="hidden" name="deptid" value="${(admin.department.id)! }"/>
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<div class="profile-info-name">姓名</div>
					<div class="profile-info-value">
						<input type="text" name="admin.name" value="${(admin.name)! }"  class="col-xs-10 col-sm-5 formText {required: true}">
						<label class="requireField">*</label>
					</div>
					<div class="profile-info-name">性别</div>
					<div class="profile-info-value">
						<label class="pull-left inline">
							<small class="muted smaller-90">男</small>
							<input type="radio" name="admin.sex" class="ace" value="nan"
								<#if (isAdd ||admin.sex == "nan")!> checked</#if> />
							<span class="lbl middle"></span>
						</label>
						<label class="pull-left inline">
							<small class="muted smaller-90">女</small>
							<input type="radio" name="admin.sex" class="ace" value="nv"
								<#if (admin.sex == "nv")!> checked</#if> />
							<span class="lbl middle"></span>
						</label>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">身份证号</div>
					<div class="profile-info-value">
						<#if isAdd??>
							<input type="text" name="admin.identityCard" value="${(admin.identityCard)! }" class="col-xs-10 col-sm-5">
						<#else>
							${(admin.identityCard)! }
							<input type="hidden" name="admin.identityCard" value="${(admin.identityCard)! }">
						</#if>
					</div>
					<div class="profile-info-name">联系电话</div>
					<div class="profile-info-value">
						<input type="text" name="admin.phoneNo" value="${(admin.phoneNo)! }" class="col-xs-10 col-sm-5 formText {required: true}">
						<label class="requireField">*</label>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">工号</div>
					<div class="profile-info-value">
						<#if isAdd??>
							<input type="text" id="input_worknum" name="admin.workNumber" value="${(admin.workNumber)! }" class="col-xs-10 col-sm-5">
							<span id="span_worknum" class="xspan"></span>
						<#else>
							${(admin.workNumber)! }
							<input type="hidden" id="input_worknum" name="admin.workNumber" value="${(admin.workNumber)! }">
						</#if>
					</div>
					<div class="profile-info-name">卡号</div>
					<div class="profile-info-value">
						<#if isAdd??>
							<input type="text" id="input_cardnum" name="admin.cardNumber" value="${(admin.cardNumber)! }" class="col-xs-10 col-sm-5">
							<span id="span_cardnum" class="xspan"></span>
						<#else>
							<input type="text" id="input_cardnum" name="admin.cardNumber" value="${(admin.cardNumber)! }">
						</#if>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">E-mail</div>
					<div class="profile-info-value">
						<input type="text" name="admin.email" value="${(admin.email)! }" class="col-xs-10 col-sm-5 ">
					</div>
					<div class="profile-info-name">亲属关系</div>
					<div class="profile-info-value">
						<input type="text" name="admin.relationShip" value="${(admin.relationShip)! }" class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">操作等级工</div>
					<div class="profile-info-value">
						<input type="text" name="admin.workerGrade" value="${(admin.workerGrade)! }" class="col-xs-10 col-sm-5">
					</div>
					<div class="profile-info-name">最高学历</div>
					<div class="profile-info-value">
						<input type="text" name="admin.education" value="${(admin.education)! }" class="col-xs-10 col-sm-5">
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">部门编码</div>
					<div class="profile-info-value">
						<#if isAdd??>
							<img id="img_dept" title="部门" alt="部门" style="cursor:pointer" src="/template/shop/images/add_bug.gif" />
							<span id="span_deptcode">${(admin.department.deptCode)! }</span>
							<input type="hidden" id="input_dept" name="admin.department.id" value="${(admin.department.id)! }" class="col-xs-10 col-sm-5" />
						<#else>
							${(admin.department.deptCode)! }
							<input type="hidden" id="input_dept" name="admin.department.id" value="${(admin.department.id)! }" />
						</#if>
					</div>
					<div class="profile-info-name">部门名称</div>
					<div class="profile-info-value">
						<span id="span_deptname">${(admin.department.deptName)! }</span>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">岗位</div>
					<div class="profile-info-value">
						<#if isAdd??>
							<img id="img_post" title="岗位" alt="岗位" style="cursor:pointer" src="/template/shop/images/add_bug.gif" />
							<span id="span_postname">${(admin.post.postName)! }</span>
							<input type="hidden" id="input_post" name="admin.post.id" value="${(admin.post.id)! }" class="col-xs-10 col-sm-5" />
						<#else>
							<img id="img_post" title="岗位" alt="岗位" style="cursor:pointer" src="/template/shop/images/add_bug.gif" />
							<span id="span_postname">${(admin.post.postName)! }</span>
							<input type="hidden" id="input_post" name="admin.post.id" value="${(admin.post.id)! }" />
						</#if>
					</div>
					<div class="profile-info-name">工位编码/名称</div>
					<div class="profile-info-value">
						<!-- <span id="span_workstation">${(admin.post.station)! }</span> -->
						<select id="sel_station" name="strStationIds" class="chosen-select work" multiple="" style="width:290px;" data-placeholder="请选择...">
					    	<#if list_station??>
					    		<#list list_station as list>
					    			<option value="${(list.id)!}">${(list.code)!}--${(list.name)!}</option>
					    		</#list>
					    	</#if>
					    </select>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">模具组号</div>
					<div class="profile-info-value">
						<select class="chosen-select work" id="model" multiple="" style="width:290px;" name="unitdistributeModels" data-placeholder="请选择...">
							<#if isEdit??>
							 <#list unitModelList as list>
							      <option value="${(list.id)!}">${(list.station)!}</option>
							</#list>
							</#if>
					    </select>
					</div>
					<div class="profile-info-name">工作范围</div>
					<div class="profile-info-value">
						<select class="chosen-select work" multiple="" id="product" style="width:290px;" name="unitdistributeProducts" data-placeholder="请选择...">
							<#list unitProductList as list>
							      <option value="${(list.id)!}">${(list.materialName)!}</option>
							</#list>
					    </select>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">班组</div>
					<div class="profile-info-value">
						<img id="img_team2" title="班组" alt="班组" style="cursor:pointer" src="/template/shop/images/add_bug.gif" />
						<span id="span_teamname">${(admin.team.teamName)! }</span>
						<input type="hidden" id="input_team" name="admin.team.id" value="${(admin.team.id)! }" class="col-xs-10 col-sm-5" />
					</div>
					<div class="profile-info-name">直接上级</div>
					<div class="profile-info-value">
						<img id="img_boss" title="直接上级" alt="直接上级" style="cursor:pointer" src="/template/shop/images/add_bug.gif">
						<span id="span_boss">${(admin.parentAdmin.name)! }</span>
						<input type="hidden" id="input_boss" name="admin.parentAdmin.id" value="${(admin.parentAdmin.id)! }" class="col-xs-10 col-sm-5" />
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">是否离职</div>
					<div class="profile-info-value">
						<label class="pull-left inline">
							<small class="muted smaller-90">在职</small>
							<input type="radio" name="admin.isDel" class="ace" value="N"
								<#if (isAdd || admin.isDel == "N")!> checked</#if> />
							<span class="lbl middle"></span>
						</label>
						<label class="pull-left inline">
							<small class="muted smaller-90">离职</small>
							<input type="radio" name="admin.isDel" class="ace" value="Y"
								<#if (admin.isDel == "Y")!> checked</#if> />
							<span class="lbl middle"></span>
						</label>
					</div>
					<div class="profile-info-name">入职日期</div>
					<div class="profile-info-value">
						<#if isAdd??>
							<input type="text" name="admin.startWorkDate" class="datePicker" value="${(strDate)! }" />
						<#else>
							${(admin.startWorkDate)!}
							<input type="hidden" name="admin.startWorkDate" class="datePicker" value="${(admin.startWorkDate)!}" />
						</#if>
					</div>
				</div>
				<div class="profile-info-row">
					<div class="profile-info-name">当前状态</div>
					<div class="profile-info-value">
						<input type="text" name="admin.nowState" value="${(admin.nowState)! }" class="col-xs-10 col-sm-5">
					</div>
				</div>
			</div>
			<div class="buttonArea">
				<#if isAdd??>
					<input type="button" class="formButton" id="btn_submit" value="确  定" hidefocus="true" />
				<#else>
					<!-- 当可以编码工号,卡号时,此submit按钮不再使用--2016-03-24 -->
					<input type="submit" class="formButton" value="确  定" hidefocus="true" />
				</#if>
				<input type="button" class="formButton" id="btn_return" value="返  回" hidefocus="true" />
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
<script type="text/javascript">
$(function(){
	$("#img_team2").bind("click",function(){
		layer.open({
	        type: 2,
	        skin: 'layui-layer-lan',
	        shift:2,
	        title: "选择班组",
	        fix: false,
	        shade: 0.5,
	        shadeClose: true,
	        maxmin: true,
	        scrollbar: false,
	        btn:['确认','取消'],
	        area: ["80%", "80%"],//弹出框的高度，宽度
	        content:"team!beforegetalllist.action",
	        yes:function(index,layero){//确定
	        	var iframeWin = window[layero.find('iframe')[0]['name']];//获得iframe 的对象
	        	var info = iframeWin.getName();
	        	$.ajax({
	        		url:"team!findFactoryUnit.action?info="+info.teamid,
	        		dataType:"json",
	        		success:function(data){
	        			$("#input_team").val(info.teamid);
	                	$("#span_teamname").text(info.teamname);
	                	$("#model").empty();
	                	/* $("#model_chosen").find(".chosen-results").empty(); */
	                	var opt="";
	                	var opt1="";
	                	for(var i=0;i<data.length;i++){
	            			opt = "<option value="+data[i].umid+">"+data[i].station+"</option>";
	            			$("#model").append(opt);
	            			/* if(i==0){
	            				opt1 = "<li class=\"active-result highlighted\" \"data-option-array-index="+i+"\""+">"+data[i].station+"</li>";
	            			}else{
	            				opt1 = "<li class=\"active-result\" \"data-option-array-index="+i+"\""+">"+data[i].station+"</li>";
	            			}
	            			$("#model_chosen").find(".chosen-results").append(opt1);   */
	            		}
	                	$("#model").chosen();
	            		$("#model").trigger("chosen:updated");
	        		},
	        		error:function(){
	        			alert("查找失败");
	        		}
	        		
	         	});
	        	layer.close(index);
	        	return false;
	        },
	        no:function(index)
	        {
	        	layer.close(index);
	        	return false;
	        }
	    });
		return false;
	});
	
	
	<#if isAdd??>
	<#else>		 
		<#list admin.unitdistributeModelSet as list>
			$("#model option[value='${(list.id)!}']").attr("selected","selected"); 		  
		</#list>
	 		 
		<#list admin.unitdistributeProductSet as list>
			$("#product option[value='${(list.id)!}']").attr("selected","selected"); 		
		</#list>
		<#list list_station2 as list>
			$("#sel_station option[value='${(list.id)!}']").attr("selected","selected");
		</#list>
		$(".work").chosen();
		$(".work").trigger("chosen:updated");
	</#if>
});
</script>
</html>