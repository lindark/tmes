package cc.jiuyi.action.admin;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CallreasonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 后台Action类 - 异常
 */
@ParentPackage("admin")
public class AbnormalAction extends BaseAdminAction {

	private static final long serialVersionUID = 7823213806344131048L;
	
	private Abnormal abnormal;
	private String loginUsername;
	private String aid;
	private String cancelId;
	private String callId;
	private String nameId;
	private String callReasonId;
	
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private AdminService adminService;
	@Resource
	private CallreasonService callReasonService;
	@Resource
	private DictService dictService;
	
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
		//pager = abnormalService.findByPager(pager);
		return LIST;
	}
	
	// 列表
	public String browser() {
		return "browser";
	}
	
	// ajax列表
	public String ajlist() {
		if(pager == null) {
			pager = new Pager();

		}
		pager = abnormalService.findByPager(pager);
		
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			Abnormal abnormal  = (Abnormal)pagerlist.get(i);
			abnormal.setCraftSet(null);
			abnormal.setModelSet(null);
			abnormal.setQualitySet(null);
			abnormal.setAdminSet(null);
			/*abnormal.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "abnormalState", abnormal.getState()));*/
			String callR=callReasonService.load(abnormal.getMessage()).getCallReason();
			abnormal.setCallReason(callR);
			abnormal.setCallreasonSet(null);
			pagerlist.set(i, abnormal);
		}
		pager.setList(pagerlist);
		
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	@InputConfig(resultName = "error")
	public String update() {
		
		if(aid!=null){
			
		    Abnormal persistent = abnormalService.load(aid);
		    persistent.setReplyDate(new Date());
		    persistent.setState("2");
		    persistent.setHandlingTime(1);
		    abnormalService.update(persistent);
		}else if(cancelId!=null){
			Abnormal persistent = abnormalService.load(cancelId);
			persistent.setState("4");
			abnormalService.update(persistent);
		}
		
		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
	}
	
	// 添加
	public String addMessage() {
		return "abnormal_message";
	}
	
	public String save() {	
		
		loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
		Admin admin1 = adminService.get("username", loginUsername);
		Abnormal abnormal = new Abnormal();
		abnormal.setCallDate(new Date());

		Admin admin = adminService.load(nameId);
		Date date = new Date();
		System.out.println(date);
		//abnormal.setCallDate(date);
		System.out.println(abnormal);
		abnormal.setResponsor(admin.getName());

		abnormal.setCreateUser(admin1.getId());

		abnormal.setModifyUser(admin1.getId());
		abnormal.setIniitiator(admin1.getId());

		abnormal.setIsDel("N");
		abnormal.setFactoryName("建新赵氏密封条工厂");
		//abnormal.setHandlingTime(new Integer(0));
		
		abnormal.setShopName("2车间");
		abnormal.setState("0");
		abnormal.setUnitName("2单元");
		abnormal.setTeamId("02");
		abnormal.setMessage(callReasonId);

		abnormalService.save(abnormal);
		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
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

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getCancelId() {
		return cancelId;
	}

	public void setCancelId(String cancelId) {
		this.cancelId = cancelId;
	}

	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getNameId() {
		return nameId;
	}

	public void setNameId(String nameId) {
		this.nameId = nameId;
	}
	
	// 获取所有原因
	public List<Callreason> getCallReasonList() {
		return callReasonService.getAll();
	}

	public String getCallReasonId() {
		return callReasonId;
	}

	public void setCallReasonId(String callReasonId) {
		this.callReasonId = callReasonId;
	}
	
	
}
