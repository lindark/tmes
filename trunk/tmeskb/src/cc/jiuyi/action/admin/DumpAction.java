package cc.jiuyi.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import cc.jiuyi.entity.Dump;
import cc.jiuyi.sap.rfc.impl.DumpRfcImpl;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.DumpService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 转储管理
 * 
 */
@ParentPackage("admin")
public class DumpAction extends BaseAdminAction {

	private static final long serialVersionUID = -5672674230144520389L;

	private static final String CONFIRMED = "1";

	private Dump dump;
	private Admin admin;
	private String warehouse;
	private String warehouseName;
	private String dumpId;
	private List<Dump> dumpList;

	@Resource
	private DumpRfcImpl dumpRfc;
	@Resource
	private DumpService dumpService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;

	public String list() {
		admin = adminService.getLoginAdmin();
		admin = adminService.load(admin.getId());
		warehouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();
		warehouseName = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouseName();
		return "list";
	}

	public String detail() {
		dump = dumpService.load(id);
		String voucherId = dump.getVoucherId();// 获取转储单证号
		return "detail";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		dump = dumpService.load(id);
		return INPUT;
	}

	public String save() {
		redirectionUrl = "dump!list.action";
		return SUCCESS;
	}

	/**
	 * 刷卡确认
	 * 
	 * @return
	 */
	public String confirm() {
		try {
			String[] ids = dumpId.split(",");
			dumpList = dumpRfc.findMaterialDocument("1805", "20150901","20151001");
			dumpService.confirmDump(ids, dumpList);
			redirectionUrl = "dump!list.action";
			return SUCCESS;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (CustomerException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	@InputConfig(resultName = "error")
	public String update() {
		Dump persistent = dumpService.load(id);
		BeanUtils.copyProperties(dump, persistent, new String[] { "id" });
		dumpService.update(persistent);
		redirectionUrl = "dump!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		dumpService.updateisdel(ids, "Y");
		redirectionUrl = "dump!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {

		HashMap<String, String> map = new HashMap<String, String>();
		admin = adminService.getLoginAdmin();
		admin = adminService.load(admin.getId());
		warehouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();

		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		pager = dumpService.findPagerByjqGrid(pager, map);
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			String today = sdf.format(date);
			dumpList = dumpRfc.findMaterialDocument("1805", "20150901",
					"20151001");
			pager.setList(dumpList);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Dump.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (CustomerException e) {
			e.printStackTrace();
			return null;
		}
		// List<Dump> lst = new ArrayList<Dump>();
		// List<Dump> dumpList = pager.getList();
		/*
		 * for (int i = 0; i < dumpList.size(); i++) { Dump dump = (Dump)
		 * dumpList.get(i);
		 * dump.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService,
		 * "dumpState", dump.getState())); lst.add(dump); }
		 */

	}

	// 同步
	public String sync() {
		// DumpSync d = new DumpSync();
		// try {
		// d.syncRepairorder(dumpService);
		// } catch (Exception e) {
		// e.printStackTrace();
		// return ERROR;
		// }// 同步
		// redirectionUrl = "dump!list.action";
		return SUCCESS;
	}

	public Dump getDump() {
		return dump;
	}

	public void setDump(Dump dump) {
		this.dump = dump;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public DumpRfcImpl getDumpRfc() {
		return dumpRfc;
	}

	public void setDumpRfc(DumpRfcImpl dumpRfc) {
		this.dumpRfc = dumpRfc;
	}

	public String getDumpId() {
		return dumpId;
	}

	public void setDumpId(String dumpId) {
		this.dumpId = dumpId;
	}

	public List<Dump> getDumpList() {
		return dumpList;
	}

	public void setDumpList(List<Dump> dumpList) {
		this.dumpList = dumpList;
	}

}
