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

@Component 
public class DocBmServiceJob extends MyDetailQuartzJobBean {
	
 public static Logger log = Logger.getLogger(DocBmServiceJob.class);
 private int timeout;
 
 //@Resource
 private DictService dictService;
 
 public void setTimeout(int timeout){
  this.timeout = timeout;
 }
 protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
  // TODO Auto-generated method stub
  try{
	  System.out.println("任务开始");
   log.info("DocBmServiceJob任务开始>........");
   //开始重置编码
   dictService = (DictService)SpringUtil.getBean("dictServiceImpl");
   dictService.updateDictValue("DOCBM","chukuBm","0000");
   //dictService.updateDictValue("DOCBM","chejianBm","0000");
   log.info("DocBmServiceJob任务结束");
  }catch(Exception e){
   log.error("DocBmServiceJob任务出错",e);
  }
 }
 




} 
