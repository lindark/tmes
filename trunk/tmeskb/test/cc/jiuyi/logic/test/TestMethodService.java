package cc.jiuyi.logic.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.CreditCard;
import cc.jiuyi.entity.Pick;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Role;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.sendmsg.Sendmsg_Service;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.AccessResourceService;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.CreditCardService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.PickService;
import cc.jiuyi.service.ProcessService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.QuartzManagerUtil;
import cc.jiuyi.util.ThinkWayUtil;

public class TestMethodService extends BaseTestCase {
	@Resource  
	private DictService dictService;
	
	@Resource
	private AccessResourceService accessResourceservice;
	@Resource
	private AdminService adminservice;
	@Resource
	private AccessObjectService accessobjectservice;
	@Resource
	private PickService pickservice;
	@Resource
	private WorkingBillService workingbillservice;
	@Resource
	private CreditCardService creditcardservice;
	
	@Resource
	private ProcessService processservice;
	@Resource
	private TeamService teamService;
	
	
	protected void setUp() {
		
	}
	
	@Test
	public void TestgetWorkingBillByTeamid() throws IOException{
		String teamid = "402880f1513d6d2f01513d6f30e30003";
		List<WorkingBill> workingBill_list=new ArrayList<WorkingBill>();
		List<WorkingBill> workingBillList = new ArrayList<WorkingBill>();
		Team team=this.teamService.get(teamid);
		List<Products> product_list=new ArrayList<Products>(team.getFactoryUnit().getProductsSet());
		for(int i=0;i<product_list.size();i++)
		{
			Products products=product_list.get(i);
			//System.out.println(products.getProductsCode());
			workingBillList = workingbillservice.getWorkingBillByProductsCode(products.getProductsCode());  //根据产品code获取对应的随工单								
		}
		for (int i = 0; i < workingBillList.size(); i++) {
			WorkingBill workingBill =workingBillList.get(i);
			workingBill_list.add(workingBill);
		}
		System.out.println(workingBill_list.toString());
			
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
	
	@Test
	public void Test3(){
		
		accessobjectservice.getAccessObjectList("402880f151148a9301511490270a0001");
	}
	
	
	@Test
	public void quazt(){
		
		 try {
			 String job_name = "动态任务调度";
		      //QuartzManagerUtil.removeJob(job_name);  	 
		      //System.out.println("【系统启动】开始(每1秒输出一次)...");  
		     // QuartzManagerUtil.addJob(job_name, WorkingBillJob.class, "0/1 * * * * ?");  
		      
		     // Thread.sleep(5000);  
//		      System.out.println("【修改时间】开始(每2秒输出一次)...");  
//		      QuartzManagerUtil.modifyJobTime(job_name, "10/2 * * * * ?");  
//		      Thread.sleep(6000);  
//		      System.out.println("【移除定时】开始...");  
//		      QuartzManagerUtil.removeJob(job_name);  
//		      System.out.println("【移除定时】成功");  
//		      
//		      System.out.println("【再次添加定时任务】开始(每10秒输出一次)...");  
//		      QuartzManagerUtil.addJob(job_name, WorkingBillJob.class, "*/10 * * * * ?");  
//		      Thread.sleep(60000);  
//		      System.out.println("【移除定时】开始...");  
//		      QuartzManagerUtil.removeJob(job_name);  
//		      System.out.println("【移除定时】成功");

			 
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		
	}
	
	@Test
	public void testPick(){
		String id = "4028841d516bacd601516bada05c0001";
		Pick pick = pickservice.get(id);
		Admin admin = adminservice.getLoginAdmin();
		List<Pick> pickList = new ArrayList<Pick>();
		pickList.add(pick);
		pickservice.saveRepeal(pickList, admin, "3");
		
	}
	
	@Test
	public void testWebService(){//测试webservice
		
	}
	
	
	@Test
	public void testCredit(){
		CreditCard creditcard = new CreditCard();
		creditcard.setDeviceCode("ceshi");
		creditcard.setCardNumber("10007");
		creditcardservice.save(creditcard);
		
	}
	
	@Test
	public void testSendMsg(){
		Sendmsg_Service service=new Sendmsg_Service();
		String phone="18668196276";
		int i=Integer.parseInt(phone.substring(phone.trim().length()-4,phone.trim().length()))*3+5698;

		String message="<?xml version=\"1.0\" encoding=\"UTF-8\"?><infos><info>"+ 
				    "<msg_id><![CDATA[-1]]></msg_id>"+  
				    "<password><![CDATA["+i+"]]></password>"+ 
				    "<src_tele_num><![CDATA[106573064090]]></src_tele_num>"+ 
				    "<dest_tele_num><![CDATA["+phone+"]]></dest_tele_num>"+ 
				    "<msg><![CDATA[你好]]></msg>"+  
				   "</info>"+
				"</infos>";

		String xml=service.getSendmsgHttpPort().sendmsg("nhjx_nb064090", message);
		System.out.println(xml);
		
	}
	
	@Test
	public void ceshi(){
//		List<HashMap> jsonlist = new ArrayList<HashMap>();
//		List<cc.jiuyi.entity.Process> processList = processservice.getAll();
//		for(int i = 0 ; i < processList.size();i++){
//			cc.jiuyi.entity.Process process = processList.get(i);
//			List<Products> productsList = new ArrayList<Products>(process.getProducts());
//			for(int y=0;y<productsList.size();y++){
//				HashMap<String, Object> map = new HashMap<String,Object>();
//				Products products = productsList.get(y);
//				String name = products.getProductsName();
//				String processname = process.getProcessName();
//				map.put("name", name);
//				map.put("processname", processname);
//				jsonlist.add(map);
//			}
//		}
//		
//		
//		for(int i =0;i<jsonlist.size();i++){
//			HashMap<String,Object> map1 = jsonlist.get(i);
//			System.out.println("name:"+map1.get("name")+",processname:"+map1.get("processname"));
//		}
//		
	}
	
}

