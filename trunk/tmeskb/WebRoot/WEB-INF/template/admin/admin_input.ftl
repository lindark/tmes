<script type="text/javascript" src="${base}/template/common/js/jquery.metadata.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.methods.js"></script>
<script type="text/javascript" src="${base}/template/common/js/jquery.validate.cn.js"></script>
<script type="text/javascript" src="${base}/template/admin/js/browser/browserValidate.js"></script>

<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
		<form id="inputForm" class="validate" action="<#if isAdd??>admin!save.action<#else>admin!update.action</#if>" method="post">
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<input type="hidden" name="department.id" value=""/> <!--id-->
					<div class="profile-info-name"> 登录名： </div>
	
					<div class="profile-info-value">
						<input type="text" name="department.deptName" class=" input input-sm  formText {required: true}"/>
						<label class="requireField">*</label>
					</div>
				
				</div>
			</div>
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<input type="hidden" name="department.id" value=""/> <!--id-->
					<div class="profile-info-name"> 密码： </div>
	
					<div class="profile-info-value">
						<input type="text" name="department.deptName" class=" input input-sm  formText {required: true}" value="${(department.deptName)!}"/>
					</div>
				
				</div>
			</div>
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<input type="hidden" name="department.id" value=""/> <!--id-->
					<div class="profile-info-name"> 重复密码： </div>
	
					<div class="profile-info-value">
						<input type="text" name="department.deptName" class=" input input-sm  formText {required: true}" value="${(department.deptName)!}"/>
					</div>
				
				</div>
			</div>
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<input type="hidden" name="department.id" value=""/> <!--id-->
					<div class="profile-info-name"> E-mail： </div>
	
					<div class="profile-info-value">
						<input type="text" name="department.deptName" class=" input input-sm  formText {required: true}" value="${(department.deptName)!}"/>
					</div>
				
				</div>
			</div>
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<input type="hidden" name="department.id" value=""/> <!--id-->
					<div class="profile-info-name"> 管理角色： </div>
	
					<div class="profile-info-value">
						<#list allRole as list>
							<label>
								<input type="checkbox" name="roleList.id" class="{required: true, messages: {required: '请至少选择一个角色!'}, messagePosition: '#roleMessagePosition'}" value="${list.id}" <#if (admin.roleSet.contains(list) == true)!> checked="checked"</#if> />
								${(list.name)!}
							</label>
						</#list>
						<span id="roleMessagePosition"></span>
					</div>
				
				</div>
			</div>
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<input type="hidden" name="department.id" value=""/> <!--id-->
					<div class="profile-info-name"> 是否启用： </div>
	
					<div class="profile-info-value">
						<label class="pull-left inline">
						    <small class="muted smaller-90">是:</small>
							<input type="radio" name="admin.isAccountEnabled" class="ace" value="true"<#if (admin.isAccountEnabled == true)!> checked</#if> />
							<span class="lbl middle"></span>
						</label>
								
						<label class="pull-left inline">
	
						    <small class="muted smaller-90">否:</small>
							<input type="radio" name="admin.isAccountEnabled" class="ace" value="false"<#if (isAdd || admin.isAccountEnabled == false)!> checked</#if> />
							<span class="lbl middle"></span>
						</label>
					</div>
				
				</div>
			</div>
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<input type="hidden" name="department.id" value=""/> <!--id-->
					<div class="profile-info-name"> 部门： </div>
	
					<div class="profile-info-value">
						${(departName)!}
					</div>
				
				</div>
			</div>
			<div class="profile-user-info profile-user-info-striped">
				<div class="profile-info-row">
					<input type="hidden" name="department.id" value=""/> <!--id-->
					<div class="profile-info-name"> 姓名： </div>
	
					<div class="profile-info-value">
						<input type="text" name="department.deptName" class=" input input-sm  formText {required: true}" value="${(department.deptName)!}"/>
					</div>
				
				</div>
			</div>
			<button>点击</button>
		</form>
