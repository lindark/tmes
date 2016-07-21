package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.UnitConversion;
import cc.jiuyi.sap.rfc.UnitConversionRfc;
import cc.jiuyi.util.SAPModel;

@Component
public class UnitConversionRfcImpl extends BaserfcServiceImpl implements UnitConversionRfc {

	public List<UnitConversion> findUnitConeversion() throws IOException {
		super.setProperty("unitsync");//根据配置文件读取到函数名称
		SAPModel model = execBapi(null,null,null);//执行 并获取返回值;
		ParameterList outs = model.getOuttab();//返回表
		Table t_data = outs.getTable("IT_ITEM");//列表
		List<UnitConversion> unitConversionList = new ArrayList<UnitConversion>();
		for(int i=0;i<t_data.getNumRows();i++){
			t_data.setRow(i);
			UnitConversion unit = new UnitConversion();
			unit.setMatnr(t_data.getString("MATNR"));//物料号
			unit.setUnitCode(t_data.getString("MEINH"));//单位编码
			unit.setConversationRatio(t_data.getDouble("UMREZ"));//转换比例
			unit.setConvertUnit("PC");//转换后的单位
			unit.setLastconversationRatio(t_data.getDouble("UMREN"));//转换后比例
			unit.setUnitDescription(t_data.getString("MSEHL"));
			unit.setState("1");
			unitConversionList.add(unit);
		}
		
		return unitConversionList;
		
	}

	
}
