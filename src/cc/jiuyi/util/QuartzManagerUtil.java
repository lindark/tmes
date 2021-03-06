package cc.jiuyi.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Quartz 动态设置
 * 
 * @author weita
 * 
 */
public class QuartzManagerUtil {
	private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();//默认找quartz.properties，可以指定其他的配置方式
	//private static String JOB_GROUP_NAME = "EXTJWEB_JOBGROUP_NAME";
	//private static String TRIGGER_GROUP_NAME = "EXTJWEB_TRIGGERGROUP_NAME";

	
	public static void ceshi(){
		HashMap<String,Object> maps= new HashMap<String,Object>();
		maps.put("kaoqintime", "2014-11-11");
		maps.put("teamid", "5000");
		
	}
	
	/**
	 * 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param jobName
	 *            任务名
	 * @param cls
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 */
	public static void addJob(String jobName, Class cls, String time,HashMap<String,Object> maps,String job_group_name,String trigger_group_name) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = new JobDetail(jobName, job_group_name, cls);// 任务名，任务组，任务执行类
			if(maps!=null)
			{
				Set set = maps.keySet();
				Iterator iter = set.iterator();
				while (iter.hasNext()) 
				{
					String key = (String) iter.next();
					jobDetail.getJobDataMap().put(key, maps.get(key));
				}
				
			}
			// 触发器
			CronTrigger trigger = new CronTrigger(jobName, trigger_group_name);// 触发器名,触发器组
			trigger.setCronExpression(time);// 触发器时间设定
			trigger.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//设置默认时区
			sched.scheduleJob(jobDetail, trigger);
			// 启动
			if (!sched.isShutdown()) {
				sched.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加一个定时任务
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 */
	public static void addJob(String jobName, String jobGroupName,
			String triggerName, String triggerGroupName, Class jobClass,
			String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			JobDetail jobDetail = new JobDetail(jobName, jobGroupName, jobClass);// 任务名，任务组，任务执行类
			// 触发器
			CronTrigger trigger = new CronTrigger(triggerName, triggerGroupName);// 触发器名,触发器组
			trigger.setCronExpression(time);// 触发器时间设定
			sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 * @param time
	 */
	public static void modifyJobTime(String jobName, String time,HashMap<String,Object> maps,String job_group_name,String trigger_group_name) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName,
					trigger_group_name);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				JobDetail jobDetail = sched.getJobDetail(jobName,
						job_group_name);
				Class objJobClass = jobDetail.getJobClass();
				removeJob(jobName,job_group_name,trigger_group_name);
				addJob(jobName, objJobClass, time,maps,job_group_name,trigger_group_name);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改一个任务的触发时间
	 * 
	 * @param triggerName
	 * @param triggerGroupName
	 * @param time
	 */
	public static void modifyJobTime(String triggerName,
			String triggerGroupName, String time) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerName,
					triggerGroupName);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				CronTrigger ct = (CronTrigger) trigger;
				// 修改时间
				ct.setCronExpression(time);
				// 重启触发器
				sched.resumeTrigger(triggerName, triggerGroupName);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param jobName
	 */
	public static void removeJob(String jobName,String job_group_name,String trigger_group_name) {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.pauseTrigger(jobName, trigger_group_name);// 停止触发器
			sched.unscheduleJob(jobName, trigger_group_name);// 移除触发器
			sched.deleteJob(jobName, job_group_name);// 删除任务
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 启动所有定时任务
	 */
	public static void startJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭所有定时任务
	 */
	public static void shutdownJobs() {
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 判断任务是否存在
	 */
	public static boolean checkJobname(String jobName,String trigger_group_name){
		try {
			Scheduler sched = gSchedulerFactory.getScheduler();
			CronTrigger trigger = (CronTrigger) sched.getTrigger(jobName,trigger_group_name);
			if (trigger == null) {
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
