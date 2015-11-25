package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.SwiptCard;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CallreasonService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.SwiptCardService;
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
	private String callId;
	private String nameId;

	private String callReasonId;
	private String closeIds;
	private String cancelIds;
	private String aids;
	private Admin admin;

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
	@Resource
	private SwiptCardService swiptCardService;
	@Resource
	private AbnormalLogService abnormalLogService;

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
		admin = adminService.getLoginAdmin();
		// pager = abnormalService.findByPager(pager);
		return LIST;
	}

	// 列表
	public String browser() {
		return "browser";
	}

	// ajax列表
	public String ajlist() {

		Admin admin1 = adminService.getLoginAdmin();

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

		System.out.println(admin1.getId());
		pager = abnormalService.getAbnormalPager(pager, map, admin1.getId());

		System.out.println(pager.getList().size());
		List pagerlist = pager.getList();
		for (int i = 0; i < pagerlist.size(); i++) {
			Abnormal abnormal = (Abnormal) pagerlist.get(i);
			List<Callreason> callreasonList = new ArrayList<Callreason>(
					abnormal.getCallreasonSet());// 消息
			List<String> strlist = new ArrayList<String>();
			for (Callreason callreason : callreasonList) {
				String str = callreason.getCallReason();
				strlist.add(str);
			}
			String comlist = CommonUtil.toString(strlist, ",");// 获取问题的字符串

			List<Admin> respon = new ArrayList<Admin>(
					abnormal.getResponsorSet());
			List<String> anslist = new ArrayList<String>();
			for (Admin admin : respon) {
				String str = admin.getName();
				anslist.add(str);
			}
			String anslist1 = CommonUtil.toString(anslist, ",");// 获取问题的字符串
			String ablists="";
			if(abnormal.getAbnormalLogSet().size()>0){
				List<AbnormalLog> abLog = new ArrayList<AbnormalLog>(
						abnormal.getAbnormalLogSet());
				List<String> ablist = new ArrayList<String>();
				for (AbnormalLog abnormalLog : abLog) {
					String str = abnormalLog.getInfo();
					ablist.add(str);
				}
				ablists = CommonUtil.toString(ablist, ",");// 获取问题的字符串
			}else{
				ablists="";
			}
					
			abnormal.setCallReason(comlist);
			abnormal.setAnswer(anslist1);
			abnormal.setLog(ablists);
			abnormal.setOriginator(abnormal.getIniitiator().getName());
			abnormal.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "abnormalState", abnormal.getState()));
			pagerlist.set(i, abnormal);
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Abnormal.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}

	@InputConfig(resultName = "error")
	public String update() {
		Admin admin1 = adminService.getLoginAdmin();
		List<Abnormal> abnormalList = abnormalService.get(ids);
        String person = admin1.getName();
		for (int i = 0; i < abnormalList.size(); i++) {
			List<Admin> adminList = null;
			Abnormal persistent = abnormalList.get(i);
			Set<Admin> responsorSet = persistent.getResponsorSet();
			if (responsorSet.contains(admin1)) {
				adminList = new ArrayList<Admin>();
				for (SwiptCard s : persistent.getSwiptCardSet()) {
					if (s.getType().equals("0")) {
						adminList.add(s.getAdmin());
					}
				}
				if (adminList.contains(admin1)) {
					addActionError("请勿重复刷卡!");
					return ERROR;
				}
				if (responsorSet.size() == (adminList.size() + 1)) {
					persistent.setState("2");
				} else {
					persistent.setState("1");

				}
				SwiptCard swiptCard = new SwiptCard();
				swiptCard.setAbnormal(persistent);
				swiptCard.setAdmin(admin1);
				swiptCard.setType("0");
				swiptCardService.save(swiptCard);
				
				AbnormalLog abnormalLog = new AbnormalLog();
				abnormalLog.setAbnormal(persistent);				
				abnormalLog.setInfo(person+"已刷卡");
				abnormalLog.setOperator(admin1);
				abnormalLogService.save(abnormalLog);
				
			} else {
				addActionError("刷卡错误!");
				return ERROR;
			}

			persistent.setReplyDate(new Date());
			Date date = new Date();
			int time = (int) ((date.getTime() - persistent.getCreateDate()
					.getTime()) / 60000);
			persistent.setHandlingTime(time);
			abnormalService.update(persistent);
		}

		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
	}

	public String close() {
		Admin admin3 = adminService.getLoginAdmin();
		String ids[] = closeIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			Abnormal persistent = abnormalService.load(ids[i]);
			if (persistent.getIniitiator().equals(admin3)) {
				if (persistent.getState() != "3" & persistent.getState() != "4") {
					persistent.setState("3");
					if (persistent.getReplyDate() == null) {
						persistent.setReplyDate(new Date());
						Date date = new Date();
						int time = (int) ((date.getTime() - persistent
								.getCreateDate().getTime()) / 60000);
						persistent.setHandlingTime(time);
					}
					abnormalService.update(persistent);
				} else {
					addActionError("刷卡错误!");
					return ERROR;
				}
			} else {
				addActionError("您没有关闭的权限!");
				return ERROR;
			}
		}

		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
	}

	public String cancel() {
		Admin admin2 = adminService.getLoginAdmin();
		String ids[] = cancelIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			Abnormal persistent = abnormalService.load(ids[i]);
			if (persistent.getIniitiator().equals(admin2)) {
				if (persistent.getState().equals("0")
						|| persistent.getState().equals("1")) {
					persistent.setState("4");
					persistent.setReplyDate(new Date());
					Date date = new Date();
					int time = (int) ((date.getTime() - persistent
							.getCreateDate().getTime()) / 60000);
					persistent.setHandlingTime(time);
					abnormalService.update(persistent);
				} else {
					addActionError("该异常不能撤销!");
					return ERROR;
				}
			} else {
				addActionError("您没有撤销的权限!");
				return ERROR;
			}

			Date date = new Date();
			int time = (int) ((date.getTime() - persistent.getCreateDate()
					.getTime()) / 60000);
			persistent.setHandlingTime(time);
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
		Admin admin = adminService.getLoginAdmin();

		Abnormal abnormal = new Abnormal();
		abnormal.setCallDate(new Date());
		abnormal.setIniitiator(admin);
		abnormal.setIsDel("N");
		abnormal.setState("0");

		abnormal.setResponsorSet(new HashSet<Admin>(adminSet));
		abnormal.setCallreasonSet(new HashSet<Callreason>(callReasonSet));
		abnormalService.save(abnormal);

		redirectionUrl = "abnormal!list.action";
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {
		ids = id.split(",");
		abnormalService.updateisdel(ids, "Y");
		// abnormalService.delete(ids);
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

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

}
