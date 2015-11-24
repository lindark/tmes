package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.HandOverProcessService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


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
	//获取所有状态
	private List<Dict> allState;
	private List<Process> processList;
	private List<WorkingBill> workingbillList;
	private List<Material> materialList;
	private List<HandOverProcess> handoverprocessList;
	private String matnr;//组件编码
	private Material material;//组件
	
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
	
	
	//添加
	public String add(){
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		material = materialservice.get("materialCode",matnr);
		workingbillList = workingbillservice.findListWorkingBill(material.getProducts().getProductsCode(), admin.getProductDate(), admin.getShift());
		return INPUT;
	}


	//列表
	public String list(){
		Admin admin = adminservice.getLoginAdmin();
		admin = adminservice.get(admin.getId());
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);//获取当前身份的所有随工单对象
		
		Object[] obj = new Object[workingbillList.size()];
		for(int i=0;i<workingbillList.size();i++){
			WorkingBill workingbill = workingbillList.get(i);
			obj[i] = workingbill.getMatnr();
		}
		materialList = materialservice.getMantrBom(obj);//取出随工单对应的物料BOM
		
		processList = processservice.findProcess(workingbillList);//取出当前随工单的所有工序
		if(processList.size() <=0){
			addActionError("未找到一条工序记录");
			return ERROR;
		}
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){		
		HashMap<String, String> map = new HashMap<String, String>();
			pager = handOverProcessService.getHandOverProcessPager(pager, map);
			List<HandOverProcess> handOverProcessList = pager.getList();
			List<HandOverProcess> lst = new ArrayList<HandOverProcess>();
			for (int i = 0; i < handOverProcessList.size(); i++) {
				HandOverProcess handOverProcess = handOverProcessList.get(i);
				handOverProcess.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "handOverProcessState", handOverProcess.getState()));
				lst.add(handOverProcess);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		handOverProcessService.updateisdel(ids, "Y");
		redirectionUrl = "hand_over_process!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			handOverProcess= handOverProcessService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			HandOverProcess persistent = handOverProcessService.load(id);
			BeanUtils.copyProperties(handOverProcess, persistent, new String[] { "id","createDate", "modifyDate"});
			handOverProcessService.update(persistent);
			redirectionUrl = "hand_over_process!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					
			  }
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		handOverProcessService.save(handOverProcess);
		redirectionUrl="hand_over_process!list.action";
		return SUCCESS;	
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


	public void setHandOverProcessService(HandOverProcessService handOverProcessService) {
		this.handOverProcessService = handOverProcessService;
	}


	//获取所有状态
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


	public String getMatnr() {
		return matnr;
	}


	public void setMatnr(String matnr) {
		this.matnr = matnr;
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
	
	
}
