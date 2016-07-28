package cc.jiuyi.action.cron;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Team;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.TeamService;
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
	private TeamService teamService;
	// 日志
	public static Logger log = Logger.getLogger(KaoqinMonitor.class);

	protected void executeInternal(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			// 获取 JobDataMap , 并从中取出参数   
	        JobDataMap data = context.getJobDetail().getJobDataMap();  
	        //String xquartz = data.getString("kaoqintime");//quartz表达式
	        String kaoqintime = data.getString("kaoqintime");
	        String teamid=data.getString("teamid");//班组ID
	        //xquartz=xquartz.replace(xquartz.substring(xquartz.indexOf("-"), xquartz.indexOf("/")+2), "");
	        //10 56-59/1 19 18 12 ? 2015
	        //0/5 56-59/1 19 18 12 ? 2015
	        //xquartz=xquartz.replace("?","");
	        
	        SimpleDateFormat sdf=new SimpleDateFormat("ss mm HH dd MM yyyy");
	        Date startdate=sdf.parse(kaoqintime);//从quartz中解析出开始时间---改为直接传入
			Date enddate = new Date();// 现在时间
			
			 //两个时间的相差分钟
			Calendar can=Calendar.getInstance();//开启考勤的时间
			Calendar can2=Calendar.getInstance();//现在的时间
			can.setTime(startdate);
			can.set(Calendar.SECOND,0);
			can2.setTime(enddate);
			int miao=can2.get(Calendar.SECOND);
			can2.set(Calendar.SECOND,0);
			enddate=can2.getTime();
			int n=0;
			if (data.getString("d_value")!=null)
			{
				can.add(Calendar.MINUTE, -1);
				startdate=can.getTime();
				n = Integer.parseInt(data.getString("d_value"))+1;// 跨时后的差值
				teamid = teamid.substring(15, teamid.length());// 获取当前员工所在班组的ID
			}
			else
			{
				n = 40;
				teamid = teamid.substring(12, teamid.length());// 获取当前员工所在班组的ID
			}

			int differ=Integer.parseInt(String.valueOf((can2.getTimeInMillis()-can.getTimeInMillis())/(60*1000)));
			//System.out.println(differ+"===="+miao+"====="+n+"====startdate="+startdate+"=========enddate="+enddate);
			if(differ>=n){
				teamService=(TeamService) SpringUtil.getBean("teamServiceImpl");
				Team t=teamService.get(teamid);
				t.setIscancreditcard("Y");
				t.setModifyDate(new Date());
				teamService.update(t);
			}
			adminService = (AdminService)SpringUtil.getBean("adminServiceImpl");
			//根据开始时间和当前时间查询出刷卡表该时间段刷卡的人,并更新admin表对应的员工
			this.adminService.updateByCreditCard(startdate,enddate,teamid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.info("KaoqinMonitor任务出错", e);
		}
	}
}
