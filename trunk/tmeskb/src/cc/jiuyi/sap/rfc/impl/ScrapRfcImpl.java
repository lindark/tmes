package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;

import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.sap.rfc.ScrapRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class ScrapRfcImpl extends BaserfcServiceImpl implements ScrapRfc{

	@Override
	public String ScrappedCrt(Scrap scrap, List<ScrapMessage> scrapmessage) throws IOException, CustomerException  {
		super.setProperty("pick");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("MOVE_TYPE", scrap.getMove_type());//移动类型
		super.setParameter(parameter);//输入参数
		/******输入结构******/
		Mapping mapping = new Mapping();
		HashMap<String,String> map = new HashMap<String,String>();
		mapping.setStrutName("IS_HEADER");//输入结构名称
		map.put("BUDAT", scrap.getBudat());//过账日期
		map.put("WERKS", scrap.getWerks());//工厂
		map.put("LGORT", scrap.getLgort());//库存地点
		map.put("ZTEXT", scrap.getZtext());//抬头文本
		mapping.setMap(map);
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		for(ScrapMessage s : scrapmessage){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", s.getSmmatterNum());//物料编码
			//item.put("MEINS", s.getMeins());//单位
			item.put("ZSFSL", s.getMenge().toString());//数量
			item.put("ITEM_TEXT", s.getItem_text());//项目文本
			item.put("CHARG", s.getCharg());//批号
			arrList.add(item);
		}
		tablemodel.setList(arrList);
		tablemodelList.add(tablemodel);
		/*******执行******/
		super.setParameter(parameter);//输入参数
		super.setStructure(mapping);//输入结构
		super.setTable(tablemodelList);
		SAPModel model = execBapi();//执行 并获取返回值;
		ParameterList out = model.getOuts();//返回参数
		String type =  out.getString("E_TYPE");//返回类型
		String message = out.getString("E_MESSAGE");//返回消息
		String mblnr = out.getString("EX_MBLNR");//物料凭证编号
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", "报废出错,"+message);
		}
		return mblnr;
	}

}
