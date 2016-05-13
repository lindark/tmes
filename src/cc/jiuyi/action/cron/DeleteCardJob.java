package cc.jiuyi.action.cron;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cc.jiuyi.service.CreditCardService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.util.SpringUtil;

public class DeleteCardJob extends MyDetailQuartzJobBean {
	public static Logger log = Logger.getLogger(DeleteCardJob.class);
	private CreditCardService creditCardService;
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		//log.info("---------------备份刷卡记录开始----------");
		//log.info("---------------备份刷卡记录结束----------");
		log.info("---------------删除刷卡记录开始----------");
		creditCardService = (CreditCardService)SpringUtil.getBean("creditCardServiceImpl");
		creditCardService.deleteCrard();
		
		
		
		log.info("---------------删除刷卡记录结束----------");
		
	}
	
}
