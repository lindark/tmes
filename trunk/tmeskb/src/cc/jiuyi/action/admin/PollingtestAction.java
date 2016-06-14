package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Arrays;
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
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Pollingtest;
import cc.jiuyi.entity.PollingtestRecord;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.PollingtestRecordService;
import cc.jiuyi.service.PollingtestService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
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
	private String cardnumber;// 刷卡卡号

	
	private String workingBillCode;
	private String maktx;
	private String end;
	private String start;
	private String state;
	private String pollingtestUserName;// 巡检人姓名
	private String confirmUserName;// 确认人姓名
	
	
	
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
	@Resource
	private UnitdistributeModelService unitdistributeModelService;
	@Resource
	private UnitdistributeProductService unitdistributeProductService;
	@Resource
	private FactoryUnitService factoryUnitService;
	
	public String list() {
		admin = adminService.getLoginAdmin();
		workingbill = workingBillService.get(workingBillId);
		return "list";
	}

	// 添加
	public String add() {
		workingbill = workingBillService.get(workingBillId);
		
		String  workCenter = workingbill.getWorkcenter();
		FactoryUnit fu = factoryUnitService.get("factoryUnitCode", workCenter);
		HashMap<String, String> map = new HashMap<String, String>();
		String matnr="";
		String funid="";
		if(fu!=null){
			funid =fu.getId(); 
		}
		matnr = workingbill.getMatnr();
		map.put("matnr", matnr);
		map.put("funid", funid);
		UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
		if(pager==null){
			pager=new Pager();
		}
		if(unitdistributeProduct!=null){
			String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
			map.put("matmr", matmr);
			pager = unitdistributeModelService.getUBMList(pager, map);
		}
		
		
		
		list_cause = causeService.getBySample("2");// 获取缺陷表中关于巡检的内容
		this.add = "add";
		return INPUT;
	}

	// 历史巡检记录
	public String history() {
		return "history";
	}

	// 编辑
	public String edit() {
		try {

			pollingtest = pollingtestService.load(id);
			workingbill = workingBillService.get(workingBillId);
			
			
			
			String  workCenter = workingbill.getWorkcenter();
			FactoryUnit fu = factoryUnitService.get("factoryUnitCode", workCenter);
			HashMap<String, String> map = new HashMap<String, String>();
			String matnr="";
			String funid="";
			if(fu!=null){
				funid =fu.getId(); 
			}
			matnr = workingbill.getMatnr();
			map.put("matnr", matnr);
			map.put("funid", funid);
			UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
			if(pager==null){
				pager=new Pager();
			}
			if(unitdistributeProduct!=null){
				String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
				map.put("matmr", matmr);
				pager = unitdistributeModelService.getUBMList(pager, map);
			}
			
			
			
			
			
			
			
			List<Cause> l_cause = causeService.getBySample("2");// 获取缺陷表中关于巡检的内容
			list_pollingtestRecord = pollingtestRecordService
					.findByPollingtestId(id);
			list_cause = new ArrayList<Cause>();
			for (int i = 0; i < l_cause.size(); i++) {
				Cause c = l_cause.get(i);
				for (int j = 0; j < list_pollingtestRecord.size(); j++) {
					PollingtestRecord sr = list_pollingtestRecord.get(j);
					if (c != null && sr != null) {
						if (c.getId().equals(sr.getCauseId())) {
							c.setCauseNum(sr.getRecordNum());
						}
					}
				}
				this.list_cause.add(c);
			}
			this.edit = "edit";
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 保存
	public String creditsave() throws Exception {
		if (pollingtest.getPollingtestAmount() == null
				|| String.valueOf(pollingtest.getPollingtestAmount()).matches(
						"^[0-9]*[1-9][0-9]*$ ")) {
			return ajaxJsonErrorMessage("巡检数量必须为零或正整数!");
		}
		Admin cardUser= adminService.getByCardnum(cardnumber);
		if(cardUser!=null){
			pollingtest.setShift(cardUser.getShift());
			pollingtest.setProductDate(cardUser.getProductDate());
		}
		pollingtestService
				.saveInfo(pollingtest, info, info2, my_id, cardnumber);
		/*
		 * redirectionUrl = "pollingtest!list.action?workingBillId=" +
		 * pollingtest.getWorkingbill().getId();
		 */
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 查看
	public String show() {
		pollingtest = pollingtestService.load(id);
		workingbill = workingBillService.get(workingBillId);
		pollingtestType = getCraftWorkString(pollingtest.getCraftWork());
		list_pollingtestRecord = pollingtestRecordService
				.findByPollingtestId(id);
		this.show = "show";
		return INPUT;
	}

	/**
	 * 修改
	 */
	public String creditupdate() {
		if (pollingtest.getPollingtestAmount() == null
				|| String.valueOf(pollingtest.getPollingtestAmount()).matches(
						"^[0-9]*[1-9][0-9]*$ ")) {
			return ajaxJsonErrorMessage("巡检数量必须为零或正整数!");
		}
		Admin cardUser= adminService.getByCardnum(cardnumber);
		if(cardUser!=null){
			pollingtest.setShift(cardUser.getShift());
			pollingtest.setProductDate(cardUser.getProductDate());
		}
		// 保存巡检单信息:巡检单，缺陷ID，缺陷数量，1保存/2确认
		this.pollingtestService.updateInfo(pollingtest, info, info2, my_id,
				cardnumber);
		/*redirectionUrl = "pollingtest!list.action?workingBillId="
				+ pollingtest.getWorkingbill().getId();*/
		return ajaxJsonSuccessMessage("您的操作已成功!");
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
				String maktx = obj.getString("maktx").toString();				
				map.put("maktx", maktx);				
			}
			if (obj.get("workingBillCode") != null) {
				String workingBillCode = obj.getString("workingBillCode")
						.toString();				
				map.put("workingBillCode", workingBillCode);
				
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
			if (obj.get("pollingtestUserName") != null) {
				String pollingtestUserName = obj.getString("pollingtestUserName").toString();
				
				map.put("pollingtestUserName", pollingtestUserName);
				
			}
			if (obj.get("confirmUserName") != null) {
				String confirmUserName = obj.getString("confirmUserName").toString();				
				map.put("confirmUserName", confirmUserName);
				
			}
		}
		
		
		pager = pollingtestService.historyjqGrid(pager, map);
		List<Pollingtest> pollingtestList = pager.getList();
		//System.out.println("结果："+pollingtestList.size());
		List<Pollingtest> lst = new ArrayList<Pollingtest>();
		for (int i = 0; i < pollingtestList.size(); i++) {
			Pollingtest pollingtest = (Pollingtest) pollingtestList.get(i);
			// 状态描述
			pollingtest.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "pollingtestState", pollingtest.getState()));
			// 工艺确认描述
			pollingtest
					.setCraftWorkRemark(getCraftWorkString(pollingtest.getCraftWork()));
			// 工单编号
			if (pollingtest.getWorkingBill() != null) {
				pollingtest
				.setWorkingBillCode(pollingtest.getWorkingBill().getWorkingBillCode());
			}
			// 产品名称
			if (pollingtest.getWorkingBill() != null) {
				pollingtest
				.setMaktx(pollingtest.getWorkingBill().getMaktx());
			}
			// 产品名称
			if (pollingtest.getWorkingBill() != null) {
				pollingtest
				.setMatnr(pollingtest.getWorkingBill().getMatnr());
			}
			// 确认人的名字
			if (pollingtest.getConfirmUser() != null) {
				pollingtest
						.setConfirmUserName(pollingtest.getConfirmUser().getName());
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
					.setCraftWorkRemark(getCraftWorkString(pollingtest.getCraftWork()));
			// 确认人的名字
			if (pollingtest.getConfirmUser() != null) {
				pollingtest
						.setConfirmUserName(pollingtest.getConfirmUser().getName());
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
	
	
	
	
	
	
	// Excel导出 @author Reece 2016/3/15
		public String excelexport() {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("workingBillCode", workingBillCode);
			map.put("maktx", maktx);
			map.put("state", state);
			map.put("start", start);
			map.put("end", end);
			map.put("pollingtestUserName", pollingtestUserName);
			map.put("confirmUserName", confirmUserName);

			List<String> header = new ArrayList<String>();
			List<Object[]> body = new ArrayList<Object[]>();
			header.add("随工单号");
			header.add("产品编号");
			header.add("产品名称");
			header.add("巡检数量");
			header.add("合格数量");
			header.add("合格率");
			header.add("工艺确认");
			header.add("巡检日期");
			header.add("巡检人");
			header.add("确认人");
			header.add("状态");

			List<Object[]> workList = pollingtestService.historyExcelExport(map);
			for (int i = 0; i < workList.size(); i++) {
				Object[] obj = workList.get(i);
				Pollingtest pollingtest = (Pollingtest) obj[0];//Pollingtest
	        	WorkingBill workingbill = (WorkingBill)obj[1];//workingBill
	        	Admin pollingtestUser = (Admin)obj[2];//pollingtestUser
	        	Admin confirmUser = (Admin)obj[3];//ponfirmUser	        	
	        	
				Object[] bodyval = {
						workingbill.getWorkingBillCode() == null ? "" : workingbill.getWorkingBillCode(),
						workingbill.getMatnr() == null ? "" : workingbill.getMatnr(),
						workingbill.getMaktx() == null ? "" : workingbill.getMaktx(),
						pollingtest.getPollingtestAmount() == null ? "" : pollingtest.getPollingtestAmount(),					
						pollingtest.getQualifiedAmount() == null ? "" : pollingtest.getQualifiedAmount(),
						pollingtest.getPassedPercent() == null ? "" : pollingtest.getPassedPercent(),
						getCraftWorkString(pollingtest.getCraftWork()),
						pollingtest.getCreateDate() == null ? "" : pollingtest.getCreateDate(),
						pollingtestUser == null ? "" : pollingtestUser.getName(),
						confirmUser == null ? "" : confirmUser.getName(),							
						ThinkWayUtil.getDictValueByDictKey(dictService,
								"pollingtestState", pollingtest.getState())
						};
				body.add(bodyval);
			}

			try {
				String fileName = "成品巡检记录表" + ".xls";
				setResponseExcel(fileName);
				ExportExcel.exportExcel("成品巡检记录表", header, body, getResponse()
						.getOutputStream());
				getResponse().getOutputStream().flush();
				getResponse().getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	
	
	
	
	
	
	// 刷卡确认
	public String creditapproval() {
		admin = adminService.getByCardnum(cardnumber);
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
//			Pollingtest pollingtest = pollingtestService.load(ids[i]);
			pollingtest = pollingtestService.get(ids[i]);
			if (CONFIRMED.equals(pollingtest.getState())) {
				// addActionError("已确认的无须再确认！");

				return ajaxJsonErrorMessage("已确认的无须再确认!");
			}
			if (UNDO.equals(pollingtest.getState())) {
				// addActionError("已撤销的无法再确认！");
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}
		}
		List<Pollingtest> list = pollingtestService.get(ids);
//		pollingtestService.confirm(list, admin, CONFIRMED);
		pollingtestService.updateConfirm(list, admin, CONFIRMED);
		// redirectionUrl = "pollingtest!list.action?workingBillId="+
		// pollingtest.getWorkingbill().getId();

		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡撤销
	public String creditundo() {
		admin = adminService.getLoginAdmin();
		ids = id.split(",");
		System.out.println("-------id-------"+id+"--------------");
		for (int i = 0; i < ids.length; i++) {
			pollingtest = pollingtestService.load(ids[i]);
			if (UNDO.equals(pollingtest.getState())) {
				// addActionError("已撤销的无法再撤销！");
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		System.out.println("-------ids-------"+Arrays.toString(ids)+"--------------");
		List<Pollingtest> list = pollingtestService.get(ids);
		System.out.println("-------size-------"+list.size()+"--------------");
		pollingtestService.confirm(list, admin, UNDO);
		/*
		 * redirectionUrl = "pollingtest!list.action?workingBillId=" +
		 * pollingtest.getWorkingbill().getId();
		 */
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	
	
	
	public String getCraftWorkString(String ids)
	{
		if(null == ids || ids.equals(""))
		{
			return "";
		}
		String result="";		
		String[] idList=ids.split(", ");		
		for(int i=0;i<idList.length;i++)
		{
			//System.out.println(i+":"+idList[i]);
			result+=ThinkWayUtil.getDictValueByDictKey(dictService, "craftWorkRemark", idList[i])+" ";
		}
		return result;
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

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getWorkingBillCode() {
		return workingBillCode;
	}

	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
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

	public String getPollingtestUserName() {
		return pollingtestUserName;
	}

	public void setPollingtestUserName(String pollingtestUserName) {
		this.pollingtestUserName = pollingtestUserName;
	}

	public String getConfirmUserName() {
		return confirmUserName;
	}

	public void setConfirmUserName(String confirmUserName) {
		this.confirmUserName = confirmUserName;
	}
	
}
