
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta http-equiv="pragma" content="no-cache"> 

<meta http-equiv="cache-control" content="no-cache"> 

<meta http-equiv="expires" content="0">   
<title>管理中心 - Powered By 九翊软件</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />

<meta name="viewport" content="width=device-width, user-scalable=no">

<!-- bootstrap & fontawesome -->
<link rel="stylesheet" href="/template/admin/assets/css/bootstrap.min.css" />
<link rel="stylesheet" href="/template/admin/assets/css/font-awesome.min.css" />
<!-- page specific plugin styles -->
<!-- text fonts -->
<link rel="stylesheet" href="/template/admin/assets/css/ace-fonts.css" />
<!-- ace styles -->
<link rel="stylesheet" href="/template/admin/assets/css/ace.min.css" id="main-ace-style" />
<!--[if lte IE 9]>
	<link rel="stylesheet" href="/template/admin/assets/css/ace-part2.min.css" />
<![endif]-->
<link rel="stylesheet" href="/template/admin/assets/css/ace-skins.min.css" />
<link rel="stylesheet" href="/template/admin/assets/css/ace-rtl.min.css" />

<!--[if lte IE 9]>
  <link rel="stylesheet" href="/template/admin/assets/css/ace-ie.min.css" />
<![endif]-->

<!-- inline styles related to this page -->
<!-- ace settings handler -->
<script src="/template/admin/assets/js/ace-extra.min.js"></script>
<!-- HTML5shiv and Respond.js for IE8 to support HTML5 elements and media queries -->

<!--[if lte IE 8]>
<script src="/template/admin/assets/js/html5shiv.min.js"></script>
<script src="/template/admin/assets/js/respond.min.js"></script>
<![endif]--></head>
<body class="no-skin">


<!-- #section:basics/navbar.layout -->
		<div id="navbar" class="navbar navbar-default">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<!-- #section:basics/sidebar.mobile.toggle -->
				<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler">
					<span class="sr-only">Toggle sidebar</span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>

					<span class="icon-bar"></span>
				</button>

				<!-- /section:basics/sidebar.mobile.toggle -->
				<div class="navbar-header pull-left">
					<!-- #section:basics/navbar.layout.brand -->
					<a href="#" class="navbar-brand">
						<small>
							<i class="fa fa-cloud"></i>
							九翊软件
						</small>
					</a>

					<!-- /section:basics/navbar.layout.brand -->

					<!-- #section:basics/navbar.toggle -->

					<!-- /section:basics/navbar.toggle -->
				</div>

				<!-- #section:basics/navbar.dropdown -->
				<div class="navbar-buttons navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						

						<li class="green">
							<a class="dropdown-toggle" href="message!inbox.action" target="mainFrame">
								<i class="ace-icon fa fa-envelope icon-animated-vertical"></i>
								<span class="badge badge-success"></span>
							</a>
						</li>

						


						<!-- #section:basics/navbar.user_menu -->
						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="/template/admin/assets/avatars/user.jpg" />
								<span class="user-info">
									<small>您好,欢迎您!</small>
									
								</span>

								<i class="ace-icon fa"></i>
							</a>
							
						</li>

						<!-- /section:basics/navbar.user_menu -->
					</ul>
				</div>

				<!-- /section:basics/navbar.dropdown -->
			</div><!-- /.navbar-container -->
		</div>

		<!-- /section:basics/navbar.layout -->
		
		
<div class="main-container" id="main-container">
	<script type="text/javascript">
		try{ace.settings.check('main-container' , 'fixed')}catch(e){}
	</script>
	

	
	<!-- #section:basics/sidebar -->
	<div id="sidebar" class="sidebar                  responsive">
		<script type="text/javascript">
			try{ace.settings.check('sidebar' , 'fixed')}catch(e){}
		</script>

		<div class="sidebar-shortcuts" id="sidebar-shortcuts">
			<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
				<a href="admin!index.action"  class="btn btn-success">
					<i class="ace-icon fa fa-signal"></i>
				</a>

				<a href="product!add.action"  class="btn btn-info">
					<i class="ace-icon fa fa-pencil"></i>
					
				</a>

				<!-- #section:basics/sidebar.layout.shortcuts -->
				<a href="member!list.action"  class="btn btn-warning">
					<i class="ace-icon fa fa-users"></i>
				</a>

				<a href="system_config!edit.action"  class="btn btn-danger">
					<i class="ace-icon fa fa-cogs"></i>
				</a>

				<!-- /section:basics/sidebar.layout.shortcuts -->
			</div>

			<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
				<span class="btn btn-success"></span>

				<span class="btn btn-info"></span>

				<span class="btn btn-warning"></span>

				<span class="btn btn-danger"></span>
			</div>
		</div><!-- /.sidebar-shortcuts -->

		<ul class="nav nav-list">
		<!-- <li>
				<a href="admin!index.action" >
					<i class="menu-icon fa fa-tachometer"></i>
					<span class="menu-text"> 首页 </span>
				</a>

				<b class="arrow"></b>
			</li> -->	
			<li>
				<a href="admin!index.action" >
					<i class="menu-icon fa fa-tachometer"></i>
					<span class="menu-text"> 生产首页 </span>
				</a>

				<b class="arrow"></b>
			</li>
			<!-- 
			<li>
			<a href="admin!index2.action" >
					<i class="menu-icon fa fa-gavel"></i>
					<span class="menu-text"> 质检首页 </span>
				</a>

				<b class="arrow"></b>
			</li>
			 -->
		<!--<li>
				<a href="#" >
					<i class="menu-icon glyphicon glyphicon-user"></i>
					<span class="menu-text"> 管理首页 </span>
				</a>  -->	

				<b class="arrow"></b>
			</li>
			
			<!-- 
			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-desktop"></i>
					<span class="menu-text"> 产品管理 </span>

					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>

							产品分类
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="product_category!add.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									添加产品分类
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="product_category!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									产品分类列表
								</a>

								<b class="arrow"></b>
							</li>

							
						</ul>
					</li>

					


					<li class="">
						<a href="product!add.action"  >
							<i class="menu-icon fa fa-caret-right"></i>
							添加产品
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="product!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							产品列表
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="product_type!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							产品类型管理
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="product_attribute!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							产品属性管理
						</a>

						<b class="arrow"></b>
					</li>

					


					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>

							品牌管理
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="brand!list.action" >
									<i class="menu-icon fa fa-leaf green"></i>
									品牌列表
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="brand!add.action" >
									<i class="menu-icon fa fa-pencil orange"></i>

									添加品牌
									<b class="arrow fa"></b>
								</a>

								<b class="arrow"></b>

								
							</li>
						</ul>
					</li>
				</ul>
			</li>
 -->
 	<!-- 
			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-users"></i>
					<span class="menu-text"> 会员管理 </span>

					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<li class="">
						<a href="member!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							会员列表
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="member_rank!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							会员等级
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="member_attribute!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							会员扩展字段
						</a>

						<b class="arrow"></b>
					</li>

					
				</ul>
			</li>
 -->
 <!-- 
			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-book"></i>
					<span class="menu-text"> 页面及缓存 </span>

					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>
							导航及内容
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="navigation!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									导航管理
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="article!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									文章管理
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="article_category!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									文章分类
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="friend_link!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									友情链接
								</a>

								<b class="arrow"></b>
							</li>


							
						</ul>
					</li>

					


					<li class="">
						<a href="cache!flush.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							缓存更新
						</a>

						<b class="arrow"></b>
					</li>

					

					<li class="">
						<a href="build_html!articleInput.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							重建文章页面
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="build_html!productInput.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							重建产品展示页
						</a>

						<b class="arrow"></b>
					</li>

					


					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>

							模板管理
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="template_dynamic!list.action" >
									<i class="menu-icon"></i>
									动态模板管理
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="template_html!list.action" >
									<i class="menu-icon"></i>

									静态模板管理
									<b class="arrow fa"></b>
								</a>

								<b class="arrow"></b>

								
							</li>
							
							<li class="">
								<a href="template_mail!list.action" >
									<i class="menu-icon"></i>

									邮件模板管理
									<b class="arrow fa"></b>
								</a>

								<b class="arrow"></b>

								
							</li>
							
						</ul>
					</li>
				</ul>
			</li>
		 -->	
		
		<!-- 
		    <li class="">

				<a href="build_html!allInput.action" >
							<i class="menu-icon fa fa-gavel"></i>
							<span class="menu-text">
						一键内容更新
						<span class="badge badge-transparent tooltip-error" title="" data-original-title="更新后会立即呈现给用户">
							<i class="ace-icon fa fa-exclamation-triangle red bigger-130"></i>
						</span>

					</span>
					
						</a>

						<b class="arrow"></b>
		    </li>
		 -->
		 <!-- 
			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-user"></i>
					<span class="menu-text"> 操作员及权限 </span>

					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					

					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>

							日志管理
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="log!list.action" >
									<i class="menu-icon"></i>
									日志列表
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="log_config!list.action" >
									<i class="menu-icon"></i>

									日志设置
									<b class="arrow fa"></b>
								</a>

								<b class="arrow"></b>

								
							</li>
							
							
							
						</ul>
					</li>
					
					
						
					
					
				</ul>
			</li>
			 -->
			<!-- 
			<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-envelope-o"></i>

							<span class="menu-text">
						站内消息

						
					</span>
					
					
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="message!inbox.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									我收到的消息
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="message!outbox.action" >
									<i class="menu-icon fa fa-caret-right"></i>

									我发出的消息
									<b class="arrow fa"></b>
								</a>

								<b class="arrow"></b>

								
							</li>
							
							
							
						</ul>
					</li>
			
			 -->

			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-flag "></i>
						生产管理
					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<li class="">
						<a href="admin!product.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							生产日期/班次绑定
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>
							生产记录
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									工序交接记录
								</a>

								<b class="arrow"></b>
							</li>
							
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									领退料记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									报工记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									返工记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									返修记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									返修收货记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									报废记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									入库记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									转储确认记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									纸箱收货记录
								</a>

								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					
					
				 </ul>
		     </li>

			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-key "></i>
						质检管理
					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
				
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>
							质检记录
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									成品抽检记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									成品巡检记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									半成品巡检记录
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="#" >
									<i class="menu-icon fa fa-caret-right"></i>
									返工记录
								</a>

								<b class="arrow"></b>
							</li>
						</ul>
					</li>
					
			</ul>
		</li>
		
		<li class="#">
				<a href="" class="dropdown-toggle">
					<i class="menu-icon fa fa-video-camera"></i>
					<span class="menu-text"> 异常管理 </span>

					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<!--  <li class="">
						<a href="abnormal!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							异常
						</a>

						<b class="arrow"></b>
					</li>-->

					<li class="">
						<a href="quality!list.action" >
							<i class="menu-icon"></i>
							质量问题单记录
						</a>

						<b class="arrow"></b>
					</li>
					
					<li class="">
						<a href="model!list.action" >
							<i class="menu-icon"></i>
							工模维修单记录
						</a>

						<b class="arrow"></b>
					</li>
					
                    
					<li class="">
						<a href="craft!list.action" >
							<i class="menu-icon"></i>
							工艺维修单记录
						</a>

						<b class="arrow"></b>
					</li>

                    
					<li class="">
						<a href="device!list.action" >
							<i class="menu-icon"></i>
							设备维修单记录
						</a>

						<b class="arrow"></b>
					</li>
					

				</ul>
			</li>

			<li class="#">
				<a href="" class="dropdown-toggle">
					<i class="menu-icon fa fa-bar-chart-o"></i>
					<span class="menu-text"> 报表管理 </span>

					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<li class="">
						<a href="working_inout!list.action" >
							<i class="menu-icon"></i>
							投入产出报表
						</a>

						<b class="arrow"></b>
					</li>
				</ul>
			</li>

			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-cog"></i>
					<span class="menu-text"> 系统管理 </span>

					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<li class="">
						<a href="system_config!edit.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							系统设置
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="payment_config!list.action" >
							<i class="menu-icon"></i>
							支付设置
						</a>

						<b class="arrow"></b>
					</li>
					
					<li class="">
						<a href="department!list.action" >
							<i class="menu-icon"></i>
							用户设置
						</a>

						<b class="arrow"></b>
					</li>
					<li class="">
						<a href="post!list.action" >
							<i class="menu-icon"></i>
							岗位管理
						</a>

						<b class="arrow"></b>
					</li>
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>
							权限管理
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="role!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									角色管理
								</a>

								<b class="arrow"></b>
							</li>

							<li class="">
								<a href="resource!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									访问路径授权
								</a>

								<b class="arrow"></b>
							</li>
							<!-- 
							<li class="">
								<a href="credit_access!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									刷卡权限管理
								</a>

								<b class="arrow"></b>
							</li>
							 -->
							<li class="">
								<a href="access_object!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									权限对象
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="access_resource!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									权限资源设置
								</a>

								<b class="arrow"></b>
							</li>

						</ul>
					</li>


					

				</ul>
			</li>

			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-tag"></i>
						开发工具
					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<li class="">
						<a href="dict!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							数据字典管理
						</a>

						<b class="arrow"></b>
					</li>
					
					<li class="">
						<a href="area!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							常用地区
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					<!--
					<li class="">
						<a href="#">
							<i class="menu-icon fa fa-caret-right"></i>
							定时同步管理
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="#l">
							<i class="menu-icon fa fa-caret-right"></i>
							SAP RFC函数配置
						</a>

						<b class="arrow"></b>
					</li>
					
					-->

				</ul>
			</li>
			
			
			
		
		     
		     
			<li class="">
				<a href="#" class="dropdown-toggle">
					<i class="menu-icon fa fa-coffee "></i>
						基础信息
					<b class="arrow fa fa-angle-down"></b>
				</a>

				<b class="arrow"></b>

				<ul class="submenu">
					<li class="">
						<a href="working_bill!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							随工单同步管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
				
					<li class="">
						<a href="material!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							物料管理
						</a>

						<b class="arrow"></b>
					</li>
					<!-- 
					<li class="">
						<a href="products!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							产品管理
						</a>

						<b class="arrow"></b>
					</li>
					 -->
					<li class="">
						<a href="bom!show.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							订单Bom管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					<li class="">
						<a href="process!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							工序代码管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					<li class="">
						<a href="process_route!show.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							订单工艺管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>
							组织管理
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="factory!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									 工厂管理
								</a>

								<b class="arrow"></b>
							</li>
							
							<li class="">
								<a href="work_shop!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									车间管理
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="factory_unit!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									单元管理
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="team!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									班组管理
								</a>

								<b class="arrow"></b>
							</li>
							<!-- 
							<li class="">
								<a href="locationonside!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									库存地点管理
								</a>

								<b class="arrow"></b>
							</li>
							 -->
						</ul>
					</li>

					<li class="">
						<a href="#" class="dropdown-toggle">
							<i class="menu-icon fa fa-caret-right"></i>
							代码管理
							<b class="arrow fa fa-angle-down"></b>
						</a>

						<b class="arrow"></b>

						<ul class="submenu">
							<li class="">
								<a href="callreason!list.action" >
									<i class="menu-icon fa fa-caret-right"></i>
									 呼叫原因管理
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="cause!list.action">
									<i class="menu-icon fa fa-caret-right"></i>
									缺陷代码管理
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="fault_reason!list.action">
									<i class="menu-icon fa fa-caret-right"></i>
									故障原因管理
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="handlemeans_results!list.action">
									<i class="menu-icon fa fa-caret-right"></i>
									处理结果代码
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="longtime_preventstep!list.action">
									<i class="menu-icon fa fa-caret-right"></i>
									预防措施
								</a>

								<b class="arrow"></b>
							</li>
							<li class="">
								<a href="receipt_reason!list.action">
									<i class="menu-icon fa fa-caret-right"></i>
									单据原因管理
								</a>

								<b class="arrow"></b>
							</li>
						
						</ul>
					</li>
					
					
					<li class="">
						<a href="unit_conversion!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							计量单位转换
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					<li class="">
						<a href="card_management!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							刷卡设备管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
						<li class="">
						<a href="equipments!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							设备管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					<li class="">
						<a href="scrap_out!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							报废后产出对照表
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					<!--
					<li class="">
						<a href="#">
							<i class="menu-icon fa fa-caret-right"></i>
							定时同步管理
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="#l">
							<i class="menu-icon fa fa-caret-right"></i>
							SAP RFC函数配置
						</a>

						<b class="arrow"></b>
					</li>
					
					-->

				</ul>
			</li>
		</ul><!-- /.nav-list -->

		<!-- #section:basics/sidebar.layout.minimize -->
		<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
			<i class="ace-icon fa fa-angle-double-left" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
		</div>

		<!-- /section:basics/sidebar.layout.minimize -->
		<script type="text/javascript">
			try{ace.settings.check('sidebar' , 'collapsed')}catch(e){}
		</script>
	</div>

	<!-- /section:basics/sidebar -->
	

	<div class="main-content">

<!-- #section:settings.box -->
					<div class="ace-settings-container" id="ace-settings-container">
						<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
							<i class="ace-icon fa fa-cog bigger-150"></i>
						</div>

						<div class="ace-settings-box clearfix" id="ace-settings-box">
							<div class="pull-left width-50">
								<!-- #section:settings.skins -->
								<div class="ace-settings-item">
									<div class="pull-left">
										<select id="skin-colorpicker" class="hide">
											<option data-skin="no-skin" value="#438EB9">#438EB9</option>
											<option data-skin="skin-1" value="#222A2D">#222A2D</option>
											<option data-skin="skin-2" value="#C6487E">#C6487E</option>
											<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
										</select>
									</div>
									<span>&nbsp; 修改皮肤</span>
								</div>

								<!-- /section:settings.skins -->

								


								<!-- /section:settings.container -->
							</div><!-- /.pull-left -->

							<div class="pull-left width-50">
								<!-- #section:basics/sidebar.options -->
								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" />
									<label class="lbl" for="ace-settings-hover">滑动菜单</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" />
									<label class="lbl" for="ace-settings-compact">图标菜单</label>
								</div>

								<div class="ace-settings-item">
									<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" />
									<label class="lbl" for="ace-settings-highlight"> 活动菜单项风格</label>
								</div>

								<!-- /section:basics/sidebar.options -->
							</div><!-- /.pull-left -->
						</div><!-- /.ace-settings-box -->
					</div><!-- /.ace-settings-container -->	
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
							
						</div><!-- /.page-header -->

						<div class="row">
							<div class="col-xs-12">
								<!-- PAGE CONTENT BEGINS -->
								
								<!-- #section:pages/error -->
								<div class="error-container">
									<div class="well">
										<h1 class="grey lighter smaller">
											<span class="blue bigger-125">
												<i class="ace-icon fa fa-sitemap"></i>
												无法访问
											</span>
											您可能没有访问该页面的权限！
										</h1>

										<hr />
										<h3 class="lighter smaller">亲，我们正在修复该异常~</h3>

										<div>
											<form class="form-search">
												<span class="input-icon align-middle">
													<i class="ace-icon fa fa-search"></i>

													<input type="text" class="search-query" placeholder="你可以搜索下..." />
												</span>
												<button class="btn btn-sm" type="button">搜素</button>
											</form>

											<div class="space"></div>
											<h4 class="smaller">可以尝试如下操作:</h4>

											<ul class="list-unstyled spaced inline bigger-110 margin-15">
												<li>
													<i class="ace-icon fa fa-hand-o-right blue"></i>
													检查下您输入的URL是否正确
												</li>

												<li>
													<i class="ace-icon fa fa-hand-o-right blue"></i>
													查看帮助文档
												</li>

												<li>
													<i class="ace-icon fa fa-hand-o-right blue"></i>
													联系管理员
												</li>
											</ul>
										</div>

										<hr />
										<div class="space"></div>

										<div class="center">
											<a href="javascript:history.go(-1)" class="btn btn-grey">
												<i class="ace-icon fa fa-arrow-left"></i>
												返 回
											</a>

											<a href="/" class="btn btn-primary">
												<i class="ace-icon fa fa-tachometer"></i>
												首 页
											</a>
										</div>
									</div>
								</div>

								<!-- /section:pages/error -->
								<!-- PAGE CONTENT ENDS -->
							</div><!-- /.col -->
						</div><!-- /.row -->
					</div><!-- /.page-content-area -->
				</div><!-- /.page-content -->
				
				
				</div><!-- /.main-content--add by welson 0728  -->
		<div class="footer">
	<div class="footer-inner">
		<!-- #section:basics/footer -->
		<div class="footer-content">
			<span>
				
				版权所有 &copy; 2014-2020 九翊软件 技术支持 <span class="blue bolder">Think-way</span> 九翊软件
			</span>

		</div>

		<!-- /section:basics/footer -->
	</div>
</div>

<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
	<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
</a><!-- /.add by welson 0728  -->
	</div><!-- /.main-container--add by welson 0728 -->			
	
	

		<!-- basic scripts -->
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='/template/admin/assets/js/jquery.min.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='/template/admin/assets/js/jquery1x.min.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/template/admin/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/template/admin/assets/js/bootstrap.min.js"></script>
		<!-- page specific plugin scripts -->
		<!--[if lte IE 8]>
		  <script src="/template/admin/assets/js/excanvas.min.js"></script>
		<![endif]-->
		<script src="/template/admin/assets/js/jquery-ui.custom.min.js"></script>
		<script src="/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/template/admin/assets/js/jquery.easypiechart.min.js"></script>
		<script src="/template/admin/assets/js/jquery.sparkline.min.js"></script>
		<script src="/template/admin/assets/js/flot/jquery.flot.min.js"></script>
		<script src="/template/admin/assets/js/flot/jquery.flot.pie.min.js"></script>
		<script src="/template/admin/assets/js/flot/jquery.flot.resize.min.js"></script>
		<!-- ace scripts -->
		<script src="/template/admin/assets/js/ace-elements.min.js"></script>
		<script src="/template/admin/assets/js/ace.min.js"></script>
		<!-- the following scripts are used in demo only for onpage help and you don't need them -->
		<link rel="stylesheet" href="/template/admin/assets/css/ace.onpage-help.css" />
		<link rel="stylesheet" href="/template/admin/docs/assets/js/themes/sunburst.css" />
		<script type="text/javascript"> ace.vars['base'] = '/template/admin/'; </script>
		<script src="/template/admin/assets/js/ace/elements.onpage-help.js"></script>
		<script src="/template/admin/assets/js/ace/ace.onpage-help.js"></script>
		<script src="/template/admin/docs/assets/js/rainbow.js"></script>
		<script src="/template/admin/docs/assets/js/language/generic.js"></script>
		<script src="/template/admin/docs/assets/js/language/html.js"></script>
		<script src="/template/admin/docs/assets/js/language/css.js"></script>
		<script src="/template/admin/docs/assets/js/language/javascript.js"></script>
		
		

		<script type="text/javascript" src="/template/common/js/jquery.cookie.js"></script>
		
	<script type="text/javascript">
	    //open Menu
		jQuery(function($) {
			var path = document.location.pathname;
			var actionName = path.replace(/\/admin\//, '');
			var str = actionName.substring(actionName.indexOf("!") + 1,actionName.indexOf("."));
			var flag = urleach(actionName);
			if(flag==false)
				actionName = actionName.replace(str,"list");
			var aObj = jQuery(".nav-list li a[href='"+actionName+"']");
			if(aObj.parent().is("li")){
				aObj.parent().addClass("active");
			}
			liOpen(aObj);
			
		});
		/**
		*用地址栏在左边导航栏中寻找，找到 return true, 未找到  return false;
		*/
		function urleach(actionName){
			var flag = false;
			jQuery(".nav-list li").each(function(){
				var sVal = jQuery(this).find("a").attr("href");
				sVal = sVal.replace(/\/admin\//, '');
				if(actionName == sVal)
					flag = true;
			})
			return flag;
		}
		
		function liOpen(xObj){
			if(xObj.length>0){
			    if(xObj.is("li")&&xObj.hasClass("hsub")&&!xObj.hasClass("open")){
			       xObj.addClass("open hsub");
			       xObj.find("ul").eq(0).removeClass("nav-hide");
			       xObj.find("ul").eq(0).addClass("submenu nav-show");
			       xObj.find("ul").eq(0).css("display","block");
			       xObj.find("ul").eq(0).show();
			       //递归展开上级菜单
			       ulOpen(xObj.parent());
			    }else{
			       liOpen(xObj.parent());//递归触发上级对象点击事件
			    }
		    }
		    
		}
		
		function ulOpen(uxObj){
		  if(uxObj.length>0){
		    if(uxObj.is("ul")){
		      	uxObj.removeClass("nav-hide");
			    uxObj.addClass("submenu nav-show");
			    uxObj.css("display","block");
			    uxObj.show();
		    }else{
		      ulOpen(uxObj.parent());
		    }
		  }
		}
  </script>
		
		
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

