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

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.RepairRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.RepairPieceService;
import cc.jiuyi.service.RepairService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 返修
 * 
 */
@ParentPackage("admin")
public class RepairAction extends BaseAdminAction {

	private static final long serialVersionUID = -5187671258106950991L;
	public static Logger log = Logger.getLogger(RepairAction.class);
	private static final String CONFIRMED = "1";
	private static final String UNDO = "3";

	private Repair repair;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;
	private List<Process> allProcess;
	private String cardnumber;// 刷卡卡号
	private String add;//新增时
	private String edit;//编辑时
	private String show;//查看时
	private List<ProcessRoute> processRouteList;//工艺路线
	private String info;
	private List<RepairPiece>list_rp;//子件
	private String loginid;//当前登录人
	private String maktx;
	private String mblnr;
	private String end;
	private String start;
	private String state;
	private List<Dict>list_dict;
	private String costcenter;
	private String departmentName;//部门描述
	private List<Dict> processList;//责任工序 

	@Resource
	private TempKaoqinService tempKaoqinService;
	@Resource
	private RepairService repairService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;
	@Resource
	private ProcessRouteService processRouteService;
	@Resource
	private BomService bomService;
	@Resource
	private FactoryUnitService fuService;//单元
	@Resource
	private RepairRfc repairrfc;
	@Resource
	private RepairPieceService repairPieceService;
	
	//返修记录列表 @author Reece 2016/03/15
	public String historylist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("createDate");
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
			if (obj.get("mblnr") != null) {
				String mblnr = obj.getString("mblnr")
						.toString();
				map.put("mblnr", mblnr);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state")
						.toString();
				map.put("state", state);
			}
			if (obj.get("start") != null) {
				String start = obj.getString("start").toString();
				map.put("start", start);
			}
			if (obj.get("end") != null) {
				String end = obj.getString("end").toString();
				map.put("end", end);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

		pager = repairPieceService.historyjqGrid(pager, map);
		List<RepairPiece> rpList = pager.getList();
		List<RepairPiece> lst = new ArrayList<RepairPiece>();
		for (int i = 0; i < rpList.size(); i++) 
		{
			RepairPiece repairPiece = rpList.get(i);
			repairPiece.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "repairState", repairPiece.getRepair().getState()));
			if (repairPiece.getRepair().getConfirmUser() != null) {
				repairPiece.setConfirmUser((repairPiece.getRepair().getConfirmUser().getName()));
			}
			repairPiece.setCreateName(repairPiece.getRepair().getCreateUser().getName());	
			repairPiece.setWorkingbillCode(workingBillService.get(
					repairPiece.getRepair().getWorkingbill().getId()).getWorkingBillCode());
			repairPiece.setMaktx(workingBillService.get(
					repairPiece.getRepair().getWorkingbill().getId()).getMaktx());
			repairPiece.setState(repairPiece.getRepair().getState());
			repairPiece.setRepairPart(repairPiece.getRepair().getRepairPart());
			repairPiece.setRepairAmount(repairPiece.getRepair().getRepairAmount());
			repairPiece.setDuty(repairPiece.getRepair().getDuty());
			repairPiece.setCharg(repairPiece.getRepair().getCharg());
			repairPiece.setProductDate(repairPiece.getRepair().getWorkingbill().getProductDate());
			repairPiece.setMblnr(repairPiece.getRepair().getEX_MBLNR());
			repairPiece.setMatnr(repairPiece.getRepair().getWorkingbill().getMatnr());
			lst.add(repairPiece);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(RepairPiece.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	//Excel导出 @author Reece 2016/3/15
		public String excelexport(){
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("mblnr", mblnr);
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
	        header.add("产品数量");
	        
/*	        header.add("组件编码");
	        header.add("组件名称");
	        header.add("组件数量");
	        header.add("组件总数量");*/
	        
	        header.add("返修部位");
	        header.add("返修数量");
	        header.add("责任人");
	        header.add("批次");
	        header.add("物料凭证号");
	        
	        header.add("返修日期");
	        header.add("创建人");
	        header.add("确认人");
	        header.add("状态");
	        
		List<Object[]> repairList = repairPieceService.historyExcelExport(map);
		for (int i = 0; i < repairList.size(); i++) {
			Object[] obj = repairList.get(i);
			RepairPiece repairPiece = (RepairPiece) obj[0];
			Repair repair = (Repair) obj[1];
			WorkingBill workingbill = (WorkingBill) obj[2];// workingbill

			Object[] bodyval = {
					workingbill.getWorkingBillCode(),
					workingbill.getProductDate(),
					workingbill.getMatnr(),
					workingbill.getMaktx(),
					repairPiece.getProductnum()
					/*
					 * ,repairPiece.getRpcode(),
					 * repairPiece.getRpname(),
					 * repairPiece.getPiecenum(),
					 * repairPiece.getRpcount()
					 */
					,
					repair.getRepairPart(),
					repair.getRepairAmount(),
					repair.getDuty(),
					repair.getCharg(),
					repair.getEX_MBLNR(),
					repairPiece.getCreateDate(),
					repair.getCreateUser() == null ? "" : repair
							.getCreateUser().getName(),
					repair.getConfirmUser() == null ? "" : repair
							.getConfirmUser().getName(),
					ThinkWayUtil.getDictValueByDictKey(dictService,
							"repairState", repair.getState()) };
			body.add(bodyval);
		}

			try {
				String fileName = "成品返修记录表"+".xls";
				setResponseExcel(fileName);
				ExportExcel.exportExcel("成品返修记录表", header, body, getResponse().getOutputStream());
				getResponse().getOutputStream().flush();
			    getResponse().getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	

	public String list() {
		/*admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行返修操作!");
			return ERROR;
		}*/
		workingbill = workingBillService.get(workingBillId);
		return "list";
	}

	// 添加
	public String add() 
	{
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行返修操作!");
			return ERROR;
		}
		this.list_dict=dictService.getList("dictname", "moudleType");
		workingbill = workingBillService.get(workingBillId);
		//String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		//String productDate = workingbill.getProductDate();
		departmentName="返修部";
		costcenter="10008431";
		processRouteList = new ArrayList<ProcessRoute>();
		//根据订单号,生产日期查询工艺路线
		//processRouteList= this.processRouteService.findProcessRoute(aufnr, productDate);
		processList = dictService.getList("dictname", "process");
		this.add="add";
		return INPUT;
	}

	// 历史返修记录
	public String history() {
		return "history";
	}

	// 编辑
	public String edit() 
	{
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行返修操作!");
			return ERROR;
		}
		
		this.list_dict=dictService.getList("dictname", "moudleType");
		repair = repairService.get(id);//根据id查询
		list_rp=new ArrayList<RepairPiece>(repair.getRpieceSet());//获取组件数据
		workingbill = workingBillService.get(workingBillId);//当前随工单
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		String productDate = workingbill.getProductDate();
		processRouteList = new ArrayList<ProcessRoute>();
		processList = dictService.getList("dictname", "process");
//		processRouteList= processRouteService.findProcessRoute(aufnr, productDate);
		this.edit="edit";
		return INPUT;
	}

	// 保存
	public String creditsave()
	{
		Admin loginAdmin= adminService.get(loginid);//登陆人
		List<Bom>list_bom=getbomlist();//获取物料表中包含list1中的数据
		this.repairService.saveData(repair,cardnumber,list_rp,list_bom,loginAdmin);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	//修改
	public String creditupdate() 
	{	
		Admin loginAdmin= adminService.get(loginid);//登陆人
		List<Bom>list_bom=getbomlist();//获取物料表中包含list1中的数据
		this.repairService.updateData(repair,list_rp,cardnumber,list_bom,loginAdmin);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡确认
	public String creditapproval() {
		
		admin = adminService.getByCardnum(cardnumber);		
		/*admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			return ajaxJsonErrorMessage("您当前未上班,不能进行返修操作!");
		}*/
		
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			repair = repairService.load(ids[i]);
			if (CONFIRMED.equals(repair.getState())) 
			{
				return ajaxJsonErrorMessage("已确认的无须再确认!");
			}
			/*if (UNDO.equals(repair.getState())) 
			{
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}*/
		}
		List<Repair> list = repairService.get(ids);
		String str=toSAP(list);//与SAP
		workingbill = workingBillService.get(workingBillId);
//		Integer totalamount = workingbill.getTotalRepairAmount();
//		for(Repair r:list){
//			totalamount = r.getRepairAmount() + totalamount;
//		}
//		workingbill.setTotalRepairAmount(totalamount);
//		workingBillService.update(workingbill);
		String isSuccess=ERROR;
		if("S".equals(str))
		{
			isSuccess=SUCCESS;
			str="您的操作已成功!";
		}
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, isSuccess);
		hashmap.put(MESSAGE, str);
		hashmap.put("totalAmount", workingbill.getTotalRepairAmount().toString());
		return ajaxJson(hashmap);
	}

	// 刷卡撤销
	public String creditundo() {
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
	//		repair = repairService.load(ids[i]);
			repair = repairService.get(ids[i]);
			if (UNDO.equals(repair.getState())) {
				// addActionError("已撤销的无法再撤销！");
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		List<Repair> list = repairService.get(ids);
		String msg = repairService.updateState(list, UNDO, workingBillId, cardnumber);	
		workingbill = workingBillService.get(workingBillId);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		if(!msg.equals("您的操作已成功!")){
			hashmap.put(STATUS, ERROR);
			hashmap.put(MESSAGE, msg);
			hashmap.put("totalAmount", workingbill.getTotalRepairAmount()
					.toString());
			return ajaxJson(hashmap);
		}
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalAmount", workingbill.getTotalRepairAmount().toString());
		return ajaxJson(hashmap);
	}

	public String browser() {
		return "browser";
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
			pager.setOrderBy("createDate");
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

		pager = repairService.findPagerByjqGrid(pager, map, workingBillId);
		@SuppressWarnings("unchecked")
		List<Repair> repairList = pager.getList();
		List<Repair> lst = new ArrayList<Repair>();
		for (int i = 0; i < repairList.size(); i++) 
		{
			Repair repair = (Repair) repairList.get(i);
			repair.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "repairState", repair.getState()));
			if (repair.getConfirmUser() != null) {
				repair.setAdminName(repair.getConfirmUser().getName());
			}
			repair.setCreateName(repair.getCreateUser().getName());
			if(repair.getProcessCode()!=null)
			{
				workingbill = workingBillService.get(workingBillId);
				String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
				String productDate = workingbill.getProductDate();
				//生产订单号,日期,编码查询一条工艺路线
				ProcessRoute pr= processRouteService.getOneByConditions(aufnr, productDate,repair.getProcessCode());
		//		if(pr!=null)
				if(repair.getProcessCode()!=null)
				{
			//		repair.setResponseName(pr.getProcessName());
					repair.setResponseName(ThinkWayUtil.getDictValueByDictKey(dictService,"process",repair.getProcessCode()));
				}
				repair.setXrepairtype(ThinkWayUtil.getDictValueByDictKey(dictService, "repairtype",repair.getRepairtype()));//成品/子件
			}
			if(repair.getMould()!=null)
			{
				repair.setXmould(ThinkWayUtil.getDictValueByDictKey(dictService, "moudleType",repair.getMould()));//模具
			}
			lst.add(repair);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Repair.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}
	/**
	 * 转到添加产品子件页面
	 */
	public String beforegetpiece()
	{
		this.workingBillId=this.info;
		return "alert";
	}
	
	/**
	 * 获取对应随工单的产品子件
	 */
	public String getpiece()
	{
		HashMap<String ,String>map=new HashMap<String,String>();
		if(pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);//倒序
		pager.setOrderBy("createDate");//以创建日期排序
		if(pager.is_search()==true&&Param!=null)
		{
			JSONObject obj=JSONObject.fromObject(Param);
			//子件编码
			if (obj.get("piececode") != null)
			{
				String piececode = obj.getString("piececode").toString();
				map.put("piececode", piececode);
			}
			//子件名称
			if (obj.get("piecename") != null)
			{
				String piecename = obj.getString("piecename").toString();
				map.put("piecename", piecename);
			}
		}
		workingbill = workingBillService.get(workingBillId);
		pager = this.bomService.getPieceByCondition(pager, map,workingbill);//(根据:子件编码/名称,随工单)查询
		@SuppressWarnings("unchecked")
		List<Bom>list1=pager.getList();
		String workCenter = workingbill.getWorkcenter();
		List<Bom>list2 = new ArrayList<Bom>();
		if(workCenter!=null){
			FactoryUnit factoryUnit = fuService.get("factoryUnitCode", workCenter); 
			list2=this.repairService.getIncludedByMaterial(list1,workingbill.getPlanCount(),factoryUnit);//获取物料表中包含list1中的数据
		}else{
			return ajaxJsonErrorMessage("该随工单没有工作中心");
		}
		pager.setList(list2);
		pager.setTotalCount(list2.size());//更新总数量
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Bom.class));//排除有关联关系的属性字段 
		JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}
	
	/**
	 * 转到添加单元中的成本中心页面
	 */
	public String beforegetcostcenter()
	{
		return "costcenter";
	}
	
	/**
	 * 获取单元中的成本中心
	 */
	public String getcostcenter()
	{
		if(pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);//倒序
		pager.setOrderBy("createDate");//以创建日期排序
		pager = this.fuService.getCostCenter(pager,"CX");//(根据:子件编码/名称,随工单)查询
		@SuppressWarnings("unchecked")
		List<FactoryUnit>list1=pager.getList();
		List<FactoryUnit>list2=new ArrayList<FactoryUnit>();
		for(int i=0;i<list1.size();i++)
		{
			FactoryUnit fu=list1.get(i);
			//成型/挤出
			fu.setxCXORJC(ThinkWayUtil.getDictValueByDictKey(dictService, "fucxorjc", fu.getCXORJC()));
			//是否可以返修/返修收获
			fu.setXiscanrepair(ThinkWayUtil.getDictValueByDictKey(dictService, "factoryUnitIscanrepair", fu.getIscanrepair()));
			list2.add(fu);
		}
		pager.setList(list2);
		pager.setTotalCount(list2.size());//更新总数量
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(FactoryUnit.class));//排除有关联关系的属性字段 
		JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}
	
	/**
	 * 查看
	 */
	public String show()
	{
		repair = repairService.get(id);//根据id查询
		repair.setXrepairtype(ThinkWayUtil.getDictValueByDictKey(dictService, "repairtype", repair.getRepairtype()));
		list_rp=new ArrayList<RepairPiece>(repair.getRpieceSet());//获取组件数据
		workingbill = workingBillService.get(workingBillId);//当前随工单
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		String productDate = workingbill.getProductDate();
		//生产订单号,日期,编码查询一条工艺路线
		ProcessRoute pr= processRouteService.getOneByConditions(aufnr, productDate,repair.getProcessCode());
	//	if(pr!=null)
		if(repair.getProcessCode()!=null)
		{
			repair.setResponseName(ThinkWayUtil.getDictValueByDictKey(dictService,"process",repair.getProcessCode()));
		}
		if(repair.getMould()!=null)
		{
			repair.setXmould(ThinkWayUtil.getDictValueByDictKey(dictService, "moudleType",repair.getMould()));//模具
		}
		this.show="show";
		return INPUT;
	}

	/**
	 * 历史查看
	 */
	public String showHistory()
	{
		repair = repairService.get(id);//根据id查询
		repair.setXrepairtype(ThinkWayUtil.getDictValueByDictKey(dictService, "repairtype", repair.getRepairtype()));
		list_rp=new ArrayList<RepairPiece>(repair.getRpieceSet());//获取组件数据
		workingbill = repair.getWorkingbill();//当前随工单
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		String productDate = workingbill.getProductDate();
		//生产订单号,日期,编码查询一条工艺路线
		ProcessRoute pr= processRouteService.getOneByConditions(aufnr, productDate,repair.getProcessCode());
	//	if(pr!=null)
		if(repair.getProcessCode()!=null)
		{
			repair.setResponseName(ThinkWayUtil.getDictValueByDictKey(dictService,"process",repair.getProcessCode()));
		}
		if(repair.getMould()!=null)
		{
			repair.setXmould(ThinkWayUtil.getDictValueByDictKey(dictService, "moudleType",repair.getMould()));//模具
		}
		this.show="show";
		return INPUT;
	}
	
	
	/**
	 * 与SAP交互   退料262  905
	 * list 主表数据   wbid随工单对象
	 * @return
	 * @author gyf
	 */
	public String toSAP(List<Repair>list)
	{
		try
		{
			// 取出主表及组件数据
			for (int i = 0; i < list.size(); i++)
			{
				Repair r = list.get(i);
				Admin a= adminService.get(loginid);
				r.setLGORT(a.getTeam().getFactoryUnit().getWarehouse());//库存地点
				r.setWERKS(a.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂SAP测试数据 工厂编码
				List<RepairPiece> listrp =new ArrayList<RepairPiece>(r.getRpieceSet());// 取出对应的组件
				if (listrp.size() > 0)
				{
					/**有组件数据,进行SAP交互*/
					// 调用SAP，执行数据交互，返回List，并判断数据交互中是否成功，成功的更新本地数据库，失败的则不保存
					Repair r_sapreturn1 = repairrfc.repairCrt("X",r, listrp);
					if(r_sapreturn1.getEX_MBLNR()!=null && !"".equals(r_sapreturn1.getEX_MBLNR())){
						log.info("---X----ex_mblnr---"+r_sapreturn1.getEX_MBLNR());
					}
					/** 出现问题 */
					if ("E".equalsIgnoreCase(r_sapreturn1.getE_TYPE()))
					{
						return r_sapreturn1.getE_MESSAGE();
					}
					else
					{
						Repair r_sapreturn = repairrfc.repairCrt("",r, listrp);
						if ("E".equalsIgnoreCase(r_sapreturn.getE_TYPE()))
						{
							return r_sapreturn.getE_MESSAGE();
						}
						log.info("-------mblnr---"+r_sapreturn.getEX_MBLNR());
						if(r_sapreturn.getEX_MBLNR()==null || "".equals(r_sapreturn.getEX_MBLNR())){
							return r_sapreturn.getWorkingbill().getWorkingBillCode()+"未返回凭证;";
						}
						/** 与SAP交互没有问题,更新本地数据库 */
						this.repairService.updateMyData(r_sapreturn, cardnumber,1,workingBillId);
					}
				}
				else
				{
					/**没有组件数据,只把状态改为确认*/
					this.repairService.updateMyData(r, cardnumber,2,"");
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return "IO出现异常，请联系系统管理员";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "系统出现问题，请联系系统管理员";
		}
		return "S";
	}

	/**
	 * 查询bom,并从物料表中过滤
	 */
	public List<Bom>getbomlist()
	{
		workingbill = workingBillService.get(workingBillId);
		String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
		String productDate = workingbill.getProductDate();
		String workingBillCode=workingbill.getWorkingBillCode();
		List<Bom>list1=this.bomService.findBom(aufnr, productDate, workingBillCode);
		String workCenter = workingbill.getWorkcenter();
		List<Bom>list_bom = new ArrayList<Bom>();
		if(workCenter!=null){
			FactoryUnit factoryUnit = fuService.get("factoryUnitCode", workCenter); 
			list_bom=this.repairService.getIncludedByMaterial(list1,workingbill.getPlanCount(),factoryUnit);//获取物料表中包含list1中的数据
		}
//		List<Bom>list_bom=this.repairService.getIncludedByMaterial(list1,workingbill.getPlanCount());//获取物料表中包含list1中的数据
		return list_bom;
	}
	
	public Repair getRepair() {
		return repair;
	}

	public void setRepair(Repair repair) {
		this.repair = repair;
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

	public List<Process> getAllProcess() {
		return allProcess;
	}

	public void setAllProcess(List<Process> allProcess) {
		this.allProcess = allProcess;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getAdd()
	{
		return add;
	}

	public void setAdd(String add)
	{
		this.add = add;
	}

	public String getEdit()
	{
		return edit;
	}

	public void setEdit(String edit)
	{
		this.edit = edit;
	}

	public String getShow()
	{
		return show;
	}

	public void setShow(String show)
	{
		this.show = show;
	}

	public List<ProcessRoute> getProcessRouteList()
	{
		return processRouteList;
	}

	public void setProcessRouteList(List<ProcessRoute> processRouteList)
	{
		this.processRouteList = processRouteList;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public List<RepairPiece> getList_rp()
	{
		return list_rp;
	}

	public void setList_rp(List<RepairPiece> list_rp)
	{
		this.list_rp = list_rp;
	}

	public String getLoginid()
	{
		return loginid;
	}

	public void setLoginid(String loginid)
	{
		this.loginid = loginid;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}
	
	public String getMblnr() {
		return mblnr;
	}

	public void setMblnr(String mblnr) {
		this.mblnr = mblnr;
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

	public List<Dict> getList_dict()
	{
		return list_dict;
	}

	public void setList_dict(List<Dict> list_dict)
	{
		this.list_dict = list_dict;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List<Dict> getProcessList() {
		return processList;
	}

	public void setProcessList(List<Dict> processList) {
		this.processList = processList;
	}
	
	
}
