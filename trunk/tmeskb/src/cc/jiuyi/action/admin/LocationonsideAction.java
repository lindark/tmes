package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.CardManagement;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CardManagementService;
import cc.jiuyi.service.LocationonsideService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 线边仓
 * 
 */
@ParentPackage("admin")
public class LocationonsideAction extends BaseAdminAction {

	private static final long serialVersionUID = -983362628543799708L;

	private Locationonside locationonside;
	private List<Locationonside> locationonsideList;
	private String info;
	private String desp;
	
	@Resource
	private LocationonsideService locationonsideService;
	@Resource
	private AdminService adminService;
	@Resource
	private LocationonsideRfc rfc;
	@Resource
	private UnitConversionService unitConversionService;
	@Resource
	private CardManagementService cardManagementService;

	public String list() {
		// pager = dumpService.findByPager(pager);
		return "list";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		locationonside = locationonsideService.load(id);
		return INPUT;
	}

	public String save() {
		locationonsideService.save(locationonside);
		redirectionUrl = "locationonside!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Locationonside persistent = locationonsideService.load(id);
		BeanUtils.copyProperties(locationonside, persistent,
				new String[] { "id" });
		locationonsideService.update(persistent);
		redirectionUrl = "locationonside!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		locationonsideService.updateisdel(ids, "Y");
		redirectionUrl = "locationonside!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {
		HashMap<String, String> map = new HashMap<String, String>();

		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
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
			if (obj.get("locationCode") != null) {
				String locationCode = obj.get("locationCode").toString();
				map.put("locationCode", locationCode);
			}
			/*
			 * if(obj.get("deliveryDate")!=null){ String deliveryDate =
			 * obj.get("deliveryDate").toString(); map.put("deliveryDate",
			 * deliveryDate); }
			 */
			if (obj.get("locationName") != null) {
				String locationName = obj.get("locationName").toString();
				map.put("locationName", locationName);
			}
		}
		pager = locationonsideService.getLocationPager(pager, map);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());

	}
	public String getStockList(){
		HttpServletRequest request = getRequest();
		String ip = ThinkWayUtil.getIp2(request);
		
		//ip ="192.168.29.85";
		
		CardManagement cardManagement = cardManagementService.get("pcIp", ip);
	/*	if(true){
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		String wareHouse = admin.getDepartment().getTeam().getFactoryUnit()
					.getWarehouse();
			String werks = admin.getDepartment().getTeam().getFactoryUnit()
					.getWorkShop().getFactory().getFactoryCode();
		*/
		if(cardManagement!=null){
			String wareHouse = cardManagement.getFactoryunit().getWarehouse();
			String werks = cardManagement.getFactoryunit().getWorkShop().getFactory().getFactoryCode();
			
			
			List<Locationonside> locationonsideLists = new ArrayList<Locationonside>();
			
			if (locationonsideList == null) {
				locationonsideList = new ArrayList<Locationonside>();
			}
			try {
				locationonsideList = rfc.findWarehouse(wareHouse, werks);
				/*if (info == null) {
					locationonsideLists = new ArrayList<Locationonside>();
					info = "401";
					int i = info.length();
					for (Locationonside los : locationonsideList) {
						if (los.getMaterialCode().length() >= i) {
							String s = los.getMaterialCode().substring(0, i);
							if (info.equals(s)) {
								locationonsideLists.add(los);
							}
						}
					}
					locationonsideList = locationonsideLists;
				}*/

					if (!"".equals(info) && info != null) {
						locationonsideLists = new ArrayList<Locationonside>();
						int i = info.length();
						for (Locationonside los : locationonsideList) {
							if (los.getMaterialCode().length() >= i) {
								String s = los.getMaterialCode().substring(0, i);
								if (info.equals(s)) {
									locationonsideLists.add(los);
								}
							}
						}
						locationonsideList = locationonsideLists;
					}
					if (!"".equals(desp) && desp != null) {
						locationonsideLists = new ArrayList<Locationonside>();
						for (Locationonside los : locationonsideList) {
							if (los.getMaterialName().indexOf(desp) > -1) {
								locationonsideLists.add(los);
							}
						}
						locationonsideList = locationonsideLists;
					}
				
				
				for (Locationonside los : locationonsideList) {
					UnitConversion ucs = unitConversionService.get("matnr",
							los.getMaterialCode());
					if (ucs != null) {
						if (los.getAmount() == null || "".equals(los.getAmount())) {
							los.setAmount("0");
						}
						if (ucs.getConversationRatio() == null
								|| "".equals(ucs.getConversationRatio())) {
							ucs.setConversationRatio(0.0);
						}
						BigDecimal dcl = new BigDecimal(los.getAmount());
						BigDecimal dcu = new BigDecimal(ucs.getConversationRatio());
						try {
							BigDecimal dc = dcl.divide(dcu).setScale(2,
									RoundingMode.HALF_UP);
							los.setBoxMount(dc.doubleValue());
						} catch (Exception e) {
							e.printStackTrace();
							addActionError("物料" + los.getMaterialCode()
									+ " 计量单位数据异常");
							return ERROR;
						}
					} else {
						los.setBoxMount(0.00);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (CustomerException e) {
				e.printStackTrace();
			}
		}else{
			addActionError("当前IP:"+ip+"未在刷卡设备管理中维护");
			return ERROR;
		}
		
		return "stock_list";
	}
	public String stockAjaxlist(){
		/*if (pager == null) {
			pager = new Pager();
		}
		if(pager.getOrderBy().equals("")){
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.is_search() == true && filters != null) {// 需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}

		pager = endProductService.getProductsPager(pager,productDate,shift);
		List<EndProduct> endProductList = pager.getList();
		List<EndProduct> lst = new ArrayList<EndProduct>();
		for (int i = 0; i < endProductList.size(); i++) {
			EndProduct endProduct = (EndProduct) endProductList.get(i);
			endProduct.setXstate(ThinkWayUtil.getDictValueByDictKey(
					dictService, "endProState", endProduct.getState()));
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
		
		return ajaxJson(jsonArray.get(0).toString());*/
		/*HttpServletRequest request = getRequest();
		String ip = ThinkWayUtil.getIp2(request);
		*/
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		List<Locationonside> locationonsideLists = new ArrayList<Locationonside>();
		String wareHouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();
		String werks = admin.getDepartment().getTeam().getFactoryUnit()
				.getWorkShop().getFactory().getFactoryCode();
		if (locationonsideList == null) {
			locationonsideList = new ArrayList<Locationonside>();
		}
		try {
			locationonsideList = rfc.findWarehouse(wareHouse, werks);
			/*if (info == null) {
				locationonsideLists = new ArrayList<Locationonside>();
				info = "401";
				int i = info.length();
				for (Locationonside los : locationonsideList) {
					if (los.getMaterialCode().length() >= i) {
						String s = los.getMaterialCode().substring(0, i);
						if (info.equals(s)) {
							locationonsideLists.add(los);
						}
					}
				}
				locationonsideList = locationonsideLists;
			}*/
			
			if (pager == null) {
				pager = new Pager();
			}
			HashMap<String, String> map = new HashMap<String, String>();
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("info") != null) {
					String matnr = obj.getString("info")
							.toString();
					map.put("info", matnr);
				}
				if (obj.get("desp") != null) {
					String maktx = obj.getString("desp")
							.toString();
					map.put("desp", maktx);
				}
				if (!"".equals(map.get("info")) && map.get("info") != null) {
					locationonsideLists = new ArrayList<Locationonside>();
					int i = map.get("info").length();
					for (Locationonside los : locationonsideList) {
						if (los.getMaterialCode().length() >= i) {
							String s = los.getMaterialCode().substring(0, i);
							if (map.get("info").equals(s)) {
								locationonsideLists.add(los);
							}
						}
					}
					locationonsideList = locationonsideLists;
				}
				if (!"".equals(map.get("desp")) && map.get("desp") != null) {
					locationonsideLists = new ArrayList<Locationonside>();
					for (Locationonside los : locationonsideList) {
						if (los.getMaterialName().indexOf(map.get("desp")) > -1) {
							locationonsideLists.add(los);
						}
					}
					locationonsideList = locationonsideLists;
				}
			}
			
			for (Locationonside los : locationonsideList) {
				UnitConversion ucs = unitConversionService.get("matnr",
						los.getMaterialCode());
				if (ucs != null) {
					if (los.getAmount() == null || "".equals(los.getAmount())) {
						los.setAmount("0");
					}
					if (ucs.getConversationRatio() == null
							|| "".equals(ucs.getConversationRatio())) {
						ucs.setConversationRatio(0.0);
					}
					BigDecimal dcl = new BigDecimal(los.getAmount());
					BigDecimal dcu = new BigDecimal(ucs.getConversationRatio());
					try {
						BigDecimal dc = dcl.divide(dcu).setScale(2,
								RoundingMode.HALF_UP);
						los.setBoxMount(dc.doubleValue());
					} catch (Exception e) {
						e.printStackTrace();
						addActionError("物料" + los.getMaterialCode()
								+ " 计量单位数据异常");
						return ERROR;
					}
				} else {
					los.setBoxMount(0.00);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}
	
		int totalListSize = locationonsideList.size();//总共记录数
		int needListPageSize = pager.getPageSize();//一页多少条
		int neddNowPage = pager.getPageNumber();//第几页
		int actualPage = totalListSize / needListPageSize;//能分几页
		pager.setTotalCount(totalListSize);
		pager.setList(locationonsideList);
	/*	if(actualPage==0 || actualPage==1){
			pager.setList(locationonsideList);
		}else{
			locationonsideLists = new ArrayList<Locationonside>();
			for(int i=neddNowPage*needListPageSize;i<locationonsideList.size();i++){
				locationonsideLists.add(locationonsideList.get(i));
			}
			pager.setList(locationonsideLists);
		}*/
		JsonConfig jsonConfig = new JsonConfig();
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	
	public Locationonside getLocationonside() {
		return locationonside;
	}

	public void setLocationonside(Locationonside locationonside) {
		this.locationonside = locationonside;
	}

	public List<Locationonside> getLocationonsideList() {
		return locationonsideList;
	}

	public void setLocationonsideList(List<Locationonside> locationonsideList) {
		this.locationonsideList = locationonsideList;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

}
