package cc.jiuyi.action.admin;

import java.io.IOException;
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

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.dao.EndProductDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.EndProductRfc;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.EndProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 成品入库
 */

@ParentPackage("admin")
public class EndProductAction extends BaseAdminAction {

	
	private static final long serialVersionUID = -8081201099604396846L;
	private Admin admin;
	private List<WorkingBill> workingBillList;
	private List<EndProduct> endProducts;
	private List<Locationonside> locationonsideList;
	private String info;
	private String cardnumber;
	private EndProduct endProduct;
	
	@Resource
	private EndProductService endProductService;
	@Resource
	private AdminService adminService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private DictService dictService;
	@Resource
	private LocationonsideRfc rfc;
	@Resource
	private EndProductRfc eprfc;
	
	
	public String list(){
		admin = adminService.getLoginAdmin();
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行成本入库操作!");
			return ERROR;
		}
		admin = adminService.get(admin.getId());
		return LIST;
	}
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {

	
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		/*HashMap<String, String> map = new HashMap<String, String>();
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
		}*/
		pager = endProductService.findByPager(pager);
		List<EndProduct> endProductList = pager.getList();
		List<EndProduct> lst = new ArrayList<EndProduct>();
		for (int i = 0; i < endProductList.size(); i++) {
			EndProduct endProduct = (EndProduct) endProductList.get(i);
			endProduct.setState(ThinkWayUtil.getDictValueByDictKey(dictService,
					"endProState", endProduct.getState()));
			if (endProduct.getConfirmName() != null) {
				endProduct.setConfirmName(endProduct.getConfirmName());
			}
			if (endProduct.getCreateName() != null) {
				endProduct.setCreateName(endProduct.getCreateName());
			}
			lst.add(endProduct);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Pick.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	public String add(){
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		String wareHouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		if(locationonsideList==null){
			locationonsideList = new ArrayList<Locationonside>();
		}
		try {
			locationonsideList = rfc.findWarehouse(wareHouse, werks);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}
		
		
		return INPUT;
	}
	public String edit(){
		if(endProduct==null){
			endProduct = new EndProduct();
		}
		endProduct = endProductService.get(id);
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		String wareHouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		if(endProduct.getMaterialBatch()==null){
			endProduct.setMaterialBatch("");
		}
		if(locationonsideList==null){
			locationonsideList = new ArrayList<Locationonside>();
		}
		try {
			locationonsideList = rfc.findWarehouse(wareHouse, werks);
			for(Locationonside ls : locationonsideList){
				if(ls.getCharg()==null){
					ls.setCharg("");
				}
				if(ls.getLocationCode().equals(endProduct.getRepertorySite()) && ls.getMaterialCode().equals(endProduct.getMaterialCode()) && ls.getCharg().equals(endProduct.getMaterialBatch())){
					endProduct.setActualMaterialMount(new Double(ls.getAmount()));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}
		
		return INPUT;
	}
	public String update(){
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			endProductService.updateEidtEndProduct(id, admin,endProduct,info);
			return ajaxJsonSuccessMessage("修改成功");
		} catch (Exception e) {
			return ajaxJsonErrorMessage("修改失败，请重试");
		}
	}
	public String creditSubmit(){
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			endProductService.saveEndProduct(endProducts,info,admin);
			return ajaxJsonSuccessMessage("保存成功!"); 
		} catch (Exception e) {
			return ajaxJsonErrorMessage("保存失败，请重试");
		}
		
	}
	public String creditApproval(){
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			//endProductService.updateApprovalEndProduct(ids,admin);
			List<EndProduct> endProductList = new ArrayList<EndProduct>();
			for(String id : ids){
				EndProduct ed = endProductService.get(id);
				if( ed!=null){
					ed.setConfirmUser(admin.getUsername());
					ed.setConfirmName(admin.getName());
					ed.setState("2");
					endProductList.add(ed);
				}
			}
			List<EndProduct> EndProductCrt = eprfc.EndProductCrt("", endProductList);
			
			return ajaxJsonSuccessMessage("保存成功!"); 
		} catch (Exception e) {
			return ajaxJsonErrorMessage("确认失败，请重试");
		}
		
	}
	
	// 获取所有状态
	public List<Dict> getAllSite(){
		return dictService.getList("dictname", "reporterSite");
	}
	// 获取所有类型
	public List<Dict> getAllType(){
		return dictService.getList("dictname", "endProductType");
	}
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<WorkingBill> getWorkingBillList() {
		return workingBillList;
	}

	public void setWorkingBillList(List<WorkingBill> workingBillList) {
		this.workingBillList = workingBillList;
	}

	public List<EndProduct> getEndProducts() {
		return endProducts;
	}

	public void setEndProducts(List<EndProduct> endProducts) {
		this.endProducts = endProducts;
	}




	public String getInfo() {
		return info;
	}




	public void setInfo(String info) {
		this.info = info;
	}
	public List<Locationonside> getLocationonsideList() {
		return locationonsideList;
	}
	public void setLocationonsideList(List<Locationonside> locationonsideList) {
		this.locationonsideList = locationonsideList;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public EndProduct getEndProduct() {
		return endProduct;
	}
	public void setEndProduct(EndProduct endProduct) {
		this.endProduct = endProduct;
	}
	

}
