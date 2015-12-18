package cc.jiuyi.action.cron;

import java.beans.Beans;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.apache.log4j.*;

import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.SpringUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 定时任务-每隔5分钟调用
 * 
 * @author weita
 * 
 */
@Component
public class WorkingBillJobAll extends MyDetailQuartzJobBean {

	public static Logger log = Logger.getLogger(WorkingBillJobAll.class);

	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			System.out.println("任务开始");
			log.info("WorkingBillJobAll任务开始>........");
			System.out.println("任务执行");
			// String jobName = context.getJobDetail().getKey().getName();
			// JobDataMap data = context.getJobDetail().getJobDataMap();
			// String ceshi = data.getString("ceshi");
			// System.out.println(ceshi);
			//String sysdate = ThinkWayUtil.formatDateByPattern(new Date(),"yyyy-MM-dd HH:mm:ss");
			//String startdate = ThinkWayUtil.timeAdd(sysdate, -86400);// yyyy-MM-dd HH:mm:ss
			//startdate = ThinkWayUtil.formatdateDate(startdate);// yyyy-MM-dd 获取开始时间，从昨天开始
			
			log.info("WorkingBillJobAll任务结束");
		} catch (Exception e) {
			log.error("WorkingBillJobAll任务出错", e);
		}
	}

}
