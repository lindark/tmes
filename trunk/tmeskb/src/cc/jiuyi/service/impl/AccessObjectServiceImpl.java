package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AccessObjectDao;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.AdminService;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 - 权限对象
 */

@Service
public class AccessObjectServiceImpl extends BaseServiceImpl<AccessObject, String> implements AccessObjectService {
	
	@Resource
	private AccessObjectDao accessobjectdao;
	
	@Resource
	public void setBaseDao(AccessObjectDao accessobjectdao) {
		super.setBaseDao(accessobjectdao);
	}
	
	@Override
	public List<AccessObject> findTypeList(String value) {
		return accessobjectdao.findTypeList(value);
	}

	@Override
	@Cacheable(modelId="caching")
	public AccessObject getAccessObjectList(String accObjKey,List<Role> rolelist){
		Object[] obj = new Object[rolelist.size()];
		for(int i=0;i<rolelist.size();i++){
			Role role = rolelist.get(i);
			obj[i] = role.getId();
		}
		return accessobjectdao.getAccessObjectList(accObjKey,obj);
	}

}