package cc.jiuyi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import cc.jiuyi.dao.AccessResourceDao;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Role;


/**
 * Dao实现类 - 权限对象
 */

@Repository
public class AccessResourceDaoImpl extends BaseDaoImpl<AccessResource, String> implements AccessResourceDao {

	public List<AccessResource> findListBypath(String path,String loginid){
		//String hql="from AccessResource inner join "
		
		return null;
	}

	@Override
	public List<AccessResource> findAccessByRoles(Object[] roleids) {
		String hql="from AccessResource where role.id in(:list)";
		return getSession().createQuery(hql).setParameterList("list", roleids).list();
	}

	

}