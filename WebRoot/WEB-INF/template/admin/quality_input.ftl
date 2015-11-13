<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>添加/编辑质量问题单 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${base}/template/admin/js/unusual/js/quality.js"></script>
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<style>
body {
	background: #fff;
}

.deleteImage,#addImage,#removeImage {
	cursor: pointer;
}
</style>
</head>
<body class="no-skin input">

	<!-- add by welson 0728 -->
	<#include "/WEB-INF/template/admin/admin_navbar.ftl">
	<div class="main-container" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.check('main-container', 'fixed')
			} catch (e) {
			}
		</script>
		<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
		<div class="main-content">
			<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">

			<!-- ./ add by welson 0728 -->
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a
						href="admin!index.action">管理中心</a></li>
					<li class="active"><#if isAdd??>添加质量问题单<#else>编辑质量问题单</#if></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->

							<form id="inputForm" class="validate"
								action="<#if isAdd??>quality!save.action<#else>quality!update.action</#if>"
								method="post">
								<input type="hidden" name="id" value="${id}" /> <input
									type="hidden" name="abnormalId" value="${(abnormal.id)!}" />
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">基本信息</a></li>
										<li><a href="#tabs-2">单据日志</a></li>

										<li><a href="#tabs-3">整改情况跟踪</a></li>
										<li><a href="#tabs-4">相关单据</a></li>
									</ul>

									<div id="tabs-1">

										<!--weitao begin modify-->
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">产品编号</div>

												<div class="profile-info-value">
													<select name="quality.productNo">
														<option value="1">40010021</option>
														<option value="2">40010021</option>
													</select>
												</div>
												<div class="profile-info-name">产品名称</div>

												<div class="profile-info-value">
													<input type="text" name="quality.productName"
														value="${(quality.productName)!}"
														class=" input input-sm  formText {required: true}" />
												</div>

											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">生产工序</div>

												<div class="profile-info-value">
													<select name="quality.process">
														<option value="1">裁切</option>
														<option value="2">接角</option>
													</select>
												</div>
												<div class="profile-info-name">班组</div>

												<div class="profile-info-value">
													<input type="text" name="quality.team"
														value="${(abnormal.teamId)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">抽检数量</div>
												<div class="profile-info-value">
													<input type="text" name="quality.samplingAmont"
														value="${(quality.samplingAmont)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
												<div class="profile-info-name">缺陷数量</div>
												<div class="profile-info-value">
													<input type="text" name="quality.failAmont"
														value="${(quality.failAmont)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
											</div>

											<div class="profile-info-row">
												<div class="profile-info-name">报告时间</div>
												<div class="profile-info-value">
													<input type="text" name="quality.createDate"
														value="${(quality.createDate)!}"
														class="formText {required: true} datePicker" />
												</div>
												<div class="profile-info-name">计划完成时间</div>
												<div class="profile-info-value">
													<input type="text" name="quality.overTime"
														value="${(quality.overTime)!}"
														class="formText {required: true} datePicker" />
												</div>
											</div>
											<div class="profile-info-row">
												<div class="profile-info-name">挤出批次</div>
												<div class="profile-info-value">
													<input type="text" name="quality.extrusionBatches"
														value="${(quality.extrusionBatches)!}"
														class=" input input-sm  formText {required: true}" />
												</div>
											</div>

										</div>
										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">质量问题描述</div>
												<div class="profile-info-value">
													<textarea name="quality.problemDescription"
														style="width:600px;">${(quality.problemDescription)!}</textarea>
												</div>
											</div>
										</div>

										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">车间整改方案</div>
												<div class="profile-info-value">
													<textarea name="quality.rectificationScheme"
														style="width:600px;">${(quality.rectificationScheme)!}</textarea>
												</div>
											</div>
										</div>

										<div class="profile-user-info profile-user-info-striped">
											<div class="profile-info-row">
												<div class="profile-info-name">工程师意见</div>
												<div class="profile-info-value">
													<textarea name="quality.engineerOpinion"
														style="width:600px;">${(quality.engineerOpinion)!} </textarea>
												</div>
											</div>
										</div>
										<!--weitao end modify-->


									</div>

									<table id="tabs-2" class="inputTable tabContent">
										<tbody>
											<tr class="title">
												<th>时间</th>
												<th>内容</th>
												<th>修改人</th>
											</tr>
											<tr>
												<td>2015-09-16 09:20</td>
												<td>张三已刷卡</td>
												<td>张三</td>
											</tr>
										</tbody>
									</table>
									<div id="tabs-3">
										<table id="sample-table-1"
											class="table table-striped table-bordered table-hover">
											<thead>
												<tr>									
													<th>内容</th>
												</tr>
											</thead>

											<tbody>
												<#if
												(quality==null||quality.flowingRectify==null||(quality.flowingRectify)!?size==0)>
												<tr class="zg">						

												<!--  	<td><input type="text"
														name="flowingRectifys[0].createDate" value=""
														class="formText {required: true} datePicker" /></td>-->
													<td><!--<input type="text"
														name="flowingRectifys[0].content" value=""
														style="width:360px;" />  -->
														<textarea name="flowingRectifys[0].content"
														style="width:600px;"></textarea>
													</td>

												</tr>
												<#else> <#list (quality.flowingRectify)! as list>
												<tr class="zg">
													
												<!--<td>  <input type="text"
														name="flowingRectifys[${list_index}].createDate"
														value="${list.createDate}"/></td>	-->
													<td><textarea name="flowingRectifys[${list_index}].content"
														style="width:600px;">${list.content}</textarea></td>
												</tr>
												</#list> </#if>
												<tr>
					<td colspan="3">
					   <!--  <button class="btn btn-xs btn-primary" id="addImage">新增</button>
					    <button class="btn btn-xs" id="removeImage">删除</button>	--> 
					    <a id="addImage">新增</a>	
					    <a id="removeImage">删除</a>				
					</td>
			</tr>
											</tbody>
										</table>
									</div>
									<!-- 
			<table id="tabs-3" class="inputTable tabContent">		   									
				<tbody>
			<tr class="title">			   
				<th>时间</th>
				<th>内容</th>
				<th>操作人</th>
			</tr>
			<#if (quality==null||quality.flowingRectify==null||(quality.flowingRectify)!?size==0)>
			<tr  class="zg">				
					<td>
						<input type="text" name="flowingRectifys[0].createDate" value="" class="formText {required: true} datePicker"/>
					</td>
					<td>	
					    <input type="text" name="flowingRectifys[0].content" value=""	style="width:360px;"/>			
						
					</td>	
					<td><input type="text" name="flowingRectifys[0].createUser" value=""/></td>		
			</tr>
			<#else>
			  <#list (quality.flowingRectify)! as list>
			     <tr  class="zg">				
					<td>
						<input type="text" name="flowingRectifys[${list_index}].createDate" value="${list.createDate}" class="formText {required: true} datePicker"/>
					</td>
					<td>	
					    <input type="text" name="flowingRectifys[${list_index}].content" value="${list.content}"	style="width:360px;"/>			
						
					</td>	
					<td><input type="text" name="flowingRectifys[${list_index}].createUser" value="${list.createUser}"/>			</td>		
			    </tr>
			  </#list>
			</#if>
			<tr>
					<td colspan="3">
						<img src="${base}/template/admin/images/input_add_icon.gif" id="addImage" alt="增加选项" />&nbsp;&nbsp;&nbsp;&nbsp;
						<img src="${base}/template/admin/images/input_remove_icon.gif" id="removeImage" alt="减少选项" />
					</td>
			</tr>
		</tbody>
			</table> -->

									<table id="tabs-4" class="inputTable tabContent">

									</table>

									<div class="buttonArea">
										<input type="submit" class="formButton" value="确  定"
											hidefocus="true" />&nbsp;&nbsp;&nbsp;&nbsp; <input
											type="button" class="formButton"
											onclick="window.history.back(); return false;" value="返  回"
											hidefocus="true" />
									</div>
							</form>

							<!-- add by welson 0728 -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content-area -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.page-content -->
	</div>
	<!-- /.main-content -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->

</body>
</html>