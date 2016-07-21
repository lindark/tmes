package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.EndProduct;
import cc.jiuyi.sap.rfc.EndProductRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;

@Component
public class EndProductRfcImpl  extends BaserfcServiceImpl implements EndProductRfc{

	@Override
	public List<EndProduct> EndProductCrt(String testrun,List<EndProduct> list)
			throws IOException, CustomerException {
		super.setProperty("pickbatch");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("GM_CODE", "04");//MB1B
		parameter.put("IS_COMMIT", testrun);//testrun
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(EndProduct e:list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("BUDAT",e.getBudate());//过账日期
			item.put("WERKS",e.getWerks());//工厂
			item.put("LGORT",e.getRepertorySite());//库存地点
			item.put("ZTEXT",e.getConfirmName());//确认人
			item.put("MOVE_TYPE",e.getMoveType());//移动类型
			item.put("XUH",e.getId());//ID
			arrList.add(item);
		}
		//去重
		List<HashMap<String,Object>> arr 
		= new ArrayList<HashMap<String,Object>>(new HashSet<HashMap<String,Object>>(arrList));
		ET_HEADER.setList(arr);
		tablemodelList.add(ET_HEADER);
		List<HashMap<String,Object>> arrList2 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for(EndProduct e:list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", e.getMaterialCode());//物料编码
			item.put("ZSFSL", e.getStockMout().toString());//数量
			item.put("XUH",e.getId());//ID
			item.put("CHARG", e.getMaterialBatch());//批次
			item.put("MOVE_STLOC", e.getReceiveRepertorySite());//收货库存地点
			arrList2.add(item);
		}
		ET_ITEM.setList(arrList2);
		tablemodelList.add(ET_ITEM);
		//super.setParameter(parameter);
		//super.setTable(tablemodelList);
		/******执行 end******/
		SAPModel model = execBapi(parameter,null,tablemodelList);//执行 并获取返回值
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_HEADER");//列表
		List<EndProduct> elist = new ArrayList<EndProduct>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			EndProduct e = new EndProduct();
			e.setE_type(t_data.getString("E_TYPE"));
			e.setE_message(t_data.getString("E_MESSAGE"));
			e.setEx_mblnr(t_data.getString("EX_MBLNR"));
			e.setId(t_data.getString("XUH"));
			elist.add(e);
		}
		return elist;
	}

}
