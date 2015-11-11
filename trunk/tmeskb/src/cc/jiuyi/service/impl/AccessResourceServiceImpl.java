package cc.jiuyi.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cc.jiuyi.dao.AccessResourceDao;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.service.AccessObjectService;
import cc.jiuyi.service.AccessResourceService;

/**
 * Service实现类 - 权限对象
 */

@Service
public class AccessResourceServiceImpl extends BaseServiceImpl<AccessResource, String> implements AccessResourceService {
	@Resource
	public void setBaseDao(AccessResourceDao accessresourcedao) {
		super.setBaseDao(accessresourcedao);
	}

}