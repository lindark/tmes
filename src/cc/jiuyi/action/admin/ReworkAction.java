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

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.ReworkRecord;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReworkRecordService;
import cc.jiuyi.service.ReworkService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-返工管理
 */

@ParentPackage("admin")
public class ReworkAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6552767291676622701L;
	
	private static final String COMPELETE ="Y";
	private static final String UNDO="4";
	private static final String CHECKED="2";
	
	private Rework rework;
	private Admin admin;
	private String workingBillId;
	private WorkingBill workingbill;
	private String show;
	private String isQualified;//是否合格
	private String isCompelete;//是否完工
	private String cardnumber;//刷卡卡号
	private String productsCode;//产品编号
	private String productsName;//产品名称
	private String state;//状态
	private String start;//起始时间
	private String end;//终止时间

	private List<Dict> allCheck;
	
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private ReworkService reworkService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource ReworkRecordService reworkRecordService;
	
	//添加
	public String add(){
		workingbill=workingBillService.get(workingBillId);
		return INPUT;
	}

	// 列表
		public String browser() {
			return "browser";
		}
		
	/**
	 * 跳转list 页面
	 * 
	 * @return
	 */
	public String history(){
		return "history";
	}
	
	
	
	//返工记录列表 @author Razey 2016/3/3
	public String historylist() {

		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		
		HashMap<String, String> map = new HashMap<String, String>();

		
		
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
			if (obj.get("productsName") != null) {
				String productsName = obj.getString("productsName").toString();
				map.put("productsName", productsName);
			}
			if (obj.get("productsCode") != null) {
				String productsCode = obj.getString("productsCode").toString();
				map.put("productsCode", productsCode);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if (obj.get("start") != null) {
				String start = obj.getString("start").toString();
				map.put("start", start);
			}
			if (obj.get("end") != null) {
				String end = obj.getString("end").toString();
				map.put("end", end);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}
		pager = reworkRecordService.historyjqGrid(pager, map);
		List<ReworkRecord> reworkRecordList = pager.getList();
		List<ReworkRecord> lst = new ArrayList<ReworkRecord>();
		try {
			for (int i = 0; i < reworkRecordList.size(); i++) {
				ReworkRecord reworkRecord = reworkRecordList.get(i);
				reworkRecord.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "reworkState", reworkRecord.getRework().getState()));
				reworkRecord.setIsQualifieds(ThinkWayUtil.getDictValueByDictKey(
						dictService, "isQualifieds", reworkRecord.getIsQualified()));
				if(reworkRecord.getRework().getWorkingbill()!=null){
					reworkRecord.setProductsName(reworkRecord.getRework().getWorkingbill().getMaktx());
				}
				if(reworkRecord.getRework().getWorkingbill()!=null){
					reworkRecord.setProductsCode(reworkRecord.getRework().getWorkingbill().getMatnr());
				}
				if(reworkRecord.getModifyDate()!=null){
					reworkRecord.setModifyDate(reworkRecord.getModifyDate());
				}
				if(reworkRecord.getCreateUser()!=null){
					reworkRecord.setXcreateUser(reworkRecord.getCreateUser().getName());
				}
				if(reworkRecord.getConfirmUser()!=null){
					reworkRecord.setXconfirmUser(reworkRecord.getConfirmUser().getName());
				}
				if(reworkRecord.getDuty()!=null){
					reworkRecord.setXduty(reworkRecord.getDuty().getName());
				}
				if(reworkRecord.getReworkAmount()!=null){
					reworkRecord.setReworkAmount(reworkRecord.getReworkAmount());
				}
				if(reworkRecord.getRework().getWorkingbill()!=null){
					reworkRecord.setWorkingbillCode(reworkRecord.getRework().getWorkingbill().getWorkingBillCode());
				}
				if(reworkRecord.getRework().getWorkingbill()!=null){
					reworkRecord.setProductsDate(reworkRecord.getRework().getWorkingbill().getProductDate());
				}
				System.out.println(reworkRecord.getCreateDate());
				lst.add(reworkRecord);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(ReworkRecord.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	
	}
	
	//Excel导出 @author Razey 2016/3/3
	public String excelexport(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("productsCode", productsCode);
		map.put("productsName", productsName);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);
		
		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		
        header.add("随工单号");
        header.add("产品编码");
        header.add("产品名称");
        header.add("生产日期");
        header.add("创建人");
        header.add("确认人");
        header.add("责任人");
        header.add("翻包数量");
        header.add("缺陷数量");
        header.add("是否合格");
        header.add("创建日期");
        header.add("翻包次数");
        header.add("状态");
        
        List<Object[]> reworkrecordList = reworkRecordService.historyExcelExport(map);
        for(int i=0;i<reworkrecordList.size();i++){
        	Object[] obj = reworkrecordList.get(i);
        	ReworkRecord reworkrecord = (ReworkRecord) obj[0];//pickdetail
        	Rework rework = (Rework)obj[1];//pick
        	WorkingBill workingbill = (WorkingBill)obj[2];//workingbill
        	Object[] bodyval = {
        			workingbill.getWorkingBillCode()==null?"":workingbill.getWorkingBillCode(),
        			workingbill.getMaktx()==null?"":workingbill.getMaktx(),
        			workingbill.getMatnr()==null?"":workingbill.getMatnr(),
        			workingbill.getProductDate()==null?"":workingbill.getProductDate(),
        			reworkrecord.getCreateUser()==null?"":reworkrecord.getCreateUser().getName(),
        			reworkrecord.getConfirmUser()==null?"":reworkrecord.getConfirmUser().getName(),
        			reworkrecord.getDuty()==null?"":reworkrecord.getDuty().getName(),
        			reworkrecord.getReworkAmount()==null?"":reworkrecord.getReworkAmount(),
        			reworkrecord.getDefectAmount()==null?"":reworkrecord.getDefectAmount(),
//        			reworkrecord.getIsQualified()==null?"":reworkrecord.getIsQualified(),
        			ThinkWayUtil.getDictValueByDictKey(dictService, "isQualifieds", reworkrecord.getIsQualified()),
        			reworkrecord.getCreateDate()==null?"":reworkrecord.getCreateDate(),
        			reworkrecord.getReworkCount()==null?"":reworkrecord.getReworkCount(),
        			ThinkWayUtil.getDictValueByDictKey(dictService,"reworkState", rework.getState())
        	};
        	body.add(bodyval);
        }
        
        try {
			String fileName = "返工记录表"+".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("返工记录表", header, body, getResponse().getOutputStream());
			getResponse().getOutputStream().flush();
		    getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	public String list(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		this.workingbill=this.workingBillService.get(workingBillId);
		return LIST;
	}
	
	
	/**
	 * 查询一个，查看功能，不能编辑
	 */
	public String show()
	{
		this.workingbill=this.workingBillService.get(workingBillId);//获取随工单的信息
		this.rework=this.reworkService.load(id);
		this.isQualified=this.dictService.getByState("isQualifieds", rework.getIsQualified());
		this.isCompelete=this.dictService.getByState("isCompeletes", rework.getIsCompelete());
		this.show="show";
		return INPUT;
	}
	
	
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){	
			HashMap<String, String> map = new HashMap<String, String>();
			workingbill = workingBillService.get(workingBillId);
			if(pager.getOrderBy().equals("")) {
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
				if (obj.get("state") != null) {
					String state = obj.getString("state").toString();
					map.put("state", state);
				}
				if(obj.get("start")!=null){
					String start = obj.get("start").toString();;
					map.put("start", start);
				}
			}
			    pager = reworkService.getReworkPager(pager, map,workingBillId);		
				List<Rework> reworkList = pager.getList();
				List<Rework> lst = new ArrayList<Rework>();
				for (int i = 0; i < reworkList.size(); i++) {
					System.out.println(reworkList.size());
					Rework rework = (Rework) reworkList.get(i);
					rework.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
							dictService, "reworkState", rework.getState()));
//					rework.setIsQualifieds(ThinkWayUtil.getDictValueByDictKey(
//							dictService, "isQualifieds", rework.getIsQualified()));
//					rework.setIsCompeletes(ThinkWayUtil.getDictValueByDictKey(
//							dictService, "isCompeletes", rework.getIsCompelete()));
//					rework.setProductsCode(rework.getWorkingbill().getMatnr());
//					rework.setProductsName(rework.getWorkingbill().getMaktx());
					rework.setXduty(rework.getDuty().getName());//责任人名
				    rework.setXcreateUser(rework.getCreateUser().getName());//创建人名
				    if(rework.getConfirmUser()!=null){
				    	rework.setXconfirmUser(rework.getConfirmUser().getName());
				    }
				    if(rework.getModifyUser()!=null){
				    	rework.setXmodifyUser(rework.getModifyUser().getName());
				    }
				    //rework.setXconfirmUser(rework.getConfirmUser().getName());//确认人名
				   // rework.setXmodifyUser(rework.getModifyUser().getName());//修改人名
					lst.add(rework);
				}
				
			pager.setList(lst);
			JsonConfig jsonConfig=new JsonConfig(); 
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Rework.class));//排除有关联关系的属性字段  
			JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
			 return ajaxJson(jsonArray.get(0).toString());	
	}
	

	//删除
	public String delete(){
		ids=id.split(",");
		reworkService.updateisdel(ids, "Y");
		redirectionUrl = "rework!list.action?workingBillId="
				+ rework.getWorkingbill().getId();
		return SUCCESS;
	}

	
	//编辑检查
		public String checkEdit(){
	    workingbill = workingBillService.get(workingBillId);
		rework = reworkService.load(id);
		if(UNDO.equals(rework.getState())){
			return ajaxJsonErrorMessage("已撤销的无法再编辑");
		}
		if(CHECKED.equals(rework.getState())){
			return ajaxJsonErrorMessage("已确认的无法再编辑");
		}
		else{
			HashMap<String , String> map = new HashMap<String , String>();
			map.put("status", "success");
			return ajaxJson(map);
		}	
	  }
		
		
		//编辑
		public String edit(){
		    workingbill = workingBillService.get(workingBillId);
			rework = reworkService.load(id);
			return INPUT;
		}	
		
	//更新
		@Validations(
				requiredStrings = {
						@RequiredStringValidator(fieldName = "rework.problem", message = "问题不允许为空!"),
						@RequiredStringValidator(fieldName = "rework.rectify", message = "调整方案不允许为空!"),
						
				  },
					intRangeFields = {
						@IntRangeFieldValidator(fieldName = "rework.reworkCount",min="0", message = "翻包次数必须为零或正整数!"),
						@IntRangeFieldValidator(fieldName = "rework.reworkAmount",min="0", message = "翻包数量必须为零或正整数!"),
						@IntRangeFieldValidator(fieldName = "rework.defectAmount",min="0", message = "缺陷数量必须为零或正整数!")		
				}
				  
		)
		@InputConfig(resultName = "error")
		public String update() {
			Rework persistent = reworkService.load(id);
			BeanUtils.copyProperties(rework, persistent, new String[] { "id","createUser"});
			String isQualified = rework.getIsQualified();
			if(isQualified.equals("Y")){
				Integer reworkCount = rework.getReworkCount();
				reworkCount=reworkCount+1;
			}
			reworkService.update(persistent);
			admin=adminService.loadLoginAdmin();
			//rework.setAdmin(admin);
			redirectionUrl = "rework!list.action?workingBillId="
					+ rework.getWorkingbill().getId();
			return SUCCESS;
		}
	

		
	//保存
//	@Validations(
//			requiredStrings = {
//					@RequiredStringValidator(fieldName = "rework.problem", message = "问题不允许为空!"),
//					@RequiredStringValidator(fieldName = "rework.rectify", message = "调整方案不允许为空!"),
//					
//			  },
//				intRangeFields = {
//					@IntRangeFieldValidator(fieldName = "rework.reworkCount",min="0", message = "翻包次数必须为零或正整数!"),
//					@IntRangeFieldValidator(fieldName = "rework.reworkAmount",min="0", message = "翻包数量必须为零或正整数!"),
//					@IntRangeFieldValidator(fieldName = "rework.defectAmount",min="0", message = "缺陷数量必须为零或正整数!")
//		
//			}
//			  
//	)
//  @InputConfig(resultName = "error")
	public String creditsubmit()throws Exception{
		if(rework.getProblem()==null||rework.getProblem()==""){
			return ajaxJsonErrorMessage("问题内容不能为空!");
		}
		if(rework.getRectify()==null||rework.getRectify()==""){
			return ajaxJsonErrorMessage("调整方案不允许为空!");
		}
		if(rework.getReworkCount()==null||String.valueOf(rework.getReworkCount()).matches("^[0-9]*[1-9][0-9]*$ ")){
			return ajaxJsonErrorMessage("翻包次数必须为零或正整数!");
		}
		if(rework.getReworkAmount()==null||String.valueOf(rework.getReworkAmount()).matches("^[0-9]*[1-9][0-9]*$ ")){
			return ajaxJsonErrorMessage("翻包数量必须为零或正整数!");
		}
		if(rework.getDefectAmount()==null||String.valueOf(rework.getDefectAmount()).matches("^[0-9]*[1-9][0-9]*$ ")){
			return ajaxJsonErrorMessage("缺陷数量必须为零或正整数!");
		}
		admin= adminService.getByCardnum(cardnumber);
		rework.setCreateUser(admin);
		rework.setModifyUser(admin);
		reworkService.save(rework);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡回复
	public String creditreply() throws Exception {
		Rework persistent = reworkService.get(id);
		BeanUtils.copyProperties(rework, persistent, new String[] { "id",
				"createUser" });
		admin = adminService.getByCardnum(cardnumber);
		persistent.setConfirmUser(admin);
		persistent.setModifyUser(admin);
		persistent.setState("3");
		reworkService.save(persistent);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡确认
	public String creditapproval() throws Exception {
		Rework persistent = reworkService.load(id);
		admin = adminService.getByCardnum(cardnumber);
		persistent.setConfirmUser(admin);
		persistent.setModifyUser(admin);
		persistent.setState("2");
		reworkService.save(persistent);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 刷卡撤销
	public String creditundo() {
		workingbill = workingBillService.get(workingBillId);
		admin = adminService.getByCardnum(cardnumber);
		ids = id.split(",");
		List<Rework> list = reworkService.get(ids);
		for (int i = 0; i < list.size(); i++) {
			rework = list.get(i);
			if (UNDO.equals(rework.getState())) {
				return ajaxJsonErrorMessage("已撤销的无法再撤销!");
			}
		}
		reworkService.saveRepeal(list, admin, UNDO);
		return ajaxJsonSuccessMessage("您的操作已成功");
	}


	public Rework getRework() {
		return rework;
	}


	public void setRework(Rework rework) {
		this.rework = rework;
	}


	public ReworkService getReworkService() {
		return reworkService;
	}


	public void setReworkService(ReworkService reworkService) {
		this.reworkService = reworkService;
	}


	//获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "StateRemark");
	}


	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}


	public DictService getDictService() {
		return dictService;
	}


	public void setDictService(DictService dictService) {
		this.dictService = dictService;
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Dict> getAllCheck() {
		return dictService.getList("dictname", "isQualifieds");
	}

	public void setAllCheck(List<Dict> allCheck) {
		this.allCheck = allCheck;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getIsCompelete() {
		return isCompelete;
	}

	public void setIsCompelete(String isCompelete) {
		this.isCompelete = isCompelete;
	}

	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getProductsCode() {
		return productsCode;
	}

	public void setProductsCode(String productsCode) {
		this.productsCode = productsCode;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
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

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}

	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public ReworkRecordService getReworkRecordService() {
		return reworkRecordService;
	}

	public void setReworkRecordService(ReworkRecordService reworkRecordService) {
		this.reworkRecordService = reworkRecordService;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getCompelete() {
		return COMPELETE;
	}

	public static String getUndo() {
		return UNDO;
	}

	public static String getChecked() {
		return CHECKED;
	}
	
	
	

	
	
}
