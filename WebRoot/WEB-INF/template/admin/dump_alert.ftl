<#assign sec=JspTaglibs["/WEB-INF/security.tld"] />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />

<title></title>
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<#include "/WEB-INF/template/common/include.ftl">
<link href="${base}/template/admin/css/input.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/template/admin/js/BasicInfo/dump_alert.js"></script>
<#include "/WEB-INF/template/common/include_adm_top.ftl">

<style>
body {
	background: #fff;
}

</style>

</head>
<body class="no-skin input">
<input type="hidden" id="loginid" value="<@sec.authentication property='principal.id' />" />
<#include "/WEB-INF/template/admin/admin_navbar.ftl">
	<!-- add by welson 0728 -->
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
			<!-- add by welson 0728 -->
			<div class="page-content">
				<div class="page-content-area">

					<div class="row">
						<div class="col-xs-12">
							<!-- ./ add by welson 0728 -->
							<form id="inputForm" class="validate" action="" method="post">
								<input type="text" id="dumpid" name="dumpid" value="${(dump.id)! }" style="display: none;" />
								<input type="hidden" name="fuid" value="${(factoryunit.id)! }" />
								<input type="hidden" id="xedit" value="${xedit! }" />
								<input type="hidden" name="materialcode" value="${materialcode! }"/>
								<!-- tabs start -->
								<div id="inputtabs">
									<ul>
										<li><a href="#tabs-1">添加物料</a></li>
									</ul>
									<div id="tabs-1" class="tab1">
										<!-- msg start -->
											<!-- workingbill  start -->
											<div class="profile-user-info profile-user-info-striped">
								  			<div class="profile-info-row">
								    			<table id="tb_cartonson" class="table table-striped table-bordered table-hover">
													<#if xshow??>
														<tr>
															<th>物料编码</th>
															<th>批次</th>
															<th>转储单号</th>
															<th>配送数量</th>
														</tr>
														<#list list_dd as ddlist>
															<tr>
																<td>${(ddlist.matnr)! }</td>
																<td>${(ddlist.charg)! }</td>
																<td>${(ddlist.lenum)! }</td>
																<td>${(ddlist.menge)! }</td>
															</tr>
														</#list>
													<#else>
														<tr>
															<th>物料编码</th>
															<th>批次</th>
															<th>库存数量</th>
															<th>配送数量</th>
														</tr>
									    				<#assign  num=0 />
														<#list list_map as mlist>
															<tr>
																<td>${(mlist.matnr)! }</td>
																<td>${(mlist.charg)! }</td>
																<td>${(mlist.verme)! }</td>
																<td>
																	<!-- 数量 -->
																	<input type="text" name="list_dd[${num}].menge" value="${(mlist.menge)! }" class="notnull input input-sm inputmenge"/>

																	<!-- SAP数据 -->
																	<input type="hidden" name="list_dd[${num}].matnr" value="${(mlist.matnr)! }" /><!-- matnr物料编码 -->
																	<input type="hidden" name="list_dd[${num}].charg" value="${(mlist.charg)! }" /><!-- charg批号 -->
																	<input type="hidden" name="list_dd[${num}].meins" value="${(mlist.meins)! }" /><!-- meins基本单位 -->
																	<input type="hidden" name="list_dd[${num}].verme" value="${(mlist.verme)! }" /><!-- verme库存数量 -->
																	<input type="hidden" name="list_dd[${num}].mantd" value="${(mlist.mantd)! }" /><!-- mantd客户端编号 -->
																	<input type="hidden" name="list_dd[${num}].rowno" value="${(mlist.rowno)! }" /><!-- rowno存放ID -->
																	<input type="hidden" name="list_dd[${num}].lgtyp" value="${(mlist.lgtyp)! }" /><!-- lgtyp仓储类型 -->
																	<input type="hidden" name="list_dd[${num}].lqnum" value="${(mlist.lqnum)! }" /><!-- dwnum Quantity in Parallel Unit of Entry -->
																	<input type="hidden" name="list_dd[${num}].lenum" value="${(mlist.lenum)! }" /><!-- lenum仓储单位编号 -->
																	<input type="hidden" name="list_dd[${num}].sequ" value="${(mlist.sequ)! }" /><!-- sequ整数 -->
																	<input type="hidden" name="list_dd[${num}].nlpla" value="${(mlist.nlpla)! }" /><!-- nlpla目的地仓位 -->
																	<!-- 本地数据 -->
																	<input type="hidden" name="list_dd[${num}].psaddress" value="${(factoryunit.psaddress)! }" /><!-- psaddress配送库存地点编码 -->
																	<input type="hidden" name="list_dd[${num}].lgpla" value="${(factoryunit.psPositionAddress)! }" /><!-- lgpla仓位//配送库存地点仓位编码 -->
																	<input type="hidden" name="list_dd[${num}].jsaddress" value="${(factoryunit.warehouse)! }" /><!-- 收货库存地点 -->
																	<input type="hidden" name="list_dd[${num}].werks" value="${(factoryunit.workShop.factory.factoryCode)! }" /><!-- 工厂 -->
																</td>
															</tr>
															<#assign num=num+1 />
														</#list>
													</#if>
												</table>
							  				</div>
						   				</div>
										<!-- end msg -->
									</div>
									<br/>
									<div class="row buttons col-md-8 col-sm-4 sub-style">
										<#if xshow??><#else>
										<a id="btn_save" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-cloud-upload"></i>
											刷卡保存
										</a>
										<a id="btn_confirm" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-cloud-upload"></i>
											刷卡确认
										</a>
										</#if>
										<a id="btn_return" class="btn btn-white btn-default btn-sm btn-round">
											<i class="ace-icon fa fa-home"></i>
											返回
										</a>
									</div>
								</div>
								<!-- end tabs -->
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
	</div>
	<!-- /.main-container -->
	<#include "/WEB-INF/template/common/include_adm_bottom.ftl">
	<!-- ./ add by welson 0728 -->
</body>
</html>
