package cc.jiuyi.action.cron;



import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.apache.log4j.*;

import cc.jiuyi.entity.Equipments;
import cc.jiuyi.sap.rfc.EquipmentRfc;
import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.service.EquipmentService;
import cc.jiuyi.util.SpringUtil;


@Component 
public class EquipmentJob extends MyDetailQuartzJobBean {
	
 public static Logger log = Logger.getLogger(EquipmentJob.class);
 private EquipmentRfc equipmentrfc;
 private EquipmentService equipmentservice;

 protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
  try{
   log.info("同步设备主数据开始>........");
   equipmentrfc = (EquipmentRfc) SpringUtil.getBean("equipmentRfcImpl");
   equipmentservice = (EquipmentService) SpringUtil.getBean("equipmentServiceImpl");
   List<Equipments> equipmentsList = equipmentrfc.getEquipment();
   equipmentservice.saveorupdate(equipmentsList);
   
   log.info("同步设备主数据开始>........");
  }catch(Exception e){
   log.error("同步设备主数据出错",e);
  }
 }
 




} 
