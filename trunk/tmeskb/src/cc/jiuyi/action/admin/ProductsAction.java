package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Products;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-产品管理
 */

@ParentPackage("admin")
public class ProductsAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Products products;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private ProductsService productsService;
	@Resource
	private DictService dictService;
	
	//是否已存在ajax验证
	public String checkProductsCode(){
		String productsCode=products.getProductsCode();
		if(productsService.isExistByProductsCode(productsCode)){
			return ajaxText("false");
		}else{
			return ajaxText("true");
		}
	}
	
	//是否已存在ajax验证
	public String checkMaterialGroup(){
		String materialGroup=products.getMaterialGroup();
		if(productsService.isExistByMaterialGroup(materialGroup)){
			return ajaxText("false");
		}else{
			return ajaxText("true");
		}
	}
	
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
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
    public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
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
			if (obj.get("productsCode") != null) {
				System.out.println("obj=" + obj);
				String productsCode = obj.getString("productsCode").toString();
				map.put("productsCode", productsCode);
			}
			if (obj.get("productsName") != null) {
				String productsName = obj.getString("productsName").toString();
				map.put("productsName", productsName);
			}
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

			pager = productsService.getProductsPager(pager, map);
			List<Products> productsList = pager.getList();
			List<Products> lst = new ArrayList<Products>();
			for (int i = 0; i < productsList.size(); i++) {
				Products products = (Products) productsList.get(i);
				products.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "productsState", products.getState()));
				lst.add(products);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		productsService.updateisdel(ids, "Y");
		redirectionUrl = "products!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			products= productsService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			Products persistent = productsService.load(id);
			BeanUtils.copyProperties(products, persistent, new String[] { "id","createDate", "modifyDate"});
			productsService.update(persistent);
			redirectionUrl = "products!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "products.materialGroup", message = "物料组不允许为空!"),
					@RequiredStringValidator(fieldName = "products.materialDescript", message = "物料描述不允许为空!"),
					@RequiredStringValidator(fieldName = "products.productsCode", message = "产品编号不允许为空!"),
					@RequiredStringValidator(fieldName = "products.productsName", message = "产品名称不允许为空!")
			  }
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		productsService.save(products);
		redirectionUrl="products!list.action";
		return SUCCESS;	
	}
		


	public Products getProducts() {
		return products;
	}


	public void setProducts(Products products) {
		this.products = products;
	}


	public ProductsService getProductsService() {
		return productsService;
	}


	public void setProductsService(ProductsService productsService) {
		this.productsService = productsService;
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
	
	
}
