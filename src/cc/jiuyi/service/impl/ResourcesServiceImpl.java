package cc.jiuyi.service.impl;

import java.util.List;

import javax.persistence.Transient;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.ResourcesDao;
import cc.jiuyi.entity.Resources;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.ResourcesService;
import cc.jiuyi.util.SpringUtil;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.intercept.web.FilterSecurityInterceptor;
import org.springframework.stereotype.Service;

/**
 * Service实现类 - 资源
 */

@Service
public class ResourcesServiceImpl extends BaseServiceImpl<Resources, String> implements ResourcesService {
	
	@javax.annotation.Resource
	ResourcesDao resourceDao;

	@javax.annotation.Resource
	public void setBaseDao(ResourcesDao resourceDao) {
		super.setBaseDao(resourceDao);
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(Resources resource) {
		resourceDao.delete(resource);
		resourceDao.flush();
		flushSpringSecurity();
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(String id) {
		Resources resource = resourceDao.load(id);
		this.delete(resource);
	}

	// 重写方法，删除时刷新SpringSecurity权限信息
	@Override
	public void delete(String[] ids) {
		for (String id : ids) {
			Resources resource = resourceDao.load(id);
			resourceDao.delete(resource);
		}
		resourceDao.flush();
		flushSpringSecurity();
	}

	// 重写方法，保存时刷新SpringSecurity权限信息
	@Override
	public String save(Resources resource) {
		String id = resourceDao.save(resource);
		resourceDao.flush();
		flushSpringSecurity();
		return id;
	}
	
	@Override
	public List<Resources> get(String[] ids) {
		
		return resourceDao.get(ids);
	}

	// 重写方法，更新时刷新SpringSecurity权限信息
	@Override
	public void update(Resources resource) {
		resourceDao.update(resource);
		resourceDao.flush();
		flushSpringSecurity();
	}
	
	// 刷新SpringSecurity权限信息
	private void flushSpringSecurity() {
		try {
			FactoryBean factoryBean = (FactoryBean)SpringUtil.getBean("&adminSecurityDefinitionSource");
			FilterInvocationDefinitionSource filterInvocationDefinitionSource = (FilterInvocationDefinitionSource) factoryBean.getObject();
		    FilterSecurityInterceptor filterSecurityInterceptor = (FilterSecurityInterceptor) SpringUtil.getBean("filterSecurityInterceptor");
		    filterSecurityInterceptor.setObjectDefinitionSource(filterInvocationDefinitionSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer getListByadmin(List<String> roleid,String path) {
		return resourceDao.getListByadmin(roleid,path);
	}
	
	@Override
	public Pager findByPager(Pager pager) {
		Integer totalCount = resourceDao.resourceCount(pager);
		List<Resources> resourceList = resourceDao.getResourcePager(pager);
		pager.setTotalCount(totalCount);
		pager.setList(resourceList);
		return pager;
	}
	


}