package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

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
import cc.jiuyi.entity.DeviceModlue;
import cc.jiuyi.entity.DeviceProcess;
import cc.jiuyi.entity.DeviceStep;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.ReceiptReason;
import cc.jiuyi.entity.UnusualLog;
import cc.jiuyi.sap.rfc.MatnrRfc;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DeviceLogService;
import cc.jiuyi.service.DeviceService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FaultReasonService;
import cc.jiuyi.service.ReceiptReasonService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.CustomerException;
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
	private String[] reasonIds;
	private Admin admin;
	private String abnorId; 
	private String equipNo;
	private String equipName;
	
	// 获取所有类型
	private List<Dict> allType;
	private List<Quality>  qualityList;
	private List<Model> modelList;
	private List<Craft> craftList;
	private List<Device> deviceList;
	private List<ReceiptReason> reasonList; 
	//private List<DeviceProcess> deviceProcessSet;
	private List<DeviceStep> deviceStepSet;
	private List<DeviceModlue> deviceModlueSet;
	
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
	@Resource
	private ReceiptReasonService receiptReasonService;
	@Resource
	private MatnrRfc matnrrfc;
	
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
		admin = adminService.get(admin.getId());
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

		if(StringUtils.isNotEmpty(abnorId) && !abnorId.equalsIgnoreCase("")){
			pager = deviceService.findByPager(pager,map,abnorId);		
		}else{
			pager = deviceService.getDevicePager(pager, map,admin.getId(),admin.getDepartment().getTeam().getId());
		}		

		List pagerlist = pager.getList();

		for (int i = 0; i < pagerlist.size(); i++) {
			System.out.println("into");
			Device device = (Device) pagerlist.get(i);
			device.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "receiptState", device.getState()));	
			device.setContactName(device.getWorkshopLinkman().getName());
			device.setWorkShopName(device.getWorkShop().getWorkShopName());
			device.setRepairName(device.getDisposalWorkers().getName());
			device.setRepairType(ThinkWayUtil.getDictValueByDictKey(
					dictService, "deviceType", device.getMaintenanceType()));

			ReceiptReason faultReason = receiptReasonService.load(device.getFault());
				String name = faultReason.getReasonName();
				device.setFaultReason(name);
			
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
	
	// 保存
	public String creditsave1(){		
		Admin admin1 = adminService.getLoginAdmin();
		
		abnormal=abnormalService.load(abnormalId);
		device.setAbnormal(abnormal);
		
		if(device.getEquipments()==null){
			addActionError("设备名称不允许为空！");
			return ERROR;
		} 
		
		if(device.getWorkShop()==null){
			addActionError("使用车间不允许为空！");
			return ERROR;
		}
		
		if(device.getDisposalWorkers()==null){
			addActionError("处理人员不允许为空！");
			return ERROR;
		}
		
	/*	if(deviceProcessSet==null){
			device.setDeviceProcessSet(null);
		}else{
			device.setDeviceProcessSet((new HashSet<DeviceProcess>(deviceProcessSet)));
		}*/
		
		if(deviceStepSet==null){
			device.setDeviceStepSet(null);
		}else{
			device.setDeviceStepSet((new HashSet<DeviceStep>(deviceStepSet)));
		}
		/*if (reasonIds != null && reasonIds.length > 0) {
			Set<ReceiptReason> reasonSet = new HashSet<ReceiptReason>(receiptReasonService.get(reasonIds));
			device.setReceiptSet(reasonSet);
		} else {
			addActionError("请选择故障原因！");
			return ERROR;
			//device.setReceiptSet(null);
		}*/
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
		abnormalLog.setType("3");
		abnormalLog.setOperator(admin1);
		abnormalLogService.save(abnormalLog);
		
		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
		//	//return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	@InputConfig(resultName = "error")
	public String update() {
		Device persistent = deviceService.load(id);
		BeanUtils.copyProperties(device, persistent, new String[] { "id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments","receiptSet","receiptSet"});
		deviceService.update(persistent);
		redirectionUrl = "device!list.action";
		return SUCCESS;
	}
	
	
	//刷卡回复	
	public String creditreply() throws Exception{
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
		
		if(device.getBeginTime()==null){
			addActionError("处理开始时间不允许为空！");
			return ERROR;
		}
		
		if(device.getDndTime()==null){
			addActionError("处理结束时间不允许为空！");
			return ERROR;
		}
		
		if(deviceStepSet==null){
			addActionError("处理过程不允许为空！");
			return ERROR;
		}
		
		if(device.getCauseAnalysis()==null){
			addActionError("原因分析不允许为空！");
			return ERROR;
		}
		
		if(device.getPreventionCountermeasures()==null){
			addActionError("预防对策不允许为空！");
			return ERROR;
		}
		BeanUtils.copyProperties(device, persistent, new String[] { "id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments","receiptSet","maintenanceType","isDown","isMaintenance","faultCharacter","diagnosis"});
		persistent.setState("1");
		deviceService.update(persistent);
		
		DeviceLog log = new DeviceLog();
		log.setDevice(persistent);
		log.setInfo("已回复");
		log.setOperator(admin);
		deviceLogService.save(log);
		
		//redirectionUrl="device!list.action";
		//return SUCCESS;
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
		
	//刷卡关闭
	public String creditclose() throws Exception{
		Admin admin = adminService.getLoginAdmin();
		Device persistent = deviceService.load(id);
		if(persistent.getState().equals("1")){
			
			if(device.getPhone()==null){
				addActionError("接到电话号码不允许为空！");
				return ERROR;
			}
			
			if(device.getCallTime()==null){
				addActionError("接到电话时间不允许为空！");
				return ERROR;
			}
			
			if(device.getArrivedTime()==null){
				addActionError("到达现场时间不允许为空！");
				return ERROR;
			}
			BeanUtils.copyProperties(device, persistent, new String[] {"id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments","receiptSet","maintenanceType","isDown","isMaintenance","faultCharacter","diagnosis","beginTime","dndTime","deviceStepSet","causeAnalysis","preventionCountermeasures","changeAccessoryAmountType"});
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
		
	//	redirectionUrl="device!list.action";
		//return SUCCESS;
		return ajaxJsonSuccessMessage("您的操作已成功!");
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
	
	// 列表
	public String sealist() {
		//pager = deviceService.findByPager(pager,abnorId);	
		abnormalId=abnorId;
		return "hlist";
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

	public List<ReceiptReason> getReasonList() {
		reasonList=receiptReasonService.getReceiptReasonByType("1");
		return reasonList;
	}

	public void setReasonList(List<ReceiptReason> reasonList) {
		this.reasonList = reasonList;
	}

	public String[] getReasonIds() {
		return reasonIds;
	}

	public void setReasonIds(String[] reasonIds) {
		this.reasonIds = reasonIds;
	}

	public String getAbnorId() {
		return abnorId;
	}

	public void setAbnorId(String abnorId) {
		this.abnorId = abnorId;
	}

	/*public List<DeviceProcess> getDeviceProcessSet() {
		return deviceProcessSet;
	}

	public void setDeviceProcessSet(List<DeviceProcess> deviceProcessSet) {
		this.deviceProcessSet = deviceProcessSet;
	}*/

	public String getEquipNo() {
		return equipNo;
	}

	public void setEquipNo(String equipNo) {
		this.equipNo = equipNo;
	}

	public String getEquipName() {
		return equipName;
	}

	public void setEquipName(String equipName) {
		this.equipName = equipName;
	}
	
	
}
