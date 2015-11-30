package cc.jiuyi.logic.test;

import javax.annotation.Resource;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cc.jiuyi.util.*;

import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Table;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.common.Key;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.DumpDetail;
import cc.jiuyi.sap.rfc.DumpRfc;
import cc.jiuyi.sap.rfc.Repairorder;
import cc.jiuyi.service.ArticleService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.impl.ArticleServiceImpl;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.junit.Test;
import org.junit.runner.RunWith;


import junit.framework.TestCase;

public class TestSAPUtilService extends BaseTestCase {
	@Resource  
	private DictService dictService;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private DumpRfc dumprfc;
	protected void setUp() {
		
	}
	
	
	@Test
	public void sapTest() throws IOException{
		/*
		 * 在调用方法时若是参数、结构、表为空，那么请传入NULL
		 * 函数名必填
		 * 以下所有map的键都为RFC中对应的字段
		 */
		String funcName="ZFM_BC_01_11";
		Map strMap=new HashMap();
		strMap.put("I_UID", "张春浩");//I_UID作为RFC参数字段名
		Mapping pp=new Mapping();//输入结构对象
		pp.setStrutName("I_STRUCT");//输入结构名
		Map map=new HashMap();//
		map.put("CRDAT", Util.getTodayWithSdt());//CRDAT作为RFC结构字段名
		map.put("VTEXT", "说明");//VTEXT作为SAP结构字段名
		pp.setMap(map);//把要输入SAP的结构数据存入对象Mapping
		List tableList=new ArrayList();//声明输入表数据集
		TableModel table=new TableModel();
		table.setData("I_TABLE1");//输入表1
		List list=new ArrayList();//声明表输入表1要传入的数据列表
		map=new HashMap();//每个表有N列，使用Map对象存储一行数据。
		map.put("BSCHL", "01");
		map.put("KOSTL", "000100000002");
		list.add(map);
		map=new HashMap();//每个表有N列，使用Map对象存储一行数据。
		map.put("BSCHL", "01");
		map.put("KOSTL", "000100000002");
		list.add(map);
		map=new HashMap();//每个表有N列，使用Map对象存储一行数据。
		map.put("BSCHL", "01");
		map.put("KOSTL", "000100000002");
		list.add(map);
		table.setList(list);
		tableList.add(table);
		
		table=new TableModel();
		table.setData("I_TABLE1");//输入表1
		list=new ArrayList();//声明表输入表1要传入的数据列表
		map=new HashMap();//每个表有N列，使用Map对象存储一行数据。
		map.put("BSCHL", "01");
		map.put("KOSTL", "000100000002");
		list.add(map);
		map=new HashMap();//每个表有N列，使用Map对象存储一行数据。
		map.put("BSCHL", "01");
		map.put("KOSTL", "000100000002");
		list.add(map);
		map=new HashMap();//每个表有N列，使用Map对象存储一行数据。
		map.put("BSCHL", "01");
		map.put("KOSTL", "000100000002");
		list.add(map);
		table.setList(list);
		tableList.add(table);
		
		SAPModel model=SAPUtil.OperSAP(strMap, pp, tableList, funcName);//访问SAP获取返回数据
		JCO.ParameterList out=model.getOuts();//返回结构与参数
		JCO.ParameterList outs=model.getOuttab();//返回表
	}
	
	
	@Test
	public void testCeshi(){
		IRepository repository; 
		//定义连接 
		        JCO.Client mConnection ; 
		//建立连接对象        
		        mConnection = 
		        JCO.createClient(   "600",         // SAP client 
		                    "abap1",            // userid 
		                    "123456",           // password 
		                    "zh",                 // language (null for the default language) 
		                    "192.168.29.85",     // application server host name 
		                    "00");                // system number 
 
		        mConnection.connect(); 
		        System.out.println(mConnection.getAttributes()); 
		        mConnection.disconnect(); 
		        
		
	}
	
	
	@Test
	public void testSAP(){
		//Repairorder r = new Repairorder();
		//r.syncRepairorder(workingbillservice);
	}
	@Test
	public void testMaterialDocument() throws IOException, CustomerException{
		List<Object> list=dumprfc.findMaterialDocument("1805", "20150901", "20151001");
		List<Dump> l1=(List) list.get(0);
		for(int i=0;i<l1.size();i++){
			Dump d=l1.get(i);
			System.out.println("物料凭证："+d.getVoucherId());
			System.out.println("凭证日期："+d.getMjahr());
		}
		List<DumpDetail> l2=(List) list.get(1);
		for(int i=0;i<l2.size();i++){
			DumpDetail d=l2.get(i);
			System.out.println("物料凭证："+d.getVoucherId());
			System.out.println("凭证日期："+d.getMjahr());
			System.out.println("物料编码："+d.getMatnr());
			System.out.println("物料描述："+d.getMaktx());
			System.out.println("库存地点："+d.getLgort());
		}
	}
	
}


