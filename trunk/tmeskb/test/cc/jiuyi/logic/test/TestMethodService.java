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
		obj[0] = "402881862bec2a21012bec2b70510002";
		obj[1] = "402881ed4ef6864e014ef687e48d0002";
		String accobjkey="handover";
		List list = new ArrayList<Role>();
		Role role = new Role();
		role.setId("402881862bec2a21012bec2b70510002");
		list.add(role);
		Role role1 = new Role();
		role1.setId("402881ed4ef6864e014ef687e48d0002");
		list.add(role1);
		//List<AccessObject> objlist = accessobjectservice.getAccessObjectList(accobjkey,list);
		
		System.out.println();
		//List<AccessObject> objlist1 = accessobjectservice.getAccessObjectList(accobjkey,list);
		System.out.println();
	}
	
	@Test
	public void Test2(){
		
		Admin admin = new Admin();
		ThinkWayUtil.getExcludeFields(Admin.class);
	}
	
}


