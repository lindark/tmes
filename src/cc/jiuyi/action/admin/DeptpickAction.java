package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.action.cron.WorkingBillJob;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Deptpick;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Pick;
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
	private String departmentName;//部门描述
	
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
		String wareHouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
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
				deptpick.setState("notapproval");
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
				String werks = createuser.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();//工厂
				String budat = deptpick.getProductDate();
				String lgort = createuser.getDepartment().getTeam().getFactoryUnit().getWarehouse();//库存地点
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

	
	

}
