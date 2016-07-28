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

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 工艺路线
 */
@ParentPackage("admin")
public class ProcessRouteAction extends BaseAdminAction {

	private static final long serialVersionUID = 2850935142585121482L;
	@Resource
	private ProcessRouteService processrouteservice;
	@Resource
	private ProcessService processservice;
	@Resource
	private WorkingBillService workingbillService;
	@Resource
	private ProductsService productsService;

	private List<ProcessRoute> processrouteList;
	private String productid;// 产品ID
	private List<cc.jiuyi.entity.Process> processAll;

	// 读取清单
	public String list() {
		return LIST;
	}

	// 获取产品的工艺路线
	public String getRoute() {
//		processrouteList = processrouteservice
//				.getList("products.id", productid);
//		JSONObject json = new JSONObject();
//		JSONArray jsonarray = new JSONArray();
//		for (int i = 0; i < processrouteList.size(); i++) {
//			ProcessRoute processroute = processrouteList.get(i);
//			Products product = processroute.getProducts();
//			int version1 = processroute.getVersion();
//			int version2 = processrouteservice.getMaxVersion(productid);
//			if (version1 == version2) {
//				cc.jiuyi.entity.Process process = processservice.get("processCode",processroute.getProcessCode());
//				JSONObject jsonobject = new JSONObject();
//				jsonobject.put("productsid", product.getId());
//				jsonobject.put("productsCode", product.getProductsCode());
//				jsonobject.put("productsName", product.getProductsName());
//				jsonobject.put("processId", process.getId());
//				jsonobject.put("processCode", process.getProcessCode());
//				jsonobject.put("processName", process.getProcessName());
//				jsonobject.put("productsNum", processroute.getProductAmount());
//				jsonobject.put("sortcode", processroute.getSortcode());
//				jsonobject.put("unit", processroute.getUnit());
//				jsonobject.put("version", processroute.getVersion());
//				jsonobject.put("workCenter", processroute.getWorkCenter());
//				jsonarray.add(jsonobject);
//			}
//		}
//		json.put("list", jsonarray);
//		System.out.println(json.toString());
//		return ajaxJson(json.toString());
		return null;
	}
	
	
	// 读取list页面
	public String show() {
		return "show";
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {

		HashMap<String, String> map = new HashMap<String, String>();
		try {
			if (pager.getOrderBy().equals("")) {
				pager.setOrderType(OrderType.desc);
				pager.setOrderBy("modifyDate");
			}
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
				if (obj.get("processCode") != null) {
					//System.out.println("obj=" + obj);
					String processCode = obj.getString("processCode")
							.toString();
					map.put("processCode", processCode);
				}
				if (obj.get("processName") != null) {
					String processName = obj.getString("processName")
							.toString();
					map.put("processName", processName);
				}
             /**if (obj.get("productsCode") != null) {
					String productsCode = obj.getString("productsCode")
							.toString();
					map.put("productsCode", productsCode);
				}
				if (obj.get("productsName") != null) {
					String productsName = obj.getString("productsName")
							.toString();
					map.put("productsName", productsName);
				}  **/
			}

			pager = processrouteservice.findPagerByjqGrid(pager, map);
			List<ProcessRoute> list = pager.getList();
			for (int i = 0; i < list.size(); i++) {
				ProcessRoute p = list.get(i);
				p.setAufnr(p.getOrders().getAufnr());
				p.setGstrp(p.getOrders().getGstrp());
				p.setGltrp(p.getOrders().getGltrp());
				p.setMatnr(p.getOrders().getMatnr());
				p.setMaktx(p.getOrders().getMaktx());
				//p.setMujuntext(p.getOrders().getMujuntext());
			}
			pager.setList(list);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil
					.getExcludeFields(ProcessRoute.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public String maintain() {

		return "maintain";
	}

	public String save() {
		//processrouteservice.mergeProcessroute(processrouteList, productid);
		return SUCCESS;
	}

	public String update() {

		return null;
	}

	public String add() {

		return null;
	}

	public String edit() {

		return null;
	}

	public List<ProcessRoute> getProcessrouteList() {
		return processrouteList;
	}

	public void setProcessrouteList(List<ProcessRoute> processrouteList) {
		this.processrouteList = processrouteList;
	}

	public List<cc.jiuyi.entity.Process> getProcessAll() {
		return processservice.getAll();
	}

	public void setProcessAll(List<cc.jiuyi.entity.Process> processAll) {
		this.processAll = processAll;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	// 根据产品productCode查询对应工序
	public String getProcessList() {
		//List<Process> processList = processService.findProcessByProductsId(id);
		WorkingBill workingBill = workingbillService.get(id);
		Products products = productsService.getByPcode(workingBill.getMatnr());
		String time = ThinkWayUtil.formatdateDate(products.getCreateDate());
		List<ProcessRoute> processrouteList=processrouteservice.findProcessRoute(workingBill.getAufnr(),time);
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		for (ProcessRoute o : processrouteList) {
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("id", o.getProcessCode());
			maps.put("name", o.getProcessName());
			lists.add(maps);
		}
		JSONArray json = JSONArray.fromObject(lists);
		return ajaxText(json.toString());
	}	
	
	
	/*public List<ProcessRoute> getProcessList(String productCode){
		return processrouteservice.getAllProcessRouteByProductCode(productCode);
	}*/

}
