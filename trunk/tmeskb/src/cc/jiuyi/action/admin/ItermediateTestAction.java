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
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.IpRecord;
import cc.jiuyi.entity.ItermediateTest;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Rework;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ItermediateTestDetailService;
import cc.jiuyi.service.ItermediateTestService;
import cc.jiuyi.service.ProductsService;
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
	private Admin admin;//人员
	private String workingBillId;//随工单ID
	private WorkingBill workingbill;//随工单
	private String add;//新增时
	private String edit;//编辑时
	private String show;//查看时
	private Products product;//产品
	private String my_id;//
	private List<Dict> allState;//获取所有状态
	private List<Material> list_material;//产品Bom
	private List<Cause> list_cause;//缺陷
	private List<ItermediateTestDetail> list_itmesg;//巡检从表不合格信息
	private List<IpRecord> list_itbug;//不合格原因
	
	
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
	@Resource
	private ProductsService productsService;
	@Resource
	private CauseService causeService;
	
	

	//添加
	public String add(){
		this.workingbill=this.workingBillService.load(workingBillId);
		this.product=this.productsService.getProducts(workingbill.getMatnr());//随工单对应的产品
		this.list_material=new ArrayList<Material>(this.product.getMaterial());//产品对应的物料
		this.list_cause=this.causeService.getBySample("3");//半成品不合格内容
		this.add="add";
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
		redirectionUrl = "itermediateTest!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			this.list_material=new ArrayList<Material>();
			this.workingbill=this.workingBillService.load(workingBillId);
			this.product=this.productsService.getProducts(workingbill.getMatnr());
			List<Material>l_material=new ArrayList<Material>( this.product.getMaterial());
			for(int i=0;i<l_material.size();i++){
				Material m=l_material.get(i);
				ItermediateTestDetail it=this.itermediateTestDetailService.getBySidAndMid(id, m.getId());
				if(it!=null){
					m.setXfailReason(it.getFailReason());
					m.setXfailAmount(it.getFailAmount());
					m.setXtestAmount(it.getTestAmount());
					m.setXgoodsSzie1(it.getGoodsSzie1());
					m.setXgoodsSzie2(it.getGoodsSzie2());
					m.setXgoodsSzie3(it.getGoodsSzie3());
					m.setXgoodsSzie4(it.getGoodsSzie4());
					m.setXgoodsSzie5(it.getGoodsSzie5());
					m.setXitid(it.getId());
					List<IpRecord> l_ir=new ArrayList<IpRecord>(it.getIpRecord());//获取每个物料对应的不合格原因
					String sbids="",sbnums="";
					for (int j = 0; j < l_ir.size(); j++) {
						IpRecord ip=l_ir.get(j);
						if(ip!=null){
							sbids=sbids+ip.getCauseId()+",";
							sbnums=sbnums+ip.getRecordNum()+",";
						}
					}
					m.setXrecordid(sbids);
					m.setXrecordNum(sbnums);				
				}
				list_material.add(m);
			}
			this.list_cause=this.causeService.getBySample("3");
			this.itermediateTest=this.itermediateTestService.load(id);
            this.edit="edit";
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			this.itermediateTestService.updateAll(itermediateTest, list_itmesg, list_itbug,my_id);
			redirectionUrl = "itermediate_test!list.action?workingBillId="+this.itermediateTest.getWorkingbill().getId();
			return SUCCESS;
		}
		
	//刷卡保存
	@Validations(
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		this.itermediateTestService.saveSubmit(itermediateTest, list_itmesg, list_itbug,my_id);
		redirectionUrl="itermediate_test!list.action?workingBillId="+this.itermediateTest.getWorkingbill().getId();
		return SUCCESS;	
	}
		
	//刷卡确认
	public String creditapproval(){
		admin=adminService.getLoginAdmin();
		ids= id.split(",");
		List<ItermediateTest> list=itermediateTestService.get(ids);
		for (int i = 0; i < ids.length; i++) {
			ItermediateTest it=list.get(i);
			if("2".equals(it.getState())){
				return ajaxJsonErrorMessage("已经确认的无法再确认!");
			}
			if("3".equals(it.getState())){
				return ajaxJsonErrorMessage("已撤销的无法再确认!");
			}
			List<ItermediateTestDetail> list1=new ArrayList<ItermediateTestDetail>(it.getItermediateTestDetail());
			if(list1.size()==0){
				return ajaxJsonErrorMessage("半成品巡检表为空,不能确认!");
			}
		}
		itermediateTestService.updateState(list, "2");
		return ajaxJsonSuccessMessage("您的操作已成功!");	
	}
	
	//刷卡撤销
		public String creditundo(){
		    admin=adminService.getLoginAdmin();
			ids= id.split(",");
			List<ItermediateTest> list=itermediateTestService.get(ids);
			for (int i = 0; i < ids.length; i++) {
				ItermediateTest it=list.get(i);
				if("3".equals(it.getState())){
					return ajaxJsonErrorMessage("已撤销的无法再撤销!");
				}
			}
			itermediateTestService.updateState(list, "3");
			return ajaxJsonSuccessMessage("您的操作已成功!");			
		}

		
		
	public String show(){
		this.workingbill=this.workingBillService.get(workingBillId);
		this.list_itmesg=new ArrayList<ItermediateTestDetail>();
		this.itermediateTest=this.itermediateTestService.load(id);
		List<ItermediateTestDetail>list_itmesg=new ArrayList<ItermediateTestDetail>(this.itermediateTest.getItermediateTestDetail());
		if(list_itmesg.size()>0){
			for (int i = 0; i < list_itmesg.size(); i++) {
				ItermediateTestDetail it=list_itmesg.get(i);
				this.list_itmesg.add(it);
			}
		}
		this.show="show";
		return INPUT;
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


	public Products getProduct() {
		return product;
	}


	public void setProduct(Products product) {
		this.product = product;
	}


	public List<Cause> getList_cause() {
		return list_cause;
	}


	public void setList_cause(List<Cause> list_cause) {
		this.list_cause = list_cause;
	}


	public List<ItermediateTestDetail> getList_itmesg() {
		return list_itmesg;
	}


	public void setList_itmesg(List<ItermediateTestDetail> list_itmesg) {
		this.list_itmesg = list_itmesg;
	}


	public List<IpRecord> getList_itbug() {
		return list_itbug;
	}


	public void setList_itbug(List<IpRecord> list_itbug) {
		this.list_itbug = list_itbug;
	}


	public List<Material> getList_material() {
		return list_material;
	}


	public void setList_material(List<Material> list_material) {
		this.list_material = list_material;
	}


	public String getAdd() {
		return add;
	}


	public void setAdd(String add) {
		this.add = add;
	}


	public String getEdit() {
		return edit;
	}


	public void setEdit(String edit) {
		this.edit = edit;
	}


	public String getShow() {
		return show;
	}


	public void setShow(String show) {
		this.show = show;
	}


	public String getMy_id() {
		return my_id;
	}


	public void setMy_id(String my_id) {
		this.my_id = my_id;
	}

	
	
}
