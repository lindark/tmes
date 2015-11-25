package cc.jiuyi.action.admin;

import java.util.Date;
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

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.UnusualLog;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FlowingRectifyService;
import cc.jiuyi.service.QualityService;
import cc.jiuyi.service.UnusualLogService;
import cc.jiuyi.util.ThinkWayUtil;

@ParentPackage("admin")
public class QualityAction extends BaseAdminAction {

	private static final long serialVersionUID = -3242463207248131962L;

	private Quality quality;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	private String loginUsername;

	private List<FlowingRectify> flowingRectifys;
	@Resource
	private QualityService qualityService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private FlowingRectifyService flowingRectifyService;
	@Resource
	private UnusualLogService unusualLogService;
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;

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
			
		   if (obj.get("team") != null) { 
			   String state = obj.getString("team").toString();
			   map.put("team", state);
			}

		   if (obj.get("productName") != null) { 
			   String productName = obj.getString("productName").toString();
			   map.put("productName", productName);
		   }

		}

		pager = qualityService.getQualityPager(pager, map);

		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			Quality quality = (Quality) pagerlist.get(i);
			quality.setProductsName(quality.getProducts().getProductsName());
			quality.setFounder(quality.getCreater().getName());
			quality.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "receiptState", quality.getState()));		
			pagerlist.set(i,quality);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Quality.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}

	public String save() {
				
		Admin admin = adminService.getLoginAdmin();
		abnormal = abnormalService.load(abnormalId);
		quality.setAbnormal(abnormal);
		quality.setCreater(admin);
		quality.setIsDel("N");
		quality.setState("0");
		qualityService.save(quality);
		
		/*for (int i = 0; i < flowingRectifys.size(); i++) {
			FlowingRectify v = flowingRectifys.get(i);
			v.setQuality(quality);
			v.setCreateDate(new Date());
			v.setCreateUser("张三");
			flowingRectifyService.save(v);
		}*/
         
		UnusualLog log = new UnusualLog();
		log.setOperator(admin.getName());
		log.setInfo("已提交");
		log.setQuality(quality);
		unusualLogService.save(log);
		
		redirectionUrl = "quality!list.action";
		return SUCCESS;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Quality persistent = qualityService.load(id);
		BeanUtils.copyProperties(quality, persistent, new String[] { "id","createDate", "modifyDate","abnormal","createUser","modifyUser","isDel","state","products","creater"});
		
	/*	for (int i = 0; i < flowingRectifys.size(); i++) {
			FlowingRectify v = flowingRectifys.get(i);
			v.setQuality(persistent);
			v.setModifyDate(new Date());
			v.setModifyUser("李四");
			flowingRectifyService.save(v);
		}*/
		
		qualityService.update(persistent);

		redirectionUrl = "quality!list.action";
		return SUCCESS;
	}
	
	public String browser(){
		return "browser";
	}

	// 删除
	public String delete() throws Exception {
		ids = id.split(",");
		qualityService.updateisdel(ids, "Y");
		redirectionUrl = "quality!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
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

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	
}
