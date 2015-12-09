package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Carton;
import cc.jiuyi.sap.rfc.CartonRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class CartonRfcImpl extends BaserfcServiceImpl implements CartonRfc{

	@Override
	public List<Carton> CartonCrt(List<Carton> list) throws IOException, CustomerException {
		super.setProperty("pickbatch");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("GM_CODE", "01");//MB01
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(Carton c : list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("BUDAT", c.getBudat());//过账日期
			item.put("WERKS", c.getWerks());//工厂
			item.put("LGORT", c.getLgort());//库存地点
			item.put("MOVE_TYPE", c.getMove_type());//移动类型
			item.put("XUH", c.getId());//序号
			arrList.add(item);
		}
		ET_HEADER.setList(arrList);
		tablemodelList.add(ET_HEADER);
		List<HashMap<String,Object>> arrList2 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for(Carton c : list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", c.getCartonCode());//物料编码
			item.put("ZSFSL", c.getCartonAmount().toString());//数量
			item.put("LIFNR", c.getLifnr());//供应商
			item.put("XUH", c.getId());//序号
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
		List<Carton> cartonlist = new ArrayList<Carton>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			Carton c = new Carton();
			c.setXuh(t_data.getString("XUH"));
			c.setE_message(t_data.getString("E_MESSAGE"));
			c.setE_type(t_data.getString("E_TYPE"));
			c.setEx_mblnr(t_data.getString("EX_MBLNR"));
			cartonlist.add(c);
		}
		return cartonlist;
	}

}
