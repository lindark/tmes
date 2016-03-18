package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.TableModel;
import cc.jiuyi.util.ThinkWayUtil;
@Component
public class HandOverProcessRfcImpl extends BaserfcServiceImpl implements HandOverProcessRfc{

	@Resource
	private AdminService adminservice;
	@Override
	public String BatchHandOver(List<HandOverProcess> list,String testrun,String loginid)
			throws IOException,CustomerException {
		Admin admin = adminservice.get(loginid);//获取当前登录身份
		admin = adminservice.load(admin.getId());
		super.setProperty("handover");//根据配置文件读取到函数名称
		/******输入参数******/
		HashMap<String,Object> parameter = new HashMap<String,Object>();
		parameter.put("IS_COMMIT", testrun);//testrun
		/******输入表******/
		List<TableModel> tablemodelList = new ArrayList<TableModel>();
		List<HashMap<String,Object>> arrList = new ArrayList<HashMap<String,Object>>();
		TableModel tablemodel = new TableModel();
		tablemodel.setData("IT_ITEM");//表名
		for(HandOverProcess p : list){
			HashMap<String,Object> item = new HashMap<String,Object>();
			item.put("MATNR", p.getMaterialCode());//物料编码
			if(p.getActualAmount()!=null || p.getUnAmount()!=null){
				Double amounts =  ThinkWayUtil.null2o(p.getActualAmount());
				Double repairamounts = ThinkWayUtil.null2o(p.getUnAmount());
				amounts = amounts + repairamounts;
				item.put("ZSFSL", amounts.toString());//数量
			}else{
				Double amount = ThinkWayUtil.null2o(p.getAmount());
				Double repairamount = ThinkWayUtil.null2o(p.getRepairAmount());
				amount = amount + repairamount;
				item.put("ZSFSL", amount.toString());//数量
			}
			item.put("ORDERID1", p.getBeforworkingbill().getWorkingBillCode());//上班随工单
			item.put("ORDERID2", p.getAfterworkingbill().getWorkingBillCode());//下班随工单
			item.put("XUH", p.getId());
			item.put("WERKS", p.getBeforworkingbill().getWerks());//工厂
			item.put("LGORT", admin.getTeam().getFactoryUnit().getWarehouse());
			arrList.add(item);
		}
		tablemodel.setList(arrList);
		tablemodelList.add(tablemodel);
		/*******执行******/
		super.setTable(tablemodelList);
		super.setParameter(parameter);
		SAPModel model = execBapi();//执行 并获取返回值
		/******执行 end******/
		ParameterList out = model.getOuts();//返回表
		String EX_MBLNR=out.getString("EX_MBLNR");
		String E_TYPE=out.getString("E_TYPE");
		String E_MESSAGE=out.getString("E_MESSAGE");
		if(E_TYPE.equals("E")){
			throw new CustomerException("1400001", "交接失败,"+E_MESSAGE);
		}
		return EX_MBLNR;
	}

}
