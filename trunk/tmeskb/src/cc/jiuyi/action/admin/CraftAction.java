package cc.jiuyi.action.admin;

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
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.ReceiptReason;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CraftLogService;
import cc.jiuyi.service.CraftService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReceiptReasonService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 工艺维修单
 */

@ParentPackage("admin")
public class CraftAction extends BaseAdminAction {

	private static final long serialVersionUID = -2383463207248343967L;
	
	private Craft craft;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	private String loginUsername;
	private Admin admin;
	private String[] reasonIds;
	
	private List<Quality>  qualityList;
	private List<Model> modelList;
	private List<Craft> craftList;
	private List<Device> deviceList;
	private List<ReceiptReason> reasonList;
	
	@Resource
	private CraftService craftService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;
	@Resource
	private CraftLogService craftLogService;
	@Resource
	private AbnormalLogService abnormalLogService;
	@Resource
	private ReceiptReasonService receiptReasonService;
	
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
		craft = craftService.load(id);
		abnormal=craft.getAbnormal();
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
	
	public String browser(){
		return "browser";
	}
	
	public String repair(){
		return "repair";
	}
    
	@InputConfig(resultName = "error")
	public String update() {
		Craft persistent = craftService.load(id);
		BeanUtils.copyProperties(craft, persistent, new String[] { "id", "team","abnormal","isDel","state","products","creater","repairName","receiptReasonSet"});
		craftService.update(persistent);
		redirectionUrl = "craft!list.action";
		return SUCCESS;
	}
	
	//刷卡回复
	public String check() throws Exception{
		Admin admin = adminService.getLoginAdmin();
		Craft persistent = craftService.load(id);
		if(persistent.getState().equals("3")){
			addActionError("已关闭的单据无法再回复！");
			return ERROR;
		}
		if(persistent.getState().equals("1")){
			addActionError("单据已回复！");
			return ERROR;
		}
		BeanUtils.copyProperties(craft, persistent, new String[] { "id", "team","abnormal","isDel","products","creater","repairName","receiptReasonSet"});
		persistent.setState("1");
		craftService.update(persistent);
		
		CraftLog log = new CraftLog();
		log.setOperator(admin);
		log.setInfo("已回复");
		log.setCraft(persistent);
		craftLogService.save(log);
		
		redirectionUrl="craft!list.action";
		return SUCCESS;
	}
	
	//刷卡关闭
	public String close() throws Exception{
		Admin admin = adminService.getLoginAdmin();
		Craft persistent = craftService.load(id);
		if(persistent.getState().equals("1")){
			BeanUtils.copyProperties(craft, persistent, new String[] { "id", "team","abnormal","isDel","products","creater","repairName","receiptReasonSet"});
			persistent.setState("3");
			craftService.update(persistent);
						
			CraftLog log = new CraftLog();
			log.setOperator(admin);
			log.setInfo("已关闭");
			log.setCraft(persistent);
			craftLogService.save(log);
			
		}else{
			addActionError("单据已关闭/未回复！");
			return ERROR;
		}
		
		redirectionUrl="craft!list.action";
		return SUCCESS;
	}
	
    public String ajlist(){
    	Admin admin = adminService.getLoginAdmin();
		HashMap<String, String> map = new HashMap<String, String>();
		
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}

		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
	
			if (obj.get("team") != null) {
				String team = obj.getString("team").toString();
				map.put("team", team);
			}
			
			if (obj.get("productName") != null) {
				String productName = obj.getString("productName").toString();
				map.put("productName", productName);
			}
			
		}

		pager = craftService.getCraftPager(pager, map,admin.getId());
		
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			Craft craft  = (Craft)pagerlist.get(i);
			craft.setAbnormal(null);
			craft.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "receiptState", craft.getState()));
			craft.setCabinetName(ThinkWayUtil.getDictValueByDictKey(
					dictService, "machineNo", craft.getCabinetCode()));
			craft.setCraftLogSet(null);
			craft.setProductsName(craft.getProducts().getProductsName());
			craft.setTeamName(craft.getTeam().getTeamName());
			pagerlist.set(i, craft);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Craft.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	// 保存
	@Validations(requiredStrings = {
			@RequiredStringValidator(fieldName = "craft.products.id", message = "产品名称不允许为空!")
			 })
	@InputConfig(resultName = "error")
	public String save() {	
		Admin admin = adminService.getLoginAdmin();
		
		abnormal=abnormalService.load(abnormalId);
		craft.setAbnormal(abnormal);
		if (reasonIds != null && reasonIds.length > 0) {
			Set<ReceiptReason> reasonSet = new HashSet<ReceiptReason>(receiptReasonService.get(reasonIds));
			craft.setReceiptReasonSet(reasonSet);
		} else {
			craft.setReceiptReasonSet(null);
		}
		craft.setState("0");
		craft.setIsDel("N");
		craft.setCreater(admin);
		craftService.save(craft);	
		
		CraftLog log = new CraftLog();
		log.setOperator(admin);
		log.setInfo("已提交");
		log.setCraft(craft);
		craftLogService.save(log);
				
		AbnormalLog abnormalLog = new AbnormalLog();
		abnormalLog.setAbnormal(abnormal);
		abnormalLog.setInfo("已开工艺维修单");
		abnormalLog.setOperator(admin);
		abnormalLogService.save(abnormalLog);
		
		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
	}
		
	// 删除
	public String delete() throws Exception {
		ids=id.split(",");
		craftService.updateisdel(ids, "Y");
		//craftService.delete(ids);
		redirectionUrl = "craft!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	public String view() {
		craft = craftService.load(id);
		abnormal=craft.getAbnormal();
		qualityList=new ArrayList<Quality>(abnormal.getQualitySet());
		modelList=new ArrayList<Model>(abnormal.getModelSet());
		craftList=new ArrayList<Craft>(abnormal.getCraftSet());
		deviceList=new ArrayList<Device>(abnormal.getDeviceSet());
		return VIEW;
	}

	public Craft getCraft() {
		return craft;
	}

	public void setCraft(Craft craft) {
		this.craft = craft;
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}
	
	// 获取所有机台号
	public List<Dict> getAllCode() {
		return dictService.getList("dictname", "machineNo");
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

	public String[] getReasonIds() {
		return reasonIds;
	}

	public void setReasonIds(String[] reasonIds) {
		this.reasonIds = reasonIds;
	}

	public List<ReceiptReason> getReasonList() {
		reasonList=receiptReasonService.getReceiptReasonByType("0");
		return reasonList;
	}

	public void setReasonList(List<ReceiptReason> reasonList) {
		this.reasonList = reasonList;
	}
	
	
}
