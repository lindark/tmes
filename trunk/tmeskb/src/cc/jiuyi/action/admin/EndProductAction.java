package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.EndProductService;
import cc.jiuyi.service.WorkingBillService;

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
	
	public String add(){
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		String wareHouse = admin.getDepartment().getTeam().getFactoryUnit().getWarehouse();
		if(locationonsideList==null){
			locationonsideList = new ArrayList<Locationonside>();
		}
		//locationonsideList = rfc.findWarehouse(warehouse, materialCodeList);
		
		
		return INPUT;
	}

	public String creditSubmit(){
		endProductService.saveEndProduct(endProducts);
		return ajaxJsonSuccessMessage("保存成功!"); 
	}
	
	
	// 获取所有状态
	public List<Dict> getAllSite(){
		return dictService.getList("dictname", "reporterSite");
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
	

}
