package cc.jiuyi.action.cron;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Callreason;
import cc.jiuyi.entity.SwiptCard;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CallreasonService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.util.CommonUtil;
import cc.jiuyi.util.QuartzManagerUtil;
import cc.jiuyi.util.SpringUtil;
import cc.jiuyi.util.SendMsgUtil;
import cc.jiuyi.util.ThinkWayUtil;

@Component 
public class ExtremelyMessage extends MyDetailQuartzJobBean {

	public static Logger log = Logger.getLogger(ExtremelyMessage.class);
	private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	private int timeout;
	
	@Resource
	private AbnormalLogService abnormalLogService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private AdminService adminService;
	@Resource
	private CallreasonService callReasonService;
	@Resource
	private UnitdistributeProductService productservice;
	
	public void setTimeout(int timeout){
		  this.timeout = timeout;
	}
		
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		  try{
			  
			// 获取 JobDataMap , 并从中取出参数   
		     JobDataMap data = context.getJobDetail().getJobDataMap();  
		     String abnorId = data.getString("id");//异常id
		     String adminId = data.getString("name");//人员id
		     String time1 = data.getString("date");//时间1
		     String time2 = data.getString("time");//时间2
		     String hour=data.getString("hour");//时间3
		     String jobname = data.getString("jobname");//任务名称
		     String count = data.getString("count");//次数
		     String json = data.getString("list");
		     String workshop= data.getString("workshop");
		     String unit= data.getString("unit");
		     
		     JSONArray jsonarray = JSONArray.fromObject(json);		     
		     
		     abnormalService=(AbnormalService) SpringUtil.getBean("abnormalServiceImpl");
		     Abnormal abnormal = abnormalService.get(abnorId);
		     adminService=(AdminService) SpringUtil.getBean("adminServiceImpl");
		     Admin admin = adminService.get(adminId);
		     callReasonService=(CallreasonService) SpringUtil.getBean("callreasonServiceImpl");
		     abnormalLogService=(AbnormalLogService) SpringUtil.getBean("abnormalLogServiceImpl");
		     productservice = 	 (UnitdistributeProductService) SpringUtil.getBean("unitdistributeProductServiceImpl");    
		     //定时发送短信		
		 
			 String adminName =admin.getName();	
	    	 List<String> strList= new ArrayList<String>();
	    	 List<HashMap<String,Object>> hashmapList = new ArrayList<HashMap<String,Object>>();		
	    	// log.info(jsonarray);
	    	 for(int i=0;i<jsonarray.size();i++){
	    		 JSONObject jsonObj= JSONObject.fromObject(jsonarray.get(i));
	    		// log.info(jsonObj);
	    		 HashMap<String,Object> maps1 = new HashMap<String,Object>();
	    		 Admin admin1 = adminService.get(jsonObj.getString("adminid"));
	    		 Admin admin2 = adminService.getAdminById(admin1.getId());

	    		 if(admin2!=null){//存在直接上级
	    			maps1.put("adminid", admin2.getId());
		 			maps1.put("reasonid",jsonObj.getString("reasonid"));
		 			maps1.put("productid",jsonObj.getString("productid"));
		 			hashmapList.add(maps1);
		 			
		 			Callreason call1 = callReasonService.get(jsonObj.getString("reasonid"));//短信
		 			//log.info(jsonObj.getString("productid"));
		 			UnitdistributeProduct product=productservice.get(jsonObj.getString("productid"));
		 			SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm"); 
					String time=sdf.format(abnormal.getCallDate()); 
					String message=workshop+" "+unit+" "+product.getMaterialName()+" 出现"+call1.getCallReason()+"。"+"呼叫人:"+adminName+" 呼叫时间:"+time;
					
		 			
		 			 String phoneNo = admin2.getPhoneNo();
		 			
		    		 try{
		    			 String str1 = SendMsgUtil.SendMsg(phoneNo,message);
		    			 SAXReader reader = new SAXReader();  //解析返回xml文件
		                 Document doc;   
		                 doc = DocumentHelper.parseText(str1); 
		                 Node stateNode = doc.selectSingleNode("/infos/info/state");
		                 
		                 if(!stateNode.getText().equalsIgnoreCase("0")){//短信发送不成功	
		                	log.info("向"+phoneNo+"手机发送短信失败");		             	
		                 }
		                 
		    			}catch(Exception e){
		    				e.printStackTrace();
		    			} 	
	    		 }
	    		
	    		 strList.add(admin1.getName());

	    	 }
	    	 if(hashmapList.size()==0){//判断是否存在直接上级，不存在就结束任务
	    		 QuartzManagerUtil.removeJob(jobname,JOB_GROUP_NAME,TRIGGER_GROUP_NAME);
	    	 }

	    	 JSONArray jsonArray = JSONArray.fromObject(hashmapList);
	    	 
	    	 String comlist = CommonUtil.toString(strList, ",");// 获取问题的字符串
	    	 AbnormalLog abnormalLog = new AbnormalLog();//日志记录
			 abnormalLog.setAbnormal(abnormal);
			 abnormalLog.setType("5");
			 abnormalLog.setOperator(admin);
			 abnormalLog.setInfo(comlist);
			 abnormalLogService.save(abnormalLog);
		 
			 HashMap<String,Object> maps = new HashMap<String,Object>();
			 Integer second=1800;//30分钟向直属上级发送
			 //Integer second=60;//30分钟向直属上级发送
			 maps.put("id",abnorId);
			 maps.put("name",adminId);
			 maps.put("date", time1);
			 maps.put("time",time2);
			 maps.put("hour",hour);
			 maps.put("jobname",jobname);						
			 maps.put("list", jsonArray.toString());
			 maps.put("workshop", workshop);
			 maps.put("unit", unit);
			 if(Integer.parseInt(count)==1){
				 maps.put("count","2");			 
				 QuartzManagerUtil.modifyJobTime(jobname,time1,maps,JOB_GROUP_NAME,TRIGGER_GROUP_NAME);//修改任务30分钟向直属上司发送短信	
			 }else if(Integer.parseInt(count)==2){
				 maps.put("count", "3");
				 QuartzManagerUtil.modifyJobTime(jobname,time2,maps,JOB_GROUP_NAME,TRIGGER_GROUP_NAME);//修改任务30分钟向直属上司发送短信	
			 }else{
				 maps.put("count", "3");//30分钟递归向上级发送	
				 String time3=ThinkWayUtil.timeAdd(hour, second);
				 maps.put("hour", time3);
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
				 Date date = sdf.parse(time3);
				 String time=ThinkWayUtil.getCron(date);
				 QuartzManagerUtil.modifyJobTime(jobname,time,maps,JOB_GROUP_NAME,TRIGGER_GROUP_NAME);//修改任务30分钟向直属上司发送短信	
			 }
			
			 
	     }catch(Exception e){
		      log.info("ExtremelyMessage任务出错",e);
		  }
	}
}
