package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Bom;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.ProcessRoute;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.WorkingBillRfc;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Repository;
import com.sap.mw.jco.JCO.Structure;
import com.sap.mw.jco.JCO.Table;


@Component
public class WorkingBillRfcImpl extends BaserfcServiceImpl implements WorkingBillRfc {
	
	@Resource
	private WorkingBillService workingbillservice;
	@Override
	public void syncRepairorder(String startdate,String enddate,String starttime,String endtime) throws IOException, CustomerException {
//		super.setProperty("workingbill");//根据配置文件读取到函数名称
//		/******输入参数******/
//		HashMap<String,Object> parameter = new HashMap<String,Object>();
//		parameter.put("STARTDATE", startdate);
//		parameter.put("ENDDATE", enddate);
//		parameter.put("STARTTIME", starttime);
//		parameter.put("ENDTIME", endtime);
//		
//		super.setParameter(parameter);
//		SAPModel model = execBapi();//执行 并获取返回值
//		
//		ParameterList outs = model.getOuttab();//返回表
//		Table table01 = outs.getTable("ET_ITEM");//列表
//		List<WorkingBill> list = new ArrayList<WorkingBill>();
//		for(int i=0;i<table01.getNumRows();i++){
//			WorkingBill workingbill = new WorkingBill();
//			table01.setRow(i);
//			workingbill.setWorkingBillCode(table01.getString("ZSGD"));//随工单号
//			workingbill.setProductDate(table01.getString("GLTRS"));//生产日期
//			workingbill.setPlanCount(table01.getInt("NEWS"));//计划数
//			workingbill.setMaktx(table01.getString("MAKTX"));//物料描述
//			workingbill.setMatnr(table01.getString("PLNBEZ"));//物料编号
//			workingbill.setWerks(table01.getString("WERKS"));//工厂
//			list.add(workingbill);
//		}
//		//workingbillservice.mergeWorkingBill(list);
		
	}
	
	@Override
	public  void syncRepairorderAll(String startdate,String enddate,String starttime,String endtime,String aufnr,String workshopcode,List<UnitdistributeProduct> unitdistributeList,String workcode) throws IOException, CustomerException {
		super.setProperty("workingbillall");//根据配置文件读取到函数名称
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("STARTDATE", startdate);
		parameter.put("ENDDATE", enddate);
		parameter.put("STARTTIME", starttime);
		parameter.put("ENDTIME", endtime);
		parameter.put("IM_ARBPL", workcode);
		if(aufnr == null) aufnr="";
		parameter.put("IM_AUFNR", aufnr);
		if(workshopcode == null) workshopcode="";
		parameter.put("G_FEVOR", workshopcode);
		//System.out.println("-------------"+parameter+"-------------");
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		for(UnitdistributeProduct m:unitdistributeList){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR",m.getMaterialCode());//物料编码
			arrList.add(item);
		}
		//System.out.println("-------------"+arrList+"-------------");
		List<HashMap<String,Object>> arr = new ArrayList<HashMap<String,Object>>(new HashSet<HashMap<String,Object>>(arrList));
		tablemodel.setList(arr);
		tablemodelList.add(tablemodel);
		//super.setParameter(parameter);
		//super.setStructure(null);
		//super.setTable(tablemodelList);
		SAPModel model = execBapi(tablemodelList,null,parameter);//执行 并获取返回值
		
		ParameterList outs = model.getOuttab();//返回表
		Table table01 = outs.getTable("ET_ITEM");//随工单
		Table table02 = outs.getTable("ET_AFKO");//订单
		Table table03 = outs.getTable("ET_AFVC");//订单工艺路线
		Table table04 = outs.getTable("ET_RESB");//生产订单BOM
		List<WorkingBill> list = new ArrayList<WorkingBill>();
		for(int i=0;i<table01.getNumRows();i++){
			WorkingBill workingbill = new WorkingBill();
			table01.setRow(i);
			workingbill.setWorkingBillCode(table01.getString("ZSGD"));//随工单号
			workingbill.setProductDate(table01.getString("ZDATE"));//生产日期
			workingbill.setPlanCount(table01.getInt("ZBJH"));//计划数
			workingbill.setMaktx(table01.getString("MAKTX"));//物料描述
			workingbill.setMatnr(table01.getString("MATNR"));//物料编号
			workingbill.setWerks(table01.getString("WERKS"));//工厂
			workingbill.setAufnr(table01.getString("AUFNR"));//订单号
			workingbill.setWorkcenter(table01.getString("ARBPL"));//工作中心
			list.add(workingbill);
		}
		List<Orders> orderlist = new ArrayList<Orders>();//生产订单
		for(int i=0;i<table02.getNumRows();i++){
			Orders order = new Orders();
			table02.setRow(i);
			order.setAufnr(table02.getString("AUFNR"));//订单号
			order.setMatnr(table02.getString("PLNBEZ"));//产品
			order.setMaktx(table02.getString("MAKTX"));//物料描述
			order.setAufpl(table02.getString("AUFPL"));//工艺路线号
			order.setRsnum(table02.getString("RSNUM"));//BOM预留号
			order.setGamng(table02.getString("GAMNG"));//订单数量
			order.setGstrp(table02.getString("GSTRP"));//订单开始日期
			order.setGltrp(table02.getString("GLTRP"));//订单结束日期
			order.setMujuntext(table02.getString("TEXT"));//长文本
			//System.out.println("-------status-----------"+table02.getString("STATUS")+"----------------");
			if(table02.getString("STATUS").indexOf("DLID")!=-1){//删除标记
				order.setIsdel("Y");
			}else{
				order.setIsdel("N");
			}
			orderlist.add(order);
		}
		
		List<ProcessRoute> processrouteList = new ArrayList<ProcessRoute>();//工艺路线
		for(int i=0;i<table03.getNumRows();i++){
			table03.setRow(i);
			ProcessRoute processroute = new ProcessRoute();
			processroute.setAufpl(table03.getString("AUFPL"));//工艺路线号
			processroute.setProcessCode(table03.getString("VORNR"));//操作，mes系统中是工序编码
			processroute.setProcessName(table03.getString("LTXA1"));//短文本
			processroute.setSteus(table03.getString("STEUS"));//控制码
			processroute.setWorkCenter(table03.getString("ARBPL"));//工作中心
			processrouteList.add(processroute);
		}
		
		List<Bom> bomList = new ArrayList<Bom>();//BOM
		for(int i=0;i<table04.getNumRows();i++){
			table04.setRow(i);
			Bom bom = new Bom();
			bom.setRsnum(table04.getString("RSNUM"));//预留号
			bom.setMaterialCode(table04.getString("MATNR"));//物料
			bom.setMaterialAmount(table04.getDouble("BDMNG"));//需求数量
			bom.setMaterialName(table04.getString("MAKTX"));//物料描述
			bom.setShift(table04.getString("ABLAD"));//班组
			bom.setRspos(table04.getString("RSPOS"));//项目
			bom.setMaterialUnit(table04.getString("MEINS"));//基本单位
			String isdel = table04.getString("XLOEK");//标记删除
			if("X".equals(isdel)){
				bom.setIsDel("Y");
			}else{
				bom.setIsDel("N");
			}
			bomList.add(bom);
		}
		workingbillservice.mergeWorkingBill(list,orderlist,processrouteList,bomList);
	}

	@Override
	public void syncRepairorderAllNew(String startdate, String enddate,String starttime, String endtime, String aufnr, String type)
			throws IOException, CustomerException {
		super.setProperty("workingbillall2");//根据配置文件读取到函数名称
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IM_TYPE", type);
		parameter.put("STARTDATE", startdate);
		parameter.put("ENDDATE", enddate);
		parameter.put("STARTTIME", starttime);
		parameter.put("ENDTIME", endtime);
		//parameter.put("IM_ARBPL", workcode);
		if(aufnr == null) aufnr="";
		parameter.put("IM_AUFNR", aufnr);
		//if(workshopcode == null) workshopcode="";
		//parameter.put("G_FEVOR", workshopcode);
		//System.out.println("-------------"+parameter+"-------------");
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
//		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
//		for(UnitdistributeProduct m:unitdistributeList){
//			HashMap<String,Object> item = new HashMap<String,Object>();
//			item.put("MATNR",m.getMaterialCode());//物料编码
//			arrList.add(item);
//		}
		//System.out.println("-------------"+arrList+"-------------");
//		List<HashMap<String,Object>> arr = new ArrayList<HashMap<String,Object>>(new HashSet<HashMap<String,Object>>(arrList));
//		tablemodel.setList(arr);
		tablemodelList.add(tablemodel);
		//super.setParameter(parameter);
		//super.setStructure(null);
		//super.setTable(tablemodelList);
		SAPModel model = execBapi(tablemodelList,null,parameter);//执行 并获取返回值
		
		ParameterList outs = model.getOuttab();//返回表
		Table table01 = outs.getTable("ET_ITEM");//随工单
		Table table02 = outs.getTable("ET_AFKO");//订单
		Table table03 = outs.getTable("ET_AFVC");//订单工艺路线
		Table table04 = outs.getTable("ET_RESB");//生产订单BOM
		Table table05 = outs.getTable("ET_ITEM_DELETED");//生产订单ET_ITEM_DELETED
		List<WorkingBill> list = new ArrayList<WorkingBill>();
		for(int i=0;i<table01.getNumRows();i++){
			WorkingBill workingbill = new WorkingBill();
			table01.setRow(i);
			workingbill.setWorkingBillCode(table01.getString("ZSGD"));//随工单号
			workingbill.setProductDate(table01.getString("ZDATE"));//生产日期
			workingbill.setPlanCount(table01.getInt("ZBJH"));//计划数
			workingbill.setMaktx(table01.getString("MAKTX"));//物料描述
			workingbill.setMatnr(table01.getString("MATNR"));//物料编号
			workingbill.setWerks(table01.getString("WERKS"));//工厂
			workingbill.setAufnr(table01.getString("AUFNR"));//订单号
			workingbill.setWorkcenter(table01.getString("ARBPL"));//工作中心
			list.add(workingbill);
		}
		List<Orders> orderlist = new ArrayList<Orders>();//生产订单
		for(int i=0;i<table02.getNumRows();i++){
			Orders order = new Orders();
			table02.setRow(i);
			order.setAufnr(table02.getString("AUFNR"));//订单号
			order.setMatnr(table02.getString("PLNBEZ"));//产品
			order.setMaktx(table02.getString("MAKTX"));//物料描述
			order.setAufpl(table02.getString("AUFPL"));//工艺路线号
			order.setRsnum(table02.getString("RSNUM"));//BOM预留号
			order.setGamng(table02.getString("GAMNG"));//订单数量
			order.setGstrp(table02.getString("GSTRP"));//订单开始日期
			order.setGltrp(table02.getString("GLTRP"));//订单结束日期
			order.setMujuntext(table02.getString("TEXT"));//长文本
			//System.out.println("-------status-----------"+table02.getString("STATUS")+"----------------");
			if(table02.getString("STATUS").indexOf("DLID")!=-1){//删除标记
				order.setIsdel("Y");
			}else{
				order.setIsdel("N");
			}
			orderlist.add(order);
		}
		
		List<ProcessRoute> processrouteList = new ArrayList<ProcessRoute>();//工艺路线
		for(int i=0;i<table03.getNumRows();i++){
			table03.setRow(i);
			ProcessRoute processroute = new ProcessRoute();
			processroute.setAufpl(table03.getString("AUFPL"));//工艺路线号
			processroute.setProcessCode(table03.getString("VORNR"));//操作，mes系统中是工序编码
			processroute.setProcessName(table03.getString("LTXA1"));//短文本
			processroute.setSteus(table03.getString("STEUS"));//控制码
			processroute.setWorkCenter(table03.getString("ARBPL"));//工作中心
			processrouteList.add(processroute);
		}
		
		List<Bom> bomList = new ArrayList<Bom>();//BOM
		for(int i=0;i<table04.getNumRows();i++){
			table04.setRow(i);
			Bom bom = new Bom();
			bom.setRsnum(table04.getString("RSNUM"));//预留号
			bom.setMaterialCode(table04.getString("MATNR"));//物料
			bom.setMaterialAmount(table04.getDouble("BDMNG"));//需求数量
			bom.setMaterialName(table04.getString("MAKTX"));//物料描述
			bom.setShift(table04.getString("ABLAD"));//班组
			bom.setRspos(table04.getString("RSPOS"));//项目
			bom.setMaterialUnit(table04.getString("MEINS"));//基本单位
			String isdel = table04.getString("XLOEK");//标记删除
			if("X".equals(isdel)){
				bom.setIsDel("Y");
			}else{
				bom.setIsDel("N");
			}
			bomList.add(bom);
		}
//		List<WorkingBill> listdel = new ArrayList<WorkingBill>();
//		for(int i=0;i<table05.getNumRows();i++){
//			WorkingBill workingbill = new WorkingBill();
//			table01.setRow(i);
//			workingbill.setWorkingBillCode(table01.getString("ZSGD"));//随工单号
//			workingbill.setProductDate(table01.getString("ZDATE"));//生产日期
//			workingbill.setPlanCount(table01.getInt("ZBJH"));//计划数
//			workingbill.setMaktx(table01.getString("MAKTX"));//物料描述
//			workingbill.setMatnr(table01.getString("MATNR"));//物料编号
//			workingbill.setWerks(table01.getString("WERKS"));//工厂
//			workingbill.setAufnr(table01.getString("AUFNR"));//订单号
//			workingbill.setWorkcenter(table01.getString("ARBPL"));//工作中心
//			listdel.add(workingbill);
//		}
		workingbillservice.mergeWorkingBill(list,orderlist,processrouteList,bomList);
		//workingbillservice.mergeWorkingBillNew(list,orderlist,processrouteList,bomList,listdel);
	}
	/**
	 * 执行BAPI
	 * 
	 * @throws IOException
	 */
	protected SAPModel execBapi(List<TableModel> tablemodelList1,Mapping mapping,HashMap<String, Object> parameter) throws IOException {
		SAPModel mode = new SAPModel();
		Client myConnection = null;

			myConnection = getSAPConnection();// 获取SAP链接
			myConnection.connect();// 开启链接
		Repository myRepository = new JCO.Repository("Repository", myConnection); // 名字
		IFunctionTemplate ft = myRepository
				.getFunctionTemplate(getFunctionName());// 从SAP读取函数信息
		Function bapi = ft.getFunction();// 获得函数物件
		ParameterList parameterList = bapi.getImportParameterList();// 获得输入参数
		JCO.ParameterList inputtable = bapi.getTableParameterList();// 输入表的处理

		if (parameter != null) {
			Set set = parameter.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry) it.next();
				parameterList.setValue((String) entry.getValue(),
						(String) entry.getKey());
			}
		}

		if (mapping != null) {
			Structure Structure01 = parameterList.getStructure(mapping
					.getStrutName());
			Map map = mapping.getMap();
			Set set = map.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				Map.Entry entry = (Map.Entry) it.next();
				Structure01.setValue((String) entry.getValue(),
						(String) entry.getKey());
			}
		}

		if (tablemodelList1 != null) {
			for (int i = 0; i < tablemodelList1.size(); i++) {
				TableModel table = (TableModel) tablemodelList1.get(i);
				Table IT_ITEM = inputtable.getTable(table.getData());
				List tablelist = table.getList();
				for (int j = 0; j < tablelist.size(); j++) {
					IT_ITEM.appendRow();
					Map map = (Map) tablelist.get(j);
					Set set = map.entrySet();
					for (Iterator it = set.iterator(); it.hasNext();) {
						Map.Entry entry = (Map.Entry) it.next();
						//System.out.println(entry.getKey() + "-----------"+ (String) entry.getValue());
						IT_ITEM.setValue((String) entry.getValue(),
								(String) entry.getKey());

					}
				}
			}
		}
		myConnection.execute(bapi);
		mode.setOuts(bapi);
		mode.setOuttab(bapi);
		if (null != myConnection) {
			this.releaseClient(myConnection);
		}

		return mode;
	}

}
