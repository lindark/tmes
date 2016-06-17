package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapLater;
import cc.jiuyi.sap.rfc.ScrapRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class ScrapRfcImpl extends BaserfcServiceImpl implements ScrapRfc{

	/*@Override
	public List<Scrap> ScrappedCrt(String testrun,List<Scrap> scrap, List<ScrapMessage> scrapmessage) throws IOException, CustomerException  {
		super.setProperty("pickbatch");//根据配置文件读取到函数名称
		*//******输入参数******//*
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("GM_CODE", "03");//
		parameter.put("IS_COMMIT", testrun);//testrun
		*//******输入表******//*
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");//表名
		for(ScrapMessage s : scrapmessage){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", s.getSmmatterNum());//物料编码
			//item.put("MEINS", s.getMeins());//单位
			item.put("ZSFSL", s.getMenge().toString());//数量
			item.put("ITEM_TEXT", s.getItem_text());//项目文本
			item.put("XUH", s.getId());//ID
			arrList.add(item);
		}
		ET_ITEM.setList(arrList);
		tablemodelList.add(ET_ITEM);
		List<HashMap<String,Object>> arrList1 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		for(Scrap s : scrap){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("BUDAT", s.getBudat());//过账日期
			item.put("WERKS", s.getWerks());//工厂
			item.put("LGORT", s.getLgort());//库存地点
			item.put("ZTEXT", s.getZtext());//抬头文本
			item.put("MOVE_TYPE",s.getMove_type());//移动类型
			item.put("XUH", s.getId());//ID
			arrList1.add(item);
		}
		ET_HEADER.setList(arrList1);
		tablemodelList.add(ET_HEADER);
		*//*******执行******//*
		super.setParameter(parameter);
		super.setTable(tablemodelList);
		SAPModel model = execBapi();//执行 并获取返回值;
		
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_HEADER");//列表
		List<Scrap> list = new ArrayList<Scrap>();
		for (int i = 0; i < t_data.getNumRows(); i++) {
			t_data.setRow(i);
			Scrap s = new Scrap();
			s.setE_type(t_data.getString("E_TYPE"));
			s.setE_message(t_data.getString("E_MESSAGE"));
			s.setId(t_data.getString("XUH"));
			s.setMblnr(t_data.getString("EX_MBLNR"));
			list.add(s);
		}
		return list;
	}*/

	public Scrap ScrappedCrt(String testrun,Scrap scrap,List<ScrapLater> list_scraplater) throws IOException, CustomerException  {
		super.setProperty("pickbatch");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("GM_CODE", "03");//移动类型
		parameter.put("IS_COMMIT", testrun);//testrun
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");//表名
		for(ScrapLater s : list_scraplater){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", s.getSlmatterNum());//物料编码
			item.put("ZSFSL", s.getSlmatterCount().toString());//数量
			item.put("ORDERID", s.getOrderid());//订单
			item.put("ITEM_TEXT", s.getItem_text());//项目文本
			item.put("XUH", s.getScrap().getId());//ID
			item.put("CHARG", s.getCharg());//批次
			arrList.add(item);
		}
		ET_ITEM.setList(arrList);
		tablemodelList.add(ET_ITEM);
		List<HashMap<String,Object>> arrList1 = new ArrayList<HashMap<String,Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");//表名
		HashMap<String,Object> item2 = new HashMap<String,Object>();
		item2.put("BUDAT", scrap.getBudat());//过账日期
		item2.put("WERKS", scrap.getWerks());//工厂
		item2.put("LGORT", scrap.getLgort());//库存地点
		item2.put("ZTEXT", scrap.getZtext());//抬头文本
		item2.put("MOVE_TYPE",scrap.getMove_type());//移动类型
		item2.put("XUH", scrap.getId());//ID
		arrList1.add(item2);
		ET_HEADER.setList(arrList1);
		tablemodelList.add(ET_HEADER);
		/*******执行******/
		super.setParameter(parameter);
		super.setTable(tablemodelList);
		SAPModel model = execBapi();//执行 并获取返回值;
		
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("ET_HEADER");//列表
		t_data.setRow(0);
		Scrap s = new Scrap();
		s.setE_type(t_data.getString("E_TYPE"));
		s.setE_message(t_data.getString("E_MESSAGE"));
		s.setId(t_data.getString("XUH"));
		System.out.println("-----------------------------------------------"+t_data.getDouble("EX_MBLNR"));
		System.out.println("-----------------------------------------------"+t_data.getString("EX_MBLNR"));
		s.setMblnr(t_data.getString("EX_MBLNR"));
		return s;
	}
}
