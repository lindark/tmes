package cc.jiuyi.action.admin;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Products;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.MaterialService;

/**
 * 后台Action类 - Bom
 */
@ParentPackage("admin")
public class BomAction extends BaseAdminAction {

	
	private static final long serialVersionUID = 1626845752616353688L;
	
	@Resource
	private BomService bomService;
	@Resource
	private MaterialService materialService;
	
	private List<Bom> bomList;
	private String productid;//产品ID
	private List<cc.jiuyi.entity.Material> materialAll;
	
	//读取清单
	public String list(){
		return LIST;
	}
	
	//获取产品的Bom
	public String getBom(){
		bomList = bomService.getList("products.id", productid);
		JSONObject json = new JSONObject();
		JSONArray jsonarray = new JSONArray();
		for(int i=0;i<bomList.size();i++){
			Bom bom = bomList.get(i);
			Products product = bom.getProducts();
			cc.jiuyi.entity.Material material = bom.getMaterial();
			JSONObject jsonobject = new JSONObject();
			jsonobject.put("productsid", product.getId());
			jsonobject.put("productsCode", product.getProductsCode());
			jsonobject.put("productsName", product.getProductsName());
			jsonobject.put("bomCode", material.getMaterialCode());
			jsonobject.put("bomName", material.getMaterialName());
			jsonobject.put("bomUnit", material.getMaterialUnit());
			jsonobject.put("bomAmount", material.getMaterialAmount());
			jsonarray.add(jsonobject);
		}
		json.put("list", jsonarray);
		System.out.println(json.toString());
		return ajaxJson(json.toString());
	}
	
	public String maintain(){	
		return "maintain";
	}
	
	public String save(){
		bomService.mergeBom(bomList, productid);
		return SUCCESS;
	}
	
	public String update(){
		
		return null;
	}
	
	public String add(){
		
		return null;
	}
	
	public String edit(){
		
		return null;
	}

	public List<Bom> getBomList() {
		return bomList;
	}
	public void setBomList(List<Bom> bomList) {
		this.bomList = bomList;
	}
	public List<cc.jiuyi.entity.Material> getMaterialAll() {
		return materialService.getAll();
	}
	public void setMaterialAll(List<cc.jiuyi.entity.Material> materialAll) {
		this.materialAll = materialAll;
	}
	public String getProductid() {
		return productid;
	}
	
	public void setProductid(String productid) {
		this.productid = productid;
	}

	
}
