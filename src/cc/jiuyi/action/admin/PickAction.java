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
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PickDetailService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;


/**
 * 后台Action类-领/退料主表
 */

@ParentPackage("admin")
public class PickAction extends BaseAdminAction {



	/**
	 * 
	 */
	private static final long serialVersionUID = 2128586659077049709L;
	
	private Pick pick;
	//获取所有状态
	private List<Dict> allState;
	
	@Resource
	private PickService pickService;
	@Resource
	private DictService dictService;
	@Resource
	private WorkingBillService workingBillService;
	@Resource
	private PickDetailService pickDetailService;
	
	
	private String workingBillId;
	private WorkingBill workingbill;
	
	//添加
	public String add(){
		return INPUT;
	}


	//列表
	public String list(){
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
		}
		workingbill=workingBillService.get(workingBillId);
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * @return
	 */
	public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
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
		
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if(obj.get("start")!=null&&obj.get("end")!=null){
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

			pager = pickService.getPickPager(pager, map);
			List<Pick> pickList = pager.getList();
			List<Pick> lst = new ArrayList<Pick>();
			for (int i = 0; i < pickList.size(); i++) {
				Pick pick = (Pick) pickList.get(i);
				pick.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
						dictService, "pickState", pick.getState()));
				lst.add(pick);
			}
		pager.setList(lst);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	
	//删除
	public String delete(){
		ids=id.split(",");
		pickService.updateisdel(ids, "Y");
//		for (String id:ids){
//			Pick pick=pickService.load(id);
//		}
		redirectionUrl = "pick!list.action";
		return SUCCESS;
	}

	
	//编辑
		public String edit(){
			pick= pickService.load(id);
			return INPUT;	
		}
		
	//更新
		@InputConfig(resultName = "error")
		public String update() {
			Pick persistent = pickService.load(id);
			BeanUtils.copyProperties(pick, persistent, new String[] { "id","createDate", "modifyDate"});
			pickService.update(persistent);
			redirectionUrl = "pick!list.action";
			return SUCCESS;
		}
		
	//保存
	@Validations(
			  
	)
	@InputConfig(resultName = "error")
	public String save()throws Exception{
		pickService.save(pick);
		redirectionUrl="pick!list.action";
		return SUCCESS;	
	}
		

	

	public WorkingBillService getWorkingBillService() {
		return workingBillService;
	}


	public void setWorkingBillService(WorkingBillService workingBillService) {
		this.workingBillService = workingBillService;
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


	public Pick getPick() {
		return pick;
	}


	public void setPick(Pick pick) {
		this.pick = pick;
	}


	public PickService getPickService() {
		return pickService;
	}


	public void setPickService(PickService pickService) {
		this.pickService = pickService;
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
	
	
}
