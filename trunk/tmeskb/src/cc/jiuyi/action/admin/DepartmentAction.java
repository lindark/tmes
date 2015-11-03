package cc.jiuyi.action.admin;

import java.util.HashSet;
import java.util.List;

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
	
	/**
	 * 跳转List 页面
	 * @return
	 */
	public String list(){
		list = deptservice.getAll();
		return "list";
	}
	/**
	 * 添加页面
	 * @return
	 */
	public String add(){
		department = deptservice.load(id);//获取的是父节点的属性
		return "input";
	}
	
	/**
	 * 保存
	 * @return
	 */
	public String save(){
		
		return ajaxJsonSuccessMessage("部门添加成功");
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
	
	
	
}