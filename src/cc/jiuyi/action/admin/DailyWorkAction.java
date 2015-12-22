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
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DailyWorkService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 报工
 */

@ParentPackage("admin")
public class DailyWorkAction extends BaseAdminAction {

	private static final long serialVersionUID = 352880047222902914L;

	private static final String CONFIRMED = "1";
	// private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";

	private DailyWork dailyWork;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;
	private List<Process> allProcess;
	private String cardnumber;//刷卡卡号

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

	/**
	 * 跳转list 页面
	 * 
	 * @return
	 */
	public String list() {
		admin = adminService.getLoginAdmin();
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}

	public String history() {
		return "history";
	}

	public String add() {
		workingbill = workingBillService.get(workingBillId);
		List<WorkingBill> workingbills = new ArrayList<WorkingBill>();
		workingbills.add(workingbill);
		allProcess = processService.findProcess(workingbills);
		return INPUT;
	}

	// 编辑
	public String edit() {
		dailyWork = dailyWorkService.load(id);
		workingbill = workingBillService.get(workingBillId);
		List<WorkingBill> workingbills = new ArrayList<WorkingBill>();
		workingbills.add(workingbill);
		allProcess = processService.findProcess(workingbills);
		return INPUT;
	}

	// 保存
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "dailyWork.enterAmout", min = "0", message = "报工数量必须为零或正整数!") })
	// @InputConfig(resultName = "error")
	public String creditsave() throws Exception {
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
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "dailyWork.enterAmout", min = "0", message = "报工数量必须为零或正整数!") })
	// @InputConfig(resultName = "error")
	public String creditupdate() throws Exception {
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
		try {
			ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				dailyWork = dailyWorkService.load(ids[i]);
				if (CONFIRMED.equals(dailyWork.getState())) {
					return ajaxJsonErrorMessage("已确认的无须再确认!");
				}
				if (UNDO.equals(dailyWork.getState())) {
					return ajaxJsonErrorMessage("已撤销的无法再确认！");
				}
			}
			List<DailyWork> list = dailyWorkService.get(ids);
			dailyWorkService.updateState(list, workingBillId,cardnumber);
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

	// 刷卡撤销
	public String creditundo() {

		try {
			ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				dailyWork = dailyWorkService.load(ids[i]);
				if (UNDO.equals(dailyWork.getState())) {
					return ajaxJsonErrorMessage("已撤销的无法再撤销！");
				}
			}
			List<DailyWork> list = dailyWorkService.get(ids);
			dailyWorkService.updateState2(list, workingBillId,cardnumber);
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
			if (obj.get("workingbillCode") != null) {
				System.out.println("obj=" + obj);
				String workingbillCode = obj.getString("workingbillCode")
						.toString();
				map.put("workingbillCode", workingbillCode);
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
			if (dailyWork.getProcess() != null) {
				dailyWork.setResponseName(dailyWork.getProcess()
						.getProcessName());
			}
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
			if (dailyWork.getProcess() != null) {
				dailyWork.setResponseName(dailyWork.getProcess()
						.getProcessName());
			}
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

}