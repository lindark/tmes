package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.entity.Locationonside;
import cc.jiuyi.sap.rfc.LocationonsideRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;


@Component
public class LocationonsideRfcImpl extends BaserfcServiceImpl implements LocationonsideRfc {

	public List<Locationonside> findWarehouse(String warehouse,String werks) throws IOException, CustomerException {
		super.setProperty("warehouse");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("LGORT", warehouse);//库存地点
		parameter.put("WERKS", werks);
		/******输入参数 end**/
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
//		tablemodel.setData("IT_ITEM");//表名
//		for(String material : materialCodeList){
//			HashMap<String,Object> item = new HashMap<String,Object>();
//			item.put("MATNR", material);
//			arrList.add(item);
//		}
//		tablemodel.setList(arrList);
//		tablemodelList.add(tablemodel);
		/******输入表 end**/
		
		/*******执行******/
		super.setParameter(parameter);//输入参数
//		super.setTable(tablemodelList);
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
			locationonside.setCharg(table01.getString("CHARG"));//批次
			locationonsideList.add(locationonside);
		}
		return locationonsideList;
	}

	public List<Locationonside> findWarehouse1(String warehouse,String werks) throws IOException, CustomerException {
		super.setProperty("warehouse1");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("LGORT", warehouse);//库存地点
		parameter.put("WERKS", werks);
		/******输入参数 end**/
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
//		tablemodel.setData("IT_ITEM");//表名
//		for(String material : materialCodeList){
//			HashMap<String,Object> item = new HashMap<String,Object>();
//			item.put("MATNR", material);
//			arrList.add(item);
//		}
//		tablemodel.setList(arrList);
//		tablemodelList.add(tablemodel);
		/******输入表 end**/
		
		/*******执行******/
		super.setParameter(parameter);//输入参数
//		super.setTable(tablemodelList);
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
			locationonside.setCharg(table01.getString("CHARG"));//批次
			locationonsideList.add(locationonside);
		}
		return locationonsideList;
	}
	
	public List<HashMap<String, String>> findMaterial(String werks,
			String lgort, String matnr,String lgpla,String maktx) throws IOException {
			List<HashMap<String,String>> arrList = new ArrayList<HashMap<String,String>>();
			super.setProperty("materiallqua");//根据配置文件读取到函数名称
			/******输入参数******/
			HashMap<String,Object> parameter = new HashMap<String,Object>();
			parameter.put("S_WERKS", werks);//工厂
			parameter.put("S_LGORT", lgort);//库存地点
			parameter.put("S_MATNR", matnr);//物料
			parameter.put("S_LGPLA", lgpla);//库位
			parameter.put("S_MAKTX", maktx);//物料描述
			super.setParameter(parameter);//输入参数
			super.setTable(null);
			SAPModel model = execBapi();//执行 并获取返回值
			ParameterList out = model.getOuts();//返回参数
			ParameterList outs = model.getOuttab();//返回表
			/******执行 end******/
			Table ET_ITEM = outs.getTable("GT_LQUA");//仓位库存信息
			for(int i=0;i<ET_ITEM.getNumRows();i++){
				ET_ITEM.setRow(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("mantd",ET_ITEM.getString("MANTD"));//客户端编号
				map.put("rowno", ET_ITEM.getString("ROWNO"));//存放ID
				map.put("matnr", ET_ITEM.getString("MATNR"));//物料编码
				map.put("charg", ET_ITEM.getString("CHARG"));//批号
				map.put("verme", ET_ITEM.getString("VERME"));//可用库存
				map.put("lqnum", ET_ITEM.getString("LQNUM"));//数量
				map.put("meins", ET_ITEM.getString("MEINS"));//基本单位
				map.put("lgtyp", ET_ITEM.getString("LGTYP"));//仓储类型
				map.put("lgpla", ET_ITEM.getString("LGPLA"));//仓位
				map.put("dwnum", ET_ITEM.getString("DWNUM"));//Quantity in Parallel Unit of Entry
				map.put("lenum", ET_ITEM.getString("LENUM"));//仓储单位编号
				map.put("sequ", ET_ITEM.getString("SEQU"));//整数
				map.put("nlpla", ET_ITEM.getString("NLPLA"));//目的地仓位
				map.put("maktx", ET_ITEM.getString("MAKTX"));//物料描述
				arrList.add(map);
			}
			return arrList;
		
		
	}
	
	
}
