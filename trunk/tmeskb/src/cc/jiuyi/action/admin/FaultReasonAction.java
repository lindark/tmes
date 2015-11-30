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

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FaultReasonService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 故障原因
 */
@ParentPackage("admin")
public class FaultReasonAction extends BaseAdminAction {

	private static final long serialVersionUID = 7323213806324131048L;
	
	private FaultReason faultReason;
	
	private String faultReasonId;
	private String type;
	private String faultId;
	
	// 获取所有类型
	private List<Dict> allType;
	// 获取所有状态
	private List<Dict> allState;	
	
	@Resource
	private FaultReasonService faultReasonService;
	@Resource
	private DictService dictService;
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		faultReason = faultReasonService.load(id);
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
			if (obj.get("reasonName") != null) {
				String reasonName = obj.getString("reasonName").toString();
				map.put("reasonName", reasonName);
			}
			
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			
		}

		pager = faultReasonService.getFaultReasonPager(pager, map);
		List<FaultReason> faultReasonList = pager.getList();
		List<FaultReason> lst = new ArrayList<FaultReason>();
		for (int i = 0; i < faultReasonList.size(); i++) {
			FaultReason faultReason = (FaultReason) faultReasonList.get(i);
			faultReason.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "faultState", faultReason.getState()));
			faultReason.setTypeReamrk(ThinkWayUtil.getDictValueByDictKey(
					dictService, "faultType", faultReason.getType()));
			lst.add(faultReason);
		}
		pager.setList(lst);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(FaultReason.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	// 删除
	public String delete() {
		ids = id.split(",");
		faultReasonService.updateisdel(ids, "Y");
		redirectionUrl = "fault_reason!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	
	// 更新
	public String update() {
		FaultReason persistent = faultReasonService.load(id);
		BeanUtils.copyProperties(faultReason, persistent, new String[] { "id",
				"createDate", "modifyDate","faultParent","type","isDel"});
		faultReasonService.update(persistent);
		redirectionUrl = "fault_reason!list.action";
		return SUCCESS;
	}
		
	// 保存
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		if(faultReasonId.equals("")){
			faultReason.setFaultParent(null);
		}else{
			FaultReason fr = faultReasonService.load(faultReasonId);
			faultReason.setFaultParent(fr);
		}
		faultReason.setIsDel("N");
		faultReasonService.save(faultReason);
		redirectionUrl = "fault_reason!list.action";
		return SUCCESS;
	}	
	
	/**
	 * 获取type的json集合
	 * @return
	 */
	public String getType(){
		
		List<Dict> dictList = dictService.getList("dictname", "faultType");;
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
		String[] proName={"isDel","faultParent.id"};
		String[] proValue={"N",faultId};
		List<FaultReason> faultReasonList = faultReasonService.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(FaultReason faultReason : faultReasonList){
			JSONObject jo = new JSONObject();
			jo.put("name", faultReason.getReasonName());
			jo.put("value", faultReason.getId());
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
		List<FaultReason> faultReasonList = faultReasonService.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(FaultReason faultReason : faultReasonList){
			JSONObject jo = new JSONObject();
			jo.put("name", faultReason.getReasonName());
			jo.put("value", faultReason.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("faultReason", json);
		return ajaxJson(jsonobj.toString());
	}
	
	public FaultReason getFaultReason() {
		return faultReason;
	}

	public void setFaultReason(FaultReason faultReason) {
		this.faultReason = faultReason;
	}

	
	public List<Dict> getAllType() {
		return dictService.getList("dictname", "faultType");
	}

	public void setAllType(List<Dict> allType) {
		this.allType = allType;
	}
	
	// 获取所有原因
	public List<FaultReason> getFaultReasonList() {
		return faultReasonService.getAllFaultReason();
	}

	
	public String getFaultReasonId() {
		return faultReasonId;
	}

	public void setFaultReasonId(String faultReasonId) {
		this.faultReasonId = faultReasonId;
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

	public String getFaultId() {
		return faultId;
	}

	public void setFaultId(String faultId) {
		this.faultId = faultId;
	}	
	
	
}
