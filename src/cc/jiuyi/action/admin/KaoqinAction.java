package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import oracle.net.aso.l;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.Station;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.StationService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 考勤
 * 
 * @author gaoyf
 * 
 */
@ParentPackage("admin")
public class KaoqinAction extends BaseAdminAction {

	private static final long serialVersionUID = -7695470770728132309L;

	/**
	 * ========================variable,object,interface
	 * start========================
	 */

	/**
	 * 对象，变量
	 */
	private Kaoqin kaoqin;
	private Admin admin;
	private List<Dict> list_dict;// 员工状态
	private List<Kaoqin> list_kq;
	private List<Admin> list_emp;
	private String info;
	private String sameTeamId;// 当前班组ID
	private String isstartteam;// 是否开启班组
	private String iswork;// 班组是否在上班
	private String iscancreditcard;// 是否可以刷卡
	private String loginid;// 当前登录人的ID
	private String cardnumber;// 刷卡人
	private int my_id;
	private String productdate;
	private String empname;
	private String classtime;
	private String factoryUnitName;
	private String unitdistributeModels;// 工位
	private List<UnitdistributeModel> unitModelList = new ArrayList<UnitdistributeModel>();
	
	private String workNumber;
	private String xFactoryUnit;
	/**
	 * service接口
	 */
	@Resource
	private KaoqinService kqService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private TeamService teamService;
	@Resource
	private StationService stationService;
	@Resource
	private UnitdistributeModelService unitdistributeModelService;

	/**
	 * ========================end
	 * variable,object,interface==========================
	 */

	/**
	 * ========================method
	 * start======================================
	 */

	// 考勤记录 @author Reece 2016/3/25
	public String history() {
		return "history";
	}

	// 考勤记录列表 @author Reece 2016/3/25
	public String historylist() {
		HashMap<String, String> map = new HashMap<String, String>();

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

		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("productdate") != null) {
				String productdate = obj.getString("productdate").toString();
				map.put("productdate", productdate);
			}
			if (obj.get("classtime") != null) {
				String classtime = obj.getString("classtime").toString();
				map.put("classtime", classtime);
			}
			if (obj.get("empname") != null) {
				String empname = obj.getString("empname").toString();
				map.put("empname", empname);
			}
			if (obj.get("factoryUnitName") != null) {
				String factoryUnitName = obj.getString("factoryUnitName")
						.toString();
				map.put("factoryUnitName", factoryUnitName);
			}

		}
		pager = kqService.historyjqGrid(pager, map);
		List<Kaoqin> kqlist = pager.getList();
		List<Kaoqin> lst = new ArrayList<Kaoqin>();
		try {
			for (int i = 0; i < kqlist.size(); i++) {
				Kaoqin kaoqin = kqlist.get(i);
				if (kaoqin.getTeam() != null) {
					kaoqin.setXteam(kaoqin.getTeam().getTeamName());
				}
				kaoqin.setXclasstime(ThinkWayUtil.getDictValueByDictKey(
						dictService, "kaoqinClasses", kaoqin.getClasstime()));
				kaoqin.setFactoryUnitName(kaoqin.getEmp().getTeam()
						.getFactoryUnit().getFactoryUnitName());
				kaoqin.setFactory(kaoqin.getEmp().getTeam().getFactoryUnit()
						.getWorkShop().getFactory().getFactoryName());
				kaoqin.setWorkshop(kaoqin.getEmp().getTeam().getFactoryUnit()
						.getWorkShop().getWorkShopName());
				if(kaoqin.getWorkState()!=null&&!"".equals(kaoqin.getWorkState()))
				{
					kaoqin.setXworkState(ThinkWayUtil.getDictValueByDictKey(dictService,"adminworkstate", kaoqin.getWorkState()));
				}
				
				if(kaoqin.getModleNum()!=null)
				{
					String[] models=kaoqin.getModleNum().split(",");
					String modelsName="";
					for(int n=0;n<models.length;n++)
					{
						UnitdistributeModel model=unitdistributeModelService.get(models[n]);
						if(model != null)
						modelsName+=model.getStation()+",";
					}
					if(modelsName.length()>0) modelsName=modelsName.substring(0, modelsName.length()-1);
					kaoqin.setModelName(modelsName);	
					
				}
				
				lst.add(kaoqin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Kaoqin.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	// Excel导出 @author Reece 2016/3/3
	public String excelexport() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("productdate", productdate);
		map.put("empname", empname);
		map.put("classtime", classtime);
		map.put("factoryUnitName", factoryUnitName);

		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		header.add("员工卡号");
		header.add("姓名");
		header.add("工号");
		header.add("手机号");

		header.add("工厂");
		header.add("车间");
		header.add("生产日期");
		header.add("单元");
		header.add("班组");
		header.add("班次");

		header.add("岗位");
		header.add("工位");
		header.add("模具组号");
		header.add("工作范围");
		header.add("异常小时数");
		header.add("员工状态");

		List<Object[]> kqlist = kqService.historyExcelExport(map);
		for (int i = 0; i < kqlist.size(); i++) {
			Object[] obj = kqlist.get(i);
			Kaoqin kaoqin = (Kaoqin) obj[0];//
			Admin admin = (Admin) obj[1];//

			String mjzh="";
			if(kaoqin.getModleNum()!=null)
			{
				String[] models=kaoqin.getModleNum().split(",");					
				for(int n=0;n<models.length;n++)
				{
					UnitdistributeModel model=unitdistributeModelService.get(models[n]);
					if(model != null)
						mjzh+=model.getStation()+",";
				}
				if(mjzh.length()>0) mjzh=mjzh.substring(0, mjzh.length()-1);					
			}			
			
			Object[] bodyval = {
					kaoqin.getCardNumber(),
					admin.getName(),
					admin.getWorkNumber(),
					admin.getPhoneNo(),

					kaoqin.getEmp().getTeam().getFactoryUnit().getWorkShop()
							.getFactory().getFactoryName(),
					kaoqin.getEmp().getTeam().getFactoryUnit().getWorkShop()
							.getWorkShopName(),
					kaoqin.getProductdate(),
					kaoqin.getEmp().getTeam().getFactoryUnit()
							.getFactoryUnitName(),
					kaoqin.getTeam().getTeamName(),
					ThinkWayUtil.getDictValueByDictKey(dictService,
							"kaoqinClasses", kaoqin.getClasstime()),

					kaoqin.getPostname(), kaoqin.getStationName(),
					mjzh, kaoqin.getWorkName(),
					kaoqin.getTardyHours(), 
					ThinkWayUtil.getDictValueByDictKey(dictService,"adminworkstate", kaoqin.getWorkState())};
			body.add(bodyval);
		}

		try {
			String fileName = "考勤记录表" + ".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("考勤记录表", header, body, getResponse()
					.getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生产日期或班次是否为空
	 */
	public boolean isnull() {
		this.admin = this.adminService.get(loginid);// 当前登录人
		String productionDate = admin.getProductDate();// 生产日期
		String shift = admin.getShift();// 班次
		if (productionDate == null || "".equals(productionDate)
				|| shift == null || "".equals(shift)) {
			return false;
		}
		return true;
	}

	/**
	 * 进入list页面
	 */
	public String list() {
		this.admin = this.adminService.get(loginid);
		if (!isnull()) {
			addActionError("请选择生产日期及班次!");
			return ERROR;
		}
		if (this.admin.getDepartment() == null) {
			addActionError("部门为空!");
			return ERROR;
		} else {
			if (this.admin.getTeam() == null) {
				addActionError("班组为空!");
				return ERROR;
			}
		}
		// 班次
		admin.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService,
				"kaoqinClasses", admin.getShift()));
		this.list_dict = this.dictService.getState("adminworkstate");// list中员工的状态
		this.sameTeamId = this.admin.getTeam().getId();// 班组ID
		// 读取员工到记录表中
		/*
		 * List<Admin>l_emp=this.adminService.getByTeamId(tid);//根据班组ID获得班组下的所有员工
		 * if(l_emp!=null) { this.list_emp=getNewAdminList(l_emp); }
		 */
		Team t = this.teamService.get(sameTeamId);
		
		// this.isstartteam=t.getIsWork();//班组是否开启
		
		this.iscancreditcard = kqService.getIsCanCreditCard(admin);
		
		this.iswork = t.getIsWork();// 班组是否在上班
		
		
		if(t.getFactoryUnit()!=null && t.getFactoryUnit().getFactoryUnitCode()!=null && !"".equals(t.getFactoryUnit().getFactoryUnitCode()))
		{
			String unitcode=t.getFactoryUnit().getFactoryUnitCode();
			this.unitModelList = this.unitdistributeModelService.getModelList(unitcode);// 查询所有工作范围
		}
		else
		{
			this.unitModelList = this.unitdistributeModelService.getAllList();
		}
		return LIST;
	}

	/**
	 * 查看历史
	 */
	public String show() {
		return "show";
	}

	/**
	 * 转入查询员工页面
	 */
	public String beforegetemp() {
		this.sameTeamId = this.info;
		return "alert";
	}

	/**
	 * 进入页面后查询本班组的员工
	 */
	public String empajlist() {
		if (pager == null) {
			pager = new Pager();
		}
		pager.setOrderType(OrderType.desc);// 倒序
		pager.setOrderBy("modifyDate");// 以创建日期排序
		pager = this.adminService.getEmpAjlist(pager, sameTeamId);// 查询本班组的员工及在本班代班的员工
		@SuppressWarnings("unchecked")
		List<Admin> list1 = pager.getList();
		List<Admin> list2 = getNewAdminList(list1);
		pager.setList(list2);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}

	/**
	 * 查询员工
	 */
	public String getemp() {
		this.admin = this.adminService.get(loginid);
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager == null) {
			pager = new Pager();
		}
		pager.setOrderType(OrderType.desc);// 倒序
		pager.setOrderBy("createDate");// 以创建日期排序
		if (pager.is_search() == true && Param != null) {
			JSONObject obj = JSONObject.fromObject(Param);
			// 班组
			if (obj.get("team") != null) {
				String team = obj.getString("team").toString();
				map.put("team", team);
			}
			// 班次
			if (obj.get("shift") != null) {
				String shift = obj.getString("shift").toString();
				map.put("shift", shift);
			}
			// 员工姓名
			if (obj.get("name") != null) {
				String name = obj.getString("name").toString();
				map.put("name", name);
			}
			// 技能
			if (obj.get("skill") != null) {
				String skill = obj.getString("skill").toString();
				map.put("skill", skill);
			}
			// 工号
			if (obj.get("workNumber") != null) {
				String workNumber = obj.getString("workNumber").toString();
				map.put("workNumber", workNumber);
			}
			// 单元
			if (obj.get("xFactoryUnit") != null) {
				String xFactoryUnit = obj.getString("xFactoryUnit").toString();
				map.put("xFactoryUnit", xFactoryUnit);
			}
		}
		pager = this.adminService.getEmpPager(pager, map, admin);
		@SuppressWarnings("unchecked")
		List<Admin> list1 = pager.getList();
		List<Admin> list2 = getNewAdminList(list1);
		pager.setList(list2);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}

	/**
	 * jqGrid查询
	 */
	public String ajlist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager == null) {
			pager = new Pager();
		}
		pager.setOrderType(OrderType.desc);// 倒序
		pager.setOrderBy("createDate");// 以创建日期排序
		if (pager.is_search() == true && Param != null) {
			JSONObject obj = JSONObject.fromObject(Param);
			// 班组
			/*
			 * if (obj.get("teams") != null) { String teams =
			 * obj.getString("teams").toString(); map.put("teams", teams); }
			 */
			// 班次
			if (obj.get("classtime") != null) {
				String classtime = obj.getString("classtime").toString();
				map.put("classtime", classtime);
			}
			// 开始日期
			if (obj.get("start") != null) {
				String start = obj.get("start").toString();
				map.put("start", start);
			}
			// 结束日期
			if (obj.get("end") != null) {
				String end = obj.get("end").toString();
				map.put("end", end);
			}
		}
		pager = this.kqService.getKaoqinPager(pager, map);
		@SuppressWarnings("unchecked")
		List<Kaoqin> list1 = pager.getList();
		List<Kaoqin> list2 = new ArrayList<Kaoqin>();
		for (int i = 0; i < list1.size(); i++) {
			Kaoqin k = list1.get(i);
			// k.setXclasstime(ThinkWayUtil.getDictValueByDictKey(dictService,
			// "kaoqinClasses", k.getClasstime()));
			// k.setXworkState(ThinkWayUtil.getDictValueByDictKey(dictService,
			// "adminworkstate", k.getWorkState()));
			list2.add(k);
		}
		pager.setList(list2);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}

	/**
	 * 修改Admin表员工工作状态
	 */
	public String updateEmpWorkState() {

		List<UnitdistributeModel> modelList = new ArrayList<UnitdistributeModel>();
		if (unitdistributeModels != null && !("").equals(unitdistributeModels)
				&& !("null").equals(unitdistributeModels)) {
			String[] id = unitdistributeModels.split(",");
			for (int i = 0; i < id.length; i++) {
				UnitdistributeModel unitMod = unitdistributeModelService
						.get(id[i].trim());
				modelList.add(unitMod);
			}
			admin.setUnitdistributeModelSet(new HashSet<UnitdistributeModel>(
					modelList));
		} else {
			admin.setUnitdistributeModelSet(null);
		}
		this.kqService.updateEmpWorkState(admin);
		unitdistributeModels = null;// 清空选择
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	/**
	 * 添加新代班员工
	 */
	public String addnewemp() {
		try {
			if (!isnull()) {
				return this.ajaxJsonErrorMessage("2");// 生产日期或班次不能为空!
			}
			if (ids != null) {
				ids = ids[0].split(",");
				this.kqService.saveNewEmp(ids, sameTeamId, admin);
			}
			return this.ajaxJsonSuccessMessage("1");// 添加成功
		} catch (Exception e) {
			e.printStackTrace();
			return this.ajaxJsonErrorMessage("3");// 系统出现异常
		}
	}
	
	
	/**
	 * 移除代班员工
	 * 
	 */
	public String removeDaiban()
	{
		Admin rda=adminService.get(id);
		rda.setIsdaiban("N");
		rda.setModifyDate(new Date());
		rda.setProductDate(null);
		rda.setShift(null);
		adminService.update(rda);
		return this.ajaxJsonSuccessMessage("1");// 移除成功
	}
	
	
	

	/**
	 * 开启考勤
	 */
	public String creditreply() {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		
		Admin longinAdmin=adminService.get(loginid);
		String shift=longinAdmin.getShift();
		String teamName=longinAdmin.getTeam().getTeamName();
		productdate=longinAdmin.getProductDate();
		if (!isnull()) {
			return this.ajaxJsonErrorMessage("生产日期或班次不能为空!");
		}
		// 保存开启考勤(刷卡)记录
		String str=this.kqService.updateBrushCardEmp(loginid, my_id);
		if ("s".equals(str)) {
			// 操作成功
			hashmap.put("info", str);
			hashmap.put(STATUS, "success");
			hashmap.put(MESSAGE, "您的操作已成功!");
			return this.ajaxJson(hashmap);
		} else if ("e".equals(str)) {
			// 操作错误
			hashmap.put("info", str);
			hashmap.put(STATUS, "success");
			hashmap.put(MESSAGE, "该班组已经下班,请刷新页面查看!");
			return this.ajaxJson(hashmap);
		} else if("ybc".equals(str)){
			// 操作成功,考勤已保存过,提示本次不在保存
			String xshift = ThinkWayUtil.getDictValueByDictKey(dictService,
					"kaoqinClasses", shift);
			hashmap.put("info", "t");
			hashmap.put(STATUS, "success");
			hashmap.put(MESSAGE, "班组下班成功!班组:" + teamName + ",生产日期:" + productdate
					+ ",班次:" + xshift + ",已经记录到考勤,本次不再重复记录!");
			return this.ajaxJson(hashmap);
		}
		else
		{			
			hashmap.put("info", "e");
			hashmap.put(STATUS, "error");
			hashmap.put(MESSAGE, "对不起,数据出现异常!请刷新重试！");
			return this.ajaxJson(hashmap);
		}
		//return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	/**
	 * 点击后刷卡 creditapproval
	 */
	public String creditapproval() {
		Admin a = this.adminService.getByCardnum(cardnumber);
		// 修改刷卡 人的状态,返回：1修改成功 2已经刷卡成功无需重复刷卡 3不是本班员工或本班代班员工
		int n = this.kqService.updateWorkStateByCreidt(cardnumber, sameTeamId,
				loginid);
		if (n == 1) {
			return this.ajaxJsonSuccessMessage(a.getName() + "刷卡成功!");
		} else if (n == 2) {
			return this.ajaxJsonErrorMessage(a.getName() + "已经刷卡成功,无需重复刷卡!");
		} else {
			return this.ajaxJsonErrorMessage(a.getName() + "非本班或本班代班员工刷卡无效!");
		}
	}

	/**
	 * 下班前获取生产日期和班次
	 */
	public String getDateAndShift() {
		try {
			this.admin = this.adminService.get(loginid);// 当前登录人
			String productionDate = admin.getProductDate();// 生产日期
			String shift = admin.getShift();// 班次
			if (productionDate == null || "".equals(productionDate)
					|| shift == null || "".equals(shift)) {
				return this.ajaxJsonErrorMessage("1");
			}
			String xshift = ThinkWayUtil.getDictValueByDictKey(dictService,
					"kaoqinClasses", shift);
			return this.ajaxJsonSuccessMessage("生产日期：" + productionDate
					+ ",班次：" + xshift + ",将记录于本次员工考勤,确定下班吗?");
		} catch (Exception e) {
			e.printStackTrace();
			return this.ajaxJsonErrorMessage("2");
		}
	}

	/**
	 * 下班
	 */
	public String creditundo() {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		if (!isnull()) {
			return this.ajaxJsonErrorMessage("生产日期或班次不能为空!");
		}
		String productdate = admin.getProductDate();// 生产日期
		String shift = admin.getShift();// 班次
		String str = this.kqService.mergeGoOffWork(this.sameTeamId, admin);
		if ("s".equals(str)) {
			// 操作成功
			hashmap.put("info", str);
			hashmap.put(STATUS, "success");
			hashmap.put(MESSAGE, "您的操作已成功!");
			return this.ajaxJson(hashmap);
		} else if ("e".equals(str)) {
			// 操作错误
			hashmap.put("info", str);
			hashmap.put(STATUS, "error");
			hashmap.put(MESSAGE, "该班组已经下班,请刷新页面查看!");
			return this.ajaxJson(hashmap);
		} else {
			// 操作成功,考勤已保存过,提示本次不在保存
			String xshift = ThinkWayUtil.getDictValueByDictKey(dictService,
					"kaoqinClasses", shift);
			hashmap.put("info", "t");
			hashmap.put(STATUS, "success");
			hashmap.put(MESSAGE, "班组下班成功!班组:" + str + ",生产日期:" + productdate
					+ ",班次:" + xshift + ",已经记录到考勤,本次不再重复记录!");
			return this.ajaxJson(hashmap);
		}
	}

	/**
	 * Admin表假字段
	 */
	public List<Admin> getNewAdminList(List<Admin> list1) {
		List<Admin> list2 = new ArrayList<Admin>();
		for (int i = 0; i < list1.size(); i++) {
			Admin a = list1.get(i);
			// 班次
			if (a.getShift() != null && !"".equals(a.getShift())) {
				a.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService,
						"kaoqinClasses", a.getShift()));
			}
			
			//班组
			if(a.getTeam()!=null)
			{
				a.setXteam( a.getTeam().getTeamName());
				//单元
				if(a.getTeam().getFactoryUnit()!=null)
				{
					a.setXfactoryUnit(a.getTeam().getFactoryUnit().getFactoryUnitName());
				}
			}
			// 工作状态
			if (a.getWorkstate() != null && !"".equals(a.getWorkstate())) {
				a.setXworkstate(ThinkWayUtil.getDictValueByDictKey(dictService,
						"adminworkstate", a.getWorkstate()));
			}
			// 岗位
			if (a.getPost() != null) {
				a.setXpost(a.getPost().getPostName());
			}
			// 工位
			if (a.getStationids() != null) {
				String[] sids = a.getStationids().split(",");
				String str = "";
				for (int j = 0; j < sids.length; j++) {
					Station s = this.stationService.get(sids[j]);
					if (s != null && "N".equals(s.getIsDel())
							&& s.getName() != null) {
						str += s.getName() + ",";
					}

				}
				if (str.endsWith(",")) {
					str = str.substring(0, str.length() - 1);
				}
				a.setXgongwei(str);// 工位
			}
			// 工作范围
			List<UnitdistributeProduct> list_up = new ArrayList<UnitdistributeProduct>(
					a.getUnitdistributeProductSet());
			if (list_up.size() > 0) {
				String str = "";
				for (int j = 0; j < list_up.size(); j++) {
					UnitdistributeProduct up = list_up.get(j);
					if (up != null && "N".equals(up.getIsDel())
							&& up.getMaterialName() != null) {
						str += up.getMaterialName() + ",";
					}
				}
				if (str.endsWith(",")) {
					str = str.substring(0, str.length() - 1);
				}
				a.setXstation(str);

			}
			// 模具组号
			List<UnitdistributeModel> list_um = new ArrayList<UnitdistributeModel>(
					a.getUnitdistributeModelSet());
			if (list_um.size() > 0) {
				String str = "";
				String str2 = "";
				for (int j = 0; j < list_um.size(); j++) {
					UnitdistributeModel um = list_um.get(j);
					if (um != null && "N".equals(um.getIsDel())
							&& um.getStation() != null) {
						str += um.getStation() + ",";
						str2 += um.getId() + ",";
					}
				}
				if (str.endsWith(",")) {
					str = str.substring(0, str.length() - 1);
					str2 = str2.substring(0, str2.length() - 1);
				}
				a.setXworkscope(str);
				a.setXstationval(str2);
			}
			list2.add(a);
		}
		return list2;
	
		}
	/**
	 * 导出Excel表
	 */
	public String outexcel() {
		try {
			List<Admin> list1 = this.adminService.getByTeamId(sameTeamId);// 查询本班组的员工及在本班代班的员工
			List<Admin> list2 = getNewAdminList(list1);
			List<String> header = new ArrayList<String>();
			header.add("员工卡号");
			header.add("姓名");
			header.add("工号");
			header.add("手机号");
			header.add("班组");
			header.add("岗位");
			header.add("工位");
			header.add("模具组号");
			header.add("工作范围");
			header.add("员工状态");
			header.add("异常小时数");
			header.add("员工卡号");header.add("姓名");header.add("工号");header.add("手机号");
			header.add("班组");header.add("单元");header.add("岗位");header.add("工位");
			header.add("模具组号");header.add("工作范围");header.add("员工状态");header.add("异常小时数");
			List<Object[]> body = new ArrayList<Object[]>();
			for (int i = 0; i < list2.size(); i++) {
				Admin a = list2.get(i);
				Object[] str = new Object[header.size()];
				str[0] = a.getCardNumber();
				str[1] = a.getName();
				str[2] = a.getWorkNumber();
				str[3] = a.getPhoneNo();
				str[4] = a.getXteam();
				str[5] = a.getXpost();
				str[6] = a.getXgongwei();
				str[7] = a.getXstation();
				str[8] = a.getXworkscope();
				str[9] = a.getXworkstate();
				str[10] = a.getTardyHours();
				str[0]=a.getCardNumber();
				str[1]=a.getName();
				str[2]=a.getWorkNumber();
				str[3]=a.getPhoneNo();
				str[4]=a.getXteam();
				str[5]=a.getXfactoryUnit();
				str[6]=a.getXpost();
				str[7]=a.getXgongwei();
				str[8]=a.getXstation();
				str[9]=a.getXworkscope();
				str[10]=a.getXworkstate();
				str[11]=a.getTardyHours();
				body.add(str);
			}
			/*** Excel 下载 ****/

			String fileName = "当前考勤" + ".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("当前考勤信息", header, body, getResponse()
					.getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
			// return this.ajaxJsonSuccessMessage("e");
		}
		// return this.ajaxJsonSuccessMessage("s");
		return null;
	}

	/** ========================end method====================================== */

	/** ===========================get/set start============================= */

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Dict> getList_dict() {
		return list_dict;
	}

	public void setList_dict(List<Dict> list_dict) {
		this.list_dict = list_dict;
	}

	public Kaoqin getKaoqin() {
		return kaoqin;
	}

	public void setKaoqin(Kaoqin kaoqin) {
		this.kaoqin = kaoqin;
	}

	public List<Kaoqin> getList_kq() {
		return list_kq;
	}

	public void setList_kq(List<Kaoqin> list_kq) {
		this.list_kq = list_kq;
	}

	public List<Admin> getList_emp() {
		return list_emp;
	}

	public void setList_emp(List<Admin> list_emp) {
		this.list_emp = list_emp;
	}

	public String getSameTeamId() {
		return sameTeamId;
	}

	public void setSameTeamId(String sameTeamId) {
		this.sameTeamId = sameTeamId;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getIscancreditcard() {
		return iscancreditcard;
	}

	public void setIscancreditcard(String iscancreditcard) {
		this.iscancreditcard = iscancreditcard;
	}

	public String getIsstartteam() {
		return isstartteam;
	}

	public void setIsstartteam(String isstartteam) {
		this.isstartteam = isstartteam;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public int getMy_id() {
		return my_id;
	}

	public void setMy_id(int my_id) {
		this.my_id = my_id;
	}

	public String getIswork() {
		return iswork;
	}

	public void setIswork(String iswork) {
		this.iswork = iswork;
	}

	public String getProductdate() {
		return productdate;
	}

	public void setProductdate(String productdate) {
		this.productdate = productdate;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public String getClasstime() {
		return classtime;
	}

	public void setClasstime(String classtime) {
		this.classtime = classtime;
	}

	public String getFactoryUnitName() {
		return factoryUnitName;
	}

	public void setFactoryUnitName(String factoryUnitName) {
		this.factoryUnitName = factoryUnitName;
	}

	public List<UnitdistributeModel> getUnitModelList() {
		return unitModelList;
	}

	public void setUnitModelList(List<UnitdistributeModel> unitModelList) {
		this.unitModelList = unitModelList;
	}

	public String getUnitdistributeModels() {
		return unitdistributeModels;
	}

	public void setUnitdistributeModels(String unitdistributeModels) {
		this.unitdistributeModels = unitdistributeModels;
	}

	public String getWorkNumber() {
		return workNumber;
	}

	public void setWorkNumber(String workNumber) {
		this.workNumber = workNumber;
	}

	public String getxFactoryUnit() {
		return xFactoryUnit;
	}

	public void setxFactoryUnit(String xFactoryUnit) {
		this.xFactoryUnit = xFactoryUnit;
	}

	
	/** ===========================end get/set=============================== */
}
