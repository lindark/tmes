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

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.DeviceLog;
import cc.jiuyi.entity.DeviceModlue;
import cc.jiuyi.entity.DeviceStep;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Equipments;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.ReceiptReason;
import cc.jiuyi.sap.rfc.DeviceRfc;
import cc.jiuyi.sap.rfc.MatnrRfc;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DeviceLogService;
import cc.jiuyi.service.DeviceService;
import cc.jiuyi.service.DeviceStepService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReceiptReasonService;
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
	private String deviceType;
	private String stopMachine;
	private String stopProduct;
	private String faultCharactor;
	private String serviceAttitude;
	private String cardnumber;//刷卡卡号
	private String reasonName;//故障原因	
	
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
	private List<Department> list;
	
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
	@Resource
	private DeviceStepService deviceStepService;
	@Resource
	private DepartmentService deptservice;
	@Resource
	private DeviceRfc devicerfc;
	
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
		if(abnorId!=null && !"".equals(abnorId)){
			abnormalId=abnorId;
		}
		return LIST;
	}
	
	// 设备选择
	public String browser() {
		return "browser";
	}
	
	public String hview(){
		device = deviceService.load(id);
		abnormal=device.getAbnormal();
		deviceType=ThinkWayUtil.getDictValueByDictKey(
				dictService, "deviceType", device.getMaintenanceType());
		stopMachine=ThinkWayUtil.getDictValueByDictKey(
				dictService, "isDown", device.getIsDown());
		stopProduct=ThinkWayUtil.getDictValueByDictKey(
				dictService, "isMaintenance", device.getIsMaintenance());
		faultCharactor=ThinkWayUtil.getDictValueByDictKey(
				dictService, "deviceProperty", device.getFaultCharacter());
		
		ReceiptReason faultReason = receiptReasonService.load(device.getFault());
		reasonName=faultReason.getReasonName();
		if(device.getServiceAttitude()==null){
			serviceAttitude="";
		}else{
			serviceAttitude=ThinkWayUtil.getDictValueByDictKey(
					dictService, "serAttitude", device.getServiceAttitude());
		}
		
		return "hview";
	}
	
	// 车间选择
	public String workshop() {
		return "workshop";
	}
	
	// 人员选择
	public String person() {
		list = deptservice.getAllByHql();
		return "person";
	}

	public String ajlist() {
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		HashMap<String, String> map = new HashMap<String, String>();
		if(pager==null)
		{
			pager=new Pager();
		}
		pager.setOrderType(OrderType.desc);//倒序
		pager.setOrderBy("modifyDate");//以修改日期排序
		/*if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}*/
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

		if(StringUtils.isNotEmpty(abnorId) && !abnorId.equalsIgnoreCase("")){//日志链接页面
			pager = deviceService.findByPager(pager,map,abnorId);		
			List pagerlist = pager.getList();
	        String str;
			for (int i = 0; i < pagerlist.size(); i++) {

				Device device = (Device) pagerlist.get(i);
				device.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", device.getState()));	
				str="<a href='device!hview.action?id="+device.getId()+"'>"+device.getEquipments().getEquipmentName()+"</a>"; 
				device.setDeviceName(str);
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
		}else if(StringUtils.isNotEmpty(abnormalId) && !abnormalId.equalsIgnoreCase("")){//异常清单链接页面
			pager = deviceService.findByPager(pager, map,abnormalId);
			List pagerlist = pager.getList();
			for (int i = 0; i < pagerlist.size(); i++) {

				Device device = (Device) pagerlist.get(i);
				device.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", device.getState()));	
				device.setDeviceName(device.getEquipments().getEquipmentName());
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
		}else{//普通清单页面
			pager = deviceService.getDevicePager(pager, map,admin.getId(),admin.getDepartment().getTeam().getId());
			List pagerlist = pager.getList();
			for (int i = 0; i < pagerlist.size(); i++) {

				Device device = (Device) pagerlist.get(i);
				device.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", device.getState()));	
				device.setDeviceName(device.getEquipments().getEquipmentName());
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
		}		

		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Device.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	// 删除
	public String delete() throws Exception {	
		ids=id.split(",");
		deviceService.updateisdel(ids, "Y");
		redirectionUrl = "device!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	// 刷卡提交
	public String creditsave(){		
		admin = adminService.getByCardnum(cardnumber);
		
		abnormal=abnormalService.load(abnormalId);
		device.setAbnormal(abnormal);
		
		if(device.getEquipments()==null){
			return ajaxJsonErrorMessage("设备名称不允许为空!");
		} 
		
		if(device.getWorkShop()==null){
			return ajaxJsonErrorMessage("使用车间不允许为空!");
		}
		
		if(device.getDisposalWorkers()==null){
			return ajaxJsonErrorMessage("处理人员不允许为空!");
		}		
				
		device.setState("0");
		device.setIsDel("N");
		device.setWorkshopLinkman(admin);
				
		List<DeviceStep> step=new ArrayList<DeviceStep>();
		DeviceStep s=new DeviceStep();
		s.setVornr("0010");
		s.setArbpl("2101");//工作中心
		s.setWerks("1000");//工厂
		s.setSteus("PM01");//控制码
		s.setDescription("维修");//工序短文本
		s.setWork_activity("10");
		s.setDuration("10");			
		step.add(s);
		List<DeviceModlue> module=new ArrayList<DeviceModlue>();
		device.setORDER_TYPE("PM01");//订单类型
		device.setCOST("2");
		device.setBeginTime(new Date());
		device.setDndTime(new Date());
		device.setTotalDownTime(5.0);
		device.setSHORT_TEXT("设备维修单");
		deviceService.save(device);
		/*try {				
			String aufnr=devicerfc.DeviceCrt("0",device,step,module);
			device.setOrderNo(aufnr);
			deviceService.save(device);
				
		} catch (IOException e1) {
			e1.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常");
		} catch (CustomerException e1) {
			e1.printStackTrace();
			return ajaxJsonErrorMessage(e1.getMsgDes());
		}catch(Exception e){
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现错误，请联系系统管理员");
		}*/
		
		DeviceLog log = new DeviceLog();
		log.setOperator(admin);
		log.setInfo("已提交");
		log.setDevice(device);
		deviceLogService.save(log);
		
		
		AbnormalLog abnormalLog = new AbnormalLog();
		abnormalLog.setAbnormal(abnormal);
		abnormalLog.setType("3");
		abnormalLog.setOperator(admin);
		abnormalLogService.save(abnormalLog);
		
        return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	public String creditupdate() {		
		admin = adminService.getByCardnum(cardnumber);
		
		Device persistent = deviceService.load(id);
		if(persistent.getWorkshopLinkman()!=admin){
			return ajaxJsonErrorMessage("您不是单据创建人，无权提交该单据!");
		}
		if(persistent.getState().equals("3") || persistent.getState().equals("1")){
			return ajaxJsonErrorMessage("已关闭/回复的单据无法再提交!");
		}
		BeanUtils.copyProperties(device, persistent, new String[] { "id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments","receiptSet","receiptSet","team","orderNo"});
		deviceService.update(persistent);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	
	//刷卡回复	
	public String creditreply() throws Exception{
		admin = adminService.getByCardnum(cardnumber);
		
		Device persistent = deviceService.load(id);
		
		if(persistent.getDisposalWorkers()!=admin){
			return ajaxJsonErrorMessage("您不是指定维修员，无法回复该单据!");
		}
		if(persistent.getState().equals("3")){
			return ajaxJsonErrorMessage("已关闭的单据无法再回复!");
		}
		if(persistent.getState().equals("1")){
			return ajaxJsonErrorMessage("单据已回复!");
		}
		if(device.getBeginTime()==null){
			return ajaxJsonErrorMessage("处理开始时间不允许为空!");
		}
		
		if(device.getDndTime()==null){
			return ajaxJsonErrorMessage("处理结束时间不允许为空!");
		}
		
		if(device.getCauseAnalysis()==null || device.getCauseAnalysis().equalsIgnoreCase("")){
			return ajaxJsonErrorMessage("原因分析不允许为空!");
		}
		
		if(device.getPreventionCountermeasures()==null || device.getPreventionCountermeasures().equalsIgnoreCase("")){
			return ajaxJsonErrorMessage("预防对策不允许为空!");
		}
		
		if(deviceStepSet==null){
			return ajaxJsonErrorMessage("处理过程不允许为空!");
		}else{
				for(DeviceStep deviceStep:deviceStepSet){
					deviceStep.setDevice(persistent);
					deviceStepService.save(deviceStep);
				}
				device.setDeviceStepSet(new HashSet<DeviceStep>(deviceStepSet));
			
		}
				
		BeanUtils.copyProperties(device, persistent, new String[] { "id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments","receiptSet","maintenanceType","isDown","isMaintenance","faultCharacter","diagnosis","team","scrapNo","orderNo"});
		persistent.setState("1");
		deviceService.update(persistent);
		
		DeviceLog log = new DeviceLog();
		log.setDevice(persistent);
		log.setInfo("已回复");
		log.setOperator(admin);
		deviceLogService.save(log);
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
		
	//刷卡关闭
	public String creditclose() throws Exception{
		admin = adminService.getByCardnum(cardnumber);
		Device persistent = deviceService.load(id);
		
		if(persistent.getWorkshopLinkman()!=admin){
			return ajaxJsonErrorMessage("您不是单据创建人，无法关闭该单据!");
		}
		if(persistent.getState().equals("1")){
			
			if(device.getPhone()==null){
				return ajaxJsonErrorMessage("接到电话号码不允许为空!");
			}
			
			if(device.getCallTime()==null){
				return ajaxJsonErrorMessage("接到电话时间不允许为空!");
			}
			
			if(device.getArrivedTime()==null){
				return ajaxJsonErrorMessage("到达现场时间不允许为空!");
			}
			BeanUtils.copyProperties(device, persistent, new String[] {"id", "abnormal","isDel","state","workShop","workshopLinkman","disposalWorkers","equipments","receiptSet","maintenanceType","isDown","isMaintenance","faultCharacter","diagnosis","beginTime","dndTime","deviceStepSet","causeAnalysis","preventionCountermeasures","changeAccessoryAmountType","team","scrapNo","orderNo"});
			persistent.setState("3");
			deviceService.update(persistent);
						
			DeviceLog log = new DeviceLog();
			log.setDevice(persistent);
			log.setOperator(admin);
			log.setInfo("已关闭");
		    deviceLogService.save(log);
			
			persistent.setSHORT_TEXT("设备维修单");
			persistent.setCOST("2");
			persistent.setORDER_TYPE("PM01");//订单类型
			persistent.setTotalDownTime(5.0);//停机时间
			persistent.setURGRP("PM1");//原因代码组
			persistent.setURCOD("1001");//原因代码
			List<DeviceStep> step=new ArrayList<DeviceStep>();
			DeviceStep s=new DeviceStep();
			s.setVornr("0010");
			s.setArbpl("2101");//工作中心
			s.setWerks("1000");//工厂
			s.setSteus("PM01");//控制码
			s.setDescription("维修");//工序短文本
			s.setWork_activity("10");
			s.setDuration("10");			
			step.add(s);
			List<DeviceModlue> module=new ArrayList<DeviceModlue>();
			DeviceModlue dm=new DeviceModlue();
			dm.setMaterial("60000167");//物料编码
			dm.setMenge("1");
			dm.setVornr("0010");
			dm.setPostp("L");
			module.add(dm);
			
			//判断是否停机
			if(persistent.getIsDown().equalsIgnoreCase("0")){
				persistent.setIsDown("X");
			}
			
			/*try {
				String aufnr=devicerfc.DeviceCrt("1",persistent, step, module);				
			} catch (IOException e1) {
				e1.printStackTrace();
				return ajaxJsonErrorMessage("IO出现异常");
			} catch (CustomerException e1) {
				e1.printStackTrace();
				return ajaxJsonErrorMessage(e1.getMsgDes());
			}catch(Exception e){
				e.printStackTrace();
				return ajaxJsonErrorMessage("系统出现错误，请联系系统管理员");
			}*/
			
			
		    
		}else{
			return ajaxJsonErrorMessage("单据已关闭/未回复!");
		}
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
	
	
	public String view() {
		device = deviceService.load(id);
		abnormal=device.getAbnormal();
		deviceType=ThinkWayUtil.getDictValueByDictKey(
				dictService, "deviceType", device.getMaintenanceType());
		stopMachine=ThinkWayUtil.getDictValueByDictKey(
				dictService, "isDown", device.getIsDown());
		stopProduct=ThinkWayUtil.getDictValueByDictKey(
				dictService, "isMaintenance", device.getIsMaintenance());
		faultCharactor=ThinkWayUtil.getDictValueByDictKey(
				dictService, "deviceProperty", device.getFaultCharacter());
		
		ReceiptReason faultReason = receiptReasonService.load(device.getFault());
		reasonName=faultReason.getReasonName();
		if(device.getServiceAttitude()==null){
			serviceAttitude="";
		}else{
			serviceAttitude=ThinkWayUtil.getDictValueByDictKey(
					dictService, "serAttitude", device.getServiceAttitude());
		}
		
		
		qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
		modelList=new ArrayList<Model>(abnormal.getModelSet());
		craftList=new ArrayList<Craft>(abnormal.getCraftSet());
		deviceList=new ArrayList<Device>(abnormal.getDeviceSet());
		return VIEW;
	}
	
	// 列表
	public String sealist() {
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

	public List<DeviceStep> getDeviceStepSet() {
		return deviceStepSet;
	}

	public void setDeviceStepSet(List<DeviceStep> deviceStepSet) {
		this.deviceStepSet = deviceStepSet;
	}

	public List<Department> getList() {
		return list;
	}

	public void setList(List<Department> list) {
		this.list = list;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getStopMachine() {
		return stopMachine;
	}

	public void setStopMachine(String stopMachine) {
		this.stopMachine = stopMachine;
	}

	public String getStopProduct() {
		return stopProduct;
	}

	public void setStopProduct(String stopProduct) {
		this.stopProduct = stopProduct;
	}

	public String getFaultCharactor() {
		return faultCharactor;
	}

	public void setFaultCharactor(String faultCharactor) {
		this.faultCharactor = faultCharactor;
	}

	public String getServiceAttitude() {
		return serviceAttitude;
	}

	public void setServiceAttitude(String serviceAttitude) {
		this.serviceAttitude = serviceAttitude;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getReasonName() {
		return reasonName;
	}

	public void setReasonName(String reasonName) {
		this.reasonName = reasonName;
	}
	
	
}
