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
import cc.jiuyi.entity.Mouldmaterial;
import cc.jiuyi.entity.PositionManagement;
import cc.jiuyi.service.PositionManagementService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * Action类 - 单元仓位管理
 */
@ParentPackage("admin")
public class PositionManagementAction extends BaseAdminAction{

	private static final long serialVersionUID = 9164068124069456691L;
	
	private PositionManagement positionManagement;//单元仓库
	@Resource
	private PositionManagementService positionManagementService;

	// 添加
	public String add() {	
		return INPUT;
	}	

	// 编辑
	public String edit() {
		positionManagement = positionManagementService.load(id);
		return INPUT;
	}
	
	// 保存
	public String save() throws Exception{		
		positionManagement.setIsDel("N");
		positionManagementService.save(positionManagement);
		redirectionUrl="position_management!list.action";
		return SUCCESS;	
	}
	//更新
	public String update() {
		PositionManagement position = positionManagementService.load(id);
		BeanUtils.copyProperties(positionManagement, position, new String[]{"id","isDel","createDate","modifyDate"});
		positionManagementService.update(position);
		redirectionUrl="position_management!list.action";
		return SUCCESS;
	}
	//删除
	public String delete(){
		ids=id.split(",");
		positionManagementService.updateisdel(ids, "Y");
		redirectionUrl="position_management!list.action";
		return SUCCESS;
	}
	
	//单元选择
	public String browser(){		
		return "browser";	
	}
		
	// 列表
	public String list() {
		return LIST;
	}
	
	public String ajlist() {

		HashMap<String, String> map = new HashMap<String, String>();

		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}

		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			
			if (obj.get("xfactoryUnitName") != null) {
				String xfactoryUnitName = obj.getString("xfactoryUnitName")
						.toString();
				map.put("xfactoryUnitName", xfactoryUnitName);
			}

		   if (obj.get("warehouse") != null) { 
			   String warehouse = obj.getString("warehouse").toString();
			   map.put("warehouse",warehouse);
		   }

		}

//		pager = positionManagementService.getMouldmaterialPager(pager, map);
		pager = positionManagementService.getPositionManagementPager(pager, map);

		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			PositionManagement positionManagement = (PositionManagement) pagerlist.get(i);		
//			mould.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
//			dictService, "equipmentState", mould.getState()));
			positionManagement.setXfactoryUnitName(positionManagement.getFactoryUnit().getFactoryUnitName());
			pagerlist.set(i,positionManagement);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(PositionManagement.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}

	
	
	public PositionManagement getPositionManagement() {
		return positionManagement;
	}

	public void setPositionManagement(PositionManagement positionManagement) {
		this.positionManagement = positionManagement;
	}

	public PositionManagementService getPositionManagementService() {
		return positionManagementService;
	}

	public void setPositionManagementService(
			PositionManagementService positionManagementService) {
		this.positionManagementService = positionManagementService;
	}	
	
	
}
