package cc.jiuyi.action.admin;

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

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.CraftLog;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.DeviceLog;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.UnusualLog;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DeviceLogService;
import cc.jiuyi.service.DeviceService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 设备
 */

@ParentPackage("admin")
public class DeviceAction extends BaseAdminAction {

	private static final long serialVersionUID = -5133263227242342963L;
	
	private Device device;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	private String loginUsername;
	private Admin admin;
	// 获取所有类型
	private List<Dict> allType;
	private List<Quality>  qualityList;
	private List<Model> modelList;
	private List<Craft> craftList;
	private List<Device> deviceList;
	
	@Resource
	private DeviceService deviceService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private DeviceLogService deviceLogService;
	@Resource
	private AbnormalLogService abnormalLogService;
	
	// 添加
	public String add() {
		if(aid!=null){
			abnormal=abnormalService.load(aid);
			qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
			modelList=new ArrayList<Model>(abnormal.getModelSet());
			craftList=new ArrayList<Craft>(abnormal.getCraftSet());
			deviceList=new ArrayList<Device>(abnormal.getDeviceSet());
		}	
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		return INPUT;
	}

	// 编辑
	public String edit() {
		device = deviceService.load(id);
		abnormal=device.getAbnormal();
		qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
		modelList=new ArrayList<Model>(abnormal.getModelSet());
		craftList=new ArrayList<Craft>(abnormal.getCraftSet());
		deviceList=new ArrayList<Device>(abnormal.getDeviceSet());
		return INPUT;
	}

	// 列表
	public String list() {
		return LIST;
	}
	
	// 设备选择
	public String browser() {
		return "browser";
	}
	
	// 车间选择
	public String workshop() {
		return "workshop";
	}
	
	// 人员选择
	public String person() {
		return "person";
	}

	public String ajlist() {
		Admin admin = adminService.getLoginAdmin();
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
			
		   if (obj.get("workShopName1") != null) { 
			   String workShopName1 = obj.getString("workShopName1").toString();
			   map.put("workShopName1", workShopName1);
			}

		   if (obj.get("repairPerson") != null) { 
			   String repairPerson = obj.getString("repairPerson").toString();
			   map.put("repairPerson", repairPerson);
		   }

		}

		pager = deviceService.getDevicePager(pager, map,admin.getId());

		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			Device device = (Device) pagerlist.get(i);
			device.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "receiptState", device.getState()));	
			device.setContactName(device.getWorkshopLinkman().getName());
			device.setWorkShopName(device.getWorkShop().getWorkShopName());
			device.setRepairName(device.getDisposalWorkers().getName());
			device.setRepairType(ThinkWayUtil.getDictValueByDictKey(
					dictService, "deviceType", device.getMaintenanceType()));
			pagerlist.set(i,device);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Device.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	// 删除
	public String delete() throws Exception {	
		ids=id.split(",");
		deviceService.updateisdel(ids, "Y");
		redirectionUrl = "device!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	public String save(){		
		Admin admin1 = adminService.getLoginAdmin();
		
		abnormal=abnormalService.load(abnormalId);
		device.setAbnormal(abnormal);
		device.setState("0");
		device.setIsDel("N");
		
		deviceService.save(device);
		
		DeviceLog log = new DeviceLog();
		log.setOperator(admin1);
		log.setInfo("已提交");
		log.setDevice(device);
		deviceLogService.save(log);
		
		
		AbnormalLog abnormalLog = new AbnormalLog();
		abnormalLog.setAbnormal(abnormal);
		abnormalLog.setInfo("已开设备维修单");
		abnormalLog.setOperator(admin1);
		abnormalLogService.save(abnormalLog);
		
		redirectionUrl = "device!list.action";
		return SUCCESS;
	}
	
	@InputConfig(resultName = "error")
	public String update() {
		Device persistent = deviceService.load(id);
		BeanUtils.copyProperties(device, persistent, new String[] { "id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments"});
		deviceService.update(persistent);
		redirectionUrl = "device!list.action";
		return SUCCESS;
	}
	
	
	//刷卡回复
	public String check() throws Exception{
		Admin admin = adminService.getLoginAdmin();
		Device persistent = deviceService.load(id);
		if(persistent.getState().equals("3")){
			addActionError("已关闭的单据无法再回复！");
			return ERROR;
		}
		if(persistent.getState().equals("1")){
			addActionError("单据已回复！");
			return ERROR;
		}
		BeanUtils.copyProperties(device, persistent, new String[] { "id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments"});
		persistent.setState("1");
		deviceService.update(persistent);
		
		DeviceLog log = new DeviceLog();
		log.setDevice(persistent);
		log.setInfo("已回复");
		log.setOperator(admin);
		deviceLogService.save(log);
		
		redirectionUrl="device!list.action";
		return SUCCESS;
	}	
		
	//刷卡关闭
	public String close() throws Exception{
		Admin admin = adminService.getLoginAdmin();
		Device persistent = deviceService.load(id);
		if(persistent.getState().equals("1")){
			BeanUtils.copyProperties(device, persistent, new String[] {"id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments"});
			persistent.setState("3");
			deviceService.update(persistent);
			
			DeviceLog log = new DeviceLog();
			log.setDevice(persistent);
			log.setOperator(admin);
			log.setInfo("已关闭");
		    deviceLogService.save(log);
		    
		}else{
			addActionError("单据已关闭/未回复！");
			return ERROR;
		}
		
		redirectionUrl="device!list.action";
		return SUCCESS;
	}	
	
	
	public String view() {
		device = deviceService.load(id);
		abnormal=device.getAbnormal();
		qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
		modelList=new ArrayList<Model>(abnormal.getModelSet());
		craftList=new ArrayList<Craft>(abnormal.getCraftSet());
		deviceList=new ArrayList<Device>(abnormal.getDeviceSet());
		return VIEW;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	public String getAbnormalId() {
		return abnormalId;
	}

	public void setAbnormalId(String abnormalId) {
		this.abnormalId = abnormalId;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public List<Dict> getAllType() {
		return dictService.getList("dictname", "deviceType");
	}

	public void setAllType(List<Dict> allType) {
		this.allType = allType;
	}
	
	public List<Dict> getAllProperty() {
		return dictService.getList("dictname", "deviceProperty");
	}
	
	public List<Dict> getAllAttitude() {
		return dictService.getList("dictname", "serAttitude");
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	public List<Dict> getAllDown() {
		return dictService.getList("dictname", "isDown");
	}
	
	public List<Dict> getAllMaintenance() {
		return dictService.getList("dictname", "isMaintenance");
	}

	public List<Quality> getQualityList() {
		return qualityList;
	}

	public void setQualityList(List<Quality> qualityList) {
		this.qualityList = qualityList;
	}

	public List<Model> getModelList() {
		return modelList;
	}

	public void setModelList(List<Model> modelList) {
		this.modelList = modelList;
	}

	public List<Device> getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(List<Device> deviceList) {
		this.deviceList = deviceList;
	}

	public List<Craft> getCraftList() {
		return craftList;
	}

	public void setCraftList(List<Craft> craftList) {
		this.craftList = craftList;
	}
	
	
}
