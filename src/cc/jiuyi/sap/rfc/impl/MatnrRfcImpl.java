package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.sap.rfc.MatnrRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
@Component
public class MatnrRfcImpl extends BaserfcServiceImpl implements MatnrRfc{
	@Override
	public List<HashMap<String, String>> getMaterialList(String matnr,
			String werks, String maktx)  throws IOException,CustomerException{
		super.setProperty("materials");//获取函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("MATNR", matnr);//物料编码
		parameter.put("WERKS", werks);//工厂
		parameter.put("DESCRIPTION", maktx);//物料描述
		/*******执行******/
		super.setParameter(parameter);//输入参数
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList outs = model.getOuttab();//返回表
		Table T_DATA = outs.getTable("T_DATA");//列表
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < T_DATA.getNumRows(); i++) {
			T_DATA.setRow(i);
			HashMap<String,String> map = new HashMap<String, String>();
			map.put("matnr", T_DATA.getString("MATNR"));//物料编码
			map.put("maktx", T_DATA.getString("MAKTX"));//物料描述
			map.put("meins", T_DATA.getString("MEINS"));//单位
			map.put("werks", T_DATA.getString("WERKS"));//工厂
			map.put("matkl", T_DATA.getString("MATKL"));//物料组
			list.add(map);
		}
		return list;
	}

}
