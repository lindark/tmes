package cc.jiuyi.action.admin;

import java.io.IOException;
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
















import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Kaoqin;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.entity.ProcessHandoverTop;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.KaoqinService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.ProcessHandoverService;
import cc.jiuyi.service.ProcessHandoverTopService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;


/**
 * 后台Action类-工序交接
 */
@ParentPackage("admin")
public class ProcessHandoverAction extends BaseAdminAction {

	private static final long serialVersionUID = -7711733851456884703L;
	private static Logger log = Logger.getLogger(AdminAction.class);  
	
	
	private Admin admin;
	private List<WorkingBill> workingbillList;
	private List<Bom> bomList; 
	private ProcessHandover processHandover;
	private ProcessHandoverTop processHandoverTop;
	public List<ProcessHandover> processHandoverList; 
	public List<ProcessHandover> processHandoverLists; 
	public List<ProcessHandoverSon> processHandoverSonList; 
	public List<ProcessHandoverSon> processHandoverSonLists; 
	public List<Process> processList;
	private String loginid;
	private String show;
	private String[] workingCode;
	private String materialCode;// 组件编码
	
	@Resource
	private AdminService adminService;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private BomService bomService;
	@Resource
	private ProcessHandoverService processHandoverService;
	@Resource
	private ProcessHandoverTopService processHandoverTopService;
	@Resource
	private DictService dictService;
	@Resource
	private HandOverProcessRfc handoverprocessrfc;
	@Resource
	private MaterialService materialService;
	@Resource
	private ProcessService processservice;
	@Resource
	private MaterialService materialservice;
	@Resource
	private KaoqinService kqService;
	@Resource
	private UnitdistributeModelService unitdistributeModelService;

	/**
	 * 列表
	 * @return
	 */
	public String list(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		return LIST;
	}
	/**
	 * 责任人选择
	 * @return
	 */
		public String browser(){		
			return "browser";	
		}

	/**
	 * 责任人搜索
	 * @return
	 */
	public String responsibleList() {
		HashMap<String, String> map = new HashMap<String, String>();
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
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

		
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
//			if (obj.get("productdate") != null) {
//				String productdate = obj.getString("productdate").toString();
//				map.put("productdate", productdate);
//			}
//			if (obj.get("classtime") != null) {
//				String classtime = obj.getString("classtime").toString();
//				map.put("classtime", classtime);
//			}
//			if (obj.get("factoryUnitName") != null) {
//				String factoryUnitName = obj.getString("factoryUnitName")
//						.toString();
//				map.put("factoryUnitName", factoryUnitName);
//			}
			if(admin.getTeam() ==null){
				return ajaxJsonSuccessMessage("未找到当前登录人的班组信息!");
			}
			if(admin.getTeam().getFactoryUnit() == null){
				return ajaxJsonSuccessMessage("未找到当前登录人的单元信息!");
			}
			String productdate = admin.getProductDate();
			String classtime = admin.getShift();
			String factoryUnitName = admin.getTeam().getFactoryUnit().getFactoryUnitName();
			map.put("productdate", productdate);
			map.put("classtime", classtime);
			map.put("factoryUnitName", factoryUnitName);
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				if (obj.get("empname") != null) {
					String empname = obj.getString("empname").toString();
					map.put("empname", empname);
				}
				if (obj.get("empworkNumber") != null) {
					String empworkNumber= obj.getString("empworkNumber").toString();
					map.put("empworkNumber", empworkNumber);
				}
			}
		
		
		pager = kqService.historyjqGrid(pager, map);
		List<Kaoqin> kqlist = pager.getList();
		List<Kaoqin> lst = new ArrayList<Kaoqin>();
		try {
			for (int i = 0; i < kqlist.size(); i++) {
				Kaoqin kaoqin = kqlist.get(i);
				if (kaoqin.getTeam() != null) {
					kaoqin.setXteam(kaoqin.getTeam().getTeamName());
				}
				kaoqin.setXclasstime(ThinkWayUtil.getDictValueByDictKey(
						dictService, "kaoqinClasses", kaoqin.getClasstime()));
				kaoqin.setFactoryUnitName(kaoqin.getEmp().getTeam()
						.getFactoryUnit().getFactoryUnitName());
				kaoqin.setFactory(kaoqin.getEmp().getTeam().getFactoryUnit()
						.getWorkShop().getFactory().getFactoryName());
				kaoqin.setWorkshop(kaoqin.getEmp().getTeam().getFactoryUnit()
						.getWorkShop().getWorkShopName());
				if(kaoqin.getWorkState()!=null&&!"".equals(kaoqin.getWorkState()))
				{
					kaoqin.setXworkState(ThinkWayUtil.getDictValueByDictKey(dictService,"adminworkstate", kaoqin.getWorkState()));
				}
				
				if(kaoqin.getModleNum()!=null)
				{
					String[] models=kaoqin.getModleNum().split(",");
					String modelsName="";
					for(int n=0;n<models.length;n++)
					{
						UnitdistributeModel model=unitdistributeModelService.get(models[n]);
						if(model != null)
						modelsName+=model.getStation()+",";
					}
					if(modelsName.length()>0) modelsName=modelsName.substring(0, modelsName.length()-1);
					kaoqin.setModelName(modelsName);	
					
				}
				
				lst.add(kaoqin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Kaoqin.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}		
		
	/**
	 * aj列表
	 * @return
	 */
	public String ajlist(){
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
		pager = processHandoverService.jqGrid(pager);
		List<ProcessHandoverTop> processHandoverTopList = pager.getList();
		List<ProcessHandoverTop> lst = new ArrayList<ProcessHandoverTop>();
		for (int i = 0; i < processHandoverTopList.size(); i++) {
			ProcessHandoverTop processHandoverTop = (ProcessHandoverTop) processHandoverTopList.get(i);
			processHandoverTop.setXstate(ThinkWayUtil.getDictValueByDictKey(dictService, "processHandoverTopState",processHandoverTop.getState()));
			processHandoverTop.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", processHandoverTop.getShift()));
			processHandoverTop.setXcreateUser(processHandoverTop.getCreateUser()==null?"":processHandoverTop.getCreateUser().getName());
			processHandoverTop.setXconfirmUser(processHandoverTop.getConfimUser()==null?"":processHandoverTop.getConfimUser().getName());
			lst.add(processHandoverTop);
		}
		pager.setList(lst);
		
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil
				.getExcludeFields(ProcessHandoverTop.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 添加工序交接
	 * @return
	 */
	public String add(){
		try{
			bomList = new ArrayList<Bom>();
			admin = adminService.getLoginAdmin();
			admin = adminService.get(admin.getId());
			processHandoverTop = new ProcessHandoverTop();
			
			//判断当前登录人是否已经创建过工序交接
			List<ProcessHandoverTop> phtlist = processHandoverTopService.getPHT(admin);
			if(phtlist!=null && phtlist.size()>0){
				addActionError("您当日工序交接已提交或已确认");
				return ERROR;
			}
			/*String uuid = CommonUtil.getUUID();
			processHandoverTop.setId(uuid);*/
			processHandoverList = new ArrayList<ProcessHandover>();
			
			//获取维护物料信息
			List<Material> materialList = materialService.getAll();

			processHandoverSonList = new ArrayList<ProcessHandoverSon>();
			if(admin.getProductDate() != null && admin.getShift() != null){
				workingbillList = workingbillservice.getListWorkingBillByDate(admin);
				if(workingbillList!=null && workingbillList.size()>0){
					Set<ProcessHandover> processHandoverSet = new HashSet<ProcessHandover>();
					/*for(int i=0;i<workingbillList.size();i++){
						WorkingBill wb = workingbillList.get(i);
						if(wb.getProcessHandover()!=null&&wb.getProcessHandover().getProcessHandoverTop().getType().equals("工序交接")){
							addActionError("当日工序交接已提交或已确认");
							return ERROR;
						}
					}*/
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
								
								Set<ProcessHandoverSon> processHandoverSonSet = new HashSet<ProcessHandoverSon>();
								for(Bom b : bmls){
									ProcessHandoverSon processHandoverSon1 = new ProcessHandoverSon();
									materialCode = b.getMaterialCode();
									Material mt = materialservice.get("materialCode", materialCode);//获取物料信息
									/*uuid = CommonUtil.getUUID();
									processHandoverSon1.setId(uuid);*/
									b.setBeforeWorkingCode(wb.getWorkingBillCode());
//									if(wbnext!=null){
//										b.setAfterWorkingCode(wbnext.getWorkingBillCode());
//									}
									if(mt == null){
										processHandoverSon1.setCqsl(1d);
									}else{
										if(mt.getCqmultiple()==null || "".equals(mt.getCqmultiple())){
											processHandoverSon1.setCqsl(1d);
										}else{
											processHandoverSon1.setCqsl(Double.valueOf(mt.getCqmultiple()));
										}
									}
									processHandoverSon1.setProcessHandover(processHandover1);
									processHandoverSon1.setProductAmount(b.getProductAmount()==null?"":b.getProductAmount().toString());
									processHandoverSon1.setBomCode(b.getMaterialCode());
									processHandoverSon1.setBomDesp(b.getMaterialName());
									processHandoverSon1.setMaterialAmount(b.getMaterialAmount()==null?"":b.getMaterialAmount().toString());
									processHandoverSon1.setBeforeWorkingCode(wb.getWorkingBillCode());
									processHandoverSonSet.add(processHandoverSon1);
									bomList.add(b);
								}
								processHandover1.setProcessHandoverSonSet(processHandoverSonSet);
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
			log.error(e);
		}
		return INPUT;
	}
	
	/**
	 * 添加零头数交接
	 * @return
	 */
	public String newAdd(){

		try{
			bomList = new ArrayList<Bom>();
			admin = adminService.getLoginAdmin();
			admin = adminService.get(admin.getId());
			processHandoverTop = new ProcessHandoverTop();
			/*String uuid = CommonUtil.getUUID();
			processHandoverTop.setId(uuid);*/
			processHandoverList = new ArrayList<ProcessHandover>();
			
			//获取维护物料信息
			List<Material> materialList = materialService.getAll();

			processHandoverSonList = new ArrayList<ProcessHandoverSon>();
			if(admin.getProductDate() != null && admin.getShift() != null){
				workingbillList = workingbillservice.getListWorkingBillByDate(admin);
				if(workingbillList!=null && workingbillList.size()>0){
					Set<ProcessHandover> processHandoverSet = new HashSet<ProcessHandover>();
//					for(int i=0;i<workingbillList.size();i++){
//						WorkingBill wb = workingbillList.get(i);
//						if(wb.getProcessHandover()!=null){
//							addActionError("当日工序交接已提交或已确认");
//							return ERROR;
//						}
//					}
					//判断当前登录人是否已经创建过工序交接
					List<ProcessHandoverTop> phtlist = processHandoverTopService.getPHT(admin);
					if(phtlist!=null && phtlist.size()>0){
						addActionError("您当日工序交接已提交或已确认");
						return ERROR;
					}
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
									OddHandOver oddHandOver = new OddHandOver();
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
									oddHandOver.setProcessHandover(processHandover1);
									oddHandOver.setProductAmount(b.getProductAmount()==null?"":b.getProductAmount().toString());
									oddHandOver.setBomCode(b.getMaterialCode());
									oddHandOver.setBomDesp(b.getMaterialName());
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
			log.error(e);
		}
		return INPUT;
	}
	
	/**
	 * 编辑
	 * @return
	 */
	public String edit(){
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		processHandoverTop = processHandoverTopService.get(id);
		processHandoverLists = new ArrayList<ProcessHandover>(processHandoverTop.getProcessHandOverSet());
		return INPUT;
	}
	/**
	 * 刷卡保存
	 * @return
	 */
	public String creditsubmit(){
		//判断当前登录人是否已经创建过工序交接
		/*List<ProcessHandoverTop> phtlist = processHandoverTopService.getPHT(admin);
		if(phtlist!=null && phtlist.size()>0){
			for(ProcessHandoverTop pht : phtlist){
				if(processHandoverTop.getProcessCode().equals(pht.getProcessCode())){
					addActionError("您当日工序交接已提交或已确认");
					return ERROR;
				}
			}
		}*/
		processHandoverService.saveProcessHandover(processHandoverTop,processHandoverList,processHandoverSonList,loginid);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	/**
	 * 刷卡保存 update
	 * @return
	 */
	public String creditupdate() {
		//判断当前登录人是否已经创建过工序交接
		/*List<ProcessHandoverTop> phtlist = processHandoverTopService.getPHT(admin);
		if(phtlist!=null && phtlist.size()>0){
			for(ProcessHandoverTop pht : phtlist){
				if(processHandoverTop.getProcessCode().equals(pht.getProcessCode()) && !processHandoverTop.getId().equals(pht.getId())){
					addActionError("您当日工序交接已提交或已确认");
					return ERROR;
				}
			}
		}*/
		//processHandoverTop = processHandoverTopService.get(id);
		processHandoverService.updateProcessHandover(processHandoverTop,processHandoverList,processHandoverSonList,loginid);
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	/**
	 * 刷卡确认 update
	 * @return
	 */
	public String creditapproval(){
		try {
			processHandoverTop = processHandoverTopService.get(id);
			String budat = null;
			for(ProcessHandover p : processHandoverTop.getProcessHandOverSet()){
				boolean flag =false;
				if(p.getProductAmount()!=null && p.getProductAmount().equals("")){
					flag = true;
				}
				if(p.getActualHOMount()!=null && !p.getActualHOMount().equals("")){
					flag = true;
				}
				if(p.getUnHOMount()!=null && !p.getUnHOMount().equals("")){
					flag = true;
				}
				for(ProcessHandoverSon ps:p.getProcessHandoverSonSet()){
					if(ps.getBomAmount()!=null && !ps.getBomAmount().equals("")){
						flag = true;
					}
					if(ps.getRepairNumber()!=null && !ps.getRepairNumber().equals("")){
						flag = true;
					}
					if(ps.getCqamount()!=null && !ps.getCqamount().equals("")){
						flag = true;
					}
					if(ps.getCqrepairamount()!=null && !ps.getCqrepairamount().equals("")){
						flag = true;
					}
				}
				if(flag){
					ProcessHandover ProcessHandover = handoverprocessrfc.BatchProcessHandOver(p, "",loginid);
					if(budat==null)
					//budat = ProcessHandover.getBudat();
					//p.setBudat(ProcessHandover.getBudat());
					p.setE_message(ProcessHandover.getE_message());
					p.setE_type(ProcessHandover.getE_type());
					p.setMblnr(ProcessHandover.getMblnr());
					processHandoverService.update(p);
				}
			}
			processHandoverTop.setBudat(budat);
			processHandoverTopService.update(processHandoverTop);
		} catch (IOException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage("IO出现异常，请联系系统管理员");
		} catch (CustomerException e) {
			e.printStackTrace();
			return ajaxJsonErrorMessage(e.getMsgDes());
		}//执行
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 查看
	 * @return
	 */
	public String view(){
		processHandoverTop = processHandoverTopService.get(id);
		processHandoverLists = new ArrayList<ProcessHandover>(processHandoverTop.getProcessHandOverSet());
		if(show==null)
		show = "show";
		return INPUT;
	}
	
	public String delete(){
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	public Admin getAdmin() {
		return admin;
	}



	public void setAdmin(Admin admin) {
		this.admin = admin;
	}



	public List<WorkingBill> getWorkingbillList() {
		return workingbillList;
	}



	public void setWorkingbillList(List<WorkingBill> workingbillList) {
		this.workingbillList = workingbillList;
	}



	public List<Bom> getBomList() {
		return bomList;
	}



	public void setBomList(List<Bom> bomList) {
		this.bomList = bomList;
	}



	public ProcessHandover getProcessHandover() {
		return processHandover;
	}



	public void setProcessHandover(ProcessHandover processHandover) {
		this.processHandover = processHandover;
	}

	public ProcessHandoverTop getProcessHandoverTop() {
		return processHandoverTop;
	}

	public void setProcessHandoverTop(ProcessHandoverTop processHandoverTop) {
		this.processHandoverTop = processHandoverTop;
	}

	public List<ProcessHandover> getProcessHandoverList() {
		return processHandoverList;
	}

	public void setProcessHandoverList(List<ProcessHandover> processHandoverList) {
		this.processHandoverList = processHandoverList;
	}

	public List<ProcessHandoverSon> getProcessHandoverSonList() {
		return processHandoverSonList;
	}

	public void setProcessHandoverSonList(
			List<ProcessHandoverSon> processHandoverSonList) {
		this.processHandoverSonList = processHandoverSonList;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String[] getWorkingCode() {
		return workingCode;
	}

	public void setWorkingCode(String[] workingCode) {
		this.workingCode = workingCode;
	}

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}
	public List<ProcessHandover> getProcessHandoverLists() {
		return processHandoverLists;
	}
	public void setProcessHandoverLists(List<ProcessHandover> processHandoverLists) {
		this.processHandoverLists = processHandoverLists;
	}
	public List<ProcessHandoverSon> getProcessHandoverSonLists() {
		return processHandoverSonLists;
	}
	public void setProcessHandoverSonLists(
			List<ProcessHandoverSon> processHandoverSonLists) {
		this.processHandoverSonLists = processHandoverSonLists;
	}
	public List<Process> getProcessList() {
		return processList;
	}
	public void setProcessList(List<Process> processList) {
		this.processList = processList;
	}
	
	
}
