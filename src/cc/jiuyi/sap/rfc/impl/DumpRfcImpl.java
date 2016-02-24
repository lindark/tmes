package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.swing.event.TableModelListener;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.sap.rfc.DumpRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class DumpRfcImpl extends BaserfcServiceImpl implements DumpRfc {
	
	@Resource
	private AdminService adminservice;
	@Override
	public List<Dump> findMaterialDocument(String lgort, String bgdat,
			String eddat) throws IOException, CustomerException {
		super.setProperty("materialdocument");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IM_LGORT", lgort);//库存地点
		parameter.put("IM_BUDAT_B", bgdat);//开始时间
		parameter.put("IM_BUDAT_E", eddat);//结束时间
		super.setParameter(parameter);//输入参数
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回参数
		ParameterList outs = model.getOuttab();//返回表
		String type =  out.getString("E_TYPE");//返回类型
		String message = out.getString("E_MESSAGE");//返回消息
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", message);
		}
		Table ET_HEADER = outs.getTable("ET_HEADER");//物料凭证抬头表
		Table ET_ITEM = outs.getTable("ET_ITEM");//物料凭证明细表
		List<Dump> materialheader = new ArrayList<Dump>();
		for (int i = 0; i < ET_HEADER.getNumRows(); i++) {
			ET_HEADER.setRow(i);
			Dump dump =new Dump();
			dump.setVoucherId(ET_HEADER.getString("MBLNR"));//物料凭证编号
			dump.setMjahr(ET_HEADER.getString("MJAHR"));//凭证年度
			dump.setDeliveryDate(ET_HEADER.getDate("BUDAT"));//过账日期
			dump.setDeliveryTime(ET_HEADER.getString("CPUTM"));//过账时间
			materialheader.add(dump);
		}
		/**
		
		array.add(materialheader);
		array.add(materialitem);
		*/
		return materialheader;
	}

	@Override
	public List<DumpDetail> findMaterialDocumentByMblnr(String mblnr,String warehouse)
			throws IOException, CustomerException {
		super.setProperty("materialdocument");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IM_MBLNR", mblnr);//物料凭证号
		parameter.put("IM_LGORT", warehouse);
		super.setParameter(parameter);//输入参数
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回参数
		ParameterList outs = model.getOuttab();//返回表
		String type =  out.getString("E_TYPE");//返回类型
		String message = out.getString("E_MESSAGE");//返回消息
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", message);
		}
		Table ET_ITEM = outs.getTable("ET_ITEM");//物料凭证明细表
		List<DumpDetail> materialitem = new ArrayList<DumpDetail>();
		for (int i=0;i < ET_ITEM.getNumRows(); i++) {
			ET_ITEM.setRow(i);
			DumpDetail dumpdetail = new DumpDetail();
			dumpdetail.setVoucherId(ET_ITEM.getString("MBLNR"));//物料凭证编号
			dumpdetail.setMjahr(ET_ITEM.getString("MJAHR"));//凭证 年度
			dumpdetail.setZeile(ET_ITEM.getString("ZEILE"));//行项目
			dumpdetail.setMatnr(ET_ITEM.getString("MATNR"));//物料编号
			dumpdetail.setWerks(ET_ITEM.getString("WERKS"));//工厂
			dumpdetail.setLgort(ET_ITEM.getString("LGORT"));//库存地点
			dumpdetail.setCharg(ET_ITEM.getString("CHARG"));//批次
			dumpdetail.setMeins(ET_ITEM.getString("MEINS"));//单位
			dumpdetail.setMaktx(ET_ITEM.getString("MAKTX"));//物料描述
			dumpdetail.setMenge(ET_ITEM.getString("MENGE"));//数量
			materialitem.add(dumpdetail);
		}
		return materialitem;
	}

	@Override
	public List<HashMap<String, String>> findMaterial(String werks,
			String lgort, String matnr,String lgpla) throws IOException {
		List<HashMap<String,String>> arrList = new ArrayList<HashMap<String,String>>();
		super.setProperty("materiallqua");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("S_WERKS", werks);//工厂
		parameter.put("S_LGORT", lgort);//库存地点
		parameter.put("S_MATNR", matnr);//物料
		parameter.put("S_LGPLA", lgpla);//物料
		super.setParameter(parameter);//输入参数
		SAPModel model = execBapi();//执行 并获取返回值
		ParameterList out = model.getOuts();//返回参数
		ParameterList outs = model.getOuttab();//返回表
		/******执行 end******/
		Table ET_ITEM = outs.getTable("GT_LQUA");//仓位库存信息
		for(int i=0;i<ET_ITEM.getNumRows();i++){
			ET_ITEM.setRow(i);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mantd",ET_ITEM.getString("MANTD"));//客户端编号
			map.put("rowno", ET_ITEM.getString("ROWNO"));//存放ID
			map.put("matnr", ET_ITEM.getString("MATNR"));//物料编码
			map.put("charg", ET_ITEM.getString("CHARG"));//批号
			map.put("verme", ET_ITEM.getString("VERME"));//可用库存
			map.put("lqnum", ET_ITEM.getString("LQNUM"));//数量
			map.put("meins", ET_ITEM.getString("MEINS"));//基本单位
			map.put("lgtyp", ET_ITEM.getString("LGTYP"));//仓储类型
			map.put("lgpla", ET_ITEM.getString("LGPLA"));//仓位
			map.put("dwnum", ET_ITEM.getString("DWNUM"));//Quantity in Parallel Unit of Entry
			map.put("lenum", ET_ITEM.getString("LENUM"));//仓储单位编号
			map.put("sequ", ET_ITEM.getString("SEQU"));//整数
			map.put("nlpla", ET_ITEM.getString("NLPLA"));//目的地仓位
			
			arrList.add(map);
		}
		return arrList;
	}
	
	public String saveMaterial(List<HashMap<String,String>> arrList) throws IOException, CustomerException{
		super.setProperty("materiallqua1");//根据配置文件读取到函数名称
		/******输入参数******/
		//HashMap<String,Object> parameter = new HashMap<String,Object>();
		//parameter.put("GM_CODE", "03");//移动类型
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		TableModel GT_LQUA = new TableModel();
		GT_LQUA.setData("GT_LQUA");
		GT_LQUA.setList(arrList);
		tablemodelList.add(GT_LQUA);
		//super.setParameter(parameter);
		super.setTable(tablemodelList);
		/******执行 end******/
		SAPModel model = execBapi();//执行 并获取返回值
		ParameterList out = model.getOuts();//返回参数
		ParameterList outs = model.getOuttab();//返回表
		//Table t_data = outs.getTable("");//列表
		String type = out.getString("E_TYPE");
		String message = out.getString("E_MESSAGE");
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", message);
		}
		return type;
	}
	
	
}
