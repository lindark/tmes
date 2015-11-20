package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
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
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Member;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CallreasonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.CommonUtil;
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
	private String closeId;
	private String closeIds;
	private String cancelIds;
	private String aids;
	 
	private List<Admin> adminSet;
	private List<Callreason> callReasonSet;
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
		
		loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
		Admin admin1 = adminService.get("username", loginUsername);
		
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
		
		pager = abnormalService.getAbnormalPager(pager,map,admin1.getId());
		
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			Abnormal abnormal  = (Abnormal)pagerlist.get(i);
			List<Callreason> callreasonList = new ArrayList<Callreason>(abnormal.getCallreasonSet());//消息
			List<String> strlist = new ArrayList<String>();
			for(Callreason callreason : callreasonList){
				String str = callreason.getCallReason();
				strlist.add(str);
			}
			String comlist = CommonUtil.toString(strlist, ",");//获取问题的字符串
			
			List<Admin> respon = new ArrayList<Admin>(abnormal.getResponsorSet());
			for(Admin admin : respon){
				String str = admin.getName();
				//respon.add(str);
			}
			//String comlist = CommonUtil.toString(respon, ",");//获取问题的字符串
			abnormal.setCallReason(comlist);
			
			pagerlist.set(i, abnormal);
		}
		
		
		JSONArray jsonArray = JSONArray.fromObject(pager);
		return ajaxJson(jsonArray.get(0).toString());
	}
//	
//	@InputConfig(resultName = "error")
//	public String update() {
//		
//		if(aid!=null){
//			
//		    Abnormal persistent = abnormalService.load(aid);
//		    persistent.setReplyDate(new Date());
//		    persistent.setState("2");
//		    Date date = new Date();
//		    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
//		    persistent.setHandlingTime(time);
//		    abnormalService.update(persistent);
//		}else if(aids!=null){
//			System.out.println(aids);
//			String id[] =aids.split(",");
//			for(int i=0;i<id.length;i++){
//				Abnormal persistent = abnormalService.load(id[i]);
//				if(persistent.getState().equals("0")){
//					persistent.setReplyDate(new Date());
//				    persistent.setState("2");
//				    Date date = new Date();
//				    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
//				    persistent.setHandlingTime(time);
//				    abnormalService.update(persistent);
//				}
//			}
//		}else if(cancelId!=null){
//			Abnormal persistent = abnormalService.load(cancelId);
//			persistent.setState("4");
//			persistent.setReplyDate(new Date());
//			Date date = new Date();
//		    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
//		    persistent.setHandlingTime(time);
//			abnormalService.update(persistent);
//		}else if(cancelIds!=null){
//			String id[] =cancelIds.split(",");
//			for(int i=0;i<id.length;i++){
//				Abnormal persistent = abnormalService.load(id[i]);
//				if(persistent.getState().equals("0")){
//					persistent.setState("4");
//					persistent.setReplyDate(new Date());
//					Date date = new Date();
//				    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
//				    persistent.setHandlingTime(time);
//					abnormalService.update(persistent);
//				}
//			}
//			
//			
//		}else if(closeId!=null){	
//				Abnormal persistent=abnormalService.load(closeId);
//				persistent.setState("3");
//				persistent.setReplyDate(new Date());
//				Date date = new Date();
//			    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
//			    persistent.setHandlingTime(time);
//				abnormalService.update(persistent);				
//		}else if(closeIds!=null){
//			String id[] =closeIds.split(",");
//			for(int i=0;i<id.length;i++){
//				Abnormal persistent=abnormalService.load(id[i]);
//				if(persistent.getState()!="3" || persistent.getState()!="4"){
//					persistent.setState("3");
//					if(persistent.getReplyDate()==null){
//						persistent.setReplyDate(new Date());
//						Date date = new Date();
//					    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
//					    persistent.setHandlingTime(time);
//					}
//					abnormalService.update(persistent);	
//				}
//			}
//		}
		
//		redirectionUrl = "abnormal!list.action";
//		return SUCCESS;
//	}
	
	// 添加
	public String addMessage() {
		return "abnormal_message";
	}
	
	public String save() {	
		Admin admin = adminService.getLoginAdmin();
		
		Abnormal abnormal = new Abnormal();
		abnormal.setCallDate(new Date());
		abnormal.setIniitiator(admin);
		abnormal.setIsDel("N");
		abnormal.setFactoryName("建新赵氏密封条工厂");		
		abnormal.setShopName("2车间");
		abnormal.setState("0");
		abnormal.setUnitName("2单元");
		abnormal.setTeamId("02");
		abnormal.setResponsorSet(new HashSet<Admin>(adminSet));
		abnormal.setCallreasonSet(new HashSet<Callreason>(callReasonSet));
		abnormalService.save(abnormal);
		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {	
		ids=id.split(",");
		abnormalService.updateisdel(ids, "Y");
		//abnormalService.delete(ids);
		redirectionUrl = "abnormal!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
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

	public String getCloseId() {
		return closeId;
	}

	public void setCloseId(String closeId) {
		this.closeId = closeId;
	}

	public String getCloseIds() {
		return closeIds;
	}

	public void setCloseIds(String closeIds) {
		this.closeIds = closeIds;
	}

	public String getCancelIds() {
		return cancelIds;
	}

	public void setCancelIds(String cancelIds) {
		this.cancelIds = cancelIds;
	}

	public String getAids() {
		return aids;
	}

	public void setAids(String aids) {
		this.aids = aids;
	}

	public List<Admin> getAdminSet() {
		return adminSet;
	}

	public void setAdminSet(List<Admin> adminSet) {
		this.adminSet = adminSet;
	}

	public List<Callreason> getCallReasonSet() {
		return callReasonSet;
	}

	public void setCallReasonSet(List<Callreason> callReasonSet) {
		this.callReasonSet = callReasonSet;
	}
	
	
}
