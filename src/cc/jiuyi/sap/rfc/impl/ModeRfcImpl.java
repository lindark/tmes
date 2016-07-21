package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;

import cc.jiuyi.entity.DeviceModlue;
import cc.jiuyi.entity.DeviceStep;
import cc.jiuyi.entity.Model;
import cc.jiuyi.sap.rfc.ModeRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
import cc.jiuyi.util.ThinkWayUtil;
@Component
public class ModeRfcImpl extends BaserfcServiceImpl implements ModeRfc{

	@Override
	public String ModelCrt(String iscrtOrchange,Model model, List<DeviceStep> step,
			List<DeviceModlue> module) throws IOException, CustomerException {
		super.setProperty("model");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IS_CREATE", iscrtOrchange);//创建还是修改
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel IT_HEADER_DATA = new TableModel();
		IT_HEADER_DATA.setData("IT_HEADER_DATA");//表名
		String orderno="";
		HashMap<String,Object> header = new HashMap<String,Object>();
		orderno=model.getOrderNo();
		header.put("ORDERID", model.getOrderNo());//订单号
		header.put("EQUIPMENT", model.getEquipments().getEquipmentNo());//设备编号
		header.put("SHORT_TEXT", model.getSHORT_TEXT());//短文本
		header.put("START_DATE",ThinkWayUtil.formatDateByPattern(model.getCreateDate(),"yyyy-MM-dd"));//开始日期
		header.put("FINISH_DATE", ThinkWayUtil.formatDateByPattern(model.getConfirmTime(),"yyyy-MM-dd"));//完成日期
		header.put("ESTIMATED_COSTS", model.getCOST());//成本
		header.put("ORDER_TYPE", model.getORDER_TYPE());//订单类型
		header.put("AUSVN", ThinkWayUtil.formatDateByPattern(model.getCreateDate(),"yyyy-MM-dd"));//故障开始日期yyyy-mm-dd
		header.put("AUZTV", ThinkWayUtil.formatDateByPattern(model.getCreateDate(),"HH:mm:ss"));//故障开始时间 hh:mm:ss
		header.put("AUSBS", ThinkWayUtil.formatDateByPattern(model.getConfirmTime(),"yyyy-MM-dd"));//故障结束日期yyyy-mm-dd
		header.put("AUZTB", ThinkWayUtil.formatDateByPattern(model.getConfirmTime(),"HH:mm:ss"));//故障结束时间 hh:mm:ss
		header.put("EAUSZT","");//停机期间
		header.put("URGRP",model.getURGRP());//原因代码组
		header.put("URCOD", model.getURCOD());//原因代码
		//header.put("URTXT", device.getURTXT());//原因文本
		header.put("QMNAM", model.getFixer().getName());//维修人
		arrList.add(header);
		IT_HEADER_DATA.setList(arrList);
		tablemodelList.add(IT_HEADER_DATA);
		//明细表2
		List<HashMap<String,Object>> arrList1 = new ArrayList<HashMap<String,Object>>();
		TableModel IT_ITEM_GX = new TableModel();
		IT_ITEM_GX.setData("IT_ITEM_GX");
		for(DeviceStep d : step){
			HashMap<String,Object> item_gx = new HashMap<String,Object>();
			item_gx.put("VORNR", d.getVornr());//工序
			item_gx.put("STEUS", d.getSteus());//控制码
			item_gx.put("ARBPL", d.getArbpl());//工作中心
			item_gx.put("DESCRIPTION", d.getDescription());//文本
			item_gx.put("WORK_ACTIVITY", d.getWork_activity());//
			item_gx.put("DURATION_NORMAL", d.getDuration());//
			item_gx.put("WERKS", d.getWerks());//工厂
			item_gx.put("ORDERID", orderno);//
			arrList1.add(item_gx);
		}
		IT_ITEM_GX.setList(arrList1);
		tablemodelList.add(IT_ITEM_GX);
		//明细表3
		List<HashMap<String,Object>> arrList2 = new ArrayList<HashMap<String,Object>>();
		TableModel IT_ITEM_ZJ = new TableModel();
		IT_ITEM_ZJ.setData("IT_ITEM_ZJ");
		for(DeviceModlue dm : module){
			HashMap<String,Object> item_zj = new HashMap<String,Object>();
			item_zj.put("MATERIAL", dm.getMaterial());//物料号
			item_zj.put("MENGE", dm.getMenge());//数量
			item_zj.put("POSTP", dm.getPostp());//项目类型
			item_zj.put("MEINS", dm.getMeins());//基本单位
			item_zj.put("VORNR", dm.getVornr());//工序
			item_zj.put("ORDERID", orderno);//
			arrList2.add(item_zj);
		}
		IT_ITEM_ZJ.setList(arrList2);
		tablemodelList.add(IT_ITEM_ZJ);
		/*******执行******/
		//super.setParameter(parameter);
		//super.setTable(tablemodelList);
		SAPModel mod = execBapi(parameter,null,tablemodelList);//执行 并获取返回值;
		ParameterList out = mod.getOuts();//返回参数
		String type =  out.getString("E_TYPE");//返回类型
		String message = out.getString("E_MESSAGE");//返回消息
		String aufnr = out.getString("EX_AUFNR");//维修单编号
		if(type.equals("E")){//如果是E，抛出自定义异常
			throw new CustomerException("1400001", "维修单创建失败,"+message);
		}
		return aufnr;
	}

}
