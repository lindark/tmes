package cc.jiuyi.logic.test;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import cc.jiuyi.dao.DictDao;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.AccessResourceService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ArticleService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.impl.ArticleServiceImpl;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.hibernate.Hibernate;
import org.junit.Test;
import org.junit.runner.RunWith;


import junit.framework.TestCase;

public class TestMethodService extends BaseTestCase {
	@Resource  
	private DictService dictService;
	
	@Resource
	private AccessResourceService accessResourceservice;
	@Resource
	private AdminService adminservice;
	@Resource
	private AccessObjectService accessobjectservice;
	
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
	
	@Test
	public void Test(){

		//List<AccessResource> accessResourceList = accessResourceservice.getList("accessobjectSet.accObjkey", "jiaojie");//根据path地址判断当前操作的功能'
		//List<AccessResource> accessResource = accessResourceservice.getAccessByKey("jiaojie", "");
		Admin admin = adminservice.get("402880ef50d6ccca0150d6ce75d40001");
		//admin.getRoleSet();
		List<AccessResource> accessList = accessResourceservice.findAccessByRoles(new ArrayList<Role>(admin.getRoleSet()));
		System.out.println();
		
	}
	
	@Test
	public void Test1(){

		Object[] obj = new Object[2];
		obj[0] = "402880f151148a9301511490270a0001";
		obj[1] = "4028841450f07ce80150f07d89b30001";
		String accobjkey="lingliao";
		List<AccessObject> objlist = accessobjectservice.findResourceList(obj,accobjkey);
		
		List<AccessObject> listtemp = new ArrayList<AccessObject>();
		for(int i=0;i<objlist.size();i++){
			AccessObject accessobject = objlist.get(i);
			if(listtemp.contains(accessobject)){
				System.out.println(accessobject);
			}else{
				listtemp.add(accessobject);
			}
		}
		
		System.out.println();
		
	}
	
}


