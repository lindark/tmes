package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.oscache.util.StringUtil;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessFunctionService;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.AccessResourceService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ResourceService;
import cc.jiuyi.service.RoleService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 权限资源对象 
 */

@ParentPackage("admin")
public class AccessResourceAction extends BaseAdminAction {
	private static final long serialVersionUID = 8128357772474252752L;
	@Resource
	private AccessResourceService accessresourceservice;
	@Resource
	private RoleService roleservice;
	@Resource
	private ResourceService resourceservice;
	@Resource
	private AccessObjectService accessobjectservice;
	@Resource
	private DictService dictService;
	@Resource
	private AccessFunctionService accessfunctionservice;
	
	private List<Role> allRole;
	private List<cc.jiuyi.entity.Resource> allResource;
	private List<AccessObject> allAccessobject;
	private List<AccessObject> accessobjectlist;
	private AccessResource accessResource;
	private List<AccessFunction> accessFunctionList;
	private AccessObject accessObject;
	private AccessFunction accessFunction;


	private String orderBy;
	private String orderType;
	
	public String list(){
		return LIST;
	}
	
	public String add(){
		
		return INPUT;
	}
	public String save(){
		accessresourceservice.save(accessResource);
		redirectionUrl = "access_resource!list.action";
		return SUCCESS;
	}
	
	/**
	 * 操作权限对象
	 * @return
	 */
	public String editaccess(){
		//getBtnhtml();
		//allAccessobject = accessobjectservice.getAll();
		//accessResource = accessresourceservice.load(id);
		return "editaccess";
	}
	
	/**
	 * 保存权限对象
	 * @return
	 */
	public String saveaccess(){
		String [] propertyNames = {"accessObject.id","accessResource.id"};
		Object [] propertyValues = {accessFunction.getAccessObject().getId(),accessFunction.getAccessResource().getId()};
		AccessFunction accessFunction = accessfunctionservice.get(propertyNames, propertyValues);
		if(accessFunction ==null){//没找到
			accessfunctionservice.save(this.accessFunction);
		}else{//找到
			accessFunction.setState(this.accessFunction.getState());
			accessfunctionservice.update(accessFunction);
		}
		AccessResource accessResource = accessresourceservice.load(id);
//		AccessResource persistent = accessresourceservice.load(id);
		//accessResource.setAccessobjectSet(new HashSet<AccessObject>(accessobjectlist));
//		BeanUtils.copyProperties(accessResource, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "isAccountLocked", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate", "loginDate", "loginIp", "authorities"});
		accessresourceservice.update(accessResource);
		redirectionUrl="access_resource!list.action";
		return SUCCESS;
	}
	
	/**
	 * JSON 格式查询
	 * @return
	 */
	public String acccesslist(){
		accessResource = accessresourceservice.get(id);
		//List<AccessObject> accessObjectList = new ArrayList<AccessObject>(accessResource.getResource().getAccessobjectSet());//取出对应的权限对象
		List<AccessObject> accessObjectList = accessobjectservice.getAccessObjectList(id);
		Collections.sort(accessObjectList);
		JSONArray jsonarray = new JSONArray();
		String state="";
		for(int i=0;i<accessObjectList.size();i++){
			JSONObject jsonobject = new JSONObject();
			AccessObject accessObject = accessObjectList.get(i);
			//if(accessObject == null) continue;
			String [] propertyNames = {"accessResource.id","accessObject.id"};
			Object [] propertyValues={accessResource.getId(),accessObject.getId()};
			AccessFunction accessFunction = accessfunctionservice.get(propertyNames, propertyValues);
			if(accessFunction!=null)
				state=accessFunction.getState();
			else
				state = "";
			jsonobject.put("accessFunction.accessObject.id", accessObject.getId());
			jsonobject.put("accObjName", accessObject.getAccObjName());
			jsonobject.put("type", accessObject.getType());
			jsonobject.put("accessFunction.accessResource.id", accessResource.getId());
			jsonobject.put("accessFunction.state",ThinkWayUtil.getDictValueByDictKey(dictService, "accessobject_state", state) );
			//jsonobject.put("stateRemark",ThinkWayUtil.getDictValueByDictKey(dictService, "accessobject_state", state) );
			jsonarray.add(jsonobject);
		}
		
		JSONObject json = new JSONObject();
		json.put("list", jsonarray);
		return ajaxJson(json.toString());
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("modifyDate");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件,复杂查询
			if(!filters.equals("")){
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		
		pager = accessresourceservice.findByPager(pager);
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			AccessResource accessresource  = (AccessResource)pagerlist.get(i);
			//accessresource.setAccessobjectname(accessresource.getAccessobject().getAccObjName());
			accessresource.setResourcename(accessresource.getResource().getName());
			accessresource.setRolename(accessresource.getRole().getName());
			pagerlist.set(i, accessresource);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(AccessResource.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
	}

	public List<Role> getAllRole() {
		this.allRole = roleservice.getAll();
		return allRole;
	}

	public void setAllRole(List<Role> allRole) {
		this.allRole = allRole;
	}

	public List<cc.jiuyi.entity.Resource> getAllResource() {
		this.allResource = resourceservice.getAll();
		return allResource;
	}

	public void setAllResource(List<cc.jiuyi.entity.Resource> allResource) {
		this.allResource = allResource;
	}

	public List<AccessObject> getAllAccessobject() {
		return allAccessobject;
	}

	public void setAllAccessobject(List<AccessObject> allAccessobject) {
		this.allAccessobject = allAccessobject;
	}

	public List<AccessObject> getAccessobjectlist() {
		return accessobjectlist;
	}

	public void setAccessobjectlist(List<AccessObject> accessobjectlist) {
		this.accessobjectlist = accessobjectlist;
	}

	public AccessResource getAccessResource() {
		return accessResource;
	}

	public void setAccessResource(AccessResource accessResource) {
		this.accessResource = accessResource;
	}

	public List<AccessFunction> getAccessFunctionList() {
		return accessFunctionList;
	}

	public void setAccessFunctionList(List<AccessFunction> accessFunctionList) {
		this.accessFunctionList = accessFunctionList;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public AccessObject getAccessObject() {
		return accessObject;
	}

	public void setAccessObject(AccessObject accessObject) {
		this.accessObject = accessObject;
	}
	public AccessFunction getAccessFunction() {
		return accessFunction;
	}

	public void setAccessFunction(AccessFunction accessFunction) {
		this.accessFunction = accessFunction;
	}

}