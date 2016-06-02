<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title>纸箱收货管理 - Powered By ${systemConfig.systemName}</title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet"
	type="text/css" />
<#if !id??> <#assign isAdd = true /> <#else> <#assign isEdit = true />
</#if> <#include "/WEB-INF/template/common/include_adm_top.ftl">
<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/common.js"></script>		
		<script type="text/javascript" src="${base}/template/admin/js/jqgrid_common.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/list.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/browser/browser.js"></script>
		<script type="text/javascript" src="${base}/template/admin/js/SystemConfig/user/admin.js"></script>
		<script src="${base}/template/admin/assets/js/jquery-ui.min.js"></script>
		<script src="${base}/template/admin/assets/js/jquery.ui.touch-punch.min.js"></script>
		
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/carton_input.js"></script>
<style>
body {
	background: #fff;
}
.tabth{text-align:center;}
.sub_style{text-align: center;}
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
				<script type="text/javascript">try {ace.settings.check('breadcrumbs', 'fixed')} catch (e) {}</script>
				<ul class="breadcrumb">
					<li><i class="ace-icon fa fa-home home-icon"></i> 
					<a href="admin!index.action">管理中心</a></li>
					<li class="active">添加纸箱收货单</li>
				</ul>
				<!-- /.breadcrumb -->
			</div>
			<!-- add by welson 0728 -->
			
			<div class="page-content">
				<div class="page-content-area">
					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<form id="inputForm" name="inputForm" class="validate" action="" method="post">
								<input type="hidden" id="my_id" name="my_id" value="${(my_id)! }" />
                            	<input type="hidden" id="id" name="id" value="${(edit)! }" />
								<input type="hidden" name="loginid" value="<@sec.authentication property='principal.id' />"/>
								<div id="inputtabs">
								 	<ul>
								    	<li><a href="#tabs-1">添加纸箱收货单</a></li>
									</ul>
									
									<div id="tabs-1" class="tab1">	
									  <div class="profile-user-info profile-user-info-striped">
												<div class="profile-info-row">
													<div class="profile-info-name">单据编号</div>
													<div class="profile-info-value">
											            <#if show>													
																${(carton.bktxt)!}
														<#else>
														<input type="text" name="bktxt" id="bktxt" value="${(carton.bktxt)!}" class=" input input-sm  formText {required: true,minlength:1,maxlength: 5}" />
											            <label class="requireField">*</label>
														</#if>	
													</div>
												</div>							
							        	</div>
								
												
										<div class="profile-user-info profile-user-info-striped">
								  			<div class="profile-info-row">
								    			<table id="tb_cartonson" class="table table-striped table-bordered table-hover">
													<tr>
														<!-- <th class="tabth">随工单编码</th> -->
														<th class="tabth">产品编码</th>
														<th class="tabth">产品名称</th>
														<th class="tabth">物料编码</th>
														<th class="tabth">物料名称</th>
														<th class="tabth">纸箱数量</th>
														<!-- 
														<th class="tabth">累计数量</th>
														 -->
													</tr>
													<#if show>
														<#if list_cs??>
															<#list list_cs as cslist>
																<tr>
																	<!-- <td>${(cslist.wbcode)! }</td> -->
																	<td>${(cslist.productcode)! }</td>
																	<td>${(cslist.productname)! }</td>
																	<td>${(cslist.MATNR)! }</td>
																	<td>${(cslist.MATNRDES)! }</td>
																	<td>${(cslist.cscount)! }</td>
																	<!-- 
																	<td>${(cslist.xcstotal)! }</td>
																	 -->
																</tr>
															</#list>
														</#if>
													<#else>
									    				<#assign  num=0 />
														<#list list_cs as list>
															<tr>
																<!-- <td>
																	${(list.wbcode)! }
																	<input type="hidden" name="list_cs[${num}].wbcode" value="${(list.wbcode)! }" />
																</td> -->
																<td>
																	${(list.productcode)! }
																	<input type="hidden" name="list_cs[${num}].productcode" value="${(list.productcode)! }" />
																</td>
																<td>
																	${(list.productname)! }
																	<input type="hidden" name="list_cs[${num}].productname" value="${(list.productname)! }" />
																</td>
																<td>
																	${(list.MATNR)! }
																	<input type="hidden" name="list_cs[${num}].MATNR" value="${(list.MATNR)! }" />
																</td>
																<td>
																	${(list.MATNRDES)! }
																	<input type="hidden" name="list_cs[${num}].MATNRDES" value="${(list.MATNRDES)! }" />
																	<input type="hidden" name="list_cs[${num}].wbid" value="${(list.wbid)! }" />
																</td>
																<td>
																	<input type="text" name="list_cs[${num}].cscount" value="${(list.cscount)! }" id="input_cscount${(list.matnr)! }" onblur="check(this)"  onkeyup="this.value=this.value.replace(/[^0-9.]/g,'')" class="notnull input input-sm"/>
																	
																</td>
																<!-- 
																<td>
																	${(list.xcstotal)! }
																</td>
																 -->									
															</tr>
															<#assign num=num+1/>
														</#list>
													</#if>
												</table>
							  				</div>
						   				</div>
									</div>
                     			</div>
							</form>
							<br/>
							<div class="row buttons col-md-8 col-sm-4 sub_style">
                            	<#if show??>
                            	<#else>
                            		<button class="btn btn-white btn-default btn-sm btn-round" id="btn_save" type="button"/>
										<i class="ace-icon glyphicon glyphicon-check"></i>
										刷卡保存
									</button>
                            	</#if>
								<button class="btn btn-white btn-default btn-sm btn-round" id="btn_back" type="button" />
									<i class="ace-icon fa fa-home"></i>
									返回
								</button>
							</div>
							<!-- add by welson 0728 -->
						</div>
						<!-- /.col -->
					</div>
					<!-- /.row -->
				</div>
				<!-- /.page-content-area -->
				
			</div>
			<!-- /.page-content -->
		</div>
		<!-- /.main-content -->
		<#include "/WEB-INF/template/admin/admin_footer.ftl">
	</div>
	<!-- /.main-container -->
<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
<!-- ./ add by welson 0728 -->
</body>
</html>
<script type="text/javascript">
$(function(){
	//刷卡保存
	$("#btn_save").click(function(){
		//提交
		//$("#inputForm").submit();
		
		var str = $("#bktxt").val();
		if(str ==""){
			layer.alert("单据编号必须填写",{icon: 7});
			return false;
		}
		var url="";
		<#if add??>
			url = "carton!creditsave.action";		
		<#else>
			url = "carton!creditupdate.action";
		</#if>
		save_event(url);
	});
});

//数量输入事件
/**
function num_event()
{
	<#list list_cs as xlist>
		$("#input_cscount${(list.matnr)!}").change(function(){
			var xid=$(this).attr("id");
			isright_event(xid);
		});
	</#list>
}
*/
function check(e) { 
    var re = /^\d+(?=\.{0,1}\d+$|$)/ 
    if (e.value != "") { 
        if (!re.test(e.value)) { 
            alert("请输入正确的数字"); 
            e.value = ""; 
            e.focus(); 
        } 
    } 
}
		
</script>