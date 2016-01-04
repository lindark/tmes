package cc.jiuyi.action.cron;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Abnormal;
import cc.jiuyi.entity.AbnormalLog;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.service.AbnormalLogService;
import cc.jiuyi.service.AbnormalService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.util.QuartzManagerUtil;
import cc.jiuyi.util.SpringUtil;
import cc.jiuyi.util.SendMsgUtil;
import cc.jiuyi.util.ThinkWayUtil;

@Component 
public class ExtremelyMessage extends MyDetailQuartzJobBean {

	public static Logger log = Logger.getLogger(ExtremelyMessage.class);
	
	private int timeout;
	
	@Resource
	private AbnormalLogService abnormalLogService;
	@Resource
	private AbnormalService abnormalService;
	@Resource
	private AdminService adminService;
	
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
		     String jobname = data.getString("jobname");//任务名称
		     String count = data.getString("count");//次数
		     String phone = data.getString("phone");//部长电话
		     String name = data.getString("minister");//部长
		     String phone1 = data.getString("phone1");//主任电话
		     String name1 = data.getString("director");//主任
		     String phone2 = data.getString("phone2");//副总电话
		     String name2 = data.getString("manager");//副总
		     
		     abnormalService=(AbnormalService) SpringUtil.getBean("abnormalServiceImpl");
		     Abnormal abnormal = abnormalService.get(abnorId);
		     adminService=(AdminService) SpringUtil.getBean("adminServiceImpl");
		     Admin admin = adminService.get(adminId);
		     abnormalLogService=(AbnormalLogService) SpringUtil.getBean("abnormalLogServiceImpl");
		     
		     String str;
		     if(Integer.parseInt(count)==1){
				 str= SendMsgUtil.SendMsg(phone,"xx异常还未响应");
		     }else if(Integer.parseInt(count)==2){
				 str= SendMsgUtil.SendMsg(phone1,"xx异常仍未响应");
		     }else{
				 str= SendMsgUtil.SendMsg(phone2,"xx异常未响应");
		     }
			 
			 
			 SAXReader reader = new SAXReader();  //解析返回xml文件
             Document doc;   
             doc = DocumentHelper.parseText(str); 
             Node stateNode = doc.selectSingleNode("/infos/info/state");
			 
			 if(stateNode.getText().equalsIgnoreCase("0")){//短信发送成功			    
					
				    AbnormalLog abnormalLog = new AbnormalLog();
					abnormalLog.setAbnormal(abnormal);
					abnormalLog.setType("5");
					abnormalLog.setOperator(admin);
					
					HashMap<String,Object> maps = new HashMap<String,Object>();
				    maps.put("id",abnorId);
					maps.put("name",adminId);
					maps.put("date", time1);
					maps.put("time",time2);
					maps.put("jobname",jobname);					
					maps.put("phone",phone);					
					maps.put("minister", name);
					maps.put("phone1",phone1);
					maps.put("director", name1);
					maps.put("phone2",phone2);
					maps.put("manager", name2);
					
																			
					if(Integer.parseInt(count)==1){
												
						abnormalLog.setInfo(name);
						abnormalLogService.save(abnormalLog);
						maps.put("count","2");
						QuartzManagerUtil.modifyJobTime(jobname,time1,maps);
					
					}else if(Integer.parseInt(count)==2){
						
						abnormalLog.setInfo(name1);
						abnormalLogService.save(abnormalLog);
												
						maps.put("count","3");												
						QuartzManagerUtil.modifyJobTime(jobname,time2,maps);
					}else{
						
						abnormalLog.setInfo(name2);
						abnormalLogService.save(abnormalLog);
						
						maps.put("count","3");						
						QuartzManagerUtil.modifyJobTime(jobname,time2,maps);
					}
				    
			  }
		  }catch(Exception e){
		      log.error("ExtremelyMessage任务出错",e);
		  }
	}
}
