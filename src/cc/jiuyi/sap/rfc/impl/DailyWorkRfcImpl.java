package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.sap.rfc.DailyWorkRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class DailyWorkRfcImpl  extends BaserfcServiceImpl implements DailyWorkRfc{

	@Override
	public void SetDailyWork(String orderid, String step, String menge) throws IOException, CustomerException {
		super.setProperty("dailywork");//根据配置文件读取到函数名称
		/******输入结构******/
		Mapping mapping = new Mapping();
		HashMap<String,String> map = new HashMap<String,String>();
		mapping.setStrutName("CS_DIS");//输入结构名称
		map.put("AUFNR", orderid);//订单
		map.put("VORNR", step);//工序
		map.put("YIELD", menge);//数量
		mapping.setMap(map);
		/*******执行******/
		super.setStructure(mapping);//输入结构
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回参数
		String type =  out.getString("E_TYPE");//返回类型
		String message = out.getString("E_MESSAGE");//返回消息
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", "报工失败,"+message);
		}
	}

	@Override
	public List<DailyWork> BatchSetDailyWork(List<DailyWork> dailywork)
			throws IOException, CustomerException {
		super.setProperty("dailyworkbatch");//根据配置文件读取到函数名称
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(DailyWork d : dailywork){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("AUFNR", d.getOrderid());
			item.put("VORNR", d.getStep());
			item.put("YIELD", d.getEnterAmount().toString());
			item.put("CONF_TEXT", d.getWb());
			arrList.add(item);
		}
		ET_HEADER.setList(arrList);
		tablemodelList.add(ET_HEADER);
		super.setTable(tablemodelList);
		/******执行 end******/
		SAPModel model = execBapi();//执行 并获取返回值
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_HEADER");//报工列表
		List<DailyWork> list = new ArrayList<DailyWork>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			DailyWork d = new DailyWork();
			d.setE_type(t_data.getString("E_TYPE"));
			d.setE_message(t_data.getString("E_MESSAGE"));
			d.setOrderid(t_data.getString("AUFNR"));
			d.setStep(t_data.getString("VORNR"));
			d.setEnterAmount(t_data.getDouble("YIELD"));
			d.setCONF_NO(t_data.getString("CONF_NO"));//确认号
			d.setCONF_CNT(t_data.getString("CONF_CNT"));//计数器
			list.add(d);
		}
		return list;
	}

	@Override
	public List<DailyWork> BatchSetDailyWorkCancel(List<DailyWork> dailywork)
			throws IOException, CustomerException {
		super.setProperty("dailyworkbatchcancel");//根据配置文件读取到函数名称
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel IT_ITEM = new TableModel();
		IT_ITEM.setData("IT_ITEM");//表名
		for(DailyWork d : dailywork){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("RUECK", d.getCONF_NO());
			item.put("RMZHL", d.getCONF_CNT());
			item.put("AUFNR", d.getOrderid());
			item.put("TXLINE", d.getWb());
			arrList.add(item);
		}
		IT_ITEM.setList(arrList);
		tablemodelList.add(IT_ITEM);
		super.setTable(tablemodelList);
		/******执行 end******/
		System.out.println("........................");
		SAPModel model = execBapi();//执行 并获取返回值
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("IT_ITEM");//报工列表
		List<DailyWork> list = new ArrayList<DailyWork>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			DailyWork d = new DailyWork();
			d.setE_type(t_data.getString("E_TYPE"));
			d.setE_message(t_data.getString("E_MESSAGE"));
			d.setCONF_NO(t_data.getString("RUECK"));
			d.setCONF_CNT(t_data.getString("RMZHL"));
			list.add(d);
		}
		return list;
	}

}
