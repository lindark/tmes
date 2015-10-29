package cc.jiuyi.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.bean.SystemConfig;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.dao.ProsDao;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.Pros;
import cc.jiuyi.entity.Role;
import cc.jiuyi.service.ProsService;
import cc.jiuyi.util.SystemConfigUtil;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service实现类 - 后台权限认证
 */

@Service
@Transactional
public class ProsServiceImpl implements ProsService {
	@Resource
	private ProsDao prosdao;

	@Override
	public Pros get(String id) {
		return null;
	}

	@Override
	public Pros load(String id) {
		return null;
	}

	@Override
	public List<Pros> get(String[] ids) {
		return null;
	}

	@Override
	public Pros get(String propertyName, Object value) {
		return null;
	}

	@Override
	public List<Pros> getList(String propertyName, Object value) {
		return null;
	}

	@Override
	public List<Pros> getAll() {
		return null;
	}

	@Override
	public Long getTotalCount() {
		return null;
	}

	@Override
	public boolean isUnique(String propertyName, Object oldValue,
			Object newValue) {
		return false;
	}

	@Override
	public boolean isExist(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String save(Pros entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Pros entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Pros entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String[] ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void evict(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Pager findByPager(Pager pager) {
		return prosdao.findByPager(pager);
	}

	@Override
	public Pager findByPager(Pager pager, DetachedCriteria detachedCriteria) {
		// TODO Auto-generated method stub
		return null;
	}


}