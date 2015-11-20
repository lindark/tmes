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
		
		pager = abnormalService.getAbnormalPager(pager,map);
		
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			Abnormal abnormal  = (Abnormal)pagerlist.get(i);
			abnormal.setCraftSet(null);
			abnormal.setModelSet(null);
			abnormal.setQualitySet(null);
			abnormal.setAdminSet(null);
			abnormal.setDeviceSet(null);
			abnormal.setAbnormalLogSet(null);
			abnormal.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "abnormalState", abnormal.getState()));
			if(abnormal.getMessage().length()>36){
				String id=abnormal.getMessage().substring(0, abnormal.getMessage().length()-1);
				String me[] = id.split(",");
				StringBuffer s = new StringBuffer();
				for(int ii=0;ii<me.length;ii++){					
					String callR=callReasonService.load(me[ii]).getCallReason();
					s.append(callR+",");
				}
				abnormal.setCallReason(s.toString());
			}else{
				String id=abnormal.getMessage().substring(0, abnormal.getMessage().length()-1);
				String callR=callReasonService.load(id).getCallReason();
				abnormal.setCallReason(callR);
			}
			
			String org=adminService.load(abnormal.getIniitiator()).getName();
			abnormal.setOriginator(org);
			
			if(abnormal.getResponsor().length()>36){
				String id=abnormal.getResponsor().substring(0, abnormal.getResponsor().length()-1);
				String me[] = id.split(",");
				StringBuffer s = new StringBuffer();
				for(int ii=0;ii<me.length;ii++){												
						String ans=adminService.load(me[ii]).getName();
						s.append(ans+",");					
				}
				abnormal.setAnswer(s.toString());
			}else{
				String id=abnormal.getResponsor().substring(0, abnormal.getResponsor().length()-1);
				String ans=adminService.load(id).getName();
				abnormal.setAnswer(ans);
			}
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
		    Date date = new Date();
		    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
		    persistent.setHandlingTime(time);
		    abnormalService.update(persistent);
		}else if(aids!=null){
			System.out.println(aids);
			String id[] =aids.split(",");
			for(int i=0;i<id.length;i++){
				Abnormal persistent = abnormalService.load(id[i]);
				if(persistent.getState().equals("0")){
					persistent.setReplyDate(new Date());
				    persistent.setState("2");
				    Date date = new Date();
				    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
				    persistent.setHandlingTime(time);
				    abnormalService.update(persistent);
				}
			}
		}else if(cancelId!=null){
			Abnormal persistent = abnormalService.load(cancelId);
			persistent.setState("4");
			persistent.setReplyDate(new Date());
			Date date = new Date();
		    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
		    persistent.setHandlingTime(time);
			abnormalService.update(persistent);
		}else if(cancelIds!=null){
			String id[] =cancelIds.split(",");
			for(int i=0;i<id.length;i++){
				Abnormal persistent = abnormalService.load(id[i]);
				if(persistent.getState().equals("0")){
					persistent.setState("4");
					persistent.setReplyDate(new Date());
					Date date = new Date();
				    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
				    persistent.setHandlingTime(time);
					abnormalService.update(persistent);
				}
			}
			
			
		}else if(closeId!=null){	
				Abnormal persistent=abnormalService.load(closeId);
				persistent.setState("3");
				persistent.setReplyDate(new Date());
				Date date = new Date();
			    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
			    persistent.setHandlingTime(time);
				abnormalService.update(persistent);				
		}else if(closeIds!=null){
			String id[] =closeIds.split(",");
			for(int i=0;i<id.length;i++){
				Abnormal persistent=abnormalService.load(id[i]);
				if(persistent.getState()!="3" || persistent.getState()!="4"){
					persistent.setState("3");
					if(persistent.getReplyDate()==null){
						persistent.setReplyDate(new Date());
						Date date = new Date();
					    int time=(int)((date.getTime()-persistent.getCreateDate().getTime())/60000);
					    persistent.setHandlingTime(time);
					}
					abnormalService.update(persistent);	
				}
			}
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
		abnormal.setCreateUser(admin1.getId());
		abnormal.setModifyUser(admin1.getId());
		abnormal.setIniitiator(admin1.getId());
		abnormal.setIsDel("N");
		abnormal.setFactoryName("建新赵氏密封条工厂");		
		abnormal.setShopName("2车间");
		abnormal.setState("0");
		abnormal.setUnitName("2单元");
		abnormal.setTeamId("02");
		StringBuffer s = new StringBuffer();
		List<Admin> list=new ArrayList<Admin>();
		for (int i = 0; i < adminSet.size(); i++) {
			Admin v = adminSet.get(i);
			if(v!=null){
				list.add(v);
				s.append(v.getId()+",");
			}
		}
		abnormal.setResponsor(s.toString());
		
		StringBuffer s1 = new StringBuffer();
	    abnormal.setAdminSet(new HashSet<Admin>(list));  
	    List<Callreason> list1=new ArrayList<Callreason>();
        for (int i = 0; i < callReasonSet.size(); i++) {
        	Callreason v = callReasonSet.get(i);
        	if(v!=null){
        		list1.add(v);
        		s1.append(v.getId()+",");
        	}
		}
        abnormal.setMessage(s1.toString());

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
