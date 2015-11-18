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
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DailyWorkService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 报工
 */

@ParentPackage("admin")
public class DailyWorkAction extends BaseAdminAction {

	private static final long serialVersionUID = 352880047222902914L;

	private static final String CONFIRMED = "1";
	private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";

	private DailyWork dailyWork;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;

	@Resource
	private DailyWorkService dailyWorkService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;

	/**
	 * 跳转list 页面
	 * 
	 * @return
	 */
	public String list() {
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}

	public String add() {
		workingbill = workingBillService.get(workingBillId);
		return INPUT;
	}

	// 保存
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "dailyWork.enterAmout", min = "0", message = "报工数量必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String save() throws Exception{
		admin = adminService.loadLoginAdmin();
		dailyWork.setAdmin(admin);
		dailyWork.setCreateUser(admin.getId());
		dailyWorkService.save(dailyWork);
		redirectionUrl = "daily_work!list.action?workingBillId="
				+ dailyWork.getWorkingbill().getId();
		return SUCCESS;
	}

	// 更新
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "dailyWork.enterAmout", min = "0", message = "报工数量必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String update() throws Exception{
		DailyWork persistent = dailyWorkService.load(id);
		BeanUtils.copyProperties(dailyWork, persistent, new String[] { "id" });
		dailyWorkService.update(persistent);
		admin = adminService.loadLoginAdmin();
		dailyWork.setAdmin(admin);
		redirectionUrl = "dailyWork!list.action?workingBillId="
				+ dailyWork.getWorkingbill().getId();
		return SUCCESS;
	}

	// 刷卡确认
	public String confirms() {
		workingbill = workingBillService.get(workingBillId);
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			dailyWork = dailyWorkService.load(ids[i]);
			if (CONFIRMED.equals(dailyWork.getState())) {
				addActionError("已确认的无须再确认！");
				return ERROR;
			}
			if (UNDO.equals(dailyWork.getState())) {
				addActionError("已撤销的无法再确认！");
				return ERROR;
			}
		}
		for (int i = 0; i < ids.length; i++) {
			dailyWork = dailyWorkService.load(ids[i]);
			if (!CONFIRMED.equals(dailyWork.getState())) {
				dailyWork.setState(CONFIRMED);
				admin = adminService.getLoginAdmin();
				dailyWork.setAdmin(admin);
				dailyWork.setConfirmUser(admin.getId());
				workingbill.setDailyWorkTotalAmount(workingbill
						.getDailyWorkTotalAmount() + dailyWork.getEnterAmount());
				dailyWorkService.update(dailyWork);
			}
		}
		workingBillService.update(workingbill);
		redirectionUrl = "daily_work!list.action?workingBillId="
				+ dailyWork.getWorkingbill().getId();
		return SUCCESS;
	}

	// 刷卡撤销
	public String undo() {
		workingbill = workingBillService.get(workingBillId);
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			dailyWork = dailyWorkService.load(ids[i]);
			if (UNDO.equals(dailyWork.getState())) {
				addActionError("已撤销的无法再撤销！");
				return ERROR;
			}
		}
		for (int i = 0; i < ids.length; i++) {
			dailyWork = dailyWorkService.load(ids[i]);
			admin = adminService.getLoginAdmin();
			dailyWork.setAdmin(admin);
			dailyWork.setConfirmUser(admin.getId());
			if (CONFIRMED.equals(dailyWork.getState())) {
				workingbill.setDailyWorkTotalAmount(workingbill
						.getDailyWorkTotalAmount() - dailyWork.getEnterAmount());
			}
			dailyWork.setState(UNDO);
			dailyWorkService.update(dailyWork);
		}
		workingBillService.update(workingbill);
		redirectionUrl = "daily_work!list.action?workingBillId="
				+ dailyWork.getWorkingbill().getId();
		return SUCCESS;
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
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
				Admin admin = adminService.load(dailyWork.getConfirmUser());
				dailyWork.setAdminName(admin.getName());
			}
			admin = adminService.load(dailyWork.getCreateUser());
			dailyWork.setCreateName(admin.getName());
			dailyWork.setWorkingbill(null);
			dailyWork.setAdmin(null);
			lst.add(dailyWork);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
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

}