package cc.jiuyi.action.admin;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.ParentPackage;

import cc.jiuyi.entity.Admin;
import cc.jiuyi.service.AdminService;

@ParentPackage("admin")
public class RingAction extends BaseAdminAction {

	private static final long serialVersionUID = 7323213806344131042L;
	private List<Admin> adminList= new ArrayList<Admin>();
	
	@Resource
	private AdminService adminService;
	// 添加
	public String add() {
		return INPUT;
	}
	
	// 清单
	public String list() {
		pager = adminService.findByPager(pager);
	//	adminList=adminService.getAll();
	//	System.out.println(adminList.size());
		return LIST;
	}

	public List<Admin> getAdminList() {
		return adminList;
	}

	public void setAdminList(List<Admin> adminList) {
		this.adminList = adminList;
	}
	
	
}
