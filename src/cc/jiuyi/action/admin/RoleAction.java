package cc.jiuyi.action.admin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Resources;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.ResourcesService;
import cc.jiuyi.service.RoleService;
import cc.jiuyi.util.ThinkWayUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 角色
 */

@ParentPackage("admin")
public class RoleAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;

	private Role role;
	private String[] resourceIds;
	private List<Resources> allResource;

	@javax.annotation.Resource
	private RoleService roleService; 
	@javax.annotation.Resource
	private ResourcesService resourceService;

	// 是否已存在 ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = role.getName();
		if (roleService.isUnique("name", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 是否已存在 ajax验证
	public String checkValue() {
		String oldValue = getParameter("oldValue");
		String newValue = role.getValue();
		if (roleService.isUnique("value", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 列表
	public String list() {
		//pager = roleService.findByPager(pager);
		return LIST;
	}

	// 删除
	public String delete() throws Exception{
		for (String id : ids) {
			Role role = roleService.get(id);
			Set<Admin> adminSet = role.getAdminSet();
			if (adminSet != null && adminSet.size() > 0) {
				return ajaxJsonErrorMessage("角色[" + role.getName() + "]下存在管理员，删除失败！");
			}
		}
		roleService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	public String ajlist(){
		if(Param !=null){
			JSONObject jsonobject = JSONObject.fromObject(Param);
			if(jsonobject.get("pager.keyword") != null){
				pager.setProperty(jsonobject.get("pager.property").toString());
				pager.setKeyword(jsonobject.get("pager.keyword").toString());
			}
		}
		pager = roleService.findByPager(pager);
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i < pager.getList().size();i++){
			Role role = (Role)pager.getList().get(i);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("id", role.getId());
			jsonobject.put("name", role.getName());
			if(role.getIsSystem()==true)
				jsonobject.put("isSystem", "<img src='/template/admin/images/list_true_icon.gif' />");
			else
				jsonobject.put("isSystem", "<img src='/template/admin/images/list_false_icon.gif' />");
			jsonobject.put("description", role.getDescription());
			jsonarray.add(jsonobject);
		}
		pager.setList(jsonarray);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Role.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		//System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
		
	}
	

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		role = roleService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "role.name", message = "角色名称不允许为空!"),
			@RequiredStringValidator(fieldName = "role.value", message = "角色标识不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "role.value", minLength = "6", message = "角色标识长度不能小于${minLength}!") 
		}, 
		regexFields = { 
			@RegexFieldValidator(fieldName = "role.value", expression = "^ROLE_.*", message = "角色标识必须以ROLE_开头!") 
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if (resourceIds != null && resourceIds.length > 0) {
			Set<Resources> resourcesSet = new HashSet<Resources>(resourceService.get(resourceIds));
			role.setResourcesSet(resourcesSet);
		} else {
			role.setResourcesSet(null);
		}
		roleService.save(role);
		redirectionUrl = "role!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "role.name", message = "角色名称不允许为空!"),
			@RequiredStringValidator(fieldName = "role.value", message = "角色标识不允许为空!")
		}, 
		stringLengthFields = {
			@StringLengthFieldValidator(fieldName = "role.value", minLength = "6", message = "角色标识长度不能小于${minLength}!") 
		}, 
		regexFields = { 
			@RegexFieldValidator(fieldName = "role.value", expression = "^ROLE_.*", message = "角色标识必须以ROLE_开头!") 
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Role persistent = roleService.load(id);
		if (persistent.getIsSystem()) {
			addActionError("系统内置角色不允许修改!");
			return ERROR;
		}
		if (resourceIds != null && resourceIds.length > 0) {
			Set<Resources> resourcesSet = new HashSet<Resources>(resourceService.get(resourceIds));
			role.setResourcesSet(resourcesSet);
		} else {
			role.setResourcesSet(null);
		}
		BeanUtils.copyProperties(role, persistent, new String[] {"id", "createDate", "modifyDate", "isSystem", "adminSet"});
		roleService.update(persistent);
		redirectionUrl = "role!list.action";
		return SUCCESS;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String[] getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String[] resourceIds) {
		this.resourceIds = resourceIds;
	}

	public List<Resources> getAllResource() {
		allResource = resourceService.getAll();
		return allResource;
	}

	public void setAllResource(List<Resources> allResource) {
		this.allResource = allResource;
	}

}