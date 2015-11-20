package cc.jiuyi.action.admin;

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
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.ModelLog;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ModelLogService;
import cc.jiuyi.service.ModelService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 工模维修单
 */

@ParentPackage("admin")
public class ModelAction extends BaseAdminAction {

	private static final long serialVersionUID = -5323263207248342963L;

	private Model model;
	private String loginUsername;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;

	@Resource
	private ModelService modelService;
	@Resource
	private AdminService adminService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private ModelLogService modelLogService;
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
		model = modelService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = modelService.findByPager(pager);
		return LIST;
	}

	@InputConfig(resultName = "error")
	public String update() {
		Model persistent = modelService.load(id);
		BeanUtils.copyProperties(model, persistent, new String[] { "id","createDate", "modifyDate","abnormal","createUser","isDel","state" });
		modelService.update(persistent);
		redirectionUrl = "model!list.action";
		return SUCCESS;
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
			if (obj.get("teamId") != null) {
				String teamId = obj.getString("teamId").toString();
				map.put("teamId", teamId);
			}

			if (obj.get("productName") != null) {
				String productName = obj.getString("productName").toString();
				map.put("productName", productName);
			}

		}

		pager = modelService.getModelPager(pager, map);

		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			Model model = (Model) pagerlist.get(i);
			model.setAbnormal(null);
			model.setModelLogSet(null);
			model.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "receiptState", model.getState()));	
			pagerlist.set(i, model);
		}
		pager.setList(pagerlist);

		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());

	}


	public String save() {
		loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME"))
				.toLowerCase();
		Admin admin = adminService.get("username", loginUsername);
		model.setCreateUser(admin.getId());
		model.setModifyUser(admin.getId());
		abnormal = abnormalService.load(abnormalId);
		model.setAbnormal(abnormal);
		model.setIsDel("N");
		model.setState("0");
		modelService.save(model);
		
		ModelLog log = new ModelLog();
		log.setInfo("已提交");
		log.setOperator(admin.getName());
		log.setModel(model);
		modelLogService.save(log);
		redirectionUrl = "model!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {
		ids = id.split(",");
		modelService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
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

}
