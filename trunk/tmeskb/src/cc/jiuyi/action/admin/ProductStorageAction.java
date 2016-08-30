package cc.jiuyi.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.ProductStorage;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.ProductStorageRfc;
import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.ProductStorageService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 
 */

@ParentPackage("admin")
public class ProductStorageAction extends BaseAdminAction {

	private static final long serialVersionUID = 1341979251254008699L;
	public static Logger log = Logger.getLogger(ProductStorageAction.class);
	
	private WorkingBill workingbill;
	private List<Material> materialList;
	private String aufnr;
	private String createDateStart;
	private String createDateEnd;
	private String wbid;
	private String matnr;
	private String funid;
	private List<String> moudles;
	private String info;
	private String lgort;
	private String accountDateStart;
	private String accountDateEnd;
	public String getLgort() {
		return lgort;
	}

	public void setLgort(String lgort) {
		this.lgort = lgort;
	}

	public String getAccountDateStart() {
		return accountDateStart;
	}

	public void setAccountDateStart(String accountDateStart) {
		this.accountDateStart = accountDateStart;
	}

	public String getAccountDateEnd() {
		return accountDateEnd;
	}

	public void setAccountDateEnd(String accountDateEnd) {
		this.accountDateEnd = accountDateEnd;
	}

	private String teamid;
	private String teamName;
	private List<Team> teamList;//班组
	
	@Resource
	private ProductStorageRfc productstoragerfc;
	@Resource
	private MaterialService materialservice;
	@Resource
	private OrdersService ordersservice;
	@Resource
	private UnitConversionService unitconversionservice;
	@Resource
	private UnitdistributeModelService unitdistributeModelService;
	@Resource
	private UnitdistributeProductService unitdistributeProductService;
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private AdminService adminService;
	@Resource
	private ProductStorageService productstorageservice;
	@Resource
	private TeamService teamservice;
	
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		return INPUT;
	}
	
	// List 按按钮后进入的页面
	public String list() {
		return LIST;
	}
	
	public void syncByQuartz(){
		
		try {
			log.info("定时生产入库同步开始");
			HashMap<String, Object> parameter = new HashMap<String, Object>();
			productstoragerfc.sysnProductStorage(parameter);
			log.info("定时生产入库同步结束");
		} catch (Exception e) {
			// TODO: handle exception
			log.info("定时生产入库同步错误"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		HashMap<String,String> map = new HashMap<String,String>();
		if(pager == null) {
			pager = new Pager();
		}
		if(pager.getOrderBy()==null || pager.getOrderBy().equals("")){
			pager.setOrderType(OrderType.desc);
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
		if(pager.is_search()==true && Param != null){//普通搜索功能
			if(!Param.equals("")){
			//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject param = JSONObject.fromObject(Param);
				String accountDateStart = ThinkWayUtil.null2String(param.get("accountDateStart"));
				String accountDateEnd = ThinkWayUtil.null2String(param.get("accountDateEnd"));
				String createDateStart = ThinkWayUtil.null2String(param.get("createDateStart"));
				String createDateEnd = ThinkWayUtil.null2String(param.get("createDateEnd"));
				String lgort = ThinkWayUtil.null2String(param.get("lgort"));//库存地点
				String matnr = ThinkWayUtil.null2String(param.get("matnr"));//物料编码
				String aufnr = ThinkWayUtil.null2String(param.get("aufnr"));//生产订单号
				map.put("accountDateStart", accountDateStart);
				map.put("accountDateEnd", accountDateEnd);
				map.put("createDateStart", createDateStart);
				map.put("createDateEnd", createDateEnd);
				map.put("lgort", lgort);
				map.put("matnr", matnr);
				map.put("aufnr", aufnr);
			}
		}
		
		pager = productstorageservice.findPagerByjqGrid(pager, map);
		
		List<ProductStorage> pagerlist = pager.getList();
		/*for(int i =0; i < pagerlist.size();i++){
			WorkingBill workingbill  = pagerlist.get(i);
			Orders order = ordersservice.get("aufnr",workingbill.getAufnr());
			if(order!=null){
				try {
					if(order.getMujuntext()!=null){
						workingbill.setModule(order.getMujuntext());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				pagerlist.set(i, workingbill);
			}
			//workingbill.setDailyWork(null);
			
		} */
		pager.setList(pagerlist);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(ProductStorage.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		//System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	
	//手工同步随工单弹窗
	public String browser(){
		return "browser";
	}

	//同步
	public String sync() throws SchedulerException {
		log.info("生产入库同步开始...");
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			HashMap<String,Object> parameter=new HashMap<String,Object>();
			//System.out.println(accountDateStart+"accountDateStart");
			parameter.put("S_BUDAT", sdf.parse(accountDateStart.replace("-", "")));
			
			if (accountDateEnd!=null && !accountDateEnd.equals("")) {
				parameter.put("E_BUDAT",sdf.parse(accountDateEnd.replace("-", "")));
			}else{
				parameter.put("E_BUDAT",null);
			}
			if (createDateStart!=null && !createDateStart.equals("")) {
				parameter.put("S_CPUDT",sdf.parse(createDateStart.replace("-", "")));
			}else{
				parameter.put("S_CPUDT",null);
			}
			if (createDateEnd!=null && !createDateEnd.equals("")) {
				parameter.put("E_CPUDT",sdf.parse(createDateEnd.replace("-", "")));
			}else{
				parameter.put("E_CPUDT",null);
			}
			//System.out.println(createDateStart.replace("-", "")+"===>createDateStart");
			parameter.put("S_LGORT", lgort);
			parameter.put("S_AUFNR", aufnr);
			parameter.put("X_FLAG", "x");
		
			productstoragerfc.sysnProductStorage(parameter);
		} catch (Exception e) {
			// TODO: handle exception
			log.info("生产入库同步错误"+e);
			e.printStackTrace();
			addActionError("生产入库同步失败");
			return "ERROR";
		}
		return SUCCESS;
	}
	
	
	
	
	public String getmoudlelist(){
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
		map.put("matnr", matnr);
		map.put("funid", funid);
		//查询条件
		/*if (pager.is_search() == true && Param != null)
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
		}*/
		UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
		String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
		map.put("matmr", matmr);
		pager = unitdistributeModelService.getUBMList(pager, map);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(UnitdistributeModel.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	
	
	
	
	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}

	public String getAufnr() {
		return aufnr;
	}

	public void setAufnr(String aufnr) {
		this.aufnr = aufnr;
	}

	public String getCreateDateStart() {
		return createDateStart;
	}

	public void setCreateDateStart(String createDateStart) {
		this.createDateStart = createDateStart;
	}

	public String getCreateDateEnd() {
		return createDateEnd;
	}

	public void setCreateDateEnd(String createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public String getWbid()
	{
		return wbid;
	}

	public void setWbid(String wbid)
	{
		this.wbid = wbid;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getFunid() {
		return funid;
	}

	public void setFunid(String funid) {
		this.funid = funid;
	}

	public List<String> getMoudles() {
		return moudles;
	}

	public void setMoudles(List<String> moudles) {
		this.moudles = moudles;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}


	public String getTeamid() {
		return teamid;
	}

	public void setTeamid(String teamid) {
		this.teamid = teamid;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}


	
}