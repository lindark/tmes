package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.Station;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.TempKaoqin;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.PostService;
import cc.jiuyi.service.StationService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 考勤
 * 
 * @author gaoyf
 * 
 */
@ParentPackage("admin")
public class TempKaoqinAction extends BaseAdminAction {

	private static final long serialVersionUID = -7695470770728132309L;

	/**
	 * ========================variable,object,interface
	 * start========================
	 */

	/**
	 * 对象，变量
	 */
	private TempKaoqin kaoqin;
	private Admin admin;
	private List<Dict> list_dict;// 员工状态
	private List<TempKaoqin> list_kq;
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
	private String workHours;//工作小时
	private String teamid;//班组id
	private List<UnitdistributeModel> unitModelList = new ArrayList<UnitdistributeModel>();
	/**
	 * service接口
	 */
	@Resource
	private TempKaoqinService tkqService;
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
	@Resource
	private UnitdistributeProductService unitdistributeProductService;
	@Resource
	private PostService postService;
	

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
	 * jqGrid查询
	 */
	public String ajlist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager == null) {
			pager = new Pager();
		}
		pager.setOrderType(OrderType.desc);// 倒序
		pager.setOrderBy("modifyDate");// 以创建日期排序
		
		Admin admin=adminService.load(loginid);
		
		//班组
		String teamId = admin.getTeam().getId();
		map.put("teamId", teamId);
		
		// 班次			
		String shift = admin.getShift();
		map.put("shift", shift);
			
		// 生产日期			
		String productDate =admin.getProductDate();
		map.put("productDate", productDate);
					
		
		pager = this.tkqService.getTempKaoqinPager(pager, map);
		
		List<TempKaoqin> kqlist = pager.getList();
		List<TempKaoqin> lst = new ArrayList<TempKaoqin>();
		try {
			for (int i = 0; i < kqlist.size(); i++) {
				TempKaoqin kaoqin = kqlist.get(i);
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
				if(kaoqin.getWorkHours()!=null&&!"".equals(kaoqin.getWorkHours())){
					kaoqin.setXworkHours(ThinkWayUtil.getDictValueByDictKey(dictService,"workHours", kaoqin.getWorkHours()));
				}
				
				if(kaoqin.getModelNum()!=null)
				{
					String[] models=kaoqin.getModelNum().split(",");
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
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(TempKaoqin.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		return this.ajaxJson(jsonArray.getString(0).toString());
	}

	/**
	 * 修改临时考勤表工作信息
	 */
	public String updatetempkaoqin() {		
		String editId=kaoqin.getId();
		if(editId==null || editId.equals(""))
		{
			return ajaxJsonErrorMessage("数据不完整!");
		}
		TempKaoqin DBkaoqin=tkqService.get(editId);
		if(DBkaoqin==null)
		{
			return ajaxJsonErrorMessage("不存在这个考勤记录!");
		}
		//数据处理
		
		
		DBkaoqin.setPhoneNum(kaoqin.getPhoneNum());
		
		
		DBkaoqin.setWorkState(kaoqin.getWorkState());
		
		DBkaoqin.setTardyHours(kaoqin.getTardyHours());
				
		if(kaoqin.getPostCode()!=null&&!"".equals(kaoqin.getPostCode()))
		{
			String postCode=kaoqin.getPostCode();
			DBkaoqin.setPostCode(postCode);
			DBkaoqin.setPostname(postService.get("postCode", postCode).getPostName());
		}
		else
		{
			DBkaoqin.setPostCode(null);
			DBkaoqin.setPostname(null);
		}
		
		if(kaoqin.getStationCode()!=null&&!"".equals(kaoqin.getStationCode()))
		{
			String str=kaoqin.getStationCode();
			str=str.replace(" ","");
			DBkaoqin.setStationCode(str);
			String[] strs=str.split(",");
			//System.out.println(strs.length);
			String stationName="";
			for(String code:strs)
			{
				Station station= stationService.getByCode(code);
				if(station!=null)
				stationName+=station.getName()+",";
			}
			stationName=stationName.substring(0, stationName.length()-1);
			DBkaoqin.setStationName(stationName);
		}
		else
		{
			DBkaoqin.setStationCode(null);
			DBkaoqin.setStationName(null);
		}
		
		if(kaoqin.getModelNum()!=null&&!"".equals(kaoqin.getModelNum()))
		{			
			String str=kaoqin.getModelNum();
			str=str.replace(" ","");
			DBkaoqin.setModelNum(str);
			/*String[] strs=str.split(",");
			String modelName="";
			for(String id:strs)
			{
				UnitdistributeModel model= unitdistributeModelService.get(id);
				if(model!=null)
				modelName+=model.getUnitName()+",";
			}
			modelName=modelName.substring(0, modelName.length()-1);
			DBkaoqin.setModelName(modelName);*/
		}
		else
		{
			DBkaoqin.setModelNum(null);
		}
		
		if(kaoqin.getWorkNum()!=null && !"".equals(kaoqin.getWorkNum()))
		{
			String str=kaoqin.getWorkNum();
			str=str.replace(" ","");
			DBkaoqin.setWorkNum(str);
			String[] strs=str.split(",");
			String workName="";
			for(String code:strs)
			{
				UnitdistributeProduct product= unitdistributeProductService.get("materialCode", code);
				if(product!=null)
				workName+=product.getMaterialName()+",";
			}
			workName=workName.substring(0, workName.length()-1);
			DBkaoqin.setWorkName(workName);
		}
		else
		{
			DBkaoqin.setWorkNum(null);
			DBkaoqin.setWorkName(null);
		}
		
		//修改tempkaoqin
	
		DBkaoqin.setModifyDate(new Date());		
		tkqService.update(DBkaoqin);
		
		
		
		//修改Kaoqin
		Kaoqin kq=kqService.getByTPSA(DBkaoqin.getTeam().getFactoryUnit().getId(), DBkaoqin.getProductdate(), DBkaoqin.getClasstime(), DBkaoqin.getEmp().getId()).get(0);
		kq.setPhoneNum(DBkaoqin.getPhoneNum());
		kq.setWorkState(DBkaoqin.getWorkState());//
		kq.setTardyHours(DBkaoqin.getTardyHours());//误工小时数
		kq.setPostCode(DBkaoqin.getPostCode());
		kq.setPostname(DBkaoqin.getEmpname());		
		kq.setStationCode(DBkaoqin.getStationCode());
		kq.setStationName(DBkaoqin.getStationName());
		kq.setModleNum(DBkaoqin.getModelNum());
		kq.setWorkNum(DBkaoqin.getWorkNum());
		kq.setWorkName(DBkaoqin.getWorkName());
		kq.setModifyDate(new Date());		
		kqService.update(kq);
		
			
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
				this.tkqService.saveNewEmp(ids, sameTeamId, admin);
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
		TempKaoqin tkq=tkqService.get(id);
		if(tkq==null)
		{
			return this.ajaxJsonErrorMessage("0");// 移除成功
		}
		
		//String adminId=tkq.getEmp().getId();		
		Kaoqin kq=kqService.getByTPSA(tkq.getTeam().getFactoryUnit().getId(), tkq.getProductdate(), tkq.getClasstime(), tkq.getEmp().getId()).get(0);

		tkqService.delete(id);
		kqService.delete(kq);
		
		/*Admin rda=adminService.get(adminId);
		rda.setIsdaiban("N");
		rda.setModifyDate(new Date());
		rda.setProductDate(null);
		rda.setShift(null);		
		adminService.update(rda);*/
		
		return this.ajaxJsonSuccessMessage("1");// 移除成功
	}
	
	
	

	/**
	 * 开启考勤
	 */
	public String creditreply() {
		if (!isnull()) {
			return this.ajaxJsonErrorMessage("生产日期或班次不能为空!");
		}
		// 保存开启考勤(刷卡)记录
		this.tkqService.updateBrushCardEmp(loginid, my_id);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	/**
	 * 点击后刷卡 creditapproval
	 */
	public String creditapproval() {
		Admin a = this.adminService.getByCardnum(cardnumber);
		// 修改刷卡 人的状态,返回：1修改成功 2已经刷卡成功无需重复刷卡 3不是本班员工或本班代班员工
		int n = this.tkqService.updateWorkStateByCreidt(cardnumber, sameTeamId,
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
	 * 导出Excel表
	 */
	public String outexcel() {
		Admin loginAdmin=adminService.getLoginAdmin();
		Admin admin = adminService.get(loginAdmin.getId());
		if(admin.getTeam()==null||admin.getTeam().getFactoryUnit()==null){
			return this.ajaxJsonSuccessMessage("当前登录人没有相关联的班组或单元");
		}
		try {
			List<TempKaoqin> list = this.tkqService.getByTPS(sameTeamId,loginAdmin.getProductDate(), loginAdmin.getShift());
			List<TempKaoqin> kqNumlist = tkqService.getWorkNumList(admin.getProductDate(), admin.getShift(), admin
					.getTeam().getFactoryUnit().getFactoryUnitCode(), "2");
			List<String> header = new ArrayList<String>();
		
			header.add("员工卡号");
			header.add("姓名");
			header.add("工号");
			header.add("手机号");
			header.add("班组");
			header.add("单元");
			header.add("岗位");
			header.add("工位");
			header.add("模具组号");
			header.add("工作范围");
			header.add("员工状态");
			header.add("异常小时数");
//			header.add("已上班人数");
			List<Object[]> body = new ArrayList<Object[]>();
			for (int i = 0; i < list.size(); i++) {
				TempKaoqin tkq=list.get(i);
				Admin a = list.get(i).getEmp();
				Object[] str = new Object[header.size()];			
			
				str[0]=tkq.getCardNumber();
				str[1]=tkq.getEmp().getName();
				str[2]=tkq.getWorkCode();
				str[3]=tkq.getPhoneNum();
				str[4]=tkq.getTeam().getTeamName();
				str[5]=tkq.getFactoryUnit();
				str[6]=tkq.getPostname();
				str[7]=tkq.getStationName();
				
				String mjzh="";
				if(tkq.getModelNum()!=null)
				{
					String[] models=tkq.getModelNum().split(",");					
					for(int n=0;n<models.length;n++)
					{
						UnitdistributeModel model=unitdistributeModelService.get(models[n]);
						if(model != null)
							mjzh+=model.getStation()+",";
					}
					if(mjzh.length()>0) mjzh=mjzh.substring(0, mjzh.length()-1);					
				}			
				str[8]=mjzh;				
				
				str[9]=tkq.getWorkName();
				
				str[10]=ThinkWayUtil.getDictValueByDictKey(dictService, "adminworkstate", tkq.getWorkState());
				str[11]=tkq.getTardyHours();
//				str[12]=kqNumlist.size();
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
	
	public String updateWorkHours(){
//		System.out.println(workHours);
//		System.out.println(productdate);
//		System.out.println(classtime);
//		System.out.println(teamid);
		Team team = teamService.get(teamid);
		tkqService.updateWorkHours(workHours,productdate,classtime,team);
		kqService.updateWorkHours(workHours,productdate,classtime,team);
		return this.ajaxJsonSuccessMessage("操作成功!");
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

	public TempKaoqin getKaoqin() {
		return kaoqin;
	}

	public void setKaoqin(TempKaoqin kaoqin) {
		this.kaoqin = kaoqin;
	}

	public List<TempKaoqin> getList_kq() {
		return list_kq;
	}

	public void setList_kq(List<TempKaoqin> list_kq) {
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

	public String getWorkHours() {
		return workHours;
	}

	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}

	public String getTeamid() {
		return teamid;
	}

	public void setTeamid(String teamid) {
		this.teamid = teamid;
	}
	
	/** ===========================end get/set=============================== */
}
