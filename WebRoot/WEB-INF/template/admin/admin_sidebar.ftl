
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
			<li>
				<a href="admin!index.action" >
					<i class="menu-icon fa fa-tachometer"></i>
					<span class="menu-text"> 首页 </span>
				</a>

				<b class="arrow"></b>
			</li>
			<li>
				<a href="admin!index1.action" >
					<i class="menu-icon fa fa-tachometer"></i>
					<span class="menu-text"> 生产首页 </span>
				</a>

				<b class="arrow"></b>
			</li>
			<li>
				<a href="#" >
					<i class="menu-icon fa fa-gavel"></i>
					<span class="menu-text"> 质检首页 </span>
				</a>

				<b class="arrow"></b>
			</li>
			<li>
				<a href="#" >
					<i class="menu-icon glyphicon glyphicon-user"></i>
					<span class="menu-text"> 管理首页 </span>
				</a>

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
						<a href="pick!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							领退料
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					 <li class="">
						<a href="dump!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							转储确认
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					<li class="">
						<a href="handover!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							交接
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					
					<li class="">
						<a href="pro!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							报废
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					
					<li class="">
						<a href="pro!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							返工
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					
					
					<li class="">
						<a href="repair!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							返修
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					
					<li class="">
						<a href="repairin!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							返修收货
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					
					<li class="">
						<a href="pro!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							报工
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					
					<li class="">

						<a href="enteringware_House!list.action" >


							<i class="menu-icon fa fa-caret-right"></i>
							入库
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
					</li>
					
					<li class="">
						<a href="pro!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							纸箱收货
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>	
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
						<a href="pros!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							抽检
						</a>

						<b class="arrow"></b>
					</li>
					
					<li class="">
						<a href="pros!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							巡检
						</a>

						<b class="arrow"></b>
					</li>
					
					
					<li class="">
						<a href="pros!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							半成品巡检
						</a>

						<b class="arrow"></b>
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
					<li class="">
						<a href="abnormal!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							异常
						</a>

						<b class="arrow"></b>
					</li>

					<li class="">
						<a href="quality!list.action" >
							<i class="menu-icon"></i>
							质量问题单
						</a>

						<b class="arrow"></b>
					</li>
					
					<li class="">
						<a href="model!list.action" >
							<i class="menu-icon"></i>
							工模维修单
						</a>

						<b class="arrow"></b>
					</li>
					
                    
					<li class="">
						<a href="craft!list.action" >
							<i class="menu-icon"></i>
							工艺维修单
						</a>

						<b class="arrow"></b>
					</li>

                    
					<li class="">
						<a href="device!list.action" >
							<i class="menu-icon"></i>
							设备维修单
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
							部门设置
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
									权限资源对象
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
						<a href="product_group!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							产品组管理
						</a>

						<b class="arrow"></b>
					</li>
					
					<li class="">
						<a href="products!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							产品管理
						</a>

						<b class="arrow"></b>
					</li>
					
					<li class="">
						<a href="material!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							产品Bom管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					
								
					
					<li class="">

						<a href="work_shop!list.action" >

							<i class="menu-icon fa fa-caret-right"></i>
							车间管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					
					<li class="">
						<a href="permission!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							权限管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					
					<li class="">
						<a href="post!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							岗位管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
	
	
	               <li class="">
						<a href="factory_unit!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							单元管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					
					
					<li class="">
						<a href="callreason!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							呼叫原因管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
						
					<li class="">
						<a href="department!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							部门管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>						
					</li>	
					
					<li class="">
						<a href="locationonside!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							线边仓管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>						
					</li>	
					
						
					</li>
					
					<li class="">
						<a href="process!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							工序管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					<li class="">
						<a href="factory!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
						   工厂管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					
					
					
					<li class="">
						<a href="working_bill!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							SAP生产计划同步管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
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
						<a href="team!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							班组管理
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
						<a href="cause!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							缺陷代码管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					
					<li class="">
						<a href="fault_reason!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							故障原因管理
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
						<a href="handlemeans_results!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							故障处理方法与结果管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
					<li class="">
						<a href="longtime_preventstep!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							长期预防措施管理
							<b class="arrow fa"></b>
						</a>

						<b class="arrow"></b>

						
					</li>
					
						<li class="">
						<a href="receipt_reason!list.action" >
							<i class="menu-icon fa fa-caret-right"></i>
							单据原因管理
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