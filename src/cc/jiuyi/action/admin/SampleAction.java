package cc.jiuyi.action.admin;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.entity.SampleRecord;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.SampleRecordService;
import cc.jiuyi.service.SampleService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 抽检
 * @author gaoyf
 *
 */
@ParentPackage("admin")
public class SampleAction extends BaseAdminAction
{
	private static final long serialVersionUID = 7706687725550333117L;
	
	/**========================variable,object,interface  start========================*/
	/**
	 * 对象或变量
	 */
	private Sample sample;//抽检
	private String wbId;//随工单id
	private WorkingBill workingbill;//随工单
	private String my_id;//自定义ID
	private List<Cause> list_cause;//缺陷
	private List<Dict> list_dict;//抽检类型
	private String info;
	private String info2;
	private Admin admin;
	private String add;
	private String edit;
	private String show;
	private List<SampleRecord>list_samrecord;//缺陷记录
	private String sampletype;//抽检类型
	private String cardnumber;//刷卡人卡号
	private String maktx;//物料编码
	private String matnr;//物料名称
	private String state;//状态
	private String start;//日期起始点
	private String end;//日期结束点
	private String xsampler;//抽检人
	private String xcomfirmation;//确认人
	private String xproductname;//产品名称
	private String xproductnum;//产品编号
	/**
	 * service接口
	 */
	@Resource
	private SampleService sampleService;//抽检
	@Resource
	private WorkingBillService workingBillService;//随工单
	@Resource
	private DictService dictService;//字典表
	@Resource
	private CauseService causeService;//缺陷表
	@Resource
	private AdminService adminService;
	@Resource
	private SampleRecordService srService;//缺陷记录
	@Resource
	private UnitdistributeModelService unitdistributeModelService;
	@Resource
	private UnitdistributeProductService unitdistributeProductService;
	@Resource
	private FactoryUnitService factoryUnitService;
	
	
	
	/**========================end  variable,object,interface==========================*/
	
	/**========================method  start======================================*/
	
	/**
	 * 查询选择的那条随工单
	 */
	public String list()
	{
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		this.workingbill=this.workingBillService.get(wbId);
		return LIST;
	}
	
	public String getMaktx() {
		return maktx;
	}

	public void setMaktx(String maktx) {
		this.maktx = maktx;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getXsampler() {
		return xsampler;
	}

	public void setXsampler(String xsampler) {
		this.xsampler = xsampler;
	}

	public String getXcomfirmation() {
		return xcomfirmation;
	}

	public void setXcomfirmation(String xcomfirmation) {
		this.xcomfirmation = xcomfirmation;
	}

	public SampleService getSampleService() {
		return sampleService;
	}

	public void setSampleService(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}

	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public CauseService getCauseService() {
		return causeService;
	}

	public void setCauseService(CauseService causeService) {
		this.causeService = causeService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public SampleRecordService getSrService() {
		return srService;
	}

	public void setSrService(SampleRecordService srService) {
		this.srService = srService;
	}

	public String history(){
		return "history";
	}
	
	/**
	 * Excel导出 @author Razey 2016/3/23
	 * 
	 * @return
	 */
	public String excelexport(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("xproductname", xproductname);
		map.put("xproductnum", xproductnum);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);
		map.put("xsampler",xsampler);
		map.put("xcomfirmation", xcomfirmation);
		
		
		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		header.add("单元");
		header.add("班组名称");
		header.add("部长");
		header.add("主任");
		header.add("副主任");
		header.add("生产日期");
		header.add("物料描述");
		header.add("抽检数量");
		header.add("合格数量");
        header.add("合格率");
        //header.add("缺陷明细");
        this.list_cause=this.causeService.getBySample("1");//获取缺陷表中关于抽检的缺陷内容
//        List<String> casetiopList = new ArrayList<String>();
        for(Cause cause : list_cause){
        	header.add(cause.getCauseName());
//        	casetiopList.add(cause.getCauseName());
        }
        header.add("抽检人");
        header.add("确认人");
        header.add("状态");
        header.add("日期");
        header.add("产品编号");
        header.add("抽检类型");
//        header.add("状态");
        
        int leng = list_cause.size()+1;
        List<Object[]> sampleList = sampleService.historyExcelExport(map);
        for (int i = 0; i < sampleList.size(); i++) {
			Object[] obj = sampleList.get(i);
			Sample sample = (Sample) obj[0];//oddHandOver
			Set<SampleRecord> sapmpleRecordSet = sample.getSapmpleRecordSet();
        	WorkingBill workingbill = (WorkingBill)obj[1];//workingbill
        	String factoryCode;
			if(workingbill.getTeam()!=null&&workingbill.getTeam().getFactoryUnit()!=null){
				factoryCode = workingbill.getTeam().getFactoryUnit().getFactoryUnitCode();
			}else{
				factoryCode = "";
			}
			String teamName;
			if(workingbill.getTeam()!=null){
				teamName = workingbill.getTeam().getTeamName();
			}else{
				teamName = "";
			}
//        	String defect = "";
//        	for(SampleRecord sampleRecord:sapmpleRecordSet){
//        		defect = defect+sampleRecord.getRecordDescription()+";";
//        	}
			Object[] bodyval = new Object[header.size()];
			bodyval[0] = factoryCode;//单元
			bodyval[1] = teamName;
			bodyval[2] = workingbill.getMinister();
			bodyval[3] = workingbill.getZhuren();
			bodyval[4] = workingbill.getFuzhuren();
			bodyval[5] = workingbill.getProductDate();
			bodyval[6] = workingbill.getMaktx()==null?"":workingbill.getMaktx();
			bodyval[7] = sample.getSampleNum()==null?"":sample.getSampleNum();
			bodyval[8] = sample.getQulified()==null?"":sample.getQulified();
			bodyval[9] = sample.getQulifiedRate()==null?"":sample.getQulifiedRate();
			for(SampleRecord sampleRecord:sapmpleRecordSet){
				if(header.contains(sampleRecord.getRecordDescription())){
					int num1 = header.indexOf(sampleRecord.getRecordDescription());
					bodyval[num1]=sampleRecord.getRecordNum();
				}
        	}
			bodyval[9+leng] = sample.getSampler()==null?"":sample.getSampler().getName();
			bodyval[10+leng] = sample.getComfirmation()==null?"":sample.getComfirmation().getName();
			bodyval[11+leng] = ThinkWayUtil.getDictValueByDictKey(dictService,"sampleState", sample.getState());
			bodyval[12+leng] = sample.getModifyDate()==null?"":sample.getModifyDate();
			bodyval[13+leng] = workingbill.getMatnr()==null?"":workingbill.getMatnr();
			bodyval[14+leng] = sample.getSampleType()==null?"":ThinkWayUtil.getDictValueByDictKey(dictService,"sampleType", sample.getSampleType());
					
//			Object[] bodyval = {
//					factoryName,//单元
//					teamName,//班组名称
//					workingbill.getMinister(),//部长
//					workingbill.getZhuren(),//主任
//					workingbill.getFuzhuren(),//副主任
//					workingbill.getProductDate(),//生产日期
//					workingbill.getMaktx()==null?"":workingbill.getMaktx(),//产品名称
//					sample.getSampleNum()==null?"":sample.getSampleNum(),//抽检数量
//					sample.getQulified()==null?"":sample.getQulified(),//合格数量
//					sample.getQulifiedRate()==null?"":sample.getQulifiedRate(),//合格率
//					defect,
//					sample.getSampler()==null?"":sample.getSampler().getName(),
//					sample.getComfirmation()==null?"":sample.getComfirmation().getName(),
//					ThinkWayUtil.getDictValueByDictKey(dictService,"sampleState", sample.getState()),
//					sample.getModifyDate()==null?"":sample.getModifyDate(),
//					workingbill.getMatnr()==null?"":workingbill.getMatnr(),
//					sample.getSampleType()==null?"":sample.getSampleType(),
////					sample.getState()==null?"":sample.getState(),
//					};
			body.add(bodyval);
		}

		try {
			String fileName = "抽检记录表" + ".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("抽检记录表", header, body, getResponse()
					.getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public String getXproductname() {
		return xproductname;
	}

	public void setXproductname(String xproductname) {
		this.xproductname = xproductname;
	}

	public String getXproductnum() {
		return xproductnum;
	}

	public void setXproductnum(String xproductnum) {
		this.xproductnum = xproductnum;
	}

	/**
	 * 抽检表记录 @author Razey 2016/3/23
	 * 
	 * @return
	 */
	public String historylist(){
		
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		try
		{
		HashMap<String, String> map = new HashMap<String, String>();
		
//		if(pager == null) {
//			pager = new Pager();
//			
//		}
//		pager.setOrderType(OrderType.desc);
//		pager.setOrderBy("createDate");
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
//			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("xproductname") != null) {
				String xproductname = obj.getString("xproductname")
						.toString();
				map.put("xproductname", xproductname);
			}
			if (obj.get("xproductnum") != null) {
				String xproductnum = obj.getString("xproductnum")
						.toString();
				map.put("xproductnum", xproductnum);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
			if (obj.get("xsampler") != null) {
				String xsampler = obj.getString("xsampler").toString();
				map.put("xsampler", xsampler);
			}
			if (obj.get("xcomfirmation") != null) {
				String xcomfirmation = obj.getString("xcomfirmation").toString();
				map.put("xcomfirmation", xcomfirmation);
			}
			
//
//		//{autoCode_Modify}
		}

		pager = sampleService.getSamplePager(pager, map);
		@SuppressWarnings("unchecked")
		List<Sample>samplelist=pager.getList();
		List<Sample>samplelist2=new ArrayList<Sample>();
		for(int i=0;i<samplelist.size();i++)
		{
			Sample s1=samplelist.get(i);
			s1.setXproductnum(s1.getWorkingBill().getMatnr());//产品编号xproductnum
			s1.setXproductname(s1.getWorkingBill().getMaktx());//产品名称xproductnames
			s1.setState(ThinkWayUtil.getDictValueByDictKey(dictService, "sampleState", s1.getState()));//状态描述--页面显示
			s1.setModifyDate(s1.getModifyDate());
			s1.setXsampler(s1.getSampler().getName());//抽检人xsampler
			if(s1.getComfirmation()!=null)
			{
				s1.setXcomfirmation(s1.getComfirmation().getName());//确认人xcomfirmation
			}
			//System.out.println(s1.getCreateDate());
//			s1.setXproductnum(s1.getWorkingBill().getMatnr());//产品编号xproductnum
//			s1.setXproductname(s1.getWorkingBill().getMaktx());//产品名称xproductnames
////			s1.setXsampletype(ThinkWayUtil.getDictValueByDictKey(dictService, "sampleType", s1.getSampleType()));//抽检类型xsampletype
			samplelist2.add(s1);
		}
		pager.setList(samplelist2);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Sample.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		//System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	
	}
	
	
	
	/**
	 * 增加前
	 * 1.需要根据随工单id查询其信息
	 */
	public String add()
	{
		this.workingbill=this.workingBillService.get(wbId);//获取随工单的信息
		//FactoryUnit fu = this.workingbill.getTeam().getFactoryUnit();
		String  workCenter = this.workingbill.getWorkcenter();
		FactoryUnit fu = factoryUnitService.get("workCenter", workCenter);
		HashMap<String, String> map = new HashMap<String, String>();
		String matnr="";
		String funid="";
		if(fu!=null){
			funid =fu.getId(); 
		}
		matnr = this.workingbill.getMatnr();
		map.put("matnr", matnr);
		map.put("funid", funid);
		UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
		if(pager==null){
			pager=new Pager();
		}
		if(unitdistributeProduct!=null){
			String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
			map.put("matmr", matmr);
			pager = unitdistributeModelService.getUBMList(pager, map);
		}
		
		
		this.list_cause=this.causeService.getBySample("1");//获取缺陷表中关于抽检的缺陷内容
		this.list_dict=this.dictService.getState("sampleType");//获取抽检类型
		this.add="add";
		return INPUT;
	}
	/**
	 * 新增刷卡保存
	 */
	public String creditsave()
	{
		//保存抽检单信息:抽检单，缺陷ID，缺陷数量，1保存/2确认
		this.sampleService.saveInfo(sample,info,info2,my_id,cardnumber);
		this.redirectionUrl="sample!list.action?wbId="+this.sample.getWorkingBill().getId();
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 新增刷卡确认
	 */
	public String creditsubmit()
	{
		//保存抽检单信息:抽检单，缺陷ID，缺陷数量，1保存/2确认
		this.sampleService.saveInfo(sample,info,info2,my_id,cardnumber);
		this.redirectionUrl="sample!list.action?wbId="+this.sample.getWorkingBill().getId();
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * jqGrid查询
	 * sample_list.ftl页面
	 */
	public String ajlist()
	{
		try
		{
			if(pager==null)
			{
				pager=new Pager();
				pager.setOrderType(OrderType.desc);
				pager.setOrderBy("modifyDate");
			}
			
			//jqGrid条件查询
			if(pager.is_search()==true&&filters!=null)
			{
				JSONObject filt=JSONObject.fromObject(filters);
				Pager pg1=new Pager();
				Map<Object,Object> mp=new HashMap<Object, Object>();
				mp.put("rules", jqGridSearchDetailTo.class);
				pg1=(Pager) JSONObject.toBean(filt,Pager.class,mp);
				pager.setRules(pg1.getRules());
				pager.setGroupOp(pg1.getGroupOp());
			}
			pager=this.sampleService.getSamplePager(pager,wbId);
			@SuppressWarnings("unchecked")
			List<Sample>samplelist=pager.getList();
			List<Sample>samplelist2=new ArrayList<Sample>();
			for(int i=0;i<samplelist.size();i++)
			{
				Sample s1=samplelist.get(i);
				s1.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService, "sampleState", s1.getState()));//状态描述--页面显示
				s1.setXsampler(s1.getSampler().getName());//抽检人xsampler
				if(s1.getComfirmation()!=null)
				{
					s1.setXcomfirmation(s1.getComfirmation().getName());//确认人xcomfirmation
				}
				//s1.setXmoudle(ThinkWayUtil.getDictValueByDictKey(dictService, "moudleType", s1.getMoudle()));
				s1.setXmoudle(s1.getMoudle());
				s1.setXproductnum(s1.getWorkingBill().getMatnr());//产品编号xproductnum
				s1.setXproductname(s1.getWorkingBill().getMaktx());//产品名称xproductnames
				s1.setXsampletype(ThinkWayUtil.getDictValueByDictKey(dictService, "sampleType", s1.getSampleType()));//抽检类型xsampletype
				samplelist2.add(s1);
			}
			pager.setList(samplelist2);
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Sample.class));//排除有关联关系的属性字段  
			JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
			return this.ajaxJson(jsonArray.get(0).toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * list页面--刷卡确认
	 */
	public String creditapproval()
	{
		ids = info.split(",");
		for (int i = 0; i < ids.length; i++)
		{
			this.sample = this.sampleService.get(ids[i]);
			String state = sample.getState();
			// 已经确认的不能重复确认
			if ("2".equals(state))
			{
				return ajaxJsonErrorMessage("已确认的无须再确认！");
			}
			// 已经撤销的不能再确认
			if ("3".equals(state))
			{
				return ajaxJsonErrorMessage("已撤销的无法再确认！");
			}
		}
		List<Sample> list = this.sampleService.get(ids);
		this.sampleService.updateState(list, "2",cardnumber);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * list页面--刷卡撤销
	 */
	public String creditundo()
	{
		ids = info.split(",");
		for (int i = 0; i < ids.length; i++)
		{
			this.sample = this.sampleService.get(ids[i]);
			String state = sample.getState();
			// 已经撤销的不能再确认
			if ("3".equals(state))
			{
				return ajaxJsonErrorMessage("已撤销的无法再撤销！");
			}
		}
		List<Sample> list = this.sampleService.get(ids);
		this.sampleService.updateState(list, "3",cardnumber);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 编辑前
	 */
	public String edit()
	{
		this.workingbill=this.workingBillService.get(wbId);//获取随工单的信息
		String  workCenter = this.workingbill.getWorkcenter();
		FactoryUnit fu = factoryUnitService.get("workCenter", workCenter);
		HashMap<String, String> map = new HashMap<String, String>();
		String matnr="";
		String funid="";
		if(fu!=null){
			funid =fu.getId(); 
		}
		matnr = this.workingbill.getMatnr();
		map.put("matnr", matnr);
		map.put("funid", funid);
		UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
		if(pager==null){
			pager=new Pager();
		}
		if(unitdistributeProduct!=null){
			String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
			map.put("matmr", matmr);
			pager = unitdistributeModelService.getUBMList(pager, map);
		}
		List<Cause> l_cause=this.causeService.getBySample("1");//获取缺陷表中关于抽检的缺陷内容
		this.list_dict=this.dictService.getState("sampleType");//获取抽检类型
		this.sample=this.sampleService.get(id);
		this.list_samrecord=this.srService.getBySampleId(id);//根据抽检id获取缺陷记录
		list_cause=new ArrayList<Cause>();
		for(int i=0;i<l_cause.size();i++)
		{
			Cause c=l_cause.get(i);
			for(int j=0;j<list_samrecord.size();j++)
			{
				SampleRecord sr=list_samrecord.get(j);
				if(c!=null&&sr!=null)
				{
					if(c.getId().equals(sr.getCauseId()))
					{
						c.setCauseNum(sr.getRecordNum());
					}
				}
			}
			this.list_cause.add(c);
		}
		this.edit="edit";
		return INPUT;
	}
	
	/**
	 * 修改刷卡保存
	 */
	public String creditupdate()
	{
		//保存抽检单信息:抽检单，缺陷ID，缺陷数量，1保存/2确认
		this.sampleService.updateInfo(sample,info,info2,my_id,cardnumber);
		this.redirectionUrl="sample!list.action?wbId="+this.sample.getWorkingBill().getId();
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 修改刷卡确认
	 */
	public String creditreply()
	{
		//保存抽检单信息:抽检单，缺陷ID，缺陷数量，1保存/2确认
		this.sampleService.updateInfo(sample,info,info2,my_id,cardnumber);
		this.redirectionUrl="sample!list.action?wbId="+this.sample.getWorkingBill().getId();
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 查询一个，查看功能，不能编辑
	 */
	public String show()
	{
		this.workingbill=this.workingBillService.get(wbId);//获取随工单的信息
		this.sample=this.sampleService.get(id);
		this.sampletype=this.dictService.getByState("sampleType",sample.getSampleType());//根据状态获取抽检类型
		this.list_samrecord=this.srService.getBySampleId(id);//根据抽检id获取缺陷记录
		this.show="show";
		return INPUT;
	}
	
	/**========================end  method======================================*/
	
	/**=========================="get/set"  start===============================*/
	
	public Sample getSample()
	{
		return sample;
	}
	public void setSample(Sample sample)
	{
		this.sample = sample;
	}

	public WorkingBill getWorkingbill()
	{
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill)
	{
		this.workingbill = workingbill;
	}

	public String getWbId()
	{
		return wbId;
	}

	public void setWbId(String wbId)
	{
		this.wbId = wbId;
	}

	public String getMy_id()
	{
		return my_id;
	}

	public void setMy_id(String my_id)
	{
		this.my_id = my_id;
	}

	public List<Cause> getList_cause()
	{
		return list_cause;
	}

	public void setList_cause(List<Cause> list_cause)
	{
		this.list_cause = list_cause;
	}

	public List<Dict> getList_dict()
	{
		return list_dict;
	}

	public void setList_dict(List<Dict> list_dict)
	{
		this.list_dict = list_dict;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getInfo2()
	{
		return info2;
	}

	public void setInfo2(String info2)
	{
		this.info2 = info2;
	}

	public Admin getAdmin()
	{
		return admin;
	}

	public void setAdmin(Admin admin)
	{
		this.admin = admin;
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

	public List<SampleRecord> getList_samrecord()
	{
		return list_samrecord;
	}

	public void setList_samrecord(List<SampleRecord> list_samrecord)
	{
		this.list_samrecord = list_samrecord;
	}

	public String getSampletype()
	{
		return sampletype;
	}

	public void setSampletype(String sampletype)
	{
		this.sampletype = sampletype;
	}

	public String getCardnumber()
	{
		return cardnumber;
	}

	public void setCardnumber(String cardnumber)
	{
		this.cardnumber = cardnumber;
	}
	
	public List<Dict> getAllMoudle() {
		return dictService.getList("dictname", "moudleType");
	}
	
	/**==========================end "get/set"====================================*/
}
