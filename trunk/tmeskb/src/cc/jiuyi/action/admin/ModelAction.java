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

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.DeviceModlue;
import cc.jiuyi.entity.DeviceStep;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.HandlemeansResults;
import cc.jiuyi.entity.LongtimePreventstep;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.ModelLog;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.sap.rfc.DeviceRfc;
import cc.jiuyi.sap.rfc.ModeRfc;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ModelLogService;
import cc.jiuyi.service.ModelService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 工模维修单
 */

@ParentPackage("admin")
public class ModelAction extends BaseAdminAction {

	private static final long serialVersionUID = -5323263207248342963L;

	private Model model;
	private String loginUsername;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	private Admin admin;
	private String abnorId;
	private String modelType;
	private String cardnumber;//刷卡卡号
	private Date replydate; 
	
	private List<FaultReason> faultReasonSet;
	private List<HandlemeansResults> handleSet;
	private List<LongtimePreventstep> longSet;
	private List<Quality>  qualityList;
	private List<Model> modelList;
	private List<Craft> craftList;
	private List<Device> deviceList;
	private List<Department> list;

	@Resource
	private ModelService modelService;
	@Resource
	private AdminService adminService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private ModelLogService modelLogService;
	@Resource
	private DictService dictService;
	@Resource
	private AbnormalLogService abnormalLogService;
	@Resource
	private DepartmentService deptservice;
	@Resource
	private ModeRfc moderfc;

	// 添加
	public String add() {
		if (aid != null) {
			abnormal = abnormalService.load(aid);
			if(abnormal.getReplyDate()!=null && !"".equals(abnormal.getReplyDate())){
				replydate=abnormal.getReplyDate();
			}
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
		model = modelService.load(id);
		System.out.println(model.getConfirmTime());
		abnormal=model.getAbnormal();
		if(abnormal.getReplyDate()!=null && !"".equals(abnormal.getReplyDate())){
			replydate=abnormal.getReplyDate();
		}
		qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
		craftList=new ArrayList<Craft>(abnormal.getCraftSet());
		modelList=new ArrayList<Model>(abnormal.getModelSet());
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
	
	public String browser(){
		return "browser";
	}
	
	public String reason(){
		return "reason";
	}
	
	public String repair(){
		list = deptservice.getAllByHql();
		return "repair";
	}
	
	public String insepector(){
		list = deptservice.getAllByHql();
		return "insepector";
	}
	
	public String handle(){
		return "handle";
	}
	
	public String prevent(){
		return "prevent";
	}
	
	public String view() {
		model = modelService.load(id);
		abnormal=model.getAbnormal();
		modelType=ThinkWayUtil.getDictValueByDictKey(
				dictService, "modelType", model.getType());
		qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
		modelList=new ArrayList<Model>(abnormal.getModelSet());
		craftList=new ArrayList<Craft>(abnormal.getCraftSet());
		deviceList=new ArrayList<Device>(abnormal.getDeviceSet());
		return VIEW;
	}


	public String creditupdate() {
		Model persistent = modelService.load(id);
		if(persistent.getState().equals("3") || persistent.getState().equals("2") || persistent.getState().equals("1")){
			return ajaxJsonErrorMessage("已关闭/确定/回复的单据无法再提交！");
		}
		BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","createUser","isDel","state","initiator","products","teamId","insepector","fixer","equipments","orderNo" });
		modelService.update(persistent);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	
	//刷卡回复		
	public String creditreply() throws Exception{
		admin = adminService.getByCardnum(cardnumber);
		Model persistent = modelService.load(id);
		if(persistent.getFixer()!=admin){
			return ajaxJsonErrorMessage("您不是指定维修员,无法回复该单据!");
		}
		if(persistent.getState().equals("2")){
			return ajaxJsonErrorMessage("已确定的单据无法再回复！");
		}
		if(persistent.getState().equals("3")){
			return ajaxJsonErrorMessage("已关闭的单据无法再回复！");
		}
/*		if(persistent.getState().equals("1")){
			return ajaxJsonErrorMessage("单据已回复！");
		}*/
			
		if(model.getFixTime()==null){
			return ajaxJsonErrorMessage("维修时间不允许为空!");
		}
				
		BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","isDel","initiator","equipments","teamId","insepector","fixer","failDescript","noticeTime","arriveTime","orderNo"});
		persistent.setState("1");	

		if(faultReasonSet!=null || persistent.getFaultReasonSet()!=null){
			persistent.setFaultReasonSet(new HashSet<FaultReason>(faultReasonSet));
		}else{
			return ajaxJsonErrorMessage("故障原因不允许为空!");
		}
		if(handleSet!=null || persistent.getHandleSet()!=null){
			persistent.setHandleSet(new HashSet<HandlemeansResults>(handleSet));
		}else{
			return ajaxJsonErrorMessage("处理方法不允许为空!");
		}
		if(longSet!=null || persistent.getLongSet()!=null){
			persistent.setLongSet(new HashSet<LongtimePreventstep>(longSet));
		}else{
			return ajaxJsonErrorMessage("预防措施不允许为空!");
		}
		
		modelService.update(persistent);
		
		ModelLog log = new ModelLog();
		log.setInfo("已回复");
		log.setOperator(admin);
		log.setModel(persistent);
		modelLogService.save(log);
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
	
	
	//刷卡确定
	public String creditapproval() throws Exception{
		admin = adminService.getByCardnum(cardnumber);
		Model persistent = modelService.load(id);
		if(persistent.getInsepector()!=admin){
			return ajaxJsonErrorMessage("您不是指定检验员,不能确认该单据!");
		}
		if(persistent.getState().equals("1")){
			if(model.getConfirmTime()==null){
				return ajaxJsonErrorMessage("确认时间不允许为空！");
			}
			BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","createUser","isDel","initiator","equipments","teamId","insepector","fixer","faultReasonSet","handleSet","longSet","fixTime","failDescript","noticeTime","arriveTime","orderNo"});
			persistent.setState("2");
			modelService.update(persistent);
			
			ModelLog log = new ModelLog();
			log.setInfo("已确认");
			log.setOperator(admin);
			log.setModel(persistent);
			modelLogService.save(log);
		}else{
			return ajaxJsonErrorMessage("该单据未回复/已关闭/已确认！");
		}
				
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
	
	//刷卡关闭
	public String creditclose() throws Exception{
		admin = adminService.getByCardnum(cardnumber);
		Model persistent = modelService.load(id);
		if(persistent.getInitiator()!=admin){
			return ajaxJsonErrorMessage("您不是单据创建人,无法关闭该单据!");
		}
		if(persistent.getState().equals("2")){
			BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","createUser","isDel","initiator","equipments","teamId","insepector","fixer","faultReasonSet","handleSet","longSet","confirmTime","failDescript","noticeTime","arriveTime","orderNo"});
			persistent.setState("3");
			modelService.update(persistent);
			
			ModelLog log = new ModelLog();
			log.setInfo("已关闭");
			log.setOperator(admin);
			log.setModel(persistent);
			modelLogService.save(log);
			
			persistent.setSHORT_TEXT("工模维修单");
			persistent.setCOST("2");
			persistent.setORDER_TYPE("PM01");//订单类型
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
			modelService.update(persistent);
			/*
			try {
				String aufnr=moderfc.ModelCrt("1",persistent, step, module);
				modelService.update(persistent);
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
			return ajaxJsonErrorMessage("该单据已关闭/未确认！");
		}
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
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
			if (obj.get("repairName") != null) {
				String repairName = obj.getString("repairName").toString();
				map.put("repairName", repairName);
			}

			if (obj.get("equipmentName") != null) {
				String equipmentName = obj.getString("equipmentName").toString();
				map.put("equipmentName", equipmentName);
			}

		}

		if(StringUtils.isNotEmpty(abnorId) && !abnorId.equalsIgnoreCase("")){//日志链接页面
			pager = modelService.findByPager(pager,map,abnorId);	
			List pagerlist = pager.getList();
			
			String str1;
			for (int i = 0; i < pagerlist.size(); i++) {
				Model model = (Model) pagerlist.get(i);
				str1="<a href='model!hview.action?id="+model.getId()+"'>"+model.getEquipments().getEquipmentName()+"</a>"; 
	            model.setTeamName(model.getTeamId().getTeamName());
				model.setProductName(str1);
				model.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", model.getState()));
				model.setRepairName(model.getFixer().getName());
							
				List<FaultReason> faultReasonList = new ArrayList<FaultReason>(
						model.getFaultReasonSet());
				List<String> strlist = new ArrayList<String>();	
	            if(model.getFaultReasonSet()==null){
	            	model.setFaultName("");
				}else{
					for (FaultReason faultReason : faultReasonList) {
						String str = faultReason.getReasonName();
						strlist.add(str);
						String comlist = CommonUtil.toString(strlist, ",");// 获取问题的字符串
						model.setFaultName(comlist);}
				}
				
				pagerlist.set(i, model);
			}

			pager.setList(pagerlist);
		}else if(StringUtils.isNotEmpty(abnormalId) && !abnormalId.equalsIgnoreCase("")){
			pager = modelService.findByPager(pager, map,abnormalId);	
			List pagerlist = pager.getList();
			for (int i = 0; i < pagerlist.size(); i++) {
				Model model = (Model) pagerlist.get(i);
	            model.setTeamName(model.getTeamId().getTeamName());
				model.setProductName(model.getEquipments().getEquipmentName());
				model.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", model.getState()));
				model.setRepairName(model.getFixer().getName());
							
				List<FaultReason> faultReasonList = new ArrayList<FaultReason>(
						model.getFaultReasonSet());
				List<String> strlist = new ArrayList<String>();	
	            if(model.getFaultReasonSet()==null){
	            	model.setFaultName("");
				}else{
					for (FaultReason faultReason : faultReasonList) {
						String str = faultReason.getReasonName();
						strlist.add(str);
						String comlist = CommonUtil.toString(strlist, ",");// 获取问题的字符串
						model.setFaultName(comlist);}
				}
				
				pagerlist.set(i, model);
			}

			pager.setList(pagerlist);
		
		}else{//普通清单页面
			pager = modelService.getModelPager(pager, map,admin.getId(),admin.getTeam().getId());	
			List pagerlist = pager.getList();
			for (int i = 0; i < pagerlist.size(); i++) {
				Model model = (Model) pagerlist.get(i);
	            model.setTeamName(model.getTeamId().getTeamName());
				model.setProductName(model.getEquipments().getEquipmentName());
				model.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", model.getState()));
				model.setRepairName(model.getFixer().getName());
							
				List<FaultReason> faultReasonList = new ArrayList<FaultReason>(
						model.getFaultReasonSet());
				List<String> strlist = new ArrayList<String>();	
	            if(model.getFaultReasonSet()==null){
	            	model.setFaultName("");
				}else{
					for (FaultReason faultReason : faultReasonList) {
						String str = faultReason.getReasonName();
						strlist.add(str);
						String comlist = CommonUtil.toString(strlist, ",");// 获取问题的字符串
						model.setFaultName(comlist);}
				}
				
				pagerlist.set(i, model);
			}

			pager.setList(pagerlist);
		}
		

		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Model.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 刷卡提交
	public String creditsave() {
		admin = adminService.getByCardnum(cardnumber);
		abnormal = abnormalService.load(abnormalId);
		model.setAbnormal(abnormal);
		
		if(model.getInsepector()==null){
			return ajaxJsonErrorMessage("检验员不允许为空！");
		}
		
		if(model.getFixer()==null){
			return ajaxJsonErrorMessage("维修员不允许为空！");
		}
		
		if(model.getEquipments()==null){
			return ajaxJsonErrorMessage("设备名称不允许为空!");
		}
		
		if(faultReasonSet==null){
			model.setFaultReasonSet(null);
		}else{
			model.setFaultReasonSet(new HashSet<FaultReason>(faultReasonSet));
		}
		if(handleSet==null){
			model.setHandleSet(null);
		}else{
			model.setHandleSet(new HashSet<HandlemeansResults>(handleSet));
		}
		if(longSet==null){
			model.setLongSet(null);
		}else{
			model.setLongSet(new HashSet<LongtimePreventstep>(longSet));
		}
		model.setIsDel("N");
		model.setState("0");
		model.setInitiator(admin);
		
		model.setSHORT_TEXT("工模维修单");
		model.setCOST("2");
		model.setCreateDate(new Date());
		model.setConfirmTime(new Date());
		model.setORDER_TYPE("PM01");//订单类型
		model.setURGRP("PM1");//原因代码组
		model.setURCOD("1001");//原因代码

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
		modelService.save(model);
		/*try {
			String aufnr=moderfc.ModelCrt("0",model, step, module);
			model.setOrderNo(aufnr);
			modelService.save(model);
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
		
		ModelLog log = new ModelLog();
		log.setInfo("已提交");
		log.setOperator(admin);
		log.setModel(model);
		modelLogService.save(log);

		AbnormalLog abnormalLog = new AbnormalLog();
		abnormalLog.setAbnormal(abnormal);
		abnormalLog.setType("1");
		abnormalLog.setOperator(admin);
		abnormalLogService.save(abnormalLog);
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	// 列表
	public String sealist() {
		abnormalId=abnorId;
		return "hlist";
	}
	
	//详情
	public String hview(){
		model = modelService.load(id);
		modelType=ThinkWayUtil.getDictValueByDictKey(
				dictService, "modelType", model.getType());
		return "hview";
	}

	// 删除
	public String delete() throws Exception {
		ids = id.split(",");
		modelService.updateisdel(ids, "Y");
		redirectionUrl = "model!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
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
	
	// 获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "modelType");
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<FaultReason> getFaultReasonSet() {
		return faultReasonSet;
	}

	public void setFaultReasonSet(List<FaultReason> faultReasonSet) {
		this.faultReasonSet = faultReasonSet;
	}

	public List<HandlemeansResults> getHandleSet() {
		return handleSet;
	}

	public void setHandleSet(List<HandlemeansResults> handleSet) {
		this.handleSet = handleSet;
	}

	public List<LongtimePreventstep> getLongSet() {
		return longSet;
	}

	public void setLongSet(List<LongtimePreventstep> longSet) {
		this.longSet = longSet;
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

	public String getAbnorId() {
		return abnorId;
	}

	public void setAbnorId(String abnorId) {
		this.abnorId = abnorId;
	}

	public List<Department> getList() {
		return list;
	}

	public void setList(List<Department> list) {
		this.list = list;
	}

	public String getModelType() {
		return modelType;
	}

	public void setModelType(String modelType) {
		this.modelType = modelType;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public Date getReplydate() {
		return replydate;
	}

	public void setReplydate(Date replydate) {
		this.replydate = replydate;
	}

	
	
}
