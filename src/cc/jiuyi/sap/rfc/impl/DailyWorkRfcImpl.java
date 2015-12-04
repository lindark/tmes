package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;

import cc.jiuyi.sap.rfc.DailyWorkRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
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
			throw new CustomerException("1400001", "领退料失败,"+message);
		}
	}

}
