package cc.jiuyi.action.cron;

import java.beans.Beans;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.apache.log4j.*;

import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.SpringUtil;

/**
 * 定时任务-每隔5分钟调用
 * @author weita
 *
 */
@Component 
public class WorkingBillJob extends MyDetailQuartzJobBean {
	
 public static Logger log = Logger.getLogger(WorkingBillJob.class);
 private int timeout;
 
 //@Resource
 private DictService dictService;
 
 public void setTimeout(int timeout){
  this.timeout = timeout;
 }
 protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
  try{
	  System.out.println("任务开始");
   log.info("WorkingBillJob任务开始>........");
  System.out.println("任务执行");
   
   
   log.info("WorkingBillJob任务结束");
  }catch(Exception e){
   log.error("WorkingBillJob任务出错",e);
  }
 }
 




} 
