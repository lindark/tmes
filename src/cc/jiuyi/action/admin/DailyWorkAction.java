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

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DailyWorkService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 报工
 */

@ParentPackage("admin")
public class DailyWorkAction extends BaseAdminAction {

	private static final long serialVersionUID = 352880047222902914L;

	private static final String CONFIRMED = "1";
	// private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";
	private static final String steus="PP02";
	private static final String UNITCODE = "CAR";

	private DailyWork dailyWork;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;
	private List<Process> allProcess;
	private String cardnumber;// 刷卡卡号
	private Integer ratio;// 箱与个的转换比率

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

	/**
	 * 跳转list 页面
	 * 
	 * @return
	 */
	public String list() {
		admin = adminService.getLoginAdmin();
		admin = adminService.getLoginAdmin();
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行报工操作!");
			return ERROR;
		}
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}

	public String history() {
		return "history";
	}

	public String add() {
		workingbill = workingBillService.get(workingBillId);
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
		dailyWork = dailyWorkService.load(id);
		workingbill = workingBillService.get(workingBillId);
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
		WorkingBill workingBill = workingbillService.get(dailyWork.getWorkingbill().getId());
		//Products products = productsService.getByPcode(workingBill.getMatnr());
		//String time = ThinkWayUtil.formatdateDate(workingBill.get);
		System.out.println(workingBill.getProductDate());
		List<ProcessRoute> processrouteList=processrouteservice.findProcessRoute(workingBill.getAufnr(),workingBill.getProductDate());
		List<String> ProcessRouteIdList = new ArrayList<String>();
		for(ProcessRoute pr:processrouteList){
			ProcessRouteIdList.add(pr.getId());
		}
		String process = processRouteService.getProcess(ProcessRouteIdList,steus);
		if (process == null || process.equals("")) {
	           return ajaxJsonErrorMessage("未找到所对应的工序!");
			}
		dailyWork.setProcessCode(process);
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
		WorkingBill workingbill = workingBillService.get(workingBillId);
		ratio = unitConversionService.getRatioByMatnr(workingbill.getMatnr(),UNITCODE);
		//ratio = unitConversionService.getRatioByCode(UNITCODE);
		if (ratio == null || ratio.equals("")) {
           return ajaxJsonErrorMessage("请在计量单位转换表中维护物料编码对应的换算数据!");
		}
		
		try {
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
				dailyWork.setEnterAmount(dailyWork.getEnterAmount()*ratio);
				dailyList.add(dailyWork);
			}
			List<DailyWork> list = dailyWorkService.get(ids);
			
			dailyWorkService.updateState(dailyList,CONFIRMED,workingBillId,ratio.toString(),cardnumber);
			
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
		WorkingBill workingbill = workingBillService.get(workingBillId);
		ratio = unitConversionService.getRatioByMatnr(workingbill.getMatnr(),UNITCODE);
		//ratio = unitConversionService.getRatioByCode(UNITCODE);
		if (ratio == null || ratio.equals("")) {
           return ajaxJsonErrorMessage("请在计量单位转换表中维护物料编码对应的换算数据!");
		}
		try {
			ids = id.split(",");
			List<DailyWork> dailyList = new ArrayList<DailyWork>();
			for (int i = 0; i < ids.length; i++) {
				dailyWork = dailyWorkService.load(ids[i]);
				if (UNDO.equals(dailyWork.getState())) {
					return ajaxJsonErrorMessage("已撤销的无法再撤销！");
				}
				dailyWork.setEnterAmount(dailyWork.getEnterAmount()*ratio);
				dailyList.add(dailyWork);
			}
			List<DailyWork> list = dailyWorkService.get(ids);
			
			dailyWorkService.updateState2(list,UNDO,workingBillId,ratio.toString(), cardnumber);
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
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("maktx") != null) {
				String maktx = obj.getString("maktx")
						.toString();
				map.put("maktx", maktx);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
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
			lst.add(dailyWork);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(DailyWork.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
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

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}
	
	
}