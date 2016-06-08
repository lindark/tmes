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
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.ReworkRecord;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReworkRecordService;
import cc.jiuyi.service.ReworkService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.SendMsgUtil;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-返工管理
 */

@ParentPackage("admin")
public class ReworkRecordAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6552767291676622701L;
	
	private static final String COMPELETE ="Y";
	private static final String UNDO="4";
	private static final String CHECKED="2";
	
	private ReworkRecord reworkRecord;
	private Admin admin;
	private String workingBillId;
	private WorkingBill workingbill;
	private Rework rework;
	private String show;
	private String isQualified;//是否合格
	private String isCompelete;//是否完工
	private String cardnumber;//刷卡卡号
	private String reworkId;//前台传的主表ID	
	private String id;//前台JQ传的主表ID
	private Integer reworkCount;//前台隐藏传的翻包次数
	private String xedit;
	private String xadd;
	

	private List<Dict> allCheck;
	
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private ReworkRecordService reworkRecordService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private ReworkService reworkService;
	
	//添加
	public String add(){
		rework = reworkService.get(reworkId);
		workingbill=workingBillService.get(workingBillId);
		xadd = "xadd";
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
	public String list() {
		if (id != null && !id.equals("")) {
			rework = reworkService.get(id);	
			if(rework.getState().equals("2")){
				xedit = "edit";
			}			
		}
		if (reworkId != null && !reworkId.equals("")) {
			rework = reworkService.get(reworkId);
			if(rework.getState().equals("2")){
				xedit = "edit";
			}	
		}
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		this.workingbill = this.workingBillService.get(workingBillId);
		return LIST;
	}

	/**
	 * 查询一个，查看功能，不能编辑
	 */
	public String show()
	{
		this.workingbill=this.workingBillService.get(workingBillId);//获取随工单的信息
		this.reworkRecord=this.reworkRecordService.load(id);
		this.isQualified=this.dictService.getByState("isQualifieds", reworkRecord.getIsQualified());
		this.isCompelete=this.dictService.getByState("isCompeletes", reworkRecord.getIsCompelete());
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
			    
			    pager = reworkRecordService.getReworkRecordPager(pager, map,id);		
				List<ReworkRecord> reworkRecordList = pager.getList();
				List<ReworkRecord> lst = new ArrayList<ReworkRecord>();
				for (int i = 0; i < reworkRecordList.size(); i++) {
					System.out.println(reworkRecordList.size());
					ReworkRecord reworkRecord = (ReworkRecord) reworkRecordList.get(i);
					reworkRecord.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
							dictService, "reworkState", reworkRecord.getState()));
					reworkRecord.setIsQualifieds(ThinkWayUtil.getDictValueByDictKey(
							dictService, "isQualifieds", reworkRecord.getIsQualified()));
					reworkRecord.setIsCompeletes(ThinkWayUtil.getDictValueByDictKey(
							dictService, "isCompeletes", reworkRecord.getIsCompelete()));
//					reworkRecord.setProductsCode(reworkRecord.getRework().getWorkingbill().getMatnr());
//					reworkRecord.setProductsName(reworkRecord.getRework().getWorkingbill().getMaktx());
					if(reworkRecord.getDuty()!=null){
						reworkRecord.setXduty(reworkRecord.getDuty().getName());//责任人名
					}
					if(reworkRecord.getCreateUser()!=null){
						reworkRecord.setXcreateUser(reworkRecord.getCreateUser().getName());//创建人名
					}
				    
				    if(reworkRecord.getConfirmUser()!=null){
				    	reworkRecord.setXconfirmUser(reworkRecord.getConfirmUser().getName());
				    }
				    if(reworkRecord.getModifyUser()!=null){
				    	reworkRecord.setXmodifyUser(reworkRecord.getModifyUser().getName());
				    }
				    //reworkRecord.setXconfirmUser(reworkRecord.getConfirmUser().getName());//确认人名
				   // reworkRecord.setXmodifyUser(reworkRecord.getModifyUser().getName());//修改人名
					lst.add(reworkRecord);
				}
				
			pager.setList(lst);
			JsonConfig jsonConfig=new JsonConfig(); 
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(ReworkRecord.class));//排除有关联关系的属性字段  
			JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
			 return ajaxJson(jsonArray.get(0).toString());	
	}
	

	//删除
	public String delete(){
		ids=id.split(",");
		reworkRecordService.updateisdel(ids, "Y");
		redirectionUrl = "reworkRecord!list.action?workingBillId="
				+ reworkRecord.getRework().getWorkingbill().getId();
		return SUCCESS;
	}

	
	//编辑检查
		public String checkEdit(){
	    workingbill = workingBillService.get(workingBillId);
		reworkRecord = reworkRecordService.get(id);
		if(reworkRecord.getState().equals("2")){
			return ajaxJsonErrorMessage("已经确认的不能再次编辑!");
		}
		else{
			HashMap<String , String> map = new HashMap<String , String>();
			map.put("status", "success");
			return ajaxJson(map);
		}	
	  }
		
		
	// 编辑
	public String edit() {
		workingbill = workingBillService.get(workingBillId);
		reworkRecord = reworkRecordService.get(id);
		return INPUT;
	}

	//更新
		@Validations(
				requiredStrings = {
						@RequiredStringValidator(fieldName = "reworkRecord.problem", message = "问题不允许为空!"),
						@RequiredStringValidator(fieldName = "reworkRecord.rectify", message = "调整方案不允许为空!"),
						
				  },
					intRangeFields = {
						@IntRangeFieldValidator(fieldName = "reworkRecord.reworkRecordCount",min="0", message = "翻包次数必须为零或正整数!"),
						@IntRangeFieldValidator(fieldName = "reworkRecord.reworkRecordAmount",min="0", message = "翻包数量必须为零或正整数!"),
						@IntRangeFieldValidator(fieldName = "reworkRecord.defectAmount",min="0", message = "缺陷数量必须为零或正整数!")		
				}
				  
		)
		@InputConfig(resultName = "error")
		public String update() {
			ReworkRecord persistent = reworkRecordService.load(id);
			BeanUtils.copyProperties(reworkRecord, persistent, new String[] { "id","createUser"});
			String isQualified = reworkRecord.getIsQualified();
			if(isQualified.equals("Y")){
				Integer reworkRecordCount = reworkRecord.getReworkAmount();
				reworkRecordCount=reworkRecordCount+1;
			}
			reworkRecordService.update(persistent);
			admin=adminService.loadLoginAdmin();
			//reworkRecord.setAdmin(admin);
			redirectionUrl = "reworkRecord!list.action?workingBillId="
					+ reworkRecord.getRework().getWorkingbill().getId();
			return SUCCESS;
		}
	

		
	//刷卡提交
//	@Validations(
//			requiredStrings = {
//					@RequiredStringValidator(fieldName = "reworkRecord.problem", message = "问题不允许为空!"),
//					@RequiredStringValidator(fieldName = "reworkRecord.rectify", message = "调整方案不允许为空!"),
//					
//			  },
//				intRangeFields = {
//					@IntRangeFieldValidator(fieldName = "reworkRecord.reworkRecordCount",min="0", message = "翻包次数必须为零或正整数!"),
//					@IntRangeFieldValidator(fieldName = "reworkRecord.reworkRecordAmount",min="0", message = "翻包数量必须为零或正整数!"),
//					@IntRangeFieldValidator(fieldName = "reworkRecord.defectAmount",min="0", message = "缺陷数量必须为零或正整数!")
//		
//			}
//			  
//	)
//  @InputConfig(resultName = "error")
	public String creditsubmit()throws Exception{
		/**后台验证**/
		if(reworkRecord.getProblem()==null||reworkRecord.getProblem()==""){
			return ajaxJsonErrorMessage("问题内容不能为空!");
		}
		if(reworkRecord.getRectify()==null||reworkRecord.getRectify()==""){
			return ajaxJsonErrorMessage("调整方案不允许为空!");
		}
		if(reworkRecord.getReworkAmount()==null||String.valueOf(reworkRecord.getReworkAmount()).matches("^[0-9]*[1-9][0-9]*$ ")){
			return ajaxJsonErrorMessage("翻包数量必须为零或正整数!");
		}
		if(reworkRecord.getDefectAmount()==null||String.valueOf(reworkRecord.getDefectAmount()).matches("^[0-9]*[1-9][0-9]*$ ")){
			return ajaxJsonErrorMessage("缺陷数量必须为零或正整数!");
		}		
		
	    reworkId = reworkRecordService.saveSubmit(cardnumber, workingBillId, reworkId, reworkCount,reworkRecord);
		
	    /**调用短信接口通知副主任确认返工单**/  
	  /*  try {
	   * if(reworkRecord.getDuty()!=null){
		    	String adminId = reworkRecord.getDuty().getId();
			    Admin admin = adminService.get(adminId);
				String str = SendMsgUtil.SendMsg(admin.getPhoneNo(), "您有新的返工单,请马上登陆系统查看并回复！");
				SAXReader reader = new SAXReader(); // 解析返回xml文件
				Document doc;
				doc = DocumentHelper.parseText(str);
				Node stateNode = doc.selectSingleNode("/infos/info/state");
				if (!stateNode.getText().equalsIgnoreCase("0")) {// 短信发送失败
					ajaxJsonErrorMessage("短信发送失败!");
				}
	    	}
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	    HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功!");
		hashmap.put("reworkId", reworkId);
		return ajaxJson(hashmap);
	}

	// 刷卡回复
	public String creditreply() throws Exception {
		ReworkRecord persistent = reworkRecordService.get(id);
		BeanUtils.copyProperties(reworkRecord, persistent, new String[] { "id",
				"createUser","rework","reworkCount"});
		admin = adminService.getByCardnum(cardnumber);
		persistent.setConfirmUser(admin);
		persistent.setModifyUser(admin);
		persistent.setState("3"); //状态变成已回复
		reworkRecordService.save(persistent);
		
		/**调用短信接口通知质检查看确认返工单**/
	    try {
	    	if(reworkRecord.getDuty()!=null){
	    		String adminId = reworkRecord.getDuty().getId();
			    Admin admin = adminService.get(adminId);
				String str = SendMsgUtil.SendMsg(admin.getPhoneNo(), "返工单已经返工,请马上登陆系统检查并确认！");
				SAXReader reader = new SAXReader(); // 解析返回xml文件
				Document doc;
				doc = DocumentHelper.parseText(str);
				Node stateNode = doc.selectSingleNode("/infos/info/state");
				if (!stateNode.getText().equalsIgnoreCase("0")) {// 短信发送失败
					ajaxJsonErrorMessage("短信发送失败!");
				}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
	    
		reworkId = persistent.getRework().getId();
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功!");
		hashmap.put("reworkId", reworkId);
		return ajaxJson(hashmap);
	}

	// 刷卡确认
	public String creditapproval() throws Exception {
		ReworkRecord persistent = reworkRecordService.get(id);
		BeanUtils.copyProperties(reworkRecord, persistent, new String[] { "id",
				"createUser","rework","reworkCount"});
		reworkId = reworkRecordService.saveApproval(cardnumber, persistent);
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put(STATUS, SUCCESS);
		hashmap.put(MESSAGE, "您的操作已成功!");
		hashmap.put("reworkId", reworkId);
		return ajaxJson(hashmap);
	}

	// 刷卡撤销
	public String creditundo() {
		workingbill = workingBillService.get(workingBillId);
		admin = adminService.getByCardnum(cardnumber);
		ids = id.split(",");
		List<ReworkRecord> list = reworkRecordService.get(ids);
//		for (int i = 0; i < list.size(); i++) {
//			ReworkRecord reworkRecord = list.get(i);
//			if (CHECKED.equals(rework.getState())) {
//				return ajaxJsonErrorMessage("已确认的无法再撤销!");
//			}
//			if (UNDO.equals(rework.getState())) {
//				return ajaxJsonErrorMessage("已撤销的无法再撤销!");
//			}
//		}
	//	reworkService.saveRepeal(list, admin, UNDO);
		reworkRecordService.saveRepeal(list, admin, UNDO);
		return ajaxJsonSuccessMessage("您的操作已成功");
	}


	public ReworkRecord getReworkRecord() {
		return reworkRecord;
	}


	public void setReworkRecord(ReworkRecord reworkRecord) {
		this.reworkRecord = reworkRecord;
	}


	public ReworkRecordService getReworkRecordService() {
		return reworkRecordService;
	}


	public void setReworkRecordService(ReworkRecordService reworkRecordService) {
		this.reworkRecordService = reworkRecordService;
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

	public String getReworkId() {
		return reworkId;
	}

	public void setReworkId(String reworkId) {
		this.reworkId = reworkId;
	}

	public Rework getRework() {
		return rework;
	}

	public void setRework(Rework rework) {
		this.rework = rework;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getReworkCount() {
		return reworkCount;
	}

	public void setReworkCount(Integer reworkCount) {
		if(reworkCount == null) reworkCount = 0;
		this.reworkCount = reworkCount;
	}

	public String getXadd() {
		return xadd;
	}

	public void setXadd(String xadd) {
		this.xadd = xadd;
	}


	public String getXedit() {
		return xedit;
	}

	public void setXedit(String xedit) {
		this.xedit = xedit;
	}

}
