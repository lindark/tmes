package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessHandoverAll;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DailyWorkService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.ProcessHandoverAllService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * @author Reece 2016/3/3
 * 后台Action类 - 报工
 */

@ParentPackage("admin")
public class DailyWorkAction extends BaseAdminAction {
	private static Logger log = Logger.getLogger(DailyWorkAction.class);  
	private static final long serialVersionUID = 352880047222902914L;

	private static final String CONFIRMED = "1";
	// private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";
	private static final String steus="PP01";
	private static final String UNITCODE = "CAR";

	private DailyWork dailyWork;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;
	private List<Process> allProcess;
	private String cardnumber;// 刷卡卡号
	//private Integer ratio;// 箱与个的转换比率
	private String module;//模具
	private String maktx;
	private String state;
	private String start;
	private String end;
	private String matnr;
	private String loginid;

	@Resource
	private DailyWorkService dailyWorkService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private ProcessService processService;
	@Resource
	private ProcessRouteService processRouteService;
	@Resource
	private ProductsService productsService;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private ProcessRouteService processrouteservice;
	@Resource
	private UnitConversionService unitConversionService;
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private UnitdistributeProductService unitdistributeProductService;
	@Resource
	private UnitdistributeModelService unitdistributeModelService;
	@Resource
	private TempKaoqinService tempKaoqinService;
	@Resource
	private ProcessHandoverAllService processHandoverAllService;
	
	//报工记录表 @author Reece 2016/3/8
	public String history() {
		return "history";
	}
	
	//报工记录表 @author Reece 2016/3/8
	public String historylist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件,复杂查询
			if (!filters.equals("")) {
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String, Class<jqGridSearchDetailTo>> m = new HashMap<String, Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		try {
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("matnr") != null) {
					String matnr = obj.getString("matnr")
							.toString();
					map.put("matnr", matnr);
				}
				if (obj.get("maktx") != null) {
					String maktx = obj.getString("maktx")
							.toString();
					map.put("maktx", maktx);
				}
				if (obj.get("start") != null) {
					String start = obj.getString("start").toString();
					map.put("start", start);
				}
				if (obj.get("end") != null) {
					String end = obj.getString("end").toString();
					map.put("end", end);
				}
				if (obj.get("start") != null && obj.get("end") != null) {
					String start = obj.get("start").toString();
					String end = obj.get("end").toString();
					map.put("start", start);
					map.put("end", end);
				}
				
				if (obj.get("state") != null) {
					String state = obj.getString("state").toString();
					map.put("state", state);
				}
			}
			pager = dailyWorkService.historyjqGrid(pager, map);
			List<DailyWork> dailyWorkList = pager.getList();
			List<DailyWork> lst = new ArrayList<DailyWork>();
			for (int i = 0; i < dailyWorkList.size(); i++) {
				DailyWork dailyWork = (DailyWork) dailyWorkList.get(i);
				dailyWork.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "dailyWorkState", dailyWork.getState()));
				if (dailyWork.getConfirmUser() != null) {
					dailyWork.setAdminName(dailyWork.getConfirmUser().getName());
				}
				/*dailyWork.setResponseName(processService.get("processCode",
						dailyWork.getProcessCode()).getProcessName());*/
				dailyWork.setCreateName(dailyWork.getCreateUser().getName());
				dailyWork.setMaktx(workingBillService.get(
						dailyWork.getWorkingbill().getId()).getMaktx());
				dailyWork.setWorkingbillCode(dailyWork.getWorkingbill()
						.getWorkingBillCode());
				dailyWork.setProductDate(dailyWork.getWorkingbill().getProductDate());
				dailyWork.setMatnr(dailyWork.getWorkingbill().getMatnr());
				/*dailyWork.setXmoudle(ThinkWayUtil.getDictValueByDictKey(dictService,
						"moudleType", dailyWork.getMoudle()));*/
				dailyWork.setXmoudle(dailyWork.getMoudle());
//				if(dailyWork.getCONF_CNT() !=null){
//					dailyWork.setNo(dailyWork.getCONF_CNT().replaceAll("^(0+)", ""));
//					String str = dailyWork.getCONF_CNT().replaceAll("^(0+)", "");
//					System.out.println(str);
//				}
//				if(dailyWork.getCONF_NO() !=null){
//				    dailyWork.setCnt(dailyWork.getCONF_NO().replaceAll("^(0+)", ""));
//				}
				lst.add(dailyWork);
			}
			pager.setList(lst);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(DailyWork.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	//Excel导出 @author Reece 2016/3/8
		public String excelexport(){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("matnr", matnr);
			map.put("maktx", maktx);
			map.put("state", state);
			map.put("start", start);
			map.put("end", end);
			
			
			List<String> header = new ArrayList<String>();
			List<Object[]> body = new ArrayList<Object[]>();
	        header.add("随工单号");
	        header.add("产品编码");
	        header.add("产品名称");
	        header.add("生产日期");
	        header.add("报工数量");
	        header.add("模具");
	        header.add("确认号");
	        header.add("计数器");
	        header.add("报工日期");
	        header.add("创建人");
	        header.add("确认人");
	        header.add("状态");
	        
	        List<Object[]> workList = dailyWorkService.historyExcelExport(map);
	        for(int i=0;i<workList.size();i++){
	        	Object[] obj = workList.get(i);
	        	DailyWork dailywork = (DailyWork) obj[0];
	        	WorkingBill workingbill = (WorkingBill)obj[1];
	        	
	        	Object[] bodyval = {workingbill.getWorkingBillCode(),workingbill.getMatnr(),workingbill.getMaktx()
	        			            ,workingbill.getProductDate(),dailywork.getEnterAmount()
	        						,dailywork.getMoudle()
	        						,dailywork.getCONF_NO(),dailywork.getCONF_CNT()
	        						,dailywork.getCreateDate(),dailywork.getCreateUser()==null?"":dailywork.getCreateUser().getName()
	        						,dailywork.getConfirmUser()==null?"":dailywork.getConfirmUser().getName(),ThinkWayUtil.getDictValueByDictKey(dictService, "dailyWorkState", dailywork.getState())};
	        	body.add(bodyval);
	        }
			
			try {
				String fileName = "报工记录表"+".xls";
				setResponseExcel(fileName);
				ExportExcel.exportExcel("报工记录表", header, body, getResponse().getOutputStream());
				getResponse().getOutputStream().flush();
			    getResponse().getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
	/**
	 * 跳转list 页面
	 * 
	 * @return
	 */
	public String list() {
		try{
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		/*admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行报工操作!");
			return ERROR;
		}*/
		workingbill = workingBillService.get(workingBillId);
		}catch(RuntimeException e){
			e.printStackTrace();
			//System.out.println("运行错误");
			log.info(e);
		}
		return LIST;
	}

	
	
	// 查看
	public String view() {
		dailyWork = dailyWorkService.load(id);
		workingbill = dailyWork.getWorkingbill();	
		/*module = ThinkWayUtil.getDictValueByDictKey(dictService,
				"moudleType", dailyWork.getMoudle());*/
		return VIEW;
	}	

	public String add() {
		
		Admin admin = adminService.get(loginid);
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);		
		if(!ThinkWayUtil.isPass(admin)){
			addActionError("您当前未上班,不能进行报工操作!");
			return ERROR;
		}
		
		List<ProcessHandoverAll> lists = processHandoverAllService.getListOfAllProcess(admin.getProductDate(),admin.getShift(),admin.getTeam().getFactoryUnit().getId());
		if(lists!=null && lists.size() != 0){
			addActionError("当前班次总体交接已完成!");
			return ERROR;
		}
		
		workingbill = workingBillService.get(workingBillId);
		String  workCenter = workingbill.getWorkcenter();
		FactoryUnit fu = factoryUnitService.get("workCenter", workCenter);
		HashMap<String, String> map = new HashMap<String, String>();
		String matnr="";
		String funid="";
		if(fu!=null){
			funid =fu.getId(); 
		}
		matnr = workingbill.getMatnr();
		map.put("matnr", matnr);
		map.put("funid", funid);
		UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
		if(pager==null){
			pager=new Pager();
		}
		if(unitdistributeProduct!=null){
			String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
			map.put("matmr", matmr);
			pager = unitdistributeModelService.getUBMList(pager, map);
		}
		
//		String productCode = workingbill.getMatnr();
//		allProcess = new ArrayList<Process>();
//		List<ProcessRoute> processRouteList = new ArrayList<ProcessRoute>();
//		processRouteList = processRouteService
//				.getProcessRouteByProductCode(productCode);
//		for (int i = 0; i < processRouteList.size(); i++) {
//			ProcessRoute processroute = processRouteList.get(i);
//			Process process = processService.get("processCode",processroute.getProcessCode());
//			allProcess.add(process);
//		}
		return INPUT;
	}

	// 编辑
	public String edit() {
		
		Admin admin = adminService.get(loginid);
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);			
		if(!ThinkWayUtil.isPass(admin)){
			addActionError("您当前未上班,不能进行报工操作!");
			return ERROR;
		}
		
		List<ProcessHandoverAll> lists = processHandoverAllService.getListOfAllProcess(admin.getProductDate(),admin.getShift(),admin.getTeam().getFactoryUnit().getId());
		if(lists!=null && lists.size() != 0){
			addActionError("当前班次总体交接已完成!");
			return ERROR;
		}
		
		dailyWork = dailyWorkService.load(id);
		workingbill = workingBillService.get(workingBillId);
		String  workCenter = workingbill.getWorkcenter();
		FactoryUnit fu = factoryUnitService.get("workCenter", workCenter);
		HashMap<String, String> map = new HashMap<String, String>();
		String matnr="";
		String funid="";
		if(fu!=null){
			funid =fu.getId(); 
		}
		matnr = workingbill.getMatnr();
		map.put("matnr", matnr);
		map.put("funid", funid);
		UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
		if(pager==null){
			pager=new Pager();
		}
		if(unitdistributeProduct!=null){
			String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
			map.put("matmr", matmr);
			pager = unitdistributeModelService.getUBMList(pager, map);
		}
//		String productCode = workingbill.getMatnr();
//		Integer version = workingbill.getProcessversion();
//		if (version == null) {
//			version = processRouteService.getMaxVersion(productsService.get(
//					"productsCode", productCode).getId());
//		}
//		allProcess = new ArrayList<Process>();
//		List<ProcessRoute> processRouteList = new ArrayList<ProcessRoute>();
//		processRouteList = processRouteService.getProcessRouteByVersionAndCode(
//				version, productCode);
//		for (int i = 0; i < processRouteList.size(); i++) {
//			ProcessRoute processroute = processRouteList.get(i);
//			Process process = processService.get("processCode",processroute.getProcessCode());
//			allProcess.add(process);
//		}
		return INPUT;
	}

	// 保存
	public String creditsave() throws Exception {
		try{
		//WorkingBill workingBill = workingbillService.get(dailyWork.getWorkingbill().getId());
		//Products products = productsService.getByPcode(workingBill.getMatnr());
		//String time = ThinkWayUtil.formatdateDate(workingBill.get);
		/*List<ProcessRoute> processrouteList=processrouteservice.findProcessRoute(workingBill.getAufnr(),workingBill.getProductDate());
		List<String> ProcessRouteIdList = new ArrayList<String>();
		for(ProcessRoute pr:processrouteList){
			ProcessRouteIdList.add(pr.getId());
		}
		String process = processRouteService.getProcess(ProcessRouteIdList,steus);
		if (process == null || process.equals("")) {
	           return ajaxJsonErrorMessage("未找到所对应的工序!");
			}
		dailyWork.setProcessCode(process);*/
		if (dailyWork.getEnterAmount() == null
				|| String.valueOf(dailyWork.getEnterAmount()).matches(
						"^[0-9]*[1-9][0-9]*$ ")) {
			return ajaxJsonErrorMessage("报工数量必须为零或正整数!");
		}
		admin = adminService.getByCardnum(cardnumber);
		dailyWork.setCreateUser(admin);
		dailyWorkService.save(dailyWork);
		/*
		 * redirectionUrl = "daily_work!list.action?workingBillId=" +
		 * dailyWork.getWorkingbill().getId();
		 */
		}catch(Exception e){
			e.printStackTrace();
			//System.out.println("运行异常");
			log.info(e);
		}
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 更新
	public String creditupdate() throws Exception {
		if (dailyWork.getEnterAmount() == null
				|| String.valueOf(dailyWork.getEnterAmount()).matches(
						"^[0-9]*[1-9][0-9]*$ ")) {
			return ajaxJsonErrorMessage("报工数量必须为零或正整数!");
		}
		DailyWork persistent = dailyWorkService.load(id);
		BeanUtils.copyProperties(dailyWork, persistent, new String[] { "id" });
		dailyWorkService.update(persistent);
		/*
		 * redirectionUrl = "daily_work!list.action?workingBillId=" +
		 * dailyWork.getWorkingbill().getId();
		 */
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡确认
	public String creditapproval() {
		/*UnitConversion unitconversion = unitConversionService.getRatioByMatnr(workingbill.getMatnr(),UNITCODE);
		ratio = unitconversion.getConversationRatio().intValue();		
		if (ratio == null || ratio.equals("")) {
           return ajaxJsonErrorMessage("请在计量单位转换表中维护物料编码对应的换算数据!");
		}*/
		
		/*Admin  card_admin= adminService.getByCardnum(cardnumber);
		card_admin = tempKaoqinService.getAdminWorkStateByAdmin(card_admin);		
		if(!ThinkWayUtil.isPass(card_admin)){
			
			return ajaxJsonErrorMessage("您当前未上班,不能进行报工操作!");
		}
		*/
		try {
			Admin admin = adminService.get(loginid);
			admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);		
			if(!ThinkWayUtil.isPass(admin)){
				
				return ajaxJsonErrorMessage("您当前未上班,不能进行报工操作!");
			}
			
			List<ProcessHandoverAll> lists = processHandoverAllService.getListOfAllProcess(admin.getProductDate(),admin.getShift(),admin.getTeam().getFactoryUnit().getId());
			if(lists!=null && lists.size() != 0){
				addActionError("当前班次总体交接已完成!");
				return ERROR;
			}
			
			WorkingBill workingbill = workingBillService.get(workingBillId);
			
			
			ids = id.split(",");
			List<DailyWork> dailyList = new ArrayList<DailyWork>();
			for (int i = 0; i < ids.length; i++) {
				dailyWork = dailyWorkService.load(ids[i]);
				if (CONFIRMED.equals(dailyWork.getState())) {
					return ajaxJsonErrorMessage("已确认的无须再确认!");
				}
				if (UNDO.equals(dailyWork.getState())) {
					return ajaxJsonErrorMessage("已撤销的无法再确认！");
				}
			//	dailyWork.setEnterAmount(dailyWork.getEnterAmount());
				WorkingBill workingBill = workingbillService.get(dailyWork.getWorkingbill().getId());
				//Products products = productsService.getByPcode(workingBill.getMatnr());
				//String time = ThinkWayUtil.formatdateDate(workingBill.get);
				List<ProcessRoute> processrouteList=processrouteservice.findProcessRoute(workingBill.getAufnr());
				List<String> ProcessRouteIdList = new ArrayList<String>();
				if(processrouteList	!=	null){
					for(ProcessRoute pr:processrouteList){
						ProcessRouteIdList.add(pr.getId());
					}
				}else{
					if(processrouteList==null || ProcessRouteIdList.size()==0){
						return ajaxJsonErrorMessage("工艺路线未找到");
					}
				}
				String process = processRouteService.getProcess(ProcessRouteIdList,steus);
				if (process == null || process.equals("")) {
			           return ajaxJsonErrorMessage("未找到所对应的工序!");
					}
				dailyWork.setProcessCode(process);
				dailyList.add(dailyWork);
			}
			//List<DailyWork> list = dailyWorkService.get(ids);
			
			dailyWorkService.updateState(dailyList,CONFIRMED,workingBillId,cardnumber);
			
			workingbill = workingBillService.get(workingBillId);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put(STATUS, SUCCESS);
			hashmap.put(MESSAGE, "您的操作已成功");
			hashmap.put("totalAmount", workingbill.getDailyWorkTotalAmount()
					.toString());
			return ajaxJson(hashmap);
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO操作失败");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}
	}
	
	//input页面的刷卡确认
	public String creditsubmit(){
		
		
		return ajaxJsonSuccessMessage("您的操作已成功!");	
	}
	

	// 刷卡撤销
	public String creditundo() {
		
	
		/*UnitConversion unitconversion = unitConversionService.getRatioByMatnr(workingbill.getMatnr(),UNITCODE);
		ratio = unitconversion.getConversationRatio().intValue();
		if (ratio == null || ratio.equals("")) {
           return ajaxJsonErrorMessage("请在计量单位转换表中维护物料编码对应的换算数据!");
		}*/
		try {
			Admin admin = adminService.get(loginid);
			admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);		
			if(!ThinkWayUtil.isPass(admin)){
				
				return ajaxJsonErrorMessage("您当前未上班,不能进行报工操作!");
			}
			
			List<ProcessHandoverAll> lists = processHandoverAllService.getListOfAllProcess(admin.getProductDate(),admin.getShift(),admin.getTeam().getFactoryUnit().getId());
			if(lists!=null && lists.size() != 0){
				addActionError("当前班次总体交接已完成!");
				return ERROR;
			}
			
			WorkingBill workingbill = workingBillService.get(workingBillId);
			
			ids = id.split(",");
			List<DailyWork> dailyList = new ArrayList<DailyWork>();
			for (int i = 0; i < ids.length; i++) {
				dailyWork = dailyWorkService.load(ids[i]);
				if (UNDO.equals(dailyWork.getState())) {
					return ajaxJsonErrorMessage("已撤销的无法再撤销！");
				}
				//dailyWork.setEnterAmount(dailyWork.getEnterAmount());
				dailyList.add(dailyWork);
			}
			List<DailyWork> list = dailyWorkService.get(ids);
			
			dailyWorkService.updateState2(list,UNDO,workingBillId,cardnumber);
			workingbill = workingBillService.get(workingBillId);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put(STATUS, SUCCESS);
			hashmap.put(MESSAGE, "您的操作已成功");
			hashmap.put("totalAmount", workingbill.getDailyWorkTotalAmount()
					.toString());
			return ajaxJson(hashmap);
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO操作失败");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}

	}
   
	
	
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件,复杂查询
			if (!filters.equals("")) {
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String, Class<jqGridSearchDetailTo>> m = new HashMap<String, Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}

		pager = dailyWorkService.findPagerByjqGrid(pager, map, workingBillId);
		List<DailyWork> dailyWorkList = pager.getList();
		List<DailyWork> lst = new ArrayList<DailyWork>();
		for (int i = 0; i < dailyWorkList.size(); i++) {
			DailyWork dailyWork = (DailyWork) dailyWorkList.get(i);
			dailyWork.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "dailyWorkState", dailyWork.getState()));
			if (dailyWork.getConfirmUser() != null) {
				dailyWork.setAdminName(dailyWork.getConfirmUser().getName());
			}
			/*dailyWork.setXmoudle(ThinkWayUtil.getDictValueByDictKey(dictService,
					"moudleType", dailyWork.getMoudle()));*/
			dailyWork.setXmoudle(dailyWork.getMoudle());
			/*dailyWork.setResponseName(processService.get("processCode",
					dailyWork.getProcessCode()).getProcessName());*/
			dailyWork.setCreateName(dailyWork.getCreateUser().getName());
			lst.add(dailyWork);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(DailyWork.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	public String getWorkingBillId() {
		return workingBillId;
	}

	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}

	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public DailyWork getDailyWork() {
		return dailyWork;
	}

	public void setDailyWork(DailyWork dailyWork) {
		this.dailyWork = dailyWork;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Process> getAllProcess() {
		return allProcess;
	}

	public void setAllProcess(List<Process> allProcess) {
		this.allProcess = allProcess;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public List<Dict> getAllMoudle() {
		return dictService.getList("dictname", "moudleType");
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	
	
}