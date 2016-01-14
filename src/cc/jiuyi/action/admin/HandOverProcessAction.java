package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.BomService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.ProcessRouteService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.ProductsService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.oscache.util.StringUtil;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Table;

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
			//TODO 根据传入进来的物料号
			List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate(),materialCode);
		    if(bomList == null)//如果没有找到一行数据，表示随工单+组件编码没有数据
		    	continue;
		    else{
		    	if(bomList.size() <=0)
		    		continue;
		    }
			HandOverProcess handoverprocess = handOverProcessService.findhandoverBypro(materialCode, processid,workingbill.getMatnr(),workingbill.getId());
			if (handoverprocess != null) {
				Integer amount = handoverprocess.getAmount();
				Integer repairamount = handoverprocess.getRepairAmount();
				workingbill.setAmount(amount);
				workingbill.setRepairamount(repairamount);
			}
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
		admin = adminservice.get(admin.getId());
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);// 获取当前身份的所有随工单对象
		if(workingbillList.size() <=0){
			addActionError("未找到任何随工单数据");
			return ERROR;
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
			List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate());
			if(bomList == null){
				addActionError("未找到一条BOM信息");
				return ERROR;
			}
			
			processList = processservice.getExistProcessList();//取出工序表中所有未删除的工序
			if(processList.isEmpty()){
				addActionError("未找到一条工序记录");
				return ERROR;
			}
			
			//获取维护物料信息
			List<Material> ml= materialservice.getAll();
			if(ml!=null && ml.size()>0){
				for(int y=0;y<bomList.size();y++){
					Bom bom = bomList.get(y);
					for(Material mt : ml){
						if(bom.getMaterialCode().equals(mt.getMaterialCode()) && workingbill.getWerks().equals(mt.getFactory().getFactoryCode())){
							materialList.add(bom);
							break;
						}
					}
				}
			}	
		}
		//processList = processservice.findProcess(workingbillList);// 取出当前随工单的所有工序
		//processList = processservice.getListRoute(matnrList);//取出所有工序
		
		String warehouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();// 获取人员对应单元对应的线边仓数据
		List<String> materialCodeList = new ArrayList<String>();
		for (Bom material : materialList) {
			materialCodeList.add(material.getMaterialCode());
		}

		try {
			locationonsideList = rfc.findWarehouse(warehouse, materialCodeList);
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
			List<Bom> bomList = bomservice.findBom(aufnr, workingbill.getProductDate());
			
			obj[i] = workingbill.getMatnr();
			for(int y=0;y<bomList.size();y++){
				Bom bom = bomList.get(y);
				if(materialList.contains(bom))
					continue;
				materialList.add(bom);
			}
		}		
		handoverprocessList = handOverProcessService.getList(
				"beforworkingbill.matnr", obj, orderBy, orderType);

		for (int i = 0; i < handoverprocessList.size(); i++) {
			HandOverProcess handoverprocess = handoverprocessList.get(i);
			Process process = processservice.get(handoverprocess.getProcessid());
			handoverprocess.setProcessName(process.getProcessName());
			handoverprocess.setMaterialCode(handoverprocess.getMaterialCode());
			handoverprocess.setMaterialName(handoverprocess.getMaterialName());
			handoverprocess.setBeforworkingbillCode(handoverprocess.getBeforworkingbill().getWorkingBillCode());
			handoverprocess.setAfterworkingbillCode(handoverprocess.getAfterworkingbill().getWorkingBillCode());
			handoverprocess.setState(ThinkWayUtil.getDictValueByDictKey(dictService, "handOverProcessState", handoverprocess.getState()));
			handoverprocessList.set(i, handoverprocess);
		}
		
		String warehouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();// 获取人员对应单元对应的线边仓数据
		List<String> materialCodeList = new ArrayList<String>();
		for (Bom material : materialList) {
			materialCodeList.add(material.getMaterialCode());
		}
		try {
			locationonsideList = rfc.findWarehouse(warehouse, materialCodeList);
			for(int i=0;i<locationonsideList.size();i++){
				HandOverProcess handoverprocess = new HandOverProcess();
				Locationonside location = (Locationonside)locationonsideList.get(i);
				handoverprocess.setProcessName("线边仓");
				handoverprocess.setMaterialCode(location.getMaterialCode());
				handoverprocess.setMaterialName(location.getMaterialName());
				handoverprocess.setAmount(new Double(location.getAmount()).intValue());
				handoverprocessList.add(handoverprocess);
			}
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
			System.out.println(e.getMessage());
			return ERROR;
		}

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


	
}
