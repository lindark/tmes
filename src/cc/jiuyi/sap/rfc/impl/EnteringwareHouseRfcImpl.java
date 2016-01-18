package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.EnteringwareHouse;
import cc.jiuyi.sap.rfc.EnteringwareHouseRfc;
import cc.jiuyi.service.DictService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
import cc.jiuyi.util.ThinkWayUtil;
import freemarker.template.utility.DateUtil;

@Component
public class EnteringwareHouseRfcImpl extends BaserfcServiceImpl implements EnteringwareHouseRfc{

	@Resource
	private DictService dictservice;
	@Override
	public List<EnteringwareHouse> WarehousingCrt(String testrun,List<EnteringwareHouse> list)
			throws IOException, CustomerException {
		super.setProperty("pickbatch");//根据配置文件读取到函数名称
		/******输入参数******/
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("GM_CODE", "02");//
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
			item.put("ZSFSL", e.getStorageAmount().toString());//数量
			item.put("XUH",e.getId());//ID
			item.put("ORDERID", e.getWorkingbill().getAufnr());//订单号
			item.put("ORDERID", e.getWorkingbill().getWorkingBillCode().substring(e.getWorkingbill().getWorkingBillCode().length()-2, e.getWorkingbill().getWorkingBillCode().length()));//
			
			/**  modify weitao
			 * 处理 编号问题
			 */
			Calendar a = ThinkWayUtil.getCalendar(e.getWorkingbill().getProductDate());
			Integer year = a.get(Calendar.YEAR);
			Integer mounth = a.get(Calendar.MONTH)+1;//获取月份
			Integer day = a.get(Calendar.DATE);//获取日期
			String yearls = StringUtils.substring(year.toString(), year.toString().length()-2);
			String mounthls="";
			String dayls="";
			if(year <10)
				yearls="0"+year;
			if(mounth <10)
				mounthls = "0"+mounth;
			if(day<10)
				dayls="0"+day;
			String lsh =yearls+mounthls+dayls;//流水号前8位
			//ThinkWayUtil.getDictValueByDictKey(dictservice, "DOCBM", keyValue);
			//TODO 用流水号 跟DICT查出来的流水号前6位进行比较，如果一致 +1，如果不一致，则 重置后面流水号，反响更新dict.此处配合 Quartz 一起工作。
			item.put("CHARG", lsh);//批次
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
