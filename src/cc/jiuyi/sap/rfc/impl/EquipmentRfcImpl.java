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
import cc.jiuyi.util.SAPModel;
@Component
public class EquipmentRfcImpl extends BaserfcServiceImpl implements EquipmentRfc{
	
	@Override
	public List<Equipments> getEquipment() throws IOException{
		super.setProperty("equipment");//获取函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		//parameter.put("IM_EQUNR", equipmentNo);//设备编码
		//parameter.put("IM_EQKTU", equipmentName);//设备名称
		//super.setParameter(parameter);//输入参数
		//super.setStructure(null);
		//super.setTable(null);
		SAPModel model = execBapi(parameter,null,null);//执行 并获取返回值
		/******执行 end******/
		ParameterList outs = model.getOuttab();//返回表
		Table et_item = outs.getTable("ET_ITEM");//返回表
		List<Equipments> list = new ArrayList<Equipments>();
		for (int i = 0; i < et_item.getNumRows(); i++) {
			et_item.setRow(i);
			Equipments e = new Equipments();
			e.setEquipmentNo(et_item.getString("EQUNR"));//设备编码
			e.setEquipmentName(et_item.getString("SHTXT"));//设备名称
			e.setCompanycode(et_item.getString("BUKRS"));//公司代码
			e.setCompany(et_item.getString("BUTXT"));//公司代码描述
			e.setFactorycode(et_item.getString("SWERK"));//工厂
			e.setVersion(et_item.getString("TYPBZ"));//型号
			e.setCostCenter(et_item.getString("KOSTL"));//成本中心
			e.setWorkCenter(et_item.getString("ARBPL"));//工作中心
			e.setFunctionPosition(et_item.getString("TPLNR"));//功能位置
			e.setFunctionPositiondecribe(et_item.getString("PLTXT"));//功能位置描述
			e.setIdentify(et_item.getString("ABCKZ"));//ABC表示
			e.setType(et_item.getString("EQTYP"));//设备种类
			e.setStartday(et_item.getString("DATAB"));//开始日期
			e.setEndday(et_item.getString("DATBI"));//结束日期
			String state = et_item.getString("ISDEL");//是否删除
			if("X".equals(state)){//标记删除了
				e.setIsDel("Y");
			}else{
				e.setIsDel("N");
			}
			list.add(e);
		}
		return list;
	}

}
