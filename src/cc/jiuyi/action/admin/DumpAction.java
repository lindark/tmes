package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Material;
import cc.jiuyi.sap.rfc.DumpRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.DumpDetailService;
import cc.jiuyi.service.DumpService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ExportExcel;
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
	private String isRecord;
	private List<Material>list_material;
	private FactoryUnit factoryunit;//单元
	private List<FactoryUnit> factoryunitList;//单元
	private String materialcode;//物料编码
	private List<HashMap<String,String>>list_map;
	private List<HashMap<String,String>>list_ddmap;
	private List<DumpDetail>list_dd;
	private String xshow;
	private String fuid;//单元主键ID
	private String dumpid;//主键
	private String xedit;
	private String voucherId;
	private String state;
	private String materialCode;
	private String end;
	private String start;

	@Resource
	private TempKaoqinService tempKaoqinService;
	@Resource
	private DumpRfc dumpRfc;
	@Resource
	private DumpService dumpService;
	@Resource
	private DumpDetailService dumpDetailService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;
	@Resource
	private FactoryUnitService fuservice;

	
	// 物料调拨记录 @author Reece 2016/3/22
	public String history() {
		return "history";
	}
	
	//物料调拨列表 @author Reece 2016/3/22
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
				String voucherId = obj.getString("voucherId").toString();
				map.put("voucherId", voucherId);
			}
			if (obj.get("materialCode") != null) {
				String materialCode = obj.getString("materialCode").toString();
				map.put("materialCode", materialCode);
			}
			if (obj.get("start") != null) {
				String start = obj.getString("start").toString();
				map.put("start", start);
			}
			if (obj.get("end") != null) {
				String end = obj.getString("end").toString();
				map.put("end", end);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
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
			dump.setCreateName(dump.getCreateUser().getName());
			if(dump.getShift()!=null&&!"".equals(dump.getShift())){
				dump.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", dump.getShift()));
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
	
	// Excel导出 @author Reece 2016/3/22
			public String excelexport() {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("voucherId", voucherId);
				map.put("materialCode", materialCode);
				map.put("state", state);
				map.put("start", start);
				map.put("end", end);

				List<String> header = new ArrayList<String>();
				List<Object[]> body = new ArrayList<Object[]>();
				header.add("生产日期");
				header.add("班次");
				header.add("组件编码");
				header.add("组件描述");
				header.add("组件总数量");

				header.add("物料凭证号");
				header.add("创建日期");
				header.add("创建人");
				header.add("确认人");
				header.add("状态");

				List<Dump> dumpList = dumpService.historyExcelExport(map);
				for (int i = 0; i < dumpList.size(); i++) {
					Dump dump = dumpList.get(i);

					Object[] bodyval = {
							dump.getProductionDate(),
							dump.getShift()==null ?"":
								ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", dump.getShift()),
							dump.getMaterialCode(),
							dump.getMaterialdes(),
							dump.getAllcount(),
							
							dump.getVoucherId(),
							dump.getCreateDate(),
							dump.getCreateUser() == null ? "" : dump
									.getCreateUser().getName(),
							dump.getConfirmUser() == null ? "" : dump
									.getConfirmUser().getName(),
							ThinkWayUtil.getDictValueByDictKey(dictService,
									"dumpState", dump.getState())};
					body.add(bodyval);
				}

				try {
					String fileName = "物料调拨记录表" + ".xls";
					setResponseExcel(fileName);
					ExportExcel.exportExcel("物料调拨记录表", header, body, getResponse()
							.getOutputStream());
					getResponse().getOutputStream().flush();
					getResponse().getOutputStream().close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
	
	
	public String list() {
		admin = adminService.get(loginid);
		admin = adminService.get(admin.getId());
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行转储操作!");
			return ERROR;
		}
		//admin = adminService.load(admin.getId());
		warehouse = admin.getTeam().getFactoryUnit()
				.getWarehouse();
		warehouseName = admin.getTeam().getFactoryUnit()
				.getWarehouseName();
		return "list";
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
			warehouse = admin.getTeam().getFactoryUnit()
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
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		try {
			admin = adminService.getLoginAdmin();
			admin = adminService.load(admin.getId());
			warehouse = admin.getTeam().getFactoryUnit()
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
							dumps.setDeliveryTime(dump.getDeliveryTime());
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

	
	public String recordList(){
		return "record_list";
	}
	public String recordAjlist(){
		if(pager==null){
			pager = new Pager();
		}
		List<DumpDetail> dumpsList = new ArrayList<DumpDetail>();
		try {
			if (pager.is_search() == true) 
			{
				HashMap<String,String> mapcheck = new HashMap<String,String>();
				if(Param != null){//普通搜索功能
					if(!Param.equals("")){
					//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
						JSONObject param = JSONObject.fromObject(Param);
						String materialCode = ThinkWayUtil.null2String(param.get("materialCode"));
						String materialName = ThinkWayUtil.null2String(param.get("materialName"));
						String voucherId = ThinkWayUtil.null2String(param.get("voucherId"));//
						String start = ThinkWayUtil.null2String(param.get("start"));//
						String end = ThinkWayUtil.null2String(param.get("end"));//
						mapcheck.put("start", start);
						mapcheck.put("end", end);
						mapcheck.put("materialCode", materialCode);
						mapcheck.put("materialName", materialName);
						mapcheck.put("voucherId", voucherId);
						pager = dumpDetailService.findDumpDetailByPager(pager,mapcheck);
					}
				}
				for(int i = 0; i < pager.getList().size(); i++){
					DumpDetail dd = (DumpDetail)pager.getList().get(i);
					if (dd.getState() != null && !"".equals(dd.getState())) {
						dd.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
								dictService, "dumpState", dd.getState()));
					}
					if (dd.getDump().getConfirmUser() != null) {
						dd.setAdminName(dd.getDump().getConfirmUser().getName());
					}
					dumpsList.add(dd);
				}
			}else{
				List<Dump> dpList = dumpService.getAll();
				for (int i = 0; i < dpList.size(); i++) {
					Dump dump = (Dump) dpList.get(i);
					Set<DumpDetail> dds = dump.getDumpDetail();
					if(dds!=null){
						for(DumpDetail dd : dds){
							if (dd.getState() != null && !"".equals(dd.getState())) {
								dd.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
										dictService, "dumpState", dd.getState()));
							}
							if (dump.getConfirmUser() != null) {
								dd.setAdminName(dump.getConfirmUser().getName());
							}
							dumpsList.add(dd);
						}
					}
				}
			}
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Dump.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(dumpsList, jsonConfig);
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("list", jsonArray);
			return ajaxJson(jsonobject.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题，请联系系统管理员");
		}
	
	}
	
	/**============================================*/
	
	/**
	 * 进入list页面
	 * @return
	 */
	public String all()
	{
		Admin emp=this.adminService.get(loginid);
		//查询明细表当前生产日期和班次下的同物料编码的已确认的领料数量
		HttpServletRequest request = getRequest();
		String ip = ThinkWayUtil.getIp2(request);
		//根据ip获取单元
		factoryunit=this.fuservice.getById(ip);
		list_map=new ArrayList<HashMap<String,String>>();
		list_map=this.dumpService.getMengeByConditions(emp,factoryunit);
		return "all";
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String alllist()
	{
		Admin emp=this.adminService.get(loginid);
		if(pager==null)
		{
			pager=new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if(pager.is_search()==true&&filters!=null)
		{
			JSONObject filt=JSONObject.fromObject(filters);
			Pager pg1=new Pager();
			Map<Object,Object> map=new HashMap<Object, Object>();
			map.put("rules", jqGridSearchDetailTo.class);
			pg1=(Pager) JSONObject.toBean(filt,Pager.class,map);
			pager.setRules(pg1.getRules());
			pager.setGroupOp(pg1.getGroupOp());
		}
		pager=this.dumpService.getAlllist(pager,emp);
		List<Dump>list=pager.getList();
		List<Dump>list2=new ArrayList<Dump>();
		for(int i=0;i<list.size();i++)
		{
			Dump d=list.get(i);
			//创建人
			if(d.getCreateUser()!=null)
			{
				d.setCreateName(d.getCreateUser().getName());
			}
			//确认人
			if(d.getConfirmUser()!=null)
			{
				d.setAdminName(d.getConfirmUser().getName());
			}
			//状态
			if (d.getState() != null && !"".equals(d.getState()))
			{
				d.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService, "dumpState", d.getState()));
			}
			//班次
			if(d.getShift()!=null&&!"".equals(d.getShift()))
			{
				d.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", d.getShift()));
			}
			list2.add(d);
		}
		pager.setList(list2);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Dump.class));//排除有关联关系的属性字段 
		JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
		
		return this.ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 添加前
	 */
	public String beforetoadd()
	{
		HttpServletRequest request = getRequest();
		String ip = ThinkWayUtil.getIp2(request);
		//根据ip获取单元
		//factoryunit=this.fuservice.getById("192.168.40.40");// 
		factoryunitList=this.fuservice.getByIds(ip);
		
		//测试时使用
		//admin=adminService.getLoginAdmin();
		//admin=adminService.get(admin.getId());
		//factoryunit=admin.getTeam().getFactoryUnit();
						
		//System.out.println(ip);
		
		//根据单元获取物料
		if(factoryunit!=null)
		{
			list_material=new ArrayList<Material>(factoryunit.getMaterialSet());
		}
		return "entry";
	}
	
	/**
	 * 添加物料前
	 */
	public String beforeaddbatch()
	{
		try
		{
			addoredit();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			addActionError("系统出现异常!");
			return ERROR;
		}
		catch (CustomerException e)
		{
			e.printStackTrace();
			addActionError(e.getMsgDes());
			return ERROR;
		}
		list_map=new ArrayList<HashMap<String,String>>();
		list_map=list_ddmap;
		return "alert";
	}
	
	/**
	 * 添加保存
	 */
	public String creditsave()
	{
		dumpid=this.dumpService.saveInfo(list_dd,fuid,cardnumber,materialcode);
		Map<String,String> map = new HashMap<String,String>();
		map.put("dumpid", dumpid);
		map.put("cardnumber", cardnumber);
		map.put("status", "success");
		
		return this.ajaxJson(map);
	}
	
	/**
	 * 修改物料前
	 */
	public String beforeeditbatch()
	{
		this.dump=this.dumpService.get(id);
		this.materialcode=dump.getMaterialCode();//物料编码
		fuid=dump.getFactoryUnitId();//单元ID
		factoryunit = fuservice.get(fuid);
		/*//获取批次
		try
		{
			addoredit();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			addActionError("IO出现异常!");
			return ERROR;
		}
		catch (CustomerException e)
		{
			e.printStackTrace();
			addActionError(e.getMsgDes());
			return ERROR;
		}*/
		list_dd=new ArrayList<DumpDetail>(dump.getDumpDetail());
		/*list_map=new ArrayList<HashMap<String,String>>();
		for(int i=0;i<list_ddmap.size();i++)
		{
			HashMap<String,String>m=list_ddmap.get(i);
			for(int j=0;j<list_dd.size();j++)
			{
				DumpDetail dd=list_dd.get(j);
				if(m.get("charg").equals(dd.getCharg()))
				{
					m.put("menge", dd.getMenge());//已填写的数量
				}
			}
			list_map.add(m);
		}*/
		xedit="xedit1";
		return "alert";
	}
	
	/**
	 * 修改保存
	 */
	public String creditupdate()
	{
		//this.dumpService.updateInfo(list_dd,fuid,dumpid);
		Dump d=this.dumpService.get(dumpid);
		List<DumpDetail> ddlist1=new ArrayList<DumpDetail>(d.getDumpDetail());//子表
		if(xedit!=null && xedit.equals("xedit1")){
			if(list_dd!=null && list_dd.size()>0){}
			for(int i=0;i<ddlist1.size();i++){
				DumpDetail dd=ddlist1.get(i);
				for(int j=0;j<list_dd.size();j++){
					DumpDetail dd1=list_dd.get(j);
					if(dd1==null)continue;
					if(dd.getId().equals(dd1.getId())){
						dd.setMenge(dd1.getMenge());
						dd.setModifyDate(new Date());
						this.dumpDetailService.update(dd);
						break;
					}
				}
			}
		}
		return this.ajaxJsonSuccessMessage(dumpid);
	}
	/**
	 * 显示
	 */
	public String toshow()
	{
		this.dump=this.dumpService.get(id);
		list_dd=new ArrayList<DumpDetail>(dump.getDumpDetail());
		this.xshow="xshow";
		return "alert";
	}
	
	/**
	 * 刷卡确认
	 */
	public String creditreply()
	{
		/*ids=id.split(",");
		for(int i=0;i<ids.length;i++)
		{
			Dump d=this.dumpService.get(ids[i]);
			String s=d.getState();
			if("1".equals(s))
			{
				return this.ajaxJsonErrorMessage("已确认的不能再确认!");
			}
		}*/
		HashMap<String,String> map = new HashMap<String,String>();
		Admin emp=this.adminService.getByCardnum(cardnumber);//确认人
		String productionDate=emp.getProductDate();//生产日期
		String shift=emp.getShift();//班次
		if(emp.getTeam()==null)return this.ajaxJsonErrorMessage("刷卡人员不存在班组");
		if(emp.getTeam().getFactoryUnit()==null)return this.ajaxJsonErrorMessage("刷卡人员不存在单元");
		String lgort = emp.getTeam().getFactoryUnit().getPsaddress();
		String werks = emp.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		map.put("lgort", ThinkWayUtil.null2String(lgort));
		map.put("werks", ThinkWayUtil.null2String(werks));
		if(productionDate==null||"".equals(productionDate)||shift==null||"".equals(shift))
		{
			return this.ajaxJsonErrorMessage("生产日期或班次不能为空!");
		}
		Dump d=this.dumpService.get(dumpid);
		String s=d.getState();
		if("1".equals(s))
		{
			return this.ajaxJsonErrorMessage("已确认的不能再确认!");
		}
		String str="";
		try
		{
			List<DumpDetail> ddlist1=new ArrayList<DumpDetail>(d.getDumpDetail());//子表
			if(xedit!=null && xedit.equals("xedit2")){
				if(list_dd!=null && list_dd.size()>0){}
				for(int i=0;i<ddlist1.size();i++){
					DumpDetail dd=ddlist1.get(i);
					for(int j=0;j<list_dd.size();j++){
						DumpDetail dd1=list_dd.get(j);
						if(dd1==null)continue;
						if(dd.getId().equals(dd1.getId())){
							dd.setMenge(dd1.getMenge());
							this.dumpDetailService.update(dd);
							break;
						}
					}
				}
			}
			List<DumpDetail>ddlist=new ArrayList<DumpDetail>(d.getDumpDetail());//子表
			for(int i=0;i<ddlist.size();i++)
			{
				DumpDetail dd=ddlist.get(i);
				String isdone=dd.getIsDone();
				List<HashMap<String,String>>mlist=getMapList(dd);
				if(!"Y".equals(isdone))
				{
					String lenum = this.dumpRfc.saveMaterial(map,mlist);
					dd.setLenum(lenum);
					dd.setIsDone("Y");//是否已经从中转仓计算过
					dd.setModifyDate(new Date());
					this.dumpDetailService.update(dd);
				}
			}
			str = this.dumpService.updateToSAP(d,ddlist,emp);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return this.ajaxJsonErrorMessage("IO操作错误!");
		}
		catch (CustomerException e)
		{
			e.printStackTrace();
			return this.ajaxJsonErrorMessage(e.getMsgDes());
		}catch(Exception e){
			e.printStackTrace();
			return this.ajaxJsonErrorMessage("系统出现错误!");
		}
		//与SAP交互及修改本地状态
		if("S".equals(str))
		{
			Map<String,String> map1 = new HashMap<String,String>();
			map1.put("dumpid", dumpid);
			map1.put("cardnumber", cardnumber);
			map1.put("status", "success");
			map1.put("funid", fuid);
			return this.ajaxJson(map1);
		}
		return this.ajaxJsonErrorMessage(str);
	}
	
	/**
	 * 刷卡撤销
	 */
	public String creditundo()
	{
		ids=id.split(",");
		List<Dump>list=new ArrayList<Dump>();
		for(int i=0;i<ids.length;i++)
		{
			Dump d=this.dumpService.get(ids[i]);
			String s=d.getState();
			if("1".equals(s))
			{
				return this.ajaxJsonErrorMessage("已确认的不能撤销!");
			}
			if("3".equals(s))
			{
				return this.ajaxJsonErrorMessage("已撤销的无需再撤销!");
			}
			list.add(d);
		}
		this.dumpService.updateData(list);//撤销
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 第一页面新增/编辑共用方法
	 * @throws CustomerException 
	 * @throws IOException 
	 */
	public String addoredit() throws IOException, CustomerException
	{
		String werks="";//工厂
		factoryunit=this.fuservice.get(fuid);//根据单元id查询单元
		if(factoryunit.getWorkShop()!=null&&factoryunit.getWorkShop().getFactory()!=null)
		{
			werks=factoryunit.getWorkShop().getFactory().getFactoryCode();//工厂
		}
		String lgort=factoryunit.getPsaddress();//配送地点编码
		String matnr=this.materialcode;//物料编码
		String lgpla=factoryunit.getPsPositionAddress();//配送库存地点仓位
		
			list_ddmap=this.dumpRfc.findMaterial(werks, lgort, matnr, lgpla,"");
		
		return "S";
	}
	
	public List<HashMap<String,String>>getMapList(DumpDetail dd)
	{
		List<HashMap<String,String>>mlist=new ArrayList<HashMap<String,String>>();
		HashMap<String,String>map=new HashMap<String,String>();
		map.put("MANTD",dd.getMantd());//客户端编号
		map.put("ROWNO", dd.getRowno());//存放ID
		map.put("MATNR", dd.getMatnr());//物料编码
		map.put("CHARG", dd.getCharg());//批号
		map.put("VERME", dd.getVerme());//可用库存
		map.put("DWNUM", dd.getMenge());//数量
		map.put("MEINS", dd.getMeins());//基本单位
		map.put("LGTYP", dd.getLgtyp());//仓储类型
		map.put("LGPLA", dd.getLgpla());//仓位
		map.put("LQNUM", dd.getLqnum());//Quantity in Parallel Unit of Entry
		map.put("LENUM", dd.getLenum());//仓储单位编号
		map.put("SEQU", dd.getSequ());//整数
		map.put("NLPLA", dd.getNlpla());//目的地仓位
		map.put("XUH", dd.getId());//序号
		mlist.add(map);
		return mlist;
	}
	
	/**============================================*/
	
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

	public String getIsRecord() {
		return isRecord;
	}

	public void setIsRecord(String isRecord) {
		this.isRecord = isRecord;
	}

	public List<Material> getList_material()
	{
		return list_material;
	}

	public void setList_material(List<Material> list_material)
	{
		this.list_material = list_material;
	}

	public FactoryUnit getFactoryunit()
	{
		return factoryunit;
	}

	public void setFactoryunit(FactoryUnit factoryunit)
	{
		this.factoryunit = factoryunit;
	}

	public String getMaterialcode()
	{
		return materialcode;
	}

	public void setMaterialcode(String materialcode)
	{
		this.materialcode = materialcode;
	}

	public List<HashMap<String, String>> getList_map()
	{
		return list_map;
	}

	public void setList_map(List<HashMap<String, String>> list_map)
	{
		this.list_map = list_map;
	}

	public List<DumpDetail> getList_dd()
	{
		return list_dd;
	}

	public void setList_dd(List<DumpDetail> list_dd)
	{
		this.list_dd = list_dd;
	}

	public String getXshow()
	{
		return xshow;
	}

	public void setXshow(String xshow)
	{
		this.xshow = xshow;
	}

	public String getFuid()
	{
		return fuid;
	}

	public void setFuid(String fuid)
	{
		this.fuid = fuid;
	}

	public String getDumpid()
	{
		return dumpid;
	}

	public void setDumpid(String dumpid)
	{
		this.dumpid = dumpid;
	}

	public String getXedit()
	{
		return xedit;
	}

	public void setXedit(String xedit)
	{
		this.xedit = xedit;
	}

	public List<HashMap<String, String>> getList_ddmap()
	{
		return list_ddmap;
	}

	public void setList_ddmap(List<HashMap<String, String>> list_ddmap)
	{
		this.list_ddmap = list_ddmap;
	}

	public String getVoucherId() {
		return voucherId;
	}

	public void setVoucherId(String voucherId) {
		this.voucherId = voucherId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
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

	public List<FactoryUnit> getFactoryunitList() {
		return factoryunitList;
	}

	public void setFactoryunitList(List<FactoryUnit> factoryunitList) {
		this.factoryunitList = factoryunitList;
	}
	
	
}
