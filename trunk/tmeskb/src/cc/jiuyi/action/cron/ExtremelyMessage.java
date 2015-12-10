package cc.jiuyi.action.cron;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component 
public class ExtremelyMessage extends MyDetailQuartzJobBean {

	public static Logger log = Logger.getLogger(ExtremelyMessage.class);
	
	private int timeout;
	
	public void setTimeout(int timeout){
		  this.timeout = timeout;
	}
		
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		  try{
			  System.out.println("ExtremelyMessage任务执行");				  			  
		  }catch(Exception e){
		      log.error("ExtremelyMessage任务出错",e);
		  }
	}
}
