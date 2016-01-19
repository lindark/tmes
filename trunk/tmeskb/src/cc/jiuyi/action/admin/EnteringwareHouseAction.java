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

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.EnteringwareHouseRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.EnteringwareHouseService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * 后台Action类 - 入库
 */

@ParentPackage("admin")
public class EnteringwareHouseAction extends BaseAdminAction {

	private static final long serialVersionUID = 352880047222902914L;

	private static final String CONFIRMED = "1";
	private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";
	private static final String UNITCODE = "1001";

	private String workingBillId;
	private WorkingBill workingbill;
	private EnteringwareHouse enteringwareHouse;
	private Admin admin;
	private Integer ratio;// 箱与个的转换比率
	private Integer totalAmount = 0;
	private String cardnumber;//刷卡卡号

	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private EnteringwareHouseService enteringwareHouseService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private UnitConversionService unitConversionService;
	@Resource
	private EnteringwareHouseRfc enteringwareHouseRfc;

	/**
	 * 跳转list 页面
	 * 
	 * @return
	 */
	public String list() {
		admin = adminService.getLoginAdmin();
		admin = adminService.getLoginAdmin();
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行入库操作!");
			return ERROR;
		}
		workingbill = workingBillService.get(workingBillId);
		List<EnteringwareHouse> enteringwares = enteringwareHouseService
				.getByBill(workingBillId);
		for (int i = 0; i < enteringwares.size(); i++) {
			totalAmount += enteringwares.get(i).getStorageAmount();
		}
		return LIST;
	}

	public String add() {
		workingbill = workingBillService.get(workingBillId);
		return INPUT;
	}

	// 历史入库记录
	public String history() {
		return "history";
	}

	// 保存
	public String creditsave() {
		if(enteringwareHouse.getStorageAmount()==null||String.valueOf(enteringwareHouse.getStorageAmount()).matches("^[0-9]*[1-9][0-9]*$ ")){
			return ajaxJsonErrorMessage("入库数量必须为零或正整数!");
		}
		admin = adminService.getByCardnum(cardnumber);
		enteringwareHouse.setCreateUser(admin);
		enteringwareHouseService.save(enteringwareHouse);
		/*redirectionUrl = "enteringware_house!list.action?workingBillId="
				+ enteringwareHouse.getWorkingbill().getId();*/
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡确认
	public String creditapproval() {
		System.out.println(workingBillId);
		WorkingBill workingbill = workingBillService.get(workingBillId);
		System.out.println(workingbill);
		ratio = unitConversionService.getRatioByMatnr(workingbill.getMatnr());
		System.out.println(ratio);
		if (ratio == null || ratio.equals("")) {
           return ajaxJsonErrorMessage("请在计量单位转换表中维护物料编码对应的换算数据!");
		}
		Admin admin = adminService.getByCardnum(cardnumber);

		String warehouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();// 线边仓
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂		
		ThinkWayUtil util = new ThinkWayUtil();
		String budat = util.SystemDate();// 过账日期
		ids = id.split(",");
		List<EnteringwareHouse> list = enteringwareHouseService.get(ids);
		for (int i = 0; i < list.size(); i++) {
			enteringwareHouse = list.get(i);
			if (CONFIRMED.equals(enteringwareHouse.getState())) {
				return ajaxJsonErrorMessage("已确认的无须再确认!");
			}
			if (UNDO.equals(enteringwareHouse.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}
		}	
		
		List<EnteringwareHouse> enterList = new ArrayList<EnteringwareHouse>();
		for(EnteringwareHouse e:list){
			e.setBudat(budat);
			e.setWerks(werks);
			e.setLgort(warehouse);
			e.setMoveType("101");
			e.setStorageAmount(e.getStorageAmount()*ratio);
			enterList.add(e);
		}
		try {
			List<EnteringwareHouse> aufnr=enteringwareHouseRfc.WarehousingCrt("",enterList);
			for(EnteringwareHouse e:aufnr){
				if("E".equalsIgnoreCase(e.getE_type()))
				{
					return this.ajaxJsonErrorMessage(e.getE_message());
				}
				
			}	

			enteringwareHouseService.updateState(aufnr, CONFIRMED, workingBillId,
					ratio,cardnumber);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CustomerException e1) {
			e1.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		workingbill = workingBillService.get(workingBillId);
		List<EnteringwareHouse> enteringwares = enteringwareHouseService
				.getByBill(workingBillId);
		for (int i = 0; i < enteringwares.size(); i++) {
			totalAmount += enteringwares.get(i).getStorageAmount();
		}
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalSingleAmount", workingbill.getTotalSingleAmount()
				.toString());
		hashmap.put("total", totalAmount.toString());
		return ajaxJson(hashmap);
	}

	// 刷卡撤销
	public String creditundo() {
		WorkingBill workingbill = workingBillService.get(workingBillId);
		ratio = unitConversionService.getRatioByMatnr(workingbill.getMatnr());
		if (ratio == null || ratio.equals("")) {
	           return ajaxJsonErrorMessage("请在计量单位转换表中维护物料编码对应的换算数据!");
			}
		Admin admin = adminService.getByCardnum(cardnumber);
		
		String warehouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();// 线边仓
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂		
		ThinkWayUtil util = new ThinkWayUtil();
		String budat = util.SystemDate();// 过账日期
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			enteringwareHouse = enteringwareHouseService.load(ids[i]);
			if (UNDO.equals(enteringwareHouse.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		List<EnteringwareHouse> list = enteringwareHouseService.get(ids);
		
		List<EnteringwareHouse> enterList = new ArrayList<EnteringwareHouse>();
		for(EnteringwareHouse e:list){
			e.setBudat(budat);
			e.setWerks(werks);
			e.setLgort(warehouse);
			e.setMoveType("102");
			e.setStorageAmount(e.getStorageAmount()*ratio);
			enterList.add(e);
		}
		try {
			List<EnteringwareHouse> aufnr=enteringwareHouseRfc.WarehousingCrt("",enterList);
			for(EnteringwareHouse e:aufnr){
				if("E".equalsIgnoreCase(e.getE_type()))
				{
					return this.ajaxJsonErrorMessage(e.getE_message());
				}
				
			}	

			enteringwareHouseService.updateState(aufnr, UNDO, workingBillId, ratio,cardnumber);
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (CustomerException e1) {
			e1.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		workingbill = workingBillService.get(workingBillId);
		List<EnteringwareHouse> enteringwares = enteringwareHouseService
				.getByBill(workingBillId);
		for (int i = 0; i < enteringwares.size(); i++) {
			totalAmount += enteringwares.get(i).getStorageAmount();
		}
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalSingleAmount", workingbill.getTotalSingleAmount()
				.toString());
		hashmap.put("total", totalAmount.toString());
		return ajaxJson(hashmap);
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
		pager = enteringwareHouseService.historyjqGrid(pager, map);
		List<EnteringwareHouse> enteringwareHouseList = pager.getList();
		List<EnteringwareHouse> lst = new ArrayList<EnteringwareHouse>();
		for (int i = 0; i < enteringwareHouseList.size(); i++) {
			EnteringwareHouse enteringwareHouse = (EnteringwareHouse) enteringwareHouseList
					.get(i);
			enteringwareHouse.setStateRemark(ThinkWayUtil
					.getDictValueByDictKey(dictService, "enteringwareState",
							enteringwareHouse.getState()));
			if (enteringwareHouse.getConfirmUser() != null) {
				enteringwareHouse.setAdminName(enteringwareHouse
						.getConfirmUser().getName());
			}
			enteringwareHouse.setCreateName(enteringwareHouse.getCreateUser()
					.getName());
			enteringwareHouse.setWorkingbillCode(workingBillService.get(
					enteringwareHouse.getWorkingbill().getId()).getWorkingBillCode());
			enteringwareHouse.setMaktx(workingBillService.get(
					enteringwareHouse.getWorkingbill().getId()).getMaktx());
			lst.add(enteringwareHouse);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil
				.getExcludeFields(EnteringwareHouse.class));// 排除有关联关系的属性字段
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
		pager = enteringwareHouseService.findPagerByjqGrid(pager, map,
				workingBillId);
		List<EnteringwareHouse> enteringwareHouseList = pager.getList();
		List<EnteringwareHouse> lst = new ArrayList<EnteringwareHouse>();
		for (int i = 0; i < enteringwareHouseList.size(); i++) {
			EnteringwareHouse enteringwareHouse = (EnteringwareHouse) enteringwareHouseList
					.get(i);
			enteringwareHouse.setStateRemark(ThinkWayUtil
					.getDictValueByDictKey(dictService, "enteringwareState",
							enteringwareHouse.getState()));
			if (enteringwareHouse.getConfirmUser() != null) {
				enteringwareHouse.setAdminName(enteringwareHouse
						.getConfirmUser().getName());
			}
			enteringwareHouse.setCreateName(enteringwareHouse.getCreateUser()
					.getName());
			lst.add(enteringwareHouse);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil
				.getExcludeFields(EnteringwareHouse.class));// 排除有关联关系的属性字段
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

	public EnteringwareHouse getEnteringwareHouse() {
		return enteringwareHouse;
	}

	public void setEnteringwareHouse(EnteringwareHouse enteringwareHouse) {
		this.enteringwareHouse = enteringwareHouse;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Integer getRatio() {
		return ratio;
	}

	public void setRatio(Integer ratio) {
		this.ratio = ratio;
	}

	public Integer getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Integer totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

}