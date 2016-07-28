package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.List;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Resources;
import cc.jiuyi.service.ResourcesService;
import cc.jiuyi.util.ThinkWayUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.oscache.util.StringUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 资源
 */

@ParentPackage("admin")
public class ResourceAction extends BaseAdminAction {

	private static final long serialVersionUID = -1066168819528324882L;

	private Resources resource;

	@javax.annotation.Resource
	private ResourcesService resourceService;

	// 是否已存在ajax验证
	public String checkName() {
		String oldValue = getParameter("oldValue");
		String newValue = resource.getName();
		if (resourceService.isUnique("name", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 是否已存在ajax验证
	public String checkValue() {
		String oldValue = getParameter("oldValue");
		String newValue = resource.getValue();
		if (resourceService.isUnique("value", oldValue, newValue)) {
			return ajaxText("true");
		} else {
			return ajaxText("false");
		}
	}

	// 列表
	public String list() {
		//pager = resourceService.findByPager(pager);
		return LIST;
	}
	
	public String ajlist(){
		if(Param !=null){
			JSONObject jsonobject = JSONObject.fromObject(Param);
			if(jsonobject.get("pager.keyword") != null){
				pager.setProperty(jsonobject.get("pager.property").toString());
				pager.setKeyword(jsonobject.get("pager.keyword").toString());
			}
		}
		
		pager = resourceService.findByPager(pager);
		
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i < pager.getList().size();i++){
			Resources resource = (Resources)pager.getList().get(i);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("id", resource.getId());
			jsonobject.put("name", resource.getName());
			jsonobject.put("value", resource.getValue());
			if(resource.getIsSystem()==true)
				jsonobject.put("isSystem", "<img src='/template/admin/images/list_true_icon.gif' />");
			else
				jsonobject.put("isSystem", "<img src='/template/admin/images/list_false_icon.gif' />");
			jsonobject.put("description", resource.getDescription());
			jsonarray.add(jsonobject);
		}
		pager.setList(jsonarray);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Resources.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		//System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
		
	}

	// 删除
	public String delete(){
		ids = ids[0].split(",");
		for(int i=0;i<ids.length;i++){
			//System.out.println(ids[i]);
			Resources resource = resourceService.load(ids[i]);
			if(resource.getIsSystem()){
				return ajaxJsonErrorMessage("系统内置不允许删除");
			}
		}
		resourceService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		resource = resourceService.load(id);
		return INPUT;
	}

	// 保存
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "resource.name", message = "资源名称不允许为空!"),
			@RequiredStringValidator(fieldName = "resource.value", message = "资源值不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		resource.setRoleSet(null);
		resourceService.save(resource);
		redirectionUrl = "resource!list.action";
		return SUCCESS;
	}

	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "resource.name", message = "资源名称不允许为空!"),
			@RequiredStringValidator(fieldName = "resource.value", message = "资源值不允许为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Resources persistent = resourceService.load(id);
		if (persistent.getIsSystem()) {
			addActionError("系统内置资源不允许修改!");
			return ERROR;
		}
		BeanUtils.copyProperties(resource, persistent, new String[] {"id", "createDate", "modifyDate", "isSystem", "roleSet"});
		resourceService.update(persistent);
		redirectionUrl = "resource!list.action";
		return SUCCESS;
	}

	public Resources getResource() {
		return resource;
	}

	public void setResource(Resources resource) {
		this.resource = resource;
	}
}