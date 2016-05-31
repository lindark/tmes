package cc.jiuyi.action.admin;

import java.text.SimpleDateFormat;
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
import org.apache.log4j.Logger;
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
import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FlowingRectify;
import cc.jiuyi.entity.Member;
import cc.jiuyi.entity.Model;
import cc.jiuyi.entity.Quality;
import cc.jiuyi.entity.SwiptCard;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CallreasonService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.SwiptCardService;
import cc.jiuyi.service.UnitdistributeProductService;
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
	public static Logger log = Logger.getLogger(Abnormal.class);
	private Abnormal abnormal;
	private String loginUsername;
	private String callId;
	private String nameId;
	private String loginid;//当前登录人的ID

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
	@Resource
	private UnitdistributeProductService productservice;

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
		if(loginid!=null && !"".equals(loginid)){//当前登陆人生产日期和班次绑定
			admin=adminService.get(loginid);
			if(admin.getShift()==null||"".equals(admin.getShift())||admin.getProductDate()==null||"".equals(admin.getProductDate()))
			{
				addActionError("请选择生产日期及班次!");
				return ERROR;
			}
		}
		
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());

		
		//班次
		admin.setXshift(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", admin.getShift()));
		return LIST;
	}

	// 列表
	public String browser() {
		list = deptservice.getAllByHql();
		return "browser";
	}

	// ajax列表
	public String ajlist() {

		Admin admin2 = adminService.getLoginAdmin();//获取当前登录人
		admin2 = adminService.get(admin2.getId());
	
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


		pager = abnormalService.getAbnormalPager(pager, map, admin2);//分页查询语句

		List pagerlist = pager.getList();  
        
		for (int i = 0; i < pagerlist.size(); i++) {//对取到的对象进行重新封装
			Abnormal abnormal = (Abnormal) pagerlist.get(i);
			List<Admin> adminList = null;
			
			//消息处理，消息与异常多对多，将对象描述以字符串的形式进行拼接显示到页面上
			List<Callreason> callreasonList = new ArrayList<Callreason>(
					abnormal.getCallreasonSet());// 消息
			List<String> strlist = new ArrayList<String>();
			for (Callreason callreason : callreasonList) {
				String str = callreason.getCallReason();
				strlist.add(str);
			}
			String comlist = CommonUtil.toString(strlist, ",");// 获取问题的字符串

			//应答人处理，应答人与异常多对多，将对象描述以字符串的形式进行拼接显示到页面上
			List<Admin> respon = new ArrayList<Admin>(
					abnormal.getResponsorSet());
			
			adminList = new ArrayList<Admin>();
			for (SwiptCard s : abnormal.getSwiptCardSet()) {
				if (s.getType().equals("0")) {//0指的是响应刷卡类型
					adminList.add(s.getAdmin());//将已经刷过卡的人添加进入集合
				}
			}
			
			List<String> anslist = new ArrayList<String>();
			
			for (Admin admin : respon) {//循环应答人
				String str;
				if(!adminList.contains(admin)){//判断该集合中是否存在刷过卡的人，将没有刷过卡的人标记为红色
					str = "<span style='color:red'>"+admin.getName()+"</span>";
				}else{
					str = admin.getName();							
				}
				anslist.add(str);
			}
			
			String anslist1 = CommonUtil.toString(anslist, ",");// 获取问题的字符串
		
			//日志处理
			String ablists="";			
			
			List<AbnormalLog> abLog = new ArrayList<AbnormalLog>(abnormal.getAbnormalLogSet());//异常日志记录
			List<String> ablist = new ArrayList<String>();	
			List<Quality> qualityList = new ArrayList<Quality>(abnormal.getQualitySet());//该异常下的所有质量问题单
			List<Model> modelList = new ArrayList<Model>(abnormal.getModelSet());//该异常下的所有工模维修单
			List<Craft> craftList = new ArrayList<Craft>(abnormal.getCraftSet());//该异常下的所有工艺维修单
	        List<Device> deviceList = new ArrayList<Device>(abnormal.getDeviceSet());//该异常下的所有设备维修单
	 
			if(abLog.size()>0){	
				if(qualityList.size()>1){//质量问题单超出一张，页面链接到单据清单页面					
					String str1="已开"+"<input type='hidden' class='abnorId' value='"+abnormal.getId()+"' />"+"<a class='quality'  style='color:#428bca;cursor:pointer'>质量问题单</a>"+"("+qualityList.size()+")";
					ablist.add(str1);
				}
				if(modelList.size()>1){	//工模维修单超出一张，页面链接到单据清单页面					
					String str2="已开"+"<input type='hidden' class='abnorId' value='"+abnormal.getId()+"' />"+"<a class='model'  style='color:#428bca;cursor:pointer'>工模维修单</a>"+"("+modelList.size()+")";
           		    ablist.add(str2);
           	    }
				
				 if(craftList.size()>1){//工艺维修单超出一张，页面链接到单据清单页面         		
					 String str3="已开"+"<input type='hidden' class='abnorId' value='"+abnormal.getId()+"' />"+"<a class='craft'  style='color:#428bca;cursor:pointer'>工艺维修单</a>"+"("+craftList.size()+")";
					 ablist.add(str3);
            	 }
				 if(deviceList.size()>1){ //设备维修单超出一张，页面链接到单据清单页面   
					 String str4="已开"+"<input type='hidden' class='abnorId' value='"+abnormal.getId()+"' />"+"<a class='device'  style='color:#428bca;cursor:pointer'>设备维修单</a>"+"("+deviceList.size()+")";
					 ablist.add(str4);
            	 }
				String str;
		
				for(AbnormalLog ab:abLog){
					
					String type = ab.getType();//获取日志类型
					String info= ab.getInfo();//获取日志描述
                     if(type.equalsIgnoreCase("0") && qualityList.size()==1){  //日志类型为0，表示该异常已开质量问题单 ,质量问题单 只有一个，则链接到单据详情页面             	                    		
                    		 str="已开"+"<a href='quality!view.action?id="+qualityList.get(0).getId()+"'>质量问题单</a>"; 
					 }
                     else  if(type.equalsIgnoreCase("1") && modelList.size()==1){//日志类型为1，表示该异常已开工模维修单 ,工模维修单 只有一个，则链接到单据详情页面       						
                    		 str="已开"+"<a href='model!view.action?id="+modelList.get(0).getId()+"'>工模维修单</a>";                     	
					 }else if(type.equalsIgnoreCase("2") && craftList.size()==1){	//日志类型为2，表示该异常已开工艺维修单 ,工艺维修单 只有一个，则链接到单据详情页面    					
                    		 str="已开"+"<a href='craft!view.action?id="+craftList.get(0).getId()+"'>工艺维修单</a>";                     	
					 }else if(type.equalsIgnoreCase("3") && deviceList.size()==1){	//日志类型为3，表示该异常已开设备维修单 ,设备维修单 只有一个，则链接到单据详情页面   					
                    		 str="已开"+"<a href='device!view.action?id="+deviceList.get(0).getId()+"'>设备维修单</a>";                      		 
					 }else if(type.equalsIgnoreCase("5") && StringUtils.isNotEmpty(info)){
						 str="已向"+info+"发送短信";
					 }else{
						 str="";
					 }
                     if(StringUtils.isNotEmpty(str) && !str.equalsIgnoreCase("")){//不为空放入集合
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
			abnormal.setCallReason(comlist);//封装短息
			abnormal.setAnswer(anslist1);//封装应答人
			abnormal.setLog(ablists);//封装日志
			abnormal.setOriginator(abnormal.getIniitiator().getName());//封装发起人
			
			//处理时间设置，状态为3表示已关闭，状态为4表示已撤销，在此两种状态下结束时间已确定可以直接计算，其他状态结束时间未定，用当前时间计算后显示页面，让时间持续增长
			if(abnormal.getState().equalsIgnoreCase("3") || abnormal.getState().equalsIgnoreCase("4")){
				abnormal.setDisposeTime(String.valueOf(abnormal.getHandlingTime()));
			}else{
				Date date = new Date();
				int time = (int) ((date.getTime() - abnormal.getCreateDate().getTime()) / 1000);
				abnormal.setDisposeTime(String.valueOf(time));
			}
			//状态封装，
			abnormal.setStateRemark(ThinkWayUtil.getDictValueByDictKey(
					dictService, "abnormalState", abnormal.getState()));
			
			//对班次进行封装
			if(abnormal.getClasstime()!=null && !"".equals(abnormal.getClasstime())){
				abnormal.setClasstime(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", abnormal.getClasstime()));
			}else{
				abnormal.setClasstime("");
			}
			
			//封装关闭人或撤销人，撤销与关闭两者选一，结束异常
			if(abnormal.getCancelPerson()!=null && !"".equals(abnormal.getCancelPerson())){
				Admin admin = adminService.get(abnormal.getCancelPerson());
				abnormal.setCloseOrcancel(admin.getName());
			}else if(abnormal.getClosePerson()!=null && !"".equals(abnormal.getClosePerson())){
				Admin admin = adminService.get(abnormal.getClosePerson());
				abnormal.setCloseOrcancel(admin.getName());
			}else{
				abnormal.setCloseOrcancel("");
			}
			
			//封装关闭人或撤销时间
			if(abnormal.getCancelTime()!=null){
				abnormal.setCloseOrcancelTime(ThinkWayUtil.formatdateDateTime(abnormal.getCancelTime()));
			}else if(abnormal.getCloseTime()!=null){
				abnormal.setCloseOrcancelTime(ThinkWayUtil.formatdateDateTime(abnormal.getCloseTime()));
			}else{
				abnormal.setCloseOrcancelTime("");
			}
			
			pagerlist.set(i, abnormal);//封装后重新放入pager对象中
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
		ids= id.split(",");
		List<Abnormal> abnormalList = abnormalService.get(ids);//获取所有的异常单,支持批量刷卡
		for (int i = 0; i < abnormalList.size(); i++) {
			List<Admin> adminList = null;
			Abnormal persistent = abnormalList.get(i);//其中一个异常
			Set<Admin> responsorSet = persistent.getResponsorSet();//获取应答人
			if (responsorSet.contains(admin)) {//判断应答人中是否有当前刷卡人
				adminList = new ArrayList<Admin>();
				for (SwiptCard s : persistent.getSwiptCardSet()) {
					if (s.getType().equals("0")) {//判断是否是响应刷卡类型
						adminList.add(s.getAdmin());//将这个异常所有响应刷卡的人放入集合
					}
				}
				if (adminList.contains(admin)) {//如果集合中有这个人，表示这人已刷过卡
					return ajaxJsonErrorMessage("请勿重复刷卡!");
				}
				if (responsorSet.size() == (adminList.size() + 1)) {//当应答人与集合中相差一的时候，表示都响应了
					persistent.setState("2");//更改状态为“2”处理中
					Date date = new Date();
					double time1=(double) ((date.getTime()-persistent.getCreateDate().getTime())/60000);
					persistent.setResponseTime(time1);//设置响应时长(分钟)，当前时间减去异常发起时间
					removeQuartz(persistent.getJobname());	//删除定时发短信任务		
				} else {
					persistent.setState("1");//状态为“1”未完全响应

				}
				SwiptCard swiptCard = new SwiptCard();//刷卡表，一个异常存在多个刷卡
				swiptCard.setAbnormal(persistent);
				swiptCard.setAdmin(admin);
				swiptCard.setType("0");
				swiptCardService.save(swiptCard);
				
				AbnormalLog abnormalLog = new AbnormalLog();//异常日志表
				abnormalLog.setAbnormal(persistent);				
				abnormalLog.setType("4");//日志类型：已刷卡
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
		for (int i = 0; i < ids.length; i++) {//支持批量进行关闭
			Abnormal persistent = abnormalService.load(ids[i]);
			if (persistent.getIniitiator().equals(admin)) {//判断刷卡人是否是异常发起人
				if (persistent.getState() != "3" & persistent.getState() != "4") {//"3"异常关闭 "4"异常撤销
					persistent.setState("3");
					persistent.setCloseTime(new Date());//关闭时间
					persistent.setClosePerson(admin.getId());//关闭人
					removeQuartz(persistent.getJobname());	//结束定时任务
					if (persistent.getReplyDate() == null) {//判断是否存在应答时间
						persistent.setReplyDate(new Date());
						Date date = new Date();//设置处理时间，当前时间减去异常发起时间
						int time = (int) ((date.getTime()-persistent.getCreateDate().getTime())/1000);
//						double time1=(double) ((date.getTime()-persistent.getCreateDate().getTime())/60000);
						persistent.setHandlingTime(time);
						persistent.setDealTime(null);
					}else{
						Date date = new Date();//设置处理时间，当前时间减去响应时间
						int time = (int) ((date.getTime()-persistent.getCreateDate().getTime())/1000);
						double time1=(double) ((date.getTime()-persistent.getReplyDate().getTime())/60000);
						persistent.setHandlingTime(time);
						persistent.setDealTime(time1);
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
			if (persistent.getIniitiator().equals(admin)) {//判断刷卡人是否是异常发起人，只有发起人才有撤销的权限
				if (persistent.getState().equals("0")
						|| persistent.getState().equals("1")) {//"0"未响应  "1"未完全响应，只有处于这两种状态才能撤销
					persistent.setState("4");//设置状态为撤销
					persistent.setCancelPerson(admin.getId());//撤销人
					persistent.setCancelTime(new Date());//撤销时间
					removeQuartz(persistent.getJobname());	//删除定时任务
					persistent.setReplyDate(new Date());
					Date date = new Date();//计算处理时长，当前时间减去异常发起时间
					int time = (int) ((date.getTime()-persistent.getCreateDate().getTime())/1000);
					double time1=(double) ((date.getTime()-persistent.getReplyDate().getTime())/60000);
					persistent.setHandlingTime(time);
					persistent.setDealTime(time1);
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

	//刷卡提交，发起一个异常
	public String creditsave(){
		//log.info("--------异常开始---------");	
		String errormes="";
		try{
			admin = adminService.getByCardnum(cardnumber);
			Admin loginAdmin=adminService.get(loginid);
			for(int i=0;i<callReasonSet.size();i++){//从页面获取人员及短信信息
				Callreason call = callReasonSet.get(i);
				if(call.getId()==null){
					return ajaxJsonErrorMessage("短信不能为空");
				}
				if(call.getAdminid() == null)
					return ajaxJsonErrorMessage("人员不能为空");
				if(call.getProductid() == null || call.getProductid() ==null)
					return ajaxJsonErrorMessage("产品不能为空");
			}
			List<Admin> responsorSet = new ArrayList<Admin>();
			List<UnitdistributeProduct> productSet = new ArrayList<UnitdistributeProduct>();
			List<Callreason> callreasonSet = new ArrayList<Callreason>();
			List<String> strLen = new ArrayList<String>();
			List<HashMap<String, String>> mapList = new ArrayList<HashMap<String,String>>();
			//获取当前刷卡人的车间，单元，和名字
			String workshop=admin.getTeam().getFactoryUnit().getWorkShop().getWorkShopName();
			String unit=admin.getTeam().getFactoryUnit().getFactoryUnitName();
			String adminName =admin.getName();
			for(int i=0;i<callReasonSet.size();i++){//向指定人员发送指定短信，同时将人员和短信信息放入map中，方便定时任务的开启和执行
				HashMap<String,String> map = new HashMap<String,String>();
				//String message="";//这里显示需要的格式?										
				
				Callreason call = callReasonSet.get(i);
				Admin admin = adminService.get(call.getAdminid());//人员
				UnitdistributeProduct product=productservice.get(call.getProductid());
				Callreason call1 = callReasonService.get(call.getId());//短信				
				SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm"); 
				String time=sdf.format(new Date()); 
				String message=workshop+" "+unit+" "+product.getMaterialName()+" 出现"+call1.getCallReason()+"。"+"呼叫人:"+adminName+" 呼叫时间:"+time;
				//log.info("调用发送短信接口开始");		
				
				String str = SendMsgUtil.SendMsg(admin.getPhoneNo(),message);	//调用发送短信接口
				
	            Document doc;   
	            doc = DocumentHelper.parseText(str); //短信发送后返回内容对其进行解析
	            Node stateNode = doc.selectSingleNode("/infos/info/state");//获取状态
		      	if(!stateNode.getText().equalsIgnoreCase("0")){//短信发送失败
		      		errormes += admin.getName()+":短信发生失败!";
		      	}								
		      	//log.info("调用发送短信接口结束");	
		      	responsorSet.add(admin);//将短信人加进去
		      	productSet.add(product);
		      	callreasonSet.add(call1);//将短信内容加进去
		      	strLen.add(admin.getName());		   
		      	map.put("adminid", call.getAdminid());
		      	map.put("reasonid", call1.getId());
		      	map.put("productid", product.getId());
		      	mapList.add(map);
		      	
		      	//同时要给添加人的上级发送，所以把添加人加入map中
		      	//发给几个需要响应的人员，就会给添加人上级发送几次短信，短信内容分别等于各响应人员的内容
		      	HashMap<String,String> sjmap = new HashMap<String,String>();
		      	sjmap.put("adminid", loginAdmin.getId());
		      	sjmap.put("reasonid", call1.getId());
		      	sjmap.put("productid", product.getId());
		      	mapList.add(sjmap);
			}
			
			/*******定时任务**********/
			Date dates = new Date();
			String jobname=ThinkWayUtil.formatdateDateTime(dates);
			job_name=job_name+jobname;//字符串拼接成唯一的任务名
			Abnormal abnormal = new Abnormal();//创建异常
			abnormal.setJobname(job_name);
			abnormal.setCallDate(new Date());
			abnormal.setIniitiator(admin);
			abnormal.setIsDel("N");
			abnormal.setState("0");
			
			abnormal.setResponsorSet(new HashSet<Admin>(responsorSet));			
			abnormal.setProductSet(new HashSet<UnitdistributeProduct>(productSet));			
			abnormal.setCallreasonSet(new HashSet<Callreason>(callreasonSet));
			String comlist = CommonUtil.toString(strLen, ",");// 获取问题的字符串						
			
			//Admin admin2 = adminService.getLoginAdmin();//生产班次和日期
			Admin admin2 = admin;
			admin2 = adminService.get(admin2.getId());
			
			abnormal.setProductdate(admin2.getProductDate());
			abnormal.setClasstime(admin2.getShift());
			abnormalService.save(abnormal);//保存数据

			AbnormalLog abnormalLog = new AbnormalLog();//创建异常日志
			abnormalLog.setAbnormal(abnormal);
			abnormalLog.setType("5");
			abnormalLog.setOperator(admin);
			abnormalLog.setInfo(comlist);
			abnormalLogService.save(abnormalLog);	
	
			Calendar can = Calendar.getInstance();	//定时任务时间1，计算十分钟后的时间，异常未应答向其再发送一条短信
			can.setTime(abnormal.getCreateDate());
			can.add(Calendar.MINUTE, 10);
			Date date=can.getTime();	
			
			Calendar can1 = Calendar.getInstance();//定时任务时间2，计算三十分钟后的时间，异常还未应答向其直接上级发送一条短信
			can1.setTime(abnormal.getCreateDate());
			can1.add(Calendar.MINUTE, 30);
			Date date1=can1.getTime();
			
			Calendar can2 = Calendar.getInstance();//定时任务时间3，计算一小时后的时间，异常还未应答向其直接上级的直接上级发送一条短信，此后如未应答递归向直接上级发送短信
			can2.setTime(abnormal.getCreateDate());
			can2.add(Calendar.MINUTE, 60);
			Date date2=can2.getTime();
	
			JSONArray jsonArray = JSONArray.fromObject(mapList);//将map类型进行转换
			//创建一个map，放入异常，人员，时间，车间单元，任务名，标记符，短信和人员等信息
			HashMap<String,Object> maps = new HashMap<String,Object>();
			maps.put("id",abnormal.getId());
			maps.put("name",admin.getId());
			maps.put("date", ThinkWayUtil.getCron(date1));
			maps.put("time", ThinkWayUtil.getCron(date2));
			maps.put("hour", ThinkWayUtil.formatdateDateTime(date2));
			
			maps.put("workshop", workshop);
			maps.put("unit", unit);
			maps.put("jobname", job_name);
			maps.put("count","1");
			maps.put("list", jsonArray.toString());
			//log.info("--------异常结束1--------");
				quartzMessage(ThinkWayUtil.getCron(date),maps);	//开启定时任务		
			//log.info("--------异常结束2--------");						
		}catch(DocumentException e){
			e.printStackTrace();
			log.info(e);
			return ajaxJsonErrorMessage("短信发送失败");
		}catch(Exception e){
			e.printStackTrace();
			log.info(e);
			return ajaxJsonErrorMessage("系统出现错误,请联系系统管理员");
		}
		
		if(!errormes.equals("")){//有没有发送成功的抛出到页面上
			return ajaxJsonWarnMessage(errormes);
		}
		
		return ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	//创建定时任务
	public void quartzMessage(String time,HashMap<String,Object> maps){	
		String name=(String)maps.get("jobname");
		removeQuartz(name);//如果存在同名的即先删除定时任务
		QuartzManagerUtil.addJob(name, ExtremelyMessage.class,time,maps);//创建定时任务			      	      
	}
	
	//删除定时任务
	public void removeQuartz(String job_name){
		 QuartzManagerUtil.removeJob(job_name);
	}
	
	// 删除
	public String delete() throws Exception {
		ids = id.split(",");
		abnormalService.updateisdel(ids, "Y");
		redirectionUrl = "abnormal!list.action";
		return ajaxJsonSuccessMessage("删除成功！");
	}
	
	public String history() {//异常历史页面
		return "history";
	}
	
	public String historylist() {//异常历史页面加载
		Admin admin2 = adminService.getLoginAdmin();
		admin2 = adminService.get(admin2.getId());
	
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
		if(pager.is_search()==true && Param != null){//普通搜索功能
			// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
			JSONObject obj = JSONObject.fromObject(Param);
			if (obj.get("originator") != null) {
				String originator = obj.getString("originator").toString();
				map.put("originator", originator);
			}
			if (obj.get("start") != null && obj.get("end") != null) {
				String start = obj.get("start").toString();
				String end = obj.get("end").toString();
				map.put("start", start);
				map.put("end", end);
			}
		}

		pager = abnormalService.getAbnormalAllPager(pager, map, admin2.getId());//分页查询
		
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
			
			//班次
			if(abnormal.getClasstime()!=null && !"".equals(abnormal.getClasstime())){
				abnormal.setClasstime(ThinkWayUtil.getDictValueByDictKey(dictService, "kaoqinClasses", abnormal.getClasstime()));
			}else{
				abnormal.setClasstime("");
			}
			
			//关闭人或撤销人
			if(abnormal.getCancelPerson()!=null && !"".equals(abnormal.getCancelPerson())){
				Admin admin = adminService.get(abnormal.getCancelPerson());
				abnormal.setCloseOrcancel(admin.getName());
			}else if(abnormal.getClosePerson()!=null && !"".equals(abnormal.getClosePerson())){
				Admin admin = adminService.get(abnormal.getClosePerson());
				abnormal.setCloseOrcancel(admin.getName());
			}else{
				abnormal.setCloseOrcancel("");
			}
			
			//关闭人或撤销时间
			if(abnormal.getCancelTime()!=null){
				abnormal.setCloseOrcancelTime(ThinkWayUtil.formatdateDateTime(abnormal.getCancelTime()));
			}else if(abnormal.getCloseTime()!=null){
				abnormal.setCloseOrcancelTime(ThinkWayUtil.formatdateDateTime(abnormal.getCloseTime()));
			}else{
				abnormal.setCloseOrcancelTime("");
			}
			pagerlist.set(i, abnormal);
		}

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);// 防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Abnormal.class));// 排除有关联关系的属性字段
		JSONArray jsonArray = JSONArray.fromObject(pager, jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
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
	
	// 获取本单元所有产品
	public List<UnitdistributeProduct> getProductList() {
		admin=adminService.getLoginAdmin();
		admin=adminService.get(admin.getId());
		return productservice.getProductList(admin.getTeam().getFactoryUnit().getFactoryUnitCode());
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

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	
}
