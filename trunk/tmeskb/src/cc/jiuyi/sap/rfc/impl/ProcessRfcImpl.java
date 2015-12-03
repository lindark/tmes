package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Process;
import cc.jiuyi.sap.rfc.ProcessRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
@Component
public class ProcessRfcImpl extends BaserfcServiceImpl implements ProcessRfc{

	@Override
	public List<Process> findProcess(String matnr, String werks,String date)
			throws IOException, CustomerException {
		super.setProperty("process");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("MATERIAL", matnr);//物料编码
		parameter.put("PLANT", werks);//工厂
		parameter.put("DATE", date);//日期
		super.setParameter(parameter);//输入参数
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		//ParameterList out = model.getOuts();//返回参数
		ParameterList outs = model.getOuttab();//返回表
		Table OPR_TAB = outs.getTable("OPR_TAB");
		List<Process> process = new ArrayList<Process>();
		for(int i = 0;i < OPR_TAB.getNumRows(); i++){
			OPR_TAB.setRow(i);
			Process pro = new Process();
			pro.setProcessCode(OPR_TAB.getString("VORNR"));
			pro.setProcessName(OPR_TAB.getString("LTXA1"));
			pro.setProcessName(OPR_TAB.getString("LTXA1"));
			process.add(pro);
		}
		return process;
	}

}
