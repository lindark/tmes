package cc.jiuyi.sap.rfc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.SAPUtil;

public class Repairorder {
	
	/**
	 * 同步随工单信息
	 */
	public void syncRepairorder(WorkingBillService workingbillservice) throws Exception{
		List list = new ArrayList<WorkingBill>();
		String funcName="Z_TEMS_READ_REPAIRORDER";//函数名称
		Map<String, String> strMap=new HashMap<String, String>();
		/**输入参数***/
		strMap.put("STARTDATE", "20150928");//开始时间
		strMap.put("ENDDATE", "20150930");//结束时间
		/**输入参数 end***/
		
		/**输出**/
		SAPModel model=SAPUtil.OperSAP(strMap, null, null, funcName);//访问SAP获取返回数据
		JCO.ParameterList out=model.getOuts();//返回结构与参数
		JCO.ParameterList outs=model.getOuttab();//返回表
		Table table01 = outs.getTable("ET_ITEM");
		for(int i=0;i<table01.getNumRows();i++){
			WorkingBill workingbill = new WorkingBill();
			table01.setRow(i);
			workingbill.setWorkingBillCode(table01.getString("ZSGD"));//随工单号
			workingbill.setProductDate(table01.getDate("GLTRS"));//生产日期
			workingbill.setPlanCount(table01.getString("NEWS"));//计划数
			workingbill.setMaktx(table01.getString("MAKTX"));//物料描述
			workingbill.setMatnr(table01.getString("PLNBEZ"));//物料编号
			list.add(workingbill);
		}
		workingbillservice.mergeWorkingBill(list);
	}
	
}
