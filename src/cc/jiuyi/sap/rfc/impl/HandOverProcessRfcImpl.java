package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;



public class HandOverProcessRfcImpl extends BaserfcServiceImpl implements HandOverProcessRfc {

	public List<HashMap<String,Object>> findWarehouse(Integer warehouse,List<String> materialCodeList) throws IOException {
		super.setProperty("warehouse");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("LGORT", warehouse);//库存地点
		/******输入参数 end**/
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		for(String material : materialCodeList){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", material);
			arrList.add(item);
		}
		tablemodel.setList(arrList);
		tablemodelList.add(tablemodel);
		/******输入表 end**/
		
		/*******执行******/
		super.setParameter(parameter);//输入参数
		super.setTable(tablemodelList);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		
		//model.getOuts();//返回参数
		ParameterList outs = model.getOuttab();//返回表
		Table table01 = outs.getTable("IT_ITEM");
		for(int i=0;i<table01.getNumRows();i++){
			table01.setRow(i);
			
		}
		
		return null;
	}
	
	
}
