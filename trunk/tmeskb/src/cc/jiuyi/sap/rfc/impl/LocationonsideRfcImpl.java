package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;


@Component
public class LocationonsideRfcImpl extends BaserfcServiceImpl implements LocationonsideRfc {

	public List<Locationonside> findWarehouse(String warehouse,List<String> materialCodeList) throws IOException, CustomerException {
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
		
		ParameterList out = model.getOuts();//返回参数
		ParameterList outs = model.getOuttab();//返回表
		
		String type =  out.getString("E_TYPE");//返回类型
		String message = out.getString("E_MESSAGE");//返回消息
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", message);
		}
		
		Table table01 = outs.getTable("IT_ITEM");
		List<Locationonside> locationonsideList = new ArrayList<Locationonside>();
		for (int i = 0; i < table01.getNumRows(); i++) {
			table01.setRow(i);
			Locationonside locationonside = new Locationonside();
			locationonside.setMaterialCode(table01.getString("MATNR"));// 物料编码
			locationonside.setMaterialName(table01.getString("MAKTX"));// 物料描述
			locationonside.setLocationCode(table01.getString("LGORT"));// 库存地点
			locationonside.setAmount(table01.getString("LABST"));// 非限制使用数量
			locationonsideList.add(locationonside);
		}
		return locationonsideList;
	}
	
	
}
