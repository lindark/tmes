package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
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
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类-工序管理
 */

@ParentPackage("admin")
public class ProcessAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private Process process;
	private List<Products> productsList;
	// 获取所有状态
	private List<WorkingBill> ar;
	private String info;
	private Products products;
	@Resource
	private ProcessService processService;
	@Resource
	private DictService dictService;
	@Resource
	private ProductsService productsService;
	private List<Products> productslist;

	// 是否已存在ajax验证
	public String checkProcesssCode() {
		String processCode = process.getProcessCode();
		if (processService.isExistByProccessCode(processCode)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}

	// 添加
	public String add() {
		// ar=getIdsAndNames();
		return INPUT;
	}

	// 列表
	public String list() {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		return LIST;
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
					System.out.println("obj=" + obj);
					String processCode = obj.getString("processCode")
							.toString();
					map.put("processCode", processCode);
				}
				if (obj.get("processName") != null) {
					String processName = obj.getString("processName")
							.toString();
					map.put("processName", processName);
				}
				if (obj.get("xproductnum") != null) {
					String xproductnum = obj.getString("xproductnum")
							.toString();
					map.put("xproductnum", xproductnum);
				}
				if (obj.get("xproductname") != null) {
					String xproductname = obj.getString("xproductname")
							.toString();
					map.put("xproductname", xproductname);
				}
			}

			pager = processService.getProcessPager(pager, map);
			List<Process> processList = pager.getList();
			List<Process> list2 = new ArrayList<Process>();
			for (int i = 0; i < processList.size(); i++) {
				Process p = processList.get(i);
				// 根据当前工序id获取对应的产品
				List<Products> productsList = productsService
						.getProductsByProcessId(p.getId());
				if (productsList.size() <= 0) {
					p.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
							dictService, "processState", p.getState()));
					list2.add(p);
				}
				// 循环productsList
				for (int j = 0; j < productsList.size(); j++) {
					Process pro = new Process();
					Products products = productsList.get(j);
					pro.setCreateDate(p.getCreateDate());
					pro.setProcessCode(p.getProcessCode());
					pro.setProcessName(p.getProcessName());
					pro.setState(p.getState());
					pro.setXproductnum(products.getProductsCode());
					pro.setXproductname(products.getProductsName());
					pro.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
							dictService, "processState", pro.getState()));
					list2.add(pro);
				}
			}
			pager.setList(list2);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Process.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		processService.updateisdel(ids, "Y");
		// for (String id:ids){
		// Process process=processService.load(id);
		// }
		redirectionUrl = "process!list.action";
		return SUCCESS;
	}

	// 查询一个带联表
	public Process getOne(String id) {
		Process p = this.processService.getOne(id);
		return p;
	}

	// 编辑
	public String edit() {
		try {
			// ar=getIdsAndNames();
			process = getOne(id);
			return INPUT;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 更新
	public String update() {
		Process persistent = processService.get(id);
		BeanUtils.copyProperties(process, persistent, new String[] { "id" });// 除了id不修改，其他都修改，自动完成设值操作
		// persistent.setProducts(new HashSet<Products>(this.productslist));
		persistent.setModifyDate(new Date());
		persistent.setProcessName(process.getProcessName());
		persistent.setState(process.getState());
		processService.update(persistent);
		redirectionUrl = "process!list.action";
		return SUCCESS;
	}

	// 保存
	/*
	 * @Validations( requiredStrings = {
	 * 
	 * @RequiredStringValidator(fieldName = "process.processCode", message =
	 * "工序编号不允许为空!"),
	 * 
	 * @RequiredStringValidator(fieldName = "process.processName", message =
	 * "工序名称不允许为空!") }
	 * 
	 * )
	 * 
	 * @InputConfig(resultName = "error")
	 */
	public String save() {
		/*
		 * ids=id.split(","); Set<Products>plist=new HashSet<Products>();
		 * for(int i=0;i<ids.length;i++) { Products
		 * p=this.pdService.load(ids[i]); plist.add(p); }
		 * process.setProducts(plist);
		 */
		process.setCreateDate(new Date());
		processService.save(process);
		redirectionUrl = "process!list.action";
		return SUCCESS;
	}

	/**
	 * 查询产品表中的id 和 产品名称maktx
	 */
	/*
	 * public List<WorkingBill> getIdsAndNames() { List<WorkingBill> l=null; try
	 * { l=this.wbService.getIdsAndNames(); } catch (Exception e) {
	 * e.printStackTrace(); } return l; }
	 */

	/**
	 * 检查工序编码是否存在
	 */
	public String getCk() {
		try {
			if (!processService.getCk(info)) {
				return this.ajaxJsonErrorMessage("e");
			}
			return this.ajaxJsonSuccessMessage("s");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 跳到选择产品页面
	 */
	public String list2() {
		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		return "browser";
	}

	/**
	 * 选择产品页面查询
	 */
	public String ajlist2() {

		HashMap<String, String> map = new HashMap<String, String>();
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
															// 此处处理普通查询结果 Param
															// 是表单提交过来的json
															// 字符串,进行处理。封装到后台执行
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
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}
		try {
			pager = productsService.getProductsPager(pager, map);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		List<Products> productsList = pager.getList();
		List<Products> lst = new ArrayList<Products>();
		for (int i = 0; i < productsList.size(); i++) {
			Products products = (Products) productsList.get(i);
			products.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "productsState", products.getState()));
			products.setMaterial(null);
			lst.add(products);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}

	/**
	 * 查看相关产品前，进入到查看页面
	 */
	public String relevant() {
		process = processService.get(id);
		return "relevant";
	}

	/**
	 * 选择产品页面查询--带工序id
	 */
	public String ajlist3() {
		HashMap<String, String> map = new HashMap<String, String>();
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
															// 此处处理普通查询结果 Param
															// 是表单提交过来的json
															// 字符串,进行处理。封装到后台执行
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
		}
		try {
			pager = productsService.getProductsPager2(pager, map, id);
			// process = processService.get(id);
			// List<Products> products = new
			// ArrayList<Products>(process.getProducts());
			// pager =new Pager();
			// pager.setList(products);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		List<Products> productsList = pager.getList();
		List<Products> lst = new ArrayList<Products>();
		for (int i = 0; i < productsList.size(); i++) {
			Products products = (Products) productsList.get(i);
			products.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "productsState", products.getState()));
			products.setMaterial(null);
			lst.add(products);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[] { "process" });// 除去联级products属性
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	// 根据产品id查询对应工序
	public String getProcessList() {
		List<Process> processList = processService.findProcessByProductsId(id);
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
		for (Process o : processList) {
			Map<String, String> maps = new HashMap<String, String>();
			maps.put("id", o.getId());
			maps.put("name", o.getProcessName());
			lists.add(maps);
		}
		JSONArray json = JSONArray.fromObject(lists);
		return ajaxText(json.toString());
	}

	/***/
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public ProcessService getProcessService() {
		return processService;
	}

	public void setProcessService(ProcessService processService) {
		this.processService = processService;
	}

	public DictService getDictService() {
		return dictService;
	}

	public void setDictService(DictService dictService) {
		this.dictService = dictService;
	}

	public List<WorkingBill> getAr() {
		return ar;
	}

	public void setAr(List<WorkingBill> ar) {
		this.ar = ar;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Products getProducts() {
		return products;
	}

	public void setProducts(Products products) {
		this.products = products;
	}

	public List<Products> getProductslist() {
		return productslist;
	}

	public void setProductslist(List<Products> productslist) {
		this.productslist = productslist;
	}

	public List<Products> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<Products> productsList) {
		this.productsList = productsList;
	}

}
