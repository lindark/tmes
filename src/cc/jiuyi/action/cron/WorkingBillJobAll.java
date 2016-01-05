package cc.jiuyi.action.cron;

import java.beans.Beans;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.SpringUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 定时任务-凌晨2点执行
 * 
 * @author weita
 * 
 */
@Component
public class WorkingBillJobAll extends MyDetailQuartzJobBean {

	public static Logger log = Logger.getLogger(WorkingBillJobAll.class);
	//@Resource
	private WorkingBillRfc workingbillrfc;
	
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			log.info("WorkingBillJobAll任务开始>........");
			System.out.println("任务执行");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = new Date();
			Calendar rightnow = Calendar.getInstance();
			rightnow.setTime(d);
			rightnow.add(Calendar.MINUTE,-10);
			String startdate = sdf.format(d);
			String enddate = sdf.format(d);
			sdf = new SimpleDateFormat("hh:mm:ss");
			String starttime = sdf.format(rightnow.getTime());
			rightnow.add(Calendar.MINUTE,10);
			String endtime = sdf.format(rightnow.getTime());
			/*********用于取出3天内的数据**************/
			workingbillrfc = (WorkingBillRfc) SpringUtil.getBean("workingBillRfcImpl");
			workingbillrfc.syncRepairorderAll(startdate, enddate,starttime,endtime);
			
			log.info("WorkingBillJobAll任务结束");
		} catch (Exception e) {
			log.error("WorkingBillJobAll任务出错", e);
		}
	}

}
