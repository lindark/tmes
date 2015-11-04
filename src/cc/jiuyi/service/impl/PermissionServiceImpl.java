package cc.jiuyi.service.impl;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.PermissionDao;
import cc.jiuyi.entity.Permission;
import cc.jiuyi.service.PermissionService;

/**
 * Service实现类 -权限管理
 * @author Reece
 *
 */

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, String>implements PermissionService{

	@Resource
	private PermissionDao permissionDao;
	
	@Resource
	public void setBaseDao(PermissionDao permissionDao){
		super.setBaseDao(permissionDao);
	}
	
	@Override
	public void delete(String id) {
		Permission permission = permissionDao.load(id);
		this.delete(permission);
	}

	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			this.delete(id);
		}
	}

	@Override
	public List<Permission> getPermissionList() {		
		return permissionDao.getPermissionList();
	}

	@Override
	public Pager getPermissionPager(Pager pager, HashMap<String, String> map) {
		// TODO Auto-generated method stub
		return permissionDao.getPermissionPager(pager, map);
	}

	@Override
	public void updateisdel(String[] ids, String oper) {
		// TODO Auto-generated method stub
		permissionDao.updateisdel(ids, oper);
		
	}

	
}
