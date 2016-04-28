package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.ReturnProduct;
import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.EndProductRfc;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.sap.rfc.ReturnProductRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ReturnProductService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.UnitConversionService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 中转仓入库
 */
@ParentPackage("admin")
public class ReturnProductAction extends BaseAdminAction {

	private static final long serialVersionUID = 5106762543110231271L;
	public static Logger log = Logger.getLogger(ReturnProductAction.class);
	private Admin admin;
	private List<WorkingBill> workingBillList;
	private List<ReturnProduct> returnProducts;
	private List<Locationonside> locationonsideList;
	private String info;
	private String cardnumber;
	private ReturnProduct returnProduct;
	private String desp;
	private String loginId;
	private String materialCode;
	private String materialDesp;
	private String state;
	private String start;
	private String end;
	
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
	@Resource
	private UnitConversionService unitConversionService;
	@Resource
	private TempKaoqinService tempKaoqinService;
	
	public String list(){
		admin = adminService.getLoginAdmin();
		/*admin = adminService.get(admin.getId());
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行成本入库操作!");
			return ERROR;
		}*/
		admin = adminService.get(admin.getId());
		return LIST;
	}
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {

		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		
		if (pager.getOrderBy().equals("")) {
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
		pager = returnProductService.jqGrid(pager,admin);
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
	//添加
	public String add(){
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());		
		
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行中转仓操作!");
			return ERROR;
		}
		
		List<Locationonside> locationonsideLists =new ArrayList<Locationonside>();
		String wareHouse = admin.getTeam().getFactoryUnit().getWarehouse();
		String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
		if(locationonsideList==null){
			locationonsideList = new ArrayList<Locationonside>();
		}
		try {
			locationonsideList = rfc.findWarehouse(wareHouse, werks);
			if(info==null){
				locationonsideLists =new ArrayList<Locationonside>();
				info = "303";
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
			if(!"".equals(info) && info!=null){
				locationonsideLists =new ArrayList<Locationonside>();
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
			if(!"".equals(desp) && desp!=null){
				locationonsideLists =new ArrayList<Locationonside>();
				for(Locationonside los : locationonsideList){
					if(los.getMaterialName().indexOf(desp)>-1){
						locationonsideLists.add(los);
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
	//编辑
	public String edit(){
		
		Admin admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());		
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行中转仓操作!");
			return ERROR;
		}
		
		if(returnProduct==null){
			returnProduct = new ReturnProduct();
		}
		returnProduct = returnProductService.get(id);
		//Admin admin = adminService.getLoginAdmin();
		Admin admin1 = adminService.get(loginId);
		String wareHouse = admin1.getTeam().getFactoryUnit().getWarehouse();
		String werks = admin1.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
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
			 Admin	admin1= adminService.get(loginId); 
			Admin admin =  adminService.getByCardnum(cardnumber);
			admin.setTeam(admin1.getTeam());
			admin.setProductDate(admin1.getProductDate());
			admin.setShift(admin1.getShift());
			returnProductService.updateEidtReturnProduct(id, admin,returnProduct,info);
			return ajaxJsonSuccessMessage("修改成功");
		} catch (Exception e) {
			return ajaxJsonErrorMessage("修改失败，请重试");
		}
	}
	//刷卡提交
	public String creditsubmit(){
		try {
			Admin	admin1= adminService.get(loginId); 
			Admin admin =  adminService.getByCardnum(cardnumber);
			admin.setTeam(admin1.getTeam());
			admin.setProductDate(admin1.getProductDate());
			admin.setShift(admin1.getShift());
			returnProductService.saveReturnProduct(returnProducts,info,admin);
			return ajaxJsonSuccessMessage("保存成功!"); 
		} catch (Exception e) {
			return ajaxJsonErrorMessage("保存失败，请重试");
		}
		
	}
	//刷卡确认
	public String creditapproval(){
		String message = "";
		List<ReturnProduct> returnProductCrt = new ArrayList<ReturnProduct>();
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);			
			admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
			if(!ThinkWayUtil.isPass(admin))
			{
				return ajaxJsonErrorMessage("您当前未上班,不能进行中转仓操作!");
				
			}
			
			
			//endProductService.updateApprovalEndProduct(ids,admin);
			Admin admin1 = adminService.get(loginId);
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
					rp.setWerks(admin1.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());
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
	//刷卡撤销
	public String creditundo(){
		String message = "";
		//List<ReturnProduct> returnProductCrt = new ArrayList<ReturnProduct>();
		try {
			Admin admin =  adminService.getByCardnum(cardnumber);
			admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
			if(!ThinkWayUtil.isPass(admin))
			{
				return ajaxJsonErrorMessage("您当前未上班,不能进行中转仓操作!");
				
			}
			//endProductService.updateApprovalEndProduct(ids,admin);
			Admin admin1 = adminService.get(loginId);
			//List<ReturnProduct> returnProductList = new ArrayList<ReturnProduct>();
			String[] ids = id.split(","); 
			for(int i=0;i<ids.length;i++){
				ReturnProduct rp = returnProductService.get(ids[i]);
				if( rp!=null){
					if("3".equals(rp.getState()) || "2".equals(rp.getState())){
						return ajaxJsonErrorMessage("已经确认或已经撤销无法再撤销!"); 
					}
				}
			}
			for(int i=0;i<ids.length;i++){
				ReturnProduct rp = returnProductService.get(ids[i]);
				if( rp!=null){
					rp.setConfirmUser(admin.getUsername());
					rp.setConfirmName(admin.getName());
					rp.setState("3");
					rp.setWerks(admin1.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode());
					returnProductService.update(rp);
				}
			}
			message = "撤销成功";
			return ajaxJsonSuccessMessage(message); 
			//return ajaxJsonSuccessMessage(message);
			/*returnProductCrt = reprfc.returnProductCrt("X", returnProductList);
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
						return ajaxJsonErrorMessage(message);*/
		} catch (Exception e) {
			return ajaxJsonErrorMessage("撤销失败，请重试");
		}
		
	}
	// 退中转仓入库历史
		public String history() {
			return "history";
		}
		// 退中转仓入库历史
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
					if (obj.get("start") != null) {
						String start = obj.getString("start").toString();
						map.put("start", start);
					}
					if (obj.get("end") != null) {
						String end = obj.getString("end").toString();
						map.put("end", end);
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
				pager = returnProductService.historyjqGrid(pager, map);
				List<ReturnProduct> returnProductList = pager.getList();
				List<ReturnProduct> lst = new ArrayList<ReturnProduct>();
				for (int i = 0; i < returnProductList.size(); i++) {
					ReturnProduct returnProduct = (ReturnProduct) returnProductList.get(i);
					returnProduct.setXstate(ThinkWayUtil.getDictValueByDictKey(
							dictService, "returnProState", returnProduct.getState()));
					if (returnProduct.getConfirmName() != null) {
						returnProduct.setConfirmName(returnProduct.getConfirmName());
					}
					if (returnProduct.getCreateName() != null) {
						returnProduct.setCreateName(returnProduct.getCreateName());
					}
					returnProduct.setReceiveRepertorySiteDesp(ThinkWayUtil.getDictValueByDictKey(
							dictService, "reporterSite", returnProduct.getReceiveRepertorySite()));
					lst.add(returnProduct);
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
	        
	        List<ReturnProduct> workList = returnProductService.historyExcelExport(map);
	        for(int i=0;i<workList.size();i++){
	        	ReturnProduct returnProduct = workList.get(i);
	        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        	Object[] bodyval = {returnProduct.getMaterialCode(),returnProduct.getMaterialDesp(),returnProduct.getMaterialBatch()
	        			            ,returnProduct.getMblnr(),ThinkWayUtil.getDictValueByDictKey(dictService, "reporterSite", returnProduct.getReceiveRepertorySite())
	        						,returnProduct.getStockMout()
	        			            ,sdf.format(returnProduct.getCreateDate()),returnProduct.getCreateUser()==null?"":returnProduct.getCreateName()
	        						,returnProduct.getConfirmUser()==null?"":returnProduct.getConfirmName(),ThinkWayUtil.getDictValueByDictKey(dictService, "returnProState", returnProduct.getState())};
	        	body.add(bodyval);
	        }
			try {
				String fileName = "退中转仓记录表"+".xls";
				setResponseExcel(fileName);
				ExportExcel.exportExcel("退中转仓记录表", header, body, getResponse().getOutputStream());
				getResponse().getOutputStream().flush();
			    getResponse().getOutputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
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
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
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
