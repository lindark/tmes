<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>产品列表 - Powered By ${systemConfig.systemName}</title>
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
			<li class="active">产品列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	
	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
	

		<form id="listForm" class="pdl10" action="product!list.action" method="post">
			<div class="operateBar">
			
			
				<a id="addButton" class="btn btn-white btn-sm btn-info btn-round" href="product!add.action">
					<i class="ace-icon fa fa-pencil-square-o blue"></i>
					添加产品
				</a>
				<select name="pager.property">
					<option value="name" <#if pager.property == "name">selected="selected" </#if>>
						产品名称
					</option>
					<option value="productSn" <#if pager.property == "productSn">selected="selected" </#if>>
						产品编码
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
				
				
				<button id="syncMatInfoButton" class="btn btn-white btn-default btn-sm btn-round">
					<i class="ace-icon fa fa-cloud-download blue"></i>
					同步SAP物料主数据
				</button>
				
				
				<button id="syncMatPriceButton" class="btn btn-white btn-default btn-sm btn-round">
					<i class="ace-icon fa fa-cloud-download blue"></i>
					同步SAP物料价格
				</button>
				
				
				<button id="syncMatStockButton" class="btn btn-white btn-default btn-sm btn-round">
					<i class="ace-icon fa fa-cloud-download blue"></i>
					刷新库存
				</button>
				
			</div>
			<table class="table table-striped table-bordered table-hover">
				<tr>
					<th class="check">
						<input type="checkbox" class="allCheck" />
					</th>
					<th>
						<span class="sort" name="name">产品名称</span>
					</th>
					<th>
						<span class="sort" name="productSn">产品编码</span>
					</th>
					<th>
						<span class="sort" name="productCategory">分类</span>
					</th>
					<th>
						<span class="sort" name="price">单价</span>
					</th>
					<th>
						<span class="sort" name="isMarketable">上架</span>
					</th>
					<th>
						<span class="sort" name="isBest">精品</span>
					</th>
					<th>
						<span class="sort" name="isNew">新品</span>
					</th>
					<th>
						<span class="sort" name="isHot">热销</span>
					</th>
					<th>
						<span class="sort" name="store">库存</span>
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
							<#if (list.name?length <= 20)!>
								<span title="${list.name}">${list.name}</span>
							<#else>
								<span title="${list.name}">${list.name[0..20]}...</span>
							</#if>
						</td>
						<td>
							${list.productSn}
						</td>
						<td>
							${list.productCategory.name}
						</td>
						<td>
							${list.price?string(priceCurrencyFormat)}
						</td>
						<td>
							<#if list.isMarketable == true>
								<img src="${base}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${base}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<#if list.isBest == true>
								<img src="${base}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${base}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<#if list.isNew == true>
								<img src="${base}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${base}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<#if list.isHot == true>
								<img src="${base}/template/admin/images/list_true_icon.gif" />
							<#else>
								<img src="${base}/template/admin/images/list_false_icon.gif" />
							</#if>
						</td>
						<td>
							<span title="被占用数: ${(list.freezeStore)}">${(list.store)!"-"}<#if list.isOutOfStock> <span class="red">[缺货]</span></#if></span>
						</td>
						<td>
							<a href="product!edit.action?id=${list.id}" title="编辑">[编辑]</a>
							<#if list.isMarketable == true>
								<a href="${base}${list.htmlFilePath}" target="_blank" title="浏览">[浏览]</a>
							<#else>
								<span title="未上架">[未上架]</span>
							</#if>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size > 0)>
				<div class="pagerBar">
				<input type="button" class="deleteButton btn btn-white btn-default btn-sm btn-round " url="product!delete.action" value="删 除" disabled hidefocus="true" />
				
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