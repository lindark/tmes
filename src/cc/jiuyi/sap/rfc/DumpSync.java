package cc.jiuyi.sap.rfc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.jiuyi.entity.Dump;
import cc.jiuyi.service.DumpService;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.SAPUtil;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Table;

public class DumpSync {
	/**
	 * 同步转储单信息
	 */
	public void syncRepairorder(DumpService dumpService) throws Exception {
		List list = new ArrayList<Dump>();
		String funcName = "Z_TEMS_READ_REPAIRORDER";// 函数名称
		Map<String, String> strMap = new HashMap<String, String>();
		/** 输入参数 ***/
		strMap.put("STARTDATE", "20150928");// 开始时间
		strMap.put("ENDDATE", "20150930");// 结束时间
		/** 输入参数 end ***/

		/** 输出 **/
		SAPModel model = SAPUtil.OperSAP(strMap, null, null, funcName);// 访问SAP获取返回数据
		JCO.ParameterList out = model.getOuts();// 返回结构与参数
		JCO.ParameterList outs = model.getOuttab();// 返回表
		Table table01 = outs.getTable("ET_ITEM");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
		for (int i = 0; i < table01.getNumRows(); i++) {
			Dump dump = new Dump();
			table01.setRow(i);
			dump.setVoucherId(table01.getString(""));// 物料凭证号
			dump.setDeliveryDate(sdf.parse(table01.getString("")));//发货日期
			dump.setState(table01.getString(""));// 状态
			//dump.setConfirmUser(table01.getString(""));// 确认人
			list.add(dump);
		}
		dumpService.mergeDump(list);
	}
}
