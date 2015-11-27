package cc.jiuyi.action.admin;

import java.io.IOException;
import java.util.ArrayList;
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
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
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
	private List<Material> materialList;
	private List<HandOverProcess> handoverprocessList;
	private String materialCode;// 组件编码
	private Material material;// 组件
	private String processid;// 工序ID
	private List<Locationonside> locationonsideList;
	private String orderBy;// 排序字段
	private String orderType;// 排序类型

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

	// 添加
	public String add() {
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		material = materialservice.get("materialCode", materialCode);
		List<Products> prouctsSet = new ArrayList<Products>(
				material.getProducts());// 产品列表
		Object[] list = new Object[2];
		for (int i = 0; i < prouctsSet.size(); i++) {
			Products products = prouctsSet.get(i);
			list[i] = products.getProductsCode();
		}
		workingbillList = workingbillservice.findListWorkingBill(list,
				admin.getProductDate(), admin.getShift());

		for (int i = 0; i < workingbillList.size(); i++) {
			WorkingBill workingbill = workingbillList.get(i);
			HandOverProcess handoverprocess = handOverProcessService
					.findhandoverBypro(materialCode, processid,
							workingbill.getMatnr());
			if (handoverprocess != null) {
				Integer amount = handoverprocess.getAmount();
				workingbill.setAmount(amount);
			}

			workingbillList.set(i, workingbill);
		}

		return INPUT;
	}

	// 获取数量
	public String getAmount() {

		return null;
	}

	// 列表
	public String list() {
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);// 获取当前身份的所有随工单对象

		Object[] obj = new Object[workingbillList.size()];
		for (int i = 0; i < workingbillList.size(); i++) {
			WorkingBill workingbill = workingbillList.get(i);
			obj[i] = workingbill.getMatnr();
		}
		materialList = materialservice.getMantrBom(obj);// 取出随工单对应的物料BOM
		processList = processservice.findProcess(workingbillList);// 取出当前随工单的所有工序
		if (processList.size() <= 0) {
			addActionError("未找到一条工序记录");
			return ERROR;
		}
		String warehouse = admin.getDepartment().getTeam().getFactoryUnit()
				.getWarehouse();// 获取人员对应单元对应的线边仓数据
		List<String> materialCodeList = new ArrayList<String>();
		for (Material material : materialList) {
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
		}
		return LIST;
	}

	/**
	 * 总体交接确认
	 * 
	 * @return
	 */
	public String ajlist() {
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);// 获取当前身份的所有随工单对象
		Object[] obj = new Object[workingbillList.size()];
		for (int i = 0; i < workingbillList.size(); i++) {
			WorkingBill workingbill = workingbillList.get(i);
			obj[i] = workingbill.getId();
		}
		
		
		handoverprocessList = handOverProcessService.getList(
				"beforworkingbill.id", obj, orderBy, orderType);

		for (int i = 0; i < handoverprocessList.size(); i++) {
			HandOverProcess handoverprocess = handoverprocessList.get(i);
			handoverprocess.setProcessName(handoverprocess.getProcess()
					.getProcessName());
			handoverprocess.setMaterialCode(handoverprocess.getMaterial()
					.getMaterialCode());
			handoverprocess.setMaterialName(handoverprocess.getMaterial()
					.getMaterialName());
			handoverprocess.setBeforworkingbillCode(handoverprocess
					.getBeforworkingbill().getWorkingBillCode());
			handoverprocessList.set(i, handoverprocess);
		}
		
		//locationonsideList = rfc.findWarehouse(warehouse, materialCodeList);

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
	@InputConfig(resultName = "error")
	public String update() {
		HandOverProcess persistent = handOverProcessService.load(id);
		BeanUtils.copyProperties(handOverProcess, persistent, new String[] {
				"id", "createDate", "modifyDate" });
		handOverProcessService.update(persistent);
		redirectionUrl = "hand_over_process!list.action";
		return SUCCESS;
	}

	// 保存
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		handOverProcessService.saveorupdate(handoverprocessList);
		redirectionUrl = "hand_over_process!list.action";
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

	public List<Material> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Material> materialList) {
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

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
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

}
