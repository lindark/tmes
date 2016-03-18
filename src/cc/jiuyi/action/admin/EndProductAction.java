package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.EndProductRfc;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.EndProductService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * @author Reece 2016/3/8
 * 后台Action类 - 成品入库
 */

@ParentPackage("admin")
public class EndProductAction extends BaseAdminAction {

	private static final long serialVersionUID = -8081201099604396846L;

	public static Logger log = Logger.getLogger(EndProductAction.class);
	private Admin admin;
	private List<WorkingBill> workingBillList;
	private List<EndProduct> endProducts;
	private List<Locationonside> locationonsideList;
	private String info;
	private String cardnumber;
	private EndProduct endProduct;
	private String desp;
	private String loginid;
	private String productDate;
	private String shift;
	private String str;
	private String materialCode;
	private String materialDesp;
	private String state;
	private String start;
	private String end;

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
	@Resource
	private UnitConversionService unitConversionService;

	public String list() {
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		boolean flag = ThinkWayUtil.isPass(admin);
		if (!flag) {
			addActionError("您当前未上班,不能进行成本入库操作!");
			return ERROR;
		}
		admin = adminService.get(admin.getId());
		/**根据生产日期/班次/已确认查出所有对象**/
		List<EndProduct> list = new ArrayList<EndProduct>(endProductService.getListChecked(productDate, shift));

		String stamp = "";
		String temp = "";
		Double sum = 0d;
		str = "";
		Collections.sort(list);// 对 pickDetailList进行排序

		// List<String> matercodeList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			EndProduct endProduct = list.get(i);

			if (!temp.equals(endProduct.getMaterialCode())) {
				temp = endProduct.getMaterialCode();
			} else {
				continue;
			}

			for (int y = 0; y < list.size(); y++) {
				EndProduct endProduct1 = list.get(y);
				if (endProduct1.getMaterialCode().equals(
						endProduct.getMaterialCode())) {
					sum += (endProduct1.getStockBoxMout());
				}
			}

			if (!stamp.equals(endProduct.getMaterialCode())) {
				stamp = endProduct.getMaterialCode();
				str += endProduct.getMaterialDesp() + ":";
				str += sum + "/箱"+"</br>";
			}
			sum = 0d;
		}

		return LIST;
	}

	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {

		if (pager == null) {
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
		return ajaxJson(jsonArray.get(0).toString());
	}

	public String add() {
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		List<Locationonside> locationonsideLists = new ArrayList<Locationonside>();
		String wareHouse = admin.getTeam().getFactoryUnit()
				.getWarehouse();
		String werks = admin.getTeam().getFactoryUnit()
				.getWorkShop().getFactory().getFactoryCode();
		if (locationonsideList == null) {
			locationonsideList = new ArrayList<Locationonside>();
		}
		try {
			locationonsideList = rfc.findWarehouse(wareHouse, werks);
			if (info == null) {
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
			}
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

		return INPUT;
	}

	public String edit() {
		if (endProduct == null) {
			endProduct = new EndProduct();
		}
		endProduct = endProductService.get(id);
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		String wareHouse = admin.getTeam().getFactoryUnit()
				.getWarehouse();
		String werks = admin.getTeam().getFactoryUnit()
				.getWorkShop().getFactory().getFactoryCode();
		if (endProduct.getMaterialBatch() == null) {
			endProduct.setMaterialBatch("");
		}
		if (locationonsideList == null) {
			locationonsideList = new ArrayList<Locationonside>();
		}
		try {
			locationonsideList = rfc.findWarehouse(wareHouse, werks);
			for (Locationonside ls : locationonsideList) {
				if (ls.getCharg() == null) {
					ls.setCharg("");
				}
				if (ls.getLocationCode().equals(endProduct.getRepertorySite())
						&& ls.getMaterialCode().equals(
								endProduct.getMaterialCode())
						&& ls.getCharg().equals(endProduct.getMaterialBatch())) {
					endProduct
							.setActualMaterialMount(new Double(ls.getAmount()));
					UnitConversion ucs = unitConversionService.get("matnr",
							ls.getMaterialCode());
					if (ucs != null) {
						if (ls.getAmount() == null || "".equals(ls.getAmount())) {
							ls.setAmount("0");
						}
						if (ucs.getConversationRatio() == null
								|| "".equals(ucs.getConversationRatio())) {
							ucs.setConversationRatio(0.0);
						}
						BigDecimal dcl = new BigDecimal(ls.getAmount());
						BigDecimal dcu = new BigDecimal(
								ucs.getConversationRatio());
						try {
							BigDecimal dc = dcl.divide(dcu).setScale(2,
									RoundingMode.HALF_UP);
							endProduct.setActualMaterialBoxMount(dc
									.doubleValue());
						} catch (Exception e) {
							e.printStackTrace();
							addActionError("物料" + ls.getMaterialCode()
									+ " 计量单位数据异常");
							return ERROR;
						}
					} else {
						endProduct.setActualMaterialBoxMount(0.00);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CustomerException e) {
			e.printStackTrace();
		}

		return INPUT;
	}

	public String update() {
		try {
			Admin admin = adminService.getByCardnum(cardnumber);
			endProductService.updateEidtEndProduct(id, admin, endProduct, info,productDate,shift);
			return ajaxJsonSuccessMessage("修改成功");
		} catch (Exception e) {
			return ajaxJsonErrorMessage("修改失败，请重试");
		}
	}

	public String creditsubmit() {
		try {
			if (endProducts != null) {
				for (EndProduct ed : endProducts) {
					if (ed. getStockBoxMout() != null
							&& !"".equals(ed.getStockBoxMout())) {
						if (ed.getStockBoxMout().compareTo(
								ed.getActualMaterialBoxMount()) > 0) {
							return ajaxJsonErrorMessage("物料"
									+ ed.getMaterialCode() + "库存不足或计量数据未维护");
						}
					}
				}
			}
			Admin admin = adminService.getByCardnum(cardnumber);
			endProductService.saveEndProduct(endProducts, info, admin,productDate,shift);
			return ajaxJsonSuccessMessage("保存成功!");

		} catch (Exception e) {
			return ajaxJsonErrorMessage("保存失败，请重试");
		}

	}

	public String creditapproval() {
		String message = "";
		List<EndProduct> endProductCrt = new ArrayList<EndProduct>();
		try {
			Admin admin = adminService.getByCardnum(cardnumber);
			// endProductService.updateApprovalEndProduct(ids,admin);
			Admin admin1 = adminService.get(loginid);
			List<EndProduct> endProductList = new ArrayList<EndProduct>();
			String[] ids = id.split(",");
			for (int i = 0; i < ids.length; i++) {
				EndProduct ed = endProductService.get(ids[i]);
				if (ed != null) {
					if (ed.getMblnr() != null && !"".equals(ed.getMblnr()))
						continue;
					ed.setConfirmUser(admin.getUsername());
					ed.setConfirmName(admin.getName());
					ed.setState("2");
					ed.setBudate(ThinkWayUtil.SystemDate());
					ed.setWerks(admin1.getTeam()
							.getFactoryUnit().getWorkShop().getFactory()
							.getFactoryCode());
					ed.setMoveType("311");
					endProductList.add(ed);
				}
			}
			endProductCrt = eprfc.EndProductCrt("X", endProductList);
			boolean flag = true;
			for (EndProduct epc : endProductCrt) {
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
				endProductCrt = eprfc.EndProductCrt("", endProductList);
				for (EndProduct epc : endProductCrt) {
					String e_type = epc.getE_type();
					String e_message = epc.getE_message();
					String ex_mblnr = epc.getEx_mblnr();
					if (e_type.equals("E")) { // 如果有一行发生了错误
						flag = false;
						message += epc.getE_message();
					} else {
						EndProduct ep = endProductService.get(epc.getId());
						ep.setMblnr(ex_mblnr);
						endProductService.update(ep);
					}
				}
				if (!flag)
					return ajaxJsonErrorMessage(message);
			}
			return ajaxJsonSuccessMessage("保存成功!");
		} catch (Exception e) {
			log.error(e);
			return ajaxJsonErrorMessage("确认失败，请重试");
		}

	}
	
	
	// 刷卡撤销
		public String creditundo() {
			ids = id.split(",");
			String str = "";
			List<EndProduct> list = endProductService.get(ids);
			for (int i = 0; i < list.size(); i++) {
				EndProduct endProduct = list.get(i);
				if (str.equals("")) {
					str = endProduct.getState();
				} else if (!endProduct.getState().equals(str)) {
					return ajaxJsonErrorMessage("请选择同一状态的记录进行撤销!");
				}
			}
			
			for (int i = 0; i < ids.length; i++) {
				endProduct = endProductService.load(ids[i]);
				if ("3".equals(endProduct.getState())) {
					return ajaxJsonErrorMessage("已撤销的无法再撤销！");
				}
				if ("2".equals(endProduct.getState())){
					return ajaxJsonErrorMessage("已确认的无法撤销！");
				}
			}
			endProductService.updateCancel(list, cardnumber);
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put(STATUS, SUCCESS);
			hashmap.put(MESSAGE, "您的操作已成功");
			return ajaxJson(hashmap);
		}
	

	public String view() {
		if (endProduct == null) {
			endProduct = new EndProduct();
		}
		endProduct = endProductService.get(id);
		return VIEW;
	}
	
	// 成品入库历史
	public String history() {
		return "history";
	}
	
	// 成品入库历史
	public String historylist(){
		try {
			if (pager == null) {
				pager = new Pager();
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

			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("materialCode") != null) {
					String materialCode = obj.getString("materialCode").toString();
					map.put("materialCode", materialCode);
				}
				if (obj.get("materialDesp") != null) {
					String materialDesp = obj.getString("materialDesp").toString();
					map.put("materialDesp", materialDesp);
				}
				if (obj.get("start") != null && obj.get("end") != null) {
					String start = obj.get("start").toString();
					String end = obj.get("end").toString();
					map.put("start", start);
					map.put("end", end);
				}
				if (obj.get("state") != null) {
					String state = obj.getString("state").toString();
					map.put("state", state);
				}
			}
			pager = endProductService.historyjqGrid(pager, map);
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
				endProduct.setReceiveRepertorySiteDesp(ThinkWayUtil.getDictValueByDictKey(
						dictService, "reporterSite", endProduct.getReceiveRepertorySite()));
				lst.add(endProduct);
			}
			pager.setList(lst);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(EndProduct.class));// 排除有关联关系的属性字段
			JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
		
	}
	
	//Excel导出
	public String excelexport(){
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("materialCode", materialCode);
		map.put("materialDesp", materialDesp);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);
		
		
		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
        header.add("物料编码");
        header.add("物料描述");
        header.add("批次");
        header.add("物料凭证号");
        header.add("接收库存地点");
        header.add("入库箱数");
        header.add("创建时间");
        header.add("创建人");
        header.add("确认人");
        header.add("状态");
        
        List<EndProduct> workList = endProductService.historyExcelExport(map);
        for(int i=0;i<workList.size();i++){
        	EndProduct endProduct = workList.get(i);
        	
        	Object[] bodyval = {endProduct.getMaterialCode(),endProduct.getMaterialDesp(),endProduct.getMaterialBatch()
        			            ,endProduct.getMblnr(),ThinkWayUtil.getDictValueByDictKey(dictService, "reporterSite", endProduct.getReceiveRepertorySite())
        						,endProduct.getStockMout()
        			            ,endProduct.getCreateDate(),endProduct.getCreateUser()==null?"":endProduct.getCreateName()
        						,endProduct.getConfirmUser()==null?"":endProduct.getConfirmName(),ThinkWayUtil.getDictValueByDictKey(dictService, "endProState", endProduct.getState())};
        	body.add(bodyval);
        }
		try {
			String fileName = "成品入库记录表"+".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("成品入库记录表", header, body, getResponse().getOutputStream());
			getResponse().getOutputStream().flush();
		    getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 获取所有状态
	public List<Dict> getAllSite() {
		return dictService.getList("dictname", "reporterSite");
	}

	// 获取所有类型
	public List<Dict> getAllType() {
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

	public String getDesp() {
		return desp;
	}

	public void setDesp(String desp) {
		this.desp = desp;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getProductDate() {
		return productDate;
	}

	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialDesp() {
		return materialDesp;
	}

	public void setMaterialDesp(String materialDesp) {
		this.materialDesp = materialDesp;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	
	
}
