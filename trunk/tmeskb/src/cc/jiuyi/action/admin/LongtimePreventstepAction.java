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
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.LongtimePreventstep;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FaultReasonService;
import cc.jiuyi.service.LongPreventService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

/**
 * 后台Action类 - 长期预防措施
 */
@ParentPackage("admin")
public class LongtimePreventstepAction extends BaseAdminAction {

	private static final long serialVersionUID = 2206341920009910756L;

	private LongtimePreventstep longtimePreventstep;
	
	private String faultReasonId;
	private String type;
	private String faultId;
	
	// 获取所有类型
	private List<Dict> allType;
	// 获取所有状态
	private List<Dict> allState;	
	
	@Resource
	private LongPreventService longPreventService;
	@Resource
	private DictService dictService;
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		longtimePreventstep = longPreventService.load(id);
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
			if (obj.get("type") != null) {
				String type = obj.getString("type").toString();
				map.put("type", type);
			}
			
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			
		}

		pager = longPreventService.getLongPreventPager(pager, map);
		List<LongtimePreventstep> longPreventList = pager.getList();
		List<LongtimePreventstep> lst = new ArrayList<LongtimePreventstep>();
		for (int i = 0; i < longPreventList.size(); i++) {
			LongtimePreventstep longtimePreventstep = (LongtimePreventstep) longPreventList.get(i);
			longtimePreventstep.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "faultState", longtimePreventstep.getState()));
			longtimePreventstep.setTypeReamrk(ThinkWayUtil.getDictValueByDictKey(
					dictService, "faultType", longtimePreventstep.getType()));
			lst.add(longtimePreventstep);
		}
		pager.setList(lst);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(LongtimePreventstep.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	// 删除
	public String delete() {
		ids = id.split(",");
		longPreventService.updateisdel(ids, "Y");
		redirectionUrl = "longtime_preventstep!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	
	// 更新
	public String update() {
		LongtimePreventstep persistent = longPreventService.load(id);
		BeanUtils.copyProperties(longtimePreventstep, persistent, new String[] { "id",
				"createDate", "modifyDate","faultParent","type","isDel"});
		longPreventService.update(persistent);
		redirectionUrl = "longtime_preventstep!list.action";
		return SUCCESS;
	}
		
	// 保存
	@InputConfig(resultName = "error")
	public String save() throws Exception {
	
		longtimePreventstep.setIsDel("N");
		longPreventService.save(longtimePreventstep);
		redirectionUrl = "longtime_preventstep!list.action";
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
	
	
/*
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
	
	
	*/
	
	public LongtimePreventstep getLongtimePreventstep() {
		return longtimePreventstep;
	}

	public void setLongtimePreventstep(LongtimePreventstep longtimePreventstep) {
		this.longtimePreventstep = longtimePreventstep;
	}

	public List<Dict> getAllType() {
		return dictService.getList("dictname", "faultType");
	}

	public void setAllType(List<Dict> allType) {
		this.allType = allType;
	}
	
	/*
	public List<FaultReason> getFaultReasonList() {
		return faultReasonService.getAllFaultReason();
	}*/

	
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
