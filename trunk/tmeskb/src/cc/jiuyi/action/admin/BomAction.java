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

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

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
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private OrdersService ordersService;
	
	private List<Bom> bomList;
	private List<Dict> allType;
	private String productid;//产品ID
	private String bomId;
	//private List<cc.jiuyi.entity.Material> materialAll;
	
	
	//读取清单
	public String list(){
		return LIST;
	}
	
	// 读取list页面
	public String show() {
		return "show";
	}

	/**
	 * ajax List
	 * @return
	 */
	public String ajlist() {

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (pager.getOrderBy().equals("")) {
				pager.setOrderType(OrderType.desc);
				pager.setOrderBy("modifyDate");
			}
			if (pager.is_search() == true && filters != null) {
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map m = new HashMap();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}

			if (pager.is_search() == true && Param != null) {
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("materialCode") != null) {
					String materialCode = obj.getString("materialCode")
							.toString();
					map.put("materialCode", materialCode);
				}
				if (obj.get("materialName") != null) {
					String materialName = obj.getString("materialName")
							.toString();
					map.put("materialName", materialName);
				}
				if (obj.get("productsCode") != null) {
					String productsCode = obj.getString("productsCode")
							.toString();
					map.put("productsCode", productsCode);
				}
				if (obj.get("productsName") != null) {
					String productsName = obj.getString("productsName")
							.toString();
					map.put("productsName", productsName);
				}
				if (obj.get("oerderCode") != null) {
					String oerderCode = obj.getString("oerderCode")
							.toString();
					map.put("oerderCode", oerderCode);
				}
				if (obj.get("shift") != null) {
					String shift = obj.getString("shift")
							.toString();
					map.put("shift", shift);
				}
			}
						
			if(StringUtils.isNotEmpty(bomId) && !bomId.equalsIgnoreCase("")){//取出当前随工单下的所有产品bom
				List<String> orderList = getorderList();
				pager = bomService.findPagerByOrders(pager, map,orderList);
				List<Bom> bomList = pager.getList();
				for (int i = 0; i < bomList.size(); i++) {
					Bom bom = bomList.get(i);
//					bom.setMaterialCode(bom.getMaterialCode());
//					bom.setMaterialName(bom.getMaterialName());
					bom.setProductsCode(bom.getOrders().getMatnr());
					bom.setProductsName(bom.getOrders().getMaktx());
					bom.setOerderCode(bom.getOrders().getAufnr());
					bom.setAuart(bom.getOrders().getAuart());
					bom.setFactory(bom.getOrders().getFactory());
					bom.setGltrp(bom.getOrders().getGltrp());
					bom.setGstrp(bom.getOrders().getGstrp());
					bom.setMujuntext(bom.getOrders().getMujuntext());
				}
				pager.setList(bomList);
			}else{
				pager = bomService.findPagerByjqGrid(pager, map);
				List<Bom> list = pager.getList();
				for (int i = 0; i < list.size(); i++) {
					Bom bom = list.get(i);
//					bom.setMaterialCode(bom.getMaterialCode());
//					bom.setMaterialName(bom.getMaterialName());
					bom.setProductsCode(bom.getOrders().getMatnr());
					bom.setProductsName(bom.getOrders().getMaktx());
					bom.setOerderCode(bom.getOrders().getAufnr());
					bom.setAuart(bom.getOrders().getAuart());
					bom.setFactory(bom.getOrders().getFactory());
					bom.setGltrp(bom.getOrders().getGltrp());
					bom.setGstrp(bom.getOrders().getGstrp());
					bom.setMujuntext(bom.getOrders().getMujuntext());
				}
				pager.setList(list);
			}
			
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig
					.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Bom.class));
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// 获取产品的Bom
	public String getBom() {
//		bomList = bomService.getList("products.id", productid);
//		
//		
//		
//		
//		
//		JSONObject json = new JSONObject();
//		JSONArray jsonarray = new JSONArray();
//		for (int i = 0; i < bomList.size(); i++) {
//			Bom bom = bomList.get(i);
//			Products product = bom.getProducts();
//			int versionBefore = bom.getVersion();
//			int versionNow = bomService.getMaxVersionByid(productid);
//			if (versionBefore == versionNow) {
//				JSONObject jsonobject = new JSONObject();
//				jsonobject.put("productsid", product.getId());
//				jsonobject.put("productsCode", product.getProductsCode());
//				jsonobject.put("productsName", product.getProductsName());
//				jsonobject.put("productAmount", bom.getProductAmount());
//				jsonobject.put("materialCode", bom.getMaterialCode());
//				jsonobject.put("materialName", bom.getMaterialName());
//				jsonobject.put("materialUnit", bom.getMaterialUnit());
//				jsonobject.put("materialAmount", bom.getMaterialAmount());
//				jsonobject.put("isCarton", bom.getIsCarton());
//				jsonobject.put("version", bom.getVersion());
//				jsonarray.add(jsonobject);
//			}
//		}
//		json.put("list", jsonarray);
//		//System.out.println(json.toString());
//		return ajaxJson(json.toString());
		
		//TODO 此处有问题
		return null;
	}
	
	public String maintain(){	
		return "maintain";
	}
	
	public String save(){
		//bomService.mergeBom(bomList, productid);
		//TODO 保存BOM信息
		redirectionUrl="bom!show.action";
		return SUCCESS;
	}
	
	public List<WorkingBill> getWorkList(){
		Admin admin1 = adminService.getLoginAdmin();
		admin1 = adminService.get(admin1.getId());
		List list=workingBillService.getListWorkingBillByDate(admin1);
		return list;
	}
	
	public List<String> getorderList(){
		List<WorkingBill> workList=getWorkList();
		List<String> orderList = new ArrayList<String>();
		for(WorkingBill work:workList){
			Orders order = ordersService.get("aufnr",work.getAufnr());
			orderList.add(order.getId());
		}
		return orderList;
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
	
	public String getProductid() {
		return productid;
	}
	
	public void setProductid(String productid) {
		this.productid = productid;
	}

	public List<Dict> getAllType() {
		return dictService.getList("dictname", "iscartonSate");
	}

	public void setAllType(List<Dict> allType) {
		this.allType = allType;
	}

	public String getBomId() {
		return bomId;
	}

	public void setBomId(String bomId) {
		this.bomId = bomId;
	}

	
}
