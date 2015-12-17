package cc.jiuyi.action.cron;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import cc.jiuyi.service.AdminService;
import cc.jiuyi.util.SpringUtil;
/**
 * 考勤：抓取刷卡员工
 * @author lenovo
 *
 */
@Component 
public class KaoqinMonitor extends MyDetailQuartzJobBean
{
	private AdminService adminService;
	// 日志
	public static Logger log = Logger.getLogger(KaoqinMonitor.class);

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			// 获取 JobDataMap , 并从中取出参数   
	        JobDataMap data = context.getJobDetail().getJobDataMap();  
	        String xquartz = data.getString("kaoqintime");//quartz表达式
	        String teamid=data.getString("teamid");//班组ID
	        xquartz=xquartz.replace(xquartz.substring(xquartz.indexOf("-"), xquartz.indexOf("/")+2), "");
	        xquartz=xquartz.replace("?","");
	        if(teamid.length()>12)
	        {
	        	String str=teamid.substring(0, 3);
	        	if("xxx".equals(str))
	        	{
	        		teamid=teamid.substring(15, teamid.length());//获取当前员工所在班组的ID
	        	}
	        	else
	        	{
	        		teamid=teamid.substring(12, teamid.length());//获取当前员工所在班组的ID
	        	}
	        }
	        SimpleDateFormat sdf=new SimpleDateFormat("ss mm HH dd MM yyyy");
	        Date startdate=sdf.parse(xquartz);//从quartz中解析出开始时间
			Date enddate = new Date();// 现在时间
			//开始重置编码
			adminService = (AdminService)SpringUtil.getBean("adminServiceImpl");
			//根据开始时间和当前时间查询出刷卡表该时间段刷卡的人,并更新admin表对应的员工
			this.adminService.updateByCreditCard(startdate,enddate,teamid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("KaoqinMonitor任务出错", e);
		}
	}
}
