package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;
import cc.jiuyi.entity.Repair;
import cc.jiuyi.sap.rfc.RepairRfc;
import cc.jiuyi.sap.rfc.impl.BaserfcServiceImpl;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;

public class RepairRfcImpl extends BaserfcServiceImpl implements RepairRfc{

	@Override
	public List<Repair> repairCrt(List<Repair> list) throws IOException,
			CustomerException {
		super.setProperty("pickbatch");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(Repair r : list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			//物料凭证抬头，需要去重 参考：http://www.2cto.com/kf/201410/341634.html
			arrList.add(item);
		}
		List<HashMap<String,Object>> arrList2 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for(Repair r : list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			//物料凭证明细
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
		List<Repair> rep = new ArrayList<Repair>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			Repair repair = new Repair();
			
			rep.add(repair);
		}
		return rep;
	}

}
