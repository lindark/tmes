package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.sap.rfc.DumpRfc;
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

	private static final String UNCONFIRMED = "2";// 未确认

	private Dump dump;
	private Admin admin;
	private String warehouse;
	private String warehouseName;
	private String dumpId;
	private List<Dump> dumpList;
	private String cardnumber;// 刷卡卡号
	private String loginid;//当前登录人id
	private String type;

	@Resource
	private DumpRfc dumpRfc;
	@Resource
	private DumpService dumpService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;

	public String list() {
		admin = adminService.get(loginid);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行转储操作!");
			return ERROR;
		}
		//admin = adminService.load(admin.getId());
		warehouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();
		warehouseName = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouseName();
		return "list";
	}

	// 历史转储记录
	public String history() {
		return "history";
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
	public String creditapproval() {
		try {
			
			String[] ids = dumpId.split(",");
			admin = adminService.getByCardnum(cardnumber);
			admin = adminService.load(admin.getId());
			warehouse = admin.getDepartment().getTeam().getFactoryUnit()
					.getWarehouse();
			String productDate = admin.getProductDate();
			Date date=ThinkWayUtil.formatStringDate(productDate);
			Date date1=DateUtils.addDays(date, -1);
			Date date2=DateUtils.addDays(date, 1);
			String productDate1=ThinkWayUtil.formatdateDateTime(date1);
			String productDate2=ThinkWayUtil.formatdateDateTime(date2);
			dumpList = dumpRfc.findMaterialDocument(warehouse, productDate1,
					productDate2);
			//根据凭证号查找数据库是否已经存在
			for (int i = 0; i < ids.length; i++) {
				if (dumpService.isExist("voucherId", ids[i])) {
					return ajaxJsonErrorMessage("已确认的无须再确认!");
				}
			}
			dumpService.saveDump(ids, dumpList, cardnumber,warehouse);
			return ajaxJsonSuccessMessage("您的操作已成功!");
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
	 * 历史转储记录
	 * @return
	 */
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
			if (obj.get("voucherId") != null) {
				System.out.println("obj=" + obj);
				String voucherId = obj.getString("voucherId").toString();
				map.put("voucherId", voucherId);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}
		pager = dumpService.findPagerByjqGrid(pager, map);
		List<Dump> dumpList = pager.getList();
		List<Dump> lst = new ArrayList<Dump>();
		for (int i = 0; i < dumpList.size(); i++) {
			Dump dump = (Dump) dumpList.get(i);
			dump.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService,
					"dumpState", dump.getState()));
			if (dump.getConfirmUser() != null) {
				dump.setAdminName(dump.getConfirmUser().getName());
			}
			lst.add(dump);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Dump.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		try {
			admin = adminService.getLoginAdmin();
			admin = adminService.load(admin.getId());
			warehouse = admin.getDepartment().getTeam().getFactoryUnit()
					.getWarehouse();
			String productDate = admin.getProductDate();
			Date date=ThinkWayUtil.formatStringDate(productDate);
			Date date1=DateUtils.addDays(date, -1);
			Date date2=DateUtils.addDays(date, 1);
			String productDate1=ThinkWayUtil.formatdateDateTime(date1);
			String productDate2=ThinkWayUtil.formatdateDateTime(date2);
			/*
			 * dumpList = dumpRfc.findMaterialDocument("1805", "20150901",
			 * "20151001");
			 */
			dumpList = dumpRfc.findMaterialDocument(warehouse, productDate1,
					productDate2);
			//System.out.println(dumpList.size());
			List<Dump> dpList = dumpService.getAll();
			if (dpList.size() != 0) {
				for (int i = 0; i < dumpList.size(); i++) {
					for (int j = 0; j < dpList.size(); j++) {
						if (dumpList.get(i).getVoucherId()
								.equals(dpList.get(j).getVoucherId())) {
							dumpList.get(i).setConfirmUser(
									dpList.get(j).getConfirmUser());
							dumpList.get(i).setState(dpList.get(j).getState());
							break;
						}
					}
				}
			}
			List<DumpDetail> dumpsList = new ArrayList<DumpDetail>();
			int num = 0;
			for (int i = 0; i < dumpList.size(); i++) {
				Dump dump = (Dump) dumpList.get(i);
				if (dump.getState() == null) {
					dump.setState(UNCONFIRMED);
				}
				if("1".equals(type)){
					if(!dump.getState().equalsIgnoreCase("1")){//排除状态为已确认的
						dump.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
								dictService, "dumpState", dump.getState()));
						if (dump.getConfirmUser() != null) {
							dump.setAdminName(dump.getConfirmUser().getName());
						}
						List<DumpDetail> dDList = dumpRfc.findMaterialDocumentByMblnr(dump.getVoucherId(),warehouse);
						
						for(DumpDetail dumps : dDList){
							String str = "0"+num;
							dumps.setId(str);
							num++;
							dumps.setState(dump.getState());
							dumps.setStateRemark(dump.getStateRemark());
							dumps.setDeliveryDate(dump.getDeliveryDate());
							dumps.setAdminName(dump.getAdminName());
							dumpsList.add(dumps);
						}
					}
				}else{
					if(!dump.getState().equalsIgnoreCase("2")){//排除状态为已确认的
						dump.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
								dictService, "dumpState", dump.getState()));
						if (dump.getConfirmUser() != null) {
							dump.setAdminName(dump.getConfirmUser().getName());
						}
						List<DumpDetail> dDList = dumpRfc.findMaterialDocumentByMblnr(dump.getVoucherId(),warehouse);
						
						for(DumpDetail dumps : dDList){
							String str = "0"+num;
							dumps.setId(str);
							num++;
							dumps.setState(dump.getState());
							dumps.setStateRemark(dump.getStateRemark());
							dumps.setDeliveryDate(dump.getDeliveryDate());
							dumps.setAdminName(dump.getAdminName());
							dumpsList.add(dumps);
						}
					}
				}
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Dump.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(dumpsList, jsonConfig);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("list", jsonArray);
			return ajaxJson(jsonobject.toString());
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

	public DumpRfc getDumpRfc() {
		return dumpRfc;
	}

	public void setDumpRfc(DumpRfc dumpRfc) {
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

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}
