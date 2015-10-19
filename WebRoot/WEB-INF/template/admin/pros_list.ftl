<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>资源列表 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
	body{background:#fff;}
	#useButn input{
		margin: 10px;
		padding: 5px;
		background: #42C047;
		color: #fff;
		width: 80px;
		height: 30px;
	}
	#selectButton input{
		margin: 20px;
		background: #fff;
		width:70px;
		height 30px;
		border: 1px solid #000;
	}
	
	#nametable{
		margin: 10px 0;
		border: 1px solid #000;
		padding: 20px;
	}
	
	#showTable table{
		margin: 0px;
		width: 100%;
		line-height: 40px;
	}
	#showTable table th{
		background: #E2F1F5;
	}
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
	
<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
		</script>

		<ul class="breadcrumb">
			<li>
				<i class="ace-icon fa fa-home home-icon"></i>
				<a href="admin!index.action">基础信息</a>
			</li>
			<li class="active">${("[" + productType.name + "]")!} 产品组管理</li>
		</ul><!-- /.breadcrumb -->
	</div>
	
	<div class="useButn" id="useButn">
		<input type="button" value="手工添加" action=""/>
		<input type="button" value="SAP同步" action=""/>
		<input type="button" value="删除" action=""/>
	</div>
	
	<div class="selectButton" id="selectButton">
		<input type="button" value="查询" action=""/>
	</div>
	
	<div class="nametable" id="nametable">
		<table>
			<tr>
				<td>产品组名称：</td>
				<td><input type="text"/></td>
				<td>描述：</td>
				<td><input type="text"/></td>
			</tr>
		</table>
	</div>
	
	<div class="showTable" id="showTable">
		<table>
			<thead>
				<tr>
					<th><input type="checkbox" /></th>
					<th>产品组编号</th>
					<th>产品组名称</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
	</div>


<#include "/WEB-INF/template/admin/admin_footer.ftl">
</div><!-- /.page-content -->
				</div><!-- /.main-content -->
	</div><!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">	
	<!-- ./ add by welson 0728 -->

</body>
</html>