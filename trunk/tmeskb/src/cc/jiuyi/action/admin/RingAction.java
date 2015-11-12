package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.service.AdminService;
import cc.jiuyi.service.DepartmentService;

@ParentPackage("admin")
public class RingAction extends BaseAdminAction {

	private static final long serialVersionUID = 7323213806344131042L;
	private List<Admin> adminList= new ArrayList<Admin>();
	private List<Department> list;
	
	@Resource
	private AdminService adminService;
	@Resource
	private DepartmentService departmentservice;
	
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 清单
	public String list() {
		list = departmentservice.getAllByHql();
		return "list";
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}

	public List<Department> getList() {
		return list;
	}

	public void setList(List<Department> list) {
		this.list = list;
	}
	
	
}
