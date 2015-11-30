package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.sap.rfc.DumpRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
@Component
public class DumpRfcImpl extends BaserfcServiceImpl implements DumpRfc {
	@Override
	public List<Object> findMaterialDocument(String lgort, String bgdat,
			String eddat) throws IOException, CustomerException {
		List<Object> array = new ArrayList();
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
			materialheader.add(dump);
		}
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
			materialitem.add(dumpdetail);
		}
		array.add(materialheader);
		array.add(materialitem);
		return array;
	}
}
