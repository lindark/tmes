package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.UnusualLog;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FlowingRectifyService;
import cc.jiuyi.service.QualityService;
import cc.jiuyi.service.UnusualLogService;
import cc.jiuyi.util.ThinkWayUtil;

@ParentPackage("admin")
public class QualityAction extends BaseAdminAction {

	private static final long serialVersionUID = -3242463207248131962L;

	private Quality quality;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	private String loginUsername;
	private Admin admin;
	private String abnorId;
	private String cardnumber;//刷卡卡号
	
	private List<Quality>  qualityList;
	private List<Model> modelList;
	private List<Craft> craftList;
	private List<Device> deviceList;
	private List<Department> list;
	private List<FlowingRectify> flowingRectifys;
	
	
	@Resource
	private QualityService qualityService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private FlowingRectifyService flowingRectifyService;
	@Resource
	private UnusualLogService unusualLogService;
	@Resource
	private AdminService adminService;
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
		quality = qualityService.load(id);
		abnormal=quality.getAbnormal();
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

	public String ajlist() {

		Admin admin1 = adminService.getLoginAdmin();
		admin1 = adminService.get(admin1.getId());
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		/*if (pager == null) {
			pager = new Pager();
			pager.setOrderBy("modifyDate");
		}*/
		
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
			
		   if (obj.get("founder") != null) { 
			   String founder = obj.getString("founder").toString();
			   map.put("founder", founder);
			}

		   if (obj.get("productName") != null) { 
			   String productName = obj.getString("productName").toString();
			   map.put("productName", productName);
		   }

		}
		
		if(StringUtils.isNotEmpty(abnorId) && !abnorId.equalsIgnoreCase("")){//日志链接页面
			pager = qualityService.findByPager(pager,map,abnorId);	
			List pagerlist = pager.getList();
			String str;
			for (int i = 0; i < pagerlist.size(); i++) {
				Quality quality = (Quality) pagerlist.get(i);
				str="<a href='quality!hview.action?id="+quality.getId()+"'>"+quality.getProducts().getProductsName()+"</a>"; 
				quality.setProductsName(str);
				quality.setFounder(quality.getCreater().getName());
				quality.setProcessName(quality.getProcess().getProcessName());
				quality.setTeamName(quality.getTeam().getTeamName());
				quality.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", quality.getState()));		
				pagerlist.set(i,quality);
			}
			pager.setList(pagerlist);
		}else{//普通清单页面
			pager = qualityService.getQualityPager(pager, map,admin1.getId(),admin1.getDepartment().getTeam().getId());		
			List pagerlist = pager.getList();
			for (int i = 0; i < pagerlist.size(); i++) {
				Quality quality = (Quality) pagerlist.get(i);
				quality.setProductsName(quality.getProducts().getProductsName());
				quality.setFounder(quality.getCreater().getName());
				quality.setProcessName(quality.getProcess().getProcessName());
				quality.setTeamName(quality.getTeam().getTeamName());
				quality.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", quality.getState()));		
				pagerlist.set(i,quality);
			}
			pager.setList(pagerlist);
		}	

		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Quality.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	// 列表
	public String sealist() {	
		abnormalId=abnorId;
		return "hlist";
	}
	
	//详情
	public String hview(){
		quality = qualityService.load(id);
		return "hview";
	}

	// 刷卡提交	
	public String creditsave() {		
		admin = adminService.getByCardnum(cardnumber);
		abnormal = abnormalService.load(abnormalId);
		
		
		if(quality.getProducts()==null){
			return ajaxJsonErrorMessage("产品名称不允许为空!");
		}
		
		if(quality.getProcess()==null){
			return ajaxJsonErrorMessage("工序名称不允许为空!");
		}
		quality.setAbnormal(abnormal);
		quality.setCreater(admin);
		quality.setIsDel("N");
		quality.setState("0");
		qualityService.save(quality);
         
		UnusualLog log = new UnusualLog();
		log.setOperator(admin);
		log.setInfo("已提交");
		log.setQuality(quality);
		unusualLogService.save(log);
		
		AbnormalLog abnormalLog = new AbnormalLog();
		abnormalLog.setAbnormal(abnormal);
		abnormalLog.setType("0");
		abnormalLog.setOperator(admin);
		abnormalLogService.save(abnormalLog);

		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	public String creditupdate() {
		Quality persistent = qualityService.load(id);
		BeanUtils.copyProperties(quality, persistent, new String[] { "id","createDate", "modifyDate","abnormal","isDel","state","products","creater","process","team","receiver"});		
		qualityService.update(persistent);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	
	//刷卡回复
	public String creditreply() throws Exception{
		admin = adminService.getByCardnum(cardnumber);
		
		Quality persistent = qualityService.load(id);
		if(persistent.getReceiver()!=admin){
			return ajaxJsonErrorMessage("您不是单据接收人!");
		}
		if(persistent.getState().equals("3")){		
			return ajaxJsonErrorMessage("已关闭的单据无法再回复!");
		}
		if(persistent.getState().equals("1")){
			return ajaxJsonErrorMessage("单据已回复!");
		}
		
		if(quality.getRectificationScheme()==null){
			return ajaxJsonErrorMessage("车间整改方案不允许为空!");
		}
		BeanUtils.copyProperties(quality, persistent, new String[] { "id","createDate", "modifyDate","abnormal","isDel","products","creater","process","team","receiver","extrusionBatches","samplingAmont","failAmont","overTime","problemDescription"});
		persistent.setState("1");
		qualityService.update(persistent);
		
		UnusualLog log = new UnusualLog();
		log.setOperator(admin);
		log.setInfo("已回复");
		log.setQuality(persistent);
		unusualLogService.save(log);
		
			return ajaxJsonSuccessMessage("您的操作已成功!");
	}	
		
	//刷卡关闭
	public String creditclose() throws Exception{
		admin = adminService.getByCardnum(cardnumber);
		
		Quality persistent = qualityService.load(id);
		if(persistent.getCreater()!=admin){
			return ajaxJsonErrorMessage("您不是单据创建人,无法关闭该单据!");
		}
		if(persistent.getState().equals("1")){
			BeanUtils.copyProperties(quality, persistent, new String[] {"id","createDate", "modifyDate","abnormal","createUser","modifyUser","isDel","products","creater","process","team","receiver","samplingAmont","failAmont","extrusionBatches","problemDescription","rectificationScheme","overTime"});
			persistent.setState("3");
			qualityService.update(persistent);
			
			UnusualLog log = new UnusualLog();
			log.setOperator(admin);
			log.setInfo("已关闭");
			log.setQuality(persistent);
			unusualLogService.save(log);
		}else{
			return ajaxJsonErrorMessage("单据已关闭/未回复!");
		}
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}			
	
	public String browser(){
		return "browser";
	}
	
	public String receive(){
		list = deptservice.getAllByHql();
		return "receive";
	}
	

	public String view() {
		quality = qualityService.load(id);
		abnormal=quality.getAbnormal();
		qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
		modelList=new ArrayList<Model>(abnormal.getModelSet());
		craftList=new ArrayList<Craft>(abnormal.getCraftSet());
		deviceList=new ArrayList<Device>(abnormal.getDeviceSet());
		return VIEW;
	}

	// 删除
	public String delete() throws Exception {
		ids = id.split(",");
		qualityService.updateisdel(ids, "Y");
		redirectionUrl = "quality!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
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

	public List<FlowingRectify> getFlowingRectifys() {
		return flowingRectifys;
	}

	public void setFlowingRectifys(List<FlowingRectify> flowingRectifys) {
		this.flowingRectifys = flowingRectifys;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
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

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	
}
