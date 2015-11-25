package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Factory;
import cc.jiuyi.entity.FactoryUnit;
import cc.jiuyi.entity.Role;
import cc.jiuyi.entity.Team;
import cc.jiuyi.entity.WorkShop;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ArticleService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.FactoryService;
import cc.jiuyi.service.FactoryUnitService;
import cc.jiuyi.service.MemberService;
import cc.jiuyi.service.MessageService;
import cc.jiuyi.service.ProductService;
import cc.jiuyi.service.RoleService;
import cc.jiuyi.service.TeamService;
import cc.jiuyi.service.WorkShopService;
import cc.jiuyi.service.WorkingBillService;
import cc.jiuyi.service.impl.AdminServiceImpl;
import cc.jiuyi.util.ThinkWayUtil;

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
 * 后台Action类 - 部门管理 
 */

@ParentPackage("admin")
public class DepartmentAction extends BaseAdminAction {

	private static final long serialVersionUID = 3142941564892040221L;
	@Resource
	private DepartmentService deptservice;
	@Resource
	private FactoryService factoryservice;
	@Resource
	private WorkShopService workshopservice;
	@Resource
	private FactoryUnitService factoryunitservice;
	@Resource
	private TeamService teamservice;
	private List<Department> list;
	private Department department;
	private String pid;//父节点id
	private String pname;//父节点名称
	private String factoryid;
	private String workshopid;
	private String factoryunitid;
	
	/**
	 * 跳转List 页面
	 * @return
	 */
	public String list(){
		list = deptservice.getAllByHql();
		return "list";
	}
	
	public String browser(){
		list = deptservice.getAllByHql();
		return "browser";
	}
	
	public String listajax(){
		list = deptservice.getAllByHql();
		
		for(int i =0; i < list.size();i++){
			Department department  = (Department)list.get(i);
			department.setAdmin(null);
			department.setChildDept(null);
			department.setParentDept(null);
			list.set(i, department);
		}
		
		JsonConfig jsonConfig=new JsonConfig();   
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);//防止自包含
		JSONArray jsonArray = JSONArray.fromObject(list,jsonConfig);
		System.out.println(jsonArray.toString());
		return ajaxJson(jsonArray.toString());
	}
	
	/**
	 * 添加页面
	 * @return
	 */
	public String add(){
		Department depart = deptservice.get(pid);
		pname = depart.getDeptName();
		return "input";
	}
	/**
	 * 修改页面
	 * @return
	 */
	public String edit(){
		department = deptservice.load(id);//获取的当前节点的属性
		return "input";
	}
	
	
	/**
	 * 获取factory的json集合
	 * @return
	 */
	public String getFactory(){
		
		List<Factory> factoryList = factoryservice.getList("isDel", "N");
		JSONArray json = new JSONArray();
		for(Factory factory : factoryList){
			JSONObject jo = new JSONObject();
			jo.put("name", factory.getFactoryName());
			jo.put("value", factory.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("factory", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 获取workshop的json集合
	 * @return
	 */
	public String getWorkshop(){
		String[] proName={"isDel","factory.id"};
		String[] proValue={"N",factoryid};
		List<WorkShop> workshopList = workshopservice.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(WorkShop workshop : workshopList){
			JSONObject jo = new JSONObject();
			jo.put("name", workshop.getWorkShopName());
			jo.put("value", workshop.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("workshop", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 获取factoryunit的json集合
	 * @return
	 */
	public String getFactoryunit(){
		String[] proName={"isDel","workShop.id"};
		String[] proValue={"N",workshopid};
		List<FactoryUnit> factoryunitList = factoryunitservice.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(FactoryUnit factoryunit : factoryunitList){
			JSONObject jo = new JSONObject();
			jo.put("name", factoryunit.getFactoryUnitName());
			jo.put("value", factoryunit.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("factoryunit", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 获取team的json集合
	 * @return
	 */
	public String getTeam(){
		String[] proName={"isDel","factoryUnit.id"};
		String[] proValue={"N",factoryunitid};
		List<Team> teamList = teamservice.getList(proName, proValue);
		JSONArray json = new JSONArray();
		for(Team team : teamList){
			JSONObject jo = new JSONObject();
			jo.put("name", team.getTeamName());
			jo.put("value", team.getId());
			json.add(jo);
		}
		JSONObject jsonobj = new JSONObject();
		jsonobj.put("team", json);
		return ajaxJson(jsonobj.toString());
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		id = deptservice.save(department);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "部门添加成功");
		jsonMap.put("id", id);//将id 返回
		jsonMap.put("deptName", department.getDeptName());//将name 返回
		jsonMap.put("parentDept", department.getParentDept().getId());//将父节点返回
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String update(){
		if(department.getParentDept().getId().equals(""))//如果传过来的是空字符串，将parentdept赋值成空
			department.setParentDept(null);
		Department persistent = deptservice.load(department.getId());
		BeanUtils.copyProperties(department, persistent, new String[] {"id", "isDel"});
		deptservice.update(persistent);
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "部门修改成功");
		jsonMap.put("id", department.getId());//将id 返回
		jsonMap.put("deptName", department.getDeptName());//将name 返回
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete(){
		ids = id.split(",");
		deptservice.updateisdel(ids, "Y");//删除
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, "部门删除成功");
		return ajaxJson(jsonMap);
	}
	
	/**
	 * 检查部门名称
	 * @return
	 */
	public String CheckDeptName(){
		//暂时不需要使用
		return ajaxText("true");
	}

	public List<Department> getList() {
		return list;
	}

	public void setList(List<Department> list) {
		this.list = list;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getFactoryid() {
		return factoryid;
	}

	public void setFactoryid(String factoryid) {
		this.factoryid = factoryid;
	}

	public String getWorkshopid() {
		return workshopid;
	}

	public void setWorkshopid(String workshopid) {
		this.workshopid = workshopid;
	}

	public String getFactoryunitid() {
		return factoryunitid;
	}

	public void setFactoryunitid(String factoryunitid) {
		this.factoryunitid = factoryunitid;
	}
	
	
	
	
}