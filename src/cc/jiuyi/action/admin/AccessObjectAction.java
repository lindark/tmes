package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ResourceService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 权限对象 
 */

@ParentPackage("admin")
public class AccessObjectAction extends BaseAdminAction {
	
	private static final long serialVersionUID = 2275362589350097717L;
	@Resource
	private AccessObjectService accessobjectservice;
	@Resource
	private ResourceService resourceService;
	@Resource
	private DictService dictService;
	
	private AccessObject accessObject;
	private List<cc.jiuyi.entity.Resource> allRes;
	private List<Dict> allDict;
	public String list(){
		
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		HashMap<String,String> map = new HashMap<String,String>();
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
		pager = accessobjectservice.findByPager(pager);
		
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			AccessObject accessObject  = (AccessObject)pagerlist.get(i);
			String typeName = ThinkWayUtil.getDictValueByDictKey(dictService, "accessobject", accessObject.getType());
			accessObject.setTypeName(typeName);
			accessObject.setResourceName(accessObject.getResource().getName());
			accessObject.setAccessResourceSet(null);
			accessObject.setResource(null);
			pagerlist.set(i, accessObject);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(AccessObject.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	
	/**
	 * 添加方法
	 * @return
	 */
	public String add(){
		
		return INPUT;
	}
	
	/**
	 * 添加方法
	 * @return
	 */
	public String edit(){
		accessObject = accessobjectservice.get(id);
		return INPUT;
	}
	
	public String save(){
		accessobjectservice.save(accessObject);
		redirectionUrl = "access_object!list.action";
		return SUCCESS;
	}
	
	public String update(){
		AccessObject persistent = accessobjectservice.load(id);
		BeanUtils.copyProperties(accessObject, persistent, new String[] {"id", "createDate"});
		accessobjectservice.update(persistent);
		redirectionUrl = "access_object!list.action";
		return SUCCESS;
	}
	
	public String delete(){
		accessobjectservice.delete(ids);
		return SUCCESS;
	}

	public AccessObject getAccessObject() {
		return accessObject;
	}

	public void setAccessObject(AccessObject accessObject) {
		this.accessObject = accessObject;
	}

	public List<cc.jiuyi.entity.Resource> getAllRes() {
		this.allRes = resourceService.getAll();
		return allRes;
	}

	public void setAllRes(List<cc.jiuyi.entity.Resource> allRes) {
		this.allRes = allRes;
	}

	public List<Dict> getAllDict() {
		allDict =  dictService.getList("dictname", "accessobject");
		return allDict;
	}

	public void setAllDict(List<Dict> allDict) {
		this.allDict = allDict;
	}

	
	
	
	
}