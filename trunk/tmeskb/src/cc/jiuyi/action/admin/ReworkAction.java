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
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReworkService;
import cc.jiuyi.service.WorkingBillService;
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
	
	private Rework rework;
	private Admin admin;
	private String workingBillId;
	private WorkingBill workingbill;
	private String show;
	private String isQualified;//是否合格
	private String isCompelete;//是否完工

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
					rework.setIsQualifieds(ThinkWayUtil.getDictValueByDictKey(
							dictService, "isQualifieds", rework.getIsQualified()));
					rework.setIsCompeletes(ThinkWayUtil.getDictValueByDictKey(
							dictService, "isCompeletes", rework.getIsCompelete()));
					rework.setProductsCode(rework.getWorkingbill().getMatnr());
					rework.setProductsName(rework.getWorkingbill().getMaktx());
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

	
	//编辑
		public String edit(){
	    workingbill = workingBillService.get(workingBillId);
		rework = reworkService.load(id);
		if(rework.getState().equals(UNDO)){
			addActionError("已撤销的无法再编辑！");
			return ERROR;
		}
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
	public String save()throws Exception{
		admin= adminService.getLoginAdmin();
		//rework.setCreateUser(admin.getId());
		rework.setCreateUser(admin);
		rework.setModifyUser(admin);
		reworkService.save(rework);
		redirectionUrl="rework!list.action?workingBillId="
				+ rework.getWorkingbill().getId();
		return SUCCESS;	
	}
		
	//刷卡回复
	public String check() throws Exception{
		Rework persistent = reworkService.load(id);
		BeanUtils.copyProperties(rework, persistent, new String[] { "id","createUser"});
		admin=adminService.getLoginAdmin();
		persistent.setConfirmUser(admin);
		persistent.setModifyUser(admin);
		persistent.setState("3");
		reworkService.save(persistent);
		redirectionUrl="rework!list.action?workingBillId="
				+rework.getWorkingbill().getId();
		return SUCCESS;
	}
	
	//刷卡确认
	public String confirm() throws Exception{
		Rework persistent = reworkService.load(id);
		BeanUtils.copyProperties(rework, persistent, new String[] { "id","createUser"});
		admin=adminService.getLoginAdmin();
		persistent.setConfirmUser(admin);
		persistent.setModifyUser(admin);
		persistent.setState("2");
		reworkService.save(persistent);
		redirectionUrl="rework!list.action?workingBillId="
				+rework.getWorkingbill().getId();
		return SUCCESS;
	}

	// 刷卡撤销
	public String creditundo() {
		workingbill = workingBillService.get(workingBillId);
		ids = id.split(",");
		for (int i = 0; i < ids.length; i++) {
			rework = reworkService.load(ids[i]);
			if (UNDO.equals(rework.getState())) {
				addActionError("已撤销的无法再撤销！");
				return ERROR;
			}
		}	
		admin=adminService.getLoginAdmin();
		rework.setConfirmUser(admin);
		rework.setState(UNDO);
		reworkService.update(rework);
		workingBillService.update(workingbill);
		redirectionUrl = "rework!list.action?workingBillId="
				+ rework.getWorkingbill().getId();
		return SUCCESS;
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

	
	
}
