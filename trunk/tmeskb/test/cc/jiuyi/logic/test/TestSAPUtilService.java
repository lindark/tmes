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

import cc.jiuyi.util.Mapping;
import cc.jiuyi.util.SAPModel;
import cc.jiuyi.util.SAPUtil;
import cc.jiuyi.util.TableModel;
import cc.jiuyi.util.Util;
import cc.jiuyi.util.*;
import com.sap.mw.jco.JCO;
import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.common.Key;
import cc.jiuyi.service.ArticleService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.impl.ArticleServiceImpl;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.junit.Test;
import org.junit.runner.RunWith;


import junit.framework.TestCase;

public class TestSAPUtilService extends BaseTestCase {
	@Resource  
	private DictService dictService;
	
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
	
}


