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

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.CraftLog;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CraftLogService;
import cc.jiuyi.service.CraftService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 工艺维修单
 */

@ParentPackage("admin")
public class CraftAction extends BaseAdminAction {

	private static final long serialVersionUID = -2383463207248343967L;
	
	private Craft craft;
	private String aid;
	private Abnormal abnormal;
	private String abnormalId;
	private String loginUsername;
	
	@Resource
	private CraftService craftService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private DictService dictService;
	@Resource
	private AdminService adminService;
	@Resource
	private CraftLogService craftLogService;
	
	// 添加
	public String add() {
		if(aid!=null){
			abnormal=abnormalService.load(aid);
		}	
		return INPUT;
	}

	// 编辑
	public String edit() {
		craft = craftService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		//pager = craftService.findByPager(pager);
		return LIST;
	}
    
	@InputConfig(resultName = "error")
	public String update() {
		Craft persistent = craftService.load(id);
		BeanUtils.copyProperties(craft, persistent, new String[] { "id", "classes","abnormal","isDel","state"});
		craftService.update(persistent);
		redirectionUrl = "craft!list.action";
		return SUCCESS;
	}
	
    public String ajlist(){
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		if (pager.getOrderBy().equals("")) {
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
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
	
			if (obj.get("team") != null) {
				String team = obj.getString("team").toString();
				map.put("team", team);
			}
			
			if (obj.get("productName") != null) {
				String productName = obj.getString("productName").toString();
				map.put("productName", productName);
			}
			
		}

		pager = craftService.getCraftPager(pager, map);
		
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			Craft craft  = (Craft)pagerlist.get(i);
			craft.setAbnormal(null);
			craft.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "receiptState", craft.getState()));
			craft.setCraftLogSet(null);
			pagerlist.set(i, craft);
		}
		pager.setList(pagerlist);

		JSONArray jsonArray = JSONArray.fromObject(pager);
		System.out.println(jsonArray.get(0).toString());
		 return ajaxJson(jsonArray.get(0).toString());
		
	}
	
	public String save() {	
		loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
		Admin admin1 = adminService.get("username", loginUsername);
		
		abnormal=abnormalService.load(abnormalId);
		craft.setAbnormal(abnormal);
		craft.setState("0");
		craft.setIsDel("N");
		craftService.save(craft);	
		
		CraftLog log = new CraftLog();
		log.setOperator(admin1.getName());
		log.setInfo("已提交");
		log.setCraft(craft);
		craftLogService.save(log);
		
		redirectionUrl = "craft!list.action";
		return SUCCESS;
	}
		
	// 删除
	public String delete() throws Exception {
		ids=id.split(",");
		craftService.delete(ids);
		redirectionUrl = "craft!list.action";
		return SUCCESS;
	}

	public Craft getCraft() {
		return craft;
	}

	public void setCraft(Craft craft) {
		this.craft = craft;
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

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}
	
	
}
