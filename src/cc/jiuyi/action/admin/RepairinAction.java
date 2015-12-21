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
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.RepairinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 返修收货
 * 
 */
@ParentPackage("admin")
public class RepairinAction extends BaseAdminAction {

	private static final long serialVersionUID = -5368121517667092305L;

	private static final String CONFIRMED = "1";
	private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";

	private Repairin repairin;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;

	@Resource
	private RepairinService repairinService;
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
		admin = adminService.getLoginAdmin();
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}

	// 添加
	public String add() {
		workingbill = workingBillService.get(workingBillId);
		return INPUT;
	}

	// 历史返修记录
	public String history() {
		return "history";
	}

	// 保存
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "repairin.receiveAmount", min = "0", message = "返修收货数量必须为零或正整数!") })
	@InputConfig(resultName = "error")
	public String creditsave() throws Exception {
		admin = adminService.loadLoginAdmin();
		repairin.setCreateUser(admin);
		repairinService.save(repairin);
		/*redirectionUrl = "repairin!list.action?workingBillId="
				+ repairin.getWorkingbill().getId();*/
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 更新
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "repairin.receiveAmoun", min = "0", message = "报工数量必须为零或正整数!") })
	//@InputConfig(resultName = "error")
	public String creditupdate() throws Exception {
		Repairin persistent = repairinService.load(id);
		BeanUtils.copyProperties(repairin, persistent, new String[] { "id" });
		repairinService.update(persistent);
		/*redirectionUrl = "repairin!list.action?workingBillId="
				+ repairin.getWorkingbill().getId();*/
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡确认
	public String creditapproval() {
		// workingbill = workingBillService.get(workingBillId);
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			repairin = repairinService.load(ids[i]);
			if (CONFIRMED.equals(repairin.getState())) {
				return ajaxJsonErrorMessage("已确认的无须再确认!");
			}
			if (UNDO.equals(repairin.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}
		}
		List<Repairin> list = repairinService.get(ids);
		repairinService.updateState(list, CONFIRMED, workingBillId);
		workingbill = workingBillService.get(workingBillId);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalAmount", workingbill.getTotalRepairinAmount()
				.toString());
		return ajaxJson(hashmap);
	}

	// 刷卡撤销
	public String creditundo() {
		workingbill = workingBillService.get(workingBillId);
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			repairin = repairinService.load(ids[i]);
			if (UNDO.equals(repairin.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		List<Repairin> list = repairinService.get(ids);
		repairinService.updateState(list, UNDO, workingBillId);
		workingbill = workingBillService.get(workingBillId);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalAmount", workingbill.getTotalRepairinAmount()
				.toString());
		return ajaxJson(hashmap);
	}
	
	public String historylist(){
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
				String workingbillCode = obj.getString("workingbillCode").toString();
				map.put("workingbillCode", workingbillCode);
			}
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}
		pager = repairinService.historyjqGrid(pager, map);
		List<Repairin> repairinList = pager.getList();
		List<Repairin> lst = new ArrayList<Repairin>();
		for (int i = 0; i < repairinList.size(); i++) {
			Repairin repairin = (Repairin) repairinList.get(i);
			repairin.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "repairinState", repairin.getState()));
			if (repairin.getConfirmUser() != null) {
				repairin.setAdminName(repairin.getConfirmUser().getName());
			}
			repairin.setCreateName(repairin.getCreateUser().getName());
			repairin.setWorkingbillCode(workingBillService.get(repairin.getWorkingbill().getId()).getWorkingBillCode());
			repairin.setMaktx(workingBillService.get(repairin.getWorkingbill().getId()).getMaktx());
			repairin.setWorkingbill(null);
			repairin.setConfirmUser(null);
			repairin.setCreateUser(null);
			lst.add(repairin);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
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

		pager = repairinService.findPagerByjqGrid(pager, map, workingBillId);
		List<Repairin> repairinList = pager.getList();
		List<Repairin> lst = new ArrayList<Repairin>();
		for (int i = 0; i < repairinList.size(); i++) {
			Repairin repairin = (Repairin) repairinList.get(i);
			repairin.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "repairinState", repairin.getState()));
			if (repairin.getConfirmUser() != null) {
				repairin.setAdminName(repairin.getConfirmUser().getName());
			}
			repairin.setCreateName(repairin.getCreateUser().getName());
			repairin.setWorkingbill(null);
			repairin.setConfirmUser(null);
			repairin.setCreateUser(null);
			lst.add(repairin);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}

	public Repairin getRepairin() {
		return repairin;
	}

	public void setRepairin(Repairin repairin) {
		this.repairin = repairin;
	}

	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}

	public String getWorkingBillId() {
		return workingBillId;
	}

	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
