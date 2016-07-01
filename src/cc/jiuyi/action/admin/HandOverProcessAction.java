package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.TempKaoqinService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类-交接管理
 */

@ParentPackage("admin")
public class HandOverProcessAction extends BaseAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -433964280757192334L;

	private HandOverProcess handOverProcess;
	// 获取所有状态
	private List<Dict> allState;
	private List<Process> processList;
	private List<WorkingBill> workingbillList;
	private List<Bom> materialList;
	private List<HandOverProcess> handoverprocessList;
	private String materialCode;// 组件编码
	private Bom material;// Bom
	private String processid;// 工序ID
	private List<Locationonside> locationonsideList;
	private String orderBy;// 排序字段
	private String orderType;// 排序类型
	private String materialName;//组件物料描述
	private String cardnumber;
	private String nowDate;
	private String shift;
	private String submitadmin;
	private String approvaladmin;
	private String state;
	

	@Resource
	private HandOverProcessService handOverProcessService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private AdminService adminservice;
	@Resource
	private ProcessService processservice;
	@Resource
	private MaterialService materialservice;
	@Resource
	private LocationonsideRfc rfc;
	@Resource
	private HandOverProcessRfc handoverprocessrfc;
	@Resource
	private BomService bomservice;
	@Resource
	private ProductsService productsservice;
	@Resource
	private ProcessRouteService processrouteservice;
	@Resource 
	private AdminService adminService;
	@Resource
	private TempKaoqinService tempKaoqinService;
	@Resource
	private FactoryUnitService factoryUnitService;
	
	// 添加 工序交接
	public String add() {
		workingbillList = new ArrayList<WorkingBill>();
		/***获取当班随工单,将产品编码的集合放到 list****/
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		//material = bomservice.get("materialCode", materialCode);
		List<WorkingBill> workingbillAll = workingbillservice.getListWorkingBillByDate(admin);//获取当班随工单集合
//		Object[] list = new Object[workingbillList.size()];
//		for(int i=0;i<workingbillList.size();i++){
//			WorkingBill workingbill = workingbillList.get(i);
//			list[i] = workingbill.getMatnr();
//		}
		//workingbillList = workingbillservice.findListWorkingBill(list,admin.getProductDate(), admin.getShift());
		/***找出今日随工单中跟materialCode 的相关的产品****/
		for (int i = 0; i < workingbillAll.size(); i++){
			WorkingBill workingbill = workingbillAll.get(i);
			
			String aufnr = workingbill.getAufnr();//生产订单号
			List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(),materialCode,workingbill.getWorkingBillCode());
		    if(bomList == null)//如果没有找到一行数据，表示随工单+组件编码没有数据
		    	continue;
		    else{
		    	if(bomList.size() <=0)
		    		continue;
		    }
		    handOverProcess = handOverProcessService.findhandoverBypro(materialCode, processid,workingbill.getId());
			if (handOverProcess != null) {
				Double amount = handOverProcess.getAmount();
				Double repairamount = handOverProcess.getRepairAmount();
				Double cqamount = handOverProcess.getCqamount();
				Double cqrepairamount = handOverProcess.getCqrepairAmount();
				workingbill.setAmount(amount);
				workingbill.setRepairamount(repairamount);
				workingbill.setCqamount(cqamount);
				workingbill.setCqrepairamount(cqrepairamount);
				Admin submit = handOverProcess.getSubmitadmin();
				Admin approval = handOverProcess.getApprovaladmin();
				if(submit == null){
					submitadmin = "";
				}else{
					submitadmin = submit.getName();
				}
				if(approval == null){
					approvaladmin = "";
				}else{
					approvaladmin = approval.getName();
				}
			}
			
			/***weitao modify***/
			//此处处理 裁切倍数,裁切后正常交接数量,裁切后返修交接数量
			FactoryUnit factoryUnit = factoryUnitService.get("factoryUnitCode", workingbill.getWorkcenter());
			Material mt = materialservice.getByNum(materialCode, factoryUnit);
//			Material mt = materialservice.get("materialCode", materialCode);//获取物料信息
			if(mt == null){
				workingbill.setCqsl(1d);
			}else{
				if(mt.getCqmultiple()==null || "".equals(mt.getCqmultiple())){
					workingbill.setCqsl(1d);
				}else{
					workingbill.setCqsl(Double.valueOf(mt.getCqmultiple()));
				}
			}
			/***weitao modify end***/
			//admin.getDepartment().getTeam().getFactoryUnit();//单元
			WorkingBill nextWorkingbill = workingbillservice.getCodeNext(workingbill.getWorkingBillCode(),nowDate,shift);//下一随工单--此处有问题。根据什么条件获取下一随工单
			if(nextWorkingbill==null){
				workingbill.setAfterworkingBillCode("");
			}else{
				workingbill.setAfterworkingBillCode(nextWorkingbill.getWorkingBillCode());
			}
			workingbillList.add(workingbill);
		}

		return INPUT;
	}

	// 获取数量
	public String getAmount() {

		return null;
	}

	// 列表
	public String list() {
		materialList = new ArrayList<Bom>();
		Admin admin = adminservice.getLoginAdmin();
		admin = adminService.get(admin.getId());
		admin = tempKaoqinService.getAdminWorkStateByAdmin(admin);
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行交接操作!");
			return ERROR;
		}
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
			Products products = productsservice.get("productsCode",workingbill.getMatnr());
			if(products == null){
				addActionError(workingbill.getMatnr()+"未维护");
				return ERROR;
			}
			String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
			//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
			List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(),workingbill.getWorkingBillCode());
			if(bomList == null || bomList.size()<=0){
				addActionError("未找到一条BOM信息");
				return ERROR;
			}
			
			processList = processservice.getExistAndStateProcessList();//取出工序表中所有未删除的工序
			if(!processList.isEmpty()){
				Collections.sort(processList, new Comparator<Process>() {
		            public int compare(Process arg0, Process arg1) {
		                return arg0.getProcessCode().compareTo(arg1.getProcessCode());
		            }
		        });
			}
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
	 
			//获取维护物料信息
			List<Material> ml= materialservice.getAll();
			if(ml!=null && ml.size()>0){
				for(int y=0;y<bomList.size();y++){
					Bom bom = bomList.get(y);
					for(Material mt : ml){
						if(bom.getMaterialCode().equals(mt.getMaterialCode()) && workingbill.getWerks().equals(mt.getFactoryunit().getWorkShop().getFactory().getFactoryCode())){
							boolean f = true;
							for(Bom b : materialList){
								if(b.getMaterialCode().equals(bom.getMaterialCode())){
									f=false;
								}
							}
							if(f){
								materialList.add(bom);	
							}
							break;
						}
					}
				}
			}
			
		}
		//processList = processservice.findProcess(workingbillList);// 取出当前随工单的所有工序
		//processList = processservice.getListRoute(matnrList);//取出所有工序
		
		String warehouse = admin.getTeam().getFactoryUnit()
				.getWarehouse();// 获取人员对应单元对应的线边仓数据
		List<String> materialCodeList = new ArrayList<String>();
		for (Bom material : materialList) {
			materialCodeList.add(material.getMaterialCode());
		}
		Collections.sort(materialList, new Comparator<Bom>() {
            public int compare(Bom arg0, Bom arg1) {
                return arg0.getMaterialCode().compareTo(arg1.getMaterialCode());
            }
        });
		try {
			String werks = admin.getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
			locationonsideList = rfc.findWarehouse1(warehouse,werks);
		} catch (IOException e) {
			addActionError("IO操作失败");
			e.printStackTrace();
			return ERROR;
		} catch (CustomerException e) {
			addActionError(e.getMsgDes());
			e.printStackTrace();
			return ERROR;
		}catch(Exception e){
			addActionError("系统出现问题，请联系系统管理员");
			e.printStackTrace();
			return ERROR;
		}
		return LIST;
	}

	/**
	 * 总体交接确认
	 * 
	 * @return
	 */
	public String ajlist() {
		materialList = new ArrayList<Bom>();
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);// 获取当前身份的所有随工单对象
		Object[] obj = new Object[workingbillList.size()];
		for (int i = 0; i < workingbillList.size(); i++) {
			WorkingBill workingbill = workingbillList.get(i);
			String aufnr = workingbill.getWorkingBillCode().substring(0,workingbill.getWorkingBillCode().length()-2);
			//Date productDate = ThinkWayUtil.formatStringDate(workingbill.getProductDate());
			List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(),workingbill.getWorkingBillCode());
			
			obj[i] = workingbill.getId();
			/*for(int y=0;y<bomList.size();y++){
				Bom bom = bomList.get(y);
				if(materialList.contains(bom))
					continue;
				materialList.add(bom);
			}*/
			//获取维护物料信息
			List<Material> ml= materialservice.getAll();
			if(ml!=null && ml.size()>0){
				for(int y=0;y<bomList.size();y++){
					Bom bom = bomList.get(y);
					for(Material mt : ml){
						if(bom.getMaterialCode().equals(mt.getMaterialCode()) && workingbill.getWerks().equals(mt.getFactoryunit().getWorkShop().getFactory().getFactoryCode())){
							boolean f = true;
							for(Bom b : materialList){
								if(b.getMaterialCode().equals(bom.getMaterialCode())){
									f=false;
								}
							}
							if(f){
								materialList.add(bom);	
							}
							break;
						}
					}
				}
			}
			
		}		
		handoverprocessList = handOverProcessService.getList(
				"beforworkingbill.id", obj, orderBy, orderType);
		
		for (int i = 0; i < handoverprocessList.size(); i++) {
			HandOverProcess handoverprocess = handoverprocessList.get(i);
			Process process = processservice.get(handoverprocess.getProcessid());
			handoverprocess.setProcessName(process.getProcessName());
			handoverprocess.setMaterialCode(handoverprocess.getMaterialCode());
			handoverprocess.setMaterialName(handoverprocess.getMaterialName());
			handoverprocess.setBeforworkingbillCode(handoverprocess.getBeforworkingbill().getWorkingBillCode());
			handoverprocess.setAfterworkingbillCode(handoverprocess.getAfterworkingbill().getWorkingBillCode());
			handoverprocess.setState(ThinkWayUtil.getDictValueByDictKey(dictService, "handOverProcessState", handoverprocess.getState()));
			Double amount = ThinkWayUtil.null2o(handoverprocess.getAmount());
			Double repairamount = ThinkWayUtil.null2o(handoverprocess.getRepairAmount());
			amount = amount + repairamount;
			handoverprocess.setAmount(amount);
			handoverprocessList.set(i, handoverprocess);
		}
		if(workingbillList!=null && workingbillList.size()>0){
			for (int i = 0; i < workingbillList.size(); i++) {
				Set<OddHandOver> ohoSet = workingbillList.get(i).getOddHandOverSet();
				if(ohoSet!=null && ohoSet.size()>0){
					for(OddHandOver oho : ohoSet){
						HandOverProcess handoverprocess = new HandOverProcess();
						handoverprocess.setProcessName("零头数交接");
						handoverprocess.setMaterialCode(oho.getMaterialCode());
						handoverprocess.setMaterialName(oho.getMaterialDesp());
						handoverprocess.setBeforworkingbillCode(oho.getBeforeWokingCode());
						handoverprocess.setAfterworkingbillCode(oho.getAfterWorkingCode());
						handoverprocess.setState(ThinkWayUtil.getDictValueByDictKey(dictService, "oddStatus", oho.getState()));
						Double amount = ThinkWayUtil.null2o(oho.getActualHOMount());
						Double repairamount = ThinkWayUtil.null2o(oho.getUnHOMount());
						amount = amount + repairamount;
						handoverprocess.setAmount(amount);
						handoverprocessList.add(handoverprocess);
					}
				}
				
			}
		}
		String warehouse = admin.getTeam().getFactoryUnit()
				.getWarehouse();// 获取人员对应单元对应的线边仓数据
		List<String> materialCodeList = new ArrayList<String>();
//		for (Bom material : materialList) {
//			materialCodeList.add(material.getMaterialCode());
//		}
//		try {
//			String werks = admin.getDepartment().getTeam().getFactoryUnit().getWorkShop().getFactory().getFactoryCode();
//			locationonsideList = rfc.findWarehouse(warehouse,werks);
//			for(int i=0;i<locationonsideList.size();i++){
//				HandOverProcess handoverprocess = new HandOverProcess();
//				Locationonside location = (Locationonside)locationonsideList.get(i);
//				handoverprocess.setProcessName("线边仓");
//				handoverprocess.setMaterialCode(location.getMaterialCode());
//				handoverprocess.setMaterialName(location.getMaterialName());
//				handoverprocess.setAmount(new Double(location.getAmount()));
//				handoverprocessList.add(handoverprocess);
//			}
//		} catch (IOException e) {
//			addActionError("IO操作失败");
//			e.printStackTrace();
//			return ERROR;
//		} catch (CustomerException e) {
//			addActionError(e.getMsgDes());
//			e.printStackTrace();
//			return ERROR;
//		}catch(Exception e){
//			addActionError("系统出现问题，请联系系统管理员");
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//			return ERROR;
//		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil
				.getExcludeFields(HandOverProcess.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(handoverprocessList,
				jsonConfig);
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("list", jsonArray);
		return ajaxJson(jsonobject.toString());
	}
	
	/**
	 * 刷卡提交
	 * @return
	 */
	public String submit(){
		
		return null;
	}
	


	// 删除
	public String delete() {
		ids = id.split(",");
		handOverProcessService.updateisdel(ids, "Y");
		redirectionUrl = "hand_over_process!list.action";
		return SUCCESS;
	}

	// 编辑
	public String edit() {
		handOverProcess = handOverProcessService.load(id);
		return INPUT;
	}

	// 更新
	//@InputConfig(resultName = "error")
	public String update() {
//		HandOverProcess persistent = handOverProcessService.load(id);
//		BeanUtils.copyProperties(handOverProcess, persistent, new String[] {
//				"id", "createDate", "modifyDate" });
//		handOverProcessService.update(persistent);
//		redirectionUrl = "hand_over_process!list.action";
		return SUCCESS;
	}

	// 刷卡提交 --员工
	//@InputConfig(resultName = "error")
	public String creditsubmit(){
//		boolean f = false;
//		for(HandOverProcess hopl  : handoverprocessList){
//			WorkingBill workingBill = workingbillservice.get(hopl.getBeforworkingbill().getId());
//			Set <HandOverProcess>  hopSet = workingBill.getBeforhandoverprocessSet();
//			if(hopSet!=null){
//				for(HandOverProcess hop : hopSet){
//					if("approval".equals(hop.getState())){
//						f= true;
//					}
//				}
//			}
//		}
//		if(f){
//			return ajaxJsonErrorMessage("数据已确认不可再次提交");
//		}
		boolean flag = false;
		for(int i=0;i<handoverprocessList.size();i++){
			HandOverProcess handoverprocess = handoverprocessList.get(i);
			handOverProcess = handOverProcessService.findhandoverBypro(handoverprocess.getMaterialCode(), handoverprocess.getProcessid(),handoverprocess.getBeforworkingbill().getId());
			if(handOverProcess == null)
				continue;
			String state = handOverProcess.getState();
			if(state == null)
				continue;
			if("approval".equals(state)){//如果有一条是非已确认状态
				flag = true;
			}
		}
		if(flag){//如果找到是已刷卡确认了，报错，不能再次提交
			return ajaxJsonErrorMessage("当前已刷卡确认!");
		}
		
		
		
		String message = handOverProcessService.savehandover(handoverprocessList,"creditsubmit",cardnumber);
		String [] msg = message.split(",");
		if(msg[0].equals("false")){
			return ajaxJsonErrorMessage(msg[1]);
		}
		return ajaxJsonSuccessMessage("保存成功!");
	}

	/**
	 * 刷卡保存
	 * @return
	 */
	public String creditsave(){
		String message = handOverProcessService.savehandover(handoverprocessList,"creditsave",cardnumber);
		String [] msg = message.split(",");
		if(msg[0].equals("false")){
			return ajaxJsonErrorMessage(msg[1]);
		}
		return ajaxJsonSuccessMessage("保存成功!");
	}
	
	/**
	 * 刷卡确认
	 * @return
	 */
	public String creditapproval(){
		
		boolean flag = true;
		for(int i=0;i<handoverprocessList.size();i++){
			HandOverProcess handoverprocess = handoverprocessList.get(i);
			handOverProcess = handOverProcessService.findhandoverBypro(handoverprocess.getMaterialCode(), handoverprocess.getProcessid(),handoverprocess.getBeforworkingbill().getId());
			if(handOverProcess == null)
				continue;
			String state = handOverProcess.getState();
			if(state != null)
				flag = false;
		}
		if(flag){//如果找到是已刷卡确认了，报错，不能再次提交
			return ajaxJsonErrorMessage("当前未提交!");
		}
		
		String message = handOverProcessService.savehandover(handoverprocessList,"creditapproval",cardnumber);
		String [] msg = message.split(",");
		if(msg[0].equals("false")){
			return ajaxJsonErrorMessage(msg[1]);
		}
		return ajaxJsonSuccessMessage("保存成功!");
	}

	public HandOverProcess getHandOverProcess() {
		return handOverProcess;
	}

	public void setHandOverProcess(HandOverProcess handOverProcess) {
		this.handOverProcess = handOverProcess;
	}

	public HandOverProcessService getHandOverProcessService() {
		return handOverProcessService;
	}

	public void setHandOverProcessService(
			HandOverProcessService handOverProcessService) {
		this.handOverProcessService = handOverProcessService;
	}

	// 获取所有状态
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

	public List<Process> getProcessList() {
		return processList;
	}

	public void setProcessList(List<Process> processList) {
		this.processList = processList;
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

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public List<HandOverProcess> getHandoverprocessList() {
		return handoverprocessList;
	}

	public void setHandoverprocessList(List<HandOverProcess> handoverprocessList) {
		this.handoverprocessList = handoverprocessList;
	}

	public Bom getMaterial() {
		return material;
	}

	public void setMaterial(Bom material) {
		this.material = material;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public List<Locationonside> getLocationonsideList() {
		return locationonsideList;
	}

	public void setLocationonsideList(List<Locationonside> locationonsideList) {
		this.locationonsideList = locationonsideList;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public String getSubmitadmin() {
		return submitadmin;
	}

	public void setSubmitadmin(String submitadmin) {
		this.submitadmin = submitadmin;
	}

	public String getApprovaladmin() {
		return approvaladmin;
	}

	public void setApprovaladmin(String approvaladmin) {
		this.approvaladmin = approvaladmin;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


	
}
