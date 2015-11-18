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
import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.RepairService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 返修
 * 
 */
@ParentPackage("admin")
public class RepairAction extends BaseAdminAction{

	private static final long serialVersionUID = -5187671258106950991L;
	
	private Repair repair;
	
	@Resource
	private RepairService repairService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private DictService dictService;
	
	private String workingBillId;
	private WorkingBill workingbill;
	
	public String list() {
		workingbill = workingBillService.get(workingBillId);
		return "list";
	}

	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		repair = repairService.load(id);
		return INPUT;
	}

	public String save() {
		repairService.save(repair);
		redirectionUrl = "repair!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Repair persistent = repairService.load(id);
		BeanUtils.copyProperties(repair, persistent, new String[] { "id" });
		repairService.update(persistent);
		redirectionUrl = "repair!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() {
		ids = id.split(",");
		repairService.updateisdel(ids, "Y");
		redirectionUrl = "repair!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String,String> map = new HashMap<String,String>();
		
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map m = new HashMap();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		if(pager.is_search()==true && Param != null){//普通搜索功能
			//此处处理普通查询结果  Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if(obj.get("repairPart") != null){
				String repairPart = obj.get("repairPart").toString();
				map.put("repairPart", repairPart);				
			}
			if(obj.get("processResponse")!=null){
				String processResponse = obj.get("processResponse").toString();
				map.put("processResponse", processResponse);				
			}
			if(obj.get("duty")!=null){
				String duty = obj.get("duty").toString();
				map.put("duty", duty);				
			}
			if(obj.get("state")!=null){
				String state = obj.get("state").toString();
				map.put("state", state);				
			}
		}
		pager = repairService.getRepairPager(pager,map);
		List<Repair> repairList = pager.getList();
		List<Repair> lst = new ArrayList<Repair>();
		for (int i = 0; i < repairList.size(); i++) {
			Repair repair = (Repair) repairList.get(i);
			repair.setStateRemark(ThinkWayUtil.getDictValueByDictKey(dictService,
					"repairState", repair.getState()));
			lst.add(repair);
		}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		 return ajaxJson(jsonArray.get(0).toString());
		
	}

	public Repair getRepair() {
		return repair;
	}

	public void setRepair(Repair repair) {
		this.repair = repair;
	}

	public String getWorkingBillId() {
		return workingBillId;
	}

	public void setWorkingBillId(String workingBillId) {
		this.workingBillId = workingBillId;
	}

	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}
	

}
