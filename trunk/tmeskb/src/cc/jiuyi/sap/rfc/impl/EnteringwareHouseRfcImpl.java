package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.sap.rfc.EnteringwareHouseRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;

@Component
public class EnteringwareHouseRfcImpl extends BaserfcServiceImpl implements EnteringwareHouseRfc{

	@Override
	public List<EnteringwareHouse> WarehousingCrt(String testrun,List<EnteringwareHouse> list)
			throws IOException, CustomerException {
		super.setProperty("pickbatch");//根据配置文件读取到函数名称
		/******输入参数******/
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("GM_CODE", "03");//移动类型
		parameter.put("IS_COMMIT", testrun);//testrun
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(EnteringwareHouse e:list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("BUDAT",e.getBudat());//过账日期
			item.put("WERKS",e.getWerks());//工厂
			item.put("LGORT",e.getLgort());//库存地点
			item.put("ZTEXT",e.getZtext());//文本
			item.put("MOVE_TYPE",e.getMoveType());//移动类型
			item.put("XUH",e.getId());//ID
			arrList.add(item);
		}
		ET_HEADER.setList(arrList);
		tablemodelList.add(ET_HEADER);
		List<HashMap<String,Object>> arrList2 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for(EnteringwareHouse e:list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", e.getWorkingbill().getMatnr());//物料编码
			item.put("ZSFSL", e.getStorageAmount());//数量
			item.put("XUH",e.getId());//ID
			//item.put("ITEM_TEXT", "");//项目文本
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
		List<EnteringwareHouse> elist = new ArrayList<EnteringwareHouse>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			EnteringwareHouse e = new EnteringwareHouse();
			e.setE_type(t_data.getString("E_TYPE"));
			e.setE_message(t_data.getString("E_MESSAGE"));
			e.setEx_mblnr(t_data.getString("EX_MBLNR"));
			elist.add(e);
		}
		return elist;
	}

}
