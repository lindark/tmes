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
import cc.jiuyi.entity.QualityProblemDescription;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FaultReasonService;
import cc.jiuyi.service.QualityProblemDescriptionService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 故障原因
 */
@ParentPackage("admin")
public class QualityProblemDescriptionAction extends BaseAdminAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1627446947910392618L;

	
	@Resource
	private QualityProblemDescriptionService qualityProblemDescriptionService;
	@Resource
	private DictService dictService;
	
	
	private QualityProblemDescription qualityProblemDescription;
	
	
	// 获取所有状态
	private List<Dict> allState;	
	
	
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		qualityProblemDescription = qualityProblemDescriptionService.load(id);
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

		if (pager == null) {
			pager = new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
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
			if (obj.get("problemDescription") != null) {
				String problemDescription = obj.getString("problemDescription").toString();
				map.put("problemDescription", problemDescription);
			}
			
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			
		}

		pager = qualityProblemDescriptionService.getQualityProblemDescriptionPager(pager, map);
		List<QualityProblemDescription> qualityProblemDescriptionList = pager.getList();
		List<QualityProblemDescription> lst = new ArrayList<QualityProblemDescription>();
		for (int i = 0; i < qualityProblemDescriptionList.size(); i++) {
			QualityProblemDescription qualityProblemDescription = (QualityProblemDescription) qualityProblemDescriptionList.get(i);
			qualityProblemDescription.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "qualityProblemDescriptionState", qualityProblemDescription.getState()));
			
			lst.add(qualityProblemDescription);
		}
		pager.setList(lst);
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(QualityProblemDescription.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}
	
	
	// 删除
	public String delete() {
		ids = id.split(",");
		qualityProblemDescriptionService.updateisdel(ids, "Y");
		redirectionUrl = "quality_problem_description!list.action";
		return SUCCESS;
	}
	
	
	// 更新
	public String update() {
		QualityProblemDescription persistent = qualityProblemDescriptionService.load(id);		
		persistent.setProblemDescription(qualityProblemDescription.getProblemDescription());
		persistent.setState(qualityProblemDescription.getState());
		qualityProblemDescriptionService.update(persistent);
		redirectionUrl = "quality_problem_description!list.action";
		return SUCCESS;
	}
		
	// 保存
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		
		qualityProblemDescription.setIsDel("N");
		qualityProblemDescriptionService.save(qualityProblemDescription);
		redirectionUrl = "quality_problem_description!list.action";
		return SUCCESS;
	}	
	
	
	/**
	 * 获取faultReason的json集合
	 * @return
	 */
	public String getAll(){
		String[] proName={"isDel"};
		String[] proValue={"N"};
		List<QualityProblemDescription> qualityProblemDescriptionList = qualityProblemDescriptionService.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(QualityProblemDescription qualityProblemDescription : qualityProblemDescriptionList){
			JSONObject jo = new JSONObject();
			jo.put("name", qualityProblemDescription.getProblemDescription());
			jo.put("value", qualityProblemDescription.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("qualityProblemDescription", json);
		return ajaxJson(jsonobj.toString());
	}
	
	

	
	
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "stateRemark");
	}

	public void setAllState(List<Dict> allState) {
		this.allState = allState;
	}

	public QualityProblemDescription getQualityProblemDescription() {
		return qualityProblemDescription;
	}

	public void setQualityProblemDescription(
			QualityProblemDescription qualityProblemDescription) {
		this.qualityProblemDescription = qualityProblemDescription;
	}

	

	
	
	
	
}
