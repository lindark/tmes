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
import cc.jiuyi.entity.ItermediateTest;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ItermediateTestDetailService;
import cc.jiuyi.service.ItermediateTestService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-半成品巡检主表
 */

@ParentPackage("admin")
public class ItermediateTestAction extends BaseAdminAction {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6045295823911487260L;
	
	private static final String CONFIRMED="2";
	private static final String REPEAL="3";
	
	private ItermediateTest itermediateTest;
	//获取所有状态
	private List<Dict> allState;
	private Admin admin;
	
	

	@Resource
	private ItermediateTestService itermediateTestService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private ItermediateTestDetailService itermediateTestDetailService;
	@Resource
	private AdminService adminService;
	
	
	private String workingBillId;
	private WorkingBill workingbill;
	

	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		System.out.println(admin.getShift());
		this.workingbill=this.workingBillService.get(workingBillId);
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
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
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

			pager = itermediateTestService.getItermediateTestPager(pager, map);
			List<ItermediateTest> itermediateTestList = pager.getList();
			List<ItermediateTest> lst = new ArrayList<ItermediateTest>();
			for (int i = 0; i < itermediateTestList.size(); i++) {
				ItermediateTest itermediateTest = (ItermediateTest) itermediateTestList.get(i);
				itermediateTest.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "itermediateTestState", itermediateTest.getState()));
				if(itermediateTest.getConfirmUser()!=null){
					itermediateTest.setXconfirmUser(itermediateTest.getConfirmUser().getName());
				}
				if(itermediateTest.getCreateUser()!=null){
					itermediateTest.setXcreateUser(itermediateTest.getCreateUser().getName());
				}	
				lst.add(itermediateTest);
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
		itermediateTestService.updateisdel(ids, "Y");
//		for (String id:ids){
//			ItermediateTest itermediateTest=itermediateTestService.load(id);
//		}
		redirectionUrl = "itermediateTest!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			itermediateTest= itermediateTestService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			ItermediateTest persistent = itermediateTestService.load(id);
			BeanUtils.copyProperties(itermediateTest, persistent, new String[] { "id","createDate", "modifyDate"});
			itermediateTestService.update(persistent);
			redirectionUrl = "itermediateTest!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		itermediateTestService.save(itermediateTest);
		redirectionUrl="itermediateTest!list.action";
		return SUCCESS;	
	}
		
	//刷卡确认
	public String confirms(){
		ids= id.split(",");
		for (int i = 0; i < ids.length; i++) {
			itermediateTest=itermediateTestService.load(ids[i]);
			if(REPEAL.equals(itermediateTest.getState())){
				addActionError("已撤销的无法再撤销");
				return ERROR;
			}
		 admin=adminService.getLoginAdmin();
		 ItermediateTest persistent=itermediateTestService.load(id);
		 BeanUtils.copyProperties(itermediateTest, persistent, new String[] { "id","createUser"});
		 persistent.setState("2");
		 persistent.setConfirmUser(admin);
		 itermediateTestService.save(persistent);
		 redirectionUrl="itermediateTest!list.action?workingBillId="
				 +itermediateTest.getWorkingbill().getId();
		}
		return SUCCESS;
	}
	
	//刷卡撤销
		public String repeal(){
			ids= id.split(",");
			for (int i = 0; i < ids.length; i++) {
				itermediateTest=itermediateTestService.load(ids[i]);
				if(REPEAL.equals(itermediateTest.getState())){
					addActionError("已撤销的无法再确认");
					return ERROR;
				}
			 admin=adminService.getLoginAdmin();
			 ItermediateTest persistent=itermediateTestService.load(id);
			 BeanUtils.copyProperties(itermediateTest, persistent, new String[] { "id","createUser"});
			 persistent.setState("3");
			 persistent.setConfirmUser(admin);
			 itermediateTestService.save(persistent);
			 redirectionUrl="itermediateTest!list.action?workingBillId="
					 +itermediateTest.getWorkingbill().getId();
			}
			return SUCCESS;
		}

	
	

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}


	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
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


	public ItermediateTest getItermediateTest() {
		return itermediateTest;
	}


	public void setItermediateTest(ItermediateTest itermediateTest) {
		this.itermediateTest = itermediateTest;
	}


	public ItermediateTestService getItermediateTestService() {
		return itermediateTestService;
	}


	public void setItermediateTestService(ItermediateTestService itermediateTestService) {
		this.itermediateTestService = itermediateTestService;
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

	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}




    

	
	
}
