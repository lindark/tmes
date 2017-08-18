package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.RepairDetail;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.RepairDetailService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * @author Reece 2016/3/2
 * 后台Action类-领/退料主表
 */

@ParentPackage("admin")
public class RepairDetailAction extends BaseAdminAction {

	private static final long serialVersionUID = 7728735768208640416L;

	public static Logger log = Logger.getLogger(RepairDetailAction.class);


	private RepairDetail repairDetail;
	// 获取所有状态
	private List<RepairDetail> repairDetailList;
	private Admin admin;
	private String processid;
	
	@Resource
	private RepairDetailService repairDetailService;
	@Resource
	private ProcessService processService;

	private String processids;//类型
	private String loginid;
	private String name;

	// 添加
	public String add() {
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
			if (obj.get("state") != null) {
				String state = obj.getString("state").toString();
				map.put("state", state);
			}
			if (obj.get("name") != null && obj.get("name") != null) {
				String name = obj.get("name").toString();
				map.put("name", name);
			}
		}
		pager = repairDetailService.getRepairDetailPager(pager, map);
		List<RepairDetail> rpList = pager.getList();
		for(RepairDetail rp : rpList){
			rp.setXprocessName(rp.getProcess().getProcessName());
		}
//		List<Pick> lst = new ArrayList<Pick>();
//		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(RepairDetail.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());

	}

	// 删除
	public String delete() {
		ids = id.split(",");
		repairDetailService.delete(ids);
		
		redirectionUrl = "repair_detail!list.action";
		return SUCCESS;
	}

	// 编辑
	public String edit() {
		repairDetail = repairDetailService.load(id);
		return INPUT;
	}

	// 更新
	@InputConfig(resultName = "error")
	public String update() {
		RepairDetail persistent = repairDetailService.load(id);
//		cc.jiuyi.entity.Process process = processService.get(processid);
		
//		persistent.setProcess(process);
		persistent.setProcess(repairDetail.getProcess());
		persistent.setRepirName(repairDetail.getRepirName());
		repairDetailService.update(persistent);
		redirectionUrl = "repair_detail!list.action";
		return SUCCESS;
	}

	// 保存
	@Validations(

	)
	@InputConfig(resultName = "error")
	public String save() throws Exception {
		repairDetail.setState("1");
		repairDetailService.save(repairDetail);
		redirectionUrl = "repair_detail!list.action";
		return SUCCESS;
	}

	public String browser() {
		return "browser";
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public RepairDetail getRepairDetail() {
		return repairDetail;
	}

	public void setRepairDetail(RepairDetail repairDetail) {
		this.repairDetail = repairDetail;
	}

	public List<RepairDetail> getRepairDetailList() {
		return repairDetailList;
	}

	public void setRepairDetailList(List<RepairDetail> repairDetailList) {
		this.repairDetailList = repairDetailList;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getProcessid() {
		return processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getProcessids() {
		return processids;
	}

	public void setProcessids(String processids) {
		this.processids = processids;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
 
	
	
	
}
