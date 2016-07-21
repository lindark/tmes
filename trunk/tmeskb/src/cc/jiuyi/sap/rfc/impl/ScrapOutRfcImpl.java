package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.ScrapOut;
import cc.jiuyi.sap.rfc.ScrapOutRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
@Component
public class ScrapOutRfcImpl extends BaserfcServiceImpl implements ScrapOutRfc{

	@Override
	public List<ScrapOut> getScrapOut(String matnr, String grout,
			String maktx, String ganme) throws IOException, CustomerException {
		super.setProperty("workinginout");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("MATNR", matnr);
		parameter.put("GROUT", grout);
		parameter.put("MAKTX", maktx);
		parameter.put("GNAME", ganme);
		//super.setParameter(parameter);
		/******执行 end******/
		SAPModel model = execBapi(parameter,null,null);//执行 并获取返回值
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_ITEM");//列表
		List<ScrapOut> list=new ArrayList<ScrapOut>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			ScrapOut s = new ScrapOut();
			s.setMaterialCode(t_data.getString("MATNR"));
			s.setMaterialName(t_data.getString("MAKTX"));
			s.setMaterialUnit(t_data.getString("MEINS"));
			s.setProductsCode(t_data.getString("GROUT"));
			s.setProductsName(t_data.getString("GNAME"));
			s.setProductsUnit(t_data.getString("GEWEI"));
			list.add(s);
		}
		return list;
	}

}
