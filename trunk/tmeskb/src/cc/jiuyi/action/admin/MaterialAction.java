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
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Products;
import cc.jiuyi.sap.rfc.MatnrRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类-产品Bom管理
 */

@ParentPackage("admin")
public class MaterialAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Material material;
	private Products products;

	private Admin admin;
	
	//获取所有状态
	private List<Dict> allState;
	private String productsCode;
	private String productsName;
	private String productsid;
	private List<Material> materialList;
	private List<Factory> factoryList;
	
	@Resource
	private AdminService adminService;
	@Resource
	private MaterialService materialService;
	@Resource
	private DictService dictService;
	@Resource
	private ProductsService productsService;
	@Resource
	private MatnrRfc matnrrfc;
	@Resource
	private FactoryService factoryservice;
	
	
	//添加
	public String add(){
		return INPUT;
	}

	//是否已存在ajax验证
	public String checkMaterialCode(){
		String materialCode=material.getMaterialCode();
		//System.out.println("组件编码为:"+material.getMaterialCode());
		if(materialService.isExistByMaterialCode(materialCode)){
			return ajaxText("false");
		}else{
			return ajaxText("true");
		}
	}
	
	
	public String getList(){
		List<Products> list=productsService.getAll();
		List<Map<String,Object>> optionList=new ArrayList<Map<String,Object>>();
		for(Products products:list){
			Map<String,Object>map=new HashMap<String,Object>();
			map.put("productCodes", products.getProductsCode());
			optionList.add(map);
		}
		JSONArray jsonArray=JSONArray.fromObject(optionList);		
		return ajaxJson(jsonArray.toString());	
	}
	
	public String browser(){
		return "browser";
	}
	
	//列表
	public String list()
	{
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if(pager==null)
		{
			pager=new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
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
			if (obj.get("materialCode") != null) {
				String materialCode = obj.getString("materialCode").toString();
				map.put("materialCode", materialCode);
			}
			if (obj.get("materialName") != null) {
				String materialName = obj.getString("materialName").toString();
				map.put("materialName", materialName);
			}
		}
            List<HashMap> list=new ArrayList<HashMap>();
			pager = materialService.getMaterialPager(pager, map);
			List<Material> materialList = pager.getList();
			List<Material> lst = new ArrayList<Material>();
			for (int i = 0; i < materialList.size(); i++) {
				Material material  = (Material)materialList.get(i);
				
				if(material.getFactoryunit()!=null)
				{
					material.setXfactoryunit(material.getFactoryunit().getFactoryUnitName());//单元名称
					if(material.getFactoryunit().getWorkShop()!=null)
					{
						material.setXworkshop(material.getFactoryunit().getWorkShop().getWorkShopName());//车间名称
						if(material.getFactoryunit().getWorkShop().getFactory()!=null)
						{
							material.setXfactory(material.getFactoryunit().getWorkShop().getFactory().getFactoryName());//工厂名称
						}
					}
				}
				lst.add(material);
			}
		pager.setList(lst);
		JsonConfig jsonConfig=new JsonConfig(); 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Material.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
public String getQualityBomByFactoryunit(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		HashMap<String, String> map = new HashMap<String, String>();
		pager.set_search(true);
		map.put("factoryunitId",admin.getTeam().getFactoryUnit().getId());
		if(pager==null)
		{
			pager=new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
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
			if (obj.get("materialCode") != null) {
				String materialCode = obj.getString("materialCode").toString();
				map.put("materialCode", materialCode);
			}
			if (obj.get("materialName") != null) {
				String materialName = obj.getString("materialName").toString();
				map.put("materialName", materialName);
			}
		}
            List<HashMap> list=new ArrayList<HashMap>();
			pager = materialService.getMaterialPager(pager, map);
			List<Material> materialList = pager.getList();
			List<Material> lst = new ArrayList<Material>();
			for (int i = 0; i < materialList.size(); i++) {
				Material material  = (Material)materialList.get(i);
				
				if(material.getFactoryunit()!=null)
				{
					material.setXfactoryunit(material.getFactoryunit().getFactoryUnitName());//单元名称
					if(material.getFactoryunit().getWorkShop()!=null)
					{
						material.setXworkshop(material.getFactoryunit().getWorkShop().getWorkShopName());//车间名称
						if(material.getFactoryunit().getWorkShop().getFactory()!=null)
						{
							material.setXfactory(material.getFactoryunit().getWorkShop().getFactory().getFactoryName());//工厂名称
						}
					}
				}
				lst.add(material);
			}
		pager.setList(lst);
		JsonConfig jsonConfig=new JsonConfig(); 
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Material.class));//排除有关联关系的属性字段 
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		materialService.delete(ids);
		redirectionUrl = "material!list.action";
		return SUCCESS;
	}

	
	//编辑
	public String edit(){
		material= materialService.load(id);
		return INPUT;	
	}
	
	public String update() {
		Material persistent = materialService.load(id);
		BeanUtils.copyProperties(material, persistent, new String[] { "id","createDate", "modifyDate"});
		materialService.update(persistent);
		redirectionUrl = "material!list.action";
		return SUCCESS;
	}
		
	//保存
	
	public String save()throws Exception{		
		materialService.save(material);
		redirectionUrl="material!list.action";
		return SUCCESS;	
	}
	
	// 同步
	public String sync() {
		try {
			List<Material> materialList = matnrrfc.getMaterialList("", "1000", "");
			materialService.mergeMaterialList(materialList);
		} catch (IOException e) {
			addActionError("IO写入失败!");
			e.printStackTrace();
			return ERROR;
		} catch (CustomerException e) {
			addActionError(e.getMessage());
			e.printStackTrace();
			return ERROR;
		} catch(Exception e){
			addActionError("系统出现错误，请联系系统管理员");
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}


	public Material getMaterial() {
		return material;
	}


	public void setMaterial(Material material) {
		this.material = material;
	}


	public MaterialService getMaterialService() {
		return materialService;
	}


	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
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
	
	//获取所有产品
	public List<Products> getProductsList(){
		return productsService.getAll();		
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public String getProductsCode() {
		return productsCode;
	}

	public void setProductsCode(String productsCode) {
		this.productsCode = productsCode;
	}

	public String getProductsid() {
		return productsid;
	}

	public void setProductsid(String productsid) {
		this.productsid = productsid;
	}

	public ProductsService getProductsService() {
		return productsService;
	}

	public void setProductsService(ProductsService productsService) {
		this.productsService = productsService;
	}

	public String getProductsName() {
		return productsName;
	}

	public void setProductsName(String productsName) {
		this.productsName = productsName;
	}

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
		this.materialList = materialList;
	}

	public List<Factory> getFactoryList() {
		this.factoryList = factoryservice.getAll();
		return factoryList;
	}

	public void setFactoryList(List<Factory> factoryList) {
		this.factoryList = factoryList;
	}


	
	
	
}
