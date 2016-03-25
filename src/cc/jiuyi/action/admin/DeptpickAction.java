package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

import cc.jiuyi.action.cron.WorkingBillJob;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.ReturnProduct;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.DeptpickRfc;
import cc.jiuyi.sap.rfc.EndProductRfc;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.sap.rfc.ReturnProductRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DeptpickService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReturnProductService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 部门领料
 */
@ParentPackage("admin")
public class DeptpickAction extends BaseAdminAction {
	public static Logger log = Logger.getLogger(DeptpickAction.class);
	private static final long serialVersionUID = 7802087889949523996L;
	
	private Admin admin;
	private List<WorkingBill> workingBillList;
	private List<Deptpick> deptpickList;
	private List<Locationonside> locationonsideList;
	private String info;
	private String cardnumber;
	private Deptpick deptpick;
	private String desp;
	private String loginId;
	private String departid;
	private String costcenter;
	private String movetype;
	private String movetype1;
	private String departmentName;//部门描述
	private String ex_mblnr;
	private String materialCode;
	private String state;
	private String start;
	private String end;
	private String type;
	
	@Resource
	private DeptpickService deptpickservice;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private LocationonsideRfc rfc;
	@Resource
	private DeptpickRfc deptpickrfc;
	
	//部门领料记录
	public String history() {
		return "history";
	}

	//部门领料记录列表 @author Reece 2016/3/18
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
				if (obj.get("materialCode") != null) {
					String materialCode = obj.getString("materialCode")
							.toString();
					map.put("materialCode", materialCode);
				}
				if (obj.get("ex_mblnr") != null) {
					String ex_mblnr = obj.getString("ex_mblnr")
							.toString();
					map.put("ex_mblnr", ex_mblnr);
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
			pager = deptpickservice.historyjqGrid(pager, map);
			List<Deptpick> deptpickList = pager.getList();
			List<Deptpick> lst = new ArrayList<Deptpick>();
			for (int i = 0; i < deptpickList.size(); i++) {
				Deptpick deptpick = (Deptpick) deptpickList.get(i);
				deptpick.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService,"deptPickState", deptpick.getState()));
				if(deptpick.getComfirmUser() != null)
					deptpick.setXcomfirmUser(deptpick.getComfirmUser().getName());
				if(deptpick.getCreateUser() != null)
					deptpick.setXcreateUser(deptpick.getCreateUser().getName());
				lst.add(deptpick);
			}
			pager.setList(lst);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Deptpick.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		}
		
		// Excel导出 @author Reece 2016/3/17
		public String excelexport() {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("ex_mblnr", ex_mblnr);
			map.put("materialCode", materialCode);
			map.put("state", state);
			map.put("start", start);
			map.put("end", end);

			List<String> header = new ArrayList<String>();
			List<Object[]> body = new ArrayList<Object[]>();
			header.add("组件编码");
			header.add("组件名称");
			header.add("批次");
			
			header.add("接收成本中心");
			header.add("接收部门");
			header.add("发料移动类型");
			header.add("库存地点");

			header.add("物料凭证号");
			header.add("领用数量");
			header.add("创建日期");
			header.add("创建人");
			header.add("确认人");
			header.add("状态");

			List<Deptpick> deptpickList = deptpickservice.historyExcelExport(map);
			for (int i = 0; i < deptpickList.size(); i++) {
				Deptpick deptpick = deptpickList.get(i);

				Object[] bodyval = {
						deptpick.getMaterialCode(),
						deptpick.getMaterialName(),
						deptpick.getMaterialBatch(),
						
						deptpick.getCostcenter(),
						deptpick.getDepartmentName(),
						deptpick.getMovetype(),
						deptpick.getRepertorySite(),
						
					
						deptpick.getEx_mblnr(),
						deptpick.getStockMount(),
						deptpick.getCreateDate(),
						deptpick.getCreateUser() == null ? "" : deptpick
								.getCreateUser().getName(),
						deptpick.getComfirmUser() == null ? "" : deptpick
								.getComfirmUser().getName(),
						ThinkWayUtil.getDictValueByDictKey(dictService,
								"deptPickState", deptpick.getState()) };
				body.add(bodyval);
			}

			try {
				String fileName = "部门领用记录表" + ".xls";
				setResponseExcel(fileName);
				ExportExcel.exportExcel("部门领用记录表", header, body, getResponse()
						.getOutputStream());
				getResponse().getOutputStream().flush();
				getResponse().getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	
	
	public String list(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行成本入库操作!");
			return ERROR;
		}
		admin = adminService.get(admin.getId());
		return LIST;
	}
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		Admin admin = adminService.getLoginAdmin();//登录人
		admin = adminService.get(admin.getId());
		pager = deptpickservice.findByPager(pager,admin);
		List<Deptpick> deptpickList = pager.getList();
		List<Deptpick> lst = new ArrayList<Deptpick>();
		for (int i = 0; i < deptpickList.size(); i++) {
			Deptpick deptpick = (Deptpick) deptpickList.get(i);
			deptpick.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService,"deptPickState", deptpick.getState()));
			if(deptpick.getComfirmUser() != null)
				deptpick.setXcomfirmUser(deptpick.getComfirmUser().getName());
			if(deptpick.getCreateUser() != null)
				deptpick.setXcreateUser(deptpick.getCreateUser().getName());
			lst.add(deptpick);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Deptpick.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	public String add(){
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		List<Locationonside> locationonsideLists =new ArrayList<Locationonside>();
		String wareHouse = admin.getTeam().getFactoryUnit().getWarehouse();
		String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		if(locationonsideList==null){
			locationonsideList = new ArrayList<Locationonside>();
		}
		try {
			locationonsideList = rfc.findWarehouse(wareHouse, werks);
			if(info==null){
				locationonsideLists =new ArrayList<Locationonside>();
				info = "";
				int i = info.length();
				for(Locationonside los : locationonsideList){
					if(los.getMaterialCode().length()>=i){
						String s = los.getMaterialCode().substring(0, i);
						if(info.equals(s)){
							locationonsideLists.add(los);
						}
					}
				}
				locationonsideList = locationonsideLists;
			}
			if(!"".equals(info) && info!=null){
				locationonsideLists =new ArrayList<Locationonside>();
				int i = info.length();
				for(Locationonside los : locationonsideList){
					if(los.getMaterialCode().length()>=i){
						String s = los.getMaterialCode().substring(0, i);
						if(info.equals(s)){
							locationonsideLists.add(los);
						}
					}
				}
				locationonsideList = locationonsideLists;
			}
			if(!"".equals(desp) && desp!=null){
				locationonsideLists =new ArrayList<Locationonside>();
				for(Locationonside los : locationonsideList){
					if(los.getMaterialName().indexOf(desp)>-1){
						locationonsideLists.add(los);
					}
				}
				locationonsideList = locationonsideLists;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}
		
		
		return INPUT;
	}
	public String edit(){
		
		deptpick = deptpickservice.get(id);

		return INPUT;
	}
	public String update(){
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			//deptpickservice.updateEidtReturnProduct(id, admin,deptpick,info);
			return ajaxJsonSuccessMessage("修改成功");
		} catch (Exception e) {
			return ajaxJsonErrorMessage("修改失败，请重试");
		}
	}
	public String creditsubmit(){
		try {
			boolean flag = false;
			Admin admin =  adminService.getByCardnum(cardnumber);
			if(admin.getProductDate() == null || admin.getShift() == null){
				return ajaxJsonErrorMessage("提交人未绑定生产日期或班次,不能提交!");
			}
			if(ThinkWayUtil.null2String(type).equals("")){
				return ajaxJsonErrorMessage("类型必须填写!");
			}else if(ThinkWayUtil.null2String(type).equals("deliver")){//发料
				if(ThinkWayUtil.null2String(movetype).equals("")){
					return ajaxJsonErrorMessage("发料移动类型不存在");
				}
			}else if(ThinkWayUtil.null2String(type).equals("rejected")){//退料
				if(ThinkWayUtil.null2String(movetype1).equals("")){
					return ajaxJsonErrorMessage("退料移动类型不存在");
				}
			}
			
			
			for(int i=0;i<deptpickList.size();i++){
				Deptpick deptpick = deptpickList.get(i);
				//if(deptpick.getStockMount()
				if(ThinkWayUtil.null2o(deptpick.getStockMount()) <=0){
					deptpickList.remove(i);
					i--;
					continue;
				}
				if(deptpick.getId() != null){
					Deptpick dp = deptpickservice.load(deptpick.getId());
					if(ThinkWayUtil.null2String(dp.getState()).equals("approval")){//已确认
						flag = true;
						continue;
					}
				}
				deptpick.setProductDate(admin.getProductDate());
				deptpick.setShift(admin.getShift());
				deptpick.setCostcenter(costcenter);
				deptpick.setMovetype(movetype);
				deptpick.setMovetype1(movetype1);
				deptpick.setState("notapproval");
				deptpick.setType(type);
				deptpick.setDepartmentName(departmentName);
				deptpick.setDepartid(departid);
				deptpick.setCreateUser(admin);//提交人
			}
			if(flag)
				return ajaxJsonErrorMessage("当前单据已确认,无法提交");
			
			deptpickservice.saveDeptpickList(deptpickList);
			return ajaxJsonSuccessMessage("保存成功!"); 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return ajaxJsonErrorMessage("保存失败，请重试");
		}
		
	}
	public String creditapproval(){
		boolean flag = false;
		String message="";
		Integer ishead=0;
		Admin admin =  adminService.getByCardnum(cardnumber);//确认人
		ids = id.split(",");
		try{
			for(int i=0;i<ids.length;i++){
				String id = ids[i];
				HashMap<String,String> map = new HashMap<String,String>();
				Deptpick deptpick = deptpickservice.get(id);
				
				if(ThinkWayUtil.null2String(deptpick.getE_type()).equals("S")){
					continue;
				}
				Admin createuser = deptpick.getCreateUser();//创建人
				String werks = createuser.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();//工厂
				String budat = deptpick.getProductDate();
				String lgort = createuser.getTeam().getFactoryUnit().getWarehouse();//库存地点
				String xuh = deptpick.getId();
				String costcenter = deptpick.getCostcenter();
				map.put("WERKS", werks);
				map.put("BUDAT", budat);
				map.put("LGORT", lgort);
				map.put("XUH", xuh);
				map.put("KOSTL", costcenter);
				
				deptpickList = new ArrayList<Deptpick>();
				deptpickList.add(deptpick);
				Deptpick dp = deptpickrfc.deptpickCrt("", map, deptpickList);
				deptpick.setE_type(dp.getE_type());
				deptpick.setE_message(dp.getE_message());
				deptpick.setEx_mblnr(dp.getEx_mblnr());
				if(!ThinkWayUtil.null2String(deptpick.getE_type()).equals("S")){
					flag = true;
					if(ishead==0)
						message+=deptpick.getE_message();
					else
						message+=","+deptpick.getE_message();
					ishead=1;
				}else{
					deptpick.setComfirmUser(admin);//确认人
					deptpick.setState("approval");//已确认
				}
				deptpickservice.update(deptpick);
			}
		}catch(IOException e){
			e.printStackTrace();
			log.error(e.getMessage());
			return ajaxJsonErrorMessage("IO操作失败");
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
			return ajaxJsonErrorMessage("确认失败");
		}
		
		if(flag){
			return ajaxJsonErrorMessage(message);
		}
		
		return ajaxJsonSuccessMessage("操作成功");
		
	}
	
	public String creditundo(){
		ids = id.split(",");
		Admin admin =  adminService.getByCardnum(cardnumber);//确认人
		List<Deptpick> deptpickList = deptpickservice.get(ids);
		for(int i=0;i<deptpickList.size();i++){
			Deptpick deptpick = deptpickList.get(i);
			if(ThinkWayUtil.null2String(deptpick.getState()).equals("approval") || ThinkWayUtil.null2String(deptpick.getState()).equals("undone")){
				return ajaxJsonErrorMessage("已确认或者已撤销的无法撤销");
			}
		}
		
		deptpickservice.updateDeptpickList(deptpickList,admin);
		
		return ajaxJsonSuccessMessage("操作成功");
	}
	
	public String view(){
		deptpick = deptpickservice.get(id);
		return VIEW;
	}
	
	
	
	
	// 获取所有状态
	public List<Dict> getAllSite(){
		return dictService.getList("dictname", "reporterSite");
	}
	// 获取所有类型
	public List<Dict> getAllType(){
		return dictService.getList("dictname", "endProductType");
	}
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<WorkingBill> getWorkingBillList() {
		return workingBillList;
	}

	public void setWorkingBillList(List<WorkingBill> workingBillList) {
		this.workingBillList = workingBillList;
	}





	public String getInfo() {
		return info;
	}




	public void setInfo(String info) {
		this.info = info;
	}
	public List<Locationonside> getLocationonsideList() {
		return locationonsideList;
	}
	public void setLocationonsideList(List<Locationonside> locationonsideList) {
		this.locationonsideList = locationonsideList;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	
	public List<Deptpick> getDeptpickList() {
		return deptpickList;
	}
	public void setDeptpickList(List<Deptpick> deptpickList) {
		this.deptpickList = deptpickList;
	}
	public Deptpick getDeptpick() {
		return deptpick;
	}
	public void setDeptpick(Deptpick deptpick) {
		this.deptpick = deptpick;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public String getDepartid() {
		return departid;
	}
	public void setDepartid(String departid) {
		this.departid = departid;
	}
	public String getCostcenter() {
		return costcenter;
	}
	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}
	public String getMovetype() {
		return movetype;
	}
	public void setMovetype(String movetype) {
		this.movetype = movetype;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getEx_mblnr() {
		return ex_mblnr;
	}

	public void setEx_mblnr(String ex_mblnr) {
		this.ex_mblnr = ex_mblnr;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getMovetype1() {
		return movetype1;
	}

	public void setMovetype1(String movetype1) {
		this.movetype1 = movetype1;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	

}
