package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Repository;
import com.sap.mw.jco.JCO.Structure;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.OddHandOver;
import cc.jiuyi.entity.ProcessHandover;
import cc.jiuyi.entity.ProcessHandoverSon;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
import cc.jiuyi.util.ThinkWayUtil;
@Component
public class HandOverProcessRfcImpl extends BaserfcServiceImpl implements HandOverProcessRfc{
	
	@Resource
	private AdminService adminservice;
	@Override
	public String BatchHandOver(List<HandOverProcess> list,String testrun,String loginid)
			throws IOException,CustomerException {
		Admin admin = adminservice.get(loginid);//获取当前登录身份
		admin = adminservice.load(admin.getId());
		super.setProperty("handover");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IS_COMMIT", testrun);//testrun
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		for(HandOverProcess p : list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", p.getMaterialCode());//物料编码
			if(p.getActualAmount()!=null || p.getUnAmount()!=null){
				Double amounts =  ThinkWayUtil.null2o(p.getActualAmount());
				Double repairamounts = ThinkWayUtil.null2o(p.getUnAmount());
				amounts = amounts + repairamounts;
				item.put("ZSFSL", amounts.toString());//数量
			}else{
				Double amount = ThinkWayUtil.null2o(p.getAmount());
				Double repairamount = ThinkWayUtil.null2o(p.getRepairAmount());
				amount = amount + repairamount;
				item.put("ZSFSL", amount.toString());//数量
			}
			item.put("ORDERID1", p.getBeforworkingbill().getWorkingBillCode());//上班随工单
			item.put("ORDERID2", p.getAfterworkingbill().getWorkingBillCode());//下班随工单
			item.put("XUH", p.getId());
			item.put("WERKS", p.getBeforworkingbill().getWerks());//工厂
			item.put("LGORT", admin.getTeam().getFactoryUnit().getWarehouse());
			arrList.add(item);
		}
		tablemodel.setList(arrList);
		tablemodelList.add(tablemodel);
		/*******执行******/
		super.setTable(tablemodelList);
		super.setParameter(parameter);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回表
		String EX_MBLNR=out.getString("EX_MBLNR");
		String E_TYPE=out.getString("E_TYPE");
		String E_MESSAGE=out.getString("E_MESSAGE");
		if(E_TYPE.equals("E")){
			throw new CustomerException("1400001", "交接失败,"+E_MESSAGE);
		}
		return EX_MBLNR;
	}
	@Override
	public ProcessHandover BatchProcessHandOver(
			ProcessHandover processHandover, String testrun, String loginid)
			throws IOException, CustomerException {
		Admin admin = adminservice.get(loginid);//获取当前登录身份
		admin = adminservice.load(admin.getId());
		super.setProperty("handover");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IS_COMMIT", testrun);//testrun
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		if(processHandover.getProcessHandoverTop().getType().equals("工序交接")){
			for(ProcessHandoverSon p : processHandover.getProcessHandoverSonSet()){
				HashMap<String,Object> item = new HashMap<String,Object>();
				if(p.getBomAmount()==null || p.getBomAmount().equals("")){
					p.setBomAmount("0");
				}
				if(p.getRepairNumber()==null || p.getRepairNumber().equals("")){
					p.setRepairNumber("0");
				}
				if(p.getCqamount()==null || p.getCqamount().equals("")){
					p.setCqamount(0.0);
				}
				if(p.getCqrepairamount()==null || p.getCqrepairamount().equals("")){
					p.setCqrepairamount(0.0);
				}
				BigDecimal bomAmount = new BigDecimal(p.getBomAmount());
				BigDecimal repairNumber = new BigDecimal(p.getRepairNumber());
				//BigDecimal cqamount = new BigDecimal(p.getCqamount());
				//BigDecimal cqrepairamount = new BigDecimal(p.getCqrepairamount());
				BigDecimal amount = bomAmount.add(repairNumber);//add(cqamount).add(cqrepairamount);
				if(new BigDecimal(0).compareTo(amount)==0)continue;
				String mount = amount.toString();
				item.put("MATNR", p.getBomCode());//物料编码
				item.put("ZSFSL", mount);//数量
				item.put("ORDERID1", p.getBeforeWorkingCode());//上班随工单
				item.put("ORDERID2", p.getAfterWokingCode());//下班随工单
				item.put("XUH", p.getId());
				item.put("WERKS", processHandover.getProcessHandoverTop().getWerk());//工厂
				item.put("LGORT", admin.getTeam().getFactoryUnit().getWarehouse());
				arrList.add(item);
			}
		}else{
			for(OddHandOver  o:processHandover.getOddHandOverSet()){
				
				HashMap<String,Object> item = new HashMap<String,Object>();
				item.put("MATNR", o.getBomCode());//物料编码
				BigDecimal actualBomMount = new BigDecimal(o.getActualHOMount()==null?0:o.getActualHOMount());
				BigDecimal unBomMount = new BigDecimal(o.getUnHOMount()==null?0:o.getUnHOMount());
				BigDecimal amount = actualBomMount.add(unBomMount);
				String mount = amount.toString();
				if(new BigDecimal(0).compareTo(amount)==0)continue;
				item.put("ZSFSL", mount);//数量
				item.put("ORDERID1", o.getBeforeWokingCode());//上班随工单
				item.put("ORDERID2", o.getAfterWorkingCode());//下班随工单
				item.put("XUH", o.getId());
				item.put("WERKS", processHandover.getProcessHandoverTop().getWerk());//工厂
				item.put("LGORT", admin.getTeam().getFactoryUnit().getWarehouse());
				arrList.add(item);
			}
		}
		tablemodel.setList(arrList);
		if(arrList.size()==0){
			return processHandover;
		}
		tablemodelList.add(tablemodel);
		/*******执行******/
		super.setTable(tablemodelList);
		super.setParameter(parameter);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回表
		String EX_MBLNR=out.getString("EX_MBLNR");
		String E_TYPE=out.getString("E_TYPE");
		String E_MESSAGE=out.getString("E_MESSAGE");
		//String buDat = out.getString("BUDAT");
		if(E_TYPE.equals("E")){
			throw new CustomerException("1400001", "交接失败,"+E_MESSAGE);
		}
		ProcessHandover processHandover1 = new ProcessHandover();
		//processHandover1.setBudat(buDat);
		processHandover1.setE_message(E_MESSAGE);
		processHandover1.setE_type(E_TYPE);
		processHandover1.setMblnr(EX_MBLNR);
		return processHandover1;
	}
	
	
	@Override
	public ProcessHandover RevokedProcessHandOver(
			ProcessHandover processHandover, String testrun, String loginid)
			throws IOException, CustomerException {
		Admin admin = adminservice.get(loginid);//获取当前登录身份
		admin = adminservice.load(admin.getId());
		super.setProperty("handoverRevoked");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		String year = processHandover.getProcessHandoverTop().getProductDate().substring(0,4);
		String oldMblnr = processHandover.getMblnr();
		parameter.put("MATERIALDOCUMENT", oldMblnr);
		parameter.put("MATDOCUMENTYEAR", year);//testrun
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		/*******执行******/
		super.setTable(tablemodelList);
		super.setParameter(parameter);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回表
		String EX_MBLNR=out.getString("EX_MBLNR");
		String E_TYPE=out.getString("E_TYPE");
		String E_MESSAGE=out.getString("E_MESSAGE");
		String EX_MJAHR=out.getString("EX_MJAHR");
	//	System.out.println(EX_MBLNR);
		processHandover.setE_message(E_MESSAGE);
		processHandover.setE_type(E_TYPE);
		processHandover.setMblnr(oldMblnr+"/"+EX_MBLNR);
		System.out.println(processHandover.getMblnr());
//		processHandover.setBudat(EX_MJAHR);
		return processHandover;
	}
}
