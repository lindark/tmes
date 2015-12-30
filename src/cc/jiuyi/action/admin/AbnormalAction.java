package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.Calendar;
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

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.BeanUtils;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;

import cc.jiuyi.action.cron.ExtremelyMessage;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.Craft;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.SwiptCard;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CallreasonService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.SwiptCardService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.QuartzManagerUtil;
import cc.jiuyi.util.SendMsgUtil;
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
	private String cardnumber;//刷卡卡号
	private  String job_name = "sendMessage";
	
	private List<Department> list;
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
	@Resource
	private DepartmentService deptservice;

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
		admin = adminService.get(admin.getId());
		return LIST;
	}

	// 列表
	public String browser() {
		list = deptservice.getAllByHql();
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


		pager = abnormalService.getAbnormalPager(pager, map, admin1.getId());

		List pagerlist = pager.getList();
   
        
		for (int i = 0; i < pagerlist.size(); i++) {
			Abnormal abnormal = (Abnormal) pagerlist.get(i);
			List<Admin> adminList = null;
			
			//消息处理
			List<Callreason> callreasonList = new ArrayList<Callreason>(
					abnormal.getCallreasonSet());// 消息
			List<String> strlist = new ArrayList<String>();
			for (Callreason callreason : callreasonList) {
				String str = callreason.getCallReason();
				strlist.add(str);
			}
			String comlist = CommonUtil.toString(strlist, ",");// 获取问题的字符串

			//应答人处理
			List<Admin> respon = new ArrayList<Admin>(
					abnormal.getResponsorSet());
			
			adminList = new ArrayList<Admin>();
			for (SwiptCard s : abnormal.getSwiptCardSet()) {
				if (s.getType().equals("0")) {
					adminList.add(s.getAdmin());
				}
			}
			
			List<String> anslist = new ArrayList<String>();
			
			for (Admin admin : respon) {
				String str;
				if(!adminList.contains(admin)){
					str = "<span style='color:red'>"+admin.getName()+"</span>";
				}else{
					str = admin.getName();							
				}
				anslist.add(str);
			}
			
			String anslist1 = CommonUtil.toString(anslist, ",");// 获取问题的字符串
		
			//日志处理
			String ablists="";			
			
			List<AbnormalLog> abLog = new ArrayList<AbnormalLog>(abnormal.getAbnormalLogSet());
			List<String> ablist = new ArrayList<String>();	
			List<Quality> qualityList = new ArrayList<Quality>(abnormal.getQualitySet());
			List<Model> modelList = new ArrayList<Model>(abnormal.getModelSet());
			List<Craft> craftList = new ArrayList<Craft>(abnormal.getCraftSet());
	        List<Device> deviceList = new ArrayList<Device>(abnormal.getDeviceSet());
	 
			if(abLog.size()>0){	
				if(qualityList.size()>1){					
					String str1="已开"+"<input type='hidden' class='abnorId' value='"+abnormal.getId()+"' />"+"<a id='quality'  style='color:#428bca;cursor:pointer'>质量问题单</a>"+"("+qualityList.size()+")";
					ablist.add(str1);
				}
				if(modelList.size()>1){					
					String str2="已开"+"<input type='hidden' class='abnorId' value='"+abnormal.getId()+"' />"+"<a id='model'  style='color:#428bca;cursor:pointer'>工模维修单</a>"+"("+modelList.size()+")";
           		    ablist.add(str2);
           	    }
				
				 if(craftList.size()>1){            		
					 String str3="已开"+"<input type='hidden' class='abnorId' value='"+abnormal.getId()+"' />"+"<a id='craft'  style='color:#428bca;cursor:pointer'>工艺维修单</a>"+"("+craftList.size()+")";
					 ablist.add(str3);
            	 }
				 if(deviceList.size()>1){    
					 String str4="已开"+"<input type='hidden' class='abnorId' value='"+abnormal.getId()+"' />"+"<a id='device'  style='color:#428bca;cursor:pointer'>设备维修单</a>"+"("+deviceList.size()+")";
					 ablist.add(str4);
            	 }
				String str;
		
				for(AbnormalLog ab:abLog){
					
					String type = ab.getType();
					String info= ab.getInfo();
                     if(type.equalsIgnoreCase("0") && qualityList.size()==1){                  	                    		
                    		 str="已开"+"<a href='quality!view.action?id="+qualityList.get(0).getId()+"'>质量问题单</a>"; 
					 }
                     else  if(type.equalsIgnoreCase("1") && modelList.size()==1){						
                    		 str="已开"+"<a href='model!view.action?id="+modelList.get(0).getId()+"'>工模维修单</a>";                     	
					 }else if(type.equalsIgnoreCase("2") && craftList.size()==1){						
                    		 str="已开"+"<a href='craft!view.action?id="+craftList.get(0).getId()+"'>工艺维修单</a>";                     	
					 }else if(type.equalsIgnoreCase("3") && deviceList.size()==1){						
                    		 str="已开"+"<a href='device!view.action?id="+deviceList.get(0).getId()+"'>设备维修单</a>";                      		 
					 }else if(type.equalsIgnoreCase("5") && StringUtils.isNotEmpty(info)){
						 str="已向"+info+"发送短信";
					 }else{
						 str="";
					 }
                     if(StringUtils.isNotEmpty(str) && !str.equalsIgnoreCase("")){
                    	 ablist.add(str);
                     }

				}
			}else{
				ablist.add("");
			}

			if(ablist.size()==0){
				ablists="";
			}else{
				ablists = CommonUtil.toString(ablist, ",");// 获取问题的字符串
			}
			//ablists = CommonUtil.toString(ablist, ",");// 获取问题的字符串	
			abnormal.setCallReason(comlist);
			abnormal.setAnswer(anslist1);
			abnormal.setLog(ablists);
			abnormal.setOriginator(abnormal.getIniitiator().getName());
			
			//处理时间设置
			if(abnormal.getState().equalsIgnoreCase("3") || abnormal.getState().equalsIgnoreCase("4")){
				abnormal.setDisposeTime(String.valueOf(abnormal.getHandlingTime()));
			}else{
				Date date = new Date();
				int time = (int) ((date.getTime() - abnormal.getCreateDate().getTime()) / 1000);
				abnormal.setDisposeTime(String.valueOf(time));
			}
			
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

	//响应刷卡
	public String creditresponse() {
		admin = adminService.getByCardnum(cardnumber);
		
		List<Abnormal> abnormalList = abnormalService.get(ids);
		for (int i = 0; i < abnormalList.size(); i++) {
			List<Admin> adminList = null;
			Abnormal persistent = abnormalList.get(i);
			Set<Admin> responsorSet = persistent.getResponsorSet();//获取应答人
			if (responsorSet.contains(admin)) {
				adminList = new ArrayList<Admin>();
				for (SwiptCard s : persistent.getSwiptCardSet()) {
					if (s.getType().equals("0")) {//判断是否是响应刷卡类型
						adminList.add(s.getAdmin());
					}
				}
				if (adminList.contains(admin)) {
					return ajaxJsonErrorMessage("请勿重复刷卡!");
				}
				if (responsorSet.size() == (adminList.size() + 1)) {
					persistent.setState("2");
					removeQuartz();			//删除定时发短信任务		
				} else {
					persistent.setState("1");

				}
				SwiptCard swiptCard = new SwiptCard();
				swiptCard.setAbnormal(persistent);
				swiptCard.setAdmin(admin);
				swiptCard.setType("0");
				swiptCardService.save(swiptCard);
				
				AbnormalLog abnormalLog = new AbnormalLog();
				abnormalLog.setAbnormal(persistent);				
				abnormalLog.setType("4");
				abnormalLog.setOperator(admin);
				abnormalLogService.save(abnormalLog);
				
			} else {
				return ajaxJsonErrorMessage("您不是应答人，刷卡错误!");
			}

			persistent.setReplyDate(new Date());
			abnormalService.update(persistent);
		}

		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	//关闭异常
	public String creditclose() {
		admin = adminService.getByCardnum(cardnumber);
		String ids[] = closeIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			Abnormal persistent = abnormalService.load(ids[i]);
			if (persistent.getIniitiator().equals(admin)) {//判断刷卡人是否是异常发起人
				if (persistent.getState() != "3" & persistent.getState() != "4") {//"3"异常关闭 "4"异常撤销
					persistent.setState("3");
					if (persistent.getReplyDate() == null) {
						persistent.setReplyDate(new Date());
						Date date = new Date();
						int time = (int) ((date.getTime()-persistent.getCreateDate().getTime())/1000);
						persistent.setHandlingTime(time);
					}else{
						Date date = new Date();
						int time = (int) ((date.getTime()-persistent.getCreateDate().getTime())/1000);
						persistent.setHandlingTime(time);
					}
					abnormalService.update(persistent);
				} else {
					return ajaxJsonErrorMessage("异常已撤销/关闭!");
				}
			} else {
				return ajaxJsonErrorMessage("您没有关闭的权限!");
			}
		}

		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

    //撤销呼叫
	public String creditundo() {
		admin = adminService.getByCardnum(cardnumber);
		String ids[] = cancelIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			Abnormal persistent = abnormalService.load(ids[i]);
			if (persistent.getIniitiator().equals(admin)) {//判断刷卡人是否是异常发起人
				if (persistent.getState().equals("0")
						|| persistent.getState().equals("1")) {//"0"未响应  "1"未完全响应
					persistent.setState("4");
					persistent.setReplyDate(new Date());
					Date date = new Date();
					int time = (int) ((date.getTime()-persistent.getCreateDate().getTime())/1000);
					persistent.setHandlingTime(time);
					abnormalService.update(persistent);
				} else {
					return ajaxJsonErrorMessage("该异常不能撤销!");
				}
			} else {
				return ajaxJsonErrorMessage("您没有撤销的权限!");
			}

		}

		return ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 添加
	public String addMessage() {
		return "abnormal_message";
	}

	//刷卡提交
	public String creditsave(){
		admin = adminService.getByCardnum(cardnumber);
		
		Abnormal abnormal = new Abnormal();
		abnormal.setCallDate(new Date());
		abnormal.setIniitiator(admin);
		abnormal.setIsDel("N");
		abnormal.setState("0");

		if(adminSet==null){
			return ajaxJsonErrorMessage("人员不允许为空!");
		} 
		
		if(callReasonSet==null){
			return ajaxJsonErrorMessage("短信不允许为空!");
		} 
		
		List<Admin> adminSets= new ArrayList<Admin>();
		List<String> strList= new ArrayList<String>();
		for(int i=0;i<adminSet.size();i++){
			if(adminSet.get(i)==null)continue;
			adminSets.add(adminSet.get(i));
			strList.add(adminSet.get(i).getName());
		}
		
		String comlist = CommonUtil.toString(strList, ",");// 获取问题的字符串
		List<Callreason> callReasonSets= new ArrayList<Callreason>();
		for(int i=0;i<callReasonSet.size();i++){
			if(callReasonSet.get(i)==null)continue;
			callReasonSets.add(callReasonSet.get(i));
		}
		abnormal.setResponsorSet(new HashSet<Admin>(adminSets));
		abnormal.setCallreasonSet(new HashSet<Callreason>(callReasonSets));
		
		abnormalService.save(abnormal);
		
		for(Admin admin:adminSets){//向应答人发送短信
			try{
			 
			 String str = SendMsgUtil.SendMsg(admin.getPhoneNo(),"XX工厂出现异常");			
			 SAXReader reader = new SAXReader();  //解析返回xml文件
             Document doc;   
             doc = DocumentHelper.parseText(str); 
             Node stateNode = doc.selectSingleNode("/infos/info/state");
        	 if(!stateNode.getText().equalsIgnoreCase("0")){//短信发送失败
        		 ajaxJsonErrorMessage("短信发送失败!");
        	 }
			}catch(Exception e){
				e.printStackTrace();
			}
		}
				
		AbnormalLog abnormalLog = new AbnormalLog();//创建异常日志
		abnormalLog.setAbnormal(abnormal);
		abnormalLog.setType("5");
		abnormalLog.setOperator(admin);
		abnormalLog.setInfo(comlist);
		abnormalLogService.save(abnormalLog);

		//定时短信任务处理
		List<Admin> adminList=adminService.getByAdminId(admin.getDepartment().getId());//部长
		List<Admin> adminList1=adminService.getDirectorByDeptId(admin.getDepartment().getId());//主任
		List<Admin> adminList2=adminService.getManagerByDeptId(admin.getDepartment().getId());//副总
		String phone = adminList.get(0).getPhoneNo();
		String phone1 = adminList1.get(0).getPhoneNo();
		String phone2 = adminList2.get(0).getPhoneNo();
		String name=adminList.get(0).getName();
		String name1=adminList1.get(0).getName();
		String name2=adminList2.get(0).getName();
				
		Calendar can = Calendar.getInstance();	//定时任务时间1
		can.setTime(abnormal.getCreateDate());
		can.add(Calendar.MINUTE, 10);
		Date date=can.getTime();	
		
		Calendar can1 = Calendar.getInstance();//定时任务时间2
		can1.setTime(abnormal.getCreateDate());
		can1.add(Calendar.MINUTE, 30);
		Date date1=can1.getTime();
		
		Calendar can2 = Calendar.getInstance();//定时任务时间3
		can2.setTime(abnormal.getCreateDate());
		can2.add(Calendar.MINUTE, 60);
		Date date2=can2.getTime();
		
		System.out.println(ThinkWayUtil.getCron(date));
		HashMap<String,Object> maps = new HashMap<String,Object>();
		maps.put("id",abnormal.getId());
		maps.put("name",admin.getId());
		maps.put("date", ThinkWayUtil.getCron(date1));
		maps.put("time", ThinkWayUtil.getCron(date2));
		maps.put("jobname", job_name);
		maps.put("count","1");
		maps.put("phone",phone);
		maps.put("minister", name);
		maps.put("phone1",phone1);
		maps.put("director", name1);
		maps.put("phone2",phone2);
		maps.put("manager", name2);
		quartzMessage(ThinkWayUtil.getCron(date),maps);	
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	//创建定时任务
	public void quartzMessage(String time,HashMap<String,Object> maps){				 
			  QuartzManagerUtil.addJob(job_name, ExtremelyMessage.class,time,maps);			      	      
	}
	
	//删除定时任务
	public void removeQuartz(){
		 QuartzManagerUtil.removeJob(job_name);
	}
	
	// 删除
	public String delete() throws Exception {
		ids = id.split(",");
		abnormalService.updateisdel(ids, "Y");
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

	public List<Department> getList() {
		return list;
	}

	public void setList(List<Department> list) {
		this.list = list;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	
}
