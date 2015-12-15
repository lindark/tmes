package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.SAPUtil;
import cc.jiuyi.util.TableModel;


@Component
public class WorkingBillRfcImpl extends BaserfcServiceImpl implements WorkingBillRfc {

	@Resource
	private WorkingBillService workingbillservice;
	@Override
	public void syncRepairorder() throws IOException, CustomerException {
		super.setProperty("workingbill");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("STARTDATE", "20150928");
		parameter.put("ENDDATE", "20150930");
		
		super.setParameter(parameter);
		SAPModel model = execBapi();//执行 并获取返回值
		
		ParameterList outs = model.getOuttab();//返回表
		Table table01 = outs.getTable("ET_ITEM");//列表
		List<WorkingBill> list = new ArrayList<WorkingBill>();
		for(int i=0;i<table01.getNumRows();i++){
			WorkingBill workingbill = new WorkingBill();
			table01.setRow(i);
			workingbill.setWorkingBillCode(table01.getString("ZSGD"));//随工单号
			workingbill.setProductDate(table01.getString("GLTRS"));//生产日期
			workingbill.setPlanCount(table01.getInt("NEWS"));//计划数
			workingbill.setMaktx(table01.getString("MAKTX"));//物料描述
			workingbill.setMatnr(table01.getString("PLNBEZ"));//物料编号
			workingbill.setWerks(table01.getString("WERKS"));//工厂
			list.add(workingbill);
		}
		workingbillservice.mergeWorkingBill(list);
		
	}

}
