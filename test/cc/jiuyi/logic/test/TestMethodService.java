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

public class TestMethodService extends BaseTestCase {
	@Resource  
	private DictService dictService;
	
	protected void setUp() {
		
	}
	
	@Test
	public void getDictCountTest() throws IOException{
		Pager pager = new Pager();
		pager.setOrderType(OrderType.asc);
		pager.setOrderBy("orderList");
		pager = dictService.findByPager(pager);
		System.out.println(pager.getList().size());
	}	
	
	
	
}


