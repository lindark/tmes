 <#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>管理中心 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include_adm_top.ftl">
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/list.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet"
	href="${base}/template/admin/assets/css/jquery.gritter.css" />
<script src="${base}/template/admin/js/Main/testindex.js"></script>
<script src="${base}/template/admin/assets/js/jquery.gritter.min.js"></script>
<script src="${base}/template/admin/js/layer/layer.js"></script>
<style type="text/css">
	.ui-datepicker td {
		padding:0px;
	}
</style>
</head>
<script type="text/javascript">
	$(function(){
		var $factory = $("#sl_ft");
		var $workShop = $("#sl_ws");
		var $factoryUnit = $("#sl_ftu");
		var $team = $("#sl_team");
		var $saveButton = $("input.saveButton");
		var working = [];
		if($factory.val()=="0" || $factory.val()=="1"){
			$("#sl_ws").html("<option value='0'>无</option>");
			$("#sl_ftu").html("<option value='0'>无</option>");
			$("#sl_team").html("<option value='0'>无</option>");
		}
		var nowDate = new Date();
		var nowYear = nowDate.getFullYear();
		var nowMonth = nowDate.getMonth()+1;
		var nowDay = nowDate.getDate();
		if(nowMonth.toString().length==1){
			nowMonth = "0"+nowMonth;
		}
		if(nowDay.toString().length==1){
			nowDay = "0"+nowDay;
		}
	    var nowtime = nowYear+"-"+nowMonth+"-"+nowDay;
		$("#productDate").val(nowtime);
		//工厂改变查找车间
		$factory.change(function(){
			if($(this).val()!="1" && $(this).val()!="0"){
				var factoryId = $(this).val();
				$.ajax({
					url:"work_shop!findWorkShopByFactory.action",
					data:{"factoryId":factoryId},
					dataType:"json",
					success:function(data){
						if(data.status=="error"){
							$workShop.html("<option value='0'>无</option>");
							$factoryUnit.html("<option value='0'>无</option>");
							$team.html("<option value='0'>无</option>");
							alert(data.message);
							//$.message(data.message);
						}else{
							if(data.length==0){
								$workShop.html("<option value='0'>无</option>");
								$factoryUnit.html("<option value='0'>无</option>");
								$team.html("<option value='0'>无</option>");
							}else{
								$workShop.html("");
								for(var i=0;i<data.length;i++){
									if(i==0){
										opt = $("<option/>").text(data[i].wsName).attr("value", data[i].wsId).attr("selected","selected");
									}else{
										opt = $("<option/>").text(data[i].wsName).attr("value", data[i].wsId);
									}
									$workShop.append(opt); 
								}
								 //车间改变查找单元
								if($("#sl_ws").val()!="0"){
									findFT($("#sl_ws"));
									}else{
										$factoryUnit.html("<option value='0'>无</option>");
										$team.html("<option value='0'>无</option>");
									}
							}
						}
					},
					error:function(){
						alert("读取数据失败数据");
					}
				});
			}else{
				$workShop.html("<option value='0'>无</option>");
				$factoryUnit.html("<option value='0'>无</option>");
				$team.html("<option value='0'>无</option>");
			}
		
		});
		
		 var findT = function findTeam(){
			 return function($_this){
				 if($_this.val()!="0"){
						var info = $_this.val();
						$.ajax({
							url:"team!findTeamByFactoryUnit.action",
							data:{"info":info},
							dataType:"json",
							success:function(data){
								if(data.status=="error"){
									$team.html("<option value='0'>无</option>");
									alert(data.message);
									//$.message(data.message);
								}else{
									if(data.length==0){
										$team.html("<option value='0'>无</option>");
									}else{
										$team.html("");
										for(var i=0;i<data.length;i++){
											if(i==0){
												opt = $("<option/>").text(data[i].tmName).attr("value", data[i].tmId).attr("selected","selected");
												if(data[i].work=="Y"){
													working.push(true);
												}else{
													working.push(false);
												}
											}else{
												opt = $("<option/>").text(data[i].tmName).attr("value", data[i].tmId);
												if(data[i].work=="Y"){
													working.push(true);
												}else{
													working.push(false);
												}
											}
											$team.append(opt); 
										}
									}
								}
								
							},
							error:function(){
								alert("数据读取失败");
							}
						});
					}else{
						$team.html("<option value='0'>无</option>");
					}
					
			 }
		 }();
		var findFT = function findTactoryUnit(){
			return function($_this){
				if($_this.val()!="0"){
					var workShopId = $_this.val();
					$.ajax({
						url:"factory_unit!findFactoryUnitByWorkShop.action",
						data:{"workShopId":workShopId},
						dataType:"json",
						success:function(data){
							if(data.status=="error"){
								$factoryUnit.html("<option value='0'>无</option>");
								$team.html("<option value='0'>无</option>");
								alert(data.message);
								//$.message(data.message);
							}else{
								if(data.length==0){
									$factoryUnit.html("<option value='0'>无</option>");
									$team.html("<option value='0'>无</option>");
								}else{
									$factoryUnit.html("");
									for(var i=0;i<data.length;i++){
										if(i==0){
											opt = $("<option/>").text(data[i].ftuName).attr("value", data[i].ftuId).attr("selected","selected");
										}else{
											opt = $("<option/>").text(data[i].ftuName).attr("value", data[i].ftuId);
										}
										$factoryUnit.append(opt); 
									}
									
									//单元改变查找班组
									if($("#sl_ftu").val()!="0"){
										findT($("#sl_ftu"));
									}else{
										$team.html("<option value='0'>无</option>");
									}
								}
							}
						},
						error:function(){
							alert("数据读取失败");
						}
					});
					}else{
						$factoryUnit.html("<option value='0'>无</option>");
						$team.html("<option value='0'>无</option>");
					}
			}
		}();
		
		
		//单元改变查找班组
		$factoryUnit.change(function(){
			findT($(this));
		});  
		
		 //车间改变查找单元
		$workShop.change(function(){
			findFT($(this));
		}); 
		 
		$saveButton.click(function(){
			var teamId = $team.val();
			var productDate = $("#productDate").val();
			var shift = $("#sl_sh").val();
				if(productDate!=""){
					// 统一日期格式
					var strDate = /^\d{4}(\-|\/|\.)\d{1,2}\1\d{1,2}$/;
					var beforelength = productDate.length;
					if(beforelength==9){
						var befores = productDate.charAt(beforelength-1);
						if(befores=="0"){
							alert("请输入正确的日期格式(例如:1970-01-01或1907-1-1)")
							return false;
						}
					}
					  //判断日期是否是预期的格式
					  if (!strDate.test(productDate)) {
					    alert("请输入正确的日期格式(例如:1970-01-01或1907-1-1)")
					    return false;
					  }
				}else{
					alert("日期不允许为空");
					 return false;
				}
				$.ajax({
					url:"admin!addTeam.action",
					data:{"teamid":teamId,"productDate":productDate,"shift":shift},
					dataType:"json",
					success:function(data){
						if(teamId!="0"){
							if(data[0].YORN=="Y"){
								var op_ftName = $('#sl_ft option:selected').text();//选中的文本
								var op_wsName = $('#sl_ws option:selected').text();//选中的文本
								var op_ftuName = $('#sl_ftu option:selected').text();//选中的文本
								var op_tmName = $('#sl_team option:selected').text();//选中的文本
								var op_tmId = $('#sl_team option:selected').val();//选中的值
								var op_tmIndex = $("#sl_team").get(0).selectedIndex;//索引
								var op_tmwork = working[op_tmIndex];
								if(op_tmwork){
									$(".working").prepend("<tr><td><input type=\"hidden\" style=\"width:350px;\" class=\"teamId\" name=\"team.id\" value='"+op_tmId+"'/><a href=\"javascript:;\" class=\"matkx\"><b class=\"green\" >"+op_tmName+"</b></a></td><td class=\"hidden-480\">"+op_ftuName+"</td><td class=\"hidden-480\">"+op_wsName+"</td><td class=\"hidden-480\">"+op_ftName+"</td><td class=\"hidden-480\"><a href=\"javascript:;\" class=\"a_delete\">[删除]</a></td></tr>");
									$(".a_delete").bind("click",function(){
										if(confirm("您确定删除此记录？")){
											var $thisA = $(this).parent().parent();
											var url = "admin!deleteTeam.action";
											var teamId =$thisA.find(".teamId").val();
											$.ajax({
												url:url,
												data:{"id":teamId},
												dataType:"json",
												success:function(data){
													if(data.status=="success"){
														$thisA.remove();
													}
													alert(data.message);
												},
												error:function(){
													alert("数据读取失败");
												}
												
											});
										}
									});
								}
							}else if(data[0].YORN=="Y1"){
								alert("保存成功");
							}else{
								$(".working").empty();
								var op_ftName = $('#sl_ft option:selected').text();//选中的文本
								var op_wsName = $('#sl_ws option:selected').text();//选中的文本
								var op_ftuName = $('#sl_ftu option:selected').text();//选中的文本
								var op_tmName = $('#sl_team option:selected').text();//选中的文本
								var op_tmId = $('#sl_team option:selected').val();//选中的值
								var op_tmIndex = $("#sl_team").get(0).selectedIndex;//索引
								var op_tmwork = working[op_tmIndex];
								if(op_tmwork){
									$(".working").prepend("<tr><td><input type=\"hidden\" style=\"width:350px;\" class=\"teamId\" name=\"team.id\" value='"+op_tmId+"'/><a href=\"javascript:;\" class=\"matkx\"><b class=\"green\" >"+op_tmName+"</b></a></td><td class=\"hidden-480\">"+op_ftuName+"</td><td class=\"hidden-480\">"+op_wsName+"</td><td class=\"hidden-480\">"+op_ftName+"</td><td class=\"hidden-480\"><a href=\"javascript:;\" class=\"a_delete\">[删除]</a></td></tr>");
									$(".a_delete").bind("click",function(){
										if(confirm("您确定删除此记录？")){
											var $thisA = $(this).parent().parent();
											var url = "admin!deleteTeam.action";
											var teamId =$thisA.find(".teamId").val();
											$.ajax({
												url:url,
												data:{"id":teamId},
												dataType:"json",
												success:function(data){
													if(data.status=="success"){
														$thisA.remove();
													}
													alert(data.message);
												},
												error:function(){
													alert("数据读取失败");
												}
												
											});
										}
									});
								}
							}
						}else{
							if(data.length>0){
								if(data[0].YORN=="Y"){
									for(var i=0;i<data.length;i++){
										$(".working").prepend("<tr><td><input type=\"hidden\" style=\"width:350px;\" class=\"teamId\" name=\"team.id\" value='"+data[i].tmId+"'/><a href=\"javascript:;\" class=\"matkx\"><b class=\"green\" >"+data[i].tmName+"</b></a></td><td class=\"hidden-480\">"+data[i].ftuName+"</td><td class=\"hidden-480\">"+data[i].wsName+"</td><td class=\"hidden-480\">"+data[i].ftName+"</td><td class=\"hidden-480\"><a href=\"javascript:;\" class=\"a_delete\">[删除]</a></td></tr>");
									}
								}else{
									$(".working").empty();
									for(var i=0;i<data.length;i++){
										$(".working").prepend("<tr><td><input type=\"hidden\" style=\"width:350px;\" class=\"teamId\" name=\"team.id\" value='"+data[i].tmId+"'/><a href=\"javascript:;\" class=\"matkx\"><b class=\"green\" >"+data[i].tmName+"</b></a></td><td class=\"hidden-480\">"+data[i].ftuName+"</td><td class=\"hidden-480\">"+data[i].wsName+"</td><td class=\"hidden-480\">"+data[i].ftName+"</td><td class=\"hidden-480\"><a href=\"javascript:;\" class=\"a_delete\">[删除]</a></td></tr>");
									}
								}
								alert("保存成功");
							}else{
								alert(data.message);
							}
							$(".a_delete").bind("click",function(){
								if(confirm("您确定删除此记录？")){
									var $thisA = $(this).parent().parent();
									var url = "admin!deleteTeam.action";
									var teamId =$thisA.find(".teamId").val();
									$.ajax({
										url:url,
										data:{"id":teamId},
										dataType:"json",
										success:function(data){
											if(data.status=="success"){
												$thisA.remove();
											}
											alert(data.message);
										},
										error:function(){
											alert("数据读取失败");
										}
										
									});
								}
							});
						}
						//$.message(data.message);
					},
					error:function(){
						alert("数据读取失败");
					}
				});
		});
		
		$(".a_delete").bind("click",function(){
			if(confirm("您确定删除此记录？")){
				var $thisA = $(this).parent().parent();
				var url = "admin!deleteTeam.action";
				var teamId =$thisA.find(".teamId").val();
				$.ajax({
					url:url,
					data:{"id":teamId},
					dataType:"json",
					success:function(data){
						if(data.status=="success"){
							$thisA.remove();
						}
						alert(data.message);
					},
					error:function(){
						alert("数据读取失败");
					}
					
				});
			}
		});
		$(".matkx").live("click",function() {
			var teamid = $(this).prev().val();
			window.self.location  = "admin!teamWorkingBill.action?teamid="+teamid;
		});
	});
</script>
<body class="no-skin">

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

			<!-- #section:basics/content.breadcrumbs -->
			<div class="breadcrumbs" id="breadcrumbs">
				<script type="text/javascript">
					try {
						ace.settings.check('breadcrumbs', 'fixed')
					} catch (e) {
					}
				</script>

				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> <a href="#">管理中心</a>
					</li>
					<li class="active">首页</li>
				</ul>
				<!-- /.breadcrumb -->



				<!-- /section:basics/content.searchbox -->
			</div>

			<!-- /section:basics/content.breadcrumbs -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">

							<div class="row">
							<div class="col-xs-12 col-sm-12 widget-container-col">
							<div class="widget-box transparent">
							<div class="widget-header widget-header-flat" style="text-align:center;">
									工厂:
									<select name="" id="sl_ft">
										<#if factoryList?size==0>
											<option value="0">请先维护工厂数据</option>
										<#else>
											<option value="1">请选择</option>
											<#list  (factoryList)! as fl>
											  		<option value="${fl.id}">${fl.factoryName}</option>
											  	</#list>
											<!-- <#if team!=null>
												<#list  (factoryList)! as fl>
											  		<option value="${fl.id}" <#if fl.id==team.factoryUnit.workShop.factory.id>selected</#if>>${fl.factoryName}</option>
											  	</#list>
											  <#else>
											  	<#list  (factoryList)! as fl>
											  		<option value="${fl.id}">${fl.factoryName}</option>
											  	</#list>
										  	</#if> -->
									</#if>
									</select>
									&nbsp;&nbsp;
									车间:
                                   <select name="" id="sl_ws">
                                </select>
                                   &nbsp;&nbsp;
                                   单元:
                                    <select name=""id="sl_ftu">
                                   </select>
                                   &nbsp;&nbsp;
                                   班组:
                                     <select name=""id="sl_team">
                                   </select>
                                   &nbsp;&nbsp;
                                     生产日期:
                                    <input type="text" id="productDate" name="" value="" class="datePicker formText"/>
                                   &nbsp;&nbsp;
                                     班次:
                                     <select name=""id="sl_sh">
                                     	<option value="1" <#if (admin.shift == 1)!> selected</#if>>早</option>
										<option value="2" <#if (admin.shift == 2)!> selected</#if>>白</option>
										<option value="3" <#if (admin.shift == 3)!> selected</#if>>晚</option>
                                   </select>
                                   &nbsp;&nbsp;
                                   <input type="button"  class="saveButton btn btn-white btn-default btn-sm btn-round" value="保存"></button>
							</div>
							</div>
							</div>
							</div>
							
						   <div>&nbsp</div>

							
							<div class="row">
							<div class="col-xs-12 col-sm-12 widget-container-col">
									<div class="widget-box transparent">
										<div class="widget-header widget-header-flat">
											<h4 class="widget-title lighter">
												<i class="ace-icon fa fa-star orange"></i> 正在工作中的班组
											</h4>

											<div class="widget-toolbar">
												<a href="#" data-action="collapse"> <i
													class="ace-icon fa fa-chevron-up"></i> </a>
											</div>
										</div>

										<div class="widget-body">
											<div class="widget-main no-padding" style="height:250px;overflow:auto;">
												<table class="table table-bordered table-striped">
													<thead class="thin-border-bottom">
														<tr>
															<th><i class="ace-icon fa fa-caret-right blue"></i>班组名称
															</th>
															<th><i class="ace-icon fa fa-caret-right blue"></i>所在单元
															</th>

															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>车间名称</th>
															<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>工厂名称</th>
																<th class="hidden-480"><i
																class="ace-icon fa fa-caret-right blue"></i>操作</th>
														</tr>
													</thead>

													<tbody class="working">
														<#list (admin.teamSet)! as list>
														<!-- <#if list.isWork=="Y"> -->
														<tr>
															<td>
															<input type="hidden" style="width:350px;" class="teamId" name="team.id" value="${(list.id)! }"/>
															<a href="javascript:;" class="matkx">
															       <b class="green" >${(list.teamName)!}</b></a>
															</td>								
                                                            <td class="hidden-480">${(list.factoryUnit.factoryUnitName)!}</td>
															<td class="hidden-480">${(list.factoryUnit.workShop.workShopName)!}</td>
															<td class="hidden-480">${(list.factoryUnit.workShop.factory.factoryName)!}</td>
															<td class="hidden-480"><a href="javascript:;" class="a_delete">[删除]</a></td>
														</tr>
														<!-- </#if> -->
														</#list>

													</tbody>
												</table>
											</div>
											<!-- /.widget-main -->
										</div>
										<!-- /.widget-body -->
									</div>
									<!-- /.widget-box -->
								</div>
							</div>														
			
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->

					<!-- PAGE CONTENT ENDS -->
				</div>
				<!-- /.col -->
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
		<!-- /.add by welson 0728  -->
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">

	<script type="text/javascript">
	//$(".matkx").live("click",function() {
		//var teamid = $(this).prev().val();
		/* var index = layer.open({
			type : 2,
			skin : 'layui-layer-lan',
			title : "<font size='5px'>当前班组正在生产的随工单</font>",
			fix : false,
			shadeClose : false,
			maxmin : true,
			scrollbar : false,
			btn:['确定'],
			area : [ '800px', '400px' ],//弹出框的高度，宽度
			content : "admin!teamWorkingBill.action?teamid="+teamid
		});
		layer.full(index);//弹出既全屏 */
		//window.self.location  = "admin!teamWorkingBill.action?teamid="+teamid;
	//});
	</script>

</body>
</html>