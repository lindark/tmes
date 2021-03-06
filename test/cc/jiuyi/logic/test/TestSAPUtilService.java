package cc.jiuyi.logic.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Carton;
import cc.jiuyi.entity.DailyWork;
import cc.jiuyi.entity.Device;
import cc.jiuyi.entity.DeviceModlue;
import cc.jiuyi.entity.DeviceStep;
import cc.jiuyi.entity.Dump;
import cc.jiuyi.entity.Equipment;
import cc.jiuyi.entity.Equipments;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Material;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.PickDetail;
import cc.jiuyi.entity.Process;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.ScrapMessage;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sap.rfc.CartonRfc;
import cc.jiuyi.sap.rfc.DailyWorkRfc;
import cc.jiuyi.sap.rfc.DeviceRfc;
import cc.jiuyi.sap.rfc.DumpRfc;
import cc.jiuyi.sap.rfc.EquipmentRfc;
import cc.jiuyi.sap.rfc.HandOverProcessRfc;
import cc.jiuyi.sap.rfc.MatStockRfc;
import cc.jiuyi.sap.rfc.MaterialRfc;
import cc.jiuyi.sap.rfc.MatnrRfc;
import cc.jiuyi.sap.rfc.PickRfc;
import cc.jiuyi.sap.rfc.ProcessRfc;
import cc.jiuyi.sap.rfc.ScrapRfc;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.CustomerException;
import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.SAPUtil;
import cc.jiuyi.util.TableModel;
import cc.jiuyi.util.Util;

import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

public class TestSAPUtilService extends BaseTestCase {
	@Resource  
	private DictService dictService;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private DumpRfc dumprfc;
	@Resource
	private PickRfc pickrfc;
	@Resource
	private MaterialRfc materialrfc;
	@Resource
	private ScrapRfc scraprfc;
	@Resource
	private ProcessRfc processrfc;
	@Resource
	private DailyWorkRfc dailyworkrfc;
	@Resource
	private CartonRfc cartonrfc;
	@Resource
	private HandOverProcessRfc handoverprocess;
	@Resource
	private DeviceRfc devicerfc;
	@Resource
	private MatnrRfc matnrrfc;
	@Resource
	private MatStockRfc matstockrfc;
	@Resource
	private EquipmentRfc eq;
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
	@Test//测试获取物料凭证
	public void testMaterialDocument() throws IOException, CustomerException{
		List<Dump> list=dumprfc.findMaterialDocument("1805", "20150901", "20151001");
		
		for(int i=0;i<list.size();i++){
			Dump d=list.get(i);
			System.out.println("物料凭证："+d.getVoucherId());
			System.out.println("凭证日期："+d.getMjahr());
		}
		
	}
	
	@Test//测试领退料
	public void testCrtMblnr(){
		try{
		
		Pick p = new Pick();
		p.setBudat("2015-11-01");
		p.setLgort("2201");//库存地点
		p.setZtext("测试凭证");//抬头文本
		p.setWerks("1000");//工厂
		p.setMove_type("261");//移动类型
		List<PickDetail> list=new ArrayList<PickDetail>();
		PickDetail pd = new PickDetail();
		pd.setCharg("15091901");//批次
		pd.setMaterialCode("10490284");//物料编码
		//pd.setMeins("PC");//单位
		pd.setOrderid("100116549");//工单
		pd.setPickAmount("5");//数量
		pd.setItem_text("文本");//文本
		list.add(pd);
		String mblnr=pickrfc.MaterialDocumentCrt("X",p, list);
		System.out.println("物料凭证："+mblnr);
	}catch(CustomerException e){
		e.printStackTrace();
		System.out.println(e.getMsgDes());
	}
	catch(Exception e){e.printStackTrace();}
	}
	
	@Test
	public void testGetBom(){
		try{
			List<Material> list=materialrfc.getBomList("30300115", "1000");
			for(int i=0;i<list.size();i++){
				Material m=list.get(i);
				System.out.println(m.getMaterialName());
				System.out.println(m.getMaterialCode());
				//System.out.println(m.getMaterialAmount());
				System.out.println(m.getMaterialUnit());
			}
		}catch(CustomerException e){
			e.printStackTrace();
			System.out.println(e.getMsgDes());
		}
		catch(Exception e){e.printStackTrace();}
	}
	
}


