<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>管理中心 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">
</head>
<body class="no-skin">

<#include "/WEB-INF/template/admin/admin_navbar.ftl">
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	
	<#include "/WEB-INF/template/admin/admin_sidebar.ftl">
	
	<div class="main-content">
	<#include "/WEB-INF/template/admin/admin_acesettingbox.ftl">
	
				<!-- #section:basics/content.breadcrumbs -->
				<div class="breadcrumbs" id="breadcrumbs">
					<script type="text/javascript">
						try{ace.settings.check('breadcrumbs' , 'fixed')}catch(e){}
					</script>

					<ul class="breadcrumb">
						<li>
							<i class="ace-icon fa fa-home home-icon"></i>
							<a href="#">管理中心</a>
						</li>
						<li class="active">首页</li>
					</ul><!-- /.breadcrumb -->

					

					<!-- /section:basics/content.searchbox -->
				</div>

				<!-- /section:basics/content.breadcrumbs -->
				<div class="page-content">
					

					<div class="page-content-area">
						<div class="page-header">
							<h1>
								门户
								<small>
									<i class="ace-icon fa fa-angle-double-right"></i>
									我的个人门户
								</small>
							</h1>
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								<div class="alert alert-block alert-success">
									<button type="button" class="close" data-dismiss="alert">
										<i class="ace-icon fa fa-times"></i>
									</button>

									<i class="ace-icon fa fa-check green"></i>

									欢迎使用
									<strong class="green">
										${systemConfig.systemDescription}
										<small>(${systemConfig.systemVersion})</small>
									</strong>,
	最好用的协同管理平台.全新的用户体验。
								</div>

								<div class="row">
									<div class="space-6"></div>

									<div class="col-sm-7 infobox-container">
										<!-- #section:pages/dashboard.infobox -->
										<div class="infobox infobox-green">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-pencil-square-o"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number">${unprocessedOrderCount} </span>
												<div class="infobox-content"><a href="order!list.action">未批准订单</a> </div>
											</div>

											<!-- #section:pages/dashboard.infobox.stat -->
												<div class="stat stat-success">
												+0%
												<i class="ace-icon fa fa-arrow-up"></i>
											</div>

											<!-- /section:pages/dashboard.infobox.stat -->
										</div>

										<div class="infobox infobox-blue">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-calendar"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number">${paidUnshippedOrderCount}</span>
												<div class="infobox-content"><a href="order!list.action">等待发货订单</a></div>
											</div>

											<div class="badge badge-success">
												+0%
												<i class="ace-icon fa fa-arrow-up"></i>
											</div>
										</div>

										<div class="infobox infobox-pink">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-shopping-cart"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number">${storeAlertCount}</span>
												<div class="infobox-content"><a href="product!list.action">产品库存报警</a></div>
											</div>
											<div class="stat stat-important">0%</div>
										</div>

										<div class="infobox infobox-red">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-flask"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number">${marketableProductCount}</span>
												<div class="infobox-content">已上架产品</div>
											</div>
										</div>

										<div class="infobox infobox-orange2">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-bolt"></i>
											</div>
											<div class="infobox-data">
												<span class="infobox-data-number">${unMarketableProductCount}</span>
												<div class="infobox-content">已下架产品</div>
											</div>

											<div class="badge badge-success">
												0%
												<i class="ace-icon fa fa-arrow-up"></i>
											</div>
										</div>

										<div class="infobox infobox-blue2">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-users"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-text">会员总数</span>

												<div class="infobox-content">
													<span class="bigger-110"></span>
													${memberTotalCount}
												</div>
											</div>
										</div>
										
										<div class="infobox infobox-orange">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-book"></i>
											</div>

											<div class="infobox-data">
												<span class="infobox-data-number">${articleTotalCount}</span>
												<div class="infobox-content"><a href="article!list.action">文章总数</a></div>
											</div>


										</div>

										<!-- /section:pages/dashboard.infobox -->
										<div class="space-6"></div>
										<!--
										<h5>程序工作目录：${statics["java.lang.System"].getProperty("user.dir")} </h5>
										-->
										<!-- #section:pages/dashboard.infobox.dark -->
										<div class="infobox infobox-green infobox-small infobox-dark">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-cog"></i>
											</div>

											<div class="infobox-data">
												<div class="infobox-content">Java版本</div>
												<div class="infobox-content">${statics["java.lang.System"].getProperty("java.version")}</div>
											</div>
										</div>

										<div class="infobox infobox-blue infobox-small infobox-dark">
											<!-- #section:pages/dashboard.infobox.sparkline -->
											<div class="infobox-icon">
												<i class="ace-icon fa fa-desktop"></i>
											</div>

											<!-- /section:pages/dashboard.infobox.sparkline -->
											<div class="infobox-data">
												<div class="infobox-content">操作系统</div>
												<div class="infobox-content">${statics["java.lang.System"].getProperty("os.name")}</div>
											</div>
										</div>

										<div class="infobox infobox-grey infobox-small infobox-dark">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-briefcase"></i>
											</div>

											<div class="infobox-data">
												<div class="infobox-content">系统版本</div>
												<div class="infobox-content">${statics["java.lang.System"].getProperty("os.version")}</div>
											</div>
										</div>
										
										<div class="infobox infobox-orange infobox-small infobox-dark">
											<div class="infobox-icon">
												<i class="ace-icon fa fa-cogs"></i>
											</div>

											<div class="infobox-data">
												<div class="infobox-content">系统架构</div>
												<div class="infobox-content">${statics["java.lang.System"].getProperty("os.arch")}</div>
											</div>
										</div>

										


										<!-- /section:pages/dashboard.infobox.dark -->
									</div>

									<div class="vspace-12-sm"></div>

									<div class="col-sm-5">
										<div class="widget-box">
											<div class="widget-header widget-header-flat widget-header-small">
												<h5 class="widget-title">
													<i class="ace-icon fa fa-signal"></i>
													产品分类销售情况统计
												</h5>

												<div class="widget-toolbar no-border">
													<div class="inline dropdown-hover">
														<button class="btn btn-minier btn-primary">
															本周
															<i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
														</button>

														<ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
															<li class="active">
																<a href="#" class="blue">
																	<i class="ace-icon fa fa-caret-right bigger-110">&nbsp;</i>
																	本周
																</a>
															</li>

															<li>
																<a href="#">
																	<i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i>
																	上周
																</a>
															</li>

															<li>
																<a href="#">
																	<i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i>
																	本月
																</a>
															</li>

															<li>
																<a href="#">
																	<i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i>
																	上月
																</a>
															</li>
														</ul>
													</div>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main">
													<!-- #section:plugins/charts.flotchart -->
													<div id="piechart-placeholder"></div>

													<!-- /section:plugins/charts.flotchart -->
													<div class="hr hr8 hr-double"></div>

													<div class="clearfix">
														<!-- #section:custom/extra.grid -->
														<div class="grid3">
															<span class="grey">
																<i class="ace-icon fa fa-bar-chart-o fa-2x blue"></i>
																&nbsp; 订单
															</span>
															<h4 class="bigger pull-right">1,255</h4>
														</div>

														<div class="grid3">
															<span class="grey">
																<i class="ace-icon fa fa-calendar fa-2x purple"></i>
																&nbsp; 发货
															</span>
															<h4 class="bigger pull-right">941</h4>
														</div>

														<div class="grid3">
															<span class="grey">
																<i class="ace-icon fa fa-credit-card fa-2x red"></i>
																&nbsp; 退货
															</span>
															<h4 class="bigger pull-right">1,050</h4>
														</div>

														<!-- /section:custom/extra.grid -->
													</div>
												</div><!-- /.widget-main -->
											</div><!-- /.widget-body -->
										</div><!-- /.widget-box -->
									</div><!-- /.col -->
								</div><!-- /.row -->

								<!-- #section:custom/extra.hr -->
								<div class="hr hr32 hr-dotted"></div>

								<!-- /section:custom/extra.hr -->
								<div class="row">
									<div class="col-sm-5">
										<div class="widget-box transparent">
											<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-star orange"></i>
													热卖产品排行
												</h4>

												<div class="widget-toolbar">
													<a href="#" data-action="collapse">
														<i class="ace-icon fa fa-chevron-up"></i>
													</a>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table table-bordered table-striped">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="ace-icon fa fa-caret-right blue"></i>品名
																</th>

																<th>
																	<i class="ace-icon fa fa-caret-right blue"></i>单价
																</th>

																<th class="hidden-480">
																	<i class="ace-icon fa fa-caret-right blue"></i>销量
																</th>
															</tr>
														</thead>

														<tbody>
															<tr>
																<td>得力7102 升级配方PVA固体胶棒21g 12支/盒</td>

																<td>
																	<small>
																		<s class="red">29.99</s>
																	</small>
																	<b class="green">19.99</b>
																</td>

																<td class="hidden-480">
																	<span class="label label-info arrowed-right arrowed-in">25,208</span>
																</td>
															</tr>

															<tr>
																<td>得力0012 优质高强度订书钉12# 1000枚/盒 10盒装</td>

																<td>
																	<small>
																		<s class="red"></s>
																	</small>
																	<b class="green">16.45</b>
																</td>

																<td class="hidden-480">
																	<span class="label label-success arrowed-in arrowed-in-right">24,000</span>
																</td>
															</tr>

															<tr>
																<td>得力0037 29mm镀镍回形针200枚/筒</td>

																<td>
																	<small>
																		<s class="red"></s>
																	</small>
																	<b class="green">15.00</b>
																</td>

																<td class="hidden-480">
																	<span class="label label-danger arrowed">22,098</span>
																</td>
															</tr>

															<tr>
																<td>得力(deli)0305 经济实惠订书机12＃</td>

																<td>
																	<small>
																		<s class="red">24.99</s>
																	</small>
																	<b class="green">19.95</b>
																</td>

																<td class="hidden-480">
																	<span class="label arrowed">
																		<s>19,100</s>
																	</span>
																</td>
															</tr>

															<tr>
																<td>得力(deli)0305 经济实惠订书机12＃</td>

																<td>
																	<small>
																		<s class="red"></s>
																	</small>
																	<b class="green">12.00</b>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning arrowed arrowed-right">18,028</span>
																</td>
															</tr>
														</tbody>
													</table>
												</div><!-- /.widget-main -->
											</div><!-- /.widget-body -->
										</div><!-- /.widget-box -->
									</div><!-- /.col -->

									<div class="col-sm-7">
										<div class="widget-box transparent">
											<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-signal"></i>
													销售统计
												</h4>

												<div class="widget-toolbar">
													<a href="#" data-action="collapse">
														<i class="ace-icon fa fa-chevron-up"></i>
													</a>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main padding-4">
													<div id="sales-charts"></div>
												</div><!-- /.widget-main -->
											</div><!-- /.widget-body -->
										</div><!-- /.widget-box -->
									</div><!-- /.col -->
								</div><!-- /.row -->

								<div class="hr hr32 hr-dotted"></div>

								<div class="row">
									<div class="col-sm-6">
										<div class="widget-box transparent" id="recent-box">
											<div class="widget-header">
												<h4 class="widget-title lighter smaller">
													<i class="ace-icon fa fa-rss orange"></i>系统相关
												</h4>

												<div class="widget-toolbar no-border">
													<ul class="nav nav-tabs" id="recent-tab">
														<li class="active">
															<a data-toggle="tab" href="#task-tab">更新日志</a>
														</li>

														<li>
															<a data-toggle="tab" href="#member-tab">BUG</a>
														</li>

														<li>
															<a data-toggle="tab" href="#comment-tab">反馈</a>
														</li>
													</ul>
												</div>
											</div>

											<div class="widget-body">
												<div class="widget-main padding-4">
													<div class="tab-content padding-8">
														<div id="task-tab" class="tab-pane active">
															<h4 class="smaller lighter green">
																<i class="ace-icon fa fa-list"></i>
																最近更新如下
															</h4>
															
															<ul id="tasks" class="item-list ui-sortable">
																


																<li class="item-blue clearfix ui-sortable-handle">
																	<label class="inline">
																		<span class="lbl"> 修复了首页点击的BUG</span>
																	</label>
																	<div class="pull-right action-buttons">
																		1天前
																	</div>
																</li>

																<li class="item-grey clearfix ui-sortable-handle">
																	<label class="inline">
																		<span class="lbl"> 添加新的控件支持</span>
																	</label>
																	<div class="pull-right action-buttons">
																		2天前
																	</div>
																</li>

																<li class="item-green clearfix ui-sortable-handle">
																	<label class="inline">

																		<span class="lbl"> 服务器缓存调整完成</span>
																	</label>
																	<div class="pull-right action-buttons">
																		3天前
																	</div>
																</li>

																<li class="item-pink clearfix ui-sortable-handle">
																	<label class="inline">
																		<span class="lbl"> 系统正式上线测试</span>
																	</label>
																	<div class="pull-right action-buttons">
																		5天前
																	</div>
																</li>
															</ul>

															
														</div>

														<div id="member-tab" class="tab-pane">
															<!-- #section:pages/dashboard.members -->
															

															<!-- /section:pages/dashboard.comments -->
														</div>
													</div>
												</div><!-- /.widget-main -->
											</div><!-- /.widget-body -->
										</div><!-- /.widget-box -->
									</div><!-- /.col -->

									<div class="col-sm-6">
										<div class="widget-box">
											<div class="widget-header">
												<h4 class="widget-title lighter smaller">
													<i class="ace-icon fa fa-comment blue"></i>
													其他系统信息
												</h4>
											</div>

											<div class="widget-body">
											
												<div class="widget-main no-padding">
													<!-- #section:pages/dashboard.conversations -->
													<br/>
											<div class="profile-user-info profile-user-info-striped">
												
												<div class="profile-info-row">
												
													<div class="profile-info-name"> 工作目录 </div>

													<div class="profile-info-value">
													<i class="fa fa-folder-open light-orange bigger-110"></i>
														<span>${statics["java.lang.System"].getProperty("user.dir")}</span>
													</div>
												</div>

												<div class="profile-info-row">
													<div class="profile-info-name"> 临时文件存放 </div>

													<div class="profile-info-value">
														<i class="fa fa-folder-open-o light-orange bigger-110"></i>
														<span>${statics["java.lang.System"].getProperty("java.io.tmpdir")}</span>

													</div>
												</div>


											</div>
											<br/>
											
														

													<!-- /section:pages/dashboard.conversations -->
													
													
													
													

												</div><!-- /.widget-main -->
											</div><!-- /.widget-body -->
										</div><!-- /.widget-box -->
									</div><!-- /.col -->
								</div><!-- /.row -->

								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				</div><!-- /.page-content -->
				
				
				</div><!-- /.main-content--add by welson 0728  -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl"><!-- /.add by welson 0728  -->
	</div><!-- /.main-container--add by welson 0728 -->			
	
	
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
		
		
		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				$('.easy-pie-chart.percentage').each(function(){
					var $box = $(this).closest('.infobox');
					var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
					var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
					var size = parseInt($(this).data('size')) || 50;
					$(this).easyPieChart({
						barColor: barColor,
						trackColor: trackColor,
						scaleColor: false,
						lineCap: 'butt',
						lineWidth: parseInt(size/10),
						animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,
						size: size
					});
				})
			
				$('.sparkline').each(function(){
					var $box = $(this).closest('.infobox');
					var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
					$(this).sparkline('html',
									 {
										tagValuesAttribute:'data-values',
										type: 'bar',
										barColor: barColor ,
										chartRangeMin:$(this).data('min') || 0
									 });
				});
			
			
			  //flot chart resize plugin, somehow manipulates default browser resize event to optimize it!
			  //but sometimes it brings up errors with normal resize event handlers
			  $.resize.throttleWindow = false;
			
			  var placeholder = $('#piechart-placeholder').css({'width':'90%' , 'min-height':'150px'});
			  var data = [
				{ label: "打印设备",  data: 38.7, color: "#68BC31"},
				{ label: "复写纸",  data: 24.5, color: "#2091CF"},
				{ label: "电脑周边",  data: 8.2, color: "#AF4E96"},
				{ label: "点钞机",  data: 18.6, color: "#DA5430"},
				{ label: "其他",  data: 10, color: "#FEE074"}
			  ]
			  function drawPieChart(placeholder, data, position) {
			 	  $.plot(placeholder, data, {
					series: {
						pie: {
							show: true,
							tilt:0.8,
							highlight: {
								opacity: 0.25
							},
							stroke: {
								color: '#fff',
								width: 2
							},
							startAngle: 2
						}
					},
					legend: {
						show: true,
						position: position || "ne", 
						labelBoxBorderColor: null,
						margin:[-30,15]
					}
					,
					grid: {
						hoverable: true,
						clickable: true
					}
				 })
			 }
			 drawPieChart(placeholder, data);
			
			 /**
			 we saved the drawing function and the data to redraw with different position later when switching to RTL mode dynamically
			 so that's not needed actually.
			 */
			 placeholder.data('chart', data);
			 placeholder.data('draw', drawPieChart);
			
			
			  //pie chart tooltip example
			  var $tooltip = $("<div class='tooltip top in'><div class='tooltip-inner'></div></div>").hide().appendTo('body');
			  var previousPoint = null;
			
			  placeholder.on('plothover', function (event, pos, item) {
				if(item) {
					if (previousPoint != item.seriesIndex) {
						previousPoint = item.seriesIndex;
						var tip = item.series['label'] + " : " + item.series['percent']+'%';
						$tooltip.show().children(0).text(tip);
					}
					$tooltip.css({top:pos.pageY + 10, left:pos.pageX + 10});
				} else {
					$tooltip.hide();
					previousPoint = null;
				}
				
			 });
			
			
			
			
			
			
				var d1 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.5) {
					d1.push([i, Math.sin(i)]);
				}
			
				var d2 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.5) {
					d2.push([i, Math.cos(i)]);
				}
			
				var d3 = [];
				for (var i = 0; i < Math.PI * 2; i += 0.2) {
					d3.push([i, Math.tan(i)]);
				}
				
			
				var sales_charts = $('#sales-charts').css({'width':'100%' , 'height':'220px'});
				$.plot("#sales-charts", [
					{ label: "纸类", data: d1 },
					{ label: "打印类", data: d2 },
					{ label: "外设类", data: d3 }
				], {
					hoverable: true,
					shadowSize: 0,
					series: {
						lines: { show: true },
						points: { show: true }
					},
					xaxis: {
						tickLength: 0
					},
					yaxis: {
						ticks: 10,
						min: -2,
						max: 2,
						tickDecimals: 3
					},
					grid: {
						backgroundColor: { colors: [ "#fff", "#fff" ] },
						borderWidth: 1,
						borderColor:'#555'
					}
				});
			
			
				$('#recent-box [data-rel="tooltip"]').tooltip({placement: tooltip_placement});
				function tooltip_placement(context, source) {
					var $source = $(source);
					var $parent = $source.closest('.tab-content')
					var off1 = $parent.offset();
					var w1 = $parent.width();
			
					var off2 = $source.offset();
					//var w2 = $source.width();
			
					if( parseInt(off2.left) < parseInt(off1.left) + parseInt(w1 / 2) ) return 'right';
					return 'left';
				}
			
			
				$('.dialogs,.comments').ace_scroll({
					size: 300
			    });
				
				
				//Android's default browser somehow is confused when tapping on label which will lead to dragging the task
				//so disable dragging when clicking on label
				var agent = navigator.userAgent.toLowerCase();
				if("ontouchstart" in document && /applewebkit/.test(agent) && /android/.test(agent))
				  $('#tasks').on('touchstart', function(e){
					var li = $(e.target).closest('#tasks li');
					if(li.length == 0)return;
					var label = li.find('label.inline').get(0);
					if(label == e.target || $.contains(label, e.target)) e.stopImmediatePropagation() ;
				});
			
				$('#tasks').sortable({
					opacity:0.8,
					revert:true,
					forceHelperSize:true,
					placeholder: 'draggable-placeholder',
					forcePlaceholderSize:true,
					tolerance:'pointer',
					stop: function( event, ui ) {
						//just for Chrome!!!! so that dropdowns on items don't appear below other items after being moved
						$(ui.item).css('z-index', 'auto');
					}
					}
				);
				$('#tasks').disableSelection();
				$('#tasks input:checkbox').removeAttr('checked').on('click', function(){
					if(this.checked) $(this).closest('li').addClass('selected');
					else $(this).closest('li').removeClass('selected');
				});
			
			
				//show the dropdowns on top or bottom depending on window height and menu position
				$('#task-tab .dropdown-hover').on('mouseenter', function(e) {
					var offset = $(this).offset();
			
					var $w = $(window)
					if (offset.top > $w.scrollTop() + $w.innerHeight() - 100) 
						$(this).addClass('dropup');
					else $(this).removeClass('dropup');
				});
			
			})
		</script>
		
		
</body>
</html>