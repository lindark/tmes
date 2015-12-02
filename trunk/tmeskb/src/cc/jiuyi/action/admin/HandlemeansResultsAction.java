package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.HandlemeansResults;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.HandleResultService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 后台Action类 - 故障原因
 */
@ParentPackage("admin")
public class HandlemeansResultsAction extends BaseAdminAction {

	private static final long serialVersionUID = 7323212806124131028L;
	
    private HandlemeansResults handlemeansResults;
	
	private String handlemeansResultsId;
	private String type;
	private String handleId;
	
	// 获取所有类型
	private List<Dict> allType;
	// 获取所有状态
	private List<Dict> allState;	
	
	@Resource
	private HandleResultService handleResultService;
	@Resource
	private DictService dictService;
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		handlemeansResults = handleResultService.load(id);
		return INPUT;
	}

	
	// 列表
	public String list() {
		return LIST;
	}
	
	/**
	 * ajax 列表
	 * 
	 * @return
	 */
	public String ajlist() {

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

		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("handleName") != null) {
				String handleName = obj.getString("handleName").toString();
				map.put("handleName", handleName);
			}
			
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			
		}

		pager = handleResultService.getHandlePager(pager, map);
		List<HandlemeansResults> handleList = pager.getList();
		List<HandlemeansResults> lst = new ArrayList<HandlemeansResults>();
		for (int i = 0; i < handleList.size(); i++) {
			HandlemeansResults handlemeansResults = (HandlemeansResults) handleList.get(i);
			handlemeansResults.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "faultState", handlemeansResults.getState()));
			handlemeansResults.setTypeReamrk(ThinkWayUtil.getDictValueByDictKey(
					dictService, "faultType", handlemeansResults.getType()));
			lst.add(handlemeansResults);
		}
		pager.setList(lst);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(HandlemeansResults.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	// 删除
	public String delete() {
		ids = id.split(",");
		handleResultService.updateisdel(ids, "Y");
		redirectionUrl = "handlemeans_results!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	
	// 更新
	public String update() {
		HandlemeansResults persistent = handleResultService.load(id);
		BeanUtils.copyProperties(handlemeansResults, persistent, new String[] { "id",
				"createDate", "modifyDate","faultParent","type","isDel"});
		handleResultService.update(persistent);
		redirectionUrl = "handlemeans_results!list.action";
		return SUCCESS;
	}
		
	// 保存
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if(handlemeansResultsId.equals("")){
			handlemeansResults.setHandleParent(null);
		}else{
			HandlemeansResults fr = handleResultService.load(handlemeansResultsId);
			handlemeansResults.setHandleParent(fr);
		}
		handlemeansResults.setIsDel("N");
		handleResultService.save(handlemeansResults);
		redirectionUrl = "handlemeans_results!list.action";
		return SUCCESS;
	}	
	
	/**
	 * 获取type的json集合
	 * @return
	 */
	public String getType(){
		
		List<Dict> dictList = dictService.getList("dictname", "handleType");;
		JSONArray json = new JSONArray();
		for(Dict dict : dictList){
			JSONObject jo = new JSONObject();
			jo.put("name", dict.getDictvalue());
			jo.put("value", dict.getDictkey());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("type", json);
		return ajaxJson(jsonobj.toString());
	}
	
	
	/**
	 * 获取faultReason的json集合
	 * @return
	 */
	public String getChild(){
		String[] proName={"isDel","handleParent.id"};
		String[] proValue={"N",handleId};
		List<HandlemeansResults> handleResultList = handleResultService.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(HandlemeansResults handleResult : handleResultList){
			JSONObject jo = new JSONObject();
			jo.put("name", handleResult.getHandleName());
			jo.put("value", handleResult.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("child", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 获取faultReason的json集合
	 * @return
	 */
	public String getAll(){
		String[] proName={"isDel","type"};
		String[] proValue={"N",type};
		List<HandlemeansResults> handleResultList = handleResultService.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(HandlemeansResults handleResult : handleResultList){
			JSONObject jo = new JSONObject();
			jo.put("name", handleResult.getHandleName());
			jo.put("value", handleResult.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("handleResult", json);
		return ajaxJson(jsonobj.toString());
	}

	
	public HandlemeansResults getHandlemeansResults() {
		return handlemeansResults;
	}

	public void setHandlemeansResults(HandlemeansResults handlemeansResults) {
		this.handlemeansResults = handlemeansResults;
	}

	public List<Dict> getAllType() {
		return dictService.getList("dictname", "faultType");
	}

	public void setAllType(List<Dict> allType) {
		this.allType = allType;
	}
	
	// 获取所有原因
	public List<HandlemeansResults> getHandleResultList() {
		return handleResultService.getAllHandle();
	}
	
	public String getHandlemeansResultsId() {
		return handlemeansResultsId;
	}

	public void setHandlemeansResultsId(String handlemeansResultsId) {
		this.handlemeansResultsId = handlemeansResultsId;
	}

	public String getHandleId() {
		return handleId;
	}

	public void setHandleId(String handleId) {
		this.handleId = handleId;
	}

	public List<Dict> getAllState() {
		return dictService.getList("dictname", "stateRemark");
	}

	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
