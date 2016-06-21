package cc.jiuyi.action.cron;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.*;

import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.sap.rfc.impl.WorkingBillRfcImpl;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SpringUtil;

/**
 * 定时任务-每隔10分钟执行一次
 * 
 * @author weita
 * 
 */
@Component
public class WorkingBillJobAll{

	public static Logger log = Logger.getLogger(WorkingBillJobAll.class);
	
	private WorkingBillRfc workingbillrfc;
	private UnitdistributeProductService unitdistributeproductservice;
	private FactoryUnitService factoryunitservice;

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
			factoryunitservice = (FactoryUnitService)SpringUtil.getBean("factoryUnitServiceImpl");
			FactoryUnit factoryunit = factoryunitservice.get("workCenter",workcode);
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
	public  void start1(String[] workcode){
		//ApplicationContext ac1 = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletContext sc); 
		//System.out.println("同步生产订单开始..."+workcode);
		log.info("同步生产订单开始..."+workcode[0]);
		try {
			Date newdate = new Date();
			String startdate = String.format("%tF%n", newdate);
			String starttime = String.format("%tT%n", newdate);
			newdate = DateUtils.addDays(newdate, 1);
			String enddate = String.format("%tF%n", newdate);
			String endtime = String.format("%tT%n", newdate);
			workingbillrfc = (WorkingBillRfc) SpringUtil.getBean("workingBillRfcImpl");
			unitdistributeproductservice = (UnitdistributeProductService)SpringUtil.getBean("unitdistributeProductServiceImpl");
			factoryunitservice = (FactoryUnitService)SpringUtil.getBean("factoryUnitServiceImpl");
			FactoryUnit factoryunit = factoryunitservice.get("workCenter",workcode[0]);
			//String workshopcode  = factoryunit.getWorkShop().getWorkShopCode();//车间编码
			String workshopcode = "201";
			String[] propertyNames = {"factoryunit.workCenter","state","isDel"};
			Object[] propertyValues = {workcode[0],"1","N"};
			List<UnitdistributeProduct> unitdistributeList = unitdistributeproductservice.getList(propertyNames,propertyValues);
			workingbillrfc.syncRepairorderAll(startdate, enddate,starttime,endtime,"",workshopcode,unitdistributeList,workcode[0]);
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
		
		log.info("同步生产订单结束..."+workcode[0]);
		//System.out.println("同步生产订单结束..."+workcode);
	}
}
