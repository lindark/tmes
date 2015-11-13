package cc.jiuyi.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.FlowingRectifyService;
import cc.jiuyi.service.QualityService;

@ParentPackage("admin")
public class QualityAction extends BaseAdminAction {

	private static final long serialVersionUID = -3242463207248131962L;

	private Quality quality;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;

	private List<FlowingRectify> flowingRectifys;
	@Resource
	private QualityService qualityService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private FlowingRectifyService flowingRectifyService;

	// 添加
	public String add() {
		if (aid != null) {
			abnormal = abnormalService.load(aid);
		}
		return INPUT;
	}

	// 编辑
	public String edit() {
		quality = qualityService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		// pager = qualityService.findByPager(pager);
		return LIST;
	}

	public String ajlist() {

		HashMap<String, String> map = new HashMap<String, String>();

		if (pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("orderList");
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
			
		   if (obj.get("team") != null) { 
			   String state = obj.getString("team").toString();
			   map.put("team", state);
			}

			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}

		}

		pager = qualityService.getQualityPager(pager, map);

		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			Quality quality = (Quality) pagerlist.get(i);
			quality.setAbnormal(null);
			quality.setFlowingRectify(null);
			pagerlist.set(i,quality);
		}
		pager.setList(pagerlist);

		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}

	/*
	 * // ajax列表 public String ajlist() { if(pager == null) { pager = new
	 * Pager(); } pager = qualityService.findByPager(pager);
	 * 
	 * List pagerlist = pager.getList(); for(int i =0; i <
	 * pagerlist.size();i++){ Quality quality = (Quality)pagerlist.get(i);
	 * quality.setAbnormal(null); quality.setFlowingRectify(null);
	 * pagerlist.set(i, quality); } pager.setList(pagerlist);
	 * 
	 * JSONArray jsonArray = JSONArray.fromObject(pager); return
	 * ajaxJson(jsonArray.get(0).toString()); }
	 */

	public String save() {
		abnormal = abnormalService.load(abnormalId);
		quality.setAbnormal(abnormal);
		quality.setCreateUser("张三");
		quality.setIsDel("N");
		quality.setModifyUser("张三");
		quality.setState("未确定");
		qualityService.save(quality);

		for (int i = 0; i < flowingRectifys.size(); i++) {
			FlowingRectify v = flowingRectifys.get(i);
			v.setQuality(quality);
			v.setCreateDate(new Date());
			v.setCreateUser("张三");
			flowingRectifyService.save(v);
		}

		redirectionUrl = "quality!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Quality persistent = qualityService.load(id);
		BeanUtils.copyProperties(quality, persistent, new String[] { "id" });
		qualityService.update(persistent);

		redirectionUrl = "quality!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {
		ids = id.split(",");
		qualityService.delete(ids);
		redirectionUrl = "quality!list.action";
		return SUCCESS;
	}

	public Quality getQuality() {
		return quality;
	}

	public void setQuality(Quality quality) {
		this.quality = quality;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Abnormal getAbnormal() {
		return abnormal;
	}

	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	public String getAbnormalId() {
		return abnormalId;
	}

	public void setAbnormalId(String abnormalId) {
		this.abnormalId = abnormalId;
	}

	public List<FlowingRectify> getFlowingRectifys() {
		return flowingRectifys;
	}

	public void setFlowingRectifys(List<FlowingRectify> flowingRectifys) {
		this.flowingRectifys = flowingRectifys;
	}

}
