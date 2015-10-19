<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>日志列表 - Powered By ${systemConfig.systemName}</title>
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
				<li class="active">日志列表&nbsp;<span class="pageInfo">总记录数: ${pager.totalCount}(共${pager.pageCount}页)</span></li>
			</ul><!-- /.breadcrumb -->
		</div>
		

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
		<form id="listForm" class="pdl10" action="log!list.action" method="post">
			<div class="operateBar">
				<select name="pager.property">
					<option value="operationName" <#if pager.property == "operationName">selected="selected" </#if>>
						操作名称
					</option>
					<option value="operator" <#if pager.property == "operator">selected="selected" </#if>>
						操作员用戶名
					</option>
				</select>
				
替换为
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
						&nbsp;
					</th>
					<th>
						<span class="sort" name="operationName">操作名称</span>
					</th>
					<th>
						<span class="sort" name="operator">操作员;</span>
					</th>
					<th>
						<span class="sort" name="ip">操作IP</span>
					</th>
					<th>
						<span class="sort" name="info">日志信息</span>
					</th>
					<th>
						<span class="sort" name="createDate">记录时间</span>
					</th>
				</tr>
				<#list pager.list as list>
					<tr>
						<td>
							&nbsp;
						</td>
						<td>
							${list.operationName}
						</td>
						<td>
							${list.operator}
						</td>
						<td>
							${list.ip}
						</td>
						<td>
							${list.info}&nbsp;
						</td>
						<td>
							<span title="${list.createDate?string("yyyy-MM-dd HH:mm:ss")}">${list.createDate}</span>
						</td>
					</tr>
				</#list>
			</table>
			<#if (pager.list?size == 0)>
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