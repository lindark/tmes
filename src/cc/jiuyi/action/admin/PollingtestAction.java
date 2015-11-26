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

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Pollingtest;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PollingtestService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 巡检
 * 
 */
@ParentPackage("admin")
public class PollingtestAction extends BaseAdminAction {

	private static final long serialVersionUID = 1579056971862840674L;

	private static final String CONFIRMED = "1";
	private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";

	private Pollingtest pollingtest;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;

	// 获取所有状态
	private List<Dict> allCraftWork;
	private List<Cause> list_cause;// 缺陷

	@Resource
	private PollingtestService pollingtestService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;
	@Resource
	private CauseService causeService;

	public String list() {
		workingbill = workingBillService.get(workingBillId);
		return "list";
	}

	// 添加
	public String add() {
		workingbill = workingBillService.get(workingBillId);
		list_cause = causeService.getBySample("2");// 获取缺陷表中关于巡检的内容
		return INPUT;
	}

	// 编辑
	public String edit() {
		pollingtest = pollingtestService.load(id);
		workingbill = workingBillService.get(workingBillId);
		list_cause = causeService.getBySample("2");// 获取缺陷表中关于巡检的内容
		return INPUT;
	}

	// 保存
	public String save() {
		admin = adminService.getLoginAdmin();
		pollingtest.setPollingtestUser(admin);
		pollingtest.setPassedPercent(getPercent(
				pollingtest.getQualifiedAmount(),
				pollingtest.getPollingtestAmount()));
		pollingtestService.save(pollingtest);
		redirectionUrl = "pollingtest!list.action?workingBillId="
				+ pollingtest.getWorkingbill().getId();
		return SUCCESS;
	}

	public String getPercent(Integer qualifiedAmount, Integer pollingtestAmount) {
		Double percent = ((double) qualifiedAmount) / pollingtestAmount*100;
		return percent + "%";
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

		pager = pollingtestService.findPagerByjqGrid(pager, map, workingBillId);
		List<Pollingtest> pollingtestList = pager.getList();
		List<Pollingtest> lst = new ArrayList<Pollingtest>();
		for (int i = 0; i < pollingtestList.size(); i++) {
			Pollingtest pollingtest = (Pollingtest) pollingtestList.get(i);
			// 状态描述
			pollingtest.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "pollingtestState", pollingtest.getState()));
			// 工艺确认描述
			pollingtest
					.setCraftWorkRemark(ThinkWayUtil.getDictValueByDictKey(
							dictService, "craftWorkRemark",
							pollingtest.getCraftWork()));
			// 确认人的名字
			if (pollingtest.getConfirmUser() != null) {
				pollingtest
						.setAdminName(pollingtest.getConfirmUser().getName());
			}
			// 巡检人的名字
			if (pollingtest.getPollingtestUser() != null) {
				pollingtest.setPollingtestUserName(pollingtest
						.getPollingtestUser().getName());
			}
			lst.add(pollingtest);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig
				.setExcludes(ThinkWayUtil.getExcludeFields(Pollingtest.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 刷卡确认
	public String confirms() {
		admin = adminService.getLoginAdmin();
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			pollingtest = pollingtestService.load(ids[i]);
			if (CONFIRMED.equals(pollingtest.getState())) {
				addActionError("已确认的无须再确认！");
				return ERROR;
			}
			if (UNDO.equals(pollingtest.getState())) {
				addActionError("已撤销的无法再确认！");
				return ERROR;
			}
		}
		List<Pollingtest> list = pollingtestService.get(ids);
		for (int i = 0; i < list.size(); i++) {
			pollingtest = list.get(i);
			pollingtest.setState(CONFIRMED);
			pollingtest.setConfirmUser(admin);
			pollingtestService.update(pollingtest);
		}
		redirectionUrl = "pollingtest!list.action?workingBillId="
				+ pollingtest.getWorkingbill().getId();
		return SUCCESS;
	}

	// 刷卡撤销
	public String undo() {
		admin = adminService.getLoginAdmin();
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			pollingtest = pollingtestService.load(ids[i]);
			if (UNDO.equals(pollingtest.getState())) {
				addActionError("已撤销的无法再撤销！");
				return ERROR;
			}
		}
		List<Pollingtest> list = pollingtestService.get(ids);
		for (int i = 0; i < list.size(); i++) {
			pollingtest = list.get(i);
			pollingtest.setState(UNDO);
			pollingtest.setConfirmUser(admin);
			pollingtestService.update(pollingtest);
		}
		redirectionUrl = "pollingtest!list.action?workingBillId="
				+ pollingtest.getWorkingbill().getId();
		return SUCCESS;
	}

	public Pollingtest getPollingtest() {
		return pollingtest;
	}

	public void setPollingtest(Pollingtest pollingtest) {
		this.pollingtest = pollingtest;
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

	public List<Dict> getAllCraftWork() {
		return dictService.getList("dictname", "craftWorkRemark");
	}

	public void setAllCraftWork(List<Dict> allCraftWork) {
		this.allCraftWork = allCraftWork;
	}

	public List<Cause> getList_cause() {
		return list_cause;
	}

	public void setList_cause(List<Cause> list_cause) {
		this.list_cause = list_cause;
	}

}
