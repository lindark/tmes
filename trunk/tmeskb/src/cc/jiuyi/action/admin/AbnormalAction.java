package cc.jiuyi.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;

/**
 * 后台Action类 - 异常
 */
@ParentPackage("admin")
public class AbnormalAction extends BaseAdminAction {

	private static final long serialVersionUID = 7823213806344131048L;
	
	private Abnormal abnormal;
	private String loginUsername;
	
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private AdminService adminService;
	
	// 添加
	public String add() {
		return INPUT;
	}

	// 编辑
	public String edit() {
		abnormal = abnormalService.load(id);
		return INPUT;
	}

	// 列表
	public String list() {
		pager = abnormalService.findByPager(pager);
		return LIST;
	}
	
	// ajax列表
	public String ajlist() {
		if(pager == null) {
			pager = new Pager();

		}
		pager = abnormalService.findByPager(pager);
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	// 添加
	public String addMessage() {
		return "abnormal_message";
	}
	
	public String save() {	
		
		loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
		Admin admin = adminService.get("username", loginUsername);
		
		System.out.println(admin.getId());
		
		System.out.println(id);
		abnormal.setCallDate(new Date());
		abnormal.setCreateDate(new Date());
		abnormal.setModifyDate(new Date());
		abnormal.setReplyDate(new Date());
		
		abnormal.setCreateUser("张三");
		abnormal.setModifyUser("张三");
		abnormal.setIniitiator("张三");
		abnormal.setIsDel("N");
		abnormal.setFactoryName("建新赵氏密封条工厂");
		abnormal.setHandlingTime(new Integer(0));
		abnormal.setResponsor("李四");
		abnormal.setShopName("2车间");
		abnormal.setState("未确定");
		abnormal.setUnitName("2单元");
		
		abnormalService.save(abnormal);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "呼叫成功");	
		return ajaxJson(jsonMap);
	}

	// 删除
	public String delete() throws Exception {	
		ids=id.split(",");
		abnormalService.delete(ids);
		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
		//return ajaxJsonSuccessMessage("删除成功！");
	}
	
	public Abnormal getAbnormal() {
		return abnormal;
	}
	public void setAbnormal(Abnormal abnormal) {
		this.abnormal = abnormal;
	}

	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}
	
	
}
