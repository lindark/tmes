package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.service.AdminService;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Service实现类 - 管理员
 */

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String> implements AdminService {

	@Resource
	private AdminDao adminDao;
	@Resource
	private DepartmentDao departmentdao;

	@Resource
	public void setBaseDao(AdminDao adminDao) {
		super.setBaseDao(adminDao);
	}

	public Admin getLoginAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		Object principal = authentication.getPrincipal();
		if (principal == null || !(principal instanceof Admin)) {
			return null;
		} else {
			return (Admin) principal;
		}
	}

	public Admin loadLoginAdmin() {
		Admin admin = getLoginAdmin();
		if (admin == null) {
			return null;
		} else {
			return adminDao.load(admin.getId());
		}
	}
	
	public boolean isExistByUsername(String username) {
		return adminDao.isExistByUsername(username);
	}
	
	public Admin getAdminByUsername(String username) {
		return adminDao.getAdminByUsername(username);
	}

	@Override
	public Pager findPagerByjqGrid(Pager pager, Map map,String departid) {
		Department department = departmentdao.get(departid);
		List<String> idList= null;
		//if(department.getParentDept() != null){
			idList = new ArrayList<String>();
			List<Department> list = departmentdao.getChildrenById(departid, null);
			list.add(department);
			for(Department dt:list){
				idList.add(dt.getId());
			}
		//}
		return adminDao.findPagerByjqGrid(pager, map,idList);
	}

	@Override
	public Pager getAdminPager(Pager pager, Map map, String departName) {
		// TODO Auto-generated method stub
		return adminDao.getAdminPager(pager,map,departName);
	}

	

}