package cc.jiuyi.action.cron;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import cc.jiuyi.sap.rfc.ProductStorageRfc;
import cc.jiuyi.service.CreditCardService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.util.SpringUtil;

public class ProductStorageJob extends MyDetailQuartzJobBean {
	public static Logger log = Logger.getLogger(ProductStorageJob.class);
	private ProductStorageRfc productstoragerfc;
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			log.info("定时生产入库同步开始");
			productstoragerfc = (ProductStorageRfc)SpringUtil.getBean("productStorageRfcImpl");
			//HashMap<String, Object> parameter = new HashMap<String, Object>();
			productstoragerfc.sysnProductStorage(null);
			log.info("定时生产入库同步结束");
		} catch (Exception e) {
			log.error("定时生产入库同步错误"+e.getMessage());
			e.printStackTrace();
		}
	}
	
}
