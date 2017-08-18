
package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cc.jiuyi.action.admin.WorkbinAction;
import cc.jiuyi.entity.Workbin;
import cc.jiuyi.entity.WorkbinSon;
import cc.jiuyi.sap.rfc.WorkbinRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.SAPUtil;
import cc.jiuyi.util.TableModel;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;
@Component
public class WorkbinRfcImpl extends BaserfcServiceImpl implements WorkbinRfc{

	public static Logger log = Logger.getLogger(WorkbinAction.class);
	
	@Override
	public Workbin WorkbinCrt(String testrun,List<WorkbinSon>list_cs) throws IOException, CustomerException {
		super.setProperty("workbin");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("GM_CODE", "01");//MB01
		parameter.put("IS_COMMIT", testrun);//MB01
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(WorkbinSon cs:list_cs)
		{
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("BUDAT", cs.getBUDAT());//过账日期
//			item.put("WERKS", cs.getWERKS());//工厂
//			item.put("LGORT", cs.getLGORT());//库存地点
//			item.put("MOVE_TYPE", cs.getMOVE_TYPE());//移动类型
			item.put("XUH", cs.getWorkbin().getId());//序号
//			item.put("ZTEXT", cs.getWorkbin().getBktxt());//单据编号
			//item.put("ET_ITEM-EBELN", cs.getWorkbin().getEX_EBELN());//采购订单
			arrList.add(item);
		}
		//去重
		List<HashMap<String,Object>> arr 
		= new ArrayList<HashMap<String,Object>>(new HashSet<HashMap<String,Object>>(arrList));
		ET_HEADER.setList(arr);
		tablemodelList.add(ET_HEADER);
		List<HashMap<String,Object>> arrList2 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for(WorkbinSon cs:list_cs)
		{
			HashMap<String,Object> item2 = new HashMap<String,Object>();
//			item2.put("MATNR", cs.getMATNR());//物料编码
//			item2.put("ZSFSL", cs.getCscount());//数量
			item2.put("LIFNR", cs.getLIFNR());//供应商
			item2.put("XUH", cs.getWorkbin().getId());//序号
			item2.put("EBELN", cs.getEBELN());//采购订单
			item2.put("EBELP", cs.getEBELP());//采购订单行项目号
			arrList2.add(item2);
		}
		ET_ITEM.setList(arrList2);
		tablemodelList.add(ET_ITEM);
		//super.setParameter(parameter);
		//super.setTable(tablemodelList);
		
		
		
		
		
		
		
		/******执行 end******/
		SAPModel model = execBapi(parameter,null,tablemodelList);//执行 并获取返回值
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_HEADER");//列表
		Table t_data1 = outs.getTable("ET_ITEM");//列表
		Workbin c_return = new Workbin();
		t_data.setRow(0);
		c_return.setId(t_data.getString("XUH"));
//		c_return.setE_MESSAGE(t_data.getString("E_MESSAGE"));
		c_return.setE_TYPE(t_data.getString("E_TYPE"));
//		c_return.setEX_MBLNR(t_data.getString("EX_MBLNR"));
		return c_return;
	}

	@Override
	public Object[] WorkbinCrtNew(String testrun,List<WorkbinSon>list_cs) throws IOException,
			CustomerException {
		Object[] obj = new Object[2];
		super.setProperty("pickbatch");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("GM_CODE", "01");//MB01
		parameter.put("IS_COMMIT", testrun);//MB01
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(WorkbinSon cs:list_cs)
		{
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("BUDAT", cs.getBUDAT());//过账日期
//			item.put("WERKS", cs.getWERKS());//工厂
//			item.put("LGORT", cs.getLGORT());//库存地点
			item.put("MOVE_TYPE", cs.getMOVE_TYPE());//移动类型
			item.put("XUH", cs.getWorkbin().getId());//序号
//			item.put("ZTEXT", cs.getWorkbin().getBktxt());//单据编号
			//item.put("ET_ITEM-EBELN", cs.getWorkbin().getEX_EBELN());//采购订单
			arrList.add(item);
		}
		//去重
		List<HashMap<String,Object>> arr 
		= new ArrayList<HashMap<String,Object>>(new HashSet<HashMap<String,Object>>(arrList));
		ET_HEADER.setList(arr);
		tablemodelList.add(ET_HEADER);
		List<HashMap<String,Object>> arrList2 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for(WorkbinSon cs:list_cs)
		{
			HashMap<String,Object> item2 = new HashMap<String,Object>();
//			item2.put("MATNR", cs.getMATNR());//物料编码
//			item2.put("ZSFSL", cs.getCscount());//数量
			item2.put("LIFNR", cs.getLIFNR());//供应商
			item2.put("XUH", cs.getWorkbin().getId());//序号
			item2.put("XUH1", cs.getId());//序号
			item2.put("EBELN", cs.getEBELN());//采购订单
			item2.put("EBELP", cs.getEBELP());//采购订单行项目号
			arrList2.add(item2);
		}
		ET_ITEM.setList(arrList2);
		tablemodelList.add(ET_ITEM);
		//super.setParameter(parameter);
		//super.setTable(tablemodelList);
		/******执行 end******/
		SAPModel model = execBapi(parameter,null,tablemodelList);//执行 并获取返回值
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_HEADER");//列表
		
		Workbin c_return = new Workbin();
		t_data.setRow(0);
		c_return.setId(t_data.getString("XUH"));
//		c_return.setE_MESSAGE(t_data.getString("E_MESSAGE"));
		c_return.setE_TYPE(t_data.getString("E_TYPE"));
//		c_return.setEX_MBLNR(t_data.getString("EX_MBLNR"));
		obj[0] = c_return;
		Table t_data1 = outs.getTable("ET_ITEM");//列表
		for(int i=0;i<t_data1.getNumRows();i++){
			t_data1.setRow(i);
			String xuh = t_data1.getString("XUH1");//序号
			log.info("-------------------"+testrun+"--------------xuh---"+xuh);
			for(WorkbinSon cs:list_cs){
				if(cs.getId().equals(xuh)){
					log.info("-------------------"+testrun+"--------------cs.getId()---"+"-----------"+cs.getId()+t_data1.getString("EBELN")+"-----------"+t_data1.getString("EBELP"));
					cs.setEBELN(t_data1.getString("EBELN"));
					cs.setEBELP(t_data1.getString("EBELP"));
					break;
				}
			}
		}
		obj[1] = list_cs;
		return obj;
	}

	@Override
	public List<WorkbinSon> getWorkbinRfc(String ordernumber) {
		List<WorkbinSon> list=new ArrayList<WorkbinSon>();//用来储存从SAP获取的数据
		try
		{
			/******输入表******/
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
			List<TableModel> tablemodelList = new ArrayList<TableModel>();
			List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
			TableModel tablemodel = new TableModel();
			tablemodel.setData("LIKP");//表名
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("VBELN", ordernumber);//交货单号
			arrList.add(item);
			tablemodel.setList(arrList);
			
			if(arrList.size()>0){
				tablemodelList.add(tablemodel);
				String funname="ZTMS_FYJH";//函数名称
				System.out.println("=========getdata start===============");
				SAPModel sapmodel=SAPUtil.OperSAP(null, null, tablemodelList, funname);//访问SAP获取数据
				System.out.println("=========end getdata===============");
				JCO.ParameterList out=sapmodel.getOuts();//返回结构与参数
				JCO.ParameterList outs=sapmodel.getOuttab();//返回表
				Table t1=outs.getTable("ZTMS_FYJH");
		
				if(t1.getNumRows()>0)
				{
					for(int i=0;i<t1.getNumRows();i++)
					{
						t1.setRow(i);
						WorkbinSon ws=new WorkbinSon();
						String myStr=t1.getString("ZTYPE").replace(" ", "");
						ws.setWerks(t1.getString("WERKS").trim());	// WERKS 工厂 factory
						ws.setBktxt(t1.getString("VBELN").trim());// VBELN 交货单号 
						ws.setShipmentsDate(t1.getString("LFDAT").trim());// LFDAT 发货日期 
						ws.setJslgort(t1.getString("LGORT_JS").trim());	// LGORT_JS 接收库存 
						ws.setJslgortDesc(t1.getString("LGOBE").trim());	// LGOBE 接收库存地点 
						ws.setMatnr(t1.getString("MATNR").trim());// MATNR 物料号 meterialNumbe
						ws.setMatnrdesc(t1.getString("MAKTX").trim());// MAKTX 物料描述 
						ws.setWscount(t1.getString("LFIMG").trim());// LFIMG 料箱数量 
						list.add(ws);
					}
				}
			}
			System.out.println("============end  更新数据成功 ===============");
			return list;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("============  更新数据失败 ===============");
			return list;
		}
	}

	@Override
	public Map<String,String> updateWorkbinRfc(String ordernumber) {
		super.setProperty("updateworkbin");//根据配置文件读取到函数名称
		String message = "";
		try
		{
			System.out.println("============end  更新数据开始 ===============");
			System.out.println("--------------"+ordernumber+"-------------------------");
			/******输入参数******/
			HashMap<String,Object> parameter = new HashMap<String,Object>();
			parameter.put("VBELN", ordernumber);//移动类型
			/**输入表**/
//			List<TableModel> tablemodelList = new ArrayList<TableModel>();
//			TableModel tablemodel = new TableModel();
//			tablemodel.setData("LIKP");//表名
//			HashMap<String,Object> item = new HashMap<String,Object>();
//			item.put("VBELN", ordernumber);//交货单号
//			tablemodelList.add(tablemodel);
			/*******执行******/
			//super.setParameter(parameter);//输入参数
			//super.setStructure(null);//输入结构
			//super.setTable(tablemodelList);
			SAPModel model = execBapi(parameter,null,null);//执行 并获取返回值
			/******执行 end******/
			Map<String,String> map = new HashMap<String,String>();
			ParameterList out = model.getOuts();//返回参数
			map.put("status", out.getString("ZTYPE"));//返回类型
			map.put("message", out.getString("ZMESSAGE"));//返回消息
			
			System.out.println("============end  更新数据成功 ===============");
			return map;
		}
		catch(Exception e)
		{	
			Map<String,String> map = new HashMap<String,String>();
			map.put("status", "E");//返回类型
			map.put("message", "更新操作失败");//返回消息
			e.printStackTrace();
			System.out.println("============  更新数据失败 ===============");
			return map;
		}
	}

}
