package cc.jiuyi.action.cron;

import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
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
		     String time1 = data.getString("date");//时间
		     String jobname = data.getString("jobname");//任务名称
		     HashMap<String,Object> maps = new HashMap<String,Object>();
		     
		     abnormalService=(AbnormalService) SpringUtil.getBean("abnormalServiceImpl");
		     Abnormal abnormal = abnormalService.get(abnorId);
		     adminService=(AdminService) SpringUtil.getBean("adminServiceImpl");
		     Admin admin = adminService.get(adminId);
		     abnormalLogService=(AbnormalLogService) SpringUtil.getBean("abnormalLogServiceImpl");
		     
			 System.out.println("ExtremelyMessage任务执行");
			 			 
			 if(1==1){
				    AbnormalLog abnormalLog = new AbnormalLog();
					abnormalLog.setAbnormal(abnormal);
					abnormalLog.setType("5");
					abnormalLog.setOperator(admin);
					abnormalLogService.save(abnormalLog);
					
					QuartzManagerUtil.modifyJobTime(jobname,time1,maps);
			  }
		  }catch(Exception e){
		      log.error("ExtremelyMessage任务出错",e);
		  }
	}
}
