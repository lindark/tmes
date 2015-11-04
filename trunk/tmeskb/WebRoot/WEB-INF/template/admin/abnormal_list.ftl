<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>异常</title>
<meta name="description"
	content="Dynamic tables and grids using jqGrid plugin" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
<#include "/WEB-INF/template/common/includelist.ftl">
<script type="text/javascript"
	src="${base}/template/unusual/js/abnormal_list.js"></script>
<script type="text/javascript"
	src="${base}/template/admin/js/jqgrid_common.js"></script>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
</head>
<body class="no-skin">
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
			<style>
.buttons {
	padding: 10px 5px;
	margin: 6px 3px;
}

button {
	width: 136px;
	height: 36px;
	padding: 3px 3px;
	margin: 3px 3px;
	/*background-color: #00DB00;*/
	color: #FFFFFF
}

.call {
	width: 100px;
	height: 60px;
	padding: 16px 6px;
	margin-left: 200px;
	margin-top: 30px;
	background-color: #edf4eb;
	border-color: #a7c9a1;
	color: #6ea465;
	font-size: 20px
}

th {
	height: 28px;
	width: 200px;
	padding-left: 100px;
}
</style>
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
						href="admin!index.action">管理中心</a>
					</li>
					<li class="active">异常&nbsp;<span class="pageInfo"></span></li>
				</ul>
				<!-- /.breadcrumb -->
			</div>

			<div class="page-content">

				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<div class="row">
								<div class="col-xs-12 col-sm-6 widget-container-col ui-sortable">

									<div class="widget-box ui-sortable-handle">
										<div class="widget-header">
											<h5 class="widget-title">当前班组信息</h5>

											<div class="widget-toolbar">
												<div class="widget-menu">
													<a data-action="settings" data-toggle="dropdown"> <i
														class="ace-icon fa fa-bars"></i> </a>

													<ul
														class="dropdown-menu dropdown-menu-right dropdown-light-blue dropdown-caret dropdown-closer">
														<li><a data-toggle="tab" href="#dropdown1">Option#1</a>
														</li>

														<li><a data-toggle="tab" href="#dropdown2">Option#2</a>
														</li>
													</ul>
												</div>

												<a data-action="fullscreen" class="orange2"> <i
													class="ace-icon fa fa-expand"></i> </a> <a data-action="reload">
													<i class="ace-icon fa fa-refresh"></i> </a> <a
													data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a> <a
													data-action="close"> <i class="ace-icon fa fa-times"></i>
												</a>
											</div>


										</div>

										<div class="widget-body">
											<div class="widget-main">
												<div class="profile-info-row">
													<div class="profile-info-name">工厂</div>
													<div class="profile-info-value">建新赵氏集团密封条厂</div>
												</div>
												<div class="profile-info-row">
													<div class="profile-info-name">车间</div>
													<div class="profile-info-value">封条厂</div>
												</div>
												<div class="profile-info-row">
													<div class="profile-info-name">单元</div>
													<div class="profile-info-value">1单元</div>
												</div>
												<div class="profile-info-row">
													<div class="profile-info-name">班组</div>
													<div class="profile-info-value">01班</div>
												</div>
												<div class="profile-info-row">
													<div class="profile-info-name">时间</div>
													<div class="profile-info-value">2015-09-21</div>
												</div>
												<!--   <table>
												    <tr>
												    <th>工厂:</th>
						                            <td>${(abnormal.productName)!}建新赵氏集团密封条厂</td>
					                                </tr>
					                                <tr>
												    <th>车间:</th>
						                            <td>${(abnormal.productName)!}密封条</td>
					                                </tr>
						                            <tr>
												    <th>单元:</th>
						                            <td>${(abnormal.productName)!}1单元</td>
					                                </tr>
					                                <tr>
												    <th>班组:</th>
						                            <td>${(abnormal.productName)!}01班</td>
					                                </tr>
												    <tr>
												    <th>时间:</th>
						                            <td>${(abnormal.productName)!}2015-09-16</td>
					                                </tr>
												    </table>
													<!-- <p class="alert alert-info">
														Nunc
													</p>
													<p class="alert alert-success">
														Raw
													</p> -->
											</div>
										</div>
									</div>


								</div>

								<div class="col-xs-12 col-sm-6 widget-container-col ui-sortable">
									<div class="widget-box ui-sortable-handle">
										<div class="widget-header">
											<h5 class="widget-title">呼叫人</h5>


											<div class="widget-toolbar">
												<div class="widget-menu">
													<a data-action="settings" data-toggle="dropdown"> <i
														class="ace-icon fa fa-bars"></i> </a>

													<ul
														class="dropdown-menu dropdown-menu-right dropdown-light-blue dropdown-caret dropdown-closer">

													</ul>
												</div>

												<a data-action="fullscreen" class="orange2"> <i
													class="ace-icon fa fa-expand"></i> </a> <a data-action="reload">
													<i class="ace-icon fa fa-refresh"></i> </a> <a
													data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a> <a
													data-action="close"> <i class="ace-icon fa fa-times"></i>
												</a>
											</div>


										</div>

										<div class="widget-body">
											<div class="widget-main">
                                              
												 <p class="alert alert-success"
													style="height:160px;background-color:white">
													<button class="call">呼叫</button>
												    <!-- 
													<div class="col-md-3 col-sm-4">
													   <button	class="btn btn-white btn-success btn-bold btn-round btn-block">
                                                         <span class="bigger-110 no-text-shadow">呼叫</span>
									                   </button>
									                 </div>  -->
												</p> 
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="row buttons">
							    <!-- 
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block">
                                      <span class="bigger-110 no-text-shadow">创建质量问题单</span>
									</button>
								</div> -->
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block">
                                      <span class="bigger-110 no-text-shadow">创建工模维修单</span>
									</button>
								</div>
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block">
                                      <span class="bigger-110 no-text-shadow">创建工艺维修单</span>
									</button>
								</div>
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block">
                                      <span class="bigger-110 no-text-shadow">创建设备维修单</span>
									</button>
								</div>
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block">
                                      <span class="bigger-110 no-text-shadow">响应刷卡</span>
									</button>
								</div>
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block">
                                      <span class="bigger-110 no-text-shadow">撤销呼叫</span>
									</button>
								</div>
								<div class="col-md-2 col-sm-4" >
									<button	class="btn btn-white btn-success btn-bold btn-round btn-block">
                                      <span class="bigger-110 no-text-shadow">关闭异常</span>
									</button>
								</div>
							</div>

							<table id="grid-table"></table>

							<div id="grid-pager"></div>

							<script type="text/javascript">
								var $path_base = "${base}/template/admin";
							</script>


						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="footer">
			<div class="footer-inner">

				<div class="footer-content">
					<span class="bigger-120"> <span class="blue bolder">Ace</span>
						Application &copy; 2013-2014 </span> &nbsp; &nbsp; <span
						class="action-buttons"> <a href="#"> <i
							class="ace-icon fa fa-twitter-square light-blue bigger-150"></i>
					</a> <a href="#"> <i
							class="ace-icon fa fa-facebook-square text-primary bigger-150"></i>
					</a> <a href="#"> <i
							class="ace-icon fa fa-rss-square orange bigger-150"></i> </a> </span>
				</div>

				<!-- /section:basics/footer -->
			</div>
		</div>

		<a href="#" id="btn-scroll-up"
			class="btn-scroll-up btn btn-sm btn-inverse"> <i
			class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i> </a>
	</div>

	<script type="text/javascript">
		
	</script>
</body>
</html>
