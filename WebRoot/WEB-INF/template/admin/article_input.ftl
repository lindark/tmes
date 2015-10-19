<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑文章 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
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
			<li class="active"><#if isAdd??>添加文章<#else>编辑文章</#if></li>
		</ul><!-- /.breadcrumb -->
	</div>
	

	<!-- add by welson 0728 -->
	<div class="page-content">
					<div class="page-content-area">					

						<div class="row">
							<div class="col-xs-12">
								<!-- ./ add by welson 0728 -->
								
		<form id="inputForm" class="validate" action="<#if isAdd??>article!save.action<#else>article!update.action</#if>" method="post">
			<input type="hidden" name="id" value="${id}" />
			<table class="inputTable">
				<tr>
					<th>
						文章标题:
					</th>
					<td>
						<input type="text" name="article.title" class="formText {required: true}" value="${(article.title)!}" />
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						文章分类:
					</th>
					<td>
						<select name="article.articleCategory.id" class="{required: true}">
							<option value="">请选择...</option>
							<#list articleCategoryTreeList as list>
								<option value="${list.id}"<#if (list.id == article.articleCategory.id)!> selected</#if>>
									<#if list.level != 0>
										<#list 1..list.level as i>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										</#list>
									</#if>
								${list.name}
								</option>
							</#list>
						</select>
						<label class="requireField">*</label>
					</td>
				</tr>
				<tr>
					<th>
						作者:
					</th>
					<td>
						<input type="text" class="formText" name="article.author" value="${(article.author)!}" />
					</td>
				</tr>
				<tr>
					<th>
						是否推荐:
					</th>
					<td>
					
					<label class="pull-left inline">
					    <small class="muted smaller-90">是:</small>
						<input type="radio" name="article.isRecommend" class="ace" value="true"<#if (article.isRecommend == true)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">否:</small>
						<input type="radio" name="article.isRecommend" class="ace" value="false"<#if (isAdd || article.isRecommend == false)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>	
				</td>
				</tr>
				<tr>
					<th>
						是否置顶:
					</th>
					<td>
					<label class="pull-left inline">
					    <small class="muted smaller-90">是:</small>
						<input type="radio" name="article.isTop" class="ace" value="true"<#if (article.isTop == true)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">否:</small>
						<input type="radio" name="article.isTop" class="ace" value="false"<#if (isAdd || article.isTop == false)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>	
					
					
					</td>
				</tr>
				<tr>
					<th>
						是否发布:
					</th>
					<td>
					
					<label class="pull-left inline">
					    <small class="muted smaller-90">是:</small>
						<input type="radio" name="article.isPublication" class="ace" value="true"<#if (article.isPublication == true)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>
							
					<label class="pull-left inline">

					    <small class="muted smaller-90">否:</small>
						<input type="radio" name="article.isPublication" class="ace" value="false"<#if (isAdd || article.isPublication == false)!> checked</#if> />
						<span class="lbl middle"></span>
					</label>	
					
					
				</td>
				</tr>
				<tr>
					<th>
						内容:
					</th>
					<td>
						<textarea name="article.content" class="wysiwyg {required: true, messagePosition: '#contentMessagePosition'}" rows="20" cols="100">${(article.content)!}</textarea>
						<div class="blank1"></div>
						<span id="contentMessagePosition"></span>
					</td>
				</tr>
				<tr>
					<th>
						页面关键词:
					</th>
					<td>
						<input type="text" class="formText" name="article.metaKeywords" value="${(article.metaKeywords)!}" />
					</td>
				</tr>
				<tr>
					<th>
						页面描述:
					</th>
					<td>
						<textarea name="article.metaDescription" class="formTextarea">${(article.metaDescription)!}</textarea>
					</td>
				</tr>
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