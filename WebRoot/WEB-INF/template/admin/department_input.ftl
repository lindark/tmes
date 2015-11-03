<form type="post" action="department!save.action" id="departform">
	<div class="profile-user-info profile-user-info-striped">
			<div class="profile-info-row">
				<div class="profile-info-name"> 部门名称 </div>

				<div class="profile-info-value">
					<input type="text" name="department.deptName" value="" class=" input input-sm  formText {required: true}" />
				</div>
				
			</div>
			<div class="profile-info-row">
				<div class="profile-info-name"> 上级部门 </div>

				<div class="profile-info-value">
					<input type="hidden" name="department.parentDept" value="${(department.id)!}"/>
					<span>${(department.deptName)!}</span>
				</div>
			
			</div>
	
	</div>
</form>