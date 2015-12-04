
package cc.jiuyi.action.admin;

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
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.RepairService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

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

	public String list() {
		admin = adminService.getLoginAdmin();
		workingbill = workingBillService.get(workingBillId);
		return "list";
	}

	// 添加
	public String add() {
		workingbill = workingBillService.get(workingBillId);
		List<WorkingBill> workingbills = new ArrayList<WorkingBill>();
		workingbills.add(workingbill);
		allProcess = processService.findProcess(workingbills);
		return INPUT;
	}

	// 保存
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "repair.repairAmount", min = "0", message = "返修数量必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if(repair.getProcessResponse().getId().equals("")){
			repair.setProcessResponse(null);
		}
		admin = adminService.loadLoginAdmin();
		repair.setCreateUser(admin);
		repairService.save(repair);
		redirectionUrl = "repair!list.action?workingBillId="
				+ repair.getWorkingbill().getId();
		return SUCCESS;
	}

	// 更新
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "repair.repairAmount", min = "0", message = "返修数量必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String update() throws Exception {
		Repair persistent = repairService.load(id);
		BeanUtils.copyProperties(repair, persistent, new String[] { "id" });
		repairService.update(persistent);
		redirectionUrl = "repair!list.action?workingBillId="
				+ repair.getWorkingbill().getId();
		return SUCCESS;
	}

	// 刷卡确认
	public String confirms() {
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			repair = repairService.load(ids[i]);
			if (CONFIRMED.equals(repair.getState())) {
				//addActionError("已确认的无须再确认！");
				return ajaxJsonErrorMessage("已确认的无须再确认!");
			}
			if (UNDO.equals(repair.getState())) {
				//addActionError("已撤销的无法再确认！");
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}
		}
		List<Repair> list = repairService.get(ids);
		repairService.updateState(list, CONFIRMED, workingBillId);
		workingbill = workingBillService.get(workingBillId);
		HashMap<String,String> hashmap = new HashMap<String,String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE,"您的操作已成功" );
		hashmap.put("totalAmount", workingbill.getTotalRepairAmount().toString());
		return ajaxJson(hashmap);
	}

	// 刷卡撤销
	public String undo() {
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			repair = repairService.load(ids[i]);
			if (UNDO.equals(repair.getState())) {
				//addActionError("已撤销的无法再撤销！");
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		List<Repair> list = repairService.get(ids);
		repairService.updateState(list, UNDO, workingBillId);
		workingbill = workingBillService.get(workingBillId);
		HashMap<String,String> hashmap = new HashMap<String,String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE,"您的操作已成功" );
		hashmap.put("totalAmount", workingbill.getCartonTotalAmount().toString());
		return ajaxJson(hashmap);
	}

	public String browser() {
		return "browser";
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
			if(repair.getProcessResponse()!=null){
				repair.setResponseName(repair.getProcessResponse().getProcessName());				
			}
			lst.add(repair);
		}
		pager.setList(lst);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Repair.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

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

}
