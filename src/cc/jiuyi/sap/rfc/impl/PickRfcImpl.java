package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;

import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.sap.rfc.PickRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class PickRfcImpl extends BaserfcServiceImpl implements PickRfc{

	@Override
	public String MaterialDocumentCrt(Pick pick,List<PickDetail> pickdetail) throws IOException, CustomerException {
		super.setProperty("pick");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("MOVE_TYPE", pick.getMove_type());//移动类型
		super.setParameter(parameter);//输入参数
		/******输入结构******/
		Mapping mapping = new Mapping();
		HashMap<String,String> map = new HashMap<String,String>();
		mapping.setStrutName("IS_HEADER");//输入结构名称
		map.put("BUDAT", pick.getBudat());//过账日期
		map.put("WERKS", pick.getWerks());//工厂
		map.put("LGORT", pick.getLgort());//库存地点
		map.put("ZTEXT", pick.getZtext());//抬头文本
		mapping.setMap(map);
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		for(PickDetail p:pickdetail){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", p.getMaterialCode());//物料编码
			item.put("ZSFSL", p.getPickAmount());//领退料数量
			item.put("CHARG", p.getCharg());//批号
			item.put("ITEM_TEXT", p.getItem_text());//项目文本
			item.put("ORDERID", p.getOrderid());//工单号
			arrList.add(item);
		}
		tablemodel.setList(arrList);
		tablemodelList.add(tablemodel);
		/*******执行******/
		super.setParameter(parameter);//输入参数
		super.setStructure(mapping);//输入结构
		super.setTable(tablemodelList);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回参数
		String type =  out.getString("E_TYPE");//返回类型
		String message = out.getString("E_MESSAGE");//返回消息
		String mblnr = out.getString("EX_MBLNR");//物料凭证编号
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", "领退料失败,"+message);
		}
		return mblnr;
	}

}
