package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.sap.rfc.MatStockRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class MatStockRfcImpl extends BaserfcServiceImpl implements MatStockRfc{

	@Override
	public List<HashMap<String, String>> getMatStockList(List<HashMap<String, String>> list) throws IOException,CustomerException {
		super.setProperty("matstock");//获取函数名称
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		for(int i=0;i<list.size();i++){
			HashMap<String,Object> item = new HashMap<String,Object>();
			if(!list.get(i).get("matnr").equals("")&&!list.get(i).get("lgort").equals("")){
				item.put("MATNR", list.get(i).get("matnr"));//物料编码
				item.put("LGORT", list.get(i).get("lgort"));//库存地点
				arrList.add(item);
			}
		}
		tablemodel.setList(arrList);
		tablemodelList.add(tablemodel);
		super.setTable(tablemodelList);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_ITEM");//列表
		List<HashMap<String, String>> stock = new ArrayList<HashMap<String,String>>();
		for(int i = 0; i < t_data.getNumRows(); i++){
			t_data.setRow(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("matnr", t_data.getString("MATNR"));
			map.put("lgort", t_data.getString("LGORT"));
			map.put("labst", t_data.getString("LABST"));//非限制使用库存
			stock.add(map);
		}
		return stock;
	}

}
