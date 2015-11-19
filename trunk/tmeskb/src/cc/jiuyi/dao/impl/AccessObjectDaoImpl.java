package cc.jiuyi.dao.impl;

import java.util.List;
import java.util.Map;

import cc.jiuyi.bean.Pager;
import cc.jiuyi.dao.AccessObjectDao;
import cc.jiuyi.dao.AdminDao;
import cc.jiuyi.entity.AccessObject;
import cc.jiuyi.entity.AccessResource;
import cc.jiuyi.entity.Admin;
import cc.jiuyi.entity.WorkingBill;
import cc.jiuyi.util.ThinkWayUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Dao实现类 - 权限对象
 */

@Repository
public class AccessObjectDaoImpl extends BaseDaoImpl<AccessObject, String> implements AccessObjectDao {
	@Override
	public List<AccessObject> findTypeList(String value) {
		String hql="form AccessObject where type=?";
		return getSession().createQuery(hql).setParameter(0, value).list();
	}

	@Override
	public List<AccessObject> findResourceList(Object[] resourceids,String accObjKey) {
		String hql="select a from AccessObject a join a.accessResourceSet b where  a.accObjkey = ? and b.id in (:list) ";
		return getSession().createQuery(hql).setParameter(0, accObjKey).setParameterList("list", resourceids).list();
	}
	
	
}