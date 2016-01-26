package cc.jiuyi.action.admin;

import java.util.ArrayList;
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

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.BeanUtils;
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
import cc.jiuyi.entity.FaultReason;
import cc.jiuyi.entity.Pollingtest;
import cc.jiuyi.entity.Post;
import cc.jiuyi.entity.Products;
import cc.jiuyi.entity.Role;
import cc.jiuyi.entity.Sample;
import cc.jiuyi.entity.Scrap;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.UnitdistributeModel;
import cc.jiuyi.entity.UnitdistributeProduct;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ArticleService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.service.MemberService;
import cc.jiuyi.service.MessageService;
import cc.jiuyi.service.PollingtestService;
import cc.jiuyi.service.PostService;
import cc.jiuyi.service.ProductService;
import cc.jiuyi.service.RoleService;
import cc.jiuyi.service.SampleService;
import cc.jiuyi.service.ScrapService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.UnitdistributeModelService;
import cc.jiuyi.service.UnitdistributeProductService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.util.ThinkWayUtil;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.annotations.InputConfig;
import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;

/**
 * 后台Action类 - 后台管理、管理员 
 */

@ParentPackage("admin")
public class AdminAction extends BaseAdminAction {

	private static final long serialVersionUID = -5383463207248344967L;
	
	public static final String SPRING_SECURITY_LAST_EXCEPTION = "SPRING_SECURITY_LAST_EXCEPTION";// Spring security 最后登录异常Session名称

	private String loginUsername;
	private String parentId; 
	private String unitdistributeModels;//工位
	private String unitdistributeProducts;//工作范围
	
	private Admin admin;
	private String adminDeptName;
	private List<Role> allRoleSystem;
	private List<Role> allRole;
	private List<Role> roleList;
	private List<WorkingBill> workingbillList;
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
	private List<Factory> factoryList; 
	private String productDate;
	private String shift;
	private List<UnitdistributeModel> unitModelList=new ArrayList<UnitdistributeModel>();
	private List<UnitdistributeProduct> unitProductList=new ArrayList<UnitdistributeProduct>();
	
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
	public String index() {
		admin = adminService.getLoginAdmin();
		admin = adminService.get(admin.getId());
		if(admin.getProductDate() != null && admin.getShift() != null){
			workingbillList = workingbillservice.getListWorkingBillByDate(admin);
		}
		return "teamindex";
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

	// 添加
	public String add() {
		Department depart = departmentservice.get(departid);
		postList = postService.getAll();
		departName = depart.getDeptName();
		if(depart.getTeam()!=null){
			String unitCode=depart.getTeam().getFactoryUnit().getFactoryUnitCode();
			unitModelList=unitdistributeModelService.getModelList(unitCode);
			unitProductList=unitdistributeProductService.getProductList(unitCode);
		}
		return INPUT;
	}

	// 编辑
	public String edit() {
		admin = adminService.load(id);
		postList = postService.getAll();
		Department depart = admin.getDepartment();
		if(depart.getTeam()!=null){
			String unitCode=depart.getTeam().getFactoryUnit().getFactoryUnitCode();
			unitModelList=unitdistributeModelService.getModelList(unitCode);
			unitProductList=unitdistributeProductService.getProductList(unitCode);
		}
		return INPUT;
	}
	
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
	public String ajlist(){
		HashMap<String,String> map = new HashMap<String,String>();
		if(pager == null) {
			pager = new Pager();
			pager.setOrderType(OrderType.asc);
			pager.setOrderBy("department.deptName");
		}
		if(pager.is_search()==true && filters != null){//需要查询条件,复杂查询
			if(!filters.equals("")){
				JSONObject filt = JSONObject.fromObject(filters);
				Pager pager1 = new Pager();
				Map<String,Class<jqGridSearchDetailTo>> m = new HashMap<String,Class<jqGridSearchDetailTo>>();
				m.put("rules", jqGridSearchDetailTo.class);
				pager1 = (Pager)JSONObject.toBean(filt,Pager.class,m);
				pager.setRules(pager1.getRules());
				pager.setGroupOp(pager1.getGroupOp());
			}
		}
		
		pager = adminService.findPagerByjqGrid(pager,map,departid);
		List pagerlist = pager.getList();
		for(int i =0; i < pagerlist.size();i++){
			Admin admin  = (Admin)pagerlist.get(i);
			admin.setDepartName(admin.getDepartment().getDeptName());
			if(admin.getPost()!=null){
			  admin.setXpost(admin.getPost().getPostName());
			}		
			pagerlist.set(i, admin);
		}
		pager.setList(pagerlist);
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		jsonConfig.setExcludes(ThinkWayUtil.getExcludeFields(Admin.class));//排除有关联关系的属性字段  
		JSONArray jsonArray = JSONArray.fromObject(pager,jsonConfig);
		System.out.println(jsonArray.get(0).toString());
		return ajaxJson(jsonArray.get(0).toString());
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
			
			if (pager.is_search() == true && Param != null) {// 普通搜索功能
				// 此处处理普通查询结果 Param 是表单提交过来的json 字符串,进行处理。封装到后台执行
				JSONObject obj = JSONObject.fromObject(Param);
				if (obj.get("adminName") != null) {
					System.out.println("obj=" + obj);
					String adminName = obj.getString("adminName").toString();
					map.put("adminName", adminName);
				}
				if (obj.get("adminDeptName") != null) {
					String adminDeptName = obj.getString("adminDeptName").toString();
					map.put("adminDeptName", adminDeptName);
				}
								
			}
			if(departid != null){
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
	public String save() {

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
		admin.setIsDel("N");
		adminService.save(admin);
		
		return ajaxJsonSuccessMessage("保存成功！");
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
	public String update() {
		Admin persistent = adminService.load(id);
		if (roleList == null && roleList.size() == 0) {
			addActionError("请至少选择一个角色!");
			return ERROR;
		}
		Department depart = new Department();
		depart.setId(departid);
		admin.setDepartment(depart);
		
		//工作范围添加
		List<UnitdistributeProduct> productList = new ArrayList<UnitdistributeProduct>();		
		if(persistent.getUnitdistributeProductSet().size()==0){
			if(unitdistributeProducts!=null && !("").equals(unitdistributeProducts)){
				ids=unitdistributeProducts.split(",");
				for(int i=0;i<ids.length;i++){
					UnitdistributeProduct unitpro=unitdistributeProductService.get(ids[i].trim());
					productList.add(unitpro);
				}
				admin.setUnitdistributeProductSet(new HashSet<UnitdistributeProduct>(productList));
			}
			
		}else{
			admin.setUnitdistributeProductSet(persistent.getUnitdistributeProductSet());
		}
				
		//工位添加
		List<UnitdistributeModel> modelList = new ArrayList<UnitdistributeModel>();
		if(persistent.getUnitdistributeModelSet().size()==0){
			if(unitdistributeModels!=null && !("").equals(unitdistributeModels)){
				String[] idss=unitdistributeModels.split(",");
				for(int i=0;i<idss.length;i++){
					UnitdistributeModel unitMod=unitdistributeModelService.get(idss[i].trim());
					modelList.add(unitMod);
				}
				admin.setUnitdistributeModelSet(new HashSet<UnitdistributeModel>(modelList));
			}			

		}else{
			admin.setUnitdistributeModelSet(persistent.getUnitdistributeModelSet());
		}
		
		admin.setRoleSet(new HashSet<Role>(roleList));
		if (StringUtils.isNotEmpty(admin.getPassword())) {
			String passwordMd5 = DigestUtils.md5Hex(admin.getPassword());
			persistent.setPassword(passwordMd5);
		}
		BeanUtils.copyProperties(admin, persistent, new String[] {"id", "createDate", "modifyDate", "username", "password", "isAccountLocked", "isAccountExpired", "isCredentialsExpired", "loginFailureCount", "lockedDate", "loginDate", "loginIp", "authorities","productDate","shift","isDel"});
		adminService.update(persistent);
		return ajaxJsonSuccessMessage("保存成功！");
	}
	
	
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

	public List<Admin> getAdminList(){
		List<Admin> adminList = adminService.getAll();
		return adminList;
	}	

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
	
	
}