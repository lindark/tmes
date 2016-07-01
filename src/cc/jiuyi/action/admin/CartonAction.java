package cc.jiuyi.action.admin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CartonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 纸箱
 * 
 */
@ParentPackage("admin")
public class CartonAction extends BaseAdminAction {

	private static final long serialVersionUID = 5682952528834965226L;

	private static final String CONFIRMED = "1";
	private static final String UNCONFIRM="2";
	private static final String UNDO = "3";

	private Carton carton;
	private String workingBillId;
	private WorkingBill workingbill;
	private Admin admin;
	private String matnr;
	private String cardnumber;// 刷卡卡号
	private List<CartonSon>list_cs;//纸箱收货子表
	private String add;
	private String edit;
	private String show;
	private String info;
	private String loginid;//当前登录人
	private String isRecord;//纸箱收货记录
	private String bktxt;//单据编号

	@Resource
	private CartonService cartonService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;
	@Resource
	private TempKaoqinService tempKaoqinService;
	
	/**
	 * list页面
	 * @return
	 */
	public String list() 
	{
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		
		/*
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行纸箱收货操作!");
			return ERROR;
		}
		*/
		return LIST;
	}

	// 添加前
	public String add() 
	{
		Admin login_admin = adminService.getLoginAdmin();
		login_admin = adminService.get(login_admin.getId());
		/*login_admin = tempKaoqinService.getAdminWorkStateByAdmin(login_admin);		
		if(!ThinkWayUtil.isPass(login_admin)){
			addActionError("您当前未上班,不能进行纸箱收货操作!");
			return ERROR;
		}*/
		
		list_cs=this.cartonService.getBomByConditions();//获取bom中随工单对应的以5开头的各个物料
		List<CartonSon> cslist = new ArrayList<CartonSon>();
		for(CartonSon cs : list_cs){
			boolean flag = true;
			for(CartonSon cs1 : cslist){
				if(cs.getMATNR().equals(cs1.getMATNR()) && cs.getProductcode().equals(cs1.getProductcode())){
					flag = false;
					break;
				}
			}
			if(flag){
				cslist.add(cs);
			}
		}
		list_cs = cslist;
		add="add";
		return INPUT;
	}
	
	// 新增保存
	public String creditsave() throws Exception
	{
		this.cartonService.saveData(list_cs,cardnumber,loginid,bktxt);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 编辑
	public String edit()
	{
		Admin login_admin = adminService.getLoginAdmin();
		login_admin = adminService.get(login_admin.getId());
		/*login_admin = tempKaoqinService.getAdminWorkStateByAdmin(login_admin);		
		if(!ThinkWayUtil.isPass(login_admin)){
			addActionError("您当前未上班,不能进行纸箱收货操作!");
			return ERROR;
		}*/
		
		this.edit=id;
		carton = cartonService.get(id);
		list_cs=this.cartonService.getBomByConditions_edit(id);//获取bom中随工单对应的以5开头的各个物料
		return INPUT;
	}

	// 修改
	public String creditupdate()
	{
		this.cartonService.updateData(list_cs,id,bktxt);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	//查看
	public String show()
	{
		carton = cartonService.get(id);
		this.list_cs=this.cartonService.getToShow(id);
		this.show="show";
		return INPUT;
	}

	// 刷卡确认
	public String creditapproval()
	{
		Admin  card_admin= adminService.getByCardnum(cardnumber);
		/*card_admin = tempKaoqinService.getAdminWorkStateByAdmin(card_admin);		
		if(!ThinkWayUtil.isPass(card_admin)){
			
			return ajaxJsonErrorMessage("您当前未上班,不能进行纸箱收货操作!");
		}*/
		
		try
		{
			ids = info.split(",");
			for (int i = 0; i < ids.length; i++)
			{
				Carton c = cartonService.load(ids[i]);
				if (CONFIRMED.equals(c.getState())) {
					return ajaxJsonErrorMessage("已确认的无须再确认!");
				}
				if (UNDO.equals(c.getState())) {
					return ajaxJsonErrorMessage("已撤销的无法再确认！");
				}
			}
			String str = this.cartonService.updateToSAPNew(ids,cardnumber,loginid);
			String issuccess=ERROR;
			if("S".equals(str))
			{
				issuccess=SUCCESS;
				str="您的操作已成功!";
			}
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put(STATUS, issuccess);
			hashmap.put(MESSAGE, str);
			return ajaxJson(hashmap);
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
	}

	// 刷卡撤销
	public String creditundo() {
		//Admin admin = adminService.get(loginid);
		Admin  card_admin= adminService.getByCardnum(cardnumber);
		/*card_admin = tempKaoqinService.getAdminWorkStateByAdmin(card_admin);		
		if(!ThinkWayUtil.isPass(card_admin)){
			
			return ajaxJsonErrorMessage("您当前未上班,不能进行纸箱收货操作!");
		}*/
		try{
		ids = info.split(",");
		String str = "";
		List<Carton> list = cartonService.get(ids);
		for (int i = 0; i < list.size(); i++) {
			Carton carton = list.get(i);
			if (str.equals("")) {
				str = carton.getState();	
			} else if (!carton.getState().equals(str)) {
				return ajaxJsonErrorMessage("请选择同一状态的记录进行撤销!");
			}
		}
		boolean flag = false;
		for (int i = 0; i < ids.length; i++) {
			carton = cartonService.load(ids[i]);
			if (UNCONFIRM.equals(carton.getState())) {
				carton.setState("3");
				carton.setModifyDate(new Date());
				Date date = new Date(); 
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//可以方便地修改日期格式
				String time = dateFormat.format(date); 
//				carton.setConfirmUser(admin);//确认人
				carton.setRevokedUser(card_admin.getName());
				carton.setRevokedTime(time);
				carton.setRevokedUserCard(card_admin.getCardNumber());
				carton.setRevokedUserId(card_admin.getId());
				cartonService.update(carton);
				flag = true;
			}
		}
		if(flag){
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put(STATUS, "SUCCESS");
			hashmap.put(MESSAGE, "您的操作已成功!");
			return ajaxJson(hashmap);
		}
		for (int i = 0; i < ids.length; i++) { 
			carton = cartonService.load(ids[i]);
			if (UNDO.equals(carton.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
//			if (CONFIRMED.equals(carton.getState())){
//				if(!carton.getCreateUser().getId().equals(card_admin.getId())){
//					return ajaxJsonErrorMessage("无法撤销非自己创建并已确认收货单!");//已确认的无法撤销！
//				}
//			}
		}
		String s = this.cartonService.updateToSAPReturn(ids,cardnumber,loginid);
		String issuccess=ERROR;
		if("S".equals(s))
		{
			issuccess=SUCCESS;
			s="您的操作已成功!";
		}
		//cartonService.updateCancel(list, cardnumber);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, issuccess);
		hashmap.put(MESSAGE, s);
		return ajaxJson(hashmap);
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
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		
		Admin loginAdmin=adminService.getLoginAdmin();
		loginAdmin = adminService.get(loginAdmin.getId());
		String unitId="";
		if(loginAdmin.getTeam()!=null && loginAdmin.getTeam().getFactoryUnit()!=null)
		{
			unitId=loginAdmin.getTeam().getFactoryUnit().getId();
		}
		
		if(pager==null)
		{
			pager=new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if(isRecord==null){
			isRecord="";
		}
		if("Y".equals(isRecord)){
			
				HashMap<String,String> mapcheck = new HashMap<String,String>();
				if(Param != null)//普通搜索功能
				{
					if(!Param.equals(""))
					{
						//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
						JSONObject param = JSONObject.fromObject(Param);
						String start = ThinkWayUtil.null2String(param.get("start"));
						String end = ThinkWayUtil.null2String(param.get("end"));
						String state = ThinkWayUtil.null2String(param.get("state"));//
						String mntr = ThinkWayUtil.null2String(param.get("mntr"));//
						mapcheck.put("start", start);
						mapcheck.put("end", end);
						mapcheck.put("state", state);
						mapcheck.put("mntr", mntr);	
					}					
				}
				if(!unitId.equals(""))
				{
					mapcheck.put("unitId", unitId);
				}
				pager = cartonService.findCartonByPager(pager,mapcheck);
				System.out.println(pager.getList().size());
				// 需要查询条件
//				JSONObject filt = JSONObject.fromObject(filters);
//				Pager pager1 = new Pager();
//				Map m = new HashMap();
//				m.put("rules", jqGridSearchDetailTo.class);
//				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
//				pager.setRules(pager1.getRules());
//				pager.setGroupOp(pager1.getGroupOp());
			
		}else{
			pager = this.cartonService.getCartonPager(pager,loginid);
		}
		List<Carton> cartonList = pager.getList();
		List<Carton> lst = new ArrayList<Carton>();
		for (int i = 0; i < cartonList.size(); i++)
		{
			Carton c =cartonList.get(i);
			c.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService, "cartonState", c.getState()));//状态
			if(c.getCreateUser()!=null)
			{
				c.setXcreateUser(c.getCreateUser().getName());//创建人
			}
			if (c.getConfirmUser() != null)
			{
				c.setXconfirmUser(c.getConfirmUser().getName());//确认人
			}
			if(c.getTeamshift()!=null)
			{
				c.setXteamshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", c.getTeamshift()));
			}
			lst.add(c);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Carton.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	public String recordList(){
		return "record_list";
	}
	
	
	
	
	/**===========================================*/
	public Carton getCarton() {
		return carton;
	}

	public void setCarton(Carton carton) {
		this.carton = carton;
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

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
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

	public List<CartonSon> getList_cs()
	{
		return list_cs;
	}

	public void setList_cs(List<CartonSon> list_cs)
	{
		this.list_cs = list_cs;
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

	public String getLoginid()
	{
		return loginid;
	}

	public void setLoginid(String loginid)
	{
		this.loginid = loginid;
	}

	public String getIsRecord() {
		return isRecord;
	}

	public void setIsRecord(String isRecord) {
		this.isRecord = isRecord;
	}

	public String getBktxt() {
		return bktxt;
	}

	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}

	
}
