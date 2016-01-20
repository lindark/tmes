package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.ReturnProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.EndProductRfc;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.sap.rfc.ReturnProductRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReturnProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 成品入库
 */
@ParentPackage("admin")
public class ReturnProductAction extends BaseAdminAction {

	private static final long serialVersionUID = 5106762543110231271L;
	
	private Admin admin;
	private List<WorkingBill> workingBillList;
	private List<ReturnProduct> returnProducts;
	private List<Locationonside> locationonsideList;
	private String info;
	private String cardnumber;
	private ReturnProduct returnProduct;
	
	@Resource
	private ReturnProductService returnProductService;
	@Resource
	private AdminService adminService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private DictService dictService;
	@Resource
	private LocationonsideRfc rfc;
	@Resource
	private ReturnProductRfc reprfc;
	
	
	public String list(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
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
		
		pager = returnProductService.findByPager(pager);
		List<ReturnProduct> endProductList = pager.getList();
		List<ReturnProduct> lst = new ArrayList<ReturnProduct>();
		for (int i = 0; i < endProductList.size(); i++) {
			ReturnProduct endProduct = (ReturnProduct) endProductList.get(i);
			endProduct.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService,
					"returnProState", endProduct.getState()));
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
			List<Locationonside> locationonsideLists =new ArrayList<Locationonside>();
			if(!"".equals(info) && info!=null){
				int i = info.length();
				for(Locationonside los : locationonsideList){
					if(los.getMaterialCode().length()>=i){
						String s = los.getMaterialCode().substring(0, i);
						if(info.equals(s)){
							locationonsideLists.add(los);
						}
					}
				}
				locationonsideList = locationonsideLists;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}
		
		
		return INPUT;
	}
	public String edit(){
		if(returnProduct==null){
			returnProduct = new ReturnProduct();
		}
		returnProduct = returnProductService.get(id);
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		String wareHouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();
		String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		if(returnProduct.getMaterialBatch()==null){
			returnProduct.setMaterialBatch("");
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
				if(ls.getLocationCode().equals(returnProduct.getRepertorySite()) && ls.getMaterialCode().equals(returnProduct.getMaterialCode()) && ls.getCharg().equals(returnProduct.getMaterialBatch())){
					returnProduct.setActualMaterialMount(new Double(ls.getAmount()));
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
			returnProductService.updateEidtReturnProduct(id, admin,returnProduct,info);
			return ajaxJsonSuccessMessage("修改成功");
		} catch (Exception e) {
			return ajaxJsonErrorMessage("修改失败，请重试");
		}
	}
	public String creditSubmit(){
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			returnProductService.saveReturnProduct(returnProducts,info,admin);
			return ajaxJsonSuccessMessage("保存成功!"); 
		} catch (Exception e) {
			return ajaxJsonErrorMessage("保存失败，请重试");
		}
		
	}
	public String creditApproval(){
		String message = "";
		List<ReturnProduct> returnProductCrt = new ArrayList<ReturnProduct>();
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			//endProductService.updateApprovalEndProduct(ids,admin);
			List<ReturnProduct> returnProductList = new ArrayList<ReturnProduct>();
			String[] ids = id.split(","); 
			for(int i=0;i<ids.length;i++){
				ReturnProduct rp = returnProductService.get(ids[i]);
				if( rp!=null){
					if(rp.getMblnr()!=null && !"".equals(rp.getMblnr()))
						continue;
					rp.setConfirmUser(admin.getUsername());
					rp.setConfirmName(admin.getName());
					rp.setState("2");
					rp.setBudate(ThinkWayUtil.SystemDate());
					rp.setWerks(admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());
					rp.setMoveType("311");
					returnProductList.add(rp);
				}
			}
			returnProductCrt = reprfc.returnProductCrt("X", returnProductList);
			boolean flag = true;
			for(ReturnProduct epc : returnProductCrt){
				String e_type = epc.getE_type();
				if (e_type.equals("E")) { // 如果有一行发生了错误
					flag = false;
					message += epc.getE_message();
				}
			}
				if (!flag)
					return ajaxJsonErrorMessage(message);
				else {
					flag = true;
					returnProductCrt = reprfc.returnProductCrt("", returnProductList);
					for(ReturnProduct epc : returnProductCrt){
						String e_type = epc.getE_type();
						String e_message = epc.getE_message();
						String ex_mblnr = epc.getEx_mblnr();
						if (e_type.equals("E")) { // 如果有一行发生了错误
							flag = false;
							message += epc.getE_message();
						}else{
							ReturnProduct ep = returnProductService.get(epc.getId());
							ep.setMblnr(ex_mblnr);
							returnProductService.update(ep);
						}
					}
					if (!flag)
						return ajaxJsonErrorMessage(message);
			}
			return ajaxJsonSuccessMessage("保存成功!"); 
		} catch (Exception e) {
			return ajaxJsonErrorMessage("确认失败，请重试");
		}
		
	}
	public String view(){
		if(returnProduct==null){
			returnProduct = new ReturnProduct();
		}
		returnProduct = returnProductService.get(id);
		return VIEW;
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
	public List<ReturnProduct> getReturnProducts() {
		return returnProducts;
	}
	public void setReturnProducts(List<ReturnProduct> returnProducts) {
		this.returnProducts = returnProducts;
	}
	public ReturnProduct getReturnProduct() {
		return returnProduct;
	}
	public void setReturnProduct(ReturnProduct returnProduct) {
		this.returnProduct = returnProduct;
	}
	
	
	

}
