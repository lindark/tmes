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
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.EnteringwareHouseRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.EnteringwareHouseService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * @author Reece 2016/3/10
 * 后台Action类 - 入库
 */

@ParentPackage("admin")
public class EnteringwareHouseAction extends BaseAdminAction {

	private static final long serialVersionUID = 352880047222902914L;

	private static final String CONFIRMED = "1";
	private static final String UNCONFIRM = "2";
	private static final String UNDO = "3";
	private static final String UNITCODE = "CAR";

	private String workingBillId;
	private WorkingBill workingbill;
	private EnteringwareHouse enteringwareHouse;
	private Admin admin;
	//private Integer ratio;// 箱与个的转换比率
	private Double totalAmount = 0d;
	private String cardnumber;//刷卡卡号
	private String loginid;//当前登录人的ID
	private String matnr;
	private String maktx;
	private String start;
	private String state;
	private String end;

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
	@Resource
	private FactoryService factoryService;
	@Resource
	private FactoryUnitService factoryUnitService;

	
	// 历史入库记录 @author Reece 2016/3/10
	public String history() {
		return "history";
	}

	// 历史入库记录 @author Reece 2016/3/10
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
			if (obj.get("matnr") != null) {
				String matnr = obj.getString("matnr").toString();
				map.put("matnr", matnr);
			}
			if (obj.get("maktx") != null) {
				String maktx = obj.getString("maktx").toString();
				map.put("maktx", maktx);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
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
					enteringwareHouse.getWorkingbill().getId())
					.getWorkingBillCode());
			enteringwareHouse.setMaktx(workingBillService.get(
					enteringwareHouse.getWorkingbill().getId()).getMaktx());
			enteringwareHouse.setMatnr((workingBillService.get(
					enteringwareHouse.getWorkingbill().getId()).getMatnr()));
			enteringwareHouse.setProductDate((workingBillService.get(
					enteringwareHouse.getWorkingbill().getId()).getProductDate()));
			enteringwareHouse.setXmoudle(ThinkWayUtil.getDictValueByDictKey(dictService,
						"moudleType", enteringwareHouse.getMoudle()));
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
	
	//Excel导出 @author Reece 2016/3/10
	public String excelexport(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("matnr", matnr);
		map.put("maktx", maktx);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);
		
		
		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
        header.add("随工单号");
        header.add("生产日期");
        header.add("产品编码");
        header.add("产品名称");
        header.add("批次");
        header.add("物料凭证号");
        header.add("入库箱数");
        header.add("模具");
        header.add("入库日期");
        header.add("创建人");
        header.add("确认人");
        header.add("状态");
        
        List<Object[]> workList = enteringwareHouseService.historyExcelExport(map);
        for(int i=0;i<workList.size();i++){
        	Object[] obj = workList.get(i);
        	EnteringwareHouse enteringwareHouse = (EnteringwareHouse) obj[0];
        	WorkingBill workingbill = (WorkingBill)obj[1];
        	
        	Object[] bodyval = {workingbill.getWorkingBillCode(),workingbill.getProductDate()
        			            ,workingbill.getMatnr(),workingbill.getMaktx()
        			            ,enteringwareHouse.getBatch(),enteringwareHouse.getEx_mblnr()
        			            ,enteringwareHouse.getStorageAmount()
        						,ThinkWayUtil.getDictValueByDictKey(dictService, "moudleType", enteringwareHouse.getMoudle())
        						,enteringwareHouse.getCreateDate(),enteringwareHouse.getCreateUser()==null?"":enteringwareHouse.getCreateUser().getName()
        						,enteringwareHouse.getConfirmUser()==null?"":enteringwareHouse.getConfirmUser().getName(),ThinkWayUtil.getDictValueByDictKey(dictService, "enteringwareState", enteringwareHouse.getState())};
        	body.add(bodyval);
        }
		
		try {
			String fileName = "成品检验记录表"+".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("成品检验记录表", header, body, getResponse().getOutputStream());
			getResponse().getOutputStream().flush();
		    getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 跳转list 页面
	 * 
	 * @return
	 */
	public String list() {
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行入库操作!");
			return ERROR;
		}
		workingbill = workingBillService.get(workingBillId);
		/*List<EnteringwareHouse> enteringwares = enteringwareHouseService
				.getByBill(workingBillId);
		for (int i = 0; i < enteringwares.size(); i++) {
			totalAmount += enteringwares.get(i).getStorageAmount();
		}*/
		return LIST;
	}
	
	// 编辑
	public String edit() {
		enteringwareHouse = enteringwareHouseService.load(id);
		workingbill = workingBillService.get(workingBillId);
		return INPUT;
	}	

	public String add() {
		workingbill = workingBillService.get(workingBillId);
		return INPUT;
	}

	
	// 查看
	public String view() {
		enteringwareHouse = enteringwareHouseService.load(id);
		workingbill = enteringwareHouse.getWorkingbill();	
		/*module = ThinkWayUtil.getDictValueByDictKey(dictService,
				"moudleType", dailyWork.getMoudle());*/
		return VIEW;
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
	
	// 更新
	public String creditupdate() throws Exception {
		if(enteringwareHouse.getStorageAmount()==null||
				String.valueOf(enteringwareHouse.getStorageAmount()).matches("^[0-9]*[1-9][0-9]*$ ")){
			return ajaxJsonErrorMessage("入库数量必须为零或正整数!");
		}
		EnteringwareHouse persistent = enteringwareHouseService.load(id);

		BeanUtils.copyProperties(enteringwareHouse, persistent, new String[] { "id","createUser" });
		enteringwareHouseService.update(persistent);
		/*
		 * redirectionUrl = "daily_work!list.action?workingBillId=" +
		 * dailyWork.getWorkingbill().getId();
		 */
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
	

	// 刷卡确认
	public String creditapproval() {		
		WorkingBill workingbill = workingBillService.get(workingBillId);
		/*UnitConversion unitconversion = unitConversionService.getRatioByMatnr(workingbill.getMatnr(),UNITCODE);
		if(unitconversion==null){
			return ajaxJsonErrorMessage("未找到该物料对应的单位!");
		}
		ratio = unitconversion.getConversationRatio().intValue();		
		if (ratio == null || ratio.equals("")) {
           return ajaxJsonErrorMessage("请在计量单位转换表中维护物料编码对应的换算数据!");
		}*/
		//Admin admin = adminService.getByCardnum(cardnumber);
		admin=adminService.get(loginid);
		
		String warehouse = admin.getTeam().getFactoryUnit().getWarehouse();// 线边仓
		String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂		
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
		String charg = enteringwareHouseService.getCharg(workingbill);
		
		List<EnteringwareHouse> enterList = new ArrayList<EnteringwareHouse>();
		for(EnteringwareHouse e:list){
			String ex_mblnr = e.getEx_mblnr();
			if(!"".equals(ex_mblnr) && ex_mblnr != null){//weitao modify
				continue;
			}
			e.setBudat(budat);
			e.setWerks(werks);
			e.setLgort(warehouse);
			e.setMoveType("101");
			e.setBatch(charg);
			//e.setStorageAmount(e.getStorageAmount());
			enterList.add(e);
		}
		try {
			List<EnteringwareHouse> aufnr=enteringwareHouseRfc.WarehousingCrt("",enterList);
			for(EnteringwareHouse e:aufnr){
				if("E".equalsIgnoreCase(e.getE_type())){
					return this.ajaxJsonErrorMessage(e.getE_message());
				}
				
			}	

			enteringwareHouseService.updateState(aufnr, CONFIRMED, workingbill,
					cardnumber);
		} catch (IOException e1) {
			e1.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常");
		} catch (CustomerException e1) {
			e1.printStackTrace();
			return ajaxJsonErrorMessage(e1.getMsgDes());
		}catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现错误，请联系系统管理员");
		}
		
		workingbill = workingBillService.get(workingBillId);
		/*List<EnteringwareHouse> enteringwares = enteringwareHouseService
				.getByBill(workingBillId);
		for (int i = 0; i < enteringwares.size(); i++) {
			totalAmount += enteringwares.get(i).getStorageAmount();
		}*/
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalSingleAmount", workingbill.getTotalSingleAmount()
				.toString());
		//hashmap.put("total", totalAmount.toString());
		return ajaxJson(hashmap);
	}

	// 刷卡撤销
	public String creditundo() {
		WorkingBill workingbill = workingBillService.get(workingBillId);
		/*UnitConversion unitconversion = unitConversionService.getRatioByMatnr(workingbill.getMatnr(),UNITCODE);
		ratio = unitconversion.getConversationRatio().intValue();		
		if (ratio == null || ratio.equals("")) {
	           return ajaxJsonErrorMessage("请在计量单位转换表中维护物料编码对应的换算数据!");
			}*/
		Admin admin = adminService.getByCardnum(cardnumber);
		
		String warehouse = admin.getTeam().getFactoryUnit().getWarehouse();// 线边仓
		String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();// 工厂		
		ThinkWayUtil util = new ThinkWayUtil();
		String budat = util.SystemDate();// 过账日期
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			enteringwareHouse = enteringwareHouseService.load(ids[i]);
			if (UNDO.equals(enteringwareHouse.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
			if(UNCONFIRM.equals(enteringwareHouse.getState())){//未确认的直接撤销
				enteringwareHouse.setState(UNDO);
				enteringwareHouseService.update(enteringwareHouse);
				
			}
		}
		List<EnteringwareHouse> list = enteringwareHouseService.get(ids);
		String charg = enteringwareHouseService.getCharg(workingbill);
		List<EnteringwareHouse> enterList = new ArrayList<EnteringwareHouse>();
		for(EnteringwareHouse e:list){
			e.setBudat(budat);
			e.setWerks(werks);
			e.setLgort(warehouse);
			e.setMoveType("102");
			e.setBatch(charg);
			if(CONFIRMED.equals(e.getState())){//将已确认的放进去
				enterList.add(e);
			}		
		}
		if(enterList.size()>0){//已确认的数据调sap接口
			
		try {
			List<EnteringwareHouse> aufnr=enteringwareHouseRfc.WarehousingCrt("",enterList);
			for(EnteringwareHouse e:aufnr){
				if("E".equalsIgnoreCase(e.getE_type()))
				{
					return this.ajaxJsonErrorMessage(e.getE_message());
				}
				
			}	

			enteringwareHouseService.updateState(aufnr, UNDO, workingbill,cardnumber);
		} catch (IOException e1) {
			e1.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常");
		} catch (CustomerException e1) {
			e1.printStackTrace();
			return ajaxJsonErrorMessage(e1.getMsgDes());
		}catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现错误，请联系系统管理员");
		}
		
		}
		
		workingbill = workingBillService.get(workingBillId);
		/*List<EnteringwareHouse> enteringwares = enteringwareHouseService
				.getByBill(workingBillId);
		for (int i = 0; i < enteringwares.size(); i++) {
			totalAmount += enteringwares.get(i).getStorageAmount();
		}*/
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalSingleAmount", workingbill.getTotalSingleAmount()
				.toString());
		//hashmap.put("total", totalAmount.toString());
		return ajaxJson(hashmap);
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
	
	
	public String record(){
		return "record";
	}
	
	/**
	 * 入库记录表
	 * 
	 * @return
	 */
	public String recordAjlist() {
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
			enteringwareHouse.setFactory(factoryService.getFactoryByCode(
					enteringwareHouse.getWorkingbill().getWerks())
					.getFactoryName());// 工厂
			enteringwareHouse.setWorkshop(factoryUnitService
					.getUnitByWorkCenter(
							enteringwareHouse.getWorkingbill().getWorkcenter())
					.getWorkShop().getWorkShopName());// 车间
			enteringwareHouse.setLocation(factoryUnitService
					.getUnitByWorkCenter(
							enteringwareHouse.getWorkingbill().getWorkcenter())
					.getWarehouseName());// 库存地点
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

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
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

	public List<Dict> getAllMoudle() {
		return dictService.getList("dictname", "moudleType");
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	
	
}