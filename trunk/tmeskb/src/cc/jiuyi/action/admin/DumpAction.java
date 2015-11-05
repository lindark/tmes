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
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Material;
import cc.jiuyi.sap.rfc.DumpSync;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.DumpService;
import cc.jiuyi.service.MaterialService;
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

	@Resource
	private DumpService dumpService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;

	public String list() {
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
		dumpService.save(dump);
		redirectionUrl = "dump!list.action";
		return SUCCESS;
	}

	public String confirm() {
		dump = dumpService.load(id);
		dump.setState(CONFIRMED);
		admin = adminService.getLoginAdmin();
		dump.setConfirmUser(admin.getUsername());
		dumpService.save(dump);
		redirectionUrl = "dump!list.action";
		return SUCCESS;
	}
	
	public String confirms(){
		ids = id.split(",");
		for(int i = 0;i<ids.length;i++){
			dump = dumpService.load(ids[i]);
			dump.setState(CONFIRMED);
			admin = adminService.getLoginAdmin();
			dump.setConfirmUser(admin.getUsername());
			dumpService.save(dump);
		}
		redirectionUrl = "dump!list.action";
		return SUCCESS;
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

		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
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
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("voucherId") != null) {
				String vocherId = obj.get("voucherId").toString();
				map.put("voucherId", vocherId);
			}
			/*
			 * if(obj.get("deliveryDate")!=null){ String deliveryDate =
			 * obj.get("deliveryDate").toString(); map.put("deliveryDate",
			 * deliveryDate); }
			 */
			if (obj.get("confirmUser") != null) {
				String confirmUser = obj.get("confirmUser").toString();
				map.put("confirmUser", confirmUser);
			}
			if (obj.get("state") != null) {
				String state = obj.get("state").toString();
				map.put("state", state);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}

		}
		pager = dumpService.getDumpPager(pager, map);
		List<Dump> dumpList = pager.getList();
		List<Dump> lst = new ArrayList<Dump>();
		for (int i = 0; i < dumpList.size(); i++) {
			Dump dump = (Dump) dumpList.get(i);
			dump.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService,
					"dumpState", dump.getState()));
			lst.add(dump);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 同步
	public String sync() {
		DumpSync d = new DumpSync();
		try {
			d.syncRepairorder(dumpService);
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}// 同步
		redirectionUrl = "dump!list.action";
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

}
