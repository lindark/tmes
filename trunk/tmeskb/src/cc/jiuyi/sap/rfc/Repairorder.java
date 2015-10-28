package cc.jiuyi.sap.rfc;

import java.util.HashMap;
import java.util.Map;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.SAPUtil;

public class Repairorder {
	
	/**
	 * 同步随工单信息
	 */
	public void syncRepairorder(){
		
		String funcName="Z_TEMS_READ_REPAIRORDER";//函数名称
		Map<String, String> strMap=new HashMap<String, String>();
		/**输入参数***/
		strMap.put("STARTDATE", "20150928");//开始时间
		strMap.put("ENDDATE", "20150930");//结束时间
		/**输入参数 end***/
		
		/**输出**/
		SAPModel model=SAPUtil.OperSAP(strMap, null, null, funcName);//访问SAP获取返回数据
		JCO.ParameterList out=model.getOuts();//返回结构与参数
		JCO.ParameterList outs=model.getOuttab();//返回表
		Table table01 = outs.getTable("ET_ITEM");
		
	}
	
}
