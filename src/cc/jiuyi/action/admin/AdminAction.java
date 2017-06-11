package cc.jiuyi.action.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.security.AccountExpiredException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.DisabledException;
import org.springframework.security.LockedException;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.Pager.OrderType;
import cc.jiuyi.bean.jqGridSearchDetailTo;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.Orders;
import cc.jiuyi.entity.Pollingtest;
import cc.jiuyi.entity.Post;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Role;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.Station;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ArticleService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.DictService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.service.MemberService;
import cc.jiuyi.service.MessageService;
import cc.jiuyi.service.OrdersService;
import cc.jiuyi.service.PollingtestService;
import cc.jiuyi.service.PostService;
import cc.jiuyi.service.ProductService;
import cc.jiuyi.service.RoleService;
import cc.jiuyi.service.SampleService;
import cc.jiuyi.service.ScrapService;
import cc.jiuyi.service.StationService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.WorkingInoutService;
import cc.jiuyi.util.ExportExcel;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

/**
 * 后台Action类 - 后台管理、管理员 
 */

@ParentPackage("admin")
public class AdminAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;
	private static Logger log = Logger.getLogger(AdminAction.class);  
	
	public static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";// Spring security 最后登录异常Session名称

	private String[] strlen = {"workingBillCode","materialCode","planCount","afteroddamount","afterunoddamount","recipientsAmount","multiple","totalSingleAmount",
			"afterFraction","scrapNumber","totalRepairAmount","totalRepairinAmount","productDate","shift","aufnr","zjdwyl","dbjyhgs","beforeunoddamount","ychgl",
			"trzsl","cczsl","slcy","jhdcl","matnr","maktx","materialName","totalAmount","isHand","jycl","xFactoryUnit","teamCode","teamName","bulkProductMount",
			"needAttendance","actulAttendance","zhuren","fuzhuren","minister","deputy","workHours","storage","offset"};
	private String[] lavenlen={"随工单编号","子件编码","计划数量","接上班零头数","接上班异常零头数","领用数","倍数","包装数",
			"交下班零头数","组件报废数","成型异常表面维修数","成型维修返回数","生产日期","班次","生产订单号","单位用量","当班检验合格数","交下班异常零头数","一次合格率",
			"投入总数量","产出总数量","数量差异","计划达成率","物料编码","物料描述","组件描述","当班报工数","单据状态","校验差异","单元","班组编码","班组名称","待包装数量",
			"应出勤人数","实出勤人数","主任","副主任","部长","副总","工作小时","入库数","偏差"};

	private String loginUsername;
	private String parentId; 
	private String unitdistributeModels;//工位
	private String unitdistributeProducts;//工作范围
	
	private Admin admin;
	private String adminDeptName;
	private List<Role> allRoleSystem;
	private List<Role> allRole;
	private List<Role> roleList;
	private List<WorkingBill> workingbillList = new ArrayList<WorkingBill>();
	private List<Team> teamList;
	private List<Pollingtest> pollingtestList;
	private List<Scrap> scrapList;
	private List<Sample> sampleList;
	private String departid;
	private String departName;
	private String teamid;
	private Team team;
	private List<Post> postList;//岗位List
	private String cardnumber;//卡号
	private String worknumber;//工号
	private List<Factory> factoryList; 
	private String productDate;
	private String shift;
	private List<UnitdistributeModel> unitModelList=new ArrayList<UnitdistributeModel>();
	private List<UnitdistributeProduct> unitProductList=new ArrayList<UnitdistributeProduct>();
	private List<Department>list_department;//部门
	private List<Admin>list_emp;
	private String deptid;//部门ID
	private String mydeptid;//部门ID
	private String loginid;//登录人id
	private String strDate;
	private String postid;//岗位ID
	private String strStationIds;//工位
	private List<Station>list_station;//工位
	private List<Station>list_station2;//工位
	private String info;
	private boolean fzrFlag=false;
	
	@Resource
	private AdminService adminService;
	@Resource
	private RoleService roleService;
	@Resource
	private WorkingBillService workingbillservice;
	
	@Resource
	private MessageService messageService;  
	@Resource
	private ProductService productService;
	@Resource
	private MemberService memberService;
	@Resource
	private ArticleService articleService;
	@Resource
	private ServletContext servletContext;
	@Resource
	private DepartmentService departmentservice;
	@Resource
	private TeamService teamService;
	@Resource
	private PollingtestService pollingtestService;
	@Resource
	private SampleService sampleService;
	@Resource
	private ScrapService scrapService;
	@Resource
	private PostService postService;
	@Resource
	private FactoryService factoryService;
	@Resource
	private UnitdistributeModelService unitdistributeModelService;
	@Resource
	private UnitdistributeProductService unitdistributeProductService;
	@Resource
	private DictService dictService;
	@Resource
	private StationService stationService;//工位
	@Resource
	private WorkingInoutService workinginoutservice;
	@Resource
	private OrdersService  ordersservice;
	
	// 登录页面
	public String login() {
		String error = getParameter("error");
		if (StringUtils.endsWithIgnoreCase(error, "captcha")) {
			addActionError("验证码错误,请重新输入!");
			return "login";
		}
		Exception springSecurityLastException = (Exception) getSession(SPRING_SECURITY_LAST_EXCEPTION);
		if (springSecurityLastException != null) {
			if (springSecurityLastException instanceof BadCredentialsException) {
				loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
				Admin admin = adminService.get("username", loginUsername);
				if (admin != null) {
					int loginFailureLockCount = getSystemConfig().getLoginFailureLockCount();
					int loginFailureCount = admin.getLoginFailureCount();
					if (getSystemConfig().getIsLoginFailureLock() && loginFailureLockCount - loginFailureCount <= 3) {
						addActionError("若连续" + loginFailureLockCount + "次密码输入错误,您的账号将被锁定!");
					} else {
						addActionError("您的登录名或密码错误!");
					}
				} else {
					addActionError("您的登录名或密码错误!");
				}
			} else if (springSecurityLastException instanceof DisabledException) {
				addActionError("您的账号已被禁用,无法登录!");
			} else if (springSecurityLastException instanceof LockedException) {
				addActionError("您的账号已被锁定,无法登录!");
			} else if (springSecurityLastException instanceof AccountExpiredException) {
				addActionError("您的账号已过期,无法登录!");
			} else {
				addActionError("出现未知错误,无法登录!");
			}
			getSession().remove(SPRING_SECURITY_LAST_EXCEPTION);
		}
		/*
		String k = (String) servletContext.getAttribute("TKWRDP" + "_" + "KEY");
		if (!StringUtils.containsIgnoreCase(k, "tkwrdp")) {
			throw new ExceptionInInitializerError();
		}
		*/
		
		HttpServletRequest request = ServletActionContext.getRequest();
		boolean isMoblie = ThinkWayUtil.JudgeIsMoblie(request);//判断是否moblie
		if(isMoblie)
			return "loginapp";
		else
			return "login";
	}
	
	
	//刷卡登录
	public String creditlogin(){
		Admin admin = adminService.get("cardNumber", cardnumber);
		Map<String, String> map = new HashMap<String,String>();
		map.put("username", admin.getUsername());
		return ajaxJson(map);
	}
	
	// 后台主页面
	public String main() {
		return "main";
	}
	
	// 后台Header
	public String header() {
		return "header";
	}
	
	// 后台菜单
	public String menu() {
		return "menu";
	}
	
	// 后台中间(显示/隐藏菜单)
	public String middle() {
		return "middle";
	}
	
	// 后台首页
	public String index1() {
		//return "index";
		//redirectionUrl = "admin!index1.action";
		//return "teamindex";
		return null;
	}
	
	
	
	// 后台首页
	@SuppressWarnings("finally")
	public String index() {
		try{			
			admin = adminService.getLoginAdmin();
			admin = adminService.get(admin.getId());
			if(admin.getProductDate() != null && admin.getShift() != null)
			{
				workingbillList = workingbillservice.getListWorkingBillByDate(admin);
				Collections.sort(workingbillList, new SortChineseName());  
				for(WorkingBill wb : workingbillList){
					Orders order = ordersservice.get("aufnr",wb.getAufnr());
					if(order!=null){
						wb.setModule(order.getMujuntext());
					} 
				}
				/*info = "";
				HashMap<String,List<String>> mapcheck = new HashMap<String,List<String>>();
				List<String> list1 = new ArrayList<String>();
				List<String> list2 = new ArrayList<String>();
				List<String> list3 = new ArrayList<String>();
				for(WorkingBill wb : workingbillList){
					if("".equals(info)){
						info = wb.getMoudle()==null?"":wb.getMoudle();
					}else{
						info = info + "," + wb.getMoudle()==null?"":wb.getMoudle();
					}
					
					list1.add(wb.getId());
					
					list2.add(wb.getWorkingBillCode());
					list3.add(wb.getAufnr());
				}
				mapcheck.put("wbid", list1);
				mapcheck.put("wbcode", list2);
				mapcheck.put("aufnr", list3);
				List<String[]> wblist = workinginoutservice.sumAmountSY(mapcheck);
				for(WorkingBill wb : workingbillList){
					for(Object[] s : wblist){
						Object wbid = s[0]==null?"":s[0];
						Object slcy = s[1]==null?"0":s[1];
						if(wb.getWorkingBillCode().equals(wbid)){
							if(new BigDecimal(0).compareTo(new BigDecimal(slcy.toString()))!=0){
								wb.setDiffamount(1d);
								break;
							}
						}
					}
					Orders order = ordersservice.get("aufnr",wb.getAufnr());
					if(order!=null){
						wb.setModule(order.getMujuntext());
					} 
				}*/
				//System.out.println("-------------");
			}
		}catch(Exception e){
			e.printStackTrace();
			log.info(e);
		}finally{
			return "teamindex";
		}
	}
	public String indexBF() {

		try{			
			admin = adminService.getLoginAdmin();
			admin = adminService.get(admin.getId());
			
			//判定是否是副主任
			/*fzrFlag=false;
			Set<Role> roles=admin.getRoleSet();
			Iterator<Role> it=roles.iterator();
			while(it.hasNext())
			{
				Role role=it.next();
				if(role.getValue().equalsIgnoreCase("ROLE_CJFZR"))
				{
					fzrFlag=true;
					break;
				}
			}*/
			if(admin.getProductDate() != null && admin.getShift() != null)
			{
				workingbillList = workingbillservice.getListWorkingBillByDate(admin);
				Collections.sort(workingbillList, new SortChineseName());  
				
				info = "";
				
				for(WorkingBill wb : workingbillList){
					HashMap<String,String> mapcheck = new HashMap<String,String>();
					mapcheck.put("wbid", wb.getId());
					JSONArray jsonarray = workinginoutservice.showInoutJsonData(strlen,lavenlen);
					jsonarray = workinginoutservice.findInoutByJsonData3(jsonarray,mapcheck,strlen);
					if("".equals(info)){
						info = wb.getMoudle()==null?"":wb.getMoudle();
					}else{
						info = info + "," + wb.getMoudle()==null?"":wb.getMoudle();
					}
					for(int i=0;i<jsonarray.size();i++){
						JSONObject jsonObject = jsonarray.getJSONObject(i);
						if(new BigDecimal(0).compareTo(new BigDecimal(jsonObject.get("slcy").toString()))!=0){
							wb.setDiffamount(1d);
							break;
						}
					}
					Orders order = ordersservice.get("aufnr",wb.getAufnr());
					if(order!=null){
						wb.setModule(order.getMujuntext());
					}           
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			log.info(e);
		}finally{
			return "teamindex";
		}
		
		
		
		/**weitao 此处处理条形码 begin**/
		//String path = getRequest().getSession().getServletContext().getRealPath("");//获取路径
		//OneBarcodeUtil.createCode("我就测试一下", path);
		/**weitao end**/
	}
	public String findWorkingBill(){
		try {
			if(admin.getProductDate() != null && admin.getShift() != null)
			{
				workingbillList = workingbillservice.getListWorkingBillByDate(admin);
				Collections.sort(workingbillList, new SortChineseName());  
				
				info = "";
				
				for(WorkingBill wb : workingbillList){
					HashMap<String,String> mapcheck = new HashMap<String,String>();
					mapcheck.put("wbid", wb.getId());
					JSONArray jsonarray = workinginoutservice.showInoutJsonData(strlen,lavenlen);
					jsonarray = workinginoutservice.findInoutByJsonData3(jsonarray,mapcheck,strlen);
					if("".equals(info)){
						info = wb.getMoudle()==null?"":wb.getMoudle();
					}else{
						info = info + "," + wb.getMoudle()==null?"":wb.getMoudle();
					}
					for(int i=0;i<jsonarray.size();i++){
						JSONObject jsonObject = jsonarray.getJSONObject(i);
						if(new BigDecimal(0).compareTo(new BigDecimal(jsonObject.get("slcy").toString()))!=0){
							wb.setDiffamount(1d);
							break;
						}
					}
					Orders order = ordersservice.get("aufnr",wb.getAufnr());
					if(order!=null){
						wb.setModule(order.getMujuntext());
					}           
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "wb_load";
	}
	
	public String changeTeam()
	{
		String teamId=getParameter("teamId");
		if(teamId!=null && !"".equals(teamId))
		{
			Team team=teamService.get(teamId);
			if(team!=null)
			{
				Admin admin=adminService.get(loginid);
				admin.setTeam(team);
				adminService.update(admin);
				return ajaxJsonSuccessMessage("更改成功！");
			}
		}
		return ajaxJsonErrorMessage("更改失败！");
	}
	
	
	// 后台质检首页弹出层
	public String teamWorkingBill() {
		if(team==null){
			team = new Team();
		}
		team = teamService.get(teamid);
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		//workingbillList = new ArrayList<WorkingBill>(getTeamById(teamid));
		workingbillList = workingbillservice.getListWorkingBillByDate(admin);
		return "teamworkingbill";
	}
	//	绑定班组
		public String addTeam(){
			loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
			admin = adminService.get("username", loginUsername);
			Set<Team> tmSet = admin.getTeamSet();
			if(tmSet==null){
				tmSet = new HashSet<Team>();
			}
				if("0".equals(teamid)){
					List<Map<String,String>> ListMap = new ArrayList<Map<String,String>>();
					List<Team> tmLists= teamService.getTeamListByWorkAndDel();
					if(tmLists.size()>0){
						if(productDate.equals(admin.getProductDate()) && shift.equals(admin.getShift())){
							for(Team tms : tmSet){
								for(Team tml : tmLists){
									if(tms.getId().equals(tml.getId())){
										tmLists.remove(tml);
										break;
									}
								}
							}
							for(Team tml : tmLists){
								Map<String,String> map = new HashMap<String,String>();
								map.put("tmId", tml.getId());
								map.put("tmName", tml.getTeamName());
								map.put("work", tml.getIsWork());
								map.put("ftuName", tml.getFactoryUnit().getFactoryUnitName());
								map.put("wsName", tml.getFactoryUnit().getWorkShop().getWorkShopName());
								map.put("ftName", tml.getFactoryUnit().getWorkShop().getFactory().getFactoryName());
								map.put("YORN", "Y");
								ListMap.add(map);
								tmSet.add(tml);
							}
						}else{
							tmSet = new  HashSet<Team>();
							for(Team tml : tmLists){
								Map<String,String> map = new HashMap<String,String>();
								map.put("tmId", tml.getId());
								map.put("tmName", tml.getTeamName());
								map.put("work", tml.getIsWork());
								map.put("ftuName", tml.getFactoryUnit().getFactoryUnitName());
								map.put("wsName", tml.getFactoryUnit().getWorkShop().getWorkShopName());
								map.put("ftName", tml.getFactoryUnit().getWorkShop().getFactory().getFactoryName());
								map.put("YORN", "N");
								ListMap.add(map);
								tmSet.add(tml);
							}
						}
						admin.setProductDate(productDate);
						admin.setShift(shift);
						admin.setTeamSet(tmSet);
						adminService.update(admin);
						if(ListMap.size()>0){
							JSONArray jsonArray = JSONArray.fromObject(ListMap);
							return ajaxJson(jsonArray.toString());
						}else{
							return ajaxJsonSuccessMessage("保存成功");
						}
					}else{
						return ajaxJsonErrorMessage("当前没有正在工作的班组");
					}
				}else{
					Team tm = teamService.get(teamid);
					List<Map<String,String>> ListMap = new ArrayList<Map<String,String>>();
					Map<String,String> map = new HashMap<String,String>();
					if(productDate.equals(admin.getProductDate()) && shift.equals(admin.getShift())){
						if(!tmSet.contains(tm)){
							map.put("YORN", "Y");
							ListMap.add(map);
							tmSet.add(tm);
							admin.setProductDate(productDate);
							admin.setTeamSet(tmSet);
							adminService.update(admin);
							JSONArray jsonArray = JSONArray.fromObject(ListMap);
							return ajaxJson(jsonArray.toString());
						}else{
							map.put("YORN", "Y1");
							ListMap.add(map);
							JSONArray jsonArray = JSONArray.fromObject(ListMap);
							return ajaxJson(jsonArray.toString());
						}
					}else{
						tmSet = new  HashSet<Team>();
						tmSet.add(tm);
						map.put("YORN", "N");
						ListMap.add(map);
						admin.setProductDate(productDate);
						admin.setTeamSet(tmSet);
						adminService.update(admin);
						JSONArray jsonArray = JSONArray.fromObject(ListMap);
						return ajaxJson(jsonArray.toString());
					}
					
					
				}
					
				
		}
		//删除绑定班组
		public String deleteTeam(){
			loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
			Admin admin = adminService.get("username", loginUsername);
			//Team tm = teamService.get(id);
			Set<Team> tmSet = admin.getTeamSet();
			if(tmSet!=null){
				for(Team tms : tmSet){
					if(tms.getId().equals(id)){
						tmSet.remove(tms);
						break;
					}
				}
				admin.setTeamSet(tmSet);
				adminService.update(admin);
				return ajaxJsonSuccessMessage("删除成功");
			}else{
				return ajaxJsonErrorMessage("删除失败");
			}
			
		}
	// 后台质检首页
		public String index2() {
			//admin = adminService.getLoginAdmin();
			//admin = adminService.get(admin.getId());
           // teamList=teamService.getTeamListByWork();//获取所有当前正在工作的班组
			loginUsername = ((String) getSession("SPRING_SECURITY_LAST_USERNAME")).toLowerCase();
			admin = adminService.get("username", loginUsername);
			Set<Team> tmSet = admin.getTeamSet();
			Iterator<Team> it = tmSet.iterator();
			while(it.hasNext()){
				Team tm = it.next();
				if(it.hasNext()){
					continue;
				}else{
					if(team==null){
						team = new Team();
					}
					team = tm;
				}
			}
            factoryList = factoryService.getFactoryListByY();
/**         for (int i = 0; i < teamList.size(); i++) {
				Team  team =teamList.get(i);
				System.out.println("================="+team.getId());
			}
		    pollingtestList=pollingtestService.getUncheckList();//获取所有未确认的巡检单
			sampleList=sampleService.getUncheckList();//获取所有未确认的抽检单
			scrapList=scrapService.getUnCheckList();//获取所有未确认的报废单                   **/
			return "testindex";
		}
		
	// 根据班组ID查询所有随工单
	public List<WorkingBill> getTeamById(String teamid) {
		Team team = this.teamService.get(teamid);
		List<Products> product_list = new ArrayList<Products>(team
				.getFactoryUnit().getProductsSet());
		List<WorkingBill> workingBill_list = new ArrayList<WorkingBill>();
		List<WorkingBill> workingBillList = new ArrayList<WorkingBill>();
		if (product_list.size() > 0) {
			for (int i = 0; i < product_list.size(); i++) {
				Products products = product_list.get(i);
				workingBillList = workingbillservice
						.getWorkingBillByProductsCode(products
								.getProductsCode()); // 根据产品code获取对应的随工单
			}
			for (int i = 0; i < workingBillList.size(); i++) {
				WorkingBill workingBill = workingBillList.get(i);
				workingBill_list.add(workingBill);
			}
		}
		return workingBill_list;
	}

	// 后台管理首页
	public String index3() {
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		return "manageindex";
	}

	//出错提示
	public String error() {
		return "error";
	}
	
	//成功提示
	public String success() {
		return "success";
	}
	
	

	// 是否已存在 ajax验证
	public String checkUsername() {
		String username = admin.getUsername();
		if (adminService.isExistByUsername(username)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}
	
	/**
	 * 修改时：验证是否存在
	 * @return
	 */
	public String checkUsername2() {
		Admin a=this.adminService.get(id);
		String username = admin.getUsername();
		if(!a.getUsername().equalsIgnoreCase(username))
		{
			if (adminService.isExistByUsername(username)) {
				return ajaxText("false");
			}
		}
		return ajaxText("true");
	}
	
	// 是否已存在 ajax验证
	public String checkCardNumber() {
		String cardNumber = admin.getCardNumber();
		if (adminService.isExistByCardNumber(cardNumber)) {
			return ajaxText("false");
		} else {
			return ajaxText("true");
		}
	}	

	// 添加
	/*public String add() {
		Department depart = departmentservice.get(departid);
		postList = postService.getAll();
		departName = depart.getDeptName();
		if(depart.getTeam()!=null){
			String unitCode=depart.getTeam().getFactoryUnit().getFactoryUnitCode();
			unitModelList=unitdistributeModelService.getModelList(unitCode);
			unitProductList=unitdistributeProductService.getProductList(unitCode);
		}
		return INPUT;
	}*/
	/**
	 * 添加
	 * @return
	 */
	public String add()
	{
		//this.list_department=this.departmentservice.getAllByHql(id);//查询所有部门
		this.unitModelList=this.unitdistributeModelService.getAllList();//查询所有工作范围
		this.unitProductList=this.unitdistributeProductService.getAllList();//查询所有工位
		//this.postList=postService.getAllList();//查询所有岗位
		//this.list_emp=this.adminService.getAllList();//查询所有在职员工
		return "input";
	}
	
	/**
	 * 编辑前
	 * @return
	 */
	public String edit()
	{
		this.admin=this.adminService.get(id);
		//this.list_department=this.departmentservice.getAllByHql(id);//查询所有部门
		this.unitModelList=this.unitdistributeModelService.getAllList();//查询所有工作范围
		this.unitProductList=this.unitdistributeProductService.getAllList();//查询所有工位
		//this.postList=postService.getAllList();//查询所有岗位
		//this.list_emp=this.adminService.getAllList();//查询所有在职员工
		return "input";
	}

	// 编辑
	/*public String edit() {
		admin = adminService.load(id);
		postList = postService.getAll();
		Department depart = admin.getDepartment();
		if(depart.getTeam()!=null&&depart.getTeam().getFactoryUnit()!=null){
			String unitCode=depart.getTeam().getFactoryUnit().getFactoryUnitCode();
			unitModelList=unitdistributeModelService.getModelList(unitCode);
			unitProductList=unitdistributeProductService.getProductList(unitCode);
		}
		return INPUT;
	}*/
	
	// 绑定生产日期和班次
	public String product() {
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		return "product";
	}

	// 列表
	public String list() {
		//pager = adminService.findByPager(pager);
		return LIST;
	}
	
	
	/**
	 * ajax 列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ajlist(){
		if(pager == null) {
			pager = new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		if(pager.is_search()==true && filters != null&&!"".equals(filters)){//需要查询条件,复杂查询
			JSONObject filt = JSONObject.fromObject(filters);
			Pager pager1 = new Pager();
			Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
			m.put("rules", jqGridSearchDetailTo.class);
			pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
			pager.setRules(pager1.getRules());
			pager.setGroupOp(pager1.getGroupOp());
		}
		
		pager = adminService.findPagerByjqGrid(pager,departid);
		List<Admin> pagerlist = pager.getList();
		List<Admin>newlist=getNewAdminList(pagerlist);
		pager.setList(newlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		//System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 加入假字段
	 * @return
	 */
	public List<Admin>getNewAdminList(List<Admin>oldlist)
	{
		List<Admin>newlist=new ArrayList<Admin>();
		for(int i =0; i < oldlist.size();i++)
		{
			Admin a  =oldlist.get(i);
			//部门编码,部门名称
			if(a.getDepartment()!=null)
			{
				a.setXdeptcode(a.getDepartment().getDeptCode());
				a.setDepartName(a.getDepartment().getDeptName());
			}
			//班组
			if(a.getTeam()!=null)
			{
				a.setXteam(a.getTeam().getTeamName());
			}
			//岗位
			if(a.getPost()!=null)
			{
				a.setXpost(a.getPost().getPostName());
			}
			//直接上级
			if(a.getParentAdmin()!=null)
			{
				a.setXparentAdmin(a.getParentAdmin().getName());
			}
			//是否离职
			if(a.getIsDel()!=null)
			{
				a.setXisJob(ThinkWayUtil.getDictValueByDictKey(dictService, "adminIsJob", a.getIsDel()));
			}
			//模具组号
			List<UnitdistributeProduct>list_up=new ArrayList<UnitdistributeProduct>(a.getUnitdistributeProductSet());
			if(list_up.size()>0)
			{
				String str="";
				for(int j=0;j<list_up.size();j++)
				{
					UnitdistributeProduct up=list_up.get(j);
					if(up.getMaterialName()!=null)
					{
						str+=up.getMaterialName()+",";
					}
				}
				if(str.endsWith(","))
				{
					str=str.substring(0,str.length()-1);
				}
				a.setXworkscope(str);
			}
			//工作范围
			List<UnitdistributeModel>list_um=new ArrayList<UnitdistributeModel>(a.getUnitdistributeModelSet());
			if(list_um.size()>0)
			{
				String str="";
				for(int j=0;j<list_um.size();j++)
				{
					UnitdistributeModel um=list_um.get(j);
					if(um.getStation()!=null)
					{
						str+=um.getStation()+",";
					}
				}
				if(str.endsWith(","))
				{
					str=str.substring(0,str.length()-1);
				}
				a.setXstation(str);
			}
			//是否启用
			if(a.getIsAccountEnabled()!=null)
			{
				if(a.getIsAccountEnabled())
				{
					a.setXisenable("已启用");
				}
				else
				{
					a.setXisenable("未启用");
				}
				//a.setXisenable(ThinkWayUtil.getDictValueByDictKey(dictService, "isEnable", a.getIsAccountEnabled()));
			}
			//创建人
			if(a.getEmpCreater()!=null)
			{
				a.setXempCreater(a.getEmpCreater().getName());
			}
			//性别
			if("nan".equals(a.getSex()))
			{
				a.setXsex("男");
			}
			if("nv".equals(a.getSex()))
			{
				a.setXsex("女");
			}
			//工位
			if(a.getStationids()!=null)
			{
				String []sids=a.getStationids().split(",");
				String str="";
				for(int j=0;j<sids.length;j++)
				{
					Station s=this.stationService.get(sids[j]);
					if(s!=null&&"N".equals(s.getIsDel())&&s.getName()!=null)
					{
						str+=s.getName()+",";
					}
					
				}
				if(str.endsWith(","))
				{
					str=str.substring(0,str.length()-1);
				}
				a.setXgongwei(str);//工位
			}
			if(a.getTeam()!=null){
				a.setXfactoryUnit(a.getTeam().getFactoryUnit()
						.getWorkShop().getFactory().getFactoryName());
				a.setxWorkshop(a.getTeam().getFactoryUnit()
						.getWorkShop().getWorkShopName());
				a.setXfactoryUnit(a.getTeam()
						.getFactoryUnit().getFactoryUnitName());
				a.setXteam(a.getTeam().getTeamName());
			}
			newlist.add(a);
		}
		return newlist;
	}
	/**
	 * 加入假字段2,比1少
	 * @return
	 */
	public List<Admin>getNewAdminList2(List<Admin>oldlist)
	{
		List<Admin>newlist=new ArrayList<Admin>();
		for(int i =0; i < oldlist.size();i++)
		{
			Admin a  =oldlist.get(i);
			//部门名称
			if(a.getDepartment()!=null)
			{
				a.setDepartName(a.getDepartment().getDeptName());
			}
			//岗位
			if(a.getPost()!=null)
			{
			  a.setXpost(a.getPost().getPostName());
			}
			//直接上级
			if(a.getParentAdmin()!=null)
			{
				a.setXparentAdmin(a.getParentAdmin().getName());
			}
			//是否离职
			if(a.getIsDel()!=null)
			{
				a.setXisJob(ThinkWayUtil.getDictValueByDictKey(dictService, "adminIsJob", a.getIsDel()));
			}
			//是否启用
			if(a.getIsAccountEnabled()!=null)
			{
				if(a.getIsAccountEnabled())
				{
					a.setXisenable("已启用");
				}
				else
				{
					a.setXisenable("未启用");
				}
			}
			//班组
			if(a.getTeam()!=null)
			{
				a.setXteam(a.getTeam().getTeamName());
			}
			newlist.add(a);
		}
		return newlist;
	}
	/**
	 * 加入假字段3
	 * @return
	 */
	public List<Admin>getNewAdminList3(List<Admin>oldlist)
	{
		List<Admin>newlist=new ArrayList<Admin>();
		for(int i =0; i < oldlist.size();i++)
		{
			Admin a  =oldlist.get(i);
			//部门名称
			if(a.getDepartment()!=null)
			{
				a.setDepartName(a.getDepartment().getDeptName());
			}
			//班组
			if(a.getTeam()!=null)
			{
				a.setXteam(a.getTeam().getTeamName());
			}
			//是否离职
			if(a.getIsDel()!=null)
			{
				a.setXisJob(ThinkWayUtil.getDictValueByDictKey(dictService, "adminIsJob", a.getIsDel()));
			}
			//是否启用
			if(a.getIsAccountEnabled()!=null)
			{
				if(a.getIsAccountEnabled())
				{
					a.setXisenable("已启用");
				}
				else
				{
					a.setXisenable("未启用");
				}
			}
			//创建人
			if(a.getEmpCreater()!=null)
			{
				a.setXempCreater(a.getEmpCreater().getName());
			}
			//管理角色
			List<Role>list_role=new ArrayList<Role>(a.getRoleSet());
			if(list_role.size()>0)
			{
				String str="";
				for(int j=0;j<list_role.size();j++)
				{
					Role r=list_role.get(j);
					str+=r.getName()+",";
				}
				if(str.endsWith(","))
				{
					str=str.substring(0, str.length()-1);
				}
				a.setXrole(str);
			}
			newlist.add(a);
		}
		return newlist;
	}
	
	/**
	 * 是否启用
	 * @return
	 */
	public String isenable()
	{
		try
		{
			String str="<select>";
			str+="<option value=''>---请选择---</option>";
			str+="<option value='true'>已启用</option>";
			str+="<option value='false'>未启用</option>";
			str+="</select>";
			return ajaxHtml(str);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ajaxHtml("<select><option></option></select>");
		}
	}
	// 删除
	public String delete() {
		ids = id.split(",");
		adminService.delete(ids);
		return ajaxJsonSuccessMessage("删除成功！");
	}

	// ajax列表
		public String ajlist1() {
			HashMap<String, String> map = new HashMap<String, String>();
			
			if (pager.getOrderBy().equals("")) {
				pager.setOrderType(OrderType.desc);
				pager.setOrderBy("modifyDate");
			}
			if(pager.is_search()==true && filters != null){//需要查询条件
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map m = new HashMap();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
			
			boolean isName = false;
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("adminName") != null) {
					//System.out.println("obj=" + obj);
					String adminName = obj.getString("adminName").toString();
					map.put("adminName", adminName);
					isName = true;
				}
				if (obj.get("adminDeptName") != null) {
					String adminDeptName = obj.getString("adminDeptName").toString();
					map.put("adminDeptName", adminDeptName);
				}
								
			}
			if(departid != null && isName ==false){
				map.put("departid", departid);
			}
			
			pager = adminService.getAdminPager(pager,map);
			List pagerlist = pager.getList();
			for (int i = 0; i < pagerlist.size(); i++) {
				Admin admin = (Admin) pagerlist.get(i);				
				admin.setDepartName(admin.getDepartment().getDeptName());
				/*admin.setRoleSet(null);
				admin.setAuthorities(null);
				admin.setDepartment(null);
				admin.setAbnormalSet(null);
				admin.setCartonConfirmUser(null);
				admin.setDailyWorkConfirmUser(null);
				admin.setEnteringwareHouseConfirmUser(null);
				admin.setRepairinConfirmUser(null);*/
				pagerlist.set(i, admin);
				//pagerlist.add(admin);
			}
			
			pager.setList(pagerlist);
			JsonConfig jsonConfig=new JsonConfig(); 
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段  
			JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
			return ajaxJson(jsonArray.get(0).toString());
		}
	
	
	// 保存
//	@Validations(
//		requiredStrings = {
//			@RequiredStringValidator(fieldName = "admin.username", message = "登录名不允许为空!"),
//			@RequiredStringValidator(fieldName = "admin.password", message = "密码不允许为空!"),
////			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
//		},
//		requiredFields = {
//			@RequiredFieldValidator(fieldName = "admin.isAccountEnabled", message = "是否启用不允许为空!")
//		},
//		stringLengthFields = {
//			@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "登录名长度必须在${minLength}到${maxLength}之间!"),
//			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!")
//		},
//		emails = {
////			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
//		},
//		regexFields = {
//			@RegexFieldValidator(fieldName = "admin.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "登录名只允许包含中文、英文、数字和下划线!") 
//		}
//	)
//	@InputConfig(resultName = "error")
	/*public String save() {
		String cardNumber = admin.getCardNumber();
		boolean b = adminService.isExistByCardNumber(cardNumber);
		if (b) {
			return ajaxJsonErrorMessage("该卡号已存在，请重新输入！");
		}
		//工作范围添加
		List<UnitdistributeProduct> productList = new ArrayList<UnitdistributeProduct>();
		if(unitdistributeProducts!=null && !("").equals(unitdistributeProducts)){
			ids=unitdistributeProducts.split(",");
			for(int i=0;i<ids.length;i++){
				UnitdistributeProduct unitpro=unitdistributeProductService.get(ids[i].trim());
				productList.add(unitpro);
			}
			admin.setUnitdistributeProductSet(new HashSet<UnitdistributeProduct>(productList));

		}else{
			admin.setUnitdistributeProductSet(null);
		}
		
		//工位添加
		List<UnitdistributeModel> modelList = new ArrayList<UnitdistributeModel>();
		if(unitdistributeModels!=null && !("").equals(unitdistributeModels)){
			String[] id=unitdistributeModels.split(",");
			for(int i=0;i<id.length;i++){
				UnitdistributeModel unitMod=unitdistributeModelService.get(id[i].trim());
				modelList.add(unitMod);
			}
			admin.setUnitdistributeModelSet(new HashSet<UnitdistributeModel>(modelList));

		}else{
			admin.setUnitdistributeModelSet(null);
		}

		if (roleList == null || roleList.size() == 0) {
			return ERROR;
		}
		
		admin.setUsername(admin.getUsername().toLowerCase());
		admin.setLoginFailureCount(0);
		admin.setIsAccountLocked(false);
		admin.setIsAccountExpired(false);
		admin.setIsCredentialsExpired(false);
		admin.setRoleSet(new HashSet<Role>(roleList));
		String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
		admin.setPassword(passwordMd5);
		//admin.setIsDel("N");
		adminService.save(admin);
		
		return ajaxJsonSuccessMessage("保存成功！");
	}*/
	public String save()
	{
		String cardNumber = admin.getCardNumber();
		boolean b = adminService.isExistByCardNumber(cardNumber);
		if (b) {
			return ajaxJsonErrorMessage("该卡号已存在,请重新输入!");
		}
		if (roleList == null || roleList.size() == 0) {
			return ajaxJsonErrorMessage("管理角色不能为空!");
		}
		try
		{
			this.adminService.saveInfo(admin,unitdistributeProducts,unitdistributeModels,roleList,loginid,null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题!");
		}
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}
	
	/**
	 * 修改保存
	 * @return
	 */
	public String update()
	{
		try
		{
			Admin persistent = adminService.load(id);	
			
			String cardNumber = persistent.getCardNumber();//对卡号进行校验
			if(!cardNumber.equals(admin.getCardNumber())){
				boolean b = adminService.isExistByCardNumber(admin.getCardNumber());
				if (b) {
					return ajaxJsonErrorMessage("该卡号已存在,请重新输入！");
				}
			}
			if (roleList == null && roleList.size() == 0) {
				return ajaxJsonErrorMessage("管理角色不能为空!");
			}
			this.adminService.updateInfo(admin,unitdistributeProducts,unitdistributeModels,roleList);//修改员工信息
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ajaxJsonErrorMessage("系统出现问题!");
		}
		return this.ajaxJsonSuccessMessage("您的操作已成功!");
	}

	// 更新
//	@Validations(
//		requiredStrings = {
//			@RequiredStringValidator(fieldName = "admin.username", message = "登录名不允许为空!"),
//			@RequiredStringValidator(fieldName = "admin.email", message = "E-mail不允许为空!")
//		},
//		requiredFields = {
//			@RequiredFieldValidator(fieldName = "admin.isAccountEnabled", message = "是否启用不允许为空!")
//		},
//		stringLengthFields = {
//			@StringLengthFieldValidator(fieldName = "admin.username", minLength = "2", maxLength = "20", message = "登录名长度必须在${minLength}到${maxLength}之间!"),
//			@StringLengthFieldValidator(fieldName = "admin.password", minLength = "4", maxLength = "20", message = "密码长度必须在${minLength}到${maxLength}之间!") },
//		emails = {
//			@EmailValidator(fieldName = "admin.email", message = "E-mail格式错误!")
//		},
//		regexFields = {
//			@RegexFieldValidator(fieldName = "admin.username", expression = "^[0-9a-z_A-Z\u4e00-\u9fa5]+$", message = "登录名只允许包含中文、英文、数字和下划线!") 
//		}
//	)
//	@InputConfig(resultName = "error")
	/*public String update() {
		Admin persistent = adminService.load(id);	
		
		String cardNumber = persistent.getCardNumber();//对卡号进行校验
		if(!cardNumber.equals(admin.getCardNumber())){
			boolean b = adminService.isExistByCardNumber(admin.getCardNumber());
			if (b) {
				return ajaxJsonErrorMessage("该卡号已存在，请重新输入！");
			}
			
		}
				
		if (roleList == null && roleList.size() == 0) {
			addActionError("请至少选择一个角色!");
			return ERROR;
		}
		Department depart = new Department();
		depart.setId(departid);
		admin.setDepartment(depart);
		
		//工作范围添加
		List<UnitdistributeProduct> productList = new ArrayList<UnitdistributeProduct>();		
		
		if(unitdistributeProducts!=null && !("").equals(unitdistributeProducts)){
			ids=unitdistributeProducts.split(",");
			for(int i=0;i<ids.length;i++){
				UnitdistributeProduct unitpro=unitdistributeProductService.get(ids[i].trim());
				productList.add(unitpro);
			}
			admin.setUnitdistributeProductSet(new HashSet<UnitdistributeProduct>(productList));
		}else{
			admin.setUnitdistributeProductSet(null);
		}
						
		//工位添加
		List<UnitdistributeModel> modelList = new ArrayList<UnitdistributeModel>();
		if(unitdistributeModels!=null && !("").equals(unitdistributeModels)){
			String[] idss=unitdistributeModels.split(",");
			for(int i=0;i<idss.length;i++){
				UnitdistributeModel unitMod=unitdistributeModelService.get(idss[i].trim());
				modelList.add(unitMod);
			}
			admin.setUnitdistributeModelSet(new HashSet<UnitdistributeModel>(modelList));
		}else{
			admin.setUnitdistributeModelSet(null);
		}
						
		admin.setRoleSet(new HashSet<Role>(roleList));
		if (StringUtils.isNotEmpty(admin.getPassword())) {
			String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
			persistent.setPassword(passwordMd5);
		}
		BeanUtils.copyProperties(admin, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "isAccountLocked", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate", "loginDate", "loginIp", "authorities","productDate","shift"});
		adminService.update(persistent);
		return ajaxJsonSuccessMessage("保存成功！");
	}*/
	
	@Validations(
			requiredFields = {
					@RequiredFieldValidator(fieldName = "admin.productDate", message = "生产日期不能为空!"),
					@RequiredFieldValidator(fieldName = "admin.shift", message = "班次不能为空!"),
				}
		)
		@InputConfig(resultName = "error")
	public String productupdate(){
		Admin persistent = adminService.load(id);
		//BeanUtils.copyProperties(admin, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password","email","name","isAccountEnabled", "isAccountLocked", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate", "loginDate", "loginIp","isDel","roleSet","department", "authorities","cardNumber"});
		persistent.setProductDate(admin.getProductDate());
		persistent.setShift(admin.getShift());
		adminService.update(persistent);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "操作成功");
		return ajaxJson(jsonMap);
	//	return SUCCESS;
		
	}
	
	/**=======================员工单独维护================================*/
	/**===========statr 用户信息管理=========*/
	/**
	 * 人员管理：进入list页面
	 */
	public String alllistry()
	{
		this.mydeptid=deptid;
		list_department=this.departmentservice.getAllDept();
		return "alllistry";
	}
	
	/**
	 * 跳到员工选择页面
	 * @return
	 */
	public String empchoose(){		
		return "empchoose";	
	}
	
	/**
	 * 查询所有员工
	 * @return
	 */
	public String ajlistemp()
	{
		if(pager == null)
		{
			pager = new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("createDate");
		}
		//需要查询条件,复杂查询
		if(pager.is_search()==true && filters != null&&!"".equals(filters))
		{
			if(!filters.equals(""))
			{
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		HashMap<String, String> map = new HashMap<String, String>();
		boolean isName = false;
		if(pager.is_search() == true && Param != null)
		{
			JSONObject obj = JSONObject.fromObject(Param);
			//姓名
			if (obj.get("name") != null)
			{
				String name = obj.getString("name").toString();
				map.put("name", name);
				isName = true;
			}else{
				//工号
				if (obj.get("workNumber") != null)
				{
					String workNumber = obj.getString("workNumber").toString();
					map.put("workNumber", workNumber);
				}
				//部门
				if (obj.get("dept") != null)
				{
					String dept = obj.getString("dept").toString();
					map.put("dept", dept);
				}
				//是否离职
				if (obj.get("islizhi") != null)
				{
					String islizhi = obj.getString("islizhi").toString();
					map.put("islizhi", islizhi);
				}
			}
		}
		pager = adminService.getAllEmp(pager,map,deptid,1,isName);
		@SuppressWarnings("unchecked")
		List<Admin> pagerlist = pager.getList();
		List<Admin> newlist=getNewAdminList(pagerlist);
//		try {
//			for (int i = 0; i < pagerlist.size(); i++) {
//				Admin theAdmin = pagerlist.get(i);
//				if(theAdmin.getTeam()!=null){
//					theAdmin.setXfactoryUnit(theAdmin.getTeam().getFactoryUnit()
//							.getWorkShop().getFactory().getFactoryName());
//					theAdmin.setxWorkshop(theAdmin.getTeam().getFactoryUnit()
//							.getWorkShop().getWorkShopName());
//					theAdmin.setXfactoryUnit(theAdmin.getTeam()
//							.getFactoryUnit().getFactoryUnitName());
//					theAdmin.setXteam(theAdmin.getTeam().getTeamName());
//				}
//				newlist.add(theAdmin);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		pager.setList(newlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 添加前
	 */
	public String addry()
	{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		this.strDate=sdf.format(new Date());
		//this.unitModelList=this.unitdistributeModelService.getAllList();//查询所有工作范围
		this.unitProductList=this.unitdistributeProductService.getAllList();//查询所有工位
		return "inputry";
	}
	
	/**
	 * 新增保存
	 */
	public String saveempry()
	{
		try
		{
			String str=this.adminService.getChecknum(admin.getWorkNumber(),admin.getCardNumber(),null);
			if("wc".equals(str))
			{
				addActionError("工号和卡号已存在!");
				return ERROR;
			}
			else if("w".equals(str))
			{
				addActionError("工号已存在!");
				return ERROR;
			}
			else if("c".equals(str))
			{
				addActionError("卡号已存在!");
				return ERROR;
			}
			this.adminService.saveInfo(admin,unitdistributeProducts,unitdistributeModels,null,loginid,strStationIds);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			addActionError("系统出现问题!");
			return ERROR;
		}
		this.redirectionUrl="admin!alllistry.action";
		return SUCCESS;
	}
	
	/**
	 * 修改前
	 */
	public String editry()
	{
		this.admin=this.adminService.get(id);
		if(admin.getPost()!=null)
		{
			this.list_station=this.stationService.getStationsByPostid(admin.getPost().getId());//根据岗位id查询所有工位
		}
		this.list_station2=this.stationService.getByIds(admin.getStationids());//根据员工表中的工位id查询工位
		//this.list_department=this.departmentservice.getAllByHql(id);//查询所有部门
		//this.unitModelList=this.unitdistributeModelService.getAllList();//查询所有工作范围
		String fauncode = this.admin.getTeam()==null?"":this.admin.getTeam().getFactoryUnit()==null?"":this.admin.getTeam().getFactoryUnit().getFactoryUnitCode();
		if(fauncode!=null && !"".equals(fauncode)){
			this.unitModelList = unitdistributeModelService.getModelList(fauncode);
		}
		this.unitProductList=this.unitdistributeProductService.getAllList();//查询所有工位
		//this.postList=postService.getAllList();//查询所有岗位
		//this.list_emp=this.adminService.getAllList();//查询所有在职员工
		return "inputry";
	}
	
	/**
	 * 修改保存
	 */
	public String updateempry()
	{
		try
		{
			this.adminService.updateEmpRy(admin,unitdistributeProducts,unitdistributeModels,strStationIds);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			addActionError("保存失败!");
			return ERROR;
		}
		this.redirectionUrl="admin!alllistry.action?deptid="+deptid;
		return SUCCESS;
	}
	
	public String beforegetemp()
	{
		return "alert";
	}
	
	public String addzrr()
	{
		return "alertzrr";
	}
	
	/**
	 * 获取未离职的,已启用的员工
	 * 1.部门添加时获取部门负责人
	 */
	public String getemp()
	{
		try
		{
			HashMap<String ,String>map=new HashMap<String,String>();
			if(pager==null)
			{
				pager=new Pager();
			}
			if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
			{
				pager.setOrderType(OrderType.desc);//倒序
				pager.setOrderBy("createDate");//以创建日期排序
			}
			if(pager.is_search()==true&&Param!=null)
			{
				JSONObject obj=JSONObject.fromObject(Param);
				//班组
				if (obj.get("team") != null)
				{
					String team = obj.getString("team").toString();
					map.put("team", team);
				}
				//员工姓名
				if (obj.get("name") != null)
				{
					String name = obj.getString("name").toString();
					map.put("name", name);
				}
				//岗位
				if (obj.get("skill") != null)
				{
					String skill = obj.getString("skill").toString();
					map.put("skill", skill);
				}
			}
			pager = this.adminService.getAllWorkEmp(pager, map);//查询所有未离职的员工
			@SuppressWarnings("unchecked")
			List<Admin>list1=pager.getList();
			List<Admin>list2=getNewAdminList2(list1);
			pager.setList(list2);
			JsonConfig jsonConfig=new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
			jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段 
			JSONArray jsonArray=JSONArray.fromObject(pager,jsonConfig);
			return this.ajaxJson(jsonArray.getString(0).toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 检验工号和卡号是否重复
	 */
	public String checknum()
	{
		String str="";
		try
		{
			str=this.adminService.getChecknum(worknumber,cardnumber,id);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return this.ajaxJsonErrorMessage("error");
		}
		return this.ajaxJsonSuccessMessage(str);
	}
	
	/**
	 * 获取岗位对应的工位
	 */
	public String getstation()
	{
		try
		{
			String str="";
			List<Station>stationlist=this.stationService.getStationsByPostid(postid);//根据岗位ID获取对应的工位
			if(stationlist!=null&&stationlist.size()>0)
			{
				for(int i=0;i<stationlist.size();i++)
				{
					Station s=stationlist.get(i);
					str+="<option value='"+s.getId()+"'>"+s.getCode()+"--"+s.getName()+"</option>";
				}
				return this.ajaxJsonSuccessMessage(str);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return this.ajaxJsonSuccessMessage("");
		}
		return this.ajaxJsonSuccessMessage("");
	}
	
	
	
	/**
	 * 获取岗位对应的工位
	 */
	public String getstationcode()
	{
		try
		{
			String str="";
			List<Station>stationlist=this.stationService.getStationsByPostid(postid);//根据岗位ID获取对应的工位
			if(stationlist!=null&&stationlist.size()>0)
			{
				for(int i=0;i<stationlist.size();i++)
				{
					Station s=stationlist.get(i);
					str+="<option value='"+s.getCode()+"'>"+s.getName()+"</option>";
				}
				return this.ajaxJsonSuccessMessage(str);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return this.ajaxJsonSuccessMessage("");
		}
		return this.ajaxJsonSuccessMessage("");
	}
	
	
	
	/**
	 * 假删除
	 */
	public String deleteempry()
	{
		this.adminService.updateToDel(id);
		this.redirectionUrl="admin!alllistry.action";
		return SUCCESS;
	}
	
	/**
	 * 导出excel
	 */
	public String outexcelry()
	{
		try
		{
			List<Admin>list1=this.adminService.getAllByConditions(admin);//根据条件查询
			List<Admin>list2=getNewAdminList(list1);
			List<String> header = new ArrayList<String>();
			header.add("部门编码");header.add("部门名称");header.add("班组");
			header.add("工号");header.add("卡号");header.add("姓名");
			header.add("性别");header.add("直接上级");header.add("岗位");
			header.add("工位");header.add("模具组号");header.add("工作范围");
			header.add("身份证号");header.add("联系电话");header.add("E-mail");
			header.add("入职日期");header.add("亲属关系");header.add("操作等级工");
			header.add("最高学历");header.add("当前状态");header.add("是否离职");
			header.add("创建日期");header.add("创建人");
			List<Object[]> body = new ArrayList<Object[]>();
			for(int i=0;i<list2.size();i++)
			{
				Admin a=list2.get(i);
				Object[] str = new Object[header.size()];
				str[0]=a.getXdeptcode();
				str[1]=a.getDepartName();
				str[2]=a.getXteam();
				str[3]=a.getWorkNumber();
				str[4]=a.getCardNumber();
				str[5]=a.getName();
				str[6]=a.getXsex();
				str[7]=a.getXparentAdmin();
				str[8]=a.getXpost();
				str[9]=a.getXgongwei();
				str[10]=a.getXworkscope();
				str[11]=a.getXstation();
				str[12]=a.getIdentityCard();
				str[13]=a.getPhoneNo();
				str[14]=a.getEmail();
				str[15]=a.getStartWorkDate();
				str[16]=a.getRelationShip();
				str[17]=a.getWorkerGrade();
				str[18]=a.getEducation();
				str[19]=a.getNowState();
				str[20]=a.getXisJob();
				str[21]=a.getCreateDate();
				str[22]=a.getXempCreater();
				body.add(str);
			}
			/***Excel 下载****/
			String fileName="人员信息"+".xls";
			setResponseExcel(fileName);
			ExportExcel.exportExcel("人员信息", header, body, getResponse().getOutputStream());
			getResponse().getOutputStream().flush();
			getResponse().getOutputStream().close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**===========end 用户信息管理===========*/
	/**===========statr 用户权限管理=========*/
	/**
	 * 进入list页面
	 */
	public String alllistqx()
	{
		list_department=this.departmentservice.getAllDept();
		return "alllistqx";
	}
	
	/**
	 * 查询所有员工,已维护过的
	 * @return
	 */
	public String ajlistempqx()
	{
		if(pager == null)
		{
			pager = new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		//需要查询条件,复杂查询
		if(pager.is_search()==true && filters != null&&!"".equals(filters))
		{
			if(!filters.equals(""))
			{
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		pager = adminService.getAllEmp(pager,null,deptid,2);
		@SuppressWarnings("unchecked")
		List<Admin> pagerlist = pager.getList();
		List<Admin>newlist=this.getNewAdminList3(pagerlist);
		pager.setList(newlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 人员权限维护:进入页面
	 */
	public String addqx()
	{
		return "inputqx";
	}
	
	/**
	 * 人员权限维护:保存
	 */
	public String saveqx()
	{
		try
		{
			if(tocheckusername(admin.getId(),admin.getUsername()))
			{
				this.adminService.updateEmpQx(admin,roleList,loginid);
			}
			else
			{
				addActionError("用户名已存在!");
				return ERROR;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			addActionError("系统出现异常!");
			return ERROR;
		}
		this.redirectionUrl="admin!alllistqx.action";
		return SUCCESS;
	}
	
	/**
	 * 人员权限维护:进入编辑页面
	 * @return
	 */
	public String editqx()
	{
		this.admin=this.adminService.get(id);
		return "inputqx";
	}
	
	/**
	 * 进入未维护过的员工的页面
	 */
	public String beforegetempqxn()
	{
		return "alertqxn";
	}
	
	/**
	 * 查询所有员工,未维护过的
	 * @return
	 */
	public String ajlistempqxn()
	{
		if(pager == null)
		{
			pager = new Pager();
		}
		if(pager.getOrderBy()==null||"".equals(pager.getOrderBy()))
		{
			pager.setOrderType(OrderType.desc);
			pager.setOrderBy("modifyDate");
		}
		//需要查询条件,复杂查询
		/*if(pager.is_search()==true && filters != null&&!"".equals(filters))
		{
			if(!filters.equals(""))
			{
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}*/
		HashMap<String, String> map = new HashMap<String, String>();
		if(pager.is_search() == true && Param != null)
		{
			JSONObject obj = JSONObject.fromObject(Param);
			//工号
			if (obj.get("workNumber") != null)
			{
				String workNumber = obj.getString("workNumber").toString();
				map.put("workNumber", workNumber);
			}
			//姓名
			if (obj.get("name") != null)
			{
				String name = obj.getString("name").toString();
				map.put("name", name);
			}
			//部门
			if (obj.get("dept") != null)
			{
				String dept = obj.getString("dept").toString();
				map.put("dept", dept);
			}
			if (obj.get("team") != null)
			{
				String team = obj.getString("team").toString();
				map.put("team", team);
			}
		}
		pager = adminService.getAllEmp(pager,map,deptid,3);
		@SuppressWarnings("unchecked")
		List<Admin> pagerlist = pager.getList();
		List<Admin>newlist=this.getNewAdminList3(pagerlist);
		pager.setList(newlist);
		JsonConfig jsonConfig=new JsonConfig();
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		return ajaxJson(jsonArray.get(0).toString());
	}
	
	/**
	 * 查询用户名是否已存在
	 */
	public String ckusername()
	{
		if(tocheckusername(admin.getId(),admin.getUsername()))
		{
			return this.ajaxJsonSuccessMessage("success");
		}
		return this.ajaxJsonErrorMessage("error");
	}
	
	/**
	 * 查询用户名是否已存在
	 */
	public boolean tocheckusername(String xid,String username)
	{
		Admin a=this.adminService.getByUsername(username);
		if(a!=null)
		{
			if(xid!=null&&xid.equals(a.getId()))
			{
				return true;
			}
			return false;
		}
		return true;
	}
	
	/**===========end 用户权限管理===========*/
	
	/**===================================================================*/
	/*
	// 获取未批准订单数
	public Long getUnprocessedOrderCount() {
		return orderService.getUnprocessedOrderCount();
	}
	
	// 获取已支付未发货订单数
	public Long getPaidUnshippedOrderCount() {
		return orderService.getPaidUnshippedOrderCount();
	}
	*/
	
	// 获取未读消息数
	public Long getUnreadMessageCount() {
		return messageService.getUnreadMessageCount();
	}
	
	// 获取商品库存报警数
	public Long getStoreAlertCount() {
		return productService.getStoreAlertCount();
	}
	
	// 获取已上架商品数
	public Long getMarketableProductCount() {
		return productService.getMarketableProductCount();
	}
	
	// 获取已下架商品数
	public Long getUnMarketableProductCount() {
		return productService.getUnMarketableProductCount();
	}
	
	// 获取会员总数
	public Long getMemberTotalCount() {
		return memberService.getTotalCount();
	}
	
	// 获取文章总数
	public Long getArticleTotalCount() {
		return articleService.getTotalCount();
	}
	
	// freemarker静态方法调用
	public TemplateHashModel getStatics() {
		return BeansWrapper.getDefaultInstance().getStaticModels();
	}
	
	public String getLoginUsername() {
		return loginUsername;
	}

	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public List<Role> getAllRoleSystem() {
		return roleService.getList("isSystem", true);
	}

	public void setAllRoleSystem(List<Role> allRoleSystem) {
		this.allRoleSystem = allRoleSystem;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<WorkingBill> getWorkingbillList() {
		return workingbillList;
	}

	public void setWorkingbillList(List<WorkingBill> workingbillList) {
		this.workingbillList = workingbillList;
	}

	public String getDepartid() {
		return departid;
	}

	public void setDepartid(String departid) {
		this.departid = departid;
	}

	public String getDepartName() {
		return departName;
	}

	public void setDepartName(String departName) {
		this.departName = departName;
	}

	public String getAdminDeptName() {
		return adminDeptName;
	}

	public void setAdminDeptName(String adminDeptName) {
		this.adminDeptName = adminDeptName;
	}

	public List<Team> getTeamList() {
		return teamList;
	}

	public void setTeamList(List<Team> teamList) {
		this.teamList = teamList;
	}

	public List<Pollingtest> getPollingtestList() {
		return pollingtestList;
	}

	public void setPollingtestList(List<Pollingtest> pollingtestList) {
		this.pollingtestList = pollingtestList;
	}

	public List<Sample> getSampleList() {
		return sampleList;
	}

	public void setSampleList(List<Sample> sampleList) {
		this.sampleList = sampleList;
	}

	public List<Scrap> getScrapList() {
		return scrapList;
	}
	public void setScrapList(List<Scrap> scrapList) {
		this.scrapList = scrapList;
	}
	public String getTeamid() {
		return teamid;
	}
	public void setTeamid(String teamid) {
		this.teamid = teamid;
	}
	public List<Role> getAllRole() {
		return roleService.getList("isSystem", false);
	}
	public void setAllRole(List<Role> allRole) {
		this.allRole = allRole;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public List<Post> getPostList() {
		return postList;
	}
	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List<Factory> getFactoryList() {
		return factoryList;
	}
	public void setFactoryList(List<Factory> factoryList) {
		this.factoryList = factoryList;
	}
	/*public List<Admin> getAdminList(){
		List<Admin> adminList = adminService.getAll();
		return adminList;
	}*/
	public String getProductDate() {
		return productDate;
	}
	public void setProductDate(String productDate) {
		this.productDate = productDate;
	}
	public String getShift() {
		return shift;
	}
	public void setShift(String shift) {
		this.shift = shift;
	}
	public String getUnitdistributeModels() {
		return unitdistributeModels;
	}
	public void setUnitdistributeModels(String unitdistributeModels) {
		this.unitdistributeModels = unitdistributeModels;
	}
	public String getUnitdistributeProducts() {
		return unitdistributeProducts;
	}
	public void setUnitdistributeProducts(String unitdistributeProducts) {
		this.unitdistributeProducts = unitdistributeProducts;
	}
	public List<UnitdistributeModel> getUnitModelList() {
		return unitModelList;
	}
	public void setUnitModelList(List<UnitdistributeModel> unitModelList) {
		this.unitModelList = unitModelList;
	}
	public List<UnitdistributeProduct> getUnitProductList() {
		return unitProductList;
	}
	public void setUnitProductList(List<UnitdistributeProduct> unitProductList) {
		this.unitProductList = unitProductList;
	}
	public List<Department> getList_department()
	{
		return list_department;
	}
	public void setList_department(List<Department> list_department)
	{
		this.list_department = list_department;
	}
	public List<Admin> getList_emp()
	{
		return list_emp;
	}
	public void setList_emp(List<Admin> list_emp)
	{
		this.list_emp = list_emp;
	}
	public String getDeptid()
	{
		return deptid;
	}
	public void setDeptid(String deptid)
	{
		this.deptid = deptid;
	}
	public String getLoginid()
	{
		return loginid;
	}
	public void setLoginid(String loginid)
	{
		this.loginid = loginid;
	}
	public String getWorknumber()
	{
		return worknumber;
	}
	public void setWorknumber(String worknumber)
	{
		this.worknumber = worknumber;
	}
	public String getPostid()
	{
		return postid;
	}
	public void setPostid(String postid)
	{
		this.postid = postid;
	}
	public List<Station> getList_station()
	{
		return list_station;
	}
	public void setList_station(List<Station> list_station)
	{
		this.list_station = list_station;
	}
	public List<Station> getList_station2()
	{
		return list_station2;
	}
	public void setList_station2(List<Station> list_station2)
	{
		this.list_station2 = list_station2;
	}
	public String getStrDate()
	{
		return strDate;
	}
	public void setStrDate(String strDate)
	{
		this.strDate = strDate;
	}
	public String getStrStationIds()
	{
		return strStationIds;
	}
	public void setStrStationIds(String strStationIds)
	{
		this.strStationIds = strStationIds;
	}
	public String getMydeptid()
	{
		return mydeptid;
	}
	public void setMydeptid(String mydeptid)
	{
		this.mydeptid = mydeptid;
	}


	public String getInfo() {
		return info;
	}


	public void setInfo(String info) {
		this.info = info;
	}
	
	
	
	 public boolean isFzrFlag() {
		return fzrFlag;
	}


	public void setFzrFlag(boolean fzrFlag) {
		this.fzrFlag = fzrFlag;
	}



	class SortChineseName implements Comparator<WorkingBill>{  
	    Collator cmp = Collator.getInstance(java.util.Locale.CHINA);  
	    @Override  
	    public int compare(WorkingBill o1, WorkingBill o2) {  
	        if (cmp.compare(o1.getMaktx(), o2.getMaktx())>0){  
	            return 1;  
	        }else if (cmp.compare(o1.getMaktx(), o2.getMaktx())<0){  
	            return -1;  
	        }  
	        return 0;  
	    }  
	}  
	
}
	