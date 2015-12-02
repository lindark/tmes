package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Material;
import cc.jiuyi.sap.rfc.MaterialRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
@Component
public class MaterialRfcImpl extends BaserfcServiceImpl implements MaterialRfc{

	@Override
	public List<Material> getBomList(String matnr,String werks) throws IOException, CustomerException {
		super.setProperty("bom");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("MTNRV", matnr);//物料编码
		parameter.put("WERKS", werks);//工厂
		super.setParameter(parameter);//输入参数
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList outs = model.getOuttab();//返回表
		Table T_DATA = outs.getTable("T_DATA");//BOM列表
		List<Material> material = new ArrayList<Material>();
		for (int i = 0; i < T_DATA.getNumRows(); i++) {
			T_DATA.setRow(i);
			Material m = new Material();
			m.setMaterialCode(T_DATA.getString("IDNRK"));//物料编码
			m.setMaterialName(T_DATA.getString("OJTXP"));//物料描述
			m.setMaterialUnit(T_DATA.getString("MMEIN"));//单位
			m.setMaterialAmount(Double.parseDouble(T_DATA.getString("MNGKO")));//数量
			m.setProject(T_DATA.getString("POSNR"));//项目
			m.setSpread(Double.parseDouble(T_DATA.getString("WEGXX")));//层
			m.setProjectType(T_DATA.getString("POSTP"));//项目类别
			material.add(m);
		}
		return material;
	}

}
