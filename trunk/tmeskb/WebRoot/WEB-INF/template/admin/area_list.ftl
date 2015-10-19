<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>地区列表 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript">
$().ready( function() {

	var $delete = $(".delete");
	
	// 地区删除
	$delete.click( function() {
		var $this = $(this);
		var id = $this.metadata().id;
		if (confirm("您确定要删除吗？") == true) {
			$.ajax({
				url: "area!delete.action",
				data: {"id": id},
				dataType: "json",
				async: false,
				success: function(data) {
					if (data.status == "success") {
						$this.parent().html("&nbsp;");
					}
					$.message(data.status, data.message);
				}
			});
		}
		return false;
	});
	
})
</script>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body{background:#fff;}
</style>
</head>
<body class="no-skin list">
	
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
			<li class="active">地区管理&nbsp;<span class="pageInfo">总记录数: ${areaList?size}</span></li>
		</ul><!-- /.breadcrumb -->
	</div>

	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
	
	
		<form id="listForm" class="pdl10" action="area!list.action" method="post">
			<div class="operateBar">
<a id="addButton" class="btn btn-white btn-sm btn-info btn-round" href="area!add.action<#if parent??>?parentId=${parentId}</#if>">
					<i class="ace-icon fa fa-pencil-square-o blue"></i>
					添加地区
				</a>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th colspan="5" class="green" style="text-align: center;">
						<#if parent??>上级地区 - [${(parent.name)!}]<#else>顶级地区</#if>
					</th>
				</tr>
				<#list areaList as list>
					<#if (list_index + 1) == 1>
						<tr>
					</#if>
					<td>
						<a href="area!list.action?parentId=${list.id}" title="查看下级地区">${list.name}</a>
						<a href="area!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						<a href="#" class="delete {id: '${list.id}'}" title="删除">[删除]</a>
					</td>
					<#if (list_index + 1) % 5 == 0 && list_has_next>
						</tr>
						<tr>
					</#if>
					<#if (list_index + 1) % 5 == 0 && !list_has_next>
						</tr>
					</#if>
					<#if (list_index + 1) % 5 != 0 && !list_has_next>
							<td colspan="${5 - areaList?size % 5}">&nbsp;</td>
						</tr>
					</#if>
				</#list>
				<#if areaList?size == 0>
					<tr>
						<td colspan="5" style="text-align: center; color: red;">
							无下级地区! <a href="area!add.action<#if parent??>?parentId=${parentId}</#if>" style="color: gray">点击添加</a>
						</td>
					</tr>
				</#if>
			</table>
			<#if parent??>
				<div class="blank1"></div>
				<#if (parent.parent)??>
					<input type="button" class="formButton" onclick="location.href='area!list.action?parentId=${(parent.parent.id)!}'" value="上级地区" />
				<#else>
					<input type="button" class="formButton" onclick="location.href='area!list.action'" value="上级地区" />
				</#if>
			</#if>
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