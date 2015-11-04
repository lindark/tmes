<#if !id??>
	<#assign isAdd = true />
<#else>
	<#assign isEdit = true />
</#if>
<button style="background-color: #428bca" class="addMessage">添加</button><button style="background-color: #abbac3">删除</button>
<form type="post" action="<#if isAdd??>abnormal!save.action<#else>abnormal!update.action</#if>" id="ringform">
	<div class="profile-user-info profile-user-info-striped">
	        <table class="table">
	        <tr>
	          <th class="check">
				<input type="checkbox" class="allCheck" />
			  </th>
			  <th><span class="sort" name="">姓名</span></th>
			  <th><span class="sort" name="">电话</span></th>
			  <th><span class="sort" name="">消息</span></th>
	        </tr>
	        <tr>
	          <td>
				<input type="checkbox" name="ids" value="" />
			  </td>
			  <td>张三</td>
			  <td>18032236212</td>
			  <td>线边仓缺料了</td>
	        </tr>
	        <tr>
	          <td>
				<input type="checkbox" name="ids" value="" />
			  </td>
			  <td>李四</td>
			  <td>18032236212</td>
			  <td>设备出现问题了</td>
	        </tr>
	        </table>
	        <!--  
			<div class="profile-info-row">
				<input type="hidden" name="department.id" value="${(department.id)!}"/> 
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
			
			</div>-->
	
	</div>
</form>