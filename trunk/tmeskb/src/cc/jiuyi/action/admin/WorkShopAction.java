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
import cc.jiuyi.entity.ProductCategory;
import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.service.WorkShopService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-车间管理
 */

@ParentPackage("admin")
public class WorkShopAction extends BaseAdminAction {

	
	private static final long serialVersionUID = 2620132694715802014L;
	
	private WorkShop workShop;
	private Factory factory;
	//获取所有状态
	private List<Dict> allState;
	private String factoryId;
	private String factoryName;
	
	@Resource
	private WorkShopService workShopService;
	@Resource
	private DictService dictService;
	@Resource
	private FactoryService factoryService;
	
	// 是否已存在 ajax验证
	public String checkworkShopCode() {
		String workShopCode = workShop.getWorkShopCode();
		if (workShopService.isExistByWorkShopCode(workShopCode)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}
	
	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
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
			if (obj.get("workShopCode") != null) {
				System.out.println("obj=" + obj);
				String workShopCode = obj.getString("workShopCode").toString();
				map.put("workShopCode", workShopCode);
			}
			if (obj.get("workShopName") != null) {
				String workShopName = obj.getString("workShopName").toString();
				map.put("workShopName", workShopName);
			}
			
			if (obj.get("factoryName1") != null) {
				String factoryName1 = obj.getString("factoryName1").toString();
				map.put("factoryName1", factoryName1);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

			pager = workShopService.getWorkShopPager(pager, map,factoryName);
			List<WorkShop> workShopList = pager.getList();
			List<WorkShop> lst = new ArrayList<WorkShop>();
			for (int i = 0; i < workShopList.size(); i++) {
				WorkShop workShop = (WorkShop) workShopList.get(i);
				workShop.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "workShopState", workShop.getState()));
				workShop.setFactory(null);
				workShop.setFactoryUnitSet(null);
				lst.add(workShop);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		workShopService.updateisdel(ids, "Y");
		redirectionUrl = "work_shop!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	
	//编辑
		public String edit(){
			workShop= workShopService.load(id);
			return INPUT;	
		}
		
		//浏览按钮
	public String browser(){
		return "browser";	
	}
		
	//更新
		@Validations(
				requiredStrings = {
						@RequiredStringValidator(fieldName = "workShop.workShopCode", message = "车间编号不允许为空!"),
						@RequiredStringValidator(fieldName = "workShop.workShopName", message = "车间名称不允许为空!")
				  }
				  
		)
		@InputConfig(resultName = "error")
		public String update() {
			WorkShop persistent = workShopService.load(id);
			BeanUtils.copyProperties(workShop, persistent, new String[] { "id","createDate", "modifyDate","factory"});
			workShopService.update(persistent);
			redirectionUrl = "work_shop!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "workShop.workShopCode", message = "车间编号不允许为空!"),
					@RequiredStringValidator(fieldName = "workShop.workShopName", message = "车间名称不允许为空!")
			  }
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{	
		factory=factoryService.load(factoryId);
		workShop.setFactoryName(factory.getFactoryName());
		workShop.setFactory(factory);
		workShopService.save(workShop);
		redirectionUrl="work_shop!list.action";
		return SUCCESS;	
	}
		
	// 获取所有工厂
	public List<Factory> getFactoryList() {
		return factoryService.getAll();
	}

	public WorkShop getWorkShop() {
		return workShop;
	}


	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}


	public WorkShopService getWorkShopService() {
		return workShopService;
	}


	public void setWorkShopService(WorkShopService workShopService) {
		this.workShopService = workShopService;
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

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
	
	
}
