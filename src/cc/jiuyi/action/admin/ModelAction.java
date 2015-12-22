package cc.jiuyi.action.admin;

import java.util.ArrayList;
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
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.HandlemeansResults;
import cc.jiuyi.entity.LongtimePreventstep;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.ModelLog;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ModelLogService;
import cc.jiuyi.service.ModelService;
import cc.jiuyi.util.CommonUtil;
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

	// 添加
	public String add() {
		if (aid != null) {
			abnormal = abnormalService.load(aid);
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
		abnormal=model.getAbnormal();
		qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
		craftList=new ArrayList<Craft>(abnormal.getCraftSet());
		modelList=new ArrayList<Model>(abnormal.getModelSet());
		deviceList=new ArrayList<Device>(abnormal.getDeviceSet());
		return INPUT;
	}

	// 列表
	public String list() {
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

	@InputConfig(resultName = "error")
	public String update() {
		Model persistent = modelService.load(id);
		BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","createUser","isDel","state","initiator","products","teamId","insepector","fixer" });
		modelService.update(persistent);
		redirectionUrl = "model!list.action";
		return SUCCESS;
	}
	
	
	//刷卡回复		
	public String creditreply1() throws Exception{
		Admin admin = adminService.getLoginAdmin();
		Model persistent = modelService.load(id);
		if(persistent.getState().equals("2")){
			addActionError("已确定的单据无法再回复！");
			return ERROR;
		}
		if(persistent.getState().equals("3")){
			addActionError("已关闭的单据无法再回复！");
			return ERROR;
		}
		if(persistent.getState().equals("1")){
			addActionError("单据已回复！");
			return ERROR;
		}
			
		if(model.getFixTime()==null){
			addActionError("维修时间不允许为空！");
			return ERROR;
		}
				
		BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","isDel","initiator","equipments","teamId","insepector","fixer","failDescript","noticeTime","arriveTime"});
		persistent.setState("1");	

		if(faultReasonSet!=null){
			persistent.setFaultReasonSet(new HashSet<FaultReason>(faultReasonSet));
		}else{
			addActionError("故障原因不允许为空！");
			return ERROR;
		}
		if(handleSet!=null){
			persistent.setHandleSet(new HashSet<HandlemeansResults>(handleSet));
		}else{
			addActionError("处理方法不允许为空！");
			return ERROR;
		}
		if(longSet!=null){
			persistent.setLongSet(new HashSet<LongtimePreventstep>(longSet));
		}else{
			addActionError("预防措施不允许为空！");
			return ERROR;
		}
		
		modelService.update(persistent);
		
		ModelLog log = new ModelLog();
		log.setInfo("已回复");
		log.setOperator(admin);
		log.setModel(persistent);
		modelLogService.save(log);
		
		redirectionUrl="model!list.action";
		return SUCCESS;
		//return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
	
	
	//刷卡确定
	public String creditapproval() throws Exception{
		Admin admin = adminService.getLoginAdmin();
		Model persistent = modelService.load(id);
		if(persistent.getState().equals("1")){
			if(model.getConfirmTime()==null){
				addActionError("确认时间不允许为空！");
				return ERROR;
			}
			BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","createUser","isDel","initiator","equipments","teamId","insepector","fixer","faultReasonSet","handleSet","longSet","fixTime","failDescript","noticeTime","arriveTime"});
			persistent.setState("2");
			modelService.update(persistent);
			
			ModelLog log = new ModelLog();
			log.setInfo("已确认");
			log.setOperator(admin);
			log.setModel(persistent);
			modelLogService.save(log);
		}else{
			addActionError("该单据未回复/已关闭/已确认！");
			return ERROR;
		}
				
	//	redirectionUrl="model!list.action";
	//	return SUCCESS;
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
	
	//刷卡关闭
	public String creditclose() throws Exception{
		Admin admin = adminService.getLoginAdmin();
		Model persistent = modelService.load(id);
		if(persistent.getState().equals("2")){
			BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","createUser","isDel","initiator","equipments","teamId","insepector","fixer","faultReasonSet","handleSet","longSet","confirmTime","failDescript","noticeTime","arriveTime","confirmTime"});
			persistent.setState("3");
			modelService.update(persistent);
			
			ModelLog log = new ModelLog();
			log.setInfo("已关闭");
			log.setOperator(admin);
			log.setModel(persistent);
			modelLogService.save(log);
		}else{
			addActionError("该单据已关闭/未确认！");
			return ERROR;
		}
		
		//redirectionUrl="model!list.action";
		//return SUCCESS;
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}	

	public String ajlist() {
		System.out.println("i");
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
			if (obj.get("repairName") != null) {
				String repairName = obj.getString("repairName").toString();
				map.put("repairName", repairName);
			}

			if (obj.get("equipmentName") != null) {
				String equipmentName = obj.getString("equipmentName").toString();
				map.put("equipmentName", equipmentName);
			}

		}

		if(StringUtils.isNotEmpty(abnorId) && !abnorId.equalsIgnoreCase("")){
			pager = modelService.findByPager(pager,map,abnorId);	
		}else{
			pager = modelService.getModelPager(pager, map,admin.getId(),admin.getDepartment().getTeam().getId());	
		}
		

		List pagerlist = pager.getList();
		System.out.println(pagerlist.size());
		
		String str1;
		for (int i = 0; i < pagerlist.size(); i++) {
			Model model = (Model) pagerlist.get(i);
			str1="<a href='model!view.action?id="+model.getId()+"'>"+model.getEquipments().getEquipmentName()+"</a>"; 
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
		System.out.println("ii");
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Model.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 保存
	public String creditsave1() {

		Admin admin = adminService.getLoginAdmin();

		abnormal = abnormalService.load(abnormalId);
		model.setAbnormal(abnormal);
		
		if(model.getInsepector()==null){
			addActionError("检验员不允许为空！");
			return ERROR;
		}
		
		if(model.getFixer()==null){
			addActionError("维修员不允许为空！");
			return ERROR;
		}
		
		if(model.getEquipments()==null){
			addActionError("设备名称不允许为空！");
			return ERROR;
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
		modelService.save(model);
		
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
		
		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
		//return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	// 列表
	public String sealist() {
		//pager = modelService.findByPager(pager,abnorId);	
		abnormalId=abnorId;
		return "hlist";
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

	
	
}
