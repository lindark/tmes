package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.WorkShopService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-单元管理
 */

@ParentPackage("admin")
public class FactoryUnitAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private FactoryUnit factoryUnit;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkShopService workShopService;
	
	//添加
	public String add(){
		return INPUT;
	}

	// 是否已存在 ajax验证
	public String checkFactoryUnitCode() {
		String factoryUnitCode = factoryUnit.getFactoryUnitCode();
		if (factoryUnitService.isExistByFactoryUnitCode(factoryUnitCode)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}

	//列表
	public String list(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
//		List<FactoryUnit> factoryUnitList = pager.getList();
//		for (FactoryUnit factoryUnit1 : factoryUnitList) {
//			factoryUnit1.setState(ThinkWayUtil.getDictValueByDictKey(dictService,"factoryUnitState", factoryUnit1.getState()));
//		}
		//dictService.getDictValueByDictKey("factoryUnitState", factoryUnit.getState());
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("factoryUnitCode") != null) {
				System.out.println("obj=" + obj);
				String factoryUnitCode = obj.getString("factoryUnitCode").toString();
				map.put("factoryUnitCode", factoryUnitCode);
			}
			if (obj.get("factoryUnitName") != null) {
				String factoryUnitName = obj.getString("factoryUnitName").toString();
				map.put("factoryUnitName", factoryUnitName);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}			
		}

			pager = factoryUnitService.getFactoryUnitPager(pager, map);
			List<FactoryUnit> factoryUnitList = pager.getList();
			List<FactoryUnit> lst = new ArrayList<FactoryUnit>();
			for (int i = 0; i < factoryUnitList.size(); i++) {
				FactoryUnit factoryUnit = (FactoryUnit) factoryUnitList.get(i);
				factoryUnit.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "factoryUnitState", factoryUnit.getState()));
				factoryUnit.setWorkShop(null);
				lst.add(factoryUnit);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		factoryUnitService.updateisdel(ids, "Y");
		redirectionUrl = "factory_unit!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	
	//编辑
		public String edit(){
			factoryUnit= factoryUnitService.load(id);
			return INPUT;	
		}
		
	//更新
		@Validations(
				requiredStrings = {
						@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitCode", message = "单元编号不允许为空!"),
						@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitName", message = "单元名称不允许为空!")
				  }
				  
		)
		@InputConfig(resultName = "error")
		public String update() {
			FactoryUnit persistent = factoryUnitService.load(id);
			BeanUtils.copyProperties(factoryUnit, persistent, new String[] { "id","createDate", "modifyDate"});
			factoryUnitService.update(persistent);
			redirectionUrl = "factory_unit!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitCode", message = "单元编号不允许为空!"),
					@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitName", message = "单元名称不允许为空!")
			  }
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		factoryUnitService.save(factoryUnit);
		redirectionUrl="factory_unit!list.action";
		return SUCCESS;	
	}
		


	public FactoryUnit getFactoryUnit() {
		return factoryUnit;
	}


	public void setFactoryUnit(FactoryUnit factoryUnit) {
		this.factoryUnit = factoryUnit;
	}


	public FactoryUnitService getFactoryUnitService() {
		return factoryUnitService;
	}


	public void setFactoryUnitService(FactoryUnitService factoryUnitService) {
		this.factoryUnitService = factoryUnitService;
	}


	//获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "stateRemark");
	}


	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}


	public DictService getDictService() {
		return dictService;
	}


	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}
	
	// 获取所有车间
	public List<WorkShop> getFactoryList() {
		return workShopService.getAll();
	}
}
