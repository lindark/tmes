package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Cause;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.ItermediateTest;
import cc.jiuyi.entity.ItermediateTestDetail;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CauseService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ItermediateTestDetailService;
import cc.jiuyi.service.ItermediateTestService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-领/退料从表
 */

@ParentPackage("admin")
public class ItermediateTestDetailAction extends BaseAdminAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8353535527507793596L;
	
	private ItermediateTestDetail itermediateTestDetail;
	//获取所有状态
	private List<Dict> allState;
	private List<Cause> list_cause;//缺陷代码
	
	private ItermediateTest itermediateTest;
	
	@Resource
	private ItermediateTestService itermediateTestService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private MaterialService materialService;
	@Resource
	private ProductsService productsServce;
	@Resource
	private ItermediateTestDetailService itermediateTestDetailService;
	@Resource
	private CauseService causeService;
	
	private WorkingBill workingbill;
	private String workingBillId;
	
	
	public String getWokingBillId() {
		return workingBillId;
	}
	public void setWokingBillId(String wokingBillId) {
		this.workingBillId = wokingBillId;
	}


	private Admin admin;
	private Material material;
	private String matnr;
    private List<Material> materialList;
    private List<ItermediateTestDetail> itermediateTestDetailList;
//    private List<PickDetail> pkList;
    

    
	//添加
	public String add(){
		workingbill=workingBillService.get(workingBillId);
		list_cause=causeService.getBySample("3"); //获取缺陷表中关于半成品巡检的内容
		return INPUT;
	}

	//列表
	public String list(){
		workingbill=workingBillService.get(workingBillId);
		list_cause=causeService.getBySample("3"); //获取缺陷表中关于半成品巡检的内容
		Admin admin=adminService.getLoginAdmin();
		admin=adminService.get(admin.getId());
		Products products=productsServce.getProducts(matnr);
		materialList=new ArrayList<Material>(products.getMaterial());
		workingbill = workingBillService.get(workingBillId);	
		return LIST;
	}
	
    //查看页面
	public String view(){
		workingbill=workingBillService.get(workingBillId);
		list_cause=causeService.getBySample("3"); //获取缺陷表中关于半成品巡检的内容
		return VIEW;
	}


	// 列表
	public String browser() {
		list_cause=causeService.getBySample("3"); //获取缺陷表中关于半成品巡检的内容
		return "browser";
	 }
	
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){

		List<Material> itermediateTestDetail = materialService.getMantrBom(matnr);
		List<HashMap> list=new ArrayList<HashMap>();
		for (int i = 0; i < itermediateTestDetail.size(); i++) {
			Material mate=itermediateTestDetail.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("id", mate.getId());
			map.put("materialCode", mate.getMaterialCode());
			map.put("materialName", mate.getMaterialName());
			list.add(map);
		}
		HashMap map1 = new HashMap();
		map1.put("list", list);
		JSONArray jsonArray = JSONArray.fromObject(map1);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		itermediateTestDetailService.updateisdel(ids, "Y");
		redirectionUrl = "pick_detail!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			itermediateTestDetail= itermediateTestDetailService.load(id);
			workingbill=workingBillService.get(workingBillId);
			list_cause=causeService.getBySample("3");//获取缺陷表中关于半成品巡检的内容
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			ItermediateTestDetail persistent = itermediateTestDetailService.load(id);
			BeanUtils.copyProperties(itermediateTestDetail, persistent, new String[] { "id","createDate", "modifyDate"});
			itermediateTestDetailService.update(persistent);
			redirectionUrl = "pick_detail!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					//@RequiredStringValidator(fieldName = "itermediateTestDetail.pickType", message = "领料类型不能为空!"),
					
			  },
			intRangeFields = {
					@IntRangeFieldValidator(fieldName = "itermediateTestDetail.pickAmount", min = "0", message = "领料数量不能为空!")
				
			}
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		workingbill = workingBillService.get(workingBillId);
		itermediateTestDetailService.save(itermediateTestDetailList,workingBillId);
		redirectionUrl="pick!list.action?workingBillId="+workingBillId;
		return SUCCESS;
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


	public WorkingBill getWorkingbill() {
		return workingbill;
	}


	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}


	public String getMatnr() {
		return matnr;
	}


	public void setMatnr(String matnr) {
		this.matnr = matnr;
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

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}



	public Admin getAdmin() {
		return admin;
	}



	public void setAdmin(Admin admin) {
		this.admin = admin;
	}



	public Material getMaterial() {
		return material;
	}



	public void setMaterial(Material material) {
		this.material = material;
	}


	


	public List<Material> getMaterialList() {
		return materialList;
	}


	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}


	public String getWorkingBillId() {
		return workingBillId;
	}


	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}
	public ItermediateTestDetail getItermediateTestDetail() {
		return itermediateTestDetail;
	}
	public void setItermediateTestDetail(ItermediateTestDetail itermediateTestDetail) {
		this.itermediateTestDetail = itermediateTestDetail;
	}
	public ItermediateTest getItermediateTest() {
		return itermediateTest;
	}
	public void setItermediateTest(ItermediateTest itermediateTest) {
		this.itermediateTest = itermediateTest;
	}
	public List<ItermediateTestDetail> getItermediateTestDetailList() {
		return itermediateTestDetailList;
	}
	public void setItermediateTestDetailList(
			List<ItermediateTestDetail> itermediateTestDetailList) {
		this.itermediateTestDetailList = itermediateTestDetailList;
	}
	public List<Cause> getList_cause() {
		return list_cause;
	}
	public void setList_cause(List<Cause> list_cause) {
		this.list_cause = list_cause;
	}
	
	
	
}
