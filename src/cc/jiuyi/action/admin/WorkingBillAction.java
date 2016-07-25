package cc.jiuyi.action.admin;

import java.io.IOException;
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
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.OrdersService;
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
 * 后台Action类 - WorkingBill
 */

@ParentPackage("admin")
public class WorkingBillAction extends BaseAdminAction {

	private static final long serialVersionUID = 1341979251224008699L;
	public static Logger log = Logger.getLogger(WorkingBill.class);
	
	private WorkingBill workingbill;
	private List<Material> materialList;
	private String aufnr;
	private String start;
	private String end;
	private String wbid;
	private String matnr;
	private String funid;
	private List<String> moudles;
	private String info;
	private String workcode;
	private String teamid;
	private String teamName;
	private String mark;
	private String empid;
	private List<Team> teamList;//班组
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private WorkingBillRfc workingbillrfc;
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
	private WorkingBillService workingbillservice;
	@Resource
	private TeamService teamservice;
	@Resource
	private KaoqinService kaoqinService;
	
	//条码打印
	public String barcodePrint(){
		JSONObject json = new JSONObject();
		try{
			WorkingBill workingbill = workingbillService.get(wbid);
			UnitConversion unitconversion = unitconversionservice.getRatioByMatnr(workingbill.getMatnr(), "CAR");
			
			json.put("workingbillCode", workingbill.getWorkingBillCode());//随工单号
			json.put("matnr", workingbill.getMatnr());//物料号
			json.put("maktx", workingbill.getMaktx());//物料描述
			json.put("charg", ThinkWayUtil.getCharg());//批次
			json.put("amount", unitconversion.getConversationRatio());//数量
			json.put("time",ThinkWayUtil.formatDateByPattern(new Date(), "HH:mm:ss") );
		}catch(Exception e){
			e.printStackTrace();
		}
		//System.out.println(json.toString());
		return ajaxJson(json.toString());
	}
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 编辑
	public String edit() {
		return INPUT;
	}

	// List 页面
	public String list() {
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
				String start = ThinkWayUtil.null2String(param.get("start"));
				String end = ThinkWayUtil.null2String(param.get("end"));
				String workingBillCode = ThinkWayUtil.null2String(param.get("workingBillCode"));//随工单
				String workCenter = ThinkWayUtil.null2String(param.get("workCenter"));//随工单
				map.put("start", start);
				map.put("end", end);
				map.put("workingBillCode", workingBillCode);
				map.put("workCenter", workCenter);
			}
		}
		
		pager = workingbillService.findPagerByjqGrid(pager, map);
		
		List<WorkingBill> pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
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
			
		}
		pager.setList(pagerlist);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(WorkingBill.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		//System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	// 删除
	public String delete() {
		ids = id.split(",");
		workingbillService.updateisdel(ids,"Y");
		redirectionUrl = "working_bill!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	
	//手工同步随工单弹窗
	public String browser(){
		return "browser";
	}

	//同步
	public String sync() throws SchedulerException {
		/*System.out.println(aufnr+","+start+","+end);
		
		
		SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = gSchedulerFactory.getScheduler();
		int state = scheduler.getTriggerState("cronTriggerWorkingBill", "DEFAULT");
		System.out.println(state);*/
		log.info("同步生产订单开始..."+workcode);
		try {
			Date newdate = new Date();
			//String startdate = String.format("%tF%n", newdate);
			String starttime = String.format("%tT%n", newdate);
			//newdate = DateUtils.addDays(newdate, 1);
			//String enddate = String.format("%tF%n", newdate);
			String endtime = String.format("%tT%n", newdate);
			//FactoryUnit factoryunit = this.factoryUnitService.get("factoryUnitCode",workcode);
			//String workshopcode  = factoryunit.getWorkShop().getWorkShopCode();//车间编码
			String workshopcode = "201";
			String[] propertyNames = {"factoryunit.factoryUnitCode","state","isDel"};
			Object[] propertyValues = {workcode,"1","N"};
			List<UnitdistributeProduct> unitdistributeList = unitdistributeProductService.getList(propertyNames,propertyValues);
			workingbillrfc.syncRepairorderAll(start, end,starttime,endtime,"",workshopcode,unitdistributeList,workcode);
		} catch (IOException e){
			log.info("同步生产订单出错"+e);
			e.printStackTrace();
		} catch (CustomerException e){
			log.info("同步生产订单出错"+e);
			e.printStackTrace();
		} catch (Exception e){
			log.info("错误"+e);
			e.printStackTrace();
		}
		
		log.info("同步生产订单结束..."+workcode);
		
		return SUCCESS;
	}
	
	
	//投入产出
	public String inout(){
		workingbill = workingbillService.get(this.workingbill.getId());
		materialList = materialservice.getMantrBom(workingbill.getMatnr());//获取产品对应的BOM信息
		return "intout";
	}
	
	// 保存
	@Validations(
			requiredStrings = { 
				@RequiredStringValidator(fieldName = "workingbill.workingBillCode", message = "随工单号不能为空!"),
				@RequiredStringValidator(fieldName = "workingbill.productDate", message = "生产日期不能为空!"),
				@RequiredStringValidator(fieldName = "workingbill.matnr", message = "产品编号不能为空!"),
				@RequiredStringValidator(fieldName = "workingbill.maktx", message = "产品描述不能为空!")
			},
			requiredFields = { 
				@RequiredFieldValidator(fieldName = "workingbill.planCount", message = "计划数量不能为空!")
			}
		)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		workingbillService.save(workingbill);
		redirectionUrl = "working_bill!list.action";
		return SUCCESS;
	}
	
	// 更新
	@Validations(
		requiredStrings = { 
			@RequiredStringValidator(fieldName = "workingbill.workingBillCode", message = "随工单号不能为空!"),
			@RequiredStringValidator(fieldName = "workingbill.productDate", message = "生产日期不能为空!"),
			@RequiredStringValidator(fieldName = "workingbill.matnr", message = "产品编号不能为空!"),
			@RequiredStringValidator(fieldName = "workingbill.maktx", message = "产品描述不能为空!")
		},
		requiredFields = { 
			@RequiredFieldValidator(fieldName = "workingbill.planCount", message = "计划数量不能为空!")
		}
	)
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		WorkingBill persistent = workingbillService.load(id);
		BeanUtils.copyProperties(workingbill, persistent, new String[]{"id", "createDate"});
		workingbillService.update(persistent);
		redirectionUrl = "working_bill!list.action";
		return SUCCESS;
	}
	//查询模具组号
	/**
	 * 进入弹框页面
	 */
	public String moudlelist()
	{
		if(pager==null){
			pager = new Pager();
		}
		info = "";
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		WorkingBill wb = workingbillService.get(id);
		
		if(admin.getProductDate() != null && admin.getShift() != null){
			List<WorkingBill> workingbillList = workingbillservice.getListWorkingBillByDate(admin);
			
			for(WorkingBill wbl : workingbillList){
				if(!id.equals(wbl.getId())){
					if("".equals(info)){
						info = wbl.getMoudle()==null?"":wbl.getMoudle();
					}else{
						info = info + "," + (wbl.getMoudle()==null?"":wbl.getMoudle());
					}
				}
			}
		}
		HashMap<String, String> map = new HashMap<String, String>();
		matnr="";
		funid="";
		
		
				String  workCenter = wb.getWorkcenter();
				FactoryUnit fu = factoryUnitService.get("factoryUnitCode", workCenter);
				if(fu!=null){
					funid =fu.getId(); 
				}
				matnr = wb.getMatnr();
				map.put("matnr", matnr);
				map.put("funid", funid);
				UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
				
				if(unitdistributeProduct!=null){
					String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
					map.put("matmr", matmr);
					pager = unitdistributeModelService.getUBMList(pager, map);
				}
				List<UnitdistributeModel> unitdistributeModelList = new ArrayList<UnitdistributeModel>();
				if(pager.getList()!=null && pager.getList().size()>0){
					String[] infos = info.split(",");
					moudles = new ArrayList<String>();
					if(wb.getMoudle()!=null){
						String[] ms = wb.getMoudle().split(",");
						for(String s : ms){
							moudles.add(s);
						}
					}
					for(int i=0;i<pager.getList().size();i++){
						UnitdistributeModel unitdistributeModel = (UnitdistributeModel) pager.getList().get(i);
						boolean flag = true;
						for(String in : infos){
							if(in.equals(unitdistributeModel.getStation())){
								flag = false;
							}
						}
						if(flag){
							unitdistributeModelList.add(unitdistributeModel);
						}
					}
					pager.setList(unitdistributeModelList);
				}
		return "moudlelist";
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
	
	public String updateWKInMoudel(){
		WorkingBill wb = workingbillService.get(wbid);
		/*String[] ids = id.split(",");
		List<UnitdistributeModel> unitdistributeModelList = unitdistributeModelService.get(ids);
		String moudle = "";
		if(unitdistributeModelList!=null && unitdistributeModelList.size()>0){
			for(UnitdistributeModel unitdistributeModel : unitdistributeModelList){
				if("".equals(moudle)){
					moudle = unitdistributeModel.getStation();
				}else{
					moudle = moudle + ","+unitdistributeModel.getStation();
				}
			}
			
		}*/
		wb.setMoudle(aufnr);
		workingbillService.update(wb);
		redirectionUrl = "admin!index.action";
		return SUCCESS;
	}
	
	/**
	 * 根据单元查找team
	 */
	public String findTeam(){
		FactoryUnit fun = factoryUnitService.get(funid);
		teamList = new ArrayList<Team>(fun.getTeam());
		workingbill = workingbillService.get(wbid);
		return "wk_team";
	}
	public String updateWKInTeam(){
		if(teamid!=null && !"".equals(teamid)){
			try {
				WorkingBill wk = workingbillService.get(wbid);
				Team oldTeam = wk.getTeam();
				Team t = teamservice.get(teamid);
				if(t != oldTeam){
					wk.setTeam(t);
					wk.setTeamName(teamName);
					wk.setZhuren(t.getZhuren());
					wk.setFuzhuren(t.getFuzhuren());
					wk.setMinister(t.getMinister());
					wk.setDeputy(t.getDeputy());
					workingbillService.update(wk);
				}
				Map<String, String> jsonMap = new HashMap<String, String>();
				jsonMap.put(STATUS, SUCCESS);
				jsonMap.put(MESSAGE, "保存成功");
				jsonMap.put("zhuren", t.getZhuren());
				jsonMap.put("fuzhuren", t.getFuzhuren());
				jsonMap.put("isNotSelect", "0");
				JSONObject jsonObject = JSONObject.fromObject(jsonMap);
				return ajax(jsonObject.toString(), "text/html");
			} catch (Exception e) {
				e.printStackTrace();
				log.info(e);
				return ajaxJsonErrorMessage("保存失败");
			}
		}else{
			WorkingBill wk = workingbillService.get(wbid);
			wk.setTeam(null);
			wk.setTeamName(null);
			wk.setZhuren(null);
			wk.setFuzhuren(null);
			wk.setMinister(null);
			wk.setDeputy(null);
			workingbillService.update(wk);
//			return ajaxJsonSuccessMessage("保存成功");
			Map<String, String> jsonMap = new HashMap<String, String>();
			jsonMap.put(STATUS, SUCCESS);
			jsonMap.put(MESSAGE, "保存成功");
			jsonMap.put("zhuren", null);
			jsonMap.put("fuzhuren", null);
			jsonMap.put("isNotSelect", "1");
			JSONObject jsonObject = JSONObject.fromObject(jsonMap);
			return ajax(jsonObject.toString(), "text/html");
		}
		
	}
	
	public String updatePerson(){
		try {
			WorkingBill wk = workingbillService.get(wbid);
			Kaoqin kaoqin = kaoqinService.get(empid);
			if(mark.equals("1")){
				wk.setFuzhuren(kaoqin.getEmpname());
			}else{
				wk.setZhuren(kaoqin.getEmpname());
			}
			workingbillService.update(wk);
			return ajaxJsonSuccessMessage("保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e);
			return ajaxJsonErrorMessage("保存失败");
		}
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
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

	public String getWorkcode() {
		return workcode;
	}

	public void setWorkcode(String workcode) {
		this.workcode = workcode;
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

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}

	
	
}