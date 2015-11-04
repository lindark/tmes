<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<form type="post" action="<#if isAdd??>department!save.action<#else>department!update.action</#if>" id="departform">
	<div class="profile-user-info profile-user-info-striped">
			<div class="profile-info-row">
				<input type="hidden" name="department.id" value="${(department.id)!}"/> <!--id-->
				<div class="profile-info-name"> 部门名称 </div>

				<div class="profile-info-value">
					<input type="text" name="department.deptName" class=" input input-sm  formText {required: true}" value="${(department.deptName)!}"/>
				</div>
				
			</div>
			<div class="profile-info-row">
				<div class="profile-info-name"> 上级部门 </div>

				<div class="profile-info-value">
				<#if isAdd??>
					<input type="hidden" name="department.parentDept.id" value="${(pid)!}"/>
					<span>${(pname)!}</span>
				<#else>
					<input type="hidden" name="department.parentDept.id" value="${(department.parentDept.id)!}"/>
					<span>${(department.parentDept.deptName)!}</span>
				</#if>
				</div>
			
			</div>
	
	</div>
</form>