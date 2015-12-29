package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Equipments;
import cc.jiuyi.sap.rfc.EquipmentRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
@Component
public class EquipmentRfcImpl extends BaserfcServiceImpl implements EquipmentRfc{

	@Override
	public List<Equipments> getEquipment(String equipmentNo, String equipmentName) throws IOException,CustomerException{
		super.setProperty("equipment");//获取函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IM_EQUNR", equipmentNo);//设备编码
		parameter.put("IM_EQKTU", equipmentName);//设备名称
		super.setParameter(parameter);//输入参数
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList outs = model.getOuttab();//返回表
		Table et_item = outs.getTable("ET_ITEM");//返回表
		List<Equipments> list = new ArrayList<Equipments>();
		for (int i = 0; i < et_item.getNumRows(); i++) {
			et_item.setRow(i);
			Equipments e = new Equipments();
			e.setEquipmentNo(et_item.getString("EQUNR"));//设备编码
			e.setEquipmentName(et_item.getString("EQKTU"));//设备名称
			list.add(e);
		}
		return list;
	}

}
