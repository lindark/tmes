
package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import cc.jiuyi.entity.Sark;
import cc.jiuyi.entity.SarkSon;
import cc.jiuyi.sap.rfc.SarkRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;
@Component
public class SarkRfcImpl extends BaserfcServiceImpl implements SarkRfc{

	public static Logger log = Logger.getLogger(SarkRfcImpl.class);
	
	@Override
	public Sark SarkCrt(String testrun,List<SarkSon>list_cs) throws IOException, CustomerException {
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
		for(SarkSon cs:list_cs)
		{
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("BUDAT", cs.getBUDAT());//过账日期
			item.put("WERKS", cs.getWERKS());//工厂
			item.put("LGORT", cs.getLGORT());//库存地点
			item.put("MOVE_TYPE", cs.getMOVE_TYPE());//移动类型
			item.put("XUH", cs.getSark().getId());//序号
			item.put("ZTEXT", cs.getSark().getBktxt());//单据编号
			//item.put("ET_ITEM-EBELN", cs.getSark().getEX_EBELN());//采购订单
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
		for(SarkSon cs:list_cs)
		{
			HashMap<String,Object> item2 = new HashMap<String,Object>();
			item2.put("MATNR", cs.getMATNR());//物料编码
			item2.put("ZSFSL", cs.getCscount());//数量
			item2.put("LIFNR", cs.getLIFNR());//供应商
			item2.put("XUH", cs.getSark().getId());//序号
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
		Sark c_return = new Sark();
		t_data.setRow(0);
		c_return.setId(t_data.getString("XUH"));
		c_return.setE_MESSAGE(t_data.getString("E_MESSAGE"));
		c_return.setE_TYPE(t_data.getString("E_TYPE"));
		c_return.setEX_MBLNR(t_data.getString("EX_MBLNR"));
		return c_return;
	}

	@Override
	public Object[] SarkCrtNew(String testrun,List<SarkSon>list_cs) throws IOException,
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
		for(SarkSon cs:list_cs)
		{
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("BUDAT", cs.getBUDAT());//过账日期
			item.put("WERKS", cs.getWERKS());//工厂
			item.put("LGORT", cs.getLGORT());//库存地点
			item.put("MOVE_TYPE", cs.getMOVE_TYPE());//移动类型
			item.put("XUH", cs.getSark().getId());//序号
			item.put("ZTEXT", cs.getSark().getBktxt());//单据编号
			//item.put("ET_ITEM-EBELN", cs.getSark().getEX_EBELN());//采购订单
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
		for(SarkSon cs:list_cs)
		{
			HashMap<String,Object> item2 = new HashMap<String,Object>();
			item2.put("MATNR", cs.getMATNR());//物料编码
			item2.put("ZSFSL", cs.getCscount());//数量
			item2.put("LIFNR", cs.getLIFNR());//供应商
			item2.put("XUH", cs.getSark().getId());//序号
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
		
		Sark c_return = new Sark();
		t_data.setRow(0);
		c_return.setId(t_data.getString("XUH"));
		c_return.setE_MESSAGE(t_data.getString("E_MESSAGE"));
		c_return.setE_TYPE(t_data.getString("E_TYPE"));
		c_return.setEX_MBLNR(t_data.getString("EX_MBLNR"));
		obj[0] = c_return;
		Table t_data1 = outs.getTable("ET_ITEM");//列表
		for(int i=0;i<t_data1.getNumRows();i++){
			t_data1.setRow(i);
			String xuh = t_data1.getString("XUH1");//序号
			log.info("-------------------"+testrun+"--------------xuh---"+xuh);
			for(SarkSon cs:list_cs){
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

}
