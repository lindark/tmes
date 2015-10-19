package cc.jiuyi.util;

import com.sap.mw.jco.JCO;
/*
 * 访问SAP后，返回数据类
 * -----------by ZhangChunhao V1.0--------
 */
public class SAPModel {
	private JCO.ParameterList  outs =null;
	private JCO.ParameterList outtab =null;
	public void setOuts(JCO.Function bapi){
		outs = bapi.getExportParameterList();
	}
	public void setOuttab(JCO.Function bapi){
		outtab = bapi.getTableParameterList();
	}
	public JCO.ParameterList getOuts(){
		return outs;
	}
	public JCO.ParameterList getOuttab(){
		return outtab;
	}
} 
