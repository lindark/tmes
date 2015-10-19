<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>产品分类列表 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<script type="text/javascript">
$().ready(function() {

	// 删除确认对话框
	$(".deleteAction").click( function() {
		if (confirm("您确定要删除此产品分类吗？") == false) {
			return false;
		}
	});
	
	// 树折叠
	$(".categoryName").click( function() {
		var level = $(this).parent().attr("level");
		var isHide;
		$(this).parent().nextAll("tr").each(function(){
			var thisLevel = $(this).attr("level");
			if(thisLevel <=  level) {
				return false;
			}
			if(isHide == null) {
				if($(this).is(":hidden")){
					isHide = true;
				} else {
					isHide = false;
				}
			}
			if( isHide) {
				$(this).show();
			} else {
				$(this).hide();
			}
		});
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
			<li class="active">产品分类列表&nbsp;<span class="pageInfo">总记录数: ${productCategoryTreeList?size}</span></li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->

		<form id="listForm"  class="pdl10"  action="product_category!list.action" method="post">
			<div class="operateBar">
			
			<a id="addButton" class="btn btn-white btn-sm btn-info btn-round"  href="product_category!add.action">
					<i class="ace-icon fa fa-pencil-square-o blue"></i>
					添加分类
			</a>
			
			
				<button id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
					<i class="ace-icon fa fa-cloud-download blue"></i>
					同步SAP产品层次
				</button>
				
			</div>
			
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th>
						分类名称&nbsp;
					</th>
					<th>
						排序&nbsp;
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list productCategoryTreeList as list>
					<tr level="${(list.level)!}">
						<td class="categoryName">
							<span style="margin-left: ${list.level * 20}px;">
								<#if list.level == 0>
									<img src="${base}/template/admin/images/list_category_first_icon.gif" />
								<#else>
									<img src="${base}/template/admin/images/list_category_icon.gif" />
								</#if>
								${list.name}
							</span>
						</td>
						<td>
							${list.orderList}
						</td>
						<td>
							<a href="${base}/shop/product!list.action?id=${list.id}" target="_blank" title="查看">[查看]</a>
							<#if list.children?size gt 0>
								<span title="无法删除">[删除]</span>
							<#else>
								<a href="product_category!delete.action?id=${list.id}" class="deleteAction" title="删除" >[删除]</a>
							</#if>
							<a href="product_category!edit.action?id=${list.id}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if productCategoryTreeList?size == 0>
				<div class="noRecord">
					没有找到任何记录!
				</div>
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