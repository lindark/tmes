<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>产品属性列表 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
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
			<li class="active">${("[" + productType.name + "]")!} 产品属性列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></li>
		</ul><!-- /.breadcrumb -->
	</div>

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="listForm" class="pdl10" action="product_attribute!list.action" method="post">
			<input type="hidden" name="productTypeId" value="${productTypeId}" />
			<div class="operateBar">
			
				<a id="addButton" class="btn btn-white btn-sm btn-info btn-round" href="product_attribute!add.action${("?productTypeId=" + productTypeId)!}">
					<i class="ace-icon fa fa-pencil-square-o blue"></i>
					添加属性
				</a>
				
				

				<select name="pager.property">
					<option value="name" <#if pager.property == "name">selected="selected" </#if>>
						属性名称
					</option>
				</select>
				<input type="text" name="pager.keyword" class="input input-sm" value="${pager.keyword!}" id="form-field-icon-1">
				<button id="searchButton" class="btn btn-white btn-default btn-sm btn-round">
					<i class="ace-icon fa fa-filter blue"></i>
					搜索
				</button>	
				
				<label class="label label-xlg label-white" style="background-color:#fff!important;color:#777;">每页显示:</label>
				<select name="pager.pageSize" id="pageSize">
					<option value="10" <#if pager.pageSize == 10>selected="selected" </#if>>
						10
					</option>
					<option value="20" <#if pager.pageSize == 20>selected="selected" </#if>>
						20
					</option>
					<option value="50" <#if pager.pageSize == 50>selected="selected" </#if>>
						50
					</option>
					<option value="100" <#if pager.pageSize == 100>selected="selected" </#if>>
						100
					</option>
				</select>
			</div>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<span class="sort" name="name">属性名称</span>
					</th>
					<th>
						<span class="sort" name="productType">产品类型</span>
					</th>
					<th>
						<span class="sort" name="attributeType">属性类型</span>
					</th>
					<th>
						<span class="sort" name="isRequired">是否必填</span>
					</th>
					<th>
						<span class="sort" name="isEnabled">是否启用</span>
					</th>
					<th>
						<span class="sort" name="orderList">排序</span>
					</th>
					<th>
						操作
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							<input type="checkbox" name="ids" value="${list.id}" />
						</td>
						<td>
							${list.name}&nbsp;
						</td>
						<td>
							${list.productType.name}&nbsp;
						</td>
						<td>
							${action.getText("AttributeType." + list.attributeType)}
						</td>
						<td>
							<#if list.isRequired == true>
								<img src="${base}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${base}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<#if list.isEnabled == true>
								<img src="${base}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${base}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							${list.orderList}
						</td>
						<td>
							<a href="product_attribute!edit.action?id=${list.id}${("&productTypeId=" + productTypeId)!}" title="编辑">[编辑]</a>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
					<input type="button" class="deleteButton btn btn-white btn-default btn-sm btn-round " url="product_attribute!delete.action" value="删 除" disabled hidefocus="true" />
					<#include "/WEB-INF/template/admin/pager.ftl" />
				</div>
			<#else>
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
