package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.MaterialService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-领/退料从表
 */

@ParentPackage("admin")
public class PickDetailAction extends BaseAdminAction {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 8353535527507793596L;
	
	private PickDetail pickDetail;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private PickDetailService pickDetailService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private AdminService adminService;
	@Resource
	private MaterialService materialService;
	
	private WorkingBill workingBill;
	private Admin admin;
	private Material material;
	private String matnr;
	
	
	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		
		
		
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){

		List<Material> pickDetail = pickDetailService.getMantrBom(matnr);

		List<HashMap> list=new ArrayList<HashMap>();
		for (int i = 0; i < pickDetail.size(); i++) {
			Material mate=pickDetail.get(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("materialCode", mate.getMaterialCode());
			map.put("materialName", mate.getMaterialName());
			list.add(map);
		}
		HashMap map1 = new HashMap();
		map1.put("list", list);
		JSONArray jsonArray = JSONArray.fromObject(map1);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		pickDetailService.updateisdel(ids, "Y");
		redirectionUrl = "pick_detail!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			pickDetail= pickDetailService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			PickDetail persistent = pickDetailService.load(id);
			BeanUtils.copyProperties(pickDetail, persistent, new String[] { "id","createDate", "modifyDate"});
			pickDetailService.update(persistent);
			redirectionUrl = "pick_detail!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			requiredStrings = {
					@RequiredStringValidator(fieldName = "pickDetail.pickType", message = "领料类型不能为空!"),
					
			  },
			intRangeFields = {
					@IntRangeFieldValidator(fieldName = "pickDetail.pickAmount", min = "0", message = "领料数量不能为空!")
				
			}
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		pickDetailService.save(pickDetail);
		redirectionUrl="pick_detail!list.action";
		return SUCCESS;	
	}
		


	public PickDetail getPickDetail() {
		return pickDetail;
	}


	public void setPickDetail(PickDetail pickDetail) {
		this.pickDetail = pickDetail;
	}


	public PickDetailService getPickDetailService() {
		return pickDetailService;
	}


	public void setPickDetailService(PickDetailService pickDetailService) {
		this.pickDetailService = pickDetailService;
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


	public WorkingBill getWorkingBill() {
		return workingBill;
	}


	public void setWorkingBill(WorkingBill workingBill) {
		this.workingBill = workingBill;
	}


	public String getMatnr() {
		return matnr;
	}


	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	
	
}
