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

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Pollingtest;
import cc.jiuyi.entity.PollingtestRecord;
import cc.jiuyi.entity.SampleRecord;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PollingtestRecordService;
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
	private String my_id;
	private String info;
	private String info2;
	private String pollingtestType;// 巡检类型
	private String add;
	private String edit;
	private String show;

	// 获取所有状态
	private List<Dict> allCraftWork;
	private List<Cause> list_cause;// 缺陷
	private List<PollingtestRecord> list_pollingtestRecord;

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
	@Resource
	private PollingtestRecordService pollingtestRecordService;

	public String list() {
		admin = adminService.getLoginAdmin();
		workingbill = workingBillService.get(workingBillId);
		return "list";
	}

	// 添加
	public String add() {
		workingbill = workingBillService.get(workingBillId);
		list_cause = causeService.getBySample("2");// 获取缺陷表中关于巡检的内容
		this.add="add";
		return INPUT;
	}

	// 编辑
	public String edit() {
		try {
			
			pollingtest = pollingtestService.load(id);
			workingbill = workingBillService.get(workingBillId);
			List<Cause> l_cause = causeService.getBySample("2");// 获取缺陷表中关于巡检的内容
			list_pollingtestRecord = pollingtestRecordService.findByPollingtestId(id);
			list_cause=new ArrayList<Cause>();
			for(int i=0;i<l_cause.size();i++)
			{
				Cause c=l_cause.get(i);
				for(int j=0;j<list_pollingtestRecord.size();j++)
				{
					PollingtestRecord sr=list_pollingtestRecord.get(j);
					if(c!=null&&sr!=null)
					{
						if(c.getId().equals(sr.getCauseId()))
						{
							c.setCauseNum(sr.getRecordNum());
						}
					}
				}
				this.list_cause.add(c);
			}
			this.edit="edit";
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 保存
	@Validations(intRangeFields = { @IntRangeFieldValidator(fieldName = "pollingtest.pollingtestAmount", min = "0", message = "巡检数量必须为零或正整数!") })
	//@InputConfig(resultName = "error")
	public String save() throws Exception {
		pollingtestService.saveInfo(pollingtest, info, info2, my_id);
		redirectionUrl = "pollingtest!list.action?workingBillId="
				+ pollingtest.getWorkingbill().getId();
		return SUCCESS;
	}

	// 查看
	public String show() {
		pollingtest = pollingtestService.load(id);
		workingbill = workingBillService.get(workingBillId);
		pollingtestType = ThinkWayUtil.getDictValueByDictKey(dictService,
				"craftWorkRemark", pollingtest.getCraftWork());
		list_pollingtestRecord = pollingtestRecordService
				.findByPollingtestId(id);
		this.show="show";
		return INPUT;
	}
	
	/**
	 * 修改
	 */
	public String update()
	{
		//保存巡检单信息:巡检单，缺陷ID，缺陷数量，1保存/2确认
		this.pollingtestService.updateInfo(pollingtest,info,info2,my_id);
		redirectionUrl = "pollingtest!list.action?workingBillId="
				+ pollingtest.getWorkingbill().getId();
		return SUCCESS;
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
	public String creditapproval() {
		admin = adminService.getLoginAdmin();
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			pollingtest = pollingtestService.load(ids[i]);
			if (CONFIRMED.equals(pollingtest.getState())) {
				//addActionError("已确认的无须再确认！");
				return ajaxJsonErrorMessage("已确认的无须再确认!");
			}
			if (UNDO.equals(pollingtest.getState())) {
				//addActionError("已撤销的无法再确认！");
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}
		}
		List<Pollingtest> list = pollingtestService.get(ids);
		pollingtestService.confirm(list, admin, CONFIRMED);
		//redirectionUrl = "pollingtest!list.action?workingBillId="+ pollingtest.getWorkingbill().getId();
		
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡撤销
	public String creditundo() {
		admin = adminService.getLoginAdmin();
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			pollingtest = pollingtestService.load(ids[i]);
			if (UNDO.equals(pollingtest.getState())) {
				//addActionError("已撤销的无法再撤销！");
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		List<Pollingtest> list = pollingtestService.get(ids);
		pollingtestService.confirm(list, admin, UNDO);
		/*redirectionUrl = "pollingtest!list.action?workingBillId="
				+ pollingtest.getWorkingbill().getId();*/
		return ajaxJsonSuccessMessage("您的操作已成功!");
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

	public String getMy_id() {
		return my_id;
	}

	public void setMy_id(String my_id) {
		this.my_id = my_id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getInfo2() {
		return info2;
	}

	public void setInfo2(String info2) {
		this.info2 = info2;
	}

	public List<PollingtestRecord> getList_pollingtestRecord() {
		return list_pollingtestRecord;
	}

	public void setList_pollingtestRecord(
			List<PollingtestRecord> list_pollingtestRecord) {
		this.list_pollingtestRecord = list_pollingtestRecord;
	}

	public String getPollingtestType() {
		return pollingtestType;
	}

	public void setPollingtestType(String pollingtestType) {
		this.pollingtestType = pollingtestType;
	}

	public String getAdd() {
		return add;
	}

	public void setAdd(String add) {
		this.add = add;
	}

	public String getEdit() {
		return edit;
	}

	public void setEdit(String edit) {
		this.edit = edit;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

}
