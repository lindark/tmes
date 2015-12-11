package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class HandOverProcessRfcImpl extends BaserfcServiceImpl implements HandOverProcessRfc{

	@Override
	public List<HandOverProcess> BatchHandOver(List<HandOverProcess> list,String testrun)
			throws IOException, CustomerException {
		super.setProperty("handover");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IS_COMMIT", testrun);//testrun
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		for(HandOverProcess p : list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", p.getMaterialCode());//物料编码
			item.put("ZSFSL", p.getAmount().toString());//数量
			item.put("ORDERID1", p.getAfterworkingbill().getWorkingBillCode());//下班随工单
			item.put("ORDERID2", p.getBeforworkingbill().getWorkingBillCode());//上班随工单
			item.put("XUH", p.getId());
			arrList.add(item);
		}
		tablemodel.setList(arrList);
		tablemodelList.add(tablemodel);
		/*******执行******/
		super.setTable(tablemodelList);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("IT_ITEM");//列表
		List<HandOverProcess> backlist = new ArrayList<HandOverProcess>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			HandOverProcess h = new HandOverProcess();
			h.setE_type(t_data.getString("E_TYPE"));
			h.setE_message(t_data.getString("E_MESSAGE"));
			h.setMblnr(t_data.getString("MBLNR"));
			h.setMaterialCode(t_data.getString("MATNR"));
			h.setId(t_data.getString("XUH"));
			backlist.add(h);
		}
		return backlist;
	}

}
