package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Repair;
import cc.jiuyi.entity.RepairPiece;
import cc.jiuyi.entity.Repairin;
import cc.jiuyi.entity.RepairinPiece;
import cc.jiuyi.sap.rfc.RepairInRfc;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
@Component
public class RepairInRfcImpl extends BaserfcServiceImpl implements RepairInRfc
{

	public Repairin repairinCrt(String testrun,Repairin repairin, List<RepairinPiece> list_rp)
			throws IOException, CustomerException
	{
		super.setProperty("repairin");// 根据配置文件读取到函数名称
		/****** 输入参数 ******/
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("IS_COMMIT", testrun);
		/****** 输入表 ******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String, Object>> arrList = new ArrayList<HashMap<String, Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");// 表名

		HashMap<String, Object> item = new HashMap<String, Object>();
		// 物料凭证抬头，需要去重 参考：http://www.2cto.com/kf/201410/341634.html
		item.put("WERKS", repairin.getWERKS());// 工厂 必填
		item.put("BUDAT", repairin.getBUDAT());//过账日期
		item.put("LGORT", repairin.getLGORT());// 库存地点
		item.put("ZTEXT", repairin.getZTEXT());// 抬头文本，选填
		item.put("XUH", repairin.getId());// 序号 必填
		//item.put("MOVE_TYPE", "261");// 移动类型
		item.put("KOSTL", repairin.getCostcenter());// 成本中心
		arrList.add(item);
		//item.put("MOVE_TYPE", "906");// 移动类型
		//arrList.add(item);

		// 去重
		List<HashMap<String, Object>> arr = new ArrayList<HashMap<String, Object>>(
				new HashSet<HashMap<String, Object>>(arrList));
		ET_HEADER.setList(arr);
		List<HashMap<String, Object>> arrList2 = new ArrayList<HashMap<String, Object>>();
		tablemodelList.add(ET_HEADER);
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for (RepairinPiece rp : list_rp)
		{
			HashMap<String, Object> item2 = new HashMap<String, Object>();
			// 物料凭证明细
			item2.put("MATNR", rp.getRpcode());// 物料编码
			item2.put("ZSFSL", rp.getRpcount().toString());// 数量
			item2.put("ITEM_TEXT", rp.getITEM_TEXT());// 项目文本 选填
			item2.put("XUH", rp.getRepairin().getId());// 序号 必填
			item2.put("CHARG", rp.getCHARG());//批次
			item2.put("ORDERID", rp.getRepairin().getWorkingbill().getAufnr());
			arrList2.add(item2);
		}
		ET_ITEM.setList(arrList2);
		tablemodelList.add(ET_ITEM);
		//super.setParameter(parameter);
		//super.setTable(tablemodelList);
		/****** 执行 end ******/
		SAPModel model = execBapi(parameter,null,tablemodelList);// 执行 并获取返回值
		ParameterList outs = model.getOuttab();// 返回表
		Table t_data = outs.getTable("ET_HEADER");// 列表
		t_data.setRow(0);
		Repairin r = new Repairin();
		r.setE_TYPE(t_data.getString("E_TYPE"));// 返回类型：成功S/失败E
		r.setE_MESSAGE(t_data.getString("E_MESSAGE"));// 返回消息
		r.setId(t_data.getString("XUH"));// 序号
		r.setEX_MBLNR(t_data.getString("EX_MBLNR"));// 返回物料凭证
		return r;
	}

	@Override
	public Repairin revokedRepairCrt(String testrun, Repairin repairin,
			List<RepairinPiece> list_rp) throws IOException, CustomerException {
		super.setProperty("repair");// 根据配置文件读取到函数名称
		/****** 输入参数 ******/
		HashMap<String, Object> parameter = new HashMap<String, Object>();
		parameter.put("IS_COMMIT", testrun);
		/****** 输入表 ******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String, Object>> arrList = new ArrayList<HashMap<String, Object>>();
		TableModel ET_HEADER = new TableModel();
		ET_HEADER.setData("ET_HEADER");// 表名

		HashMap<String, Object> item = new HashMap<String, Object>();
		// 物料凭证抬头，需要去重 参考：http://www.2cto.com/kf/201410/341634.html
		item.put("WERKS", repairin.getWERKS());// 工厂 必填
		item.put("BUDAT", repairin.getBUDAT());//过账日期
		item.put("LGORT", repairin.getLGORT());// 库存地点
		item.put("ZTEXT", repairin.getZTEXT());// 抬头文本，选填
		item.put("XUH", repairin.getId());// 序号 必填
		item.put("KOSTL", repairin.getCostcenter());// 成本中心
		arrList.add(item);
		// 去重
		List<HashMap<String, Object>> arr = new ArrayList<HashMap<String, Object>>(
				new HashSet<HashMap<String, Object>>(arrList));
		ET_HEADER.setList(arr);
		List<HashMap<String, Object>> arrList2 = new ArrayList<HashMap<String, Object>>();
		tablemodelList.add(ET_HEADER);
		TableModel ET_ITEM = new TableModel();
		ET_ITEM.setData("ET_ITEM");
		for (RepairinPiece rp : list_rp)
		{
			HashMap<String, Object> item2 = new HashMap<String, Object>();
			// 物料凭证明细
			item2.put("MATNR", rp.getRpcode());// 物料编码
			item2.put("ZSFSL", rp.getRpcount().toString());// 数量
			item2.put("ITEM_TEXT", rp.getITEM_TEXT());// 项目文本 选填
			item2.put("XUH", rp.getRepairin().getId());// 序号 必填
			item2.put("CHARG", rp.getCHARG());//批次
			item2.put("ORDERID", rp.getRepairin().getWorkingbill().getAufnr());
			arrList2.add(item2);
		}
		ET_ITEM.setList(arrList2);
		tablemodelList.add(ET_ITEM);
		//super.setParameter(parameter);
		//super.setTable(tablemodelList);
		/****** 执行 end ******/
		SAPModel model = execBapi(parameter,null,tablemodelList);// 执行 并获取返回值
		ParameterList outs = model.getOuttab();// 返回表
		Table t_data = outs.getTable("ET_HEADER");// 列表
		t_data.setRow(0);
		Repairin r = new Repairin();
		r.setE_TYPE(t_data.getString("E_TYPE"));// 返回类型：成功S/失败E
		r.setE_MESSAGE(t_data.getString("E_MESSAGE"));// 返回消息
		r.setId(t_data.getString("XUH"));// 序号
		r.setEX_MBLNR(t_data.getString("EX_MBLNR"));// 返回物料凭证
		return r;
	}
}
