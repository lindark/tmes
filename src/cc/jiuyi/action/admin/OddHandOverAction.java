package cc.jiuyi.action.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.CartonSon;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.OddHandOverService;
import cc.jiuyi.service.ProcessHandoverTopService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 后台管理、管理员 
 */

@ParentPackage("admin")
public class OddHandOverAction extends BaseAdminAction {
	
	private static final long serialVersionUID = -4732324994891087871L;
	
	private static Logger log = Logger.getLogger(OddHandOverAction.class);  
	
	private String[] workingBillIds;
	private Double[] actualMounts;
	private Double[] unMounts;
	private String cardnumber;//刷卡卡号
	private Admin admin;
	private String[] workingCode;
	private String nowDate;
	private String shift;
	private String loginid;
	private List<WorkingBill> workingbillList;//随工单集合
	private List<Bom> bomList;
	public List<ProcessHandover> processHandoverList; 
	
	private String workingBillCode;
	private String materialCode;
	private String end;
	private String start;
	private String state;
	private String show;//查看零头数交接
	private HandOverProcess handOverProcess;
	private OddHandOver oddHandOver;
	private List<Bom> materialList;
	private ProcessHandoverTop processHandoverTop = new ProcessHandoverTop();
	private Set<OddHandOver> oddHandOverSet = new HashSet<OddHandOver>();
	private List<OddHandOver> oddHandOverList;
	public List<ProcessHandover> processHandoverLists; 
	public List<Process> processList;
	public List<HashMap<String,Pager>> pagerMapList;
	
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private BomService bomService;
	@Resource
	private MaterialService materialService;
	@Resource
	private OddHandOverService oddHandOverService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminservice;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private BomService bomservice;
	@Resource
	private HandOverProcessService handOverProcessService;
	@Resource
	private ProcessHandoverTopService processHandoverTopService;
	@Resource
	private MaterialService materialservice;
	@Resource
	private ProcessService processservice;
	@Resource
	private FactoryUnitService factoryUnitService;
	@Resource
	private UnitdistributeProductService unitdistributeProductService;
	@Resource
	private UnitdistributeModelService unitdistributeModelService;
	@Resource
	private TempKaoqinService tempKaoqinService;
	
	// 零头数记录表 @author Reece 2016/3/15
	public String history() {
		return "history";
	}
	
	// 零头数记录表 @author Reece 2016/3/15
	public String historylist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件,复杂查询
			if (!filters.equals("")) {
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String, Class<jqGridSearchDetailTo>> m = new HashMap<String, Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		try {
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("materialCode") != null) {
					String materialCode = obj.getString("materialCode").toString();
					map.put("materialCode", materialCode);
				}
				if (obj.get("workingBillCode") != null) {
					String workingBillCode = obj.getString("workingBillCode")
							.toString();
					map.put("workingBillCode", workingBillCode);
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
			pager = oddHandOverService.historyjqGrid(pager, map);
			List<OddHandOver> oddList = pager.getList();
			List<OddHandOver> lst = new ArrayList<OddHandOver>();
			for (int i = 0; i < oddList.size(); i++) {
				OddHandOver oddHandOver = oddList.get(i);
				oddHandOver.setWorkingBillCode(oddHandOver.getWorkingBill().getWorkingBillCode());
				oddHandOver.setMaktx(oddHandOver.getWorkingBill().getMaktx());
				oddHandOver.setProductDate(oddHandOver.getWorkingBill().getProductDate());
				ProcessHandover p = oddHandOver.getProcessHandover();
				oddHandOver.setState(p==null?oddHandOver.getState():p.getProcessHandoverTop().getState());
				oddHandOver.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "oddStatus", oddHandOver.getState()));
				oddHandOver.setMatnr(oddHandOver.getWorkingBill().getMatnr());
				oddHandOver.setMblnr(p==null?oddHandOver.getMblnr():p.getMblnr());
				oddHandOver.setSubmitName(p==null?oddHandOver.getSubmitName():p.getProcessHandoverTop().getPhtcreateUser().getName());
				Admin a = p==null?null:p.getProcessHandoverTop().getPhtconfimUser();
				oddHandOver.setSureName(a==null?oddHandOver.getSureName():a.getName());
				lst.add(oddHandOver);
			}
			pager.setList(lst);
		} catch (Exception e) {
			e.printStackTrace();
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(OddHandOver.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 查看
	 * @return
	 */
	public String view(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		processHandoverTop = processHandoverTopService.get(id);
		processHandoverLists = new ArrayList<ProcessHandover>(processHandoverTop.getProcessHandOverSet());
		if(show==null)
		show = "show";
		return INPUT;
	}
	
	/**
	 * 查询零头数交接
	 * @return
	 */
	public String add() {
		materialList = new ArrayList<Bom>();
		Admin admin = adminservice.getLoginAdmin();
		admin = adminService.get(admin.getId());
//		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
//		boolean flag = ThinkWayUtil.isPass(admin);
//		if(!flag){
//			addActionError("您当前未上班,不能进行交接操作!");
//			return ERROR;
//		}
		admin = adminservice.get(admin.getId());
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);// 获取当前身份的所有随工单对象
		if(workingbillList.size() <=0){
			addActionError("未找到任何随工单数据");
			return ERROR;
		}
		for (int i = 0; i < workingbillList.size(); i++) {
			if("Y".equals(workingbillList.get(i).getIsHand())){
				addActionError("当日交接已完成，不可再次交接");
				return ERROR;
			}
		}
		for (int i = 0; i < workingbillList.size(); i++) {
			WorkingBill workingbill = workingbillList.get(i);
//			Products products = productsservice.get("productsCode",workingbill.getMatnr());
//			if(products == null){
//				addActionError(workingbill.getMatnr()+"未维护");
//				return ERROR;
//			}
			String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
			//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
			List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(),workingbill.getWorkingBillCode());
			if(bomList == null || bomList.size()<=0){
				addActionError("未找到一条BOM信息");
				return ERROR;
			}
			
//			processList = processservice.getExistAndStateProcessList();//取出工序表中所有未删除的工序
//			if(!processList.isEmpty()){
//				Collections.sort(processList, new Comparator<Process>() {
//		            public int compare(Process arg0, Process arg1) {
//		                return arg0.getProcessCode().compareTo(arg1.getProcessCode());
//		            }
//		        });
//			}
			if(state==null){
				state = "0";
			}
			wb:for (int k = 0; k < workingbillList.size(); k++) {
				List<OddHandOver> ohoSets = new ArrayList<OddHandOver>(workingbillList.get(k).getOddHandOverSet());
				if(ohoSets!=null && ohoSets.size()>0){
					for(OddHandOver oho : ohoSets){
						if("1".equals(oho.getState())){
							state="1";
						}else if("2".equals(oho.getState())){
							state="2";
						}
						break wb;
					}
				}
			}		
		}
		return INPUT;
	}
	
	/**
	 * 创建零头数交接
	 * @return
	 */
	public String addChangeNum(){
		try{
			bomList = new ArrayList<Bom>();
			admin = adminService.getLoginAdmin();
			admin = adminService.get(admin.getId());
			processHandoverTop = new ProcessHandoverTop();
			/*String uuid = CommonUtil.getUUID();
			processHandoverTop.setId(uuid);*/
			processHandoverList = new ArrayList<ProcessHandover>();
			pagerMapList = new ArrayList<HashMap<String,Pager>>();
			//获取维护物料信息
			List<Material> materialList = materialService.getAll();
			
			admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
			
			boolean flag = ThinkWayUtil.isPass(admin);
			if(!flag){
				addActionError("您当前未上班,不能进行零头数交接操作!");
				return ERROR;
			}
			
			oddHandOverList = new ArrayList<OddHandOver>();
			if(admin.getProductDate() != null && admin.getShift() != null){
				workingbillList = workingbillservice.getListWorkingBillByDate(admin);
				for (int i = 0; i < workingbillList.size(); i++) {
					if("Y".equals(workingbillList.get(i).getIsHand())){
						addActionError("当日交接已完成，不可再次交接");
						return ERROR;
					}
				}
				
				if(workingbillList!=null && workingbillList.size()>0){
					Set<ProcessHandover> processHandoverSet = new HashSet<ProcessHandover>();
//					for(int i=0;i<workingbillList.size();i++){
//						WorkingBill wb = workingbillList.get(i);
//						if(wb.getProcessHandover()!=null&&wb.getProcessHandover().getProcessHandoverTop().getType().equals("工序交接")){
//							addActionError("当日工序交接已提交或已确认");
//							return ERROR;
//						}
//					}
					//判断当前登录人是否已经创建过工序交接
					List<ProcessHandoverTop> phtlist = processHandoverTopService.getReN(admin);
//					if(phtlist!=null && phtlist.size()>0){
//						addActionError("您当日零头数交接已提交或已确认");
//						return ERROR;
//					}
					Collections.sort(workingbillList, new Comparator<WorkingBill>() {
						public int compare( WorkingBill o1,  WorkingBill o2) {
							 
			               /* int map1value = Integer.parseInt(o1.getWorkingBillCode());
			                int map2value =  Integer.parseInt(o2.getWorkingBillCode());
			                return map1value - map2value;*/
							return o1.getWorkingBillCode().compareTo(o2.getWorkingBillCode());
			            }
					}); 
					for(int i=0;i<workingbillList.size();i++){
						
						WorkingBill wb = workingbillList.get(i);
						String  workCenter = wb.getWorkcenter();
						FactoryUnit fu = factoryUnitService.get("workCenter", workCenter);
						HashMap<String, String> map = new HashMap<String, String>();
						String matnr="";
						String funid="";
						if(fu!=null){
							funid =fu.getId(); 
						}
						matnr = wb.getMatnr();
						map.put("matnr", matnr);
						map.put("funid", funid);
						UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
						
							pager=new Pager();
						
						if(unitdistributeProduct!=null){
							String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
							map.put("matmr", matmr);
							pager = unitdistributeModelService.getUBMList(pager, map);
						}
							HashMap<String,Pager> pagerMap = new HashMap<String,Pager>();
							Pager pager1 = new Pager();
							pager1.setSearchString(wb.getWorkingBillCode());
							pagerMap.put("pager", pager);
							pagerMap.put("workingBillCode", pager1);
							pagerMapList.add(pagerMap);
						String s = Integer.toString(i);
						ProcessHandover processHandover1 = new ProcessHandover();
						/*uuid = CommonUtil.getUUID();
						processHandover1.setId(uuid);*/
						processHandover1.setProcessHandoverTop(processHandoverTop);
						processHandover1.setWorkingBill(wb);
						processHandover1.setAufnr(wb.getAufnr());
						processHandover1.setWorkingBillCode(wb.getWorkingBillCode());
						processHandover1.setPlanCount(wb.getPlanCount()==null?"":wb.getPlanCount().toString());
						processHandover1.setMatnr(wb.getMatnr());
						processHandover1.setMaktx(wb.getMaktx());
//						WorkingBill wbnext = workingbillservice.getCodeNext(admin,wb.getWorkingBillCode(),admin.getProductDate(),admin.getShift());
//						if(wbnext!=null){
//							//workingbillList.get(i).setAfterworkingBillCode(wbnext.getWorkingBillCode());
//							processHandover1.setAfterWorkingBillCode(wbnext.getWorkingBillCode());
//						}
						
						
						if(materialList!=null && materialList.size()>0){
							//获取Bom
							String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
							List<Bom> bomLists = bomService.findBom(aufnr, wb.getProductDate(),wb.getWorkingBillCode());
							if(bomLists != null && bomLists.size()>0){
								//删除Bom中未维护物料
								List<Bom> bmls = new ArrayList<Bom>();
								for(Bom bm : bomLists){
									for(Material mt : materialList){
										if(bm.getMaterialCode().equals(mt.getMaterialCode()) && wb.getWerks().equals(mt.getFactoryunit().getWorkShop().getFactory().getFactoryCode())){
											bmls.add(bm);
											break;
										}
									}
								}
								
								Set<OddHandOver> oddHandOverSet = new HashSet<OddHandOver>();
								for(Bom b : bmls){
									oddHandOver = new OddHandOver();
									materialCode = b.getMaterialCode();
									Material mt = materialservice.get("materialCode", materialCode);//获取物料信息
									b.setBeforeWorkingCode(wb.getWorkingBillCode());
									if(mt == null){
										oddHandOver.setCqsl(1d);
										
									}else{
										if(mt.getCqmultiple()==null || "".equals(mt.getCqmultiple())){
											oddHandOver.setCqsl(1d);
										}else{
											oddHandOver.setCqsl(Double.valueOf(mt.getCqmultiple()));
										}
									}
									oddHandOver.setMaterialAmount(b.getMaterialAmount());
									oddHandOver.setProcessHandover(processHandover1);
									oddHandOver.setProductAmount(b.getProductAmount()==null?"":b.getProductAmount().toString());
									oddHandOver.setBomCode(b.getMaterialCode());
									oddHandOver.setBomDesp(b.getMaterialName());
									oddHandOver.setBeforeWokingCode(b.getBeforeWorkingCode()==null?"":b.getBeforeWorkingCode().toString());
									oddHandOver.setAfterWorkingCode(b.getAfterWorkingCode()==null?"":b.getAfterWorkingCode().toString());
									oddHandOverSet.add(oddHandOver);		
									bomList.add(b);
								}
								processHandover1.setOddHandOverSet(oddHandOverSet);
							}
						}
						processHandoverSet.add(processHandover1);
					}
					processHandoverTop.setProcessHandOverSet(processHandoverSet);
				}
				processHandoverLists = new ArrayList<ProcessHandover>(processHandoverTop.getProcessHandOverSet());
				processList = processservice.getExistAndStateProcessList();//取出工序表中所有未删除的工序
				if(!processList.isEmpty()){
					Collections.sort(processList, new Comparator<Process>() {
			            public int compare(Process arg0, Process arg1) {
			                return arg0.getProcessCode().compareTo(arg1.getProcessCode());
			            }
			        });
				}
			}else{
				addActionError("请绑定生产日期和班次");
				return ERROR;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return INPUT;
	}
	/**
	 * 编辑零头数交接
	 * @return
	 */
	public String edit(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		pagerMapList = new ArrayList<HashMap<String,Pager>>();
		if(admin.getProductDate() != null && admin.getShift() != null){
			workingbillList = workingbillservice.getListWorkingBillByDate(admin);
			if(workingbillList!=null && workingbillList.size()>0){
				for(int i=0;i<workingbillList.size();i++){
					WorkingBill wb = workingbillList.get(i);
					//////////////////////////////////////////////////////////////
		//			workingbill = workingBillService.get(workingBillId);
					String  workCenter = wb.getWorkcenter();
					FactoryUnit fu = factoryUnitService.get("workCenter", workCenter);
					HashMap<String, String> map = new HashMap<String, String>();
					String matnr="";
					String funid="";
					if(fu!=null){
						funid =fu.getId(); 
					}
					matnr = wb.getMatnr();
					map.put("matnr", matnr);
					map.put("funid", funid);
					UnitdistributeProduct unitdistributeProduct = unitdistributeProductService.getUnitdistributeProduct(map);
					
						pager=new Pager();
					
					if(unitdistributeProduct!=null){
						String matmr  = unitdistributeProduct.getMaterialName().substring(unitdistributeProduct.getMaterialName().length()-2);
						map.put("matmr", matmr);
						pager = unitdistributeModelService.getUBMList(pager, map);
					}
						HashMap<String,Pager> pagerMap = new HashMap<String,Pager>();
						Pager pager1 = new Pager();
						pager1.setSearchString(wb.getWorkingBillCode());
						pagerMap.put("pager", pager);
						pagerMap.put("workingBillCode", pager1);
						pagerMapList.add(pagerMap);
				}
			}
		}
		processHandoverTop = processHandoverTopService.get(id);
		processHandoverLists = new ArrayList<ProcessHandover>(processHandoverTop.getProcessHandOverSet());
		return INPUT;
	}
	
	// Excel导出 @author Reece 2016/3/15
	public String excelexport() {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("workingBillCode", workingBillCode);
		map.put("materialCode", materialCode);
		map.put("state", state);
		map.put("start", start);
		map.put("end", end);

		List<String> header = new ArrayList<String>();
		List<Object[]> body = new ArrayList<Object[]>();
		header.add("交接随工单号");
		header.add("产品编码");
		header.add("产品名称");
		header.add("下班随工单号");
		header.add("生产日期");
		header.add("组件编码");
		header.add("组件名称");
		
		header.add("实际零头数交接数量");
		header.add("实际异常交接数量");
		header.add("实际物料数量");
		header.add("实际异常物料数量");
		
		header.add("物料凭证号");
		header.add("交接日期");
		header.add("提交人");
		header.add("确认人");
		header.add("状态");

		List<Object[]> workList = oddHandOverService.historyExcelExport(map);
		for (int i = 0; i < workList.size(); i++) {
			Object[] obj = workList.get(i);
			OddHandOver oddHandOver = (OddHandOver) obj[0];//oddHandOver
        	WorkingBill workingbill = (WorkingBill)obj[1];//workingbill
        	
			ProcessHandover p = oddHandOver.getProcessHandover();
			oddHandOver.setMblnr(p==null?oddHandOver.getMblnr():p.getMblnr());
			oddHandOver.setSubmitName(p==null?oddHandOver.getSubmitName():p.getProcessHandoverTop().getPhtcreateUser().getName());
			Admin a = p==null?null:p.getProcessHandoverTop().getPhtconfimUser();
			oddHandOver.setSureName(a==null?oddHandOver.getSureName():a.getName());
			oddHandOver.setState(p==null?oddHandOver.getState():p.getProcessHandoverTop().getState());
			Object[] bodyval = {
					workingbill.getWorkingBillCode(),
					workingbill.getMatnr(),
					workingbill.getMaktx(),
					oddHandOver.getAfterWorkingCode(),
					workingbill.getProductDate(),
					oddHandOver.getBomCode(),
					oddHandOver.getBomDesp(),
				
					oddHandOver.getActualHOMount()==null?0:oddHandOver.getActualHOMount(),
					oddHandOver.getUnHOMount()==null?0:oddHandOver.getUnHOMount(),
					oddHandOver.getActualBomMount()==null?0:oddHandOver.getActualBomMount(),
					oddHandOver.getUnBomMount()==null?0:oddHandOver.getUnBomMount(),
					
					
					oddHandOver.getMblnr(),
					oddHandOver.getCreateDate(),
					oddHandOver.getSubmitName(),
					oddHandOver.getSureName(),
					ThinkWayUtil.getDictValueByDictKey(dictService,
							"oddStatus", oddHandOver.getState())};
			body.add(bodyval);
		}

		try {
			String fileName = "零头交接记录表" + ".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("零头交接记录表", header, body, getResponse()
					.getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
		
		
		/**=====================end  method================================*/
		
		/**=====================get/set  start==============================*/
		
	/**
	 * 创建零头数交接（新）
	 * @return
	 */
	
	public String newCreditsubmit(){
		try {
			oddHandOverService.saveOddHandOverList(processHandoverTop, oddHandOverList, processHandoverList, loginid);
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
		}
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 编辑零头数交接
	 * @return
	 */
	public String creditupdate() {
		//获取当前登录人信息
//		Admin admin = adminService.getLoginAdmin();
//		admin = adminService.get(admin.getId());
		try {
		oddHandOverService.updateOddHandOver(processHandoverTop,processHandoverList,oddHandOverList,loginid);
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
		}
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	//刷卡提交
	public String creditsubmit(){
		//获取当前登录人信息
		//Admin admin = adminService.getLoginAdmin();
		//admin = adminService.get(admin.getId());
	/*	if(workingBillIds!=null && workingBillIds.length>0){
			for(int i=0;i<workingBillIds.length;i++){
				//获取随工单 
				WorkingBill wb = workingBillService.get(workingBillIds[i]);
				List<OddHandOver> ohoSets = new ArrayList<OddHandOver>(wb.getOddHandOverSet());
				if(ohoSets!=null && ohoSets.size()>0){
					for(OddHandOver oho : ohoSets){
						if("2".equals(oho.getState())){
							return ajaxJsonErrorMessage("当前班次已确认交接，无法再次交接");
						}
					}
				}
			}
		}*/
		Admin admin = adminService.get(loginid);
		
		//获取维护物料信息
		List<Material> materialList = materialService.getAll();
			if(materialList!=null && materialList.size()>0){
						for(int i=0;i<actualMounts.length;i++){
							if((actualMounts[i]!=null && !"".equals(actualMounts[i])) || (unMounts[i]!=null && !"".equals(unMounts[i]))){
								
								//获取随工单 
								WorkingBill wb = workingBillService.get(workingBillIds[i]);
								//WorkingBill wbnext = workingBillService.getCodeNext(wb.getWorkingBillCode(),nowDate,shift);
								WorkingBill wbnext = workingBillService.getCodeNext(admin,wb.getWorkingBillCode(),nowDate,shift);
								if(wbnext==null){
									return ajaxJsonErrorMessage("未找到下班随工单");
								}
								
								//若存在先删除
								try {
									List<OddHandOver> ohoSets = new ArrayList<OddHandOver>(wb.getOddHandOverSet());
									if(ohoSets!=null && ohoSets.size()>0){
										for(OddHandOver oho : ohoSets){
											oddHandOverService.delete(oho);
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									return ajaxJsonErrorMessage("系统异常，重新进入");
								}
								
								
								//获取Bom
								String aufnr = wb.getWorkingBillCode().substring(0,wb.getWorkingBillCode().length()-2);
								List<Bom> bomList = bomService.findBom(aufnr, wb.getProductDate(),wb.getWorkingBillCode());
								if(bomList == null || bomList.size()<=0){
									//addActionError("未找到一条BOM信息");
									return ajaxJsonErrorMessage("未找到一条BOM信息");
								}
								
								//删除Bom中未维护物料
								List<Bom> bmls = new ArrayList<Bom>();
								for(Bom bm : bomList){
									for(Material mt : materialList){
										if(bm.getMaterialCode().equals(mt.getMaterialCode()) && wb.getWerks().equals(mt.getFactoryunit().getWorkShop().getFactory().getFactoryCode())){
											bmls.add(bm);
											break;
										}
									}
								}
								 
								//通过交接数量，计算bom实际数量
								if(bmls.size()>0){
									Set<OddHandOver> ohoSets = new HashSet<OddHandOver>();
									for(Bom bm : bmls){
										OddHandOver oho = new OddHandOver();
										if(actualMounts[i]!=null && !"".equals(actualMounts[i])){
											Double mount = actualMounts[i] * (bm.getMaterialAmount() / wb.getPlanCount());
											BigDecimal   b   =   new   BigDecimal(mount);  
											mount   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
											oho.setActualHOMount(mount);
										}
										oho.setBeforeWokingCode(wb.getWorkingBillCode());
										
										oho.setAfterWorkingCode(wbnext.getWorkingBillCode());//下班随工单
										//获取提交人
										if(admin == null){
											admin = new Admin();
										}
										admin = adminService.getByCardnum(cardnumber);
										oho.setSubmitCode(admin.getCardNumber());
										oho.setSubmitName(admin.getName());
										oho.setWorkingBill(wb);
										oho.setActualBomMount(actualMounts[i]);
										oho.setUnBomMount(unMounts[i]);
										oho.setState("1");
										oho.setMaterialCode(bm.getMaterialCode());
										oho.setMaterialDesp(bm.getMaterialName());
										if(unMounts[i]!=null && !"".equals(unMounts[i])){
											Double mount = unMounts[i] * (bm.getMaterialAmount() / wb.getPlanCount());
											BigDecimal   b   =   new   BigDecimal(mount);  
											mount   =   b.setScale(3,   BigDecimal.ROUND_HALF_UP).doubleValue();  
											oho.setUnHOMount(mount);
										}
										oddHandOverService.save(oho);
										ohoSets.add(oho);
									}
									wb.setOddHandOverSet(ohoSets);
									workingBillService.update(wb);
								}else{
									//addActionError("没有找到可交接记录");
									return ajaxJsonErrorMessage("没有找到可交接记录");
								}
							}
						}
		}else{
			//addActionError("未维护物料信息");
			//return ERROR;
			return ajaxJsonErrorMessage("未维护物料信息");
			
		}
		return ajaxJsonSuccessMessage("您的操作已成功");
	}
	
	//刷卡确认
	public String creditapproval(){
		Set<OddHandOver> ohoSet = new HashSet<OddHandOver>();
		boolean f = false;
		if(actualMounts!=null && actualMounts.length>0){
			for(int i=0;i<actualMounts.length;i++){
				if(actualMounts[i]!=null && !"".equals(actualMounts[i])){
					//获取随工单 
					WorkingBill wb = workingBillService.get(workingBillIds[i]);
					try {
						//获取零头数交接
						ohoSet  = wb.getOddHandOverSet();
						if(ohoSet!=null && ohoSet.size()>0){
							for(OddHandOver oho : ohoSet){
								//获取确认人
								admin = adminService.getByCardnum(cardnumber);
								oho.setSureCode(admin.getCardNumber());
								oho.setSureName(admin.getName());
								oho.setState("2");
								oddHandOverService.update(oho);
							}
							f = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
						return ajaxJsonErrorMessage("系统异常，重新进入");
					}
					
				}
			}
			if(f){
				return ajaxJsonSuccessMessage("您的操作已成功");
			}else{
				return ajaxJsonErrorMessage("无可交接数据,不可确认");
			}
		}else{
			return ajaxJsonErrorMessage("无可交接数据,不可确认");
		}
		
	}
	
	
	public String findAfterWorkingCode(){
		Admin admin = adminService.get(loginid);
		List<Map<String,String>> mapList = new ArrayList<Map<String,String>>();
		if(workingCode!=null && workingCode.length>0){
			for(int i=0;i<workingCode.length;i++){
				Map<String,String> map = new HashMap<String,String>();
				WorkingBill nextWorkingbill = workingBillService.getCodeNext(admin,workingCode[i],nowDate,shift);//下一随工单
				if(nextWorkingbill == null){
					map.put("afterCode","");
				}else{
					map.put("afterCode", nextWorkingbill.getWorkingBillCode());
				}
				mapList.add(map);
			}
			JSONArray jsonArray = JSONArray.fromObject(mapList);
			return ajaxJson(jsonArray.toString());
		}else{
			return ajaxJsonErrorMessage("没有上班随工单");
		}
		
	}
	
	
	public Double[] getActualMounts() {
		return actualMounts;
	}

	public void setActualMounts(Double[] actualMounts) {
		this.actualMounts = actualMounts;
	}

	public String[] getWorkingBillIds() {
		return workingBillIds;
	}

	public void setWorkingBillIds(String[] workingBillIds) {
		this.workingBillIds = workingBillIds;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public Double[] getUnMounts() {
		return unMounts;
	}

	public void setUnMounts(Double[] unMounts) {
		this.unMounts = unMounts;
	}

	public String[] getWorkingCode() {
		return workingCode;
	}

	public void setWorkingCode(String[] workingCode) {
		this.workingCode = workingCode;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}

	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getWorkingBillCode() {
		return workingBillCode;
	}

	public void setWorkingBillCode(String workingBillCode) {
		this.workingBillCode = workingBillCode;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<WorkingBill> getWorkingbillList() {
		return workingbillList;
	}

	public void setWorkingbillList(List<WorkingBill> workingbillList) {
		this.workingbillList = workingbillList;
	}

	public List<Bom> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Bom> materialList) {
		this.materialList = materialList;
	}

	public HandOverProcess getHandOverProcess() {
		return handOverProcess;
	}

	public void setHandOverProcess(HandOverProcess handOverProcess) {
		this.handOverProcess = handOverProcess;
	}

	public ProcessHandoverTop getProcessHandoverTop() {
		return processHandoverTop;
	}

	public void setProcessHandoverTop(ProcessHandoverTop processHandoverTop) {
		this.processHandoverTop = processHandoverTop;
	}

	public Set<OddHandOver> getOddHandOverSet() {
		return oddHandOverSet;
	}

	public void setOddHandOverSet(Set<OddHandOver> oddHandOverSet) {
		this.oddHandOverSet = oddHandOverSet;
	}

	public List<OddHandOver> getOddHandOverList() {
		return oddHandOverList;
	}

	public void setOddHandOverList(List<OddHandOver> oddHandOverList) {
		this.oddHandOverList = oddHandOverList;
	}

	public OddHandOver getOddHandOver() {
		return oddHandOver;
	}

	public void setOddHandOver(OddHandOver oddHandOver) {
		this.oddHandOver = oddHandOver;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public List<ProcessHandover> getProcessHandoverList() {
		return processHandoverList;
	}

	public void setProcessHandoverList(List<ProcessHandover> processHandoverList) {
		this.processHandoverList = processHandoverList;
	}
	
	
	

}
