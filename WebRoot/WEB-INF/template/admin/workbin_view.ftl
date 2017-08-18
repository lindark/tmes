<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<title>管理中心</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/common/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browserValidate.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/layer/layer.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/admin_inputry.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/jquery.cxselect-1.3.7/js/jquery.cxselect.min.js"></script>
		
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/workbin_input.js"></script>
<style type="text/css">
	.mymust{color: red;font-size: 10px;}
	.class_label_xfuname{width:200px;line-height: 30px;border:1px solid;border-color: #d5d5d5;}
	.xspan{font-family: 微软雅黑;font-size: 10px;color:red;}
	.sub_style{text-align: center;}
</style>
<#include "/WEB-INF/template/common/include_adm_top.ftl">
</head>
<body class="no-skin list">
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
						href="admin!index.action">管理中心</a><!-- <button type="button" id="btn11">111111111111</button> --></li>
					<li class="active">库存 </li>
				</ul>
				<!-- /.breadcrumb -->
			</div>


			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- PAGE CONTENT BEGINS -->
							<form class="form-horizontal validate" id="searchform1" name="searchform1"  method="post"
								action="workbin!add.action" role="form">
								<div class="operateBar">
									<div class="profile-user-info profile-user-info-striped">
								  			<div class="profile-info-row">
									<table id="tb_cartonson" class="table table-striped table-bordered table-hover">
													<tr>
														<th class="tabth">工厂</th>
														<th class="tabth">交货单号</th>
														<th class="tabth">发货日期</th>
														<th class="tabth">接收仓库</th>
														<th class="tabth">接收仓库描述</th>
														<th class="tabth">料箱编码</th>
														<th class="tabth">料箱描述</th>
														<th class="tabth">料箱数量</th>
													</tr>
									    				<#assign  num=0 />
														<#list list_cs as list>
															<tr>
																	<td>${(cslist.werks)! }</td>
																	<td>${(cslist.bktxt)! }</td>
																	<td>${(cslist.shipmentsDate)! }</td>
																	<td>${(cslist.jslgort)! }</td>
																	<td>${(cslist.matnr)! }</td>
																	<td>${(cslist.matnrdesc)! }</td>
																	<td>${(cslist.wscount)! }</td>
																</tr>
															<#assign num=num+1/>
														</#list>
												</table>
										</div>
										</div>
								</div>
							</form>
							<br/>
							<div class="row buttons col-md-8 col-sm-4 sub_style">
								<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type="button" />
									<i class="ace-icon fa fa-home"></i>
									返回
								</button>
							</div>
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				<!-- PAGE CONTENT ENDS -->

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
<script type ="text/javascript">
   $(function(){
	  // var $excelReport = $("#excelReport");
	   var $searchform = $("#searchButton");
	   /* $excelReport.click(function(){
		   $searchform.attr("action","pick!excelexport.action");
		   $searchform.submit();
	   });	  */ 
	   $searchform.click(function(){
		   var info = $("#info").val();
		    var desp = $("#desp").val();
		    var infoId = $("#infoId").val();
		    var position = $("#position").val();
		    var infoName = $("#infoName").text();
		   window.self.location="locationonside!getStockList.action?info="+info+"&desp="+desp+"&infoId="+infoId+"&position="+position+"&infoName="+infoName;
	   });
	   
	   
   })
 
</script>
<script type="text/javascript">
	/**
	 * 用了ztree 有这个bug，这里是处理。不知道bug如何产生
	 */
	
	$(function(){
		var ishead=0;
		$("#ace-settings-btn").click(function(){
			if(ishead==0){
				ishead=1;
				$("#ace-settings-box").addClass("open");
			}else{
				ishead=0;
				$("#ace-settings-box").removeClass("open");
			}
		});
		$(".btn-colorpicker").click(function(){
				$(".dropdown-colorpicker").addClass("open");
		})
		
		var ishead2=0;
		$(".light-blue").click(function(){
			if(ishead2==0){
				ishead2=1;
				$(this).addClass("open");
			}else{
				ishead2=0;
				$(this).removeClass("open");
			}
			
		})
		
		$("#searchButton1").click(function(){
			if($("#bktxt").val()==""){
				alert("请输入交货单号");
			}
			
			$("#searchform1").submit();
		});
		
		//刷卡保存
		$("#btn_save").click(function(){
			var str = $("#bktxt").val();
			if(str ==""){
				layer.alert("交货单号必须填写",{icon: 7});
				return false;
			}
			var url="carton!creditsave.action";	
			save_event(url);
		});
		
		
		
	})
	
	
</script>