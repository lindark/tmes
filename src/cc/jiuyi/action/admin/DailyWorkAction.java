package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.DailyWorkService;
import cc.jiuyi.service.WorkingBillService;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;


import org.apache.struts2.convention.annotation.ParentPackage;


/**
 * 后台Action类 - 报工
 */

@ParentPackage("admin")
public class DailyWorkAction extends BaseAdminAction {

	private static final long serialVersionUID = 352880047222902914L;
	
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private DailyWorkService dailyWorkService;
	
	private String workingBillId;
	private WorkingBill workingbill;
	private DailyWork dailyWork;

	/**
	 * 跳转list 页面
	 * @return
	 */
	public String list(){
		workingbill = workingBillService.get(workingBillId);
		return LIST;
	}
	
	public String add(){
		workingbill = workingBillService.get(workingBillId);
		return INPUT;
	}
	
	public String save(){
		dailyWorkService.save(dailyWork);
		redirectionUrl="daily_work!list.action?workingBillId="+dailyWork.getWorkingbill().getId();
		return SUCCESS;
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
		if(pager.is_search()==true && filters != null){//需要查询条件,复杂查询
			if(!filters.equals("")){
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		
		pager = dailyWorkService.findByPager(pager);
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			DailyWork dailyWork  = (DailyWork)pagerlist.get(i);
			dailyWork.setWorkingbill(null);
			pagerlist.set(i, dailyWork);
		}
		pager.setList(pagerlist);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
		
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

	public DailyWork getDailyWork() {
		return dailyWork;
	}

	public void setDailyWork(DailyWork dailyWork) {
		this.dailyWork = dailyWork;
	}
	
	
	
}