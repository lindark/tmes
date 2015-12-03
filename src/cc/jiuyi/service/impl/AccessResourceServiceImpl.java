package cc.jiuyi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springmodules.cache.annotations.CacheFlush;
import org.springmodules.cache.annotations.Cacheable;

import cc.jiuyi.dao.AccessResourceDao;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.HandOverProcess;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.AccessResourceService;

/**
 * Service实现类 - 权限对象
 */

@Service
public class AccessResourceServiceImpl extends BaseServiceImpl<AccessResource, String> implements AccessResourceService {
	
	@Resource
	private AccessResourceDao accessResourcedao;
	
	@Resource
	public void setBaseDao(AccessResourceDao accessresourcedao) {
		super.setBaseDao(accessresourcedao);
	}

	@Override
	public List<AccessResource> findAccessByRoles(List<Role> rolelist) {
		//List<String> roleids = new ArrayList<String>();
		Object[] roleids = new Object[rolelist.size()];
		for(int i=0;i<rolelist.size();i++){
			Role role = rolelist.get(i);
			roleids[i] = role.getId();
		}
		
		return accessResourcedao.findAccessByRoles(roleids);
	}

	@CacheFlush(modelId="flushing")
	public void update(AccessResource accessResource) {
		accessResourcedao.update(accessResource);
	}
	

}