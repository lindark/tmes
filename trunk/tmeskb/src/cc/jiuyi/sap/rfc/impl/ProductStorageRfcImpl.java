package cc.jiuyi.sap.rfc.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Repository;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.dao.ProductStorageDao;
import cc.jiuyi.dao.WorkingBillDao;
import cc.jiuyi.entity.ProductStorage;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.ProductStorageRfc;
import cc.jiuyi.service.ProductStorageService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.SAPModel;
@Component
public class ProductStorageRfcImpl  extends BaserfcServiceImpl implements ProductStorageRfc{
	
	@Resource
	private ProductStorageService productstorageservice;
	@Resource
	private ProductStorageDao productstoragedao;
	
	
	public void sysnProductStorage(HashMap<String,Object> parameter) throws IOException, CustomerException{
		super.setProperty("productStorageSync");//根据配置文件读取到函数名称
		SAPModel mode = new SAPModel();
		Client myConnection = null;

			myConnection = getSAPConnection();// 获取SAP链接
			myConnection.connect();// 开启链接
		Repository myRepository = new JCO.Repository("Repository", myConnection); // 名字
		IFunctionTemplate ft = myRepository
				.getFunctionTemplate(getFunctionName());// 从SAP读取函数信息
		Function bapi = ft.getFunction();// 获得函数物件
		ParameterList parameterList = bapi.getImportParameterList();// 获得输入参数
		
		if (parameter != null) {
			Set set = parameter.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				Map.Entry<String, Object> entry = (Map.Entry) it.next();
				parameterList.setValue( entry.getValue(),
						(String) entry.getKey());
			}
		}
		
		myConnection.execute(bapi);
		
		mode.setOuts(bapi);
		mode.setOuttab(bapi);
		//SAPModel mode = execBapi(parameter,null,null);//执行 并获取返回值
		ParameterList outs = mode.getOuttab();//返回表
		Table table01 = outs.getTable("GT_AUFNR");//返回的表名
		
		for(int i=0;i<table01.getNumRows();i++){
//			WorkingBill workingbill = new WorkingBill();
			table01.setRow(i);
			//System.out.println("product storage sap"+table01.getString(1));
			//System.out.println(table01.getString(2));
			//System.out.println("product storage sap menge==>"+table01.getString("MENGE"));
			ProductStorage ps =new ProductStorage();
			//将获得到的数据一行行装入实体bean，利用dao类实现插入操作
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			ps.setMBLNR(table01.getString("MBLNR"));
			try {
				ps.setBudat(table01.getString("BUDAT"));
				ps.setZEILE(table01.getString("ZEILE"));
				ps.setCPUDT(table01.getString("CPUDT"));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			ps.setCPUTM(table01.getString("CPUTM"));
			ps.setMatnr(table01.getString("MATNR"));
			ps.setMaktx(table01.getString("MAKTX"));
			ps.setAufnr(table01.getString("AUFNR"));
			ps.setSGTXT(table01.getString("SGTXT"));
			ps.setWerks(table01.getString("WERKS"));
			ps.setBwart(table01.getString("BWART"));
			ps.setMenge(table01.getDouble("MENGE"));
			ps.setCharg(table01.getString("CHARG"));
			ps.setLgort(table01.getString("LGORT"));
			ps.setWEMNG(table01.getDouble("WEMNG"));
			ps.setZdate(table01.getString("ZDATE"));
			ps.setZtime(table01.getString("ZTIME"));
			//System.out.println("product storage sap BUDAT==>"+ps.getBudat());
			//throw new IOException();
			productstorageservice.savePS(ps);
		}
	}
}
