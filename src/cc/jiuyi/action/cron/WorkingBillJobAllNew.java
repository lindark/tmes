package cc.jiuyi.action.cron;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.service.WorkShopService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SpringUtil;
/**
 * 定时任务-每隔10分钟执行一次
 * 
 * @author weita
 * 
 */
@Component
public class WorkingBillJobAllNew extends MyDetailQuartzJobBean {
	public static Logger log = Logger.getLogger(WorkingBillJobAllNew.class);
	private WorkingBillRfc workingbillrfc;
//	private WorkShopService workShopService;
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		log.info("同步生产订单开始...");
		try {
			Date newdate = new Date();
			newdate = DateUtils.addMinutes(newdate, -30);
			String startdate = String.format("%tF%n", newdate);
			String starttime = String.format("%tT%n", newdate);
			//newdate = DateUtils.addDays(newdate, 1);
			newdate = DateUtils.addMinutes(newdate, +35);
			String enddate = String.format("%tF%n", newdate);
			String endtime = String.format("%tT%n", newdate);
			workingbillrfc = (WorkingBillRfc) SpringUtil.getBean("workingBillRfcImpl");
//			workShopService = (WorkShopService) SpringUtil.getBean("workShopServiceImpl");
			//自动同步
			//时间 + type  A 自动同步，B手工同步 startdate, enddate,starttime,endtime ，type  A
//			List<WorkShop> shopList = workShopService.getWorkShopBySyc();
//			if(shopList!=null && shopList.size()>0){
//				boolean flag = true;
//				for(WorkShop ws : shopList){
//					if(flag){
//						flag = false;
//						workingbillrfc.syncRepairorderAllNew(startdate, enddate,starttime,"","A");
//						flag = true;
//					}
//				}
//			}
			workingbillrfc.syncRepairorderAllNew(startdate, enddate,starttime,endtime,"","A");
		} catch (IOException e){
			log.info("同步生产订单出错"+e);
			e.printStackTrace();
		} catch (CustomerException e){
			log.info("同步生产订单出错"+e);
			e.printStackTrace();
		} catch (Exception e){
			log.info("错误"+e);
			e.printStackTrace();
		}
		
		log.info("同步生产订单结束...");
		
	}
}
