package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AccessFunctionDao;
import cc.jiuyi.dao.AccessObjectDao;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.DepartmentDao;
import cc.jiuyi.entity.AccessFunction;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Department;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessFunctionService;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.AdminService;

import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.Cacheable;

/**
 * Service实现类 -
 */

@Service
public class AccessFunctionServiceImpl extends BaseServiceImpl<AccessFunction, String> implements AccessFunctionService {
	
	@Resource
	private AccessFunctionDao accessFunctiondao;
	
	@Resource
	public void setBaseDao(AccessFunctionDao accessFunctiondao) {
		super.setBaseDao(accessFunctiondao);
	}
	
	@Cacheable(modelId="caching")
	public AccessFunction get(String[] propertyNames, Object[] propertyValues){
		return accessFunctiondao.get(propertyNames, propertyValues);
	}
	
	@Cacheable(modelId="flushing")
	public void update(AccessFunction entity) {
		accessFunctiondao.update(entity);
	}

	public List<Object[]> getAccessFunctionList(String path,
			List<String> roleList) {
		return accessFunctiondao.getAccessFunctionList(path, roleList);
	}
}