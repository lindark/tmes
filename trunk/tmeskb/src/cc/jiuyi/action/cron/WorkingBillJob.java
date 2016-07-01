package cc.jiuyi.action.cron;

import java.beans.Beans;
import java.io.IOException;
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
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.*;

import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SpringUtil;
import cc.jiuyi.util.ThinkWayUtil;

/**
 * 定时任务-每隔5分钟调用
 * 
 * @author weita
 * 
 */
@Component
public class WorkingBillJob extends MyDetailQuartzJobBean {

	public static Logger log = Logger.getLogger(WorkingBillJob.class);
	//@Resource
	private WorkingBillRfc workingbillrfc;
	private UnitdistributeProductService unitdistributeproductservice;
	
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		try {
			// 获取 JobDataMap , 并从中取出参数   
		     JobDataMap data = context.getJobDetail().getJobDataMap();  
		     String name = data.getString("jobname");
		     //System.out.println("同步开始name-----------------"+name);
		     start(name);
		     //System.out.println("同步结束name-----------------"+name);
		} catch (Exception e) {
			// TODO: handle exception
		}
//		System.out.println("任务开始:"+ThinkWayUtil.SystemDate());
//		for(int i=0 ;i<5000;i++){
//			System.out.print(i);
//		}
//		System.out.println("任务结束:"+ThinkWayUtil.SystemDate());
//		try {
//			System.out.println("任务开始");
//			log.info("WorkingBillJob任务开始>........");
//			System.out.println("任务执行");
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			Date d = new Date();
//			Calendar rightnow = Calendar.getInstance();
//			rightnow.setTime(d);
//			rightnow.add(Calendar.MINUTE,-10);
//			String startdate = sdf.format(d);
//			String enddate = sdf.format(d);
//			sdf = new SimpleDateFormat("hh:mm:ss");
//			String starttime = sdf.format(rightnow.getTime());
//			rightnow.add(Calendar.MINUTE,10);
//			String endtime = sdf.format(rightnow.getTime());
//			/******取10分钟之内的数据*********/
//			workingbillrfc = (WorkingBillRfc) SpringUtil.getBean("workingBillRfcImpl");
//			workingbillrfc.syncRepairorderAll(startdate, enddate,starttime,endtime,"");
//			//workingbillrfc.syncRepairorderAll(startdate, enddate);
//			
//			log.info("WorkingBillJob任务结束");
//		} catch (Exception e) {
//			log.error("WorkingBillJob任务出错", e);
//		}
	}
	
	//workcode 单元
		public  void start(String workcode){
			//ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletContext sc); 
			//System.out.println("同步生产订单开始..."+workcode);
			log.info("同步生产订单开始..."+workcode);
			try {
				Date newdate = new Date();
				String startdate = String.format("%tF%n", newdate);
				String starttime = String.format("%tT%n", newdate);
				newdate = DateUtils.addDays(newdate, 1);
				String enddate = String.format("%tF%n", newdate);
				String endtime = String.format("%tT%n", newdate);
				workingbillrfc = (WorkingBillRfc) SpringUtil.getBean("workingBillRfcImpl");
				unitdistributeproductservice = (UnitdistributeProductService)SpringUtil.getBean("unitdistributeProductServiceImpl");
				//factoryunitservice = (FactoryUnitService)SpringUtil.getBean("factoryUnitServiceImpl");
				//FactoryUnit factoryunit = factoryunitservice.get("workCenter",workcode);
				//String workshopcode  = factoryunit.getWorkShop().getWorkShopCode();//车间编码
				String workshopcode = "201";
				String[] propertyNames = {"factoryunit.workCenter","state","isDel"};
				Object[] propertyValues = {workcode,"1","N"};
				List<UnitdistributeProduct> unitdistributeList = unitdistributeproductservice.getList(propertyNames,propertyValues);
				workingbillrfc.syncRepairorderAll(startdate, enddate,starttime,endtime,"",workshopcode,unitdistributeList,workcode);
			} catch (IOException e){
				log.error("同步生产订单出错"+e);
				e.printStackTrace();
			} catch (CustomerException e){
				log.error("同步生产订单出错"+e);
				e.printStackTrace();
			} catch (Exception e){
				log.error("错误"+e);
				e.printStackTrace();
			}
			
			log.info("同步生产订单结束..."+workcode);
			//System.out.println("同步生产订单结束..."+workcode);
		}

}
