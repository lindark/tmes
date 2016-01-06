package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;
import cc.jiuyi.sap.rfc.RepairInRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;

public class RepairInRfcImpl extends BaserfcServiceImpl implements RepairInRfc{

	public List<Map<Object,Object>> repairCrt(List<Map<Object,Object>> list1,List<Map<Object,Object>> list2) throws IOException,
	CustomerException {
		super.setProperty("repairin");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(Map<Object,Object> map : list2){
			HashMap<String,Object> item = new HashMap<String,Object>();
			//物料凭证抬头，需要去重 参考：http://www.2cto.com/kf/201410/341634.html
			item.put("WERKS", map.get("WERKS").toString());//工厂 必填
			item.put("LGORT", map.get("LGORT").toString());//库存地点
			item.put("ZTEXT", map.get("ZTEXT").toString());//抬头文本，选填
			item.put("XUH", map.get("XUH").toString());//序号  必填
			item.put("MOVE_TYPE","261");//移动类型
			item.put("KOSTL", "10008431");//成本中心
			arrList.add(item);
			
			item.put("WERKS", map.get("WERKS").toString());//工厂 必填
			item.put("LGORT", map.get("LGORT").toString());//库存地点
			item.put("ZTEXT", map.get("ZTEXT").toString());//抬头文本，选填
			item.put("XUH", map.get("XUH").toString());//序号  必填
			item.put("MOVE_TYPE","906");//移动类型
			item.put("KOSTL", "10008431");//成本中心
			arrList.add(item);
		}
		//去重
		List<HashMap<String,Object>> arr 
		= new ArrayList<HashMap<String,Object>>(new HashSet<HashMap<String,Object>>(arrList));
		ET_HEADER.setList(arr);
		List<HashMap<String,Object>> arrList2 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for(Map<Object,Object> map : list1){
			HashMap<String,Object> item = new HashMap<String,Object>();
			//物料凭证明细
			item.put("MATNR", map.get("MATNR").toString());//物料编码
			item.put("ZSFSL", map.get("ZSFSL").toString());//数量
			item.put("ITEM_TEXT", map.get("ITEM_TEXT").toString());//项目文本 选填
			item.put("XUH", map.get("XUH").toString());//序号 必填
			arrList2.add(item);
		}
		ET_ITEM.setList(arrList2);
		tablemodelList.add(ET_ITEM);
		super.setParameter(parameter);
		super.setTable(tablemodelList);
		/******执行 end******/
		SAPModel model = execBapi();//执行 并获取返回值
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_HEADER");//列表
		List<Map<Object,Object>> list_sapreturn = new ArrayList<Map<Object,Object>>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			Map<Object,Object>m=new HashMap<Object,Object>();
			m.put("E_TYPE", t_data.getString("E_TYPE"));//返回类型：成功S/失败E
			m.put("E_MESSAGE", t_data.getString("E_MESSAGE"));//返回消息
			m.put("XUH", t_data.getString("XUH"));//序号
			m.put("EX_MBLNR", t_data.getString("EX_MBLNR"));//返回物料凭证
			list_sapreturn.add(m);
		}
		return list_sapreturn;
	}

}
