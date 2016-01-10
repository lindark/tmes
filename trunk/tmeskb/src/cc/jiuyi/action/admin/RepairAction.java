package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.RepairRfc;
import cc.jiuyi.sap.rfc.impl.RepairRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.RepairService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 返修
 * 
 */
@ParentPackage("admin")
public class RepairAction extends BaseAdminAction {

	private static final long serialVersionUID = -5187671258106950991L;

	private static final String CONFIRMED = "1";
	private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";

	private Repair repair;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;
	private List<Process> allProcess;
	private String cardnumber;// 刷卡卡号

	@Resource
	private RepairService repairService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;
	@Resource
	private ProcessService processService;
	@Resource
	private ProcessRouteService processRouteService;
	@Resource
	private ProductsService productsService;

	public String list() {
		admin = adminService.getLoginAdmin();
		workingbill = workingBillService.get(workingBillId);
		return "list";
	}

	// 添加
	public String add() {
		workingbill = workingBillService.get(workingBillId);
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
		List<ProcessRoute> processRouteList= processRouteService.findProcessRoute(aufnr, productDate);
		
		allProcess = new ArrayList<Process>();
		for (int i = 0; i < processRouteList.size(); i++) {
			ProcessRoute processroute = processRouteList.get(i);
			cc.jiuyi.entity.Process process = processService.get("processCode",processroute.getProcessCode());
			allProcess.add(process);
		}
		return INPUT;
	}

	// 历史返修记录
	public String history() {
		return "history";
	}

	// 编辑
	public String edit() {
//		repair = repairService.load(id);
//		workingbill = workingBillService.get(workingBillId);
//		Integer version = workingbill.getProcessversion();
//		String productCode = workingbill.getMatnr();
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
//			cc.jiuyi.entity.Process process = processService.get("processCode",processroute.getProcessCode());
//			allProcess.add(process);
//		}
		return INPUT;
		
		//TODO 返修收货未完成
	}

	// 保存
	public String creditsave() throws Exception {
		if (repair.getRepairAmount() == null
				|| String.valueOf(repair.getRepairAmount()).matches(
						"^[0-9]*[1-9][0-9]*$ ")) {
			return ajaxJsonErrorMessage("返修数量必须为零或正整数!");
		}
		admin = adminService.getByCardnum(cardnumber);
		repair.setCreateUser(admin);
		repairService.save(repair);
		/*
		 * redirectionUrl = "repair!list.action?workingBillId=" +
		 * repair.getWorkingbill().getId();
		 */
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 更新
	public String creditupdate() throws Exception {
		if (repair.getRepairAmount() == null
				|| String.valueOf(repair.getRepairAmount()).matches(
						"^[0-9]*[1-9][0-9]*$ ")) {
			return ajaxJsonErrorMessage("返修数量必须为零或正整数!");
		}
		Repair persistent = repairService.load(id);
		BeanUtils.copyProperties(repair, persistent, new String[] { "id" });
		repairService.update(persistent);
		/*
		 * redirectionUrl = "repair!list.action?workingBillId=" +
		 * repair.getWorkingbill().getId();
		 */
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡确认
	public String creditapproval() {
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			repair = repairService.load(ids[i]);
			if (CONFIRMED.equals(repair.getState())) 
			{
				return ajaxJsonErrorMessage("已确认的无须再确认!");
			}
			if (UNDO.equals(repair.getState())) 
			{
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}
		}
		List<Repair> list = repairService.get(ids);
		repairService.updateState(list, CONFIRMED, workingBillId, cardnumber);
		workingbill = workingBillService.get(workingBillId);
		String str=toSAP(list,workingbill);
		String isSuccess=ERROR;
		if("S".equals(str))
		{
			isSuccess=SUCCESS;
			str="您的操作已成功!";
		}
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, isSuccess);
		hashmap.put(MESSAGE, str);
		hashmap.put("totalAmount", workingbill.getTotalRepairAmount().toString());
		return ajaxJson(hashmap);
	}

	// 刷卡撤销
	public String creditundo() {
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			repair = repairService.load(ids[i]);
			if (UNDO.equals(repair.getState())) {
				// addActionError("已撤销的无法再撤销！");
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		List<Repair> list = repairService.get(ids);
		repairService.updateState(list, UNDO, workingBillId, cardnumber);
		workingbill = workingBillService.get(workingBillId);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalAmount", workingbill.getTotalRepairAmount()
				.toString());
		return ajaxJson(hashmap);
	}

	public String browser() {
		return "browser";
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
		pager = repairService.historyjqGrid(pager, map);
		List<Repair> repairList = pager.getList();
		List<Repair> lst = new ArrayList<Repair>();
		for (int i = 0; i < repairList.size(); i++) {
			Repair repair = (Repair) repairList.get(i);
			repair.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "repairState", repair.getState()));
			if (repair.getConfirmUser() != null) {
				repair.setAdminName(repair.getConfirmUser().getName());
			}
			repair.setCreateName(repair.getCreateUser().getName());
			repair.setWorkingbillCode(workingBillService.get(
					repair.getWorkingbill().getId()).getWorkingBillCode());
			repair.setMaktx(workingBillService.get(
					repair.getWorkingbill().getId()).getMaktx());
			repair.setResponseName(processService.get("processCode",
					repair.getProcessCode()).getProcessName());
			lst.add(repair);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Repair.class));// 排除有关联关系的属性字段
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

		pager = repairService.findPagerByjqGrid(pager, map, workingBillId);
		List<Repair> repairList = pager.getList();
		List<Repair> lst = new ArrayList<Repair>();
		for (int i = 0; i < repairList.size(); i++) {
			Repair repair = (Repair) repairList.get(i);
			repair.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "repairState", repair.getState()));
			if (repair.getConfirmUser() != null) {
				repair.setAdminName(repair.getConfirmUser().getName());
			}
			repair.setCreateName(repair.getCreateUser().getName());
			if(repair.getProcessCode()!=null)
			{
				repair.setResponseName(processService.get("processCode",repair.getProcessCode()).getProcessName());
			}
			lst.add(repair);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Repair.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}

	/**
	 * 与SAP交互   退料262  905
	 * list 主表数据   wbid随工单对象
	 * @return
	 * @author gyf
	 */
	public String toSAP(List<Repair>list,WorkingBill wb)
	{
		List listall =this.repairService.getSAPMap(list,wb,cardnumber);
		List<Map<Object,Object>> list1 =(List<Map<Object, Object>>) listall.get(0);
		List<Map<Object,Object>> list2 =(List<Map<Object, Object>>) listall.get(1);
		List<Map<Object,Object>>list_sapreturn=null;
		if(list1.size()==0||list2.size()==0)
		{
			return "对应物料为空!";
		}
		try
		{
			String e_msg="";
			boolean flag=true;
			RepairRfc repairRfc=new RepairRfcImpl();
			//调用SAP，执行数据交互，返回List，并判断数据交互中是否成功，成功的更新本地数据库，失败的则不保存
			list_sapreturn=new ArrayList<Map<Object,Object>>(repairRfc.repairCrt(list1,list2));
			e_msg="";
			for(int i=0;i<list_sapreturn.size();i++)
			{
				Map<Object,Object>m=list_sapreturn.get(i);
				/**出现问题*/
				if("E".equalsIgnoreCase(m.get("E_TYPE").toString()))
				{
					flag=false;
					e_msg+=m.get("E_MESSAGE").toString();
				}
				else
				{
					/**与SAP交互没有问题,更新本地数据库*/
					this.repairService.updateMyData(m,cardnumber);
				}
			}
			if(!flag)
			{
				return e_msg;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "IO出现异常，请联系系统管理员";
		} catch (Exception e) {
			e.printStackTrace();
			return "系统出现问题，请联系系统管理员";
		}
		return "S";
	}
	
	public Repair getRepair() {
		return repair;
	}

	public void setRepair(Repair repair) {
		this.repair = repair;
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

}
