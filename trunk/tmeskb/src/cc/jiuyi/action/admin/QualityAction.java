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
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.QualityProblemDescription;
import cc.jiuyi.entity.UnusualLog;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FlowingRectifyService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.QualityProblemDescriptionService;
import cc.jiuyi.service.QualityService;
import cc.jiuyi.service.UnusualLogService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * @author ALF
 *
 */
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
	private String process;//工序
	private String product;
	private String bomproduct; 
	private String bomId;
	
	private String materialCode;
	
	private List<Quality>  qualityList;
	private List<Model> modelList;
	private List<Craft> craftList;
	private List<Device> deviceList;
	private List<Department> list;
	private List<FlowingRectify> flowingRectifys;
	private List<Dict> processList;//工序列表
	private List<QualityProblemDescription> problemDescriptionList;//问题描述列表
	private List<Dict> stateList;//状态列表
	
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
	@Resource
	private ProcessService processService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private ProductsService productsService;
	@Resource
	private OrdersService ordersService;
	@Resource
	private BomService bomService;
	@Resource
	private QualityProblemDescriptionService qualityProblemDescriptionService;
	
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
		/*Process pro = processService.get(quality.getProcess());
		process=pro.getProcessName();*/
		if(quality.getBom()!=null && !quality.getBom().equalsIgnoreCase("")){
			bomproduct=bomService.getMaterialName(quality.getBom());
		}
		if(quality.getProducts()!=null && !quality.getProducts().equalsIgnoreCase("")){
			Products products=productsService.get("productsCode",quality.getProducts());
			product = products.getProductsName();
		}
		if(quality.getProcess()!=null && !quality.getProcess().equals(""))
		quality.setProcessName(ThinkWayUtil.getDictValueByDictKey(dictService, "process", quality.getProcess()));
		
		abnormal=quality.getAbnormal();
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

	public String ajlist() {

		Admin admin1 = adminService.getLoginAdmin();
		admin1 = adminService.get(admin1.getId());
		
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
			
		   if (obj.get("founder") != null) { 
			   String founder = obj.getString("founder").toString();
			   map.put("founder", founder);
			}

		   if (obj.get("process") != null) { 
			   String process = obj.getString("process").toString();
			   map.put("process", process);
		   }

		}
		
		if(StringUtils.isNotEmpty(abnorId) && !abnorId.equalsIgnoreCase("")){//日志链接页面
			pager = qualityService.findByPager(pager,map,abnorId);	
			List pagerlist = pager.getList();
			String str;
			for (int i = 0; i < pagerlist.size(); i++) {
				Quality quality = (Quality) pagerlist.get(i);
				if(quality.getProducts()==null){
					String product=bomService.getMaterialName(quality.getBom());
					str="<a href='quality!hview.action?id="+quality.getId()+"'>"+product+"</a>"; 
				}else{	
					Products products=productsService.get("productsCode",quality.getProducts());
					String product = products.getProductsName();
					str="<a href='quality!hview.action?id="+quality.getId()+"'>"+product+"</a>"; 
				}
				
				quality.setProductsName(str);
				quality.setFounder(quality.getCreater().getName());
				/*Process process = processService.get(quality.getProcess());
				quality.setProcessName(process.getProcessName());*/
				quality.setTeamName(quality.getTeam().getTeamName());
				quality.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", quality.getState()));	
				if(quality.getProcess()!=null && !quality.getProcess().equals(""))
				quality.setProcessName(ThinkWayUtil.getDictValueByDictKey(
						dictService, "process", quality.getProcess()));
				String str0="";
				if(quality.getQualityProblemDescription()!=null && !quality.getQualityProblemDescription().equals(""))
				{
					List<QualityProblemDescription> qList= qualityProblemDescriptionService.get(quality.getQualityProblemDescription().split(", "));
					
					for(QualityProblemDescription q:qList)
					{
						str0+=q.getProblemDescription()+", ";
					}
				}
				quality.setProblemDescriptionName(str0);
				pagerlist.set(i,quality);
			}
			pager.setList(pagerlist);
		}else if(StringUtils.isNotEmpty(abnormalId) && !abnormalId.equalsIgnoreCase("")){
			pager = qualityService.findByPager(pager, map,abnormalId);		
			List pagerlist = pager.getList();
			for (int i = 0; i < pagerlist.size(); i++) {
				Quality quality = (Quality) pagerlist.get(i);
				if(quality.getProducts()==null){
					String product=bomService.getMaterialName(quality.getBom());
					quality.setProductsName(product);
				}else{
					Products products=productsService.get("productsCode",quality.getProducts());
					String product = products.getProductsName();
					quality.setProductsName(product);
				}
				
				quality.setFounder(quality.getCreater().getName());				
				quality.setTeamName(quality.getTeam().getTeamName());
				quality.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", quality.getState()));
				if(quality.getProcess()!=null && !quality.getProcess().equals(""))
				quality.setProcessName(ThinkWayUtil.getDictValueByDictKey(
						dictService, "process", quality.getProcess()));
				String str="";
				if(quality.getQualityProblemDescription()!=null && !quality.getQualityProblemDescription().equals(""))
				{
					List<QualityProblemDescription> qList= qualityProblemDescriptionService.get(quality.getQualityProblemDescription().split(", "));
					
					for(QualityProblemDescription q:qList)
					{
						str+=q.getProblemDescription()+", ";
					}
				}
				quality.setProblemDescriptionName(str);
				pagerlist.set(i,quality);
			}
			pager.setList(pagerlist);
		}else{//普通清单页面
			pager = qualityService.getQualityPager(pager, map,admin1.getId(),admin1.getTeam().getId());		
			List pagerlist = pager.getList();
			for (int i = 0; i < pagerlist.size(); i++) {
				Quality quality = (Quality) pagerlist.get(i);
				if(quality.getProducts()==null){
					String product=bomService.getMaterialName(quality.getBom());
					quality.setProductsName(product);
				}else{
					Products products=productsService.get("productsCode",quality.getProducts());
					String product = products.getProductsName();
					quality.setProductsName(product);
				}
				
				quality.setFounder(quality.getCreater().getName());				
				quality.setTeamName(quality.getTeam().getTeamName());
				quality.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "receiptState", quality.getState()));	
				if(quality.getProcess()!=null && !quality.getProcess().equals(""))
				{	
					String str=getDictValueByDictKey(quality.getProcess(),"process");				
					quality.setProcessName(str==null ? "":str);
				}
				String str1="";
				if(quality.getQualityProblemDescription()!=null && !quality.getQualityProblemDescription().equals(""))
				{
					List<QualityProblemDescription> qList= qualityProblemDescriptionService.get(quality.getQualityProblemDescription().split(", "));
					
					for(QualityProblemDescription q:qList)
					{
						str1+=q.getProblemDescription()+", ";
					}
				}
				quality.setProblemDescriptionName(str1);
				
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
		if(quality.getBom()!=null && !quality.getBom().equalsIgnoreCase("")){
			bomproduct=bomService.getMaterialName(quality.getBom());
		}
		if(quality.getProducts()!=null && !quality.getProducts().equalsIgnoreCase("")){
			Products products=productsService.get("productsCode",quality.getProducts());
			product = products.getProductsName();
		}
		if(quality.getProcess()!=null && !quality.getProcess().equals(""))
		quality.setProcessName(ThinkWayUtil.getDictValueByDictKey(
				dictService, "process", quality.getProcess()));
		String str="";
		if(quality.getQualityProblemDescription()!=null && !quality.getQualityProblemDescription().equals(""))
		{
			List<QualityProblemDescription> qList= qualityProblemDescriptionService.get(quality.getQualityProblemDescription().split(", "));
			
			for(QualityProblemDescription q:qList)
			{
				str+=q.getProblemDescription()+", ";
			}
		}
		quality.setProblemDescriptionName(str);
		return "hview";
	}

	// 刷卡提交	
	public String creditsave() {		
		admin = adminService.getByCardnum(cardnumber);
		abnormal = abnormalService.load(abnormalId);
		
		
		/*if(quality.getProducts()==null){
			return ajaxJsonErrorMessage("产品名称不允许为空!");
		}
		
		if(quality.getProcess()==null){
			return ajaxJsonErrorMessage("工序名称不允许为空!");
		}*/
		
		if(quality.getProductDate()==null){
			quality.setProductDate(admin.getProductDate());
		}	
		if(quality.getShift()==null || quality.getShift().equals("") ){
			quality.setShift(admin.getShift());
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
		abnormalLog.setType("0");//质量问题单"0"
		abnormalLog.setOperator(admin);
		abnormalLogService.save(abnormalLog);

		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	public String creditupdate() {
		admin = adminService.getByCardnum(cardnumber);		
		Quality persistent = qualityService.load(id);
		if(admin!=persistent.getCreater()){
			return ajaxJsonErrorMessage("您不是单据创建人，无权提交该单据!");
		}
		if(persistent.getState().equals("3") || persistent.getState().equals("1")){
			return ajaxJsonErrorMessage("已关闭/回复的单据无法再提交!");
		}
		BeanUtils.copyProperties(quality, persistent, new String[] { "id","createDate", "modifyDate","abnormal","isDel","state","products","creater","process","team","receiver","engineer","engineerOpinion","bom","productDate","shift"});		
		persistent.setEngineer(quality.getEngineer());
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
		/*if(persistent.getState().equals("1")){
			return ajaxJsonErrorMessage("单据已回复!");
		}*/
		
		if(quality.getRectificationScheme()==null || quality.getRectificationScheme().equalsIgnoreCase("")){
			return ajaxJsonErrorMessage("车间整改方案不允许为空!");
		}
		BeanUtils.copyProperties(quality, persistent, new String[] { "id","createDate","abnormal", "modifyDate","isDel","products","creater","process","team","receiver","extrusionBatches","samplingAmont","failAmont","overTime","problemDescription","engineer","engineerOpinion","bom"});
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
		if(persistent.getEngineer()!=admin){
			return ajaxJsonErrorMessage("您不是指定工程师,无法关闭该单据!");
		}
		
		if(quality.getEngineerOpinion()==null || quality.getEngineerOpinion().equalsIgnoreCase("")){
			return ajaxJsonErrorMessage("工程师意见不允许为空!");
		}
		
		if(persistent.getState().equals("1")){
			BeanUtils.copyProperties(quality, persistent, new String[] {"id","createDate", "modifyDate","createUser","abnormal","modifyUser","isDel","products","creater","process","team","receiver","samplingAmont","failAmont","extrusionBatches","problemDescription","rectificationScheme","overTime","engineer","bom"});
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
		bomId=bomId;
		return "browser";
	}
	
	public String receive(){
		list = deptservice.getAllByHql();
		return "receive";
	}
	
	public String engineer(){
		list = deptservice.getAllByHql();
		return "engineer";
	}
	

	public String view() {
		quality = qualityService.load(id);
		if(quality.getBom()!=null && !quality.getBom().equalsIgnoreCase("")){
			bomproduct=bomService.getMaterialName(quality.getBom());
		}
		if(quality.getProducts()!=null && !quality.getProducts().equalsIgnoreCase("")){
			Products products=productsService.get("productsCode",quality.getProducts());
			product = products.getProductsName();
		}
		if(quality.getProcess()!=null && !quality.getProcess().equals(""))
		quality.setProcessName(ThinkWayUtil.getDictValueByDictKey(
				dictService, "process", quality.getProcess()));
		String str="";
		if(quality.getQualityProblemDescription()!=null && !quality.getQualityProblemDescription().equals(""))
		{
			List<QualityProblemDescription> qList= qualityProblemDescriptionService.get(quality.getQualityProblemDescription().split(", "));
			
			for(QualityProblemDescription q:qList)
			{
				str+=q.getProblemDescription()+", ";
			}
		}
		quality.setProblemDescriptionName(str);
		
		
		
		
		abnormal=quality.getAbnormal();
		/*Process pro = processService.get(quality.getProcess());
		process=pro.getProcessName();*/
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
	
	public List<WorkingBill> getWorkList(){
		Admin admin1 = adminService.getLoginAdmin();
		admin1 = adminService.get(admin1.getId());
		List list=workingBillService.getListWorkingBillByDate(admin1);
		return list;
	}
	
	public List<Bom> getBomList(){
		List<WorkingBill> workList=getWorkList();
		List<Bom> bomList = new ArrayList<Bom>();
		for(WorkingBill work:workList){
			Orders order = ordersService.get("aufnr",work.getAufnr());
			bomList.addAll(new ArrayList<Bom>(order.getBomSet()));
		}
		return bomList;
	}
	
	/*public List<Products> getProductsList(){
		List<WorkingBill> workList=getWor();
		List<Products> productsList= new ArrayList<Products>();
		for(WorkingBill work:workList){
			Products products = productsService.getProducts(work.getMatnr());
			productsList.add(products);
		}
		return productsList;
	}
	*/

	public String getDictValueByDictKey(String key,String dictName)
	{
		if(key==null || key.equals("") || dictName==null || dictName.equals(""))
		return "";
		
		return ThinkWayUtil.getDictValueByDictKey(dictService, dictName, key);
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

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getBomproduct() {
		return bomproduct;
	}

	public void setBomproduct(String bomproduct) {
		this.bomproduct = bomproduct;
	}

	public String getBomId() {
		return bomId;
	}

	public void setBomId(String bomId) {
		this.bomId = bomId;
	}

	public List<Dict> getProcessList() {
		return dictService.getList("dictname", "process");
	}

	public void setProcessList(List<Dict> processList) {
		this.processList = processList;
	}

	

	public List<QualityProblemDescription> getProblemDescriptionList() {
		return qualityProblemDescriptionService.getAllQualityProblemDescription();
	}

	public void setProblemDescriptionList(
			List<QualityProblemDescription> problemDescriptionList) {
		this.problemDescriptionList = problemDescriptionList;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public List<Dict> getStateList() {
		return dictService.getList("dictname", "qualityProblemDescriptionState");
	}

	public void setStateList(List<Dict> stateList) {
		this.stateList = stateList;
	}

	
    
	
	
	
}
