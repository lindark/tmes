package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.WorkShopService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
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
	private String workShopId;
	private WorkShop workShop;
	private List<Products> allProducts;
	private List<Dict>list_cxorjc;//成型/挤出
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkShopService workShopService;
	
	//添加
	public String add(){
		list_cxorjc=this.dictService.getState("fucxorjc");//成型/挤压类型
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
		return LIST;
	}
	
	/**
	 * 获取相关产品
	 * @return
	 */
	public String editproducts(){
		factoryUnit = factoryUnitService.get(id);
		allProducts = factoryUnitService.getAllProducts();
		return "editproduct";
	}
	
	/**
	 * 保存相关产品
	 * @return
	 */
	public String saveProducts(){
		FactoryUnit fu = factoryUnitService.get(id);
		List<Products> proList = new ArrayList<Products>();
		if(ids == null){
			fu.setProductsSet(null);
		}else{
			for (int i = 0; i < ids.length; i++) {
				Products products = new Products();
				products.setId(ids[i]);
				proList.add(products);
			}
			fu.setProductsSet(new HashSet<Products>(proList));
		}
		factoryUnitService.update(fu);
		redirectionUrl = "factory_unit!list.action";
		return SUCCESS;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist()
	{

		HashMap<String, String> map = new HashMap<String, String>();

		if (pager==null)
		{
			pager=new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null)
		{// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}

		if (pager.is_search() == true && Param != null)
		{// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("factoryUnitCode") != null)
			{
				System.out.println("obj=" + obj);
				String factoryUnitCode = obj.getString("factoryUnitCode").toString();
				map.put("factoryUnitCode", factoryUnitCode);
			}
			if (obj.get("factoryUnitName") != null)
			{
				String factoryUnitName = obj.getString("factoryUnitName").toString();
				map.put("factoryUnitName", factoryUnitName);
			}
			if (obj.get("state") != null)
			{
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if (obj.get("factoryName") != null)
			{
				String factoryName = obj.getString("factoryName").toString();
				map.put("factoryName", factoryName);
			}
			if (obj.get("workShopName") != null)
			{
				String workShopName = obj.getString("workShopName").toString();
				map.put("workShopName", workShopName);
			}
		}

		pager = factoryUnitService.getFactoryUnitPager(pager, map);
		List<FactoryUnit> factoryUnitList = pager.getList();
		List<FactoryUnit> lst = new ArrayList<FactoryUnit>();
		for (int i = 0; i < factoryUnitList.size(); i++)
		{
			FactoryUnit factoryUnit = (FactoryUnit) factoryUnitList.get(i);
			factoryUnit.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService, "factoryUnitState", factoryUnit.getState()));
			factoryUnit.setFactoryName(factoryUnit.getWorkShop().getFactory().getFactoryName());
			factoryUnit.setWorkShopName(factoryUnit.getWorkShop().getWorkShopName());
			//是否可以返修/返修收获
			if(factoryUnit.getIscanrepair()!=null)
			{
				factoryUnit.setXiscanrepair(ThinkWayUtil.getDictValueByDictKey(dictService, "factoryUnitIscanrepair", factoryUnit.getIscanrepair()));
			}
			lst.add(factoryUnit);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(FactoryUnit.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
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
		list_cxorjc=this.dictService.getState("fucxorjc");//成型/挤压类型
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
			BeanUtils.copyProperties(factoryUnit, persistent, new String[] { "id","createDate", "modifyDate","workShop"});
			factoryUnitService.update(persistent);
			redirectionUrl = "factory_unit!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitCode", message = "单元编号不允许为空!"),
					@RequiredStringValidator(fieldName = "factoryUnit.factoryUnitName", message = "单元名称不允许为空!"),
					@RequiredStringValidator(fieldName = "factoryUnit.warehouse", message = "线边仓编码不允许为空!"),
					@RequiredStringValidator(fieldName = "factoryUnit.warehouseName", message = "线边仓描述不允许为空!")
			  }
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		workShop=workShopService.load(workShopId);
		factoryUnit.setWorkShop(workShop);
		factoryUnitService.save(factoryUnit);
		redirectionUrl="factory_unit!list.action";
		return SUCCESS;	
	}
		
	//改变车间查找单元
	public String findFactoryUnitByWorkShop(){
		List<Map<String,String>> ListMap = new ArrayList<Map<String,String>>();
		WorkShop ws = workShopService.get(workShopId);
		Set<FactoryUnit> ftuSet = ws.getFactoryUnitSet();
		if(ftuSet!=null){
			for(FactoryUnit ftu : ftuSet){
				if("N".equals(ftu.getIsDel()) && "1".equals(ftu.getState())){
					Map<String,String> map = new HashMap<String,String>();
					map.put("ftuId", ftu.getId());
					map.put("ftuName", ftu.getFactoryUnitName());
					ListMap.add(map);
				}
			}
			if(ListMap.size()>0){
				JSONArray jsonArray = JSONArray.fromObject(ListMap);
				return ajaxJson(jsonArray.toString());
			}else{
				return ajaxJsonErrorMessage("未找到可用单元");
			}
			
		}else{
			return ajaxJsonErrorMessage("请先维护数据");
		}
		
		
	}

	
	
	/**=============弹框==================== */
	/**
	 * 进入弹框页面
	 */
	public String factoryunitlist()
	{
		return "faunlist";
	}

	/**
	 * 获取班组数据:已启用的
	 */
	public String getfaunlist(){
		HashMap<String, String> map = new HashMap<String, String>();
		if(pager == null)
		{
			pager = new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		//查询条件
		if (pager.is_search() == true && Param != null)
		{
			JSONObject obj = JSONObject.fromObject(Param);
			// 单元编码
			if (obj.get("factoryUnitCode") != null)
			{
				String factoryUnitCode = obj.getString("factoryUnitCode").toString();
				map.put("factoryUnitCode", factoryUnitCode);
			}
			// 单元名称
			if (obj.get("factoryUnitName") != null)
			{
				String factoryUnitName = obj.getString("factoryUnitName").toString();
				map.put("factoryUnitName", factoryUnitName);
			}
		}
		pager = factoryUnitService.getAllList(pager, map);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Team.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
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

	public String getWorkShopId() {
		return workShopId;
	}

	public void setWorkShopId(String workShopId) {
		this.workShopId = workShopId;
	}

	public WorkShop getWorkShop() {
		return workShop;
	}

	public void setWorkShop(WorkShop workShop) {
		this.workShop = workShop;
	}

	public List<Products> getAllProducts() {
		return allProducts;
	}

	public void setAllProducts(List<Products> allProducts) {
		this.allProducts = allProducts;
	}

	public List<Dict> getList_cxorjc()
	{
		return list_cxorjc;
	}

	public void setList_cxorjc(List<Dict> list_cxorjc)
	{
		this.list_cxorjc = list_cxorjc;
	}
	
}
