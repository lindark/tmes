package cc.jiuyi.action.admin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Role;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.ArticleService;
import cc.jiuyi.service.DepartmentService;
import cc.jiuyi.service.MemberService;
import cc.jiuyi.service.MessageService;
import cc.jiuyi.service.ProductService;
import cc.jiuyi.service.RoleService;
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
	
	private List<Department> list;
	private Department department;
	private String pid;//父节点id
	private String pname;//父节点名称
	
	/**
	 * 跳转List 页面
	 * @return
	 */
	public String list(){
		list = deptservice.getAllByHql();
		return "list";
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
		deptservice.update(department);
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
	
	
	
	
}