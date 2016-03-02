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

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dict;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.ScrapLaterService;
import cc.jiuyi.service.ScrapMessageService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 报废信息表
 * @author gaoyf
 *
 */
@ParentPackage("admin")
public class ScrapMessageAction extends BaseAdminAction
{
	private static final long serialVersionUID = -5681929554406341857L;
	
	@Resource
	private ScrapMessageService scrapMessageService;
	@Resource
	private WorkingBillService wbService;//随工单
	
	private Admin admin;
	private WorkingBill workingbill;//随工单
	
	private String wbId;//随工单id
	
	@Resource
	private AdminService adminService;
	@Resource
	private DictService dictService;//字典表
	
	/**
	 * 当前随工单的报废数单数据
	 */
	public String list()
	{
		this.admin=this.adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		boolean flag = ThinkWayUtil.isPass(admin);
		if(!flag){
			addActionError("您当前未上班,不能进行报废操作!");
			return ERROR;
		}
		this.workingbill=this.wbService.get(wbId);
		return LIST;
	}
	
	// 报废记录
	public String history() {
		return "history";
	}
	
	public String historylist() {
		HashMap<String, String> map = new HashMap<String, String>();
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if (pager.is_search() == true && filters != null) {// 需要查询条件,复杂查询
			if (!filters.equals("")) {
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String, Class<jqGridSearchDetailTo>> m = new HashMap<String, Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager) JSONObject.toBean(filt, Pager.class, m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		if (pager.is_search() == true && Param != null) {// 普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("maktx") != null) {
				String maktx = obj.getString("maktx")
						.toString();
				map.put("maktx", maktx);
			}
			if (obj.get("state") != null) {
				String state = obj.getString("state")
						.toString();
				map.put("state", state);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}			
		}
		pager = scrapMessageService.getMessagePager(pager, map);
		List<ScrapMessage> scrapMessageList = pager.getList();
		List<ScrapMessage> lst = new ArrayList<ScrapMessage>();
		for (int i = 0; i < scrapMessageList.size(); i++) {
			ScrapMessage scrapMessage = (ScrapMessage) scrapMessageList
					.get(i);
			scrapMessage.setWorkingbill(scrapMessage.getScrap().getWorkingBill().getWorkingBillCode());
			scrapMessage.setProductName(wbService.get(
					scrapMessage.getScrap().getWorkingBill().getId()).getMaktx());
			scrapMessage.setState(ThinkWayUtil.getDictValueByDictKey(dictService, "scrapState", scrapMessage.getScrap().getState()));
			lst.add(scrapMessage);
		}
		pager.setList(lst);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil
				.getExcludeFields(ScrapMessage.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getWbId() {
		return wbId;
	}

	public void setWbId(String wbId) {
		this.wbId = wbId;
	}

	public WorkingBill getWorkingbill() {
		return workingbill;
	}

	public void setWorkingbill(WorkingBill workingbill) {
		this.workingbill = workingbill;
	}
	
	//获取所有状态
	public List<Dict> getAllState() {
		return dictService.getList("dictname", "scrapState");
	}	

}
