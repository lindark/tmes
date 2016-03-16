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
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairinPiece;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.RepairInRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.RepairinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 返修收货
 * 
 */
@ParentPackage("admin")
public class RepairinAction extends BaseAdminAction {

	private static final long serialVersionUID = -5368121517667092305L;

	private static final String CONFIRMED = "1";
	private static final String UNDO = "3";

	private Repairin repairin;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;
	private String cardnumber;// 刷卡卡号
	private String add;//新增时
	private String edit;//编辑时
	private String show;//查看时
	private List<ProcessRoute> processRouteList;//工艺路线
	private String info;
	private List<RepairinPiece>list_rp;//子件
	private String loginid;
	private String maktx;
	private String EX_MBLNR;
	private String state;
	private String end;
	private String start;

	public String getLoginid()
	{
		return loginid;
	}

	public void setLoginid(String loginid)
	{
		this.loginid = loginid;
	}

	@Resource
	private RepairinService repairinService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private BomService bomService;
	@Resource
	private FactoryUnitService fuService;//单元
	@Resource
	private RepairInRfc repairinRfc;
	
	
	//返修收货记录列表 @author Reece 2016/3/17
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
			if (obj.get("EX_MBLNR") != null) {
				String EX_MBLNR = obj.getString("EX_MBLNR")
						.toString();
				map.put("EX_MBLNR", EX_MBLNR);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state")
						.toString();
				map.put("state", state);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
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
			repairin.setWorkingbillCode(workingBillService.get(
					repairin.getWorkingbill().getId()).getWorkingBillCode());
			repairin.setMaktx(workingBillService.get(
					repairin.getWorkingbill().getId()).getMaktx());
			repairin.setMatnr(workingBillService.get(
					repairin.getWorkingbill().getId()).getMatnr());
			repairin.setProductDate(workingBillService.get(
					repairin.getWorkingbill().getId()).getProductDate());
			lst.add(repairin);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Repairin.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	// Excel导出 @author Reece 2016/3/17
	public String excelexport() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("EX_MBLNR", EX_MBLNR);
		map.put("maktx", maktx);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);

		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		header.add("随工单号");
		header.add("产品编码");
		header.add("产品名称");
		header.add("生产日期");

		header.add("收货数量");
		header.add("物料凭证号");
		header.add("创建日期");
		header.add("创建人");
		header.add("确认人");
		header.add("状态");

		List<Object[]> repairinList = repairinService.historyExcelExport(map);
		for (int i = 0; i < repairinList.size(); i++) {
			Object[] obj = repairinList.get(i);
			Repairin repairin = (Repairin) obj[0];
			WorkingBill workingbill = (WorkingBill) obj[1];// workingbill

			Object[] bodyval = {
					workingbill.getWorkingBillCode(),
					workingbill.getMatnr(),
					workingbill.getMaktx(),
					workingbill.getProductDate(),
					repairin.getReceiveAmount(),
					repairin.getEX_MBLNR(),
					repairin.getCreateDate(),
					repairin.getCreateUser() == null ? "" : repairin
							.getCreateUser().getName(),
					repairin.getConfirmUser() == null ? "" : repairin
							.getConfirmUser().getName(),
					ThinkWayUtil.getDictValueByDictKey(dictService,
							"repairinState", repairin.getState()) };
			body.add(bodyval);
		}

		try {
			String fileName = "返修收货记录表" + ".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("返修收货记录表", header, body, getResponse()
					.getOutputStream());
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
			addActionError("您当前未上班,不能进行返修收货操作!");
			return ERROR;
		}
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}

	// 添加
	public String add() {
		workingbill = workingBillService.get(workingBillId);
		this.add="add";
		return INPUT;
	}
	// 编辑
	public String edit() 
	{
		repairin = repairinService.get(id);//根据id查询
		list_rp=new ArrayList<RepairinPiece>(repairin.getRpieceSet());//获取组件数据
		workingbill = workingBillService.get(workingBillId);//当前随工单
		this.edit="edit";
		return INPUT;
	}

	// 历史返修记录
	public String history() {
		return "history";
	}

	// 保存
	public String creditsave()
	{
		List<Bom>list_bom=getbomlist();//获取物料表中包含list1中的数据
		this.repairinService.saveData(repairin,cardnumber,list_rp,list_bom);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 更新
	public String creditupdate()
	{
		List<Bom>list_bom=getbomlist();//获取物料表中包含list1中的数据
		this.repairinService.updateData(repairin,list_rp,cardnumber,list_bom);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡确认
	public String creditapproval() {
		// workingbill = workingBillService.get(workingBillId);
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			repairin = repairinService.load(ids[i]);
			if (CONFIRMED.equals(repairin.getState()))
			{
				return ajaxJsonErrorMessage("已确认的无须再确认!");
			}
		}
		List<Repairin> list = repairinService.get(ids);
		workingbill = workingBillService.get(workingBillId);
		String str=toSAP(list);
		String isSuccess=ERROR;
		if("S".equals(str))
		{
			isSuccess=SUCCESS;
			str="您的操作已成功!";
		}
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, isSuccess);
		hashmap.put(MESSAGE, str);
		hashmap.put("totalAmount", workingbill.getTotalRepairinAmount().toString());
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
		repairinService.updateState(list, UNDO, workingBillId, cardnumber);
		workingbill = workingBillService.get(workingBillId);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功");
		hashmap.put("totalAmount", workingbill.getTotalRepairinAmount()
				.toString());
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
		pager = repairinService.findPagerByjqGrid(pager, map, workingBillId);
		@SuppressWarnings("unchecked")
		List<Repairin> repairinList = pager.getList();
		List<Repairin> lst = new ArrayList<Repairin>();
		for (int i = 0; i < repairinList.size(); i++) 
		{
			Repairin repairin = (Repairin) repairinList.get(i);
			repairin.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "repairState", repairin.getState()));
			if (repairin.getConfirmUser() != null) {
				repairin.setAdminName(repairin.getConfirmUser().getName());
			}
			repairin.setCreateName(repairin.getCreateUser().getName());
			repairin.setXrepairintype(ThinkWayUtil.getDictValueByDictKey(dictService, "repairintype",repairin.getRepairintype()));//成品/子件
			lst.add(repairin);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Repairin.class));// 排除有关联关系的属性字段
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
		List<Bom>list2=this.repairinService.getIncludedByMaterial(list1,workingbill.getPlanCount());//获取物料表中包含list1中的数据
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
		pager = this.fuService.getCostCenter(pager,null);//(根据:子件编码/名称,随工单)查询
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
		repairin = repairinService.get(id);//根据id查询
		repairin.setXrepairintype(ThinkWayUtil.getDictValueByDictKey(dictService, "repairintype", repairin.getRepairintype()));
		list_rp=new ArrayList<RepairinPiece>(repairin.getRpieceSet());//获取组件数据
		workingbill = workingBillService.get(workingBillId);//当前随工单
		this.show="show";
		return INPUT;
	}


	/**
	 * 历史查看
	 */
	public String showHistory()
	{
		repairin = repairinService.get(id);//根据id查询
		repairin.setXrepairintype(ThinkWayUtil.getDictValueByDictKey(dictService, "repairintype", repairin.getRepairintype()));
		list_rp=new ArrayList<RepairinPiece>(repairin.getRpieceSet());//获取组件数据
		workingbill = repairin.getWorkingbill();//当前随工单
		this.show="show";
		return INPUT;
	}
	
	/**
	 * 
	 * 与SAP交互   退料262  905
	 * list 主表数据   wbid随工单对象
	 * @return
	 * @author gyf
	 */
	public String toSAP(List<Repairin>list)
	{
		try
		{
			// 取出主表及组件数据
			for (int i = 0; i < list.size(); i++)
			{
				Repairin r = list.get(i);
				Admin a= adminService.get(loginid);
				r.setLGORT(a.getDepartment().getTeam().getFactoryUnit().getWarehouse());//库存地点
				r.setWERKS(a.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());//工厂SAP测试数据 工厂编码
				List<RepairinPiece> listrp = new ArrayList<RepairinPiece>(r.getRpieceSet());// 取出对应的组件
				if (listrp.size() > 0)
				{
					/**有组件数据,进行SAP交互*/
					// 调用SAP，执行数据交互，返回List，并判断数据交互中是否成功，成功的更新本地数据库，失败的则不保存
					Repairin r_sapreturn1 = repairinRfc.repairinCrt("X",r, listrp);
					/** 出现问题 */
					if ("E".equalsIgnoreCase(r_sapreturn1.getE_TYPE()))
					{
						return r_sapreturn1.getE_MESSAGE();
					}
					else
					{
						Repairin r_sapreturn = repairinRfc.repairinCrt("",r, listrp);
						if ("E".equalsIgnoreCase(r_sapreturn.getE_TYPE()))
						{
							return r_sapreturn.getE_MESSAGE();
						}
						/** 与SAP交互没有问题,更新本地数据库 */
						this.repairinService.updateMyData(r_sapreturn, cardnumber,1,workingBillId);
					}
				}
				else
				{
					/**没有组件数据,只把状态改为确认*/
					this.repairinService.updateMyData(r, cardnumber,2,"");
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
		List<Bom>list_bom=this.repairinService.getIncludedByMaterial(list1,workingbill.getPlanCount());//获取物料表中包含list1中的数据
		return list_bom;
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

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public List<RepairinPiece> getList_rp()
	{
		return list_rp;
	}

	public void setList_rp(List<RepairinPiece> list_rp)
	{
		this.list_rp = list_rp;
	}

	public List<ProcessRoute> getProcessRouteList()
	{
		return processRouteList;
	}

	public void setProcessRouteList(List<ProcessRoute> processRouteList)
	{
		this.processRouteList = processRouteList;
	}

	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getEX_MBLNR() {
		return EX_MBLNR;
	}

	public void setEX_MBLNR(String eX_MBLNR) {
		EX_MBLNR = eX_MBLNR;
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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	
	
}
